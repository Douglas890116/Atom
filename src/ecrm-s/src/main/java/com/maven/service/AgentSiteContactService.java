package com.maven.service;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.AgentSiteContact;

@Service
public interface AgentSiteContactService extends BaseServcie<AgentSiteContact>{
	
	/**
	 * 根据站点编码获取站点联系方式
	 * @param domaincode
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	AgentSiteContact selectByDomaincode(Integer domaincode)throws Exception;
	
	/**
	 * 保存站点联系方式
	 * @param contact
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	int saveContact(AgentSiteContact contact)throws Exception;
	
	/**
	 * 编辑站点联系方式
	 * @param contact
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	int updateContact(AgentSiteContact contact)throws Exception;

}
