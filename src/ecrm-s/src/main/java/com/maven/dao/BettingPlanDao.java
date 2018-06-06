package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.base.dao.DataSource;
import com.maven.entity.BettingAllDay;
import com.maven.entity.BettingAllDay2;
import com.maven.entity.BettingAllPlan;

@Repository
public interface BettingPlanDao extends BaseDao<BettingAllPlan>{
	
	/**
	 * 数据统计之金额统计
	 * @param object
	 * @return
	 */
	Map<String, Object> takeRecordCountMoney(Map<String, Object> object) ;
	
	
	/**
	 * 根据主键更新
	 * @param bettingAllDay
	 */
	void updateByPrimary(BettingAllPlan bettingAllDay);
	/**
	 * 根据批次号取消计划
	 * @param bettingAllDay
	 */
	int deleteByPatchnoCancel(BettingAllPlan bettingAllDay);
	/**
	 * 根据批次号取消财务核准
	 * @param bettingAllDay
	 */
	int updateByPatchnoCancel(BettingAllPlan bettingAllDay) ;
	/**
	 * 根据主键更新
	 * @param bettingAllDay
	 */
	BettingAllPlan selectByPrimary(String lsh);
	/**
	 * 分页查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	List<BettingAllPlan> selectForPage(Map<String, Object> paramObj);
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
	List<BettingAllPlan> select(Map<String, Object> paramObj);
	
	/**
	 * 批量插入
	 * @param bettingAllDay
	 */
	int saveRecordBatch(List<BettingAllPlan> list);
	
	/**
	 * 插入
	 * @param bettingAllDay
	 */
	int saveRecord(BettingAllPlan bettingAllDay) ;
}
