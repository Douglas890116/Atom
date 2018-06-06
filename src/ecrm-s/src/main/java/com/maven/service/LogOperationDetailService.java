package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.LogOperationDetail;

@Service
public interface LogOperationDetailService extends BaseServcie<LogOperationDetail>{

	/**
	 * 批量保存详细操作日志
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int saveBatchLog(List<LogOperationDetail> object)throws Exception;
	
	/**
	 * 查询操作日记详情
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<LogOperationDetail> findOperatingLogDetail(Map<String, Object> map);
}
