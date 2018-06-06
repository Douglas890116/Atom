package com.maven.base.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;

@Repository
public abstract class BaseDaoImpl<T> extends SqlSessionDaoSupport implements BaseDao<T> {

	@Override
	public T save(String mapId, T t) {
		int row = getSqlSession().insert(mapId, t);
		if (row != 1)
			return null;
		return t;
	}

	@Override
	public int add(String mapId, Object obj) {
		int row = getSqlSession().insert(mapId, obj);
		return row;
	}

	@Override
	public int saveRecordBatch(String mapId, List<T> list) {
		return getSqlSession().insert(mapId, list);
	}
	
	@Override
	public List<T> select(String mapId,T t){
		return getSqlSession().selectList(mapId, t);
	}
	
	@Override
	public T selectByPrimaryKey(String mapId, String id) {
		return getSqlSession().selectOne(mapId, id);
	}

	@Override
	public List<T> selectAll(String mapId, Object params) {
		return getSqlSession().selectList(mapId, params);
	}

	@Override
	public int selectAllCount(String mapId, Object params) {
		Long count =  getSqlSession().selectOne(mapId, params);
		return count == null?0:count.intValue();
	}

	@Override
	public int update(String mapId, Object t) {
		return getSqlSession().update(mapId, t);
	}

	@Override
	public int delete(String mapId, Object params) {
		return getSqlSession().update(mapId, params);
	}
	
	@Override
	public int logicDelete(String mapId,Object params){
		return getSqlSession().update(mapId,params);
	}

}
