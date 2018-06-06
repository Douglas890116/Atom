package com.maven.dao.impl;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ActivityRaffleControlDao;
import com.maven.entity.ActivityRaffleControl;

@Repository
public class ActivityRaffleControlDaoImpl extends BaseDaoImpl<ActivityRaffleControl> implements ActivityRaffleControlDao{

	@Override
	public int updateRaffleTime(ActivityRaffleControl __raffle) {
		return getSqlSession().update("ActivityRaffleControl.updateRaffleTime", __raffle);
	}

	@Override
	public int updateActivityRaffleControl(ActivityRaffleControl __raffle) {
		return getSqlSession().update("ActivityRaffleControl.update", __raffle);
	}
}
