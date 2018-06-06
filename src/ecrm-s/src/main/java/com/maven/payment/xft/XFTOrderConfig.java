package com.maven.payment.xft;

public class XFTOrderConfig {
	private String orderId;
	private String orderAmount;
	private String bankCode;
	private String isApp;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getIsApp() {
		return isApp;
	}
	public void setIsApp(String isApp) {
		this.isApp = isApp;
	}
}