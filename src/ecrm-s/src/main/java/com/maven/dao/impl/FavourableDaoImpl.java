package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.FavourableDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.Favourable;

@Repository
public class FavourableDaoImpl extends BaseDaoImpl<Favourable> implements FavourableDao{

	@Override
	public void addBetRecord(Favourable betrecord) throws Exception {
		getSqlSession().insert("Favourable.insert", betrecord);
	}
	@Override
	public void updateBetRecord(Favourable betrecord) throws Exception {
		getSqlSession().insert("Favourable.updateByPrimaryKeySelective", betrecord);
	}

	@Override
	public List<Favourable> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectList("Favourable.selectBetRecord", parameter);
	}

	@Override
	public int selectBetRecordCount(Map<String, Object> parameter) throws Exception {
		return getSqlSession().selectOne("Favourable.selectBetRecordCount", parameter);
	}

}
