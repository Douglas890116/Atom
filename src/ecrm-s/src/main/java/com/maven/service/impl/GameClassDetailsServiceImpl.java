package com.maven.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.GameClassDetailsDao;
import com.maven.entity.GameClassDetails;
import com.maven.service.GameClassDetailsService;

@Service
public class GameClassDetailsServiceImpl extends BaseServiceImpl<GameClassDetails> implements GameClassDetailsService{

	@Autowired
	private GameClassDetailsDao gameClassDetailsDao;
	
	@Override
	public BaseDao<GameClassDetails> baseDao() {
		return gameClassDetailsDao;
	}

	@Override
	public Class<GameClassDetails> getClazz() {
		return GameClassDetails.class;
	}

}
