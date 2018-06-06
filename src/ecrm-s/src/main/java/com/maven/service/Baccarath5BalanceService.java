package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.Baccarath5Balance;
import com.maven.entity.Baccarath5Exchange;
import com.maven.entity.TakeDepositRecord;

@Service
public interface Baccarath5BalanceService extends BaseServcie<Baccarath5Balance>{
	
	/**
	 * 获取需要投注列表
	 * @return
	 */
	@DataSource("slave")
	List<Baccarath5Balance> selectBetRecord(Map<String, Object> parameter) throws Exception;
	
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
	void addActivityBetRecord(Baccarath5Balance activityBetRecord) throws Exception;
	
	/**
	 * 更新余额
	 * @param employeecode
	 * @param money 本次更新的余额（可能是正数，也可能是负数）
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	int updateBalance(String employeecode, double money) throws Exception;
}
