package com.maven.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.Game;
import com.maven.entity.UserMoneyInAndOut;
/**
 * 上下分记录
 * @author Ethan
 *
 */
@Service
public interface UserMoneyInAndOutService{
	/**
	 * 根据员工编码,添加对应的上下分
	 * @param map
	 */
	@DataSource("master")
	void saveMoneyInAndOutRecord(UserMoneyInAndOut object) throws Exception;
	
	/**
	 * 根据员工编码和游戏类型，对所有记录标记为已下分
	 * @param map
	 */
	@DataSource("master")
	int updateIsdown(UserMoneyInAndOut object) throws Exception;
	
	/**
	 * 查询上下分记录
	 * @param object
	 * @return
	 */
	@DataSource("slave")
	List<UserMoneyInAndOut> findMoneyInAndOut(Map<String, Object> object)throws Exception;
	
	
	/**
	 * 统计上下分记录
	 * @param object
	 * @return
	 */
	@DataSource("slave")
	Map<String, Object> findMoneyInAndOutCount(Map<String, Object> object)throws Exception;
	
	/**
	 * 查询上下分记录
	 * @param object
	 * @return
	 */
	@DataSource("slave")
	List<UserMoneyInAndOut> findMoneyInAndOutWarn(Map<String, Object> object)throws Exception;
	/**
	 * 统计上下分记录
	 * @param object
	 * @return
	 */
	@DataSource("slave")
	Map<String, Object> findMoneyInAndOutCountWarn(Map<String, Object> object)throws Exception;
	
	/**
	 * 返回丢失金额
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	int tc_back_losemoney(String moneyinoutcode,String moneychangedesc)throws Exception;
	
	/**
	 * 修改上下分状态
	 * @param inout
	 * @return
	 */
	@DataSource("master")
	int updateInOutState(UserMoneyInAndOut inout) throws Exception ;

	/**
	 * 查找最后一条上分记录
	 * @param object
	 * @return
	 */
	@DataSource("slave")
	UserMoneyInAndOut findMaxUpRecord(String employeecode)throws Exception;
	
	/**
	 * 转分
	 * @param gametype
	 * @param employeecode
	 * @param listGame1
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public void transfer(String gametype,String employeecode,List<Game> listGame1) throws Exception ;
	/**
	 * 指定游戏上分
	 * @param orderamount 金额
	 * @param gametype 游戏类型
	 * @param employeecode 用户编码
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public boolean upIntegral(BigDecimal beforeAmount,int orderamount,String gametype ,EmployeeApiAccout ea, String patchno) throws Exception;
	
	
	/**
	 * 指定游戏下分
	 * @param orderamount 金额
	 * @param gametype 游戏类型
	 * @param employeecode 用户编码
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public boolean downIntegral(BigDecimal beforeAmount,int downIntegralAmount,String gametype ,EmployeeApiAccout eaa, String patchno) throws Exception;
}
