package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.entity.ActivityBetRecord;

@Repository
public class ActivityBetRecordDaoImpl extends BaseDaoImpl<ActivityBetRecord> implements ActivityBetRecordDao{

	@Override
	public void addBetRecord(ActivityBetRecord betrecord) throws Exception {
		getSqlSession().insert("ActivityBetRecord.insertSelective", betrecord);
	}

	@Override
	public List<ActivityBetRecord> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectList("ActivityBetRecord.selectBetRecord", parameter);
	}

	@Override
	public int selectBetRecordCount(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("ActivityBetRecord.selectBetRecordCount", parameter);
	}

	@Override
	public Map<String, Object> selectBetRecordCountMoney(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("ActivityBetRecord.selectBetRecordCountMoney", parameter);
	}
	
	/**
	 * 按条件删除
	 * @param betrecord
	 * @throws Exception
	 */
	public void deleteByConditions(Map<String, Object> parameter) throws Exception {
		getSqlSession().delete("ActivityBetRecord.deleteByConditions", parameter);
	}
}
