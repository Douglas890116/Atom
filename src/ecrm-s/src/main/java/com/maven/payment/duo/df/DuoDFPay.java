package com.maven.payment.duo.df;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.maven.payment.PayInterface;
import com.maven.util.Encrypt;
import com.maven.util.HttpPostUtil;
import com.maven.util.JSONUnit;
import com.maven.util.RSASignature;

import net.sf.json.JSONObject;

public class DuoDFPay {
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
	public String getUrl(DuoDFMerchantConfig merchant, DuoDFOrderConfig order) throws Exception  {
		String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String payeeDetails = AESEncrypt(merchant, order);
		String sign = RSASign(merchant, payeeDetails, time);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put(DuoDFAppConstants.payout_Version, version);
		params.put(DuoDFAppConstants.payout_SignType, signtype);
		params.put(DuoDFAppConstants.payout_SignInfo, sign);
		params.put(DuoDFAppConstants.payout_TranDateTime, time);
		params.put(DuoDFAppConstants.payout_Charset, charset);
		params.put(DuoDFAppConstants.payout_CurCode, curcode);
		params.put(DuoDFAppConstants.payout_Remark, remark);
		params.put(DuoDFAppConstants.payout_MerId, merchant.getMerId());
		params.put(DuoDFAppConstants.payout_PayeeDetails, payeeDetails);
		
		String result = HttpPostUtil.doPostSubmitMap3(merchant.getPayUrl(), params);
		System.out.println("多多代付请求响应结果原文：" + result);
		
		if(result.startsWith("[") && result.endsWith("]"))
			result = result.substring(1, result.length() - 1);
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap = JSONUnit.getMapFromJsonNew(result);
		if (resultMap.get("code") != null && resultMap.get("code").equals("1088")) {
			return PayInterface.PAY_SUCCESS;
		} else {
			return resultMap.get("message");
		}
	}
	/**
	 * 加密参数
	 * @param merchant
	 * @param order
	 * @return
	 * @throws Exception 
	 */
	private static String AESEncrypt(DuoDFMerchantConfig merchant, DuoDFOrderConfig order) throws Exception {
		JSONObject payeeInfo = new JSONObject();
		payeeInfo.put(DuoDFAppConstants.payoutDetail_OrdId, order.getOrdId());
		payeeInfo.put(DuoDFAppConstants.payoutDetail_RecvBankAcctName, order.getAccountName());
		payeeInfo.put(DuoDFAppConstants.payoutDetail_RecvBankAccNumber, order.getAccountBankNum());
		payeeInfo.put(DuoDFAppConstants.payoutDetail_BankCode, order.getBankCode());
		payeeInfo.put(DuoDFAppConstants.payoutDetail_RecvBankProvince, order.getAccountBankProvince());
		payeeInfo.put(DuoDFAppConstants.payoutDetail_RecvBankCity, order.getAccountBankCity());
		payeeInfo.put(DuoDFAppConstants.payoutDetail_RecvBankBranch, order.getAccountBankBranch());
		payeeInfo.put(DuoDFAppConstants.payoutDetail_OrdAmt, order.getAmount());
		payeeInfo.put(DuoDFAppConstants.payoutDetail_CorpPersonFlag, corppersonflag);// 固定值： 1
		payeeInfo.put(DuoDFAppConstants.payoutDetail_NotifyURL, merchant.getNotifyUrl());
		
		String payeeDetails = "[" + payeeInfo.toString() + "]";
		System.out.println("多多代付PayeeDetails参数原文：" + payeeDetails);
		
		String AESKey = merchant.getASEKey();
		System.out.println("多多代付AES加密Key：" + AESKey);
		
		String encryptedData = Encrypt.AESEncodeEncrypt(payeeDetails, AESKey);
		System.out.println("多多代付AES加密后数据：" + encryptedData);
		
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
	private static String RSASign(DuoDFMerchantConfig merchant, String payeeDetails, String tranDateTime)
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
		params.put(DuoDFAppConstants.payout_Charset, charset);
		params.put(DuoDFAppConstants.payout_CurCode, curcode);
		params.put(DuoDFAppConstants.payout_MerId, merchant.getMerId());
		params.put(DuoDFAppConstants.payout_PayeeDetails, payeeDetails);
		params.put(DuoDFAppConstants.payout_Remark, remark);
		params.put(DuoDFAppConstants.payout_SignType, signtype);
		params.put(DuoDFAppConstants.payout_TranDateTime, tranDateTime);
		params.put(DuoDFAppConstants.payout_Version, version);
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
		System.out.println("多多代付，待签名的数据：" + data);
		
		String privateKey = merchant.getRSAPrivateKey();
		System.out.println("多多代付RSA私钥：" + privateKey);
		
		String sign = RSASignature.sign(data, privateKey);
		System.out.println("多多代付，签名后的数据：" + sign);

		return sign;
	}
}