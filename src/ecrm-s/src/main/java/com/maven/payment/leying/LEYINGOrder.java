package com.maven.payment.leying;

public class LEYINGOrder {

	/**
	 * 订单号
	 */
	private String orderID;
	
	/**
	 * 订单明细金额
	 */
	private String orderAmount;
	
	/**
	 * 下单商户显示名
	 */
	private String displayName="";
	
	public LEYINGOrder(String orderID, String orderAmount) {
		this.orderID = orderID;
		this.orderAmount = orderAmount;
	}

	/**
	 * 商品名称
	 */
	private String goodsName="充值";
	
	/**
	 * 商品数量
	 */
	private String goodsCount="1";
	
	public String toString(){
		return this.orderID+","+this.orderAmount+","+this.displayName+","+this.goodsName+","+this.goodsCount;
	}
	
}
