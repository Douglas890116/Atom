package com.maven.controller.activity;

import java.io.IOException;
import java.util.Date;
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
import com.maven.entity.ActivityTemplate.Enum_activity;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.interceptor.RepeatRequestIntercept;
import com.maven.entity.EnterpriseActivityCustomization;
import com.maven.entity.EnterpriseBrandActivity;
import com.maven.entity.EnterpriseEmployee;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.EnterpriseActivityCustomizationService;
import com.maven.service.EnterpriseBrandActivityService;
import com.maven.service.EnterpriseEmployeeService;

@RequestMapping("/MemBerActivity")
@Controller
public class MemberActivityDispatchController extends BaseController{

	private static LoggerManager log = LoggerManager.getLogger(
			MessageController.class.getName(), OutputManager.LOG_MEMBERACTIVITYDISPATCH);
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	
	@Autowired
	private EnterpriseActivityCustomizationService enterpriseActivityCustomizationService;
	@Autowired
	private EnterpriseBrandActivityService enterpriseBrandActivityService;
	
	/**
	 * 活动的总入口
	 * 
	 * "employeecode" = 会员编码
	 * "enterprisebrandactivitycode" = 绑定活动ID
	 * "loginip" = 登录IP地址
	 * "fingerprintcode" = 客户浏览器唯一性标识
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/trigger")
	public void activity(HttpServletRequest request,HttpServletResponse response,Model model){
		try {
			Map<String,Object> __parames = super.apiDecode(super.getRequestParamters(request));
			request.setAttribute("activityparams", __parames);
			String __enterprisebrandactivitycode = String.valueOf(__parames.get("enterprisebrandactivitycode"));
			
			//三秒限时
			//改为三分钟内禁止重复提交
			RepeatRequestIntercept.isIntercept(__parames.get("employeecode").toString() , request.getRequestURI().replaceAll("/+", "/"), 180000);
			
			final EnterpriseEmployee ee = enterpriseEmployeeService.takeEmployeeByCode(__parames.get("employeecode").toString());
			
			EnterpriseBrandActivity brandActivity = enterpriseBrandActivityService.selectByPrimaryKey(__enterprisebrandactivitycode);
			if(brandActivity == null || !brandActivity.getStatus().equals(EnterpriseBrandActivity.Enum_status.启用.value)) {
				request.getRequestDispatcher("/MemBerActivity/noactivity1").forward(request, response);
				log.Error("MemBerActivity=========trigger============查找绑定活动="+__enterprisebrandactivitycode);
				return;
			}
			// 活动是否进行中
			//Date1.after(Date2),当Date1大于Date2时，返回TRUE，当小于等于时，返回false
			//Date1.before(Date2)，当Date1小于Date2时，返回TRUE，当大于等于时，返回false
			Date currendate = new Date();
		    if( !(currendate.after(brandActivity.getBegintime()) && currendate.before(brandActivity.getEndtime()) )){
		    	request.getRequestDispatcher("/MemBerActivity/noactivity").forward(request, response);
		    	log.Error("MemBerActivity=========trigger============时间要求="+currendate.toLocaleString()+"=="+brandActivity.getBegintime().toLocaleString()+"=="+brandActivity.getEndtime().toLocaleString());
		    	return;
		    }
		    //检查是否指定品牌的用户才能参与该活动
			if(brandActivity.getBrandcode() != null && !brandActivity.getBrandcode().equals("") && !brandActivity.getBrandcode().equals(ee.getBrandcode())) {
				request.getRequestDispatcher("/MemBerActivity/noactivity2").forward(request, response);
				log.Error("MemBerActivity=========trigger============品牌要求="+brandActivity.getBrandcode()+"=="+ee.getBrandcode());
		    	return;
			}
			
			EnterpriseActivityCustomization __eacustomization = enterpriseActivityCustomizationService.selectByEnterprisebrandactivitycode(__enterprisebrandactivitycode);
			if(__eacustomization == null) {
				request.getRequestDispatcher("/MemBerActivity/noactivity3").forward(request, response);
				log.Error("MemBerActivity=========trigger============查找活动定制="+__enterprisebrandactivitycode);
				return;
			}
			
			
			
			if(__eacustomization.getActivitytemplatecode().equals(Enum_activity.注册彩金.activitycode)){
				request.getRequestDispatcher("/RegBonus/Draw").forward(request, response);
			}else if(__eacustomization.getActivitytemplatecode().equals(Enum_activity.充值赠送.activitycode)){
				request.getRequestDispatcher("/DepositBonus/Draw").forward(request, response);
			}else if(__eacustomization.getActivitytemplatecode().equals(Enum_activity.输钱返利.activitycode)){
				request.getRequestDispatcher("/MonthLoseBonus/Draw").forward(request, response);
			}else if(__eacustomization.getActivitytemplatecode().equals(Enum_activity.幸运抽奖.activitycode)){
				request.getRequestDispatcher("/SignRaffle/Draw").forward(request, response);
			}else if(__eacustomization.getActivitytemplatecode().equals(Enum_activity.充值大抽奖.activitycode)){//
				request.getRequestDispatcher("/DepositRaffle/Draw").forward(request, response);
			}else if(__eacustomization.getActivitytemplatecode().equals(Enum_activity.签到领红包.activitycode)){//
				request.getRequestDispatcher("/Redbag/Draw").forward(request, response);
			}else if(__eacustomization.getActivitytemplatecode().equals(Enum_activity.注册领红包.activitycode)){//
				request.getRequestDispatcher("/RegRedbag/Draw").forward(request, response);
			}else if(__eacustomization.getActivitytemplatecode().equals(Enum_activity.完善资料领红包.activitycode)){//
				request.getRequestDispatcher("/UserinfoRedbag/Draw").forward(request, response);
			}else if(__eacustomization.getActivitytemplatecode().equals(Enum_activity.每日充值享受抽奖.activitycode)){//
				request.getRequestDispatcher("/DepositRaffleDay/Draw").forward(request, response);
			}else{
				request.getRequestDispatcher("/MemBerActivity/notemplate").forward(request, response);
			}
		} catch (LogicTransactionRollBackException e) {
			log.Error(e.getMessage(), e);
			
			try {
				request.getRequestDispatcher("/MemBerActivity/logicTransactionRollBackException").forward(request, response);
			} catch (ServletException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				log.Error(e1.getMessage(), e1);
			}
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			try {
				request.getRequestDispatcher("/MemBerActivity/notemplate").forward(request, response);
			} catch (ServletException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				log.Error(e1.getMessage(), e1);
			}
		}
	}
	
	@RequestMapping(value={"/noactivity"},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String noactivity(){
		return Enum_MSG.逻辑事物异常.message("活动未开始或已过期");
	}
	
	@RequestMapping(value={"/noactivity1"},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String noactivity1(){
		return Enum_MSG.逻辑事物异常.message("活动未启用或不能根据活动编号找到活动信息，请确认活动编码");
	}
	@RequestMapping(value={"/noactivity2"},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String noactivity2(){
		return Enum_MSG.逻辑事物异常.message("该活动要求符合品牌的玩家才能参与，请确认该活动的品牌设置及当前会员的品牌是否符合");
	}
	@RequestMapping(value={"/noactivity3"},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String noactivity3(){
		return Enum_MSG.逻辑事物异常.message("不能根据活动编号查找到活动定制编号，请确认");
	}
	
	
	@RequestMapping(value={"/logicTransactionRollBackException"},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String logicTransactionRollBackException(){
		return Enum_MSG.逻辑事物异常.message("操作太频繁。请您180秒后再操作！");
	}
	
	@RequestMapping(value={"/notemplate"},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String notemplate(){
		return Enum_MSG.逻辑事物异常.message("活动模板不存在");
	}
	
	
	
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}

	
}
