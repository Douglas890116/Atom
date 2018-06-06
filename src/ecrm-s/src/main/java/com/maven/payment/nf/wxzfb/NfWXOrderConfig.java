package com.maven.payment.nf.wxzfb;

/**
 * 
 * @author Administrator
 *
 */
public class NfWXOrderConfig {

	/*******金额  （单位元）******/
	private double amount;
	
	/*******订单号******/
	private String orderId;//30个长度的字符串和数字
	
	
//	09:扫码支付 
//	10:公众号支付
//	10: 微信app
//	10:支付宝wap
//	10：微信公众号
	private String payMode = "09";
	
//	微信扫码：WECHAT
//	支付宝扫码：ALIPAY
//	微信app：WECHATAPP
//	微信公众号：WECHATPUBLIC
//	支付宝wap:ALIPAYWAP
	private String bankId = "";
	
	private String creditType = "3";
	
	
	public NfWXOrderConfig() {
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


	public NfWXOrderConfig(double amount, String orderId, String payMode, String bankId,
			String creditType) {
		super();
		this.amount = amount;
		this.orderId = orderId;
		this.payMode = payMode;
		this.bankId = bankId;
		this.creditType = creditType;
	}


	
}
