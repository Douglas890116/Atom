package com.maven.payment.gst;

import com.maven.payment.PayInterface;

/**
 * 国盛通支付
 * @author 
 *
 */
public class GSTPayMent<M extends GSTMerchantConfig,O extends GSTOrderConfig> implements PayInterface<M,O>{

	@Override
	public String save(GSTMerchantConfig merchant, GSTOrderConfig order) throws Exception {
//		AttrCheckout.checkout(merchant, false, YOMMerchantConfig.M_Save_Paramters.paramters());
//		AttrCheckout.checkout(order, false, YOMOrderConfig.O_Save_Paramters.paramters());
		
		
		GSTSave save = new GSTSave();
		
		String skipUrl = save.getUrl(merchant,order);
		
		return skipUrl;
	}

	@Override
	@Deprecated
	public String pay(GSTMerchantConfig merchant, GSTOrderConfig order) throws Exception {
		return "";
	}

	public static void main(String[] args) throws Exception {
		PayInterface<GSTMerchantConfig,GSTOrderConfig> __yyePay = new GSTPayMent<GSTMerchantConfig, GSTOrderConfig>();
		GSTMerchantConfig merchant = new GSTMerchantConfig();
		merchant.setPayUrl("http://9stpay.com/login/gateway/pay.html");
		merchant.setMerNo("898");
		merchant.setMerKey("724ec4d79c5fa7fb6018a939a7c080fd");
		merchant.setNotiUrl("http://api.hyzonghe.net/TPayment/YomPayCallback");
		merchant.setRefererUrl("http://api.hyzonghe.net/");
		
		GSTOrderConfig  __yeeorder =  new GSTOrderConfig();
		__yeeorder.setBankCode("WEIXIN");
		__yeeorder.setOrderAmount("100");
		__yeeorder.setCustomerIp("192.168.1.1");
		__yeeorder.setOrderNo("35E88CCB2A074197AF5C8B6420F98373");
		__yeeorder.setPaytype("2");
//		__yeeorder.setInputCharset("UTF-8");可不填写，有默认值
//		__yeeorder.setReturnParams("1");//回调参数集合
//		__yeeorder.setSian(sian);//不是请求参数
		
		
		String url = __yyePay.save(merchant, __yeeorder);
		String[] params = url.split("\\?")[1].split("&");
		for (String string : params) {
			System.err.println(string);
		}
		System.err.println(url);
	}
	

}
