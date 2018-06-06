package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.LoginWhiteListDao;
import com.maven.entity.LoginWhiteList;

@Repository
public class LoginWhiteListDaoImpl extends BaseDaoImpl<LoginWhiteList> implements LoginWhiteListDao{

	
	/**
	 * 查询指定企业下面的全部白名单
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public List<LoginWhiteList> queryByEnterprisecode(Map<String, Object> object) throws Exception {
		return getSqlSession().selectList("LoginWhiteList.queryByEnterprisecode",object);
	}
	
	/**
	 * 批量删除
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public void deleteSelectIp(String[] array) throws Exception {
		getSqlSession().delete("LoginWhiteList.deleteSelectIp",array);
	}
	
	/**
	 * 保存
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public void saveLoginWhiteList(LoginWhiteList obj) throws Exception {
		getSqlSession().insert("LoginWhiteList.add", obj);
	}
		
	
	/**
	 * 修改
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public void updateLoginWhiteList(LoginWhiteList obj) throws Exception {
		getSqlSession().update("LoginWhiteList.update",obj);
	}
}
