package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.LogOperation;
import com.maven.entity.LogOperationDetail;

@Service
public interface LogOperationService extends BaseServcie<LogOperation>{

	/**
	 * 保存操作日志
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int saveLog(LogOperation log,List<LogOperationDetail> logDetail)throws Exception;
	
	/**
	 * 查询操作日志
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<LogOperation> findOperatingLog(Map<String, Object> map)throws Exception;
	
	/**
	 * 统计查询日志
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public int findOperatingLogCount(Map<String, Object> map)throws Exception;
}
