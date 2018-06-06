package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.Baccarath5ExchangeDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.Baccarath5Exchange;

@Repository
public class Baccarath5ExchangeDaoImpl extends BaseDaoImpl<Baccarath5Exchange> implements Baccarath5ExchangeDao {

	@Override
	public void addBetRecord(Baccarath5Exchange betrecord) throws Exception {
		getSqlSession().insert("Baccarath5Exchange.insertSelective", betrecord);
	}

	@Override
	public List<Baccarath5Exchange> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectList("Baccarath5Exchange.selectBetRecord", parameter);
	}

	@Override
	public int selectBetRecordCount(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("Baccarath5Exchange.selectBetRecordCount", parameter);
	}

	@Override
	public Map<String, Object> selectBetRecordCountMoney(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("Baccarath5Exchange.selectBetRecordCountMoney", parameter);
	}
}
