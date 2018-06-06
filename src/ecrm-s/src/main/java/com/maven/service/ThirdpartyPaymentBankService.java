
package com.maven.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.ThirdpartyPaymentBank;

@Service
public interface ThirdpartyPaymentBankService extends BaseServcie<ThirdpartyPaymentBank>{
	
	/**
	 * 获取第三方支付支持的银行
	 * @param thirdpartypaymenttypecode
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<ThirdpartyPaymentBank>  takeThirdpartyPaymentBank(String thirdpartypaymenttypecode) throws Exception;
}
