package com.hy.pull.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


/**
 * 共公的拦截器
 * 创建日期 2016-10-06
 * @author temdy
 */
public class CommonInterceptor implements HandlerInterceptor {

	public CommonInterceptor() {
	}
	
	/**
	 * 在DispatcherServlet完全处理完请求后被调用
	 * 创建日期 2016-10-06
	 * @author temdy
	 * @param request 请求对象
	 * @param response 响应对象
	 * @param handler 处理器对象
	 * @param ex 异常对象
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}


	/**
	 * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
	 * 创建日期 2016-10-06
	 * @author temdy
	 * @param request 请求对象
	 * @param response 响应对象
	 * @param handler 处理器对象
	 * @param modelAndView 模型和视图对象
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	
	/**
	 * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
	 * 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链，
	 * 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion()
	 * 创建日期 2016-10-06
	 * @author temdy
	 * @param request 请求对象
	 * @param response 响应对象
	 * @param handler 处理器对象
	 * @return 是否通过{true,false}
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String path = request.getRequestURI().toString().replace("/frames", "");
		request.setAttribute("path", path);	
		HttpSession session = request.getSession();
		//如果session中不存在管理员的信息，跳转到登录页面
		if (session.getAttribute("sessionAdmin") != null) {
			String list = session.getAttribute("powerList").toString();		
			if (list.contains(path)) {
				return true;
			}else{
				response.sendRedirect(request.getContextPath()+"/manager/system/error.do");
				return false;
			}
		} else {
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return false;
		}
	}
	
	/**
	 * 构造新的路径
	 * @author temdy
	 * @param path 请求路径
	 * @return 新的路径
	 */
	public String getNewPath(String path) {
		String p1 = path.substring(0,path.lastIndexOf("/"));
		p1 = p1.substring(p1.lastIndexOf("/")+1);
		String p2 = path.substring(path.lastIndexOf("/"));
		return p1+p2;
	}
}