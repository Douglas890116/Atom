package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.LogLoginDao;
import com.maven.entity.LogLogin;

@Repository
public class LogLoginDaoImpl extends BaseDaoImpl<LogLogin> implements LogLoginDao{
	/**
	 * 查询用户登录日记
	 * @param object
	 * @return
	 */
	@Override
	public List<LogLogin> findEmployeeLoginLog(Map<String, Object> object) {
		return getSqlSession().selectList("LogLogin.findEmployeeLoginLog",object);
	}
	/**
	 * 查询在IP上面登录的所有用户
	 */
	@Override
	public List<LogLogin> countIpLoginUser(Map<String, Object> object) {
		return getSqlSession().selectList("LogLogin.getAllIpLoginUser", object);
	}
	
	@Override
	public int countIpLoginUserCount(Map<String, Object> object) {
		return getSqlSession().selectOne("LogLogin.getAllIpLoginUserCount", object);
	}
	
	@Override
	public List<LogLogin> selectIpByLoginaccount(String loginaccount) throws Exception {
		return getSqlSession().selectList("LogLogin.selectIpByLoginaccount", loginaccount);
	}
}
