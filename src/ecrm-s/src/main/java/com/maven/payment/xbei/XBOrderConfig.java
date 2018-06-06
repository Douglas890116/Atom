package com.maven.payment.xbei;

/**
 * 优付支付
 * @author Administrator
 *
 */
public class XBOrderConfig {
	
	/**
	 * 参数字符集编码，仅支持UTF-8
	 */
	private String inputCharset = "UTF-8";
	/**
	 * 商户订单号，[13位数时间戳，加8位会员编码，加9位随机数]，最大长度为30位
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
	private String tradeIp;
	
	private String orderDate = "";//20130102030405
	
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

	public String getSian() {
		return sian;
	}

	public void setSian(String sian) {
		this.sian = sian;
	}


	public XBOrderConfig() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getTradeIp() {
		return tradeIp;
	}


	public void setTradeIp(String tradeIp) {
		this.tradeIp = tradeIp;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	
}
