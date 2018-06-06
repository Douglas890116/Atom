package com.maven.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.ActivityLoseBonus;

@Repository
public interface ActivityLoseBonusDao extends BaseDao<ActivityLoseBonus>{
	
	/**
	 * 查询上月输值返利领取记录
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	ActivityLoseBonus selectLastMonthRecord(Map<String, Object> parameter) throws Exception;
	
}
