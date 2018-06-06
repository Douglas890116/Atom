package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.AgentItemInfo;

@Repository
public interface AgentItemInfoDao extends BaseDao<AgentItemInfo>{
	/**
	 * 查看栏目列表信息(根据企业和品牌编码)
	 * 
	 * @param agentItemInfo
	 * @throws Exception
	 */
	List<AgentItemInfo> selectAgentItemInfo(Map<String, Object> parameter) throws Exception;

	/**
	 * 查看栏目的个数
	 * 
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	int selectAgentItemInfoCount(Map<String, Object> parameter) throws Exception;

	/**
	 * 新增一个栏目
	 * 
	 * @param agentItemInfo
	 * @throws Exception
	 */
	void addAgentItemInfo(AgentItemInfo agentItemInfo) throws Exception;

	/**
	 * 修改一个栏目
	 * 
	 * @param agentItemInfo
	 * @throws Exception
	 */
	void updateAgentItemInfo(AgentItemInfo agentItemInfo) throws Exception;

	/**
	 * 删除栏目
	 * 
	 * @param agentItemInfo
	 * @throws Exception
	 */
	void deleteAgentItemInfos(List<String> list) throws Exception;

	List<String> selectBrandCode(String enterprisecode);
	
	void addDefaultAgentItemInfo(Map<String, Object> parameter) throws Exception;
}