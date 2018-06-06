package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.BusinessEmployeeLovelDao;
import com.maven.entity.EnterpriseEmployeeLevel;
@Repository
public class BusinessEmployeeLovelDaoImpl extends BaseDaoImpl<EnterpriseEmployeeLevel> implements BusinessEmployeeLovelDao {
	/**
	 * 调用获取员工级别数据方法
	 * @return Map<String, Object>
	 */
	public List<EnterpriseEmployeeLevel> getAllEmployeeLovel() {
		return getSqlSession().selectList("EnterpriseEmployeeLevel.select");
	}
	
	/**
	 * 根据条件查询数据并且分页
	 * @param object
	 * @return
	 */
	@Override
	public List<EnterpriseEmployeeLevel> levelQuery(Map<String, Object> object) {
		return getSqlSession().selectList("EnterpriseEmployeeLevel.levelQuery",object);
	}
	
	/**
	 * 根据条件统计
	 * @param object
	 * @return
	 */
	@Override
	public int count(Map<String, Object> object) {
		return getSqlSession().selectOne("EnterpriseEmployeeLevel.count", object);
	}
	
	/**
	 * 保存方法
	 */
	@Override
	public void save(EnterpriseEmployeeLevel employeeLevel) {
		getSqlSession().insert("EnterpriseEmployeeLevel.save", employeeLevel);
	}
	
	/**
	 * 批量删除
	 */
	@Override
	public void deleteBatch(String[] array) {
		getSqlSession().update("EnterpriseEmployeeLevel.batchDelete", array);
	}
	
	/**
	 * 修改数据
	 */
	@Override
	public void update(EnterpriseEmployeeLevel employeeLevel) {
		getSqlSession().update("EnterpriseEmployeeLevel.update", employeeLevel);
	}
	
	/**
	 * 删除
	 */
	@Override
	public void delete(String deleteCode) {
		getSqlSession().update("EnterpriseEmployeeLevel.delete", deleteCode);
	}
	
	/**
	 * 根据编码查询单个对象
	 */
	@Override
	public EnterpriseEmployeeLevel getOneObject(String employeelevelCode) {
		return getSqlSession().selectOne("EnterpriseEmployeeLevel.selectObject", employeelevelCode);
	}

}
