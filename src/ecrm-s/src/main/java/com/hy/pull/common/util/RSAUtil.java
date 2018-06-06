package com.hy.pull.common.util;


import org.apache.commons.codec.binary.Base64;
import org.omg.IOP.Encoding;

import javax.crypto.Cipher;
//import javax.persistence.Convert;
import java.io.*;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtil {

	 /**
     * String to hold name of the encryption algorithm.
     */
    public static final String ALGORITHM = "RSA";

    /**
     * String to hold the name of the private key file.
     */
    public static final String PRIVATE_KEY_FILE = "D:/rsa/pkcs8_priv2.pem";

    /**
     * String to hold name of the public key file.
     */
    public static final String PUBLIC_KEY_FILE = "D:/rsa/rsa_public_key.pem";

    /**
     * rsa验签
     *
     * @param content   被签名的内容
     * @param sign      签名后的结果
     * @param publicKey rsa公钥
     * @param charset   字符集
     * @return 验签结果
     * @throws SignatureException 验签失败，则抛异常
     */
    static boolean doCheck(String content, String sign, String publicKey, String charset) throws SignatureException {
        try {
            PublicKey pubKey = getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey.getBytes()));

            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initVerify(pubKey);
            signature.update(getContentBytes(content, charset));

            return signature.verify(Base64.decodeBase64(sign.getBytes()));
        } catch (Exception e) {
            throw new SignatureException("RSA验证签名[content = " + content + "; charset = " + charset + "; signature = " + sign + "]发生异常!", e);
        }
    }

    private static PublicKey getPublicKeyFromX509(String algorithm, InputStream ins) throws NoSuchAlgorithmException {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            byte[] encodedKey = StreamTool.readInputStream(ins);

            // 先base64解码
            encodedKey = Base64.decodeBase64(encodedKey);
            return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
        } catch (Exception ex) {
            // 不可能发生
        }
        return null;
    }

    private static byte[] getContentBytes(String content, String charset) throws UnsupportedEncodingException {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        return content.getBytes(charset);
    }


    /**
     * rsa签名
     *
     * @param content    待签名的字符串
     * @param privateKey rsa私钥字符串
     * @param charset    字符编码
     * @return 签名结果
     * @throws Exception 签名失败则抛出异常
     */
    public static String rsaSign(String content, String privateKey, String charset) throws SignatureException {
        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8(ALGORITHM, new ByteArrayInputStream(privateKey.getBytes()));

            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initSign(priKey);
            if (charset == null || "".equals(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }
            byte[] signed = signature.sign();

            return new String(Base64.encodeBase64(signed));
        } catch (Exception e) {
            throw new SignatureException("RSAcontent = " + content + "; charset = " + charset, e);
        }
    }

    public static PrivateKey getPrivateKeyFromPKCS8(String algorithm, InputStream ins) throws Exception {
        if (ins == null || (algorithm == null || "".equals(algorithm))) {
            return null;
        }
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        byte[] encodedKey = StreamTool.readInputStream(ins);
        encodedKey = Base64.decodeBase64(encodedKey);
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
    }
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//--------------------------------------RSA公私钥------------------------------------------
		//公私钥如下：
		String s_publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDGnt9667FBKIZWD9OsH9btriImqqU2btve4rNj7xcQD/7KTow/tA5t26tWIWvL93BSLT6ng8lkvZq2Zuf3HyVtlXOqQPPqi4vGk9HBxQhLKWHcuAX54fc7dOYrG80aiQ6DuNWQ7FEaBZIE+JY2uYpo33smKNIP36Wsum56UuaDgQIDAQAB";
		String s_privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMae33rrsUEohlYP06wf1u2uIiaqpTZu297is2PvFxAP/spOjD+0Dm3bq1Yha8v3cFItPqeDyWS9mrZm5/cfJW2Vc6pA8+qLi8aT0cHFCEspYdy4Bfnh9zt05isbzRqJDoO41ZDsURoFkgT4lja5imjfeyYo0g/fpay6bnpS5oOBAgMBAAECgYBahE7eqwkV480M7ZuOxtfha8lei8hNPUpwkiok8fI1vMRmGFPsODeXM1QrJYIF61dB7CKwnuuqQIqAc/dk9MnOD0q3qZ6jMb/M+jFq/73HwuM1f67gQvVwPJl7vjINhB+J/PParVqYkmvNOkuuOgqv/jf16qEovFdXlL9hutHGbQJBAPw9/pQ4adX1UDojdysBRjH8nL82TsGxeLH9b97iHF3kfYilvEUCqDNCi/K/4z/3XD63ADaW8cQ0n5AnQV31RacCQQDJlGAqvt92TAxf2UrJ855AGQnynpboX3KIim05dPWhSzYfnHm6IHFiXZnvljmkri1jcE+UqDOrGuSfOc3j++KXAkBF0XMR27uPuWMHdKGbibLAS0entYR/IHxj5957NuLbKk+E7zr5bw7XgWfzPSHNps4lncm1UnqA8H/qCrORKj6pAkEAhiIWT7tNBPGbtlfn3TQflHVU2j3PGvcQRm0eOwJpxBdA/43mrgSSjirMmNF0r/E6wJVmTvwRzYSKpq0XJOJiJwJBAMNl7PP+UuoHovWYc5JCNIbCHwQm0GchUWorIv+G/v2c2GJTFua77xgPcKym2pI4tq3iuelk/YD/Sz9yUr2znUQ=";
		
		try {
            
        	//－－－－－－－－－－－－－－－－－－－－－－－－－－URL－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
        	String s_locate;
        	s_locate = "http://demo-sfgames.dashinggame.com/static/html/hall.html";
        	
        	//－－－－－－－－－－－－－－－－－－－－－－－－－－XML－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
        	String strMerchantId,strPlayerName,strUID;
    		
    		//正式接口（单个）：
    		strMerchantId = "1002810";
            strPlayerName = "11oYkU99B6H1p01qew4DyaZExk32Ar";
            strUID = "8480BB62791C2F15A4DC2417FD3D1772";
        	
        	String ss_xmltest="<?xml version='1.0' encoding='utf-8'?>" +
        					  "<message>" +
        					  "<playerName>" + strPlayerName + "</playerName>" +
        					  "<uId>" + strUID + "</uId>" +
        					  "<merchantId>" + strMerchantId +"</merchantId>" +
        					  "</message>";
    	
        	System.out.println("－－－－－－－－－－－－－传输前－－－－－－－－－－－－－－");
        	System.out.println("xml：" + ss_xmltest);
        	
        	byte[] byte_xmlbase64 = Base64.encodeBase64(ss_xmltest.getBytes("UTF-8"));
        	String s_xmlbase64 = new String(byte_xmlbase64,"UTF-8");
        	System.out.println("xml经过BASE64编码后：" + s_xmlbase64);
        	
        	String s_xmlSign = rsaSign(ss_xmltest, s_privateKey, "UTF-8");
        	System.out.println("xml签名后：" + s_xmlSign);
        	
        	String s_url = s_locate + "?EnStr=" + s_xmlSign + "&XmlStr=" + s_xmlbase64;
        	System.out.println("游戏请求URL：" + s_url);
        	
        	System.out.println("－－－－－－－－－－－－－接收后－－－－－－－－－－－－－－");
        	
        	String s_xmlAft = new String(Base64.decodeBase64(s_xmlbase64.getBytes()));
        	System.out.println("编码xml经过BASE64解码后：" + s_xmlAft);
        	
        	boolean b_check = doCheck(s_xmlAft, s_xmlSign, s_publicKey, "UTF-8");
        	System.out.println("xml签名验证：" + b_check);
         	
     	
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
		
		
		

	}

}
