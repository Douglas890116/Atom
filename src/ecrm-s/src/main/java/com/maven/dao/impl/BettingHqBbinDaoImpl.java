package com.maven.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.BettingHqBbinDao;
import com.maven.entity.BettingHqBbin;

@Repository
public class BettingHqBbinDaoImpl extends BaseDaoImpl<BettingHqBbin> implements BettingHqBbinDao{
	/**
	 * 获取游戏记录总数之金额统计
	 * @param object
	 * @return
	 */
	public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) throws Exception {
		return getSqlSession().selectOne("BettingHqBbin.takeRecordCountMoney", object);
	}
}
