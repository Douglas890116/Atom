package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.ActivityRedbagDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityRedbag;

@Repository
public class ActivityRedbagDaoImpl extends BaseDaoImpl<ActivityRedbag> implements ActivityRedbagDao{

	@Override
	public void addBetRecord(ActivityRedbag betrecord) throws Exception {
		getSqlSession().insert("ActivityRedbag.insertSelective", betrecord);
	}
	@Override
	public void updateBetRecord(ActivityRedbag betrecord) throws Exception {
		getSqlSession().insert("ActivityRedbag.updateByPrimaryKey", betrecord);
	}

	@Override
	public List<ActivityRedbag> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectList("ActivityRedbag.selectBetRecord", parameter);
	}

	@Override
	public int selectBetRecordCount(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("ActivityRedbag.selectBetRecordCount", parameter);
	}

	@Override
	public Map<String, Object> selectBetRecordCountMoney(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("ActivityRedbag.selectBetRecordCountMoney", parameter);
	}
}
