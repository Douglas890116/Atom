package com.maven.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.entity.ThirdpartyPaymentTypeSetting;

@Repository
public interface ThirdpartyPaymentTypeSettingDao {
	
	/**
	 * 根据支付类型编码查询所对应的属性值
	 * @param thirdpartyPaymentTypeCode
	 * @return
	 */
	List<ThirdpartyPaymentTypeSetting> takeThirdpartyPaymentTypeSetting(String thirdpartyPaymentTypeCode);

}
