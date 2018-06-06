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
import com.maven.service.ActivityRegBonusService;
import com.maven.util.AttrCheckout;

@RequestMapping("/RegBonus")
@Controller
public class ActivityRegBonusController extends BaseController{

	private static LoggerManager log = LoggerManager.getLogger(
			ActivityRegBonusController.class.getName(), OutputManager.LOG_MEMBERACTIVITYDISPATCH);
	@Autowired
	private ActivityRegBonusService activityRegBonusService;
	
	
	/**
	 * 开业大酬宾 体验红包大派送接口
	 * @param request,session
	 * @return json 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"/Draw"},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String registerBonus(HttpServletRequest request){
		BigDecimal amount = new BigDecimal(0);
		try {
			Map<String,Object> parames = (Map<String,Object>)request.getAttribute("activityparams");
			AttrCheckout.checkout(parames, false, new String[]{"employeecode","enterprisebrandactivitycode","loginip"});
			amount = activityRegBonusService.tc_getRegBonus(String.valueOf(parames.get("employeecode")), 
					Integer.valueOf(parames.get("enterprisebrandactivitycode").toString()), String.valueOf(parames.get("loginip")));
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
		Map<String, Object> resultmap = new HashMap<String, Object>();
		resultmap.put("status", "success");
		resultmap.put("message", amount.intValue());
		return Enum_MSG.成功.message(resultmap);
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}

	
}
