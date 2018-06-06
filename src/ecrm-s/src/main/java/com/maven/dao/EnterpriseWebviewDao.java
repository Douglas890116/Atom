package com.maven.dao;

import java.util.List;
import java.util.Map;

import com.maven.base.dao.BaseDao;
import com.maven.entity.EnterpriseWebview;

public interface EnterpriseWebviewDao extends BaseDao<EnterpriseWebview> {

	List<EnterpriseWebview> queryContactData(Map<String, Object> parames);
	
	List<EnterpriseWebview> queryContactByEnterpriseCode(String enterpriseCode);

	int queryContactDataCount(Map<String, Object> parames);

	void saveContact(EnterpriseWebview enterpriseWebview);

	void deleteAllContact(String enterpriseCode);
	
	void deleteByParameter(Map<String, Object> param);
	
}
