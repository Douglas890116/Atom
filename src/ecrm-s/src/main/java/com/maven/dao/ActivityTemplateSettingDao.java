package com.maven.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.ActivityTemplateSetting;

@Repository
public interface ActivityTemplateSettingDao extends BaseDao<ActivityTemplateSetting>{
	
	/**
	 * 查询所有参数字段
	 * @return
	 * @throws Exception
	 */
	public List<ActivityTemplateSetting> selectUnshareFleid() throws Exception;
	
	/**
	 * 批量删除参数
	 * @param activitysettingcode
	 * @return
	 * @throws Exception
	 */
	public int deleteBatch(List<String> activitysettingcode) throws Exception;
	
	/**
	 * 根据模板ID查找模板所有参数
	 * @param templateid
	 * @return
	 * @throws Exception
	 */
	public List<ActivityTemplateSetting> selectSettingByTemplateId(Integer templateid) throws Exception;

}
