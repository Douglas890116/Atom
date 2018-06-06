package com.maven.utils;

import java.net.URLDecoder;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AESUtil2Net {

	/**
	 * @author miracle.qu
	 * @see AES算法加密明文
	 * @param data
	 *            明文
	 * @param key
	 *            密钥，长度16
	 * @param iv
	 *            偏移量，长度16
	 * @return 密文
	 */
	public static String encryptAES(String data, String key, String iv) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			int blockSize = cipher.getBlockSize();
			byte[] dataBytes = data.getBytes();
			int plaintextLength = dataBytes.length;

			if (plaintextLength % blockSize != 0) {
				plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
			}

			byte[] plaintext = new byte[plaintextLength];
			System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

			cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
			byte[] encrypted = cipher.doFinal(plaintext);

			return Base64Helper.encode(encrypted).trim();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @author miracle.qu
	 * @see AES算法解密密文
	 * @param data
	 *            密文
	 * @param key
	 *            密钥，长度16
	 * @param iv
	 *            偏移量，长度16
	 * @return 明文
	 */
	public static String decryptAES(String data, String key, String iv) throws Exception {
		try {
			byte[] encrypted1 = Base64Helper.decode(data);

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes("utf-8"), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes("utf-8"));

			cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original);
			return originalString.trim();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
		try {
//			System.out.println(encryptAES("{\"return_url\":\"http://zf.szqppay.com/Pay/LeFu/ChargeInCallBack.ashx\",\"out_trade_no\":\"20170329023112\",\"amount_str\":\"1.00\",\"tran_ip\":\"180.232.108.150\"}", "rGKpfj0RsSzaSnl0", "6bG0cht1zu3J2qp9"));
			System.out.println(decryptAES("NoDABzy/JfmVq/CvFNnTkAIjyYUBgnyufcnO+Kz3lL93HEiStIexDO0EmxenMjV4z0CqGcRgJjS2bNE3p5oq5sdRlPcu8USpyQDtvr5D5O4bNaPyol7cREqeCX5jDzZXlNdxvbWvyff/+ea4TitG43/En5/ilN2v/tvdAwF6oIVQ9j0fqVdk9lBS99Kdw1lK/2k8pMN9ZYEUtcf1psd9og==", "rGKpfj0RsSzaSnl0", "6bG0cht1zu3J2qp9"));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
