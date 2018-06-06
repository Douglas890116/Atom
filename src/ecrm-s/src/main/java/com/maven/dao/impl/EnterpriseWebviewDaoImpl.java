package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.EnterpriseWebviewDao;
import com.maven.entity.EnterpriseWebview;
@Repository
public class EnterpriseWebviewDaoImpl extends BaseDaoImpl<EnterpriseWebview> implements EnterpriseWebviewDao {

	@Override
	public List<EnterpriseWebview> queryContactData(Map<String, Object> parames) {
		return getSqlSession().selectList("EnterpriseWebview.selectAll", parames);
	}
	
	@Override
	public List<EnterpriseWebview> queryContactByEnterpriseCode(String enterpriseCode) {
		return getSqlSession().selectList("EnterpriseWebview.selectByEnterpriseCode", enterpriseCode);
	}

	@Override
	public int queryContactDataCount(Map<String, Object> parames) {
		return getSqlSession().selectOne("EnterpriseWebview.selectCount",parames);
	}
	
	@Override
	public void saveContact(EnterpriseWebview enterpriseWebView) {
		getSqlSession().insert("EnterpriseWebview.insert", enterpriseWebView);
	}

	@Override
	public void deleteAllContact(String enterpriseCode) {
		getSqlSession().delete("EnterpriseWebview.deleteAll", enterpriseCode);
	}
	
	@Override
	public void deleteByParameter(Map<String, Object> param) {
		getSqlSession().delete("EnterpriseWebview.deleteByParameter", param);
	}

}
