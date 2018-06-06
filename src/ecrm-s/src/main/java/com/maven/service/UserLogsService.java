package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.UserLogs;

@Service
public interface UserLogsService extends BaseServcie<UserLogs>{
	
	/**
	 * 获取需要投注列表
	 * @return
	 */
	@DataSource("slave")
	List<UserLogs> selectBetRecord(Map<String, Object> parameter) throws Exception;
	
	/**
	 * 需要投注列表总数
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	int selectBetRecordCount(Map<String, Object> parameter) throws Exception;
	
	
	/**
	 * 增加需要打码的记录
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	void addActivityBetRecord(UserLogs activityBetRecord) throws Exception;
	/**
	 * 批量增加打码记录
	 * @param activityBetRecord
	 * @throws Exception
	 */
	@DataSource("master")
	void batchAddActivityBetRecord(List<UserLogs> activityBetRecordList) throws Exception;
}
