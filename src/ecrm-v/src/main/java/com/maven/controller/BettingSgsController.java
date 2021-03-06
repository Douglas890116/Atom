package com.maven.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.entity.BettingSgs;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseEmployee;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.BettingGameService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseService;
import com.maven.util.SGSUtils;
import com.maven.utils.StringUtil;

@Controller
@RequestMapping("/sgsgame")
public class BettingSgsController extends BaseController {
	private static LoggerManager log = LoggerManager.getLogger(BettingHqXcpController.class.getName(),
			OutputManager.LOG_BETTINGHQXCP);

	@Resource
	private EnterpriseEmployeeService employeeService;
	private static List<Enterprise> listEnterprise;
	@Resource
	private EnterpriseService enterpriseService;
	
	@Resource(name = "bettingSgsServiceImpl")
	private BettingGameService<BettingSgs> bettingGameService;

	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model model) {
		super.assembleEnterprise(request.getSession(), listEnterprise, enterpriseService, model, request);
		model.addAttribute("employeecode", request.getParameter("employeecode"));
		return "/gamerecord/sgsgame";
	}

	@RequestMapping(value = { "/data" })
	@ResponseBody
	public Map<String, Object> data(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			Map<String, Object> object = getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);

			/********* 非超级管理员时只能查询本团队内的数据 ************/
			if (!SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())) {
				super.dataLimits(ee, object, session);
			}

			super.assembleParent(object, session, ee);
			List<BettingSgs> list = bettingGameService.takeRecord(object);
			if (null != list && list.size() > 0) {
				String gameId = null;
				for (BettingSgs bettingSgs : list) {
					gameId = bettingSgs.getGameid();
					bettingSgs.setGamename(SGSUtils.getGameName(gameId));
					gameId = null;
				}
			}
			Map<String, Object> result = bettingGameService.takeRecordCountMoney(object);
			int countRecord = StringUtil.getInt(result.get("count"));

			Map<String, Object> data = super.formatPagaMap(list, countRecord);
			Map<String, Object> summary = new HashMap<String, Object>();
			summary.put("betmoney", StringUtil.getDouble(result.get("betmoney")));
			summary.put("netmoney", StringUtil.getDouble(result.get("netmoney")));
			summary.put("postbal", StringUtil.getDouble(result.get("postbal")));
			data.put("summary", summary);

			return data;

		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 数据导出Excel
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/exportExcel")
	public String exportExcel(HttpServletRequest request, HttpSession session, Model model) {
		try {
			Map<String, Object> object = getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);

			/********* 非超级管理员时只能查询本团队内的数据 ************/
			if (!SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())) {
				super.dataLimits(ee, object, session);
			}
			List<BettingSgs> list = bettingGameService.takeRecord(object);
			if (null != list && list.size() > 0) {
				String gameId = null;
				for (int i = 0; i < list.size(); i++) {
					gameId = list.get(i).getGameid();
					list.get(i).setGamename(SGSUtils.getGameName(gameId));
					gameId = null;
				}
			}
			model.addAttribute("listData", list);
			model.addAttribute("title", "SGS申博游戏报表数据");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "/gamerecord/sgsgameexcel";
	}

	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
