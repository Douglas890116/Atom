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
import com.maven.entity.BettingHqAg;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseEmployee;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.BettingGameService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseService;
import com.maven.util.AGUtils;
import com.maven.utils.StringUtil;

@Controller
@RequestMapping("/TAGGame")
public class BettingHqAgController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(BettingHqXcpController.class.getName(), OutputManager.LOG_BETTINGHQXCP);
	
	@Resource
	private EnterpriseEmployeeService employeeService;
	
	private static List<Enterprise> listEnterprise;
	@Resource
	private EnterpriseService enterpriseService;
	
	@Resource(name="bettingHqAgServiceImpl")
	private BettingGameService<BettingHqAg> bettingGameService;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request,Model model){
		super.assembleEnterprise(request.getSession(), listEnterprise, enterpriseService, model, request);
		model.addAttribute("employeecode", request.getParameter("employeecode"));
		return "/gamerecord/taggame";
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
			
			List<BettingHqAg> list = bettingGameService.takeRecord(object);
			
			for (BettingHqAg bettingHqAg : list) {
				bettingHqAg.setPlayType(AGUtils.getPlayType(bettingHqAg.getPlayType(), bettingHqAg.getGameType()));
				bettingHqAg.setGameType(AGUtils.getGameName(bettingHqAg.getGameType()));
				bettingHqAg.setPlatformType(AGUtils.__PlatformType.get(bettingHqAg.getPlatformType()));
				bettingHqAg.setRound(AGUtils.__Roundype.get(bettingHqAg.getRound()));
			}
			
			Map<String, Object> result = bettingGameService.takeRecordCountMoney(object);
			int countRecord = StringUtil.getInt(result.get("count"));
			
			Map<String, Object> data = super.formatPagaMap(list, countRecord);
			Map<String, Object> summary = new HashMap<String, Object>();
			summary.put("betAmount", StringUtil.getDouble(result.get("betAmount")));
			summary.put("validBetAmount", StringUtil.getDouble(result.get("validBetAmount")));
			summary.put("netAmount", StringUtil.getDouble(result.get("netAmount")));
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
            List<BettingHqAg> list = bettingGameService.takeRecord(object);
			String gameType;
			String playType;
			for (int i = 0; i < list.size(); i++) {
				gameType = list.get(i).getGameType();
				playType = list.get(i).getPlayType();
				list.get(i).setGameType(AGUtils.getGameName(gameType));
				list.get(i).setPlayType(AGUtils.getPlayType(playType, gameType));
				gameType = null;
				playType = null;
			}
            model.addAttribute("listData", list);
            model.addAttribute("title", "TAG游戏报表数据");
        } catch (Exception e) {
            log.Error(e.getMessage(), e);
        }
        return "/gamerecord/taggameexcel";
    }
    
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
