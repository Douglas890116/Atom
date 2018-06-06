package com.maven.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Model class of enterprise_employee_capital_account.
 * 员工游戏账户对象
 * @author Ethan
 * @version $Id$
 */
public class EnterpriseEmployeeCapitalAccount implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/** 员工编码  */
	private String employeecode;
	/** 用户上级编码 */
	private String parentemployeecode;
	
	/** 账户余额. */
	private BigDecimal  balance;
	
	/** 上分前余额. */
	private BigDecimal  upintegralbalance;

	/** 累计存款. */
	private BigDecimal accumulateddeposit;
	
	/** 累计取款. */
	private BigDecimal accumulatedwithdraw;
	
	/** 总输赢.*/
	private BigDecimal summoney;
	
	/** 用户名 */
	private String loginaccount;
	
	/** 上级用户名 */
	private String parentemployeeaccount;
	
	
	
	
	/**
	 * Set the 用户名
	 * @return
	 */
	public String getLoginaccount() {
		return loginaccount;
	}

	public void setLoginaccount(String loginaccount) {
		this.loginaccount = loginaccount;
	}
	
	/**
	 * Set the 上级用户名
	 * @return
	 */
	public String getParentemployeeaccount() {
		return parentemployeeaccount;
	}

	public void setParentemployeeaccount(String parentemployeeaccount) {
		this.parentemployeeaccount = parentemployeeaccount;
	}

	/**
	 * Set the 总输赢
	 * @return
	 */
	public BigDecimal getSummoney() {
		return summoney;
	}

	public void setSummoney(BigDecimal summoney) {
		this.summoney = summoney;
	}

	/**
	 * Constructor.
	 */
	public EnterpriseEmployeeCapitalAccount() {
	}
	
	public EnterpriseEmployeeCapitalAccount(String employeecode,String parentemployeecode) {
		this.employeecode = employeecode;
		this.parentemployeecode = parentemployeecode;
	}
	
	public EnterpriseEmployeeCapitalAccount(String employeecode) {
		this.employeecode = employeecode; 
	}
	
	/**
	 * 
	 * @param employeecode 用户编码
	 * @param balance 进账/出账 金额
	 * @param isdepost_withdrawl 是否是存取款操作
	 */
	public EnterpriseEmployeeCapitalAccount(String employeecode,BigDecimal  balance,boolean isdepost_withdrawl){
		this.employeecode = employeecode; 
		this.balance = balance;
		if(isdepost_withdrawl){
			if(balance.compareTo(new BigDecimal("0"))!=1){
				this.accumulatedwithdraw = balance;
			}else{
				this.accumulateddeposit = balance;
			}
		}
	}
	

	public String getEmployeecode() {
		return employeecode;
	}

	public void setEmployeecode(String employeecode) {
		this.employeecode = employeecode;
	}

	public String getParentemployeecode() {
		return parentemployeecode;
	}

	public void setParentemployeecode(String parentemployeecode) {
		this.parentemployeecode = parentemployeecode;
	}

	/**
	 * Set the 账户余额.
	 * 
	 * @param balance
	 *            账户余额
	 */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	/**
	 * Get the 账户余额.
	 * 
	 * @return 账户余额
	 */
	public BigDecimal getBalance() {
		return this.balance;
	}

	public BigDecimal getUpintegralbalance() {
		return upintegralbalance;
	}

	public void setUpintegralbalance(BigDecimal upintegralbalance) {
		this.upintegralbalance = upintegralbalance;
	}

	/**
	 * Get the 累计存款 
	 * @return
	 */
	public BigDecimal getAccumulateddeposit() {
		return accumulateddeposit;
	}

	/**
	 * Set the 累计存款 
	 * @return
	 */
	public void setAccumulateddeposit(BigDecimal accumulateddeposit) {
		this.accumulateddeposit = accumulateddeposit;
	}

	/**
	 * Get the 累计取款 
	 * @return
	 */
	public BigDecimal getAccumulatedwithdraw() {
		return accumulatedwithdraw;
	}

	/**
	 * Set the 累计取款 
	 * @return
	 */
	public void setAccumulatedwithdraw(BigDecimal accumulatedwithdraw) {
		this.accumulatedwithdraw = accumulatedwithdraw;
	}



}
