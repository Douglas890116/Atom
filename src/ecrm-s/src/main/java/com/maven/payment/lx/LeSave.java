package com.maven.payment.lx;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.lechinepay.channel.lepay.client.apppay.AppPay;
import com.lechinepay.channel.lepay.share.LePayParameters;
import com.maven.exception.LogicTransactionException;

public class LeSave {
	private static final String keyStoreType = "PKCS12";
	public String getUrl(LeMerchantConfig merchant, LeOrderConfig order) throws LogicTransactionException {
		Map<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put(LePayParameters.VERSION, "1.0.0");
        requestMap.put(LePayParameters.ENCODING, "UTF-8");
        requestMap.put(LePayParameters.SIGNATURE, "");
        requestMap.put(LePayParameters.REQ_RESERVED, "payment");
        requestMap.put(LePayParameters.MCH_ID, merchant.getMchId());
        requestMap.put(LePayParameters.CMP_APP_ID, merchant.getAppId());
        requestMap.put(LePayParameters.PAY_TYPE_CODE, order.getPayTypeCode());
        requestMap.put(LePayParameters.OUT_TRADE_NO, order.getOutTradeNo());
        requestMap.put(LePayParameters.TRADE_TIME, order.getTradeTime());
        requestMap.put(LePayParameters.AMOUNT, order.getAmount());
        requestMap.put(LePayParameters.SUMMARY, "Recharge");
        requestMap.put(LePayParameters.SUMMARY_DETAIL, "detail");
        requestMap.put(LePayParameters.DEVICE_ID, "L897554536354");
        requestMap.put(LePayParameters.DEVICE_IP, "127.0.0.1");
        requestMap.put(LePayParameters.RETURN_URL, merchant.getReturnUrl());
//		requestMap.put(LeAppConstants.request_version, "1.0.0");
//		requestMap.put(LeAppConstants.request_encoding, "UTF-8");
//		requestMap.put(LeAppConstants.request_signature, "");
//		requestMap.put(LeAppConstants.request_reqReserved, "payment");
//		requestMap.put(LeAppConstants.request_mchId, merchant.getMchId());
//		requestMap.put(LeAppConstants.request_cmpAppId, merchant.getAppId());
//		requestMap.put(LeAppConstants.request_payTypeCode, order.getPayTypeCode());
//		requestMap.put(LeAppConstants.request_outTradeNo, order.getOutTradeNo());
//		requestMap.put(LeAppConstants.request_tradeTime, order.getTradeTime());
//		requestMap.put(LeAppConstants.request_amount, order.getAmount());
//		requestMap.put(LeAppConstants.request_summary, "product");
//		requestMap.put(LeAppConstants.request_summaryDetail, "detail");
//		requestMap.put(LeAppConstants.request_deviceId, "L897554536354");
//		requestMap.put(LeAppConstants.request_deviceIp, order.getDeviceIp());
//		requestMap.put(LeAppConstants.request_buyerId, order.getBuyerId());
//		requestMap.put(LeAppConstants.request_returnUrl, merchant.getReturnUrl());
		System.out.println("乐信付接口请求参数 : " + requestMap.toString());
		
		System.out.println("商户私钥地址 : " + merchant.getKeyStorePath());
		System.out.println("商户私钥密码 : " + merchant.getKeyStorePassword());
		System.out.println("乐信公钥地址 : " + merchant.getCertificatePath());
		AppPay.init(merchant.getKeyStorePassword(), merchant.getKeyStorePath(), keyStoreType, merchant.getCertificatePath());
		Map<String, Object> responseMap = AppPay.execute(merchant.getPayUrl(), merchant.getShortUrl(), requestMap);
		System.out.println("请求结果\n" + responseMap);
		if(null != responseMap && null != responseMap.get("respCode") && responseMap.get("respCode").equals("000000")) {
			if (order.getPayTypeCode().equals("web.pay")) {
				return responseMap.get("webOrderInfo").toString();
			} else if (order.getPayTypeCode().equals("wap.pay")) {
				return responseMap.get("h5OrderInfo").toString();
			} else {
				throw new LogicTransactionException("请求失败");
			}
		}
		return "";
	}
	/**
	 * 验证回调参数
	 * @param params
	 * @param keyStorePath
	 * @param certificatePath
	 * @param keyStorePassword
	 * @return
	 */
	public static boolean checkReponseSign(Map<String, Object> params, String keyStorePath, String certificatePath, String keyStorePassword) {
		AppPay.init(keyStorePassword, keyStorePath, keyStoreType, certificatePath);
		return AppPay.verify(params);
	}
	
	public static void main(String[] args) {
		jmtlxf();
		dwlxf();
	}
	
