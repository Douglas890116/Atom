package com.maven.dao;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.ActivityTemplate;

@Repository
public interface ActivityTemplateDao extends BaseDao<ActivityTemplate>{
	
	/**
	 * 根据活动模板名称获取活动模板
	 * @param name
	 * @return
	 * @throws Exception
	 */
	ActivityTemplate getByName(String name) throws Exception;
	
	
}
