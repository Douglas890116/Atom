package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.EnterpriseReportDates;
import com.maven.entity.TakeDepositRecord;

@Service
public interface EnterpriseReportDatesService extends BaseServcie<EnterpriseReportDates>{
	
	/**
	 * 获取需要投注列表
	 * @return
	 */
	@DataSource("slave")
	List<EnterpriseReportDates> selectBetRecord(Map<String, Object> parameter) throws Exception;
	
	/**
	 * 需要投注列表总数
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	int selectBetRecordCount(Map<String, Object> parameter) throws Exception;
	
	@DataSource("slave")
	Map<String, Object> selectBetRecordCountMoney(Map<String, Object> parameter) throws Exception;
	
	/**
	 * 增加需要打码的记录
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	void addActivityBetRecord(EnterpriseReportDates activityBetRecord) throws Exception;
	
	/**
	 * 批量新增
	 * @param betrecord
	 * @throws Exception
	 */
	@DataSource("master")
	void saveBatchRecord(List<EnterpriseReportDates> list) throws Exception;
	
	/**
	 * 用户存款排名报表查询
	 * @param paramObj
	 * @return
	 */
	@DataSource("slave")
	List<EnterpriseReportDates> call_userDepositTakeReport(Map<String, Object> paramObj)throws Exception;
}
