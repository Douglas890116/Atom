package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.LogLogin;

@Service
public interface LogLoginService extends BaseServcie<LogLogin>{

	/**
	 * 添加登陆日志
	 * @param log
	 * @return
	 */
	@DataSource("master")
	public int addLoginLog(LogLogin log) throws Exception;
	
	/**
	 * 查询用户登录日记
	 * @param object
	 * @return
	 */
	@DataSource("slave")
	public List<LogLogin> findEmployeeLoginLog(Map<String, Object> object)throws Exception;
	
	/**
	 * 查询在IP上面登录的所有用户
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<LogLogin> queryIpLoginUser(Map<String, Object> object)throws Exception;
	
	/**
	 * 查询同IP总数
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public int queryIpLoginUserCount(Map<String, Object> object)throws Exception;
}
