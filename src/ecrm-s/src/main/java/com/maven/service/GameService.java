package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.entity.Game;


@Service
public interface GameService {
	/**
	 * 获取所有接入游戏平台信息
	 * @return
	 */
	@DataSource("slave")
	public List<Game> getAllGame() throws Exception;
	
	/**
	 * 通过游戏编码获取游戏信息
	 * @param gamecode
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public Game getGameUseCode(String gamecode) throws Exception;
	
	/**
	 * 获取品牌开放的游戏
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<Game> takeBrandGames(String brandcode) throws Exception;
	
	
	@DataSource("slave")
	public Map<String, Game> takeBrandGamesMap(String brandcode) throws Exception ;
	
	/**
	 * 获取品牌开放的游戏
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<Game> takeBrandGames(String brandcode,int flag) throws Exception;
	
	
	@DataSource("slave")
	public Map<String, Game> takeBrandGamesMap(String brandcode,int flag) throws Exception ;
	
	/**
	 * 获取企业下的API游戏
	 * @param enterprisecode
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<Game> takeEnterpriseGames(String enterprisecode) throws Exception;
	
	/**
	 * 查询所有游戏
	 * @return
	 */
	@DataSource("slave")
	public List<Game> findGameData(Map<String,Object> map) throws Exception;
	
	/**
	 * 统计所有游戏
	 * @return
	 */
	@DataSource("slave")
	public int findGameDataCount(Map<String,Object> map) throws Exception;
	
	/**
	 * 根据游戏类型编码查询
	 * @param gamecode
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public Game getGame(String gamecode) throws Exception;
	
	/**
	 * 根据游戏编码删除游戏类型
	 * @param string
	 */
	@DataSource("master")
	public void deleteGameType(String gamecode) throws Exception;
	
	/**
	 * 根据游戏编码批量删除游戏类型
	 * @param string
	 */
	@DataSource("master")
	public void deleteSelectGameType(String[] array)throws Exception;
	
	/**
	 * 保存游戏类型
	 * @param game
	 */
	@DataSource("master")
	public void saveGameType(Game game)throws Exception;
	
	/**
	 * 更新游戏类型
	 * @param game
	 * @throws Exception
	 */
	@DataSource("master")
	public void updateGameType(Game game)throws Exception;
}
