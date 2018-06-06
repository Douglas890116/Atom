package com.maven.util;

import java.security.MessageDigest;
/**
 * Md5加密方法
 * 宝立付、可可支付 在使用此方法类
 * @author klay
 *
 */
public class SignUtil {
	/**
	 * MD5加密
	 * 
	 * @param aData
	 * @return
	 * @throws SecurityException
	 */
	public static String MD5Encode(String aData) throws SecurityException {
		String resultString = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = bytes2HexString(md.digest(aData.getBytes("UTF-8")));
		} catch (Exception e) {
			e.printStackTrace();
			throw new SecurityException("MD5运算失败");
		}
		return resultString;
	}

	/**
	 * 转16进制String
	 * 
	 * @param b
	 * @return
	 */
	public static String bytes2HexString(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex.toUpperCase();
		}
		return ret;
	}
	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer();
		sb.append("#payKey=eaa539224e5d48169836a93b787a4fae\n");
		sb.append("##\u5546\u6237\u5BC6\u94A5\n");
		sb.append("#paySecret=509dfdd949d04485aab47241a31d4108\n");
		sb.append("##\u5B50\u5546\u6237\u652F\u4ED8Key\n");
		sb.append("#subPayKey=\n");
		sb.append("##\u626B\u7801\u652F\u4ED8\u8BF7\u6C42\u5730\u5740\n");
		sb.append("#scanPayUrl=http://gateway.kekepay.com/cnpPay/initPay\n");
		sb.append("##B2C\u652F\u4ED8\u8BF7\u6C42\u5730\u5740\n");
		sb.append("#b2cPayUrl=http://gateway.kekepay.com/b2cPay/initPay\n");
		sb.append("##T0\u4EE3\u4ED8\u94F6\u884C\u5361\u5730\u5740\n");
		sb.append("#toAccountProxyPayUrl=http://gateway.kekepay.com/accountProxyPay/initPay\n");
		sb.append("###\u652F\u4ED8\u67E5\u8BE2\u4EA4\u6613\u5730\u5740\n");
		sb.append("#payQueryUrl=http://gateway.kekepay.com/query/singleOrder\n");
		sb.append("###\u652F\u4ED8\u67E5\u8BE2\u4EA4\u6613\u5730\u5740\n");
		sb.append("#proxyPayQueryUrl=http://gateway.kekepay.com/proxyPayQuery/query\n");
		sb.append("##\u6761\u7801\u652F\u4ED8\u8BF7\u6C42\u5730\u5740\n");
		sb.append("#f2fPayUrl=http://gateway.kekepay.com/f2fPay/doPay\n");
		sb.append("##\u652F\u4ED8\u7ED3\u679C\u540E\u53F0\u901A\u77E5\u5730\u5740\n");
		sb.append("#notifyUrl=http://www.baidu.com/\n");
		sb.append("##\u652F\u4ED8\u7ED3\u679C\u9875\u9762\u901A\u77E5\u5730\u5740\n");
		sb.append("#returnUrl=http://www.baidu.com/\n");
		sb.append("##\u8BA2\u5355\u8FC7\u671F\u65F6\u95F4(\u5355\u4F4D:\u5206\u949F)\n");
		sb.append("#orderPeriod=5\n");
		sb.append("##\u4E0B\u5355IP\n");
		sb.append("#orderIp=123.57.38.74\n");
		sb.append("#quickPayApplyUrl=http://gateway.kekepay.com/quickPay/initPay\n");
		sb.append("#quickPayUrl=http://gateway.kekepay.com/quickPay/pay\n");
		sb.append("#quickPayBinding=http://gateway.kekepay.com/quickGateWayPay/binding\n");
		sb.append("#quickPayGateWay=http://gateway.kekepay.com/quickGateWayPay/initPay\n");
		System.out.println(sb.toString());
	}
}