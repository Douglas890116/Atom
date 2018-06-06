package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.ActivityRaffleRecord;

@Service
public interface ActivityRaffleRecordService extends BaseServcie<ActivityRaffleRecord>{
	
	
	/**
	 * 抽奖记录
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	int addRaffleRecord(ActivityRaffleRecord __rafflerecord) throws Exception;

	/**
	 * 获取抽奖记录
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	List<ActivityRaffleRecord> selectRaffleRecord(Map<String, Object> parameter) throws Exception;
	
	/**
	 * 获取抽奖记录json
	 * @param employeecode
	 * @param startraffletime
	 * @param endraffletime
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getRaffleRecordJson(String employeecode, String startraffletime, String endraffletime) throws Exception;
}
