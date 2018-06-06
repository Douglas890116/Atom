package com.maven.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.base.dao.DataSource;
import com.maven.dao.EnterpriseEmployeeDao;
import com.maven.entity.Baccarath5Balance;
import com.maven.entity.EmployeeGamecataloyBonus;
import com.maven.entity.EmployeeRelation;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeCapitalAccount;
import com.maven.entity.EnterpriseEmployeeType;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.service.Baccarath5BalanceService;
import com.maven.service.BrandDomainService;
import com.maven.service.EmployeeGamecataloyBonusService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.EnterpriseEmployeeInformationService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.util.ActivityUtils.ActivityCheckMessage;
import com.maven.util.AttrCheckout;
import com.maven.util.BeanToMapUtil;
import com.maven.utility.ClassUtil;

@Service
public class EnterpriseEmployeeServiceImpl extends BaseServiceImpl<EnterpriseEmployee> 
	implements EnterpriseEmployeeService{
	
	/** 员工 */
	@Autowired
	private EnterpriseEmployeeDao enterpriseEmployeeDao;
	
	/** 会员游戏洗码  */
	@Autowired
	private EmployeeGamecataloyBonusService employeeGamecataloyBonusService;
	
	/**  员工的资金账户 */
	@Autowired
	private EnterpriseEmployeeCapitalAccountService enterpriseEmployeeCapitalAccountService;
	
//	@Autowired
//	private EmployeeMappingMenuService employeeMappingMenuService;
	
	@Autowired
	private EnterpriseEmployeeInformationService enterpriseEmployeeInformationService;
	
	@Autowired
	private BrandDomainService brandDomainService;
	
	@Autowired
	private Baccarath5BalanceService baccarath5BalanceService;
	
	
	@Override
	public BaseDao<EnterpriseEmployee> baseDao() {
		return enterpriseEmployeeDao;
	}

	@Override
	public Class<EnterpriseEmployee> getClazz() {
		return EnterpriseEmployee.class;
	}
	
	/**
	 * 分页查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	public List<EnterpriseEmployee> selectAll(Map<String, Object> paramObj)throws Exception {
		return enterpriseEmployeeDao.selectAll(ClassUtil.getMapId(getClazz(), new Throwable()), paramObj);
	}
	/**
	 * 分页查询记录总数
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	public int selectAllCount(Map<String, Object> paramObj)throws Exception {
		return enterpriseEmployeeDao.selectAllCount(ClassUtil.getMapId(getClazz(), new Throwable()), paramObj);
	}
	
	/**
	 * 查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	public List<EnterpriseEmployee> select(EnterpriseEmployee paramObj)throws Exception {
		return enterpriseEmployeeDao.select(ClassUtil.getMapId(getClazz(), new Throwable()), paramObj);
	}
	
	/**
	 * 会员信息查询
	 * @param object
	 * @return List<EnterpriseEmployee>
	 */
	public List<EnterpriseEmployee> findEmployee(Map<String,Object> object) throws  Exception{
		return enterpriseEmployeeDao.findEmployee(object);
	}
	/**
	 * 会员信息查询
	 * @param object
	 * @return List<EnterpriseEmployee>
	 */
	public List<EnterpriseEmployee> findEmployeeAgent(Map<String,Object> object) throws  Exception{
		return enterpriseEmployeeDao.findEmployeeAgent(object);
	}
	
	
	/**
	 * 会员总记录数查询
	 * @param object
	 * @return List<EnterpriseEmployee>
	 */
	public int findEmployeeCount(Map<String,Object> object) throws  Exception{
		return enterpriseEmployeeDao.findEmployeeCount(object);
	}
	
	/**
	 * 代理查询
	 * @param object
	 * @return List<EnterpriseEmployee>
	 */
	@Override
	public List<EnterpriseEmployee> queryAgent(Map<String, Object> object) throws Exception {
		return enterpriseEmployeeDao.queryAgent(object);
	}
	/**
	 * 代理查询
	 * @param object
	 * @return int
	 */
	@Override
	public int queryAgentCount(Map<String, Object> object) throws Exception {
		return enterpriseEmployeeDao.queryAgentCount(object);
	}
	
	@Override
	public List<EnterpriseEmployee> takeDerictly(Map<String, Object> object) throws Exception {
		return enterpriseEmployeeDao.takeDerictly(AttrCheckout.checkout(object, false, new String[]{"selfecode","superiorecode"}));
	}

	@Override
	public int takeDerictlyCount(Map<String, Object> object) throws Exception {
		return enterpriseEmployeeDao.takeDerictlyCount(AttrCheckout.checkout(object, false, new String[]{"selfecode","superiorecode"}));
	}
	
	private List<EnterpriseEmployee> takeByLoginAccount(String loginaccount)throws Exception {
		EnterpriseEmployee __ee = new EnterpriseEmployee();
		__ee.setLoginaccount(loginaccount);
		return super.select(__ee);
	}
	
	/**
	 * 代理/用户类型账号
	 * @param enterpriseEmployee
	 */
	@Override
	public void tc_saveUser(EnterpriseEmployee enterpriseEmployee,List<EmployeeGamecataloyBonus> bonus,boolean isDirectly) throws Exception{
		//保存用户
		enterpriseEmployee = AttrCheckout.checkout(enterpriseEmployee, false, new String[]{"enterprisecode","parentemployeecode","parentemployeeaccount","employeetypecode","loginaccount","loginpassword","fundpassword","displayalias","employeestatus","onlinestatus","dividend","share"});
		List<EnterpriseEmployee> __exit = takeByLoginAccount(enterpriseEmployee.getLoginaccount());
		if(__exit.size()>0)  throw new LogicTransactionRollBackException("用户名已存在");
		enterpriseEmployeeDao.saveEnterpriseEmployee(enterpriseEmployee);
		
		//创建用户的资金帐户
		enterpriseEmployeeCapitalAccountService.tc_saveEmployeeCapitalAccount(
				new EnterpriseEmployeeCapitalAccount(enterpriseEmployee.getEmployeecode(), enterpriseEmployee.getParentemployeecode()));
		
		//保存用户接入平台游戏洗码
		if(bonus != null && bonus.size() > 0 ) {
			for (EmployeeGamecataloyBonus b : bonus) {
				b.setEmployeecode(enterpriseEmployee.getEmployeecode());
				b.setParentemployeecode(enterpriseEmployee.getParentemployeecode());
			}
			employeeGamecataloyBonusService.tc_installEmployeeCategyBonus(bonus);
		}
		
		
		//对代理用户进行授权
		if(!isDirectly
			&&!enterpriseEmployee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.会员.value)){
//			employeeMappingMenuService.tc_agentAuthorization(enterpriseEmployee);//暂时不设置默认权限
		}
		/****
		//创建元宝账户
		if(enterpriseEmployee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.代理.value) ||
				enterpriseEmployee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.信用代理.value)
				) {
			baccarath5BalanceService.addActivityBetRecord(new Baccarath5Balance(
					enterpriseEmployee.getEmployeecode(), enterpriseEmployee.getLoginaccount(), 
					enterpriseEmployee.getEnterprisecode(), enterpriseEmployee.getBrandcode(),
					enterpriseEmployee.getParentemployeecode(), 0.0, new Date()));
		}
		*/
	}
	
	
	/**
	 * 注册企业类型账号
	 * @param enterpriseEmployee
	 */
	@Override
	public void tc_saveEnterprise(EnterpriseEmployee enterpriseEmployee) throws Exception{
		AttrCheckout.checkout(enterpriseEmployee, false, new String[]{"enterprisecode","loginaccount","loginpassword","parentemployeecode","parentemployeeaccount"});
		//保存员工
		List<EnterpriseEmployee> __exit = takeByLoginAccount(enterpriseEmployee.getLoginaccount());
		if(__exit.size()>0)  throw new LogicTransactionRollBackException("用户名已存在");
		enterpriseEmployee.setEmployeetypecode(EnterpriseEmployeeType.Type.企业号.value);
		enterpriseEmployee.setDividend(new BigDecimal("1"));
		enterpriseEmployee.setShare(new BigDecimal("1"));
		enterpriseEmployeeDao.saveEnterpriseEmployee(enterpriseEmployee);
		
		//创建员工的资金帐户
		enterpriseEmployeeCapitalAccountService.tc_saveEmployeeCapitalAccount(
				new EnterpriseEmployeeCapitalAccount(enterpriseEmployee.getEmployeecode(), enterpriseEmployee.getParentemployeecode()));
		
		//设置企业号洗码值
		employeeGamecataloyBonusService.tc_settingEnterpriseBonus(
				new EmployeeGamecataloyBonus(enterpriseEmployee.getEmployeecode(), enterpriseEmployee.getParentemployeecode()));
		
	}
	
	
	
	/**
	 * 根据员工编码删除
	 * @param request
	 */
	public void tc_deleteEmployee(String employeecode) throws Exception{
		//权限映射删除
		//employeeMappingMenuServiceImpl.tc_deleteEmployeePermissionGroup(employeecode);
		//根据员工编码删除对应的所有信息卡信息
		//enterpriseEmployeeInformationService.tc_deleteEmployeeBankInfo(employeecode);
		//员工删除
		enterpriseEmployeeDao.deleteEmployee(employeecode);
	}
	/**
	 * 删除选中的一条或者多条数据
	 * @param String[] array
	 */
	public void tc_deleteSelectEmployee(String[] array) throws Exception{
		//enterpriseEmployeeInformationService.tc_deleteSelectEmployeeBankInfo(array);
		enterpriseEmployeeDao.deleteSelectEmployee(array);
		//TODO 逻辑删除银行卡
		enterpriseEmployeeInformationService.tc_logicDeleteSelectEmployeeBankInfo(array);
		//TODO 逻辑删除代理站点,逻辑删除会员站点
		brandDomainService.tc_logicDeleteByEmployeecode(array);
	}
	/**
	 * 禁用选中的一条或者多条数据
	 * @param String[] array
	 */
	public void tc_disableSelectEmployee(String[] array) throws Exception{
		enterpriseEmployeeDao.disableSelectEmployee(array);
	}
	/**
	 * 启用选中的一条或者多条数据
	 * @param String[] array
	 */
	public void tc_activateSelectEmployee(String[] array) throws Exception{
		enterpriseEmployeeDao.activateSelectEmployee(array);
	}
	
	/**
	 * 根据会员编码批量查找会员列表
	 * @param String[] array
	 */
	public List<EnterpriseEmployee> findEmployeeByCodes(String[] array) throws Exception{
		return enterpriseEmployeeDao.findEmployeeByCodes(array);
	}
	
	/**
	 * 批量修改员工等级
	 * @param Map<String, Object> map
	 */
	public void updateEmployeeLevel(Map<String, Object> map) throws Exception{
		enterpriseEmployeeDao.updateEmployeeLevel(map);
	}
	/**
	 * 批量修改会员信用评级
	 * @param Map<String, Object> map
	 */
	public void updateMemberCreditRating(Map<String, Object> map) throws Exception{
		enterpriseEmployeeDao.updateMemberCreditRating(map);
	}
	/**
	 * 判断用户名是否存在
	 * @param loginaccount
	 * @return int
	 */
	public List<EnterpriseEmployee> queryEmployeeIsExist(String loginaccount) {
		return enterpriseEmployeeDao.employeeIsExist(loginaccount);
	}
	
	/**
	 * 用户登录
	 */
	@Override
	public EnterpriseEmployee getLogin(Map<String, Object> object) throws Exception {
		AttrCheckout.checkout(object, false, new String[]{"enterprisecode","loginaccount","loginpassword"});
		EnterpriseEmployee oee = BeanToMapUtil.convertMap(object, EnterpriseEmployee.class);
		List<EnterpriseEmployee> users = super.select(oee);
		if(users.size()!=1)return null;
		EnterpriseEmployee ee = users.get(0);
		return ee;
	}
	
	/**
	 * GGPoker登陆
	 */
	@Override
	public EnterpriseEmployee getGGPokerLogin(EnterpriseEmployee object) throws Exception {
		AttrCheckout.checkout(object, false, new String[]{"loginaccount"});
		List<EnterpriseEmployee> users = super.select(object);
		if(users.size()!=1)return null;
		EnterpriseEmployee ee = users.get(0);
		return ee;
	}
	
	/**
	 * 根据手机号码查用户
	 */
	@Override
	public EnterpriseEmployee takeEmployeeByPhoneno(EnterpriseEmployee object) throws Exception {
		AttrCheckout.checkout(object, false, new String[]{"phoneno"});
		List<EnterpriseEmployee> users = super.select(object);
		if(users.size()!=1)return null;
		EnterpriseEmployee ee = users.get(0);
		return ee;
	}

	/**
	 * 移动端登陆
	 */
	@Override
	public EnterpriseEmployee getPhoneLogin(EnterpriseEmployee object) throws Exception {
		AttrCheckout.checkout(object, false, new String[]{"loginaccount","loginpassword"});
		List<EnterpriseEmployee> users = super.select(object);
		if(users.size()!=1)return null;
		EnterpriseEmployee ee = users.get(0);
		return ee;
	}

	/**
	 * 修改用户状态
	 */
	@Override
	public int updateOnlineStatus(EnterpriseEmployee ee) throws Exception {
		return enterpriseEmployeeDao.updateOnlineStatus(AttrCheckout.checkout(ee, false, new String[]{"employeecode","onlinestatus"}));
	}

	/**
	 * 获取用户信息
	 */
	@Override
	public EnterpriseEmployee takeEnterpriseEmployee(Map<String,Object> object) throws Exception{
		EnterpriseEmployee e = BeanToMapUtil.convertMap(object, EnterpriseEmployee.class);
		return super.selectFirst(AttrCheckout.checkout(e, true, new String[]{"enterprisecode","employeecode"}));
	}
	
	/**
	 * 根据ID获取员工信息
	 */
	@Override
	public EnterpriseEmployee takeEmployeeByCode(String employeecode) throws Exception{
		if(employeecode==null)  throw new ArgumentValidationException("parameter [ employeecode ] is null"); 
		return super.selectByPrimaryKey(employeecode);
	}

	/**
	 * 根据登录名与密码返回员工对象
	 * @param map
	 * @return List<EnterpriseEmployee>
	 */
	@Override
	public List<EnterpriseEmployee> getThisLoginEmployeeMsg(Map<String, Object> map) throws Exception {
		return enterpriseEmployeeDao.getThisLoginEmployeeMsg(map);
	}
	
	/**
	 * 修改登录密码
	 */
	public int updatePassword(Map<String, Object> map)throws Exception {
		return enterpriseEmployeeDao.updatePassword(map);
	}
	
	/**
	 * 修改资金密码
	 */
	public int updateCapital(Map<String, Object> map)throws Exception {
		return enterpriseEmployeeDao.updateCapital(map);
	}
	
	/**
	 * 修改联系方式
	 */
	public int updateInfo(Map<String, Object> map)throws Exception {
		return enterpriseEmployeeDao.updateInfo(map);
	}
	
	/**
	 * 用户登录密码与取款密码重置方法
	 * @param Map<String, Object>
	 */
	@Override
	public int tc_restPassword(Map<String,Object> map) throws Exception {
		return enterpriseEmployeeDao.restPassword(map);
	}

	/**
	 * 设置分红和占成
	 */
	@Override
	public int tc_setDividendShare(EnterpriseEmployee ee) throws Exception {
		return enterpriseEmployeeDao.setDividendShare(AttrCheckout.checkout(ee, true, new String[]{"employeecode","dividend","share"}));
	}

	@Override
	public List<EmployeeRelation> call_uspTakeTeamAgent(String employeecode) throws Exception {
		return enterpriseEmployeeDao.uspTakeTeamAgent(employeecode);
	}
	
	/**
	 * 通过函数查找用户的上级用户编码，中间以逗号分隔
	 * @param employeecode
	 * @return
	 * @throws Exception
	 */
	@Override
	public String call_UfnTakeRecursionUpperLevel(String employeecode) throws Exception {
		return enterpriseEmployeeDao.ufnTakeRecursionUpperLevel(employeecode);
	}

	@Override
	public String call_UfnTakeShareholder(String employeecode) throws Exception {
		return enterpriseEmployeeDao.ufnTakeShareholder(employeecode);
	}
	
	@Override
	public String call_ufnTakeTeamAgent(String employeecode) throws Exception {
		return enterpriseEmployeeDao.ufnTakeTeamAgent(employeecode);
	}

	@Override
	public List<EnterpriseEmployee> queryEmployeeIsExist(Map<String, Object> map) {
		return enterpriseEmployeeDao.employeeIsExist(map);
	}

	/**
	 * 用户活跃度过程调用
	 * @param paramObj
	 * @return
	 */
	@Override
	public List<EnterpriseEmployee> call_employeeActivityReport(Map<String, Object> paramObj)throws Exception {
		return enterpriseEmployeeDao.employeeActivityReport(paramObj);
	}
	
	/**
	 * 用户注册报表统计查询
	 * @param paramObj
	 * @return
	 */
	@Override
	public List<EnterpriseEmployee> call_userRegisteredReport(Map<String, Object> paramObj)throws Exception {
		return enterpriseEmployeeDao.queryUserRegisteredReport(paramObj);
	}
	
	/**
	 * 用户输赢统计查询
	 */
	@Override
	public List<EnterpriseEmployee> call_userLoseWinCount(Map<String, Object> paramObj)throws Exception {
		return enterpriseEmployeeDao.queryUserLoseWinCount(paramObj);
	}
	
	/**
	 * 查询下级会员
	 */
	@Override
	public List<EnterpriseEmployee> call_userDownMemberDetail(Map<String, Object> paramObj) throws Exception {
		return enterpriseEmployeeDao.queryUserDownMemberDetail(paramObj);
	}
	
	/**
	 * 游戏报表统计查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<EnterpriseEmployee> call_userGameLoseWinCount(Map<String, Object> paramObj) throws Exception {
		return enterpriseEmployeeDao.queryUserGameLoseWinCount(paramObj);
	}
	
	/**
	 * 游戏报表统计查询（新的。根据员工编码查询该团队的所有会员汇总金额数据）
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<EnterpriseEmployee> call_UserGameAgentReport(Map<String, Object> paramObj) throws Exception {
		return enterpriseEmployeeDao.queryUserGameAgentReport(paramObj);
	}
	
	/**
	 * 游戏报表统计查询（新的。根据员工编码查询该代理的所有直线会员）
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<EnterpriseEmployee> call_UserGameMemberReport(Map<String, Object> paramObj) throws Exception {
		return enterpriseEmployeeDao.queryUserGameMemberReport(paramObj);
	}
	
	/**
	 * 根据员工编码统计个人汇总数据
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	public List<EnterpriseEmployee> call_UserGameMemberReportOne(Map<String, Object> paramObj) throws Exception {
		return enterpriseEmployeeDao.queryUserGameMemberReportOne(paramObj);
	}
	
	/**
	 * 根据员工编码统计个人涉及活动金额数据
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	public List<EnterpriseEmployee> call_UserActivityMemberReportOne(Map<String, Object> paramObj) throws Exception {
		return enterpriseEmployeeDao.queryUserActivityMemberReportOne(paramObj);
	}
	
	/**
	 * 游戏报表下级会员统计查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<EnterpriseEmployee> call_userGameDownMemberDetail(Map<String, Object> paramObj) throws Exception {
		return enterpriseEmployeeDao.queryUserGameDownMemberDetail(paramObj);
	}
	
	/**
	 * 游戏报表下级会员统计查询（新的）
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	public List<EnterpriseEmployee> call_userGameDownMemberDetailNew(Map<String, Object> paramObj)throws Exception {
		return enterpriseEmployeeDao.queryuserGameDownMemberDetailNew(paramObj);
	}
	
	/**
	 * 利润报表统计
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<EnterpriseEmployee> call_userProfitReportCount(Map<String, Object> paramObj) throws Exception {
		return enterpriseEmployeeDao.queryUserProfitReportCount(paramObj);
	}
	/**
	 * 利润报表统计之金额
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> queryUserProfitReportCountMoney(Map<String, Object> paramObj) throws Exception {
		return enterpriseEmployeeDao.queryUserProfitReportCountMoney(paramObj);
	}
	/**
	 * 利润报表直线会员查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<EnterpriseEmployee> call_userProfitReportDownMemberDetail(Map<String, Object> paramObj)
			throws Exception {
		return enterpriseEmployeeDao.queryUserProfitReportDownMemberDetail(paramObj);
	}
	
	/**
	 * 代理贡献排名查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<EnterpriseEmployee> call_agentContributionRankingReport(Map<String, Object> paramObj) throws Exception {
		return enterpriseEmployeeDao.queryAgentContributionRankingReport(paramObj);
	}
	
	/**
	 * 代理贡献明细查询
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<EnterpriseEmployee> call_agentContributionDetail(Map<String, Object> paramObj) throws Exception {
		return enterpriseEmployeeDao.queryAgentContributionDetail(paramObj);
	}
	
	/**
	 * 用户来源统计
	 * @param paramObj
	 * @return
	 */
	@Override
	public List<EnterpriseEmployee> call_userDomainLink(Map<String, Object> paramObj) {
		return enterpriseEmployeeDao.queryUserDomainLink(paramObj);
	}

	/**
	 * 参与活动时根据用户名获取用户，无用户时返还错误信息
	 */
	@Override
	public EnterpriseEmployee queryEmployeeByLoginaccount(String loginaccount) throws Exception {
		List<EnterpriseEmployee> ee_list = this.queryEmployeeIsExist(loginaccount);
		if (ee_list == null || ee_list.isEmpty()){
			/*acresult.setResult(false);
			acresult.setCode(ActivityCheckMessage.GET_USERNOTEXIST.value);
			acresult.setMessage(ActivityCheckMessage.GET_USERNOTEXIST.desc);
			return null;*/
			throw new LogicTransactionRollBackException(ActivityCheckMessage.GET_USERNOTEXIST.desc);
		}
		EnterpriseEmployee ee = ee_list.get(0);
		return ee;
	}
	
	/**
	 * 统计企业的所有会员在指定时间内的有效投注额，按人员分组（按大类）
	 * @param paramObj
	 * @return
	 */
	@Override
	public List<EnterpriseEmployee> call_uspActivityButmoneyEnterprisecodeBigType(Map<String, Object> paramObj) throws Exception {
		return enterpriseEmployeeDao.queryUspActivityButmoneyEnterprisecodeBigType(paramObj);
	}

	/**
	 * 统计企业的所有会员在指定时间内的有效投注额，按人员分组（按小类）
	 * @param paramObj
	 * @return
	 */
	@Override
	public List<EnterpriseEmployee> call_uspActivityButmoneyEnterprisecodeSmallType(Map<String, Object> paramObj) throws Exception {
		return enterpriseEmployeeDao.queryUspActivityButmoneyEnterprisecodeSmallType(paramObj);
	}
	
	/**
	 * 根据编码查下级代理和下级会员总数
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@Override
	public EnterpriseEmployee call_Count(String employeecode)throws Exception {
		return enterpriseEmployeeDao.queryCount(employeecode);
	}
	/**
	 * 根据编码查下级代理和下级会员余额
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@Override
	public EnterpriseEmployee call_Balances(String parentemployeecode)throws Exception {
		return enterpriseEmployeeDao.queryBalances(parentemployeecode);
	}
}
