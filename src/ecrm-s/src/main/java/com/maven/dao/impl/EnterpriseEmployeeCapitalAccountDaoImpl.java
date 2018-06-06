package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.EnterpriseEmployeeCapitalAccountDao;
import com.maven.entity.EnterpriseEmployeeCapitalAccount;
/**
 * 
 * @author Ethan
 * 用户账户信息
 */
@Repository
public class EnterpriseEmployeeCapitalAccountDaoImpl extends BaseDaoImpl<EnterpriseEmployeeCapitalAccount>
		implements EnterpriseEmployeeCapitalAccountDao {
	
	/**
	 * 更新用户账户资金信息
	 * @param map
	 */
	@Override
	public int updateEmployeeCapitalAccount(EnterpriseEmployeeCapitalAccount object) {
		return getSqlSession().update("EnterpriseEmployeeCapitalAccount.updateEmployeeAccount", object);
	}
	/**
	 * 创建员工的资金帐户
	 * @param object
	 */
	@Override
	public void saveEmployeeCapitalAccount(EnterpriseEmployeeCapitalAccount object) {
		getSqlSession().insert("EnterpriseEmployeeCapitalAccount.save", object);
		
	}
	/**
	 * 客户输赢分析查询
	 * @param paramObj
	 */
	@Override
	public List<EnterpriseEmployeeCapitalAccount> queryUserLoseWinAnalysis(Map<String, Object> paramObj){
		return getSqlSession().selectList("EnterpriseEmployeeCapitalAccount.queryUserLoseWinAnalysis",paramObj);
	}

	/**
	 * 客户输赢分析数量统计
	 * @param paramObj
	 */
	@Override
	public Map<String, Object> countUserLoseWinAnalysis(Map<String, Object> paramObj){
		return getSqlSession().selectOne("EnterpriseEmployeeCapitalAccount.countUserLoseWinAnalysis", paramObj);
	}
}
