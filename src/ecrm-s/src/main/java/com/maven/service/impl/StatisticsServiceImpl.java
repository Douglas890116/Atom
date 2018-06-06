package com.maven.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.dao.StatisticsDao;
import com.maven.service.StatisticsService;

@Service
public class StatisticsServiceImpl implements StatisticsService{
	
	@Autowired
	private StatisticsDao statisticsDao;

	@Override
	public Map<String, String> call_ERegisterAndSave(String enterprisecode) throws Exception{
		return statisticsDao.call_ERegisterAndSave(enterprisecode);
	}
	
	
	

}
