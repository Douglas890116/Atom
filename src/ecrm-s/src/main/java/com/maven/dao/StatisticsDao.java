package com.maven.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsDao {

	/**
	 * 企业注册用户与存款
	 * @param enterprisecode
	 * @return
	 */
	Map<String, String> call_ERegisterAndSave(String enterprisecode);
	
}
