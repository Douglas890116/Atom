package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.EnterpriseEmployeeRegeditLogDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.EnterpriseEmployeeRegeditLog;

@Repository
public class EnterpriseEmployeeRegeditLogDaoImpl extends BaseDaoImpl<EnterpriseEmployeeRegeditLog> implements EnterpriseEmployeeRegeditLogDao {

	@Override
	public void addBetRecord(EnterpriseEmployeeRegeditLog betrecord) throws Exception {
		getSqlSession().insert("EnterpriseEmployeeRegeditLog.insertSelective", betrecord);
	}

	@Override
	public List<EnterpriseEmployeeRegeditLog> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectList("EnterpriseEmployeeRegeditLog.selectBetRecord", parameter);
	}

	@Override
	public int selectBetRecordCount(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("EnterpriseEmployeeRegeditLog.selectBetRecordCount", parameter);
	}

	@Override
	public List<EnterpriseEmployeeRegeditLog> selectBetRecordGroup(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectList("EnterpriseEmployeeRegeditLog.selectAllGroup", parameter);
	}
	
	@Override
	public int selectBetRecordGroupCount(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("EnterpriseEmployeeRegeditLog.selectAllGroupCount", parameter);
	}
}
