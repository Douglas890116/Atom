package com.maven.payment.nf;

import java.net.URLDecoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hy.pull.common.util.Encrypt;
import com.maven.util.HttpPostUtil;

import net.sf.json.JSONObject;
import net.sf.jsqlparser.expression.StringValue;

public class NfPaySignUtil {

	
	public static String getRequestUrl(NfMerchantConfig merchant, NfOrderConfig order) {
		
//		组合签名数据的格式：
//		version=v1&
//		partnerId=0755000001&
//		orderId=5435345&
//		goods=amF2YbPM0PLJ6LzG0+/R1A==&
//		amount=100.00&
//		expTime=&
//		notifyUrl=https://wap.partner.com/notify.jsp&
//		pageUrl=https://wap.partner.com/page.jsp&
//		reserve=sid&
//		extendInfo=13590492970|00001&
//		payMode=00&bankId=CCB&
//		creditType=2&
//		key=12348314-9388-11de-b73f-0f19974d6b20
		
//		version=[version]&partnerId=[partnerId]&orderId=[ordered]&goods=[goods]&amount=[amount]&expTime=[expTime]&notifyUrl=[notifyUrl]&pageUrl=[pageUrl]&reserve=[reserve]&extendInfo=[extendInfo]&payMode=[payMode]&bankId=[bankId]&creditType=[creditType]&key=[key]
		
		StringBuffer signdata = new StringBuffer();
		signdata.append("version=").append("v1").append("&");
		signdata.append("partnerId=").append(merchant.getMerNo()).append("&");
		signdata.append("orderId=").append(order.getOrderId()).append("&");
		signdata.append("goods=").append("ABC").append("&");
		signdata.append("amount=").append(order.getAmount()).append("&");
		signdata.append("expTime=").append("").append("&");
		signdata.append("notifyUrl=").append(merchant.getNotifyUrl()).append("&");
		signdata.append("pageUrl=").append(merchant.getPageUrl()).append("&");
		signdata.append("reserve=").append("").append("&");
		signdata.append("extendInfo=").append("").append("&");
		signdata.append("payMode=").append(order.getPayMode()).append("&");
		signdata.append("bankId=").append(order.getBankId()).append("&");
		signdata.append("creditType=").append(order.getCreditType()).append("&");
		signdata.append("key=").append(merchant.getMerKey()).append("");

		System.out.println("MD5原文="+signdata.toString());
		String sign = Encrypt.MD5(signdata.toString());
		System.out.println("MD5密文="+sign);
		
		String url = merchant.getPayUrl()+"?"+signdata.toString()+"&sign="+sign;
		System.out.println("===============支付请求结果="+url);
		
//		微信扫码：WECHAT
//		支付宝扫码：ALIPAY
		if(order.getBankId().equals("WECHAT") || order.getBankId().equals("ALIPAY")) {
//			String result = HttpPostUtil.doPostSubmit(url);
			String result = HttpPostUtil.doGetSubmit(url);
			System.out.println("微信和支付宝返回结果："+result);
			
			JSONObject jsonObject = JSONObject.fromObject(result);
			if(jsonObject.getString("code").equals("NoCardScanCodePay00")) {
    			String code_url = jsonObject.getJSONObject("msg").getString("code_url");//返回
    			
    			return code_url;
			} else {
				return url;
			}
    		
		} else {
			return url;
		}
	}
	
	
	
	/**
	 * 对返回结果：
	 * 
	 * 
	 * @param req
	 * @param MER_KEY
	 * @return
	 */
	public static boolean checkResponseSign(HttpServletRequest request,Map<String, Object> __mer_params) {
		
		try {
//			将RSA签名源串再加上商户和支付网关约定好的通讯密钥，做为md5的源串
			
//			partnerId=200909091234&orderId=5435345&amount=100.00&result=S&payTime=20090909081030&traceId=12345678&reserve=sid&creditType=2&key=TEST12345678TEST
			String md5 = request.getParameter("md5");
			
			String partnerId = __mer_params.get("MER_NO").toString();
			String merkey = __mer_params.get("MEK_KEY").toString();
			
			String orderId = request.getParameter("orderId");
			String amount = request.getParameter("amount");
			String resultRSA = request.getParameter("result");
			String payTime = request.getParameter("payTime");
			String traceId = request.getParameter("traceId");
			String reserve = request.getParameter("reserve");
			String creditType = request.getParameter("creditType");
			
			StringBuffer signdata = new StringBuffer();
			signdata.append("partnerId=").append(partnerId).append("&");
			signdata.append("orderId=").append(orderId).append("&");
			signdata.append("amount=").append(amount).append("&");
			signdata.append("result=").append(resultRSA).append("&");
			signdata.append("payTime=").append(payTime).append("&");
			signdata.append("traceId=").append(traceId).append("&");
			signdata.append("reserve=").append(reserve).append("&");
			signdata.append("creditType=").append(creditType).append("&");
			signdata.append("key=").append(merkey).append("");
			
			System.out.println("校验签名：MD5原文="+signdata.toString());
			String sign = Encrypt.MD5(signdata.toString());
			System.out.println("校验签名：MD5密文="+sign);
			
			return sign.equals(md5);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		return false;
	}
	public static void main(String[] args) {
		try {
			String url = "https://pay.newpaypay.com/servlet/InitBankLogoServlet";
			String partnerId = "0755143";
//			WEBPAY:表示获取支持WEB网站的银行列表
//			WAPPAY:表示获取支持手机网站的银行列表
			String channel = "WEBPAY";
			String rstType = "json";
			String key = "7a03ea6a66dd3dd49f122e1528593453";
			
//			partnerId=0755000001&channel=WEBPAY&key=12348314-9388-11de-b73f-0f19974d6b20&rstType=json
			String data = "partnerId=".concat(partnerId).concat("&channel=").concat(channel).concat("&key=").concat(key).concat("&rstType=").concat(rstType);
			System.out.println("data="+data);
			String sign = Encrypt.MD5(data);
			System.out.println("sign="+sign);
			
			System.out.println(url+"?"+data+"&sign="+sign);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 参数过滤
	 * @param text
	 * @return
	 */
	@SuppressWarnings("unused")
	private String formatString(String text){ 
		if(text == null) {
			return ""; 
		}
		return text;
	}
}

