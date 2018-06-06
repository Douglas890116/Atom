package com.maven.payment.rf;
/**
 * 锐付接口订单信息
 * @author klay
 *
 */
public class RFOrderConfig {
	/** 订单号 */
	private String orderNo;
	/** 支付类型 */
	private String appType;
	/** 订单金额 */
	private String orderAmount;
	/** 支付银行 */
	private String bank;
	/** 卡类型 */
	private String cardType;
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
}