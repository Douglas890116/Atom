package com.maven.payment.hr;

/**
 * 华仁支付 商户信息
 * 
 * @author klay
 *
 */
public class HRMerchantConfig {
    /**
     * 存款交易请求地址
     */
    private String payUrl;
    /**
     * 商户号
     */
    private String merNo;
    
    /**
     * 华仁RSA：公匙
     * 我方使用，用于加密需要提交的参数
     */
    private String HRRsaPublicKey;
    /**
     * MD5加密签名的key
     */
    private String md5Key;
    /**
     * 商户RSA：公钥钥
     * 对方使用，用于加密回调的参数
     */
    private String merRsaPublicKey;
    /**
     * 商户RSA：私钥
     * 我方使用，用于解密回调的参数
     * 
     */
    private String merRsaPrivateKey;
    /**
     * 支付结束后的返回地址
     */
    private String notiUrl;
    /**
     * 支付结果的回调地址
     */
    private String returnUrl;
    
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
    public String getHRRsaPublicKey() {
        return HRRsaPublicKey;
    }
    public void setHRRsaPublicKey(String hRRsaPublicKey) {
        HRRsaPublicKey = hRRsaPublicKey;
    }
    public String getMd5Key() {
        return md5Key;
    }
    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key;
    }
    public String getMerRsaPublicKey() {
        return merRsaPublicKey;
    }
    public void setMerRsaPublicKey(String merRsaPublicKey) {
        this.merRsaPublicKey = merRsaPublicKey;
    }
    public String getMerRsaPrivateKey() {
        return merRsaPrivateKey;
    }
    public void setMerRsaPrivateKey(String merRsaPrivateKey) {
        this.merRsaPrivateKey = merRsaPrivateKey;
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
}