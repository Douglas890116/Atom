package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ApiSoltGametypeEnterprise;
import com.maven.entity.TakeDepositRecord;

@Service
public interface ApiSoltGametypeEnterpriseService extends BaseServcie<ApiSoltGametypeEnterprise>{
	
	@DataSource("slave")
	List<ApiSoltGametypeEnterprise> selectTypes(Map<String, Object> parameter) throws Exception;
	
	@DataSource("master")
	void deleteByEnterprisecode(String enterprisecode) throws Exception;
	
	/**
	 * 获取需要投注列表
	 * @return
	 */
	@DataSource("slave")
	List<ApiSoltGametypeEnterprise> selectBetRecord(Map<String, Object> parameter) throws Exception;
	/**
	 * 需要投注列表总数
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	int selectBetRecordCount(Map<String, Object> parameter) throws Exception;
	
	
	/**
	 * 获取需要投注列表
	 * @return
	 */
	@DataSource("slave")
	List<ApiSoltGametypeEnterprise> selectAdd(Map<String, Object> parameter) throws Exception;
	/**
	 * 需要投注列表总数
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	int selectAddCount(Map<String, Object> parameter) throws Exception;
	
	
	
	/**
	 * 获取需要投注列表
	 * @return
	 */
	@DataSource("slave")
	List<ApiSoltGametypeEnterprise> select(Map<String, Object> parameter) throws Exception;
	
	
	
	
	/**
	 * 增加需要打码的记录
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	void addActivityBetRecord(ApiSoltGametypeEnterprise activityBetRecord) throws Exception;
}
