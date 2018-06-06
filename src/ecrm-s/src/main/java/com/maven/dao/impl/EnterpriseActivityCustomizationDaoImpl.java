package com.maven.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.EnterpriseActivityCustomizationDao;
import com.maven.entity.EnterpriseActivityCustomization;

@Repository
public class EnterpriseActivityCustomizationDaoImpl extends BaseDaoImpl<EnterpriseActivityCustomization>
	implements EnterpriseActivityCustomizationDao{

	@Override
	public List<EnterpriseActivityCustomization> checkEnterpriseActivity(String enterprisecode, int activitytemplatecode) throws Exception{
		EnterpriseActivityCustomization eac = new EnterpriseActivityCustomization();
		eac.setEnterprisecode(enterprisecode);
		eac.setActivitytemplatecode(activitytemplatecode);
		eac.setDatastatus("1");
		List<EnterpriseActivityCustomization> eacs = getSqlSession().selectList("EnterpriseActivityCustomization.select", eac);
		if (eacs == null || eacs.isEmpty()){
			return null;
		}
		return eacs;
	}

	@Override
	public EnterpriseActivityCustomization selectByEnterprisebrandactivitycode(String __enterprisebrandactivitycode)
			throws Exception {
		return getSqlSession().selectOne("EnterpriseActivityCustomization.selectByEnterprisebrandactivitycode", 
				__enterprisebrandactivitycode);
	}

	@Override
	public List<EnterpriseActivityCustomization> selectAllByEnterprisecode(String enterprisecode) throws Exception {
		return getSqlSession().selectList("EnterpriseActivityCustomization.selectAllByEnterprisecode", enterprisecode);
	}
	
}
