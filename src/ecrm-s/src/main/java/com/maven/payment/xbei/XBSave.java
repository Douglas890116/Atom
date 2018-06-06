package com.maven.payment.xbei;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.maven.util.Encrypt;

/**
 * 新贝支付
 * @author Administrator
 *
 */
public class XBSave {
	
	 
	public XBSave() {
		
	}
	
	/**
	 * 获取支付请求URL
	 */
	public String getUrl(XBMerchantConfig merchant, XBOrderConfig order){
		
//		
//		Version=[V1.0]MerchantCode=[E00000]OrderId=[MR00000000]Amount=[10]AsyNotifyUrl=[http://www.xxx.com/AsynNotify]SynNotifyUrl=[http://www.xxx.com/SynNotifyUrl]OrderDate=[20131201112211]TradeIp=[127.0.0.1]PayCode=[100001]TokenKey=[1234567890]
//		                                                                                
		                                                                                		
		String sign = getRequestSign(merchant,order);
		
		StringBuffer url = new StringBuffer();
		url.append(merchant.getPayUrl())
		.append("?").append(XBAppConstants.Version).append("=").append(merchant.getVersion())
		.append("&").append(XBAppConstants.MerchantCode).append("=").append(merchant.getMerNo())
		.append("&").append(XBAppConstants.OrderId).append("=").append(order.getOrderNo())
		.append("&").append(XBAppConstants.Amount).append("=").append(order.getOrderAmount())
		.append("&").append(XBAppConstants.AsyNotifyUrl).append("=").append(merchant.getNotiUrl())
		.append("&").append(XBAppConstants.SynNotifyUrl).append("=").append(merchant.getReturnUrl())
		.append("&").append(XBAppConstants.OrderDate).append("=").append(order.getOrderDate())
		.append("&").append(XBAppConstants.TradeIp).append("=").append(order.getTradeIp())
		.append("&").append(XBAppConstants.PayCode).append("=").append(order.getBankCode())
		.append("&").append(XBAppConstants.SignValue).append("=").append(sign);
		
		System.out.println("新贝完整支付请求地址："+url);
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
	private static String getRequestSign(XBMerchantConfig merchant, XBOrderConfig order) {
		  StringBuffer sValue = new StringBuffer();
//			
//			Version=[V1.0]MerchantCode=[E00000]OrderId=[MR00000000]Amount=[10]AsyNotifyUrl=[http://www.xxx.com/AsynNotify]SynNotifyUrl=[http://www.xxx.com/SynNotifyUrl]OrderDate=[20131201112211]TradeIp=[127.0.0.1]PayCode=[100001]TokenKey=[1234567890]
//			           

		sValue.append(XBAppConstants.Version).append("=[").append(merchant.getVersion()).append("]");
		sValue.append(XBAppConstants.MerchantCode).append("=[").append(merchant.getMerNo()).append("]");
		sValue.append(XBAppConstants.OrderId).append("=[").append(order.getOrderNo()).append("]");
		sValue.append(XBAppConstants.Amount).append("=[").append(order.getOrderAmount()).append("]");
		sValue.append(XBAppConstants.AsyNotifyUrl).append("=[").append(merchant.getNotiUrl()).append("]");
		sValue.append(XBAppConstants.SynNotifyUrl).append("=[").append(merchant.getReturnUrl()).append("]");
		sValue.append(XBAppConstants.OrderDate).append("=[").append(order.getOrderDate()).append("]");
		sValue.append(XBAppConstants.TradeIp).append("=[").append(order.getTradeIp()).append("]");
		sValue.append(XBAppConstants.PayCode).append("=[").append(order.getBankCode()).append("]");
		sValue.append(XBAppConstants.TokenKey).append("=[").append(merchant.getMerKey()).append("]");
		System.out.println("支付请求做MD5参数="+sValue);
		String sNewString = null;
		
			
		sNewString = Encrypt.MD5(sValue.toString()).toUpperCase();//要求=MD5之后全部转为大写
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
	private static String getResponeSign(HttpServletRequest req,String merKey) {
		
//		同步通知参数：
//		Version=[V1.0]MerchantCode=[E00000]OrderId=[MR00000000]OrderDate=[20131201112211]TradeIp=[127.0.0.1]PayCode =[100001]State=[8888]TokenKey=[123456]
//		      
		
//		异步通知参数：
//		{SerialNo=XB03636205065066617351, 
//		flag=365de6f4eb6a9cc14156f65ee2048b14, 
//				Message=交易成功, 
//				Amount=100.00, 
//				OrderId=E000IRJV1484964477180NWCKCPPSK, 
//				OrderDate=20170121100757, 
//				TradeIp=121.58.225.242, 
//				PayCode=100012, 
//				FinishTime=20170121100802, 
//				Version=V1.0, 
//				MerchantCode=e03929, 
//				State=8888, 
//				SignValue=CB66CB5B7A44F6BDC9275E12FFF03F33}
		  
//		签名格式及顺序：
//		Version=[V1.0]MerchantCode=[E00000]OrderId=[MR00000000]OrderDate=[2013120 1112211]TradeIp=[127.0.0.1]SerialNo=[SER1010101010]Amount=[10.00]PayCode=[1 00001]State=[8888]FinishTime=[20131213121312]TokenKey=[123456]                                                                 
		                                                                		  
		StringBuffer sValue = new StringBuffer();
		sValue.append(XBAppConstants.Version).append("=[").append(req.getParameter(XBAppConstants.Version)).append("]");
		sValue.append(XBAppConstants.MerchantCode).append("=[").append(req.getParameter(XBAppConstants.MerchantCode)).append("]");
		sValue.append(XBAppConstants.OrderId).append("=[").append(req.getParameter(XBAppConstants.OrderId)).append("]");
		sValue.append(XBAppConstants.OrderDate).append("=[").append(req.getParameter(XBAppConstants.OrderDate)).append("]");
		sValue.append(XBAppConstants.TradeIp).append("=[").append(req.getParameter(XBAppConstants.TradeIp)).append("]");
		sValue.append(XBAppConstants.SerialNo).append("=[").append(req.getParameter(XBAppConstants.SerialNo)).append("]");
		sValue.append(XBAppConstants.Amount).append("=[").append(req.getParameter(XBAppConstants.Amount)).append("]");
		sValue.append(XBAppConstants.PayCode).append("=[").append(req.getParameter(XBAppConstants.PayCode)).append("]");
		sValue.append(XBAppConstants.State).append("=[").append(req.getParameter(XBAppConstants.State)).append("]");
		sValue.append(XBAppConstants.FinishTime).append("=[").append(req.getParameter(XBAppConstants.FinishTime)).append("]");
		sValue.append(XBAppConstants.TokenKey).append("=[").append(merKey).append("]");
		
		String sNewString = null;
		sNewString = Encrypt.MD5(sValue.toString()).toUpperCase();
		
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
	public static boolean checkResponseSign(HttpServletRequest req,String merKey) {
		
//		{
//		SerialNo=XB03636205065066572301, 
//		flag=02804cde5fec7b4d75817c09d83d6948, 
//		Message=交易成功, 
//		Amount=100.00, 
//		OrderId=E000IRJV1484902636350P9MILDV7N, 
//		OrderDate=20170120165716, 
//		TradeIp=121.58.225.242, 
//		PayCode=100012, 
//		FinishTime=20170120165720, 
//		Version=V1.0, 
//		MerchantCode=e03929, 
//		State=8888, 
//		SignValue=90C7DADD48FB7F3B9610253644BFA4F1
//				}
		
        String return_sign = req.getParameter(XBAppConstants.SignValue).toUpperCase();
        
        String sign = getResponeSign(req, merKey).toUpperCase();
        
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
	 
	public static void main(String[] args) {

//		异步通知参数：
//		{SerialNo=XB03636205065066617351, 
//		flag=365de6f4eb6a9cc14156f65ee2048b14, 
//				Message=交易成功, 
//				Amount=100.00, 
//				OrderId=E000IRJV1484964477180NWCKCPPSK, 
//				OrderDate=20170121100757, 
//				TradeIp=121.58.225.242, 
//				PayCode=100012, 
//				FinishTime=20170121100802, 
//				Version=V1.0, 
//				MerchantCode=e03929, 
//				State=8888, 
//				SignValue=CB66CB5B7A44F6BDC9275E12FFF03F33}
		  

		                                                                  
		                                                                		  
		StringBuffer sValue = new StringBuffer();
		sValue.append(XBAppConstants.Version).append("=[").append("V1.0").append("]");
		sValue.append(XBAppConstants.MerchantCode).append("=[").append("e03929").append("]");
		sValue.append(XBAppConstants.OrderId).append("=[").append("E000IRJV1484964477180NWCKCPPSK").append("]");
		sValue.append(XBAppConstants.OrderDate).append("=[").append("20170121100757").append("]");
		sValue.append(XBAppConstants.TradeIp).append("=[").append("121.58.225.242").append("]");
		sValue.append(XBAppConstants.SerialNo).append("=[").append("XB03636205065066617351").append("]");
		sValue.append(XBAppConstants.Amount).append("=[").append("100.00").append("]");
		sValue.append(XBAppConstants.PayCode).append("=[").append("100012").append("]");
		sValue.append(XBAppConstants.State).append("=[").append("8888").append("]");
		sValue.append(XBAppConstants.FinishTime).append("=[").append("20170121100802").append("]");
		sValue.append(XBAppConstants.TokenKey).append("=[").append("ASDASHFhuaisdha532dahsjhdASDFDW5").append("]");
		System.out.println(sValue.toString());
//		Version=[V1.0]MerchantCode=[E00000]OrderId=[MR00000000]OrderDate=[2013120 1112211]TradeIp=[127.0.0.1]SerialNo=[SER1010101010]Amount=[10.00]PayCode=[1 00001]State=[8888]FinishTime=[20131213121312]TokenKey=[123456]
		                                                                  
		                                                                  
		String sNewString = Encrypt.MD5(sValue.toString()).toUpperCase();
		System.out.println(sNewString.equals("CB66CB5B7A44F6BDC9275E12FFF03F33"));
	}
}
