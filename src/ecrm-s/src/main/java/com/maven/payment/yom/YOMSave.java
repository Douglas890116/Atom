package com.maven.payment.yom;

import javax.servlet.http.HttpServletRequest;

import com.maven.util.Encrypt;

/**
 * 优付支付
 * @author Administrator
 *
 */
public class YOMSave {
	
	 
	public YOMSave() {
		
	}
	
	/**
	 * 获取支付请求URL
	 */
	public String getUrl(String t0_charset , String t1_returnurl , String t2_notifyurl , String t3_bankcode , String t4_orderno , 
			String t5_orderamount ,String t6_productname, String t7_productnum, String t8_referer , String t9_customerip, String t10_phone, String t11_address, String t12_params, 
			String MER_KEY, String postUrl,String merNo){
		
		String sign = getRequestSign(t0_charset , t1_returnurl , t2_notifyurl , t3_bankcode , t4_orderno , 
				t5_orderamount ,t6_productname, t7_productnum, t8_referer , t9_customerip, t10_phone, t11_address, t12_params, MER_KEY);
		StringBuffer url = new StringBuffer();
		url.append(postUrl)
		.append("?").append(YomAppConstants.p1_INPUT_CHARSET).append("=").append(t0_charset)
		.append("&").append(YomAppConstants.p1_RETURN_URL).append("=").append(t1_returnurl)
		.append("&").append(YomAppConstants.p1_NOTIFY_URL).append("=").append(t2_notifyurl)
		.append("&").append(YomAppConstants.p1_BANK_CODE).append("=").append(t3_bankcode)
		.append("&").append(YomAppConstants.p1_MER_NO).append("=").append(merNo)
		.append("&").append(YomAppConstants.p1_ORDER_NO).append("=").append(t4_orderno)
		.append("&").append(YomAppConstants.p1_ORDER_AMOUNT).append("=").append(t5_orderamount)
		.append("&").append(YomAppConstants.p1_PRODUCT_NAME).append("=").append(t6_productname)
		.append("&").append(YomAppConstants.p1_PRODUCT_NUM).append("=").append(t7_productnum)
		.append("&").append(YomAppConstants.p1_REFERER).append("=").append(t8_referer)
		.append("&").append(YomAppConstants.p1_CUSTOMER_IP).append("=").append(t9_customerip)
		.append("&").append(YomAppConstants.p1_CUSTOMER_PHONE).append("=").append(t10_phone)
		.append("&").append(YomAppConstants.p1_RECEIVE_ADDRESS).append("=").append(t11_address)
		.append("&").append(YomAppConstants.p1_RETURN_PARAMS).append("=").append(t12_params)
		.append("&").append(YomAppConstants.p1_SIGN).append("=").append(sign);
		System.out.println(url);
		return url.toString();
	}
	
	/**
	 * 支付请求做MD5参数签名
	 * @param t0_charset
	 * @param t1_returnurl
	 * @param t2_notifyurl
	 * @param t3_bankcode
	 * @param t4_orderno
	 * @param t5_orderamount
	 * @param t6_productname
	 * @param t7_productnum
	 * @param t8_referer
	 * @param t9_customerip
	 * @param t10_phone
	 * @param t11_address
	 * @param t12_params
	 * @param MER_KEY
	 * @return
	 */
	private static String getRequestSign(String t0_charset , String t1_returnurl , String t2_notifyurl , String t3_bankcode , String t4_orderno , 
			String t5_orderamount ,String t6_productname, String t7_productnum, String t8_referer , String t9_customerip, String t10_phone, String t11_address, String t12_params, String MER_KEY) {
		  StringBuffer sValue = new StringBuffer();
//		  
//		  $t0_charset . $t1_returnurl . $t2_notifyurl . $t3_bankcode . $t4_orderno . $t5_orderamount .
//		  $t6_productname. $t7_productnum. $t8_referer . $t9_customerip. $t10_phone. $t11_address. $t12_params.
//		  MER_KEY;
//		  
		sValue.append(t0_charset);
		sValue.append(t1_returnurl);
		sValue.append(t2_notifyurl);
		sValue.append(t3_bankcode);
		sValue.append(t4_orderno);
		sValue.append(t5_orderamount);
		sValue.append(t6_productname);
		sValue.append(t7_productnum);
		sValue.append(t8_referer);
		sValue.append(t9_customerip);
		sValue.append(t10_phone);
		sValue.append(t11_address);
		sValue.append(t12_params);
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
	public static String getResponeSign(String mer_no, String order_no, String order_amount , String return_params, String trade_no, String trade_time, String trade_status,
			String MER_KEY) {
		StringBuffer sValue = new StringBuffer();
		sValue.append(mer_no);
		sValue.append(order_no);
		sValue.append(order_amount);
		sValue.append(return_params);
		sValue.append(trade_no);
		sValue.append(trade_time);
		sValue.append(trade_status);
		sValue.append(MER_KEY);
		String sNewString = null;
		sNewString = Encrypt.MD5(sValue.toString());
		
//		System.out.println("============5=====================================getResponeSign=======================sValue=================="+sValue);
//		System.err.println("============6=====================================getResponeSign=======================sNewString=================="+sNewString);
		
		return (sNewString);
	}
	
	/**
	 * 结果通知回参做签名校验
	 * @param req
	 * @param MER_KEY
	 * @return
	 */
	public static boolean checkResponseSign(HttpServletRequest req, String MER_KEY) {
		
		String mer_no = req.getParameter(YomAppConstants.p2_mer_no);
        String order_no = req.getParameter(YomAppConstants.p2_order_no);
        String order_amount = req.getParameter(YomAppConstants.p2_order_amount);
        String return_params = req.getParameter(YomAppConstants.p2_return_params);
        String trade_no = req.getParameter(YomAppConstants.p2_trade_no);
        String trade_time = req.getParameter(YomAppConstants.p2_trade_time);
        String trade_status = req.getParameter(YomAppConstants.p2_trade_status);
        String return_sign = req.getParameter(YomAppConstants.p2_sign);
        
        String sign = getResponeSign(mer_no, order_no, order_amount, return_params, trade_no, trade_time, trade_status, MER_KEY);
        
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
