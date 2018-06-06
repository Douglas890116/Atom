package com.maven.payment.hr.df;

import java.util.HashMap;
import java.util.Map;

import com.hy.pull.common.util.Encrypt;
import com.maven.payment.hr.HRAppConstants;
import com.maven.payment.hr.RSAClient;
import com.maven.util.JSONUnit;

import net.sf.json.JSONObject;

/**
 * 华仁代付
 * @author klay
 *
 */
public class HRDFSave {
    /**
     * 华仁代付接口 协议包编码
     */
    private static final String PAGE_CODE = "1005";
    public HRDFSave() {}
    /**
     * 获取支付请求URL
     * @param merchant
     * @param order
     * @return
     * @throws Exception 
     */
    public String getUrl(HRDFMerchantConfig merchant, HRDFOrderConfig order) throws Exception {
        String sign = getRequestSign(merchant, order);
        JSONObject data = new JSONObject();
        data.put(HRAppConstants.p1_v_pagecode, HRDFSave.PAGE_CODE);
        data.put(HRDFAppConstants.p1_v_mid, merchant.getMerchantCode());
        data.put(HRDFAppConstants.p1_v_oid, order.getV_oid());
        data.put(HRDFAppConstants.p1_v_amount, order.getV_amount());
        data.put(HRDFAppConstants.p1_v_payeename, order.getV_payeename());
        data.put(HRDFAppConstants.p1_v_payeecard, order.getV_payeecard());
        data.put(HRDFAppConstants.p1_v_accountprovince, order.getV_accountprovince());
        data.put(HRDFAppConstants.p1_v_accountcity, order.getV_accountcity());
        data.put(HRDFAppConstants.p1_v_bankname, order.getV_bankname());
        data.put(HRDFAppConstants.p1_v_bankno, order.getV_bankno());
        data.put(HRDFAppConstants.p1_v_ymd, order.getV_oid().split("-")[0]);
        data.put(HRDFAppConstants.p1_v_url, merchant.getReturnUrl());
        data.put(HRDFAppConstants.p1_v_md5info, sign);
        String originalData = "[" + data.toString() + "]";// C#服务器需要
        System.out.println("数据原文：\n" + originalData);
        System.out.println("华仁加密公钥：\n" + merchant.getHRRsaPublicKey());
        String rsa = RSAClient.encode(originalData, merchant.getHRRsaPublicKey());// 使用华仁的公钥进行RSA加密
        System.out.println("rsa密文：\n" + rsa);
        
        StringBuffer url = new StringBuffer(merchant.getPayUrl());
        url.append("?state=0&mid=").append(merchant.getMerchantCode())
        .append("&data=").append(rsa);
        System.out.println("请求地址：\n" + url.toString());
        return url.toString();
    }
    /**
     * MD5签名
     * @param merchant
     * @param order
     * @return
     */
    public static String getRequestSign(HRDFMerchantConfig merchant, HRDFOrderConfig order) {
//        v_md5info是所有列的值进行加密得到的（不包含本身），不能有空格。所有参与加密的参数加上v_MD5key进行MD5加密生成签名串v_sign，按照文档的顺序进行加密。
//        对data参数进行rsa加密：
//        1、请使用我们提供的加密密钥生成器，生成商户的公钥和私钥；
//        2、公钥提供给我们，我们将用这个公钥进行数据加密，发送给你们，你们用你们的私钥解密，
//        3、当然，我们也会提供我们的公钥给你们，你们的数据用我们的公钥加密，发送给我们。
//        4、加密是吧json串整体加密，解密也是解密整体json串，就是data的值是rsa加密的。
        StringBuffer sb = new StringBuffer();
        sb.append(HRDFAppConstants.p1_v_pagecode).append(HRDFSave.PAGE_CODE)
        .append(HRDFAppConstants.p1_v_mid).append(merchant.getMerchantCode())
        .append(HRDFAppConstants.p1_v_oid).append(order.getV_oid())
        .append(HRDFAppConstants.p1_v_amount).append(order.getV_amount())
        .append(HRDFAppConstants.p1_v_payeename).append(order.getV_payeename())
        .append(HRDFAppConstants.p1_v_payeecard).append(order.getV_payeecard())
        .append(HRDFAppConstants.p1_v_accountprovince).append(order.getV_accountprovince())
        .append(HRDFAppConstants.p1_v_accountcity).append(order.getV_accountcity())
        .append(HRDFAppConstants.p1_v_bankname).append(order.getV_bankname())
        .append(HRDFAppConstants.p1_v_bankno).append(order.getV_bankno())
        .append(HRDFAppConstants.p1_v_ymd).append(order.getV_oid().split("-")[0])
        .append(HRDFAppConstants.p1_v_url).append(merchant.getReturnUrl());
        return Encrypt.MD5(sb.toString().concat(merchant.getMd5Key()));
    }
    /**
     * 结果通知回参做签名校验
     * @param request
     * @param md5Key 签名用的md5Key
     * @return
     */
    public static boolean checkResponseSign(String params) {
        if (params.startsWith("[") && params.endsWith("]"))
            params = params.substring(1, params.length() - 1);
        Map<String, String> data = new HashMap<String, String>();
        data = JSONUnit.getMapFromJsonNew(params);
        String pagecode = data.get(HRDFAppConstants.p2_v_pagecode);
        String mid = data.get(HRDFAppConstants.p2_v_mid);
        String oid = data.get(HRDFAppConstants.p2_v_oid);
        String orderid = data.get(HRDFAppConstants.p2_v_orderid);
        String amount = data.get(HRDFAppConstants.p2_v_amount);
        String status = data.get(HRDFAppConstants.p2_v_status);
        String sign = data.get(HRDFAppConstants.p2_v_sign);
        
        StringBuffer sb = new StringBuffer();
        sb.append(HRDFAppConstants.p2_v_pagecode).append(pagecode)
        .append(HRDFAppConstants.p2_v_mid).append(mid)
        .append(HRDFAppConstants.p2_v_oid).append(oid)
        .append(HRDFAppConstants.p2_v_orderid).append(orderid)
        .append(HRDFAppConstants.p2_v_amount).append(amount)
        .append(HRDFAppConstants.p2_v_status).append(status);
        
        String encrypt = Encrypt.MD5(sb.toString().concat(RSAClient.MD5Key));
        return encrypt.equals(sign);
    }
}