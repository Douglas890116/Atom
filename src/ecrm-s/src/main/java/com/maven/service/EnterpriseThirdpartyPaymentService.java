package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.EnterpriseThirdpartyPayment;
import com.maven.entity.EnterpriseThirdpartyPaymentAgument;

@Service
public interface EnterpriseThirdpartyPaymentService extends BaseServcie<EnterpriseThirdpartyPayment>{
	
	/**
	 * 根据条件查询所有
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	List<EnterpriseThirdpartyPayment> findAll(Map<String, Object> map) throws Exception;
	
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
	 * @param __ETPayment
	 * @throws Exception
	 */
	@DataSource("master")
	void tc_save(EnterpriseThirdpartyPayment __ETPayment,List<EnterpriseThirdpartyPaymentAgument> __ETPaymentAguments)throws Exception;
	
	/**
	 * 设置公司默认出款卡
	 * @param enterpriseThirdpartyPayment
	 */
	@DataSource("master")
	void tc_update(EnterpriseThirdpartyPayment enterpriseThirdpartyPayment)throws Exception;
	
	/**
	 * 修改
	 * @param enterpriseThirdpartyPayment
	 */
	@DataSource("master")
	void update(EnterpriseThirdpartyPayment enterpriseThirdpartyPayment)throws Exception;
	
	/**
	 * 获取企业可用的第三方支付
	 * @param enterprisecode
	 * @return
	 */
	@DataSource("slave")
	public List<EnterpriseThirdpartyPayment> takeEnterpriseThirdpartypayment(String enterprisecode)throws Exception ;
	

	/**
	 * 更新企业第三方支付当前余额
	 * @param ei
	 */
	@DataSource("master")
	public void updateCurrentBalance(EnterpriseThirdpartyPayment etp)throws Exception ;
}
