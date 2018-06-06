/**
 * 
 */
package com.maven.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.EnterpriseInformation;

@Service
public interface EnterpriseInformationService extends BaseServcie<EnterpriseInformation>{
	/**
	 * 根据银行卡号查询
	 * @return EnterpriseInformation
	 */
	@DataSource("slave")
	public EnterpriseInformation findEnterpriseInformation(String enterprisepaymentaccount) throws Exception;
	
	/**
	 * 添加银行卡
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int add(EnterpriseInformation obj)throws Exception;

	/**
	 * 修改银行卡
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int update(EnterpriseInformation object) throws Exception ;
	

	/**
	 * 删除银行卡
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int delete(EnterpriseInformation object) throws Exception ;
	
	/**
	 * 批量逻辑删除
	 * @param enterpriseinformationcodes
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int tc_logicDelete(List<String> enterpriseinformationcodes)throws Exception;
	
	/**
	 * 跟新企业银行卡当前余额
	 * @param ei
	 * @throws Exception
	 */
	@DataSource("master")
	public void updateCurrentBalance(EnterpriseInformation ei) throws Exception;
}
