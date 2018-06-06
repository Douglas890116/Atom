package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.ExchangeRate;

@Service
public interface ExchangeRateService extends BaseServcie<ExchangeRate> {
	/**
	 * 保存新增汇率
	 * @return
	 */
	@DataSource("master")
	public int saveExchangeRate(ExchangeRate exchangeRate);
	/**
	 * 通过ID删除汇率
	 * @param id
	 * @return
	 */
	@DataSource("master")
	public int deleteExchangeRateById(Integer id);
	/**
	 * 通过ID批量删除
	 * @param ids
	 * @return
	 */
	@DataSource("master")
	public int deleteExchangeRatesByIds(String[] ids);
	/**
	 * 根据汇率数据
	 * @param exchangeRate
	 * @return
	 */
	@DataSource("master")
	public int updateExchange(ExchangeRate exchangeRate);
	/**
	 * 获取全部汇率数据
	 * @return
	 */
	@DataSource("slave")
	public List<ExchangeRate> getAllExchangeRates();
	/**
	 * 通过ID获取汇率数据
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public ExchangeRate getExchangeRateById(Integer id);
	/**
	 * 通过货币名称获取汇率数据
	 * @param currencyName
	 * @return
	 */
	@DataSource("slave")
	public ExchangeRate getExchangeRateByCurrencyName(String currencyName);
	/**
	 * 通过货币英文代码获取汇率数据
	 * @param currencyCode
	 * @return
	 */
	@DataSource("slave")
	public ExchangeRate getExchangeRateByCurrencyCode(String currencyCode);
	/**
	 * 模糊查询
	 * @param condition
	 * @return
	 */
	@DataSource("slave")
	public List<ExchangeRate> getExchangeRateByCondition(Map<String,Object> condition);
	/**
	 * 查询数据量
	 * @param condition
	 * @return
	 */
	@DataSource("slave")
	public int getQueryCount(Map<String,Object> condition);
}
