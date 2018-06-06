package com.maven.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.BettingHqOgIbcDao;
import com.maven.entity.BettingHqOgIbc;

@Repository
public class BettingHqOgIbcDaoImpl extends BaseDaoImpl<BettingHqOgIbc> implements BettingHqOgIbcDao{
	/**
	 * 获取游戏记录总数之金额统计
	 * @param object
	 * @return
	 */
	public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) throws Exception {
		return getSqlSession().selectOne("BettingHqOgIbc.takeRecordCountMoney", object);
	}
}
