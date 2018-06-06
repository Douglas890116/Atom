package com.maven.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.dao.ThirdpartyPaymentTypeDao;
import com.maven.entity.ThirdpartyPaymentType;
import com.maven.service.ThirdpartyPaymentTypeService;
@Service
public class ThirdpartyPaymentTypeServiceImpl implements ThirdpartyPaymentTypeService {
	
	@Autowired
	private ThirdpartyPaymentTypeDao thirdpartyPaymentTypeDaoImpl;
	
	/**
	 * 加载所有的快捷支付类型
	 */
	@Override
	public List<ThirdpartyPaymentType> takeThirdpartyPaymentType() {
		return thirdpartyPaymentTypeDaoImpl.takeThirdpartyPaymentType();
	}

}
