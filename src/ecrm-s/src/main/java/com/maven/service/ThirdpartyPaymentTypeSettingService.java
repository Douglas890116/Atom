package com.maven.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.entity.ThirdpartyPaymentTypeSetting;

@Service
public interface ThirdpartyPaymentTypeSettingService {
	/**
	 * 根据支付类型编码查询所对应的属性值
	 * @param thirdpartyPaymentTypeCode
	 * @return
	 */
	@DataSource("slave")
	List<ThirdpartyPaymentTypeSetting> takeThirdpartyPaymentTypeSetting(String thirdpartyPaymentTypeCode)throws Exception;

}
