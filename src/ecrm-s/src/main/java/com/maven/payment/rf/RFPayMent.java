package com.maven.payment.rf;

import com.maven.payment.PayInterface;
import com.maven.payment.rf.RFAppConstants.RF_AppType;
/**
 * 锐付接口
 * @author klay
 *
 * @param <M>
 * @param <O>
 */
public class RFPayMent<M extends RFMerchantConfig, O extends RFOrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(RFMerchantConfig merchant, RFOrderConfig order) throws Exception {
		RFSave save = new RFSave();
		return save.getUrl(merchant, order);
	}

	@Override
	public String pay(RFMerchantConfig merchant, RFOrderConfig order) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		PayInterface<RFMerchantConfig, RFOrderConfig> payment = new RFPayMent<RFMerchantConfig, RFOrderConfig>();
		// 商户信息
		RFMerchantConfig merchant = new RFMerchantConfig();
		merchant.setPartyId("gateway_WRFT001461");
		merchant.setAccountId("gateway_WRFT001461001");
		merchant.setMd5Key("069aa6ba30297be4389c34c6361331491a4f161ee4442529bfca7182cc51875dd5df87a125b101ae1c420864db577cc36b9f1d848b1e3caebf2bfcddc718957b8b82a211e8121fe3b98a081790f7cb47");
		merchant.setEncodeType("Md5");
		merchant.setGoods("EGG");
		merchant.setCheckUrl("http://api.hyzonghe.net/TPayment/RFPayCallback");
		merchant.setReturnUrl("http://api.hyzonghe.net/TPayment/success");
		merchant.setPayUrl("https://payment.rfupayadv.com/prod/commgr/control/inPayService");
		
		RFOrderConfig order = new RFOrderConfig();
		order.setAppType(RF_AppType.微信支付.value);
		order.setBank("wechat");
		order.setCardType("01");
		order.setOrderAmount("3");
		order.setOrderNo("EGG" + System.currentTimeMillis());
		
		try {
			String url = payment.save(merchant, order);
			
			String baseUrl = url.split("[?]")[0];
			System.out.println("请求支付地址：" + baseUrl);
			
			String queryString = url.split("[?]")[1];
			String[] params = queryString.split("&");
			for (String str : params) {
				if(str.split("=").length == 2) {
					System.out.println("<input type=\"text\" name=\"" + str.split("=")[0] + "\" value=\"" + str.split("=")[1] + "\"/>");
				} else {
					System.out.println("<input type=\"text\" name=\"" + str.split("=")[0] + "\" value=\"\"/>");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
