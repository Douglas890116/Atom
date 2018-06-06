package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.LogOperationDao;
import com.maven.entity.LogOperation;

@Repository
public class LogOperationDaoImpl extends BaseDaoImpl<LogOperation> implements LogOperationDao{
	
	/**
	 * 查询操作日志
	 * @param map
	 * @return
	 */
	@Override
	public List<LogOperation> findOperatingLog(Map<String, Object> map) {
		return getSqlSession().selectList("LogOperation.findOperatingLog", map);
	}
	
	/**
	 * 统计查询日志
	 * @param map
	 * @return
	 */
	@Override
	public int findOperatingLogCount(Map<String, Object> map) {
		return getSqlSession().selectOne("LogOperation.findOperatingLogCount", map);
	}

}
