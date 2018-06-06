package com.maven.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.PermissionRoleDao;
import com.maven.entity.EmployeeMappingRole;
import com.maven.entity.PermissionRole;
import com.maven.service.EmployeeMappingRoleService;
import com.maven.service.PermissionRoleService;
import com.maven.util.AttrCheckout;
@Service
public class PermissionRoleServiceImpl extends BaseServiceImpl<PermissionRole> implements PermissionRoleService {
	
	@Autowired
	private PermissionRoleDao permissionRoleDao;
	
	@Autowired
	private EmployeeMappingRoleService employeeMappingRoleService;
	
	@Override
	public BaseDao<PermissionRole> baseDao() {
		return permissionRoleDao;
	}
	
	@Override
	public Class<PermissionRole> getClazz() {
		return PermissionRole.class;
	}
	/** 新增角色 */
	@Override
	public int addRole(PermissionRole role) throws Exception {
		return super.add(role);
	}
	/** 物理删除角色 */
	@Override
	public int deleteRole(String rolecode) throws Exception {
		return super.delete(rolecode);

	}
	/** 逻辑删除 */
	@Override
	public int logicDelete(String rolecode) throws Exception {
		return super.logicDelete(rolecode);
	}
	/** 修改角色 */
	@Override
	public int updateRole(PermissionRole role) throws Exception {
		AttrCheckout.checkout(role, false, new String[]{"rolecode"});
		
		PermissionRole data = selectByPrimaryKey(role.getRolecode());
		boolean isSame = role.compareTo(data) == 0;
		
		int result = super.update(role);
		if (isSame) return result;
		if (result == 1) {
			Map<String, EmployeeMappingRole> userRoleMap = employeeMappingRoleService.getPermissionUser(role.getRolecode());
			Set<String> users = userRoleMap.keySet();
			List<EmployeeMappingRole> emrList = new ArrayList<EmployeeMappingRole>();
			List<String> rolecodeList = new ArrayList<String>();
			for (String employeecode : users) {
				emrList = employeeMappingRoleService.getUserPermissionList(employeecode);
				for (EmployeeMappingRole permissionrole : emrList) {
					rolecodeList.add(permissionrole.getRolecode());
				}
				// 保存用户授权的角色信息
				employeeMappingRoleService.tc_Authorization(employeecode, emrList, rolecodeList);
			}
			return 1;
		}
		return -1;
	}
	/** 根据条件获取所有角色 */
	@Override
	public List<PermissionRole> selectAll(Map<String, Object> object) throws Exception {
		return super.selectAll(object);
	}
	/** 根据条件获取所有角色总数 */
	@Override
	public int selectAllCount(Map<String,Object> object) throws Exception {
		return super.selectAllCount(object);
	}
	/** 根据主键查找角色信息 */
	@Override
	public PermissionRole selectByRolecode(String rolecode) throws Exception {
		return super.selectByPrimaryKey(rolecode);
	}
	/**============================== 神奇的分割线 ==============================**/
}