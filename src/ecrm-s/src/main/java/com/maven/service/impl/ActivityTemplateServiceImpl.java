package com.maven.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.ActivityTemplateDao;
import com.maven.entity.ActivityTemplate;
import com.maven.service.ActivityTemplateService;
import com.maven.util.AttrCheckout;

@Service
public class ActivityTemplateServiceImpl extends BaseServiceImpl<ActivityTemplate> implements ActivityTemplateService{

	@Autowired
	private ActivityTemplateDao activityTemplateDao;
	
	@Override
	public BaseDao<ActivityTemplate> baseDao() {
		return activityTemplateDao;
	}

	@Override
	public Class<ActivityTemplate> getClazz() {
		return ActivityTemplate.class;
	}

	@Override
	public int tc_addActivityTemplate(ActivityTemplate template) throws Exception {
		return super.add(AttrCheckout.checkout(template, false, new String[]{"name","status"}));
	}

	@Override
	public int tc_editActivityTemplate(ActivityTemplate template) throws Exception {
		return super.update(AttrCheckout.checkout(template, false, new String[]{"activitycode"}));
	}

	@Override
	public int tc_deleteActivityTemplate(List<String> activitycodes) throws Exception {
		return super.logicDelete(activitycodes);
	}

	


}
