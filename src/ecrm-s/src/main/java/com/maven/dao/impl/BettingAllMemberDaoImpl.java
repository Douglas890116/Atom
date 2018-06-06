package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.BettingAllAgentDao;
import com.maven.dao.BettingAllDayDao;
import com.maven.dao.BettingAllDayDao2;
import com.maven.dao.BettingAllMemberDao;
import com.maven.entity.BettingAllAgent;
import com.maven.entity.BettingAllDay;
import com.maven.entity.BettingAllDay2;
import com.maven.entity.BettingAllMember;
import com.maven.entity.BettingAllPlan;

@Repository
public class BettingAllMemberDaoImpl extends BaseDaoImpl<BettingAllMember> implements BettingAllMemberDao{
	
	/**
	 * 数据统计之金额统计
	 * @param object
	 * @return
	 */
	@Override
	public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) {
		return getSqlSession().selectOne("BettingAllMember.takeRecordCountMoney", object);
	}
	
	/**
	 * 根据批次号删除数据
	 * @param bettingAllDay
	 */
	@Override
	public int deletePatchno(BettingAllPlan bettingAllDay) {
		return getSqlSession().delete("BettingAllMember.deletePatchno", bettingAllDay);
	}
	/**
	 * 根据主键更新
	 * @param bettingAllDay
	 */
	@Override
	public void updateByPrimary(BettingAllMember bettingAllDay) {
		getSqlSession().update("BettingAllMember.updateByPrimaryKeySelective", bettingAllDay);
	}

	/**
	 * 分页查询
	 */
	@Override
	public List<BettingAllMember> selectForPage(Map<String, Object> paramObj) {
		return getSqlSession().selectList("BettingAllMember.selectForPage", paramObj);
	}
	
	/**
	 * 分页查询 记录总数
	 * @param paramObj
	 * @throws Exception
	 */
	@Override
	public int selectForPageCount(Map<String, Object> paramObj) {
		return getSqlSession().selectOne("BettingAllMember.selectForPageCount", paramObj);
	}
	
	/**
	 * 根据参数查询
	 */
	@Override
	public List<BettingAllMember> select(Map<String, Object> paramObj) {
		return getSqlSession().selectList("BettingAllMember.select", paramObj);
	}

	
	/**
	 * 批量插入
	 * @param bettingAllDay
	 */
	@Override
	public int saveRecordBatch(List<BettingAllMember> list) {
		return getSqlSession().insert("BettingAllMember.saveRecordBatch", list);
	}
}
