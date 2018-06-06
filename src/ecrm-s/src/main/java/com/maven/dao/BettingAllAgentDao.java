package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.base.dao.DataSource;
import com.maven.entity.BettingAllAgent;
import com.maven.entity.BettingAllDay;
import com.maven.entity.BettingAllDay2;
import com.maven.entity.BettingAllPlan;

@Repository
public interface BettingAllAgentDao extends BaseDao<BettingAllAgent>{
	
	/**
	 * 数据统计之金额统计
	 * @param object
	 * @return
	 */
	Map<String, Object> takeRecordCountMoney(Map<String, Object> object) ;
	
	/**
	 * 根据批次号删除数据
	 * @param bettingAllDay
	 */
	int deletePatchno(BettingAllPlan bettingAllDay) ;
	/**
	 * 根据主键更新
	 * @param bettingAllDay
	 */
	void updateByPrimary(BettingAllAgent bettingAllDay);
	/**
	 * 分页查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	List<BettingAllAgent> selectForPage(Map<String, Object> paramObj);
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
	List<BettingAllAgent> select(Map<String, Object> paramObj);
	
	/**
	 * 批量插入
	 * @param bettingAllDay
	 */
	int saveRecordBatch(List<BettingAllAgent> list);
}
