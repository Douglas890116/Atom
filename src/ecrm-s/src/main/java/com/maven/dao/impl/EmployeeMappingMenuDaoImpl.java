package com.maven.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.EmployeeMappingMenuDao;
import com.maven.entity.EmployeeMappingMenu;
import com.maven.entity.EnterpriseEmployee;

@Repository
public class EmployeeMappingMenuDaoImpl extends BaseDaoImpl<EmployeeMappingMenu> 
	implements EmployeeMappingMenuDao{

	/**
	 * 批量授权
	 * @return
	 */
	public int addBatch(List<EmployeeMappingMenu> list) {
		return getSqlSession().insert("EmployeeMappingMenu.addBatch", list);
	}
	/**
	 * 根据员工编码删除权限组
	 * @param map
	 * @throws RuntimeException
	 */
	@Override
	public void deleteEmployeePermission(String employeecode) {
		getSqlSession().delete("EmployeeMappingMenu.deleteByEmployeecode", employeecode);
	}

	
	@Override
	public void deleteTearmPermission(Object object) {
		getSqlSession().delete("EmployeeMappingMenu.deleteEmployeePesrsssion",object);
	}
	@Override
	public void agentAuthorization(EnterpriseEmployee ee) {
		getSqlSession().delete("EmployeeMappingMenu.agentAuthorization",ee);
	}
	
}
