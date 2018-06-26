package com.hy.pull.common.util.game.sa;

import java.nio.charset.Charset;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 沙龙游戏加密解密工具类
 * 创建日期：2016-10-11
 * @author temdy
 */
public class DeEnCode {
	public static String keys = "cy300003";
	
	/**
	 * 加密
	 * @param message 待加密的字符串
	 * @param key 密钥
	 * @return 加密后的字符串
	 */
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

	/**
	 * 解密
	 * @param message 待解密的字符串
	 * @param key 密钥
	 * @return 解密后的字符串
	 */
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
			return new String(retByte, Charset.forName("utf-8"));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取密钥
	 * @param username 用户名
	 * @param password 密码
	 * @return 密钥
	 */
	public static String getkey(String username, String password) {
		String keys = "abcdefghijklmnopqrstuvwxyz0123456789";
		int valueLen = (username + password).length();
		int i = valueLen % 32;
		int iStartPos = i / 4;
		int iStartPosLen = i % 4;
		String returnPass = keys.substring(iStartPos, iStartPos + iStartPosLen) + password;
		return encrypt(returnPass, "@hy3456#");

	}
	
	/**
	 * MD5加密
	 * @param inStr 待加密的字符串
	 * @return 加密后的字符串
	 */
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
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();

	}
}
