package com.maven.dao;

import java.util.List;
import java.util.Map;

import com.maven.entity.EnterpriseBrandContact;

public interface EnterpriseBrandContactDao {

	List<EnterpriseBrandContact> queryBrandContact(Map<String, Object> parames);

	int queryBrandContactCount(Map<String, Object> parames);

	void deleteBrandContact(String[] array);

	void saveBrandContact(EnterpriseBrandContact brandContact);

	EnterpriseBrandContact getBrandContact(Map<String, Object> parames);

	void updateBrandContact(EnterpriseBrandContact brandContact);

}
