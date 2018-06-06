package com.maven.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.BettingHqNhqDao;
import com.maven.dao.BettingSgsDao;
import com.maven.entity.BettingHqNhq;
import com.maven.entity.BettingSgs;
@Repository
public class BettingSgsDaoImpl extends BaseDaoImpl<BettingSgs> implements BettingSgsDao {

	/**
	 * 获取游戏记录总数之金额统计
	 * @param object
	 * @return
	 */
	public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) throws Exception {
		return getSqlSession().selectOne("BettingSgs.takeRecordCountMoney", object);
	}
}
