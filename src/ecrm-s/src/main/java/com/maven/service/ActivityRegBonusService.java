package com.maven.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.maven.base.service.BaseServcie;
import com.maven.entity.ActivityRegBonus;

@Service
public interface ActivityRegBonusService extends BaseServcie<ActivityRegBonus>{

	/**
	 * 获取注册红包
	 * @param regbonuscheck
	 * @return
	 * @throws Exception
	 */
	BigDecimal tc_getRegBonus(String employeecode, Integer brandactivitycode, String ip) throws Exception;
	
}
