package com.maven.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.GameClass;

@Repository
public interface GameClassDao extends BaseDao<GameClass>{
	
	public List<GameClass> selectSortMenu(String gameType);

}
