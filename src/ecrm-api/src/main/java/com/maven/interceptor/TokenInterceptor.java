package com.maven.interceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.maven.annotation.Token;
import com.maven.logger.LoggerManager;
import com.maven.logger.SwithObject;
/**
 * 用于拦截订单回调重复提交
 * @author zack
 *
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {

	private static LoggerManager log = LoggerManager.getLogger("TOKEN", new SwithObject(true));

	/** 3 秒内禁止重复请求 */
	private static final long timeout = 1000 * 3;
	/** 进行过回调的订单号 */
	private static Map<String, Long> orderIdMap = new HashMap<String, Long>();
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 清理订单数据
		removeTimeOutOrderId();
		
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			Token annotation = method.getAnnotation(Token.class);
			if (annotation != null) {
				if (isRepeatSubmit(request, annotation.value())) {
					log.Warn("please don't repeat submit, url:" + request.getServletPath());
					return false;
				}
			}
		}
		return super.preHandle(request, response, handler);  
	}

	private boolean isRepeatSubmit(HttpServletRequest request, String orderIdName) {
		if (orderIdName == null || StringUtils.isBlank(orderIdName)) return false;
		String client_orderId = request.getParameter(orderIdName);
		Long lastRequestTime = orderIdMap.get(client_orderId);
		Long nowRequestTime = System.currentTimeMillis();
		if (lastRequestTime == null) {
			orderIdMap.put(client_orderId, nowRequestTime);
			return false;
		}
		if ((nowRequestTime - lastRequestTime) > timeout) {
			orderIdMap.put(client_orderId, nowRequestTime);
			return false;
		}
		return true;
	}

	private synchronized void removeTimeOutOrderId() {
		if (orderIdMap == null || orderIdMap.size() <= 0) return;
		Long timestamp = System.currentTimeMillis();
		Set<String> keySet = orderIdMap.keySet();
		Iterator<String> keys = keySet.iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			Long value = orderIdMap.get(key);
			// 删除10分钟前的数据
			if (timestamp - value > (1000 * 60 * 10))
				keys.remove();
		}
	}
}