package com.maven.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.BettingEbet;
import com.maven.entity.BettingEibc;
import com.maven.entity.BettingGg;
import com.maven.entity.BettingHqNhq;
@Repository
public interface BettingEibcDao extends BaseDao<BettingEibc>{
	/**
	 * 获取游戏记录总数之金额统计
	 * @param object
	 * @return
	 */
	Map<String, Object> takeRecordCountMoney(Map<String, Object> object) throws Exception;
}
