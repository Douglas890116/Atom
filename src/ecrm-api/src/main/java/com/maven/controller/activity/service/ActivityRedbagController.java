package com.maven.controller.activity.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.constant.Enum_MSG;
import com.maven.controller.BaseController;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.ActivityRedbagService;
import com.maven.service.ActivityRegBonusService;
import com.maven.util.AttrCheckout;

@RequestMapping("/Redbag")
@Controller
public class ActivityRedbagController extends BaseController{

	private static LoggerManager log = LoggerManager.getLogger(
			ActivityRedbagController.class.getName(), OutputManager.LOG_MEMBERACTIVITYDISPATCH);
	@Autowired
	private ActivityRedbagService activityRedbagService;
	
	
	/**
	 * 签到领红包
	 * @param request,session
	 * @return json 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"/Draw"},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String registerBonus(HttpServletRequest request){
		try {
			Map<String,Object> parames = (Map<String,Object>)request.getAttribute("activityparams");
			AttrCheckout.checkout(parames, false, new String[]{"employeecode","enterprisebrandactivitycode","loginip"});
			
			Map<String,Object> __result = activityRedbagService.tc_redbag(
					String.valueOf(parames.get("employeecode")), 
					Integer.valueOf(parames.get("enterprisebrandactivitycode").toString()), 
					String.valueOf(parames.get("loginip")));
			
			return Enum_MSG.成功.message(__result);
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch(LogicTransactionRollBackException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}

	
}
