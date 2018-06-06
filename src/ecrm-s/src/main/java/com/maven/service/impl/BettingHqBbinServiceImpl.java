package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.BettingHqBbinDao;
import com.maven.entity.BettingHqBbin;
import com.maven.service.BettingGameService;

@Service
public class BettingHqBbinServiceImpl extends BaseServiceImpl<BettingHqBbin>implements BettingGameService<BettingHqBbin> {

	@Autowired
	private BettingHqBbinDao bettingHqBbinService;
	
	@Override
	public BaseDao<BettingHqBbin> baseDao() {
		return bettingHqBbinService;
	}

	@Override
	public Class<BettingHqBbin> getClazz() {
		return BettingHqBbin.class;
	}

	/**
	 * 获取游戏记录
	 */
	@Override
	public List<BettingHqBbin> takeRecord(Map<String, Object>  object)throws Exception {
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
		return bettingHqBbinService.takeRecordCountMoney(object);
	}

}
