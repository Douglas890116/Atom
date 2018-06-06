package com.maven.payment.yb;

/**
 * 银宝支付
 * @author Administrator
 *
 */
public class YbMerchantConfig {

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

	public YbMerchantConfig() {
		super();
		// TODO Auto-generated constructor stub
	}

	public YbMerchantConfig(String payUrl, String merNo, String merKey, String notiUrl, String returnUrl) {
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
	
	
}
