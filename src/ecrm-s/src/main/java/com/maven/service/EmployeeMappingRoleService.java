package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.entity.EmployeeMappingRole;
/**
 * 用户权限组Service
 * @author Ethan
 *
 */
@Service
public interface EmployeeMappingRoleService{
	
	/**
	 * 对用户进行授权
	 * @param 
	 *  employeecode 用户编码
	 *  list  对象填充属性： employeecode ， permissiongroupcode
	 *  失败自动回滚
	 */
	@DataSource("master")
	public void tc_Authorization(String employeecode,List<EmployeeMappingRole> list, List<String> rolecodes) throws Exception;
	
	/**
	 * 获取用户权限
	 * @param employeecode
	 * @return
	 */
	@DataSource("slave")
	public Map<String,EmployeeMappingRole> getUserPermission(String employeecode)  throws Exception;

	/**
	 * 获取用户权限List
	 * @param employeecode
	 * @return
	 */
	@DataSource("slave")
	public List<EmployeeMappingRole> getUserPermissionList(String employeecode)  throws Exception;
	/**
	 * 获取拥有该权限的用户
	 * @param rolecode 角色code
	 * @return <用户code， 角色信息>
	 * @throws Exception
	 */
	@DataSource("slave")
	public Map<String,EmployeeMappingRole> getPermissionUser(String rolecode) throws Exception;
}
