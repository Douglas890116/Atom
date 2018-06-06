package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.AgentBannerInfo;

@Service
public interface AgentBannerInfoService extends BaseServcie<AgentBannerInfo> {

	/**
	 * 查看banner列表信息(根据企业和品牌编码)
	 * 
	 * @param agentBannerInfo
	 * @throws Exception
	 */
	@DataSource("slave")
	List<AgentBannerInfo> selectAgentBannerInfo(Map<String, Object> parameter) throws Exception;

	/**
	 * 查看banner的个数
	 * 
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	int selectBannerCount(Map<String, Object> parameter) throws Exception;
	
	/**
	 * 查看默认的banner图
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	List<AgentBannerInfo> selectDefaultAgentBannerInfo() throws Exception;
	
	/**
	 * 根据企业查看brandcode
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	List<String> selectBrandCode(String enterprisecode) throws Exception;

	/**
	 * 新增一个banner图
	 * 
	 * @param agentBannerInfo
	 * @throws Exception
	 */
	@DataSource("master")
	void addAgentBannerInfo(AgentBannerInfo agentBannerInfo) throws Exception;
	
	/**
	 * 新增默认的banner图
	 * 
	 * @param agentBannerInfo
	 * @throws Exception
	 */
	@DataSource("master")
	void addDefaultAgentBannerInfo(List<Map<String, Object>> list) throws Exception;

	/**
	 * 修改一个banner图
	 * 
	 * @param agentBannerInfo
	 * @throws Exception
	 */
	@DataSource("master")
	void updateAgentBannerInfo(AgentBannerInfo agentBannerInfo) throws Exception;

	/**
	 * 删除banner
	 * 
	 * @param agentBannerInfo
	 * @throws Exception
	 */
	@DataSource("master")
	void deleteAgentBannerInfos(List<String> list) throws Exception;
	
}