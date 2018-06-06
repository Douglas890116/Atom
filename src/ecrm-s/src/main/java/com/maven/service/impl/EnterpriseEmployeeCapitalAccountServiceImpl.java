package com.maven.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hy.pull.common.util.MoneyHelper;
import com.maven.base.dao.BaseDao;
import com.maven.cache.SystemCache;
import com.maven.constant.Enum_MSG;
import com.maven.dao.EnterpriseEmployeeCapitalAccountDao;
import com.maven.dao.EnterpriseEmployeeDao;
import com.maven.entity.EmployeeMoneyChange;
import com.maven.entity.EmployeeMoneyChangeType;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneychangetype;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneyinouttype;
import com.maven.entity.EnterpriseEmployeeCapitalAccount;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.logger.TLogger;
import com.maven.service.EmployeeMoneyChangeService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.util.AttrCheckout;
import com.maven.util.RandomString;
/**
 * 更新用户账户资金信息
 * @param map
 */
@Service
public class EnterpriseEmployeeCapitalAccountServiceImpl extends BaseServiceImpl<EnterpriseEmployeeCapitalAccount>
		implements EnterpriseEmployeeCapitalAccountService {
	@Autowired
	private EnterpriseEmployeeCapitalAccountDao employeeCapitalAccountDao;
	@Autowired
	private EnterpriseEmployeeService employeeService;
	@Autowired
	private EmployeeMoneyChangeService employeeMoneyChangeService;
	
	private static Map<String, String> __map_employeecode_enterprisecode = new HashMap<String, String>();
	
	@Override
	public BaseDao<EnterpriseEmployeeCapitalAccount> baseDao() {
		return employeeCapitalAccountDao;
	}
	 
	
	@Override
	public Class<EnterpriseEmployeeCapitalAccount> getClazz() {
		return EnterpriseEmployeeCapitalAccount.class;
	}
	
	/**
	 * 更新账户资金,写入帐变记录
	 * @param employeecode 用户编码
	 * @param changeinout  进账或者出账
	 * @param money 正存负取
	 * @param moneychangetype 帐变详细类型编码
	 * @throws Exception
	 */
	public int tc_updateCapitalAccount(String ordernumber,String employeecode, BigDecimal money,EmployeeMoneyChangeType moneychangetype,String moneychangedesc) throws Exception {
		AttrCheckout.checkout(moneychangetype, false,new String[]{"moneychangetypecode","moneyinouttype"});
		if(employeecode==null || money==null)  {
			throw new LogicTransactionRollBackException("资金操作参数异常");
		}
		// 帐变常规逻辑处理
		if(moneychangetype.getMoneyinouttype()==Enum_moneyinouttype.进账.value) //存款的金额值应为正数
		{
			if(money.doubleValue()<0) {
				throw new LogicTransactionRollBackException(Enum_MSG.金额异常.desc);
			}
		}
		if(moneychangetype.getMoneyinouttype()==Enum_moneyinouttype.出账.value) //取款的金额应为负数
		{
			if(money.doubleValue()>0) {
				throw new LogicTransactionRollBackException(Enum_MSG.金额异常.desc);
			}
		}
		EnterpriseEmployeeCapitalAccount ea = takeCurrencyAccount(employeecode);
		if(ea==null) {
			throw new LogicTransactionRollBackException(Enum_MSG.资金账户不存在.desc);
		}
		if(moneychangetype.getMoneyinouttype()==Enum_moneyinouttype.出账.value){
			if(MoneyHelper.moneyFormatDouble(ea.getBalance().add(money).doubleValue()) < 0) {
				TLogger.getLogger().Error("tc_updateCapitalAccount=用户编码："+employeecode+"  本次取款金额："+money+"，当前金额："+ea.getBalance().doubleValue()+"，余额不足以支付。tc_updateCapitalAccount。 批次号："+ordernumber);
				throw new LogicTransactionRollBackException(Enum_MSG.余额不足.desc);
			}
				
		}
		EnterpriseEmployeeCapitalAccount eeca = null;
		if(moneychangetype.getMoneychangetypecode().equals(Enum_moneychangetype.游戏上分.value)
				||moneychangetype.getMoneychangetypecode().equals(Enum_moneychangetype.游戏下分.value)){
			eeca = new EnterpriseEmployeeCapitalAccount(employeecode, money,false);
			//TLogger.getLogger().Error("tc_updateCapitalAccount=用户编码："+employeecode+"  账变类型为上下分："+moneychangetype.getMoneychangetypecode()+"。 批次号："+ordernumber);
			// 特殊帐变类型处理
			if(moneychangetype.getMoneychangetypecode().equals(Enum_moneychangetype.游戏上分.value)){
				eeca.setUpintegralbalance(eeca.getBalance().abs());
			}
		}else{
			eeca = new EnterpriseEmployeeCapitalAccount(employeecode, money,true);
			//TLogger.getLogger().Error("tc_updateCapitalAccount=用户编码："+employeecode+"  账变类型不是上下分："+moneychangetype.getMoneychangetypecode()+"。 批次号："+ordernumber);
		}
		long sorttime = RandomString.SORTTIME();
		// 更新账户资金
		int status = employeeCapitalAccountDao.updateEmployeeCapitalAccount(eeca);
		if(status<=0){
			TLogger.getLogger().Error("tc_updateCapitalAccount=用户编码："+employeecode+"  本次取款金额："+money+"，当前金额："+ea.getBalance().doubleValue()+"，未能更新账户余额记录。tc_updateCapitalAccount。 批次号："+ordernumber);
			throw new LogicTransactionRollBackException(Enum_MSG.余额不足.desc);
		} else {
			//TLogger.getLogger().Error("tc_updateCapitalAccount=用户编码："+employeecode+"  余额已更新："+money+" 批次号："+ordernumber);
		}
		
		// 写入帐变
		String enterprisecode = __map_employeecode_enterprisecode.get(ea.getEmployeecode());
		if(enterprisecode == null) {
			 enterprisecode = employeeService.takeEmployeeByCode(ea.getEmployeecode()).getEnterprisecode();
			 __map_employeecode_enterprisecode.put(ea.getEmployeecode(), enterprisecode);
		}
		
		String moneychangecode = RandomString.UUID();
		int count = employeeMoneyChangeService.addMoneyChangeRecord(new EmployeeMoneyChange(moneychangecode, ordernumber, ea.getEmployeecode() ,ea.getParentemployeecode(), moneychangetype.getMoneychangetypecode(), 
					new Date(), money, ea.getBalance(), moneychangedesc,sorttime, enterprisecode));
		//TLogger.getLogger().Error("tc_updateCapitalAccount=用户编码："+employeecode+"  账变记录写入："+moneychangecode+"。tc_updateCapitalAccount。实际受影响行数="+count+" 批次号："+ordernumber);
		return status;
	}
	
	/**
	 * 更新账户资金,写入帐变记录（只针对冲正冲负的业务）
	 * @param employeecode 用户编码
	 * @param changeinout  进账或者出账
	 * @param money 正存负取
	 * @param moneychangetype 帐变详细类型编码
	 * @throws Exception
	 */
	public int tc_updateCapitalAccount(String ordernumber,String employeecode, BigDecimal money,EmployeeMoneyChangeType moneychangetype,String moneychangedesc,String moneyaddtype) throws Exception {
		AttrCheckout.checkout(moneychangetype, false,new String[]{"moneychangetypecode","moneyinouttype"});
		if(employeecode==null || money==null)  {
			throw new LogicTransactionRollBackException("资金操作参数异常");
		}
		// 帐变常规逻辑处理
		if(moneychangetype.getMoneyinouttype()==Enum_moneyinouttype.进账.value) //存款的金额值应为正数
		{
			if(money.doubleValue()<0) {
				throw new LogicTransactionRollBackException(Enum_MSG.金额异常.desc);
			}
		}
		if(moneychangetype.getMoneyinouttype()==Enum_moneyinouttype.出账.value) //取款的金额应为负数
		{
			if(money.doubleValue()>0) {
				throw new LogicTransactionRollBackException(Enum_MSG.金额异常.desc);
			}
		}
		EnterpriseEmployeeCapitalAccount ea = takeCurrencyAccount(employeecode);
		if(ea==null) {
			throw new LogicTransactionRollBackException(Enum_MSG.资金账户不存在.desc);
		}
		
		if(moneychangetype.getMoneyinouttype()==Enum_moneyinouttype.出账.value){
			if(ea.getBalance().add(money).doubleValue()<0) {
				throw new LogicTransactionRollBackException(Enum_MSG.余额不足.desc);
			}
		}
		
		EnterpriseEmployeeCapitalAccount eeca = null;
		if(moneychangetype.getMoneychangetypecode().equals(Enum_moneychangetype.游戏上分.value)
				||moneychangetype.getMoneychangetypecode().equals(Enum_moneychangetype.游戏下分.value)){
			eeca = new EnterpriseEmployeeCapitalAccount(employeecode, money,true);
			// 特殊帐变类型处理
			if(moneychangetype.getMoneychangetypecode().equals(Enum_moneychangetype.游戏上分.value)){
				eeca.setUpintegralbalance(eeca.getBalance().abs());
			}
		}else{
			eeca = new EnterpriseEmployeeCapitalAccount(employeecode, money,false);
		}
		long sorttime = RandomString.SORTTIME();
		// 更新账户资金
		int status = employeeCapitalAccountDao.updateEmployeeCapitalAccount(eeca);
		if(status>0){
			// 写入帐变
			String enterprisecode = __map_employeecode_enterprisecode.get(ea.getEmployeecode());
			if(enterprisecode == null) {
				 enterprisecode = employeeService.takeEmployeeByCode(ea.getEmployeecode()).getEnterprisecode();
				 __map_employeecode_enterprisecode.put(ea.getEmployeecode(), enterprisecode);
			}
			
			employeeMoneyChangeService.addMoneyChangeRecord(new EmployeeMoneyChange(RandomString.UUID(),ordernumber, ea.getEmployeecode() ,ea.getParentemployeecode(), moneychangetype.getMoneychangetypecode(), 
						new Date(), money, ea.getBalance(), moneychangedesc,moneyaddtype,sorttime, enterprisecode));
		}
		return status;
	}
	
	
	/**
	 * 创建员工的资金帐户
	 * @param map
	 * @throws Exception 
	 */
	@Override
	public void tc_saveEmployeeCapitalAccount(EnterpriseEmployeeCapitalAccount object) throws Exception {
		employeeCapitalAccountDao.saveEmployeeCapitalAccount(AttrCheckout.checkout(object, true, new String[]{"employeecode","parentemployeecode"}));
	}
	
	/**
	 * 获取会员的账户余额
	 * @param employee
	 * @param Currency
	 * @return
	 * @throws Exception 
	 */
	public EnterpriseEmployeeCapitalAccount takeCurrencyAccount(String employeecode) throws Exception{
		return super.selectOne(AttrCheckout.checkout(new EnterpriseEmployeeCapitalAccount(employeecode),false, new String[]{"employeecode"}));
	}

	/**
	 * 客户输赢分析查询
	 * @param paramObj
	 */
	@Override
	public List<EnterpriseEmployeeCapitalAccount> queryUserLoseWinAnalysis(Map<String, Object> paramObj) throws Exception {
		return employeeCapitalAccountDao.queryUserLoseWinAnalysis(paramObj);
	}

	/**
	 * 客户输赢分析数量统计
	 * @param paramObj
	 */
	@Override
	public Map<String, Object> queryCountUserLoseWinAnalysis(Map<String, Object> paramObj)throws Exception {
		return employeeCapitalAccountDao.countUserLoseWinAnalysis(paramObj);
	}


}
