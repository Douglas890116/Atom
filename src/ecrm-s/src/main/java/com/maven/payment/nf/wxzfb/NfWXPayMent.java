package com.maven.payment.nf.wxzfb;

import java.util.UUID;

import com.maven.payment.PayInterface;

import net.sf.json.JSONObject;

/**
 * 
 * @author 
 *
 */
public class NfWXPayMent<M extends NfWXMerchantConfig,O extends NfWXOrderConfig> implements PayInterface<M,O>{

	@Override
	public String save(NfWXMerchantConfig merchant, NfWXOrderConfig order) throws Exception {
		
		String requestUrl = NfWXPaySignUtil.getRequestUrl(merchant, order);
		System.out.println("支付请求链接="+requestUrl);
		return requestUrl;
	}

	@Override
	@Deprecated
	public String pay(NfWXMerchantConfig merchant, NfWXOrderConfig order) throws Exception {
		return "";
	}

	
	public static void main(String[] args) throws Exception {
		PayInterface<NfWXMerchantConfig,NfWXOrderConfig> __yyePay = new NfWXPayMent<NfWXMerchantConfig, NfWXOrderConfig>();
		NfWXMerchantConfig merchant = new NfWXMerchantConfig();
		merchant.setMerNo("755000089");
		merchant.setMerKey("b17a98e4-24e2-11e7-ac95-5bb4754f3d40");

		merchant.setNotifyUrl("http://api.hyzonghe.net/TPayment/ZPayCallback");
		merchant.setPageUrl("http://api.hyzonghe.net/TPayment/ZPayCallback");
		merchant.setPayUrl("https://pay.dinpay.com/gateway");
		
		NfWXOrderConfig  __yeeorder =  new NfWXOrderConfig();
		__yeeorder.setAmount(10.21);
		__yeeorder.setBankId("ICBC");
//		__yeeorder.setCreditType(creditType);默认
		__yeeorder.setOrderId(UUID.randomUUID().toString().substring(0, 30));
		__yeeorder.setPayMode("01");
		String url = __yyePay.save(merchant, __yeeorder);
		String[] params = url.split("\\?")[1].split("&");
		for (String string : params) {
			System.err.println(string);
		}
		
//		JSONObject object = JSONObject.fromObject(result);
//		System.out.println(object.optString("address")); ;
		
	}
	

}
