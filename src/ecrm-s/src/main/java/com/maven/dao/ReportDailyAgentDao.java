package com.maven.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.ReportDailyAgent;

@Repository
public interface ReportDailyAgentDao extends BaseDao<ReportDailyAgent>{
	
	/**
	 * 数据统计之金额统计
	 * @param object
	 * @return
	 */
	Map<String, Object> takeRecordCountMoney(Map<String, Object> object) ;
	/**
	 * 批量修改
	 * @param rdas
	 * @throws Exception
	 */
	void updateBatchStatus(Map<String, Object> object) throws Exception;
}
