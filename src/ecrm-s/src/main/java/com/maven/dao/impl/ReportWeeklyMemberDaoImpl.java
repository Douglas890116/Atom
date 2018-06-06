package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ReportWeeklyMemberDao;
import com.maven.entity.ReportWeeklyMember;

@Repository
public class ReportWeeklyMemberDaoImpl extends BaseDaoImpl<ReportWeeklyMember> implements ReportWeeklyMemberDao {

	/**
	 * 数据统计之金额统计
	 * @param object
	 * @return
	 */
	@Override
	public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) {
		return getSqlSession().selectOne("ReportWeeklyMember.takeRecordCountMoney", object);
	}
	@Override
	public void insertBatch(List<ReportWeeklyMember> rwms) {
		getSqlSession().insert("ReportWeeklyMember.insertBatch", rwms);
	}

	@Override
	public void updateByPrimary(ReportWeeklyMember rwm) {
		getSqlSession().update("ReportWeeklyMember.update", rwm);
	}
}
