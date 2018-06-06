package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.BettingHqOgIbcDao;
import com.maven.entity.BettingHqOgIbc;
import com.maven.service.BettingGameService;

@Service
public class BettingHqOgIbcServiceImpl extends BaseServiceImpl<BettingHqOgIbc>implements BettingGameService<BettingHqOgIbc> {

	@Autowired
	private BettingHqOgIbcDao bettingHqOgIbcDao;
	
	@Override
	public BaseDao<BettingHqOgIbc> baseDao() {
		return bettingHqOgIbcDao;
	}

	@Override
	public Class<BettingHqOgIbc> getClazz() {
		return BettingHqOgIbc.class;
	}

	/**
	 * 获取游戏记录
	 */
	@Override
	public List<BettingHqOgIbc> takeRecord(Map<String,Object> object)throws Exception {
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
		return bettingHqOgIbcDao.takeRecordCountMoney(object);
	}
}
