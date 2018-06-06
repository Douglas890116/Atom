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
import com.maven.entity.BettingEbet;
import com.maven.entity.BettingIm;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseEmployee;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.BettingGameService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseService;
import com.maven.util.IMUtils;
import com.maven.utils.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Controller
@RequestMapping("/imgame")
public class BettingImController extends BaseController{
private static LoggerManager log = LoggerManager.getLogger(BettingIm.class.getName(), OutputManager.LOG_BETTINGHQXCP);
	
	@Resource(name="bettingImServiceImpl")
	private BettingGameService<BettingIm> bettingGameService;
	
	@Resource
	private EnterpriseEmployeeService employeeService;
	private static List<Enterprise> listEnterprise;
	@Resource
	private EnterpriseService enterpriseService;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request,Model model){
		super.assembleEnterprise(request.getSession(), listEnterprise, enterpriseService, model, request);
		model.addAttribute("employeecode", request.getParameter("employeecode"));
		return "/gamerecord/imgame";
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
			List<BettingIm> list = bettingGameService.takeRecord(object);
			JSONArray array = null;
			JSONObject jsonObject = null;
			String dataitems = null;
			StringBuffer __newdataitems = null;
			for (BettingIm data : list) {
				__newdataitems = new StringBuffer();
				dataitems = data.getDetailitems();
				array = JSONArray.fromObject(dataitems);
				if(array != null && array.size() > 0) {
					for (int i = 0; i < array.size(); i++) {
						jsonObject = array.getJSONObject(i); 
						String Market = jsonObject.getString("Market");//盘口类别
						String EventName = jsonObject.getString("EventName");//赛事名称
						String EventDateTime = jsonObject.getString("EventDateTime");
						String SportsName = jsonObject.getString("SportsName");//体育名称
						String CompetitionName = jsonObject.getString("CompetitionName");//竞技名称
						String Period = jsonObject.getString("Period");//1H  上半场, 2H  下半场, FT  全场
						String BetTypeDesc = jsonObject.getString("BetTypeDesc");//
						String HomeTeamName = jsonObject.getString("HomeTeamName");//主队名称
						String AwayTeamName = jsonObject.getString("AwayTeamName");//客队名称
						String WagerHomeTeamScore = jsonObject.getString("WagerHomeTeamScore");//注单确定时的主队比分
						String WagerAwayTeamScore = jsonObject.getString("WagerAwayTeamScore");//注单确定时的客队比分
						String HomeTeamHTScore = jsonObject.getString("HomeTeamHTScore");//主队上半场比分(仅在结算时提供。)
						String AwayTeamHTScore = jsonObject.getString("AwayTeamHTScore");//客队上半场比分 (仅在结算时提供。)
						String HomeTeamFTScore = jsonObject.getString("HomeTeamFTScore");//主队全场比分(仅在结算时提供。)
						String AwayTeamFTScore = jsonObject.getString("AwayTeamFTScore");//客队全场比分(仅在结算时提供。)
						
						Market = IMUtils.__Market.get(Market);
						Period = IMUtils.__Period.get(Period);
						
						__newdataitems.append(Market).append("("+SportsName+")").append(", ");
						//__newdataitems.append(EventName).append(", ");
						__newdataitems.append(CompetitionName).append("("+Period+")").append(", ");
						__newdataitems.append(HomeTeamName).append(" 【对】 ").append(AwayTeamName);
						__newdataitems.append("\r\n");
					}
				}
				data.setDetailitems(__newdataitems.toString());
			}
			
			Map<String, Object> result = bettingGameService.takeRecordCountMoney(object);
			int countRecord = StringUtil.getInt(result.get("count"));
			
			
			Map<String, Object> data = super.formatPagaMap(list, countRecord);
			Map<String, Object> summary = new HashMap<String, Object>();
			summary.put("betmoney", StringUtil.getDouble(result.get("betmoney")));
			summary.put("netmoney", StringUtil.getDouble(result.get("netmoney")));
			data.put("summary", summary);
			
			return data;
			
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 数据导出Excel
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
			List<BettingIm> list = bettingGameService.takeRecord(object);
			model.addAttribute("listData", list);
			model.addAttribute("title", "IM游戏报表数据");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "/gamerecord/imgameexcel";
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
