package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.entity.EmployeeMoneyChangeType;

@Repository
public interface EmployeeMoneyChangeTypeDao {

	EmployeeMoneyChangeType getEmployeeMoneyChangeType(String moneyChangeTypeCode);

	List<EmployeeMoneyChangeType> findEmployeeMoneyChangeTypeData(Map<String, Object> map);

	int findEmployeeMoneyChangeTypeDataCount(Map<String, Object> map);

	void deleteEmployeeMoneyChangeType(String moneyChangeTypeCode);

	void deleteSelectEmployeeMoneyChangeType(String[] array);

	void saveEmployeeMoneyChangeType(EmployeeMoneyChangeType employeeMoneyChangeType);

	void updateEmployeeMoneyChangeType(EmployeeMoneyChangeType employeeMoneyChangeType);

	List<EmployeeMoneyChangeType> findEmployeeMoneyChangeType(Map<String, Object> map);

}
