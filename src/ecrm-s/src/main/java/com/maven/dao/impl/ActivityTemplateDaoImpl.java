package com.maven.dao.impl;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ActivityTemplateDao;
import com.maven.entity.ActivityTemplate;

@Repository
public class ActivityTemplateDaoImpl extends BaseDaoImpl<ActivityTemplate> implements ActivityTemplateDao{

	@Override
	public ActivityTemplate getByName(String name) throws Exception {
		return getSqlSession().selectOne("ActivityTemplate.selectByName", name);
	}

}
