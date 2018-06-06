package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.BettingAllDayDao;
import com.maven.entity.BettingAllDay;

@Repository
public class BettingAllDayDaoImpl extends BaseDaoImpl<BettingAllDay> implements BettingAllDayDao{
	
	/**
	 * 数据统计之金额统计
	 * @param object
	 * @return
	 */
	@Override
	public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) {
		return getSqlSession().selectOne("BettingAllDay.takeRecordCountMoney", object);
	}
	
	/**
	 * 用户输钱排名
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<BettingAllDay> queryUserLoseRanking(Map<String, Object> paramObj) {
		return getSqlSession().selectList("BettingAllDay.queryUserLoseRanking", paramObj);
	}
	
	/**
	 * 用户赢钱排名
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<BettingAllDay> queryUserWinRanking(Map<String, Object> paramObj) {
		return getSqlSession().selectList("BettingAllDay.queryUserWinRanking", paramObj);
	}
	
	/**
	 * 查询用户输赢游戏记录
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<BettingAllDay> queryUserLoseWinGameRecord(Map<String, Object> paramObj) {
		return getSqlSession().selectList("BettingAllDay.queryUserLoseWinGameRecord", paramObj);
	}
	
	/**
	 * 统计用户输赢游戏记录
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int countUserLoseWinGameRecord(Map<String, Object> paramObj) {
		return getSqlSession().selectOne("BettingAllDay.countUserLoseWinGameRecord",paramObj);
	}

	/**
	 * 查询会员编码
	 * @param paramObj
	 * @return
	 */
	@Override
	public List<BettingAllDay> selectBettingByDate(Map<String, Object> paramObj) {
		return getSqlSession().selectList("BettingAllDay.selectBettingByDate",paramObj);
	}

	/**
	 * 根据主键更新
	 * @param bettingAllDay
	 */
	@Override
	public void updateByPrimary(BettingAllDay bettingAllDay) {
		getSqlSession().update("BettingAllDay.updateByPrimaryKeySelective", bettingAllDay);
	}

	/**
	 * 分页查询
	 */
	@Override
	public List<BettingAllDay> selectForPage(Map<String, Object> paramObj) {
		return getSqlSession().selectList("BettingAllDay.selectForPage", paramObj);
	}
	
	/**
	 * 分页查询 记录总数
	 * @param paramObj
	 * @throws Exception
	 */
	@Override
	public int selectForPageCount(Map<String, Object> paramObj) {
		return getSqlSession().selectOne("BettingAllDay.selectForPageCount", paramObj);
	}
	
	/**
	 * 根据参数查询
	 */
	@Override
	public List<BettingAllDay> select(Map<String, Object> paramObj) {
		return getSqlSession().selectList("BettingAllDay.select", paramObj);
	}
	/**
	 * 批量插入
	 * @param bettingAllDay
	 */
	@Override
	public int saveRecordBatch(List<BettingAllDay> list) {
		return getSqlSession().insert("BettingAllDay.saveRecordBatch", list);
	}
}
