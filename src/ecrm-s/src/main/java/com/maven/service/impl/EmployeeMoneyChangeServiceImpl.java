package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.EmployeeMoneyChangeDao;
import com.maven.entity.EmployeeMoneyChange;
import com.maven.service.EmployeeMoneyChangeService;
import com.maven.util.AttrCheckout;

@Service
public class EmployeeMoneyChangeServiceImpl extends BaseServiceImpl<EmployeeMoneyChange> implements EmployeeMoneyChangeService{

	@Autowired
	private EmployeeMoneyChangeDao employeeMoneyChangeDao;
	
	@Override
	public BaseDao<EmployeeMoneyChange> baseDao() {
		return employeeMoneyChangeDao;
	}

	@Override
	public Class<EmployeeMoneyChange> getClazz() {
		return EmployeeMoneyChange.class;
	}

	@Override
	public int addMoneyChangeRecord(EmployeeMoneyChange moneyChange) throws Exception {
		return super.add(AttrCheckout.checkout(moneyChange, false, new String[]{"moneychangecode","employeecode","moneychangetypecode","moneychangedate","moneychangeamount","settlementamount","moneyinoutcomment"}));
	}
	/**
	 * 查询账变记录
	 * @param object
	 * @return
	 */
	@Override
	public List<EmployeeMoneyChange> findAccountChange(Map<String, Object> object) throws Exception {
		return employeeMoneyChangeDao.findAccountChange(object);
	}
	/**
	 * 统计账变记录
	 * @param object
	 * @return
	 */
	@Override
	public Map<String, Object> findAccountChangeCount(Map<String, Object> object) throws Exception {
		return employeeMoneyChangeDao.findAccountChangeCount(object);
	}
}
