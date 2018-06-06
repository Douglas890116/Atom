package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.entity.EmployeeMoneyChangeType;

@Service
public interface EmployeeMoneyChangeTypeService{
	
	/**
	 * 根据资金类型编码查询
	 * @param moneyChangeTypeCode
	 * @return EmployeeMoneyChangeType
	 */
	@DataSource("slave")
	EmployeeMoneyChangeType getEmployeeMoneyChangeType(String moneyChangeTypeCode)throws Exception;
	
	
	/**
	 * 根据条件查询
	 * @param map
	 * @return List<EmployeeMoneyChangeType>
	 */
	@DataSource("slave")
	List<EmployeeMoneyChangeType> findEmployeeMoneyChangeTypeData(Map<String, Object> map)throws Exception;
	
	/**
	 * 根据条件查询统计
	 * @param map
	 * @return int
	 */
	@DataSource("slave")
	int findEmployeeMoneyChangeTypeDataCount(Map<String, Object> map)throws Exception;
	
	/**
	 * 根据编码删除
	 * @param moneyChangeTypeCode
	 */
	@DataSource("master")
	void deleteEmployeeMoneyChangeType(String moneyChangeTypeCode)throws Exception;
	
	/**
	 * 根据编码批量删除
	 * @param array
	 */
	@DataSource("master")
	void deleteSelectEmployeeMoneyChangeType(String[] array)throws Exception;
	
	/**
	 * 保存资金类型
	 * @param employeeMoneyChangeType
	 */
	@DataSource("master")
	void saveEmployeeMoneyChangeType(EmployeeMoneyChangeType employeeMoneyChangeType)throws Exception;
	
	/**
	 * 修改资金类型
	 * @param employeeMoneyChangeType
	 */
	@DataSource("master")
	void updateEmployeeMoneyChangeType(EmployeeMoneyChangeType employeeMoneyChangeType)throws Exception;

	/**
	 * 根据活动类别查询对应的活动类型
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	List<EmployeeMoneyChangeType> findEmployeeMoneyChangeType(Map<String, Object> map)throws Exception;


}
