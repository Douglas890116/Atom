package com.maven.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.EmployeeMappingRole;
import com.maven.entity.EnterpriseEmployee;
/**
 * 员工权限映射 Dao
 * @author Ethan
 *
 */
@Repository
public interface EmployeeMappingRoleDao extends BaseDao<EmployeeMappingRole>{
	
	/**
	 * 批量授权
	 * @return
	 */
	public int addBatch(List<EmployeeMappingRole> list);
	
	/**
	 * 根据员工编码删除角色
	 * @param map
	 * @throws RuntimeException
	 */
	public int deleteEmployeePermission(String employeecode);
	
	/**
	 * 删除用户角色,同时删除团队成员权限
	 * @param array
	 */
	public int deleteTearmPermission(Object object);
	
	/**
	 * 默认代理授权
	 * @param ee
	 */
	public void agentAuthorization(EnterpriseEmployee ee);
}