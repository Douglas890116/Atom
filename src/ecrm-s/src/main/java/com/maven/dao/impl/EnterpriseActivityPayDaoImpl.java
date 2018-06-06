package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.EnterpriseActivityPayDao;
import com.maven.entity.EnterpriseActivityPay;

@Repository
public class EnterpriseActivityPayDaoImpl extends BaseDaoImpl<EnterpriseActivityPay> implements EnterpriseActivityPayDao{

	@Override
	public void addBetRecord(EnterpriseActivityPay betrecord) throws Exception {
		getSqlSession().insert("EnterpriseActivityPay.add", betrecord);
	}
	@Override
	public int batchAddBetRecord(List<EnterpriseActivityPay> list) throws Exception {
		return getSqlSession().insert("EnterpriseActivityPay.batchAdd", list);
	}
	@Override
	public List<EnterpriseActivityPay> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectList("EnterpriseActivityPay.selectAll", parameter);
	}

	@Override
	public int selectBetRecordCount(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("EnterpriseActivityPay.selectAllCount", parameter);
	}

	@Override
	public Map<String, Object> selectBetRecordCountMoney(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("EnterpriseActivityPay.selectBetRecordCountMoney", parameter);
	}
	
	@Override
	public int selectAllCountNoti(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("EnterpriseActivityPay.selectAllCountNoti", parameter);
	}
}
