package com.maven.payment.mf;

import com.maven.payment.PayInterface;
import com.maven.util.RandomString;

public class MFPayMent<M extends MFMerchantConfig, O extends MFOrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(M merchant, O order) throws Exception {
		MFSave save = new MFSave();
		return save.getUrl(merchant, order);
	}

	@Override
	public String pay(M merchant, O order) throws Exception {
		return null;
	}

	public static void main(String[] args) {
		try {
			MFMerchantConfig merchant = new MFMerchantConfig();
			MFOrderConfig order = new MFOrderConfig();
			/** 商户信息 **/
			merchant.setMerId("52");
			merchant.setMerKey("KrOKsdJVHl0yi04JPNoIHJ90K4B1aHVo");
			merchant.setMerInfo("api");
			merchant.setNeedResponse("1");// 固定值1
			merchant.setPayUrl("http://sj.miaojiesuan.net/hspay/node/");
			merchant.setReturnUrl("http://api.hyzonghe.net/TPayment/MFPayCallback");

			order.setAmount("10.00");
			order.setBankNo("weixin");
			order.setCurrency("CNY");// 固定值CNY
			order.setGoodsCategory("commodityCategory");
			order.setGoodsDesc("commodityDescription");
			order.setGoodsName("commodityName");
			order.setOrderId(RandomString.UUID());

			PayInterface<MFMerchantConfig, MFOrderConfig> payment = new MFPayMent<MFMerchantConfig, MFOrderConfig>();

			payment.save(merchant, order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
