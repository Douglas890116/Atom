package com.hy.pull.mapper;

import java.util.Map;

/**
 * 对应数据表sa_gameinfo的操作接口
 * @author temdy
 */
public interface SaGameinfoMapper extends BaseMapper{
	/**
	 * 通过实体字段检索结果按某个字段求最大值
	 * 
	 * @param entity 条件集合
	 * @return 返回按某个字段求最大值
	 */
	String max(Map<String, Object> entity);
}
