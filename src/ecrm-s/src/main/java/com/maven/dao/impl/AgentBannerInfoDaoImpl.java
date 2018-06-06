package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.AgentBannerInfoDao;
import com.maven.entity.AgentBannerInfo;

@Repository
public class AgentBannerInfoDaoImpl extends BaseDaoImpl<AgentBannerInfo> implements AgentBannerInfoDao {

	@Override
	public List<AgentBannerInfo> selectAgentBannerInfo(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectList("AgentBannerInfo.selectAll", parameter);
	}

	@Override
	public int selectBannerCount(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("AgentBannerInfo.selectAllCount", parameter);
	}

	@Override
	public void addAgentBannerInfo(AgentBannerInfo agentBannerInfo) throws Exception {
		getSqlSession().insert("AgentBannerInfo.insertSelective", agentBannerInfo);
	}

	@Override
	public void updateAgentBannerInfo(AgentBannerInfo agentBannerInfo) throws Exception {
		getSqlSession().insert("AgentBannerInfo.updateByPrimaryKeySelective", agentBannerInfo);
	}

	@Override
	public void deleteAgentBannerInfos(List<String> list) throws Exception {
		getSqlSession().update("AgentBannerInfo.deleteByPrimaryKey", list);
	}

	@Override
	public void addDefaultAgentBannerInfo(Map<String, Object> parameter) throws Exception {
		getSqlSession().insert("AgentBannerInfo.addDefaultAgentBannerInfo", parameter);
	}

	@Override
	public List<String> selectBrandCode(String enterprisecode) throws Exception {
		return getSqlSession().selectList("AgentBannerInfo.selectBrandCode", enterprisecode);
	}

}