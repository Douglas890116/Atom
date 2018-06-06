package com.maven.payment.jeanpay;

/**
 * jeanpay极付
 * @author Administrator
 *
 */
public class JEANPAYMerchantConfig {

	/**
	 * 存款交易请求地址
	 */
	private String payUrl;
	/**
	 * 商户号
	 */
	private String merNo;
	/**
	 * 商户密钥
	 */
	private String merKey;
	/**
	 * 商户接收支付成功数据的地址
	 */
	private String notiUrl;
	
	/**
	 * 用户支付完毕返回的地址
	 */
	private String returnUrl = "";
	
	
	private String version = "V3.5.0";
	
	private String signType = "MD5";
	

	public JEANPAYMerchantConfig(String payUrl, String merNo, String merKey, String notiUrl, String returnUrl) {
		super();
		this.payUrl = payUrl;
		this.merNo = merNo;
		this.merKey = merKey;
		this.notiUrl = notiUrl;
		this.returnUrl = returnUrl;
	}
	
	
	
	public String getPayUrl() {
		return payUrl;
	}

	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}

	public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}

	public String getMerKey() {
		return merKey;
	}

	public void setMerKey(String merKey) {
		this.merKey = merKey;
	}

	public String getNotiUrl() {
		return notiUrl;
	}

	public void setNotiUrl(String notiUrl) {
		this.notiUrl = notiUrl;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}



	public JEANPAYMerchantConfig() {
		super();
		// TODO Auto-generated constructor stub
	}



	



	public String getVersion() {
		return version;
	}



	public void setVersion(String version) {
		this.version = version;
	}



	public String getSignType() {
		return signType;
	}



	public void setSignType(String signType) {
		this.signType = signType;
	}

	
}
