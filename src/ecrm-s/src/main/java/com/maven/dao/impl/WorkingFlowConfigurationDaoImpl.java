package com.maven.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.WorkingFlowConfigurationDao;
import com.maven.entity.WorkingFlowConfiguration;

@Repository
public class WorkingFlowConfigurationDaoImpl extends BaseDaoImpl<WorkingFlowConfiguration> implements WorkingFlowConfigurationDao{

	@Override
	public List<WorkingFlowConfiguration> takeAll() throws Exception{
		return super.selectAll("WorkingFlowConfiguration.select", null);
	}

	@Override
	public int updateSort(Object object) throws Exception {
		return this.update("WorkingFlowConfiguration.updateSort", object);
	}

}
