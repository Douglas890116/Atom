package com.maven.payment.th;

public class THMerchantConfig{

	/**
	 * 字符集编码，取值UTF-8或者GBK
	 */
	private  String CHARSET = "UTF-8"; 
	/**
	 * 存款地址
	 */
    private  String saveUrl;
    /**
     * 支付地址
     */
    private String payUrl;
    /**
     * 商户号
     */
    private  String merchantCode ;
    /**
     * 私钥key
     */
    private  String merchantKey ;
    /**
     * 通知当前支付是否成功的URL
     */
    private  String callbackUrl ;
    /**
     * 页面跳转到商户页面的URL
     */
    private  String pageToUserUrl ;
    /**
     * 支付方式,目前暂只支持网银支付，取值为1
     */
    private  String payType; 
    /**
     * 提交请求网站的域名，防钓鱼 
     */
    private  String referer ;
    /**
     * 回传参数
     */
    private String returnParams;
    
    public enum Enum_payType{
    	网银支付("1","网银支付");
		public String value;
		public String desc;
		private Enum_payType(String value,String desc){
			this.value=value;
			this.desc=desc;
		}
	}
    
    /**
     * 存款所需商户参数
     * @author Administrator
     *
     */
    public enum M_Save_Paramters{
    	存款接口请求地址("saveUrl"),
    	商户号("merchantCode"),
    	商户密钥("merchantKey"),
    	支付接口回调地址("callbackUrl"),
    	支付方式("payType"),
    	请求网站域名("referer"),
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
    
    /**
     * 取款所需商户参数
     * @author Administrator
     *
     */
    public enum M_Pay_Paramters{
    	取款接口请求地址("payUrl"),
    	商户号("merchantCode"),
    	商户密钥("merchantKey"),
    	;
		public String value;
		private M_Pay_Paramters(String value){
			this.value=value;
		}
		public static String[] paramters(){
			M_Pay_Paramters[] s = M_Pay_Paramters.values();
			String[] ss = new String[s.length];
			for (int i = 0 ;i<s.length;i++) {
				ss[i] = s[i].value;
			}
			return ss;
		}
	}
    
    /**
     * 编译的字符集编码
     * @return
     */
	public String getCHARSET() {
		return CHARSET;
	}
	/**
	 * 编译的字符集编码
	 * @param cHARSET
	 */
	public void setCHARSET(String cHARSET) {
		CHARSET = cHARSET;
	}
	/**
	 * 存款地址
	 * @return
	 */
	public String getSaveUrl() {
		return saveUrl;
	}
	/**
	 * 存款地址
	 * @param saveUrl
	 */
	public void setSaveUrl(String saveUrl) {
		this.saveUrl = saveUrl;
	}
	/**
	 * 支付地址
	 * @return
	 */
	public String getPayUrl() {
		return payUrl;
	}
	/**
	 * 支付地址
	 * @param payUrl
	 */
	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}
	/**
	 * 商户号
	 * @return
	 */
	public String getMerchantCode() {
		return merchantCode;
	}
	/**
	 * 商户号
	 * @param merchantCode
	 */
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	/**
	 * 商户密钥
	 * @return
	 */
	public String getMerchantKey() {
		return merchantKey;
	}
	/**
	 * 商户密钥
	 * @param merchantKey
	 */
	public void setMerchantKey(String merchantKey) {
		this.merchantKey = merchantKey;
	}
	/**
	 * 支付接口回调地址
	 * @return
	 */
	public String getCallbackUrl() {
		return callbackUrl;
	}
	/**
	 *  支付接口回调地址
	 * @param callbackUrl
	 */
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	/**
	 * 支付成功跳转地址
	 * @return
	 */
	public String getPageToUserUrl() {
		return pageToUserUrl;
	}
	/**
	 * 支付成功跳转地址
	 * @param pageToUserUrl
	 */
	public void setPageToUserUrl(String pageToUserUrl) {
		this.pageToUserUrl = pageToUserUrl;
	}
	/**
	 * 支付方式
	 * @return
	 */
	public String getPayType() {
		return payType;
	}
	/**
	 * 支付方式
	 * @param payType
	 */
	public void setPayType(String payType) {
		this.payType = payType;
	}
	/**
	 * 提交请求网站的域名，防钓鱼 
	 * @return
	 */
	public String getReferer() {
		return referer;
	}
	/**
	 * 提交请求网站的域名，防钓鱼
	 * @param referer
	 */
	public void setReferer(String referer) {
		this.referer = referer;
	}
	/**
	 * 回传参数
	 * @return
	 */
	public String getReturnParams() {
		return returnParams;
	}
	/**
	 * 回传参数
	 * @param returnParams
	 */
	public void setReturnParams(String returnParams) {
		this.returnParams = returnParams;
	}
    
}
