package com.maven.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.cache.SystemCache;
import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.entity.BettingGgp;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.PermissionMenu.Enum_language;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.BettingGameService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseService;
import com.maven.utils.StringUtil;
@Controller
@RequestMapping("/ggpgame")
public class BettingGgpController extends BaseController{
private static LoggerManager log = LoggerManager.getLogger(BettingHqXcpController.class.getName(), OutputManager.LOG_BETTINGHQXCP);
	
	@Autowired
	private EnterpriseEmployeeService employeeService;
	@Resource(name="bettingGgpServiceImpl")
	private BettingGameService<BettingGgp> bettingGameService;
	
	private static List<Enterprise> listEnterprise;
	@Resource
	private EnterpriseService enterpriseService;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request,Model model){
		super.assembleEnterprise(request.getSession(), listEnterprise, enterpriseService, model, request);
		model.addAttribute("employeecode", request.getParameter("employeecode"));
		return "/gamerecord/ggpgame";
	}
	
	@RequestMapping(value={"/data"})
	@ResponseBody
	public Map<String,Object> data(HttpServletRequest request , HttpServletResponse response , HttpSession session){
		try {
			Map<String,Object> object = getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			
			object.put("startDate", object.get("startDate").toString().replaceAll("-", "").substring(0, 8));
			object.put("endDate", object.get("endDate").toString().replaceAll("-", "").substring(0, 8));
			
			/*********非超级管理员时只能查询本团队内的数据************/
			if(!SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())){
				super.dataLimits(ee, object,session);
			}
			
			//传入了企业号,需要根据企业筛选
			if (object.get("enterprisecode") != null) {
				object.put("enterprisecode", object.get("enterprisecode"));
			}
			
			if (object.get("parentName") != null) {
				String parentemployeeaccount = object.get("parentName").toString();
				EnterpriseEmployee parent = new EnterpriseEmployee();
				parent.setLoginaccount(parentemployeeaccount);
				List<EnterpriseEmployee> list = employeeService.select(parent);
				if (null != list && list.size() > 0) {
					//object.put("parentemployeecode", list.get(0).getEmployeecode());
					super.dataLimits(list.get(0), object,session);
				} else {
					// 如果查不到上级账户code，就直接传名字进去，查不出数据，表示这个账号下没有会员
					//object.put("parentemployeecode", parentemployeeaccount);
				}
				//object.remove("parentName");
			}

			
			
			List<BettingGgp> list = bettingGameService.takeRecord(object);
			
			//转人民币
			for (BettingGgp bettingGgp : list) {
				double real_money = bettingGgp.getBetmoney() / SystemCache.getInstance().getExchangeRateAll("USD");//转人民币
				bettingGgp.setBetmoney(real_money);
			}
			
			Map<String, Object> result = bettingGameService.takeRecordCountMoney(object);
			int countRecord = StringUtil.getInt(result.get("count"));
			
			Map<String, Object> data = super.formatPagaMap(list, countRecord);
			Map<String, Object> summary = new HashMap<String, Object>();
			summary.put("betmoney", StringUtil.getDouble(result.get("betmoney")));
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
	@RequestMapping("exportExcel")
	public String exportExcel(HttpServletRequest request, HttpSession session, Model model){
		try {
			Map<String,Object> object = getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			
			object.put("startDate", object.get("startDate").toString().replaceAll("-", "").substring(0, 8));
			object.put("endDate", object.get("endDate").toString().replaceAll("-", "").substring(0, 8));
			
			if (object.get("parentName") != null) {
				String parentemployeeaccount = object.get("parentName").toString();
				EnterpriseEmployee parent = new EnterpriseEmployee();
				parent.setLoginaccount(parentemployeeaccount);
				List<EnterpriseEmployee> list = employeeService.select(parent);
				if (null != list && list.size() > 0) {
					object.put("parentemployeecode", list.get(0).getEmployeecode());
				} else {
					// 如果查不到上级账户code，就直接传名字进去，查不出数据，表示这个账号下没有会员
					object.put("parentemployeecode", parentemployeeaccount);
				}
				object.remove("parentName");
			}
			
			/*********非超级管理员时只能查询本团队内的数据************/
			if(!SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())){
				super.dataLimits(ee, object,session);
			}
			List<BettingGgp> list = bettingGameService.takeRecord(object);
			
			//转人民币
			for (BettingGgp bettingGgp : list) {
				double real_money = bettingGgp.getBetmoney() / SystemCache.getInstance().getExchangeRateAll("USD");//转人民币
				bettingGgp.setBetmoney(real_money);
			}
			
			String language = String.valueOf(session.getAttribute(Constant.LANGUAGE));
			model.addAttribute("listData", list);
			model.addAttribute("title", (Enum_language.英文.value.equals(language)?"GG Poker Report":"GGP扑克报表数据")+object.get("startDate")+"-"+object.get("endDate"));
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "/gamerecord/ggpgameexcel";
	}
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
