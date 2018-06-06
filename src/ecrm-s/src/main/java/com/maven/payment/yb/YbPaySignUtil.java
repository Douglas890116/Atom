package com.maven.payment.yb;

import javax.servlet.http.HttpServletRequest;

import com.maven.util.Encrypt;

public class YbPaySignUtil {

	public static String getRequestUrl(YbMerchantConfig merchant, YbOrderConfig order) {
		StringBuffer requestUrl = new StringBuffer();
		
//		商户ID	partner
//		银行类型	banktype
//		金额	paymoney
//		商户订单号	ordernumber
//		下行异步通知地址	callbackurl
//		下行同步通知地址	hrefbackurl
//		备注信息	attach
//		MD5签名	sign

		String ordermoney = order.getPaymoney() + "";
		
		String sign = getRequestSign(merchant.getMerNo(), order.getBanktype(),ordermoney  , order.getOrdernumber(), merchant.getNotiUrl(), merchant.getMerKey());
		
		requestUrl.append(merchant.getPayUrl()+"?");
		
		requestUrl.append(YbPayAppConstants.partner).append("=").append(merchant.getMerNo()).append("&");
		requestUrl.append(YbPayAppConstants.p1_banktype).append("=").append(order.getBanktype()).append("&");
		requestUrl.append(YbPayAppConstants.p1_paymoney).append("=").append(ordermoney).append("&");
		requestUrl.append(YbPayAppConstants.p1_ordernumber).append("=").append(order.getOrdernumber()).append("&");
		requestUrl.append(YbPayAppConstants.p1_callbackurl).append("=").append(merchant.getNotiUrl()).append("&");
//		requestUrl.append(YbPayAppConstants.p1_hrefbackurl).append("=").append(merchant.getMerNo()).append("&");//可空
		requestUrl.append(YbPayAppConstants.p1_attach).append("=").append(order.getAttach()).append("&");
		requestUrl.append(YbPayAppConstants.sign).append("=").append(sign);
		
		return requestUrl.toString();
	}
	
	/**
	 * 支付请求做MD5参数签名
	 * @param c_code
	 * @param pay_type
	 * @param pay_amt
	 * @param agent_bill_id
	 * @param MER_KEY
	 * @return
	 */
	private static String getRequestSign(String partner , String banktype , String paymoney , String ordernumber , String callbackurl, String MER_KEY) {
		  
		  
//			partner={}&banktype={}&paymoney={}&ordernumber={}&callbackurl={}key
//			其中，key为商户签名。
		
		StringBuffer sValue = new StringBuffer();
		
		sValue.append(YbPayAppConstants.partner).append("=").append(partner);
		sValue.append("&").append(YbPayAppConstants.p1_banktype).append("=").append(banktype);
		sValue.append("&").append(YbPayAppConstants.p1_paymoney).append("=").append(paymoney);
		sValue.append("&").append(YbPayAppConstants.p1_ordernumber).append("=").append(ordernumber);
		sValue.append("&").append(YbPayAppConstants.p1_callbackurl).append("=").append(callbackurl);
		sValue.append(MER_KEY);
		
		System.out.println("支付请求做MD5参数="+sValue);
		String sNewString = null;
		
			
		sNewString = Encrypt.MD5(sValue.toString());
		System.out.println("支付请求做MD5参数签名="+sNewString);
		return (sNewString);
	}
	
	/**
	 * 结果通知回参做签名
	 * @param mer_no
	 * @param order_no
	 * @param order_amount
	 * @param return_params
	 * @param trade_no
	 * @param trade_time
	 * @param trade_status
	 * @param MER_KEY
	 * @return
	 */
	private static String getResponeSign(String partner, String ordernumber, String orderstatus , String paymoney, String MER_KEY) {
//		
//		partner={}&ordernumber={}&orderstatus={}&paymoney={}key
//		其中,key为商户签名
		
		StringBuffer sValue = new StringBuffer();
		
		sValue.append(YbPayAppConstants.partner).append("=").append(partner);
		sValue.append("&").append(YbPayAppConstants.p2_ordernumber).append("=").append(ordernumber);
		sValue.append("&").append(YbPayAppConstants.p2_orderstatus).append("=").append(orderstatus);
		sValue.append("&").append(YbPayAppConstants.p2_paymoney).append("=").append(paymoney);
		sValue.append(MER_KEY);
		
		String sNewString = null;
		sNewString = Encrypt.MD5(sValue.toString());
		
		return (sNewString);
	}
	
	/**
	 * 结果通知回参做签名校验
	 * @param req
	 * @param MER_KEY
	 * @return
	 */
	public static boolean checkResponseSign(HttpServletRequest req, String MER_KEY) {
		
		
		String partner = req.getParameter(YbPayAppConstants.partner);
        String p2_ordernumber = req.getParameter(YbPayAppConstants.p2_ordernumber);
        String p2_orderstatus = req.getParameter(YbPayAppConstants.p2_orderstatus);
        String p2_paymoney = req.getParameter(YbPayAppConstants.p2_paymoney);
        String return_sign = req.getParameter(YbPayAppConstants.sign);
        
        String sign = getResponeSign(partner, p2_ordernumber, p2_orderstatus, p2_paymoney, MER_KEY);
        
		return sign.equals(return_sign);
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
