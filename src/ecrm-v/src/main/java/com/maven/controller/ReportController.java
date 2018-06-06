package com.maven.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.maven.constant.Constant;
import com.maven.entity.BettingAllDay;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeCapitalAccount;
import com.maven.entity.EnterpriseEmployeeType;
import com.maven.entity.EnterpriseEmployeeType.Type;
import com.maven.entity.Game;
import com.maven.entity.TakeDepositRecord;
import com.maven.entity.WorkingFlowObject;
import com.maven.entity.TakeDepositRecord.Enum_orderstatus;
import com.maven.entity.TakeDepositRecord.Enum_ordertype;
import com.maven.game.enums.GameEnum;
import com.maven.game.enums.GameEnum.Enum_GameType;
import com.maven.logger.LoggerManager;
import com.maven.service.BettingAllDayService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.GameService;
import com.maven.service.TakeDepositRecoredService;
import com.maven.service.UserLogsService;
import com.maven.utility.DateUtil;
import com.maven.utils.MoneyHelper;
import com.maven.utils.StringUtil;

@RequestMapping("/report")
@Controller
public class ReportController extends BaseController{
	
	/**
	 * 存取款
	 */
	@Autowired
	private TakeDepositRecoredService takeDepositRecoredService;
	
	/**
	 * 用户
	 */
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeServiceImpl;
	
	/**
	 * 账户资金
	 */
	@Autowired
	private EnterpriseEmployeeCapitalAccountService enterpriseEmployeeCapitalAccountServiceImpl;
	
	/**
	 * 游戏记录
	 */
	@Autowired
	private BettingAllDayService bettingAllDayServiceImpl;
	
	@Autowired
	private GameService gameService;
	@Autowired
	private UserLogsService userLogsService;
	
	//业务办理
	@RequestMapping("/businessHandleReport")
	private String businessHandle(){
		return "/report/businessHandleReport";
	}
	//业务办理详情
	@RequestMapping("/businessHandleReportDetail")
	private String businessHandleReportDetail(Model model,HttpServletRequest request){
		model.addAttribute("startDate",request.getParameter("startDate"));
		model.addAttribute("endDate",request.getParameter("endDate"));
		model.addAttribute("startHandleTime",request.getParameter("startHandleTime"));
		model.addAttribute("endHandleTime",request.getParameter("endHandleTime"));
		return "/report/businessHandleReportDetail";
	}
	
	//存款排名报表页面
	@RequestMapping("/employeeDepositRankingReport")
	private String employeeDepositRankingReport(){
		return "/report/employeeDepositRankingReport";
	}
	//存款排名详细页面
	@RequestMapping("/employeeDepositRankingReportDetail")
	private String employeeDepositRankingReportDetail(Model model,HttpServletRequest request){
		model.addAttribute("employeecode", request.getParameter("employeecode"));
		model.addAttribute("orderStartDate", request.getParameter("orderStartDate"));
		model.addAttribute("orderEndDate", request.getParameter("orderEndDate"));
		return "/report/employeeDepositRankingDetail";
	}
	
	//取款排名报表页面
	@RequestMapping("/employeeWithdrawalsRankingReport")
	private String employeeWithdrawalsRankingReport(){
		return "/report/employeeWithdrawalsRankingReport";
	}
	//取款排名详细页面
	@RequestMapping("/employeeWithdrawalsRankingReportDetail")
	private String employeeWithdrawalskingReportDetail(Model model,HttpServletRequest request){
		model.addAttribute("employeecode", request.getParameter("employeecode"));
		model.addAttribute("orderStartDate", request.getParameter("orderStartDate"));
		model.addAttribute("orderEndDate", request.getParameter("orderEndDate"));
		return "/report/employeeWithdrawalsRankingDetail";
	}
	
	
	//用户活跃度报表页面
	@RequestMapping("/employeeActivityReport")
	private String employeeActivityReport(){
		return "/report/employeeActivityReport";
	}
	
	//用户活跃度报表页面
	@RequestMapping("/employeeActivityDetail")
	private String employeeActivityDetail(Model model,HttpServletRequest request){
		model.addAttribute("loginDate", request.getParameter("loginDate"));
		return "/report/employeeActivityDetail";
	}
	
	//用户注册统计报表页面
	@RequestMapping("/userRegistered")
	private String userRegistered(){
		return "/report/userRegisteredReport";
	}

	//客户输赢分析列表
	@RequestMapping("/userLoseWinAnalysis")
	private String userLoseWinAnalysis(){
		return "/report/userLoseWinAnalysis";
	}
	
	//取款次数排名列表
	@RequestMapping("/employeeWithdraNumberRanking")
	private String employeeWithdraNumberRanking(){
		return "/report/withdraNumberRankingReport";
	}
	
	
	//取款次数排名明细
	@RequestMapping("/withdraNumberRankingReportDetail")
	private String withdraNumberRankingReportDetail(Model model,HttpServletRequest request){
		model.addAttribute("employeecode", request.getParameter("employeecode"));
		model.addAttribute("orderStartDate", request.getParameter("orderStartDate"));
		model.addAttribute("orderEndDate", request.getParameter("orderEndDate"));
		return "/report/withdraNumberRankingReportDetail";
	}
	
	//用户输赢统计
	@RequestMapping("/userLoseWinCount")
	private String userLoseWinCount(Model model,HttpServletRequest request){
		model.addAttribute("employeecode",request.getParameter("employeecode"));
		model.addAttribute("employeeType",request.getParameter("employeeType"));
		return "/report/userLoseWinCount";
	}
	
	//游戏报表
	@RequestMapping("/userGameReport")
	private String userGameReport(Model model,HttpServletRequest request){
		model.addAttribute("employeecode",request.getParameter("employeecode"));
		//model.addAttribute("employeeType",request.getParameter("employeeType"));
		return "/report/userGameReport";
	}
	
	//游戏报表
	@RequestMapping("/memberGameRecord")
	private String memberGameRecord(Model model,HttpServletRequest request){
		model.addAttribute("employeecode",request.getParameter("employeecode"));
		//model.addAttribute("employeeType",request.getParameter("employeeType"));
		return "/report/memberGameRecord";
	}
	
	//利润报表
	@RequestMapping("/userProfitReport")
	private String userProfitReport(Model model,HttpServletRequest request){
		model.addAttribute("employeecode",request.getParameter("employeecode"));
		model.addAttribute("employeeType",request.getParameter("employeeType"));
		return "/report/userProfitReport";
	}
	
