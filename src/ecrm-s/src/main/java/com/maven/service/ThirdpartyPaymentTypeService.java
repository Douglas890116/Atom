package com.maven.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.entity.ThirdpartyPaymentType;

@Service
public interface ThirdpartyPaymentTypeService {
	
	/**
	 * 加载所有快捷支付类型
	 * @return
	 */
	@DataSource("slave")
	List<ThirdpartyPaymentType> takeThirdpartyPaymentType();
	
}
