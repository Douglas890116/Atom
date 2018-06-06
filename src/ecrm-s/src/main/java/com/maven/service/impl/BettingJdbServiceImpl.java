package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.BettingJdbDao;
import com.maven.entity.BettingJdb;
import com.maven.service.BettingGameService;

@Service
public class BettingJdbServiceImpl extends BaseServiceImpl<BettingJdb> implements BettingGameService<BettingJdb> {

	@Autowired
	private BettingJdbDao BettingJdbDaoImpl;
	
	@Override
	public BaseDao<BettingJdb> baseDao() {
		return BettingJdbDaoImpl;
	}

	@Override
	public Class<BettingJdb> getClazz() {
		return BettingJdb.class;
	}
	
	@Override
	public List<BettingJdb> takeRecord(Map<String, Object> object) throws Exception {
		return super.selectAll(object);
	}

	@Override
	public int takeRecordCount(Map<String, Object> object) throws Exception {
		return super.selectAllCount(object);
	}

	@Override
	public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) throws Exception {
		return BettingJdbDaoImpl.takeRecordCountMoney(object);
	}
}