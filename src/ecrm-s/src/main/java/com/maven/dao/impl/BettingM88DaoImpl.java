package com.maven.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.BettingGgDao;
import com.maven.dao.BettingHqNhqDao;
import com.maven.dao.BettingIdnDao;
import com.maven.dao.BettingM88Dao;
import com.maven.entity.BettingGg;
import com.maven.entity.BettingHqNhq;
import com.maven.entity.BettingIdn;
import com.maven.entity.BettingM88;
@Repository
public class BettingM88DaoImpl extends BaseDaoImpl<BettingM88> implements BettingM88Dao {

	/**
	 * 获取游戏记录总数之金额统计
	 * @param object
	 * @return
	 */
	public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) throws Exception {
		return getSqlSession().selectOne("BettingM88.takeRecordCountMoney", object);
	}
}
