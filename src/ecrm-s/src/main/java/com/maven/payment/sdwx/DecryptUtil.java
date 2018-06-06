package com.maven.payment.sdwx;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;


public class DecryptUtil {
	private String Key1;
	private String Key2;

	public static String DecryptData(String data, String key1, String key2) {
		DecryptUtil decrypt = new DecryptUtil();
		decrypt.Key1 = key1;
		decrypt.Key2 = key2;
		String ret = "";
		try {
			ret = decrypt.DecryptData(data);
			ret = ret.substring(0, ret.length() - 32);
			return ret;
		} catch (Exception ex) {
			return "103";
		}
	}

	private String DecryptData(String data) {
		String decryptData = "";
		try {
			byte[] keyBytes;
			keyBytes = new BASE64Decoder().decodeBuffer(Key1);
			byte[] keyIV = new BASE64Decoder().decodeBuffer(Key2);
			byte[] byteMi = new BASE64Decoder().decodeBuffer(data);
			IvParameterSpec zeroIv = new IvParameterSpec(keyIV);
			SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
			byte[] decryptedData = cipher.doFinal(byteMi);
			decryptData = new String(decryptedData, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decryptData;
	}
}
