package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ActivityRaffleSigninDao;
import com.maven.entity.ActivityRaffleSignin;

@Repository
public class ActivityRaffleSigninDaoImpl extends BaseDaoImpl<ActivityRaffleSignin> implements ActivityRaffleSigninDao{

	@Override
	public String usp_raffle_signin(Object object) {
		return getSqlSession().selectOne("ActivityRaffleSignin.usp_raffle_signin", object);
	}

	@Override
	public List<ActivityRaffleSignin> selectSigninRecord(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectList("ActivityRaffleSignin.selectSigninRecord", parameter);
	}

}
