package com.maven.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.constant.Constant;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.LogOperation;
import com.maven.entity.LogOperationDetail;
import com.maven.logger.LoggerManager;
import com.maven.service.LogOperationDetailService;
import com.maven.service.LogOperationService;

@RequestMapping("/operatingLog")
@Controller
public class EmployeeOperatingLogController extends BaseController{
	
	@Autowired
	LogOperationService logOperationServiceImpl;
	
	@Autowired
	LogOperationDetailService logOperationDetailServiceImpl;
	
	@RequestMapping("/index")
	public String operatingList(){
		return "/logger/operatingLogIndex";
	}
	
	/**
	 * 查询操作日志
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/findOperatingLog")
	@ResponseBody
	public Map<String,Object> findOperatingLog(HttpServletRequest request,HttpSession session){
		Map<String,Object> map = getRequestParamters(request);
		//获取登录用户的信息
		EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
		try {
			super.dataLimits(employee, map,session);
			List<LogOperation> list = logOperationServiceImpl.findOperatingLog(map);
			int count = logOperationServiceImpl.findOperatingLogCount(map);
			return super.formatPagaMap(list, count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.formatPagaMap(null, 0);
	}
	
	/**
	 * 查询操作日志详情
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/findOperatingLogDetail")
	@ResponseBody
	public Map<String,Object> findOperatingLogDetail(HttpServletRequest request,HttpSession session){
		Map<String,Object> map = getRequestParamters(request);
		Map<String,Object> result = new HashMap<String,Object>();
		//获取登录用户的信息
		//EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
		try {
			//this.dataLimits(employee, map);
			List<LogOperationDetail> list = logOperationDetailServiceImpl.findOperatingLogDetail(map);
			result.put("rows", list);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	@Override
	public LoggerManager getLogger() {
		return null;
	}
}
