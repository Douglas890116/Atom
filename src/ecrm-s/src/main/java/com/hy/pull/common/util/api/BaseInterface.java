package com.hy.pull.common.util.api;

import java.util.Map;

/**
 * 游戏接口
 * @author temdy
 */
public interface BaseInterface {
	
	/**
	 * 创建用户接口
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	Object createAccount(Map<String, Object> entity);
	
	/**
	 * 获取余额
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	Object getBalance(Map<String, Object> entity);
	
	/**
	 * 下分（取款）的接口
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	Object withdraw(Map<String, Object> entity);
	
	/**
	 * 上分（存款）的接口
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	Object deposit(Map<String, Object> entity);	
	
	/**
	 * 获取游戏结果
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	Object getRecord(Map<String, Object> entity);
	
	/**
	 * 更新信息接口
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	Object updateInfo(Map<String, Object> entity);
	
	/**
	 * 获取订单接口
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	Object getOrder(Map<String, Object> entity);
	
	/**
	 * 登录的接口
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	Object login(Map<String, Object> entity);
	
	/**
	 * 是否在线接口
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	Object isOnLine(Map<String, Object> entity);
}
