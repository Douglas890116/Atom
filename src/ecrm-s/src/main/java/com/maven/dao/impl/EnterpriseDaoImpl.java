package com.maven.dao.impl;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.EnterpriseDao;
import com.maven.entity.Enterprise;

@Repository
public class EnterpriseDaoImpl extends BaseDaoImpl<Enterprise> implements EnterpriseDao{
	

	/**
	 * 删除企业时,调用该方法验证企业是否可以删除
	 * @param enterprisecode
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean validateDeleteEnterprise(String enterprisecode) {
		int mark =  getSqlSession().selectOne("Enterprise.validateDeleteEnterprise", enterprisecode);
		if(mark==0){
			return true;
		}
		return false;
	}



}
