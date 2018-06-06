package com.maven.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.EnterpriseBannerInfo;
import com.maven.entity.EnterpriseEmployeeLevelBonus;
import com.maven.entity.TakeDepositRecord;

@Service
public interface EnterpriseEmployeeLevelBonusService extends BaseServcie<EnterpriseEmployeeLevelBonus>{
	
	/**
	 * 获取需要投注列表
	 * @return
	 */
	@DataSource("slave")
	List<EnterpriseEmployeeLevelBonus> selectBetRecord(Map<String, Object> parameter) throws Exception;
	
	
	
	/**
	 * 增加需要打码的记录
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	void addActivityBetRecord(EnterpriseEmployeeLevelBonus activityBetRecord) throws Exception;
	
	/**
	 * 增加需要打码的记录
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	void updateBetRecord(EnterpriseEmployeeLevelBonus activityBetRecord) throws Exception;
	
	/**
	 * 获取需要投注列表
	 * @return
	 */
	@DataSource("slave")
	Map<String,BigDecimal> takeEmployeeLevelBonusMap(String employeelevelcode) throws Exception;
	
}
