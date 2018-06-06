package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.BettingAllDayDao;
import com.maven.dao.BettingAllDayDao2;
import com.maven.dao.BettingPlanDao;
import com.maven.entity.BettingAllDay;
import com.maven.entity.BettingAllDay2;
import com.maven.entity.BettingAllPlan;

@Repository
public class BettingPlanDaoImpl extends BaseDaoImpl<BettingAllPlan> implements BettingPlanDao{
	
	/**
	 * 数据统计之金额统计
	 * @param object
	 * @return
	 */
	@Override
	public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) {
		return getSqlSession().selectOne("BettingPlan.takeRecordCountMoney", object);
	}
	
	/**
	 * 根据批次号取消计划
	 * @param bettingAllDay
	 */
	@Override
	public int deleteByPatchnoCancel(BettingAllPlan bettingAllDay) {
		return getSqlSession().delete("BettingPlan.deleteByPatchnoCancel", bettingAllDay);
	}
	/**
	 * 根据批次号取消财务核准
	 * @param bettingAllDay
	 */
	@Override
	public int updateByPatchnoCancel(BettingAllPlan bettingAllDay) {
		return getSqlSession().update("BettingPlan.updateByPatchnoCancel", bettingAllDay);
	}
	/**
	 * 根据主键更新
	 * @param bettingAllDay
	 */
	@Override
	public void updateByPrimary(BettingAllPlan bettingAllDay) {
		getSqlSession().update("BettingPlan.updateByPrimaryKeySelective", bettingAllDay);
	}

	/**
	 * 根据主键更新
	 * @param bettingAllDay
	 */
	@Override
	public BettingAllPlan selectByPrimary(String lsh) {
		return getSqlSession().selectOne("BettingPlan.selectByPrimaryKey", lsh);
	}
	/**
	 * 分页查询
	 */
	@Override
	public List<BettingAllPlan> selectForPage(Map<String, Object> paramObj) {
		return getSqlSession().selectList("BettingPlan.selectForPage", paramObj);
	}
	
	/**
	 * 分页查询 记录总数
	 * @param paramObj
	 * @throws Exception
	 */
	@Override
	public int selectForPageCount(Map<String, Object> paramObj) {
		return getSqlSession().selectOne("BettingPlan.selectForPageCount", paramObj);
	}
	
	/**
	 * 根据参数查询
	 */
	@Override
	public List<BettingAllPlan> select(Map<String, Object> paramObj) {
		return getSqlSession().selectList("BettingPlan.select", paramObj);
	}

	
	/**
	 * 批量插入
	 * @param bettingAllDay
	 */
	@Override
	public int saveRecordBatch(List<BettingAllPlan> list) {
		return getSqlSession().insert("BettingPlan.saveRecordBatch", list);
	}
	
	/**
	 * 插入
	 * @param bettingAllDay
	 */
	@Override
	public int saveRecord(BettingAllPlan bettingAllDay) {
		return getSqlSession().insert("BettingPlan.insert", bettingAllDay);
	}
}
