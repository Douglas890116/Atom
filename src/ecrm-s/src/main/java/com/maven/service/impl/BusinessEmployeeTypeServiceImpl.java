package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.dao.BusinessEmployeeTypeDao;
import com.maven.entity.EnterpriseEmployeeType;
import com.maven.service.BusinessEmployeeTypeService;
@Service
public class BusinessEmployeeTypeServiceImpl implements BusinessEmployeeTypeService {
	
	@Autowired
	private BusinessEmployeeTypeDao BusinessEmployeeTypeDao;
	
	/**
	 * 查询所有的用户类型
	 * @return
	 */
	public List<EnterpriseEmployeeType> getAllEmployeeType() {
		
		return BusinessEmployeeTypeDao.getAllEmployeeType();
	}

	/**
	 * 根据条件查询
	 */
	@Override
	public List<EnterpriseEmployeeType> queryEmployeeType(Map<String, Object> map)throws Exception {
		return BusinessEmployeeTypeDao.queryEmployeeType(map);
	}

	/**
	 * 根据条件统计
	 */
	@Override
	public int queryEmployeeTypeCount(Map<String, Object> map) throws Exception{
		return BusinessEmployeeTypeDao.queryEmployeeTypeCount(map);
	}
	/**
	 * 保存用户类型
	 * @param map
	 */
	@Override
	public void saveEmployeeType(Map<String, Object> map) throws Exception {
		BusinessEmployeeTypeDao.saveEmployeeType(map);
	}
	/**
	 * 删除用户类型
	 * @param string
	 * @throws Exception
	 */
	@Override
	public void deleteEmployeeType(String employeetypecode) throws Exception {
		BusinessEmployeeTypeDao.deleteEmployeeType(employeetypecode);
	}
	/**
	 * 修改用户类型
	 * @param parameter
	 * @throws Exception
	 */
	@Override
	public void updateEmployeeType(Map<String, Object> parameter) throws Exception {
		BusinessEmployeeTypeDao.updateEmployeeType(parameter);
	}
	/**
	 * 批量删除用户类型
	 * @param string
	 * @throws Exception
	 */
	@Override
	public void tc_deleteSelect(String[] array) throws Exception {
		BusinessEmployeeTypeDao.deleteSelect(array);
	}

}
