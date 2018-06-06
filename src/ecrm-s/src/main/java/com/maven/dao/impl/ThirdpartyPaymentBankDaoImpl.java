package com.maven.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ThirdpartyPaymentBankDao;
import com.maven.entity.ThirdpartyPaymentBank;
@Repository
public class ThirdpartyPaymentBankDaoImpl extends BaseDaoImpl<ThirdpartyPaymentBank> implements ThirdpartyPaymentBankDao {

	@Override
	public List<ThirdpartyPaymentBank> takeThirdpartyPaymentBank(String thirdpartypaymenttypecode) {
		return getSqlSession().selectList("ThirdpartyPaymentBank.selectPaymentBank",thirdpartypaymenttypecode);
	}
	
}
