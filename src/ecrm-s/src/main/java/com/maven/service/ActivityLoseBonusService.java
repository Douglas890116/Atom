package com.maven.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.ActivityLoseBonus;

@Service
public interface ActivityLoseBonusService extends BaseServcie<ActivityLoseBonus>{

	/**
	 * 申请月输值返利奖金
	 * @param loginaccount
	 * @param brandcode
	 * @return
	 */
	@DataSource("master")
	public BigDecimal tc_monthLoseBonus(String employeecode, Integer brandactivitycode) throws Exception;
}
