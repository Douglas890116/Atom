package com.maven.payment.jeanpay;

import java.util.UUID;

import com.maven.payment.PayInterface;
import com.maven.util.HttpPostUtil;

import net.sf.json.JSONObject;

/**
 * 极付支付
 * @author 
 *
 */
public class JEANPAYPayMent<M extends JEANPAYMerchantConfig,O extends JEANPAYOrderConfig> implements PayInterface<M,O>{

	@Override
	public String save(JEANPAYMerchantConfig merchant, JEANPAYOrderConfig order) throws Exception {
		
		
		JEANPAYpayUtil save = new JEANPAYpayUtil();
		
		String skipUrl = save.getUrl(merchant,order);
		
		return skipUrl;
	}

	@Override
	public String pay(JEANPAYMerchantConfig merchant, JEANPAYOrderConfig order) throws Exception {
		//提交付款
		JEANPAYpayUtil save = new JEANPAYpayUtil();
		
		String result = save.getPayUrl(merchant,order);
		
		JSONObject jsonObject = JSONObject.fromObject(result);
		if(jsonObject.getString("code").equals("0")) {
			return PayInterface.PAY_SUCCESS;
		}
		return jsonObject.getString("info");
	}

	public static void main(String[] args) throws Exception {
		PayInterface<JEANPAYMerchantConfig,JEANPAYOrderConfig> __yyePay = new JEANPAYPayMent<JEANPAYMerchantConfig, JEANPAYOrderConfig>();
		JEANPAYMerchantConfig merchant = new JEANPAYMerchantConfig();
		merchant.setPayUrl("https://pay.jeanpay.com/payment/gateway");
		merchant.setMerNo("80060092");
		merchant.setMerKey("EBA277BA92D4B5E199AF59CB3C8C5F2B");
		merchant.setNotiUrl("http://api.hyzonghe.net/TPayment/YomPayCallback");
		merchant.setReturnUrl("http://api.hyzonghe.net/TPayment/YomPayCallback");
		
		JEANPAYOrderConfig  __yeeorder =  new JEANPAYOrderConfig();
		__yeeorder.setBankCode("ICBC");
		__yeeorder.setOrderAmount("100.00");
		__yeeorder.setOrderNo(UUID.randomUUID().toString().replaceAll("-", ""));
		__yeeorder.setPaytype("PTY_ONLINE_PAY");
		__yeeorder.setServiceName("BANK_PAY");
		
		String url = __yyePay.save(merchant, __yeeorder);
		String[] params = url.split("\\?")[1].split("&");
		for (String string : params) {
			System.err.println(string);
		}
		System.err.println(url);
	}
	

}
