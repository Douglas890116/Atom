package com.maven.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.ActivityRaffleControl;

@Service
public interface ActivityRaffleControlService extends BaseServcie<ActivityRaffleControl>{
	
	/**
	 * 签到抽奖映射
	 */
	public final static String QDCJYS = "QDCJYS";
	
	/**
	 * 奖金几率映射
	 */
	public final static String JJJLYS = "JJJLYS";
	
	
	/**
	 * 签到与抽奖
	 * @param __employeecode
	 * @param __enterprisebrandcodeactivitycode
	 * @return
	 */
	@DataSource("master")
	Map<String,Object> tc_raffle(String __employeecode,int __enterprisebrandcodeactivitycode, Map<String,Object> parames)throws Exception;
	
	/**
	 * 充值与抽奖
	 * @param __employeecode
	 * @param __enterprisebrandcodeactivitycode
	 * @return
	 */
	@DataSource("master")
	Map<String,Object> cz_raffle(String __employeecode,int __enterprisebrandcodeactivitycode, Map<String,Object> parames )throws Exception;
	
	/**
	 * 每日充值享受抽奖
	 * @param __employeecode
	 * @param __enterprisebrandcodeactivitycode
	 * @return
	 */
	@DataSource("master")
	Map<String,Object> cz_raffle_day(String __employeecode,int __enterprisebrandcodeactivitycode, Map<String,Object> parames )throws Exception;
	
	/**
	 * 抽奖记录
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	int addRaffleRecord(ActivityRaffleControl __rafflerecord) throws Exception;
}
