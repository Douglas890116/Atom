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
import com.maven.entity.BettingHqOgOg;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseEmployee;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.BettingGameService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseService;
import com.maven.util.OGUtils;
import com.maven.utils.StringUtil;

@RequestMapping("/OGGame")
@Controller
public class BettingHqOgOgController extends BaseController {
	
	private static LoggerManager log = LoggerManager.getLogger(
			BettingHqOgOgController.class.getName(), OutputManager.LOG_BETTINGHQOGOG);
	
	@Resource
	private EnterpriseEmployeeService employeeService;
	private static List<Enterprise> listEnterprise;
	@Resource
	private EnterpriseService enterpriseService;
	@Resource(name="bettingHqOgOgServiceImpl")
	private BettingGameService<BettingHqOgOg> bettingGameService;

	@RequestMapping("/list")
	public String list(HttpServletRequest request,Model model){
		super.assembleEnterprise(request.getSession(), listEnterprise, enterpriseService, model, request);
		model.addAttribute("employeecode", request.getParameter("employeecode"));
		return "/gamerecord/oggame";
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
			
			List<BettingHqOgOg> list = bettingGameService.takeRecord(object);
			for (BettingHqOgOg bettingHqOgOg : list) {
				String content = bettingHqOgOg.getAoiGameBettingContent();
				if(content != null && !content.equals("")) {
					bettingHqOgOg.setAoiGameBettingContent(OGUtils.getGameBettingContent(content));
				}
			}
			
			Map<String, Object> result = bettingGameService.takeRecordCountMoney(object);
			int countRecord = StringUtil.getInt(result.get("count"));
			
			Map<String, Object> data = super.formatPagaMap(list, countRecord);
			Map<String, Object> summary = new HashMap<String, Object>();
			summary.put("aoiBettingAmount", StringUtil.getDouble(result.get("aoiBettingAmount")));
			summary.put("aoiCompensateRate", StringUtil.getDouble(result.get("aoiCompensateRate")));
			summary.put("aoiValidAmount", StringUtil.getDouble(result.get("aoiValidAmount")));
			summary.put("aoiWinLoseAmount", StringUtil.getDouble(result.get("aoiWinLoseAmount")));
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
            List<BettingHqOgOg> list = bettingGameService.takeRecord(object);
            model.addAttribute("listData", list);
            model.addAttribute("title", "OG-东方游戏报表数据");
        } catch (Exception e) {
            log.Error(e.getMessage(), e);
        }
        return "/gamerecord/oggameexcle";
    }
    
	@Override
	public LoggerManager getLogger() {
		return log;
	}

}
