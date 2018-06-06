package com.maven.payment.yunsheng;

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
 * 云盛支付
 * @author Administrator
 *
 */
public class YSpayUtil {
	
	 
	public YSpayUtil() {
		
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
	 * 
	 * 
	 * 获取付款请求URL
	 */
	public String getUrl(YSMerchantConfig merchant, YSOrderConfig order){
		
		order.setOrdertime(getCurrentTime());
		
		try {
			/***
			交易返回接口MD5摘要认证的明文信息如下： 
			【订单编号】+【订单金额】+【订单日期】+【密钥】 
			加密参数的次序为：BillNo + Amount + OrderDate +SignKey
			例：2010092012345610020100920f00961bf2cb076e7d19d4224386700be
			把该字符串用MD5加密后转化成小写就是参数Sign的值。

			*/
			// 组织请求数据
			Map<String, String> paramsMap = new HashMap<String, String>();
			
			paramsMap.put("MerchantId", merchant.getMerNo());
			paramsMap.put("BillNo", order.getOrderNo());
			paramsMap.put("OrderDate", order.getOrdertime());
			paramsMap.put("Amount", order.getOrderAmount());
			paramsMap.put("NotifyUrl", merchant.getNotiUrl());
			paramsMap.put("ReturnUrl", merchant.getReturnUrl());
			paramsMap.put("GoodsName", order.getGoodsName());
			paramsMap.put("BankCode", order.getBankCode());
			paramsMap.put("PayType", order.getPayType());
			
			String data = order.getOrderNo().concat(order.getOrderAmount()).concat(order.getOrdertime()).concat(merchant.getMerKey());
			String signMsg = Encrypt.MD5(data).toLowerCase();	// 签名数据
			paramsMap.put("Sign", signMsg);
			
			System.out.println("MD5签名原文："+data);
			System.out.println("MD5签名密文："+signMsg);
	        
			
			StringBuffer sb = new StringBuffer();
			Set es = paramsMap.entrySet();//所有参与传参的参数按照accsii排序（升序）  
			Iterator it = es.iterator();  
	        while(it.hasNext()) {  
	            Map.Entry entry = (Map.Entry)it.next();  
	            String k = (String)entry.getKey();  
	            Object v = entry.getValue();  
//	            if(null != v && !"".equals(v) ) {     
	                sb.append(k + "=" + v + "&");
//	            }  
	        }  
	        
			System.out.println("完整的请求参数："+paramsMap);
			System.out.println("完整的请求参数字符串："+sb);
			System.out.println("支付请求地址："+merchant.getPayUrl());
			System.out.println("MD5_KEY："+merchant.getMerKey());
			
			return merchant.getPayUrl() + "?"+sb.toString();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "error";
	}
	
	
	/**
	 * 结果通知回参做签名校验
	 * @param req
	 * @param MER_KEY
	 * @return
	 */
	public static boolean checkResponseSign(HttpServletRequest request, String MER_KEY) {
		
		try {
			/**
			交易返回接口MD5摘要认证的明文信息如下： 
			【成功标志】+【商户编号】+【订单编号】+【订单金额】+【订单日期】+【密钥】 
			加密参数的次序为： 
			Success + MerchantId + BillNo + Amount + OrderDate + SignKey
			例：Y2010092012345610020100920f00961bf2cb076e7d19d4224386700be
			把该字符串用MD5加密后再转小写就是参数MD5的值。
			*/
			
			// 获取请求参数，并将数据组织成前面验证源字符串
			String Success = request.getParameter("success");
			String MerchantId = request.getParameter("merchantId");
			String BillNo = request.getParameter("billno");
			String Amount = request.getParameter("amount");
			String OrderDate = request.getParameter("orderDate");
			
			String sign = request.getParameter("sign");
			System.out.println("返回的MD5参数Sign="+sign);
			
			String data = Success.concat(MerchantId).concat(BillNo).concat(Amount).concat(OrderDate).concat(MER_KEY);
			String signMsg = Encrypt.MD5(data).toLowerCase();	// 签名数据
			
			System.out.println("MD5签名原文："+data);
			System.out.println("MD5签名密文："+signMsg);

			
			return signMsg.equals(sign);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
        
		return false;
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
