package com.maven.dao.impl;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.PermissionMenuDao;
import com.maven.entity.PermissionMenu;

@Repository
public class PermissionMenuDaoImpl extends BaseDaoImpl<PermissionMenu> implements PermissionMenuDao{

	@Override
	public int updateSort(Object object) throws Exception {
		return super.update("PermissionMenu.updateSort", object);
	}

}
