package com.maven.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.EmployeeApiAccoutDao;
import com.maven.entity.EmployeeApiAccout;
import com.maven.service.EmployeeApiAccoutService;
import com.maven.util.AttrCheckout;

@Service
public class EmployeeApiAccoutServiceImpl extends BaseServiceImpl<EmployeeApiAccout> implements EmployeeApiAccoutService{

	@Autowired
	private EmployeeApiAccoutDao employeeApiAccoutDao;
	
	@Override
	public BaseDao<EmployeeApiAccout> baseDao() {
		return employeeApiAccoutDao;
	}

	@Override
	public Class<EmployeeApiAccout> getClazz() {
		return EmployeeApiAccout.class;
	}

	@Override
	public List<EmployeeApiAccout> getEmployeeApiAccoutByObject(EmployeeApiAccout object) throws Exception {
		return super.select(object);
	}
	
	/**
	 * 根据游戏账号和类型查找对象
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public EmployeeApiAccout getEmployeeApiAccoutByGameaccount(EmployeeApiAccout object) throws Exception {
		List<EmployeeApiAccout> list = super.select(AttrCheckout.checkout(object,false, new String[]{"gameaccount","gametype"}));
		if(list==null || list.size()==0) return null;
		return list.get(0);
	}

	@Override
	public EmployeeApiAccout getEmployeeApiAccout(EmployeeApiAccout object) throws Exception {
		List<EmployeeApiAccout> list = super.select(AttrCheckout.checkout(object,false, new String[]{"employeecode","gametype"}));
		if(list==null || list.size()==0) return null;
		return list.get(0);
	}

	@Override
	public List<EmployeeApiAccout> getAllEmployeeApiAccout(String employeecode) throws Exception {
		return super.select(AttrCheckout.checkout(new EmployeeApiAccout(null,employeecode),false, new String[]{"employeecode"}));
	}
	
	@Override
	public int tc_createApiAccount(EmployeeApiAccout object) throws Exception {
		return super.add(AttrCheckout.checkout(object,false, new String[]{"enterprisecode","gametype","employeecode","parentemployeecode","loginaccount","gameaccount","gamepassword"}));
	}

	@Override
	public int updatePassword(EmployeeApiAccout object) throws Exception { 
		return super.update(AttrCheckout.checkout(object,false, new String[]{"enterprisecode","gameaccount","gamepassword","employeecode","loginaccount","gametype"}));
	}
	
	/**
	 * 禁用选中的一条或者多条数据
	 * @param String[] array
	 */
	public void tc_disableSelectEmployee(String[] array) throws Exception{
		employeeApiAccoutDao.tc_disableSelectEmployee(array);
	}
	/**
	 * 启用选中的一条或者多条数据
	 * @param String[] array
	 */
	public void tc_activateSelectEmployee(String[] array) throws Exception{
		employeeApiAccoutDao.tc_activateSelectEmployee(array);
	}
	
	@Override
	public List<EmployeeApiAccout> selectUnionGName(EmployeeApiAccout eaa) {
		return employeeApiAccoutDao.selectUnionGName(eaa);
	}

}
