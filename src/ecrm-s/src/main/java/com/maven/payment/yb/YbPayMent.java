package com.maven.payment.yb;

import java.util.UUID;

import com.maven.payment.PayInterface;
import com.maven.util.Encrypt;
import com.maven.util.HttpPostUtil;

import net.sf.json.JSONObject;

/**
 * 
 * @author 
 *
 */
public class YbPayMent<M extends YbMerchantConfig,O extends YbOrderConfig> implements PayInterface<M,O>{

	@Override
	public String save(YbMerchantConfig merchant, YbOrderConfig order) throws Exception {
//		AttrCheckout.checkout(merchant, false, YOMMerchantConfig.M_Save_Paramters.paramters());
//		AttrCheckout.checkout(order, false, YOMOrderConfig.O_Save_Paramters.paramters());
		
		String requestUrl = YbPaySignUtil.getRequestUrl(merchant, order);
		System.out.println("支付请求链接="+requestUrl);
		return requestUrl;
	}

	@Override
	@Deprecated
	public String pay(YbMerchantConfig merchant, YbOrderConfig order) throws Exception {
		return "";
	}

	
	public static void main(String[] args) throws Exception {
		PayInterface<YbMerchantConfig,YbOrderConfig> __yyePay = new YbPayMent<YbMerchantConfig, YbOrderConfig>();
		YbMerchantConfig merchant = new YbMerchantConfig();
		merchant.setMerKey("a50420ca45bc6661f7b3fd1cf907ad74");
		merchant.setMerNo("19190");
		merchant.setNotiUrl("http://api.hyzonghe.net/TPayment/YbPayCallback");
		merchant.setPayUrl("http://wytj.9vpay.com/PayBank.aspx");
		
		
		YbOrderConfig  __yeeorder =  new YbOrderConfig();
		__yeeorder.setOrdernumber(UUID.randomUUID().toString());
		__yeeorder.setPaymoney(100.00);
		__yeeorder.setAttach("abc");
		__yeeorder.setBanktype("ABC");
		
//		String url = __yyePay.save(merchant, __yeeorder);
		String url = "http://wytj.9vpay.com/PayBank.aspx?partner=19190&banktype=CTTIC&paymoney=100.0&ordernumber=B3DD72E6AE734020A43FCD5D56E566A7&callbackurl=http://api.gametaole.net//TPayment/YbPayCallback&attach=E000IFIK&sign=d4244fe6e25502c07f7ddb23440e9a06";
		
		String[] params = url.split("\\?")[1].split("&");
		for (String string : params) {
			System.err.println(string);
		}
		
//		JSONObject object = JSONObject.fromObject(result);
//		System.out.println(object.optString("address")); ;
		
	}
	

}
