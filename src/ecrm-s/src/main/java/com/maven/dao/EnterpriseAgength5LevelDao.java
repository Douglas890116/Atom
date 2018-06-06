package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.entity.EnterpriseAgength5Level;
import com.maven.entity.EnterpriseEmployeeLevel;
@Repository
public interface EnterpriseAgength5LevelDao {
	/**
	 * 调用获取员工级别数据方法
	 * @return Map<String, Object>
	 */
	public List<EnterpriseAgength5Level> getAllEmployeeLovel();
	
	/**
	 * 根据条件查询数据并且分页
	 * @param object
	 * @return
	 */
	public List<EnterpriseAgength5Level> levelQuery(Map<String, Object> object);
	
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
	public void save(EnterpriseAgength5Level employeeLevel);
	
	/**
	 * 批量删除
	 * @param array
	 */
	public void deleteBatch(String[] array);
	
	/**
	 * 修改
	 * @param employeeLevel
	 */
	public void update(EnterpriseAgength5Level employeeLevel);
	
	/**
	 * 删除
	 */
	public void delete(String deleteCode);
	
	/**
	 * 根据编码查询单个对象
	 * @param employeelevelCode
	 * @return
	 */
	public EnterpriseAgength5Level getOneObject(String employeelevelcode);

}
