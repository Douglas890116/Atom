package com.maven.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.WorkingFlowConfiguration;

@Repository
public interface WorkingFlowConfigurationDao extends BaseDao<WorkingFlowConfiguration>{
	
	/**
	 * 获取所有有效记录
	 * @return
	 */
	public List<WorkingFlowConfiguration> takeAll() throws Exception;
	
	/**
	 * 更新排序
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int updateSort(Object object) throws Exception;
	
}
