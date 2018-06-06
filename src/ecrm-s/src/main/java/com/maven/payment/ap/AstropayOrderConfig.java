package com.maven.payment.ap;

public class AstropayOrderConfig {

	private String orderNo;          // x_invoice_num
	private String orderType;        // x_type
	private String orderAmount;      // x_amount
	private String currency;         // x_currency
	private String userCardNum;      // x_card_num
	private String userCardCode;     // x_card_code
	private String userCardExpDate;  // x_exp_date
	private String userNo;           // x_unique_id
	private String userEmail;        // x_email
	private String userName;         // x_name
	private String userDocument;     // x_document
	private String userCountry;      // x_country
	private String mobileNum;        // x_mobile_number
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getUserCardNum() {
		return userCardNum;
	}
	public void setUserCardNum(String userCardNum) {
		this.userCardNum = userCardNum;
	}
	public String getUserCardCode() {
		return userCardCode;
	}
	public void setUserCardCode(String userCardCode) {
		this.userCardCode = userCardCode;
	}
	public String getUserCardExpDate() {
		return userCardExpDate;
	}
	public void setUserCardExpDate(String userCardExpDate) {
		this.userCardExpDate = userCardExpDate;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserDocument() {
		return userDocument;
	}
	public void setUserDocument(String userDocument) {
		this.userDocument = userDocument;
	}
	public String getUserCountry() {
		return userCountry;
	}
	public void setUserCountry(String userCountry) {
		this.userCountry = userCountry;
	}
	public String getMobileNum() {
		return mobileNum;
	}
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
}