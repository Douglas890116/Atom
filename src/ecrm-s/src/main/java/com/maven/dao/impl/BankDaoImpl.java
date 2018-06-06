package com.maven.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.BankDao;
import com.maven.entity.Bank;
@Repository
public class BankDaoImpl extends BaseDaoImpl<Bank> implements BankDao {
	/**
	 * 查询所有银行资料
	 * @return List<BankInfo>
	 */
	public List<Bank> getAllBankInfo() {
		return getSqlSession().selectList("Bank.select");
	}
	/**
	 * 根据银行编码查询单个银行信息
	 * @return Bank
	 */
	@Override
	public Bank getBankInfo(String bankcode) {
		return getSqlSession().selectOne("Bank.bankCodeQuyer",bankcode);
	}

	
}
