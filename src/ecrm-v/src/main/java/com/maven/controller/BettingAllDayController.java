package com.maven.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.cache.SystemCache;
import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.entity.BettingAllDay;
import com.maven.entity.BettingAllGameWinloseDetail;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.Game;
import com.maven.game.enums.GameEnum.Enum_GameType;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.BettingAllDayService;
import com.maven.service.BettingAllGameWinloseDetailService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseService;
import com.maven.util.GameUtils;
import com.maven.utils.StringUtil;

@Controller
@RequestMapping("/bettingallday")
public class BettingAllDayController extends BaseController {

	private static LoggerManager log = LoggerManager.getLogger(BettingHqHqController.class.getName(),
			OutputManager.LOG_BETTINGALLDAY);
	@Autowired
	private BettingAllGameWinloseDetailService winloseService;
	@Autowired
	private BettingAllDayService bettingAllDayService;
	@Autowired
	private EnterpriseService enterpriseService;
	@Autowired
	private EnterpriseEmployeeService employeeService;
	
	private static List<Enterprise> listEnterprise;
	/**
	 * index
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/index")
	@SuppressWarnings("unchecked")
	public String betrecordIndex(HttpServletRequest request, HttpSession session, Model model) {
		try {
			List<Game> listGame = (List<Game>) session.getAttribute(Constant.ENTERPRISE_GAMES);
			model.addAttribute("listGame", listGame);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/bettingallday/index";
	}

	/**
	 * list页面请求数据
	 * 
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/data")
	@ResponseBody
	public Map<String, Object> data(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> parameter = getRequestParamters(request);

			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);

			/********* 非超级管理员时只能查询本团队内的数据 ************/
			if (!SystemConstant.getProperties("admin.employeecode").equals(loginEmployee.getEmployeecode())) {
				parameter.put("enterprisecode", loginEmployee.getEnterprisecode());
			}

			List<BettingAllDay> betrecords = bettingAllDayService.selectForPage(parameter);
			if(null != betrecords && betrecords.size() > 0) {
				String gamePlatform = null;
				for (int i = 0; i < betrecords.size(); i++) {
					gamePlatform = betrecords.get(i).getGamePlatform();
					betrecords.get(i).setGamePlatform(GameUtils.getGameTypeNameByGameType(gamePlatform));
					gamePlatform = null;
				}
			}
			int count = bettingAllDayService.selectForPageCount(parameter);
			return super.formatPagaMap(betrecords, count);
			/*
			 * Map<String, Object> result =
			 * bettingAllDayService.selectForPageCount(parameter); int
			 * countRecord = StringUtil.getInt(result.get("count"));
			 * 
			 * Map<String, Object> data = super.formatPagaMap(betrecords,
			 * countRecord); Map<String, Object> summary = new HashMap<String,
			 * Object>(); summary.put("mustbet",
			 * StringUtil.getDouble(result.get("mustbet")));
			 * summary.put("alreadybet",
			 * StringUtil.getDouble(result.get("alreadybet")));
			 * data.put("summary", summary);
			 * 
			 * return data;
			 */
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 会员游戏总会
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/memberGameReport")
	@SuppressWarnings("unchecked")
	public String memberGameReport(HttpServletRequest request, HttpSession session, Model model) {
		try {
			List<Game> listGame = (List<Game>) session.getAttribute(Constant.ENTERPRISE_GAMES);
			model.addAttribute("listGame", listGame);
		} catch (Exception e) {
			log.Debug("BettingAllDayController.memberGameReport会员游戏汇总报错：", e);
		}
		return "/bettingallday/memberGameReport";
	}

	/**
	 * 会员游戏总会报表数据
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/report")
	@ResponseBody
	public Map<String, Object> report(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> parameters = this.getRequestParamters(request);
			/********* 非超级管理员时只能查询本团队内的数据 ************/
			if (!SystemConstant.getProperties("admin.employeecode").equals(loginEmployee.getEmployeecode())){
				parameters.put("enterprisecode", loginEmployee.getEnterprisecode());
			}
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
			
			
			
			
			
