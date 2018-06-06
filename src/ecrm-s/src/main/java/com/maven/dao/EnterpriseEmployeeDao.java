package com.maven.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.BaseDao;
import com.maven.entity.EmployeeRelation;
import com.maven.entity.EnterpriseEmployee;

@Repository
public interface EnterpriseEmployeeDao extends BaseDao<EnterpriseEmployee>{
	/**
	 * 分页参数
	 * @param object
	 * @return List<EnterpriseEmployee>
	 */
	public List<EnterpriseEmployee> findEmployee(Object object);
	/**
	 * 分页参数
	 * @param object
	 * @return List<EnterpriseEmployee>
	 */
	public List<EnterpriseEmployee> findEmployeeAgent(Object object);
	
	/**
	 * 查询总记录条数
	 * @param object
	 * @return List<EnterpriseEmployee>
	 */
	public int findEmployeeCount(Object object);
	
	/**
	 * 保存用户注册
	 * @param enterpriseEmployee
	 */
	public void saveEnterpriseEmployee(EnterpriseEmployee enterpriseEmployee);
	
	
	/**
	 * 根据员工编码删除
	 * @param request
	 */
	public void deleteEmployee(String employeecode);
	/**
	 * 删除选中的一条或者多条数据
	 * @param String[] array
	 */
	public void deleteSelectEmployee(String[] array);
	/**
	 * 禁用选中的一条或者多条数据
	 * @param String[] array
	 */
	public void disableSelectEmployee(String[] array);
	/**
	 * 启用选中的一条或者多条数据
	 * @param String[] array
	 */
	public void activateSelectEmployee(String[] array);
	
	/**
	 * 根据会员编码批量查找会员列表
	 * @param String[] array
	 */
	public List<EnterpriseEmployee> findEmployeeByCodes(String[] array) throws Exception;
	
	/**
	 * 批量修改员工等级
	 * @param Map<String, Object> map
	 */
	public void updateEmployeeLevel(Map<String, Object> map);
	
	/**
	 * 批量修改会员评级
	 * @param Map<String, Object> map
	 */
	public void updateMemberCreditRating(Map<String, Object> map);
	/**
	 * 判断用户名是否存在
	 * @param loginaccount
	 * @return int
	 */
	public List<EnterpriseEmployee> employeeIsExist(String loginaccount);
	/**
	 * 验证账号是否属于当前登录用户的下级
	 * @param map
	 * @return List<EnterpriseEmployee>
	 */
	public List<EnterpriseEmployee> employeeIsExist(Map<String, Object> map);
	
	/**
	 * 根据权限组查询该权限组下的所有用户
	 * @param Map
	 * @return List<EnterpriseEmployee>
	 */
	public List<EnterpriseEmployee> queryNotPermissiongroupEmployee(Map<String, Object> map);
	/**
	 * 根据权限组统计该权限组下的所有用户数量
	 * @param map
	 * @return int 
	 */
	public int queryNotPermissiongroupEmployeeCount(Map<String, Object> map);
	/**
	 * 批量权限删除,根据权限组编码查询有该权限的所有用户
	 * @param request
	 * @return
	 */
	public List<EnterpriseEmployee> queryHavePermissiongroupEmployee(Map<String, Object> map);
	/**
	 * 统计拥有该权限的所有用户
	 * @param request
	 * @return
	 */
	public int queryHavePermissiongroupEmployeeCount(Map<String, Object> map);
	/**
	 * 根据登录名与密码返回员工对象
	 * @param map
	 * @return List<EnterpriseEmployee>
	 */
	public List<EnterpriseEmployee> getThisLoginEmployeeMsg(Map<String, Object> map);
	
	/**
	 * 修改密码
	 */
	public int updatePassword(Map<String, Object> map);
	
	/**
	 * 用户登录密码与取款密码重置方法
	 * @param Map<String, Object>
	 */
	public int restPassword(Map<String, Object> map);
	
	/**
	 * 修改用户在线状态
	 * @param ee
	 * @return
	 */
	public int updateOnlineStatus(EnterpriseEmployee ee);
	
	/**
	 * 修改联系方式
	 */
	public int updateInfo(Map<String, Object> map);
	
	/**
	 * 代理查询
	 * @param object
	 * @return List<EnterpriseEmployee>
	 */
	public List<EnterpriseEmployee> queryAgent(Map<String, Object> map);
	/**
	 * 代理查询统计
	 * @param object
	 * @return int
	 */
	public int queryAgentCount(Map<String, Object> map);
	
	/**
	 * 获取直属上级与直属下级
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<EnterpriseEmployee> takeDerictly(Map<String,Object> object) throws Exception;
	
	/**
	 * 获取直属上级与直属下级总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int takeDerictlyCount(Map<String,Object> object) throws Exception;
	
	/**
	 * 设置分红和占成
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int setDividendShare(Object object) throws Exception;
	
	
	/**
	 * 通过函数获取用户编码与所有下级代理编码
	 * @return
	 * @throws Exception
	 */
	public String ufnTakeTeamAgent(String employeecode) throws Exception ;
	
