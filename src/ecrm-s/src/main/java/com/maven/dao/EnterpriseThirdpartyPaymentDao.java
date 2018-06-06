package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.EnterpriseThirdpartyPayment;
@Repository
public interface EnterpriseThirdpartyPaymentDao extends BaseDao<EnterpriseThirdpartyPayment>{
	
	/**
	 * 根据条件查询所有
	 * @param map
	 * @return
	 */
	List<EnterpriseThirdpartyPayment> findAll(Map<String, Object> map);
	
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
	 * 获取下一个主键
	 */
	public String takePrimariyKey();
	
	/**
	 * 保存
	 * @param EnterpriseThirdpartyPayment
	 * @return
	 */
	void save(EnterpriseThirdpartyPayment enterpriseThirdpartyPayment);
	
	/**
	 * 获取企业可用的第三方支付
	 * @param enterprisecode
	 * @return
	 */
	public List<EnterpriseThirdpartyPayment> takeEnterpriseThirdpartypayment(String enterprisecode);
	
	/**
	 * 设置公司默认出款卡
	 * @param enterpriseThirdpartyPayment
	 */
	void update(EnterpriseThirdpartyPayment enterpriseThirdpartyPayment);
	
	/**
	 * 更新企业第三方支付当前余额
	 * @param ei
	 */
	public void updateCurrentBalance(EnterpriseThirdpartyPayment etp);
	
}
