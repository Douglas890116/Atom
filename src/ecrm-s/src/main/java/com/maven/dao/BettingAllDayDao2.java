package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.base.dao.DataSource;
import com.maven.entity.BettingAllDay;
import com.maven.entity.BettingAllDay2;

@Repository
public interface BettingAllDayDao2 extends BaseDao<BettingAllDay2>{
	
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
	int deletePatchno(BettingAllDay2 bettingAllDay) ;
	/**
	 * 根据主键更新
	 * @param bettingAllDay
	 */
	void updateByPrimary(BettingAllDay2 bettingAllDay);
	/**
	 * 分页查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	List<BettingAllDay2> selectForPage(Map<String, Object> paramObj);
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
	List<BettingAllDay2> select(Map<String, Object> paramObj);
	
	/**
	 * 获取分组数据
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	List<BettingAllDay2> selectGroup(Map<String, Object> paramObj);
	/**
	 * 批量插入
	 * @param bettingAllDay
	 */
	int saveRecordBatch(List<BettingAllDay2> list);
}
