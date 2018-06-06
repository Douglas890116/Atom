package com.maven.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.ThirdpartyPaymentBank;
@Repository
public interface ThirdpartyPaymentBankDao extends BaseDao<ThirdpartyPaymentBank>{

	/**
	 * 查询第三方支付支持的银行
	 * @param thirdpartypaymenttypecode
	 * @return
	 */
	public List<ThirdpartyPaymentBank> takeThirdpartyPaymentBank(String thirdpartypaymenttypecode);
}
