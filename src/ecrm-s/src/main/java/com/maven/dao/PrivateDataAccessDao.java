package com.maven.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.PrivateDataAccess;

@Repository
public interface PrivateDataAccessDao extends BaseDao<PrivateDataAccess>{

	/**
	 * 查询可授隐私数据权限的用户
	 * @param object
	 * @return
	 */
	public List<PrivateDataAccess> selectAccredit(Object object);
	
	/**
	 * 查询可授隐私数据权限的用户总数
	 * @param object
	 * @return
	 */
	public int selectAccreditCount(Object object);
	
	/**
	 * 检查用户是否有查看隐私数据权限
	 * @param object
	 * @return
	 */
	public int checkAuthority(Object object);
}
