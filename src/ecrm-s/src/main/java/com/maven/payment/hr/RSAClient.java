package com.maven.payment.hr;

import java.net.URLDecoder;
import java.util.Map;

import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.maven.util.JSONUnit;
import com.maven.util.RSACoder;

public class RSAClient {
    /** MD5加密串 **/
    public static String MD5Key = "MyXb8HZ";

    public static void main(String[] args) throws Exception {
        // 华仁接口公钥
//        String HRPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdlfulqoHwbI5Jx3QBitq8MURZd0iAcTx+Or2LxYe/rniRxMoAOFs84KzNpML/0kmL2pzni9oXuAXDTxUsyr4DBMS78KyGQZP5HkWUbNEQZ2tMmtIWmu4Mjs0EInmfSsez5fV+OUeEjQZ9OvWdJ3fkBgOUoW9nLXYO4srQuGm+LwIDAQAB";
        // 公钥
//        String publicKey   = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCVTdDVOFay0TWqlKXhigCqbhmvewluFMZnfsAaaaodnIkTiOG+c8YWL615tQaxNU9zXAuSTSVn5A8nKJmEQs5wbS3JYSTOwCtLrXWLrMr8W4L7kFC81bd2/w6mSHISsOrQ6a1CVUQAkIktN1/TTuEpRYhR4S7PoS01HGMy1piqAwIDAQAB";
        // 私钥
        String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJVN0NU4VrLRNaqUpeGKAKpuGa97CW4Uxmd+wBppqh2ciROI4b5zxhYvrXm1BrE1T3NcC5JNJWfkDycomYRCznBtLclhJM7AK0utdYusyvxbgvuQULzVt3b/DqZIchKw6tDprUJVRACQiS03X9NO4SlFiFHhLs+hLTUcYzLWmKoDAgMBAAECgYAtB7kydbmAWSTse8TED1FAFdDdYihn8RAd6taZoMDUCYA2ShR70oMt8ddKW9TW4ZNC4cIDsAzWFqyTTOVwRI3qWGxlxkJl5EnFu4hcWXFBZT9aQ4pZOMXcpZAadkk3lrintaNPsZXhqObHpxoFDkzQpPpr9AIu9VAAfP49ZW1DoQJBANGTtTHFt9/Qup97yb/u67SRCPFA2Pf1ensPUIcmhI90oVHSYbwsc6jLqXRTyRCm/ZnnM9T5jmRu1anc8rwUQ/sCQQC2YEK5brucahUQzD9TFAfdTlMMAJ+SPH1QJ9jYbkRVa4i/a/FzBR4BCrl8hGjWQQXNhc2w2JzqLZpqbugdfMuZAkAQhFCabJeyNvQOT6Y1zzGaWHfY86Bl4l3Vxv40uI9n8uwn06nKN8KhwfNH7LaC7nY8I+GM3mIffjCuo3Ap7HrzAkAVGs6d5tKPJzeI2hn54zeFxKqXmPreUWGvBO1zHk+KEwegHz2xscXnGPaeEjSPlra1Mea7sFV4RA66glsaDncBAkBO16bjDVgfCDGjEM+mzAERDuxSYWnoJJoYtzv+i6CeFqgaq96RoYVqo+RjgNumiSeTVr1JzXhBPsQooRJP7rJy";
//        String dataStr = "{\"v_pagecode\":\"1001\",\"v_mid\":\"110102\",\"v_oid\":\"20170514-110102-150945\",\"v_rcvname\":\"收货人名称\",\"v_rcvaddr\":\"地址\",\"v_rcvtel\":\"13513512345\",\"v_goodsname\":\"商品\",\"v_goodsdescription\":\"描述\",\"v_rcvpost\":\"10021\",\"v_qq\":\"1254545\",\"v_amount\":\"12\",\"v_ymd\":\"20170514\",\"v_orderstatus\":\"1\",\"v_ordername\":\"定货人名称\",\"v_bankno\":\"105100000017\",\"v_moneytype\":\"0\",\"v_url\":\"http://api.hyzonghe.net/TPayment/HRPayCallback\",\"v_md5info\":\"fdefce6ae46c8523ae63bb6127d8b8c9\"}";
//        System.out.println("加密前原文：\n" + dataStr);
//        System.out.println("==================================================");
//        String encodedData = RSAClient.encode(dataStr, publicKey);
        
        String encodedStr = "jOyMYkswBWg0xZeFj8542CxrqsOSHlBBsyAO4v9KQXD1NhPii1oDHgPxMNF64qU3Xb4t9MlPNwJCgWOHFCT8rRddZ+48CHkgmv94htCSnC/sBT1NKVdZC40IlomXfOC89NRwEte+kSHC46qvblFslpTwLY5tw12HQGo8edZ776Q=@DW+HTQGL5NAtvettVadfZDsgKNrPy29vno+H48bfSil/dIrUddw1zOPwFDjecYf1WsDsH/Erz7XG1w2NNoi45MwIFQcA11j3+tkmB2/NhNcdkQ+sGZr6Lbh4Gse0PuW/t5r9CsgcQ4RDQ+W5vehjzqxrAJPPRmndfJ862BmkVhI=@T6BwMvhChgWgQOmXeFWxo5HW6molXc+ys0b9+P6cxYNhcg5Qt7OIPJMLNkxKqRwaLRbAtAebIhuMDcIxdu/4GnuU92j8kEH8kUyN2VEqGagjLmKHIXDD3SsGz1F8nfhQ1ClNZfbZQCahJRzByG+myjLtD/Eou5sCLaL12TVS7v4=@fLq4faZouLTD9+2tfXaBi9DCMEpdgNEH2d9SxCLyxyN2jBrMPwkWlHgDQQFl9bm0NO+BkcDBBWRMlmO63A3/9RlUF+gdGIlG89136TDjzKTqqHXjMjE3wbflRPqiEJhasqe5GEKoexQf9449ENyiDhvZjV/NGnBEPSPBiuJzzCs=@Arf8Est/XxrDhgvFlRWtgVMGA0D2ISNEGI4Yjv/UyWP+1WKMf8euwYJIKqPDXRnuFX+X7JPawS4Khvxoclad0z2GPg2vGoh86hP9fHvzsjuPDz0Rb7tBGoGFlzYkEbFfM7KkcccDnY7lFHcX/GpwNno021lB3WFNtnt1COdwUAE=@eiOu8/Z+f0sw3oGii9oP4ChxUJQwWK1E4J1jEcmRYqgdwvIoV+dWHU3Th9RxIxyXHc4D3iy99/lv+R0B6npBx8hE1wD7gNfwHGYvH9g8+5+PMMnf8mf8GHYb8hFJmtJEG0uxpjye5tEftPyFGkLUjEEdMpnG95seTqvEsRMQS9o=@U5+2eGl2KGvb4Xo9YahOlo2x9nLl5ya/mlJ32OQiJ86SvLQJtxFCJn4RZyAG3qtI/olWkBHyttKRbmO9iQjyc6bamoKwrueLJPHpD4cuX4Eb49H2Rm4x0LkEoCP8GNfeApHOTDVmwgLBS7xCtL08K2H4lodq7sEoeV92ybFplTQ=";
        System.out.println("加密后密文：\n" + encodedStr);
        System.out.println("==================================================");
        String result = RSAClient.decode(encodedStr, privateKey);
        System.out.println("解密密后原文：\n" + result);
        System.out.println("==================================================");
        System.out.println("签名校验结果：" + HRSave.checkResponseSign(result));
        result = result.substring(1, result.length() - 1);
        Map<String, String> map = JSONUnit.getMapFromJsonNew(result);
        System.out.println("请求结果：" + map.get("v_resultmsg"));
    }
    /**
     * 加密
     * @param str_m 待加密的字符串
     * @param publicKey 加密公钥
     * @return
     * @throws Exception
     */
    public static String encode(String str_m, String publicKey) throws Exception {
        if (StringUtils.isBlank(str_m)) return "";
        int strLen = str_m.length();
        int lentemp = 0;
        int blocklen = 0;
        if (strLen % 90 == 0)
            lentemp = strLen / 90;
        else
            lentemp = strLen / 90 + 1;
        String block = "";
        String mask = "";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < lentemp; i++) {
            if (strLen >= 90)
                blocklen = 90;
            else
                blocklen = strLen;
            block = str_m.substring(i * 90, blocklen + i * 90);
            mask = RSACoder.encryptByPublicKey(block, publicKey);
            result.append(mask).append("@");
            strLen -= blocklen;
        }
        String str = result.toString().substring(0, result.length() - 1);
        return formatString(str);
    }

    /**
     * 解密密文
     * @param str_en 待解密的密文
     * @param privateKey 解密用的私钥
     * @return
     * @throws Exception
     */
    public static String decode(String str_en, String privateKey) throws Exception {
        if (StringUtils.isBlank(str_en)) return "";
        str_en = formatUnicode(str_en);
        StringBuilder result = new StringBuilder();
        String[] str = str_en.split("@");
        String s = null;
        for (int i = 0; i < str.length; i++) {
            s = RSACoder.decryptByPrivateKey(str[i], privateKey);
            result.append(s);
        }
        return URLDecoder.decode(result.toString(), "utf-8");
    }
    /**
     * 格式化数据
     * 
     * @param data
     * @return
     */
    public static String formatString(String data) {
        data = data.replace("#", "%23");
        data = data.replace("=", "%3d");
        data = data.replace("&", "%26");
        data = data.replace("+", "%2b");
        data = data.replace("/", "%2f");
        data = data.replace("?", "%3f");
        data = data.replace("%", "%25");
        return data;
    }
    /**
     * 将参数格式化为正常数据
     * @param data
     * @return
     */
    public static String formatUnicode(String data) {
        data = data.replace("%25", "%");
        data = data.replace("%23", "#");
        data = data.replace("%3d", "=");
        data = data.replace("%26", "&");
        data = data.replace("%2b", "+");
        data = data.replace("%2f", "/");
        data = data.replace("%3f", "?");
        data = data.replace("%5b", "[");
        data = data.replace("%7b", "{");
        data = data.replace("%3a", ":");
        data = data.replace("%2c", ",");
        data = data.replace("%5d", "]");
        data = data.replace("%7d", "}");
        data = data.replace("%22", "\"");
        return data;
    }
}