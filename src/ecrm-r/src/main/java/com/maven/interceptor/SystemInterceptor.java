package com.maven.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.maven.controller.BaseController;
import com.maven.logger.LoggerManager;
import com.maven.logger.SwithObject;
import com.maven.logger.SystemVisiteLog;
import com.maven.logger.TLogger;

@Repository
public class SystemInterceptor extends HandlerInterceptorAdapter {

	@SuppressWarnings("unused")
	private static LoggerManager log = LoggerManager.getLogger("MAIN", new SwithObject(true));

	/** URL白名单 */
	private List<String> excludedUrls;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		request.getSession().setMaxInactiveInterval(1800);
		String requestUri = request.getRequestURI().replaceAll("/+", "/");
		// 静态资源
		if (excluded(requestUri, excludedUrls))
			return true;
		// 初始化运行日志环境
		setRunningLogEnvironment(handler);
		// 请求统计
		SystemVisiteLog.ApiTrigger(requestUri);
		return true;
	}


	private boolean excluded(String requestUri, List<String> excludedlist) {
		for (String url : excludedlist) {
			if (requestUri.endsWith(url)) {
				return true;
			}
		}
		return false;
	}

	private void setRunningLogEnvironment(Object handler) {
		if (handler instanceof HandlerMethod) {
			HandlerMethod base = (HandlerMethod) handler;
			LoggerManager log = ((BaseController) base.getBean()).getLogger();
			TLogger.setLogger(log);
		}
	}

	public void setExcludedUrls(List<String> excludedUrls) {
		this.excludedUrls = excludedUrls;
	}

}
