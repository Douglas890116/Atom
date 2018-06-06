package com.maven.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.maven.dao.PandectDao;

@Repository
public class PandectDaoImpl extends SqlSessionDaoSupport implements PandectDao {

	@Override
	public BigDecimal usp_takeLoseWin(Map<String, Object> object) {
		return getSqlSession().selectOne("Pandect.usp_takeLoseWin", object);
	}

	@Override
	public List<Map<String, Object>> usp_performanceForBrand(Map<String, Object> object) {
		return getSqlSession().selectList("Pandect.usp_performanceForBrand", object);
	}

	@Override
	public List<Map<String, Object>> usp_useractivity(Map<String, Object> object) {
		return getSqlSession().selectList("Pandect.usp_useractivity", object);
	}

	@Override
	public BigDecimal usp_balance(Map<String, Object> object) {
		return getSqlSession().selectOne("Pandect.usp_balance", object);
	}

	@Override
	public BigDecimal usp_savemoney(Map<String, Object> object) {
		return getSqlSession().selectOne("Pandect.usp_savemoney", object);
	}

	@Override
	public BigDecimal usp_takemoney(Map<String, Object> object) {
		return getSqlSession().selectOne("Pandect.usp_takemoney", object);
	}

	@Override
	public List<Map<String, Object>> usp_performance(Map<String, Object> object) {
		return getSqlSession().selectList("Pandect.usp_performance", object);
	}
	
	@Override
	public Map<String, Object> usp_takemoney_inspect(Map<String, Object> object) {
		return getSqlSession().selectOne("Pandect.usp_takemoney_inspect", object);
	}
	

}
