package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ActivityRaffleRecordDao;
import com.maven.entity.ActivityRaffleRecord;

@Repository
public class ActivityRaffleRecordDaoImpl extends BaseDaoImpl<ActivityRaffleRecord> implements ActivityRaffleRecordDao{

	@Override
	public List<ActivityRaffleRecord> selectRaffleRecord(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectList("ActivityRaffleRecord.selectRaffleRecord", parameter);
	}

}
