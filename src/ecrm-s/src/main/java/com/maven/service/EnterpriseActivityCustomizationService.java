package com.maven.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.EnterpriseActivityCustomization;
import com.maven.util.ActivityUtils.ActivityCheckResult;

@Service
public interface EnterpriseActivityCustomizationService extends BaseServcie<EnterpriseActivityCustomization>{

	/**
	 * 保存定制活动
	 * @param activity
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	int saveCActivity(EnterpriseActivityCustomization activity)throws Exception;
	
	/**
	 * 修改定制活动
	 * @param activity
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	int updateCActivity(EnterpriseActivityCustomization activity)throws Exception;
	
	/**
	 * 逻辑删除
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	int tc_logicDelete(List<String> ids)throws Exception;
	
	/**
	 * 根据企业编码、活动模板查询企业是否有该活动
	 * @param enterprisecode
	 * @param activitytemplatecode
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	List<EnterpriseActivityCustomization> checkEnterpriseActivity(String enterprisecode, String activitytemplatename, ActivityCheckResult regresult) throws Exception;
	
	/**
	 * 企业活动变编码
	 * @param __enterprisebrandactivitycode
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	EnterpriseActivityCustomization selectByEnterprisebrandactivitycode(String __enterprisebrandactivitycode) throws Exception;
	
	/**
	 * 根据企业编码查找所有活动
	 * @param enterprisecode
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	List<EnterpriseActivityCustomization> selectAllByEnterprisecode(String enterprisecode) throws Exception;
}
