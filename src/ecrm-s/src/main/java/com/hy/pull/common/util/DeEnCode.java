package com.hy.pull.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;


public class DeEnCode {

	public static String encrypt(String message, String key) {
		try {
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
			String str = new sun.misc.BASE64Encoder().encode(cipher.doFinal(message.getBytes("UTF-8")));
			return str.replaceAll("\r|\n", "");
		} catch (Exception e) {
			return null;
		}
	}
	public static String getkey(String username,String password){
		String keys = "abcdefghijklmnopqrstuvwxyz0123456789";
		int valueLen = (username+password).length();
		int i = valueLen%32;
		int iStartPos = i/4;
		int iStartPosLen = i%4;
//		System.out.println(valueLen);
//		System.out.println(i);
//		System.out.println(iStartPos);
//		System.out.println(iStartPosLen);
		String returnPass = keys.substring(iStartPos,iStartPos+iStartPosLen)+password;
		return encrypt(returnPass,"@cy3456#");
		
	}
	public static String decrypt(String message, String key) {
		try {
			message = message.replace(" ", "+");
			byte[] bytesrc = new sun.misc.BASE64Decoder().decodeBuffer(message);
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
			cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
			byte[] retByte = cipher.doFinal(bytesrc);
			return new String(retByte);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Map<String, Object> getparam(String params) {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] value = params.split("!");
		for (int i = 0; i < value.length; i++) {
			String[] param = value[i].split("=");
			if (param.length > 1) {
				map.put(param[0], param[1].trim());
			}
		}
		return map;
	}
	public static String string2MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = md5Bytes[i] & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
	
	public static void main(String[] args) throws Exception {
//		//待加密内容
//    	String value = "method=RegUserInfo&Key=07FFA91640ED47C9ACFA127115891D9C&Time=20160708000000&Checkkey=hygjgame20168888&Username=test11&CurrencyType=CNY";
//    	//密码，长度要是8的倍数
//    	String key = "g9G16nTs";
//		System.out.println(encrypt(value, key));
//		System.out.println(string2MD5("method=RegUserInfo&Key=07FFA91640ED47C9ACFA127115891D9C&Time=20160708000000&Checkkey=hygjgame20168888&Username=test11&CurrencyType=CNYGgaIMaiNNtg2016070800000007FFA91640ED47C9ACFA127115891D9C"));
		System.out.println(getkey("vinson","123123"));
//		yMnSxWcARBe2XuCd/bL85Q==
	}
}
