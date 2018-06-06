package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.EnterpriseActivityCustomizationSetting;
import com.maven.entity.EnterpriseBrandActivity;

@Service
public interface EnterpriseActivityCustomizationSettingService extends BaseServcie<EnterpriseActivityCustomizationSetting>{

	/**
	 * 保存参数
	 * @param settings
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	int saveAgument(Integer ecactivitycode,List<EnterpriseActivityCustomizationSetting> settings)throws Exception;
	
	/**
	 * 查询活动参数
	 * @return
	 * @throws Exception
	 */
	@DataSource("salve")
	Map<Integer,String> selectActivityAgument(Integer ecactivitycode)throws Exception;
	
	/**
	 * 根据企业定制活动记录查询该活动模板参数与值
	 * @return
	 * @throws Exception
	 */
	Map<String, String> selectModelSettingKeyValue(EnterpriseBrandActivity eacus) throws Exception;
}
