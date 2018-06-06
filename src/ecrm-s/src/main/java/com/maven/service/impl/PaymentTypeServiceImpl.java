package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.PaymentTypeDao;
import com.maven.entity.PaymentType;
import com.maven.service.PaymentTypeService;

@Service
public class PaymentTypeServiceImpl extends BaseServiceImpl<PaymentType> implements PaymentTypeService{

	@Autowired
	private PaymentTypeDao paymentTypeDao;
	
	@Override
	public BaseDao<PaymentType> baseDao() {
		return paymentTypeDao;
	}

	@Override
	public Class<PaymentType> getClazz() {
		return PaymentType.class;
	}

	/**
	 * 获取所有的支付方式
	 */
	@Override
	public List<PaymentType> getAllPaymentType() throws Exception{
		return super.select(null);
	}
	
	/**
	 * 根据支付编码查询
	 * @param string
	 * @return
	 */
	@Override
	public PaymentType getPaymentType(String paymenttypecode) throws Exception {
		return paymentTypeDao.getPaymentType(paymenttypecode);
	}
	
	/**
	 * 根据添加查询支付类型
	 * @param map
	 * @return
	 */
	@Override
	public List<PaymentType> findPaymentTypeData(Map<String, Object> map) throws Exception {
		return paymentTypeDao.findPaymentTypeData(map);
	}
	
	/**
	 * 根据添加统计支付类型
	 * @param map
	 * @return
	 */
	@Override
	public int findPaymentTypeDataCount(Map<String, Object> map) throws Exception {
		return paymentTypeDao.findPaymentTypeDataCount(map);
	}
	
	/**
	 * 删除支付类型
	 * @param string
	 */
	@Override
	public void deletePaymentType(String paymenttypecode) throws Exception {
		paymentTypeDao.deletePaymentType(paymenttypecode);
	}
	
	/**
	 * 批量删除
	 * @param array
	 * @throws Exception
	 */
	@Override
	public void deleteSelectPaymentType(String[] array) throws Exception {
		paymentTypeDao.deleteSelectPaymentType(array);
	}
	
	/**
	 * 保存支付类型
	 * @param paymentType
	 */
	@Override
	public void savePaymentType(PaymentType paymentType) throws Exception {
		paymentTypeDao.savePaymentType(paymentType);
	}
	
	/**
	 * 修改支付类型
	 * @param paymentType
	 * @throws Exception
	 */
	@Override
	public void updatePaymentType(PaymentType paymentType) throws Exception {
		paymentTypeDao.updatePaymentType(paymentType);
	}

}
