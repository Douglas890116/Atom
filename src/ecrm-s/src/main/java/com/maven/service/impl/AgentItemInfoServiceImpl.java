package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.AgentItemInfoDao;
import com.maven.entity.AgentItemInfo;
import com.maven.service.AgentItemInfoService;

@Service
public class AgentItemInfoServiceImpl extends BaseServiceImpl<AgentItemInfo> implements AgentItemInfoService {

	@Autowired
	private AgentItemInfoDao agentItemInfoDao;

	@Override
	public BaseDao<AgentItemInfo> baseDao() {
		return agentItemInfoDao;
	}

	@Override
	public Class<AgentItemInfo> getClazz() {
		return AgentItemInfo.class;
	}

	@Override
	public List<AgentItemInfo> selectAgentItemInfo(Map<String, Object> parameter) throws Exception {
		return agentItemInfoDao.selectAgentItemInfo(parameter);
	}

	@Override
	public int selectAgentItemInfoCount(Map<String, Object> parameter) throws Exception {
		return agentItemInfoDao.selectAgentItemInfoCount(parameter);
	}

	@Override
	public void addAgentItemInfo(AgentItemInfo agentItemInfo) throws Exception {
		agentItemInfoDao.addAgentItemInfo(agentItemInfo);
	}

	@Override
	public void updateAgentItemInfo(AgentItemInfo agentItemInfo) throws Exception {
		agentItemInfoDao.updateAgentItemInfo(agentItemInfo);
	}

	@Override
	public void deleteAgentItemInfos(List<String> list) throws Exception {
		agentItemInfoDao.deleteAgentItemInfos(list);
	}

	@Override
	public List<String> selectBrandCode(String enterprisecode) {
		return agentItemInfoDao.selectBrandCode(enterprisecode);
	}

	@Override
	public void addDefaultAgentItemInfo(List<Map<String, Object>> list) throws Exception {
		for (Map<String, Object> map : list) {
			agentItemInfoDao.addDefaultAgentItemInfo(map);
		}
	}

}