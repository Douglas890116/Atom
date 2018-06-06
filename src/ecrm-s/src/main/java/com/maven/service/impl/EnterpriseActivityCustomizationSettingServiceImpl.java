package com.maven.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.ActivityTemplateSettingDao;
import com.maven.dao.EnterpriseActivityCustomizationSettingDao;
import com.maven.entity.ActivityTemplateSetting;
import com.maven.entity.EnterpriseActivityCustomizationSetting;
import com.maven.entity.EnterpriseBrandActivity;
import com.maven.service.EnterpriseActivityCustomizationSettingService;
import com.maven.util.AttrCheckout;

@Service
public class EnterpriseActivityCustomizationSettingServiceImpl extends BaseServiceImpl<EnterpriseActivityCustomizationSetting>
	implements EnterpriseActivityCustomizationSettingService{

	@Autowired
	private EnterpriseActivityCustomizationSettingDao enterpriseActivityCustomizationSettingDao;
	@Autowired
	private ActivityTemplateSettingDao activityTemplateSettingDao;
	
	@Override
	public BaseDao<EnterpriseActivityCustomizationSetting> baseDao() {
		return enterpriseActivityCustomizationSettingDao;
	}

	@Override
	public Class<EnterpriseActivityCustomizationSetting> getClazz() {
		return EnterpriseActivityCustomizationSetting.class;
	}

	@Override
	public int saveAgument(Integer ecactivitycode,List<EnterpriseActivityCustomizationSetting> settings) throws Exception {
		/**
		 * 删除原有参数
		 */
		enterpriseActivityCustomizationSettingDao.deleteByECActivitycode(ecactivitycode);
		/**
		 * 写入新的参数
		 */
		super.saveRecordBatch(AttrCheckout.checkout(settings, false, new String[]{"ecactivitycode","activitysettingcode","agementvalue"}));
		return 1;
	}

	@Override
	public Map<Integer, String> selectActivityAgument(Integer ecactivitycode) throws Exception {
		List<EnterpriseActivityCustomizationSetting> __auments = super.select(
				new EnterpriseActivityCustomizationSetting(ecactivitycode));
		Map<Integer,String> __aument = new HashMap<Integer, String>();
		for (EnterpriseActivityCustomizationSetting __eac : __auments) {
			__aument.put(__eac.getActivitysettingcode(), __eac.getAgementvalue());
		}
		return __aument;
	}

	@Override
	public Map<String, String> selectModelSettingKeyValue(EnterpriseBrandActivity eacus) throws Exception {
		//1根据企业定制活动信息查找活动模板所有参数，2根据活动模板所有参与企业定制活动信息查找所有参数值，3拼装成map返回key为活动模板code value为企业定制参数
		Map<String, String> keyvalue = new HashMap<String, String>();
		//1
		List<ActivityTemplateSetting> settings = activityTemplateSettingDao.selectSettingByTemplateId(eacus.getEcactivitycode());
		if (settings == null || settings.isEmpty()){
			return null;
		}
		//2
		List<Integer> activitysettingcodes = new ArrayList<Integer>();
		for (ActivityTemplateSetting forsettings : settings) {
			activitysettingcodes.add(forsettings.getActivitysettingcode());
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ecactivitycode", eacus.getEcactivitycode());
		params.put("activitysettingcode", activitysettingcodes);
		List<EnterpriseActivityCustomizationSetting> eacsettings = this.enterpriseActivityCustomizationSettingDao.selectSettingByTwoCode(params);
		//3
		for (ActivityTemplateSetting forsettings : settings) {
			for (EnterpriseActivityCustomizationSetting foreacsettings : eacsettings) {
				if (forsettings.getActivitysettingcode().equals(foreacsettings.getActivitysettingcode())){
					keyvalue.put(forsettings.getAgementname(), foreacsettings.getAgementvalue());
				}
			}
		}
		return keyvalue;
	}

}
