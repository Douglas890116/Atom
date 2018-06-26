package com.hy.pull.mapper;

import java.util.List;
import java.util.Map;

/**
 * 包含通用操作的接口
 * 
 * @author temdy
 *
 */
public interface BaseMapper {
	
	/**
	 * 插入记录
	 * 
	 * @param entity 插入对象
	 * @return 返回影响的行数
	 */
	int insert(Map<String, Object> entity);

	/**
	 * 批量插入记录
	 * 
	 * @param list  插入对象列表
	 * @return 返回影响的行数
	 */
	int batchInsert(List<Map<String, Object>> list);
	
	/**
	 * 选择插入记录
	 * 
	 * @param entity 插入对象
	 * @return 返回影响的行数
	 */
	int optInsert(Map<String, Object> entity);

	/**
	 * 根据实体删除记录
	 * 
	 * @param entity 条件
	 * @return 返回影响的行数
	 */
	int delete(Map<String, Object> entity);

	/**
	 * 更新记录
	 * 
	 * @param entity 更新对象
	 * @return 返回影响的行数
	 */
	int update(Map<String, Object> entity);

	/**
	 * 批量更新记录
	 * 
	 * @param list 更新对象列表
	 * @return 返回影响的行数
	 */
	int batchUpdate(List<Map<String, Object>> list);

	/**
	 * 根据主键查询单条记录
	 * 
	 * @param key 主键
	 * @return 返回单条记录
	 */
	Map<String, Object> selectByPrimaryKey(String key);

	/**
	 * 查询所有记录
	 * 
	 * @return 返回所有列表
	 */
	List<Map<String, Object>> selectAll();

	/**
	 * 通过实体字段查询记录
	 * 
	 * @param entity 条件集合
	 * @return 返回筛选列表
	 */
	List<Map<String, Object>> selectByEntity(Map<String, Object> entity);

	/**
	 * 通过实体字段模糊查询记录
	 * 
	 * @param entity 条件集合
	 * @return 返回筛选列表
	 */
	List<Map<String, Object>> selectByEntityLike(Map<String, Object> entity);

	/**
	 * 通过实体字段模糊查询记录分页
	 * 
	 * @param entity 条件集合
	 * @return 返回筛选列表
	 */
	List<Map<String, Object>> selectByEntityLikePage(Map<String, Object> entity);

	/**
	 * 通过实体字段检索结果总数
	 * 
	 * @param entity 条件集合
	 * @return 返回结果总数
	 */
	int count(Map<String, Object> entity);

	/**
	 * 通过实体字段检索结果按某个字段求和汇总
	 * 
	 * @param entity 条件集合
	 * @return 返回按某个字段求和汇总
	 */
	String sum(Map<String, Object> entity);
}