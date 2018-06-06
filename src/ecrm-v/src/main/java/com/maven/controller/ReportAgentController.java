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
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeType;
import com.maven.entity.Game;
import com.maven.entity.ReportDailyAgent;
import com.maven.entity.ReportWeeklyAgent;
import com.maven.entity.UserLogs;
import com.maven.exception.LogicTransactionException;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.BettingAllDayService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.ReportDailyAgentService;
import com.maven.service.ReportWeeklyAgentService;
import com.maven.service.UserLogsService;
import com.maven.util.AttrCheckout;
import com.maven.util.GameUtils;
import com.maven.utils.MoneyHelper;
import com.maven.utils.StringUtil;

@RequestMapping("/reportAgent")
@Controller
public class ReportAgentController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			ReportAgentController.class.getName(), OutputManager.LOG_REPORTAGENT);
	
	@SuppressWarnings("serial")
	private static List<String> ALL_AGENTTYPE = new ArrayList<String>(){{this.add(EnterpriseEmployeeType.Type.代理.value);this.add(EnterpriseEmployeeType.Type.信用代理.value);}};
	
	@Autowired
	private ReportDailyAgentService reportDailyAgentService;
	@Autowired
	private ReportWeeklyAgentService reportWeeklyAgentService;
	@Autowired
	private BettingAllDayService bettingAllDayService;
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	@Autowired
	private UserLogsService userLogsService;
	@Autowired
	private EnterpriseEmployeeService employeeService;
	/**
	 * 代理日结报表页面
	 * @return
	 */
	@RequestMapping("/reportDailyList")
	public String reportDailyList(HttpServletRequest request, HttpSession session, Model model){
		List<Game> listGame = (List<Game>) session.getAttribute(Constant.ENTERPRISE_GAMES);
		model.addAttribute("listGame", listGame);
		return "/report/reportDailyAgentList";
	}
	
	/**
	 * 转到取消计划页面-日结算
	 * @return
	 */
	@RequestMapping("/cancelDaily")
	public String cancelDaily(){
		return "/report/cancelDailyAgent";
	}
	/**
	 * 转到取消计划页面-周结算
	 * @return
	 */
	@RequestMapping("/cancelWeekly")
	public String cancelWeekly(){
		return "/report/cancelWeeklyAgent";
	}
	
	/**
	 * 取消日计划
	 * @return
	 */
	@RequestMapping("/cancelDailyPlan")
	@ResponseBody
	public Map<String, Object> cancelDailyPlan(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> parameters = this.getRequestParamters(request);
			
			userLogsService.addActivityBetRecord(new UserLogs(loginEmployee.getEnterprisecode(), loginEmployee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.结算管理业务, "代理日结计划取消", loginEmployee.getLoginaccount(), null));
			
			String payno = StringUtil.getString(parameters.get("payno"));
			if(payno.equals("") || payno.equals("null")) {
				return this.packJSON(Constant.BooleanByte.NO, "支付单号错误！");
			}
			if(payno.length() != 17 && payno.length() != "2017-10-02 12:30:00".length() ) {
				return this.packJSON(Constant.BooleanByte.NO, "支付单号格式错误！");
			}
			parameters.put("enterprisecode", loginEmployee.getEnterprisecode());
			List<ReportDailyAgent> __listdata = this.reportDailyAgentService.selectAll(parameters);
			if(__listdata == null || __listdata.size() == 0) {
				return this.packJSON(Constant.BooleanByte.NO, "该支付单号没有任何发放数据！");
			}
			//执行取消
			String result = this.reportDailyAgentService.updateDailyPlan(__listdata);
			
			userLogsService.addActivityBetRecord(new UserLogs(loginEmployee.getEnterprisecode(), loginEmployee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.结算管理业务, "代理日结计划取消", loginEmployee.getLoginaccount(), null));
			
			return this.packJSON(Constant.BooleanByte.YES, result);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.Error("cancelDailyPlan 取消日计划失败", e);
			return this.packJSON(Constant.BooleanByte.NO, "取消日计划失败："+e.getMessage());
		}
	}
	/**
	 * 取消周计划
	 * @return
	 */
	@RequestMapping("/cancelWeeklyPlan")
	@ResponseBody
	public Map<String, Object> cancelWeeklyPlan(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> parameters = this.getRequestParamters(request);
			
			String payno = StringUtil.getString(parameters.get("payno"));
			if(payno.equals("") || payno.equals("null")) {
				return this.packJSON(Constant.BooleanByte.NO, "支付单号错误！");
			}
			if(payno.length() != 17 && payno.length() != "2017-10-02 12:30:00".length() ) {
				return this.packJSON(Constant.BooleanByte.NO, "支付单号格式错误！");
			}
			parameters.put("enterprisecode", loginEmployee.getEnterprisecode());
			List<ReportWeeklyAgent> __listdata = this.reportWeeklyAgentService.selectAll(parameters);
			if(__listdata == null || __listdata.size() == 0) {
				return this.packJSON(Constant.BooleanByte.NO, "该支付单号没有任何发放数据！");
			}
			//执行取消
			String result = this.reportWeeklyAgentService.updateProcessDaily(__listdata);
			
			userLogsService.addActivityBetRecord(new UserLogs(loginEmployee.getEnterprisecode(), loginEmployee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.结算管理业务, "代理周结计划取消", loginEmployee.getLoginaccount(), null));
			
			return this.packJSON(Constant.BooleanByte.YES, result);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.Error("cancelWeeklyPlan 取消周计划失败", e);
			return this.packJSON(Constant.BooleanByte.NO, "取消周计划失败："+e.getMessage());
		}
	}
	
	/**
	 * 代理日结报表数据
	 * @return
	 */
	@RequestMapping("/reportDailyData")
	@ResponseBody
	public Map<String, Object> reportDailyData(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> parameters = this.getRequestParamters(request);
			super.dataLimits(loginEmployee, parameters, session);
			
			
			if (parameters.get("parentName") != null) {
				String parentemployeeaccount = parameters.get("parentName").toString();
				EnterpriseEmployee parent = new EnterpriseEmployee();
				parent.setLoginaccount(parentemployeeaccount);
				List<EnterpriseEmployee> list = employeeService.select(parent);
				if (null != list && list.size() > 0) {
					//parameters.put("parentemployeecode", list.get(0).getEmployeecode());
					super.dataLimits(list.get(0), parameters, session);
				} else {
					// 如果查不到上级账户code，就直接传名字进去，查不出数据，表示这个账号下没有会员
					//parameters.put("parentemployeecode", parentemployeeaccount);
				}
				parameters.remove("parentName");
			}
			/*********非超级管理员时只能查询本团队内的数据************/
			if( !SystemConstant.getProperties("admin.employeecode").equals(loginEmployee.getEmployeecode())){
				parameters.put("enterprisecode", loginEmployee.getEnterprisecode());
			}
			
			
			
			List<ReportDailyAgent> bads = this.reportDailyAgentService.selectAll(parameters);
			if(null != bads && bads.size() > 0) {
				String gamePlatform = null;
				String gameBigType  = null;
				for (int i = 0; i < bads.size(); i++) {
					gamePlatform = bads.get(i).getGameplatform();
					gameBigType  = bads.get(i).getGametype();
					bads.get(i).setGameplatform(GameUtils.getGameTypeNameByGameType(gamePlatform));
					bads.get(i).setGametype(GameUtils.getGameBigTypeName(gameBigType));
					gamePlatform = null;
					gameBigType  = null;
				}
			}
			Map<String, Object> result = reportDailyAgentService.takeRecordCountMoney(parameters);
			int countRecord = StringUtil.getInt(result.get("count"));
			
			Map<String, Object> data = super.formatPagaMap(bads, countRecord);
			Map<String, Object> summary = new HashMap<String, Object>();
			summary.put("bet", StringUtil.getDouble(result.get("bet")));
			summary.put("amount", StringUtil.getDouble(result.get("amount")));
			data.put("summary", summary);
			
			return data;
			
//			int count = this.reportDailyAgentService.selectAllCount(parameters);
//			return this.formatPagaMap(bads, count);
		} catch (Exception e) {
			log.Error("reportDailyData 获取会员日洗码报表失败", e);
		}
		return null;
	}
	
	
	/**
	 * 导出会代理日结报表数据
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/excelDailyData")
	public String excelDailyData(Model model, HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> parameters = this.getRequestParamters(request);
			super.dataLimits(loginEmployee, parameters, session);
			
			if (parameters.get("parentName") != null) {
				String parentemployeeaccount = parameters.get("parentName").toString();
				EnterpriseEmployee parent = new EnterpriseEmployee();
				parent.setLoginaccount(parentemployeeaccount);
				List<EnterpriseEmployee> list = employeeService.select(parent);
				if (null != list && list.size() > 0) {
					//parameters.put("parentemployeecode", list.get(0).getEmployeecode());
					super.dataLimits(list.get(0), parameters, session);
				} else {
					// 如果查不到上级账户code，就直接传名字进去，查不出数据，表示这个账号下没有会员
					//parameters.put("parentemployeecode", parentemployeeaccount);
				}
				parameters.remove("parentName");
			}
			/*********非超级管理员时只能查询本团队内的数据************/
			if( !SystemConstant.getProperties("admin.employeecode").equals(loginEmployee.getEmployeecode())){
				parameters.put("enterprisecode", loginEmployee.getEnterprisecode());
			}
			
			
			List<ReportDailyAgent> bads = this.reportDailyAgentService.selectAll(parameters);
			if(null != bads && bads.size() > 0) {
				String gamePlatform = null;
				String gameBigType  = null;
				for (int i = 0; i < bads.size(); i++) {
					gamePlatform = bads.get(i).getGameplatform();
					gameBigType  = bads.get(i).getGametype();
					bads.get(i).setGameplatform(GameUtils.getGameTypeNameByGameType(gamePlatform));
					bads.get(i).setGametype(GameUtils.getGameBigTypeName(gameBigType));
					gamePlatform = null;
					gameBigType  = null;
				}
			}
			model.addAttribute("listDailyData", bads);
			model.addAttribute("title", "代理日结报表数据");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
		}
		return "/report/reportDailyAgentExcel";
	}
	
	/**
	 * 代理日结报表发放选择
	 * @return
	 */
	@RequestMapping("/processDailySelect")
	@ResponseBody
	public Map<String, Object> processDailySelect(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> parameters = this.getRequestParamters(request);
			parameters.put("enterprisecode", loginEmployee.getEnterprisecode());
//			parameters.put("parentemployeecode", loginEmployee.getEmployeecode());
			super.dataLimits(loginEmployee, parameters, session);
			parameters.put("status", Enum_status.未发放.value);
			parameters.put("iditems", AttrCheckout.convertListString(String.valueOf(parameters.get("iditems"))));
			List<ReportDailyAgent> processList = this.reportDailyAgentService.selectAll(parameters);
			if (processList.isEmpty()) {
				return this.packJSON(Constant.BooleanByte.YES, "没有需要发放的记录");
			}
			String result = this.reportDailyAgentService.updateProcessDaily(processList);
			
			userLogsService.addActivityBetRecord(new UserLogs(loginEmployee.getEnterprisecode(), loginEmployee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.结算管理业务, "代理日结发放", loginEmployee.getLoginaccount(), null));
			
			return this.packJSON(Constant.BooleanByte.YES, result);
			
		} catch (LogicTransactionException e) {
			log.Error("processDailySelect 发放选择的代理洗码失败", e);
			return this.packJSON(Constant.BooleanByte.NO, e.getMessage());
		} catch (Exception e) {
			log.Error("processDailySelect 发放选择的代理洗码失败", e);
			return this.packJSON(Constant.BooleanByte.NO, "发放失败");
		}
		
	}
	
	/**
	 * 代理日结报表发放所有
	 * @return
	 */
	@RequestMapping("/processDailyAll")
	@ResponseBody
	public Map<String, Object> processDailyAll(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> parameters = this.getRequestParamters(request);
			parameters.put("enterprisecode", loginEmployee.getEnterprisecode());
//			parameters.put("parentemployeecode", loginEmployee.getEmployeecode());
			super.dataLimits(loginEmployee, parameters, session);
			parameters.put("status", Enum_status.未发放.value);
			List<ReportDailyAgent> processList = this.reportDailyAgentService.selectAll(parameters);
			if (processList.isEmpty()) {
				return this.packJSON(Constant.BooleanByte.YES, "没有需要发放的记录");
			}
			
			String result = this.reportDailyAgentService.updateProcessDaily(processList);
			
			return this.packJSON(Constant.BooleanByte.YES, result);
		} catch (LogicTransactionException e) {
			log.Error("processDailySelect 发放选择的代理洗码失败", e);
			return this.packJSON(Constant.BooleanByte.NO, e.getMessage());
		} catch (Exception e) {
			log.Error("processDailySelect 发放选择的代理洗码失败", e);
			return this.packJSON(Constant.BooleanByte.NO, "发放失败");
		}
		
	}
	
	
	
	
	/***************************************************************************************************************************************
	 * **************************************************************代理周报表相关业务******************************************************
	****************************************************************************************************************************************/
	/**
	 * 代理周结报表页面
	 * @return
	 */
	@RequestMapping("/reportWeekList")
	public String reportWeekList(HttpServletRequest request, HttpSession session, Model model){
		List<Game> listGame = (List<Game>) session.getAttribute(Constant.ENTERPRISE_GAMES);
		model.addAttribute("listGame", listGame);
		return "/report/reportWeeklyAgentList";
	}
	
	
	/**
	 * 代理周结报表数据
	 * @return
	 */
	@RequestMapping("/reportWeekData")
	@ResponseBody
	public Map<String, Object> reportWeekData(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> parameters = this.getRequestParamters(request);
			super.dataLimits(loginEmployee, parameters, session);
			
			
			if (parameters.get("parentName") != null) {
				String parentemployeeaccount = parameters.get("parentName").toString();
				EnterpriseEmployee parent = new EnterpriseEmployee();
				parent.setLoginaccount(parentemployeeaccount);
				List<EnterpriseEmployee> list = employeeService.select(parent);
				if (null != list && list.size() > 0) {
					//parameters.put("parentemployeecode", list.get(0).getEmployeecode());
					super.dataLimits(list.get(0), parameters, session);
				} else {
					// 如果查不到上级账户code，就直接传名字进去，查不出数据，表示这个账号下没有会员
					//parameters.put("parentemployeecode", parentemployeeaccount);
				}
				parameters.remove("parentName");
			}
			/*********非超级管理员时只能查询本团队内的数据************/
			if( !SystemConstant.getProperties("admin.employeecode").equals(loginEmployee.getEmployeecode())){
				parameters.put("enterprisecode", loginEmployee.getEnterprisecode());
			}
			
			
			List<ReportWeeklyAgent> bads = this.reportWeeklyAgentService.selectAll(parameters);
			if(null != bads && bads.size() > 0) {
				String gamePlatform = null;
				String gameBigType  = null;
				for (int i = 0; i < bads.size(); i++) {
					gamePlatform = bads.get(i).getGameplatform();
					gameBigType  = bads.get(i).getGametype();
					bads.get(i).setGameplatform(GameUtils.getGameTypeNameByGameType(gamePlatform));
					bads.get(i).setGametype(GameUtils.getGameBigTypeName(gameBigType));
					gamePlatform = null;
					gameBigType  = null;
				}
			}
			Map<String, Object> result = reportWeeklyAgentService.takeRecordCountMoney(parameters);
			int countRecord = StringUtil.getInt(result.get("count"));
			
			Map<String, Object> data = super.formatPagaMap(bads, countRecord);
			Map<String, Object> summary = new HashMap<String, Object>();
			summary.put("bet", StringUtil.getDouble(result.get("bet")));
			summary.put("amount", StringUtil.getDouble(result.get("amount")));
			summary.put("subtract", StringUtil.getDouble(result.get("subtract")));
			data.put("summary", summary);
			
			return data;
			
//			int count = this.reportWeeklyAgentService.selectAllCount(parameters);
//			return this.formatPagaMap(bads, count);
		} catch (Exception e) {
			log.Error("reportDailyData 获取代理周洗码报表失败", e);
		}
		return null;
	}
	
	/**
	 * 代理周结报表发放选择
	 * @return
	 */
	@RequestMapping("/processWeekSelect")
	@ResponseBody
	public Map<String, Object> processWeekSelect(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> parameters = this.getRequestParamters(request);
			parameters.put("enterprisecode", loginEmployee.getEnterprisecode());
//			parameters.put("parentemployeecode", loginEmployee.getEmployeecode());
			super.dataLimits(loginEmployee, parameters, session);
			parameters.put("status", Enum_status.未发放.value);
			parameters.put("iditems", AttrCheckout.convertListString(String.valueOf(parameters.get("iditems"))));
			List<ReportWeeklyAgent> processList = this.reportWeeklyAgentService.selectAll(parameters);
			if (processList.isEmpty()) {
				return this.packJSON(Constant.BooleanByte.YES, "没有需要发放的记录");
			}
			
			String result = this.reportWeeklyAgentService.updateProcessDaily(processList);
			
			userLogsService.addActivityBetRecord(new UserLogs(loginEmployee.getEnterprisecode(), loginEmployee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.结算管理业务, "代理周结发放", loginEmployee.getLoginaccount(), null));
			
			return this.packJSON(Constant.BooleanByte.YES, result);
			
		} catch (LogicTransactionException e) {
			log.Error("processDailySelect 发放选择的代理洗码失败", e);
			return this.packJSON(Constant.BooleanByte.NO, e.getMessage());
		} catch (Exception e) {
			log.Error("processDailySelect 发放选择的代理洗码失败", e);
			return this.packJSON(Constant.BooleanByte.NO, "发放失败");
		}
		
	}
	
	/**
	 * 代理周结报表发放所有
	 * @return
	 */
	@RequestMapping("/processWeekAll")
	@ResponseBody
	public Map<String, Object> processWeekAll(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> parameters = this.getRequestParamters(request);
			parameters.put("enterprisecode", loginEmployee.getEnterprisecode());
//			parameters.put("parentemployeecode", loginEmployee.getEmployeecode());
			super.dataLimits(loginEmployee, parameters, session);
			parameters.put("status", Enum_status.未发放.value);
			List<ReportWeeklyAgent> processList = this.reportWeeklyAgentService.selectAll(parameters);
			if (processList.isEmpty()) {
				return this.packJSON(Constant.BooleanByte.YES, "没有需要发放的记录");
			}
			//执行
			String result = this.reportWeeklyAgentService.updateProcessDaily(processList);
			
			userLogsService.addActivityBetRecord(new UserLogs(loginEmployee.getEnterprisecode(), loginEmployee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.结算管理业务, "代理周结发放", loginEmployee.getLoginaccount(), null));
			
			return this.packJSON(Constant.BooleanByte.YES, result);
		} catch (LogicTransactionException e) {
			log.Error("processDailySelect 发放选择的代理洗码失败", e);
			return this.packJSON(Constant.BooleanByte.NO, e.getMessage());
		} catch (Exception e) {
			log.Error("processDailySelect 发放选择的代理洗码失败", e);
			return this.packJSON(Constant.BooleanByte.NO, "发放失败");
		}
	}
	
	/**
	 * 代理周结报表校验
	 * @return
	 */
	@RequestMapping("/processWeekCheck")
	@ResponseBody
	@SuppressWarnings("unused")
	public Map<String, Object> processWeekCheck(HttpServletRequest request, HttpSession session) {
		try {
			
			String reportcode = request.getParameter("reportcode");
			ReportWeeklyAgent reportWeeklyAgent = reportWeeklyAgentService.selectByPrimaryKey(reportcode);
			if(reportWeeklyAgent.getAmount() - reportWeeklyAgent.getSubtract() > 0) {
				return this.packJSON(Constant.BooleanByte.NO, "未完全发放完毕，无法校验。请先发放再做校验");
			}
			
			//0=查找同一批次的
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("employeecode", reportWeeklyAgent.getEmployeecode());
			params.put("gameplatform", reportWeeklyAgent.getGameplatform());
			params.put("gametype", reportWeeklyAgent.getGametype());
			params.put("patchno", reportWeeklyAgent.getPatchno());
			List<ReportWeeklyAgent> listReportWeeklyAgent = reportWeeklyAgentService.selectAll(params);
			double amount = 0;
			double but = 0;
			for (ReportWeeklyAgent reportWeeklyAgent2 : listReportWeeklyAgent) {
				amount += reportWeeklyAgent2.getAmount();
				but += reportWeeklyAgent2.getBet();
			}
			
			//1=查找此代理的所有代理
			Map<String, Object> ep = new HashMap<String, Object>();
			ep.put("parentemployeecode", reportWeeklyAgent.getEmployeecode());
			ep.put("employeetypecodes",ALL_AGENTTYPE);
			List<EnterpriseEmployee> agents = enterpriseEmployeeService.queryAgent(ep);
			List<String> agentId = new ArrayList<String>(); //获取所有代理code
			
			if (agents.isEmpty()) {
				log.Info(reportWeeklyAgent.getEmployeecode()+"下无代理");
			} else {
				for (EnterpriseEmployee ee : agents) { //
					if (!agentId.contains(ee.getEmployeecode())) {
						agentId.add(ee.getEmployeecode());
					}
				}
			}
			agentId.add(reportWeeklyAgent.getEmployeecode());//当前代理也加入
			
			
			//2=查找此代理的所有会员的游戏记录
			ep.clear();
			ep.put("parentemployeecodes", agentId);//
			ep.put("startDate", reportWeeklyAgent.getStarttime());
			ep.put("endDate", reportWeeklyAgent.getEndtime());
			ep.put("gamePlatform", reportWeeklyAgent.getGameplatform());//平台
			ep.put("gameBigType", reportWeeklyAgent.getGametype());//游戏分类
			List<BettingAllDay> bads = this.bettingAllDayService.selectBettingByDate(ep);
			
			//3=比较
			double rebatesCash = 0;
			double butMoney = 0;
			for (BettingAllDay bad : bads) { 
				rebatesCash += (StringUtil.getDouble(bad.getRebatesCash()) );//洗码金额
				butMoney += bad.getValidMoney();//取有效投注金额
			}
			
			/**不校验投注总额
			if (Math.abs(reportWeeklyAgent.getBet().doubleValue() - butMoney) > 0.5) {//正负不能在0.5，避免小数点问题
				return this.packJSON(Constant.BooleanByte.NO, "校验不通过！投注总额不一致。周结算投注总额为："+reportWeeklyAgent.getBet()+"，游戏记录累计投注总额为："+butMoney);
			}
			*/
			
			if (Math.abs(amount - rebatesCash) > 0.5) {//正负不能在0.5，避免小数点问题
				double diff = MoneyHelper.moneyFormatDouble (rebatesCash - amount);
				boolean isNeed = diff > 0 ? true : false ;
				StringBuffer buffer = new StringBuffer();
				buffer.append("校验不通过！洗码总额不一致。周结算洗码总额为："+MoneyHelper.doubleFormat(amount)+"，游戏记录累计洗码总额为："+MoneyHelper.doubleFormat(rebatesCash) );
				if( isNeed ) {
					buffer.append("，应补发差额："+diff);
				}
				
				Map<String, Object> json = this.packJSON(Constant.BooleanByte.NO, buffer.toString());
				json.put("isNeed", isNeed );
				json.put("diffMoney",  MoneyHelper.doubleFormat(diff)  );
				return json;
			}
			
			return this.packJSON(Constant.BooleanByte.YES, "校验通过！投注总额与洗码总额校验结果完全一致");
			
			
		} catch (LogicTransactionException e) {
			log.Error("processWeekCheck 洗码校验失败", e);
			return this.packJSON(Constant.BooleanByte.NO, e.getMessage());
		} catch (Exception e) {
			log.Error("processWeekCheck 洗码校验失败", e);
			return this.packJSON(Constant.BooleanByte.NO, "洗码校验失败");
		}
	}
	
	/**
	 * 代理周结报表 修改实发金额
	 * @return
	 */
	@RequestMapping("/updateMoney")
	@ResponseBody
	public Map<String, Object> updateMoney(HttpServletRequest request, HttpSession session) {
		try {
			
			String reportcode = request.getParameter("lsh");
			String paymoneyreal = request.getParameter("paymoneyreal");
			ReportWeeklyAgent reportWeeklyAgent = reportWeeklyAgentService.selectByPrimaryKey(reportcode);
			if( !reportWeeklyAgent.getPaytype().equals(ReportWeeklyAgent.Enum_paytype.补发.value)) {
				return this.packJSON(Constant.BooleanByte.NO, "非补发记录，不能修改金额");
			}
			if( !reportWeeklyAgent.getStatus().equals(ReportWeeklyAgent.Enum_status.未发放.value)) {
				return this.packJSON(Constant.BooleanByte.NO, "不是未发放状态，不能修改金额");
			}
			reportWeeklyAgent.setActual(Double.valueOf(paymoneyreal));
			reportWeeklyAgentService.update(reportWeeklyAgent);
			
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			userLogsService.addActivityBetRecord(new UserLogs(loginEmployee.getEnterprisecode(), reportWeeklyAgent.getEmployeecode(), reportWeeklyAgent.getLoginaccount(), 
				     UserLogs.Enum_operatype.结算管理业务, "代理周结修改金额", loginEmployee.getLoginaccount(), "旧金额"+reportWeeklyAgent.getActual()+"，新金额："+paymoneyreal));
			
			
			return this.packJSON(Constant.BooleanByte.YES, "操作成功");
			
			
		} catch (LogicTransactionException e) {
			log.Error("updateMoney 修改金额失败", e);
			return this.packJSON(Constant.BooleanByte.NO, e.getMessage());
		} catch (Exception e) {
			log.Error("updateMoney 修改金额失败", e);
			return this.packJSON(Constant.BooleanByte.NO, "洗码校验失败");
		}
	}
	
	
	/**
	 * 代理周结报表补发
	 * @return
	 */
	@RequestMapping("/processWeekSupplement")
	@ResponseBody
	public Map<String, Object> processWeekSupplement(HttpServletRequest request, HttpSession session) {
		try {
			String reportcode = request.getParameter("reportcode");
			//0=查找同一批次的
			ReportWeeklyAgent reportWeeklyAgent = reportWeeklyAgentService.selectByPrimaryKey(reportcode);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("employeecode", reportWeeklyAgent.getEmployeecode());
			params.put("gameplatform", reportWeeklyAgent.getGameplatform());
			params.put("gametype", reportWeeklyAgent.getGametype());
			params.put("patchno", reportWeeklyAgent.getPatchno());
			List<ReportWeeklyAgent> listReportWeeklyAgent = reportWeeklyAgentService.selectAll(params);
			double amount = 0;
			double but = 0;
			for (ReportWeeklyAgent reportWeeklyAgent2 : listReportWeeklyAgent) {
				amount += reportWeeklyAgent2.getAmount();
				but += reportWeeklyAgent2.getBet();
			}
			
			//1=查找此代理的所有代理
			Map<String, Object> ep = new HashMap<String, Object>();
			ep.put("parentemployeecode", reportWeeklyAgent.getEmployeecode());
			ep.put("employeetypecodes", ALL_AGENTTYPE);
			List<EnterpriseEmployee> agents = enterpriseEmployeeService.queryAgent(ep);
			List<String> agentId = new ArrayList<String>(); //获取所有代理code
			
			if (agents.isEmpty()) {
				log.Info(reportWeeklyAgent.getEmployeecode()+"下无代理");
			} else {
				for (EnterpriseEmployee ee : agents) { //
					if (!agentId.contains(ee.getEmployeecode())) {
						agentId.add(ee.getEmployeecode());
					}
				}
			}
			agentId.add(reportWeeklyAgent.getEmployeecode());//当前代理也加入
			
			
			//2=查找此代理的所有会员的游戏记录
			ep.clear();
			ep.put("parentemployeecodes", agentId);//
			ep.put("startDate", reportWeeklyAgent.getStarttime());
			ep.put("endDate", reportWeeklyAgent.getEndtime());
			ep.put("gamePlatform", reportWeeklyAgent.getGameplatform());//平台
			ep.put("gameBigType", reportWeeklyAgent.getGametype());//游戏分类
			List<BettingAllDay> bads = this.bettingAllDayService.selectBettingByDate(ep);
			
			//3=比较
			double rebatesCash = 0;
			double butMoney = 0;
			for (BettingAllDay bad : bads) { 
				rebatesCash += (StringUtil.getDouble(bad.getRebatesCash()) );//洗码金额
				butMoney += bad.getValidMoney();//取有效投注金额
			}
			
			/**不校验投注总额
			if (Math.abs(reportWeeklyAgent.getBet().doubleValue() - butMoney) > 0.5) {//正负不能在0.5，避免小数点问题
				return this.packJSON(Constant.BooleanByte.NO, "校验不通过！投注总额不一致，无法进行补发。周结算投注总额为："+reportWeeklyAgent.getBet()+"，游戏记录累计投注总额为："+butMoney);
			}
			*/
			
			// 需要补发（差额在0.5以上，并且日结记录的洗码金额要大于周结洗码金额）
			if ( (Math.abs(amount - rebatesCash) > 0.5) && (rebatesCash > amount) ) {//正负不能在0.5，避免小数点问题
				
				double diff = MoneyHelper.moneyFormatDouble(rebatesCash - amount);
				this.reportWeeklyAgentService.updateProcessSupplement(reportWeeklyAgent, MoneyHelper.moneyFormatDouble(butMoney - but), diff);
				
				EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
				userLogsService.addActivityBetRecord(new UserLogs(loginEmployee.getEnterprisecode(), reportWeeklyAgent.getEmployeecode(), reportWeeklyAgent.getLoginaccount(), 
					     UserLogs.Enum_operatype.结算管理业务, "代理周结补发", loginEmployee.getLoginaccount(), null));
				
				return this.packJSON(Constant.BooleanByte.YES, "操作完成！已补发洗码金额"+(diff)+"元");
				
			} else {
				//不需要补发的情况
				return this.packJSON(Constant.BooleanByte.YES, "校验通过！不需要补发");
			}
			
		} catch (LogicTransactionException e) {
			log.Error("processWeekSupplement 洗码补发失败", e);
			return this.packJSON(Constant.BooleanByte.NO, e.getMessage());
		} catch (Exception e) {
			log.Error("processWeekSupplement 洗码补发失败", e);
			return this.packJSON(Constant.BooleanByte.NO, "洗码补发失败");
		}
	}
	 
	@Override
	public LoggerManager getLogger() {
		return null;
	}

}
