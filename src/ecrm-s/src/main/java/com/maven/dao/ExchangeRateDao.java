package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.ExchangeRate;
@Repository
public interface ExchangeRateDao extends BaseDao<ExchangeRate> {
	/**
	 * 查询所有汇率
	 * @return List<ExchangeRate>
	 */
	List<ExchangeRate> getAllExchangeRate();
	/**
	 * 根据货币ID查询该货币对人民币汇率
	 * @param id
	 * @return ExchangeRate
	 */
	ExchangeRate getExchangeRateByID(Integer id);
	/**
	 * 根据货币名称查询该货币对人民币汇率
	 * @param String 货币名称
	 * @return ExchangeRate
	 */
	ExchangeRate getExchangeRateByCurrencyName(String currencyName);
	/**
	 * 根据货币的英文代码查询该货币对人民币汇率
	 * @param CurrencyName
	 * @return ExchangeRate
	 */
	ExchangeRate getExchangeRateByCurrencyCode(String currencyCode);
	/**
	 * 根据条件模糊查询
	 * @param currencyName
	 * @param currencyCode
	 * @return
	 */
	List<ExchangeRate> getExchangeRateByCondition(Map<String,Object> condition);
	/**
	 * 查询数据大小
	 * @param currencyName
	 * @param currencyCode
	 * @return
	 */
	int getQueryCount(Map<String,Object> condition);
	/**
	 * 保存汇率
	 * @param exchangeRate
	 */
	int saveExchangeRate(ExchangeRate exchangeRate);
	/**
	 * 跟新汇率
	 * @param exchangeRate
	 */
	int updateExchangeRate(ExchangeRate exchangeRate);
	/**
	 * 删除汇率
	 * @param id 货币ID
	 */
	int deleteExchangeRate(Integer id);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int batchDelete(String[] ids);
}