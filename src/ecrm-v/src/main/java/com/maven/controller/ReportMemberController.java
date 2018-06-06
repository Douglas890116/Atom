package com.maven.controller;

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
import com.maven.entity.Game;
import com.maven.entity.ReportWeeklyAgent;
import com.maven.entity.ReportWeeklyMember;
import com.maven.entity.UserLogs;
import com.maven.exception.LogicTransactionException;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.BettingAllDayService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.ReportWeeklyMemberService;
import com.maven.service.UserLogsService;
import com.maven.util.AttrCheckout;
import com.maven.util.GameUtils;
import com.maven.utils.MoneyHelper;
import com.maven.utils.StringUtil;

@RequestMapping("/reportMember")
@Controller
public class ReportMemberController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			ReportMemberController.class.getName(), OutputManager.LOG_REPORTMEMBER);
	
	@Autowired
	private BettingAllDayService bettingAllDayService;
	@Autowired
	private ReportWeeklyMemberService reportWeeklyMemberService;
	@Autowired
	private UserLogsService userLogsService;
	@Autowired
	private EnterpriseEmployeeService employeeService;
	/**
	 * 会员日结报表页面
	 * @return
	 */
	@RequestMapping("/reportDailyList")
	public String reportDailyList(HttpServletRequest request, HttpSession session, Model model){
		List<Game> listGame = (List<Game>) session.getAttribute(Constant.ENTERPRISE_GAMES);
		model.addAttribute("listGame", listGame);
		return "/report/reportDailyMemberList";
	}
	/**
	 * 会员周结报表页面
	 * @return
	 */
	@RequestMapping("/reportWeeklyList")
	public String reportWeeklyList(HttpServletRequest request, HttpSession session, Model model){
		List<Game> listGame = (List<Game>) session.getAttribute(Constant.ENTERPRISE_GAMES);
		model.addAttribute("listGame", listGame);
		return "/report/reportWeeklyMemberList";
	}
	
	/**
	 * 转到取消计划页面-日结算
	 * @return
	 */
	@RequestMapping("/cancelDaily")
	public String cancelDaily(){
		return "/report/cancelDailyMember";
	}
	/**
	 * 转到取消计划页面-周结算
	 * @return
	 */
	@RequestMapping("/cancelWeekly")
	public String cancelWeekly(){
		return "/report/cancelWeeklyMember";
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
				     UserLogs.Enum_operatype.结算管理业务, "会员日结计划取消", loginEmployee.getLoginaccount(), null));
			
			String payno = StringUtil.getString(parameters.get("payno"));
			if(payno.equals("") || payno.equals("null")) {
				return this.packJSON(Constant.BooleanByte.NO, "支付单号错误！");
			}
			if(payno.length() != 17 && payno.length() != "2017-10-02 12:30:00".length() ) {
				return this.packJSON(Constant.BooleanByte.NO, "支付单号格式错误！");
			}
			
			parameters.put("enterprisecode", loginEmployee.getEnterprisecode());
			List<BettingAllDay> __listdata = this.bettingAllDayService.selectForPage(parameters);
			if(__listdata == null || __listdata.size() == 0) {
				return this.packJSON(Constant.BooleanByte.NO, "该支付单号没有任何发放数据！");
			}
			//取消
			String result = this.bettingAllDayService.updateDailyPlan(__listdata);
			
			userLogsService.addActivityBetRecord(new UserLogs(loginEmployee.getEnterprisecode(), loginEmployee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.结算管理业务, "会员日结计划取消", loginEmployee.getLoginaccount(), null));
			
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
			
			if(payno.contains("-") && payno.contains(":")) {
				parameters.put("reporttime", payno);
				parameters.remove("payno");
			}
			parameters.put("enterprisecode", loginEmployee.getEnterprisecode());
			List<ReportWeeklyMember> __listdata = this.reportWeeklyMemberService.selectAll(parameters);
			if(__listdata == null || __listdata.size() == 0) {
				return this.packJSON(Constant.BooleanByte.NO, "该支付单号没有任何发放数据！");
			}
			
			//取消
			String result = reportWeeklyMemberService.updateWeeklyPlan(__listdata);
			
			userLogsService.addActivityBetRecord(new UserLogs(loginEmployee.getEnterprisecode(), loginEmployee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.结算管理业务, "会员周计划取消", loginEmployee.getLoginaccount(), null));
			
			return this.packJSON(Constant.BooleanByte.YES, result);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.Error("cancelWeeklyPlan 取消周计划失败", e);
			return this.packJSON(Constant.BooleanByte.NO, "取消周计划失败："+e.getMessage());
		}
	}
	public static void main(String[] args) {
		System.out.println("00201710041352059".length());
	}
	
	
	/**
	 * 会员日结报表数据
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
			
			
			List<BettingAllDay> bads = this.bettingAllDayService.selectForPage(parameters);
			if(null != bads && bads.size() > 0) {
				String gamePlatform = null;
				String gameBigType  = null;
				for (int i = 0; i < bads.size(); i++) {
					gamePlatform = bads.get(i).getGamePlatform();
					gameBigType  = bads.get(i).getGameBigType();
					bads.get(i).setGamePlatform(GameUtils.getGameTypeNameByGameType(gamePlatform));
					bads.get(i).setGameBigType(GameUtils.getGameBigTypeName(gameBigType));
					gamePlatform = null;
					gameBigType  = null;
				}
			}
			Map<String, Object> result = bettingAllDayService.takeRecordCountMoney(parameters);
			int countRecord = StringUtil.getInt(result.get("count"));
			
			Map<String, Object> data = super.formatPagaMap(bads, countRecord);
			Map<String, Object> summary = new HashMap<String, Object>();
			summary.put("validMoney", StringUtil.getDouble(result.get("validMoney")));
			summary.put("netMoney", StringUtil.getDouble(result.get("netMoney")));
			summary.put("rebatesCash", StringUtil.getDouble(result.get("rebatesCash")));
			data.put("summary", summary);
			
			return data;
			
//			int count = this.bettingAllDayService.selectForPageCount(parameters);
//			return this.formatPagaMap(bads, count);
		} catch (Exception e) {
			log.Error("reportDailyData 获取会员日洗码报表失败", e);
		}
		return null;
	}
	/**
	 * 导出会员日结报表数据
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
			
			
			List<BettingAllDay> bads = this.bettingAllDayService.selectForPage(parameters);
			if(null != bads && bads.size() > 0) {
				String gamePlatform = null;
				String gameBigType  = null;
				for (int i = 0; i < bads.size(); i++) {
					gamePlatform = bads.get(i).getGamePlatform();
					gameBigType  = bads.get(i).getGameBigType();
					bads.get(i).setGamePlatform(GameUtils.getGameTypeNameByGameType(gamePlatform));
					bads.get(i).setGameBigType(GameUtils.getGameBigTypeName(gameBigType));
					gamePlatform = null;
					gameBigType  = null;
				}
			}
			model.addAttribute("listDailyData", bads);
			model.addAttribute("title", "会员日结报表数据");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
		}
		return "/report/reportDailyMemberExcel";
	}
	
	/**
	 * 会员日结报表发放选择
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
			List<BettingAllDay> processList = this.bettingAllDayService.select(parameters);
			if (processList.isEmpty()) {
				return this.packJSON(Constant.BooleanByte.YES, "没有需要发放的记录");
			}
			String result = this.bettingAllDayService.updateProcessDaily(processList);
			
			userLogsService.addActivityBetRecord(new UserLogs(loginEmployee.getEnterprisecode(), loginEmployee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.结算管理业务, "会员日结发放", loginEmployee.getLoginaccount(), null));
			
			return this.packJSON(Constant.BooleanByte.YES, result);
		} catch (LogicTransactionException e) {
			log.Error("processDailySelect 发放选择的会员洗码失败", e);
			return this.packJSON(Constant.BooleanByte.NO, e.getMessage());
		} catch (Exception e) {
			log.Error("processDailySelect 发放选择的会员洗码失败", e);
			return this.packJSON(Constant.BooleanByte.NO, "发放失败，可能是余额不足");
		}
		
	}
	
	/**
	 * 会员日结报表发放所有
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
			List<BettingAllDay> processList = this.bettingAllDayService.select(parameters);
			if (processList.isEmpty()) {
				return this.packJSON(Constant.BooleanByte.YES, "没有需要发放的记录");
			}
			String result = this.bettingAllDayService.updateProcessDaily(processList);
			
			userLogsService.addActivityBetRecord(new UserLogs(loginEmployee.getEnterprisecode(), loginEmployee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.结算管理业务, "会员日结发放", loginEmployee.getLoginaccount(), null));
			
			return this.packJSON(Constant.BooleanByte.YES, result);
			
		} catch (LogicTransactionException e) {
			log.Error("processDailySelect 发放选择的会员洗码失败", e);
			return this.packJSON(Constant.BooleanByte.NO, e.getMessage());
		} catch (Exception e) {
			log.Error("processDailySelect 发放选择的会员洗码失败", e);
			return this.packJSON(Constant.BooleanByte.NO, "发放失败，可能是余额不足");
		}
		
	}
	
	//https://download.you7878.com/appfile/bjl/hy_20171005.exe
	//https://download.you7878.com/appfile/bjl/hy_20171005.ipa
	//https://download.you7878.com/appfile/bjl/hy_1466753457377.apk
	
	/**
	 * 会员周结报表数据
	 * @return
	 */
	@RequestMapping("/reportWeeklyData")
	@ResponseBody
	public Map<String, Object> reportWeeklyData(HttpServletRequest request, HttpSession session) {
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
			
			
			List<ReportWeeklyMember> rwms = this.reportWeeklyMemberService.selectAll(parameters);
			if(null != rwms && rwms.size() > 0) {
				String gamePlatform = null;
				String gameBigType  = null;
				for (int i = 0; i < rwms.size(); i++) {
					gamePlatform = rwms.get(i).getGameplatform();
					gameBigType  = rwms.get(i).getGametype();
					rwms.get(i).setGameplatform(GameUtils.getGameTypeNameByGameType(gamePlatform));
					rwms.get(i).setGametype(GameUtils.getGameBigTypeName(gameBigType));
					gamePlatform = null;
					gameBigType  = null;
				}
			}
			Map<String, Object> result = reportWeeklyMemberService.takeRecordCountMoney(parameters);
			int countRecord = StringUtil.getInt(result.get("count"));
			
			Map<String, Object> data = super.formatPagaMap(rwms, countRecord);
			Map<String, Object> summary = new HashMap<String, Object>();
			summary.put("bet", StringUtil.getDouble(result.get("bet")));
			summary.put("amount", StringUtil.getDouble(result.get("amount")));
			summary.put("subtract", StringUtil.getDouble(result.get("subtract")));
			summary.put("actual", StringUtil.getDouble(result.get("actual")));
			data.put("summary", summary);
			
			return data;
			
//			int count = this.reportWeeklyMemberService.selectAllCount(parameters);
//			return this.formatPagaMap(rwms, count);
		} catch (Exception e) {
			log.Error("reportWeeklyData 获取会员周洗码报表失败", e);
		}
		return null;
	}
	
	/**
	 * 会员周结报表发放选择
	 * @return
	 */
	@RequestMapping("/processWeeklySelect")
	@ResponseBody
	public Map<String, Object> processWeeklySelect(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> parameters = this.getRequestParamters(request);
			parameters.put("enterprisecode", loginEmployee.getEnterprisecode());
//			parameters.put("parentemployeecode", loginEmployee.getEmployeecode());
			super.dataLimits(loginEmployee, parameters, session);
			parameters.put("status", Enum_status.未发放.value);
			parameters.put("iditems", AttrCheckout.convertListString(String.valueOf(parameters.get("iditems"))));
			List<ReportWeeklyMember> processList = this.reportWeeklyMemberService.selectAll(parameters);
			if (processList.isEmpty()) {
				return this.packJSON(Constant.BooleanByte.YES, "没有需要发放的记录");
			}
			String result = this.reportWeeklyMemberService.updateProcessWeekly(processList);
			
			userLogsService.addActivityBetRecord(new UserLogs(loginEmployee.getEnterprisecode(), loginEmployee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.结算管理业务, "会员周结发放", loginEmployee.getLoginaccount(), null));
			
			return this.packJSON(Constant.BooleanByte.YES, result);
		} catch (LogicTransactionException e) {
			log.Error("processDailySelect 发放选择的会员洗码失败", e);
			return this.packJSON(Constant.BooleanByte.NO, e.getMessage());
		} catch (Exception e) {
			log.Error("processWeeklySelect 发放选择的会员周洗码失败", e);
			return this.packJSON(Constant.BooleanByte.NO, "发放失败，可能是余额不足");
		}
		
	}
	
	/**
	 * 会员周结报表发放所有
	 * @return
	 */
	@RequestMapping("/processWeeklyAll")
	@ResponseBody
	public Map<String, Object> processWeeklyAll(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> parameters = this.getRequestParamters(request);
			parameters.put("enterprisecode", loginEmployee.getEnterprisecode());
//			parameters.put("parentemployeecode", loginEmployee.getEmployeecode());
			super.dataLimits(loginEmployee, parameters, session);
			
			parameters.put("status", Enum_status.未发放.value);
			List<ReportWeeklyMember> processList = this.reportWeeklyMemberService.selectAll(parameters);
			if (processList.isEmpty()) {
				return this.packJSON(Constant.BooleanByte.YES, "没有需要发放的记录");
			}
			String result = this.reportWeeklyMemberService.updateProcessWeekly(processList);
			
			userLogsService.addActivityBetRecord(new UserLogs(loginEmployee.getEnterprisecode(), loginEmployee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.结算管理业务, "会员周结发放", loginEmployee.getLoginaccount(), null));
			
			return this.packJSON(Constant.BooleanByte.YES, result);
		} catch (LogicTransactionException e) {
			log.Error("processDailySelect 发放选择的会员洗码失败", e);
			return this.packJSON(Constant.BooleanByte.NO, e.getMessage());
		} catch (Exception e) {
			log.Error("processWeeklyAll 发放选择的会员周洗码失败", e);
			return this.packJSON(Constant.BooleanByte.NO, "发放失败，可能是余额不足");
		}
		
	}
	
	
	
	
	
	/**
	 * 会员周结报表校验
	 * @return
	 */
	@RequestMapping("/processWeekCheck")
	@ResponseBody
	@SuppressWarnings("unused")
	public Map<String, Object> processWeekCheck(HttpServletRequest request, HttpSession session) {
		try {
			
			String reportcode = request.getParameter("reportcode");
			ReportWeeklyMember member = reportWeeklyMemberService.selectByPrimaryKey(reportcode);
			if(member.getAmount() - member.getSubtract() > 0) {
				return this.packJSON(Constant.BooleanByte.NO, "未完全发放完毕，无法校验。请先发放再做校验");
			}
			// 1=统计同一批次的累计周结数据
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("employeecode", member.getEmployeecode());
			params.put("gameplatform", member.getGameplatform());
			params.put("gametype", member.getGametype());
			params.put("patchno", member.getPatchno());
			List<ReportWeeklyMember> listReportWeeklyMember = reportWeeklyMemberService.selectAll(params);
			double but = 0;
			double amount = 0;
			for (ReportWeeklyMember reportWeeklyMember : listReportWeeklyMember) {
				but += reportWeeklyMember.getBet();
				amount += reportWeeklyMember.getAmount();
			}
			
			// 2=统计日结数据
			params.clear();
			params.put("employeecode", member.getEmployeecode());
			params.put("gamePlatform", member.getGameplatform());//平台
			params.put("gameBigType", member.getGametype());//游戏分类
			params.put("startBetDay", member.getStarttime());
			params.put("endBetDay", member.getEndtime());
			List<BettingAllDay> listBettingAllDay = bettingAllDayService.selectBettingByDate(params); //查询企业下上周所有游戏投注用户
			
			
			//3=比较
			double rebatesCash = 0;
			double butMoney = 0;
			for (BettingAllDay bad : listBettingAllDay) { 
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
			ReportWeeklyMember reportWeeklyAgent = reportWeeklyMemberService.selectByPrimaryKey(reportcode);
			if( !reportWeeklyAgent.getPaytype().equals(ReportWeeklyAgent.Enum_paytype.补发.value)) {
				return this.packJSON(Constant.BooleanByte.NO, "非补发记录，不能修改金额");
			}
			if( !reportWeeklyAgent.getStatus().equals(ReportWeeklyAgent.Enum_status.未发放.value)) {
				return this.packJSON(Constant.BooleanByte.NO, "不是未发放状态，不能修改金额");
			}
			reportWeeklyAgent.setActual(Double.valueOf(paymoneyreal));
			reportWeeklyMemberService.update(reportWeeklyAgent);
			
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			userLogsService.addActivityBetRecord(new UserLogs(loginEmployee.getEnterprisecode(), reportWeeklyAgent.getEmployeecode(), reportWeeklyAgent.getLoginaccount(), 
				     UserLogs.Enum_operatype.结算管理业务, "会员周结修改金额", loginEmployee.getLoginaccount(), "旧金额"+reportWeeklyAgent.getActual()+"，新金额："+paymoneyreal));
			
			
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
	 * 会员周结报表补发
	 * @return
	 */
	@RequestMapping("/processWeekSupplement")
	@ResponseBody
	public Map<String, Object> processWeekSupplement(HttpServletRequest request, HttpSession session) {
		try {
			String reportcode = request.getParameter("reportcode");
			// 1=统计同一批次的累计周结数据
			ReportWeeklyMember member = reportWeeklyMemberService.selectByPrimaryKey(reportcode);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("employeecode", member.getEmployeecode());
			params.put("gameplatform", member.getGameplatform());
			params.put("gametype", member.getGametype());
			params.put("patchno", member.getPatchno());
			List<ReportWeeklyMember> listReportWeeklyMember = reportWeeklyMemberService.selectAll(params);
			double but = 0;
			double amount = 0;
			for (ReportWeeklyMember reportWeeklyMember : listReportWeeklyMember) {
				but += reportWeeklyMember.getBet();
				amount += reportWeeklyMember.getAmount();
			}
			
			// 2=统计日结数据
			params.clear();
			params.put("employeecode", member.getEmployeecode());
			params.put("gameplatform", member.getGameplatform());//平台
			params.put("gameBigType", member.getGametype());//游戏分类
			params.put("startBetDay", member.getStarttime());
			params.put("endBetDay", member.getEndtime());
			List<BettingAllDay> listBettingAllDay = bettingAllDayService.selectBettingByDate(params); //查询企业下上周所有游戏投注用户
			
			
			//3=比较
			double rebatesCash = 0;
			double butMoney = 0;
			for (BettingAllDay bad : listBettingAllDay) { 
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
				this.reportWeeklyMemberService.updateProcessSupplement(member, MoneyHelper.moneyFormatDouble(butMoney - but), diff);
				
				EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
				userLogsService.addActivityBetRecord(new UserLogs(loginEmployee.getEnterprisecode(), member.getEmployeecode(), member.getLoginaccount(), 
					     UserLogs.Enum_operatype.结算管理业务, "会员周结补发", loginEmployee.getLoginaccount(), null));
				
				
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
