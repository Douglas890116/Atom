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
import com.maven.entity.BettingHqBbin;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseEmployee;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.BettingGameService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseService;
import com.maven.util.BBINUtils;
import com.maven.utils.StringUtil;

@RequestMapping("/BBINGame")
@Controller
public class BettingHqBbinController extends BaseController {
	
	private static LoggerManager log = LoggerManager.getLogger(
			BettingHqBbinController.class.getName(), OutputManager.LOG_BETTINGHQBBIN);

	@Resource
	private EnterpriseEmployeeService employeeService;
	private static List<Enterprise> listEnterprise;
	@Resource
	private EnterpriseService enterpriseService;
	
	@Resource(name="bettingHqBbinServiceImpl")
	private BettingGameService<BettingHqBbin> bettingGameService;

	@RequestMapping("/list")
	public String list(HttpServletRequest request,Model model){
		super.assembleEnterprise(request.getSession(), listEnterprise, enterpriseService, model, request);
		model.addAttribute("employeecode", request.getParameter("employeecode"));
		return "/gamerecord/bbingame";
	}
	
	@RequestMapping("/data")
	@ResponseBody
	public Map<String,Object> data(HttpServletRequest request , HttpServletResponse response , HttpSession session){
		try {
			Map<String,Object> object = getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			
			/*********非超级管理员时只能查询本团队内的数据************/
			if(!SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())){
				super.dataLimits(ee, object,session);
			}
			super.assembleParent(object, session, ee);
			List<BettingHqBbin> list = bettingGameService.takeRecord(object);
			String gameType;
			for (BettingHqBbin bettingHqBbin : list) {
				gameType = bettingHqBbin.getBbinGameType();
				bettingHqBbin.setBbinGameType(BBINUtils.getGameName(gameType));
				if(gameType != null) {
					bettingHqBbin.setBbinWagerDetail(BBINUtils.getBbinWagerDetail(bettingHqBbin.getBbinWagerDetail(), gameType));
				}
			}
			
			Map<String, Object> result = bettingGameService.takeRecordCountMoney(object);
			int countRecord = StringUtil.getInt(result.get("count"));
			
			Map<String, Object> data = super.formatPagaMap(list, countRecord);
			Map<String, Object> summary = new HashMap<String, Object>();
			summary.put("bbinBetAmount", StringUtil.getDouble(result.get("bbinBetAmount")));
			summary.put("bbinPayoff", StringUtil.getDouble(result.get("bbinPayoff")));
			summary.put("bbinCommissionable", StringUtil.getDouble(result.get("bbinCommissionable")));
			data.put("summary", summary);
			
			return data;
			
//			int count = bettingGameService.takeRecordCount(object);
//			return super.formatPagaMap(list, count);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 讲数据导出Excel
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/exportExcel")
	public String exportExcel(HttpServletRequest request, HttpSession session, Model model){
		try {
			Map<String,Object> object = getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			
			/*********非超级管理员时只能查询本团队内的数据************/
			if(!SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())){
				super.dataLimits(ee, object,session);
			}
			List<BettingHqBbin> list = bettingGameService.takeRecord(object);
			String gameType;
			for (int i = 0; i < list.size(); i++) {
				gameType = list.get(i).getBbinGameType();
				list.get(i).setBbinGameType(BBINUtils.getGameName(gameType));
				gameType = null;
			}
			model.addAttribute("listData", list);
			model.addAttribute("title", "BBIN-波音游戏报表数据");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "/gamerecord/bbingameexcel";
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
