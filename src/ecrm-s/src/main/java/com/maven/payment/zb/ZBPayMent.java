package com.maven.payment.zb;

import com.maven.payment.PayInterface;
import com.maven.util.RandomString;

public class ZBPayMent<M extends ZBMerchantConfig, O extends ZBOrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(ZBMerchantConfig merchant, ZBOrderConfig order) throws Exception {
		ZBSave save = new ZBSave();
		return save.getUrl(merchant, order);
	}

	@Override
	public String pay(ZBMerchantConfig merchant, ZBOrderConfig order) throws Exception {
		return null;
	}

	public static void main(String[] args) {
		PayInterface<ZBMerchantConfig, ZBOrderConfig> pay = new ZBPayMent<ZBMerchantConfig, ZBOrderConfig>();
		
		ZBMerchantConfig merchant = new ZBMerchantConfig();
		merchant.setCustomer("881011");
		merchant.setMd5Key("54a5f52002dd45b5893ca129378e508f");
		merchant.setPayUrl("https://gateway.zbpay.cc/GateWay/index.aspx");
		merchant.setAsynbackurl("http://pay.99scm.com/TPayment/ZBPayCallback");
		merchant.setSynbackurl("http://pay.99scm.com/TPayment/success");
		
		ZBOrderConfig order = new ZBOrderConfig();
		order.setOrderid(RandomString.UUID().substring(2));
		order.setAmount("2.00");
		order.setRequestTime((System.currentTimeMillis() / 1000) + "");
//		order.setBanktype("1004");// ＰＣ微信
//		order.setBanktype("1007");// 手机微信
		order.setBanktype("1006");// 手机支付宝
//		order.setBanktype("992"); // ＰＣ支付宝
//		order.setBanktype("993"); // QQ扫码
//		order.setBanktype("970"); // 招商网银
		order.setAttach("bank");
		
		try {
			pay.save(merchant, order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
