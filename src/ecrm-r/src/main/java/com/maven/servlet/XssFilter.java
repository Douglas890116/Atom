package com.maven.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class XssFilter implements Filter{

	public FilterConfig filterConfig = null;
	
	private static final List<String> SIMPLE_HTTP_METHODS = Arrays.asList("GET", "POST", "HEAD");

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
        this.filterConfig = null;
    }

    public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) req), res);
    }

    /**
     * 跨域处理
     * @param response
     * @return
     */
	@SuppressWarnings("unused")
	private HttpServletResponse originHandle(HttpServletRequest request,ServletResponse res) {
		HttpServletResponse response = (HttpServletResponse)res;
		String origin = request.getHeader("Origin");
		if(origin!=null && isEnabled(request)){
			if (isSimpleRequest(request)){
				response.setHeader("Access-Control-Allow-Origin", "*");
				response.setHeader("Access-Control-Allow-Credentials", "true");
			}
		}
		return response;
	}
	
	private boolean isEnabled(HttpServletRequest request){
		if ("Upgrade".equalsIgnoreCase(request.getHeader("Connection")) &&
		"WebSocket".equalsIgnoreCase(request.getHeader("Upgrade"))){
			return false;
		}
		return true;
	}
	
	private boolean isSimpleRequest(HttpServletRequest request){
		String method = request.getMethod();
		if (SIMPLE_HTTP_METHODS.contains(method)){
			return request.getHeader("Access-Control-Request-Method") == null;
		}
		return false;
	}
}
