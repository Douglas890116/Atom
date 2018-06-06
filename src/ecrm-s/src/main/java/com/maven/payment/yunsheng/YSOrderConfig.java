package com.maven.payment.yunsheng;

/**
 * 云盛支付
 * @author Administrator
 *
 */
public class YSOrderConfig {
	
	/**
	 * 商户订单号，由商户系统生成的唯一订单编号，最大长度为32位
	 */
	private String orderNo;
	/**
	 * 支付金额，订单总金额以元为单位，精确到小数点后两位
	 */
	private String orderAmount;
	
	/**
	 * 银行编码，String(16)
	 * 
	 * PayType不是网银时：
	 * 微信BankCode=WECHAT；支付宝BankCode=ALIPAY；QQ钱包=QQPAY
	 * 
	 */
	private String bankCode;

	private String ordertime;//yyyyMMddHHmmss
	
	private String GoodsName = "AABBCC";

	/**
	 * 支付类型
	 * 
	 * 1网银,2支付，3微信，4 QQ钱包
	 * 
	 */
	private String PayType = "1";


	public YSOrderConfig(String orderNo, String orderAmount, String bankCode, String ordertime) {
		super();
		this.orderNo = orderNo;
		this.orderAmount = orderAmount;
		this.bankCode = bankCode;
		this.ordertime = ordertime;
	}
	
	
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



	public YSOrderConfig() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String getOrdertime() {
		return ordertime;
	}


	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}


	public String getGoodsName() {
		return GoodsName;
	}


	public void setGoodsName(String goodsName) {
		GoodsName = goodsName;
	}


	public String getPayType() {
		return PayType;
	}


	public void setPayType(String payType) {
		PayType = payType;
	}


	
}
