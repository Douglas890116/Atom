package com.maven.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.ThirdpartyPaymentBankDao;
import com.maven.entity.ThirdpartyPaymentBank;
import com.maven.service.ThirdpartyPaymentBankService;
@Service
public class ThirdpartyPaymentBankServiceImpl extends BaseServiceImpl<ThirdpartyPaymentBank> implements ThirdpartyPaymentBankService {

	@Autowired
	ThirdpartyPaymentBankDao thirdpartyPaymentBankDao;
	@Override
	public BaseDao<ThirdpartyPaymentBank> baseDao() {
		return thirdpartyPaymentBankDao;
	}

	@Override
	public Class<ThirdpartyPaymentBank> getClazz() {
		return ThirdpartyPaymentBank.class;
	}

	@Override
	public List<ThirdpartyPaymentBank> takeThirdpartyPaymentBank(String thirdpartypaymenttypecode) throws Exception {
		return thirdpartyPaymentBankDao.takeThirdpartyPaymentBank(thirdpartypaymenttypecode);
	}
	
	
	

}
