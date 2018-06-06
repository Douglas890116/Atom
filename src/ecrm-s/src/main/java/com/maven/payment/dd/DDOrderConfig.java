package com.maven.payment.dd;

public class DDOrderConfig {
	private String orderNo;
	private String amount;
	private String name;
	/* 代付参数 */
	private String customerPhone;
	private String customerName;
	private String customerCertType;
	private String customerCertId;
	private String bankNo;
	private String bankName;
	private String bankCardNo;
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerCertType() {
		return customerCertType;
	}
	public void setCustomerCertType(String customerCertType) {
		this.customerCertType = customerCertType;
	}
	public String getCustomerCertId() {
		return customerCertId;
	}
	public void setCustomerCertId(String customerCertId) {
		this.customerCertId = customerCertId;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
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
}