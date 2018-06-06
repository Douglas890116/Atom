package com.maven.payment.nf;

import java.util.UUID;

import com.maven.payment.PayInterface;

import net.sf.json.JSONObject;

/**
 * 
 * @author 
 *
 */
public class NfPayMent<M extends NfMerchantConfig,O extends NfOrderConfig> implements PayInterface<M,O>{

	@Override
	public String save(NfMerchantConfig merchant, NfOrderConfig order) throws Exception {
		
		String requestUrl = NfPaySignUtil.getRequestUrl(merchant, order);
		System.out.println("支付请求链接="+requestUrl);
		return requestUrl;
	}

	@Override
	@Deprecated
	public String pay(NfMerchantConfig merchant, NfOrderConfig order) throws Exception {
		return "";
	}

	
	public static void main(String[] args) throws Exception {
		PayInterface<NfMerchantConfig,NfOrderConfig> __yyePay = new NfPayMent<NfMerchantConfig, NfOrderConfig>();
		NfMerchantConfig merchant = new NfMerchantConfig();
		merchant.setMerNo("0755143");
		merchant.setMerKey("7a03ea6a66dd3dd49f122e1528593453");

		merchant.setNotifyUrl("http://api.hyzonghe.net/TPayment/ZPayCallback");
		merchant.setPageUrl("http://api.hyzonghe.net/TPayment/ZPayCallback");
		merchant.setPayUrl("https://pay.newpaypay.com/center/proxy/partner/v1/pay.jsp");
		
		NfOrderConfig  __yeeorder =  new NfOrderConfig();
		__yeeorder.setAmount(120);
		__yeeorder.setBankId("ALIPAY");
//		__yeeorder.setCreditType(creditType);默认
		__yeeorder.setOrderId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 30));
		__yeeorder.setPayMode("09");
		
		
		String url = __yyePay.save(merchant, __yeeorder);
		String[] params = url.split("\\?")[1].split("&");
		for (String string : params) {
			System.err.println(string);
		}
		
//		JSONObject object = JSONObject.fromObject(result);
//		System.out.println(object.optString("address")); ;
		
	}
	

}
