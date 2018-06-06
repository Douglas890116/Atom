package com.maven.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityBetRecord.Enum_ecactivitycode;
import com.maven.entity.EnterpriseActivityCustomization;
import com.maven.entity.EnterpriseBrandActivity;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.UserLogs;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.EnterpriseActivityCustomizationService;
import com.maven.service.EnterpriseBrandActivityService;
import com.maven.service.UserLogsService;
import com.maven.utils.StringUtil;

@Controller
@RequestMapping("/userlog")
public class UserLogsController extends BaseController {
	
	private LoggerManager log = LoggerManager.getLogger(UserLogsController.class.getName(),
			OutputManager.LOG_ACTIVITYBETRECORD);
	@Autowired
	private UserLogsService userLogsService;
	
	
	/**
	 * 会员操作日志
	 * @param request
	 * @return
	 */
	@RequestMapping("/userlog")
	public String index1(HttpServletRequest request, Model model){
		try {
			Map<String, String> typeMap = new HashMap<String, String>();
		    for (UserLogs.Enum_operatype type : UserLogs.Enum_operatype.values()) {
		        typeMap.put(type.value, type.desc);
		    }
		    model.addAttribute("operatypes", typeMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/userlog/userlog";
	}
	/**
	 * 后台操作日志
	 * @param request
	 * @return
	 */
	@RequestMapping("/adminlog")
	public String index2(HttpServletRequest request, Model model){
		try {
			Map<String, String> typeMap = new HashMap<String, String>();
		    for (UserLogs.Enum_operatype type : UserLogs.Enum_operatype.values()) {
		        typeMap.put(type.value, type.desc);
		    }
		    model.addAttribute("operatypes", typeMap);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/userlog/adminlog";
	}
	
	/**
	 * list页面请求数据
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/userlogdata")
	@ResponseBody
	public Map<String, Object> userlogdata(HttpServletRequest request, HttpSession session){
		try {		
			Map<String, Object> parameter = getRequestParamters(request);
			
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			parameter.put("isuserlog", "sure");			
			
			//*********非超级管理员时只能查询本团队内的数据************//*
			if(!SystemConstant.getProperties("admin.employeecode").equals(loginEmployee.getEmployeecode())){
				parameter.put("enterprisecode", loginEmployee.getEnterprisecode());
			} 
			
			List<UserLogs> betrecords = userLogsService.selectBetRecord(parameter);
			int count = userLogsService.selectBetRecordCount(parameter);
			return super.formatPagaMap(betrecords, count);
			
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * list页面请求数据
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/adminlogdata")
	@ResponseBody
	public Map<String, Object> adminlogdata(HttpServletRequest request, HttpSession session){
		try {		
			Map<String, Object> parameter = getRequestParamters(request);
			
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			parameter.put("isadminlog", "sure");			
			
			//*********非超级管理员时只能查询本团队内的数据************//*
			if(!SystemConstant.getProperties("admin.employeecode").equals(loginEmployee.getEmployeecode())){
				parameter.put("enterprisecode", loginEmployee.getEnterprisecode());
			} 
			
			List<UserLogs> betrecords = userLogsService.selectBetRecord(parameter);
			int count = userLogsService.selectBetRecordCount(parameter);
			return super.formatPagaMap(betrecords, count);
			
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public LoggerManager getLogger() {
		return log;
	}
	
}
