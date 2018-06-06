package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityRedbag;
import com.maven.entity.TakeDepositRecord;

@Service
public interface ActivityRedbagService extends BaseServcie<ActivityRedbag>{
	
	/**
	 * 获取需要投注列表
	 * @return
	 */
	@DataSource("slave")
	List<ActivityRedbag> selectBetRecord(Map<String, Object> parameter) throws Exception;
	
	/**
	 * 需要投注列表总数
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	int selectBetRecordCount(Map<String, Object> parameter) throws Exception;
	
	@DataSource("slave")
	Map<String, Object> selectBetRecordCountMoney(Map<String, Object> parameter) throws Exception;
	/**
	 * 增加需要打码的记录
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	void addActivityBetRecord(ActivityRedbag activityBetRecord) throws Exception;
	@DataSource("master")
	void updateActivityBetRecord(ActivityRedbag activityBetRecord) throws Exception;
	
	public Map<String, Object> tc_redbag_regedit(final String __employeecode, final int __enterprisebrandactivitycode, final String loginip, final String fingerprintcode) throws Exception;
	
	public Map<String, Object> tc_redbag(final String __employeecode, final int __enterprisebrandactivitycode, final String loginip) throws Exception;
	
	/**
	 * 完善资料送红包
	 */
	public Map<String, Object> tc_redbag_userinfo(final String __employeecode, final int __enterprisebrandactivitycode, final String loginip, final String fingerprintcode) throws Exception;
}
