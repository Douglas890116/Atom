package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.cache.SystemCache;
import com.maven.dao.EnterpriseThirdpartyPaymentBankDao;
import com.maven.entity.EnterpriseThirdpartyPayment;
import com.maven.entity.ThirdpartyPaymentBank;
import com.maven.service.EnterpriseThirdpartyPaymentBankService;
@Service
public class EnterpriseThirdpartyPaymentBankServiceImpl implements EnterpriseThirdpartyPaymentBankService {
	
	@Autowired
	private EnterpriseThirdpartyPaymentBankDao enterpriseThirdpartyPaymentBankDao;
	
	/**
	 * 根据条件查询所有
	 * @param map
	 * @return
	 */
	@Override
	public List<ThirdpartyPaymentBank> findAll(Map<String, Object> map) throws Exception {
		return enterpriseThirdpartyPaymentBankDao.findAll(map);
	}
	
	/**
	 * 根据条件查询统计所有
	 * @param map
	 * @return
	 */
	@Override
	public int findCountAll(Map<String, Object> map) throws Exception {
		return enterpriseThirdpartyPaymentBankDao.findCountAll(map);
	}
	
	/**
	 * 单条记录删除
	 * @param md5value
	 */
	@Override
	public void tc_delete(String enterpriseThirdpartyCode) throws Exception {
		enterpriseThirdpartyPaymentBankDao.delete(enterpriseThirdpartyCode);
		SystemCache.getInstance().resetThirdpartyPaymentBanks();
	}

	/**
	 * 批量删除
	 * @param array
	 */
	@Override
	public void tc_deleteSelect(String[] array) throws Exception {
		enterpriseThirdpartyPaymentBankDao.deleteSelect(array);
		SystemCache.getInstance().resetThirdpartyPaymentBanks();
	}

	/**
	 * 启用与禁用方法
	 * @param request
	 * @return
	 */
	@Override
	public void tc_enableDisable(EnterpriseThirdpartyPayment enterpriseThirdpartyPayment) throws Exception {
		enterpriseThirdpartyPaymentBankDao.enableDisable(enterpriseThirdpartyPayment);
		SystemCache.getInstance().resetThirdpartyPaymentBanks();
	}
	
	@Override
	public void tc_save(ThirdpartyPaymentBank enterpriseThirdpartyPaymentBank,HttpServletRequest request) throws Exception {
		enterpriseThirdpartyPaymentBankDao.save(enterpriseThirdpartyPaymentBank);
		SystemCache.getInstance().resetThirdpartyPaymentBanks();
	}
	
	@Override
	public void tc_update(ThirdpartyPaymentBank enterpriseThirdpartyPayment) throws Exception {
		enterpriseThirdpartyPaymentBankDao.update(enterpriseThirdpartyPayment);
		SystemCache.getInstance().resetThirdpartyPaymentBanks();
	}

	@Override
	public ThirdpartyPaymentBank findThirdpartyPaymentBank(
			String paymentbankCode) throws Exception {
		return enterpriseThirdpartyPaymentBankDao.findThirdpartyPaymentBank(paymentbankCode);
	}

}
