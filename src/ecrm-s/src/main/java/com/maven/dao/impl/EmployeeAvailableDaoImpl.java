package com.maven.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.EmployeeAvailableDao;
import com.maven.entity.EmployeeAvailable;

@Repository
public class EmployeeAvailableDaoImpl extends BaseDaoImpl<EmployeeAvailable> implements EmployeeAvailableDao{

	
	public List<EmployeeAvailable> selectByDate(Date date) {
		return getSqlSession().selectList("EmployeeAvailable.selectByDate", date);
	}

	@Override
	public int insertEmployeeAvailable(EmployeeAvailable ea) {
		return getSqlSession().insert("EmployeeAvailable.add", ea);
	}

	@Override
	public int deleteEmployeeAvailable(String employeecode) {
		return getSqlSession().delete("EmployeeAvailable.deleteByPrimaryKey", employeecode);
	}

}
