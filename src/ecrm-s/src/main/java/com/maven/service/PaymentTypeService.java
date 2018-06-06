package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.entity.PaymentType;

@Service
public interface PaymentTypeService {
	
	/**
	 * 获取所有的支付方式
	 * @return
	 */
	@DataSource("slave")
	public List<PaymentType> getAllPaymentType()throws Exception;
	
	/**
	 * 根据支付编码查询
	 * @param string
	 * @return
	 */
	@DataSource("slave")
	public PaymentType getPaymentType(String string)throws Exception;
	
	/**
	 * 根据添加查询支付类型
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<PaymentType> findPaymentTypeData(Map<String, Object> map)throws Exception;

	/**
	 * 根据添加统计支付类型
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public int findPaymentTypeDataCount(Map<String, Object> map)throws Exception;
	
	/**
	 * 删除支付类型
	 * @param string
	 */
	@DataSource("master")
	public void deletePaymentType(String paymenttypecode)throws Exception;
	
	/**
	 * 批量删除
	 * @param array
	 * @throws Exception
	 */
	@DataSource("master")
	public void deleteSelectPaymentType(String[] array)throws Exception;

	/**
	 * 保存支付类型
	 * @param paymentType
	 */
	@DataSource("master")
	public void savePaymentType(PaymentType paymentType)throws Exception;

	/**
	 * 修改支付类型
	 * @param paymentType
	 * @throws Exception
	 */
	@DataSource("master")
	public void updatePaymentType(PaymentType paymentType)throws Exception;

}
