package com.maven.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.base.dao.DataSource;
import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.entity.EmployeeGamecataloyBonus;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeLevel;
import com.maven.entity.EnterpriseEmployeeLevelBonus;
import com.maven.entity.EnterpriseEmployeeType.Type;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.entity.Game;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.BusinessEmployeeLovelService;
import com.maven.service.EmployeeGamecataloyBonusService;
import com.maven.service.EnterpriseEmployeeLevelBonusService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.GameService;
import com.maven.util.BeanToMapUtil;
@Controller
@RequestMapping("/employeeLevelBonus")
public class EnterpriseEmployeeLevelBonusController extends BaseController{
	private static LoggerManager log = LoggerManager.getLogger(EnterpriseEmployeeLevelBonusController.class.getName(), OutputManager.LOG_ENTERPRISEINFORMATION);
	
	@Autowired
	private BusinessEmployeeLovelService businessEmployeeLovelService;
	@Autowired
	private GameService gameService;
	@Autowired
	private EmployeeGamecataloyBonusService employeeGamecataloyBonusService;
	@Autowired
	private EnterpriseEmployeeLevelBonusService bonusService;
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	@Autowired
	private EnterpriseEmployeeLevelBonusService enterpriseEmployeeLevelBonusService;
	
	
	@RequestMapping(value={"/setting"})
	public String setting(HttpServletRequest request,HttpSession session , Model model){
		
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			
			Map<String,Object> object = super.getRequestParamters(request);
			String employeelevelcode = String.valueOf(object.get("employeelevelcode"));
			System.out.println("employeelevelcode="+employeelevelcode);
			EnterpriseEmployeeLevel employeeLevel = businessEmployeeLovelService.getOneObject(employeelevelcode);
			model.addAttribute("employeeLevel", employeeLevel);
			
			//上级用户的信息
			EnterpriseEmployee superior = new EnterpriseEmployee();
			superior.setEnterprisecode(ee.getEnterprisecode());
			superior.setEmployeetypecode(Type.企业号.value);
			superior.setParentemployeecode(SystemConstant.getProperties("admin.employeecode"));
			superior = enterpriseEmployeeService.select(superior).get(0);
			
			//加载品牌接入平台信息
			List<Game> games = gameService.takeEnterpriseGames(ee.getEnterprisecode());
			//修改用户的当前洗码
			Map<String,BigDecimal> bonus = bonusService.takeEmployeeLevelBonusMap(employeelevelcode);
			//上级用户的洗码
			Map<String,BigDecimal> supbonus =  employeeGamecataloyBonusService.takeEmployeeGameCategoryBonusMap(superior.getEmployeecode());
			
			
			model.addAttribute("supbonus", supbonus);
			model.addAttribute("games", games);
			model.addAttribute("bonus", bonus);
			
			return "/userinfo/employeeLevelBonusSetting";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/saveSetting")
	@ResponseBody
	public Map<String,Object> saveSetting(HttpServletRequest request,HttpSession session){
		try {
			Map<String,Object> object = super.getRequestParamters(request);
			
			String employeelevelcode = String.valueOf(object.get("employeelevelcode"));
			
			//遍历map中的键  
			for (String key : object.keySet()) {  
				if( !key.startsWith("type")) {
					continue;
				}
//				System.out.println("Key = " + key + "  Value = " + object.get(key));  
			    
			    BigDecimal bonus = new BigDecimal(object.get(key).toString()).divide(new BigDecimal("100"));
			    String gametype = key.split("_")[1];
			    String categorytype = key.split("_")[2];
			    
			    EnterpriseEmployeeLevelBonus data = new EnterpriseEmployeeLevelBonus(employeelevelcode, gametype, categorytype,bonus);
			    enterpriseEmployeeLevelBonusService.addActivityBetRecord(data);
			    
			}  
			
			return super.packJSON(Constant.BooleanByte.YES, "成功修改结算配置");
		}catch(LogicTransactionRollBackException e){
			return super.packJSON(Constant.BooleanByte.NO, e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "修改结算配置失败，请稍后尝试");
		}
		
	}
	
	
	
	
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}

}
