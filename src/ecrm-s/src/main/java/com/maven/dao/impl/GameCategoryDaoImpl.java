package com.maven.dao.impl;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.GameCategoryDao;
import com.maven.entity.GameCategory;

@Repository
public class GameCategoryDaoImpl extends BaseDaoImpl<GameCategory> implements GameCategoryDao{

}
