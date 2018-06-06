package com.maven.payment.xbei;

/**
 * 新贝支付
 * @author Administrator
 *
 */
public class XBMerchantConfig {

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
	/**
	 * 网关版本号
	 */
	private String version = "V1.0";
	
	/**
	 * 存款所需商户参数
	 * 
	 * @author Administrator
	 *
	 */
	public enum M_Save_Paramters {
		存款接口请求地址("payUrl"), 
		商户号("merNo"), 
		商户密钥("merKey"), 
		支付接口回调地址("notiUrl"), 
		网关版本号("version"),;
		public String value;

		private M_Save_Paramters(String value) {
			this.value = value;
		}

		public static String[] paramters() {
			M_Save_Paramters[] s = M_Save_Paramters.values();
			String[] ss = new String[s.length];
			for (int i = 0; i < s.length; i++) {
				ss[i] = s[i].value;
			}
			return ss;
		}
	}

	public XBMerchantConfig(String payUrl, String merNo, String merKey, String notiUrl, String returnUrl) {
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




	public XBMerchantConfig() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String getVersion() {
		return version;
	}



	public void setVersion(String version) {
		this.version = version;
	}

	
}
