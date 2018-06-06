package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.ActivityButBonus;
import com.maven.entity.LoginWhiteList;

@Repository
public interface ActivityButBonusDao extends BaseDao<ActivityButBonus> {

	
	/**
	 * 批量删除
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public void deleteSelectIp(String[] array) throws Exception ;
	
	/**
	 * 保存
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public void saveActivityButBonus(ActivityButBonus obj) throws Exception ;
		
	
	/**
	 * 修改
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public void updateActivityButBonus(ActivityButBonus obj) throws Exception ;
}
