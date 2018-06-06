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

import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.entity.ApiSoltGametype;
import com.maven.entity.BettingWin88;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseEmployee;
import com.maven.game.enums.GameEnum;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.ApiSoltGametypeService;
import com.maven.service.BettingGameService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseService;
import com.maven.util.PTUtils;
import com.maven.utils.StringUtil;
@Controller
@RequestMapping("/win88game")
public class BettingWin88Controller extends BaseController{
private static LoggerManager log = LoggerManager.getLogger(BettingHqXcpController.class.getName(), OutputManager.LOG_BETTINGHQXCP);
	
	@Resource(name="bettingWin88ServiceImpl")
	private BettingGameService<BettingWin88> bettingGameService;
	@Resource
	private EnterpriseEmployeeService employeeService;
	private static List<Enterprise> listEnterprise;
	@Resource
	private EnterpriseService enterpriseService;
	@Autowired
	private ApiSoltGametypeService apiSoltGametypeService;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request,Model model){
		super.assembleEnterprise(request.getSession(), listEnterprise, enterpriseService, model, request);
		model.addAttribute("employeecode", request.getParameter("employeecode"));
		try {
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("biggametype", GameEnum.Enum_GameType.PT.gametype);
			List<ApiSoltGametype> list = apiSoltGametypeService.selectAll(params);
			model.addAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/gamerecord/win88game";
	}
	
	@RequestMapping(value={"/data"})
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
			List<BettingWin88> list = bettingGameService.takeRecord(object);
			ApiSoltGametype apiSoltGametype = null;
			for (BettingWin88 bettingHqPt : list) {
				String ptGametype = bettingHqPt.getPtGametype();
				String ptGamename = bettingHqPt.getPtGamename();
				
				String name = ptGamename.split("\\(")[1].replaceAll("\\)", "");
				
				bettingHqPt.setPtGametype(PTUtils.__GameType.get(ptGametype) == null ? ptGametype : PTUtils.__GameType.get(ptGametype) );
				
				apiSoltGametype = new ApiSoltGametype();
				apiSoltGametype.setBiggametype(GameEnum.Enum_GameType.PT.gametype);
				apiSoltGametype.setGamecodeweb(name);
				apiSoltGametype = apiSoltGametypeService.selectFirst(apiSoltGametype);
				if(apiSoltGametype != null) {
					bettingHqPt.setPtGamename(apiSoltGametype.getCnname());
				}
			}
			
			Map<String, Object> result = bettingGameService.takeRecordCountMoney(object);
			int countRecord = StringUtil.getInt(result.get("count"));
			
			Map<String, Object> data = super.formatPagaMap(list, countRecord);
			Map<String, Object> summary = new HashMap<String, Object>();
			summary.put("ptBet", StringUtil.getDouble(result.get("ptBet")));
			summary.put("ptWin", StringUtil.getDouble(result.get("ptWin")));
			summary.put("ptBalance", StringUtil.getDouble(result.get("ptBalance")));
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
            List<BettingWin88> list = bettingGameService.takeRecord(object);
            model.addAttribute("listData", list);
            model.addAttribute("title", "WIN88报表数据");
        } catch (Exception e) {
            log.Error(e.getMessage(), e);
        }
        return "/gamerecord/win88gameexcel";
    }
    
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
