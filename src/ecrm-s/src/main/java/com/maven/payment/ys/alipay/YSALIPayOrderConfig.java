package com.maven.payment.ys.alipay;

public class YSALIPayOrderConfig {
	private String orderNo;
	private String orderAmount;
	private String orderTime;
	private String orderSubject;
	
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
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getOrderSubject() {
		return orderSubject;
	}
	public void setOrderSubject(String orderSubject) {
		this.orderSubject = orderSubject;
	}
}