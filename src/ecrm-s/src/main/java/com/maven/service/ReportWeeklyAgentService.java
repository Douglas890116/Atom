package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.ReportWeeklyAgent;

@Service
public interface ReportWeeklyAgentService extends BaseServcie<ReportWeeklyAgent> {
	
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
	void saveBatch(List<ReportWeeklyAgent> rdas) throws Exception;
	
	/**
	 * 发放代理周结
	 * @param rdas
	 * @throws Exception
	 */
	@DataSource("master")
	String updateProcessDaily(List<ReportWeeklyAgent> rdas) throws Exception;
	
	/**
	 * 取消支付计划
	 * @param rdas
	 * @throws Exception
	 */
	@DataSource("master")
	String updateWeeklyPlan(List<ReportWeeklyAgent> rdas) throws Exception;
	
	/**
	 * 代理补发洗码
	 * @param rdas
	 * @throws Exception
	 */
	@DataSource("master")
	void updateProcessSupplement(ReportWeeklyAgent rdas, double butMoney, double money ) throws Exception;
	
	@DataSource("master")
	void update(ReportWeeklyAgent rdas) throws Exception;
}
