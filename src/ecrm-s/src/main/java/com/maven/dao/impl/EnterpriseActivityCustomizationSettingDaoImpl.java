package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.EnterpriseActivityCustomizationSettingDao;
import com.maven.entity.EnterpriseActivityCustomizationSetting;

@Repository
public class EnterpriseActivityCustomizationSettingDaoImpl extends BaseDaoImpl<EnterpriseActivityCustomizationSetting>
	implements EnterpriseActivityCustomizationSettingDao{

	@Override
	public int deleteByECActivitycode(Integer eCActivitycode) {
		return getSqlSession().delete("EnterpriseActivityCustomizationSetting.deleteByECActivitycode", eCActivitycode);
	}

	@Override
	public List<EnterpriseActivityCustomizationSetting> selectSettingByTwoCode(Map<String, Object> map)
			throws Exception {
		return getSqlSession().selectList("EnterpriseActivityCustomizationSetting.selectSettingByTwoCode", map);
	}
	
}
