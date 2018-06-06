package com.maven.payment.duo;

public class DuoOrderConfig {
	private String orderId;
	private String orderAmount;
	private String bankCode;
	private String proInfo;
	private String remark;
	
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
	public String getProInfo() {
		return proInfo;
	}
	public void setProInfo(String proInfo) {
		this.proInfo = proInfo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}