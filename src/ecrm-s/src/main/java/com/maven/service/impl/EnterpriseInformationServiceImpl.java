/**
 * 
 */
package com.maven.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.EnterpriseInformationDao;
import com.maven.entity.EnterpriseInformation;
import com.maven.service.EnterpriseInformationService;
import com.maven.util.AttrCheckout;

@Service
public class EnterpriseInformationServiceImpl extends BaseServiceImpl<EnterpriseInformation> implements EnterpriseInformationService{

	@Autowired
	private EnterpriseInformationDao enterpriseInformationDao;

	@Override
	public BaseDao<EnterpriseInformation> baseDao() {
		return enterpriseInformationDao;
	}

	@Override
	public Class<EnterpriseInformation> getClazz() {
		return EnterpriseInformation.class;
	}

	/**
	 * 根据银行卡号查询
	 * @return EnterpriseInformation
	 */
	@Override
	public EnterpriseInformation findEnterpriseInformation(String enterprisepaymentaccount) throws Exception{
		return enterpriseInformationDao.findEnterpriseInformation(enterprisepaymentaccount);
	}
	
	@Override
	public int add(EnterpriseInformation obj) {
		return super.add(obj);
	}

	@Override
	public int update(EnterpriseInformation object) throws Exception {
		AttrCheckout.checkout(object, false, new String[]{"enterpriseinformationcode"});
		return super.update(object);
	}

	@Override
	public int delete(EnterpriseInformation object) throws Exception {
		AttrCheckout.checkout(object, false, new String[]{"enterpriseinformationcode"});
		return super.delete(object);
	}

	@Override
	public int tc_logicDelete(List<String> enterpriseinformationcodes) throws Exception {
		return super.logicDelete(enterpriseinformationcodes);
	}
	
	@Override
	public void updateCurrentBalance(EnterpriseInformation ei) throws Exception {
		enterpriseInformationDao.updateCurrentBalance(AttrCheckout.checkout(ei, false, new String[]{"openningaccount","enterprisecode","currentbalance"}));
	}
}
