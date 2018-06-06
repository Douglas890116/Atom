package com.maven.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.EmployeeApiAccoutDao;
import com.maven.entity.EmployeeApiAccout;

@Repository
public class EmployeeApiAccoutDaoImpl extends BaseDaoImpl<EmployeeApiAccout> implements EmployeeApiAccoutDao{

	@Override
	public List<EmployeeApiAccout> selectUnionGName(EmployeeApiAccout eaa) {
		return getSqlSession().selectList("EmployeeApiAccout.selectUnionGName",eaa);
	}

	/**
	 * 禁用选中的一条或者多条数据
	 * @param String[] array
	 */
	public void tc_disableSelectEmployee(String[] array) throws Exception{
		getSqlSession().update("EmployeeApiAccout.disableSelectEmployee", array);
	}
	/**
	 * 启用选中的一条或者多条数据
	 * @param String[] array
	 */
	public void tc_activateSelectEmployee(String[] array) throws Exception{
		getSqlSession().update("EmployeeApiAccout.activateSelectEmployee", array);
	}
}
