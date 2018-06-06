package com.maven.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ActivityLoseBonusDao;
import com.maven.entity.ActivityLoseBonus;

@Repository
public class ActivityLoseBonusDaoImpl extends BaseDaoImpl<ActivityLoseBonus> implements ActivityLoseBonusDao{

	@Override
	public ActivityLoseBonus selectLastMonthRecord(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("ActivityLoseBonus.selectLastMonthRecord", parameter);
	}

}
