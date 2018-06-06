package com.maven.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.EmployeeMappingMenuDao;
import com.maven.entity.EmployeeMappingMenu;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.PermissionMenu;
import com.maven.entity.PermissionMenu.Enum_menustatus;
import com.maven.service.EmployeeMappingMenuService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.util.AttrCheckout;
import com.maven.util.EmployeeMappingMenuTemp;
/**
 * 用户权限组ServiceImpl
 * @author Ethan
 *
 */
@Service
public class EmployeeMappingMenuServiceImpl extends BaseServiceImpl<EmployeeMappingMenu> 
	implements EmployeeMappingMenuService{
	/*
	 * 员工与权限组映射
	 */
	@Autowired
	private EmployeeMappingMenuDao employeeMappingMenuDaoDao; 
	
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	
	
	@Override
	public BaseDao<EmployeeMappingMenu> baseDao() {
		return employeeMappingMenuDaoDao;
	}

	@Override
	public Class<EmployeeMappingMenu> getClazz() {
		return EmployeeMappingMenu.class;
	}


	/**
	 * 对用户进行授权
	 */
	@Override
	public void tc_Authorization(String employeecode,List<EmployeeMappingMenu> list)throws Exception{
		Map<String,EmployeeMappingMenu> employeeMenu = new HashMap<String, EmployeeMappingMenu>();
		for (EmployeeMappingMenu e : list) {
			employeeMenu.put(e.getMenucode().trim(), e);
		}
		Map<String,EmployeeMappingMenu> perssion = this.getUserPersseion(employeecode);
		List<EmployeeMappingMenu> delPeserssion = new ArrayList<EmployeeMappingMenu>();
		List<EmployeeMappingMenu> addPeserssion = new ArrayList<EmployeeMappingMenu>();
		for (EmployeeMappingMenu em : employeeMenu.values()) {
			if(perssion.get(em.getMenucode())==null){
				addPeserssion.add(em);
			}
		}
		for (EmployeeMappingMenu em : perssion.values()) {
			if(employeeMenu.get(em.getMenucode())==null){
				delPeserssion.add(em);
			}
		}
		
		if(delPeserssion.size()>0){
			Map<String,Object> object = new HashMap<String, Object>();
			object.put("menucodes", delPeserssion);
			object.put("employeecodes", enterpriseEmployeeService.call_ufnTakeTeamAgent(employeecode).split(","));
			employeeMappingMenuDaoDao.deleteTearmPermission(object);
		}
		if(addPeserssion.size()>0){
			employeeMappingMenuDaoDao.addBatch(addPeserssion);
		}
	}
	
	/**
	 * 查询用户的权限
	 * @param userCode
	 * @throws Exception 
	 */
	public Map<String,EmployeeMappingMenu> getUserPersseion(String userCode) throws Exception{
		Map<String,EmployeeMappingMenu> employeeMenu = new HashMap<String, EmployeeMappingMenu>();
		List<EmployeeMappingMenu> permissiongroups = super.select(new EmployeeMappingMenu(userCode, null));
		for (EmployeeMappingMenu e : permissiongroups) {
			employeeMenu.put(e.getMenucode(), e);
		}
		return employeeMenu;
	}

	/**
	 * 将用户权限装载到模板权限
	 */
	@Override
	public List<PermissionMenu> takeMergePermission(String employeecode, Map<String, PermissionMenu> permissionModel) throws Exception {
		Map<String, EmployeeMappingMenu> userPermission = this.getUserPersseion(employeecode);
		List<PermissionMenu> menus = new ArrayList<PermissionMenu>();
		for (PermissionMenu e : permissionModel.values()) {
			PermissionMenu pm = (PermissionMenu)e.clone();
			if(userPermission.get(e.getMenucode())!=null){
				pm.setMenustatus(Enum_menustatus.启用.value);
			}else{
				pm.setMenustatus(Enum_menustatus.禁用.value);
			}
			pm.setExpanded(true);
			menus.add(pm);
		}
		return menus;
	}

	/**
	 * 代理类型用户集成默认权限
	 */
	@Override
	public void tc_agentAuthorization(EnterpriseEmployee ee) throws Exception {
		
//		employeeMappingMenuDaoDao.agentAuthorization(AttrCheckout.checkout(ee, false, new String[]{"employeecode","parentemployeecode"}));//jason注释，默认是继承了他的上级，也就是企业号
		
		
		//获取所有菜单项
		List<EmployeeMappingMenu> list = new ArrayList<EmployeeMappingMenu>();
		
		Iterator<String> obj = EmployeeMappingMenuTemp.agentmenu.keySet().iterator();
		while (obj.hasNext()) {
			String menucode = (String) obj.next();
			list.add(new EmployeeMappingMenu(ee.getEmployeecode(), menucode));
		}
		tc_Authorization(ee.getEmployeecode(), list);//保存
	}
	
}
