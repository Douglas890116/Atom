package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.EnterpriseThirdpartyPaymentBankDao;
import com.maven.entity.EnterpriseThirdpartyPayment;
import com.maven.entity.ThirdpartyPaymentBank;
@Repository
public class EnterpriseThirdpartyPaymentBankDaoImpl extends BaseDaoImpl<ThirdpartyPaymentBank> implements EnterpriseThirdpartyPaymentBankDao{
	
	/**
	 * 根据条件查询所有
	 * @param map
	 * @return
	 */
	@Override
	public List<ThirdpartyPaymentBank> findAll(Map<String, Object> map) {
		return getSqlSession().selectList("ThirdpartyPaymentBank.findAll", map);
	}
	
	/**
	 * 根据条件查询统计所有
	 * @param map
	 * @return
	 */
	@Override
	public int findCountAll(Map<String, Object> map) {
		return getSqlSession().selectOne("ThirdpartyPaymentBank.findCountAll",map);
	}
	
	/**
	 * 单条记录删除
	 * @param md5value
	 */
	@Override
	public void delete(String enterpriseThirdpartyCode) {
		getSqlSession().update("ThirdpartyPaymentBank.deleteByPrimaryKey",enterpriseThirdpartyCode);
	}
	
	/**
	 * 批量删除
	 * @param array
	 */
	@Override
	public void deleteSelect(String[] array) {
		getSqlSession().update("ThirdpartyPaymentBank.deleteSelect",array);
	}
	
	/**
	 * 启用与禁用方法
	 * @param request
	 * @return
	 */
	@Override
	public void enableDisable(EnterpriseThirdpartyPayment enterpriseThirdpartyPayment) {
		getSqlSession().update("ThirdpartyPaymentBank.update", enterpriseThirdpartyPayment);
	}
	
	/**
	 * 保存
	 */
	@Override
	public void save(ThirdpartyPaymentBank enterpriseThirdpartyPaymentBank) {
		 getSqlSession().insert("ThirdpartyPaymentBank.save", enterpriseThirdpartyPaymentBank);
	}
	
	/**
	 * 设置公司默认出款卡
	 * @param enterpriseThirdpartyPayment
	 */
	@Override
	public void update(ThirdpartyPaymentBank enterpriseThirdpartyPayment) {
		 getSqlSession().update("ThirdpartyPaymentBank.updatesave", enterpriseThirdpartyPayment);
	}
				
	@Override
	public ThirdpartyPaymentBank findThirdpartyPaymentBank(String paymentbankCode) {
		return getSqlSession().selectOne("ThirdpartyPaymentBank.getPaymentBankCode", paymentbankCode);
	}

}
