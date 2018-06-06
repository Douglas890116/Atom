package com.maven.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.EmployeeAvailableDao;
import com.maven.entity.EmployeeAvailable;
import com.maven.service.EmployeeAvailableService;

@Service
public class EmployeeAvailableServiceImpl extends BaseServiceImpl<EmployeeAvailable> implements EmployeeAvailableService{

	@Autowired
	private EmployeeAvailableDao employeeAvailableDao;

	@Override
	public boolean insertEmployeeAvailable(EmployeeAvailable ea) {
		int res = employeeAvailableDao.insertEmployeeAvailable(ea);
		return res > 0 ? true : false;
	}

	@Override
	public boolean deleteEmployeeAvailable(String employeecode) {
		int res = employeeAvailableDao.deleteEmployeeAvailable(employeecode);
		return res > 0 ? true : false;
	}

	@Override
	public BaseDao<EmployeeAvailable> baseDao() {
		return employeeAvailableDao;
	}

	@Override
	public Class<EmployeeAvailable> getClazz() {
		return EmployeeAvailable.class;
	}
	
}
