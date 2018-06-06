/**
 * 
 */
package com.maven.dao;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.EnterpriseInformation;

@Repository
public interface EnterpriseInformationDao extends BaseDao<EnterpriseInformation> {
	/**
	 * 根据银行卡号查询
	 * @return EnterpriseInformation
	 */
	EnterpriseInformation findEnterpriseInformation(String enterprisepaymentaccount);
	/**
	 * 更新企业银行卡当前余额
	 * @param ei
	 */
	public void updateCurrentBalance(EnterpriseInformation ei);
}
