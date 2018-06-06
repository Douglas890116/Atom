package com.maven.payment.jh2.df;

public class JH2DFMerchantConfig {
	private String payKey;
	private String merKey;
	private String payUrl;
	private String proxyType;
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
	public String getProxyType() {
		return proxyType;
	}
	public void setProxyType(String proxyType) {
		this.proxyType = proxyType;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
}