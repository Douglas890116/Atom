package com.maven.payment.mb;

/**
 * 摩宝支付
 * @author Administrator
 *
 */
public class MBOrderConfig {
	
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
	 * 
	 * 不进行签名，支付系统根据该银行代码直接跳转银行网银，不输或输入的银行代码不存在则展示Mo宝支付首页让用户选择支付方式。
工行 ICBC农行 ABC中行 BOC建行 CCB
交行 COMM招行 CMB浦发 SPDB兴业 CIB
民生 CMBC广发GDB中信 CNCB光大 CEB
华夏 HXB邮储PSBC平安PAB
	 */
	private String bankCode;

	private String ordertime;//YYYYMMDD年月日
	
	
	
	//以下参数是代付使用
	private String bankAccNo;//银行卡卡号
	private String bankAccName;//银行卡户名
	private String bankAccCode;//银行卡银行代码
	private String bankName;//银行卡开户行名称



	public MBOrderConfig(String orderNo, String orderAmount, String bankCode, String ordertime) {
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



	public MBOrderConfig() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String getOrdertime() {
		return ordertime;
	}


	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}


	public String getBankAccNo() {
		return bankAccNo;
	}


	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}


	public String getBankAccName() {
		return bankAccName;
	}


	public void setBankAccName(String bankAccName) {
		this.bankAccName = bankAccName;
	}


	public String getBankAccCode() {
		return bankAccCode;
	}


	public void setBankAccCode(String bankAccCode) {
		this.bankAccCode = bankAccCode;
	}


	public String getBankName() {
		return bankName;
	}


	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	
}
