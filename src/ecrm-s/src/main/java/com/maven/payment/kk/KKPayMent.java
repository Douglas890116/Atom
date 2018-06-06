package com.maven.payment.kk;

import com.maven.payment.PayInterface;

public class KKPayMent<M extends KKMerchantConfig, O extends KKOrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(KKMerchantConfig merchant, KKOrderConfig order) throws Exception {
		KKSave save = new KKSave();
		return save.getUrl(merchant, order);
	}

	@Override
	public String pay(KKMerchantConfig merchant, KKOrderConfig order) throws Exception {
		return null;
	}

	public static void main(String[] args) {
		String openId = "oQaSPxOtL4m8ul_klJgmWt6tycjw";
		PayInterface<KKMerchantConfig, KKOrderConfig> pay = new KKPayMent<KKMerchantConfig, KKOrderConfig>();
		// 商户信息
		KKMerchantConfig merchant = new KKMerchantConfig();
		merchant.setPayKey("90f280d69d244449b76d9c896278cbed");
		merchant.setProductType("10000102");
		merchant.setMd5Key("9297896e682142c785902fdcac68df9b");
		merchant.setPayUrl("http://gateway.kekepay.com/cnpPay/initPay");
		merchant.setReturnUrl("http://api.hyzonghe.net/TPayment/success");
		merchant.setNotifyUrl("http://api.hyzonghe.net/TPayment/KKPayCallback");
		
		merchant.setWXappid("wxcb57276540c0ea96");
		merchant.setWXappSecret("d7730bbb4e38de328a8e6603c26f858c");
		// 订单信息
		KKOrderConfig order = new KKOrderConfig();
		order.setOutTradeNo(KKAppConstants.getOrderNo());
		order.setOrderPrice("2.00");
		order.setOrderTime(KKSave.getOrderTime());
		order.setProductName("Name");
		order.setOrderIp("180.232.108.150");
		
		order.setOpenid(openId);
		
		try {
			String apiUrl = pay.save(merchant, order);
			System.out.println("扫码地址：" + apiUrl);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		/* https://open.weixin.qq.com/connect/oauth2/authorize
		?appid=APPID
		&redirect_uri=REDIRECT_URI
		&response_type=code
		&scope=SCOPE
		&state=STATE#wechat_redirect
		*/
//		try {
//			
//			String appid = "wxcb57276540c0ea96";
//			String appSecret = "d7730bbb4e38de328a8e6603c26f858c";
//			String state = Encrypt.MD5(appSecret);
//			String callbackurl = "http://api-zfp.dangdangai.com/TPayment/WXCallback";
//			StringBuffer url = new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize");
//			url.append("?appid=").append(appid)
//			   .append("&redirect_uri=").append(URLEncoder.encode(callbackurl, "UTF-8"))
//			   .append("&response_type=").append("code")
//			   .append("&scope=").append("snsapi_base")
//			   .append("&state=").append(state)
//			   .append("#wechat_redirect");
//			System.out.println(url);
//			
//	/* https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code */
//			String code = "031i8pcb2TeJMQ00xMbb2TDvcb2i8pcg";
//			StringBuffer url2 = new StringBuffer("https://api.weixin.qq.com/sns/oauth2/access_token");
//			url2.append("?appid=").append(appid)
//			    .append("&secret=").append(appSecret)
//			    .append("&code=").append(code)
//			    .append("&grant_type=authorization_code");
//			System.out.println(url2);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
	}
}