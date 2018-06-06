package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.EmployeeMoneyChangeDao;
import com.maven.entity.EmployeeMoneyChange;

@Repository
public class EmployeeMoneyChangeDaoImpl extends BaseDaoImpl<EmployeeMoneyChange> implements EmployeeMoneyChangeDao{
	
	/**
	 * 查询账变记录
	 * @param object
	 * @return
	 */
	@Override
	public List<EmployeeMoneyChange> findAccountChange(Map<String, Object> object) {
		return getSqlSession().selectList("EmployeeMoneyChange.findAccountChange", object);
	}
	
	/**
	 * 统计账变记录
	 * @param object
	 * @return
	 */
	@Override
	public Map<String, Object> findAccountChangeCount(Map<String, Object> object) {
		return getSqlSession().selectOne("EmployeeMoneyChange.findAccountChangeCount", object);
	}
}
