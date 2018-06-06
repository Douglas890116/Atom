package com.maven.payment.zft.df;

public class ZFTDFOrderConfig {
//	序号--------------------1
//	银行账户----------------621799xxxxxxxxx4393
//	开户名------------------张三
//	开户行名称--------------中国邮政储蓄银行
//	分行--------------------开福区分行
//	支行--------------------开福区分行
//	公/私-------------------私
//	金额--------------------487.00
//	币种--------------------CNY
//	省----------------------湖南省
//	市----------------------长沙
//	手机号------------------
//	证件类型----------------
//	证件号------------------
//	用户协议号--------------
//	商户订单号--------------201708075352476560485471005
//	备注--------------------OK
	private String orderNo;
	private String orderAmount;
	private String orderDate;
	private String userName;// 开户名
	private String bankCard;// 银行卡号
	private String bankName;// 银行
	private String branchName;
	private String province;
	private String city;
	
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
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
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
}