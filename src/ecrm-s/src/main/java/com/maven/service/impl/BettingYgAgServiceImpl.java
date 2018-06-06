package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.BettingYgAgDao;
import com.maven.entity.BettingYgAg;
import com.maven.service.BettingGameService;

@Service
public class BettingYgAgServiceImpl extends BaseServiceImpl<BettingYgAg>implements BettingGameService<BettingYgAg> {

	@Autowired
	private BettingYgAgDao bettingYgAgDao;
	
	@Override
	public BaseDao<BettingYgAg> baseDao() {
		return bettingYgAgDao;
	}

	@Override
	public Class<BettingYgAg> getClazz() {
		return BettingYgAg.class;
	}

	/**
	 * 获取游戏记录
	 */
	@Override
	public List<BettingYgAg> takeRecord(Map<String,Object> object)throws Exception {
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
		return bettingYgAgDao.takeRecordCountMoney(object);
	}
}
