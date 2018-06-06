package com.maven.dao;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.GamePlatformPrefix;

@Repository
public interface GamePlatformPrefixDao extends BaseDao<GamePlatformPrefix>{

	void saveGamePlatformPrefix(GamePlatformPrefix game);


	void updateGamePlatformPrefix(GamePlatformPrefix game);
}
