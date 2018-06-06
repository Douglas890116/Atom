package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.EnterpriseWebviewDao;
import com.maven.entity.EnterpriseWebview;
import com.maven.service.EnterpriseWebviewService;
@Service
public class EnterpriseWebviewServiceImpl extends BaseServiceImpl<EnterpriseWebview> implements EnterpriseWebviewService {

	@Autowired
	private EnterpriseWebviewDao enterpriseWebviewDaoImpl;
	
	@Override
	public List<EnterpriseWebview> queryContactData(Map<String, Object> parames) {
		return enterpriseWebviewDaoImpl.queryContactData(parames);
	}
	
	@Override
	public List<EnterpriseWebview> queryContactByEnterpriseCode(String enterpriseCode){
		return enterpriseWebviewDaoImpl.queryContactByEnterpriseCode(enterpriseCode);
	}

	@Override
	public int queryContactDataCount(Map<String, Object> parames) {
		return enterpriseWebviewDaoImpl.queryContactDataCount(parames);
	}

	@Override
	public void saveContact(EnterpriseWebview enterpriseWebview) throws Exception {
		enterpriseWebviewDaoImpl.saveContact(enterpriseWebview);
	}

	@Override
	public void deleteContact(String enterpriseCode) throws Exception {
		enterpriseWebviewDaoImpl.deleteAllContact(enterpriseCode);
	}

	@Override
	public BaseDao<EnterpriseWebview> baseDao() {
		return enterpriseWebviewDaoImpl;
	}

	@Override
	public Class<EnterpriseWebview> getClazz() {
		return EnterpriseWebview.class;
	}

	@Override
	public void deleteByParameter(Map<String, Object> param) throws Exception {
		enterpriseWebviewDaoImpl.deleteByParameter(param);
	}

}
