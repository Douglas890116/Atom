package com.maven.payment.lfqqpay;

public class LfQQPayOrderConfig {
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 订单金额
	 */
	private String amount;
	/**
	 * 商品名称(不要带特殊字符)
	 */
	private String subject;
	/**
	 * 商品描述(不要带特殊字符)
	 */
	private String subBody;
	/**
	 * 备注
	 */
	private String remark;
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSubBody() {
		return subBody;
	}
	public void setSubBody(String subBody) {
		this.subBody = subBody;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}