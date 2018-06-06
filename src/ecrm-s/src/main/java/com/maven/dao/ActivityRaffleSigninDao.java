package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.ActivityRaffleSignin;

@Repository
public interface ActivityRaffleSigninDao extends BaseDao<ActivityRaffleSignin>{
	
	/**
	 * 抽奖签到
	 * @param object
	 * @return
	 */
	String usp_raffle_signin(Object object);
	
	/**
	 * 获取签到记录
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	List<ActivityRaffleSignin> selectSigninRecord(Map<String, Object> parameter) throws Exception;
}
