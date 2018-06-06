package com.maven.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.Bank;

@Service
public interface BankService extends BaseServcie<Bank>{
	/**
	 * 查询银行资料
	 * @return List<BankInfo>
	 */
	@DataSource("slave")
	List<Bank> getAllBankInfo();
	/**
	 * 根据银行编码查询单个银行信息
	 * @return Bank
	 */
	@DataSource("slave")
	Bank getBankInfo(String bankcode);

}
