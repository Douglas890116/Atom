package com.maven.payment.hr.df;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.maven.payment.PayInterface;
import com.maven.payment.hr.HRAppConstants;
import com.maven.payment.hr.RSAClient;
import com.maven.util.HttpPostUtil;
import com.maven.util.JSONUnit;

/**
 * 华仁代付
 * 
 * @author klay
 *
 */
public class HRDFPayMent<M extends HRDFMerchantConfig, O extends HRDFOrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(HRDFMerchantConfig merchant, HRDFOrderConfig order) throws Exception {
		return null;
	}

	@Override
	public String pay(M merchant, O order) throws Exception {
		HRDFSave save = new HRDFSave();
		String url = save.getUrl(merchant, order);
		String encodedStr = HttpPostUtil.doGetSubmit(url);
		System.out.println("华仁代付结果密文：\n" + encodedStr);
		String result = RSAClient.decode(encodedStr, merchant.getMerRsaPrivateKey());
		result = URLDecoder.decode(result, "utf-8");
		System.out.println("华仁代付结果原文：\n" + result);
		if (result.startsWith("[") && result.endsWith("]"))
			result = result.substring(1, result.length() - 1);
		Map<String, String> map = new HashMap<String, String>();
		map = JSONUnit.getMapFromJsonNew(result);
		if (map.get("ResCode") != null && map.get("ResCode").equals("0150")) {
			return PayInterface.PAY_SUCCESS;
		} else if (StringUtils.isNotBlank(map.get("ResCode")) || StringUtils.isNotBlank(map.get("ResDesc"))) {
			StringBuffer sb = new StringBuffer("==========华仁代付，出款失败==========\n");
			sb.append("错误信息代码ResCode【").append(map.get("ResCode")).append("】\n");
			sb.append("错误信息说明ResDesc【").append(map.get("ResDesc")).append("】");
			return sb.toString();
		}
		return "未知错误，请联系管理员！";
	}

	public static void main(String[] args) {
		PayInterface<HRDFMerchantConfig, HRDFOrderConfig> __hrpay = new HRDFPayMent<HRDFMerchantConfig, HRDFOrderConfig>();
		HRDFMerchantConfig __merchant = new HRDFMerchantConfig();
		HRDFOrderConfig __order = new HRDFOrderConfig();
		String payURL = "http://api.hr-pay.com/PayInterface.aspx";
		String RequestURL = "http://api.hyzonghe.net/";
		String bankNo = "103100000026";
		// 华仁接口公钥
		String HRPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdlfulqoHwbI5Jx3QBitq8MURZd0iAcTx+Or2LxYe/rniRxMoAOFs84KzNpML/0kmL2pzni9oXuAXDTxUsyr4DBMS78KyGQZP5HkWUbNEQZ2tMmtIWmu4Mjs0EInmfSsez5fV+OUeEjQZ9OvWdJ3fkBgOUoW9nLXYO4srQuGm+LwIDAQAB";
		// 商户公钥
		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCVTdDVOFay0TWqlKXhigCqbhmvewluFMZnfsAaaaodnIkTiOG+c8YWL615tQaxNU9zXAuSTSVn5A8nKJmEQs5wbS3JYSTOwCtLrXWLrMr8W4L7kFC81bd2/w6mSHISsOrQ6a1CVUQAkIktN1/TTuEpRYhR4S7PoS01HGMy1piqAwIDAQAB";
		// 商户私钥
		String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJVN0NU4VrLRNaqUpeGKAKpuGa97CW4Uxmd+wBppqh2ciROI4b5zxhYvrXm1BrE1T3NcC5JNJWfkDycomYRCznBtLclhJM7AK0utdYusyvxbgvuQULzVt3b/DqZIchKw6tDprUJVRACQiS03X9NO4SlFiFHhLs+hLTUcYzLWmKoDAgMBAAECgYAtB7kydbmAWSTse8TED1FAFdDdYihn8RAd6taZoMDUCYA2ShR70oMt8ddKW9TW4ZNC4cIDsAzWFqyTTOVwRI3qWGxlxkJl5EnFu4hcWXFBZT9aQ4pZOMXcpZAadkk3lrintaNPsZXhqObHpxoFDkzQpPpr9AIu9VAAfP49ZW1DoQJBANGTtTHFt9/Qup97yb/u67SRCPFA2Pf1ensPUIcmhI90oVHSYbwsc6jLqXRTyRCm/ZnnM9T5jmRu1anc8rwUQ/sCQQC2YEK5brucahUQzD9TFAfdTlMMAJ+SPH1QJ9jYbkRVa4i/a/FzBR4BCrl8hGjWQQXNhc2w2JzqLZpqbugdfMuZAkAQhFCabJeyNvQOT6Y1zzGaWHfY86Bl4l3Vxv40uI9n8uwn06nKN8KhwfNH7LaC7nY8I+GM3mIffjCuo3Ap7HrzAkAVGs6d5tKPJzeI2hn54zeFxKqXmPreUWGvBO1zHk+KEwegHz2xscXnGPaeEjSPlra1Mea7sFV4RA66glsaDncBAkBO16bjDVgfCDGjEM+mzAERDuxSYWnoJJoYtzv+i6CeFqgaq96RoYVqo+RjgNumiSeTVr1JzXhBPsQooRJP7rJy";
		// 签名MD5Key
		String md5Key = "MyXb8HZ";
		// 商户号
		String merNo = "110102";

		try {
			__merchant.setHRRsaPublicKey(HRPublicKey);
			__merchant.setMd5Key(md5Key);
			__merchant.setMerchantCode(merNo);
			__merchant.setMerRsaPrivateKey(privateKey);
			__merchant.setMerRsaPublicKey(publicKey);
			__merchant.setReturnUrl(RequestURL + "/TPayment/success");
			__merchant.setPayUrl(payURL);

			__order.setV_oid("20170517-110102-120127");
			__order.setV_accountcity("accountcity");
			__order.setV_accountprovince("accountprovince");
			__order.setV_amount("10.00");
			__order.setV_bankname("中国农业银行");
			__order.setV_bankno(bankNo);
			__order.setV_oid(HRAppConstants.getOderNumber(merNo));
			__order.setV_payeecard("6228481629731488073");
			__order.setV_payeename("黄一曼");

			String result = __hrpay.pay(__merchant, __order);
			System.out.println(result);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}