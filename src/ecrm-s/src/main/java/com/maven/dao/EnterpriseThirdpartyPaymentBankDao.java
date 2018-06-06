package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.entity.EnterpriseThirdpartyPayment;
import com.maven.entity.ThirdpartyPaymentBank;
@Repository
public interface EnterpriseThirdpartyPaymentBankDao {
	
	/**
	 * 根据条件查询所有
	 * @param map
	 * @return
	 */
	List<ThirdpartyPaymentBank> findAll(Map<String, Object> map);
	
	/**
	 * 根据条件查询统计所有
	 * @param map
	 * @return
	 */
	int findCountAll(Map<String, Object> map);
	
	/**
	 * 单条记录删除
	 * @param md5value
	 */
	void delete(String enterpriseThirdpartyCode);
	
	/**
	 * 批量删除
	 * @param array
	 */
	void deleteSelect(String[] array);
	
	/**
	 * 启用与禁用方法
	 * @param request
	 * @return
	 */
	void enableDisable(EnterpriseThirdpartyPayment enterpriseThirdpartyPayment);
	
	/**
	 * 保存
	 * @param EnterpriseThirdpartyPayment
	 * @return
	 */
	void save(ThirdpartyPaymentBank enterpriseThirdpartyPaymentBank);
	
	/**
	 * 设置公司默认出款卡
	 * @param enterpriseThirdpartyPayment
	 */
	void update(ThirdpartyPaymentBank enterpriseThirdpartyPayment);
	
	/**
	 *  解密之后的值
	 * @param enterpriseThirdpartyPaymentBank
	 * @throws Exception
	 */
	ThirdpartyPaymentBank findThirdpartyPaymentBank(String paymentbankCode)throws Exception;
	
	
	
}
