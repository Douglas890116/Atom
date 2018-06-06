package com.maven.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.Bank;
@Repository
public interface BankDao extends BaseDao<Bank>{
	/**
	 * 查询所有银行资料
	 * @return List<BankInfo>
	 */
	List<Bank> getAllBankInfo();
	/**
	 * 根据银行编码查询单个银行信息
	 * @return Bank
	 */
	Bank getBankInfo(String bankcode);
	
}
