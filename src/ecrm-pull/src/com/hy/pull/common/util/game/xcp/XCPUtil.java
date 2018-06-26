package com.hy.pull.common.util.game.xcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


/**
 * 拉取XCP游戏数据工具类
 * 创建日期：2016-10-22
 * @author temdy
 */
public class XCPUtil {

	/**
	 * 获取接口的数据
	 * @param url 接口URL
	 * @return 响应数据
	 */
	public static String getUrl(String url) {
		StringBuilder sb = new StringBuilder();
		try {
			InputStream in = new URL(url).openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return sb.toString();
	}

	/**
	 * DES加密
	 * @param plainText 待加密的数据
	 * @param key 密钥
	 * @return 加密后的数据
	 * @throws Exception 抛出Exception异常
	 */
	public static byte[] desEncrypt(byte[] plainText, String key) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey keys = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, keys, sr);
		byte data[] = plainText;
		byte encryptedData[] = cipher.doFinal(data);
		return encryptedData;
	}

	/**
	 * DES解密
	 * @param encryptText 待解密的数据
	 * @param key 密钥
	 * @return 解密后的字符串
	 * @throws Exception 抛出Exception异常
	 */
	public static byte[] desDecrypt(byte[] encryptText, String key) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey keys = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, keys, sr);
		byte encryptedData[] = encryptText;
		byte decryptedData[] = cipher.doFinal(encryptedData);
		return decryptedData;
	}

	/**
	 * 字符串加密
	 * @param input 待加密的字符串
	 * @param key 密钥
	 * @return 加密后的字符串
	 * @throws Exception 抛出Exception异常
	 */
	public static String encrypt(String input, String key) throws Exception {
		String params = base64Encode(desEncrypt(input.getBytes(), key)).replaceAll("\\s*", "");
		params = URLEncoder.encode(params, "utf-8");
		return params;
	}

	/**
	 * 字符串解密 
	 * @param input 待解密的字符串
	 * @param key 密钥
	 * @return 解密后的字符串
	 * @throws Exception 抛出Exception异常
	 */
	public static String decrypt(String input, String key) throws Exception {
		byte[] result = base64Decode(input);
		return new String(desDecrypt(result, key));
	}

	/**
	 * base64加密
	 * @param s 待加密的数据
	 * @return 加密后的字符串
	 */
	public static String base64Encode(byte[] s) {
		if (s == null)
			return null;
		BASE64Encoder b = new sun.misc.BASE64Encoder();
		return b.encode(s);
	}

	/**
	 * base64解密
	 * @param s 待解密的字符串
	 * @return 解密后的字符串
	 * @throws IOException 抛出IOException异常
	 */
	public static byte[] base64Decode(String s) throws IOException {
		if (s == null) {
			return null;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] b = decoder.decodeBuffer(s);
		return b;
	}

	/**
	 * MD5加密
	 * @param s 待加密的字符串
	 * @return 加密后的字符串
	 */
	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str).toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
