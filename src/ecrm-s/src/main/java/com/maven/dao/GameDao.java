package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.Game;

@Repository
public interface GameDao extends BaseDao<Game>{

	List<Game> findGameData(Map<String, Object> map);

	int findGameDataCount(Map<String, Object> map);

	void deleteGameType(String gamecode);

	void deleteSelectGameType(String[] array);

	void saveGameType(Game game);

	Game getGame(String gamecode);

	void updateGameType(Game game);

}
