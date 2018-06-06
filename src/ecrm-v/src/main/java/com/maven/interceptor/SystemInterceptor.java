package com.maven.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.maven.annotation.OperationLog;
import com.maven.annotation.PrivacyData;
import com.maven.annotation.RequestInterval;
import com.maven.constant.Constant;
import com.maven.controller.BaseController;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.LogOperation;
import com.maven.entity.PermissionMenu;
import com.maven.entity.PermissionMenu.Enum_menustatus;
import com.maven.logger.LoggerManager;
import com.maven.logger.SwithObject;
import com.maven.logger.SystemVisiteLog;
import com.maven.logger.TLogger;
import com.maven.logger.ThreadOparetionLogger;
import com.maven.privacy.PrivacyEntity;
import com.maven.privacy.ThreadPrivateDate;
import com.maven.util.RelactionMenu;
import com.maven.utility.ContextUrlManager;

@Repository
public class SystemInterceptor extends HandlerInterceptorAdapter {

	private static LoggerManager log = LoggerManager.getLogger("MAIN", new SwithObject(true));

	/** 静态资源白名单 */
	private List<String> excludedUrls;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		request.getSession().setMaxInactiveInterval(1800);
		
		String requestUri = request.getRequestURI().replaceAll("/+", "/");
		log.Debug("请求:" + requestUri + " 进入拦截器");
		// 静态资源
		if (excluded(requestUri, excludedUrls))
			return true;
		// 初始化运行日志环境
		setRunningLogEnvironment(handler);
		// 访问请求统计
		SystemVisiteLog.SystemTrigger(requestUri);
		// 请求时间间隔校验
		if (!requestInterval(request, handler, requestUri, response))
			return false;
		// 用户是否登录校验
		if (!sessionValidate(response, request))
			return false;
		// 操作化日志初始化
		setOperationLogEnvironment(handler, requestUri, request);
		// 用户隐私数据权限
		userPrivateDateAccess(request, handler);
		// 访问权限控制
		return permissionValidate(request, response, requestUri);
	}

	private boolean requestInterval(HttpServletRequest request, Object handler, String requestUri,
			HttpServletResponse response) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod base = (HandlerMethod) handler;
			RequestInterval requestInterval = base.getMethodAnnotation(RequestInterval.class);
			if (requestInterval != null) {
				@SuppressWarnings("unchecked")
				Map<String, Date> interval = (Map<String, Date>) request.getSession()
						.getAttribute(Constant.USER_REQUEST_INTERVAL);
				if (interval == null) {
					interval = new HashMap<String, Date>();
					request.getSession().setAttribute(Constant.USER_REQUEST_INTERVAL, interval);
				}
				Date date = interval.get(requestUri);
				interval.put(requestUri, new Date());
				if (date != null) {
					interval.put(requestUri, new Date());
					if ((new Date().getTime()) - date.getTime() <= requestInterval.millinsecond()) {
						request.setAttribute("intervaltime",requestInterval.millinsecond()/1000);
						String accept = request.getHeader("accept");
						if (accept.toLowerCase().indexOf("application/json") > -1) {
							request.getRequestDispatcher("/EmployeeMPG/ajaxRequestInterval").forward(request, response);
						} else {
							request.getRequestDispatcher("/EmployeeMPG/requestInterval").forward(request, response);
						}
						return false;
					}
				}
			}
		}
		return true;
	}

	private void userPrivateDateAccess(HttpServletRequest request, Object handler) {
		if (handler instanceof HandlerMethod) {
			HandlerMethod base = (HandlerMethod) handler;
			PrivacyData priss = base.getMethodAnnotation(PrivacyData.class);
			if (priss != null) {
				ThreadPrivateDate.setIsaccessmethod(new PrivacyEntity(true, priss.fileds()));
			} else {
				ThreadPrivateDate.setIsaccessmethod(null);
			}
			ThreadPrivateDate
					.setUserprivatedata((Boolean) request.getSession().getAttribute(Constant.PRIVATE_DATA_PSERSSION));
		}
	}

	private boolean excluded(String requestUri, List<String> excludedlist) {
		for (String url : excludedlist) {
			if (requestUri.endsWith(url)) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private boolean permissionValidate(HttpServletRequest request, HttpServletResponse response, String requestUri)
			throws Exception {
		Map<String, PermissionMenu> systemPserssion = (Map<String, PermissionMenu>) request.getSession().getAttribute(Constant.SYSTEM_PSERSSION);
				
		Map<String, PermissionMenu> userPerssion = (Map<String, PermissionMenu>) request.getSession().getAttribute(Constant.USER_PSERSSION);
				
//		EnterpriseEmployee loginEmployee = (EnterpriseEmployee) request.getSession().getAttribute(Constant.USER_SESSION);
		
		
		if (userPerssion != null) {
			Iterator<PermissionMenu> iterator = systemPserssion.values().iterator();
			while (iterator.hasNext()) {
				PermissionMenu pm = iterator.next();
				
				
				/****jason20170510
				if(loginEmployee.getLoginaccount().equals("jmtArvin")) {
					log.Error("jmtArvin pm=" + pm);
					log.Error("getMenuurl=" + pm.getMenuurl() );
					log.Error("requestUri=" + requestUri);
					if(pm != null){
						log.Error("getMenucode=" + pm.getMenucode());
					}
					log.Error("PermissionMenu menu="+userPerssion.get(pm.getMenucode()));
				}
				*/
				
				if (pm != null && pm.getMenuurl().length()>2 && requestUri.indexOf(pm.getMenuurl()) > -1) {
					PermissionMenu menu = userPerssion.get(pm.getMenucode());
					if (menu != null && menu.getMenustatus() == Enum_menustatus.启用.value) {
						PermissionMenu parentmenu = systemPserssion.get(pm.getParentmenucode());
						
						request.setAttribute(Constant.MENU_RELATION,
								new RelactionMenu(
										pm.getMenucode(),
										pm.getMenuname(),
										pm.getEnglishname(),
										pm.getParentmenucode(),
										parentmenu == null ? "" : parentmenu.getMenuname(),
										parentmenu == null ? "" : parentmenu.getEnglishname(),
										parentmenu == null ? "" : parentmenu.getMenuurl())
								);
						
						return true;
					} else {
						/*
						if(requestUri.equals("/common/resetLoginPassword") || requestUri.equals("/common/resetCapitalPassword")) {
							return true;
						}
						this.forword(request, response);
						log.Error("请求1:" + requestUri + " 被拦截.");
						return false;
						*/
					}
				}
				
			}
			log.Debug("请求:" + requestUri + " 通过拦截器");
			return true;
		} else {
			log.Error("请求2:" + requestUri + " 被拦截");
			this.forword(request, response);
			return false;
		}
	}

	private boolean sessionValidate(HttpServletResponse response, HttpServletRequest request) throws IOException {
		EnterpriseEmployee ee = (EnterpriseEmployee) request.getSession().getAttribute(Constant.USER_SESSION);
		if (ee == null) {
			String uri = request.getRequestURI().replace("/+", "/");
			String url = ContextUrlManager.getBasePath();
			if (uri.indexOf("h5") >= 0) url += "/h5/login";
			
			//System.out.println(url);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			StringBuilder builder = new StringBuilder();
			builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
			builder.append("alert(\"页面过期，请重新登录\");");
			builder.append("window.top.location.href=\"");
			builder.append(url);
			builder.append("\";</script>");
			out.print(builder.toString());
			out.close();
			return false;
		}
		return true;
	}

	private void setOperationLogEnvironment(Object handler, String requestUri, HttpServletRequest request) {
		if (handler instanceof HandlerMethod) {
			EnterpriseEmployee ee = (EnterpriseEmployee) request.getSession().getAttribute(Constant.USER_SESSION);
			HandlerMethod base = (HandlerMethod) handler;
			LoggerManager log = ((BaseController) base.getBean()).getLogger();
			TLogger.setLogger(log);
			OperationLog s = base.getMethodAnnotation(OperationLog.class);
			if (s != null) {
				ThreadOparetionLogger.setOperation(new LogOperation(s.value(), requestUri, ee.getEmployeecode(),
						ee.getParentemployeecode(), ee.getLoginaccount()));
			} else {
				ThreadOparetionLogger.setOperation(null);
			}
		}
	}

	private void setRunningLogEnvironment(Object handler) {
		if (handler instanceof HandlerMethod) {
			HandlerMethod base = (HandlerMethod) handler;
			LoggerManager log = ((BaseController) base.getBean()).getLogger();
			TLogger.setLogger(log);
		}
	}

	private void forword(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String accept = request.getHeader("accept");
		if (accept.toLowerCase().indexOf("application/json") > -1) {
			request.getRequestDispatcher("/EmployeeMPG/perssionIntercept").forward(request, response);
		} else {
			request.getRequestDispatcher("/EmployeeMPG/noPrivilege").forward(request, response);
		}
	}

	public void setExcludedUrls(List<String> excludedUrls) {
		this.excludedUrls = excludedUrls;
	}

}
