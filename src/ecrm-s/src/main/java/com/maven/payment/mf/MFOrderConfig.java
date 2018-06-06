package com.maven.payment.mf;

public class MFOrderConfig {
	/** 订单ID **/
	private String orderId;
	/** 订单总金额 **/
	private String amount;
	/** 货币类型，固定值：CNY **/
	private String currency; 
	/** 商品名称 **/
	private String goodsName; 
	/** 商品类别 **/
	private String goodsCategory;
	/** 商品描述 **/
	private String goodsDesc;
	/** 支付银行编码 **/
	private String bankNo;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsCategory() {
		return goodsCategory;
	}
	public void setGoodsCategory(String goodsCategory) {
		this.goodsCategory = goodsCategory;
	}
	public String getGoodsDesc() {
		return goodsDesc;
	}
	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
}