package com.maven.base.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.maven.utility.BizException;

/**
 * @author Kevin.Z
 * @link kevinzl@126.com
 */
@Repository
public interface BaseDao<T> {

	/**
	 * 添加用户
	 * 
	 * @param mapId
	 * @param t
	 *            泛型对象
	 * @return
	 */
	public T save(String mapId, T t);

	/**
	 * 添加用户
	 * 
	 * @param mapId
	 * @param obj
	 *            Object参数集合，通过对象属性配置文件获取值
	 * @return
	 */
	public int add(String mapId, Object obj);
	
	/**
	 * 批量保存
	 * 
	 * @param mapId
	 *            MyBaits调用的方法SQL语句ID
	 * @param list
	 *            List集合
	 * @return
	 * @throws BizException
	 */
	public int saveRecordBatch(String mapId, List<T> list);
	

	/**
	 * 
	 * @param mapId
	 *            MyBaits调用的方法SQL语句ID
	 * @param 传入ID参数
	 *            ，通过变量名id在配置文件中获取
	 * @return
	 */
	public T selectByPrimaryKey(String mapId, String id);

	/**
	 * 精确查询
	 * @param mapId
	 * @param t
	 * @return
	 */
	public List<T> select(String mapId,T t);
	/**
	 * 
	 * @param mapId
	 *            MyBaits调用的方法SQL语句ID
	 * @param params
	 *            Object参数集合，通过对象属性配置文件获取值
	 * @return
	 */
	public List<T> selectAll(String mapId, Object params);

	/**
	 * 
	 * @param mapId
	 *            MyBaits调用的方法SQL语句ID
	 * @param params
	 *            Map参数集合，通过Key值在配置文件获取
	 * @return
	 */
	public int selectAllCount(String mapId, Object params);

	/**
	 * 
	 * @param mapId
	 *            MyBaits调用的方法SQL语句ID
	 * @param params
	 *            任意对象，通过对象属性配置文件获取值
	 * @return
	 * @throws BizException
	 */
	public int update(String mapId, Object t);


	/**
	 * 
	 * @param mapId
	 *            MyBaits调用的方法SQL语句ID
	 * @param params
	 *            Map参数集合，通过Key值在配置文件获取
	 * @return
	 * @throws BizException
	 */
	public int delete(String mapId, Object params);
	
	/**
	 * 逻辑删除
	 * @param mapId 
	 * @param params
	 * @return
	 */
	public int logicDelete(String mapId,Object params);
	
	

	
}
