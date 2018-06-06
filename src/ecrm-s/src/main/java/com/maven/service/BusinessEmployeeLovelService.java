package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.entity.EnterpriseEmployeeLevel;

@Service
public interface BusinessEmployeeLovelService {
	/**
	 * 调用获取员工级别数据方法
	 * @return Map<String, Object>
	 */
	@DataSource("slave")
	public List<EnterpriseEmployeeLevel> getAllEmployeeLovel();
	
	/**
	 * 根据编码查询单个对象
	 * @param employeelevelCode
	 * @return
	 */
	@DataSource("slave")
	public EnterpriseEmployeeLevel getOneObject(String employeelevelCode);
	
	/**
	 * 根据条件查询数据并且分页
	 * @param object
	 * @return
	 */
	@DataSource("slave")
	public List<EnterpriseEmployeeLevel> takelevelQuery(Map<String, Object> object)throws Exception;
	
	/**
	 * 根据条件统计
	 * @param object
	 * @return
	 */
	@DataSource("slave")
	public int takeLevelQueryCount(Map<String, Object> object)throws Exception;

	/**
	 * 保存方法
	 * @param employeeLevel
	 * @throws Exception
	 */
	@DataSource("master")
	public void tc_save(EnterpriseEmployeeLevel employeeLevel)throws Exception;
	
	/**
	 * 批量删除
	 * @param array
	 * @throws Exception
	 */
	@DataSource("master")
	public void tc_batchDelete(String[] array)throws Exception;
	
	/**
	 * 数据修改
	 * @param employeeLevel
	 * @throws Exception
	 */
	@DataSource("master")
	public void tc_update(EnterpriseEmployeeLevel employeeLevel)throws Exception;
	
	/**
	 * 
	 * @param parameter
	 */
	@DataSource("master")
	public void tc_delete(String deleteCode)throws Exception;

}
