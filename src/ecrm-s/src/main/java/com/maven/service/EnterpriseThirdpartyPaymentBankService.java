package com.maven.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.entity.EnterpriseThirdpartyPayment;
import com.maven.entity.ThirdpartyPaymentBank;

@Service
public interface EnterpriseThirdpartyPaymentBankService {
	
	/**
	 * 根据条件查询所有
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	List<ThirdpartyPaymentBank> findAll(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据条件查询统计所有
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	int findCountAll(Map<String, Object> map)throws Exception;
	
	/**
	 * 单条记录删除
	 * @param md5value
	 */
	@DataSource("master")
	void tc_delete(String enterpriseThirdpartyCode)throws Exception;

	/**
	 * 批量删除
	 * @param array
	 */
	@DataSource("master")
	void tc_deleteSelect(String[] array)throws Exception;
	
	/**
	 * 启用与禁用方法
	 * @param enterpriseThirdpartyPayment
	 * @throws Exception
	 */
	@DataSource("master")
	void tc_enableDisable(EnterpriseThirdpartyPayment enterpriseThirdpartyPayment)throws Exception;
	
	/**
	 * 保存
	 * @param enterpriseThirdpartyPayment
	 * @throws Exception
	 */
	@DataSource("master")
	void tc_save(ThirdpartyPaymentBank enterpriseThirdpartyPayment,HttpServletRequest request)throws Exception;
	
	/**
	 * 设置公司默认出款卡
	 * @param enterpriseThirdpartyPayment
	 */
	@DataSource("master")
	void tc_update(ThirdpartyPaymentBank enterpriseThirdpartyPayment)throws Exception;
	
	
	/**
	 *  解密之后的值
	 * @param enterpriseThirdpartyPaymentBank
	 * @throws Exception
	 */
	@DataSource("master")
	ThirdpartyPaymentBank findThirdpartyPaymentBank(String paymentbankCode)throws Exception;
	

}
