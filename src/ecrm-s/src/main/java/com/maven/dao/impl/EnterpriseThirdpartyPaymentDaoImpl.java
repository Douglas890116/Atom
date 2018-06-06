package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.EnterpriseThirdpartyPaymentDao;
import com.maven.entity.EnterpriseThirdpartyPayment;
@Repository
public class EnterpriseThirdpartyPaymentDaoImpl extends BaseDaoImpl<EnterpriseThirdpartyPayment> 
	implements EnterpriseThirdpartyPaymentDao{
	
	/**
	 * 根据条件查询所有
	 * @param map
	 * @return
	 */
	@Override
	public List<EnterpriseThirdpartyPayment> findAll(Map<String, Object> map) {
		return getSqlSession().selectList("EnterpriseThirdpartyPayment.findAll", map);
	}
	
	/**
	 * 根据条件查询统计所有
	 * @param map
	 * @return
	 */
	@Override
	public int findCountAll(Map<String, Object> map) {
		return getSqlSession().selectOne("EnterpriseThirdpartyPayment.findCountAll",map);
	}
	
	/**
	 * 单条记录删除
	 * @param md5value
	 */
	@Override
	public void delete(String enterpriseThirdpartyCode) {
		getSqlSession().update("EnterpriseThirdpartyPayment.updateOne",enterpriseThirdpartyCode);
	}
	
	/**
	 * 批量删除
	 * @param array
	 */
	@Override
	public void deleteSelect(String[] array) {
		getSqlSession().update("EnterpriseThirdpartyPayment.updateSelect",array);
	}
	
	/**
	 * 启用与禁用方法
	 * @param request
	 * @return
	 */
	@Override
	public void enableDisable(EnterpriseThirdpartyPayment enterpriseThirdpartyPayment) {
		getSqlSession().update("EnterpriseThirdpartyPayment.update", enterpriseThirdpartyPayment);
	}
	
	@Override
	public String takePrimariyKey(){
		return getSqlSession().selectOne("EnterpriseThirdpartyPayment.takePrimariyKey");
	}
	/**
	 * 保存
	 */
	@Override
	public void save(EnterpriseThirdpartyPayment enterpriseThirdpartyPayment) {
		 getSqlSession().insert("EnterpriseThirdpartyPayment.add", enterpriseThirdpartyPayment);
	}
	
	/**
	 * 设置公司默认出款卡
	 * @param enterpriseThirdpartyPayment
	 */
	@Override
	public void update(EnterpriseThirdpartyPayment enterpriseThirdpartyPayment) {
		 getSqlSession().update("EnterpriseThirdpartyPayment.updateDefaultPaymentCard", enterpriseThirdpartyPayment);
	}

	@Override
	public List<EnterpriseThirdpartyPayment> takeEnterpriseThirdpartypayment(String enterprisecode) {
		return getSqlSession().selectList("EnterpriseThirdpartyPayment.takeEnterpriseThirdpartypayment", enterprisecode);
	}

	@Override
	public void updateCurrentBalance(EnterpriseThirdpartyPayment etp) {
		getSqlSession().update("EnterpriseThirdpartyPayment.updateCurrentBalance", etp);
	}

}
