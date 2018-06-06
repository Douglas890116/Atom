package com.maven.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.maven.cache.SystemCache;
import com.maven.constant.Constant;
import com.maven.entity.EmployeeGamecataloyBonus;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeType;
import com.maven.entity.EnterpriseEmployeeType.Type;
import com.maven.entity.Game;
import com.maven.entity.GameCategory;
import com.maven.entity.PermissionRole;
import com.maven.entity.UserLogs;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.EmployeeGamecataloyBonusService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.GameService;
import com.maven.service.PermissionRoleService;
import com.maven.service.UserLogsService;
/**
 * 只处理用户类型为代理(0004)的数据
 * @author Ethan
 *
 */
@Controller
@RequestMapping("/employeeAgent")
public class EnterpriseEmployeeIsAgentTypeControll extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			EnterpriseEmployeeIsAgentTypeControll.class.getName(), OutputManager.LOG_EMPLOYEEISAGENTTYPE);
	
	@SuppressWarnings("serial")
	private static List<String> ALL_AGENTTYPE = new ArrayList<String>(){{this.add(EnterpriseEmployeeType.Type.代理.value);this.add(EnterpriseEmployeeType.Type.信用代理.value);}};
	/** 权限角色 */
	@Autowired
	private PermissionRoleService permissionRoleService;
	/**  企业员工 */
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	
	/**  会员接入品台游戏返点  */
	@Autowired
	private EmployeeGamecataloyBonusService employeeGamecataloyBonusService;
	
	@Autowired
	private GameService gameService;
	@Autowired
	private UserLogsService userLogsService;
	/**
	 * 代理员工主页面
	 * @return
	 */
	@RequestMapping("/userJsp/agentEmployeeIndex")
	public String enterpriseIndex(HttpServletRequest request,Model model){
		model.addAttribute("employeecode", request.getParameter("employeecode"));
		return "/userjsp/agentEmployeeIndex";
	}
	
	/**
	 * 代理会员查询页面
	 * @return
	 */
	@RequestMapping(value={"/agentMember","/member"})
	public String agentMember(HttpServletRequest request,Model model){
		model.addAttribute("parentemployeecode", request.getParameter("parentemployeecode"));
		return "/userjsp/agentMember";
	}
	
	/**
	 * 代理新增页面
	 * @return
	 */
	@RequestMapping("/userJsp/agentEmployeeAdd")
	public String enterpriseAdd(Model model, HttpSession session, HttpServletRequest request) {
		model.addAttribute(Constant.MENU_RELATION, request.getAttribute(Constant.MENU_RELATION));
		try {
			String parentemployeecode = request.getParameter("parentemployeecode");
			String parentemployeeaccount = request.getParameter("parentemployeeaccount");
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			List<Game> games = gameService.takeEnterpriseGames(ee.getEnterprisecode());
			Map<String, BigDecimal> bonus = null;
			if (StringUtils.isNotBlank(parentemployeecode))
				bonus = employeeGamecataloyBonusService.takeEmployeeGameCategoryBonusMap(parentemployeecode);
			else 
				bonus = employeeGamecataloyBonusService.takeEmployeeGameCategoryBonusMap(ee.getEmployeecode());
			
			// 加载权限角色信息
			Map<String, Object> object = new HashMap<String, Object>();
			object.put("enterprisecode", ee.getEnterprisecode());
			List<PermissionRole> roleList = permissionRoleService.selectAll(object);
			
			model.addAttribute("rolelist", roleList);
			model.addAttribute("bonus", bonus);
			model.addAttribute("games", games);

			// 判断是否是金马腾，以开启指定上级的功能
			boolean isJMT = ee.getEnterprisecode().equals("EN003X");
			if (isJMT) {
			model.addAttribute("isJMT", isJMT);
			model.addAttribute("parentemployeeaccount", parentemployeeaccount);
			model.addAttribute("parentemployeecode", parentemployeecode);
			}
			return "/userjsp/agentEmployeeAdd";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	/**
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findParentEmployee")
	public Map<String, Object> findParentEmployee(HttpServletRequest request, HttpSession session, RedirectAttributes attrs) {
		try {
			//获取当前登录用户的信息
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			String parentemployeeaccount = request.getParameter("parentemployeeaccount");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("loginaccount", parentemployeeaccount);
			params.put("enterprisecode", ee.getEnterprisecode());
			params.put("employeetypecode", EnterpriseEmployeeType.Type.代理.value);
			super.dataLimits(ee, params, session);
			List<EnterpriseEmployee> list = enterpriseEmployeeService.queryAgent(params);
			if (null != list && list.size() > 0) {
				EnterpriseEmployee parentemployee = list.get(0);
				 return super.packJSON(Constant.BooleanByte.YES, "代理查找成功", parentemployee);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "系统错误");
		}
		return super.packJSON(Constant.BooleanByte.NO, "输入账号不存在");
	}
	/**
	 * 只查询用户为代理的数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/findEmployee")
	@ResponseBody
	public Map<String,Object> findEmployee(HttpServletRequest request,HttpSession session){
		try {
			//获取登录用户的信息
			EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String,Object> obj = getRequestParamters(request);
			//判断是否查看下级数据
			/*if(!obj.containsKey("parentemployeecode")){
				obj.put("parentemployeecode", employee.getEmployeecode());
			}*/
			
			
			obj.put("enterprisecode", employee.getEnterprisecode());
			
			
			super.dataLimits(employee, obj,session);
			obj.put("employeetypecodes", ALL_AGENTTYPE);
			//特殊参数处理
			pramsSpecialHandle(obj);
			
			//以下判断当前用户是信用代理还是现金代理。查询结果的差别在与统计到的下级代理数量
			List<EnterpriseEmployee> list = null;
			if(employee.getEmployeetypecode().equals(Type.代理.value)) {
				list = enterpriseEmployeeService.findEmployee(obj);
			} else if(employee.getEmployeetypecode().equals(Type.信用代理.value)) {
				list = enterpriseEmployeeService.findEmployeeAgent(obj);
			} else {
				list = enterpriseEmployeeService.findEmployee(obj);
			}
			
			//加密标识字段的值
			this.encryptSign(list,session.getId(),new String[]{"employeecode"});
			//查询总记录条数
			int count = enterpriseEmployeeService.findEmployeeCount(obj);
			return super.formatPagaMap(list, count);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 查询指定代理用户的直线会员
	 * @param request
	 * @return
	 */
	 
	@RequestMapping("/findAgentAllMember")
	@ResponseBody
	public Map<String,Object> findAgentAllMember(HttpServletRequest request,HttpSession session){
		try {
			//获取登录用户的信息
			EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String,Object> obj = getRequestParamters(request);
			obj.put("employeetypecode", Type.会员.value);
			super.dataLimits(employee, obj,session);
			//特殊参数处理
			pramsSpecialHandle(obj);
			List<EnterpriseEmployee> list = enterpriseEmployeeService.findEmployee(obj);
			//加密标识字段的值
			this.encryptSign(list,session.getId(),new String[]{"employeecode","loginaccount"});
			//查询总记录条数
			long listTotal = enterpriseEmployeeService.findEmployeeCount(obj);
			Map<String,Object> map =new HashMap<String,Object>();
			map.put("rows", list);
			map.put("results", listTotal);
			return map;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 分红、占成、洗码页面
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/bonusPage")
	public String bonusPage(HttpServletRequest request,HttpSession session,Model model){
		EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
		try {
			String employeeCodeArray[] = request.getParameterValues("employeeCodeArray[]");
			List<Game> games = gameService.takeEnterpriseGames(ee.getEnterprisecode());
			model.addAttribute("games", games);
			session.setAttribute("employeeCodeArray", employeeCodeArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/userjsp/bonusPage";
	}
	
	/**
	 * 批量设置用户的分红、占成、洗码
	 */
	@RequestMapping("/batchSaveDividendShareBonus")
	@ResponseBody
	public Map<String,Object> batchSaveDividendShareBonus(HttpSession session,HttpServletRequest request,@ModelAttribute EnterpriseEmployee enterpriseEmployee){
		try {
			Map<String,Object> object = super.getRequestParamters(request);
			//获取当前登录用户的信息
			EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			if(enterpriseEmployee.getDividend().doubleValue()<0
					||enterpriseEmployee.getShare().doubleValue()<0){
				return super.packJSON(Constant.BooleanByte.NO, "分红或者占成不能小于0%");
			}else if(enterpriseEmployee.getDividend().doubleValue()>1
					||enterpriseEmployee.getDividend().compareTo(employee.getDividend())==1){
				return super.packJSON(Constant.BooleanByte.NO, "下级分红不能超过自身分红");
			}else if(enterpriseEmployee.getShare().doubleValue()>1
					||enterpriseEmployee.getShare().compareTo(employee.getShare())==1){
				return super.packJSON(Constant.BooleanByte.NO, "下级占成不能超过自身占成");
			}
			//洗码校验与数据封装
			List<EmployeeGamecataloyBonus> bonus = new ArrayList<EmployeeGamecataloyBonus>();
			List<EmployeeGamecataloyBonus> list = employeeGamecataloyBonusService.takeEmployeeGameCategoryBonus(employee.getEmployeecode());
			String employeecode[] = (String[]) session.getAttribute("employeeCodeArray");
			for (int i = 0; i < employeecode.length; i++){
				//设置每个用户的分红、占成
				EnterpriseEmployee obj = new EnterpriseEmployee();
				obj.setEmployeecode(employeecode[i]);
				obj.setDividend(new BigDecimal(object.get("dividend").toString()));
				obj.setShare(new BigDecimal(object.get("share").toString()));
				enterpriseEmployeeService.tc_setDividendShare(obj);
				//循环判断洗码是否大于上级用户
				for (EmployeeGamecataloyBonus e : list) {
					String key = e.getGametype()+"_"+e.getCategorytype();
					if(object.get(key)!=null){
						BigDecimal b = new BigDecimal(String.valueOf(object.get(key))).divide(new BigDecimal(100));
						GameCategory gc = SystemCache.getInstance().getGameCategoryByCnmKey(key);
						if(b.compareTo(gc.getMinbonus())==-1){
							return super.packJSON(Constant.BooleanByte.NO, gc.getGametype().replace("Game","")+"-"+gc.getCategoryname()+": 洗码不能小于"+gc.getMinbonus().multiply(new BigDecimal("100")));
						}else if(b.compareTo(gc.getMaxbonus())==1){
							return super.packJSON(Constant.BooleanByte.NO, gc.getGametype().replace("Game","")+"-"+gc.getCategoryname()+": 洗码不能大于"+gc.getMaxbonus().multiply(new BigDecimal("100")));
						}else if(b.compareTo(e.getBonus())==1){
							return super.packJSON(Constant.BooleanByte.NO, gc.getGametype().replace("Game","")+"-"+gc.getCategoryname()+": 下级洗码不能超过自身洗码");
						}else{
							bonus.add(new EmployeeGamecataloyBonus(employeecode[i],employee.getEmployeecode(),e.getGametype(),e.getCategorytype(),b));
						}
					}
				}
				
				obj = enterpriseEmployeeService.takeEmployeeByCode(employeecode[i]);
				userLogsService.addActivityBetRecord(new UserLogs(obj.getEnterprisecode(), obj.getEmployeecode(), obj.getLoginaccount(), 
					     UserLogs.Enum_operatype.用户信息业务, "批量设置用户分红占成洗码:"+obj.getLoginaccount(), employee.getLoginaccount(), null));
				
				
			}
			if(bonus.size()==0) {
				return super.packJSON(Constant.BooleanByte.NO, "请设置用户洗码");
			}
			session.removeAttribute("employeeCodeArray");
			employeeGamecataloyBonusService.tc_installEmployeeCategyBonus(bonus);
			return super.packJSON(Constant.BooleanByte.YES, "洗码设置成功");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "操作失败，请稍后尝试");
		}
	}
	
	
	
	/**
	 * 企业类型的用户密码修改页面
	 */
	@RequestMapping("/userJsp/enterpriseModify")
	public String rowModify(Model model, HttpServletRequest request){
		model.addAttribute("loginaccount", request.getParameter("loginaccount"));
		return "/userjsp/enterpriseModify";
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
