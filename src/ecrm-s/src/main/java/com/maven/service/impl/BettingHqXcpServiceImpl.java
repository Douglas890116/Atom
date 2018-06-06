package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.BettingHqXcpDao;
import com.maven.entity.BettingHqXcp;
import com.maven.service.BettingGameService;

@Service
public class BettingHqXcpServiceImpl extends BaseServiceImpl<BettingHqXcp>implements BettingGameService<BettingHqXcp> {

	@Autowired
	private BettingHqXcpDao bettingHqXcpDao;
	
	@Override
	public BaseDao<BettingHqXcp> baseDao() {
		return bettingHqXcpDao;
	}

	@Override
	public Class<BettingHqXcp> getClazz() {
		return BettingHqXcp.class;
	}

	/**
	 * 获取游戏记录
	 */
	@Override
	public List<BettingHqXcp> takeRecord(Map<String,Object> object)throws Exception {
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
		return bettingHqXcpDao.takeRecordCountMoney(object);
	}
}
