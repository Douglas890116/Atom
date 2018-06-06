package com.maven.payment.gst;

/**
 * 国盛通支付
 * @author Administrator
 *
 */
public class GSTOrderConfig {
	
	/**
	 * 参数字符集编码，仅支持UTF-8
	 */
	private String inputCharset = "UTF-8";
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
	 * 用户IP
	 */
	private String customerIp="";
	/**
	 * 商户扩展信息，String(128)
	 */
	private String returnParams="";
	
	/**
	 * 支付方式
	 * 	1：网银支付
		2：微信
		3：支付宝
	 */
	private String paytype;
	/**
	 * 签名数据
	 */
	private String sian;
	
	private String ordertime;//yyyy-MM-dd HH:mm:ss 

	/**
	 * 存款订单参数
	 * 
	 * @author Administrator
	 *
	 */
	public enum O_Save_Paramters {
		银行编码("bankCode"), 订单号("orderNo"), 订单金额("orderAmount"), ;
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

	public GSTOrderConfig(String inputCharset, String orderNo, String orderAmount, String bankCode, String customerIp,  String paytype,
			String returnParams, String sian) {
		super();
		this.inputCharset = inputCharset;
		this.orderNo = orderNo;
		this.orderAmount = orderAmount;
		this.bankCode = bankCode;
		this.customerIp = customerIp;
		this.returnParams = returnParams;
		this.sian = sian;
		this.paytype = paytype;
	}
	
	
	public String getInputCharset() {
		return inputCharset;
	}

	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
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

	public String getCustomerIp() {
		return customerIp;
	}

	public void setCustomerIp(String customerIp) {
		this.customerIp = customerIp;
	}

	public String getReturnParams() {
		return returnParams;
	}

	public void setReturnParams(String returnParams) {
		this.returnParams = returnParams;
	}

	public String getSian() {
		return sian;
	}

	public void setSian(String sian) {
		this.sian = sian;
	}


	public GSTOrderConfig() {
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

	
}
