package com.maven.payment.mb;

/**
 * 摩宝支付
 * @author Administrator
 *
 */
public class MBMerchantConfig {

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
	
	
	private String apiVersion = "1.0.0.0";
	
	/**
	 * 合作伙伴ID 
	 */
	private String platformID = "";
	
	/**
	 * 接口名字 WEB方式：“WEB_PAY_B2C”（兼容PC和手机浏览器）
	 */
	private String apiName = "WEB_PAY_B2C";
	
	/**
	 * 
	 * 不进行签名，根据选择的支付方式直接对应页面。不输入或选择支付方式不存在则认为是该商户所拥有的全部方式。
1.网银
2.一键支付
3非银行支付
4 支付宝扫描
5微信扫码
	 */
	private String choosePayType = "";
	

	public MBMerchantConfig(String payUrl, String merNo, String merKey, String notiUrl, String platformID, String apiName) {
		super();
		this.payUrl = payUrl;
		this.merNo = merNo;
		this.merKey = merKey;
		this.notiUrl = notiUrl;
		this.platformID = platformID;
		this.apiName = apiName;
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



	public MBMerchantConfig() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String getApiVersion() {
		return apiVersion;
	}



	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}



	public String getPlatformID() {
		return platformID;
	}



	public void setPlatformID(String platformID) {
		this.platformID = platformID;
	}



	public String getApiName() {
		return apiName;
	}



	public void setApiName(String apiName) {
		this.apiName = apiName;
	}



	public String getChoosePayType() {
		return choosePayType;
	}



	public void setChoosePayType(String choosePayType) {
		this.choosePayType = choosePayType;
	}


	
}
