package com.maven.payment.jeanpay;

/**
 * 优付支付
 * @author Administrator
 *
 */
public class JEANPAYOrderConfig {
	
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
	 */
	private String bankCode;
	
	/**
	 * 支付方式
	 * 	BANK_PAY=网银支付，QUICK_PAY=快捷支付，WXPAY=微信扫码支付，ALIPAY=支付宝扫码支付
	 * 
	 */
	private String paytype;
	
	/**
	 * 
PTY_ONLINE_PAY为单笔订单支付接口；
PTY_TRADE_QUERY为单笔订单查询接口；
PTY_PAYMENT_TO_CARD为提现到银行卡接口；
PTY_PAYMENT_QUERY为提现订单查询接口；
PTY_ACCOUNT_BALANCE为账户余额查询接口；
	 */
	private String serviceName = "PTY_ONLINE_PAY";
	
	/**
	 * 签名数据
	 */
	private String sign;
	
	private String ordertime;//yyyy-MM-dd HH:mm:ss 
	
	
	//以下是付款接口必备参数
	private String bankAccountName = "";//收款银行卡户名
	private String bankAccountCardno = "";//收款银行卡号
//	private String bankProvince = "";//银行所在省份, 例如：江苏省
//	private String bankCity = "";//银行所在城市, 例如：南京市
	private String bankName = "";//银行全称，参见：提现银行代码 例如：中国工商银行
//	private String bankBranch = "";//开户行分行，例如：南京市分行
	private String bankPoint = "";//开户行支行，例如：竹山路支行


	public JEANPAYOrderConfig(String orderNo, String orderAmount, String bankCode, String serviceName, String ordertime,
			String bankAccountName, String bankAccountCardno, String bankName,
			String bankPoint) {
		super();
		this.orderNo = orderNo;
		this.orderAmount = orderAmount;
		this.bankCode = bankCode;
		this.serviceName = serviceName;
		this.ordertime = ordertime;
		this.bankAccountName = bankAccountName;
		this.bankAccountCardno = bankAccountCardno;
		this.bankName = bankName;
		this.bankPoint = bankPoint;
	}
	
	public JEANPAYOrderConfig(String orderNo, String orderAmount, String bankCode, String paytype,String sign,String serviceName,String ordertime) {
		super();
		this.orderNo = orderNo;
		this.orderAmount = orderAmount;
		this.bankCode = bankCode;
		this.sign = sign;
		this.paytype = paytype;
		this.serviceName = serviceName;
		this.ordertime = ordertime;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getServiceName() {
		return serviceName;
	}



	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
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



	public JEANPAYOrderConfig() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getPaytype() {
		return paytype;
	}


	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}


	public String getOrdertime() {
		return ordertime;
	}


	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankAccountCardno() {
		return bankAccountCardno;
	}

	public void setBankAccountCardno(String bankAccountCardno) {
		this.bankAccountCardno = bankAccountCardno;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankPoint() {
		return bankPoint;
	}

	public void setBankPoint(String bankPoint) {
		this.bankPoint = bankPoint;
	}

	
}
