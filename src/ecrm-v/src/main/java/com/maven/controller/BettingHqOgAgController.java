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
import com.maven.entity.BettingHqOgAg;
import com.maven.entity.EnterpriseEmployee;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.BettingGameService;
import com.maven.utils.StringUtil;

@RequestMapping("/AGGame")
@Controller
public class BettingHqOgAgController extends BaseController {
	
	private static LoggerManager log = LoggerManager.getLogger(
			BettingHqOgAgController.class.getName(), OutputManager.LOG_BETTINGHQOGAG);

	@Resource(name="bettingHqOgAgServiceImpl")
	private BettingGameService<BettingHqOgAg> bettingGameService;

	@RequestMapping("/list")
	public String list(HttpServletRequest request,Model model){
		model.addAttribute("employeecode", request.getParameter("employeecode"));
		return "/gamerecord/aggame";
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
			
			List<BettingHqOgAg> list = bettingGameService.takeRecord(object);
			
			Map<String, Object> result = bettingGameService.takeRecordCountMoney(object);
			int countRecord = StringUtil.getInt(result.get("count"));
			
			Map<String, Object> data = super.formatPagaMap(list, countRecord);
			Map<String, Object> summary = new HashMap<String, Object>();
			summary.put("stakeamount", StringUtil.getDouble(result.get("stakeamount")));
			summary.put("validstake", StringUtil.getDouble(result.get("validstake")));
			summary.put("winloss", StringUtil.getDouble(result.get("winloss")));
			summary.put("balanceafter", StringUtil.getDouble(result.get("balanceafter")));
			data.put("summary", summary);
			
			return data;
			
//			int count = bettingGameService.takeRecordCount(object);
//			return super.formatPagaMap(list, count);
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
