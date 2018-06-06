package com.maven.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.AgentBannerInfoDao;
import com.maven.entity.AgentBannerInfo;
import com.maven.service.AgentBannerInfoService;

@Service
public class AgentBannerInfoServiceImpl extends BaseServiceImpl<AgentBannerInfo> implements AgentBannerInfoService {

	@Autowired
	private AgentBannerInfoDao agentBannerInfoDao;

	@Override
	public BaseDao<AgentBannerInfo> baseDao() {
		return agentBannerInfoDao;
	}

	@Override
	public Class<AgentBannerInfo> getClazz() {
		return AgentBannerInfo.class;
	}

	@Override
	public List<AgentBannerInfo> selectAgentBannerInfo(Map<String, Object> parameter) throws Exception {
		return agentBannerInfoDao.selectAgentBannerInfo(parameter);
	}

	@Override
	public int selectBannerCount(Map<String, Object> parameter) throws Exception {
		return agentBannerInfoDao.selectBannerCount(parameter);
	}

	@Override
	public void addAgentBannerInfo(AgentBannerInfo agentBannerInfo) throws Exception {
		agentBannerInfoDao.addAgentBannerInfo(agentBannerInfo);
	}

	@Override
	public void updateAgentBannerInfo(AgentBannerInfo agentBannerInfo) throws Exception {
		agentBannerInfoDao.updateAgentBannerInfo(agentBannerInfo);
	}

	@Override
	public void deleteAgentBannerInfos(List<String> list) throws Exception {
		agentBannerInfoDao.deleteAgentBannerInfos(list);
	}

	@Override
	public List<AgentBannerInfo> selectDefaultAgentBannerInfo() throws Exception {
		return null;
	}

	@Override
	public void addDefaultAgentBannerInfo(List<Map<String, Object>> list) throws Exception {
		for (Map<String, Object> map : list) {
			agentBannerInfoDao.addDefaultAgentBannerInfo(map);
		}
	}

	@Override
	public List<String> selectBrandCode(String enterprisecode) throws Exception {
		return agentBannerInfoDao.selectBrandCode(enterprisecode);
	}

}