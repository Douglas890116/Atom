package com.maven.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.ActivityTemplate;

@Service
public interface ActivityTemplateService extends BaseServcie<ActivityTemplate>{

	/**
	 * 创建活动模板
	 * @param template
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int tc_addActivityTemplate(ActivityTemplate template)throws Exception;
	
	/**
	 * 编辑活动模板
	 * @param template
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int tc_editActivityTemplate(ActivityTemplate template)throws Exception;
	
	/**
	 * 批量删除活动模板
	 * @param activitycodes
	 * @return
	 */
	@DataSource("master")
	public int tc_deleteActivityTemplate(List<String> activitycodes)throws Exception;
}
