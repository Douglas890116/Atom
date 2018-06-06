package com.maven.payment.hr;

import com.maven.payment.PayInterface;

/**
 * 华仁支付
 * @author klay
 *
 */
public class HRPayMent<M extends HRMerchantConfig,O extends HROrderConfig> implements PayInterface<M,O> {

    @Override
    public String save(HRMerchantConfig merchant, HROrderConfig order) throws Exception {
        HRSave save = new HRSave();
        return save.getUrl(merchant, order);
    }

    @Override
    public String pay(M merchant, O order) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }
    
    public static void main(String[] args) {
    	PayInterface<HRMerchantConfig, HROrderConfig> __hrPay = new HRPayMent<HRMerchantConfig, HROrderConfig>();
        HRMerchantConfig __merchant = new HRMerchantConfig();
        HROrderConfig __order = new HROrderConfig();
        String RequestURL = "http://api.hyzonghe.net/";
        String bankNo = "308584000013";
        // 华仁接口公钥
        String HRPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdlfulqoHwbI5Jx3QBitq8MURZd0iAcTx+Or2LxYe/rniRxMoAOFs84KzNpML/0kmL2pzni9oXuAXDTxUsyr4DBMS78KyGQZP5HkWUbNEQZ2tMmtIWmu4Mjs0EInmfSsez5fV+OUeEjQZ9OvWdJ3fkBgOUoW9nLXYO4srQuGm+LwIDAQAB";
        // 商户公钥
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCVTdDVOFay0TWqlKXhigCqbhmvewluFMZnfsAaaaodnIkTiOG+c8YWL615tQaxNU9zXAuSTSVn5A8nKJmEQs5wbS3JYSTOwCtLrXWLrMr8W4L7kFC81bd2/w6mSHISsOrQ6a1CVUQAkIktN1/TTuEpRYhR4S7PoS01HGMy1piqAwIDAQAB";
        // 商户私钥
        String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJVN0NU4VrLRNaqUpeGKAKpuGa97CW4Uxmd+wBppqh2ciROI4b5zxhYvrXm1BrE1T3NcC5JNJWfkDycomYRCznBtLclhJM7AK0utdYusyvxbgvuQULzVt3b/DqZIchKw6tDprUJVRACQiS03X9NO4SlFiFHhLs+hLTUcYzLWmKoDAgMBAAECgYAtB7kydbmAWSTse8TED1FAFdDdYihn8RAd6taZoMDUCYA2ShR70oMt8ddKW9TW4ZNC4cIDsAzWFqyTTOVwRI3qWGxlxkJl5EnFu4hcWXFBZT9aQ4pZOMXcpZAadkk3lrintaNPsZXhqObHpxoFDkzQpPpr9AIu9VAAfP49ZW1DoQJBANGTtTHFt9/Qup97yb/u67SRCPFA2Pf1ensPUIcmhI90oVHSYbwsc6jLqXRTyRCm/ZnnM9T5jmRu1anc8rwUQ/sCQQC2YEK5brucahUQzD9TFAfdTlMMAJ+SPH1QJ9jYbkRVa4i/a/FzBR4BCrl8hGjWQQXNhc2w2JzqLZpqbugdfMuZAkAQhFCabJeyNvQOT6Y1zzGaWHfY86Bl4l3Vxv40uI9n8uwn06nKN8KhwfNH7LaC7nY8I+GM3mIffjCuo3Ap7HrzAkAVGs6d5tKPJzeI2hn54zeFxKqXmPreUWGvBO1zHk+KEwegHz2xscXnGPaeEjSPlra1Mea7sFV4RA66glsaDncBAkBO16bjDVgfCDGjEM+mzAERDuxSYWnoJJoYtzv+i6CeFqgaq96RoYVqo+RjgNumiSeTVr1JzXhBPsQooRJP7rJy";                
        // 签名MD5Key
        String md5Key = "MyXb8HZ";
        // 商户号
        String merNo = "110102";
        try {
            __merchant.setMerNo(merNo);
            __merchant.setPayUrl("http://api.hr-pay.com/PayInterface.aspx");
            __merchant.setNotiUrl(RequestURL + "/TPayment/success");
            __merchant.setReturnUrl(RequestURL + "TPayment/HRPayCallback");
            __merchant.setHRRsaPublicKey(HRPublicKey);
            __merchant.setMerRsaPublicKey(publicKey);
            __merchant.setMerRsaPrivateKey(privateKey);
            __merchant.setMd5Key(md5Key);

            __order.setV_oid(HRAppConstants.getOderNumber(merNo));
            __order.setV_rcvname("收货人姓名");
            __order.setV_rcvaddr("收货人地址");
            __order.setV_rcvtel("12345678911");
            __order.setV_goodsname("商品名称");// 必填参数，随便写
            __order.setV_goodsdescription("商品描述");// 必填参数，随便写
            __order.setV_rcvpost("收货人邮政编码");
            __order.setV_qq("收货人QQ");
            __order.setV_amount( "10");
            __order.setV_ordername("订货人姓名");
            __order.setV_bankno(bankNo);
            __order.setV_moneytype("0");//货币类型：0-人民币，1-美元

            String url = __hrPay.save(__merchant, __order);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}