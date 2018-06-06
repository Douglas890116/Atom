package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.EnterpriseEmployeeCapitalAccount;
/**
 * 更新用户账户资金信息
 * @param map
 */
@Repository
public interface EnterpriseEmployeeCapitalAccountDao extends BaseDao<EnterpriseEmployeeCapitalAccount>{
	/**
	 * 更新用户账户资金信息
	 * @param map
	 */
	int updateEmployeeCapitalAccount(EnterpriseEmployeeCapitalAccount object);
	/**
	 * 创建员工的资金帐户
	 * @param map
	 */
	void saveEmployeeCapitalAccount(EnterpriseEmployeeCapitalAccount object);
	
	/**
	 * 客户输赢分析查询
	 * @param paramObj
	 * @return List<TakeDepositRecord>
	 */
	List<EnterpriseEmployeeCapitalAccount> queryUserLoseWinAnalysis(Map<String, Object> paramObj);

	/**
	 * 客户输赢分析数量统计
	 * @param paramObj
	 * @return List<EnterpriseEmployeeCapitalAccount>
	 */
	Map<String, Object> countUserLoseWinAnalysis(Map<String, Object> paramObj);
}
