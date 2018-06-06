package com.maven.payment.mb;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Mobo360SignUtil {

	private static PrivateKey privateKey = null;
	private static X509Certificate cert = null;
	private static String signType = Mobo360Config.SIGN_TYPE;
	private static final String CHAR_SET = "UTF-8";
	private static String key = "";

	private Mobo360SignUtil() {

	}

	public static void init() throws Exception {
		if ("RSA".equals(signType)) {
			initRAS(Mobo360Config.PFX_FILE, Mobo360Config.CERT_FILE,
					Mobo360Config.PASSWD);
		} else if ("MD5".equals(signType)) {
			initMD5();
		}
	}

	/**
	 * MD5签名初始化
	 * 
	 */
	public static void initMD5() throws Exception {
		key = Mobo360Config.MD5_KEY;
	}

	/**
	 * RAS签名初始化
	 * 
	 * @param pfxFile
	 *            签名私钥文件的绝对路径
	 * @param certFile
	 *            验证签名文件的绝对路径
	 * @param pfxPwd
	 *            私钥文件的密码
	 */
	public static void initRAS(String pfxFilePath, String certFilePath,
			String pfxPwd) throws Exception {
		if (StringUtils.isBlank(pfxFilePath)) {
			throw new Exception("私钥文件路径不能为空！");
		}
		if (StringUtils.isBlank(certFilePath)) {
			throw new Exception("公钥文件路径不能为空！");
		}
		if (StringUtils.isBlank(pfxPwd)) {
			throw new Exception("私钥密码不能为空！");
		}
		if (privateKey == null || cert == null) {
			InputStream is = null;
			try {
				KeyStore ks = KeyStore.getInstance("PKCS12");
				is = new FileInputStream(pfxFilePath);
				if (is == null) {
					throw new Exception("证书文件路径不正确！");
				}
				String pwd = pfxPwd;
				ks.load(is, pwd.toCharArray());
				String alias = "";
				Enumeration<String> e = ks.aliases();
				while (e.hasMoreElements()) {
					alias = e.nextElement();
				}
				privateKey = (PrivateKey) ks.getKey(alias, pwd.toCharArray());
				CertificateFactory cf = CertificateFactory.getInstance("X.509");
				is = new FileInputStream(certFilePath);
				if (is == null) {
					throw new Exception("证书文件路径不正确！");
				}
				cert = (X509Certificate) cf.generateCertificate(is);
				is.close();
			} catch (Exception e) {
				throw new RuntimeException("签名初始化失败！" + e);
			} finally {
				if (null != is) {
					is.close();
				}
			}
		}
	}

	/**
	 * 生成签名
	 * 
	 * @param sourceData
	 * @return
	 * @throws Exception
	 */
	public static String signData(String sourceData) throws Exception {
		String signStrintg = "";
		if ("RSA".equals(signType)) {
			if (null == privateKey) {
				throw new Exception("签名尚未初始化！");
			}
			if (StringUtils.isBlank(sourceData)) {
				throw new Exception("签名数据为空！");
			}
			Signature sign = Signature.getInstance("MD5withRSA");
			sign.initSign(privateKey);
			sign.update(sourceData.getBytes("utf-8"));
			byte[] signBytes = sign.sign();
			BASE64Encoder encoder = new BASE64Encoder();
			signStrintg = encoder.encode(signBytes);
		} else if ("MD5".equals(signType)) {
			signStrintg = signByMD5(sourceData, key);
		}
		signStrintg.replaceAll("\r", "").replaceAll("\n", "");
		return signStrintg;
	}

	/**
	 * 验证签名
	 * 
	 * @param signData
	 *            签名数据
	 * @param srcData
	 *            原数据
	 * @return
	 * @throws Exception
	 */
	public static boolean verifyData(String signData, String srcData)
			throws Exception {
		if ("RSA".equals(signType)) {
			if (null == cert) {
				throw new Exception("签名尚未初始化！");
			}
			if (StringUtils.isBlank(signData)) {
				throw new Exception("系统校验时：签名数据为空！");
			}
			if (StringUtils.isBlank(srcData)) {
				throw new Exception("系统校验时：原数据为空！");
			}
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] b = decoder.decodeBuffer(signData);
			Signature sign = Signature.getInstance("MD5withRSA");
			sign.initVerify(cert);
			sign.update(srcData.getBytes("utf-8"));
			return sign.verify(b);
		} else if ("MD5".equals(signType)) {
			if (signData.equalsIgnoreCase(signByMD5(srcData, key))) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static String signByMD5(String sourceData, String key)
			throws Exception {
		String data = sourceData + key;
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] sign = md5.digest(data.getBytes(CHAR_SET));

		return Bytes2HexString(sign).toUpperCase();
	}

	/**
	 * 将byte数组转成十六进制的字符串
	 * 
	 * @param b
	 *            byte[]
	 * @return String
	 */
	public static String Bytes2HexString(byte[] b) {
		StringBuffer ret = new StringBuffer(b.length);
		String hex = "";
		for (int i = 0; i < b.length; i++) {
			hex = Integer.toHexString(b[i] & 0xFF);

			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret.append(hex.toUpperCase());
		}
		return ret.toString();
	}

}