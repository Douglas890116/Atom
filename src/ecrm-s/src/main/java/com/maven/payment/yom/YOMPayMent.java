package com.maven.payment.yom;

import com.maven.payment.PayInterface;

/**
 * 优付支付
 * @author 
 *
 */
public class YOMPayMent<M extends YOMMerchantConfig,O extends YOMOrderConfig> implements PayInterface<M,O>{

	@Override
	public String save(YOMMerchantConfig merchant, YOMOrderConfig order) throws Exception {
//		AttrCheckout.checkout(merchant, false, YOMMerchantConfig.M_Save_Paramters.paramters());
//		AttrCheckout.checkout(order, false, YOMOrderConfig.O_Save_Paramters.paramters());
		
		
		YOMSave save = new YOMSave();
		
		String skipUrl = save.getUrl(
				order.getInputCharset(), merchant.getReturnUrl(), merchant.getNotiUrl(), order.getBankCode(), 
				order.getOrderNo(), order.getOrderAmount(), order.getProductName(), order.getProductNum(), 
				merchant.getRefererUrl(), order.getCustomerIp(), order.getCustomerPhone(), order.getCustomerAddress(), order.getReturnParams(), 
				merchant.getMerKey(), merchant.getPayUrl(), merchant.getMerNo());
		
		return skipUrl;
	}

	@Override
	@Deprecated
	public String pay(YOMMerchantConfig merchant, YOMOrderConfig order) throws Exception {
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
		PayInterface<YOMMerchantConfig,YOMOrderConfig> __yyePay = new YOMPayMent<YOMMerchantConfig, YOMOrderConfig>();
		YOMMerchantConfig merchant = new YOMMerchantConfig();
		merchant.setMerKey("0dg4b37ior3f2i1024jhnufyybp0uma66slm94t9f2ql04x51452d6wtv6mki8ul");
		merchant.setMerNo("10982");
		merchant.setNotiUrl("http://api.hyzonghe.net/TPayment/YomPayCallback");
		merchant.setPayUrl("https://www.yompay.com/Payapi/");
		merchant.setRefererUrl("http://api.hyzonghe.net/");
		merchant.setReturnUrl("");
		
		YOMOrderConfig  __yeeorder =  new YOMOrderConfig();
		__yeeorder.setBankCode("ABC");
		__yeeorder.setOrderAmount("100");
		__yeeorder.setCustomerIp("192.168.1.1");
		__yeeorder.setProductNum("1");//
		__yeeorder.setCustomerAddress("A");//可不填写，有默认值
		__yeeorder.setCustomerPhone("13631223451");//可不填写，有默认值
//		__yeeorder.setInputCharset("UTF-8");可不填写，有默认值
		__yeeorder.setOrderNo("35E88CCB2A074197AF5C8B6410F98373");
		__yeeorder.setReturnParams("1");//回调参数集合
//		__yeeorder.setSian(sian);//不是请求参数
		
		
		String url = __yyePay.save(merchant, __yeeorder);
		String[] params = url.split("\\?")[1].split("&");
		for (String string : params) {
			System.err.println(string);
		}
		System.err.println(url);
	}
	

}
