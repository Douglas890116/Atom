package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ActivityButBonusDao;
import com.maven.dao.LoginWhiteListDao;
import com.maven.entity.ActivityButBonus;
import com.maven.entity.LoginWhiteList;

@Repository
public class ActivityButBonusDaoImpl extends BaseDaoImpl<ActivityButBonus> implements ActivityButBonusDao{

	/**
	 * 批量删除
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public void deleteSelectIp(String[] array) throws Exception {
		getSqlSession().delete("ActivityButBonus.deleteSelectIp",array);
	}
	
	/**
	 * 保存
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public void saveActivityButBonus(ActivityButBonus obj) throws Exception {
		getSqlSession().insert("ActivityButBonus.add", obj);
	}
		
	
	/**
	 * 修改
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public void updateActivityButBonus(ActivityButBonus obj) throws Exception {
		getSqlSession().update("ActivityButBonus.update",obj);
	}
}
