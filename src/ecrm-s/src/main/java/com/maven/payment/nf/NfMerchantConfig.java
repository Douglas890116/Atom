package com.maven.payment.nf;

/**
 * 牛付支付
 * @author Administrator
 *
 */
public class NfMerchantConfig {

	private String version = "v1";
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
	private String notifyUrl;
	
	/**
	 * 页面跳转地址
	 */
	private String pageUrl;
	

	public NfMerchantConfig() {
		super();
		// TODO Auto-generated constructor stub
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


	public String getNotifyUrl() {
		return notifyUrl;
	}


	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}


	public String getPageUrl() {
		return pageUrl;
	}


	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}


	public NfMerchantConfig(String payUrl, String merNo, String merKey, String notifyUrl, String pageUrl) {
		super();
		this.payUrl = payUrl;
		this.merNo = merNo;
		this.merKey = merKey;
		this.notifyUrl = notifyUrl;
		this.pageUrl = pageUrl;
	}


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}



	
}
