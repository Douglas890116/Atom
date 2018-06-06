package com.maven.service;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.PlatformApiOutput;

@Service
public interface PlatformApiOutputService extends BaseServcie<PlatformApiOutput>{
	
	/**
	 * 获取接口配置通过品牌编码
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public PlatformApiOutput takeConfigUseEnterprisecode(String brandcode)throws Exception;
	
	/**
	 * 获取接口配置通过主键
	 * @param outputapicode
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public PlatformApiOutput takeConfigUseOutputapicode(String outputapicode)throws Exception;
	
	/**
	 * 添加品牌接口配置信息
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int addBrandApiConfig(PlatformApiOutput object)throws Exception;
	
	

}
