package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.ReportWeeklyMember;

@Service
public interface ReportWeeklyMemberService extends BaseServcie<ReportWeeklyMember>{

	/**
	 * 数据统计之金额统计
	 * @param object
	 * @return
	 */
	Map<String, Object> takeRecordCountMoney(Map<String, Object> object) throws Exception ;
	
	/**
	 * 批量新增会员周洗码
	 * @param rwms
	 * @throws Exception
	 */
	@DataSource("master")
	void insertBatch(List<ReportWeeklyMember> rwms) throws Exception;
	
	/**
	 * 发放list中的所有洗码记录
	 * @param paramObj
	 * @throws Exception
	 */
	@DataSource("master")
	String updateProcessWeekly(List<ReportWeeklyMember> processList) throws Exception;
	
	/**
	 * 代理补发洗码
	 * @param rdas
	 * @throws Exception
	 */
	@DataSource("master")
	void update(ReportWeeklyMember rdas) throws Exception ;
	
	/**
	 * 代理补发洗码
	 * @param rdas
	 * @throws Exception
	 */
	@DataSource("master")
	void updateProcessSupplement(ReportWeeklyMember rdas, double butMoney, double money ) throws Exception ;
	
	/**
	 * 取消支付计划
	 * @param paramObj
	 * @throws Exception
	 */
	@DataSource("master")
	String updateWeeklyPlan(List<ReportWeeklyMember> processList) throws Exception ;
}
