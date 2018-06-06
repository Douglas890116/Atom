package com.maven.payment.jft;

/**
 * 优付支付
 * @author Administrator
 *
 */
public class JFTMerchantConfig {

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
	 * 商户支付来源域名，String(40)
	 */
	private String refererUrl;
	
	private String payType = "DTDP";//支付类型固定填写  DTDP
	private String curCode = "RMB";//固定填写  RMB 
	private String productInfo = "productInfo";//
	private String remark = "remark";
	private String signType = "MD5";
	
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
		商户支付来源域名("returnUrl"),;
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

	public JFTMerchantConfig(String payUrl, String merNo, String merKey, String notiUrl, String returnUrl,
			String refererUrl) {
		super();
		this.payUrl = payUrl;
		this.merNo = merNo;
		this.merKey = merKey;
		this.notiUrl = notiUrl;
		this.returnUrl = returnUrl;
		this.refererUrl = refererUrl;
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

	public String getRefererUrl() {
		return refererUrl;
	}

	public void setRefererUrl(String refererUrl) {
		this.refererUrl = refererUrl;
	}



	public JFTMerchantConfig() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String getPayType() {
		return payType;
	}



	public String getCurCode() {
		return curCode;
	}



	public String getProductInfo() {
		return productInfo;
	}



	public String getRemark() {
		return remark;
	}



	public String getSignType() {
		return signType;
	}

	public static void main(String[] args) {
		try {
			java.net.URL  url = new  java.net.URL("http://0012.321dw.com/member-deposit.html?MerId=5900696&OrdId=DD20815466154265A8F48D3EAE56EF3C&OrdAmt=2.00&OrdNo=DTDP2017022010423378739075&ResultCode=success002&Remark=remark&SignType=MD5&SignInfo=95b25f83d21dbb5953832ea2dea9e8d9");
			String host = url.getHost();// 获取主机名 
			String port = url.getPort() + "" ;
			String domain = "";
			if(port.equals("-1")) {//不带端口
				domain = url.getProtocol()+"://"+host+"/";
			} else {
				domain = url.getProtocol()+"://"+host+":"+port+"/";
			}
			
			System.out.println(domain);
			System.out.println(port);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
