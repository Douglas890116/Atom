package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.PaymentType;

@Repository
public interface PaymentTypeDao extends BaseDao<PaymentType>{

	PaymentType getPaymentType(String paymenttypecode);

	List<PaymentType> findPaymentTypeData(Map<String, Object> map);

	int findPaymentTypeDataCount(Map<String, Object> map);

	void deletePaymentType(String paymenttypecode);

	void deleteSelectPaymentType(String[] array);

	void savePaymentType(PaymentType paymentType);

	void updatePaymentType(PaymentType paymentType);

}
