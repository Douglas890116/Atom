package com.maven.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.entity.EnterpriseGame;
import com.maven.entity.GameApiInput;

@Service
public interface GameApiInputService{
	
	/**
	 * 获取所有品牌的游戏证书信息
	 * @return
	 */
	@DataSource("slave")
	public List<GameApiInput> getAllGames() throws Exception;
	
	/**
	 * 通过品牌编码获取证书信息
	 * @param brandcode
	 * @return
	 */
	@DataSource("slave")
	public GameApiInput takeGameAPI(String enterprisecode)throws Exception;
	
	/**
	 * 添加接入游戏API证书信息
	 * @param api
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public void addGameInputApi(GameApiInput api,List<EnterpriseGame> games)throws Exception;
	
	/**
	 * 修改接入游戏API证书信息
	 * @param api
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public void updateGameInputApi(GameApiInput api, List<EnterpriseGame> gamesAll, List<EnterpriseGame> thisAddList)throws Exception;
	
	
	

}
