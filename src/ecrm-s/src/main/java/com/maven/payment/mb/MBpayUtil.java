package com.maven.payment.mb;

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
 * 摩宝支付
 * @author Administrator
 *
 */
public class MBpayUtil {
	
	 
	public MBpayUtil() {
		
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
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
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
	public String getPayUrl(MBMerchantConfig merchant, MBOrderConfig order){
		
		order.setOrdertime(getCurrentTime());
		
		try {
			
			Mobo360Config.MD5_KEY = merchant.getMerKey();
			// 初始化签名
			Mobo360SignUtil.init();
			// 组织请求数据
			Map<String, String> paramsMap = new HashMap<String, String>();
			
			paramsMap.put("apiName", "SINGLE_ENTRUST_SETT");
			paramsMap.put("apiVersion", Mobo360Config.MOBAOPAY_API_VERSION);
			paramsMap.put("platformID", merchant.getPlatformID());
			paramsMap.put("merchNo", merchant.getMerNo());
			paramsMap.put("orderNo", order.getOrderNo());
			paramsMap.put("tradeDate", order.getOrdertime());
			paramsMap.put("Amt", order.getOrderAmount());
			paramsMap.put("merchUrl", merchant.getNotiUrl());
			paramsMap.put("merchParam", "1");
			paramsMap.put("tradeSummary", "2");
			
			paramsMap.put("bankAccNo", "2");
			paramsMap.put("bankAccName", "2");
			paramsMap.put("bankCode", order.getBankAccCode());
			paramsMap.put("bankName", "2");
			paramsMap.put("bankAccNo", "2");
					
			/**
	         * bankCode为空，提交表单后浏览器在新窗口显示摩宝支付收银台页面，在这里可以通过账户余额支付或者选择银行支付；
	         * bankCode不为空，取值只能是接口文档中列举的银行代码，提交表单后浏览器将在新窗口直接打开选中银行的支付页面。
	         * 无论选择上面两种方式中的哪一种，支付成功后收到的通知都是同一接口。
	         **/
			paramsMap.put("bankCode", order.getBankCode() == null ? "" : order.getBankCode() );
			

			String paramsStr = Mobo360Merchant.generatePayRequest(paramsMap);	// 签名源数据
			String signMsg = Mobo360SignUtil.signData(paramsStr);	// 签名数据
			String epayUrl = merchant.getPayUrl();	//支付网关地址
			paramsMap.put("signMsg", signMsg);
	        
			
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
			System.out.println("支付请求地址："+epayUrl);
			System.out.println("MD5_KEY："+Mobo360Config.MD5_KEY);
			
			return epayUrl + "?"+sb.toString();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "error";
	}
	
	/**
	 * 
	 * 
	 * 
	 * 获取付款请求URL
	 */
	public String getUrl(MBMerchantConfig merchant, MBOrderConfig order){
		
		order.setOrdertime(getCurrentTime());
		
		try {
			
			Mobo360Config.MD5_KEY = merchant.getMerKey();
			// 初始化签名
			Mobo360SignUtil.init();
			// 组织请求数据
			Map<String, String> paramsMap = new HashMap<String, String>();
			
			paramsMap.put("apiName", Mobo360Config.MOBAOPAY_APINAME_PAY);
			paramsMap.put("apiVersion", Mobo360Config.MOBAOPAY_API_VERSION);
			paramsMap.put("platformID", merchant.getPlatformID());
			paramsMap.put("merchNo", merchant.getMerNo());
			paramsMap.put("orderNo", order.getOrderNo());
			paramsMap.put("tradeDate", order.getOrdertime());
			paramsMap.put("amt", order.getOrderAmount());
			paramsMap.put("merchUrl", merchant.getNotiUrl());
			paramsMap.put("merchParam", "1");
			paramsMap.put("tradeSummary", "2");
					
			/**
	         * bankCode为空，提交表单后浏览器在新窗口显示摩宝支付收银台页面，在这里可以通过账户余额支付或者选择银行支付；
	         * bankCode不为空，取值只能是接口文档中列举的银行代码，提交表单后浏览器将在新窗口直接打开选中银行的支付页面。
	         * 无论选择上面两种方式中的哪一种，支付成功后收到的通知都是同一接口。
	         **/
			paramsMap.put("bankCode", order.getBankCode() == null ? "" : order.getBankCode() );
			

			String paramsStr = Mobo360Merchant.generatePayRequest(paramsMap);	// 签名源数据
			String signMsg = Mobo360SignUtil.signData(paramsStr);	// 签名数据
			String epayUrl = merchant.getPayUrl();	//支付网关地址
			paramsMap.put("signMsg", signMsg);
	        
			
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
			System.out.println("支付请求地址："+epayUrl);
			System.out.println("MD5_KEY："+Mobo360Config.MD5_KEY);
			
			return epayUrl + "?"+sb.toString();
			
			
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
			
			Mobo360Config.MD5_KEY = MER_KEY;
			
			// 签名初始化
			Mobo360SignUtil.init();

			// 获取请求参数，并将数据组织成前面验证源字符串
			String apiName = request.getParameter("apiName");
			String notifyTime = request.getParameter("notifyTime");
			String tradeAmt = request.getParameter("tradeAmt");
			String merchNo = request.getParameter("merchNo");
			String merchParam = request.getParameter("merchParam");
			String orderNo = request.getParameter("orderNo");
			String tradeDate = request.getParameter("tradeDate");
			String accNo = request.getParameter("accNo");
			String accDate = request.getParameter("accDate");
			String orderStatus = request.getParameter("orderStatus");
			String signMsg = request.getParameter("signMsg");
			signMsg.replaceAll(" ", "\\+");

			String srcMsg = String
					.format(
							"apiName=%s&notifyTime=%s&tradeAmt=%s&merchNo=%s&merchParam=%s&orderNo=%s&tradeDate=%s&accNo=%s&accDate=%s&orderStatus=%s",
							apiName, notifyTime, tradeAmt, merchNo,
							merchParam, orderNo, tradeDate, accNo, accDate,
							orderStatus);
			
			System.out.println("返回的MD5参数signMsg="+signMsg);
			System.out.println("支付响应做MD5签名srcMsg="+srcMsg);

			// 验证签名
			boolean verifyRst = Mobo360SignUtil.verifyData(signMsg, srcMsg);
			
			
			// 判断通知类型，若为后台通知需要回写"SUCCESS"给支付系统表明已收到支付通知
			// 否则支付系统将按一定的时间策略在接下来的24小时内多次发送支付通知。
			if (request.getParameter("notifyType").equals("1")) {
				
				// 回写‘SUCCESS’方式一： 重定向到一个专门用于处理回写‘SUCCESS’的页面，这样可以保证输出内容中只有'SUCCESS'这个字符串。
				
				// 回写‘SUCCESS’方式二： 直接让当前输出流中包含‘SUCCESS’字符串。两种方式都可以，但建议采用第一种。
				// out.println("SUCCESS");
			}
			
			
			
			return verifyRst;
			
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
