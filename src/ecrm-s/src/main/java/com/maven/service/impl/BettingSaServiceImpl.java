package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.BettingSaDao;
import com.maven.entity.BettingSa;
import com.maven.service.BettingGameService;

@Service
public class BettingSaServiceImpl extends BaseServiceImpl<BettingSa>implements BettingGameService<BettingSa> {

	@Autowired
	private BettingSaDao bettingSaDao;
	
	@Override
	public BaseDao<BettingSa> baseDao() {
		return bettingSaDao;
	}

	@Override
	public Class<BettingSa> getClazz() {
		return BettingSa.class;
	}

	/**
	 * 获取游戏记录
	 */
	@Override
	public List<BettingSa> takeRecord(Map<String,Object> object)throws Exception {
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
		return bettingSaDao.takeRecordCountMoney(object);
	}
}
