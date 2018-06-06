package com.maven.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.EnterpriseBrandNoticDao;
import com.maven.entity.EnterpriseBrandNotic;

@Repository
public class EnterpriseBrandNoticDaoImpl extends BaseDaoImpl<EnterpriseBrandNotic> implements EnterpriseBrandNoticDao{

	@Override
	public int logicDelete(Object object) throws Exception {
		return getSqlSession().update("EnterpriseBrandNotic.logicDelete", object);
	}

	@Override
	public List<EnterpriseBrandNotic> showUserNotic(EnterpriseBrandNotic notic) throws Exception {
		return getSqlSession().selectList("EnterpriseBrandNotic.showUserNotic", notic);
	}
	
}
