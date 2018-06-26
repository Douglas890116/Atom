package com.hy.pull.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 基本操作控制中心
 * 创建日期 2016-10-07
 * @author temdy
 */
@Controller
@Scope("request")
public class BaseController {
	
	/**
	 * 跳转页面操作
	 * @author temdy
	 * @param entity 提交表单集合
	 * @param request 请求对象
	 * @param response 响应对象
	 * @return 跳转URL
	 */
	@RequestMapping(value = "/index")
	public String list(@RequestParam Map<String, Object> entity, HttpServletRequest request, HttpServletResponse response) {
		return "/manager/index";
	}
}