package com.maven.payment.sdwx;

/**
 * SD微信支付
 * @author Administrator
 *
 */
public class SDWXMerchantConfig {

	private String unit="1";//货币单位 人民币
	private String language="zh-cn";
	
	private String merchantid;
	private String backurl;//异步通知地址
	private String backurlbrowser;//同步返回地址
	
	private String payUrl6006;//付款地址
	private String payUrl6009;//付款地址
	private String payUrl6010;//付款地址
	private String key1;
	private String key2;
	private String md5key;
	
	/**
	 * 以下是出款使用
	 */
	private String payUrl;//出款接口的付款地址
	
			
	/**
	 * 出款接口必须使用的
	 * @param merchantid
	 * @param key1
	 * @param key2
	 * @param md5key
	 * @param payUrl
	 */
	public SDWXMerchantConfig(String merchantid, String key1, String key2,  String payUrl) {
		super();
		this.merchantid = merchantid;
		this.key1 = key1;
		this.key2 = key2;
		this.payUrl = payUrl;
	}

	public SDWXMerchantConfig(String merchantid, String backurl, String backurlbrowser, String payUrl6006,
			String payUrl6009, String payUrl6010, String key1, String key2, String md5key) {
		super();
		this.merchantid = merchantid;
		this.backurl = backurl;
		this.backurlbrowser = backurlbrowser;
		this.payUrl6006 = payUrl6006;
		this.payUrl6009 = payUrl6009;
		this.payUrl6010 = payUrl6010;
		this.key1 = key1;
		this.key2 = key2;
		this.md5key = md5key;
	}
	
	public SDWXMerchantConfig() {
	}
	
	
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getMerchantid() {
		return merchantid;
	}
	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getBackurl() {
		return backurl;
	}
	public void setBackurl(String backurl) {
		this.backurl = backurl;
	}
	public String getBackurlbrowser() {
		return backurlbrowser;
	}
	public void setBackurlbrowser(String backurlbrowser) {
		this.backurlbrowser = backurlbrowser;
	}

	public String getPayUrl6006() {
		return payUrl6006;
	}

	public void setPayUrl6006(String payUrl6006) {
		this.payUrl6006 = payUrl6006;
	}

	public String getPayUrl6009() {
		return payUrl6009;
	}

	public void setPayUrl6009(String payUrl6009) {
		this.payUrl6009 = payUrl6009;
	}

	public String getPayUrl6010() {
		return payUrl6010;
	}

	public void setPayUrl6010(String payUrl6010) {
		this.payUrl6010 = payUrl6010;
	}

	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public String getKey2() {
		return key2;
	}

	public void setKey2(String key2) {
		this.key2 = key2;
	}

	public String getMd5key() {
		return md5key;
	}

	public void setMd5key(String md5key) {
		this.md5key = md5key;
	}

	public String getPayUrl() {
		return payUrl;
	}

	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}
	
	
	
}
