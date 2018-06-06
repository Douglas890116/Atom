package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.EnterpriseThirdpartyPaymentAgument;

@Service
public interface EnterpriseThirdpartyPaymentAgumentService extends BaseServcie<EnterpriseThirdpartyPaymentAgument>{
	
	/**
	 * 批量保存
	 * @param list
	 */
	@DataSource("master")
	void tc_save(List<EnterpriseThirdpartyPaymentAgument> list)throws Exception;
	
	/**
	 * 修改 
	 * @param enterpriseThirdpartyPaymentAgument
	 * @throws Exception
	 */
	@DataSource("master")
	void tc_update(EnterpriseThirdpartyPaymentAgument enterpriseThirdpartyPaymentAgument)throws Exception;
	
	
	/**
	 *  保存参数类型修改之后的值
	 * @param enterpriseThirdpartyPaymentAgument
	 * @throws Exception
	 */
	@DataSource("master")
	void updateEnterpriseThirdpartyPaymentAgument( EnterpriseThirdpartyPaymentAgument enterpriseThirdpartyPaymentAgument)throws Exception;
	
	/**
	 * 关联查询所有
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<EnterpriseThirdpartyPaymentAgument> selectUnionAll(Map<String, Object> object) throws Exception;
	/**
	 * 获得第三方支付商户参数
	 * @param enterprisethirdpartycode
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public Map<String, Object> takeEnterprisePayAgument(String enterprisethirdpartycode) throws Exception;
	
	/**
	 * 获取企业默认的第三方支付参数
	 * @param enterprisecode
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public Map<String,Object> takeEDefualtPayAgument(String enterprisecode) throws Exception;
	

}
