package com.maven.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.GameClassDao;
import com.maven.entity.GameClass;

@Repository
public class GameClassDaoImpl extends BaseDaoImpl<GameClass> implements GameClassDao{

	@Override
	public List<GameClass> selectSortMenu(String gameType) {
		return getSqlSession().selectList("GameClass.selectSortMenu",gameType);
	}

}
