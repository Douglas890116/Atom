package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.BettingHqAgDao;
import com.maven.dao.BettingMgDao;
import com.maven.entity.BettingHqAg;
import com.maven.entity.BettingMg;
import com.maven.service.BettingGameService;

@Service
public class BettingMgServiceImpl extends BaseServiceImpl<BettingMg> implements BettingGameService<BettingMg>{
	
	@Autowired
	private BettingMgDao bettingMgDaoImpl;
	
	@Override
	public BaseDao<BettingMg> baseDao() {
		return bettingMgDaoImpl;
	}

	@Override
	public Class<BettingMg> getClazz() {
		return BettingMg.class;
	}

	@Override
	public List<BettingMg> takeRecord(Map<String, Object> object) throws Exception {
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
		return bettingMgDaoImpl.takeRecordCountMoney(object);
	}

}
