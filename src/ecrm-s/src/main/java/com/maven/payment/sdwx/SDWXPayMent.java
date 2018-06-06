package com.maven.payment.sdwx;

import java.util.UUID;

import com.hy.pull.common.util.api.FileHelper;
import com.maven.payment.PayInterface;
import com.maven.util.Encrypt;
import com.maven.util.HttpPostUtil;

import net.sf.json.JSONObject;

/**
 * SD支付
 * @author 
 *
 */
public class SDWXPayMent<M extends SDWXMerchantConfig,O extends SDWXOrderConfig> implements PayInterface<M,O>{

	@Override
	public String save(SDWXMerchantConfig merchant, SDWXOrderConfig order) throws Exception {
		
		String requestUrl = SDWXPaySignUtil.getRequestUrl(merchant, order);
		System.out.println("支付请求链接="+requestUrl);
		return requestUrl;
	}

	@Override
	public String pay(SDWXMerchantConfig merchant, SDWXOrderConfig order) throws Exception {
		String result = SDWXPaySignUtil.payout(merchant, order);
		
		JSONObject jsonObject = JSONObject.fromObject(result);
		if(jsonObject.getString("code").equals("0")) {
			return PayInterface.PAY_SUCCESS;
		}
		return jsonObject.getString("info");
	}

	
	public static void main(String[] args) throws Exception {
		PayInterface<SDWXMerchantConfig,SDWXOrderConfig> __yyePay = new SDWXPayMent<SDWXMerchantConfig, SDWXOrderConfig>();
		/*
		
		SDWXMerchantConfig merchant = new SDWXMerchantConfig(
				"EG9783", 
				"http://api.officenewline.org:1010/ToService.aspx", 
				"http://api.officenewline.org:1010/ToService.aspx", 
				
				"http://api.officenewline.org:1010/ToService.aspx",
				"http://api.officenewline.org:1010/ToService.aspx", 
				"http://api.officenewline.org:1010/ToService.aspx", 
				"YTgWujPXTxA=", "uS2J7YxCc3o=", "13940e1f02294a6ba6de53eb8339d173");
		
		String order = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 24);
		SDWXOrderConfig  __yeeorder =  new SDWXOrderConfig(order, "test", "100", null, "6006");
		
		String url = __yyePay.save(merchant, __yeeorder);
		String[] params = url.split("\\?")[1].split("&");
		for (String string : params) {
			System.err.println(string);
		}
		*/
		
		SDWXMerchantConfig merchant = new SDWXMerchantConfig("egg777", "b06Alyqn/u0=", "vwpBOJeKTKg=", "https://payout.sdapayapi.com/8001/Customer.asmx");
		
		String intoAccount = "11";
		String intoName = "22";
		String intoBank1 = "ABC";
		String intoBank2 = "";
		double intoAmount = 2;
		String serialNumber  = UUID.randomUUID().toString();
		
		SDWXOrderConfig __yeeorder = new SDWXOrderConfig(intoAccount, intoName, intoBank1, intoBank2, intoAmount, serialNumber);
		String result = __yyePay.pay(merchant, __yeeorder);
		
		
	}
	

}
