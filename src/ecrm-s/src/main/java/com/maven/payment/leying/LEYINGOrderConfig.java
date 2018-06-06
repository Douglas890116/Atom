package com.maven.payment.leying;

public class LEYINGOrderConfig {

	/**
	 * 请求序列号
	 */
	private String serialID;
	
	/**
	 * 订单提交时间
	 */
	private String submitTime;
	
	/**
	 * 订单明细信息
	 */
	private String orderDetails;
	
	/**
	 * 订单总金额
	 */
	private String totalAmount;
	
	/**
	 * 存款订单参数
	 * 
	 * @author Administrator
	 *
	 */
	public enum O_Save_Paramters {
		请求序列号("serialID"), 
		订单明细信息("orderDetails"), 
		订单提交时间("submitTime"),
		订单总金额("totalAmount") ;
		
		public String value;

		private O_Save_Paramters(String value) {
			this.value = value;
		}

		public static String[] paramters() {
			O_Save_Paramters[] s = O_Save_Paramters.values();
			String[] ss = new String[s.length];
			for (int i = 0; i < s.length; i++) {
				ss[i] = s[i].value;
			}
			return ss;
		}
	}
	
	public String getSerialID() {
		return serialID;
	}

	public void setSerialID(String serialID) {
		this.serialID = serialID;
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public String getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(String orderDetails) {
		this.orderDetails = orderDetails;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	
}
