package com.maven.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.StatisticsDao;

@Repository
@SuppressWarnings("rawtypes")
public class StatisticsDaoImpl extends BaseDaoImpl implements StatisticsDao {

	@Override
	public Map<String, String> call_ERegisterAndSave(String enterprisecode) {
		return getSqlSession().selectOne("EnterpriseStatistics.enterpriseRegisterNumAndSaveSum", enterprisecode);
	}

}
