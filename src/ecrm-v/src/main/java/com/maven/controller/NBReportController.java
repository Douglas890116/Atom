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
import com.maven.entity.Enterprise;
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
import com.maven.service.EnterpriseService;
import com.maven.service.GameService;
import com.maven.service.TakeDepositRecoredService;
import com.maven.service.UserLogsService;
import com.maven.utility.DateUtil;
import com.maven.utils.MoneyHelper;
import com.maven.utils.StringUtil;

@RequestMapping("/nbreport")
@Controller
public class NBReportController extends BaseController{
	
	@Autowired
	private TakeDepositRecoredService takeDepositRecoredService;
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeServiceImpl;
	@Autowired
	private EnterpriseEmployeeCapitalAccountService enterpriseEmployeeCapitalAccountServiceImpl;
	@Autowired
	private BettingAllDayService bettingAllDayServiceImpl;
	@Autowired
	private EnterpriseService enterpriseService;
	@Autowired
	private GameService gameService;
	@Autowired
	private UserLogsService userLogsService;
	
	
	
	@RequestMapping("/a1")
	private String a1(Model model,HttpServletRequest request){
		model.addAttribute("employeecode",request.getParameter("employeecode"));
		model.addAttribute("employeeType",request.getParameter("employeeType"));
		return "/report/nbreporta1";
	}
	@RequestMapping("/a2")
	private String a2(Model model,HttpServletRequest request){
		model.addAttribute("employeecode",request.getParameter("employeecode"));
		model.addAttribute("employeeType",request.getParameter("employeeType"));
		return "/report/nbreporta2";
	}
	@RequestMapping("/a3")
	private String a3(Model model,HttpServletRequest request){
		model.addAttribute("employeecode",request.getParameter("employeecode"));
		model.addAttribute("employeeType",request.getParameter("employeeType"));
		return "/report/nbreporta3";
	}
	@RequestMapping("/b")
	private String b(Model model,HttpServletRequest request){
		model.addAttribute("employeecode",request.getParameter("employeecode"));
		model.addAttribute("employeeType",request.getParameter("employeeType"));
		return "/report/nbreportb";
	}
	
	
	/**
	 * @see 利润报表查询（a1方式）
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
			//获取当前用户的占成
			Enterprise enterprise = enterpriseService.selectByPrimaryKey(loginEmployee.getEnterprisecode());
			double curren_share = loginEmployee.getShare().doubleValue();
			double Depositrate = Double.valueOf(enterprise.getDepositrate());
			double Takerate = Double.valueOf(enterprise.getTakerate());
			
			
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
					
					double but_money = StringUtil.getDouble(paramObj.get("but_money"));
					double net_money = StringUtil.getDouble(paramObj.get("net_money"));
					double rebates_cash = StringUtil.getDouble(paramObj.get("rebates_cash")) ;//游戏输赢
					double activity_money = StringUtil.getDouble(paramObj.get("activity_money"));
					double depositMoney = StringUtil.getDouble(paramObj.get("depositMoney"));
					double withdrawMoney = StringUtil.getDouble(paramObj.get("withdrawMoney"));
					
					
					//存款手续费
					double dividend = MoneyHelper.moneyFormatDouble( depositMoney * Depositrate);
					//取款手续费
					double share = MoneyHelper.moneyFormatDouble( withdrawMoney * Takerate );
					
					//代理结算额  = 使用：占成代理结算方式一
					//公式=游戏输赢 * 公司占成 + 手续费 + 优惠 + 洗码；
					double profit_amount = MoneyHelper.moneyFormatDouble( net_money * curren_share  + dividend + share + activity_money + rebates_cash);
					
					item.put("game_betting_amount", but_money);
					item.put("activity_money", activity_money);//优惠额
					item.put("lose_win_amount", net_money);
					item.put("bonus", rebates_cash);//洗码额
					item.put("countMember", 0);
					item.put("countAgent", 0);
					item.put("countEmployee", 0);
					item.put("depositMoney", depositMoney);//存款额
					item.put("withdrawMoney", withdrawMoney);//取款额
					item.put("remark", "会员个人汇总");
					
					item.put("dividend", dividend);
					item.put("share", share);
					item.put("profit_amount", profit_amount);
					
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
					
					double but_money = StringUtil.getDouble(paramObj.get("but_money"));
					double net_money = StringUtil.getDouble(paramObj.get("net_money"));
					double rebates_cash = StringUtil.getDouble(paramObj.get("rebates_cash")) ;//游戏输赢
					double activity_money = StringUtil.getDouble(paramObj.get("activity_money"));
					double depositMoney = StringUtil.getDouble(paramObj.get("depositMoney"));
					double withdrawMoney = StringUtil.getDouble(paramObj.get("withdrawMoney"));
					
					
					//存款手续费
					double dividend = MoneyHelper.moneyFormatDouble( depositMoney * Depositrate);
					//取款手续费
					double share = MoneyHelper.moneyFormatDouble( withdrawMoney * Takerate );
					
					//代理结算额  = 使用：占成代理结算方式一
					//公式=游戏输赢 * 公司占成 + 手续费 + 优惠 + 洗码；
					double profit_amount = MoneyHelper.moneyFormatDouble( net_money * curren_share  + dividend + share + activity_money + rebates_cash);
					
					item.put("game_betting_amount", but_money);
					item.put("activity_money", activity_money);
					item.put("lose_win_amount", net_money);
					item.put("bonus", rebates_cash);
					item.put("countMember", paramObj.get("countMember"));
					item.put("countAgent", paramObj.get("countAgent"));
					item.put("countEmployee", paramObj.get("countEmployee"));
					item.put("dividend", dividend);
					item.put("share", share);
					item.put("profit_amount", profit_amount);
					item.put("depositMoney", depositMoney);
					item.put("withdrawMoney", withdrawMoney);
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
					
					double but_money = StringUtil.getDouble(paramObj.get("but_money"));
					double net_money = StringUtil.getDouble(paramObj.get("net_money"));
					double rebates_cash = StringUtil.getDouble(paramObj.get("rebates_cash")) ;//游戏输赢
					double activity_money = StringUtil.getDouble(paramObj.get("activity_money"));
					double depositMoney = StringUtil.getDouble(paramObj.get("depositMoney"));
					double withdrawMoney = StringUtil.getDouble(paramObj.get("withdrawMoney"));
					
					
					//存款手续费
					double dividend = MoneyHelper.moneyFormatDouble( depositMoney * Depositrate);
					//取款手续费
					double share = MoneyHelper.moneyFormatDouble( withdrawMoney * Takerate );
					
					//代理结算额  = 使用：占成代理结算方式一
					//公式=游戏输赢 * 公司占成 + 手续费 + 优惠 + 洗码；
					double profit_amount = MoneyHelper.moneyFormatDouble( net_money * curren_share  + dividend + share + activity_money + rebates_cash);
					
					item.put("game_betting_amount", but_money);
					item.put("activity_money", activity_money);
					item.put("lose_win_amount", net_money);
					item.put("bonus", rebates_cash);
					item.put("countMember", paramObj.get("countMember"));
					item.put("countAgent", 0);
					item.put("countEmployee", 0);
					item.put("dividend", dividend);
					item.put("share", share);
					item.put("profit_amount", profit_amount);
					item.put("depositMoney", depositMoney);
					item.put("withdrawMoney", withdrawMoney);
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
	 * @see 利润报表查询（a2方式）
	 * @param session
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/queryUserProfitNew2")
	@ResponseBody
	public Map<String, Object> queryUserProfitNew2(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			//获取当前用户的占成
			Enterprise enterprise = enterpriseService.selectByPrimaryKey(loginEmployee.getEnterprisecode());
			double curren_share = loginEmployee.getShare().doubleValue();
			double Depositrate = Double.valueOf(enterprise.getDepositrate());
			double Takerate = Double.valueOf(enterprise.getTakerate());
			
			
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
					
					double but_money = StringUtil.getDouble(paramObj.get("but_money"));
					double net_money = StringUtil.getDouble(paramObj.get("net_money"));
					double rebates_cash = StringUtil.getDouble(paramObj.get("rebates_cash")) ;//游戏输赢
					double activity_money = StringUtil.getDouble(paramObj.get("activity_money"));
					double depositMoney = StringUtil.getDouble(paramObj.get("depositMoney"));
					double withdrawMoney = StringUtil.getDouble(paramObj.get("withdrawMoney"));
					
					
					//存款手续费
					double dividend = MoneyHelper.moneyFormatDouble( depositMoney * Depositrate);
					//取款手续费
					double share = MoneyHelper.moneyFormatDouble( withdrawMoney * Takerate );
					
					//代理结算额  = 使用：占成代理结算方式二
					//公式=（游戏输赢-手续费-优惠-洗码）* 代理占成；
					double profit_amount = MoneyHelper.moneyFormatDouble( Math.abs(net_money) - dividend - share - activity_money - rebates_cash) * curren_share;
					
					if(net_money > 0) {//如果是赢钱，大家都不用出钱
						profit_amount = 0;
					} else {
						profit_amount = 0 - profit_amount;
					}
					
					item.put("game_betting_amount", but_money);
					item.put("activity_money", activity_money);//优惠额
					item.put("lose_win_amount", net_money);
					item.put("bonus", rebates_cash);//洗码额
					item.put("countMember", 0);
					item.put("countAgent", 0);
					item.put("countEmployee", 0);
					item.put("depositMoney", depositMoney);//存款额
					item.put("withdrawMoney", withdrawMoney);//取款额
					item.put("remark", "会员个人汇总");
					
					item.put("dividend", dividend);
					item.put("share", share);
					item.put("profit_amount", profit_amount);
					
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
					
					double but_money = StringUtil.getDouble(paramObj.get("but_money"));
					double net_money = StringUtil.getDouble(paramObj.get("net_money"));
					double rebates_cash = StringUtil.getDouble(paramObj.get("rebates_cash")) ;//游戏输赢
					double activity_money = StringUtil.getDouble(paramObj.get("activity_money"));
					double depositMoney = StringUtil.getDouble(paramObj.get("depositMoney"));
					double withdrawMoney = StringUtil.getDouble(paramObj.get("withdrawMoney"));
					
					
					//存款手续费
					double dividend = MoneyHelper.moneyFormatDouble( depositMoney * Depositrate);
					//取款手续费
					double share = MoneyHelper.moneyFormatDouble( withdrawMoney * Takerate );
					
					//代理结算额  = 使用：占成代理结算方式二
					//公式=（游戏输赢-手续费-优惠-洗码）* 代理占成；
					double profit_amount = MoneyHelper.moneyFormatDouble( Math.abs(net_money) - dividend - share - activity_money - rebates_cash) * (enterpriseEmployee.getShare().doubleValue());
					
					if(net_money > 0) {//如果是赢钱，大家都不用出钱
						profit_amount = 0;
					} else {
						profit_amount = 0 - profit_amount;
					}
					
					item.put("game_betting_amount", but_money);
					item.put("activity_money", activity_money);
					item.put("lose_win_amount", net_money);
					item.put("bonus", rebates_cash);
					item.put("countMember", paramObj.get("countMember"));
					item.put("countAgent", paramObj.get("countAgent"));
					item.put("countEmployee", paramObj.get("countEmployee"));
					item.put("dividend", dividend);
					item.put("share", share);
					item.put("profit_amount", profit_amount);
					item.put("depositMoney", depositMoney);
					item.put("withdrawMoney", withdrawMoney);
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
					
					double but_money = StringUtil.getDouble(paramObj.get("but_money"));
					double net_money = StringUtil.getDouble(paramObj.get("net_money"));
					double rebates_cash = StringUtil.getDouble(paramObj.get("rebates_cash")) ;//游戏输赢
					double activity_money = StringUtil.getDouble(paramObj.get("activity_money"));
					double depositMoney = StringUtil.getDouble(paramObj.get("depositMoney"));
					double withdrawMoney = StringUtil.getDouble(paramObj.get("withdrawMoney"));
					
					
					//存款手续费
					double dividend = MoneyHelper.moneyFormatDouble( depositMoney * Depositrate);
					//取款手续费
					double share = MoneyHelper.moneyFormatDouble( withdrawMoney * Takerate );
					
					//代理结算额  = 使用：占成代理结算方式二
					//公式=（游戏输赢-手续费-优惠-洗码）* 代理占成；
					double profit_amount = MoneyHelper.moneyFormatDouble( Math.abs(net_money) - dividend - share - activity_money - rebates_cash) * curren_share;
					
					if(net_money > 0) {//如果是赢钱，大家都不用出钱
						profit_amount = 0;
					} else {
						profit_amount = 0 - profit_amount;
					}
					
					item.put("game_betting_amount", but_money);
					item.put("activity_money", activity_money);
					item.put("lose_win_amount", net_money);
					item.put("bonus", rebates_cash);
					item.put("countMember", paramObj.get("countMember"));
					item.put("countAgent", 0);
					item.put("countEmployee", 0);
					item.put("dividend", dividend);
					item.put("share", share);
					item.put("profit_amount", profit_amount);
					item.put("depositMoney", depositMoney);
					item.put("withdrawMoney", withdrawMoney);
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
	 * @see 利润报表查询（a3方式）
	 * @param session
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/queryUserProfitNewA3")
	@ResponseBody
	public Map<String, Object> queryUserProfitNewA3(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			//获取当前用户的占成
			Enterprise enterprise = enterpriseService.selectByPrimaryKey(loginEmployee.getEnterprisecode());
			double curren_share = loginEmployee.getShare().doubleValue();
			double Depositrate = Double.valueOf(enterprise.getDepositrate());
			double Takerate = Double.valueOf(enterprise.getTakerate());
			
			
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
					
					double but_money = StringUtil.getDouble(paramObj.get("but_money"));
					double net_money = StringUtil.getDouble(paramObj.get("net_money"));
					double rebates_cash = StringUtil.getDouble(paramObj.get("rebates_cash")) ;//游戏输赢
					double activity_money = StringUtil.getDouble(paramObj.get("activity_money"));
					double depositMoney = StringUtil.getDouble(paramObj.get("depositMoney"));
					double withdrawMoney = StringUtil.getDouble(paramObj.get("withdrawMoney"));
					
					
					//存款手续费
					double dividend = MoneyHelper.moneyFormatDouble( depositMoney * Depositrate);
					//取款手续费
					double share = MoneyHelper.moneyFormatDouble( withdrawMoney * Takerate );
					
					//代理结算额  = 使用：占成代理结算方式二
					//公式=（游戏输赢-手续费-优惠-洗码）* 代理占成；
					double profit_amount = MoneyHelper.moneyFormatDouble( Math.abs(net_money) - dividend - share - activity_money - rebates_cash) * curren_share;
					
					if(net_money > 0) {//如果是赢钱，大家都不用出钱
						profit_amount = 0;
					} else {
						profit_amount = 0 - profit_amount;
					}
					
					item.put("game_betting_amount", but_money);
					item.put("activity_money", activity_money);//优惠额
					item.put("lose_win_amount", net_money);
					item.put("bonus", rebates_cash);//洗码额
					item.put("countMember", 0);
					item.put("countAgent", 0);
					item.put("countEmployee", 0);
					item.put("depositMoney", depositMoney);//存款额
					item.put("withdrawMoney", withdrawMoney);//取款额
					item.put("remark", "会员个人汇总");
					
					item.put("dividend", dividend);
					item.put("share", share);
					item.put("profit_amount", profit_amount);
					
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
					
					double but_money = StringUtil.getDouble(paramObj.get("but_money"));
					double net_money = StringUtil.getDouble(paramObj.get("net_money"));
					double rebates_cash = StringUtil.getDouble(paramObj.get("rebates_cash")) ;//游戏输赢
					double activity_money = StringUtil.getDouble(paramObj.get("activity_money"));
					double depositMoney = StringUtil.getDouble(paramObj.get("depositMoney"));
					double withdrawMoney = StringUtil.getDouble(paramObj.get("withdrawMoney"));
					
					
					//存款手续费
					double dividend = MoneyHelper.moneyFormatDouble( depositMoney * Depositrate);
					//取款手续费
					double share = MoneyHelper.moneyFormatDouble( withdrawMoney * Takerate );
					
					//代理结算额  = 使用：占成代理结算方式二
					//公式=（游戏输赢-手续费-优惠-洗码）* 代理占成；
					double profit_amount = MoneyHelper.moneyFormatDouble( Math.abs(net_money) - dividend - share - activity_money - rebates_cash) * (enterpriseEmployee.getShare().doubleValue());
					
					if(net_money > 0) {//如果是赢钱，大家都不用出钱
						profit_amount = 0;
					} else {
						profit_amount = 0 - profit_amount;
					}
					
					item.put("game_betting_amount", but_money);
					item.put("activity_money", activity_money);
					item.put("lose_win_amount", net_money);
					item.put("bonus", rebates_cash);
					item.put("countMember", paramObj.get("countMember"));
					item.put("countAgent", paramObj.get("countAgent"));
					item.put("countEmployee", paramObj.get("countEmployee"));
					item.put("dividend", dividend);
					item.put("share", share);
					item.put("profit_amount", profit_amount);
					item.put("depositMoney", depositMoney);
					item.put("withdrawMoney", withdrawMoney);
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
					
					double but_money = StringUtil.getDouble(paramObj.get("but_money"));
					double net_money = StringUtil.getDouble(paramObj.get("net_money"));
					double rebates_cash = StringUtil.getDouble(paramObj.get("rebates_cash")) ;//游戏输赢
					double activity_money = StringUtil.getDouble(paramObj.get("activity_money"));
					double depositMoney = StringUtil.getDouble(paramObj.get("depositMoney"));
					double withdrawMoney = StringUtil.getDouble(paramObj.get("withdrawMoney"));
					
					
					//存款手续费
					double dividend = MoneyHelper.moneyFormatDouble( depositMoney * Depositrate);
					//取款手续费
					double share = MoneyHelper.moneyFormatDouble( withdrawMoney * Takerate );
					
					//代理结算额  = 使用：占成代理结算方式二
					//公式=（游戏输赢-手续费-优惠-洗码）* 代理占成；
					double profit_amount = MoneyHelper.moneyFormatDouble( Math.abs(net_money) - dividend - share - activity_money - rebates_cash) * curren_share;
					
					if(net_money > 0) {//如果是赢钱，大家都不用出钱
						profit_amount = 0;
					} else {
						profit_amount = 0 - profit_amount;
					}
					
					item.put("game_betting_amount", but_money);
					item.put("activity_money", activity_money);
					item.put("lose_win_amount", net_money);
					item.put("bonus", rebates_cash);
					item.put("countMember", paramObj.get("countMember"));
					item.put("countAgent", 0);
					item.put("countEmployee", 0);
					item.put("dividend", dividend);
					item.put("share", share);
					item.put("profit_amount", profit_amount);
					item.put("depositMoney", depositMoney);
					item.put("withdrawMoney", withdrawMoney);
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
	 * @see 利润报表查询（现金代b方式）
	 * @param session
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/queryUserProfitNew3")
	@ResponseBody
	public Map<String, Object> queryUserProfitNew3(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			//获取当前用户的占成
			Enterprise enterprise = enterpriseService.selectByPrimaryKey(loginEmployee.getEnterprisecode());
			double curren_share = loginEmployee.getShare().doubleValue();
			double Depositrate = Double.valueOf(enterprise.getDepositrate());
			double Takerate = Double.valueOf(enterprise.getTakerate());
			
			
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
					
					double but_money = StringUtil.getDouble(paramObj.get("but_money"));
					double net_money = StringUtil.getDouble(paramObj.get("net_money"));
					double rebates_cash = StringUtil.getDouble(paramObj.get("rebates_cash")) ;//游戏输赢
					double activity_money = StringUtil.getDouble(paramObj.get("activity_money"));
					double depositMoney = StringUtil.getDouble(paramObj.get("depositMoney"));
					double withdrawMoney = StringUtil.getDouble(paramObj.get("withdrawMoney"));
					
					
					//存款手续费
					double dividend = MoneyHelper.moneyFormatDouble( depositMoney * Depositrate);
					//取款手续费
					double share = MoneyHelper.moneyFormatDouble( withdrawMoney * Takerate );
					
					//代理结算额  = 使用：占成代理结算方式二
					//公式=（游戏输赢-手续费-优惠-洗码）* 代理占成；
					double profit_amount = MoneyHelper.moneyFormatDouble( net_money - dividend - share - activity_money - rebates_cash) * curren_share;
					
					item.put("game_betting_amount", but_money);
					item.put("activity_money", activity_money);//优惠额
					item.put("lose_win_amount", net_money);
					item.put("bonus", rebates_cash);//洗码额
					item.put("countMember", 0);
					item.put("countAgent", 0);
					item.put("countEmployee", 0);
					item.put("depositMoney", depositMoney);//存款额
					item.put("withdrawMoney", withdrawMoney);//取款额
					item.put("remark", "会员个人汇总");
					
					item.put("dividend", dividend);
					item.put("share", share);
					item.put("profit_amount", profit_amount);
					
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
					
					double but_money = StringUtil.getDouble(paramObj.get("but_money"));
					double net_money = StringUtil.getDouble(paramObj.get("net_money"));
					double rebates_cash = StringUtil.getDouble(paramObj.get("rebates_cash")) ;//游戏输赢
					double activity_money = StringUtil.getDouble(paramObj.get("activity_money"));
					double depositMoney = StringUtil.getDouble(paramObj.get("depositMoney"));
					double withdrawMoney = StringUtil.getDouble(paramObj.get("withdrawMoney"));
					
					
					//存款手续费
					double dividend = MoneyHelper.moneyFormatDouble( depositMoney * Depositrate);
					//取款手续费
					double share = MoneyHelper.moneyFormatDouble( withdrawMoney * Takerate );
					
					//代理结算额  = 使用：占成代理结算方式二
					//公式=（游戏输赢-手续费-优惠-洗码）* 代理占成；
					double profit_amount = MoneyHelper.moneyFormatDouble( net_money - dividend - share - activity_money - rebates_cash) * curren_share;
					
					item.put("game_betting_amount", but_money);
					item.put("activity_money", activity_money);
					item.put("lose_win_amount", net_money);
					item.put("bonus", rebates_cash);
					item.put("countMember", paramObj.get("countMember"));
					item.put("countAgent", paramObj.get("countAgent"));
					item.put("countEmployee", paramObj.get("countEmployee"));
					item.put("dividend", dividend);
					item.put("share", share);
					item.put("profit_amount", profit_amount);
					item.put("depositMoney", depositMoney);
					item.put("withdrawMoney", withdrawMoney);
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
					
					double but_money = StringUtil.getDouble(paramObj.get("but_money"));
					double net_money = StringUtil.getDouble(paramObj.get("net_money"));
					double rebates_cash = StringUtil.getDouble(paramObj.get("rebates_cash")) ;//游戏输赢
					double activity_money = StringUtil.getDouble(paramObj.get("activity_money"));
					double depositMoney = StringUtil.getDouble(paramObj.get("depositMoney"));
					double withdrawMoney = StringUtil.getDouble(paramObj.get("withdrawMoney"));
					
					
					//存款手续费
					double dividend = MoneyHelper.moneyFormatDouble( depositMoney * Depositrate);
					//取款手续费
					double share = MoneyHelper.moneyFormatDouble( withdrawMoney * Takerate );
					
					//代理结算额  = 使用：占成代理结算方式二
					//公式=（游戏输赢-手续费-优惠-洗码）* 代理占成；
					double profit_amount = MoneyHelper.moneyFormatDouble( net_money - dividend - share - activity_money - rebates_cash) * curren_share;
					
					item.put("game_betting_amount", but_money);
					item.put("activity_money", activity_money);
					item.put("lose_win_amount", net_money);
					item.put("bonus", rebates_cash);
					item.put("countMember", paramObj.get("countMember"));
					item.put("countAgent", 0);
					item.put("countEmployee", 0);
					item.put("dividend", dividend);
					item.put("share", share);
					item.put("profit_amount", profit_amount);
					item.put("depositMoney", depositMoney);
					item.put("withdrawMoney", withdrawMoney);
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
	
	@Override
	public LoggerManager getLogger() {
		return null;
	}
}
