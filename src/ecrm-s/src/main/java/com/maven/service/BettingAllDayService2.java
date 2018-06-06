package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.entity.BettingAllDay;
import com.maven.entity.BettingAllDay2;
import com.maven.entity.BettingAllPlan;

@Service
public interface BettingAllDayService2 {
	
	/**
	 * 数据统计之金额统计
	 * @param object
	 * @return
	 */
	@DataSource("slave")
	public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) throws Exception ;
	
	
	/**
	 * 根据主键更新
	 * @param bettingAllDay
	 * @throws Exception
	 */
	@DataSource("master")
	void updateByPrimary(BettingAllDay2 bettingAllDay) throws Exception;
	/**
	 * 分页查询
	 * @param paramObj
	 * @throws Exception
	 */
	@DataSource("slave")
	List<BettingAllDay2> selectForPage(Map<String, Object> paramObj) throws Exception;
	/**
	 * 分页查询 记录总数
	 * @param paramObj
	 * @throws Exception
	 */
	@DataSource("slave")
	int selectForPageCount(Map<String, Object> paramObj) throws Exception;
	/**
	 * 根据参数查询
	 * @param paramObj
	 * @throws Exception
	 */
	@DataSource("slave")
	List<BettingAllDay2> select(Map<String, Object> paramObj) throws Exception;
	
	/**
	 * 生成计划
	 * @param bettingAllDay
	 * @throws Exception
	 */
	@DataSource("master")
	int updateDoPlan(String planDate,String newPatchNo) throws Exception;
	/**
	 * 取消计划（已汇总）
	 * @param bettingAllDay
	 * @throws Exception
	 */
	@DataSource("master")
	int updateCancelPlan(BettingAllPlan plan, String operater) throws Exception;
	/**
	 * 财务核准计划
	 * @param bettingAllDay
	 * @throws Exception
	 */
	@DataSource("master")
	int updateDoPlanCaiwu(BettingAllPlan plan, String operater) throws Exception ;
	/**
	 * 取消核准
	 * @param bettingAllDay
	 * @throws Exception
	 */
	@DataSource("master")
	int updateCancelPlanCaiwu(BettingAllPlan plan, String operater) throws Exception;
}