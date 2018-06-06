package com.maven.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.ActivityDepositBonus;

@Service
public interface ActivityDepositBonusService extends BaseServcie<ActivityDepositBonus>{

	/**
	 * 申请存款奖金
	 * @param loginaccount
	 * @param brandcode
	 * @return
	 */
	@DataSource("master")
	public BigDecimal tc_depositBonus(String employeecode, Integer brandactivitycode) throws Exception;
	
	/**
	 * 保存记录
	 * @param loginaccount
	 * @param brandcode
	 * @return
	 */
	@DataSource("master")
	public void saveActivityDepositBonus(ActivityDepositBonus activityDepositBonus) throws Exception;
}
