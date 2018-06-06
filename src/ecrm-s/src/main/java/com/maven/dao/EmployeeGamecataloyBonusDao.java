package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.EmployeeGamecataloyBonus;

@Repository
public interface EmployeeGamecataloyBonusDao extends BaseDao<EmployeeGamecataloyBonus>{
	
	/**
	 * 设置企业号游戏返点
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int settingEnterpriseBonus(EmployeeGamecataloyBonus object) throws Exception;
	/**
	 * 查询用户所有游戏的洗码
	 * @param employeecode
	 * @return
	 */
	public List<EmployeeGamecataloyBonus> findGameBonus(String employeecode)throws Exception;
	
	/**
	 * 修改用户返点
	 * @param bonus
	 * @return
	 * @throws Exception
	 */
	public int updateBounus(Map<String,Object> object)throws Exception;
	
	/**
	 * 批量修改(多个)会员返点
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int updateMultiMemberBonus(Map<String,Object> object) throws Exception ;

	/**
	 * 根据会员编码list获取所有游戏的洗码
	 * @param employeecodes
	 * @return
	 * @throws Exception
	 */
	public List<EmployeeGamecataloyBonus> findGameBonus(List<String> employeecodes) throws Exception;
	
}
