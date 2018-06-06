package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.BettingHqAgDao;
import com.maven.dao.BettingHqPngDao;
import com.maven.entity.BettingHqAg;
import com.maven.entity.BettingHqPng;
import com.maven.service.BettingGameService;

@Service
public class BettingHqPngServiceImpl extends BaseServiceImpl<BettingHqPng> implements BettingGameService<BettingHqPng>{
	
	@Autowired
	private BettingHqPngDao bettingHqPngDaoImpl;
	
	@Override
	public BaseDao<BettingHqPng> baseDao() {
		return bettingHqPngDaoImpl;
	}

	@Override
	public Class<BettingHqPng> getClazz() {
		return BettingHqPng.class;
	}

	@Override
	public List<BettingHqPng> takeRecord(Map<String, Object> object) throws Exception {
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
		return bettingHqPngDaoImpl.takeRecordCountMoney(object);
	}

}
