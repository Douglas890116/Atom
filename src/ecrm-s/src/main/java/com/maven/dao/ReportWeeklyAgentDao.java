package com.maven.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.ReportWeeklyAgent;

@Repository
public interface ReportWeeklyAgentDao extends BaseDao<ReportWeeklyAgent>{
	/**
	 * 数据统计之金额统计
	 * @param object
	 * @return
	 */
	Map<String, Object> takeRecordCountMoney(Map<String, Object> object) ;
}
