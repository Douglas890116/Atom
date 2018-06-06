package com.maven.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ThirdpartyPaymentTypeDao;
import com.maven.entity.ThirdpartyPaymentType;
@Repository
public class ThirdpartyPaymentTypeDaoImpl extends BaseDaoImpl<ThirdpartyPaymentType> implements ThirdpartyPaymentTypeDao {
	
	/**
	 * 加载快捷支付类型
	 */
	@Override
	public List<ThirdpartyPaymentType> takeThirdpartyPaymentType() {
		return getSqlSession().selectList("ThirdpartyPaymentType.select");
	}

}
