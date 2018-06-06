package com.maven.payment.lf;

/**
 * 
 * @author Administrator
 *
 */
public class LfOrderConfig {
	
	/*******银行类型******/
	private String bankCode = "";

	/*******金额  （单位元）******/
	private double paymoney;
	
	/*******订单号******/
	private String ordernumber;
	
	/*******请求时间 YYMMDDHHmmss******/
	private String requestTime;
	
	private String goodName = "A";
	private String goodsDetail = "B";
	
	//交易IP
	private String transip;
	
	public LfOrderConfig() {
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


	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public String getGoodsDetail() {
		return goodsDetail;
	}

	public void setGoodsDetail(String goodsDetail) {
		this.goodsDetail = goodsDetail;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}


	public String getTransip() {
		return transip;
	}


	public void setTransip(String transip) {
		this.transip = transip;
	}
	

	
}
