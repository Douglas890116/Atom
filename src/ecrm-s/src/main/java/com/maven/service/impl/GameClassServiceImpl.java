package com.maven.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.GameClassDao;
import com.maven.entity.GameClass;
import com.maven.service.GameClassService;

@Service
public class GameClassServiceImpl extends BaseServiceImpl<GameClass> implements GameClassService{

	@Autowired
	private GameClassDao gameClassDao;
	
	@Override
	public BaseDao<GameClass> baseDao() {
		return gameClassDao;
	}

	@Override
	public Class<GameClass> getClazz() {
		return GameClass.class;
	}

	@Override
	public List<GameClass> takeFormatMenu(String gameType) {
		List<GameClass> list = gameClassDao.selectSortMenu(gameType);
		return createMenu(list);
	}
	
	private List<GameClass> createMenu(List<GameClass> list){
		Map<String,List<GameClass>> mConll = new HashMap<String, List<GameClass>>(); 
		for (GameClass g : list) {
			if(mConll.get(g.getParentclasstype())==null){
				List<GameClass>  items = new ArrayList<GameClass>();
				mConll.put(g.getParentclasstype(), items);
			}
			mConll.get(g.getParentclasstype()).add(g);
		}
		List<GameClass> first = mConll.get("0");
		for (GameClass g :  first) {
			g.setChildren(mConll.get(String.valueOf(g.getGameclasscode())));
		}
		return first;
	}

}
