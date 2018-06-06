package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.entity.EmployeeMappingMenu;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.PermissionMenu;
/**
 * 用户权限组Service
 * @author Ethan
 *
 */
@Service
public interface EmployeeMappingMenuService{
	
	/**
	 * 对用户进行授权
	 * @param 
	 *  employeecode 用户编码
	 *  list  对象填充属性： employeecode ， permissiongroupcode
	 *  失败自动回滚
	 */
	@DataSource("master")
	public void tc_Authorization(String employeecode,List<EmployeeMappingMenu> list) throws Exception;
	
	/**
	 * 对代理类型用户进行默认权限继承
	 * @param ee
	 * @throws Exception
	 */
	@DataSource("master")
	public void tc_agentAuthorization(EnterpriseEmployee ee) throws Exception;
	
	/**
	 * 获取用户权限
	 * @param userCode
	 * @return
	 */
	@DataSource("slave")
	public Map<String,EmployeeMappingMenu> getUserPersseion(String userCode)  throws Exception;

	/**
	 * 将用户权限装载到模板权限，用于对用户进行授权
	 * @param employeecode
	 * @param permissionModel
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<PermissionMenu> takeMergePermission(String employeecode,Map<String,PermissionMenu> permissionModel) throws Exception;
	
}
