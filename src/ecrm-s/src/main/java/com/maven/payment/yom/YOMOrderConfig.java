package com.maven.payment.yom;

/**
 * 优付支付
 * @author Administrator
 *
 */
public class YOMOrderConfig {
	
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
	 * 商品名称，String(32)
	 */
	private String productName="A";
	/**
	 * 商品数量
	 */
	private String productNum="1"; 
	/**
	 * 商品描述
	 */
	private String productDesc="ALL";
	/**
	 * 用户IP
	 */
	private String customerIp="";
	/**
	 * 用户电话
	 */
	private String customerPhone = "0";
	/**
	 * 用户收货地址
	 */
	private String customerAddress = "A";
	/**
	 * 商户扩展信息，String(128)
	 */
	private String returnParams="";
	
	/**
	 * 签名数据
	 */
	private String sian;

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

	public YOMOrderConfig(String inputCharset, String orderNo, String orderAmount, String bankCode, String productName,
			String productNum, String productDesc, String customerIp, String customerPhone, String customerAddress,
			String returnParams, String sian) {
		super();
		this.inputCharset = inputCharset;
		this.orderNo = orderNo;
		this.orderAmount = orderAmount;
		this.bankCode = bankCode;
		this.productName = productName;
		this.productNum = productNum;
		this.productDesc = productDesc;
		this.customerIp = customerIp;
		this.customerPhone = customerPhone;
		this.customerAddress = customerAddress;
		this.returnParams = returnParams;
		this.sian = sian;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductNum() {
		return productNum;
	}

	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getCustomerIp() {
		return customerIp;
	}

	public void setCustomerIp(String customerIp) {
		this.customerIp = customerIp;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
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


	public YOMOrderConfig() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
