package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.EnterpriseReportDates;
import com.maven.entity.TakeDepositRecord;

@Repository
public interface EnterpriseReportDatesDao extends BaseDao<EnterpriseReportDates>{
	
	/**
	 * 增加打码记录
	 * @param betrecord
	 * @throws Exception
	 */
	void addBetRecord(EnterpriseReportDates betrecord) throws Exception;
	
	/**
	 * 批量新增
	 * @param betrecord
	 * @throws Exception
	 */
	void saveBatchRecord(List<EnterpriseReportDates> list) throws Exception;
	
	/**
	 * 查询需要打码的记录
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	List<EnterpriseReportDates> selectBetRecord(Map<String, Object> parameter) throws Exception;
	
	/**
	 * 查询需要打码的记录总数
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	int selectBetRecordCount(Map<String, Object> parameter) throws Exception;
	
	/**
	 * 查询需要打码的记录总数之金额统计
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectBetRecordCountMoney(Map<String, Object> parameter) throws Exception;
	
	/**
	 * 存储过程,总计-取款稽查
	 */
	List<EnterpriseReportDates> userDepositTakeReport(Map<String, Object> paramObj) throws Exception ;
	
	
}
