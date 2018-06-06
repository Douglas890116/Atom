package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.entity.EmployeeGamecataloyBonus;
import com.maven.entity.EmployeeRelation;
import com.maven.entity.EnterpriseEmployee;

@Service
public interface EnterpriseEmployeeService{
	
	/**
	 * 分页查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<EnterpriseEmployee> selectAll(Map<String, Object> paramObj)throws Exception;
	
	/**
	 * GGPoker登陆
	 */
	@DataSource("slave")
	public EnterpriseEmployee getGGPokerLogin(EnterpriseEmployee object) throws Exception ;
	
	/**
	 * 查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<EnterpriseEmployee> select(EnterpriseEmployee paramObj)throws Exception;
	
	/**
	 * 根据会员编码批量查找会员列表
	 * @param String[] array
	 */
	@DataSource("slave")
	public List<EnterpriseEmployee> findEmployeeByCodes(String[] array) throws Exception;
	
	/**
	 * 分页查询记录总数
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public int selectAllCount(Map<String, Object> paramObj)throws Exception;
	
	/**
	 * 根据ID获取员工信息
	 * @param employeecode
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public EnterpriseEmployee takeEmployeeByCode(String employeecode) throws Exception;
	/**
	 * 分页参数
	 * @param object
	 * @return List<EnterpriseEmployee>
	 */
	@DataSource("slave")
	public List<EnterpriseEmployee> findEmployee(Map<String,Object> object) throws  Exception;
	/**
	 * 分页参数
	 * @param object
	 * @return List<EnterpriseEmployee>
	 */
	@DataSource("slave")
	public List<EnterpriseEmployee> findEmployeeAgent(Map<String,Object> object) throws  Exception;
	
	/**
	 * 查询总记录条数
	 * @param object
	 * @return List<EnterpriseEmployee>
	 */
	@DataSource("slave")
	public int findEmployeeCount(Map<String,Object> object) throws  Exception;
	
	/**
	 * 代理查询
	 * @param object
	 * @return List<EnterpriseEmployee>
	 */
	@DataSource("slave")
	public List<EnterpriseEmployee> queryAgent(Map<String,Object> map) throws  Exception;
	/**
	 * 查询代理总记录条数
	 * @param object
	 * @return int
	 */
	@DataSource("slave")
	public int queryAgentCount(Map<String,Object> map) throws  Exception;
	
	/**
	 * 获取直属上级与直属下级
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<EnterpriseEmployee> takeDerictly(Map<String,Object> object) throws Exception;
	
	/**
	 * 获取直属上级与直属下级总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public int takeDerictlyCount(Map<String,Object> object) throws Exception;
	
	/**
	 * 注册代理号/会员类型账号
	 * @param enterpriseEmployee 用户信息  EnterpriseEmployee对象
	 * @param bonus  返点信息
	 * @param isDirectly 上级是否为企业号
	 * @throws Exception
	 */
	@DataSource("master")
	public void tc_saveUser(EnterpriseEmployee enterpriseEmployee,List<EmployeeGamecataloyBonus> bonus,boolean isDirectly )throws Exception;
	
	/**
	 * 注册企业类型账号
	 * @param enterpriseEmployee
	 * @throws Exception
	 */
	@DataSource("master")
	public void tc_saveEnterprise(EnterpriseEmployee enterpriseEmployee)throws Exception;
	/**
	 * 根据员工编码删除
	 * @param employeecode
	 */
	@DataSource("master")
	public void tc_deleteEmployee(String employeecode)throws Exception;
	/**
	 * 删除选中的一条或者多条数据
	 * @param String[] array
	 */
	@DataSource("master")
	public void tc_deleteSelectEmployee(String[] array)throws Exception;
	/**
	 * 禁用选中的一条或者多条数据
	 * @param String[] array
	 */
	@DataSource("master")
	public void tc_disableSelectEmployee(String[] array)throws Exception;
	
	/**
	 * 批量修改员工等级
	 * @param Map<String, Object> map
	 */
	@DataSource("master")
	public void updateEmployeeLevel(Map<String, Object> map) throws Exception;

