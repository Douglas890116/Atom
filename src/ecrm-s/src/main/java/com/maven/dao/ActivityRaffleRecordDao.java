package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.ActivityRaffleRecord;

@Repository
public interface ActivityRaffleRecordDao extends BaseDao<ActivityRaffleRecord>{
	
	/**
	 * 获取抽奖记录
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	List<ActivityRaffleRecord> selectRaffleRecord(Map<String, Object> parameter) throws Exception;
}
