package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.LogOperation;

@Repository
public interface LogOperationDao extends BaseDao<LogOperation> {
	
	/**
	 * 查询操作日志
	 * @param map
	 * @return
	 */
	List<LogOperation> findOperatingLog(Map<String, Object> map);
	
	/**
	 * 统计查询日志
	 * @param map
	 * @return
	 */
	int findOperatingLogCount(Map<String, Object> map);

}
