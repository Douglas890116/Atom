package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.BettingHqAgDao;
import com.maven.dao.BettingTtgDao;
import com.maven.entity.BettingHqAg;
import com.maven.entity.BettingTtg;
import com.maven.service.BettingGameService;

@Service
public class BettingTtgServiceImpl extends BaseServiceImpl<BettingTtg> implements BettingGameService<BettingTtg>{
	
	@Autowired
	private BettingTtgDao bettingMgDaoImpl;
	
	@Override
	public BaseDao<BettingTtg> baseDao() {
		return bettingMgDaoImpl;
	}

	@Override
	public Class<BettingTtg> getClazz() {
		return BettingTtg.class;
	}

	@Override
	public List<BettingTtg> takeRecord(Map<String, Object> object) throws Exception {
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