	/**
	 * 帝王-乐信付签名
	 * @param request
	 * @return
	 */
	public static Object dwlxf(){
		try {
			BigDecimal ordermount = new BigDecimal("123.00");
			String mchid = "61003";
			String appid = "52504";
			
			Map<String, Object> requestMap = new HashMap<String, Object>();
	        requestMap.put(LePayParameters.VERSION, "1.0.0");
	        requestMap.put(LePayParameters.ENCODING, "UTF-8");
	        requestMap.put(LePayParameters.SIGNATURE, "");
	        requestMap.put(LePayParameters.REQ_RESERVED, "payment");
	        requestMap.put(LePayParameters.MCH_ID, mchid);//商户ID
	        requestMap.put(LePayParameters.CMP_APP_ID, appid);//应用ID
	        requestMap.put(LePayParameters.PAY_TYPE_CODE, "web.pay");
	        requestMap.put(LePayParameters.OUT_TRADE_NO, UUID.randomUUID().toString().replaceAll("-", ""));
	        requestMap.put(LePayParameters.TRADE_TIME, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
	        requestMap.put(LePayParameters.AMOUNT, ordermount.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_DOWN).toString());
	        requestMap.put(LePayParameters.SUMMARY, "Recharge");
	        requestMap.put(LePayParameters.SUMMARY_DETAIL, "detail");
	        requestMap.put(LePayParameters.DEVICE_ID, "L897554536354");
	        requestMap.put(LePayParameters.DEVICE_IP, "127.0.0.1");
	        requestMap.put(LePayParameters.RETURN_URL, "http://api.hyzonghe.net/TPayment/success");
	        
			String KeyStorePath = "F:/RELEASE/ecrm-api/WEB-INF/certificate/lx/dwkey.pfx";
			String KeyStorePassword = "ZHdwYXNzd29yZA==";
			String CertificatePath = "F:/RELEASE/ecrm-api/WEB-INF/certificate/lx/pdtserver.cer";
			String PayUrl = "https://openapi.unionpay95516.cc/pre.lepay.api";
			String ShortUrl = "/order/add";
			
			AppPay.init(KeyStorePassword, KeyStorePath, "PKCS12", CertificatePath);
			Map<String, Object> responseMap = AppPay.execute(PayUrl, ShortUrl, requestMap);
			System.out.println("帝王-乐信付签名及请求结果：" + responseMap);
			
			return responseMap;
		}catch (Exception e) {
			System.out.println("帝王-乐信付签名及请求异常："+e.getLocalizedMessage());
		}
		return null;
	}
	
	/**
	 * 金马腾-乐信付签名
	 * @param request
	 * @return
	 */
	public static Object jmtlxf(){
		try {
			BigDecimal ordermount = new BigDecimal("122.00");
			String mchid = "62088";
			String appid = "55523";
			
			Map<String, Object> requestMap = new HashMap<String, Object>();
	        requestMap.put(LePayParameters.VERSION, "1.0.0");
	        requestMap.put(LePayParameters.ENCODING, "UTF-8");
	        requestMap.put(LePayParameters.SIGNATURE, "");
	        requestMap.put(LePayParameters.REQ_RESERVED, "payment");
	        requestMap.put(LePayParameters.MCH_ID, mchid);//商户ID
	        requestMap.put(LePayParameters.CMP_APP_ID, appid);//应用ID
	        requestMap.put(LePayParameters.PAY_TYPE_CODE, "web.pay");
	        requestMap.put(LePayParameters.OUT_TRADE_NO, UUID.randomUUID().toString().replaceAll("-", ""));
	        requestMap.put(LePayParameters.TRADE_TIME, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
	        requestMap.put(LePayParameters.AMOUNT, ordermount.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_DOWN).toString());
	        requestMap.put(LePayParameters.SUMMARY, "Recharge");
	        requestMap.put(LePayParameters.SUMMARY_DETAIL, "detail");
	        requestMap.put(LePayParameters.DEVICE_ID, "L897554536354");
	        requestMap.put(LePayParameters.DEVICE_IP, "127.0.0.1");
	        requestMap.put(LePayParameters.RETURN_URL, "http://api.hyzonghe.net/TPayment/success");
	        
			String KeyStorePath = "F:/RELEASE/ecrm-api/WEB-INF/certificate/lx/jmtkey.pfx";
			String KeyStorePassword = "am10cGFzc3dvcmQ=";
			String CertificatePath = "F:/RELEASE/ecrm-api/WEB-INF/certificate/lx/pdtserver.cer";
			String PayUrl = "https://openapi.unionpay95516.cc/pre.lepay.api";
			String ShortUrl = "/order/add";
			
			AppPay.init(KeyStorePassword, KeyStorePath, "PKCS12", CertificatePath);
			Map<String, Object> responseMap = AppPay.execute(PayUrl, ShortUrl, requestMap);
			System.out.println("金马腾-乐信付签名及请求结果：" + responseMap);
			
			return responseMap;
		}catch (Exception e) {
			System.out.println("金马腾-乐信付签名及请求异常："+e.getLocalizedMessage());
		}
		return null;
	}
}