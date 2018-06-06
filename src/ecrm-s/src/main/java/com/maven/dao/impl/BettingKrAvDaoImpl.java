package com.maven.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.BettingKrAvDao;
import com.maven.entity.BettingKrAv;

@Repository
public class BettingKrAvDaoImpl extends BaseDaoImpl<BettingKrAv> implements BettingKrAvDao{
	/**
	 * 获取游戏记录总数之金额统计
	 * @param object
	 * @return
	 */
	public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) throws Exception {
		return getSqlSession().selectOne("BettingKrAv.takeRecordCountMoney", object);
	}
}
