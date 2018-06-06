package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.entity.EnterpriseGame;

@Service
public interface EnterpriseGameService {
	
	/**
	 * 企业API游戏授权
	 * @param enterprisecode
	 * @param enterprisegames
	 * @throws Exception
	 */
	@DataSource("master")
	public void savaEnterpriseGameAccredit(String enterprisecode,List<EnterpriseGame> enterprisegames)throws Exception;
	
	/**
	 *  获取企业的API授权游戏
	 * @param enterprisecode
	 * @return List<EnterpriseGame>
	 * @throws Exception
	 */
	@DataSource("slave")
	public Map<String,EnterpriseGame> takeEnterpriseGame(String enterprisecode)throws Exception;

	/**
	 * 分页查询
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<EnterpriseGame> selectAll(Map<String,Object> params)throws Exception;
	
	/**
	 * 统计总数
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public int selectAllCount(Map<String,Object> object) throws Exception;
}
