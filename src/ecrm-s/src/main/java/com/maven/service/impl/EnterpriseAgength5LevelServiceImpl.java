package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.dao.BusinessEmployeeLovelDao;
import com.maven.dao.EnterpriseAgength5LevelDao;
import com.maven.entity.EnterpriseAgength5Level;
import com.maven.entity.EnterpriseEmployeeLevel;
import com.maven.service.BusinessEmployeeLovelService;
import com.maven.service.EnterpriseAgength5LevelService;
@Service
public class EnterpriseAgength5LevelServiceImpl implements EnterpriseAgength5LevelService {
	
	@Autowired
	private EnterpriseAgength5LevelDao businessEmployeeLovelDao;
	
	/**
	 * 调用获取员工级别数据方法
	 * @return Map<String, Object>
	 */
	public List<EnterpriseAgength5Level> getAllEmployeeLovel() {
		return businessEmployeeLovelDao.getAllEmployeeLovel();
	}
	
	/**
	 * 根据条件查询数据并且分页
	 * @param object
	 * @return
	 */
	@Override
	public List<EnterpriseAgength5Level> takelevelQuery(Map<String, Object> object) throws Exception {
		return businessEmployeeLovelDao.levelQuery(object);
	}
	
	/**
	 * 根据条件统计
	 * @param object
	 * @return
	 */
	@Override
	public int takeLevelQueryCount(Map<String, Object> object) throws Exception {
		return businessEmployeeLovelDao.count(object);
	}
	
	/**
	 * 保存方法
	 */
	@Override
	public void tc_save(EnterpriseAgength5Level employeeLevel) throws Exception {
		businessEmployeeLovelDao.save(employeeLevel);
	}
	
	/**
	 * 批量删除
	 */
	@Override
	public void tc_batchDelete(String[] array) throws Exception {
		businessEmployeeLovelDao.deleteBatch(array);
	}
	
	/**
	 *  数据修改
	 */
	@Override
	public void tc_update(EnterpriseAgength5Level employeeLevel) throws Exception {
		businessEmployeeLovelDao.update(employeeLevel);
	}
	
	/**
	 * 删除
	 */
	@Override
	public void tc_delete(String deleteCode) throws Exception {
		businessEmployeeLovelDao.delete(deleteCode);
	}
	
	/**
	 * 根据编码查询单个对象
	 */
	@Override
	public EnterpriseAgength5Level getOneObject(String employeelevelCode) {
		return businessEmployeeLovelDao.getOneObject(employeelevelCode);
	}

}
