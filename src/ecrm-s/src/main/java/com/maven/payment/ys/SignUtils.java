package com.maven.payment.ys;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import org.apache.commons.codec.binary.Base64;

import com.maven.util.RandomString;
public class SignUtils {
	private static final String RSA_ALGORITHM = "SHA1WithRSA";
	/**
	 * 
	 * @param content 签名原文
	 * @param pfxUrl  私钥地址
	 * @param charset 编码
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws CertificateException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyStoreException 
	 * @throws UnrecoverableKeyException 
	 * @throws InvalidKeyException 
	 * @throws SignatureException 
	 * @throws Exception
	 */
	public static String rsaSign(String content, String pfxUrl, String cerPassword, String charset)
			throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException,
				FileNotFoundException, IOException, InvalidKeyException, SignatureException {

			System.out.println("进入签名方法：content[" + content + "], charset[" + charset + "]");

			PrivateKey priKey = getPrivateKey(pfxUrl, cerPassword);

			java.security.Signature signature = java.security.Signature.getInstance(RSA_ALGORITHM);

			signature.initSign(priKey);

			signature.update(content.getBytes(charset));

			byte[] signed = signature.sign();

			String sign = new String(Base64.encodeBase64(signed), charset);

			System.out.println("进入签名完：content[" + content + "], charset[" + charset + "], sign[" + sign + "]");

			return sign;
	}
	/**
	 * 验签
	 * @param content 验签原文
	 * @param sign    签名
	 * @param cerUrl  公钥地址
	 * @param charset 字符集
	 * @return
	 * @throws InvalidKeyException
	 * @throws CertificateException
	 * @throws FileNotFoundException
	 * @throws SignatureException
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static boolean rsaCheckContent(String content, String sign, String cerUrl, String charset)
		throws InvalidKeyException, CertificateException, FileNotFoundException,
			SignatureException, UnsupportedEncodingException, NoSuchAlgorithmException {
		
		System.out.println("进入验证签名方法: content[" + content + "], sign[" + sign + "], charset[" + charset + "]");
		
		java.security.Signature signetcheck = java.security.Signature.getInstance(RSA_ALGORITHM);
		signetcheck.initVerify(getPublicKey(cerUrl));
		signetcheck.update(content.getBytes(charset));
		
		return signetcheck.verify(Base64.decodeBase64(sign.getBytes(charset)));
	}
	
	/**
	 * 获取公钥
	 * @param cerUrl
	 * @return
	 * @throws CertificateException
	 * @throws FileNotFoundException
	 */
	private static PublicKey getPublicKey(String cerUrl)
			throws CertificateException, FileNotFoundException {
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		Certificate cac = cf.generateCertificate(new FileInputStream(cerUrl));
		return cac.getPublicKey();
	}
	
	private static PrivateKey getPrivateKey(String cerUrl, String cerPassword)
		throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
			FileNotFoundException, IOException, UnrecoverableKeyException {
		
		KeyStore keystoreCA = KeyStore.getInstance("PKCS12");
		
		keystoreCA.load(new FileInputStream(cerUrl), cerPassword.toCharArray());
		
		Enumeration<?> aliases = keystoreCA.aliases();
		
		String keyAlias = null;
		if (aliases != null) {
			while (aliases.hasMoreElements()) {
				keyAlias = (String) aliases.nextElement();
				// 获取CA私钥
				return (PrivateKey) (keystoreCA.getKey(keyAlias, cerPassword.toCharArray()));
			}
		}
		return null;
	}
	
	/**
	 * 生成银盛规定格式的订单号
	 * @return
	 */
	public static String getOrderNo() {
		// 银盛支付合作商户网站唯一订单号.该参数支持汉字，前8位必须为当前日期，最大长度为16个.
		// 2017041211443511443557605760
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String randomNo = RandomString.UUID().substring(8);
		return format.format(new Date()).concat(randomNo);
	}
}