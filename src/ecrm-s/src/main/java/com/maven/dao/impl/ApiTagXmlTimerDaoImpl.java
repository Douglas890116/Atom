package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.ApiTagXmlTimerDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ApiTagXmlTimer;

@Repository
public class ApiTagXmlTimerDaoImpl extends BaseDaoImpl<ApiTagXmlTimer> implements ApiTagXmlTimerDao{

	@Override
	public void addBetRecord(ApiTagXmlTimer betrecord) throws Exception {
		getSqlSession().insert("ApiTagXmlTimer.insertSelective", betrecord);
	}
	
	@Override
	public void updateBetRecord(ApiTagXmlTimer betrecord) throws Exception {
		getSqlSession().insert("ApiTagXmlTimer.updateByPrimaryKeySelective", betrecord);
	}

	@Override
	public List<ApiTagXmlTimer> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectList("ApiTagXmlTimer.selectBetRecord", parameter);
	}

	@Override
	public int selectBetRecordCount(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("ApiTagXmlTimer.selectBetRecordCount", parameter);
	}
}
