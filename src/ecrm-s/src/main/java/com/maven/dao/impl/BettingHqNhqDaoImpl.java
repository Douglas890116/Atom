package com.maven.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.BettingHqNhqDao;
import com.maven.entity.BettingHqNhq;
@Repository
public class BettingHqNhqDaoImpl extends BaseDaoImpl<BettingHqNhq> implements BettingHqNhqDao {

	/**
	 * 获取游戏记录总数之金额统计
	 * @param object
	 * @return
	 */
	public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) throws Exception {
		return getSqlSession().selectOne("BettingHqNhq.takeRecordCountMoney", object);
	}
}
