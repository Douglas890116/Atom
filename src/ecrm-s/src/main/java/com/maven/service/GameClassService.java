package com.maven.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.GameClass;

@Service
public interface GameClassService extends BaseServcie<GameClass>{
	
	/**
	 * 根据游戏类型格式化JSON菜单
	 * @param gameType
	 * @return
	 */
	@DataSource("slave")
	public List<GameClass> takeFormatMenu(String gameType);

}
