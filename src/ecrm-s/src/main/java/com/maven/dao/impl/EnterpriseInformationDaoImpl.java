/**
 * 
 */
package com.maven.dao.impl;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.EnterpriseInformationDao;
import com.maven.entity.EnterpriseInformation;

@Repository
public class EnterpriseInformationDaoImpl extends BaseDaoImpl<EnterpriseInformation> implements EnterpriseInformationDao{
	/**
	 * 根据银行卡号查询
	 * @return EnterpriseInformation
	 */
	@Override
	public EnterpriseInformation findEnterpriseInformation(String enterprisepaymentaccount) {
		return getSqlSession().selectOne("EnterpriseInformation.findEnterpriseInformation", enterprisepaymentaccount);
	}

	@Override
	public void updateCurrentBalance(EnterpriseInformation ei) {
		getSqlSession().update("EnterpriseInformation.updateCurrentBalance", ei);
	}

}
