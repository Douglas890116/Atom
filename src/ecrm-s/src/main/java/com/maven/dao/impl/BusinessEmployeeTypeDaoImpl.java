package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.BusinessEmployeeTypeDao;
import com.maven.entity.EnterpriseEmployeeType;
@Repository
public class BusinessEmployeeTypeDaoImpl extends BaseDaoImpl<EnterpriseEmployeeType> implements BusinessEmployeeTypeDao {
	/**
	 * 查询所有的用户类型
	 * @return
	 */
	public List<EnterpriseEmployeeType> getAllEmployeeType() {
		return getSqlSession().selectList("EnterpriseEmployeeType.select");
	}
	/**
	 * 根据条件查询
	 */
	@Override
	public List<EnterpriseEmployeeType> queryEmployeeType(Map<String, Object> map) {
		return getSqlSession().selectList("EnterpriseEmployeeType.query", map);
	}
	/**
	 * 根据条件统计
	 */
	@Override
	public int queryEmployeeTypeCount(Map<String, Object> map) {
		return getSqlSession().selectOne("EnterpriseEmployeeType.queryCount", map);
	}
	/**
	 * 保存用户类型
	 * @param map
	 */
	@Override
	public void saveEmployeeType(Map<String, Object> map) {
		getSqlSession().insert("EnterpriseEmployeeType.save", map);
	}
	/**
	 * 删除用户类型
	 * @param string
	 */
	@Override
	public void deleteEmployeeType(String employeetypecode) {
		getSqlSession().delete("EnterpriseEmployeeType.delete", employeetypecode);
	}
	/**
	 * 修改用户类型
	 * @param parameter
	 * @throws Exception
	 */
	@Override
	public void updateEmployeeType(Map<String, Object> parameter) {
		getSqlSession().update("EnterpriseEmployeeType.update", parameter);
	}
	/**
	 * 批量删除用户类型
	 * @param string
	 * @throws Exception
	 */
	@Override
	public void deleteSelect(String[] array) {
		getSqlSession().update("EnterpriseEmployeeType.deleteSelect", array);
	}

}
