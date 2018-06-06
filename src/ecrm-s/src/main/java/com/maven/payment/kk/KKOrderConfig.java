package com.maven.payment.kk;

public class KKOrderConfig {
	/** 订单号 */
	private String outTradeNo;
	/** 订单金额 */
	private String orderPrice;
	/** 订单时间 */
	private String orderTime;
	/** 商品名称 */
	private String productName;
	/** 支付IP */
	private String orderIp;
	/* 微信公众号参数 */
	/** 用户openId */
	private String openid;
	
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getOrderIp() {
		return orderIp;
	}
	public void setOrderIp(String orderIp) {
		this.orderIp = orderIp;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
}