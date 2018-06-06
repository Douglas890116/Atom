package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.entity.BettingAllDay;
import com.maven.entity.BettingAllDay2;
import com.maven.entity.BettingAllPlan;

@Service
public interface BettingPlanService {
	
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
	void updateByPrimary(BettingAllPlan bettingAllDay) throws Exception;
	/**
	 * 根据主键查询
	 * @param bettingAllDay
	 * @throws Exception
	 */
	@DataSource("slave")
	BettingAllPlan selectByPrimary(String lsh) throws Exception;
	
	/**
	 * 分页查询
	 * @param paramObj
	 * @throws Exception
	 */
	@DataSource("slave")
	List<BettingAllPlan> selectForPage(Map<String, Object> paramObj) throws Exception;
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
	List<BettingAllPlan> select(Map<String, Object> paramObj) throws Exception;
	
}