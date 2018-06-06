package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.UserMoneyInAndOut;
/**
 * 账变记录
 * @author Ethan
 *
 */
@Repository
public interface UserMoneyInAndOutDao extends BaseDao<UserMoneyInAndOut>{
	/**
	 * 根据员工编码,添加对应的账户变动记录
	 * @param map
	 */
	void insertAccountChangeRecord(Object object);
	
	/**
	 * 根据员工编码和游戏类型，对所有记录标记为已下分
	 * @param map
	 */
	int updateIsdown(UserMoneyInAndOut object) throws Exception ;
	/**
	 * 查询上下分记录
	 * @param object
	 * @return
	 */
	List<UserMoneyInAndOut> findMoneyInAndOut(Map<String, Object> object);
	/**
	 * 统计上下分记录
	 * @param object
	 * @return
	 */
	Map<String, Object> findMoneyInAndOutCount(Map<String, Object> object);
	
	/**
	 * 查询上下分记录
	 * @param object
	 * @return
	 */
	List<UserMoneyInAndOut> findMoneyInAndOutWarn(Map<String, Object> object);
	/**
	 * 统计上下分记录
	 * @param object
	 * @return
	 */
	Map<String, Object> findMoneyInAndOutCountWarn(Map<String, Object> object);
	
	/**
	 * 查找最后一条上分记录
	 * @param object
	 * @return
	 */
	UserMoneyInAndOut findMaxUpRecord(String employeecode);
	
	/**
	 * 修改资金返还状态
	 * @param object
	 * @return
	 */
	int updateBackState(String moneyinoutcode);
	
	/**
	 * 修改上下分状态
	 * @param inout
	 * @return
	 */
	int updateInOutState(UserMoneyInAndOut inout);

}
