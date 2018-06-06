package com.maven.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.BankDao;
import com.maven.entity.Bank;
import com.maven.service.BankService;
@Service
public class BankServiceImpl extends BaseServiceImpl<Bank> implements BankService {
	@Autowired
	private BankDao bankDao;
	
	@Override
	public BaseDao<Bank> baseDao() {
		return bankDao;
	}

	@Override
	public Class<Bank> getClazz() {
		return Bank.class;
	}
	/**
	 * 查询银行资料
	 * @return List<BankInfo>
	 */
	public List<Bank> getAllBankInfo() {
		return bankDao.getAllBankInfo();
	}
	/**
	 * 根据银行编码查询单个银行信息
	 * @return Bank
	 */
	@Override
	public Bank getBankInfo(String bankcode) {
		return bankDao.getBankInfo(bankcode);
	}
	
}
