package com.maven.payment.jh2.df;

public class JH2DFOrderConfig {
	private String orderNo;
	private String orderPrice;
	// 银行账户参数
	private String receiverName;
	private String receiverAccountNo;
	private String bankCode;
	private String bankName;
	private String bankClearNo;
	private String bankBranchNo;
	private String bankBranchName;
	private String province;
	private String city;
	private String phoneNo;
	private String certType;
	private String certNo;
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverAccountNo() {
		return receiverAccountNo;
	}
	public void setReceiverAccountNo(String receiverAccountNo) {
		this.receiverAccountNo = receiverAccountNo;
	}
	public String getBankClearNo() {
		return bankClearNo;
	}
	public void setBankClearNo(String bankClearNo) {
		this.bankClearNo = bankClearNo;
	}
	public String getBankBranchNo() {
		return bankBranchNo;
	}
	public void setBankBranchNo(String bankBranchNo) {
		this.bankBranchNo = bankBranchNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankBranchName() {
		return bankBranchName;
	}
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getCertType() {
		return certType;
	}
	public void setCertType(String certType) {
		this.certType = certType;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	
}