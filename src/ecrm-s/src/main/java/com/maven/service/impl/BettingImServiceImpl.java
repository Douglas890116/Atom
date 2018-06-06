package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.BettingEbetDao;
import com.maven.dao.BettingGgDao;
import com.maven.dao.BettingHqNhqDao;
import com.maven.dao.BettingImDao;
import com.maven.entity.BettingEbet;
import com.maven.entity.BettingGg;
import com.maven.entity.BettingHqNhq;
import com.maven.entity.BettingIm;
import com.maven.service.BettingGameService;
@Service
public class BettingImServiceImpl extends BaseServiceImpl<BettingIm> implements BettingGameService<BettingIm> {
	
	@Autowired
	private BettingImDao bettingHqNhqDaoImpl;

	@Override
	public BaseDao<BettingIm> baseDao() {
		return bettingHqNhqDaoImpl;
	}

	@Override
	public Class<BettingIm> getClazz() {
		return BettingIm.class;
	}

	@Override
	public List<BettingIm> takeRecord(Map<String, Object> object) throws Exception {
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
