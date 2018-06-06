package com.maven.payment.jft;

import com.maven.payment.PayInterface;

/**
 * 国盛通支付
 * @author 
 *
 */
public class JFTPayMent<M extends JFTMerchantConfig,O extends JFTOrderConfig> implements PayInterface<M,O>{

	@Override
	public String save(JFTMerchantConfig merchant, JFTOrderConfig order) throws Exception {
//		AttrCheckout.checkout(merchant, false, YOMMerchantConfig.M_Save_Paramters.paramters());
//		AttrCheckout.checkout(order, false, YOMOrderConfig.O_Save_Paramters.paramters());
		
		
		JFTSave save = new JFTSave();
		
		String skipUrl = save.getUrl(merchant,order);
		
		return skipUrl;
	}

	@Override
	@Deprecated
	public String pay(JFTMerchantConfig merchant, JFTOrderConfig order) throws Exception {
		return "";
	}

	public static void main(String[] args) throws Exception {
		PayInterface<JFTMerchantConfig,JFTOrderConfig> __yyePay = new JFTPayMent<JFTMerchantConfig, JFTOrderConfig>();
		JFTMerchantConfig merchant = new JFTMerchantConfig();
		merchant.setPayUrl("http://cashier.chinapay360.com/payment/");
		merchant.setMerNo("5900696");
		merchant.setMerKey("DFPCC8pKPpXAjbWmMBX62BJjbck3Z3CS");
		merchant.setNotiUrl("http://api.hyzonghe.net/TPayment/YomPayCallback");
		merchant.setReturnUrl("http://api.hyzonghe.net/TPayment/YomPayCallback");
		
		JFTOrderConfig  __yeeorder =  new JFTOrderConfig();
		__yeeorder.setBankCode("ICBC");
		__yeeorder.setOrderAmount("100");
		__yeeorder.setCustomerIp("192.168.1.1");
		__yeeorder.setOrderNo("35E88CCB2A074197AF5C8B6420F98373");
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
