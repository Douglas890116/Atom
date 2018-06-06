package com.maven.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ReportWeeklyAgentDao;
import com.maven.entity.ReportWeeklyAgent;

@Repository
public class ReportWeeklyAgentDaoImpl extends BaseDaoImpl<ReportWeeklyAgent> implements ReportWeeklyAgentDao {
	
	/**
	 * 数据统计之金额统计
	 * @param object
	 * @return
	 */
	@Override
	public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) {
		return getSqlSession().selectOne("ReportWeeklyAgent.takeRecordCountMoney", object);
	}
}
