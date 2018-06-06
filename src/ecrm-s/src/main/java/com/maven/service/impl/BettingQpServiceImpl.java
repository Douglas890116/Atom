package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.BettingHqAgDao;
import com.maven.dao.BettingMgDao;
import com.maven.dao.BettingQpDao;
import com.maven.entity.BettingHqAg;
import com.maven.entity.BettingMg;
import com.maven.entity.BettingQp;
import com.maven.service.BettingGameService;

@Service
public class BettingQpServiceImpl extends BaseServiceImpl<BettingQp> implements BettingGameService<BettingQp>{
	
	@Autowired
	private BettingQpDao bettingQpDaoImpl;
	
	@Override
	public BaseDao<BettingQp> baseDao() {
		return bettingQpDaoImpl;
	}

	@Override
	public Class<BettingQp> getClazz() {
		return BettingQp.class;
	}

	@Override
	public List<BettingQp> takeRecord(Map<String, Object> object) throws Exception {
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
		return bettingQpDaoImpl.takeRecordCountMoney(object);
	}

}
