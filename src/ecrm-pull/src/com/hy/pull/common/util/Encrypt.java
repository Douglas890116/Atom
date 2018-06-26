package com.hy.pull.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;



/**
 * MD5和AES加密类
 * 创建日期 2016-10-06
 * @author temdy
 */
public class Encrypt {
	
	public static void main(String[] args) {
		System.out.println(MD5("gjhfkjdhfkjdhfkjh54985789"));
	}
	
	/**
	 * MD5加密
	 * @author temdy
	 * @param value 待加密的字符串
	 * @return 加密后的字符串
	 */
	public static String MD5(String value) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(value.getBytes("UTF-8"));
            byte[] b = md.digest();
            StringBuffer buf = new StringBuffer();
            int i = 0, len = b.length;            
            for (int offset = 0; offset < len; offset++) {
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
	 * @author temdy
	 * @param value 待加密的字符串
	 * @param key 密钥Key
	 * @return 加密后的字符串
	 */
	public static String AESEncrypt(String value,String key) {
		return AESEncrypt(value,key,true);//默认对加密后的字符串进行URLEncoder加密
	}
	
	/**
	 * AES加密
	 * @author temdy
	 * @param value 待加密的字符串
	 * @param key 密钥Key
	 * @param isURLEncoder 是否对加密后的字符串进行URLEncoder加密
	 * @return 加密后的字符串
	 */
	public static String AESEncrypt(String value,String key,boolean isURLEncoder) {
		String encryptValue = null;
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			byte[] raw = key.getBytes("UTF-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(value.getBytes("UTF-8"));
			encryptValue = Base64.getEncoder().encodeToString(encrypted);//此处使用BASE64做转码
			if(isURLEncoder){
				encryptValue = URLEncoder.encode(encryptValue, "UTF-8");//URL加密
			}
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encryptValue;
	}
	
	/**
	 * AES解密
	 * @author temdy
	 * @param value 待解密的字符串
	 * @param key 密钥Key
	 * @return 解密后的字符串
	 */
	public static String AESDecrypt(String value,String key) {
		return AESDecrypt(value,key,true);////默认对解密前的字符串进行URLDecoder解密
	}

	/**
	 * AES解密
	 * @author temdy
	 * @param value 待解密的字符串
	 * @param key 密钥Key
	 * @param isURLDecoder 是否对解密前的字符串进行URLDecoder解密
	 * @return 解密后的字符串
	 */
	public static String AESDecrypt(String value,String key,boolean isURLDecoder) {
		String decryptValue = null;
		try {
			byte[] raw = key.getBytes("UTF-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			if(isURLDecoder){
				value = URLDecoder.decode(value, "UTF-8");
			}
			byte[] encrypted = cipher.doFinal(Base64.getDecoder().decode(value));//先用base64解密
			decryptValue = new String(encrypted, "UTF-8");
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return decryptValue;
	}
}
