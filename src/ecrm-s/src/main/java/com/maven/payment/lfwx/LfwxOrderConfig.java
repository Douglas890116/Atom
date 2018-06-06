package com.maven.payment.lfwx;

/**
 * 
 * @author Administrator
 *
 */
public class LfwxOrderConfig {
	
	private String service = "wx_pay";//固定值

	private String wx_pay_type = "wx_sm";//固定值
	
	private String transip;//交易IP
	/*******金额  （单位元）******/
	private double paymoney;
	
	/*******订单号******/
	private String ordernumber;
	
	
	private String subject = "在线充值";//固定值
	private String sub_body = "B";//固定值
	
	
	
	public LfwxOrderConfig() {
		super();
		// TODO Auto-generated constructor stub
	}


	public double getPaymoney() {
		return paymoney;
	}

	public void setPaymoney(double paymoney) {
		this.paymoney = paymoney;
	}


	public String getOrdernumber() {
		return ordernumber;
	}

	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
	}



	public String getTransip() {
		return transip;
	}


	public void setTransip(String transip) {
		this.transip = transip;
	}


	public String getService() {
		return service;
	}


	public String getWx_pay_type() {
		return wx_pay_type;
	}


	public String getSubject() {
		return subject;
	}


	public String getSub_body() {
		return sub_body;
	}
	

	
}
