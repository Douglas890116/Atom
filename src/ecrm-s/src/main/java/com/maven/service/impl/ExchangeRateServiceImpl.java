package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.ExchangeRateDao;
import com.maven.entity.ExchangeRate;
import com.maven.service.ExchangeRateService;

@Service
public class ExchangeRateServiceImpl extends BaseServiceImpl<ExchangeRate> implements ExchangeRateService {
	
	@Autowired
	private ExchangeRateDao dao;
	
	@Override
	public int saveExchangeRate(ExchangeRate exchangeRate) {
		return dao.saveExchangeRate(exchangeRate);
	}

	@Override
	public int deleteExchangeRateById(Integer id) {
		return dao.deleteExchangeRate(id);
	}
	
	@Override
	public int deleteExchangeRatesByIds(String[] ids) {
		return dao.batchDelete(ids);
	}
	
	@Override
	public int updateExchange(ExchangeRate exchangeRate) {
		return dao.updateExchangeRate(exchangeRate);
	}

	@Override
	public List<ExchangeRate> getAllExchangeRates() {
		return dao.getAllExchangeRate();
	}

	@Override
	public ExchangeRate getExchangeRateById(Integer id) {
		return dao.getExchangeRateByID(id);
	}

	@Override
	public ExchangeRate getExchangeRateByCurrencyName(String currencyName) {
		return dao.getExchangeRateByCurrencyName(currencyName);
	}

	@Override
	public ExchangeRate getExchangeRateByCurrencyCode(String currencyCode) {
		return dao.getExchangeRateByCurrencyCode(currencyCode);
	}

	@Override
	public List<ExchangeRate> getExchangeRateByCondition(Map<String,Object> condition) {
		return dao.getExchangeRateByCondition(condition);
	}
	@Override
	public int getQueryCount(Map<String,Object> condition) {
		return dao.getQueryCount(condition);
	}
	
	@Override
	public BaseDao<ExchangeRate> baseDao() {
		return dao;
	}

	@Override
	public Class<ExchangeRate> getClazz() {
		return ExchangeRate.class;
	}
}
