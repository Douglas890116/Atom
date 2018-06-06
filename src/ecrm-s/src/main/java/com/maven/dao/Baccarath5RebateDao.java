package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.base.dao.DataSource;
import com.maven.entity.Baccarath5Rebate;
import com.maven.entity.BettingAllDay;
import com.maven.entity.BettingAllDay2;

@Repository
public interface Baccarath5RebateDao extends BaseDao<Baccarath5Rebate>{
	
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
	void updateByPrimary(Baccarath5Rebate bettingAllDay);
	/**
	 * 分页查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	List<Baccarath5Rebate> selectForPage(Map<String, Object> paramObj);
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
	List<Baccarath5Rebate> select(Map<String, Object> paramObj);
	
	/**
	 * 批量插入
	 * @param bettingAllDay
	 */
	int saveRecordBatch(List<Baccarath5Rebate> list);
}
