package com.maven.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.EmployeeMappingRoleDao;
import com.maven.entity.EmployeeMappingRole;
import com.maven.entity.EnterpriseEmployee;

@Repository
public class EmployeeMappingRoleDaoImpl extends BaseDaoImpl<EmployeeMappingRole> implements EmployeeMappingRoleDao{

	/**
	 * 批量授权
	 * @return
	 */
	public int addBatch(List<EmployeeMappingRole> list) {
		return getSqlSession().insert("EmployeeMappingRole.addBatch", list);
	}
	/**
	 * 根据员工编码删除权限组
	 * @param map
	 * @throws RuntimeException
	 */
	@Override
	public int deleteEmployeePermission(String employeecode) {
		return getSqlSession().delete("EmployeeMappingRole.deleteByEmployeecode", employeecode);
	}

	/**
	 * 删除用户权限
	 */
	@Override
	public int deleteTearmPermission(Object object) {
		return getSqlSession().delete("EmployeeMappingRole.deleteEmployeePesrsssion",object);
	}
	/**
	 * 代理赋默认权限
	 */
	@Override
	public void agentAuthorization(EnterpriseEmployee ee) {
		getSqlSession().insert("EmployeeMappingRole.agentAuthorization",ee);
	}
	
}
