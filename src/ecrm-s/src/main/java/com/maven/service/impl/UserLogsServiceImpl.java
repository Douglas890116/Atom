package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.UserLogsDao;
import com.maven.entity.UserLogs;
import com.maven.service.UserLogsService;

@Service
public class UserLogsServiceImpl extends BaseServiceImpl<UserLogs> implements UserLogsService{

	@Autowired
	private UserLogsDao activityBetRecordDao;
	
	
	@Override
	public BaseDao<UserLogs> baseDao() {
		return activityBetRecordDao;
	}

	@Override
	public Class<UserLogs> getClazz() {
		return UserLogs.class;
	}

	@Override
	public List<UserLogs> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return activityBetRecordDao.selectBetRecord(parameter);
	}

	@Override
	public int selectBetRecordCount(Map<String, Object> parameter) throws Exception {
		return activityBetRecordDao.selectBetRecordCount(parameter);
	}
	
	
	/**
	 * 增加需要打码的记录
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@Override
	public void addActivityBetRecord(UserLogs activityBetRecord) throws Exception {
		activityBetRecordDao.addBetRecord(activityBetRecord);
	}

	@Override
	public void batchAddActivityBetRecord(List<UserLogs> activityBetRecordList) throws Exception {
	    activityBetRecordDao.batchAddBetRecord(activityBetRecordList);
	}
}
