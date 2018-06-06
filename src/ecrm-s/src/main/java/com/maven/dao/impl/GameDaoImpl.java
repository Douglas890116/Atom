package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.GameDao;
import com.maven.entity.Game;

@Repository
public class GameDaoImpl extends BaseDaoImpl<Game> implements GameDao{
	
	/**
	 * 查询所有游戏
	 * @return
	 */
	@Override
	public List<Game> findGameData(Map<String, Object> map) {
		return getSqlSession().selectList("Game.findGameData", map);
	}

	/**
	 * 统计所有游戏
	 * @return
	 */
	@Override
	public int findGameDataCount(Map<String, Object> map) {
		return getSqlSession().selectOne("Game.findGameDataCount", map);
	}
	
	/**
	 * 根据游戏编码删除游戏类型
	 * @param string
	 */
	@Override
	public void deleteGameType(String gamecode) {
		getSqlSession().delete("Game.deleteGameType", gamecode);
	}
	
	/**
	 * 根据游戏编码批量删除游戏类型
	 * @param string
	 */
	@Override
	public void deleteSelectGameType(String[] array) {
		getSqlSession().delete("Game.deleteSelectGameType", array);
	}

	/**
	 * 保存游戏类型
	 * @param game
	 */
	@Override
	public void saveGameType(Game game) {
		getSqlSession().insert("Game.saveGameType", game);
	}
	
	/**
	 * 根据游戏类型编码查询
	 * @param gamecode
	 * @return
	 * @throws Exception
	 */
	@Override
	public Game getGame(String gamecode) {
		return getSqlSession().selectOne("Game.selectByPrimaryKey", gamecode);
	}
	
	/**
	 * 更新游戏类型
	 */
	@Override
	public void updateGameType(Game game) {
		getSqlSession().update("Game.updateGameType", game);
	}
}
