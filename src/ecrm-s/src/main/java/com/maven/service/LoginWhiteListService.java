package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.LoginWhiteList;

@Service
public interface LoginWhiteListService extends BaseServcie<LoginWhiteList>{

	/**
	 * 查询指定企业下面的全部白名单
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<LoginWhiteList> queryByEnterprisecode(Map<String, Object> object)throws Exception;
	
	/**
	 * 批量删除
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public void deleteSelectIp(String[] array) throws Exception ;
	
	
	/**
	 * 保存
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public void saveLoginWhiteList(LoginWhiteList obj) throws Exception ;
		
	
	/**
	 * 修改
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public void updateLoginWhiteList(LoginWhiteList obj) throws Exception ;
}
