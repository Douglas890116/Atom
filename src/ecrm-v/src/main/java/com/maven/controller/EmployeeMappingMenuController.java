package com.maven.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.annotation.OperationLog;
import com.maven.annotation.RequestInterval;
import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.entity.EmployeeMappingMenu;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.PermissionMenu;
import com.maven.exception.ArgumentValidationException;
import com.maven.logger.LoggerManager;
import com.maven.logger.OparetionDescription;
import com.maven.logger.OutputManager;
import com.maven.service.EmployeeMappingMenuService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.PermissionMenuService;
/**
 * 批量授权Control
 * @author Ethan
 *
 */
@Controller
@RequestMapping("/EmployeeMPG")
public class EmployeeMappingMenuController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			EmployeeMappingMenuController.class.getName(), OutputManager.LOG_EMPLOYEEMAPPINGMENU);
	
	/** 员工权限映射 */
	@Autowired
	private EmployeeMappingMenuService employeeMappingMenuService;
	
	/** 权限菜单 */
	@Autowired
	private PermissionMenuService permissionMenuService;
	
	/** 员工 */
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	
	/**
	 * 加载用户权限模板与用户权限
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value={"/permission","/agentpermission"})
	@SuppressWarnings("unchecked")
	public String loadUserPermission(Model model,HttpServletRequest request,HttpSession session){
		try {
			EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			
			/*********非超级管理员时只能查询本团队内的数据************/
			boolean isadmin = false;
			if(SystemConstant.getProperties("admin.employeecode").equals(employee.getEmployeecode())){
				isadmin = true;
			}
			
			Map<String,Object> __object = super.getRequestParamters(request);
			String __employeecode = String.valueOf(__object.get("employeecode"));
			if(super.decodeSign(__employeecode, session.getId())){
				EnterpriseEmployee __user = enterpriseEmployeeService.takeEmployeeByCode(__employeecode.split("_")[1]);
				Map<String,PermissionMenu> __permissionModel = null;
				if(__user.getEmployeecode().equals(SystemConstant.getProperties("admin.employeecode"))){
					__permissionModel = (Map<String,PermissionMenu>)session.getAttribute(Constant.SYSTEM_PSERSSION);
				}else{
					Map<String,PermissionMenu> __allMenus = (Map<String,PermissionMenu>)session.getAttribute(Constant.SYSTEM_PSERSSION);
					Map<String,EmployeeMappingMenu> __pColletion = employeeMappingMenuService.getUserPersseion(__user.getParentemployeecode());
					__permissionModel = permissionMenuService.takeUserMenus(__allMenus,__pColletion);
				}
				List<PermissionMenu>  __menus = employeeMappingMenuService.takeMergePermission(__user.getEmployeecode(), __permissionModel);
				Collections.sort(__menus);
				for (PermissionMenu __pm : __menus) {
					//如果是sa用户，则在名称后面显示menucode
					if(isadmin) {
						__pm.setMenuname(__pm.getMenuname() + " [ "+ __pm.getMenucode()+ " ]");
					}
					__pm.setSign(super.encryptString(__pm.getMenucode(), session.getId()));
				}
				String __menu = permissionMenuService.takeFormatMenu(__menus);
				model.addAttribute("menus", __menu);
				model.addAttribute("employeename", __object.get("employeename"));
				model.addAttribute("employeecode", __employeecode);
				return "/permission/user_permission";
			}else{
				return Constant.PAGE_DECODEREFUSE;
			}
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
			return Constant.PAGE_PARAMSERROR;
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
		
	}
	
	/**
	 * 对用户进行授权
	 * @param request
	 * @return
	 */
	@RequestMapping("/auth")
	@ResponseBody
	@RequestInterval(millinsecond=3000)
	@OperationLog(OparetionDescription.ENTERPRISEEMPLOYEEINFO_COMPETENCE)
	public Map<String,Object> authorization(HttpServletRequest request,HttpSession session){
		try {
			Map<String,Object> object = this.getRequestParamters(request);
			String employeecode = (String)object.get("employeecode");
			String menucode = (String)object.get("menucode");
			if(super.decodeSign(employeecode, session.getId())){
				employeecode = employeecode.split("_")[1];
				List<EmployeeMappingMenu> list = new ArrayList<EmployeeMappingMenu>();
				if(StringUtils.isNotBlank(menucode)){
					String[] menus = menucode.split(",");
					for (String s : menus) {
						if(super.decodeSign(s, session.getId())){
							list.add(new EmployeeMappingMenu(employeecode,s.split("_")[1]));
						}
					}
				}
				employeeMappingMenuService.tc_Authorization(employeecode, list);
				return super.packJSON(Constant.BooleanByte.YES, "成功授予权限");
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "授权失败,请稍后尝试");
		}
	}
	
	
	/**
	 * 没有访问权限页面
	 * @return
	 */
	@RequestMapping("/noPrivilege")
	public String PerssionIntercept(){
		return Constant.PAGE_NOPRIVILEGE;
	}
	
	/**
	 * Ajax没有权限返回数据
	 * @return
	 */
	@RequestMapping("/perssionIntercept")
	@ResponseBody
	public Map<String,Object> ajaxPerssionIntercept(){
		return super.packJSON(Constant.BooleanByte.NO, "您没有该功能的访问权限");
	}
	

	/**
	 * 请求间隔访问控制
	 * @return
	 */
	@RequestMapping("/requestInterval")
	public String requestInterval(Model model){
		return Constant.PAGE_INTERVAL;
	}
	
	/**
	 * Ajax请求间隔访问控制
	 * @return
	 */
	@RequestMapping("/ajaxRequestInterval")
	@ResponseBody
	public Map<String,Object> ajaxRequestInterval(HttpServletRequest request){
		return super.packJSON(Constant.BooleanByte.NO, request.getAttribute("intervaltime")+"秒之内不能重复请求");
	}
	
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
