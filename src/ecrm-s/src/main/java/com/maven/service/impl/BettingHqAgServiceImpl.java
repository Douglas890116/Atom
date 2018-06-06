package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.BettingHqAgDao;
import com.maven.entity.BettingHqAg;
import com.maven.service.BettingGameService;

@Service
public class BettingHqAgServiceImpl extends BaseServiceImpl<BettingHqAg> implements BettingGameService<BettingHqAg>{
	
	@Autowired
	private BettingHqAgDao bettingHqAgDaoImpl;
	
	@Override
	public BaseDao<BettingHqAg> baseDao() {
		return bettingHqAgDaoImpl;
	}

	@Override
	public Class<BettingHqAg> getClazz() {
		return BettingHqAg.class;
	}

	@Override
	public List<BettingHqAg> takeRecord(Map<String, Object> object) throws Exception {
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
		return bettingHqAgDaoImpl.takeRecordCountMoney(object);
	}

}
