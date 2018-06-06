package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.BettingHqOgAgDao;
import com.maven.entity.BettingHqOgAg;
import com.maven.service.BettingGameService;

@Service
public class BettingHqOgAgServiceImpl extends BaseServiceImpl<BettingHqOgAg>implements BettingGameService<BettingHqOgAg> {

	@Autowired
	private BettingHqOgAgDao bettingHqOgAgDao;
	
	@Override
	public BaseDao<BettingHqOgAg> baseDao() {
		return bettingHqOgAgDao;
	}

	@Override
	public Class<BettingHqOgAg> getClazz() {
		return BettingHqOgAg.class;
	}

	/**
	 * 获取游戏记录
	 */
	@Override
	public List<BettingHqOgAg> takeRecord(Map<String,Object> object)throws Exception {
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
		return bettingHqOgAgDao.takeRecordCountMoney(object);
	}
}
