package com.maven.dao;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.PermissionMenu;

@Repository
public interface PermissionMenuDao extends BaseDao<PermissionMenu> {
	
	public int updateSort(Object object) throws Exception;

}
