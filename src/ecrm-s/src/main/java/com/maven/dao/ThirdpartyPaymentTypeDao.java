package com.maven.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.entity.ThirdpartyPaymentType;
@Repository
public interface ThirdpartyPaymentTypeDao {
	
	/**
	 * 加载所有的快捷支付类型
	 */
	List<ThirdpartyPaymentType> takeThirdpartyPaymentType();

}
