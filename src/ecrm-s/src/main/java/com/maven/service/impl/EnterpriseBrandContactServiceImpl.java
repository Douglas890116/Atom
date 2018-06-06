package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.dao.EnterpriseBrandContactDao;
import com.maven.entity.EnterpriseBrandContact;
import com.maven.service.EnterpriseBrandContactService;

@Service
public class EnterpriseBrandContactServiceImpl implements EnterpriseBrandContactService {
	@Autowired
	private EnterpriseBrandContactDao enterpriseBrandContactDao;
	
	@Override
	public List<EnterpriseBrandContact> queryBrandContact(Map<String, Object> parames) throws Exception {
		return enterpriseBrandContactDao.queryBrandContact(parames);
	}

	@Override
	public int queryBrandContactCount(Map<String, Object> parames) throws Exception {
		return enterpriseBrandContactDao.queryBrandContactCount(parames);
	}

	@Override
	public void deleteBrandContact(String[] array) throws Exception {
		enterpriseBrandContactDao.deleteBrandContact(array);
	}

	@Override
	public void saveBrandContact(EnterpriseBrandContact brandContact) throws Exception {
		enterpriseBrandContactDao.saveBrandContact(brandContact);
	}

	@Override
	public EnterpriseBrandContact getBrandContact(Map<String, Object> parames) throws Exception {
		return enterpriseBrandContactDao.getBrandContact(parames);
	}

	@Override
	public void updateBrandContact(EnterpriseBrandContact brandContact) throws Exception {
		enterpriseBrandContactDao.updateBrandContact(brandContact);
	}

}
