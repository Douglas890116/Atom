package com.maven.dao.impl;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.GamePlatformPrefixDao;
import com.maven.entity.GamePlatformPrefix;

@Repository
public class GamePlatformPrefixDaoImpl extends BaseDaoImpl<GamePlatformPrefix> implements GamePlatformPrefixDao {
	
	/**
	 * 保存
	 * @param game
	 */
	@Override
	public void saveGamePlatformPrefix(GamePlatformPrefix game) {
		getSqlSession().insert("GamePlatformPrefix.saveGamePlatformPrefix", game);
	}
	
	/**
	 * 更新
	 */
	@Override
	public void updateGamePlatformPrefix(GamePlatformPrefix game) {
		getSqlSession().update("GamePlatformPrefix.updateByPrimaryKey", game);
	}
}
