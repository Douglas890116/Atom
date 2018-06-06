package com.maven.controller.activity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.constant.Enum_MSG;
import com.maven.controller.BaseController;
import com.maven.controller.member.MessageController;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityBetRecord.Enum_ecactivitycode;
import com.maven.entity.ActivityTemplate.Enum_activity;
import com.maven.entity.EnterpriseActivityCustomization;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.EnterpriseActivityCustomizationService;
import com.maven.util.StringUtils;

@RequestMapping("/ActivityData")
@Controller
public class ActivityDataDispatchController extends BaseController{

	private static LoggerManager log = LoggerManager.getLogger(
			MessageController.class.getName(), OutputManager.LOG_BRANDACTIVITYDISPATCH);
	
	@Autowired
	private EnterpriseActivityCustomizationService enterpriseActivityCustomizationService;
	@Autowired
	private ActivityBetRecordService activityBetRecordService;
	
	
	@RequestMapping("/trigger")
	public void activity(HttpServletRequest request,HttpServletResponse response,Model model){
		try {
			Map<String,Object> __parames = super.apiDecode(super.getRequestParamters(request));
			request.setAttribute("activityparams", __parames);
			String __enterprisebrandactivitycode = String.valueOf(__parames.get("enterprisebrandactivitycode"));
			EnterpriseActivityCustomization __eacustomization = 
					enterpriseActivityCustomizationService.selectByEnterprisebrandactivitycode(__enterprisebrandactivitycode);
			if(__eacustomization.getActivitytemplatecode().equals(Enum_activity.幸运抽奖.activitycode)){
				String way = String.valueOf(__parames.get("way"));
				if (way.equals(Enum_data.签到记录.value)){
					request.getRequestDispatcher("/SignRaffle/SigninData").forward(request, response);
				} else if (way.equals(Enum_data.抽奖记录.value)){
					request.getRequestDispatcher("/SignRaffle/RaffleData").forward(request, response);					
				}
				
			}else if(__eacustomization.getActivitytemplatecode().equals(Enum_activity.充值大抽奖.activitycode)){
				request.getRequestDispatcher("/DepositRaffle/RaffleData").forward(request, response);					
				
			}else if(__eacustomization.getActivitytemplatecode().equals(Enum_activity.充值赠送高级版.activitycode)){
				request.getRequestDispatcher("/DepositRaffle/RaffleData").forward(request, response);
				
			} else if(__eacustomization.getActivitytemplatecode().equals(Enum_activity.注册领红包.activitycode)
				|| __eacustomization.getActivitytemplatecode().equals(Enum_activity.签到领红包.activitycode)){
				request.getRequestDispatcher("/RegRedbag/RedbagData").forward(request, response);
				
			} else {
				request.getRequestDispatcher("/ActivityData/notemplate").forward(request, response);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			try {
				request.getRequestDispatcher("/ActivityData/notemplate").forward(request, response);
			} catch (ServletException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				log.Error(e1.getMessage(), e1);
			}
		}
	}
	
	/**
	 * 优惠流水记录接口（即活动打码流水表）
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value={"/benefitRecord"}, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String benefitRecord(HttpServletRequest request){
		
		try {
			
			Map<String,Object> __parames = super.apiDecode(super.getRequestParamters(request));
			
			String enterprisecode = String.valueOf(__parames.get("enterprisecode"));
			String employeecode = String.valueOf(__parames.get("employeecode"));
			String createtime_begin = String.valueOf(__parames.get("createtime_begin"));
			String createtime_end = String.valueOf(__parames.get("createtime_end"));
			
			__parames.put("field", "createtime");
			__parames.put("direction", "desc");
					
            List<ActivityBetRecord> betrecords = activityBetRecordService.selectBetRecord(__parames);
            
			//查询企业所有活动,为所有记录的活动ID转换为活动名称
			List<EnterpriseActivityCustomization> alleac = enterpriseActivityCustomizationService.selectAllByEnterprisecode(enterprisecode);
			Map<Integer, String> eackeyvalue = new HashMap<Integer, String>();
			for (EnterpriseActivityCustomization eac : alleac) {
				eackeyvalue.put(eac.getEcactivitycode(), eac.getActivityname());
			}
			for (ActivityBetRecord abr : betrecords) {
				if (eackeyvalue.containsKey(abr.getEcactivitycode())){
					abr.setActivityname(eackeyvalue.get(abr.getEcactivitycode()));
				} else if (abr.getEcactivitycode().equals(Enum_ecactivitycode.存款流水.value)) {
					abr.setActivityname(Enum_ecactivitycode.存款流水.desc);
				} else {
					abr.setActivityname(Enum_ecactivitycode.UNKNOW.desc);
				}
			}
					
			Map<String,Object> __result = new HashMap<String, Object>(); 
			__result.put("count", betrecords.size());
			__result.put("record", betrecords);
			
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
	
	
	@RequestMapping(value={"/notemplate"},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String notemplate(){
		return Enum_MSG.逻辑事物异常.message("企业活动不存在");
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
	
	/**
	 * 活动数据信息请求类型
	 */
	public enum Enum_data{
		签到记录("SigninData","签到记录"),
		抽奖记录("RaffleData","抽奖记录"),
		;
		public String value;
		public String desc;
		
		private Enum_data(String value,String desc){
			this.value=value;
			this.desc=desc;
		}
	}
}
