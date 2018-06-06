package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.BettingGgDao;
import com.maven.dao.BettingHqNhqDao;
import com.maven.dao.BettingIdn2Dao;
import com.maven.dao.BettingIdnDao;
import com.maven.entity.BettingGg;
import com.maven.entity.BettingHqNhq;
import com.maven.entity.BettingIdn;
import com.maven.entity.BettingIdn2;
import com.maven.service.BettingGameService;
@Service
public class BettingIdn2ServiceImpl extends BaseServiceImpl<BettingIdn2> implements BettingGameService<BettingIdn2> {
	
	@Autowired
	private BettingIdn2Dao bettingHqNhqDaoImpl;

	@Override
	public BaseDao<BettingIdn2> baseDao() {
		return bettingHqNhqDaoImpl;
	}

	@Override
	public Class<BettingIdn2> getClazz() {
		return BettingIdn2.class;
	}

	@Override
	public List<BettingIdn2> takeRecord(Map<String, Object> object) throws Exception {
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
