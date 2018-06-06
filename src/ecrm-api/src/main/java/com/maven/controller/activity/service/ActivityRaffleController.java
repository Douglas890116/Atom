package com.maven.controller.activity.service;

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
import com.maven.service.ActivityRaffleControlService;
import com.maven.service.ActivityRaffleRecordService;
import com.maven.service.ActivityRaffleSigninService;
import com.maven.util.AttrCheckout;
import com.maven.util.DateUtils;

/**
 * 签到抽奖
 * @author Administrator
 *
 */
@RequestMapping("/SignRaffle")
@Controller
public class ActivityRaffleController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			ActivityRaffleController.class.getName(), OutputManager.LOG_ACTIVITYRAFFLE);

	@Autowired
	private ActivityRaffleControlService activityRaffleControlService;
	@Autowired
	private ActivityRaffleRecordService activityRaffleRecordService;
	@Autowired
	private ActivityRaffleSigninService activityRaffleSigninService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"/Draw"},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String SignInRaffle(HttpServletRequest request){
		try {
			Map<String,Object> __parames = (Map<String,Object>)request.getAttribute("activityparams");
			AttrCheckout.checkout(__parames, false, new String[]{"employeecode","enterprisebrandactivitycode","loginip"});
			Map<String,Object> __result = activityRaffleControlService.tc_raffle(
					String.valueOf(__parames.get("employeecode")),
					Integer.parseInt(String.valueOf(__parames.get("enterprisebrandactivitycode"))), __parames);
			if(__result.get("status").equals("fail")){
				return Enum_MSG.逻辑事物异常.message(__result.get("message"));
			}else{
				return Enum_MSG.成功.message(__result);
			}
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
	
	/**
	 * 查询签到活动数据接口
	 * @param request,session
	 * @return json 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"/SigninData"},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String signinData(HttpServletRequest request){
		try {
			Map<String,Object> parames = (Map<String,Object>)request.getAttribute("activityparams");
			AttrCheckout.checkout(parames, false, new String[]{"employeecode"});
			/*如果没有开始、结束时间，默认为当月*/
			String starttime = "";
			String endtime = "";
			if (parames.get("starttime") == null){
				starttime = DateUtils.getfirstDayofMonth(0);
			} else {
				starttime = parames.get("starttime").toString();
			}
			if (parames.get("endtime") == null){
				endtime = DateUtils.getlastDayofMonth(0);
			} else {
				endtime = parames.get("endtime").toString();
			}
			Map<String, Object> signinrecord_json = activityRaffleSigninService.getSigninRecordJson(String.valueOf(parames.get("employeecode")),
					starttime, endtime);
			return Enum_MSG.成功.message(signinrecord_json);
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
	
	/**
	 * 查询抽奖记录数据接口
	 * @param request,session
	 * @return json 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"/RaffleData"},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String raffleData(HttpServletRequest request){
		try {
			Map<String,Object> parames = (Map<String,Object>)request.getAttribute("activityparams");
			AttrCheckout.checkout(parames, false, new String[]{"employeecode"});
			/*如果没有开始、结束时间，默认为当月*/
			String starttime = "";
			String endtime = "";
			if (parames.get("starttime") == null){
				starttime = DateUtils.getfirstDayofMonth(0);
			} else {
				starttime = parames.get("starttime").toString();
			}
			if (parames.get("endtime") == null){
				endtime = DateUtils.getlastDayofMonth(0);
			} else {
				endtime = parames.get("endtime").toString();
			}
			Map<String, Object> rafflerecord_json = activityRaffleRecordService.getRaffleRecordJson(String.valueOf(parames.get("employeecode")),
					starttime, endtime);
			return Enum_MSG.成功.message(rafflerecord_json);
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
