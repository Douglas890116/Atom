package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.BettingQwpDao;
import com.maven.entity.BettingQwp;
import com.maven.service.BettingGameService;

@Service
public class BettingQwpServiceImpl extends BaseServiceImpl<BettingQwp> implements BettingGameService<BettingQwp> {

	@Autowired
	private BettingQwpDao bettingQwpDaoImpl;

	@Override
	public BaseDao<BettingQwp> baseDao() {
		return bettingQwpDaoImpl;
	}

	@Override
	public Class<BettingQwp> getClazz() {
		return BettingQwp.class;
	}
	
	@Override
	public List<BettingQwp> takeRecord(Map<String, Object> object) throws Exception {
		return super.selectAll(object);
	}

	@Override
	public int takeRecordCount(Map<String, Object> object) throws Exception {
		return super.selectAllCount(object);
	}

	@Override
	public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) throws Exception {
		return bettingQwpDaoImpl.takeRecordCountMoney(object);
	}
}