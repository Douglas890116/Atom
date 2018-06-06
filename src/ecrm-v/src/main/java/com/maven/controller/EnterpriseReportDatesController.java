package com.maven.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

import com.hy.pull.common.util.DateUtil;
import com.maven.cache.SystemCache;
import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.entity.BettingAllDay;
import com.maven.entity.BettingAllGameWinloseDetail;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseReportDates;
import com.maven.entity.Game;
import com.maven.game.enums.GameEnum.Enum_GameType;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.BettingAllDayService;
import com.maven.service.BettingAllGameWinloseDetailService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseReportDatesService;
import com.maven.service.EnterpriseService;
import com.maven.util.GameUtils;
import com.maven.utils.StringUtil;

@Controller
@RequestMapping("/EnterpriseReportDates")
public class EnterpriseReportDatesController extends BaseController {

	private static LoggerManager log = LoggerManager.getLogger(BettingHqHqController.class.getName(),
			OutputManager.LOG_BETTINGALLDAY);
	@Autowired
	private EnterpriseReportDatesService enterpriseReportDatesService;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	/**
	 * index
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, HttpSession session, Model model) {
		
		try {
			//获取7天内的日期
			Calendar ca = Calendar.getInstance();
			ca.add(Calendar.DATE, -7);
			String startDate = sdf.format(ca.getTime());
			model.addAttribute("startDate", startDate);
			
			String endDate = sdf.format(new Date());
			model.addAttribute("endDate", endDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/enterprise/reportDates";
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

			List<EnterpriseReportDates> betrecords = enterpriseReportDatesService.selectBetRecord(parameter);
			if(!parameter.containsKey("enterprisecode")) {
				for (EnterpriseReportDates enterpriseReportDates : betrecords) {
					enterpriseReportDates.setEnterprisecode(enterpriseReportDates.getReportdate()+"="+enterpriseReportDates.getEnterprisecode());
				}
			} else {
				for (EnterpriseReportDates enterpriseReportDates : betrecords) {
					enterpriseReportDates.setEnterprisecode(enterpriseReportDates.getReportdate().toString());
				}
			}
			Map<String, Object> result = enterpriseReportDatesService.selectBetRecordCountMoney(parameter);
			int countRecord = StringUtil.getInt(result.get("count"));
			Map<String, Object> data = super.formatPagaMap(betrecords, countRecord);
			
			
			Map<String, Object> summary = new HashMap<String, Object>();
			summary.put("memberRegeditCount", StringUtil.getDouble(result.get("memberRegeditCount")));
			summary.put("agentRegeditCount", StringUtil.getDouble(result.get("agentRegeditCount")));
			summary.put("loginCount", StringUtil.getDouble(result.get("loginCount")));
			
			summary.put("firstDepositCount", StringUtil.getDouble(result.get("firstDepositCount")));
			summary.put("firstDepositMoney", StringUtil.getDouble(result.get("firstDepositMoney")));
			summary.put("secondDepositCount", StringUtil.getDouble(result.get("secondDepositCount")));
			summary.put("secondDepositMoney", StringUtil.getDouble(result.get("secondDepositMoney")));
			
			summary.put("todayDepositCount", StringUtil.getDouble(result.get("todayDepositCount")));
			summary.put("todayDepositCount1", StringUtil.getDouble(result.get("todayDepositCount1")));
			summary.put("todayDepositMoney", StringUtil.getDouble(result.get("todayDepositMoney")));
			
			summary.put("todayTakeCount", StringUtil.getDouble(result.get("todayTakeCount")));
			summary.put("todayTakeCount1", StringUtil.getDouble(result.get("todayTakeCount1")));
			summary.put("todayTakeMoney", StringUtil.getDouble(result.get("todayTakeMoney")));
			
			summary.put("todayBetmoney", StringUtil.getDouble(result.get("todayBetmoney")));
			summary.put("todayNetmoney", StringUtil.getDouble(result.get("todayNetmoney")));
			summary.put("todayVaildmoney", StringUtil.getDouble(result.get("todayVaildmoney")));
			
			summary.put("todayProsmoney", StringUtil.getDouble(result.get("todayProsmoney")));
			summary.put("todayConsmoney", StringUtil.getDouble(result.get("todayConsmoney")));
			summary.put("todayPreferentialCount", StringUtil.getDouble(result.get("todayPreferentialCount")));
			summary.put("todayPreferentialMoney", StringUtil.getDouble(result.get("todayPreferentialMoney")));
			
			summary.put("todayWashCount", StringUtil.getDouble(result.get("todayWashCount")));
			summary.put("todayWashMoney", StringUtil.getDouble(result.get("todayWashMoney")));
			data.put("summary", summary);
			
			return data;
			
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}

	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}