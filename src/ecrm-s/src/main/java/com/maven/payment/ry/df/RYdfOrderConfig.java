package com.maven.payment.ry.df;

public class RYdfOrderConfig {
	/**
	 * 订单号
	 */
	private String ordId;
	/**
	 * 收款人姓名
	 */
	private String accountName;
	/**
	 * 收款人账号
	 */
	private String accountBankNum;
	/**
	 * 银行编号
	 */
	private String bankCode;
	/**
	 * 开户行省
	 */
	private String accountBankProvince;
	/**
	 * 开户行市
	 */
	private String accountBankCity;
	/**
	 * 支行名称
	 */
	private String accountBankBranch;
	/**
	 * 代付金额
	 */
	private String amount;
	
	public String getOrdId() {
		return ordId;
	}
	public void setOrdId(String ordId) {
		this.ordId = ordId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountBankNum() {
		return accountBankNum;
	}
	public void setAccountBankNum(String accountBankNum) {
		this.accountBankNum = accountBankNum;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getAccountBankProvince() {
		return accountBankProvince;
	}
	public void setAccountBankProvince(String accountBankProvince) {
		this.accountBankProvince = accountBankProvince;
	}
	public String getAccountBankCity() {
		return accountBankCity;
	}
	public void setAccountBankCity(String accountBankCity) {
		this.accountBankCity = accountBankCity;
	}
	public String getAccountBankBranch() {
		return accountBankBranch;
	}
	public void setAccountBankBranch(String accountBankBranch) {
		this.accountBankBranch = accountBankBranch;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
}