	/**
	 * 通过函数回溯用户上级股东编码
	 * @param employeecode
	 * @return
	 * @throws Exception
	 */
	public String ufnTakeShareholder(String employeecode) throws Exception ;
	
	/**
	 * 通过存储过程获取用户编码与所有下级代理编码和代理父编码
	 * @param employeecode
	 * @return
	 * @throws Exception
	 */
	public List<EmployeeRelation> uspTakeTeamAgent(String employeecode) throws Exception ;
	
	/**
	 * 通过函数查找用户的上级用户编码，中间以逗号分隔
	 * @param employeecode
	 * @return
	 * @throws Exception
	 */
	public String ufnTakeRecursionUpperLevel(String employeecode) throws Exception ;
	 /**
	  * 修改资金密码
	  */
	public int updateCapital(Map<String, Object> map);

	/**
	 * 用户活跃度过程调用
	 * @param paramObj
	 * @return
	 */
	public List<EnterpriseEmployee> employeeActivityReport(Map<String, Object> paramObj);
	/**
	 * 用户注册报表统计查询
	 * @param paramObj
	 * @return
	 */
	public List<EnterpriseEmployee> queryUserRegisteredReport(Map<String, Object> paramObj);
	
	/**
	 * 用户输赢统计查询
	 * @param paramObj
	 * @return
	 */
	public List<EnterpriseEmployee> queryUserLoseWinCount(Map<String, Object> paramObj);
	
	/**
	 * 查询下级会员
	 * @param paramObj
	 * @return
	 */
	public List<EnterpriseEmployee> queryUserDownMemberDetail(Map<String, Object> paramObj);
	
	/**
	 * 游戏报表统计查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	public List<EnterpriseEmployee> queryUserGameLoseWinCount(Map<String, Object> paramObj);
	
	/**
	 * 游戏报表统计查询（新的。根据员工编码查询该团队的所有会员汇总金额数据）
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	public List<EnterpriseEmployee> queryUserGameAgentReport(Map<String, Object> paramObj);
	
	/**
	 * 游戏报表统计查询（新的。根据员工编码查询该代理的所有直线会员）
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	public List<EnterpriseEmployee> queryUserGameMemberReport(Map<String, Object> paramObj);
	
	/**
	 * 根据员工编码统计个人汇总数据
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	public List<EnterpriseEmployee> queryUserGameMemberReportOne(Map<String, Object> paramObj) ;
	
	/**
	 * 根据员工编码统计个人涉及活动金额数据
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	public List<EnterpriseEmployee> queryUserActivityMemberReportOne(Map<String, Object> paramObj) ;
	
	/**
	 * 游戏报表下级会员统计查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	public List<EnterpriseEmployee> queryUserGameDownMemberDetail(Map<String, Object> paramObj);
	
	/**
	 * 游戏报表下级会员统计查询（新的）
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	public List<EnterpriseEmployee> queryuserGameDownMemberDetailNew(Map<String, Object> paramObj);
	
	/**
	 * 利润报表统计
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	public List<EnterpriseEmployee> queryUserProfitReportCount(Map<String, Object> paramObj);
	/**
	 * 利润报表统计之金额
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryUserProfitReportCountMoney(Map<String, Object> paramObj) ;
	
	/**
	 * 利润报表直线会员查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	public List<EnterpriseEmployee> queryUserProfitReportDownMemberDetail(Map<String, Object> paramObj);
	
	/**
	 * 代理贡献排名查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	public List<EnterpriseEmployee> queryAgentContributionRankingReport(Map<String, Object> paramObj);
	
	/**
	 * 代理贡献明细查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	public List<EnterpriseEmployee> queryAgentContributionDetail(Map<String, Object> paramObj);
	
	/**
	 * 用户来源统计
	 * @param paramObj
	 * @return
	 */
	public List<EnterpriseEmployee> queryUserDomainLink(Map<String, Object> paramObj);
	
	/**
	 * 统计企业的所有会员在指定时间内的有效投注额，按人员分组（按大类）
	 * @param paramObj
	 * @return
	 */
	public List<EnterpriseEmployee> queryUspActivityButmoneyEnterprisecodeBigType(Map<String, Object> paramObj);
	
	/**
	 * 统计企业的所有会员在指定时间内的有效投注额，按人员分组（按小类）
	 * @param paramObj
	 * @return
	 */
	public List<EnterpriseEmployee> queryUspActivityButmoneyEnterprisecodeSmallType(Map<String, Object> paramObj) throws Exception;
	
	/**
	 * 根据编码查下级代理和下级会员总数
	 * @param paramObj
	 * @return
	 */
	public EnterpriseEmployee queryCount(String employeecode) throws Exception;
	
	/**
	 * 根据编码查下级代理和下级会员余额
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	public EnterpriseEmployee queryBalances(String parentemployeecode)throws Exception ;
	
}
