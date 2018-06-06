package com.maven.payment.hc;

/**
 * 汇潮支付
 * @author Administrator
 *
 */
public class HCOrderConfig {
	
	/**
	 * 商户订单号
	 */
	private String orderNo;
	/**
	 * 支付金额，订单总金额以元为单位，精确到小数点后两位
	 */
	private String orderAmount;
	
	/**
	 * 银行编码，String(16)
	 */
	private String bankCode;
	
	/**
	 * 支付方式
B2CCredit B2C信用卡
B2CDebit B2C借记卡
noCard 银联快捷支付 
quickPay 快捷支付
B2B 企业网银支付
	 * 
	 */
	private String paytype = "quickPay";

	
	/**
	 * 签名数据
	 */
	private String sign;
	
	private String ordertime;//YYYYMMDDHHMMSS

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

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}



	public HCOrderConfig() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getPaytype() {
		return paytype;
	}


	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}


	public String getOrdertime() {
		return ordertime;
	}


	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	
}
