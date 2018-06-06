package com.hy.pull.common.util.game.sa;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 沙龙游戏MD5加密工具类
 * 创建日期：2016-10-11 
 * @author temdy
 */
public class MD5Encoder {

	public static final String DEFAULT_CHARSET = "UTF-8";

	private MD5Encoder() {
	}

	private static final char[] hexadecimal = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };

	/**
	 * 加密
	 * @param s 待加密的字符串
	 * @return 加密后的字符串
	 */
	public static String encode(String s) {
		return encode(s, DEFAULT_CHARSET);
	}

	/**
	 * 加密
	 * @param s 待加密的字符串
	 * @param charset 编码
	 * @return 加密后的字符串
	 */
	public static String encode(String s, String charset) {
		if (s == null)
			return null;

		byte[] strTemp = null;
		try {
			strTemp = s.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			System.out.println(e);
			return null;
		}
		MessageDigest mdTemp = null;
		try {
			mdTemp = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e);
			return null;
		}
		mdTemp.update(strTemp);
		byte[] binaryData = mdTemp.digest();

		if (binaryData.length != 16)
			return null;

		char[] buffer = new char[32];

		for (int i = 0; i < 16; i++) {
			int low = binaryData[i] & 0x0f;
			int high = (binaryData[i] & 0xf0) >> 4;
			buffer[i * 2] = hexadecimal[high];
			buffer[i * 2 + 1] = hexadecimal[low];
		}

		return new String(buffer);
	}
}
