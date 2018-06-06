package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.entity.Baccarath5Rebate;
import com.maven.entity.BettingAllDay;
import com.maven.entity.BettingAllDay2;
import com.maven.entity.BettingAllPlan;

@Service
public interface Baccarath5RebateService {
	
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
	void updateByPrimary(Baccarath5Rebate bettingAllDay) throws Exception;
	/**
	 * 分页查询
	 * @param paramObj
	 * @throws Exception
	 */
	@DataSource("slave")
	List<Baccarath5Rebate> selectForPage(Map<String, Object> paramObj) throws Exception;
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
	List<Baccarath5Rebate> select(Map<String, Object> paramObj) throws Exception;
	
	/**
	 * 批量插入
	 * @param bettingAllDay
	 */
	@DataSource("master")
	int saveRecordBatch(List<Baccarath5Rebate> list);
}