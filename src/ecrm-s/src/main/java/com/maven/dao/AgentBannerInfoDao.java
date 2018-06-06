package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.AgentBannerInfo;

@Repository
public interface AgentBannerInfoDao extends BaseDao<AgentBannerInfo> {

	/**
	 * 查看banner列表信息(根据企业和品牌编码)
	 * 
	 * @param agentBannerInfo
	 * @throws Exception
	 */
	List<AgentBannerInfo> selectAgentBannerInfo(Map<String, Object> parameter) throws Exception;

	/**
	 * 查看banner的个数
	 * 
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	int selectBannerCount(Map<String, Object> parameter) throws Exception;

	/**
	 * 新增一个banner图
	 * 
	 * @param agentBannerInfo
	 * @throws Exception
	 */
	void addAgentBannerInfo(AgentBannerInfo agentBannerInfo) throws Exception;

	/**
	 * 修改一个banner图
	 * 
	 * @param agentBannerInfo
	 * @throws Exception
	 */
	void updateAgentBannerInfo(AgentBannerInfo agentBannerInfo) throws Exception;

	/**
	 * 删除banner
	 * 
	 * @param agentBannerInfo
	 * @throws Exception
	 */
	void deleteAgentBannerInfos(List<String> list) throws Exception;

	/**
	 * 增加默认的banner图
	 * 
	 * @throws Exception
	 */
	void addDefaultAgentBannerInfo(Map<String, Object> parameter) throws Exception;

	List<String> selectBrandCode(String enterprisecode) throws Exception;
}