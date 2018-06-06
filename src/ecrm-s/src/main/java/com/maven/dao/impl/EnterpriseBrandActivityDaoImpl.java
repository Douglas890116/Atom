package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.EnterpriseBrandActivityDao;
import com.maven.entity.EnterpriseBrandActivity;

@Repository
public class EnterpriseBrandActivityDaoImpl extends BaseDaoImpl<EnterpriseBrandActivity> implements EnterpriseBrandActivityDao{

	@Override
	public int deleteBatch(List<String> activitycodes) throws Exception {
		return getSqlSession().update("EnterpriseBrandActivity.deleteBatch", activitycodes);
	}

	@Override
	public List<Map<String, Object>> selectActivityAgument(int __enterprisebrandactivitycode) {
		return getSqlSession().selectList("EnterpriseBrandActivity.selectActivityAgument", __enterprisebrandactivitycode);
	}

	@Override
	public List<Map<String, Object>> selectActivityByBrand(String brandcode) throws Exception {
		return getSqlSession().selectList("EnterpriseBrandActivity.selectActivityByBrand", brandcode);
	}

}
