package com.maven.payment.lfwx;

/**
 * 乐付微信支付
 * @author Administrator
 *
 */
public class LfwxMerchantConfig {

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
	 * 商户公钥
	 */
	private String merPubKey;
	
	/**
	 * 乐付公钥
	 */
	private String pubKey;
	
	/**
	 * 业务参数加密密⽂
	 */
	private String content;
	
	/**
	 * 签名字符串
	 */
	private String sign;
	/**
	 * 签名字符串
	 */
	private String signType = "SHA1WITHRSA";
	
	private String inputCharset = "UTF-8";
	
	/**
	 * 后台异步通知地址
	 */
	private String return_url = "";

	public LfwxMerchantConfig() {
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


	public String getInputCharset() {
		return inputCharset;
	}

	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}

	public String getMerPubKey() {
		return merPubKey;
	}

	public void setMerPubKey(String merPubKey) {
		this.merPubKey = merPubKey;
	}

	public String getPubKey() {
		return pubKey;
	}

	public void setPubKey(String pubKey) {
		this.pubKey = pubKey;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public String getSign() {
		return sign;
	}



	public void setSign(String sign) {
		this.sign = sign;
	}



	public String getSignType() {
		return signType;
	}



	public void setSignType(String signType) {
		this.signType = signType;
	}



	public String getReturn_url() {
		return return_url;
	}



	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
	
	
}
