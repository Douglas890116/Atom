package com.maven.payment.lx;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 乐信付快捷支付接口
 */
import com.maven.payment.PayInterface;
import com.maven.util.RandomString;

public class LePayMent<M extends LeMerchantConfig, O extends LeOrderConfig> implements PayInterface<M, O> {
	/**
	 * 存款
	 */
	@Override
	public String save(LeMerchantConfig merchant, LeOrderConfig order) throws Exception {
		LeSave save = new LeSave();
		return save.getUrl(merchant, order);
	}
	/**
	 * 出款
	 */
	@Override
	public String pay(M merchant, O order) throws Exception {
		return null;
	}

	public static void main(String[] args) {
		try {
			PayInterface<LeMerchantConfig, LeOrderConfig> payment = new LePayMent<LeMerchantConfig, LeOrderConfig>();
			LeMerchantConfig merchant = new LeMerchantConfig();
			LeOrderConfig order = new LeOrderConfig();
			String keyStorePath = "F:/ZONGHE/ecrm-api/WEB-INF/certificate/lx/dwkey.pfx";
			String certificatePath = "F:/ZONGHE/ecrm-api/WEB-INF/certificate/lx/pdtserver.cer";
			String keyStorePassword = "ZHdwYXNzd29yZA==";// dwpassword
															// Base64编码后的结果
			/* ==========商户信息========== */
			merchant.setMchId("61003");// 61003
			merchant.setAppId("52504");// 52504
			merchant.setKeyStorePath(keyStorePath);
			merchant.setCertificatePath(certificatePath);
			merchant.setKeyStorePassword(keyStorePassword);
			merchant.setPayUrl("https://openapi.unionpay95516.cc/pre.lepay.api");
			merchant.setShortUrl("/order/add");
			merchant.setReturnUrl("http://api.hyzonghe.net/HCPayCallback");
			/* ==========订单信息========== */
			BigDecimal amount = new BigDecimal(100.00);// 充值金额为100元
			// 乐信付接口要求金额参数单位为“分”，不能带小数点
			order.setAmount(amount.multiply(new BigDecimal(100).setScale(0, BigDecimal.ROUND_DOWN)).toString());
			order.setBuyerId("EN0000");
			order.setDeviceId("L897554536354");
			order.setDeviceIp("127.0.0.1");
			order.setOutTradeNo(RandomString.UUID());
			order.setPayTypeCode("web.pay");
			order.setSummary("prodect");
			order.setSummaryDetail("detail");
			order.setTradeTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

			String url = payment.save(merchant, order);
			System.out.println();
			System.out.println(url);
			
			PayInterface<LeMerchantConfig, LeOrderConfig> payment2 = new LePayMent<LeMerchantConfig, LeOrderConfig>();
			LeMerchantConfig merchant2 = new LeMerchantConfig();
			LeOrderConfig order2 = new LeOrderConfig();
			String keyStorePath2 = "F:/ZONGHE/ecrm-api/WEB-INF/certificate/lx/jmtkey.pfx";
			String certificatePath2 = "F:/ZONGHE/ecrm-api/WEB-INF/certificate/lx/pdtserver.cer";
			String keyStorePassword2 = "am10cGFzc3dvcmQ=";// dwpassword
															// Base64编码后的结果
			/* ==========商户信息========== */
			merchant2.setMchId("62088");// 61003
			merchant2.setAppId("55523");// 52504
			merchant2.setKeyStorePath(keyStorePath2);
			merchant2.setCertificatePath(certificatePath2);
			merchant2.setKeyStorePassword(keyStorePassword2);
			merchant2.setPayUrl("https://openapi.unionpay95516.cc/pre.lepay.api");
			merchant2.setShortUrl("/order/add");
			merchant2.setReturnUrl("http://api.hyzonghe.net/HCPayCallback");
			/* ==========订单信息========== */
			BigDecimal amount2 = new BigDecimal(100.00);// 充值金额为100元
			// 乐信付接口要求金额参数单位为“分”，不能带小数点
			order2.setAmount(amount2.multiply(new BigDecimal(100).setScale(0, BigDecimal.ROUND_DOWN)).toString());
			order2.setBuyerId("EN0000");
			order2.setDeviceId("L897554536354");
			order2.setDeviceIp("127.0.0.1");
			order2.setOutTradeNo(RandomString.UUID());
			order2.setPayTypeCode("web.pay");
			order2.setSummary("prodect");
			order2.setSummaryDetail("detail");
			order2.setTradeTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

			String url2 = payment2.save(merchant2, order2);
			System.out.println();
			System.out.println(url);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