			String gamePlatform = (String) parameters.get("gamePlatform");
			if (StringUtils.isNotBlank(gamePlatform) && Enum_GameType.validate(gamePlatform)) {
				for (Enum_GameType game : Enum_GameType.values()) {
					if (gamePlatform.equals(game.gametype)) {
						parameters.put("platformtype", game.bettingcode);
					}
				}
				parameters.remove("gamePlatform");
			}
			List<BettingAllGameWinloseDetail> list = winloseService.selectForPage(parameters);
			if(null != list && list.size() > 0) {
				String platformType = null;
				String gameBigType  = null;
				for (int i = 0; i < list.size(); i++) {
					platformType = list.get(i).getPlatformtype();
					gameBigType  = list.get(i).getGamebigtype();
					list.get(i).setPlatformtype(GameUtils.getGameTypeNameBybettingCode(platformType));
					list.get(i).setGamebigtype(GameUtils.getGameBigTypeName(gameBigType));
				}
			}
			Map<String, Object> result = winloseService.takeRecordCountMoney(parameters);
			int countRecord = StringUtil.getInt(result.get("count"));

			Map<String, Object> data = super.formatPagaMap(list, countRecord);
			Map<String, Object> summary = new HashMap<String, Object>();
			summary.put("betmoney", StringUtil.getDouble(result.get("betmoney")));
			summary.put("validbet", StringUtil.getDouble(result.get("validMoney")));
			summary.put("netmoney", StringUtil.getDouble(result.get("netMoney")));
			data.put("summary", summary);
			return data;
		} catch (Exception e) {
			log.Error("会员游戏总会报表数据", e);
		}
		return null;
	}
	@RequestMapping("export")
	public String export(HttpServletRequest request, HttpSession session, Model model) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> parameters = this.getRequestParamters(request);

			/********* 非超级管理员时只能查询本团队内的数据 ************/
			if (!SystemConstant.getProperties("admin.employeecode").equals(loginEmployee.getEmployeecode())) {
				parameters.put("enterprisecode", loginEmployee.getEnterprisecode());
			}
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

			
			
			String gamePlatform = (String) parameters.get("gamePlatform");
			if (StringUtils.isNotBlank(gamePlatform) && Enum_GameType.validate(gamePlatform)) {
				for (Enum_GameType game : Enum_GameType.values()) {
					if (gamePlatform.equals(game.gametype)) {
						parameters.put("platformtype", game.bettingcode);
					}
				}
				parameters.remove("gamePlatform");
			}
			List<BettingAllGameWinloseDetail> list = winloseService.selectForPage(parameters);
			if(null != list && list.size() > 0) {
				String platformType = null;
				String gameBigType  = null;
				for (int i = 0; i < list.size(); i++) {
					platformType = list.get(i).getPlatformtype();
					gameBigType  = list.get(i).getGamebigtype();
					list.get(i).setPlatformtype(GameUtils.getGameTypeNameBybettingCode(platformType));
					list.get(i).setGamebigtype(GameUtils.getGameBigTypeName(gameBigType));
				}
			}
			model.addAttribute("listData", list);
			model.addAttribute("title", "会员游戏总汇报表");
		} catch (Exception e) {
			log.Error("导出会员游戏总汇出错！", e);
		}
		return "/bettingallday/memberGameExcel";
	}
	
	
	/**
	 * 会员日报表
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/dayreport")
	public String dayreport(HttpServletRequest request, HttpSession session, Model model) {
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			List<Game> listGame = (List<Game>) session.getAttribute(Constant.ENTERPRISE_GAMES);
			model.addAttribute("listGame", listGame);
			
			if ( SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())) {
				if (listEnterprise == null || listEnterprise.size() == 0) {
					listEnterprise = enterpriseService.selectAll(new HashMap<String, Object>());
				}
				model.addAttribute("listEnterprise", listEnterprise);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/bettingallday/day_report";
	}

	/**
	 * list页面请求数据
	 * 
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/dayreportData")
	@ResponseBody
	public Map<String, Object> dayreportData(HttpServletRequest request, HttpSession session, Model model) {
		try {
			Map<String, Object> parameter = getRequestParamters(request);

			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);

			/********* 非超级管理员时只能查询本团队内的数据 ************/
//			boolean isadmin = false;
			if ( !SystemConstant.getProperties("admin.employeecode").equals(loginEmployee.getEmployeecode())) {
				parameter.put("enterprisecode", loginEmployee.getEnterprisecode());
			} 
