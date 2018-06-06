package com.maven.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.EmployeeMappingMenu;
import com.maven.entity.PermissionMenu;

@Service
public interface PermissionMenuService extends BaseServcie<PermissionMenu>{
	
	/**
	 * 将菜单集合组装成成BUI菜单树
	 * @param menus
	 * @return
	 */
	@DataSource("slave")
	public String takeFormatMenu(List<PermissionMenu> menus) throws Exception;
	/**
	 * 根据用户权限生成BUI主框架菜单
	 * @param menus
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public String[] takeFormatEmployeeMenu(String basePath,Collection<PermissionMenu> employeeMenus, String language) throws Exception;
	/**
	 * 获取用户菜单项
	 * @param menus 用户菜单映射对象
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public Map<String,PermissionMenu> takeUserMenus(Map<String,PermissionMenu> allMenus,Map<String,EmployeeMappingMenu> uMenusMapping) throws Exception;
	
	/**
	 * 获取所有菜单项
	 * @param menus
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public  Map<String, PermissionMenu> takeMenus() throws Exception;
	
	/**
	 * 添加菜单
	 * @param menu
	 * @return
	 */
	@DataSource("master")
	public int addMenu(Map<String,Object> object) throws Exception;
	
	/**
	 * 删除菜单
	 * @param menuCode
	 */
	@DataSource("master")
	public void deleteMenu(Map<String,Object> object) throws Exception;
	
	/**
	 * 修改菜单
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int updateMenu(PermissionMenu menu) throws Exception;
	
	/**
	 * 逻辑删除
	 * @param menucodes
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int logicDelete(Object menucodes)throws Exception;
	
	

}
