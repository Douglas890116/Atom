package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.EmployeeMoneyChangeTypeDao;
import com.maven.entity.EmployeeMoneyChangeType;
@Repository
public class EmployeeMoneyChangeTypeDaoImpl extends BaseDaoImpl<EmployeeMoneyChangeType> implements EmployeeMoneyChangeTypeDao {
	
	/**
	 * 根据资金类型编码查询
	 * @param moneyChangeTypeCode
	 * @return EmployeeMoneyChangeType
	 */
	@Override
	public EmployeeMoneyChangeType getEmployeeMoneyChangeType(String moneyChangeTypeCode) {
		return getSqlSession().selectOne("EmployeeMoneyChangeType.getEmployeeMoneyChangeType", moneyChangeTypeCode);
	}
	
	/**
	 * 根据条件查询
	 * @param map
	 * @return List<EmployeeMoneyChangeType>
	 */
	@Override
	public List<EmployeeMoneyChangeType> findEmployeeMoneyChangeTypeData(Map<String, Object> map) {
		return getSqlSession().selectList("EmployeeMoneyChangeType.select", map);
	}
	
	/**
	 * 根据条件查询统计
	 * @param map
	 * @return int
	 */
	@Override
	public int findEmployeeMoneyChangeTypeDataCount(Map<String, Object> map) {
		return getSqlSession().selectOne("EmployeeMoneyChangeType.selectCount", map);
	}
	
	/**
	 * 根据编码删除
	 * @param moneyChangeTypeCode
	 */
	@Override
	public void deleteEmployeeMoneyChangeType(String moneyChangeTypeCode) {
		getSqlSession().delete("EmployeeMoneyChangeType.delete", moneyChangeTypeCode);
	}
	
	/**
	 * 根据编码批量删除
	 * @param array
	 */
	@Override
	public void deleteSelectEmployeeMoneyChangeType(String[] array) {
		getSqlSession().delete("EmployeeMoneyChangeType.batchDelete", array);
	}
	
	/**
	 * 保存资金类型
	 * @param employeeMoneyChangeType
	 */
	@Override
	public void saveEmployeeMoneyChangeType(EmployeeMoneyChangeType employeeMoneyChangeType) {
		getSqlSession().insert("EmployeeMoneyChangeType.save", employeeMoneyChangeType);
	}
	
	/**
	 * 修改资金类型
	 * @param employeeMoneyChangeType
	 */
	@Override
	public void updateEmployeeMoneyChangeType(EmployeeMoneyChangeType employeeMoneyChangeType) {
		getSqlSession().update("EmployeeMoneyChangeType.update", employeeMoneyChangeType);
	}
	/**
	 * 查询相应类别的类型
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<EmployeeMoneyChangeType> findEmployeeMoneyChangeType(Map<String, Object> map) {
		return getSqlSession().selectList("EmployeeMoneyChangeType.selectAll",map);
	}

}
