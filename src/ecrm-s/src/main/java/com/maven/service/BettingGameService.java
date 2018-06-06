package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;

@Service
public interface BettingGameService<T> {

	/**
	 * 获取游戏记录
	 * @param object
	 * @return
	 */
	@DataSource("slave")
	public List<T> takeRecord(Map<String,Object> object)throws Exception;
	
	/**
	 * 获取游戏记录总数
	 * @param object
	 * @return
	 */
	@DataSource("slave")
	public int takeRecordCount(Map<String,Object> object)throws Exception;
	
	/**
	 * 获取游戏记录总数之金额统计
	 * @param object
	 * @return
	 */
	@DataSource("slave")
	public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) throws Exception;
	
}
