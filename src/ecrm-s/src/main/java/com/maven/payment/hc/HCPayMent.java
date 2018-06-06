package com.maven.payment.hc;

import java.util.UUID;

import com.maven.payment.PayInterface;
import com.maven.util.HttpPostUtil;

/**
 * 汇潮支付
 * @author 
 *
 */
public class HCPayMent<M extends HCMerchantConfig,O extends HCOrderConfig> implements PayInterface<M,O>{

	@Override
	public String save(HCMerchantConfig merchant, HCOrderConfig order) throws Exception {
		
		
		HCpayUtil save = new HCpayUtil();
		
		String skipUrl = save.getUrl(merchant,order);
		
		return skipUrl;
	}

	@Override
	@Deprecated
	public String pay(HCMerchantConfig merchant, HCOrderConfig order) throws Exception {
		
		return null;
	}

	public static void main(String[] args) throws Exception {
		PayInterface<HCMerchantConfig,HCOrderConfig> __yyePay = new HCPayMent<HCMerchantConfig, HCOrderConfig>();
		HCMerchantConfig merchant = new HCMerchantConfig();
		merchant.setPayUrl("https://gwapi.yemadai.com/pay/sslpayment");
		merchant.setMerNo("40888");
		merchant.setMerKey("jm");
		merchant.setNotiUrl("http://api.hyzonghe.net/TPayment/YomPayCallback");
		merchant.setReturnUrl("http://api.hyzonghe.net/TPayment/YomPayCallback");
		
		HCOrderConfig  __yeeorder =  new HCOrderConfig();
		__yeeorder.setBankCode("ICBC");
		__yeeorder.setOrderAmount("100.00");
		__yeeorder.setOrderNo(UUID.randomUUID().toString().replaceAll("-", ""));
		__yeeorder.setPaytype("noCard");
		
		String url = __yyePay.save(merchant, __yeeorder);
		String[] params = url.split("\\?")[1].split("&");
		for (String string : params) {
			System.err.println(string);
		}
		System.err.println(url);
	}
	

}
