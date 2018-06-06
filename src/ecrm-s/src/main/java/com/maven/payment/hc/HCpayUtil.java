package com.maven.payment.hc;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.maven.payment.lfwx.LfwxPayAppConstants;
import com.maven.util.Encrypt;
import com.maven.util.HttpPostUtil;

import net.sf.json.JSONObject;

/**
 * 汇潮支付
 * @author Administrator
 *
 */
public class HCpayUtil {
	
	 
	public HCpayUtil() {
		
	}
	
	/**
	 * 获取当前时间，精确到yyyyMMddHHmmss
	 * 
	 * xufc20130419 修改
	 * 
	 * @return
	 */
	private static String getCurrentTime() {
		String strTime = "-1";
		try {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			strTime = dateFormat.format(date);

		} catch (Exception e) {
			strTime = "-1";
			e.fillInStackTrace();
		}
		return strTime;
	}
	
	
	/**
	 * 获取支付请求URL
	 */
	public String getUrl(HCMerchantConfig merchant, HCOrderConfig order){
		
		order.setOrdertime(getCurrentTime());
		
		SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();  
        parameters.put("MerNo", merchant.getMerNo());  
        parameters.put("BillNo", order.getOrderNo());  
        parameters.put("Amount", order.getOrderAmount());  
        parameters.put("ReturnURL", merchant.getReturnUrl());  
        parameters.put("AdviceURL", merchant.getNotiUrl());
        parameters.put("OrderTime", order.getOrdertime());  
        parameters.put("defaultBankNumber", order.getBankCode());
        parameters.put("payType", order.getPaytype());
        
        StringBuffer data = new StringBuffer();
        data.append("MerNo=").append(merchant.getMerNo()).append("&");
        data.append("BillNo=").append(order.getOrderNo()).append("&");
        data.append("Amount=").append(order.getOrderAmount()).append("&");
        data.append("OrderTime=").append(order.getOrdertime()).append("&");
        data.append("ReturnURL=").append(merchant.getReturnUrl()).append("&");
        data.append("AdviceURL=").append(merchant.getNotiUrl()).append("&").append(merchant.getMerKey());
        
        System.out.println("MD5参数原文="+data);
        
        String sign = Encrypt.MD5(data.toString()).toUpperCase();//转大写
        parameters.put("SignInfo", sign);
        
        System.out.println("MD5参数密文="+sign);
        
        StringBuffer sb = new StringBuffer();  
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）  
        Iterator it = es.iterator();  
        while(it.hasNext()) {  
        	 Map.Entry entry = (Map.Entry)it.next();  
             String k = (String)entry.getKey();  
             Object v = entry.getValue();  
             if(null != v && !"".equals(v) ) {     
                 sb.append(k + "=" + v + "&");
             }  
        }  
		
		
		
		String url = merchant.getPayUrl() + "?"+sb.toString();
		
		System.out.println("完整的请求参数："+url);
		
		return url;
	}
	
	
	/**
	 * 结果通知回参做签名校验
	 * @param req
	 * @param MER_KEY
	 * @return
	 */
	public static boolean checkResponseSign(HttpServletRequest req, String MER_KEY) {
		
		String response_sign = req.getParameter("SignInfo");
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("MerNo=").append(req.getParameter("MerNo")).append("&");
		buffer.append("BillNo=").append(req.getParameter("BillNo")).append("&");
		buffer.append("OrderNo=").append(req.getParameter("OrderNo")).append("&");
		buffer.append("Amount=").append(req.getParameter("Amount")).append("&");
		buffer.append("Succeed=").append(req.getParameter("Succeed")).append("&").append(MER_KEY);
		
        String params = buffer.toString();
        System.out.println("支付响应做MD5参数="+params);
        
        String sign = Encrypt.MD5(params).toUpperCase();
        System.out.println("支付响应做MD5参数签名="+sign);
        
		return sign.equals(response_sign);
	}
	
	
	
	public static void main(String[] args) {
//		notify_type=async_notify&
//				order_amount=1500.00&
//				order_sn=901493354848263263042817124728&
//				order_time=2017-04-28 12:47:26&
//				out_trade_no=XFM314620170428124726297&
//				partner_id=123456&
//				resp_code=RESPONSE_SUCCESS&
//				resp_desc=订单支付成功&
//				trade_status=TRADE_SUCCESS&
//				trade_time=2017-04-28 12:47:28&
//				key=秘钥KEY
				
		System.out.println("EC28E89EED529DA52CB2FCB7ADDB0C19");
		System.out.println(Encrypt.MD5("notify_type=async_notify&order_amount=1.00&order_sn=1705130856060351839&order_time=2017-05-13 08:56:05&out_trade_no=55A1D627FEF84312BE64373D526120A2&partner_id=80060156&resp_code=RESPONSE_SUCCESS&resp_desc=订单支付成功&trade_status=TRADE_SUCCESS&trade_time=2017-05-13 08:56:06&key=F50497D07532C29C77759605AD5F1E9D").toUpperCase());
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
