package com.maven.controller;

import java.util.ArrayList;
import java.util.Collections;
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

import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.entity.EmployeeMappingMenu;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.PermissionMenu;
import com.maven.entity.PermissionMenu.Enum_menustatus;
import com.maven.entity.PermissionRole;
import com.maven.exception.ArgumentValidationException;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.EmployeeMappingMenuService;
import com.maven.service.EnterpriseService;
import com.maven.service.PermissionMenuService;
import com.maven.service.PermissionRoleService;

@Controller
@RequestMapping("/PermissionRole")
public class PermissionRoleController extends BaseController {
	/** 员工权限映射 */
	@Autowired
	private EmployeeMappingMenuService employeeMappingMenuService;
//	/** 员工查询 */
//	@Autowired
//	private EnterpriseEmployeeService enterpriseEmployeeService;
	/** 权限菜单 */
	@Autowired
	private PermissionMenuService permissionMenuService;
	/** 角色列表 */
	@Autowired
	private PermissionRoleService permissionRoleService;
	/** 企业数据 */
	@Autowired
	private EnterpriseService enterpriseService;
	/** 企业编码名称对照 */
	private Map<String, String> enterpriseMap = new HashMap<String, String>();
	/**
	 * 角色首页
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "role/index";
	}
	/**
	 * 获取角色数据
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/data")
	public Map<String,Object> getRoleList(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> obj = getRequestParamters(request);
			
			/*********非超级管理员时只能查询本团队内的数据************/
			if(!SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode()))
				obj.put("enterprisecode", ee.getEnterprisecode());
			
			List<PermissionRole> roleList = permissionRoleService.selectAll(obj);
			int listCount = permissionRoleService.selectAllCount(obj);
			
			if(roleList != null && roleList.size() > 0) {
				if(enterpriseMap == null || enterpriseMap.size() <= 0) initEnterpriseMap();
				String enterprisecode;
				for (PermissionRole permissionRole : roleList) {
					enterprisecode = permissionRole.getEnterprisecode();
					permissionRole.setEnterprisecode(enterpriseMap.get(enterprisecode));
				}
			}
			
			Map<String,String> ss = new HashMap<String, String>();
			ss.put("rolecode", "sign");
			super.encryptSignTarget(roleList, session.getId(), ss);
			
			return formatPagaMap(roleList, listCount);
		} catch (Exception e) {
			log.Error("获取角色集合报错", e);
		}
		return null;
	}
	/**
	 * 新增角色
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	@SuppressWarnings("unchecked")
	public String addRole(HttpSession session, Model model) {
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);

			/********* 非超级管理员时只能查询本团队内的数据 ************/
			boolean isadmin = false;
			if (SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())) isadmin = true;

			Map<String, PermissionMenu> __permissionModel = null;
			if (isadmin) {
				__permissionModel = (Map<String, PermissionMenu>) session.getAttribute(Constant.SYSTEM_PSERSSION);
			} else {
				Map<String, PermissionMenu> __allMenus = (Map<String, PermissionMenu>) session.getAttribute(Constant.SYSTEM_PSERSSION);
				Map<String, EmployeeMappingMenu> __pColletion = employeeMappingMenuService.getUserPersseion(ee.getEmployeecode());
				__permissionModel = permissionMenuService.takeUserMenus(__allMenus, __pColletion);
			}
			List<PermissionMenu> __menus = new ArrayList<PermissionMenu>(__permissionModel.values());
