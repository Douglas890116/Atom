package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.FavourableUserDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.FavourableUser;

@Repository
public class FavourableUserDaoImpl extends BaseDaoImpl<FavourableUser> implements FavourableUserDao{

	@Override
	public void addBetRecord(FavourableUser betrecord) throws Exception {
		getSqlSession().insert("FavourableUser.insertSelective", betrecord);
	}
	
	@Override
	public void deleteBetRecord(String lsh) throws Exception {
		getSqlSession().insert("FavourableUser.deleteByPrimaryKey", lsh);
	}

	@Override
	public List<FavourableUser> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectList("FavourableUser.selectBetRecord", parameter);
	}

	@Override
	public int selectBetRecordCount(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("FavourableUser.selectBetRecordCount", parameter);
	}

}
