package com.maven.payment.ys.alipay;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.maven.payment.PayInterface;
import com.maven.payment.ys.SignUtils;

public class YSALIPayPayMent<M extends YSALIPayMerchantConfig, O extends YSALIPayOrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(YSALIPayMerchantConfig merchant, YSALIPayOrderConfig order) throws Exception {
		YSALIPaySave save = new YSALIPaySave();
		return save.getUrl(merchant, order);
	}

	@Override
	public String pay(YSALIPayMerchantConfig merchant, YSALIPayOrderConfig order) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		PayInterface<YSALIPayMerchantConfig, YSALIPayOrderConfig> payment = new YSALIPayPayMent<YSALIPayMerchantConfig, YSALIPayOrderConfig>();
		
		YSALIPayMerchantConfig merchant = new YSALIPayMerchantConfig();
		merchant.setMerId("sf_test");
		merchant.setMerName("银盛支付深圳分公司");
		merchant.setPrivateKeyUrl("F:\\项目文档\\功能需求\\第三方支付\\银盛支付\\深分测试账号\\sf_test.pfx");
		merchant.setPublicKeyUrl("F:\\项目文档\\功能需求\\第三方支付\\银盛支付\\深分测试账号\\businessgate.cer");
		merchant.setPassword("123456");
		merchant.setBusinessCode("01000010");
		merchant.setTimeout("10m");
		merchant.setPayUrl("https://mertest.ysepay.com/openapi_gateway/gateway.do");
		merchant.setMethod("ysepay.online.wap.directpay.createbyuser");
		merchant.setReturnUrl("http://pay.99scm.com/TPayment/success");
		merchant.setNotifyUrl("http://pay.99scm.com/TPayment/YSALIPayCallback");
		
		YSALIPayOrderConfig order = new YSALIPayOrderConfig();
		order.setOrderNo(SignUtils.getOrderNo());
		order.setOrderAmount("1.0");
		order.setOrderTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		order.setOrderSubject("Recharge");
		
		try {
			String url = payment.save(merchant, order);
			
			System.out.println();
//			String baseUrl = url.split("[?]")[0];
//			System.out.println(baseUrl);
			String query = url.split("[?]")[1];
			String[] params = query.split("&");
			for (String param : params) {
				if (param.split("=").length > 1) {
					System.out.println("\t\t<input type=\"text\" name=\"" + param.split("=")[0] + "\" value=\"" + param.split("=")[1] + "\" readonly=\"readonly\"/><br/>");
				} else {
					System.err.println("\t\t<input type=\"text\" name=\"" + param.split("=")[0] + "\" value=\"\" readonly=\"readonly\"/><br/>");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}