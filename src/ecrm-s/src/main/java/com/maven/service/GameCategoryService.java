package com.maven.service;

import java.util.List;

import com.maven.base.dao.DataSource;
import com.maven.entity.GameCategory;

public interface GameCategoryService {
	
	/**
	 * 获取所有的接入游戏平台游戏种类
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<GameCategory> takeAllGameCategory() throws Exception;
	
	/**
	 * 获取接入游戏平台游戏种类根据游戏类型
	 * @param gametype
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<GameCategory> takeGategoryUseGameType(String gametype)throws Exception;
	
	/**
	 * 获取介入游戏平台游戏种类，根据游戏类型与种类类型
	 * @param gametype
	 * @param categorytype
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public GameCategory takegategoryUseCmnKey(String gametype,String categorytype)throws Exception;
	
	

}
