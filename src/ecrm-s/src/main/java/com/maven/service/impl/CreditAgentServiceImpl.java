package com.maven.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.entity.EmployeeMoneyChangeType;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneychangetype;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneyinouttype;
import com.maven.entity.EnterpriseEmployeeCapitalAccount;
import com.maven.exception.LogicTransactionException;
import com.maven.service.CreditAgentService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.util.RandomString;

/***
 * 信用代理业务类
 * @author Administrator
 *
 */
@Service
public class CreditAgentServiceImpl implements CreditAgentService {

	private final static String MONEY_ENNOUGH = "转出账户余额不足";  
	
	private final static String MONEY_LOW_UPPER_LIMIT = "转账金额不能少于0";
	
	private final static String NO_DIRECT_JUNIOR = "该用户不是您的直系下级";
	
	
	
	@Autowired
	private EnterpriseEmployeeCapitalAccountService  enterpriseEmployeeCapitalAccountService;
	
	
	@Override
	public boolean tc_in_integral(String out_integral_employeecode, String in_integral_employeecode,
			BigDecimal shiff_money) throws Exception{
		String ordernumber = RandomString.UUID();
		EnterpriseEmployeeCapitalAccount out_employee_capital = enterpriseEmployeeCapitalAccountService.selectByPrimaryKey(out_integral_employeecode);
		if(out_employee_capital.getBalance().compareTo(shiff_money)==-1){
			throw new LogicTransactionException(MONEY_ENNOUGH);
		}
		if(shiff_money.compareTo(new BigDecimal("0"))==-1 || shiff_money.compareTo(new BigDecimal("0"))==0){
			throw new LogicTransactionException(MONEY_LOW_UPPER_LIMIT);
		}
		EnterpriseEmployeeCapitalAccount in_employee_capital = enterpriseEmployeeCapitalAccountService.selectByPrimaryKey(in_integral_employeecode);
		if(!in_employee_capital.getParentemployeecode().equals(out_employee_capital.getEmployeecode())){
			throw new LogicTransactionException(NO_DIRECT_JUNIOR);
		}
		//转出金额
		enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(ordernumber, 
				out_integral_employeecode, shiff_money.negate(), new EmployeeMoneyChangeType(Enum_moneychangetype.转出金额.value,Enum_moneyinouttype.出账), "向 "+in_employee_capital.getLoginaccount()+" 转入金额");
		//转入金额
		enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(ordernumber, 
				in_integral_employeecode, shiff_money, new EmployeeMoneyChangeType(Enum_moneychangetype.转入金额.value,Enum_moneyinouttype.进账), "由 "+out_employee_capital.getLoginaccount()+" 转入金额");
		return true;
	}



	@Override
	public boolean tc_out_integral(String out_integral_employeecode, String in_integral_employeecode,
			BigDecimal shiff_money) throws Exception {
		String ordernumber = RandomString.UUID();
		EnterpriseEmployeeCapitalAccount out_employee_capital = enterpriseEmployeeCapitalAccountService.selectByPrimaryKey(out_integral_employeecode);
		if(out_employee_capital.getBalance().compareTo(shiff_money)==-1){
			throw new LogicTransactionException(MONEY_ENNOUGH);
		}
		if(shiff_money.compareTo(new BigDecimal("0"))==-1 || shiff_money.compareTo(new BigDecimal("0"))==0){
			throw new LogicTransactionException(MONEY_LOW_UPPER_LIMIT);
		}
		EnterpriseEmployeeCapitalAccount in_employee_capital = enterpriseEmployeeCapitalAccountService.selectByPrimaryKey(in_integral_employeecode);
		if(!in_employee_capital.getEmployeecode().equals(out_employee_capital.getParentemployeecode())){
			throw new LogicTransactionException(NO_DIRECT_JUNIOR);
		}
		//转出金额
		enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(ordernumber, 
				out_integral_employeecode, shiff_money.negate(), new EmployeeMoneyChangeType(Enum_moneychangetype.转出金额.value,Enum_moneyinouttype.出账), "被 "+in_employee_capital.getLoginaccount()+" 转出金额");
		//转入金额
		enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(ordernumber, 
				in_integral_employeecode, shiff_money, new EmployeeMoneyChangeType(Enum_moneychangetype.转入金额.value,Enum_moneyinouttype.进账), "从 "+out_employee_capital.getLoginaccount()+" 转出金额");
		return true;
	}

}
