package com.maven.payment.sdp2p;

/**
 * SD微信支付
 * @author Administrator
 *
 */
public class SDP2PMerchantConfig {

	
	private String LoginAccount;//操作员号
	private String backurl;//异步通知地址
	private String backurlbrowser;//同步返回地址
	
	private String key1;
	private String key2;
	private String md5key;
	
	private String WebServiceUrl;
	
	
	public SDP2PMerchantConfig() {
	}




	public String getLoginAccount() {
		return LoginAccount;
	}




	public void setLoginAccount(String loginAccount) {
		LoginAccount = loginAccount;
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




	public String getWebServiceUrl() {
		return WebServiceUrl;
	}




	public void setWebServiceUrl(String webServiceUrl) {
		WebServiceUrl = webServiceUrl;
	}
	
	
	
	
}
