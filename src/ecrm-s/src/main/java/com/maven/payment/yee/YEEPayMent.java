package com.maven.payment.yee;

import com.maven.payment.PayInterface;
import com.maven.util.AttrCheckout;

/**
 * 易宝快捷支付
 * @author 
 *
 */
public class YEEPayMent<M extends YEEMerchantConfig,O extends YEEOrderConfig> implements PayInterface<M,O>{

	@Override
	public String save(YEEMerchantConfig merchant, YEEOrderConfig order) throws Exception {
		AttrCheckout.checkout(merchant, false, YEEMerchantConfig.M_Save_Paramters.paramters());
		AttrCheckout.checkout(order, false, YEEOrderConfig.O_Save_Paramters.paramters());
		YEESave save = new YEESave(merchant.getP0_Cmd(), merchant.getP1_MerId(), merchant.getP4_Cur(), merchant.getP8_Url(), merchant.getPr_NeedResponse());
		String skipUrl = save.getUrl(merchant.getSaveUrl(), merchant.getKeyValue(), order.getP2_Order(), order.getP3_Amt(), order.getP5_Pid(), 
				order.getP6_Pcat(), order.getP7_Pdesc(), order.getP9_SAF(), order.getPa_MP(), order.getPd_FrpId());
		return skipUrl;
	}

	@Override
	@Deprecated
	public String pay(YEEMerchantConfig merchant, YEEOrderConfig order) throws Exception {
		return "";
	}
	
	public static void main(String[] args) throws Exception {
		PayInterface<YEEMerchantConfig,YEEOrderConfig> __yyePay = new YEEPayMent<YEEMerchantConfig, YEEOrderConfig>();
		YEEMerchantConfig merchant = new YEEMerchantConfig();
		merchant.setSaveUrl("https://www.yeepay.com/app-merchant-proxy/node");
		merchant.setP1_MerId("10001126856");
		merchant.setKeyValue("69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl");
		merchant.setP8_Url("http://localhost:8080/yeepaydemo/yeepayCommon/JAVA/callback.jsp");
		YEEOrderConfig  __yeeorder =  new YEEOrderConfig();
		__yeeorder.setP2_Order("1100");
		__yeeorder.setP3_Amt("0.01");
		__yeeorder.setPd_FrpId("ICBC-NET-B2C");
		System.out.println(__yyePay.save(merchant, __yeeorder));
	}
	

}
