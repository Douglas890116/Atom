package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.UserLogsDao;
import com.maven.entity.UserLogs;

@Repository
public class UserLogsDaoImpl extends BaseDaoImpl<UserLogs> implements UserLogsDao{

	@Override
	public void addBetRecord(UserLogs betrecord) throws Exception {
		getSqlSession().insert("UserLogs.insertSelective", betrecord);
	}

	@Override
	public void batchAddBetRecord(List<UserLogs> betRecordList) throws Exception {
	    getSqlSession().insert("UserLogs.batchInsert", betRecordList);
	}
	@Override
	public List<UserLogs> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectList("UserLogs.selectBetRecord", parameter);
	}

	@Override
	public int selectBetRecordCount(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("UserLogs.selectBetRecordCount", parameter);
	}

}
