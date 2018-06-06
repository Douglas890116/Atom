package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.BankCardsBlacklistDao;
import com.maven.entity.BankCardsBlacklist;
import com.maven.service.BankCardsBlacklistService;
@Service
public class BankCardsBlacklistServiceImpl extends BaseServiceImpl<BankCardsBlacklist> implements BankCardsBlacklistService {
	@Autowired
	private BankCardsBlacklistDao bankCardsBlacklistDao;
	
	@Override
	public int add(BankCardsBlacklist obj) throws Exception {
		return bankCardsBlacklistDao.add(obj);
	}

	@Override
	public int delete(Integer lsh) throws Exception {
		return bankCardsBlacklistDao.delete(lsh);
	}

	@Override
	public int update(BankCardsBlacklist obj) throws Exception {
		return bankCardsBlacklistDao.update(obj);
	}

	@Override
	public BankCardsBlacklist selectByPrimaryKey(Integer lsh) throws Exception {
		return bankCardsBlacklistDao.selectByPrimaryKey(lsh);
	}

	@Override
	public List<BankCardsBlacklist> selectAll(Map<String, Object> params) throws Exception {
		return bankCardsBlacklistDao.selectAll(params);
	}

	@Override
	public int selectAllCount(Map<String, Object> params) throws Exception {
		return bankCardsBlacklistDao.selectAllCount(params);
	}

	@Override
	public BaseDao<BankCardsBlacklist> baseDao() {
		return bankCardsBlacklistDao;
	}

	@Override
	public Class<BankCardsBlacklist> getClazz() {
		return BankCardsBlacklist.class;
	}
}