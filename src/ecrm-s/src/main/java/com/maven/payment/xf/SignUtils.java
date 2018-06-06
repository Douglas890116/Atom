package com.maven.payment.xf;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.axis.encoding.Base64;

public class SignUtils {

	private static final String MAC_NAME = "HmacSHA1";
	private static final String ENCODING = "UTF-8";

	public static String HmacSHA1(String data, String key)
			throws NoSuchAlgorithmException, InvalidKeyException,
				IllegalStateException, UnsupportedEncodingException {
		// 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), MAC_NAME);
		// 生成一个指定 Mac 算法 的 Mac 对象
		Mac mac = Mac.getInstance(MAC_NAME);
		// 用给定密钥初始化 Mac 对象
		mac.init(secretKey);
		byte[] encoded = mac.doFinal(data.getBytes(ENCODING));
		return Base64.encode(encoded);
	}
}