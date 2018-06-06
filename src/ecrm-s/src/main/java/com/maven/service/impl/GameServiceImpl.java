package com.maven.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.cache.SystemCache;
import com.maven.dao.GameDao;
import com.maven.entity.EnterpriseGame;
import com.maven.entity.EnterpriseOperatingBrandGame;
import com.maven.entity.Game;
import com.maven.service.EnterpriseGameService;
import com.maven.service.EnterpriseOperatingBrandGameService;
import com.maven.service.GameService;

@Service
public class GameServiceImpl extends BaseServiceImpl<Game> implements GameService{

	@Autowired
	private GameDao GameDao;
	@Autowired
	private EnterpriseOperatingBrandGameService enterpriseOperatingBrandGameService;
	@Autowired
	private EnterpriseGameService enterpriseGameService;
	
	@Override
	public BaseDao<Game> baseDao() {
		return GameDao;
	}

	@Override
	public Class<Game> getClazz() {
		return Game.class;
	}

	@Override
	public List<Game> getAllGame() throws Exception{
		return super.select(null);
	}

	@Override
	public List<Game> takeBrandGames(String brandcode) throws Exception {
		List<EnterpriseOperatingBrandGame> list = enterpriseOperatingBrandGameService.takeBrandGames(new EnterpriseOperatingBrandGame(brandcode));
		List<Game> games = new ArrayList<Game>();
		for (EnterpriseOperatingBrandGame e : list) {
			Game g = SystemCache.getInstance().getGame(e.getGamecode());
			games.add(g.clone());
		}
		return games;
	}
	
	@Override
	public Map<String, Game> takeBrandGamesMap(String brandcode) throws Exception {
		List<EnterpriseOperatingBrandGame> list = enterpriseOperatingBrandGameService.takeBrandGames(new EnterpriseOperatingBrandGame(brandcode));
		Map<String, Game> games = new HashMap<String, Game>();
		for (EnterpriseOperatingBrandGame e : list) {
			Game g = SystemCache.getInstance().getGame(e.getGamecode());
			games.put(g.getGamecode(), g.clone());
		}
		return games;
	}
	
	@Override
	public List<Game> takeBrandGames(String brandcode,int flag ) throws Exception {
		List<EnterpriseOperatingBrandGame> list = enterpriseOperatingBrandGameService.takeBrandGames(new EnterpriseOperatingBrandGame(brandcode,flag));
		List<Game> games = new ArrayList<Game>();
		for (EnterpriseOperatingBrandGame e : list) {
			Game g = SystemCache.getInstance().getGame(e.getGamecode());
			games.add(g.clone());
		}
		return games;
	}
	
	@Override
	public Map<String, Game> takeBrandGamesMap(String brandcode,int flag ) throws Exception {
		List<EnterpriseOperatingBrandGame> list = enterpriseOperatingBrandGameService.takeBrandGames(new EnterpriseOperatingBrandGame(brandcode,flag));
		Map<String, Game> games = new HashMap<String, Game>();
		for (EnterpriseOperatingBrandGame e : list) {
			Game g = SystemCache.getInstance().getGame(e.getGamecode());
			games.put(g.getGamecode(), g.clone());
		}
		return games;
	}
	
	@Override
	public List<Game> takeEnterpriseGames(String enterprisecode) throws Exception {
		Map<String,EnterpriseGame>  __enterprise__games = enterpriseGameService.takeEnterpriseGame(enterprisecode);
		List<Game> games = new ArrayList<Game>();
		for (EnterpriseGame e : __enterprise__games.values()) {
			Game g = SystemCache.getInstance().getGame(e.getGamecode());
			games.add(g);
		}
		return games;
	}

	@Override
	public Game getGameUseCode(String gamecode) throws Exception {
		return super.selectByPrimaryKey(gamecode);
	}
	
	/**
	 * 查询所有游戏
	 * @return
	 */
	@Override
	public List<Game> findGameData(Map<String, Object> map) throws Exception {
		return GameDao.findGameData(map);
	}
	
	/**
	 * 统计所有游戏
	 * @return
	 */
	@Override
	public int findGameDataCount(Map<String, Object> map) throws Exception {
		return GameDao.findGameDataCount(map);
	}
	
	/**
	 * 根据游戏编码删除游戏类型
	 * @param string
	 */
	@Override
	public void deleteGameType(String gamecode) throws Exception {
		GameDao.deleteGameType(gamecode);
	}
	
	/**
	 * 根据游戏编码批量删除游戏类型
	 * @param string
	 */
	@Override
	public void deleteSelectGameType(String[] array) throws Exception {
		GameDao.deleteSelectGameType(array);
	}
	
	/**
	 * 保存游戏类型
	 * @param game
	 */
	public void saveGameType(Game game)throws Exception{
		GameDao.saveGameType(game);
	}
	
	/**
	 * 根据游戏类型编码查询
	 * @param gamecode
	 * @return
	 * @throws Exception
	 */
	@Override
	public Game getGame(String gamecode) throws Exception {
		return GameDao.getGame(gamecode);
	}
	
	/**
	 * 更新游戏类型
	 */
	@Override
	public void updateGameType(Game game) throws Exception {
		GameDao.updateGameType(game);
	}
}
