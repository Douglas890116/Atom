package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.LogLoginDao;
import com.maven.entity.LogLogin;
import com.maven.service.LogLoginService;
import com.maven.util.AttrCheckout;

@Service
public class LogLoginServiceImpl extends BaseServiceImpl<LogLogin> implements LogLoginService{

	@Autowired
	private LogLoginDao logLoginDao;
	
	@Override
	public BaseDao<LogLogin> baseDao() {
		return logLoginDao;
	}

	@Override
	public Class<LogLogin> getClazz() {
		return LogLogin.class;
	}

	@Override
	public int addLoginLog(LogLogin log)  throws Exception{
		return super.add(AttrCheckout.checkout(log, false, new String[]{"employeecode","parentemployeecode","enterprisecode","loginaccount","logintime","loginip"}));
	}

	/**
	 * 查询用户登录日记
	 * @param object
	 * @return
	 */
	@Override
	public List<LogLogin> findEmployeeLoginLog(Map<String, Object> object) throws Exception {
		return logLoginDao.findEmployeeLoginLog(object);
	}

	/**
	 * 查询在IP上面登录的所有用户
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<LogLogin> queryIpLoginUser(Map<String, Object> object) throws Exception {
		return logLoginDao.countIpLoginUser(AttrCheckout.checkout(object, false, new String[]{"enterprisecode"}));
	}
	
	@Override
	public int queryIpLoginUserCount(Map<String, Object> object)throws Exception{
		return logLoginDao.countIpLoginUserCount(AttrCheckout.checkout(object, false, new String[]{"enterprisecode"}));
	}

}
