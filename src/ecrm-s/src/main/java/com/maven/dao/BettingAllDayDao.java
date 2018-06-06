package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.base.dao.DataSource;
import com.maven.entity.BettingAllDay;

@Repository
public interface BettingAllDayDao extends BaseDao<BettingAllDay>{
	
	/**
	 * 数据统计之金额统计
	 * @param object
	 * @return
	 */
	Map<String, Object> takeRecordCountMoney(Map<String, Object> object) ;
	
	/**
	 * 用户输钱排名
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	List<BettingAllDay> queryUserLoseRanking(Map<String, Object> paramObj);
	/**
	 * 用户赢钱排名
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	List<BettingAllDay> queryUserWinRanking(Map<String, Object> paramObj);
	/**
	 * 查询用户输赢游戏记录
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	List<BettingAllDay> queryUserLoseWinGameRecord(Map<String, Object> paramObj);
	/**
	 * 统计用户输赢游戏记录
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	int countUserLoseWinGameRecord(Map<String, Object> paramObj);
	/**
	 * 根据时间查询会员投注
	 * @param paramObj
	 * @return
	 */
	List<BettingAllDay> selectBettingByDate(Map<String, Object> paramObj);
	/**
	 * 根据主键更新
	 * @param bettingAllDay
	 */
	void updateByPrimary(BettingAllDay bettingAllDay);
	/**
	 * 分页查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	List<BettingAllDay> selectForPage(Map<String, Object> paramObj);
	/**
	 * 分页查询 记录总数
	 * @param paramObj
	 * @throws Exception
	 */
	int selectForPageCount(Map<String, Object> paramObj);
	/**
	 * 根据参数查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	List<BettingAllDay> select(Map<String, Object> paramObj);
	
	/**
	 * 批量插入
	 * @param bettingAllDay
	 */
	int saveRecordBatch(List<BettingAllDay> list);
}
