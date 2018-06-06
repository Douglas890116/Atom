package com.maven.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.ActivityTemplateSetting;

@Service
public interface ActivityTemplateSettingService extends BaseServcie<ActivityTemplateSetting>{
	
	/**
	 * 查询所有参数字段
	 * @return
	 */
	@DataSource("slave")
	public List<ActivityTemplateSetting> selectUnshareFleid() throws Exception;
	
	/**
	 * 保存模板参数
	 * @param setting
	 * @return
	 */
	@DataSource("master")
	public int tc_saveActivityTemplateSetting(ActivityTemplateSetting setting) throws Exception;
	
	/**
	 * 修改模板参数
	 * @param setting
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int tc_updateActivityTemplateSetting(ActivityTemplateSetting setting)  throws Exception;
	
	/**
	 * 批量删除参数
	 * @param activitysettingcode
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int tc_deleteBatchActivityTemplateSetting(List<String> activitysettingcode) throws Exception;

}
