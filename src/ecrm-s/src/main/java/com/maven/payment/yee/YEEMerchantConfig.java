package com.maven.payment.yee;

public class YEEMerchantConfig {

	/**
	 * 业务类型
	 */
	private String p0_Cmd="Buy";
	/**
	 * 交易币种
	 */
	private String p4_Cur="CNY";
	/**
	 * 应答机制
	 */
	private String pr_NeedResponse="1";
	/**
	 * 存款交易请求地址
	 */
	private String saveUrl;
	/**
	 * 商户代码
	 */
	private String p1_MerId;
	/**
	 * 商户密钥
	 */
	private String keyValue;
	/**
	 * 商户接收支付成功数据的地址
	 */
	private String p8_Url;
	

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public String getP0_Cmd() {
		return p0_Cmd;
	}

	public void setP0_Cmd(String p0_Cmd) {
		this.p0_Cmd = p0_Cmd;
	}

	public String getP1_MerId() {
		return p1_MerId;
	}

	public void setP1_MerId(String p1_MerId) {
		this.p1_MerId = p1_MerId;
	}

	public String getSaveUrl() {
		return saveUrl;
	}

	public void setSaveUrl(String saveUrl) {
		this.saveUrl = saveUrl;
	}

	public String getP8_Url() {
		return p8_Url;
	}

	public void setP8_Url(String p8_Url) {
		this.p8_Url = p8_Url;
	}
	
	public String getP4_Cur() {
		return p4_Cur;
	}

	public void setP4_Cur(String p4_Cu) {
		this.p4_Cur = p4_Cu;
	}

	public String getPr_NeedResponse() {
		return pr_NeedResponse;
	}

	public void setPr_NeedResponse(String pr_NeedResponse) {
		this.pr_NeedResponse = pr_NeedResponse;
	}

	

	/**
	 * 存款所需商户参数
	 * 
	 * @author Administrator
	 *
	 */
	public enum M_Save_Paramters {
		存款接口请求地址("saveUrl"), 
		商户号("p1_MerId"), 
		商户密钥("keyValue"), 
		支付接口回调地址("p8_Url"), ;
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
}
