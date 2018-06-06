package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.EmployeeGamecataloyBonusDao;
import com.maven.entity.EmployeeGamecataloyBonus;

@Repository
public class EmployeeGamecataloyBonusDaoImpl extends BaseDaoImpl<EmployeeGamecataloyBonus> implements EmployeeGamecataloyBonusDao{

	@Override
	public int settingEnterpriseBonus(EmployeeGamecataloyBonus object) throws Exception {
		return getSqlSession().insert("EmployeeGamecataloyBonus.enterpriseBonus", object);
	}

	@Override
	public List<EmployeeGamecataloyBonus> findGameBonus(String employeecode) {
		return getSqlSession().selectList("EmployeeGamecataloyBonus.findGameBonus", employeecode);
	}

	@Override
	public int updateBounus(Map<String,Object> object) throws Exception {
		return getSqlSession().update("EmployeeGamecataloyBonus.updateBounus", object);
	}
	
	@Override
	public int updateMultiMemberBonus(Map<String,Object> object) throws Exception {
		return getSqlSession().update("EmployeeGamecataloyBonus.updateMultiMemberBonus", object);
	}

	@Override
	public List<EmployeeGamecataloyBonus> findGameBonus(List<String> employeecodes) throws Exception {
		return getSqlSession().selectList("EmployeeGamecataloyBonus.findGameBonusByEmployeecodeList", employeecodes);
	}
}
