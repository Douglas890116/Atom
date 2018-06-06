package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.entity.EnterpriseEmployeeType;
@Repository
public interface BusinessEmployeeTypeDao {

	List<EnterpriseEmployeeType> getAllEmployeeType();

	List<EnterpriseEmployeeType> queryEmployeeType(Map<String, Object> map);

	int queryEmployeeTypeCount(Map<String, Object> map);

	void saveEmployeeType(Map<String, Object> map);

	void deleteEmployeeType(String employeetypecode);

	void updateEmployeeType(Map<String, Object> parameter);

	void deleteSelect(String[] array);

}
