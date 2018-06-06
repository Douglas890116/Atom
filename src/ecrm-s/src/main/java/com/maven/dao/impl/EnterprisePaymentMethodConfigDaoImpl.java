package com.maven.dao.impl;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.EnterprisePaymentMethodConfigDao;
import com.maven.entity.EnterprisePaymentMethodConfig;
@Repository
public class EnterprisePaymentMethodConfigDaoImpl extends BaseDaoImpl<EnterprisePaymentMethodConfig> implements EnterprisePaymentMethodConfigDao {
	
	/**
	 * 根据公司编码查询
	 */
	@Override
	public EnterprisePaymentMethodConfig queryByCode(String enterpriseCode) {
		return getSqlSession().selectOne("EnterprisePaymentMethodConfig.getByIdEnterprisCode",enterpriseCode);
	}
	
	/**
	 * 保存出款方式
	 */
	@Override
	public void save(EnterprisePaymentMethodConfig paymentMethodConfig) {
		getSqlSession().insert("EnterprisePaymentMethodConfig.save", paymentMethodConfig);
	}
	
	/**
	 * 修改出款方式
	 */
	@Override
	public void update(EnterprisePaymentMethodConfig paymentMethodConfig) {
		getSqlSession().update("EnterprisePaymentMethodConfig.update", paymentMethodConfig);
	}

}
