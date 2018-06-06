package com.maven.payment.jpay;

import java.util.UUID;

import com.maven.payment.PayInterface;
import com.maven.util.HttpPostUtil;

import net.sf.json.JSONObject;

/**
 * 
 * @author 
 *
 */
public class JPayMent<M extends JMerchantConfig,O extends JOrderConfig> implements PayInterface<M,O>{

	@Override
	public String save(JMerchantConfig merchant, JOrderConfig order) throws Exception {
//		AttrCheckout.checkout(merchant, false, YOMMerchantConfig.M_Save_Paramters.paramters());
//		AttrCheckout.checkout(order, false, YOMOrderConfig.O_Save_Paramters.paramters());
		
//		返回格式，需要抽取出二维码地址
//		{"res":0,"address":"http://pay.hebipo.net:8888/PayPlat/pay/dimImage.do?token_id=71a057ed99d9e4dc4c127fd2558ced786425b27e5d0f36ce560518fa83ceb9d7053ee5d098713454","paysvr":"pay.wappay"}
		String requestUrl = JPaySignUtil.getUrl(merchant, order);
		String result = HttpPostUtil.doGetSubmit(requestUrl);
//		System.out.println("result====="+result);
		JSONObject object = JSONObject.fromObject(result);
		if(object.optString("res").equals("0")) {
			return object.optString("address");
		} else {
			System.err.println("res="+object.optString("res"));
			return requestUrl;
		}
		
	}

	@Override
	@Deprecated
	public String pay(JMerchantConfig merchant, JOrderConfig order) throws Exception {
		return "";
	}

	
	public static void main(String[] args) throws Exception {
		PayInterface<JMerchantConfig,JOrderConfig> __yyePay = new JPayMent<JMerchantConfig, JOrderConfig>();
		JMerchantConfig merchant = new JMerchantConfig();
		merchant.setMerKey("553DFE89AB14283A");
		merchant.setMerNo("813743");
		merchant.setNotiUrl("http://api.hyzonghe.net/TPayment/YomPayCallback");
		merchant.setReturnUrl("http://api.hyzonghe.net/TPayment/YomPayCallback");
		merchant.setPayUrl("http://pay.thesecond.top:8888/PayPlat/pay/dimReq.do");
		
		
		JOrderConfig  __yeeorder =  new JOrderConfig();
		__yeeorder.setAgent_bill_id(UUID.randomUUID().toString());
//		__yeeorder.setGoods_name(goods_name);//可不填。有默认值
//		__yeeorder.setRemark(remark);//可不填。有默认值
		__yeeorder.setPay_amt(12.01);
		__yeeorder.setPay_type("30");
		
		
		String url = __yyePay.save(merchant, __yeeorder);
//		String[] params = url.split("\\?")[1].split("&");
//		for (String string : params) {
//			System.err.println(string);
//		}
		System.err.println(url);
		
//		JSONObject object = JSONObject.fromObject(result);
//		System.out.println(object.optString("address")); ;
		
	}
	

}
