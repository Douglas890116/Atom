package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.ReportDailyAgent;

@Service
public interface ReportDailyAgentService extends BaseServcie<ReportDailyAgent> {
	
	/**
	 * 数据统计之金额统计
	 * @param object
	 * @return
	 */
	Map<String, Object> takeRecordCountMoney(Map<String, Object> object) throws Exception ;
	
	/**
	 * 批量新增
	 * @throws Exception
	 */
	@DataSource("master")
	void saveBatch(List<ReportDailyAgent> rdas) throws Exception;
	
	/**
	 * 发放代理日结
	 * @param rdas
	 * @throws Exception
	 */
	@DataSource("master")
	String updateProcessDaily(List<ReportDailyAgent> rdas) throws Exception;
	/**
	 * 取消只计划
	 * @param rdas
	 * @throws Exception
	 */
	@DataSource("master")
	String updateDailyPlan(List<ReportDailyAgent> rdas) throws Exception;
	
	/**
	 * 批量修改
	 * @param rdas
	 * @throws Exception
	 */
	@DataSource("master")
	void updateBatchStatus(Map<String, Object> object) throws Exception;
	
	/**
	 * 主键修改
	 * @param rdas
	 * @throws Exception
	 */
	@DataSource("master")
	void update(ReportDailyAgent dailyAgent) throws Exception;
}
