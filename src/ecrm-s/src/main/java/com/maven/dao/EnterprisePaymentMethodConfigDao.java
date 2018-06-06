package com.maven.dao;

import org.springframework.stereotype.Repository;

import com.maven.entity.EnterprisePaymentMethodConfig;

@Repository
public interface EnterprisePaymentMethodConfigDao {
	
	/**
	 * 根据公司编码查询
	 * @param string
	 * @return
	 */
	EnterprisePaymentMethodConfig queryByCode(String enterpriseCode);
	
	/**
	 * 保存出款方式
	 * @param object
	 */
	void save(EnterprisePaymentMethodConfig paymentMethodConfig);
	
	/**
	 * 修改出款方式
	 * @param object
	 */
	void update(EnterprisePaymentMethodConfig paymentMethodConfig);

}
