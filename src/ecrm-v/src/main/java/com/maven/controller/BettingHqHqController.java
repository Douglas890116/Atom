package com.maven.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.entity.BettingHqHq;
import com.maven.entity.EnterpriseEmployee;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.BettingGameService;

@Controller
@RequestMapping("/HQGame")
public class BettingHqHqController extends BaseController {
	
	private static LoggerManager log = LoggerManager.getLogger(
			BettingHqHqController.class.getName(), OutputManager.LOG_BETTINGHQHQ);
	
	@Resource(name="bettingHqHqServiceImpl")
	private BettingGameService<BettingHqHq> bettingGameService;

	@RequestMapping("/list")
	public String list(HttpServletRequest request){
		return "/gamerecord/hqgame";
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
			
			List<BettingHqHq> list = bettingGameService.takeRecord(object);
			int count = bettingGameService.takeRecordCount(object);
			return super.formatPagaMap(list, count);
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
