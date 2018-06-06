package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.BettingHqAgDao;
import com.maven.dao.BettingYoplayDao;
import com.maven.entity.BettingHqAg;
import com.maven.entity.BettingYoplay;
import com.maven.service.BettingGameService;

@Service
public class BettingYoplayServiceImpl extends BaseServiceImpl<BettingYoplay> implements BettingGameService<BettingYoplay>{
	
	@Autowired
	private BettingYoplayDao bettingHqAgDaoImpl;
	
	@Override
	public BaseDao<BettingYoplay> baseDao() {
		return bettingHqAgDaoImpl;
	}

	@Override
	public Class<BettingYoplay> getClazz() {
		return BettingYoplay.class;
	}

	@Override
	public List<BettingYoplay> takeRecord(Map<String, Object> object) throws Exception {
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
		return bettingHqAgDaoImpl.takeRecordCountMoney(object);
	}

}
