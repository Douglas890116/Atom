package com.maven.payment.ch;

import java.text.DecimalFormat;

import com.maven.entity.ThirdpartyPaymentType.Enum_ThirdpartyPaymentType;
import com.maven.payment.PayInterface;
import com.maven.util.RandomString;

import net.sf.json.JSONObject;

public class CHPayMent<M extends CHMerchantConfig, O extends CHOrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(CHMerchantConfig merchant, CHOrderConfig order) throws Exception {
		StringBuilder params = new StringBuilder();
		params.append("p0_Cmd=" + CHOrderConfig.p0_Cmd);
		params.append("&p1_MerId=" + merchant.getMerCode());
		params.append("&p2_Order=" + order.getP2_Order());
		params.append("&p3_Cur=" + CHOrderConfig.p3_Cur);
		params.append("&p4_Amt=" + order.getP4_Amt());
		// params.append("&p8_Url=" + order.getP8_Url());

		String type = order.getPa_FrpId();
		if (type.equals(Enum_ThirdpartyPaymentType.畅汇网银.value)) {
			params.append("&pa_FrpId=OnlinePay");
			params.append("&pg_BankCode=" + order.getPg_BankCode());
		} else {
			params.append("&pa_FrpId=" + order.getPg_BankCode());
		}
		String[] stringArray = params.toString().split("&");
		StringBuilder hmacString = new StringBuilder();
		for (String string : stringArray) {
			System.out.println(string);
			String[] tempArray = string.split("=");
			hmacString.append(tempArray[1]);
		}
		System.out.println("**************");
		System.out.println(hmacString.toString());
		System.out.println("**************");
		params.append("&hmac=" + CHPayUtil.hmacSign(hmacString.toString(), merchant.getSha1Key()));

		// 网银的直接拿到这串url请求,其他需要先请求,拿到报文的r3_PayInfo
		if (type.equals(Enum_ThirdpartyPaymentType.畅汇网银.value)) {
			return merchant.getPayUrl() + "?" + params.toString();
		} else {
			JSONObject json = JSONObject.fromObject(CHPayUtil.sendPost(merchant.getPayUrl(), params.toString()));
			return json.getString("r3_PayInfo");
//			return json.toString();
		}
	}

	@Override
	public String pay(CHMerchantConfig merchant, CHOrderConfig order) throws Exception {
		return null;
	}

	public static void main(String[] args) throws Exception {
		CHMerchantConfig merchant = new CHMerchantConfig();
		merchant.setMerCode("CHANG1509970778911");
		merchant.setSha1Key("b57b52ad4b644a7e981a0f4261b0f9d7");
		merchant.setPayUrl("https://changcon.92up.cn/controller.action");

		CHOrderConfig order = new CHOrderConfig();
		order.setP2_Order(RandomString.UUID());
		order.setP4_Amt(new DecimalFormat("0.00").format(10.00));
		order.setP8_Url("");
		order.setPa_FrpId("P100");
		// 网银支付
		order.setPg_BankCode("WEIXIN");
		CHPayMent<CHMerchantConfig, CHOrderConfig> payMent = new CHPayMent<CHMerchantConfig, CHOrderConfig>();
		System.out.println(payMent.save(merchant, order));
	}
}