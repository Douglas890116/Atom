package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.EmployeeMoneyChange;
@Service
public interface EmployeeMoneyChangeService extends BaseServcie<EmployeeMoneyChange>{
	
	/**
	 * 添加帐变记录
	 * @param moneyChange
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int addMoneyChangeRecord(EmployeeMoneyChange moneyChange)throws Exception;
	/**
	 * 查询账变记录
	 * @param object
	 * @return
	 */
	@DataSource("slave")
	public List<EmployeeMoneyChange> findAccountChange(Map<String, Object> object)throws Exception;
	/**
	 * 统计账变记录
	 * @param object
	 * @return
	 */
	@DataSource("slave")
	public Map<String, Object> findAccountChangeCount(Map<String, Object> object)throws Exception;

}
