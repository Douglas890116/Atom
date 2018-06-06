package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.BettingGgDao;
import com.maven.dao.BettingHqNhqDao;
import com.maven.dao.BettingQtDao;
import com.maven.entity.BettingGg;
import com.maven.entity.BettingHqNhq;
import com.maven.entity.BettingQt;
import com.maven.service.BettingGameService;
@Service
public class BettingQtServiceImpl extends BaseServiceImpl<BettingQt> implements BettingGameService<BettingQt> {
	
	@Autowired
	private BettingQtDao bettingHqNhqDaoImpl;

	@Override
	public BaseDao<BettingQt> baseDao() {
		return bettingHqNhqDaoImpl;
	}

	@Override
	public Class<BettingQt> getClazz() {
		return BettingQt.class;
	}

	@Override
	public List<BettingQt> takeRecord(Map<String, Object> object) throws Exception {
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
