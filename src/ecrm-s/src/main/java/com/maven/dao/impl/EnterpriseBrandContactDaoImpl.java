package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.maven.dao.EnterpriseBrandContactDao;
import com.maven.entity.EnterpriseBrandContact;

@Repository
public class EnterpriseBrandContactDaoImpl extends SqlSessionDaoSupport implements EnterpriseBrandContactDao {

	@Override
	public List<EnterpriseBrandContact> queryBrandContact(Map<String, Object> parames) {
		return getSqlSession().selectList("EnterpriseBrandContact.queryBrandContact", parames);
	}

	@Override
	public int queryBrandContactCount(Map<String, Object> parames) {
		return getSqlSession().selectOne("EnterpriseBrandContact.queryBrandContactCount",parames);
	}

	@Override
	public void deleteBrandContact(String[] array) {
		getSqlSession().update("EnterpriseBrandContact.deleteBrandContact",array);
	}

	@Override
	public void saveBrandContact(EnterpriseBrandContact brandContact) {
		getSqlSession().insert("EnterpriseBrandContact.saveBrandContact", brandContact);
	}

	@Override
	public EnterpriseBrandContact getBrandContact(Map<String, Object> parames) {
		return getSqlSession().selectOne("EnterpriseBrandContact.getBrandContact", parames);
	}

	@Override
	public void updateBrandContact(EnterpriseBrandContact brandContact) {
		getSqlSession().update("EnterpriseBrandContact.updateBrandContact", brandContact);
	}

}
