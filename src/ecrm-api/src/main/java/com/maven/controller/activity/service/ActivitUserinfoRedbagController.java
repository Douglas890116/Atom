package com.maven.controller.activity.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.constant.Enum_MSG;
import com.maven.controller.BaseController;
import com.maven.controller.member.MessageController;
import com.maven.entity.ActivityRedbag;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.ActivityRaffleControlService;
import com.maven.service.ActivityRaffleRecordService;
import com.maven.service.ActivityRedbagService;
import com.maven.util.AttrCheckout;
import com.maven.util.DateUtils;


/**
 * 完善资料领红包
 * @author Administrator
 *
 */
@RequestMapping("/UserinfoRedbag")
@Controller
public class ActivitUserinfoRedbagController extends BaseController{

	private static LoggerManager log = LoggerManager.getLogger(
			MessageController.class.getName(), OutputManager.LOG_MEMBERACTIVITYDISPATCH);
	
	@Autowired
	private ActivityRedbagService activityRedbagService;
	
	
	/**
	 * 送红包
	 * @param request,session
	 * @return json 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"/Draw"},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String depositBonus(HttpServletRequest request){
		
		try {
			Map<String,Object> parames = (Map<String,Object>)request.getAttribute("activityparams");
			AttrCheckout.checkout(parames, false, new String[]{"employeecode","enterprisebrandactivitycode","loginip"});
			
					
			Map<String,Object> __result = activityRedbagService.tc_redbag_userinfo(
					String.valueOf(parames.get("employeecode")),
					Integer.parseInt(String.valueOf(parames.get("enterprisebrandactivitycode"))), 
					String.valueOf(parames.get("loginip")), String.valueOf(parames.get("fingerprintcode")));
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
	 * 用户是否满足领完善资料领红包的条件
	 * @param request,session
	 * @return json 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"/IsUserInfobag"},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String IsUserInfobag(HttpServletRequest request){
		try {
			Map<String,Object> parames = (Map<String,Object>)request.getAttribute("activityparams");
			AttrCheckout.checkout(parames, false, new String[]{"employeecode"});
			
			Map<String,Object> __returnAument = new HashMap<String, Object>();
			
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("employeecode", parames.get("employeecode").toString());
			params.put("redbagtype", ActivityRedbag.Enum_redbagtype.完善资料领红包.value.toString());
			int count = activityRedbagService.selectBetRecordCount(params);
			
			if(count > 0) {
				__returnAument.put("status", "fail");
				__returnAument.put("message", "已经领取过红包了");
			} else {
				__returnAument.put("status", "success");
				__returnAument.put("message", "可以领取");
			}
			
			return Enum_MSG.成功.message(__returnAument);
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
	 * 查询所有的红包，包括签到和注册红包和完善资料领红包
	 * @param request,session
	 * @return json 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"/RedbagData"},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String raffleData(HttpServletRequest request){
		try {
			Map<String,Object> parames = (Map<String,Object>)request.getAttribute("activityparams");
			AttrCheckout.checkout(parames, false, new String[]{"employeecode"});
					
			
			if(parames.get("startime") != null  ) {
				if(parames.get("startime").toString().length() == 10) {
					parames.put("startime", parames.get("startime").toString().replaceAll("-", ""));
				} else {
					parames.remove("startime");
				}
			}
			if(parames.get("endtime") != null ) {
				if(parames.get("endtime").toString().length() == 10) {
					parames.put("endtime", parames.get("endtime").toString().replaceAll("-", ""));
				} else {
					parames.remove("endtime");
				}
			}
			
			System.out.println("===============================红包记录查询条件："+parames);
			
			List<ActivityRedbag> listActivityRedbag = activityRedbagService.selectBetRecord(parames);
			int count = activityRedbagService.selectBetRecordCount(parames);
			
			Map<String,Object> returnObject = new HashMap<String, Object>();
			returnObject.put("record", listActivityRedbag);
			returnObject.put("count", count);
			
			return Enum_MSG.成功.message(returnObject);
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
