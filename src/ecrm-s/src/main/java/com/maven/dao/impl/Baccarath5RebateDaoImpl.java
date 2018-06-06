package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.Baccarath5RebateDao;
import com.maven.dao.BettingAllDayDao;
import com.maven.dao.BettingAllDayDao2;
import com.maven.entity.Baccarath5Rebate;
import com.maven.entity.BettingAllDay;
import com.maven.entity.BettingAllDay2;
import com.maven.entity.BettingAllPlan;

@Repository
public class Baccarath5RebateDaoImpl extends BaseDaoImpl<Baccarath5Rebate> implements Baccarath5RebateDao{
	
	/**
	 * 数据统计之金额统计
	 * @param object
	 * @return
	 */
	@Override
	public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) {
		return getSqlSession().selectOne("Baccarath5Rebate.selectBetRecordCountMoney", object);
	}
	
	/**
	 * 根据主键更新
	 * @param bettingAllDay
	 */
	@Override
	public void updateByPrimary(Baccarath5Rebate bettingAllDay) {
		getSqlSession().update("Baccarath5Rebate.updateStatus", bettingAllDay);
	}

	/**
	 * 分页查询
	 */
	@Override
	public List<Baccarath5Rebate> selectForPage(Map<String, Object> paramObj) {
		return getSqlSession().selectList("Baccarath5Rebate.selectBetRecord", paramObj);
	}
	
	/**
	 * 分页查询 记录总数
	 * @param paramObj
	 * @throws Exception
	 */
	@Override
	public int selectForPageCount(Map<String, Object> paramObj) {
		return getSqlSession().selectOne("Baccarath5Rebate.selectBetRecordCount", paramObj);
	}
	
	/**
	 * 根据参数查询
	 */
	@Override
	public List<Baccarath5Rebate> select(Map<String, Object> paramObj) {
		return getSqlSession().selectList("Baccarath5Rebate.select", paramObj);
	}
	/**
	 * 批量插入
	 * @param bettingAllDay
	 */
	@Override
	public int saveRecordBatch(List<Baccarath5Rebate> list) {
		return getSqlSession().insert("Baccarath5Rebate.saveRecordBatch", list);
	}
}
