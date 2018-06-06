package com.maven.payment.jh2;
/**
 * 聚合支付 - 金塔
 * @author zack
 *
 */
public class JH2MerchantConfig {
	private String payKey;
	private String merKey;
	private String payUrl;
	private String returnUrl;
	private String notifyUrl;
	private String productType;
	
	public String getPayKey() {
		return payKey;
	}
	public void setPayKey(String payKey) {
		this.payKey = payKey;
	}
	public String getMerKey() {
		return merKey;
	}
	public void setMerKey(String merKey) {
		this.merKey = merKey;
	}
	public String getPayUrl() {
		return payUrl;
	}
	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
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
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
}