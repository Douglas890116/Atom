package com.maven.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ReportDailyAgentDao;
import com.maven.entity.ReportDailyAgent;

@Repository
public class ReportDailyAgentDaoImpl extends BaseDaoImpl<ReportDailyAgent> implements ReportDailyAgentDao {
	/**
	 * 数据统计之金额统计
	 * @param object
	 * @return
	 */
	@Override
	public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) {
		return getSqlSession().selectOne("ReportDailyAgent.takeRecordCountMoney", object);
	}
	/**
	 * 批量修改
	 * @param rdas
	 * @throws Exception
	 */
	public void updateBatchStatus(Map<String, Object> object) {
		getSqlSession().update("ReportDailyAgent.updateBatchStatus",object);
	}
}
