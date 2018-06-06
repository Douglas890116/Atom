package com.maven.util;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

/**
 * 非对称加密算法RSA算法组件 非对称算法一般是用来传送对称加密算法的密钥来使用的，相对于DH算法，RSA算法只需要一方构造密钥，不需要
 * 大费周章的构造各自本地的密钥对了。DH算法只能算法非对称算法的底层实现。而RSA算法算法实现起来较为简单
 * 
 * 注意：密匙长度为1024
 * 
 * @author kongqz
 */
public class RSACoder {
    /** 非对称密钥算法 **/
    public static final String KEY_ALGORITHM = "RSA";
    /** RSA填充算法 **/
    public static final String CIPHER_ALGORITHM_RSA = "RSA";
    /** PKCS1填充算法 **/
    public static final String CIPHER_ALGORITHM_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";
    /** RSA_PKCS1_OAEP_PADDING填充算法 **/
    public static final String CIPHER_ALGORITHM_OAEP_PADDING = "RSA/ECB/OAEPPadding";
    /** 无填充算法 **/
    public static final String CIPHER_ALGORITHM_NO_PADDING = "RSA/ECB/NoPadding";
    // 华仁公钥
    public static final String HR_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdlfulqoHwbI5Jx3QBitq8MURZd0iAcTx+Or2LxYe/rniRxMoAOFs84KzNpML/0kmL2pzni9oXuAXDTxUsyr4DBMS78KyGQZP5HkWUbNEQZ2tMmtIWmu4Mjs0EInmfSsez5fV+OUeEjQZ9OvWdJ3fkBgOUoW9nLXYO4srQuGm+LwIDAQAB";
    // 公钥
    public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCVTdDVOFay0TWqlKXhigCqbhmvewluFMZnfsAaaaodnIkTiOG+c8YWL615tQaxNU9zXAuSTSVn5A8nKJmEQs5wbS3JYSTOwCtLrXWLrMr8W4L7kFC81bd2/w6mSHISsOrQ6a1CVUQAkIktN1/TTuEpRYhR4S7PoS01HGMy1piqAwIDAQAB";
    // 私钥
    public static final String PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJVN0NU4VrLRNaqUpeGKAKpuGa97CW4Uxmd+wBppqh2ciROI4b5zxhYvrXm1BrE1T3NcC5JNJWfkDycomYRCznBtLclhJM7AK0utdYusyvxbgvuQULzVt3b/DqZIchKw6tDprUJVRACQiS03X9NO4SlFiFHhLs+hLTUcYzLWmKoDAgMBAAECgYAtB7kydbmAWSTse8TED1FAFdDdYihn8RAd6taZoMDUCYA2ShR70oMt8ddKW9TW4ZNC4cIDsAzWFqyTTOVwRI3qWGxlxkJl5EnFu4hcWXFBZT9aQ4pZOMXcpZAadkk3lrintaNPsZXhqObHpxoFDkzQpPpr9AIu9VAAfP49ZW1DoQJBANGTtTHFt9/Qup97yb/u67SRCPFA2Pf1ensPUIcmhI90oVHSYbwsc6jLqXRTyRCm/ZnnM9T5jmRu1anc8rwUQ/sCQQC2YEK5brucahUQzD9TFAfdTlMMAJ+SPH1QJ9jYbkRVa4i/a/FzBR4BCrl8hGjWQQXNhc2w2JzqLZpqbugdfMuZAkAQhFCabJeyNvQOT6Y1zzGaWHfY86Bl4l3Vxv40uI9n8uwn06nKN8KhwfNH7LaC7nY8I+GM3mIffjCuo3Ap7HrzAkAVGs6d5tKPJzeI2hn54zeFxKqXmPreUWGvBO1zHk+KEwegHz2xscXnGPaeEjSPlra1Mea7sFV4RA66glsaDncBAkBO16bjDVgfCDGjEM+mzAERDuxSYWnoJJoYtzv+i6CeFqgaq96RoYVqo+RjgNumiSeTVr1JzXhBPsQooRJP7rJy";
    
