package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.AgentItemInfoDao;
import com.maven.entity.AgentItemInfo;

@Repository
public class AgentItemInfoDaoImpl extends BaseDaoImpl<AgentItemInfo> implements AgentItemInfoDao{

	@Override
	public List<AgentItemInfo> selectAgentItemInfo(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectList("AgentItemInfo.selectAll", parameter);
	}

	@Override
	public int selectAgentItemInfoCount(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("AgentItemInfo.selectAllCount", parameter);
	}

	@Override
	public void addAgentItemInfo(AgentItemInfo agentItemInfo) throws Exception {
		getSqlSession().insert("AgentItemInfo.insertSelective", agentItemInfo);
	}

	@Override
	public void updateAgentItemInfo(AgentItemInfo agentItemInfo) throws Exception {
		getSqlSession().insert("AgentItemInfo.updateByPrimaryKeySelective", agentItemInfo);
	}

	@Override
	public void deleteAgentItemInfos(List<String> list) throws Exception {
		getSqlSession().update("AgentItemInfo.deleteByPrimaryKey", list);
	}

	@Override
	public List<String> selectBrandCode(String enterprisecode) {
		return getSqlSession().selectList("AgentItemInfo.selectBrandCode", enterprisecode);
	}

	@Override
	public void addDefaultAgentItemInfo(Map<String, Object> parameter) throws Exception {
		getSqlSession().insert("AgentItemInfo.addDefaultAgentItemInfo", parameter);
	}

}