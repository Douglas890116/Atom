package com.maven.payment.yunsheng;

import java.util.UUID;

import com.maven.payment.PayInterface;
import com.maven.util.HttpPostUtil;

/**
 * 云盛支付
 * @author 
 *
 */
public class YSPayMent<M extends YSMerchantConfig,O extends YSOrderConfig> implements PayInterface<M,O>{

	@Override
	public String save(YSMerchantConfig merchant, YSOrderConfig order) throws Exception {
		
		
		YSpayUtil save = new YSpayUtil();
		
		String skipUrl = save.getUrl(merchant,order);
		
		return skipUrl;
	}

	@Override
	public String pay(YSMerchantConfig merchant, YSOrderConfig order) throws Exception {
		return null;
	}

	public static void main(String[] args) throws Exception {
		PayInterface<YSMerchantConfig,YSOrderConfig> __yyePay = new YSPayMent<YSMerchantConfig, YSOrderConfig>();
		YSMerchantConfig merchant = new YSMerchantConfig();
		merchant.setPayUrl("http://gateway.ynnpay.com/Recharge/Index");
		merchant.setMerNo("2000001151");
		merchant.setMerKey("79e4ddadb8d74853ae3d6af5e9255f09");
		merchant.setNotiUrl("http://api.hyzonghe.net/TPayment/YomPayCallback");
		merchant.setReturnUrl("http://api.hyzonghe.net/TPayment/YomPayCallback");
		
		YSOrderConfig  __yeeorder =  new YSOrderConfig();
		__yeeorder.setBankCode("");
		__yeeorder.setOrderAmount("100.00");
		__yeeorder.setOrderNo(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 30));
		__yeeorder.setPayType("4");
		
		String url = __yyePay.save(merchant, __yeeorder);
		String[] params = url.split("\\?")[1].split("&");
		for (String string : params) {
			System.err.println(string);
		}
		System.err.println(url);
	}
	

}
