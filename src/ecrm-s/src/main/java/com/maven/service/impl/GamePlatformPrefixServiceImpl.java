package com.maven.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.GamePlatformPrefixDao;
import com.maven.entity.GamePlatformPrefix;
import com.maven.service.GamePlatformPrefixService;

@Service
public class GamePlatformPrefixServiceImpl extends BaseServiceImpl<GamePlatformPrefix> implements GamePlatformPrefixService {

	@Autowired
	private GamePlatformPrefixDao prefixDao;
	
	
	@Override
	public BaseDao<GamePlatformPrefix> baseDao() {
		return prefixDao;
	}

	@Override
	public Class<GamePlatformPrefix> getClazz() {
		return GamePlatformPrefix.class;
	}
	
	
	/**
	 * 保存
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public void saveGamePlatformPrefix(GamePlatformPrefix game)throws Exception {
		prefixDao.saveGamePlatformPrefix(game);
	}
	
	/**
	 * 保存
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public void updateGamePlatformPrefix(GamePlatformPrefix obj) throws Exception {
		prefixDao.updateGamePlatformPrefix(obj);
	}
}