	//用户存取款汇总统计
	@RequestMapping("/userDepositTakeReport")
	private String userDepositTakeReport(){
		return "/report/userDepositTakeReport";
	}	
	//用户提存比分析统计
	@RequestMapping("/userDepositTakeRateReport")
	private String userDepositTakeRateReport(){
		return "/report/userDepositTakeRateReport";
	}
	//用户输赢概率统计分析
	@RequestMapping("/userGameWinLoseRateReport")
	private String userGameWinLoseRateReport(Model model){
		Enum_GameType[] enum_array = GameEnum.Enum_GameType.values();
		
		
		Map<String, String> typeMap = new HashMap<String, String>();
		for (Enum_GameType type : enum_array) {
	        typeMap.put(type.bettingcode, type.name);
	    }
	    model.addAttribute("enum_array", typeMap);
		return "/report/userGameWinLoseRateReport";
	}
	
	//用户输赢排名报表页面
	@RequestMapping("/userLoseWinRankingReport")
	private String userLoseWinRankingReport(Model model,HttpServletRequest request){
		model.addAttribute("mark", request.getParameter("mark"));
		return "/report/userLoseWinRankingReport";
	}	
	
	//用户输赢游戏记录详情
	@RequestMapping("/userLoseWinGameRecordDetail")
	private String userLoseWinGameRecordDetail(Model model,HttpServletRequest request){
		model.addAttribute("mark", request.getParameter("mark"));
		model.addAttribute("loginaccount", request.getParameter("loginaccount"));
		model.addAttribute("startDate", request.getParameter("startDate"));
		model.addAttribute("endDate", request.getParameter("endDate"));
		return "/report/userLoseWinGameRecordDetail";
	}
	
	//代理贡献排名
	@RequestMapping("/agentContributionRankingReport")
	private String agentContributionRankingReport(){
		return "/report/agentContributionRankingReport";
	}
	
	//代理贡献明细
	@RequestMapping("/agentContributionRankingDetail")
	private String agentContributionRankingDetail(Model model,HttpServletRequest request){
		model.addAttribute("employeecode", request.getParameter("employeecode"));
		return "/report/agentContributionRankingDetail";
	}
	
	//用户来源统计
	@RequestMapping("/userDomainLink")
	private String userDomainLink(){
		return "/report/userDomainCount";
	}
	
