package com.maven.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;

@Service
public interface StatisticsService {
	
	/**
	 * 企业注册用户与存款
	 * @param enterprisecode
	 * @return
	 */
	@DataSource("slave")
	Map<String,String> call_ERegisterAndSave(String enterprisecode)throws Exception;

}
