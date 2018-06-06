package com.maven.payment.jeanpay;

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

import com.hy.pull.common.util.api.Enum_MSG;
import com.maven.payment.lfwx.LfwxPayAppConstants;
import com.maven.util.Encrypt;
import com.maven.util.HttpPostUtil;

import net.sf.json.JSONObject;

/**
 * 极付支付
 * @author Administrator
 *
 */
public class JEANPAYpayUtil {
	
	 
	public JEANPAYpayUtil() {
		
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
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			strTime = dateFormat.format(date);

		} catch (Exception e) {
			strTime = "-1";
			e.fillInStackTrace();
		}
		return strTime;
	}
	
	/**
	 * 获取付款请求URL
	 */
	public String getPayUrl(JEANPAYMerchantConfig merchant, JEANPAYOrderConfig order){
		
		order.setOrdertime(getCurrentTime());
		
//		out_trade_no	String	Y	商户订单号: 商户提交的唯一订单号，该订单号不能重复。
//		bank_account_name	String	Y	收款银行卡户名
//		bank_account_cardno	String	Y	收款银行卡号
//		bank_province	String	Y	银行所在省份, 例如：江苏省
//		bank_city	String	Y	银行所在城市, 例如：南京市
//		bank_code	String	Y	银行编号，参见：提现银行代码 例如：BANK_ICBC
//		bank_name	String	Y	银行全称，参见：提现银行代码 例如：中国工商银行
//		bank_branch	String	Y	开户行分行，例如：南京市分行
//		bank_point	String	Y	开户行支行，例如：竹山路支行
//		total_fee	String	Y	提现金额, 单笔限额:50000元
//		notify_url	String	Y	提现出款异步通知地址，用于接收提现结果。
//		trade_time	Date	Y	时间格式为yyyy-MM-dd HH:mm:ss，例如：2015-01-01 12:00:00
//		remark	String	N	提现备注， 例如：福利费、佣金等
		
		SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();  
        parameters.put("partner_id", merchant.getMerNo());  
        parameters.put("service_name", "PTY_PAYMENT_TO_CARD");  //提现
        parameters.put("input_charset", "UTF-8");  
        parameters.put("version", merchant.getVersion());  
        parameters.put("sign_type", merchant.getSignType());
        
        parameters.put("out_trade_no", order.getOrderNo());  
        parameters.put("bank_account_name", order.getBankAccountName());
        parameters.put("bank_account_cardno", order.getBankAccountCardno());  
        parameters.put("bank_province", order.getBankPoint());
        parameters.put("bank_city", order.getBankPoint());
        parameters.put("bank_code", order.getBankCode());
        parameters.put("bank_name", order.getBankName());
        parameters.put("bank_branch", order.getBankPoint());
        parameters.put("bank_point", order.getBankPoint());
        parameters.put("total_fee", order.getOrderAmount());
        parameters.put("notify_url", merchant.getNotiUrl());
        parameters.put("trade_time", order.getOrdertime());
        
        StringBuffer sb = new StringBuffer();  
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）  
        Iterator it = es.iterator();  
        while(it.hasNext()) {  
            Map.Entry entry = (Map.Entry)it.next();  
            String k = (String)entry.getKey();  
            Object v = entry.getValue();  
            if(null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k) && !"sign_type".equals(k)) {     
                sb.append(k + "=" + v + "&");
            }  
        }  
        
        String params = sb.substring(0, sb.length() - 1) + "&key="+merchant.getMerKey();;
		
		System.out.println("极限代付请求做MD5参数="+params);
		String sign = Encrypt.MD5(params.toString()).toUpperCase();//转大写
		System.out.println("极限代付请求做MD5参数签名="+sign);
		parameters.put("sign", sign);
		
		sb = new StringBuffer();
		es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）  
        it = es.iterator();  
        while(it.hasNext()) {  
            Map.Entry entry = (Map.Entry)it.next();  
            String k = (String)entry.getKey();  
            Object v = entry.getValue();  
            if(null != v && !"".equals(v) ) {     
                sb.append(k + "=" + v + "&");
            }  
        }  
        
		System.out.println("极限代付完整的请求参数："+parameters);
		try {

			String result = HttpPostUtil.doPostSubmitMap(merchant.getPayUrl(), parameters);
			System.out.println("极限代付付款结果："+result);
			
			JSONObject jsonObject = JSONObject.fromObject(result);
			if(jsonObject.getJSONObject("payment_to_card_response").getString("resp_code").equals("RESPONSE_SUCCESS")) {
				return Enum_MSG.成功.message("RESPONSE_SUCCESS");
			}
			
			return Enum_MSG.逻辑事务异常.message(jsonObject);
			
		} catch (Exception e) {
			e.printStackTrace();
			return Enum_MSG.系统错误.message("系统异常："+e.getMessage());
		}
		
