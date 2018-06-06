package com.maven.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.GameCategoryDao;
import com.maven.entity.GameCategory;
import com.maven.service.GameCategoryService;
import com.maven.util.AttrCheckout;

@Service
public class GameCategoryServiceImpl extends BaseServiceImpl<GameCategory> implements GameCategoryService{

	@Autowired
	private GameCategoryDao gameCategoryDao;
	
	@Override
	public BaseDao<GameCategory> baseDao() {
		return gameCategoryDao;
	}

	@Override
	public Class<GameCategory> getClazz() {
		return GameCategory.class;
	}

	@Override
	public List<GameCategory> takeAllGameCategory() throws Exception {
		return super.select(null);
	}

	@Override
	public List<GameCategory> takeGategoryUseGameType(String gametype) throws Exception {
		return super.select(AttrCheckout.checkout(new GameCategory(gametype,null), false, new String[]{"gametype"}));
	}

	@Override
	public GameCategory takegategoryUseCmnKey(String gametype, String categorytype) throws Exception {
		return super.selectOne(AttrCheckout.checkout(new GameCategory(gametype,categorytype), false, new String[]{"gametype","categorytype"}));
	}
	
	

}