//			else {
//				//
//				isadmin = true;
//			}
			
			
			if(parameter.get("loginaccount") == null || parameter.get("loginaccount").equals("null")) {
				parameter.put("loginaccount", null);
			}
			if(parameter.get("gametype") == null || parameter.get("gametype").equals("null")) {
				parameter.put("gametype", null);
			}
			if(parameter.get("startDate") == null || parameter.get("startDate").equals("null")) {
				parameter.put("startDate", 0);
			}
			if(parameter.get("endDate") == null || parameter.get("endDate").equals("null")) {
				parameter.put("endDate", 0);
			}
			parameter.put("out_count", 0);
			
			List<BettingAllGameWinloseDetail> list = winloseService.call_uspUserDayReport(parameter);
			for (BettingAllGameWinloseDetail data : list) {
				Enum_GameType __tt = Enum_GameType.getByGametype(data.getPlatformtype());
				EmployeeApiAccout __account = SystemCache.getInstance().getEmployeeGameAccount(data.getEmployeecode(), data.getPlatformtype());
				if(__account != null ) {
					data.setLoginaccount(__account.getLoginaccount());
					data.setBrandcode(__account.getGameaccount());//放会员的游戏账号
				}
				data.setPlatformtype(__tt.name);
				
			}
			int count = 0;
			if(parameter.get("out_count") != null && !parameter.get("out_count").toString().equals("null")) {
				count = Integer.valueOf(parameter.get("out_count").toString());
			}
			
			return super.formatPagaMap(list, count);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * list页面请求数据
	 * 
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/dayreportExcel")
	public String dayreportExcel(HttpServletRequest request, HttpSession session, Model model) {
		try {
			Map<String, Object> parameter = getRequestParamters(request);

			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);

			/********* 非超级管理员时只能查询本团队内的数据 ************/
			if (!SystemConstant.getProperties("admin.employeecode").equals(loginEmployee.getEmployeecode())) {
				parameter.put("enterprisecode", loginEmployee.getEnterprisecode());
			} else {
				//
			}
			parameter.put("enterprisecode", loginEmployee.getEnterprisecode());
			
			if(parameter.get("loginaccount") == null || parameter.get("loginaccount").equals("null")) {
				parameter.put("loginaccount", null);
			}
			if(parameter.get("gametype") == null || parameter.get("gametype").equals("null")) {
				parameter.put("gametype", null);
			}
			if(parameter.get("startDate") == null || parameter.get("startDate").equals("null")) {
				parameter.put("startDate", 0);
			}
			if(parameter.get("endDate") == null || parameter.get("endDate").equals("null")) {
				parameter.put("endDate", 0);
			}
			parameter.put("start", 0);
			parameter.put("limit", 1000);
			
			double total_betmoney = 0;
			double total_validbet = 0;
			double total_netmoney = 0;
			int total_count = 0;
			
			List<BettingAllGameWinloseDetail> list = winloseService.call_uspUserDayReport(parameter);
			if (list != null && list.size() > 0) {
				for (BettingAllGameWinloseDetail data : list) {
					Enum_GameType __tt = Enum_GameType.getByGametype(data.getPlatformtype());
					EmployeeApiAccout __account = SystemCache.getInstance()
							.getEmployeeGameAccount(data.getEmployeecode(), data.getPlatformtype());
					if (__account != null) {
						data.setLoginaccount(__account.getLoginaccount());
						data.setBrandcode(__account.getGameaccount());//放会员的游戏账号
					}
					data.setPlatformtype(__tt.name);

					total_betmoney += data.getBetmoney();
					total_validbet += data.getValidbet();
					total_netmoney += data.getNetmoney();
					total_count += data.getCnt();
				} 
			}
			model.addAttribute("list", list);
			model.addAttribute("size", list == null ? 0 : list.size());
			model.addAttribute("title", "会员游戏日报表");
			
			model.addAttribute("total_betmoney", total_betmoney);
			model.addAttribute("total_validbet", total_validbet);
			model.addAttribute("total_netmoney", total_netmoney);
			model.addAttribute("total_count", total_count);
			
			return "/bettingallday/day_report_excel";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "";
	}
	
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}