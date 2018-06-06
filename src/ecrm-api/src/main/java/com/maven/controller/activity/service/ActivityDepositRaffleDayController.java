package com.maven.controller.activity.service;

import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.constant.Enum_MSG;
import com.maven.controller.BaseController;
import com.maven.controller.member.MessageController;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.ActivityRaffleControlService;
import com.maven.service.ActivityRaffleRecordService;
import com.maven.util.AttrCheckout;
import com.maven.util.DateUtils;


/**
 * 每日充值抽奖一次
 * @author Administrator
 *
 */
@RequestMapping("/DepositRaffleDay")
@Controller
public class ActivityDepositRaffleDayController extends BaseController{

	private static LoggerManager log = LoggerManager.getLogger(
			MessageController.class.getName(), OutputManager.LOG_MEMBERACTIVITYDISPATCH);
	
	@Autowired
	private ActivityRaffleControlService activityRaffleControlService;
	@Autowired
	private ActivityRaffleRecordService activityRaffleRecordService;
	
	
	/**
	 * 抽奖
	 * @param request,session
	 * @return json 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"/Draw"},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String depositBonus(HttpServletRequest request){
		
		try {
			Map<String,Object> parames = (Map<String,Object>)request.getAttribute("activityparams");
			AttrCheckout.checkout(parames, false, new String[]{"employeecode","enterprisebrandactivitycode"});
			
					
			Map<String,Object> __result = activityRaffleControlService.cz_raffle_day(
					String.valueOf(parames.get("employeecode")),
					Integer.parseInt(String.valueOf(parames.get("enterprisebrandactivitycode"))), 
					parames);
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
			/*如果没有开始、结束时间，默认为当天*/
			String starttime = "";
			String endtime = "";
			if (parames.get("starttime") == null){
				starttime = DateUtils.getTodayByString();
			} else {
				starttime = parames.get("starttime").toString();
			}
			if (parames.get("endtime") == null){
				endtime = DateUtils.getTodayByDateTime();
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

	public static void main(String[] args) {
		System.out.println((new Double(10.0/3.0)).intValue());
		
	}
	
}
