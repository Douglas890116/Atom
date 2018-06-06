package com.maven.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.EnterpriseOperatingBrandGameDao;
import com.maven.entity.EnterpriseOperatingBrandGame;

@Repository
public class EnterpriseOperatingBrandGameDaoImpl extends BaseDaoImpl<EnterpriseOperatingBrandGame> implements EnterpriseOperatingBrandGameDao{

	@Override
	public int deleteEnterpriseGame(String enterprisecode) {
		return super.delete("EnterpriseOperatingBrandGame.deleteEnterpriseGame", enterprisecode);
	}

	@Override
	public int deleteGamecode(String gamecode) {
		return super.delete("EnterpriseOperatingBrandGame.deleteGamecode", gamecode);
	}
	
	
	/**
	 * 批量更新数据
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int updateListData(List<EnterpriseOperatingBrandGame> object) throws Exception {
		return super.update("EnterpriseOperatingBrandGame.updateListData", object);
	}
}
