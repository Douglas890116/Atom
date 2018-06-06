package com.maven.dao;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.ActivityRaffleControl;

@Repository
public interface ActivityRaffleControlDao extends BaseDao<ActivityRaffleControl>{
	
	int updateRaffleTime(ActivityRaffleControl __raffle);
	
	int updateActivityRaffleControl(ActivityRaffleControl __raffle) ;
}
