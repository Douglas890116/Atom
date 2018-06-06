package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.BankCardsBlacklistDao;
import com.maven.entity.BankCardsBlacklist;
@Repository
public class BankCardsBlacklistDaoImpl extends BaseDaoImpl<BankCardsBlacklist> implements BankCardsBlacklistDao {
	@Override
	public int add(BankCardsBlacklist obj) throws Exception {
		return getSqlSession().insert("BankCardsBlacklist.add", obj);
	}

	@Override
	public int delete(Integer lsh) throws Exception {
		return getSqlSession().delete("BankCardsBlacklist.delete", lsh);
	}

	@Override
	public int update(BankCardsBlacklist obj) throws Exception {
		return getSqlSession().update("BankCardsBlacklist.update", obj);
	}

	@Override
	public BankCardsBlacklist selectByPrimaryKey(Integer lsh) throws Exception {
		return getSqlSession().selectOne("BankCardsBlacklist.selectByPrimaryKey", lsh);
	}

	@Override
	public List<BankCardsBlacklist> selectAll(Map<String, Object> params) throws Exception {
		return getSqlSession().selectList("BankCardsBlacklist.selectAll", params);
	}

	@Override
	public int selectAllCount(Map<String, Object> params) throws Exception {
		return getSqlSession().selectOne("BankCardsBlacklist.selectAllCount", params);
	}
}