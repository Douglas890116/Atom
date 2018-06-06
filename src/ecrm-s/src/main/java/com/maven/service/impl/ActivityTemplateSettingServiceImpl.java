package com.maven.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.ActivityTemplateSettingDao;
import com.maven.entity.ActivityTemplateSetting;
import com.maven.service.ActivityTemplateSettingService;
import com.maven.util.AttrCheckout;

@Service
 public class ActivityTemplateSettingServiceImpl extends BaseServiceImpl<ActivityTemplateSetting> implements ActivityTemplateSettingService{

	@Autowired
	private ActivityTemplateSettingDao activityTemplateSettingDao;
	
	@Override
	public BaseDao<ActivityTemplateSetting> baseDao() {
		return activityTemplateSettingDao;
	}

	@Override
	public Class<ActivityTemplateSetting> getClazz() {
		return ActivityTemplateSetting.class;
	}

	@Override
	public List<ActivityTemplateSetting> selectUnshareFleid() throws Exception{
		return activityTemplateSettingDao.selectUnshareFleid();
	}

	@Override
	public int tc_saveActivityTemplateSetting(ActivityTemplateSetting setting) throws Exception {
		return super.add(AttrCheckout.checkout(setting, false, new String[]{"activitycode","agementname","agementdesc"}));
	}

	@Override
	public int tc_updateActivityTemplateSetting(ActivityTemplateSetting setting) throws Exception {
		return super.update(AttrCheckout.checkout(setting, false, new String[]{"activitysettingcode"}));
	}

	@Override
	public int tc_deleteBatchActivityTemplateSetting(List<String> activitysettingcode) throws Exception {
		return activityTemplateSettingDao.deleteBatch(activitysettingcode);
	}

}
