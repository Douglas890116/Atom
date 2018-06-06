package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.BettingHqHqDao;
import com.maven.entity.BettingHqHq;
import com.maven.service.BettingGameService;

@Service
public class BettingHqHqServiceImpl extends BaseServiceImpl<BettingHqHq>implements BettingGameService<BettingHqHq> {

	@Autowired
	private BettingHqHqDao BettingHqHqDao;
	
	@Override
	public BaseDao<BettingHqHq> baseDao() {
		return BettingHqHqDao;
	}

	@Override
	public Class<BettingHqHq> getClazz() {
		return BettingHqHq.class;
	}

	/**
	 * 获取游戏记录
	 */
	@Override
	public List<BettingHqHq> takeRecord(Map<String, Object>  object)throws Exception {
		return super.selectAll(object);
	}

	/**
	 * 获取游戏记录总数
	 */
	@Override
	public int takeRecordCount(Map<String, Object>  object)throws Exception{
		return super.selectAllCount(object);
	}
	/**
	 * 获取游戏记录总数之金额统计
	 * @param object
	 * @return
	 */
	@Override
	public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) throws Exception {
		return BettingHqHqDao.takeRecordCountMoney(object);
	}

}
