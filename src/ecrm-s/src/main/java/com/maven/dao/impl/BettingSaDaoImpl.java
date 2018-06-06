package com.maven.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.BettingSaDao;
import com.maven.entity.BettingSa;

@Repository
public class BettingSaDaoImpl extends BaseDaoImpl<BettingSa> implements BettingSaDao{
	/**
	 * 获取游戏记录总数之金额统计
	 * @param object
	 * @return
	 */
	public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) throws Exception {
		return getSqlSession().selectOne("BettingSa.takeRecordCountMoney", object);
	}
}
