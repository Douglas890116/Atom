package com.maven.service;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.GamePlatformPrefix;

@Service
public interface GamePlatformPrefixService extends BaseServcie<GamePlatformPrefix>{
	/**
	 * 保存
	 * @param game
	 */
	@DataSource("master")
	public void saveGamePlatformPrefix(GamePlatformPrefix game)throws Exception;
	
	/**
	 * 更新
	 * @param game
	 * @throws Exception
	 */
	@DataSource("master")
	public void updateGamePlatformPrefix(GamePlatformPrefix game)throws Exception;
}