//		return jsonObject.getJSONObject("payment_to_card_response").getString("resp_code");
	}
	
	/**
	 * 获取支付请求URL
	 */
	public String getUrl(JEANPAYMerchantConfig merchant, JEANPAYOrderConfig order){
		
		order.setOrdertime(getCurrentTime());
		
		SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();  
        parameters.put("partner_id", merchant.getMerNo());  
        parameters.put("service_name", order.getServiceName());  
        parameters.put("input_charset", "UTF-8");  
        parameters.put("version", merchant.getVersion());  
        parameters.put("sign_type", merchant.getSignType());
        
        parameters.put("out_trade_no", order.getOrderNo());  
        parameters.put("order_amount", order.getOrderAmount());
        parameters.put("order_time", order.getOrdertime());  
        parameters.put("return_url", merchant.getReturnUrl());
        parameters.put("notify_url", merchant.getNotiUrl());
        parameters.put("pay_type", order.getPaytype());
        parameters.put("bank_code", order.getBankCode());
        
        StringBuffer sb = new StringBuffer();  
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）  
        Iterator it = es.iterator();  
        while(it.hasNext()) {  
            Map.Entry entry = (Map.Entry)it.next();  
            String k = (String)entry.getKey();  
            Object v = entry.getValue();  
            if(null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k) && !"sign_type".equals(k)) {     
                sb.append(k + "=" + v + "&");
            }  
        }  
        
        String params = sb.substring(0, sb.length() - 1) + "&key="+merchant.getMerKey();;
		
		System.out.println("支付请求做MD5参数="+params);
		String sign = Encrypt.MD5(params.toString()).toUpperCase();//转大写
		System.out.println("支付请求做MD5参数签名="+sign);
		parameters.put("sign", sign);
		
		sb = new StringBuffer();
		es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）  
        it = es.iterator();  
        while(it.hasNext()) {  
            Map.Entry entry = (Map.Entry)it.next();  
            String k = (String)entry.getKey();  
            Object v = entry.getValue();  
            if(null != v && !"".equals(v) ) {     
                sb.append(k + "=" + v + "&");
            }  
        }  
        
		System.out.println("完整的请求参数："+params);
		
		String url = merchant.getPayUrl() + "?"+sb.toString();
		
		if(order.getPaytype().equals("WXPAY") || order.getPaytype().equals("ALIPAY") ) {
			
			String result = HttpPostUtil.doPostSubmitMap(merchant.getPayUrl(), parameters);
			System.out.println("二维码支付请求结果："+result);
			JSONObject jsonObject = JSONObject.fromObject(result);
			if(jsonObject.getJSONObject("payment_online_response").getString("resp_code").equals("RESPONSE_SUCCESS")) {
				return jsonObject.getJSONObject("payment_online_response").getString("qrcode_url");
			}
		}
		
		return url;
	}
	
	
	/**
	 * 结果通知回参做签名校验
	 * @param req
	 * @param MER_KEY
	 * @return
	 */
	public static boolean checkResponseSign(HttpServletRequest req, String MER_KEY) {
		
		String response_sign = req.getParameter("sign");
		/***
		SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();  
		
		//响应参数
		Enumeration<?> obj = req.getParameterNames();
		while (obj.hasMoreElements()) {
			String key = (String) obj.nextElement();
			String value = req.getParameter(key);
			if (StringUtils.isNotBlank(value)) {
				parameters.put(key, value.trim());  
			}
		}
//      notify_type=async_notify, partner_id=80060156, out_trade_no=55A1D627FEF84312BE64373D526120A2, trade_time=2017-05-13 08:56:06, resp_desc=订单支付成功, order_amount=1.00, trade_status=TRADE_SUCCESS, sign=EC28E89EED529DA52CB2FCB7ADDB0C19, resp_code=RESPONSE_SUCCESS, order_time=2017-05-13 08:56:05, sign_type=MD5, order_sn=1705130856060351839}
//		MD5原文=partnerId=80060156&orderId=null&amount=null&result=null&payTime=null&traceId=null&reserve=null&creditType=null&key=F50497D07532C29C77759605AD5F1E9D
        StringBuffer sb = new StringBuffer();  
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）  
        Iterator it = es.iterator();  
        while(it.hasNext()) {  
            Map.Entry entry = (Map.Entry)it.next();  
            String k = (String)entry.getKey();  
            Object v = entry.getValue();  
            if(null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k) && !"sign_type".equals(k)) {     
                sb.append(k + "=" + v + "&");
            }  
        }
        String params = sb.substring(0, sb.length() - 1) + "&key="+MER_KEY;  
        ****/
		StringBuffer buffer = new StringBuffer();
		buffer.append("notify_type=").append(req.getParameter("notify_type")).append("&");
		buffer.append("order_amount=").append(req.getParameter("order_amount")).append("&");
		buffer.append("order_sn=").append(req.getParameter("order_sn")).append("&");
		buffer.append("order_time=").append(req.getParameter("order_time")).append("&");
		buffer.append("out_trade_no=").append(req.getParameter("out_trade_no")).append("&");
		buffer.append("partner_id=").append(req.getParameter("partner_id")).append("&");
		buffer.append("resp_code=").append(req.getParameter("resp_code")).append("&");
		buffer.append("resp_desc=").append(req.getParameter("resp_desc")).append("&");
		buffer.append("trade_status=").append(req.getParameter("trade_status")).append("&");
		buffer.append("trade_time=").append(req.getParameter("trade_time")).append("&");
		buffer.append("key=").append(MER_KEY).append("");
		
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
