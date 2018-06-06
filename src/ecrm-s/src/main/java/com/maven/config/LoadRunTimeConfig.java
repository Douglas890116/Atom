package com.maven.config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.maven.cache.SystemCache;

public class LoadRunTimeConfig extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
		//加载缓存
		SystemCache.getInstance();
		
	}

}
