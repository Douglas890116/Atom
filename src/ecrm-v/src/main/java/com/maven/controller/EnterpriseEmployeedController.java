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

import com.maven.constant.Constant;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeType;
import com.maven.entity.Game;
import com.maven.entity.PermissionRole;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.EmployeeGamecataloyBonusService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.GameService;
import com.maven.service.PermissionRoleService;
/**
 * 只处理用户类型为员工(0002)的数据
 * @author Ethan
 *
 */
@Controller
@RequestMapping("/employeeOperating")
public class EnterpriseEmployeedController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(EnterpriseEmployeedController.class.getName(), OutputManager.LOG_EMPLOYEE);
	/** 游戏信息 */
	@Autowired
	private GameService gameService;
	/** 权限角色 */
	@Autowired
	private PermissionRoleService permissionRoleService;
	/**  企业员工 */
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;

	/**  会员接入品台游戏返点  */
	@Autowired
	private EmployeeGamecataloyBonusService employeeGamecataloyBonusService;
	/**
	 * 员工主页面
	 * @return
	 */
	@RequestMapping("/userJsp/employeeIndex")
	public String employeeIndex(HttpSession session,Model model){
		//return super.validateIsEmployee(session, model)?"/userjsp/employeeIndex":Constant.PAGE_IDENTITY_NO_MATCH;
		return "/userjsp/employeeIndex";
	}
	/**
	 * 员工新增页面
	 * @return
	 */
	@RequestMapping("/userJsp/employeeAdd")
	public String employeeAdd(HttpServletRequest request,HttpSession session,Model model){
		EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
		try {
			// 加载游戏、分红、占成信息
			List<Game> games = gameService.takeEnterpriseGames(ee.getEnterprisecode());
			Map<String,BigDecimal> bonus = employeeGamecataloyBonusService.takeEmployeeGameCategoryBonusMap(ee.getEmployeecode());
			
			// 加载权限角色信息
			Map<String, Object> object = new HashMap<String, Object>();
			object.put("enterprisecode", ee.getEnterprisecode());
			List<PermissionRole> roleList = permissionRoleService.selectAll(object);
			
			model.addAttribute("rolelist", roleList);
			model.addAttribute("bonus", bonus);
			model.addAttribute("games", games);
			model.addAttribute("employee", ee);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
		}
		return "/userjsp/employeeAdd";
	}
	/**
	 * 只查询用户为员工类型的数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/findEmployee")
	@ResponseBody
	public Map<String,Object> findEmployee(HttpServletRequest request,HttpSession session){
		try {
			//获取登录用户的信息
			EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String,Object> object = getRequestParamters(request);
			super.dataLimits(employee, object,session);
			object.put("employeetypecode", EnterpriseEmployeeType.Type.员工.value);
			//特殊参数处理
			pramsSpecialHandle(object);
			List<EnterpriseEmployee> list = enterpriseEmployeeService.findEmployee(object);
			//加密标识字段的值
			this.encryptSign(list,session.getId(),new String[]{"employeecode","loginaccount"});
			//查询总记录条数
			int listTotal = enterpriseEmployeeService.findEmployeeCount(object);
			return super.formatPagaMap(list, listTotal);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 员工类型的用户密码修改页面
	 */
	@RequestMapping("/userJsp/employeeModify")
	public String rowModify(Model model, HttpServletRequest request){
		model.addAttribute("loginaccount", request.getParameter("loginaccount"));
		return "/userjsp/employeeModify";
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
	
	/**
	 * 特殊参数处理
	 * @param obj
	 */
	private void pramsSpecialHandle(Map<String, Object> obj) {
		if(obj.get("dividend_begin")!=null){
			BigDecimal dividend_begin = new BigDecimal(String.valueOf(obj.get("dividend_begin")));
			obj.put("dividend_begin", dividend_begin.divide(new BigDecimal(100)));
		}
		if(obj.get("dividend_end")!=null){
			BigDecimal dividend_end = new BigDecimal(String.valueOf(obj.get("dividend_end")));
			obj.put("dividend_end", dividend_end.divide(new BigDecimal(100)));
		}
		if(obj.get("share_begin")!=null){
			BigDecimal share_begin = new BigDecimal(String.valueOf(obj.get("share_begin")));
			obj.put("share_begin", share_begin.divide(new BigDecimal(100)));
		}
		if(obj.get("share_end")!=null){
			BigDecimal share_end = new BigDecimal(String.valueOf(obj.get("share_end")));
			obj.put("share_end", share_end.divide(new BigDecimal(100)));
		}
	}
}
