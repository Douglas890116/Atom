package com.maven.base.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.maven.base.dao.DataSource;

/**
 * @author Kevin.Z
 * @link kevinzl@126.com
 */
@Component
public interface BaseServcie<T> {
	
	/**
	 * 根据参数精确查询
	 * @param t
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<T> select(T t)throws Exception;

	/**
	 * 根据参数模糊查询
	 * @param object
	 * @return
	 */
	@DataSource("slave")
	public List<T> selectAll(Object object)throws Exception;
	
	/**
	 * 模糊查询总记录数
	 * @param object
	 * @return
	 */
	@DataSource("slave")
	public int selectAllCount(Object object)throws Exception;
	
	/**
	 * 根据主键查询
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public T selectByPrimaryKey(String id)throws Exception;
	

	/**
	 * 查询第一个元素
	 * 
	 * @param params
	 *            参数集合
	 * @return
	 */
	@DataSource("slave")
	public T selectFirst(T t)throws Exception;
	
	/**
	 * 根据条件查询单个元素，当返回元素不是单个时抛出错误
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public T selectOne(T t)throws Exception;
	
	/**
	 * 获取泛型的类信息
	 * 
	 * @return
	 */
	public Class<T> getClazz();

}
