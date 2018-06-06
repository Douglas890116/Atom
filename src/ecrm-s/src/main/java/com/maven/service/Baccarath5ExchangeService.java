package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.Baccarath5Exchange;
import com.maven.entity.TakeDepositRecord;

@Service
public interface Baccarath5ExchangeService extends BaseServcie<Baccarath5Exchange>{
	
	/**
	 * 获取需要投注列表
	 * @return
	 */
	@DataSource("slave")
	List<Baccarath5Exchange> selectBetRecord(Map<String, Object> parameter) throws Exception;
	
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
	void addActivityBetRecord(Baccarath5Exchange activityBetRecord) throws Exception;
	
	/**
	 * 元宝兑换积分
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	void tc_exchange(String enterprisecode,String employeecode,double money) throws Exception;
	
	/**
	 * 新元宝兑换积分
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	void tc_dochange(String enterprisecode,String employeecode,double money) throws Exception;
	/**
	 * 代理转出积分到玩家
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	void tc_transfer(String enterprisecode,String form_employeecode,String to_employeecode,double money) throws Exception;
}
