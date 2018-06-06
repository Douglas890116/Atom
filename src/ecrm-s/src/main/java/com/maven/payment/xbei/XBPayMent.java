package com.maven.payment.xbei;

import com.maven.payment.PayInterface;
import com.maven.util.RandomString;
import com.maven.util.RandomStringNum;

/**
 * 新贝支付
 * @author 
 *
 */
public class XBPayMent<M extends XBMerchantConfig,O extends XBOrderConfig> implements PayInterface<M,O>{

	@Override
	public String save(XBMerchantConfig merchant, XBOrderConfig order) throws Exception {
//		AttrCheckout.checkout(merchant, false, YOMMerchantConfig.M_Save_Paramters.paramters());
//		AttrCheckout.checkout(order, false, YOMOrderConfig.O_Save_Paramters.paramters());
		
		
		XBSave save = new XBSave();
		
		String skipUrl = save.getUrl(merchant,order);
		
		return skipUrl;
	}

	@Override
	@Deprecated
	public String pay(XBMerchantConfig merchant, XBOrderConfig order) throws Exception {
		return "";
	}
//	INPUT_CHARSET=UTF-8&
//			RETURN_URL=&
//			NOTIFY_URL=http://api.hyzonghe.net/TPayment/YomPayCallback&
//			BANK_CODE=&
//			MER_NO=10982&
//			ORDER_NO=35E88CCB2A074197AF5C8B6410F98373&
//			ORDER_AMOUNT=100&
//			PRODUCT_NAME=&
//			PRODUCT_NUM=&
//			REFERER=http://api.hyzonghe.net&
//			CUSTOMER_IP=api.hyzonghe.net&
//			CUSTOMER_PHONE=0&
//			RECEIVE_ADDRESS=A&
//			RETURN_PARAMS=&
//			SIGN=e6dc28b6873c7a9f766913e97e3c8197
	public static void main(String[] args) throws Exception {
		PayInterface<XBMerchantConfig,XBOrderConfig> __yyePay = new XBPayMent<XBMerchantConfig, XBOrderConfig>();
		XBMerchantConfig merchant = new XBMerchantConfig();
		merchant.setMerKey("123456789012231");
		merchant.setMerNo("E02453");
		merchant.setNotiUrl("http://api.hyzonghe.net/TPayment/YomPayCallback");
		merchant.setPayUrl("https://gws.xbeionline.com/Gateway/XbeiPay");
		merchant.setReturnUrl("http://api.hyzonghe.net/TPayment/YomPayCallback");
		
		XBOrderConfig  __yeeorder =  new XBOrderConfig();
		__yeeorder.setBankCode("100012");
		__yeeorder.setOrderAmount("100");
		__yeeorder.setTradeIp("192.168.1.1");
		__yeeorder.setOrderNo(RandomString.createRandomString(30));
		__yeeorder.setOrderDate("20161212101010");
		
		
		String url = __yyePay.save(merchant, __yeeorder);
		String[] params = url.split("\\?")[1].split("&");
		for (String string : params) {
			System.err.println(string);
		}
		System.err.println(url);
		
		System.out.println("35E88CCB2A074197AF5C8B6410F98373".length());
	}
	

}
