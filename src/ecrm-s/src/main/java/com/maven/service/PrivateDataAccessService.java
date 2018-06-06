package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.PrivateDataAccess;

@Service
public interface PrivateDataAccessService extends BaseServcie<PrivateDataAccess>{
	
	/**
	 * 添加隐私数据权限
	 * @return
	 */
	@DataSource("master")
	public int addPriveDateAccess(List<PrivateDataAccess> objects)throws Exception;
	/**
	 * 批量删除授权
	 * @param list
	 * @return
	 */
	@DataSource("master")
	public int deleteListAccess(List<String> ids)throws Exception;
	
	/**
	 * 单条删除授权
	 * @param list
	 * @return
	 */
	@DataSource("master")
	public int deleteAccess(String id)throws Exception;
	
	/**
	 * 查询可授隐私数据权限的用户
	 * @param object
	 * @return
	 */
	@DataSource("slave")
	public List<PrivateDataAccess> selectAccredit(Map<String,Object> object);
	
	/**
	 * 查询可授隐私数据权限的用户总数
	 * @param object
	 * @return
	 */
	@DataSource("slave")
	public int selectAccreditCount(Map<String,Object> object);

	/**
	 * 检查用户是否有查看隐私数据权限
	 * @param employee
	 * @return
	 */
	@DataSource("slave")
	public boolean checkAuthority(EnterpriseEmployee employee);
}
