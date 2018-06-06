package com.maven.payment.el;

public class ELOrderConfig {
	// 通用参数
	private String orderNo;
	private String orderAmount;
	private String orderCurrency;
	private String orderDatetime;
	// 扫码独有参数
	private String payMode;
	private String isPhone;
	// 代付独有参数
	private String cashType;
	private String accountName;
	private String bankName;
	private String bankCardNo;
	private String canps;
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getOrderCurrency() {
		return orderCurrency;
	}
	public void setOrderCurrency(String orderCurrency) {
		this.orderCurrency = orderCurrency;
	}
	public String getOrderDatetime() {
		return orderDatetime;
	}
	public void setOrderDatetime(String orderDatetime) {
		this.orderDatetime = orderDatetime;
	}
	public String getPayMode() {
		return payMode;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	public String getIsPhone() {
		return isPhone;
	}
	public void setIsPhone(String isPhone) {
		this.isPhone = isPhone;
	}
	public String getCashType() {
		return cashType;
	}
	public void setCashType(String cashType) {
		this.cashType = cashType;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getCanps() {
		return canps;
	}
	public void setCanps(String canps) {
		this.canps = canps;
	}
}