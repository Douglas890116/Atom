package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.UserMoneyInAndOutDao;
import com.maven.entity.UserMoneyInAndOut;
@Repository
public class UserMoneyInAndOutDaoImpl extends BaseDaoImpl<UserMoneyInAndOut>implements UserMoneyInAndOutDao {

	/**
	 * 根据员工编码,添加对应的账户变动记录
	 * @param map
	 */
	public void insertAccountChangeRecord(Object object) {
		getSqlSession().insert("UserMoneyInAndOut.insert", object);
	}
	/**
	 * 查询上下分记录
	 * @param object
	 * @return
	 */
	@Override
	public List<UserMoneyInAndOut> findMoneyInAndOut(Map<String, Object> object) {
		return getSqlSession().selectList("UserMoneyInAndOut.findMoneyInAndOut", object);
	}
	/**
	 * 统计上下分记录
	 * @param object
	 * @return
	 */
	@Override
	public Map<String, Object> findMoneyInAndOutCount(Map<String, Object> object) {
		return getSqlSession().selectOne("UserMoneyInAndOut.findMoneyInAndOutCount", object);
	}
	
	/**
	 * 查询上下分记录
	 * @param object
	 * @return
	 */
	@Override
	public List<UserMoneyInAndOut> findMoneyInAndOutWarn(Map<String, Object> object) {
		return getSqlSession().selectList("UserMoneyInAndOut.findMoneyInAndOutWarn", object);
	}
	/**
	 * 统计上下分记录
	 * @param object
	 * @return
	 */
	@Override
	public Map<String, Object> findMoneyInAndOutCountWarn(Map<String, Object> object) {
		return getSqlSession().selectOne("UserMoneyInAndOut.findMoneyInAndOutCountWarn", object);
	}
	
	
	/**
	 * 根据员工编码和游戏类型，对所有记录标记为已下分
	 * @param map
	 */
	public int updateIsdown(UserMoneyInAndOut object) throws Exception {
		return getSqlSession().update("UserMoneyInAndOut.update_in_out_isdown", object);
	}
	
	
	/**
	 * 查找最后一条上分记录
	 * @param object
	 * @return
	 */
	public UserMoneyInAndOut findMaxUpRecord(String employeecode) {
		return getSqlSession().selectOne("UserMoneyInAndOut.findMaxUpRecord", employeecode);
	}
	
	/**
	 * 修改资金返回状态
	 */
	@Override
	public int updateBackState(String moneyinoutcode) {
		return getSqlSession().update("UserMoneyInAndOut.update_back_state", moneyinoutcode);
	}
	
	
	@Override
	public int updateInOutState(UserMoneyInAndOut inout) {
		return getSqlSession().update("UserMoneyInAndOut.update_in_out_state", inout);
	}

}
