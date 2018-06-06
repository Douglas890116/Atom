package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.PermissionRole;

@Service
public interface PermissionRoleService extends BaseServcie<PermissionRole>{
	/**
	 * 添加角色
	 * @param role
	 * @return
	 */
	@DataSource("master")
	public int addRole(PermissionRole role) throws Exception;
	
	/**
	 * 删除角色
	 * @param menuCode
	 */
	@DataSource("master")
	public int deleteRole(String rolecode) throws Exception;
	
	/**
	 * 逻辑删除
	 * @param menucodes
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int logicDelete(String rolecode)throws Exception;
	
	/**
	 * 修改角色
	 * @param role
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int updateRole(PermissionRole role) throws Exception;
	
	/**
	 * 获取所有角色
	 * @param roles
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<PermissionRole> selectAll(Map<String,Object> object) throws Exception;
	/**
	 * 获取所有角色总数
	 * @param roles
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public int selectAllCount(Map<String,Object> object) throws Exception;
	/**
	 * 根据rolecode获取角色信息
	 * @param rolecode
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public PermissionRole selectByRolecode(String rolecode) throws Exception;

}