package com.maven.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ActivityRegBonusDao;
import com.maven.entity.ActivityRegBonus;
import com.maven.util.ActivityUtils.ActivityUniqueinfoCheck;

@Repository
public class ActivityRegBonusDaoImpl extends BaseDaoImpl<ActivityRegBonus> implements ActivityRegBonusDao{

	@Override
	public List<ActivityRegBonus> selectByLoginaccount(String loginaccount) throws Exception {
		return getSqlSession().selectList("ActivityRegBonus.selectByLoginAccount", loginaccount);
	}

	@SuppressWarnings("serial")
	@Override
	public List<ActivityRegBonus> selectByUniqueinfo(final ActivityUniqueinfoCheck regbonuscheck) throws Exception {
		return getSqlSession().selectList("ActivityRegBonus.selectByUniqueinfo", new HashMap<String, String>(){{
			this.put("housenumber", regbonuscheck.getHousenumber());
			this.put("houseaddress", regbonuscheck.getHouseaddress());
			this.put("email", regbonuscheck.getEmail());
			this.put("phonenumber", regbonuscheck.getPhonenumber());
			this.put("payment", regbonuscheck.getPayment());
			this.put("ip", regbonuscheck.getIp());
		}});
	}

	@Override
	public void addRegBonusRecord(ActivityRegBonus regbonus) {
		getSqlSession().insert("ActivityRegBonus.insert", regbonus);
	}
	
}
