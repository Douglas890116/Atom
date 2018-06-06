package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.entity.EnterpriseEmployeeLevel;
@Repository
public interface BusinessEmployeeLovelDao {
	/**
	 * 调用获取员工级别数据方法
	 * @return Map<String, Object>
	 */
	public List<EnterpriseEmployeeLevel> getAllEmployeeLovel();
	
	/**
	 * 根据条件查询数据并且分页
	 * @param object
	 * @return
	 */
	public List<EnterpriseEmployeeLevel> levelQuery(Map<String, Object> object);
	
	/**
	 * 根据条件统计
	 * @param object
	 * @return
	 */
	public int count(Map<String, Object> object);
	
	/**
	 * 保存方法
	 * @param employeeLevel
	 */
	public void save(EnterpriseEmployeeLevel employeeLevel);
	
	/**
	 * 批量删除
	 * @param array
	 */
	public void deleteBatch(String[] array);
	
	/**
	 * 修改
	 * @param employeeLevel
	 */
	public void update(EnterpriseEmployeeLevel employeeLevel);
	
	/**
	 * 删除
	 */
	public void delete(String deleteCode);
	
	/**
	 * 根据编码查询单个对象
	 * @param employeelevelCode
	 * @return
	 */
	public EnterpriseEmployeeLevel getOneObject(String employeelevelCode);

}
