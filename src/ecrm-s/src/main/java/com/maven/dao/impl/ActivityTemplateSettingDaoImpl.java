package com.maven.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ActivityTemplateSettingDao;
import com.maven.entity.ActivityTemplateSetting;

@Repository
public class ActivityTemplateSettingDaoImpl extends BaseDaoImpl<ActivityTemplateSetting> implements ActivityTemplateSettingDao{

	@Override
	public List<ActivityTemplateSetting> selectUnshareFleid() {
		return getSqlSession().selectList("ActivityTemplateSetting.selectUnshareFleid");
	}

	@Override
	public int deleteBatch(List<String> activitysettingcode) throws Exception {
		return getSqlSession().update("ActivityTemplateSetting.deleteBatch",activitysettingcode);
	}

	@Override
	public List<ActivityTemplateSetting> selectSettingByTemplateId(Integer templateid) throws Exception {
		return getSqlSession().selectList("ActivityTemplateSetting.selectSettingByTemplateId", templateid);
	}

}
