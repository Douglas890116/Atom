package com.maven.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maven.base.dao.impl.BaseDaoImpl;
import com.maven.dao.EnterpriseEmployeeDao;
import com.maven.entity.EmployeeRelation;
import com.maven.entity.EnterpriseEmployee;

@Repository
public class EnterpriseEmployeeDaoImpl extends BaseDaoImpl<EnterpriseEmployee> 
	implements EnterpriseEmployeeDao{
	/**
	 * 分页参数
	 * @param object
	 * @return List<EnterpriseEmployee>
	 */
	public List<EnterpriseEmployee> findEmployee(Object object) {
		List<EnterpriseEmployee> list =  getSqlSession().selectList("EnterpriseEmployee.pageParameter",object);
		return list;
	}
	/**
	 * 分页参数
	 * @param object
	 * @return List<EnterpriseEmployee>
	 */
	public List<EnterpriseEmployee> findEmployeeAgent(Object object) {
		List<EnterpriseEmployee> list =  getSqlSession().selectList("EnterpriseEmployee.pageParameterAgent",object);
		return list;
	}
	
	/**
	 * 查询总记录条数
	 * @param object
	 * @return List<EnterpriseEmployee>
	 */
	public int findEmployeeCount(Object object) {
		Long count = getSqlSession().selectOne("EnterpriseEmployee.findCount",object);
		return count==null?0:count.intValue();
	}
	
	/**
	 * 保存用户注册
	 * @param enterpriseEmployee
	 */
	public void saveEnterpriseEmployee(EnterpriseEmployee enterpriseEmployee) {
		getSqlSession().insert("EnterpriseEmployee.save",enterpriseEmployee);
	}
	/**
	 * 根据员工编码删除
	 * @param request
	 */
	public void deleteEmployee(String employeecode) {
		int i = getSqlSession().delete("EnterpriseEmployee.deleteEmployee",employeecode);
		System.err.println(i);
	}
	/**
	 * 删除选中的一条或者多条数据
	 * @param String[] array
	 */
	public void deleteSelectEmployee(String[] array) {
		
		getSqlSession().delete("EnterpriseEmployee.deleteSelectEmployee", array);
	}
	/**
	 * 禁用选中的一条或者多条数据
	 * @param String[] array
	 */
	public void disableSelectEmployee(String[] array) {
		
		getSqlSession().update("EnterpriseEmployee.disableSelectEmployee", array);
	}
	/**
	 * 启用选中的一条或者多条数据
	 * @param String[] array
	 */
	public void activateSelectEmployee(String[] array) {
		
		getSqlSession().update("EnterpriseEmployee.activateSelectEmployee", array);
	}
	
	/**
	 * 根据会员编码批量查找会员列表
	 * @param String[] array
	 */
	public List<EnterpriseEmployee> findEmployeeByCodes(String[] array) throws Exception {
		return getSqlSession().selectList("EnterpriseEmployee.findEmployeeByCodes", array);
	}
	
	/**
	 * 批量修改员工等级
	 * @param Map<String, Object> map
	 */
	public void updateEmployeeLevel(Map<String, Object> map) {
		getSqlSession().update("EnterpriseEmployee.updateEmployeeLevel", map);
	}
	
	/**
	 * 批量修改会员信用评级
	 * @param Map<String, Object> map
	 */
	public void updateMemberCreditRating(Map<String, Object> map) {
		getSqlSession().update("EnterpriseEmployee.updateMemberCreditRating", map);
	}
	
	/**
	 * 修改密码
	 */
	public int updatePassword(Map<String, Object> map){
		return getSqlSession().update("EnterpriseEmployee.updatePassword", map);
	}
	
	@Override
	public int updateCapital(Map<String, Object> map) {
		return getSqlSession().update("EnterpriseEmployee.updateCapitalpwd", map);
	}
	
	/**
	 * 修改用户在线状态
	 */
	@Override
	public int updateOnlineStatus(EnterpriseEmployee ee) {
		return getSqlSession().update("EnterpriseEmployee.updateOnlineStatus", ee);
	}
	
	/**
	 * 修改联系方式
	 */
	public int updateInfo(Map<String, Object> map){
		return getSqlSession().update("EnterpriseEmployee.updateInfo", map);
	}
	
	/**
	 * 用户登录密码与取款密码重置方法
	 * @param Map<String, Object>
	 */
	@Override
	public int restPassword(Map<String, Object> map) {
		return getSqlSession().update("EnterpriseEmployee.restPassword", map);
	}
	
	/**
	 * 判断用户名是否存在
	 * @param loginaccount
	 * @return int
	 */
	public List<EnterpriseEmployee> employeeIsExist(String loginaccount) {
		return getSqlSession().selectList("EnterpriseEmployee.employeeIsExist", loginaccount);
	}
	/**
	 * 验证账号是否属于当前登录用户的下级
	 * @param map
	 * @return List<EnterpriseEmployee>
	 */
	public List<EnterpriseEmployee> employeeIsExist(Map<String, Object> map){
		return getSqlSession().selectList("EnterpriseEmployee.findAllDownEmployee", map);
	}
	/**
	 * 根据权限组查询该权限组下的所有用户
	 * @param Map
	 * @return List<EnterpriseEmployee>
	 */
	@Override
	public List<EnterpriseEmployee> queryNotPermissiongroupEmployee(Map<String, Object> map) {
		return getSqlSession().selectList("EnterpriseEmployee.queryNotPermissiongroupEmployee", map);
	}
	/**
	 * 根据权限组统计该权限组下的所有用户数量
	 * @param map
	 * @return int 
	 */
	@Override
	public int queryNotPermissiongroupEmployeeCount(Map<String, Object> map) {
		return getSqlSession().selectList("EnterpriseEmployee.queryNotPermissiongroupEmployeeCount", map).size();
	}
	/**
	 * 批量权限删除,根据权限组编码查询有该权限的所有用户
	 * @param request
	 * @return
	 */
	@Override
	public List<EnterpriseEmployee> queryHavePermissiongroupEmployee(Map<String, Object> map) {
		return  getSqlSession().selectList("EnterpriseEmployee.queryHavePermissiongroupEmployee", map);
	}
	/**
	 * 统计拥有该权限的所有用户
	 * @param request
	 * @return
	 */
	@Override
	public int queryHavePermissiongroupEmployeeCount(Map<String, Object> map) {
		return getSqlSession().selectList("EnterpriseEmployee.queryHavePermissiongroupEmployeeCount", map).size();
	}
	/**
	 * 根据登录名与密码返回员工对象
	 * @param map
	 * @return List<EnterpriseEmployee>
	 */
	@Override
	public List<EnterpriseEmployee> getThisLoginEmployeeMsg(Map<String, Object> map) {
		return getSqlSession().selectList("EnterpriseEmployee.getThisLoginEmployeeMsg", map);
	}
	
	/**
	 * 代理查询
	 * @param object
	 * @return List<EnterpriseEmployee>
	 */
	@Override
	public List<EnterpriseEmployee> queryAgent(Map<String, Object> map) {
		return getSqlSession().selectList("EnterpriseEmployee.agentQuery", map);
	}
	/**
	 * 代理查询统计
	 * @param object
	 * @return int
	 */
	@Override
	public int queryAgentCount(Map<String, Object> map) {
		Long count =  getSqlSession().selectOne("EnterpriseEmployee.agentQueryCount",map);
		return count==null?0:count.intValue();
	}
	
	@Override
	public List<EnterpriseEmployee> takeDerictly(Map<String, Object> object) throws Exception {
		return super.selectAll("EnterpriseEmployee.takeDerictly", object);
	}
	@Override
	public int takeDerictlyCount(Map<String, Object> object) throws Exception {
		return super.selectAllCount("EnterpriseEmployee.takeDerictlyCount", object);
	}
	
	@Override
	public int setDividendShare(Object object) throws Exception {
		return getSqlSession().update("EnterpriseEmployee.setDividendShare", object);
	}
	@Override
	public List<EmployeeRelation> uspTakeTeamAgent(String employeecode) throws Exception {
		return getSqlSession().selectList("EnterpriseEmployee.usp_takeTeamAgent",employeecode);
	}
	/**
	 * 通过函数查找用户的上级用户编码，中间以逗号分隔
	 * @param employeecode
	 * @return
	 * @throws Exception
	 */
	public String ufnTakeRecursionUpperLevel(String employeecode) throws Exception {
		return getSqlSession().selectOne("EnterpriseEmployee.ufn_takeRecursionUpperLevel",employeecode);
	}
	
	@Override
	public String ufnTakeTeamAgent(String employeecode) throws Exception {
		return getSqlSession().selectOne("EnterpriseEmployee.ufn_takeTeamAgent",employeecode);
	}
	
	@Override
	public String ufnTakeShareholder(String employeecode) throws Exception {
		return getSqlSession().selectOne("EnterpriseEmployee.ufn_takeShareholder",employeecode);
	}
	
	/**
	 * 用户活跃度过程调用
	 * @param paramObj
	 * @return
	 */
	@Override
	public List<EnterpriseEmployee> employeeActivityReport(Map<String, Object> paramObj) {
		return getSqlSession().selectList("EnterpriseEmployee.employeeActivityReport", paramObj);
	}
	/**
	 * 用户注册报表统计查询
	 * @param paramObj
	 * @return
	 */
	@Override
	public List<EnterpriseEmployee> queryUserRegisteredReport(Map<String, Object> paramObj) {
		return getSqlSession().selectList("EnterpriseEmployee.queryUserRegisteredReport", paramObj);
	}
	/**
	 * 用户输赢统计查询
	 * @param paramObj
	 * @return
	 */
	@Override
	public List<EnterpriseEmployee> queryUserLoseWinCount(Map<String, Object> paramObj) {
		return getSqlSession().selectList("EnterpriseEmployee.queryUserLoseWinCount", paramObj);
	}
	
	/**
	 * 查询下级会员
	 */
	@Override
	public List<EnterpriseEmployee> queryUserDownMemberDetail(Map<String, Object> paramObj) {
		return getSqlSession().selectList("EnterpriseEmployee.queryUserDownMemberDetail", paramObj);
	}
	
	/**
	 * 游戏报表统计查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<EnterpriseEmployee> queryUserGameLoseWinCount(Map<String, Object> paramObj) {
		return getSqlSession().selectList("EnterpriseEmployee.queryUserGameLoseWinCount",paramObj);
	}
	
	/**
	 * 游戏报表统计查询（新的。根据员工编码查询该团队的所有会员汇总金额数据）
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<EnterpriseEmployee> queryUserGameAgentReport(Map<String, Object> paramObj) {
		return getSqlSession().selectList("EnterpriseEmployee.queryUserGameAgentReport",paramObj);
	}
	
	/**
	 * 游戏报表统计查询（新的。根据员工编码查询该代理的所有直线会员）
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<EnterpriseEmployee> queryUserGameMemberReport(Map<String, Object> paramObj) {
		return getSqlSession().selectList("EnterpriseEmployee.queryUserGameMemberReport",paramObj);
	}
	
	/**
	 * 根据员工编码统计个人汇总数据
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	public List<EnterpriseEmployee> queryUserGameMemberReportOne(Map<String, Object> paramObj)  {
		return getSqlSession().selectList("EnterpriseEmployee.queryUserGameMemberReportOne",paramObj);
	}
	
	/**
	 * 根据员工编码统计个人涉及活动金额数据
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	public List<EnterpriseEmployee> queryUserActivityMemberReportOne(Map<String, Object> paramObj) {
		return getSqlSession().selectList("EnterpriseEmployee.queryUserActivityMemberReportOne",paramObj);
	}
	
	/**
	 * 游戏报表下级会员统计查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<EnterpriseEmployee> queryUserGameDownMemberDetail(Map<String, Object> paramObj) {
		return getSqlSession().selectList("EnterpriseEmployee.queryUserGameDownMemberDetail",paramObj);
	}
	
	/**
	 * 游戏报表下级会员统计查询（新的）
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	public List<EnterpriseEmployee> queryuserGameDownMemberDetailNew(Map<String, Object> paramObj) {
		return getSqlSession().selectList("EnterpriseEmployee.queryUserGameDownMemberDetailNew",paramObj);
	}
	
	/**
	 * 利润报表统计
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<EnterpriseEmployee> queryUserProfitReportCount(Map<String, Object> paramObj) {
		return getSqlSession().selectList("EnterpriseEmployee.queryUserProfitReportCount", paramObj);
	}
	/**
	 * 利润报表统计之金额
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> queryUserProfitReportCountMoney(Map<String, Object> paramObj) {
		return getSqlSession().selectOne("EnterpriseEmployee.queryUserProfitReportCountMoney", paramObj);
	}
	
	/**
	 * 利润报表直线会员查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<EnterpriseEmployee> queryUserProfitReportDownMemberDetail(Map<String, Object> paramObj) {
		return getSqlSession().selectList("EnterpriseEmployee.queryUserProfitReportDownMemberDetail", paramObj);
	}
	
	/**
	 * 代理贡献排名查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<EnterpriseEmployee> queryAgentContributionRankingReport(Map<String, Object> paramObj) {
		return getSqlSession().selectList("EnterpriseEmployee.queryAgentContributionRankingReport",paramObj);
	}
	
	/**
	 * 代理贡献明细查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<EnterpriseEmployee> queryAgentContributionDetail(Map<String, Object> paramObj) {
		return getSqlSession().selectList("EnterpriseEmployee.queryAgentContributionDetail",paramObj);
	}
	
	/**
	 * 用户来源统计
	 * @param paramObj
	 * @return
	 */
	@Override
	public List<EnterpriseEmployee> queryUserDomainLink(Map<String, Object> paramObj) {
		return getSqlSession().selectList("EnterpriseEmployee.queryUserDomainLink", paramObj);
	}
	
	/**
	 * 统计企业的所有会员在指定时间内的有效投注额，按人员分组（按大类）
	 * @param paramObj
	 * @return
	 */
	public List<EnterpriseEmployee> queryUspActivityButmoneyEnterprisecodeBigType(Map<String, Object> paramObj) {
		return getSqlSession().selectList("EnterpriseEmployee.queryUspActivityButmoneyEnterprisecodeBigType", paramObj);
	}
	/**
	 * 统计企业的所有会员在指定时间内的有效投注额，按人员分组（按小类）
	 * @param paramObj
	 * @return
	 */
	public List<EnterpriseEmployee> queryUspActivityButmoneyEnterprisecodeSmallType(Map<String, Object> paramObj) {
		return getSqlSession().selectList("EnterpriseEmployee.queryUspActivityButmoneyEnterprisecodeSmallType", paramObj);
	}
	
	/**
	 * 根据编码查下级代理和下级会员总数
	 * @param paramObj
	 * @return
	 */
	public EnterpriseEmployee queryCount(String employeecode) throws Exception {
		return getSqlSession().selectOne("EnterpriseEmployee.findCountByType", employeecode);
	}
	/**
	 * 根据编码查下级代理和下级会员余额
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	public EnterpriseEmployee queryBalances(String parentemployeecode)throws Exception {
		return getSqlSession().selectOne("EnterpriseEmployee.findBalances", parentemployeecode);
	}
}
