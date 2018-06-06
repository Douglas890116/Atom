package com.maven.payment.nf;

/**
 * 
 * @author Administrator
 *
 */
public class NfOrderConfig {

	/*******金额  （单位元）******/
	private double amount;
	
	/*******订单号******/
	private String orderId;//30个长度的字符串和数字
	
	
//	00:WAP手机银行支付模式
//	01:网银支付模式
//	02:委托银行扣款模式
//	03:客户端集成银行客户端程序模式
//	04:客户端调用银行WAP页面支付模式
//	05:手机网页支付模式
	private String payMode = "01";
	
	private String bankId = "";
	//0:表示仅允许使用借记卡支付
//	1:表示仅允许使用信用卡支付
//	2:表示借记卡和信用卡都能对订单进行支付
	private String creditType = "2";
	
	
	public NfOrderConfig() {
		super();
		// TODO Auto-generated constructor stub
	}



	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public String getPayMode() {
		return payMode;
	}


	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}


	public String getBankId() {
		return bankId;
	}


	public void setBankId(String bankId) {
		this.bankId = bankId;
	}


	public String getCreditType() {
		return creditType;
	}


	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}


	public NfOrderConfig(double amount, String orderId, String payMode, String bankId,
			String creditType) {
		super();
		this.amount = amount;
		this.orderId = orderId;
		this.payMode = payMode;
		this.bankId = bankId;
		this.creditType = creditType;
	}


	
}
