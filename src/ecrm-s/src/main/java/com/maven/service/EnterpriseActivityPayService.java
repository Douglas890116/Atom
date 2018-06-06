package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.EnterpriseActivityPay;

@Service
public interface EnterpriseActivityPayService extends BaseServcie<EnterpriseActivityPay>{
	
	/**
	 * 获取需要投注列表
	 * @return
	 */
	@DataSource("slave")
	List<EnterpriseActivityPay> selectBetRecord(Map<String, Object> parameter) throws Exception;
	
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
	void addActivityBetRecord(EnterpriseActivityPay activityBetRecord) throws Exception;
	
	@DataSource("slave")
	int selectAllCountNoti(Map<String, Object> parameter) throws Exception;
	
	/**
	 * 批量增加需要大码的记录
	 * @param list
	 * @throws Exception
	 */
	@DataSource("master")
	int batchAddActivityBetRecord(List<EnterpriseActivityPay> list) throws Exception;
	
	/**
	 * 修改需要打码的记录
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	void updateActivityBetRecord(EnterpriseActivityPay activityBetRecord) throws Exception;
}
