package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.PaymentTypeDao;
import com.maven.entity.PaymentType;

@Repository
public class PaymentTypeDaoImpl extends BaseDaoImpl<PaymentType> implements PaymentTypeDao{
	
	/**
	 * 根据支付编码查询
	 * @param string
	 * @return
	 */
	@Override
	public PaymentType getPaymentType(String paymenttypecode) {
		return getSqlSession().selectOne("PaymentType.selectByPrimaryKey", paymenttypecode);
	}
	
	/**
	 * 根据添加查询支付类型
	 * @param map
	 * @return
	 */
	@Override
	public List<PaymentType> findPaymentTypeData(Map<String, Object> map) {
		return getSqlSession().selectList("PaymentType.select", map);
	}
	
	/**
	 * 根据添加统计支付类型
	 * @param map
	 * @return
	 */
	@Override
	public int findPaymentTypeDataCount(Map<String, Object> map) {
		return getSqlSession().selectOne("PaymentType.selectCount", map);
	}
	
	/**
	 * 删除支付类型
	 * @param string
	 */
	@Override
	public void deletePaymentType(String paymenttypecode) {
		getSqlSession().delete("PaymentType.delete", paymenttypecode);
	}
	
	/**
	 * 批量删除
	 * @param array
	 * @throws Exception
	 */
	@Override
	public void deleteSelectPaymentType(String[] array) {
		getSqlSession().delete("PaymentType.selectDelete", array);
	}
	
	/**
	 * 保存支付类型
	 * @param paymentType
	 */
	@Override
	public void savePaymentType(PaymentType paymentType) {
		getSqlSession().insert("PaymentType.Add", paymentType);
	}
	
	/**
	 * 修改支付类型
	 * @param paymentType
	 * @throws Exception
	 */
	@Override
	public void updatePaymentType(PaymentType paymentType) {
		getSqlSession().update("PaymentType.update", paymentType);
	}

}
