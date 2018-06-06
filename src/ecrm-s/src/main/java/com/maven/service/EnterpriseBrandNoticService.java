package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.entity.EnterpriseBrandNotic;

@Service
public interface EnterpriseBrandNoticService {
	
	/**
	 * 添加公告
	 * @param object
	 * @return
	 */
	@DataSource("master")
	public int addNotic(Map<String,Object> object) throws Exception;
	
	/**
	 * 编辑公告
	 * @param object
	 * @return
	 */
	@DataSource("master")
	public int eidtNotic(Map<String,Object> object) throws Exception;
	
	/**
	 * 通过主键获取公告
	 */
	@DataSource("slave")
	public EnterpriseBrandNotic takeNoticByCode(String noticcode) throws Exception; 
	
	/**
	 * 读取公告
	 * @param object
	 * @return
	 */
	@DataSource("slave")
	public List<EnterpriseBrandNotic> takeNotic(Map<String,Object> object) throws Exception;
	
	/**
	 * 统计公告总数
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public int takeNoticCount(Map<String,Object> object) throws Exception;
	
	/**
	 * 删除公告
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int deleteNotic(Map<String,Object> object)throws Exception;
	

	/**
	 * 读取用户可看到的公告
	 * @param notic
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<EnterpriseBrandNotic> takeUserNotic(EnterpriseBrandNotic notic)throws Exception;
	
	

}
