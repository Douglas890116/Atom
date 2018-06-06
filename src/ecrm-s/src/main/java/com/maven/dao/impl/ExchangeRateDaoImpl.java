package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ExchangeRateDao;
import com.maven.entity.ExchangeRate;

@Repository
public class ExchangeRateDaoImpl extends BaseDaoImpl<ExchangeRate> implements ExchangeRateDao {

	@Override
	public List<ExchangeRate> getAllExchangeRate() {
		return getSqlSession().selectList("ExchangeRate.getAll");
	}

	@Override
	public ExchangeRate getExchangeRateByID(Integer id) {
		return getSqlSession().selectOne("ExchangeRate.getExchangeRateById", id);
	}

	@Override
	public ExchangeRate getExchangeRateByCurrencyName(String currencyName) {
		return getSqlSession().selectOne("ExchangeRate.getExchangeRateByCurrencyName", currencyName);
	}

	@Override
	public ExchangeRate getExchangeRateByCurrencyCode(String currencyCode) {
		return getSqlSession().selectOne("ExchangeRate.getExchangeRateByCurrencyCode", currencyCode);
	}
	
	@Override
	public List<ExchangeRate> getExchangeRateByCondition(Map<String,Object> condition) {
		return getSqlSession().selectList("ExchangeRate.getExchangeRateByCondition", condition);
	}
	
	@Override
	public int getQueryCount(Map<String,Object> condition) {
		return getSqlSession().selectOne("ExchangeRate.count", condition);
	}
	
	@Override
	public int saveExchangeRate(ExchangeRate exchangeRate) {
		return getSqlSession().insert("ExchangeRate.save", exchangeRate);
	}

	@Override
	public int updateExchangeRate(ExchangeRate exchangeRate) {
		return getSqlSession().update("ExchangeRate.update", exchangeRate);
	}

	@Override
	public int deleteExchangeRate(Integer id) {
		return getSqlSession().delete("ExchangeRate.delete", id);
	}
	@Override
	public int batchDelete(String[] ids) {
		return getSqlSession().delete("ExchangeRate.batchDelete", ids);
	}
}