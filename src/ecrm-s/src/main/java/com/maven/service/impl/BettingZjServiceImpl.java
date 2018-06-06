package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.BettingZjDao;
import com.maven.entity.BettingZj;
import com.maven.service.BettingGameService;

@Service
public class BettingZjServiceImpl extends BaseServiceImpl<BettingZj>implements BettingGameService<BettingZj> {

	@Autowired
	private BettingZjDao bettingZjDao;
	
	@Override
	public BaseDao<BettingZj> baseDao() {
		return bettingZjDao;
	}

	@Override
	public Class<BettingZj> getClazz() {
		return BettingZj.class;
	}

	/**
	 * 获取游戏记录
	 */
	@Override
	public List<BettingZj> takeRecord(Map<String,Object> object)throws Exception {
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
		return bettingZjDao.takeRecordCountMoney(object);
	}
}
