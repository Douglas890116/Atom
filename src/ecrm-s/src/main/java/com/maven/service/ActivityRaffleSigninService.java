package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.ActivityRaffleSignin;

@Service
public interface ActivityRaffleSigninService extends BaseServcie<ActivityRaffleSignin>{
	
	/**
	 * 活动签到
	 * @param __object
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	int tc_raffleSignIn(Map<String,Object> __object)throws Exception;

	/**
	 * 获取签到记录
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	List<ActivityRaffleSignin> selectSigninRecord(Map<String, Object> parameter) throws Exception;
	
	/**
	 * 获取签到记录json
	 * @param employeecode
	 * @param starttime
	 * @param endtime
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getSigninRecordJson(String employeecode, String startsignintime, String endsignintime) throws Exception; 
}
