package com.maven.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;

/**
 * RSA签名验签类
 */
public class RSASignature {
	/**
	 * RSA Key的生成算法
	 */
	private static final String KEY_ALGORITHM = "RSA";

	/**
	 * RSA 签名算法为： SHA1WithRSA;
	 */
	private static final String SIGN_ALGORITHMS = "SHA1WithRSA";

	/**
	 * RSA 签名
	 * 
	 * @param content
	 *            待加密的数据文本
	 * @param privateKey
	 *            私钥
	 * @param encode
	 *            字符集编码
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 * @throws UnsupportedEncodingException
	 */
	public static String sign(String content, String privateKey, String encode) throws NoSuchAlgorithmException,
			InvalidKeySpecException, InvalidKeyException, SignatureException, UnsupportedEncodingException {

		PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));

		KeyFactory keyf = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey priKey = keyf.generatePrivate(priPKCS8);

		java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

		signature.initSign(priKey);
		signature.update(content.getBytes(encode));

		byte[] signed = signature.sign();

		String sign = Base64.encodeBase64String(signed);

//		return sign;
		return replaceBlank(sign);
	}

	/**
	 * RSA 签名 - 无编码格式
	 * 
	 * @param content
	 *            待加密的数据文本
	 * @param privateKey
	 *            私钥
	 * @return
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	public static String sign(String content, String privateKey)
			throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {

		PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));

		KeyFactory keyf = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey priKey = keyf.generatePrivate(priPKCS8);

		java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

		signature.initSign(priKey);
		signature.update(content.getBytes());

		byte[] signed = signature.sign();

		String sign = Base64.encodeBase64String(signed);

//		return sign;
		return replaceBlank(sign);
	}

	/**
	 * RSA签名校验
	 * 
	 * @param content
	 *            签名数据
	 * @param sign
	 *            签名值
	 * @param publicKey
	 *            公钥
	 * @param encode
	 *            字符集编码
	 * @return
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 * @throws UnsupportedEncodingException
	 */
	public static boolean doCheck(String content, String sign, String publicKey, String encode)
			throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException,
			UnsupportedEncodingException {

		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		byte[] encodedKey = Base64.decodeBase64(publicKey);
		PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

		java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

		signature.initVerify(pubKey);
		signature.update(content.getBytes(encode));

		boolean bverify = signature.verify(Base64.decodeBase64(sign));

		return bverify;
	}

	/**
	 * RSA签名校验 - 无编码格式
	 * 
	 * @param content
	 *            签名数据
	 * @param sign
	 *            签名只
	 * @param publicKey
	 *            公钥
	 * @return
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	public static boolean doCheck(String content, String sign, String publicKey)
			throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		byte[] encodedKey = Base64.decodeBase64(publicKey);
		PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

		java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

		signature.initVerify(pubKey);
		signature.update(content.getBytes());

		boolean bverify = signature.verify(Base64.decodeBase64(sign));

		return bverify;
	}
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
}