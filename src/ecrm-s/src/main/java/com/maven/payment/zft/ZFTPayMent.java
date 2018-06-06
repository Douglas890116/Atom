package com.maven.payment.zft;

import com.maven.payment.PayInterface;
import com.maven.payment.zft.ZFTAppConstants.ZFT_PayType;
import com.maven.util.RandomString;

public class ZFTPayMent<M extends ZFTMerchantConfig, O extends ZFTOrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(ZFTMerchantConfig merchant, ZFTOrderConfig order) throws Exception {
		ZFTSave save = new ZFTSave();
		String url =  save.getUrl(merchant, order);
		return url;
	}

	@Override
	public String pay(ZFTMerchantConfig merchant, ZFTOrderConfig order) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		PayInterface<ZFTMerchantConfig, ZFTOrderConfig> payment = new ZFTPayMent<ZFTMerchantConfig, ZFTOrderConfig>();
		
		ZFTMerchantConfig merchant = new ZFTMerchantConfig();
		merchant.setMerId("101000000002504");
		merchant.setShaKey("5g13e78ce14g2df1198g35e63cc247c756f684c134c95664g23e7g9d21b0c3aa");
		merchant.setMerEmail("fin@jintabet.com");
		merchant.setPayType(ZFT_PayType.直连模式.value);
		merchant.setPayUrl("https://ebank.zftong.net/payment/v1/order/");
		merchant.setReturnUrl("http://api.hyzonghe.net/TPayment/success");
		merchant.setNotifyUrl("http://api.hyzonghe.net/TPayment/ZFTCallback");
		merchant.setAppName("m.egg777.com");
		
		ZFTOrderConfig order = new ZFTOrderConfig();
		order.setOrderNo("TEST"+RandomString.UUID().substring(4));
		order.setOrderAmount("5.0");
		order.setOrderBank("ALIPAY");
		order.setOrderTitle("Recharge");
		order.setOrderBody("Recharger");
		
		order.setIsApp("app");
		
		order.setUserIP("180.232.108.150");
		order.setAppMsg(RandomString.UUID().toLowerCase());
		order.setAppType("wap");
		
		try {
			String url = payment.save(merchant, order);
			if ("ALIPAY".equals(order.getOrderBank())
					|| "WXPAY".equals(order.getOrderBank())
					|| "QQPAY".equals(order.getOrderBank())) {
				System.out.println();
				String baseUrl = url.split("[?]")[0];
				System.out.println("\t\t<form action=\"" + baseUrl + "\" method=\"POST\">");
				String query = url.split("[?]")[1];
				String[] params = query.split("&");
				for (String param : params) {
					if (param.split("=").length > 1) {
						System.out.println("\t\t<input type=\"text\" name=\"" + param.split("=")[0] + "\" value=\""
								+ param.split("=")[1] + "\" readonly=\"readonly\"/><br/>");
					} else {
						System.err.println("\t\t<input type=\"text\" name=\"" + param.split("=")[0]
								+ "\" value=\"\" readonly=\"readonly\"/><br/>");
					}
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
