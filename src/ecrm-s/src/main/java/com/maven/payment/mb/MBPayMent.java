package com.maven.payment.mb;

import java.util.UUID;

import com.maven.payment.PayInterface;
import com.maven.util.HttpPostUtil;

/**
 * 摩宝支付
 * @author 
 *
 */
public class MBPayMent<M extends MBMerchantConfig,O extends MBOrderConfig> implements PayInterface<M,O>{

	@Override
	public String save(MBMerchantConfig merchant, MBOrderConfig order) throws Exception {
		
		
		MBpayUtil save = new MBpayUtil();
		
		String skipUrl = save.getUrl(merchant,order);
		
		return skipUrl;
	}

	@Override
	public String pay(MBMerchantConfig merchant, MBOrderConfig order) throws Exception {
		//提交付款
		MBpayUtil save = new MBpayUtil();
		
		String result = save.getPayUrl(merchant,order);
		
		return result.equals("0") ? PayInterface.PAY_SUCCESS : result + "";
	}

	public static void main(String[] args) throws Exception {
		PayInterface<MBMerchantConfig,MBOrderConfig> __yyePay = new MBPayMent<MBMerchantConfig, MBOrderConfig>();
		MBMerchantConfig merchant = new MBMerchantConfig();
		merchant.setPayUrl("https://pay.jeanpay.com/payment/gateway");
		merchant.setMerNo("80060092");
		merchant.setMerKey("EBA277BA92D4B5E199AF59CB3C8C5F2B");
		merchant.setNotiUrl("http://api.hyzonghe.net/TPayment/YomPayCallback");
		merchant.setReturnUrl("http://api.hyzonghe.net/TPayment/YomPayCallback");
		
		MBOrderConfig  __yeeorder =  new MBOrderConfig();
		__yeeorder.setBankCode("ICBC");
		__yeeorder.setOrderAmount("100.00");
		__yeeorder.setOrderNo(UUID.randomUUID().toString().replaceAll("-", ""));
		
		String url = __yyePay.save(merchant, __yeeorder);
		String[] params = url.split("\\?")[1].split("&");
		for (String string : params) {
			System.err.println(string);
		}
		System.err.println(url);
	}
	

}
