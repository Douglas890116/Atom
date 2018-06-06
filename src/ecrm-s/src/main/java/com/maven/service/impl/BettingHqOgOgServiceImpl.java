package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.BettingHqOgOgDao;
import com.maven.entity.BettingHqOgOg;
import com.maven.service.BettingGameService;

@Service
public class BettingHqOgOgServiceImpl extends BaseServiceImpl<BettingHqOgOg>implements BettingGameService<BettingHqOgOg> {

	@Autowired
	private BettingHqOgOgDao bettingHqOgOgDao;
	
	@Override
	public BaseDao<BettingHqOgOg> baseDao() {
		return bettingHqOgOgDao;
	}

	@Override
	public Class<BettingHqOgOg> getClazz() {
		return BettingHqOgOg.class;
	}

	/**
	 * 获取游戏记录
	 */
	@Override
	public List<BettingHqOgOg> takeRecord(Map<String,Object> object)throws Exception {
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
		return bettingHqOgOgDao.takeRecordCountMoney(object);
	}
}