//			List<PermissionMenu> __menus = employeeMappingMenuService.takeMergePermission(ee.getEmployeecode(), __permissionModel);
			
			Collections.sort(__menus);
			for (PermissionMenu __pm : __menus) {
				// 如果是sa用户，则在名称后面显示menucode
				if (isadmin) __pm.setMenuname(__pm.getMenuname() + " [ " + __pm.getMenucode() + " ]");
				__pm.setSign(super.encryptString(__pm.getMenucode(), session.getId()));
				__pm.setExpanded(true);
			}
			String __menu = permissionMenuService.takeFormatMenu(__menus);
			model.addAttribute("menus", __menu);
			
			return "/role/add";
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_PARAMSERROR;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	/**
	 * 保存新增角色
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/save")
	public Map<String, Object> saveRole(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> params = getRequestParamters(request);
			String[] permissionArray = String.valueOf(params.get("menucode")).split(",");

			String permissions = "";
			if( permissionArray != null && permissionArray.length > 0 && !"null".equals(permissionArray[0])) {
				StringBuffer sb = new StringBuffer(permissions);
				for (String str : permissionArray)
					sb.append(str.split("_")[1]).append("|");
				permissions = sb.substring(0, sb.length() - 1);
			}
			
			PermissionRole role = new PermissionRole();
			role.setRolename(params.get("rolename").toString());
			role.setEnterprisecode(ee.getEnterprisecode());
			role.setPermissions(permissions);
			role.setDatastatus(Constant.Enum_DataStatus.正常.value);
			
			int result = permissionRoleService.addRole(role);
			
			if(result > 0)
				return packJSON(Constant.BooleanByte.YES, "角色保存成功!");
			else
				return packJSON(Constant.BooleanByte.NO, "角色保存失败，请稍后再试!");
		} catch (Exception e) {
			log.Error("保存新增角色报错", e);
			return packJSON(Constant.BooleanByte.NO, "系统错误，请稍后再试!");
		}
	}
	/**
	 * 克隆角色
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/clone")
	public Map<String, Object> cloneRole(HttpServletRequest request, HttpSession session) {
		try {
			String rolecode = request.getParameter("rolecode");
			if (decodeSign(rolecode, session.getId())) {
				rolecode = rolecode.split("_")[1];
				Map<String, Object> obj = new HashMap<String, Object>();
				obj.put("rolecode", rolecode);
				PermissionRole role = permissionRoleService.selectByRolecode(rolecode);
				role.setRolecode(null);
				role.setRolename("[克隆] " + role.getRolename());
				
				int result = permissionRoleService.addRole(role);
				
				if(result > 0) {
					return packJSON(Constant.BooleanByte.YES, "角色克隆成功!");
				} else {
					return packJSON(Constant.BooleanByte.NO, "角色克隆失败，请稍后再试!");
				}
			} else {
				return packJSON(Constant.BooleanByte.NO, "解密错误，无权操作!");
			}
		} catch (Exception e) {
			log.Error("克隆角色报错", e);
			return packJSON(Constant.BooleanByte.NO, "系统错误，请稍后再试!");
		}
	}
	/**
	 * 编辑角色
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit")
	@SuppressWarnings("unchecked")
	public String editRole(HttpServletRequest request, HttpSession session, Model model) {
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			String rolecode = request.getParameter("rolecode");
			if (decodeSign(rolecode, session.getId())) {
				rolecode = rolecode.split("_")[1];
				Map<String, Object> obj = new HashMap<String, Object>();
				obj.put("rolecode", rolecode);
				PermissionRole role = permissionRoleService.selectByRolecode(rolecode);
				String permissions = role.getPermissions();
				
				/********* 非超级管理员时只能查询本团队内的数据 ************/
				boolean isadmin = false;
				if (SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())) isadmin = true;

				Map<String, PermissionMenu> __permissionModel = null;
				if (isadmin) {
					__permissionModel = (Map<String, PermissionMenu>) session.getAttribute(Constant.SYSTEM_PSERSSION);
				} else {
					Map<String, PermissionMenu> __allMenus = (Map<String, PermissionMenu>) session.getAttribute(Constant.SYSTEM_PSERSSION);
					Map<String, EmployeeMappingMenu> __pColletion = employeeMappingMenuService.getUserPersseion(ee.getEmployeecode());
					__permissionModel = permissionMenuService.takeUserMenus(__allMenus, __pColletion);
				}
				
				List<PermissionMenu> __menus = takeMergePermissionRole(permissions, new ArrayList<PermissionMenu>(__permissionModel.values()));
				
				Collections.sort(__menus);
				for (PermissionMenu __pm : __menus) {
					// 如果是sa用户，则在名称后面显示menucode
					if (isadmin) __pm.setMenuname(__pm.getMenuname() + " [ " + __pm.getMenucode() + " ]");
					__pm.setSign(super.encryptString(__pm.getMenucode(), session.getId()));
					__pm.setExpanded(true);
				}
				
				String __menu = permissionMenuService.takeFormatMenu(__menus);
				
				model.addAttribute("menus", __menu);
				model.addAttribute("rolecode", role.getRolecode());
				model.addAttribute("rolename", role.getRolename());
				return "/role/edit";
			} else {
				return Constant.PAGE_DECODEREFUSE;
			}
		} catch (Exception e) {
			log.Error("编辑角色信息报错", e);
			return Constant.PAGE_ERROR;
		}
	}
	/**
	 * 更新角色信息
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> updateRole(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> params = getRequestParamters(request);
			String rolecode = params.get("rolecode").toString();
			String rolename = params.get("rolename").toString();
			String[] permissionArray = String.valueOf(params.get("menucode")).split(",");
			
			String permissions = "";
			if(permissionArray != null && permissionArray.length > 0 && !"null".equals(permissionArray[0])) {
				StringBuffer sb = new StringBuffer(permissions);
				for (String str : permissionArray)
					sb.append(str.split("_")[1]).append("|");
				permissions = sb.substring(0, sb.length() - 1);
			}
			
			PermissionRole role = new PermissionRole();
			role.setRolecode(rolecode);
			role.setRolename(rolename);
			role.setPermissions(permissions);
			
			int result = permissionRoleService.updateRole(role);
			if(result > 0)
				return packJSON(Constant.BooleanByte.YES, "角色更新成功!");
			else
				return packJSON(Constant.BooleanByte.NO, "角色更新失败!");
		} catch (Exception e) {
			log.Error("更新角色信息报错", e);
			return packJSON(Constant.BooleanByte.NO, "系统错误，请稍后再试!");
		}
	}
	/**
	 * 删除角色-逻辑删除
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public Map<String, Object> deleteRole(HttpServletRequest request, HttpSession session) {
		try {
			String rolecode = request.getParameter("rolecode");
			if(decodeSign(rolecode, session.getId())) {
				int result = permissionRoleService.logicDelete(rolecode.split("_")[1]);
				if (result > 0)
					return packJSON(Constant.BooleanByte.YES, "删除成功!");
				else
					return packJSON(Constant.BooleanByte.NO, "删除失败!");
			} else {
				return packJSON(Constant.BooleanByte.NO, "解密失败!");
			}
		} catch (Exception e) {
			log.Error("删除角色报错", e);
			return packJSON(Constant.BooleanByte.NO, "系统错误，请稍后再试!");
		}
	}
	
	/**
	 * 批量删除角色-逻辑删除
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/batchDelete")
	public Map<String, Object> batchDeleteRole(HttpServletRequest request, HttpSession session) {
		try {
			String temp = request.getParameter("sign");
			String[] array = temp.split(",");
			if(decodeSign(array, session.getId())) {
				for (int i = 0; i < array.length; i++) {
					array[i]=array[i].split("_")[1];
				}
				
				int result = 0;
				for (String rolecode : array)
					result += permissionRoleService.logicDelete(rolecode);
				
				if (result > 0)
					return packJSON(Constant.BooleanByte.YES, "删除角色数据 [ " + result + " ] 条!");
				else
					return packJSON(Constant.BooleanByte.NO, "删除失败!");
			} else {
				return packJSON(Constant.BooleanByte.NO, "解密失败!");
			}
		} catch (Exception e) {
			log.Error("删除角色报错", e);
			return packJSON(Constant.BooleanByte.NO, "系统错误，请稍后再试!");
		}
	}
	/** 初始化用户的权限信息 */
	private List<PermissionMenu> takeMergePermissionRole(String permissions, List<PermissionMenu> __menus)
			throws CloneNotSupportedException {
		if (StringUtils.isNotBlank(permissions)) {
			String[] p = permissions.split("[|]");
			List<PermissionMenu> menus = new ArrayList<PermissionMenu>();
			begin: for (PermissionMenu e : __menus) {
				PermissionMenu pm = (PermissionMenu) e.clone();
				for (String str : p) {
					if (str.equals(e.getMenucode())) {
						pm.setMenustatus(Enum_menustatus.启用.value);
						menus.add(pm);
						continue begin;
					}
				}
				pm.setMenustatus(Enum_menustatus.禁用.value);
				menus.add(pm);
			}
			return menus;
		} else {
			for (PermissionMenu p : __menus) {
				p.setMenustatus(Enum_menustatus.禁用.value);
			}
			return __menus;
		}
	}
	
	/** 获取企业名称键值对 */
	private void initEnterpriseMap() throws Exception {
		List<Enterprise> enterpriseList = enterpriseService.selectAll(null);
		if(enterpriseList != null && enterpriseList.size() > 0) {
		for (Enterprise enterprise : enterpriseList)
			enterpriseMap.put(enterprise.getEnterprisecode(), enterprise.getEnterprisename());
		}
	}
	private static LoggerManager log = LoggerManager.getLogger(PermissionMenuController.class.getName(), OutputManager.LOG_PERMISSIONROLE);
	@Override
	public LoggerManager getLogger() { return log; }
}