package com.maven.payment.yb;

/**
 * 
 * @author Administrator
 *
 */
public class YbOrderConfig {
	
	/*******银行类型******/
	private String banktype = "";

	/*******金额  （单位元）******/
	private double paymoney;
	
	
	/*******备注******/
	private String attach = "B";
	
	/*******订单号******/
	private String ordernumber;
	
	public YbOrderConfig() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getBanktype() {
		return banktype;
	}

	public void setBanktype(String banktype) {
		this.banktype = banktype;
	}

	public double getPaymoney() {
		return paymoney;
	}

	public void setPaymoney(double paymoney) {
		this.paymoney = paymoney;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getOrdernumber() {
		return ordernumber;
	}

	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
	}

	public YbOrderConfig(String banktype, double paymoney, String attach, String ordernumber) {
		super();
		this.banktype = banktype;
		this.paymoney = paymoney;
		this.attach = attach;
		this.ordernumber = ordernumber;
	}
	

	
}
