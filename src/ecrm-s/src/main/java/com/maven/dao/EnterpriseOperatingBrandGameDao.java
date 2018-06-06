package com.maven.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.base.dao.DataSource;
import com.maven.entity.EnterpriseOperatingBrandGame;

@Repository
public interface EnterpriseOperatingBrandGameDao extends BaseDao<EnterpriseOperatingBrandGame>{
	
	public int deleteEnterpriseGame(String enterprisecode);
	public int  deleteGamecode(String gamecode);
	
	/**
	 * 批量更新数据
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int updateListData(List<EnterpriseOperatingBrandGame> object) throws Exception ;
}
