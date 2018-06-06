package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.base.dao.DataSource;
import com.maven.entity.ReportWeeklyMember;

@Repository
public interface ReportWeeklyMemberDao extends BaseDao<ReportWeeklyMember>{
	/**
	 * 数据统计之金额统计
	 * @param object
	 * @return
	 */
	Map<String, Object> takeRecordCountMoney(Map<String, Object> object) ;
	/**
	 * 批量插入会员周报表
	 * @param rwms
	 */
	void insertBatch(List<ReportWeeklyMember> rwms);
	/**
	 * 根据时间查询会员周报表
	 * @param paramObj
	 * @return
	 */
	/*@DataSource("slave")
	List<ReportWeeklyMember> selectBettingByDate(Map<String, Object> paramObj);*/
	/**
	 * 根据主键更新
	 * @param bettingAllDay
	 */
	@DataSource("master")
	void updateByPrimary(ReportWeeklyMember bettingAllDay);
	/**
	 * 分页查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	/*@DataSource("slave")
	List<ReportWeeklyMember> selectForPage(Map<String, Object> paramObj);*/
	/**
	 * 分页查询 记录总数
	 * @param paramObj
	 * @throws Exception
	 */
	/*@DataSource("slave")
	int selectForPageCount(Map<String, Object> paramObj);*/
	/**
	 * 根据参数查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	/*@DataSource("slave")
	List<BettingAllDay> select(Map<String, Object> paramObj);*/
}
