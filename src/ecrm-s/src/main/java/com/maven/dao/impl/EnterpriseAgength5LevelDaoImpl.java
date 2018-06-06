package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.BusinessEmployeeLovelDao;
import com.maven.dao.EnterpriseAgength5LevelDao;
import com.maven.entity.EnterpriseAgength5Level;
import com.maven.entity.EnterpriseEmployeeLevel;
@Repository
public class EnterpriseAgength5LevelDaoImpl extends BaseDaoImpl<EnterpriseAgength5Level> implements EnterpriseAgength5LevelDao {
	/**
	 * 调用获取员工级别数据方法
	 * @return Map<String, Object>
	 */
	public List<EnterpriseAgength5Level> getAllEmployeeLovel() {
		return getSqlSession().selectList("EnterpriseAgength5Level.select");
	}
	
	/**
	 * 根据条件查询数据并且分页
	 * @param object
	 * @return
	 */
	@Override
	public List<EnterpriseAgength5Level> levelQuery(Map<String, Object> object) {
		return getSqlSession().selectList("EnterpriseAgength5Level.levelQuery",object);
	}
	
	/**
	 * 根据条件统计
	 * @param object
	 * @return
	 */
	@Override
	public int count(Map<String, Object> object) {
		return getSqlSession().selectOne("EnterpriseAgength5Level.count", object);
	}
	
	/**
	 * 保存方法
	 */
	@Override
	public void save(EnterpriseAgength5Level employeeLevel) {
		getSqlSession().insert("EnterpriseAgength5Level.save", employeeLevel);
	}
	
	/**
	 * 批量删除
	 */
	@Override
	public void deleteBatch(String[] array) {
		getSqlSession().update("EnterpriseAgength5Level.batchDelete", array);
	}
	
	/**
	 * 修改数据
	 */
	@Override
	public void update(EnterpriseAgength5Level employeeLevel) {
		getSqlSession().update("EnterpriseAgength5Level.update", employeeLevel);
	}
	
	/**
	 * 删除
	 */
	@Override
	public void delete(String deleteCode) {
		getSqlSession().update("EnterpriseAgength5Level.delete", deleteCode);
	}
	
	/**
	 * 根据编码查询单个对象
	 */
	@Override
	public EnterpriseAgength5Level getOneObject(String employeelevelcode) {
		return getSqlSession().selectOne("EnterpriseAgength5Level.selectObject", employeelevelcode);
	}

}
