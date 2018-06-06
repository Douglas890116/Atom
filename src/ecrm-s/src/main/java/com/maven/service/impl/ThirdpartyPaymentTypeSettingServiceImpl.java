package com.maven.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.dao.ThirdpartyPaymentTypeSettingDao;
import com.maven.entity.ThirdpartyPaymentTypeSetting;
import com.maven.service.ThirdpartyPaymentTypeSettingService;
@Service
public class ThirdpartyPaymentTypeSettingServiceImpl implements ThirdpartyPaymentTypeSettingService {
	
	@Autowired
	private ThirdpartyPaymentTypeSettingDao ThirdpartyPaymentTypeSettingDaoImpl;
	
	/**
	 * 根据支付类型编码查询所对应的属性值
	 * @param thirdpartyPaymentTypeCode
	 * @return
	 */
	@Override
	public List<ThirdpartyPaymentTypeSetting> takeThirdpartyPaymentTypeSetting(String thirdpartyPaymentTypeCode)throws Exception {
		return ThirdpartyPaymentTypeSettingDaoImpl.takeThirdpartyPaymentTypeSetting(thirdpartyPaymentTypeCode);
	}

}
