package com.maven.payment.kk;

public class KKMerchantConfig {
	/** 商户支付Key */
	private String payKey;
	/** 支付类型 */
	private String productType;
	/** 支付接口地址 */
	private String payUrl;
	/** MD5签名Key */
	private String md5Key;
	/** 支付完后返回地址 */
	private String returnUrl;
	/** 回调地址 */
	private String notifyUrl;
	
	/*========== 商户微信公众号信息 ==========*/
	private String WXappid;
	private String WXappSecret;
	private String WXcallbackUrl;
	private String WXcodeUrl;
	private String WXopenIdUrl;
	
	public String getPayKey() {
		return payKey;
	}
	public void setPayKey(String payKey) {
		this.payKey = payKey;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getPayUrl() {
		return payUrl;
	}
	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}
	public String getMd5Key() {
		return md5Key;
	}
	public void setMd5Key(String md5Key) {
		this.md5Key = md5Key;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getWXappid() {
		return WXappid;
	}
	public void setWXappid(String wXappid) {
		WXappid = wXappid;
	}
	public String getWXappSecret() {
		return WXappSecret;
	}
	public void setWXappSecret(String wXappSecret) {
		WXappSecret = wXappSecret;
	}
	public String getWXcallbackUrl() {
		return WXcallbackUrl;
	}
	public void setWXcallbackUrl(String wXcallbackUrl) {
		WXcallbackUrl = wXcallbackUrl;
	}
	public String getWXcodeUrl() {
		return WXcodeUrl;
	}
	public void setWXcodeUrl(String wXcodeUrl) {
		WXcodeUrl = wXcodeUrl;
	}
	public String getWXopenIdUrl() {
		return WXopenIdUrl;
	}
	public void setWXopenIdUrl(String wXopenIdUrl) {
		WXopenIdUrl = wXopenIdUrl;
	}
}