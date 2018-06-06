package com.maven.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.dao.EnterprisePaymentMethodConfigDao;
import com.maven.entity.EnterprisePaymentMethodConfig;
import com.maven.service.EnterprisePaymentMethodConfigService;
import com.maven.util.BeanToMapUtil;

@Service
public class EnterprisePaymentMethodConfigServiceImpl implements EnterprisePaymentMethodConfigService {
	@Autowired
	EnterprisePaymentMethodConfigDao EnterprisePaymentMethodConfigDaoImpl;
	
	/**
	 * 根据公司编码查询,如果出款方式存在就修改,不存在新增
	 */
	@Override
	public void tc_saveAndUpdate(Map<String, Object> object) throws Exception {
		EnterprisePaymentMethodConfig paymentMehtod = EnterprisePaymentMethodConfigDaoImpl.queryByCode((String)object.get("enterpriseCode"));
		EnterprisePaymentMethodConfig paymentMethodConfig = BeanToMapUtil.convertMap(object, EnterprisePaymentMethodConfig.class);
		if(null == paymentMehtod){
			EnterprisePaymentMethodConfigDaoImpl.save(paymentMethodConfig);
		}else{
			EnterprisePaymentMethodConfigDaoImpl.update(paymentMethodConfig);
		}
	}
	
	/**
	 * 根据公司编码查询
	 * @param enterprisecode
	 */
	@Override
	public EnterprisePaymentMethodConfig queryByCode(String enterpriseCode) {
		return EnterprisePaymentMethodConfigDaoImpl.queryByCode(enterpriseCode);
	}
}
