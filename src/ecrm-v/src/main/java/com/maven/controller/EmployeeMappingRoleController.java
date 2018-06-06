package com.maven.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.annotation.OperationLog;
import com.maven.annotation.RequestInterval;
import com.maven.constant.Constant;
import com.maven.entity.EmployeeMappingRole;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.PermissionMenu.Enum_menustatus;
import com.maven.entity.PermissionRole;
import com.maven.logger.LoggerManager;
import com.maven.logger.OparetionDescription;
import com.maven.logger.OutputManager;
import com.maven.service.EmployeeMappingRoleService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.PermissionRoleService;

/**
 * 批量授权Control
 * @author Ethan
 *
 */
@Controller
@RequestMapping("/EmployeeRole")
public class EmployeeMappingRoleController extends BaseController {
	
	private static LoggerManager log = LoggerManager.getLogger(EmployeeMappingMenuController.class.getName(), OutputManager.LOG_EMPLOYEEMAPPINGROLE);
	
	/** 权限角色 */
	@Autowired
	private PermissionRoleService permissionRoleService;
	/** 员工信息 */
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	/** 员工角色映射 */
	@Autowired
	private EmployeeMappingRoleService employeeMappingRoleService;
	
	
	/**
	 * 获取企业角色列表 并 获取请求用户的拥有的角色
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/loadRoleList")
	public Map<String, Object> getEmployeeRoles(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> data = new HashMap<String, Object>();
			Map<String, Object> object = getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			object.put("enterprisecode", ee.getEnterprisecode());
			
			List<PermissionRole> prList = permissionRoleService.selectAll(object);
			data.put("enterpriseRoleList", prList);
			
			String employeecode = String.valueOf(object.get("employeecode"));
			List<EmployeeMappingRole> emrList = employeeMappingRoleService.getUserPermissionList(employeecode);
			data.put("employeeRoleList", emrList);
			
			return super.packJSON(Constant.BooleanByte.YES, "", data);
		} catch (Exception e) {
			log.Error("获取企业及用户角色标错", e);
			return super.packJSON(Constant.BooleanByte.NO, "系统错误，请稍后再试");
		}
	}
	
	/**
	 * 加载用户角色
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"/permission","/agentpermission"})
	public String loadEmployeeRole(HttpServletRequest request, HttpSession session, Model model) {
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			
			Map<String,Object> object = super.getRequestParamters(request);
			String employeecode = String.valueOf(object.get("employeecode"));
			if(super.decodeSign(employeecode, session.getId())) {
				
				employeecode = employeecode.split("_")[1];
				
				EnterpriseEmployee user = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
				
				object.clear();
				
				// 加载当前管理员企业的角色信息 
				object.put("enterprisecode", ee.getEnterprisecode());
				List<PermissionRole> roleList = permissionRoleService.selectAll(object);
				
				// 加载用户角色
				Map<String, EmployeeMappingRole> userRole = employeeMappingRoleService.getUserPermission(employeecode);
				
				for (PermissionRole pr : roleList) {
					if(userRole.get(pr.getRolecode()) != null)
						pr.setRolestatus(Enum_menustatus.启用.value);
				}
				
				model.addAttribute("rolelist", roleList);
				model.addAttribute("employeecode", user.getEmployeecode());
				model.addAttribute("loginaccount", user.getLoginaccount());
				
				return "/role/employeerole";
			} else {
				return Constant.PAGE_DECODEREFUSE;
			}
		} catch (Exception e) {
			log.Error("加载用户mapping角色报错", e);
			return Constant.PAGE_ERROR;
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
	public Map<String, Object> authorization(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> object = this.getRequestParamters(request);
			String employeecode = object.get("employeecode").toString();
			String rolecodes = object.get("rolecodes").toString();
			List<EmployeeMappingRole> emrList = new ArrayList<EmployeeMappingRole>();
			List<String> rolecodeList = new ArrayList<String>();
			if (StringUtils.isNotBlank(rolecodes)) {
				String[] rolecode = rolecodes.split(",");
				for (String code : rolecode) {
					emrList.add(new EmployeeMappingRole(employeecode, code));
					rolecodeList.add(code);
				}
			}
			// 保存用户授权的角色信息
			employeeMappingRoleService.tc_Authorization(employeecode, emrList, rolecodeList);
			
			return super.packJSON(Constant.BooleanByte.YES, "成功授予权限");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "授权失败,请稍后尝试");
		}
	}
	
	@Override
	public LoggerManager getLogger() { return log; }
}