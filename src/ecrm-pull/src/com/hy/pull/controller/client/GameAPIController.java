package com.hy.pull.controller.client;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 游戏API接口控制中心
 * 创建日期 2016-11-15
 * @author temdy
 */
@Controller
@Scope("request")
@RequestMapping("/api")
public class GameAPIController {
	
	/**
	 * 添加帐号接口
	 * @param entity 提交表单集合
	 * @param request 请求对象
	 * @param response 响应对象
	 * @return Object 操作结果
	 */
	@RequestMapping(value = "/createAccount")
	@ResponseBody
	public Object createAccount(@RequestParam Map<String, Object> entity, HttpServletRequest request, HttpServletResponse response) {	
		return null;
	}
	
	/**
	 * 获取余额接口
	 * @param entity 提交表单集合
	 * @param request 请求对象
	 * @param response 响应对象
	 * @return Object 操作结果
	 */
	@RequestMapping(value = "/getBalance")
	@ResponseBody
	public Object getBalance(@RequestParam Map<String, Object> entity, HttpServletRequest request, HttpServletResponse response) {	
		return null;
	}

	
	/**
	 * 下分（取款）接口
	 * @param entity 提交表单集合
	 * @param request 请求对象
	 * @param response 响应对象
	 * @return Object 操作结果
	 */
	@RequestMapping(value = "/withdraw")
	@ResponseBody
	public Object withdraw(@RequestParam Map<String, Object> entity, HttpServletRequest request, HttpServletResponse response) {	
		return null;
	}
	
	/**
	 * 上分（存款）接口
	 * @param entity 提交表单集合
	 * @param request 请求对象
	 * @param response 响应对象
	 * @return Object 操作结果
	 */
	@RequestMapping(value = "/deposit")
	@ResponseBody
	public Object deposit(@RequestParam Map<String, Object> entity, HttpServletRequest request, HttpServletResponse response) {	
		return null;
	}

	
	/**
	 * 获取游戏接口
	 * @param entity 提交表单集合
	 * @param request 请求对象
	 * @param response 响应对象
	 * @return Object 操作结果
	 */
	@RequestMapping(value = "/getRecord")
	@ResponseBody
	public Object getRecord(@RequestParam Map<String, Object> entity, HttpServletRequest request, HttpServletResponse response) {	
		return null;
	}
	
	/**
	 * 更新信息接口
	 * @param entity 提交表单集合
	 * @param request 请求对象
	 * @param response 响应对象
	 * @return Object 操作结果
	 */
	@RequestMapping(value = "/updateInfo")
	@ResponseBody
	public Object updateInfo(@RequestParam Map<String, Object> entity, HttpServletRequest request, HttpServletResponse response) {	
		return null;
	}

	
	/**
	 * 游戏跳转接口
	 * @param entity 提交表单集合
	 * @param request 请求对象
	 * @param response 响应对象
	 * @return Object 操作结果
	 */
	@RequestMapping(value = "/jump")
	@ResponseBody
	public Object jump(@RequestParam Map<String, Object> entity, HttpServletRequest request, HttpServletResponse response) {	
		return null;
	}
	
	/**
	 * 游戏跳转接口
	 * @param entity 提交表单集合
	 * @param request 请求对象
	 * @param response 响应对象
	 * @return Object 操作结果
	 */
	@RequestMapping(value = "/login")
	@ResponseBody
	public Object login(@RequestParam Map<String, Object> entity, HttpServletRequest request, HttpServletResponse response) {	
		return null;
	}
	
	/**
	 * 是否在线接口
	 * @param entity 提交表单集合
	 * @param request 请求对象
	 * @param response 响应对象
	 * @return Object 操作结果
	 */
	@RequestMapping(value = "/isOnLine")
	@ResponseBody
	public Object isOnLine(@RequestParam Map<String, Object> entity, HttpServletRequest request, HttpServletResponse response) {	
		return null;
	}
}
