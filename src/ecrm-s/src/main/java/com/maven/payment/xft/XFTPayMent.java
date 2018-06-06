package com.maven.payment.xft;

import com.maven.payment.PayInterface;
import com.maven.util.RandomString;

public class XFTPayMent<M extends XFTMerchantConfig, O extends XFTOrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(XFTMerchantConfig merchant, XFTOrderConfig order) throws Exception {
		XFTUtil save = new XFTUtil();
		return save.getUrl(merchant, order);
	}

	@Override
	public String pay(XFTMerchantConfig merchant, XFTOrderConfig order) throws Exception {
		return null;
	}

	public static void main(String[] args) {
		PayInterface<XFTMerchantConfig, XFTOrderConfig> payment = new XFTPayMent<XFTMerchantConfig, XFTOrderConfig>();
		
		XFTMerchantConfig merchant = new XFTMerchantConfig();
		merchant.setMerId("100000000002363");
		merchant.setMerKey("6cf103f1755a4ef65210441a576a6336a9d3bead453eaaffg0438d3b47fg33ae");
		merchant.setPayUrl("https://ebank.xfuoo.com/payment/v1/order/");
		merchant.setReturnUrl("http://api.hyzonghe.net/TPayment/success");
		merchant.setNotifyUrl("http://api.hyzonghe.net/TPayment/XFTCallback");
		
		XFTOrderConfig order = new XFTOrderConfig();
		order.setOrderId("TEST".concat(RandomString.UUID().substring(4)));
		order.setOrderAmount("50.00");
		order.setBankCode("CMB");
		order.setIsApp("pc");
		
		try {
			String url = payment.save(merchant, order);
			System.out.println(url);
			if (!order.getIsApp().equals("app")) {
				String urlStr = url.toString();
				String baseUrl = urlStr.split("[?]")[0];
				String query = urlStr.split("[?]")[1];
				String[] params = query.split("&");

				System.out.println();
				System.out.println("\t\t<form action=\"" + baseUrl + "\" method=\"post\">");
				for (String param : params) {
					if (param.split("=").length > 1)
					System.out.println("\t\t<input type=\"text\" name=\"" + param.split("=")[0] + "\" value=\"" + param.split("=")[1] + "\" readonly=\"readonly\"/>");
				}
				System.out.println("\t\t<br/><input type=\"submit\" value=\"提交\"/>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
