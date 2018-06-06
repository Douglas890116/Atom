package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.entity.BettingAllDay;
import com.maven.entity.BettingAllDay2;

@Service
public interface BettingAllDayService {
	
	/**
	 * 数据统计之金额统计
	 * @param object
	 * @return
	 */
	@DataSource("slave")
	public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) throws Exception ;
	
	/**
	 * 用户输钱排名
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	List<BettingAllDay> call_userLoseRanking(Map<String, Object> paramObj)throws Exception;
	/**
	 * 用户赢钱排名
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	List<BettingAllDay> call_userWinRanking(Map<String, Object> paramObj)throws Exception;
	/**
	 * 查询用户输赢游戏记录
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	List<BettingAllDay> queryUserLoseWinGameRecord(Map<String, Object> paramObj)throws Exception;
	/**
	 * 统计用户输赢游戏记录
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	int queryCountUserLoseWinGameRecord(Map<String, Object> paramObj)throws Exception;
	/**
	 * 查询会员编码
	 * @param paramObj
	 * @return
	 */
	@DataSource("slave")
	List<BettingAllDay> selectBettingByDate(Map<String, Object> paramObj) throws Exception;
	/**
	 * 根据主键更新
	 * @param bettingAllDay
	 * @throws Exception
	 */
	@DataSource("master")
	void updateByPrimary(BettingAllDay bettingAllDay) throws Exception;
	
	/**
	 * 做汇总计划
	 * 
	 * 将投注明细数据汇总到bettingAllDay表
	 * @param planDate 要汇总的日期
	 * @param newPatchNo 汇总后的批次号
	 * 
	 * @throws Exception
	 */
	@DataSource("master")
	void updateDoPlan(String planDate,String newPatchNo) throws Exception ;
	/**
	 * 分页查询
	 * @param paramObj
	 * @throws Exception
	 */
	@DataSource("slave")
	List<BettingAllDay> selectForPage(Map<String, Object> paramObj) throws Exception;
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
	List<BettingAllDay> select(Map<String, Object> paramObj) throws Exception;
	/**
	 * 发放list中的所有洗码记录
	 * @param paramObj
	 * @throws Exception
	 */
	@DataSource("master")
	String updateProcessDaily(List<BettingAllDay> processList) throws Exception;
	/**
	 * 取消支付计划
	 * @param paramObj
	 * @throws Exception
	 */
	@DataSource("master")
	String updateDailyPlan(List<BettingAllDay> processList) throws Exception;
	
	
}