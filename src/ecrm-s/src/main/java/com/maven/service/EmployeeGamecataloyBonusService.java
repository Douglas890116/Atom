package com.maven.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.entity.EmployeeGamecataloyBonus;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseGame;

@Service
public interface EmployeeGamecataloyBonusService {
	
	/**
	 * 获取用户的接入平台游戏返点
	 * @param employeecode
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<EmployeeGamecataloyBonus> takeEmployeeGameCategoryBonus(String employeecode) throws Exception; 
	
	/**
	 * 查找并更新用户的洗码设置
	 * @param employeecode
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public void updateEmployeeGameCategoryBonus(String Enterprisecode, List<EnterpriseGame> games) throws Exception; 
	
	/**
	 * 获取用户的接入平台游戏返点
	 * @param employeecode
	 * @return Map<String,EmployeeGamecataloyBonus> Key = gametype_categorytype
	 * @throws Exception
	 */
	@DataSource("slave")
	public Map<String,BigDecimal> takeEmployeeGameCategoryBonusMap(String employeecode) throws Exception;
	
	/**
	 * 新增用户游戏洗码
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int tc_installEmployeeCategyBonus(List<EmployeeGamecataloyBonus> bonus) throws Exception;
	
	/**
	 * 批量修改(多个)会员洗码
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int tc_settingMultiMemberBonus(Map<String,List<EmployeeGamecataloyBonus>> object) throws Exception ;

	/**
	 * 修改用户结算配置信息
	 * @param ee 必须参数 "employeecode","dividend","share"  
	 * @param userbonus 必须参数 "employeecode","gametype","categorytype","bonus" 
	 * userBouns.get("__insert");
	 * userBouns.get("__upateUp");
	 * userBouns.get("__upateDown");
	 */
	@DataSource("master")
	public void tc_settingSettleConfig(EnterpriseEmployee ee,Map<String,List<EmployeeGamecataloyBonus>> userbonus)throws Exception;
	
	@DataSource("master")
	public int updateMultiMemberBonus(List<EmployeeGamecataloyBonus> userbonus) throws Exception;
	
	/**
	 * 设置企业号返点
	 * @param employeecode
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int tc_settingEnterpriseBonus(EmployeeGamecataloyBonus object) throws Exception;
	/**
	 * 查询用户所有游戏的洗码
	 * @param employeecode
	 * @return
	 */
	@DataSource("slave")
	public List<EmployeeGamecataloyBonus> findGameBonus(String employeecode)throws Exception;
	
	
	/**
	 * 根据会员编码list获取所有游戏的洗码
	 * @param employeecodes
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public Map<String, Map<String, BigDecimal>> findGameBonus(List<String> employeecodes) throws Exception;
	/**
	 * 根据会员编码list获取所有游戏的洗码，带分隔符
	 * @param employeecodes
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public Map<String, Map<String, BigDecimal>> findGameBonus2(List<String> employeecodes) throws Exception;
	
	
}