	/**
	 * 业务处理时间统计报表查询
	 * @param request
	 */
	@RequestMapping("/businessHandleCount")
	@ResponseBody
	public List<TakeDepositRecord> businessHandleCount(HttpServletRequest request,HttpSession session){
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			paramObj.put("enterprisecode", loginEmployee.getEnterprisecode());
			List<TakeDepositRecord> take = takeDepositRecoredService.call_businessHandleCount(paramObj);
			return take;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 业务处理时间统计报表明细查询
	 * @param request
	 */
	@RequestMapping("/queryBusinessHandleReportDetail")
	@ResponseBody
	public Map<String, Object> queryBusinessHandleReportDetail(HttpServletRequest request,HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			paramObj.put("orderstatus", Enum_orderstatus.审核通过.value);
			paramObj.put("flowcodes", new ArrayList<WorkingFlowObject>());
			super.dataLimits(loginEmployee, paramObj,session);
			// 查询分页数据
			List<TakeDepositRecord> list = takeDepositRecoredService.findTakeDepositRecord(paramObj);
			// 统计数据
			Map<String, Object> result = takeDepositRecoredService.findcountTakeDepositRecord(paramObj);
			int countRecord = StringUtil.getInt(result.get("count"));
			map.put("rows", list);
			map.put("results", countRecord);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
	/**
	 * 存款统计报表查询
	 * @param request
	 */
	@RequestMapping("/queryEmployeeDepositRankingReport")
	@ResponseBody
	public List<TakeDepositRecord> queryEmployeeDepositRankingReport(HttpServletRequest request,HttpSession session){
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			paramObj.put("employeecode", loginEmployee.getEmployeecode());//"E00000BN"
			List<TakeDepositRecord> depositList = takeDepositRecoredService.call_employeeDepositRankingReportCount(paramObj);
			return depositList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 存款统计报表明细查询
	 * @param request
	 */
	@RequestMapping("/queryEmployeeDepositRankingDeatil")
	@ResponseBody
	public Map<String, Object> queryEmployeeDepositRankingDeatil(HttpServletRequest request,HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, Object> paramObj = getRequestParamters(request);
			paramObj.put("ordertype", Enum_ordertype.存款.value);
			paramObj.put("orderstatus", Enum_orderstatus.审核通过.value);			
			// 查询分页数据
			List<TakeDepositRecord> list = takeDepositRecoredService.findTakeDepositRecord(paramObj);
			// 统计数据
			Map<String, Object> result = takeDepositRecoredService.findcountTakeDepositRecord(paramObj);
			int countRecord = StringUtil.getInt(result.get("count"));
			map.put("rows", list);
			map.put("results", countRecord);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 取款统计报表查询
	 * @param request
	 */
	@RequestMapping("/queryEmployeeWithdrawalsRankingReport")
	@ResponseBody
	public List<TakeDepositRecord> queryEmployeeWithdrawalsRankingReport(HttpServletRequest request,HttpSession session){
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			paramObj.put("employeecode", loginEmployee.getEmployeecode());//"E00000BN"
			List<TakeDepositRecord> deposit = takeDepositRecoredService.call_employeeWithdrawalsRankingReportCount(paramObj);
			return deposit;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 取款统计报表明细查询
	 * @param request
	 */
	@RequestMapping("/queryEmployeeWithdrawalsRankingDeatil")
	@ResponseBody
	public Map<String, Object> queryEmployeeWithdrawalsRankingDeatil(HttpServletRequest request,HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, Object> paramObj = getRequestParamters(request);
			paramObj.put("ordertype", Enum_ordertype.取款.value);
			paramObj.put("orderstatus", Enum_orderstatus.审核通过.value);			
			// 查询分页数据
			List<TakeDepositRecord> list = takeDepositRecoredService.findTakeDepositRecord(paramObj);
			// 统计数据
			Map<String, Object> result = takeDepositRecoredService.findcountTakeDepositRecord(paramObj);
			int countRecord = StringUtil.getInt(result.get("count"));
			map.put("rows", list);
			map.put("results", countRecord);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 用户活跃度报表查询方法
	 * @param request
	 */
	@RequestMapping("/queryEmployeeActivityReport")
	@ResponseBody
	public List<EnterpriseEmployee> employeeActivityReport(HttpServletRequest request,HttpSession session){
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			paramObj.put("employeecode", loginEmployee.getEmployeecode());
			List<EnterpriseEmployee> list = enterpriseEmployeeServiceImpl.call_employeeActivityReport(paramObj);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 用户活跃度报表明细查询方法
	 * @param request
	 */
	@RequestMapping("/queryEmployeeActivityDetail")
	@ResponseBody
	public Map<String,Object> queryEmployeeActivityDetail(HttpServletRequest request,HttpSession session){
		Map<String,Object> map =new HashMap<String,Object>();
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			paramObj.put("parentemployeecode", loginEmployee.getEmployeecode());
			//查询
			List<EnterpriseEmployee> list = enterpriseEmployeeServiceImpl.findEmployee(paramObj);
			//查询总记录条数
			long listTotal = enterpriseEmployeeServiceImpl.findEmployeeCount(paramObj);
			map.put("rows", list);
			map.put("results", listTotal);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @see 客户输赢分析
	 * @param session
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/queryUserLoseWinAnalysis")
	@ResponseBody
	public Map<String, Object> queryUserLoseWinAnalysis(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			paramObj.put("enterprisecode", loginEmployee.getEnterprisecode());
			
//			this.dataLimits(loginEmployee, paramObj, session);
			List<EnterpriseEmployeeCapitalAccount> list = enterpriseEmployeeCapitalAccountServiceImpl.queryUserLoseWinAnalysis(paramObj);
			
			Map<String, Object> result = enterpriseEmployeeCapitalAccountServiceImpl.queryCountUserLoseWinAnalysis(paramObj);
			int countRecord = StringUtil.getInt(result.get("count"));
			map.put("rows", list);
			map.put("results", countRecord);

			Map<String, String> summary = new HashMap<String, String>();
			summary.put("accumulateddeposit", MoneyHelper.doubleFormat(result.get("accumulateddeposit")));
			summary.put("accumulatedwithdraw", MoneyHelper.doubleFormat(result.get("accumulatedwithdraw")));
			summary.put("summoney", MoneyHelper.doubleFormat(result.get("summoney")));
			map.put("summary", summary);
			
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 用户注册统计
	 * @param request
	 */
	@RequestMapping("/queryUserRegisteredReport")
	@ResponseBody
	public List<EnterpriseEmployee> queryUserRegisteredReport(HttpServletRequest request,HttpSession session){
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			paramObj.put("employeecode", loginEmployee.getEmployeecode());
			List<EnterpriseEmployee> list = enterpriseEmployeeServiceImpl.call_userRegisteredReport(paramObj);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @see 取款次数排名报表
	 * @param session
	 * @param request
	 * @return Map<String,Object>
	 */
	
	@RequestMapping("/withdraNumberRankingReport")
	@ResponseBody
	public List<TakeDepositRecord> withdraNumberRankingReport(HttpSession session, HttpServletRequest request) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			paramObj.put("employeecode", loginEmployee.getEmployeecode());
			// 取款次数统计查询
			List<TakeDepositRecord> list = takeDepositRecoredService.call_employeeWithdraNumberRanking(paramObj);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @see 取款次数排名明细查询
	 * @param session
	 * @param request
	 * @return Map<String,Object>
	 */
	
	@RequestMapping("/queryWithdraNumberRankingReportDetail")
	@ResponseBody
	public Map<String, Object> queryWithdraNumberRankingReportDetail(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
		//	EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			paramObj.put("orderstatus", Enum_orderstatus.审核通过.value);
			paramObj.put("ordertype",Enum_ordertype.取款.value);
			// 查询分页数据
			List<TakeDepositRecord> list = takeDepositRecoredService.findTakeDepositRecord(paramObj);
			// 统计数据
			Map<String, Object> result = takeDepositRecoredService.findcountTakeDepositRecord(paramObj);
			int countRecord = StringUtil.getInt(result.get("count"));
			map.put("rows", list);
			map.put("results", countRecord);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * @see 用户输赢统计查询
	 * @param session
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/queryUserLoseWinCount")
	@ResponseBody
	public Map<String, Object> queryUserLoseWinCount(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			if(null== paramObj.get("employeeType")
					||paramObj.get("employeeType").equals(EnterpriseEmployeeType.Type.代理.value)
						||paramObj.get("employeeType").equals(EnterpriseEmployeeType.Type.信用代理.value)){
				if(null== paramObj.get("employeeType")){
					paramObj.put("employeecode", loginEmployee.getEmployeecode());
				}
				List<EnterpriseEmployee> list = enterpriseEmployeeServiceImpl.call_userLoseWinCount(paramObj);
				map.put("rows", list);
				map.put("results", paramObj.get("countNumber"));
				return map;
			}else{
				List<EnterpriseEmployee> list = enterpriseEmployeeServiceImpl.call_userDownMemberDetail(paramObj);
				map.put("rows", list);
				map.put("results", paramObj.get("countNumber"));
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * @see 用户输赢统计查询（新的）
	 * @param session
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/queryUserLoseWinCountNew")
	@ResponseBody
	public Map<String, Object> queryUserLoseWinCountNew(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			Object employeecode = paramObj.get("employeecode");
			Object loginaccount = null;
			Object startDate = paramObj.get("startDate");
			Object endDate = paramObj.get("endDate");
			String employeeType = StringUtil.getString(paramObj.get("employeeType"));//查会员时
			paramObj.remove("startDate");
			paramObj.remove("endDate");
			paramObj.remove("employeecode");
			
			List<Map<String, Object>> listRecord = new ArrayList<Map<String,Object>>();
			
			if(employeeType.equals("T003")) {
				paramObj.put("employeetypecode", Type.会员.value);
				paramObj.put("parentemployeecode", employeecode);
				List<EnterpriseEmployee> listEnterpriseEmployee = enterpriseEmployeeServiceImpl.selectAll(paramObj);
				int count = enterpriseEmployeeServiceImpl.selectAllCount(paramObj);
				
				for (EnterpriseEmployee enterpriseEmployee : listEnterpriseEmployee) {
					
					paramObj.clear();
					paramObj.put("employeecode", enterpriseEmployee.getEmployeecode());
					paramObj.put("startDate", startDate);
					paramObj.put("endDate", endDate);
					enterpriseEmployeeServiceImpl.call_UserGameMemberReportOne(paramObj);
					
					Map<String, Object> item = new HashMap<String, Object>();
					item.put("loginaccount", enterpriseEmployee.getLoginaccount());
					item.put("employeecode", enterpriseEmployee.getEmployeecode());
					item.put("employeetypename", "个人会员");
					item.put("game_betting_amount", StringUtil.getDouble(paramObj.get("but_money")));
					double lose_win_amount = MoneyHelper.moneyFormatDouble( StringUtil.getDouble(paramObj.get("net_money")) + StringUtil.getDouble(paramObj.get("rebates_cash")) );
					double rebates_cash = StringUtil.getDouble(paramObj.get("rebates_cash"));
					//查活动金额
					double activity_money = StringUtil.getDouble(paramObj.get("activity_money"));
					
					//代理分红(游戏输赢为负数的时候计算) = abs(游戏输赢) * 分红比例 
					double dividend = MoneyHelper.moneyFormatDouble( Math.abs(lose_win_amount < 0 ?  enterpriseEmployee.getDividend().doubleValue() * (lose_win_amount - activity_money ) : 0) );
					
					//客户输赢 = 游戏输赢  + 代理分红
					double user_lose_win_amount = MoneyHelper.moneyFormatDouble( lose_win_amount + dividend);
					
					//代理占成 = 客户输赢 * 占成比例 
					double share = MoneyHelper.moneyFormatDouble( (user_lose_win_amount  ) * enterpriseEmployee.getShare().doubleValue() );
					
					item.put("activity_money", activity_money);
					item.put("lose_win_amount", lose_win_amount);
					item.put("bonus", rebates_cash);
					item.put("countMember", 0);
					item.put("countAgent", 0);
					item.put("dividend", dividend);
					item.put("share", share);
					item.put("user_lose_win_amount", user_lose_win_amount);
					item.put("remark", "会员个人汇总");
					listRecord.add(item);
				}
				map.put("rows", listRecord);
				map.put("results", count);

			} else {
				paramObj.put("employeetypecode", Type.代理.value);
				//查所有的直线代理团队汇总数据
				if(employeecode == null){
					paramObj.put("parentemployeecode", loginEmployee.getEmployeecode());
					loginaccount = loginEmployee.getLoginaccount();
					employeecode = loginEmployee.getEmployeecode();
				} else {
					paramObj.put("parentemployeecode", employeecode);
					loginEmployee = enterpriseEmployeeServiceImpl.takeEmployeeByCode(String.valueOf(employeecode));
					loginaccount = loginEmployee.getLoginaccount();
					employeecode = loginEmployee.getEmployeecode();
				}
				List<EnterpriseEmployee> listEnterpriseEmployee = enterpriseEmployeeServiceImpl.selectAll(paramObj);
				int count = enterpriseEmployeeServiceImpl.selectAllCount(paramObj);
				
				for (EnterpriseEmployee enterpriseEmployee : listEnterpriseEmployee) {
					
					paramObj.clear();
					paramObj.put("employeecode", enterpriseEmployee.getEmployeecode());
					paramObj.put("startDate", startDate);
					paramObj.put("endDate", endDate);
					enterpriseEmployeeServiceImpl.call_UserGameAgentReport(paramObj);
					
					Map<String, Object> item = new HashMap<String, Object>();
					item.put("loginaccount", enterpriseEmployee.getLoginaccount());
					item.put("employeecode", enterpriseEmployee.getEmployeecode());
					item.put("employeetypename", "代理");
					item.put("game_betting_amount", StringUtil.getDouble(paramObj.get("but_money")));
					double lose_win_amount = MoneyHelper.moneyFormatDouble( StringUtil.getDouble(paramObj.get("net_money")) + StringUtil.getDouble(paramObj.get("rebates_cash")) );
					double activity_money = StringUtil.getDouble(paramObj.get("activity_money"));
					
					//代理分红(游戏输赢为负数的时候计算) = abs(游戏输赢) * 分红比例 
					double dividend = MoneyHelper.moneyFormatDouble( Math.abs(lose_win_amount < 0 ?  enterpriseEmployee.getDividend().doubleValue() * (lose_win_amount - activity_money ) : 0) );
					
					//客户输赢 = 游戏输赢  + 代理分红
					double user_lose_win_amount = MoneyHelper.moneyFormatDouble( lose_win_amount + dividend);
					
					//代理占成 = 客户输赢 * 占成比例 
					double share = MoneyHelper.moneyFormatDouble( (user_lose_win_amount  ) * enterpriseEmployee.getShare().doubleValue() );
					
					item.put("activity_money", activity_money);
					item.put("lose_win_amount", lose_win_amount);
					item.put("bonus", StringUtil.getDouble(paramObj.get("rebates_cash")));
					item.put("countMember", paramObj.get("countMember"));
					item.put("countAgent", paramObj.get("countAgent"));
					item.put("dividend", dividend);
					item.put("share", share);
					item.put("user_lose_win_amount", user_lose_win_amount);
					
					item.put("remark", "直线代理团队汇总");
					listRecord.add(item);
				}
				
				//查所有直线会员汇总数据
				paramObj.clear();
				paramObj.put("employeecode", employeecode);
				paramObj.put("startDate", startDate);
				paramObj.put("endDate", endDate);
				enterpriseEmployeeServiceImpl.call_UserGameMemberReport(paramObj);
				Map<String, Object> item = new HashMap<String, Object>();
				item.put("loginaccount", loginaccount);
				item.put("employeecode", employeecode);
				item.put("employeetypename", "会员");
				item.put("game_betting_amount", StringUtil.getDouble(paramObj.get("but_money")));
				
				double lose_win_amount = MoneyHelper.moneyFormatDouble( StringUtil.getDouble(paramObj.get("net_money")) + StringUtil.getDouble(paramObj.get("rebates_cash")) );//游戏输赢
				double activity_money = StringUtil.getDouble(paramObj.get("activity_money"));
				//代理分红(游戏输赢为负数的时候计算) = abs(游戏输赢) * 分红比例 
				double dividend = MoneyHelper.moneyFormatDouble( Math.abs(lose_win_amount < 0 ?  loginEmployee.getDividend().doubleValue() * (lose_win_amount - activity_money )  : 0) );
				
				//客户输赢 = 中奖金额 + 返点金额(有效投注额 * 返点比例)  + 代理分红(abs(游戏输赢) * 分红比例) -  投注金额
				double user_lose_win_amount = MoneyHelper.moneyFormatDouble( lose_win_amount + dividend);
				
				//代理占成 = 客户输赢(中奖金额 + 返点金额(有效投注额 * 返点比例)  + 代理分红(abs(游戏输赢) * 分红比例) -  投注金额) * 占成比例 
				double share = MoneyHelper.moneyFormatDouble( (user_lose_win_amount + dividend ) * loginEmployee.getShare().doubleValue() );
				
				item.put("activity_money", activity_money);
				item.put("lose_win_amount", lose_win_amount);
				item.put("bonus", StringUtil.getDouble(paramObj.get("rebates_cash")));
				item.put("countMember", paramObj.get("countMember"));
				item.put("countAgent", 0);
				item.put("dividend", dividend);
				item.put("share", share);
				item.put("user_lose_win_amount", user_lose_win_amount);
				item.put("remark", "直线会员汇总");
				listRecord.add(0,item);
				
				map.put("rows", listRecord);
				map.put("results", count);
			}
			
			return map;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * @see 游戏报表查询（新的）
	 * @param session
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/queryUserGameLoseWinNew")
	@ResponseBody
	public Map<String, Object> queryUserGameLoseWinNew(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			Object employeecode = paramObj.get("employeecode");
			Object loginaccount = null;
			Object startDate = paramObj.get("startDate");
			Object endDate = paramObj.get("endDate");
			paramObj.remove("startDate");
			paramObj.remove("endDate");
			paramObj.remove("employeecode");
			
			List<Map<String, Object>> listRecord = new ArrayList<Map<String,Object>>();
			
			paramObj.put("employeetypecode", Type.代理.value);
			//查所有的直线代理团队汇总数据
			if(employeecode == null){
				paramObj.put("parentemployeecode", loginEmployee.getEmployeecode());
				loginaccount = loginEmployee.getLoginaccount();
				employeecode = loginEmployee.getEmployeecode();
			} else {
				paramObj.put("parentemployeecode", employeecode);
				EnterpriseEmployee target = enterpriseEmployeeServiceImpl.takeEmployeeByCode(String.valueOf(employeecode));
				loginaccount = target.getLoginaccount();
				employeecode = target.getEmployeecode();
			}
			List<EnterpriseEmployee> listEnterpriseEmployee = enterpriseEmployeeServiceImpl.selectAll(paramObj);
			int count = enterpriseEmployeeServiceImpl.selectAllCount(paramObj);
			
			for (EnterpriseEmployee enterpriseEmployee : listEnterpriseEmployee) {
				
				paramObj.clear();
				paramObj.put("employeecode", enterpriseEmployee.getEmployeecode());
				paramObj.put("startDate", startDate);
				paramObj.put("endDate", endDate);
				enterpriseEmployeeServiceImpl.call_UserGameAgentReport(paramObj);
				
				Map<String, Object> item = new HashMap<String, Object>();
				item.put("loginaccount", enterpriseEmployee.getLoginaccount());
				item.put("employeecode", enterpriseEmployee.getEmployeecode());
				item.put("employeetypename", "代理");
				item.put("game_betting_amount", StringUtil.getDouble(paramObj.get("but_money")));
				item.put("lose_win_amount", StringUtil.getDouble(paramObj.get("net_money")) + StringUtil.getDouble(paramObj.get("rebates_cash")));
				item.put("bonus", StringUtil.getDouble(paramObj.get("rebates_cash")));
				item.put("countMember", paramObj.get("countMember"));
				item.put("countAgent", paramObj.get("countAgent"));
				item.put("remark", "直线代理团队汇总");
				listRecord.add(item);
			}
			
			//查所有直线会员汇总数据
			paramObj.clear();
			paramObj.put("employeecode", employeecode);
			paramObj.put("startDate", startDate);
			paramObj.put("endDate", endDate);
			enterpriseEmployeeServiceImpl.call_UserGameMemberReport(paramObj);
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("loginaccount", loginaccount);
			item.put("employeecode", employeecode);
			item.put("employeetypename", "会员");
			item.put("game_betting_amount", StringUtil.getDouble(paramObj.get("but_money")));
			item.put("lose_win_amount", StringUtil.getDouble(paramObj.get("net_money")) + StringUtil.getDouble(paramObj.get("rebates_cash")));
			item.put("bonus", StringUtil.getDouble(paramObj.get("rebates_cash")));
			item.put("countMember", paramObj.get("countMember"));
			item.put("countAgent", 0);
			item.put("remark", "直线会员汇总");
			listRecord.add(0,item);
			
			map.put("rows", listRecord);
			map.put("results", count);
			return map;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * @see 游戏报表查询
	 * @param session
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/queryUserGameLoseWin")
	@ResponseBody
	public Map<String, Object> queryUserGameLoseWin(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			//if(null== paramObj.get("employeeType")||paramObj.get("employeeType").equals(EnterpriseEmployeeType.Type.代理.value)){
				if(null == paramObj.get("employeecode")){
					paramObj.put("employeecode", loginEmployee.getEmployeecode());
					List<EnterpriseEmployee> list = enterpriseEmployeeServiceImpl.call_userGameLoseWinCount(paramObj);
					map.put("rows", list);
					map.put("results", paramObj.get("countNumber"));
					return map;
				}else{
					List<EnterpriseEmployee> list = enterpriseEmployeeServiceImpl.call_userGameLoseWinCount(paramObj);
					map.put("rows", list);
					map.put("results", paramObj.get("countNumber"));
					return map;
				}
				
				
			//}else{
				/*List<EnterpriseEmployee> list = enterpriseEmployeeServiceImpl.queryUserGameDownMemberDetail(paramObj);
				map.put("rows", list);
				map.put("results", paramObj.get("countNumber"));
				return map;*/
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * @see 会员的游戏记录查询
	 * @param session
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/queryUserGameRecord")
	@ResponseBody
	public Map<String, Object> queryUserGameRecord(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, Object> paramObj = getRequestParamters(request);
			
			List<EnterpriseEmployee> list = enterpriseEmployeeServiceImpl.call_userGameDownMemberDetail(paramObj);
			map.put("rows", list);
			map.put("results", paramObj.get("countNumber"));
			
			//统计数
			Map<String, Object> summary = new HashMap<String, Object>();
			summary.put("game_betting_amount", StringUtil.getDouble(paramObj.get("game_betting_amount")));
			summary.put("lose_win_amount", StringUtil.getDouble(paramObj.get("lose_win_amount")));
			summary.put("bonus", StringUtil.getDouble(paramObj.get("bonus")));
			map.put("summary", summary);
			
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * @see 会员的游戏记录查询（新的）
	 * @param session
	 * @param request
	 * @return Map<String,Object>
	 */
	private static Map<String, Game> mapGame ;
	@RequestMapping("/queryUserGameRecordNew")
	@ResponseBody
	public Map<String, Object> queryUserGameRecordNew(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, Object> paramObj = getRequestParamters(request);
			
			List<EnterpriseEmployee> list = enterpriseEmployeeServiceImpl.call_userGameDownMemberDetailNew(paramObj);
			
			if(mapGame == null) {
				List<Game> listGame = this.gameService.getAllGame();
				mapGame = new HashMap<String, Game>();
				for (Game game : listGame) {
					mapGame.put(game.getGametype(), game);
				}
			}
			
			
			for (EnterpriseEmployee item : list) {
				item.setEmployeetypename("会员");
				Game game = mapGame.get(item.getGametype());
				item.setGamename(game == null ? "" : game.getGamename());
			}
			map.put("rows", list);
			map.put("results", paramObj.get("countMember"));
			
			//统计数
//			Map<String, Object> summary = new HashMap<String, Object>();
//			summary.put("game_betting_amount", StringUtil.getDouble(paramObj.get("but_money")));
//			summary.put("lose_win_amount", StringUtil.getDouble(paramObj.get("net_money")));
//			summary.put("bonus", StringUtil.getDouble(paramObj.get("rebates_cash")));
//			map.put("summary", summary);
			
			
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * @see 利润报表查询
	 * @param session
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/queryUserProfit")
	@ResponseBody
	public Map<String, Object> queryUserProfit(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			if(null== paramObj.get("employeeType")
					||paramObj.get("employeeType").equals(EnterpriseEmployeeType.Type.代理.value)
						||paramObj.get("employeeType").equals(EnterpriseEmployeeType.Type.信用代理.value)){
				if(null== paramObj.get("employeeType")){
					paramObj.put("employeecode", loginEmployee.getEmployeecode());
				}
				List<EnterpriseEmployee> list = enterpriseEmployeeServiceImpl.call_userProfitReportCount(paramObj);
				map.put("rows", list);
				map.put("results", paramObj.get("countNumber"));
				
				Map<String, String> summary = new HashMap<String, String>();
				summary.put("game_betting_amount", MoneyHelper.doubleFormat(paramObj.get("game_betting_amount")));
				summary.put("lose_win_amount", MoneyHelper.doubleFormat(paramObj.get("lose_win_amount")));
				summary.put("bonus", MoneyHelper.doubleFormat(paramObj.get("bonus")));
				summary.put("dividend", MoneyHelper.doubleFormat(paramObj.get("dividend")));
				summary.put("share", MoneyHelper.doubleFormat(paramObj.get("share")));
				summary.put("profit_amount", MoneyHelper.doubleFormat(paramObj.get("profit_amount")));
				map.put("summary", summary);
				
				return map;
			}else{
				List<EnterpriseEmployee> list = enterpriseEmployeeServiceImpl.call_userProfitReportDownMemberDetail(paramObj);
				map.put("rows", list);
				map.put("results", paramObj.get("countNumber"));
				
				Map<String, String> summary = new HashMap<String, String>();
				summary.put("game_betting_amount", MoneyHelper.doubleFormat(paramObj.get("game_betting_amount")));
				summary.put("lose_win_amount", MoneyHelper.doubleFormat(paramObj.get("lose_win_amount")));
				summary.put("bonus", MoneyHelper.doubleFormat(paramObj.get("bonus")));
				summary.put("dividend", MoneyHelper.doubleFormat(paramObj.get("dividend")));
				summary.put("share", MoneyHelper.doubleFormat(paramObj.get("share")));
				summary.put("profit_amount", MoneyHelper.doubleFormat(paramObj.get("profit_amount")));
				map.put("summary", summary);
				
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
	/**
	 * @see 利润报表查询（新的）
	 * @param session
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/queryUserProfitNew")
	@ResponseBody
	public Map<String, Object> queryUserProfitNew(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
			
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			Object employeecode = paramObj.get("employeecode");
			String employeeType = StringUtil.getString(paramObj.get("employeeType"));//查会员时
			Object loginaccount = null;
			Object startDate = paramObj.get("startDate");
			Object endDate = paramObj.get("endDate");
			paramObj.remove("startDate");
			paramObj.remove("endDate");
			paramObj.remove("employeecode");
			
			List<Map<String, Object>> listRecord = new ArrayList<Map<String,Object>>();
			
			if(employeeType.equals("T003")) {
				paramObj.put("employeetypecode", Type.会员.value);
				paramObj.put("parentemployeecode", employeecode);
				List<EnterpriseEmployee> listEnterpriseEmployee = enterpriseEmployeeServiceImpl.selectAll(paramObj);
				int count = enterpriseEmployeeServiceImpl.selectAllCount(paramObj);
				
				for (EnterpriseEmployee enterpriseEmployee : listEnterpriseEmployee) {
					//查所有直线会员汇总数据
					paramObj.clear();
					paramObj.put("employeecode", enterpriseEmployee.getEmployeecode());
					paramObj.put("startDate", startDate);
					paramObj.put("endDate", endDate);
					enterpriseEmployeeServiceImpl.call_UserGameMemberReportOne(paramObj);
					Map<String, Object> item = new HashMap<String, Object>();
					item.put("loginaccount", enterpriseEmployee.getLoginaccount());
					item.put("employeecode", enterpriseEmployee.getEmployeecode());
					item.put("employeetypename", "个人会员");
					item.put("game_betting_amount", StringUtil.getDouble(paramObj.get("but_money")));
					
					double lose_win_amount = MoneyHelper.moneyFormatDouble( StringUtil.getDouble(paramObj.get("net_money")) + StringUtil.getDouble(paramObj.get("rebates_cash")) );//游戏输赢
					double activity_money = StringUtil.getDouble(paramObj.get("activity_money"));
					//代理分红(游戏输赢为负数的时候计算) = abs(游戏输赢) * 分红比例 
					double dividend = MoneyHelper.moneyFormatDouble( Math.abs(lose_win_amount < 0 ?  loginEmployee.getDividend().doubleValue() * (lose_win_amount -  activity_money)  : 0) );
					
					//客户输赢 = 游戏输赢  + 代理分红
					double user_lose_win_amount = MoneyHelper.moneyFormatDouble( lose_win_amount + dividend);
					
					//代理占成 = 客户输赢 * 占成比例 
					double share = MoneyHelper.moneyFormatDouble( (user_lose_win_amount  ) * loginEmployee.getShare().doubleValue() );
					
					//企业利润 = (中奖金额   -  投注金额 + 返点金额  + 代理分红) * (1 - 代理占成比例)
					double profit_amount = MoneyHelper.moneyFormatDouble( (lose_win_amount + dividend ) * (1 - loginEmployee.getShare().doubleValue()) );
					
					item.put("activity_money", activity_money);
					item.put("lose_win_amount", lose_win_amount);
					item.put("bonus", StringUtil.getDouble(paramObj.get("rebates_cash")));
					item.put("countMember", 0);
					item.put("countAgent", 0);
					item.put("countEmployee", 0);
					item.put("dividend", dividend);
					item.put("share", share);
					item.put("user_lose_win_amount", user_lose_win_amount);
					item.put("profit_amount", profit_amount);
					item.put("depositMoney", StringUtil.getDouble(paramObj.get("depositMoney")));
					item.put("withdrawMoney", StringUtil.getDouble(paramObj.get("withdrawMoney")));
					item.put("remark", "会员个人汇总");
					listRecord.add(0,item);
				}
				map.put("rows", listRecord);
				map.put("results", count);
				
				
			} else {
//				paramObj.put("employeetypecode", Type.代理.value);
				List<String> employeetypecodes = new ArrayList<String>();
				employeetypecodes.add(Type.代理.value);
				employeetypecodes.add(Type.员工.value);
				paramObj.put("employeetypecodes", employeetypecodes);
				//查所有的直线代理团队汇总数据
				if(employeecode == null){
					paramObj.put("parentemployeecode", loginEmployee.getEmployeecode());
					loginaccount = loginEmployee.getLoginaccount();
					employeecode = loginEmployee.getEmployeecode();
				} else {
					paramObj.put("parentemployeecode", employeecode);
					loginEmployee = enterpriseEmployeeServiceImpl.takeEmployeeByCode(String.valueOf(employeecode));
					loginaccount = loginEmployee.getLoginaccount();
					employeecode = loginEmployee.getEmployeecode();
				}
				List<EnterpriseEmployee> listEnterpriseEmployee = enterpriseEmployeeServiceImpl.selectAll(paramObj);
				int count = enterpriseEmployeeServiceImpl.selectAllCount(paramObj);
				
				for (EnterpriseEmployee enterpriseEmployee : listEnterpriseEmployee) {
					
					paramObj.clear();
					paramObj.put("employeecode", enterpriseEmployee.getEmployeecode());
					paramObj.put("startDate", startDate);
					paramObj.put("endDate", endDate);
					enterpriseEmployeeServiceImpl.call_UserGameAgentReport(paramObj);
					
					Map<String, Object> item = new HashMap<String, Object>();
					item.put("loginaccount", enterpriseEmployee.getLoginaccount());
					item.put("employeecode", enterpriseEmployee.getEmployeecode());
					item.put("employeetypename", enterpriseEmployee.getEmployeetypename());
					item.put("game_betting_amount", StringUtil.getDouble(paramObj.get("but_money")));
					double lose_win_amount = MoneyHelper.moneyFormatDouble( StringUtil.getDouble(paramObj.get("net_money")) + StringUtil.getDouble(paramObj.get("rebates_cash")) );
					double activity_money = StringUtil.getDouble(paramObj.get("activity_money"));
					
					//代理分红(游戏输赢为负数的时候计算) = abs(游戏输赢) * 分红比例 
					double dividend = MoneyHelper.moneyFormatDouble( Math.abs(lose_win_amount < 0 ?  enterpriseEmployee.getDividend().doubleValue() * (lose_win_amount - activity_money )  : 0) );
					
					//客户输赢 = 游戏输赢  + 代理分红
					double user_lose_win_amount = MoneyHelper.moneyFormatDouble( lose_win_amount + dividend);
					
					//代理占成 = 客户输赢 * 占成比例 
					double share = MoneyHelper.moneyFormatDouble( (user_lose_win_amount  ) * enterpriseEmployee.getShare().doubleValue() );
					
					//企业利润 = (中奖金额   -  投注金额 + 返点金额  + 代理分红) * (1 - 代理占成比例)
					double profit_amount = MoneyHelper.moneyFormatDouble( (lose_win_amount + dividend ) * (1 - enterpriseEmployee.getShare().doubleValue()) );
					item.put("activity_money", activity_money);
					item.put("lose_win_amount", lose_win_amount);
					item.put("bonus", StringUtil.getDouble(paramObj.get("rebates_cash")));
					item.put("countMember", paramObj.get("countMember"));
					item.put("countAgent", paramObj.get("countAgent"));
					item.put("countEmployee", paramObj.get("countEmployee"));
					item.put("dividend", dividend);
					item.put("share", share);
					item.put("user_lose_win_amount", user_lose_win_amount);
					item.put("profit_amount", profit_amount);
					item.put("depositMoney", StringUtil.getDouble(paramObj.get("depositMoney")));
					item.put("withdrawMoney", StringUtil.getDouble(paramObj.get("withdrawMoney")));
					item.put("remark", "直线代理团队汇总");
					if(enterpriseEmployee.getEmployeetypecode().equals(Type.员工.value)) {
						item.put("remark", "下级员工团队汇总");
					}
					listRecord.add(item);
				}
				
				if(!loginEmployee.getEmployeetypecode().equals(Type.会员.value)) {
					//查所有直线会员汇总数据
					paramObj.clear();
					paramObj.put("employeecode", employeecode);
					paramObj.put("startDate", startDate);
					paramObj.put("endDate", endDate);
					enterpriseEmployeeServiceImpl.call_UserGameMemberReport(paramObj);
					Map<String, Object> item = new HashMap<String, Object>();
					item.put("loginaccount", loginaccount);
					item.put("employeecode", employeecode);
					item.put("employeetypename", "会员");
					item.put("game_betting_amount", StringUtil.getDouble(paramObj.get("but_money")));
					
					double lose_win_amount = MoneyHelper.moneyFormatDouble( StringUtil.getDouble(paramObj.get("net_money")) + StringUtil.getDouble(paramObj.get("rebates_cash")) );//游戏输赢
					double activity_money = StringUtil.getDouble(paramObj.get("activity_money"));
					//代理分红(游戏输赢为负数的时候计算) = abs(游戏输赢) * 分红比例 
					double dividend = MoneyHelper.moneyFormatDouble( Math.abs(lose_win_amount < 0 ?  loginEmployee.getDividend().doubleValue() * (lose_win_amount - activity_money )  : 0) );
					
					//客户输赢 = 游戏输赢  + 代理分红
					double user_lose_win_amount = MoneyHelper.moneyFormatDouble( lose_win_amount + dividend);
					
					//代理占成 = 客户输赢 * 占成比例 
					double share = MoneyHelper.moneyFormatDouble( (user_lose_win_amount  ) * loginEmployee.getShare().doubleValue() );
					
					//企业利润 = (中奖金额   -  投注金额 + 返点金额  + 代理分红) * (1 - 代理占成比例)
					double profit_amount = MoneyHelper.moneyFormatDouble( (lose_win_amount + dividend ) * (1 - loginEmployee.getShare().doubleValue()) );
					
					item.put("activity_money", activity_money);
					item.put("lose_win_amount", lose_win_amount);
					item.put("bonus", StringUtil.getDouble(paramObj.get("rebates_cash")));
					item.put("countMember", paramObj.get("countMember"));
					item.put("countAgent", 0);
					item.put("countEmployee", 0);
					item.put("dividend", dividend);
					item.put("share", share);
					item.put("user_lose_win_amount", user_lose_win_amount);
					item.put("profit_amount", profit_amount);
					item.put("depositMoney", StringUtil.getDouble(paramObj.get("depositMoney")));
					item.put("withdrawMoney", StringUtil.getDouble(paramObj.get("withdrawMoney")));
					item.put("remark", "直线会员汇总");
					listRecord.add(0,item);
					
				}
				
				map.put("rows", listRecord);
				map.put("results", count);
				
			}
			
			return map;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
	/**
	 * @see 用户存取款汇总统计
	 * @param session
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/queryUserDepositTake")
	@ResponseBody
	public Map<String, Object> userDepositTakeCount(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			paramObj.put("employeecode", loginEmployee.getEmployeecode());
			// 用户存取款查询
			List<TakeDepositRecord> list = takeDepositRecoredService.call_userDepositTakeCount(paramObj);
			map.put("rows", list);
			map.put("results", paramObj.get("countNumber"));
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * @see 用户提存比统计分析
	 * @param session
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/queryUserDepositTakeRate")
	@ResponseBody
	public Map<String, Object> userDepositTakeRate(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			paramObj.put("enterprisecode", loginEmployee.getEnterprisecode());
			// 用户存取款查询
			List<TakeDepositRecord> list = takeDepositRecoredService.call_userDepositTakeRate(paramObj);
			map.put("rows", list);
			map.put("results", paramObj.get("countNumber"));
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * @see 用户输赢概率统计分析
	 * @param session
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/queryUserGameWinLoseRate")
	@ResponseBody
	public Map<String, Object> userGameWinLoseRate(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			paramObj.put("enterprisecode", loginEmployee.getEnterprisecode());
			if(paramObj.get("loginaccount") != null && paramObj.get("loginaccount").toString().trim().equals("")) {
				paramObj.put("loginaccount", null);
			}
			// 用户存取款查询
			List<TakeDepositRecord> list = takeDepositRecoredService.call_userGameWinLoseRate(paramObj);
			map.put("rows", list);
			map.put("results", paramObj.get("countNumber"));
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * @see 用户输赢排名报表查询
	 * @param session
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/queryUserLoseWinRanking")
	@ResponseBody
	public Map<String, Object> queryUserLoseWinRanking(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			paramObj.put("employeecode", loginEmployee.getEmployeecode());
			if(paramObj.get("mark").equals("0")){
				List<BettingAllDay> list = bettingAllDayServiceImpl.call_userLoseRanking(paramObj);
				map.put("result", list);
				return map;
			}
			List<BettingAllDay> list = bettingAllDayServiceImpl.call_userWinRanking(paramObj);
			map.put("result", list);
			return map;
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * @see 查询用户输赢游戏记录
	 * @param session
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/queryUserLoseWinGameRecordDetail")
	@ResponseBody
	public Map<String, Object> queryUserLoseWinGameRecordDetail(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Map<String, Object> paramObj = getRequestParamters(request);
			if(paramObj.get("mark").equals("0")){
				paramObj.put("mark", "<");
			}else{
				paramObj.put("mark", ">");
			}
			List<BettingAllDay> list = bettingAllDayServiceImpl.queryUserLoseWinGameRecord(paramObj);
			int count = bettingAllDayServiceImpl.queryCountUserLoseWinGameRecord(paramObj);
			map.put("rows", list);
			map.put("results", count);
			return map;
		}catch(Exception e){
			e.printStackTrace();
			return map;
		}
	}
	
	/**
	 * 代理贡献排名查询
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryAgentContributionRankingReport")
	@ResponseBody
	public List<EnterpriseEmployee> queryAgentContributionRankingReport(HttpSession session,HttpServletRequest request){
		try {
			Map<String, Object> paramObj = getRequestParamters(request);
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			paramObj.put("employeecode", loginEmployee.getEmployeecode());
			List<EnterpriseEmployee> list = enterpriseEmployeeServiceImpl.call_agentContributionRankingReport(paramObj);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 代理贡献明细查询
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryAgentContributionDetail")
	@ResponseBody
	public List<EnterpriseEmployee> queryAgentContributionDetail(HttpSession session,HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			paramObj.put("employeecode", loginEmployee.getEmployeecode());
			List<EnterpriseEmployee> list = enterpriseEmployeeServiceImpl.call_agentContributionDetail(paramObj);
			map.put("rows", list);
			map.put("results", paramObj.get("countNumber"));
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 用户来源统计
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryUserDomainLink")
	@ResponseBody
	public List<EnterpriseEmployee> queryUserDomainLink(HttpSession session,HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			paramObj.put("employeecode", loginEmployee.getEmployeecode());
			List<EnterpriseEmployee> list = enterpriseEmployeeServiceImpl.call_userDomainLink(paramObj);
			map.put("rows", list);
			map.put("results", paramObj.get("countNumber"));
			return list;
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
