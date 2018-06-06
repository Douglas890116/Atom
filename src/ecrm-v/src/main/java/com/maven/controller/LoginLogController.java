package com.maven.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.LogLogin;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.LogLoginService;
import com.maven.utils.HttpsGetUtil;

@Controller
@RequestMapping("/LoginLog")
public class LoginLogController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			LoginLogController.class.getName(), OutputManager.LOG_LOGINLOG);
	
	@Autowired
	private LogLoginService logLoginService;
	/**
	 * 跳转登陆日志查询页面
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model,HttpServletRequest request){
		model.addAttribute("loginIp", request.getParameter("loginip"));
		return "/logger/loginloglist";
	}
	
	/**
	 * 跳转登陆日志查询页面
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String loginIndex(){
		return "/logger/loginIndex";
	}
	
	@RequestMapping("/data")
	@ResponseBody
	public Map<String,Object> data(HttpServletRequest request , HttpServletResponse response , HttpSession session){
		try {
			Map<String,Object> object = getRequestParamters(request);
			//获取登录用户的信息
			EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			super.dataLimits(employee, object,session);
			List<LogLogin> list = logLoginService.selectAll(object);
			int count = logLoginService.selectAllCount(object);
			return super.formatPagaMap(list, count);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	/**
	 * 统计IP上登录的所有用户
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping("/countIpLoginUser")
	@ResponseBody
	public Map<String,Object> countIpLoginUser(HttpServletRequest request , HttpServletResponse response , HttpSession session){
		try {
			Map<String,Object> object = getRequestParamters(request);
			//获取登录用户的信息
			EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			object.put("enterprisecode", employee.getEnterprisecode());
			List<LogLogin> list = logLoginService.queryIpLoginUser(object);
			int listcount = logLoginService.queryIpLoginUserCount(object);
			return super.formatPagaMap(list, listcount);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 查IP地区
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value  = "/findIp", produces="text/html;charset=GBK")
	@ResponseBody
	public String findIp(HttpServletRequest request , HttpServletResponse response , HttpSession session){
		try {
			Map<String,Object> object = getRequestParamters(request);
			String url = "http://ip.ws.126.net/ipquery?ip="+object.get("ip");
			//获取登录用户的信息
			String result = HttpsGetUtil.doHttpsGetJson(url);
			return result;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 登录日志首页
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/indexall")
	public String indexall(){
		return "/logger/indexall";
	}
	
	@RequestMapping("/indexdata")
	@ResponseBody
	public Map<String,Object> indexdata(HttpServletRequest request , HttpServletResponse response , HttpSession session){
		try {
			Map<String,Object> object = getRequestParamters(request);
			//获取登录用户的信息
			EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			
			/*********非超级管理员时只能查询本团队内的数据************/
			if(!SystemConstant.getProperties("admin.employeecode").equals(employee.getEmployeecode())){
				object.put("enterprisecode", employee.getEnterprisecode());
			}
			
			List<LogLogin> list = logLoginService.selectAll(object);
			int count = logLoginService.selectAllCount(object);
			return super.formatPagaMap(list, count);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}

}
