package com.maven.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.EmployeeMoneyChangeType;
import com.maven.entity.EnterpriseEmployeeCapitalAccount;
/**
 * 资金账户Service
 * @param map
 */
@Service
public interface EnterpriseEmployeeCapitalAccountService extends BaseServcie<EnterpriseEmployeeCapitalAccount>{
	/**
	 * 更新账户资金信息,写入帐变记录
	 * @param map
	 */
	@DataSource("master")
	public int tc_updateCapitalAccount(String ordernumber,String employeecode, BigDecimal money,EmployeeMoneyChangeType moneychangetype,String moneychangedesc)throws Exception;
	
	/**
	 * 更新账户资金,写入帐变记录（只针对冲正冲负的业务）
	 * @param employeecode 用户编码
	 * @param changeinout  进账或者出账
	 * @param money 正存负取
	 * @param moneychangetype 帐变详细类型编码
	 * @throws Exception
	 */
	@DataSource("master")
	public int tc_updateCapitalAccount(String ordernumber,String employeecode, BigDecimal money,EmployeeMoneyChangeType moneychangetype,String moneychangedesc,String moneyaddtype) throws Exception;
	
	/**
	 * 创建员工的资金帐户
	 * @param map
	 */
	@DataSource("master")
	public void tc_saveEmployeeCapitalAccount(EnterpriseEmployeeCapitalAccount object)throws Exception;
	
	/**
	 * 获取用户资金账户
	 * @param object 必选参数  "employeecode"
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public EnterpriseEmployeeCapitalAccount takeCurrencyAccount(String employee) throws Exception;
	
	/**
	 * 客户输赢分析查询
	 * @param paramObj
	 * @return List<EnterpriseEmployeeCapitalAccount>
	 */
	@DataSource("slave")
	List<EnterpriseEmployeeCapitalAccount> queryUserLoseWinAnalysis(Map<String, Object> paramObj) throws Exception;

	/**
	 * 客户输赢分析数量统计
	 * @param paramObj
	 * @return List<EnterpriseEmployeeCapitalAccount>
	 */
	@DataSource("slave")
	Map<String, Object> queryCountUserLoseWinAnalysis(Map<String, Object> paramObj) throws Exception;
	
}
