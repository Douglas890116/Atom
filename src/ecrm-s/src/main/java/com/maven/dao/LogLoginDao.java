package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.LogLogin;

@Repository
public interface LogLoginDao extends BaseDao<LogLogin>{
	/**
	 * 查询用户登录日记
	 * @param object
	 * @return
	 */
	List<LogLogin> findEmployeeLoginLog(Map<String, Object> object);
	/**
	 * 查询同IP用户
	 * @param object
	 * @return
	 */
	List<LogLogin> countIpLoginUser(Map<String, Object> object);
	
	/**
	 * 查询同IP用户总数
	 * @param object
	 * @return
	 */
	int countIpLoginUserCount(Map<String, Object> object);
	
	/**
	 * 根据用户名查询所有登录过的IP
	 * @param loginaccount
	 * @return
	 * @throws Exception
	 */
	List<LogLogin> selectIpByLoginaccount(String loginaccount) throws Exception;
}
