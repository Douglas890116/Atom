package com.maven.payment.zft;

public class ZFTOrderConfig {
	// 订单基本信息
	private String orderNo;
	private String orderAmount;
	private String orderBank;
	private String orderTitle;
	private String orderBody;
	
	// 订单用户信息
	private String isApp;
	private String userEmail;
	private String userIP;
	private String appMsg;
	private String appType;
	
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
	public String getOrderBank() {
		return orderBank;
	}
	public void setOrderBank(String orderBank) {
		this.orderBank = orderBank;
	}
	public String getOrderTitle() {
		return orderTitle;
	}
	public void setOrderTitle(String orderTitle) {
		this.orderTitle = orderTitle;
	}
	public String getOrderBody() {
		return orderBody;
	}
	public void setOrderBody(String orderBody) {
		this.orderBody = orderBody;
	}
	public String getIsApp() {
		return isApp;
	}
	public void setIsApp(String isApp) {
		this.isApp = isApp;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserIP() {
		return userIP;
	}
	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}
	public String getAppMsg() {
		return appMsg;
	}
	public void setAppMsg(String appMsg) {
		this.appMsg = appMsg;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
}