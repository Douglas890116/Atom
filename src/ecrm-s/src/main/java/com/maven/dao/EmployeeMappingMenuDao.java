package com.maven.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.EmployeeMappingMenu;
import com.maven.entity.EnterpriseEmployee;
/**
 * 员工权限映射 Dao
 * @author Ethan
 *
 */
@Repository
public interface EmployeeMappingMenuDao extends BaseDao<EmployeeMappingMenu>{
	
	/**
	 * 批量授权
	 * @return
	 */
	public int addBatch(List<EmployeeMappingMenu> list);
	
	/**
	 * 根据员工编码删除权限组
	 * @param map
	 * @throws RuntimeException
	 */
	public void deleteEmployeePermission(String employeecode);
	
	/**
	 * 删除用户权限,同时删除团队成员权限
	 * @param array
	 */
	public void deleteTearmPermission(Object object);
	
	/**
	 * 默认代理授权
	 * @param ee
	 */
	public void agentAuthorization(EnterpriseEmployee ee);
	

}
