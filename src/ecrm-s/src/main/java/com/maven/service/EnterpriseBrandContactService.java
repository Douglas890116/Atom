package com.maven.service;

import java.util.List;
import java.util.Map;

import com.maven.base.dao.DataSource;
import com.maven.entity.EnterpriseBrandContact;

public interface EnterpriseBrandContactService {

	
	@DataSource("slave")
	List<EnterpriseBrandContact> queryBrandContact(Map<String, Object> parames)throws Exception;

	@DataSource("slave")
	int queryBrandContactCount(Map<String, Object> parames)throws Exception;

	@DataSource("master")
	void deleteBrandContact(String[] array)throws Exception;

	@DataSource("master")
	void saveBrandContact(EnterpriseBrandContact brandContact)throws Exception;

	@DataSource("slave")
	EnterpriseBrandContact getBrandContact(Map<String, Object> parames)throws Exception;

	@DataSource("master")
	void updateBrandContact(EnterpriseBrandContact brandContact)throws Exception;
	
}
