package com.maven.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseEmployee;

@Service
public interface EnterpriseService extends BaseServcie<Enterprise>{
	
	/**
	 * 创建企业
	 * @return
	 */
	@DataSource("master")
	public int tc_CreateEnterprise(Enterprise e , EnterpriseEmployee ee) throws Exception;
	 
	/**
	 * 修改企业
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int update(Enterprise e) throws Exception;
	
	/**
	 * 逻辑删除
	 * @param enterprisecoe
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int tc_logicDelete(List<String> enterprisecoe)throws Exception;
	
	/**
	 * 删除企业时,调用该方法验证企业是否可以删除
	 * @param enterprisecode
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public boolean getValidateDeleteEnterprise(String enterprisecode)throws Exception;
	

}
