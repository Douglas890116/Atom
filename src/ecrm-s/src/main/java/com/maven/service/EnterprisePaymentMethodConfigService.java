package com.maven.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.entity.EnterprisePaymentMethodConfig;

@Service
public interface EnterprisePaymentMethodConfigService{
	
	/**
	 * 根据企业编码查询,如果出款方式存在就修改,不存在新增
	 * @param object @DataSource("slave")
	 */
	@DataSource("master")
	public void tc_saveAndUpdate(Map<String, Object> object)throws Exception;
	
	/**
	 * 根据公司编码查询
	 * @param enterprisecode
	 */
	@DataSource("slave")
	public EnterprisePaymentMethodConfig queryByCode(String enterpriseCode);
	
}
