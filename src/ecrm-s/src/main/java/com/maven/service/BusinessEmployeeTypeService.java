package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.entity.EnterpriseEmployeeType;
@Service
public interface BusinessEmployeeTypeService {
	/**
	 * 查询所有的用户类型
	 * @return
	 */
	@DataSource("slave")
	List<EnterpriseEmployeeType> getAllEmployeeType();
	/**
	 * 根据条件查询
	 */
	@DataSource("slave")
	List<EnterpriseEmployeeType> queryEmployeeType(Map<String,Object> map)throws Exception;
	/**
	 * 根据条件统计
	 */
	@DataSource("slave")
	int queryEmployeeTypeCount(Map<String,Object> map)throws Exception;
	/**
	 * 保存用户类型
	 * @param map
	 */
	@DataSource("master")
	void saveEmployeeType(Map<String, Object> map)throws Exception;
	/**
	 * 删除用户类型
	 * @param string
	 * @throws Exception
	 */
	@DataSource("master")
	void deleteEmployeeType(String employeetypecode)throws Exception;
	/**
	 * 修改用户类型
	 * @param parameter
	 * @throws Exception
	 */
	@DataSource("master")
	void updateEmployeeType(Map<String, Object> parameter)throws Exception;
	/**
	 * 批量删除用户类型
	 * @param string
	 * @throws Exception
	 */
	@DataSource("master")
	void tc_deleteSelect(String[] array)throws Exception;
}
