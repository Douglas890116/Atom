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
import com.maven.entity.BettingSa;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseEmployee;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.BettingGameService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseService;
import com.maven.util.SAUtils;
import com.maven.utils.StringUtil;

@RequestMapping("/SaGame")
@Controller
public class BettingSaController extends BaseController {
	
	private static LoggerManager log = LoggerManager.getLogger(
			BettingSaController.class.getName(), OutputManager.LOG_BETTINGSA);
	
	@Resource
	private EnterpriseEmployeeService employeeService;
	
	private static List<Enterprise> listEnterprise;
	@Resource
	private EnterpriseService enterpriseService;
	
	@Resource(name="bettingSaServiceImpl")
	private BettingGameService<BettingSa> bettingGameService;

	@RequestMapping("/list")
	public String list(HttpServletRequest request,Model model){
		super.assembleEnterprise(request.getSession(), listEnterprise, enterpriseService, model, request);
		model.addAttribute("employeecode", request.getParameter("employeecode"));
		return "/gamerecord/sagame";
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
			
			List<BettingSa> list = bettingGameService.takeRecord(object);
			if (null != list && list.size() > 0) {
				String gameId = null;
				
				for (BettingSa bettingSa : list) {
					gameId = bettingSa.getDetail();
					bettingSa.setDetail(SAUtils.getGameName(gameId));
					gameId = null;
					
					bettingSa.setGametype(SAUtils.__GameType.get(bettingSa.getGametype()));
				}
			}
			Map<String, Object> result = bettingGameService.takeRecordCountMoney(object);
			int countRecord = StringUtil.getInt(result.get("count"));
			
			Map<String, Object> data = super.formatPagaMap(list, countRecord);
			Map<String, Object> summary = new HashMap<String, Object>();
			summary.put("betamount", StringUtil.getDouble(result.get("betamount")));
			summary.put("resultamount", StringUtil.getDouble(result.get("resultamount")));
			summary.put("validbet", StringUtil.getDouble(result.get("validbet")));
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
            List<BettingSa> list = bettingGameService.takeRecord(object);
			if (null != list && list.size() > 0) {
				String gameId = null;
				for (int i = 0; i < list.size(); i++) {
					gameId = list.get(i).getDetail();
					list.get(i).setDetail(SAUtils.getGameName(gameId));
					gameId = null;
				}
			}
            model.addAttribute("listData", list);
            model.addAttribute("title", "SA游戏报表数据");
        } catch (Exception e) {
            log.Error(e.getMessage(), e);
        }
        return "/gamerecord/sagameexcel";
    }
    
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
