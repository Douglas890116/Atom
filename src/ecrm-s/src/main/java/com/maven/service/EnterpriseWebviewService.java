package com.maven.service;

import java.util.List;
import java.util.Map;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.EnterpriseWebview;

public interface EnterpriseWebviewService extends BaseServcie<EnterpriseWebview>{
	
	@DataSource("slave")
	List<EnterpriseWebview> queryContactData(Map<String, Object> parames);
	
	@DataSource("slave")
	List<EnterpriseWebview> queryContactByEnterpriseCode(String enterpriseCode);

	@DataSource("slave")
	int queryContactDataCount(Map<String, Object> parames);

	@DataSource("master")
	void saveContact(EnterpriseWebview enterpriseWebview)throws Exception;

	@DataSource("master")
	void deleteContact(String enterpriseCode)throws Exception;
	
	@DataSource("master")
	void deleteByParameter(Map<String, Object> param) throws Exception;
}
