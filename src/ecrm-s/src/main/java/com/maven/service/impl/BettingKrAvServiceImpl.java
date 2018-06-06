package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.BettingKrAvDao;
import com.maven.entity.BettingKrAv;
import com.maven.service.BettingGameService;

@Service
public class BettingKrAvServiceImpl extends BaseServiceImpl<BettingKrAv>implements BettingGameService<BettingKrAv> {

	@Autowired
	private BettingKrAvDao bettingKrAvDao;
	
	@Override
	public BaseDao<BettingKrAv> baseDao() {
		return bettingKrAvDao;
	}

	@Override
	public Class<BettingKrAv> getClazz() {
		return BettingKrAv.class;
	}

	/**
	 * 获取游戏记录
	 */
	@Override
	public List<BettingKrAv> takeRecord(Map<String,Object> object)throws Exception {
		return super.selectAll(object);
	}

	/**
	 * 获取游戏记录总数
	 */
	@Override
	public int takeRecordCount(Map<String,Object> object)throws Exception{
		return super.selectAllCount(object);
	}
	/**
	 * 获取游戏记录总数之金额统计
	 * @param object
	 * @return
	 */
	@Override
	public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) throws Exception {
		return bettingKrAvDao.takeRecordCountMoney(object);
	}
}
