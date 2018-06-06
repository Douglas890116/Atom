package com.maven.payment.leying;

public class LEYINGMerchantConfig {
	
	/**
	 * 版本
	 */
	private String version="1.0";
	
	/**
	 * 商户ID
	 */
	private String partnerID;
	
	/**
	 * 存款地址
	 */
	private String saveUrl;
	
	/**
	 * 显示用回调地址
	 */
	private String returnUrl;
	
	/**
	 * 商户处理结果的通知地址
	 */
	private String noticeUrl;
	
	/**
	 * 报文编码格式固定值1 ：表示UTF-8;
	 */
	private String charset="1";
	
	/**
	 * 选择报文签名类型，值为1或者2.
	 * 1：RSA 方式
	 * 2：MD5 方式
	 */
	private String signType="2";
	
	/**
	 * 付款方支付方式
	 */
	private String payType="ALL";
	
	
	/**
	 * MD5Key
	 */
	private String md5key;
	
	/**
     * 存款所需商户参数
     * @author Administrator
     *
     */
    public enum M_Save_Paramters{
    	商户ID("partnerID"),
    	存款地址("saveUrl"),
    	显示用回调地址("returnUrl"),
    	商户处理结果通知地址("noticeUrl"),
    	MD5Key("md5key"),
    	;
		public String value;
		private M_Save_Paramters(String value){
			this.value=value;
		}
		public static String[] paramters(){
			M_Save_Paramters[] s = M_Save_Paramters.values();
			String[] ss = new String[s.length];
			for (int i = 0 ;i<s.length;i++) {
				ss[i] = s[i].value;
			}
			return ss;
		}
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPartnerID() {
		return partnerID;
	}

	public void setPartnerID(String partnerID) {
		this.partnerID = partnerID;
	}

	public String getSaveUrl() {
		return saveUrl;
	}

	public void setSaveUrl(String saveUrl) {
		this.saveUrl = saveUrl;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getNoticeUrl() {
		return noticeUrl;
	}

	public void setNoticeUrl(String noticeUrl) {
		this.noticeUrl = noticeUrl;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getMd5key() {
		return md5key;
	}

	public void setMd5key(String md5key) {
		this.md5key = md5key;
	}
	
	
	
	
}