	/**
	 * 批量修改员会员信用评级
	 * @param Map<String, Object> map
	 */
	@DataSource("master")
	public void updateMemberCreditRating(Map<String, Object> map) throws Exception;
	/**
	 * 启用选中的一条或者多条数据
	 * @param String[] array
	 */
	@DataSource("master")
	public void tc_activateSelectEmployee(String[] array)throws Exception;
	
	/**
	 * 判断用户名是否存在
	 * @param loginaccount
	 * @return int
	 */
	@DataSource("slave")
	public List<EnterpriseEmployee> queryEmployeeIsExist(String loginaccount) throws  Exception;
	
	/**
	 * 根据手机号码查用户
	 */
	@DataSource("slave")
	public EnterpriseEmployee takeEmployeeByPhoneno(EnterpriseEmployee object) throws Exception ;
	
	/**
	 * 参与活动时根据用户名获取用户，无用户时返还错误信息
	 * @param loginaccount
	 * @return int
	 */
	@DataSource("slave")
	public EnterpriseEmployee queryEmployeeByLoginaccount(String loginaccount) throws  Exception;
	
	/**
	 * 验证账号是否属于当前登录用户的下级
	 * @param map
	 * @return List<EnterpriseEmployee>
	 */
	@DataSource("slave")
	public List<EnterpriseEmployee> queryEmployeeIsExist(Map<String, Object> map);
	
	/**
	 * API接口用户登录
	 * @param object 返回 EnterpriseEmployee对象
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public EnterpriseEmployee getLogin(Map<String,Object> object) throws Exception;
	/**
	 * HY移动端获取用户游戏账号
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public EnterpriseEmployee getPhoneLogin(EnterpriseEmployee object) throws Exception;
	
	/**
	 * 修改用户在线状态
	 * @return
	 */
	@DataSource("master")
	public int updateOnlineStatus(EnterpriseEmployee ee) throws Exception;
	
	/**
	 * 根据登录名与密码返回员工对象
	 * @param map
	 * @return List<EnterpriseEmployee>
	 */
	@DataSource("slave")
	public List<EnterpriseEmployee> getThisLoginEmployeeMsg(Map<String, Object> map) throws  Exception;
	
	/**
	 * 获取企业下的用户信息
	 * @param object
	 * @return
	 * @throws Exception
	 */
	 @DataSource("slave")
	 public EnterpriseEmployee takeEnterpriseEmployee(Map<String,Object> object) throws Exception;
	
	 /**
	  * 修改登录密码
	  */
	 @DataSource("master")
	 public int updatePassword(Map<String, Object> map)throws Exception;
	 
	 /**
	  * 修改资金密码
	  */
	 @DataSource("master")
	 public int updateCapital(Map<String, Object> map)throws Exception;
	 
	 /**
	  * 修改联系方式
	  */
	 @DataSource("master")
	 public int updateInfo(Map<String, Object> map)throws Exception;
	 
	/**
	 * 用户登录密码与取款密码重置方法
	 * @param Map<String, Object>
	 */
	@DataSource("master")
	public int tc_restPassword(Map<String,Object> map) throws Exception;
	
	/**
	 * 设置分红和占成
	 * @param ee 必须参数 "employeecode","gametype","categorytype","bonus"
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int tc_setDividendShare(EnterpriseEmployee ee) throws Exception;
	
	/**
	 * 通过函数获取用户编码与所有下级代理编码
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public String call_ufnTakeTeamAgent(String employeecode) throws Exception ;
	
	
	/**
	 * 通过函数回溯用户上级股东编码
	 * @param employeecode
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public String call_UfnTakeShareholder(String employeecode) throws Exception ;
	
	/**
	 * 通过函数查找用户的上级用户编码，中间以逗号分隔
	 * @param employeecode
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public String call_UfnTakeRecursionUpperLevel(String employeecode) throws Exception ;
	
	/**
	 * 通过存储过程获取用户编码与所有下级代理编码和代理父编码
	 * @param employeecode
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<EmployeeRelation> call_uspTakeTeamAgent(String employeecode) throws Exception ;
	
	/**
	 * 用户活跃度过程调用
	 * @param paramObj
	 * @return
	 */
	@DataSource("slave")
	public List<EnterpriseEmployee> call_employeeActivityReport(Map<String, Object> paramObj)throws Exception;
	
