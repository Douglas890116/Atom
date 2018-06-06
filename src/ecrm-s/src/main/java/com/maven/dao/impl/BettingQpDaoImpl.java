package com.maven.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.BettingHqAgDao;
import com.maven.dao.BettingMgDao;
import com.maven.dao.BettingQpDao;
import com.maven.entity.BettingHqAg;
import com.maven.entity.BettingMg;
import com.maven.entity.BettingQp;

@Repository
public class BettingQpDaoImpl  extends BaseDaoImpl<BettingQp> implements BettingQpDao {

	/**
	 * 获取游戏记录总数之金额统计
	 * @param object
	 * @return
	 */
	public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) throws Exception {
		return getSqlSession().selectOne("BettingQp.takeRecordCountMoney", object);
	}
}
