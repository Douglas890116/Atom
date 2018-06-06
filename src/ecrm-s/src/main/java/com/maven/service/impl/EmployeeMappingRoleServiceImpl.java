package com.maven.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.EmployeeMappingRoleDao;
import com.maven.entity.EmployeeMappingMenu;
import com.maven.entity.EmployeeMappingRole;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.PermissionRole;
import com.maven.service.EmployeeMappingMenuService;
import com.maven.service.EmployeeMappingRoleService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.PermissionRoleService;
import com.maven.util.EmployeeMappingMenuTemp;

import edu.emory.mathcs.backport.java.util.Arrays;

@Service
public class EmployeeMappingRoleServiceImpl extends BaseServiceImpl<EmployeeMappingRole>
		implements EmployeeMappingRoleService {
	/** 权限角色 */
	@Autowired
	private PermissionRoleService permissionRoleService;
	/** 用户角色对应 */
	@Autowired
	private EmployeeMappingRoleDao employeeMappingRoleDao;
	/** 用户查询 */
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	/** 员工权限映射 */
	@Autowired
	private EmployeeMappingMenuService employeeMappingMenuService;
	

	@SuppressWarnings("unchecked")
	@Override
	public void tc_Authorization(String employeecode, List<EmployeeMappingRole> list, List<String> rolecodes) throws Exception {
		Map<String,EmployeeMappingRole> employeeRole = new HashMap<String, EmployeeMappingRole>();
		for (EmployeeMappingRole e : list) {
			employeeRole.put(e.getRolecode().trim(), e);
		}
		Map<String,EmployeeMappingRole> perssion = this.getUserPermission(employeecode);
		List<EmployeeMappingRole> delPeserssion = new ArrayList<EmployeeMappingRole>();
		List<EmployeeMappingRole> addPeserssion = new ArrayList<EmployeeMappingRole>();
		for (EmployeeMappingRole er : employeeRole.values()) {
			if(perssion.get(er.getRolecode())==null){
				addPeserssion.add(er);
			}
		}
		for (EmployeeMappingRole em : perssion.values()) {
			if(employeeRole.get(em.getRolecode())==null){
				delPeserssion.add(em);
			}
		}
		
		if(delPeserssion.size()>0){
			Map<String,Object> object = new HashMap<String, Object>();
			object.put("rolecodes", delPeserssion);
			object.put("employeecodes", enterpriseEmployeeService.call_ufnTakeTeamAgent(employeecode).split(","));
			employeeMappingRoleDao.deleteTearmPermission(object);
		}
		if(addPeserssion.size()>0){
			employeeMappingRoleDao.addBatch(addPeserssion);
		}
		Map<String, Object> object = new HashMap<String, Object>();
		List<EmployeeMappingMenu> emmList = new ArrayList<EmployeeMappingMenu>();
		
		if(rolecodes != null && rolecodes.size() > 0) {
			object.put("rolecodes", rolecodes);
			List<PermissionRole> roleList = permissionRoleService.selectAll(object);
			StringBuffer permissions = new StringBuffer();
			if(null != roleList && roleList.size() > 0) {
				for (PermissionRole pr : roleList) {
					if(StringUtils.isNotBlank(pr.getPermissions())
							&& !"null".equals(pr.getPermissions())) {
						permissions.append("|").append(pr.getPermissions());
					}
				}
				if (permissions.length() > 1) {
					String[] menucodes = permissions.substring(1, permissions.length()).split("[|]");
					Set<String> codes = new HashSet<String>(Arrays.asList(menucodes));
					for (String code : codes) {
						emmList.add(new EmployeeMappingMenu(employeecode, code));
					}
				}
			}
		}
		employeeMappingMenuService.tc_Authorization(employeecode, emmList);
	}

	@Override
	public Map<String, EmployeeMappingRole> getUserPermission(String employeecode) throws Exception {
		Map<String,EmployeeMappingRole> employeeRole = new HashMap<String, EmployeeMappingRole>();
		List<EmployeeMappingRole> permissiongroups = super.select(new EmployeeMappingRole(employeecode, null));
		for (EmployeeMappingRole e : permissiongroups) {
			employeeRole.put(e.getRolecode(), e);
		}
		return employeeRole;
	}
	@Override
	public List<EmployeeMappingRole> getUserPermissionList(String employeecode)  throws Exception {
		return super.select(new EmployeeMappingRole(employeecode, null));
	}
	
	@Override
	public Map<String,EmployeeMappingRole> getPermissionUser(String rolecode) throws Exception {
		Map<String,EmployeeMappingRole> employeeRole = new HashMap<String, EmployeeMappingRole>();
		List<EmployeeMappingRole> permissiongroups = super.select(new EmployeeMappingRole(null, rolecode));
		for (EmployeeMappingRole e : permissiongroups) {
			employeeRole.put(e.getEmployeecode(), e);
		}
		return employeeRole;
	}

	@Override
	public BaseDao<EmployeeMappingRole> baseDao() {
		return employeeMappingRoleDao;
	}

	@Override
	public Class<EmployeeMappingRole> getClazz() {
		return EmployeeMappingRole.class;
	}

}
