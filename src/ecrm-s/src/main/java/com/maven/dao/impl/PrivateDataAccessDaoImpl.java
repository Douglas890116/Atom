package com.maven.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.PrivateDataAccessDao;
import com.maven.entity.PrivateDataAccess;

@Repository
public class PrivateDataAccessDaoImpl extends BaseDaoImpl<PrivateDataAccess> implements PrivateDataAccessDao{

	@Override
	public List<PrivateDataAccess> selectAccredit(Object object) {
		return getSqlSession().selectList("PrivateDataAccess.selectAccredit", object);
	}

	@Override
	public int selectAccreditCount(Object object) {
		Long count =  getSqlSession().selectOne("PrivateDataAccess.selectAccreditCount", object);
		return count == null?0:count.intValue();
	}
	@Override
	public int checkAuthority(Object object) {
		Long count =  getSqlSession().selectOne("PrivateDataAccess.checkAuthority", object);
		return count == null?0:count.intValue();
	}
}
