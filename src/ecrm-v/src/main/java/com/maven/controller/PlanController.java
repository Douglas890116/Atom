package com.maven.controller;

import java.util.ArrayList;
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
import com.maven.entity.BettingAllDay;
import com.maven.entity.BettingAllDay.Enum_status;
import com.maven.entity.BettingAllPlan;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeType;
import com.maven.entity.ReportDailyAgent;
import com.maven.entity.ReportWeeklyAgent;
import com.maven.entity.UserLogs;
import com.maven.exception.LogicTransactionException;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.BettingAllDayService;
import com.maven.service.BettingAllDayService2;
import com.maven.service.BettingPlanService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.ReportDailyAgentService;
import com.maven.service.ReportWeeklyAgentService;
import com.maven.service.UserLogsService;
import com.maven.util.AttrCheckout;
import com.maven.util.GameUtils;
import com.maven.utils.MoneyHelper;
import com.maven.utils.StringUtil;

/**
 * 支付计划
 * @author Administrator
 *
 */
@RequestMapping("/plan")
@Controller
public class PlanController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			PlanController.class.getName(), OutputManager.LOG_REPORTAGENT);
	
	@SuppressWarnings("serial")
	private static List<String> ALL_AGENTTYPE = new ArrayList<String>(){{this.add(EnterpriseEmployeeType.Type.代理.value);this.add(EnterpriseEmployeeType.Type.信用代理.value);}};
	
	@Autowired
	private ReportDailyAgentService reportDailyAgentService;
	@Autowired
	private ReportWeeklyAgentService reportWeeklyAgentService;
	@Autowired
	private BettingAllDayService2 bettingAllDayService;
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	@Autowired
	private UserLogsService userLogsService;
	@Autowired
	private EnterpriseEmployeeService employeeService;
	@Autowired
	private BettingPlanService bettingPlanService;
	
	/**
	 * 生成支付计划页面
	 * @return
	 */
	@RequestMapping("/plan")
	public String plan(){
		return "/plan/plan";
	}
	/**
	 * 查询支付计划
	 * @return
	 */
	@RequestMapping("/list")
	public String list(){
		return "/plan/list";
	}
	
	/**
	 * 生成计划
	 * @return
	 */
	@RequestMapping("/doPlan")
	@ResponseBody
	public Map<String, Object> doPlan(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> parameters = this.getRequestParamters(request);
			
			/*********非超级管理员时只能查询本团队内的数据************/
			if( !SystemConstant.getProperties("admin.employeecode").equals(loginEmployee.getEmployeecode())){
				parameters.put("enterprisecode", loginEmployee.getEnterprisecode());
			}
			
			String betday = parameters.get("betday").toString();
			if(betday.length() != 8) {
				return super.packJSON(Constant.BooleanByte.NO, "计划日期请输入8位数的年月日");
			}
			
			//检查是否已生成过计划
			Map<String, Object> paramObj = new HashMap<String, Object>();
			paramObj.put("betday", parameters.get("betday"));
			paramObj.put("status", BettingAllPlan.Enum_status.已汇总.value);
			List<BettingAllPlan> list = bettingPlanService.select(paramObj);
			if(list != null && list.size() > 0) {
				return super.packJSON(Constant.BooleanByte.NO, "已生成过该日的计划："+betday);
			}
			
			parameters.put("operater", loginEmployee.getLoginaccount());
			//int count = bettingAllDayService.updateDoPlan(planDate, newPatchNo)
			
			return super.packJSON(Constant.BooleanByte.YES, "生成计划完毕，记录数："+0);
		} catch (Exception e) {
			return super.packJSON(Constant.BooleanByte.NO, "生成计划失败："+e.getMessage());
		}
	}
	/**
	 * 取消计划（已汇总）
	 * @return
	 */
	@RequestMapping("/cancelPlan")
	@ResponseBody
	public Map<String, Object> cancelPlan(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> parameters = this.getRequestParamters(request);
			
			/*********非超级管理员时只能查询本团队内的数据************/
			if( !SystemConstant.getProperties("admin.employeecode").equals(loginEmployee.getEmployeecode())){
				parameters.put("enterprisecode", loginEmployee.getEnterprisecode());
			}
			
			String lsh = parameters.get("lsh").toString();
			BettingAllPlan plan = bettingPlanService.selectByPrimary(lsh);
			if(plan.getStatus().intValue() == BettingAllPlan.Enum_status.财务已核准.value.intValue()) {
				return super.packJSON(Constant.BooleanByte.NO, "该计划财务已经核准，请先取消核准");
			}
			if(plan.getStatus().intValue() == BettingAllPlan.Enum_status.已支付.value.intValue()) {
				return super.packJSON(Constant.BooleanByte.NO, "该计划已支付，无法取消");
			}
			
			//执行取消计划
			bettingAllDayService.updateCancelPlan(plan, loginEmployee.getLoginaccount() );
			
			return super.packJSON(Constant.BooleanByte.YES, "计划取消成功");
		} catch (Exception e) {
			return super.packJSON(Constant.BooleanByte.NO, "取消计划失败："+e.getMessage());
		}
	}
	/**
	 * 财务核准计划（生成洗码）
	 * @return
	 */
	@RequestMapping("/doPlanCaiwu")
	@ResponseBody
	public Map<String, Object> doPlanCaiwu(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> parameters = this.getRequestParamters(request);
			
			/*********非超级管理员时只能查询本团队内的数据************/
			if( !SystemConstant.getProperties("admin.employeecode").equals(loginEmployee.getEmployeecode())){
				parameters.put("enterprisecode", loginEmployee.getEnterprisecode());
			}
			
			String lsh = parameters.get("lsh").toString();
			BettingAllPlan plan = bettingPlanService.selectByPrimary(lsh);
			if(plan.getStatus().intValue() == BettingAllPlan.Enum_status.财务已核准.value.intValue()) {
				return super.packJSON(Constant.BooleanByte.NO, "该计划财务已核准！");
			}
			if(plan.getStatus().intValue() == BettingAllPlan.Enum_status.已支付.value.intValue()) {
				return super.packJSON(Constant.BooleanByte.NO, "该计划已支付，无法核准");
			}
			
			//执行核准计划
			bettingAllDayService.updateDoPlanCaiwu(plan, loginEmployee.getLoginaccount() );
			
			return super.packJSON(Constant.BooleanByte.YES, "核准已完毕");
		} catch (Exception e) {
			return super.packJSON(Constant.BooleanByte.NO, "财务核准失败："+e.getMessage());
		}
	}
	/**
	 * 取消核准
	 * @return
	 */
	@RequestMapping("/cancelPlanCaiwu")
	@ResponseBody
	public Map<String, Object> cancelPlanCaiwu(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> parameters = this.getRequestParamters(request);
			
			/*********非超级管理员时只能查询本团队内的数据************/
			if( !SystemConstant.getProperties("admin.employeecode").equals(loginEmployee.getEmployeecode())){
				parameters.put("enterprisecode", loginEmployee.getEnterprisecode());
			}
			
			String lsh = parameters.get("lsh").toString();
			BettingAllPlan plan = bettingPlanService.selectByPrimary(lsh);
			if(plan.getStatus().intValue() == BettingAllPlan.Enum_status.已汇总.value.intValue()) {
				return super.packJSON(Constant.BooleanByte.NO, "该计划已汇总，还不能取消核准");
			}
			if(plan.getStatus().intValue() == BettingAllPlan.Enum_status.已支付.value.intValue()) {
				return super.packJSON(Constant.BooleanByte.NO, "该计划已支付，无法取消");
			}
			
			//执行取消计划
			bettingAllDayService.updateCancelPlanCaiwu(plan, loginEmployee.getLoginaccount() );
			
			return super.packJSON(Constant.BooleanByte.YES, "已取消财务核准");
		} catch (Exception e) {
			return super.packJSON(Constant.BooleanByte.NO, "取消核准失败："+e.getMessage());
		}
	}
	/**
	 * 查询支付计划
	 * @return
	 */
	@RequestMapping("/data")
	@ResponseBody
	public Map<String, Object> data(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> parameters = this.getRequestParamters(request);
			
			/*********非超级管理员时只能查询本团队内的数据************/
			if( !SystemConstant.getProperties("admin.employeecode").equals(loginEmployee.getEmployeecode())){
				parameters.put("enterprisecode", loginEmployee.getEnterprisecode());
			}

			List<BettingAllPlan> list = bettingPlanService.selectForPage(parameters);
			Map<String, Object> result = bettingPlanService.takeRecordCountMoney(parameters);
			int countRecord = StringUtil.getInt(result.get("count"));
			
			Map<String, Object> data = super.formatPagaMap(list, countRecord);
			Map<String, Object> summary = new HashMap<String, Object>();
			summary.put("totalCount", StringUtil.getDouble(result.get("totalCount")));
			summary.put("totalBetmoney", StringUtil.getDouble(result.get("totalBetmoney")));
			summary.put("totalValidbet", StringUtil.getDouble(result.get("totalValidbet")));
			summary.put("totalNetmoney", StringUtil.getDouble(result.get("totalNetmoney")));
			data.put("summary", summary);
			
			return data;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public LoggerManager getLogger() {
		return null;
	}

}
