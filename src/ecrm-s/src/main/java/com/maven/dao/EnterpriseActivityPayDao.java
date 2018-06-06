package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.EnterpriseActivityPay;

@Repository
public interface EnterpriseActivityPayDao extends BaseDao<EnterpriseActivityPay>{
	
	/**
	 * 增加打码记录
	 * @param betrecord
	 * @throws Exception
	 */
	void addBetRecord(EnterpriseActivityPay betrecord) throws Exception;
	
	/**
	 * 批量增加大码记录
	 * @param list
	 * @throws Exception
	 */
	int batchAddBetRecord(List<EnterpriseActivityPay> list) throws Exception;
	
	/**
	 * 查询需要打码的记录
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	List<EnterpriseActivityPay> selectBetRecord(Map<String, Object> parameter) throws Exception;
	
	/**
	 * 查询需要打码的记录总数
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	int selectBetRecordCount(Map<String, Object> parameter) throws Exception;
	
	int selectAllCountNoti(Map<String, Object> parameter) throws Exception ;
	
	/**
	 * 查询需要打码的记录总数之金额统计
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectBetRecordCountMoney(Map<String, Object> parameter) throws Exception;
}
