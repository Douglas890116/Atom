package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityButBonus;
import com.maven.entity.TakeDepositRecord;

@Service
public interface ActivityButBonusService extends BaseServcie<ActivityButBonus>{
	
	/**
	 * 保存
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	void saveActivityButBonus(ActivityButBonus obj) throws Exception ;
	
}
