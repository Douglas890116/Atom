package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.LoginWhiteList;

@Repository
public interface LoginWhiteListDao extends BaseDao<LoginWhiteList> {

	/**
	 * 查询指定企业下面的全部白名单
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public List<LoginWhiteList> queryByEnterprisecode(Map<String, Object> object) throws Exception ;
	
	
	/**
	 * 批量删除
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public void deleteSelectIp(String[] array) throws Exception ;
	
	/**
	 * 保存
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public void saveLoginWhiteList(LoginWhiteList obj) throws Exception ;
		
	
	/**
	 * 修改
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public void updateLoginWhiteList(LoginWhiteList obj) throws Exception ;
}
