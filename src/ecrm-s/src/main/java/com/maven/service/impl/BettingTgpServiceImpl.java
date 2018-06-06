package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.BettingGgDao;
import com.maven.dao.BettingHqNhqDao;
import com.maven.dao.BettingTgpDao;
import com.maven.entity.BettingGg;
import com.maven.entity.BettingHqNhq;
import com.maven.entity.BettingTgp;
import com.maven.service.BettingGameService;
@Service
public class BettingTgpServiceImpl extends BaseServiceImpl<BettingTgp> implements BettingGameService<BettingTgp> {
	
	@Autowired
	private BettingTgpDao bettingHqNhqDaoImpl;

	@Override
	public BaseDao<BettingTgp> baseDao() {
		return bettingHqNhqDaoImpl;
	}

	@Override
	public Class<BettingTgp> getClazz() {
		return BettingTgp.class;
	}

	@Override
	public List<BettingTgp> takeRecord(Map<String, Object> object) throws Exception {
		return super.selectAll(object);
	}

	@Override
	public int takeRecordCount(Map<String, Object> object) throws Exception {
		return super.selectAllCount(object);
	}
	/**
	 * 获取游戏记录总数之金额统计
	 * @param object
	 * @return
	 */
	@Override
	public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) throws Exception {
		return bettingHqNhqDaoImpl.takeRecordCountMoney(object);
	}
}
