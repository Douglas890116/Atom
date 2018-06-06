package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.Baccarath5BalanceDao;
import com.maven.dao.Baccarath5ExchangeDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.Baccarath5Balance;
import com.maven.entity.Baccarath5Exchange;

@Repository
public class Baccarath5BalanceDaoImpl extends BaseDaoImpl<Baccarath5Balance> implements Baccarath5BalanceDao {

	@Override
	public void addBetRecord(Baccarath5Balance betrecord) throws Exception {
		getSqlSession().insert("Baccarath5Balance.insertSelective", betrecord);
	}

	@Override
	public List<Baccarath5Balance> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectList("Baccarath5Balance.selectBetRecord", parameter);
	}

	@Override
	public int selectBetRecordCount(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("Baccarath5Balance.selectBetRecordCount", parameter);
	}

	@Override
	public Map<String, Object> selectBetRecordCountMoney(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("Baccarath5Balance.selectBetRecordCountMoney", parameter);
	}
}
