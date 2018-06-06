package com.maven.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.ActivityRegBonus;
import com.maven.util.ActivityUtils.ActivityUniqueinfoCheck;

@Repository
public interface ActivityRegBonusDao extends BaseDao<ActivityRegBonus>{
	
	/**
	 * 根据用户名查询领取记录
	 * @param loginaccount
	 * @return
	 * @throws Exception
	 */
	List<ActivityRegBonus> selectByLoginaccount(String loginaccount) throws Exception;
	
	/**
	 * 根据身份信息查询领取记录
	 * @param regbonuscheck
	 * @return
	 * @throws Exception
	 */
	List<ActivityRegBonus> selectByUniqueinfo(ActivityUniqueinfoCheck regbonuscheck) throws Exception;
	
	/**
	 * 增加开户彩金领取记录
	 * @param regbonus
	 */
	void addRegBonusRecord(ActivityRegBonus regbonus);
}
