package com.maven.payment.ry.df;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.maven.util.Encrypt;
import com.maven.util.RSASignature;

import net.sf.json.JSONObject;

public class RYdfPay {
	// 保证一些固定值一致
	private static final String version = "2.1";
	private static final String charset = "UTF-8";
	private static final String remark = "remark";
	private static final String curcode = "CNY";
	private static final String signtype = "RSA";
	private static final String corppersonflag = "1";
	/**
	 * 获取代付接口地址
	 * @param merchant
	 * @param order
	 * @return
	 * @throws Exception 
	 */
	public String getUrl(RYdfMerchantConfig merchant, RYdfOrderConfig order) throws Exception  {
		String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String payeeDetails = AESEncrypt(merchant, order);
		String sign = RSASign(merchant, payeeDetails, time);
		StringBuffer sb = new StringBuffer(merchant.getPayUrl());
		sb.append("?").append(RYdfAppConstants.payout_Version).append("=").append(version)
		  .append("&").append(RYdfAppConstants.payout_SignType).append("=").append(signtype)
		  .append("&").append(RYdfAppConstants.payout_SignInfo).append("=").append(sign)
		  .append("&").append(RYdfAppConstants.payout_TranDateTime).append("=").append(time)
		  .append("&").append(RYdfAppConstants.payout_Charset).append("=").append(charset)
		  .append("&").append(RYdfAppConstants.payout_CurCode).append("=").append(curcode)
		  .append("&").append(RYdfAppConstants.payout_Remark).append("=").append(remark)
		  .append("&").append(RYdfAppConstants.payout_MerId).append("=").append(merchant.getMerId())
		  .append("&").append(RYdfAppConstants.payout_PayeeDetails).append("=").append(payeeDetails);
		System.out.println("如意代付接口地址：" + sb.toString());
		return sb.toString();
	}
	/**
	 * 加密参数
	 * @param merchant
	 * @param order
	 * @return
	 * @throws Exception 
	 */
	private static String AESEncrypt(RYdfMerchantConfig merchant, RYdfOrderConfig order) throws Exception {
		JSONObject payeeInfo = new JSONObject();
		payeeInfo.put(RYdfAppConstants.payoutDetail_OrdId, order.getOrdId());
		payeeInfo.put(RYdfAppConstants.payoutDetail_RecvBankAcctName, order.getAccountName());
		payeeInfo.put(RYdfAppConstants.payoutDetail_RecvBankAccNumber, order.getAccountBankNum());
		payeeInfo.put(RYdfAppConstants.payoutDetail_BankCode, order.getBankCode());
		payeeInfo.put(RYdfAppConstants.payoutDetail_RecvBankProvince, order.getAccountBankProvince());
		payeeInfo.put(RYdfAppConstants.payoutDetail_RecvBankCity, order.getAccountBankCity());
		payeeInfo.put(RYdfAppConstants.payoutDetail_RecvBankBranch, order.getAccountBankBranch());
		payeeInfo.put(RYdfAppConstants.payoutDetail_OrdAmt, order.getAmount());
		payeeInfo.put(RYdfAppConstants.payoutDetail_CorpPersonFlag, corppersonflag);// 固定值： 1
		payeeInfo.put(RYdfAppConstants.payoutDetail_NotifyURL, merchant.getNotifyUrl());
		
		String payeeDetails = "[" + payeeInfo.toString() + "]";
		System.out.println("如意代付PayeeDetails参数原文：" + payeeDetails);
		
		String AESKey = merchant.getASEKey();
		System.out.println("如意代付AES加密Key：" + AESKey);
		
		String encryptedData = Encrypt.AESEncodeEncrypt(payeeDetails, AESKey);
		System.out.println("如意代付AES加密后数据：" + encryptedData);
		
		return encryptedData;
	}
	/**
	 * 数据签名
	 * @param merchant
	 * @param payeeDetails
	 * @param tranDateTime
	 * @return
	 * @throws InvalidKeyException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 * @throws SignatureException
	 */
	private static String RSASign(RYdfMerchantConfig merchant, String payeeDetails, String tranDateTime)
			throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, SignatureException {
		// Charset=UTF-8
		// &CurCode=CNY
		// &MerId=22276540
		// &PayeeDetails=iv+PhT/T//vLrSDmWyXfEWbAS0c05RG1j6rDMTWP0/BA1lsJwRmg3NqYSrSv9h0uYNHoFMWfkNZoKjR5ToVau0As678MMCyTYzPl9gjRf27xyTXjFnegMKTiJ7KdG5Sb72t2kkH8wTNwm8zT3jY4XizvwbZzBKPxvuGVMWFp1XgbHPsoGZ5X73kIRAOlO2WPTlOEk7i9Hb1odF5BdTl70Q3Xmaa+W9YGUC4GVBcGvFCVPfeuPdDmnqjXZtuha7kwmcUiVsKLfP0Y1Hj75qp+W1nlSc6wOddC10ucKHotlYtJtJ1cB/QGeYIbkaWcjFX0GzKDfBO9qMRTo379rU9V9jAJqmxYaA6XpaG8ITwVPTn0DqoIdzIU/GXBUGsRWUaWoOOL3yIMBfhRKkJFx0aovQ==
		// &Remark=Test Remark
		// &SignType=RSA
		// &TranDateTime=20170125024944
		// &Version=2.1
		SortedMap<String, String> params = new TreeMap<String, String>();
		params.put(RYdfAppConstants.payout_Charset, charset);
		params.put(RYdfAppConstants.payout_CurCode, curcode);
		params.put(RYdfAppConstants.payout_MerId, merchant.getMerId());
		params.put(RYdfAppConstants.payout_PayeeDetails, payeeDetails);
		params.put(RYdfAppConstants.payout_Remark, remark);
		params.put(RYdfAppConstants.payout_SignType, signtype);
		params.put(RYdfAppConstants.payout_TranDateTime, tranDateTime);
		params.put(RYdfAppConstants.payout_Version, version);
		Set<Entry<String, String>> es = params.entrySet();// 所有参与传参的参数按照accsii排序（升序）
		Iterator<Entry<String, String>> it = es.iterator();
		StringBuffer sb = new StringBuffer();
		Entry<String, String> entry = null;
		while (it.hasNext()) {
			entry = it.next();
			String k = entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k) && !"sign_type".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		String data = sb.substring(0, sb.length() - 1);
		System.out.println("如意代付，待签名的数据：" + data);
		
		String privateKey = merchant.getRSAPrivateKey();
		System.out.println("如意代付RSA私钥：" + privateKey);
		
		String sign = RSASignature.sign(data, privateKey);
		System.out.println("如意代付，签名后的数据：" + sign);

		return sign;
	}
}