    // 华仁公钥 xml格式
//    private static String module = "nZX7paqB8GyOScd0AYravDFEWXdIgHE8fjq9i8WHv654kcTKADhbPOCszaTC/9JJi9qc54vaF7gFw08VLMq+AwTEu/CshkGT+R5FlGzREGdrTJrSFpruDI7NBCJ5n0rHs+X1fjlHhI0GfTr1nSd35AYDlKFvZy12DuLK0Lhpvi8=";
//    private static String exponentString = "AQAB";
            
    /**
     * 公钥加密
     * @param data 待加密的数据
     * @param publicKeyStr  RSA公钥
     * @return      返回加密后的密文
     * @throws Exception
     */
    public static String encryptByPublicKey(String dataStr, String publicKeyStr) throws Exception {
        byte[] dataByte = dataStr.getBytes("utf-8");
        byte[] publicKey = Base64.decodeBase64(publicKeyStr);
        byte[] resultByte = encryptByPublicKey(dataByte, publicKey);
        String resultStr =  Base64.encodeBase64String(resultByte);
        return replaceBlank(resultStr);// 去掉换行符
    }
    
    /**
     * 私钥解密
     * @param dataStr 待解迷的密文
     * @param privateKeyStr 私钥字符串
     * @return      返回解密后的原文
     * @throws Exception
     */
    public static String decryptByPrivateKey(String dataStr, String privateKeyStr) throws Exception {
        byte[] dataByte = Base64.decodeBase64(dataStr);
        byte[] privateKey = Base64.decodeBase64(privateKeyStr);
        byte[] resultByte = decryptByPrivateKey(dataByte, privateKey);
        return new String(resultByte, "utf-8");
    }
    
    /**
     * 公钥加密
     * 
     * @param data待加密数据
     * @param key 密钥
     * @return byte[] 加密数据
     */
    public static byte[] encryptByPublicKey(byte[] data, byte[] key) throws Exception {
        // xml格式的密文用一下方法加密
//        byte[] modulusBytes = Base64.decodeBase64(module);
//        byte[] exponentBytes = Base64.decodeBase64(exponentString);
//        BigInteger modulus = new BigInteger(1, modulusBytes);
//        BigInteger exponent = new BigInteger(1, exponentBytes);
//        RSAPublicKeySpec rsaKeySpec = new RSAPublicKeySpec(modulus, exponent);
        // 初始化公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
        // 实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 产生公钥
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
        // 数据加密
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_PKCS1_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return cipher.doFinal(data);
    }

    /**
     * 私钥解密
     * 
     * @param data
     *            待解密数据
     * @param key
     *            密钥
     * @return byte[] 解密数据
     */
    public static byte[] decryptByPrivateKey(byte[] data, byte[] key) throws Exception {
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
        // 实例化钥匙工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 数据解密
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_PKCS1_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }
    /**
     * 取消换行符
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String dataStr = "鹅鹅鹅，曲项向天歌，白毛浮绿水，红掌拨清波。好诗好诗！！！~~~";
//        String dataStr = "{\"v_pagecode\":\"1001\",\"v_mid\":\"110102\",\"v_oid\":\"20170514-110102-141221\",\"v_rcvname\":\"收货人名称\",\"v_rcvaddr\":\"地址\",\"v_rcvtel\":\"13513512345\",\"v_goodsname\":\"商品\",\"v_goodsdescription\":\"描述\",\"v_rcvpost\":\"10021\",\"v_qq\":\"1254545\",\"v_amount\":\"12\",\"v_ymd\":\"20170514\",\"v_orderstatus\":\"1\",\"v_ordername\":\"定货人名称\",\"v_bankno\":\"105100000017\",\"v_moneytype\":\"0\",\"v_url\":\"http://api.hyzonghe.net/TPayment/HRPayCallback\",\"v_md5info\":\"0e1db763c623d991f3b49bf1b3123aa3\"}";
        System.out.println("数据原文：\n" + dataStr);
        System.out.println("==================================================");
        String encodedData = RSACoder.encryptByPublicKey(dataStr, RSACoder.PUBLIC_KEY);
        System.out.println("加密后密文：\n" + encodedData);
        System.out.println("==================================================");
        String resultData = RSACoder.decryptByPrivateKey(encodedData, RSACoder.PRIVATE_KEY);
        System.out.println("解密后原文：\n" + resultData);
        System.out.println("==================================================");
    }
}