	/**
	 * 用户注册报表统计查询
	 * @param paramObj
	 * @return
	 */
	@DataSource("slave")
	public List<EnterpriseEmployee> call_userRegisteredReport(Map<String, Object> paramObj)throws Exception;
	
	/**
	 * 用户输赢统计查询
	 * @param paramObj
	 * @return
	 */
	@DataSource("slave")
	public List<EnterpriseEmployee> call_userLoseWinCount(Map<String, Object> paramObj)throws Exception;
	
	/**
	 * 查询下级会员
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<EnterpriseEmployee> call_userDownMemberDetail(Map<String, Object> paramObj)throws Exception;
	
	/**
	 * 游戏报表统计查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<EnterpriseEmployee> call_userGameLoseWinCount(Map<String, Object> paramObj)throws Exception;
	
	/**
	 * 游戏报表统计查询（新的。根据员工编码查询该团队的所有会员汇总金额数据）
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<EnterpriseEmployee> call_UserGameAgentReport(Map<String, Object> paramObj) throws Exception;
	
	/**
	 * 游戏报表统计查询（新的。根据员工编码查询该代理的所有直线会员）
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<EnterpriseEmployee> call_UserGameMemberReport(Map<String, Object> paramObj) throws Exception;
	
	/**
	 * 根据员工编码统计个人汇总数据
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<EnterpriseEmployee> call_UserGameMemberReportOne(Map<String, Object> paramObj) throws Exception;
	
	/**
	 * 根据员工编码统计个人涉及活动金额数据
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<EnterpriseEmployee> call_UserActivityMemberReportOne(Map<String, Object> paramObj) throws Exception;
	
	/**
	 * 游戏报表下级会员统计查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<EnterpriseEmployee> call_userGameDownMemberDetail(Map<String, Object> paramObj)throws Exception;
	
	/**
	 * 游戏报表下级会员统计查询（新的）
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<EnterpriseEmployee> call_userGameDownMemberDetailNew(Map<String, Object> paramObj)throws Exception;
	
	/**
	 * 利润报表统计
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<EnterpriseEmployee> call_userProfitReportCount(Map<String, Object> paramObj)throws Exception;
	/**
	 * 利润报表统计之金额
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public Map<String, Object> queryUserProfitReportCountMoney(Map<String, Object> paramObj) throws Exception ;
	
	/**
	 * 利润报表直线会员查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<EnterpriseEmployee> call_userProfitReportDownMemberDetail(Map<String, Object> paramObj)throws Exception;
	
	/**
	 * 代理贡献排名查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<EnterpriseEmployee> call_agentContributionRankingReport(Map<String, Object> paramObj)throws Exception;
	
	/**
	 * 代理贡献明细查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<EnterpriseEmployee> call_agentContributionDetail(Map<String, Object> paramObj)throws Exception;
	
	/**
	 * 用户来源统计
	 * @param paramObj
	 * @return
	 */
	@DataSource("slave")
	public List<EnterpriseEmployee> call_userDomainLink(Map<String, Object> paramObj);
	
	/**
	 * 统计企业的所有会员在指定时间内的有效投注额，按人员分组（按大类）
	 * @param paramObj
	 * @return
	 */
	@DataSource("slave")
	public List<EnterpriseEmployee> call_uspActivityButmoneyEnterprisecodeBigType(Map<String, Object> paramObj)throws Exception;
	
	/**
	 * 统计企业的所有会员在指定时间内的有效投注额，按人员分组（按小类）
	 * @param paramObj
	 * @return
	 */
	@DataSource("slave")
	public List<EnterpriseEmployee> call_uspActivityButmoneyEnterprisecodeSmallType(Map<String, Object> paramObj)throws Exception;
	
	/**
	 * 根据编码查下级代理和下级会员总数
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public EnterpriseEmployee call_Count(String employeecode)throws Exception;
	/**
	 * 根据编码查下级代理和下级会员余额
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public EnterpriseEmployee call_Balances(String parentemployeecode)throws Exception ;
}
