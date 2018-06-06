package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.DataHandle;
import com.maven.entity.DataHandleLogs;
import com.maven.entity.DataHandleMaintenance;
import com.maven.entity.TakeDepositRecord;

@Service
public interface DataHandleLogsService extends BaseServcie<DataHandleLogs>{
	
	/**
	 * 获取需要投注列表
	 * @return
	 */
	@DataSource("slave")
	List<DataHandleLogs> selectBetRecord(Map<String, Object> parameter) throws Exception;
	
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
	void addActivityBetRecord(DataHandleLogs activityBetRecord) throws Exception;
	
	/**
	 * 增加需要打码的记录
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	void updateActivityBetRecord(DataHandleLogs activityBetRecord) throws Exception;
}
