package com.maven.payment.sdp2p;

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
public class SDWXPayMent<M extends SDP2PMerchantConfig,O extends SDP2POrderConfig> implements PayInterface<M,O>{

	@Override
	public String save(SDP2PMerchantConfig merchant, SDP2POrderConfig order) throws Exception {
		
		String requestUrl = "";
		System.out.println("支付请求链接="+requestUrl);
		return requestUrl;
	}

	@Override
	public String pay(SDP2PMerchantConfig merchant, SDP2POrderConfig order) throws Exception {
		int result = 0;
		//如果返回结果大于0则表示提交成功，返回值为这条记录的Id。如果返回值小于0代表提交失败，通过返回值可以判断失败的原因。
		return result > 0 ? PayInterface.PAY_SUCCESS : result + "";
	}

	
	public static void main(String[] args) throws Exception {
		PayInterface<SDP2PMerchantConfig,SDP2POrderConfig> __yyePay = new SDWXPayMent<SDP2PMerchantConfig, SDP2POrderConfig>();
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
		
	}
	

}
