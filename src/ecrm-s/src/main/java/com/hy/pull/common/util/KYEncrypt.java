package com.hy.pull.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class KYEncrypt {
	
	/**
	 * MD5 32位加密
	 * @param sourceStr
	 * @return
	 */
	public static String MD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes("UTF-8"));
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
            
        } catch (NoSuchAlgorithmException e) {
           	e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
         return result;
    }
	
	
	/**
	 * AES加密
	 * @param sSrc
	 * @return
	 * @throws Exception
	 */
	public static String AESEncrypt(String value,String key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		byte[] raw = key.getBytes("UTF-8");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(value.getBytes("UTF-8"));
		String base64 = new BASE64Encoder().encode(encrypted);// 此处使用BASE64做转码
		return URLEncoder.encode(base64, "UTF-8");//URL加密
	}
	
	/**
	 * AES加密 不进行URLEncoder
	 * @param sSrc
	 * @return
	 * @throws Exception
	 */
	public static String AESUNURLEncrypt(String value,String key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		byte[] raw = key.getBytes("UTF-8");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(value.getBytes("UTF-8"));
		return new BASE64Encoder().encode(encrypted);// 此处使用BASE64做转码
	}

	/**
	 * AES 解密
	 * @param sSrc
	 * @return
	 * @throws Exception
	 */
	public static String AESDecrypt(String value,String key,boolean isDecodeURL) throws Exception {
		try {
			byte[] raw = key.getBytes("UTF-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			if(isDecodeURL)	value = URLDecoder.decode(value, "UTF-8");
			byte[] encrypted1 = new BASE64Decoder().decodeBuffer(value);// 先用base64解密
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, "UTF-8");
			return originalString;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
