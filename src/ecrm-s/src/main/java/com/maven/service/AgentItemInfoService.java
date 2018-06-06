package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.AgentItemInfo;

@Service
public interface AgentItemInfoService extends BaseServcie<AgentItemInfo> {
	
	/**
	 * 查看栏目列表信息(根据企业和品牌编码)
	 * 
	 * @param agentItemInfo
	 * @throws Exception
	 */
	@DataSource("slave")
	List<AgentItemInfo> selectAgentItemInfo(Map<String, Object> parameter) throws Exception;

	/**
	 * 查看栏目的个数
	 * 
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	int selectAgentItemInfoCount(Map<String, Object> parameter) throws Exception;

	/**
	 * 新增一个栏目
	 * 
	 * @param agentItemInfo
	 * @throws Exception
	 */
	@DataSource("master")
	void addAgentItemInfo(AgentItemInfo agentItemInfo) throws Exception;

	/**
	 * 修改一个栏目
	 * 
	 * @param agentItemInfo
	 * @throws Exception
	 */
	@DataSource("master")
	void updateAgentItemInfo(AgentItemInfo agentItemInfo) throws Exception;

	/**
	 * 删除栏目
	 * 
	 * @param agentItemInfo
	 * @throws Exception
	 */
	@DataSource("master")
	void deleteAgentItemInfos(List<String> list) throws Exception;

	/**
	 * 根据企业查看brandcode
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	List<String> selectBrandCode(String enterprisecode) throws Exception;
	
	@DataSource("master")
	void addDefaultAgentItemInfo(List<Map<String, Object>> list) throws Exception;
}