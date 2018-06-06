package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.EnterpriseActivityPayDao;
import com.maven.entity.EnterpriseActivityPay;
import com.maven.service.EnterpriseActivityPayService;

@Service
public class EnterpriseActivityPayServiceImpl extends BaseServiceImpl<EnterpriseActivityPay> implements EnterpriseActivityPayService{

	@Autowired
	private EnterpriseActivityPayDao enterpriseActivityPayDao;
	
	
	@Override
	public BaseDao<EnterpriseActivityPay> baseDao() {
		return enterpriseActivityPayDao;
	}

	@Override
	public Class<EnterpriseActivityPay> getClazz() {
		return EnterpriseActivityPay.class;
	}

	@Override
	public List<EnterpriseActivityPay> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return enterpriseActivityPayDao.selectBetRecord(parameter);
	}

	@Override
	public int selectBetRecordCount(Map<String, Object> parameter) throws Exception {
		return enterpriseActivityPayDao.selectBetRecordCount(parameter);
	}
	
	@Override
	public int selectAllCountNoti(Map<String, Object> parameter) throws Exception {
		return enterpriseActivityPayDao.selectAllCountNoti(parameter);
	}
	
	@Override
	public Map<String, Object> selectBetRecordCountMoney(Map<String, Object> parameter) throws Exception {
		return enterpriseActivityPayDao.selectBetRecordCountMoney(parameter);
	}
	
	/**
	 * 增加需要打码的记录
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@Override
	public void addActivityBetRecord(EnterpriseActivityPay activityBetRecord) throws Exception {
		enterpriseActivityPayDao.addBetRecord(activityBetRecord);
	}
	
	@Override
	public int batchAddActivityBetRecord(List<EnterpriseActivityPay> list) throws Exception {
		return enterpriseActivityPayDao.batchAddBetRecord(list);
	}
	/**
	 * 修改需要打码的记录
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@Override
	public void updateActivityBetRecord(EnterpriseActivityPay activityBetRecord) throws Exception {
		enterpriseActivityPayDao.update("EnterpriseActivityPay.updateByPrimaryKeySelective", activityBetRecord);
	}

}
