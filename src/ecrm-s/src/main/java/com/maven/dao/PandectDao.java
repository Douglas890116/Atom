package com.maven.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface PandectDao {
	
	/**
	 * 列表-团队品牌贡献 
	 * @param object
	 * @return
	 */
	List<Map<String, Object>> usp_performanceForBrand(Map<String,Object> object);
	
	/**
	 * 列表-用户活跃度
	 * @param object
	 * @return
	 */
	List<Map<String,Object>> usp_useractivity(Map<String,Object> object);
	
	/**
	 * 总计-团队余额 
	 * @param object
	 * @return
	 */
	BigDecimal usp_balance(Map<String,Object> object);
	
	/**
	 * 总计-团队输赢
	 * @param object
	 * @return
	 */
	BigDecimal usp_takeLoseWin(Map<String,Object> object);
	
	/**
	 * 总计-团队存款
	 * @param object
	 * @return
	 */
	BigDecimal usp_savemoney(Map<String,Object> object);
	
	/**
	 * 总计-团队取款
	 * @param object
	 * @return
	 */
	BigDecimal usp_takemoney(Map<String,Object> object);
	
	
	/**
	 * 列表-团队贡献
	 * @param object
	 * @return
	 */
	List<Map<String,Object>> usp_performance(Map<String,Object> object);
	
	/**
	 * 总计-取款稽查
	 * @param object
	 * @return
	 */
	Map<String, Object> usp_takemoney_inspect(Map<String, Object> object);
	
	
	

}
