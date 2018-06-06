package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.BettingAllDayDao;
import com.maven.dao.BettingAllDayDao2;
import com.maven.entity.BettingAllDay;
import com.maven.entity.BettingAllDay2;
import com.maven.entity.BettingAllPlan;

@Repository
public class BettingAllDayDaoImpl2 extends BaseDaoImpl<BettingAllDay2> implements BettingAllDayDao2{
	
	/**
	 * 数据统计之金额统计
	 * @param object
	 * @return
	 */
	@Override
	public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) {
		return getSqlSession().selectOne("BettingAllDay2.takeRecordCountMoney", object);
	}
	
	/**
	 * 根据批次号删除数据
	 * @param bettingAllDay
	 */
	@Override
	public int deletePatchno(BettingAllDay2 bettingAllDay) {
		return getSqlSession().delete("BettingAllDay2.deletePatchno", bettingAllDay);
	}
	/**
	 * 根据主键更新
	 * @param bettingAllDay
	 */
	@Override
	public void updateByPrimary(BettingAllDay2 bettingAllDay) {
		getSqlSession().update("BettingAllDay2.updateByPrimaryKeySelective", bettingAllDay);
	}

	/**
	 * 分页查询
	 */
	@Override
	public List<BettingAllDay2> selectForPage(Map<String, Object> paramObj) {
		return getSqlSession().selectList("BettingAllDay2.selectForPage", paramObj);
	}
	
	/**
	 * 分页查询 记录总数
	 * @param paramObj
	 * @throws Exception
	 */
	@Override
	public int selectForPageCount(Map<String, Object> paramObj) {
		return getSqlSession().selectOne("BettingAllDay2.selectForPageCount", paramObj);
	}
	
	/**
	 * 根据参数查询
	 */
	@Override
	public List<BettingAllDay2> select(Map<String, Object> paramObj) {
		return getSqlSession().selectList("BettingAllDay2.select", paramObj);
	}

	/**
	 * 获取分组数据
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<BettingAllDay2> selectGroup(Map<String, Object> paramObj) {
		return getSqlSession().selectList("BettingAllDay2.selectGroup", paramObj);
	}
	
	/**
	 * 批量插入
	 * @param bettingAllDay
	 */
	@Override
	public int saveRecordBatch(List<BettingAllDay2> list) {
		return getSqlSession().insert("BettingAllDay2.saveRecordBatch", list);
	}
}
