package com.maven.payment.jpay;

import javax.servlet.http.HttpServletRequest;

import com.maven.util.Encrypt;

public class JPaySignUtil {

	
	 /**   
     * 将元为单位的转换为分 替换小数点，支持以逗号区分的金额  
     *   
     * @param amount  
     * @return  
     */    
    private static String changeY2F(String amount){    
        String currency =  amount.replaceAll("\\$|\\￥|\\,", "");  //处理包含, ￥ 或者$的金额    
        int index = currency.indexOf(".");    
        int length = currency.length();    
        Long amLong = 0l;    
        if(index == -1){    
            amLong = Long.valueOf(currency+"00");    
        }else if(length - index >= 3){    
            amLong = Long.valueOf((currency.substring(0, index+3)).replace(".", ""));    
        }else if(length - index == 2){    
            amLong = Long.valueOf((currency.substring(0, index+2)).replace(".", "")+0);    
        }else{    
            amLong = Long.valueOf((currency.substring(0, index+1)).replace(".", "")+"00");    
        }    
        return amLong.toString();    
    }    
    
	/**
	 * 获取支付请求URL（得到二维码图片地址）
	 */
	public static String getUrl(JMerchantConfig merchant, JOrderConfig order){
		
		//sign=md5(c_code=1234567&pay_type=30&pay_amt=100&agent_bill_id=20100225132210&key=CC08C5E3E69F4E6B81DC0B)
		double zje = order.getPay_amt();//订单的支付金额
		String total_fee = changeY2F(String.valueOf(zje));
		String sign = getRequestSign(
				merchant.getMerNo(), 
				order.getPay_type(), 
				total_fee, 
				order.getAgent_bill_id(), 
				merchant.getMerKey());
		
//		c_code	必填	商户编号
//		pay_type	必填	22 支付宝  30微信
//		pay_amt	必填	金额  单位 分
//		goods_name	必填	商品名称
//		remark	必填	备注
//		agent_bill_id	必填	订单号
//		notify_url	必填	通知地址
//		callback_url	必填	返回地址
//		sign	必填	MD5签名结果

		
		StringBuffer url = new StringBuffer();
		url.append(merchant.getPayUrl())
		.append("?").append(JPayAppConstants.c_code).append("=").append(merchant.getMerNo())
		.append("&").append(JPayAppConstants.p1_pay_type).append("=").append(order.getPay_type())
		.append("&").append(JPayAppConstants.p1_pay_amt).append("=").append(total_fee)
		.append("&").append(JPayAppConstants.p1_goods_name).append("=").append(order.getGoods_name())
		.append("&").append(JPayAppConstants.p1_remark).append("=").append(order.getRemark())
		.append("&").append(JPayAppConstants.p1_agent_bill_id).append("=").append(order.getAgent_bill_id())
		.append("&").append(JPayAppConstants.p1_notify_url).append("=").append(merchant.getNotiUrl())
		.append("&").append(JPayAppConstants.p1_callback_url).append("=").append(merchant.getReturnUrl())
		.append("&").append(JPayAppConstants.p1_sign).append("=").append(sign);
//		System.out.println(url);
		return url.toString();
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
	private static String getRequestSign(String c_code , String pay_type , String pay_amt , String agent_bill_id , String MER_KEY) {
		  StringBuffer sValue = new StringBuffer();
//		  
//		  sign=md5(c_code=1234567&pay_type=30&pay_amt=100&agent_bill_id=20100225132210&key=CC08C5E3E69F4E6B81DC0B)
//		  
		
		sValue.append(JPayAppConstants.c_code).append("=").append(c_code);
		sValue.append("&").append(JPayAppConstants.p1_pay_type).append("=").append(pay_type);
		sValue.append("&").append(JPayAppConstants.p1_pay_amt).append("=").append(pay_amt);
		sValue.append("&").append(JPayAppConstants.p1_agent_bill_id).append("=").append(agent_bill_id);
		sValue.append("&").append(JPayAppConstants.c_key).append("=").append(MER_KEY);
		
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
	private static String getResponeSign(String c_code, String pay_type, String pay_amt , String jpay_bill_id, String agent_bill_id,	String MER_KEY) {
//		
//		sign=md5(c_code=1234567&pay_type=30&pay_amt=100&jpay_bill_id=23423423&agent_bill_id=20100225132210&key=CC08C5E3E69F4E6B81DC0B)
//				
		
		StringBuffer sValue = new StringBuffer();
//		sValue.append(c_code);
//		sValue.append(pay_type);
//		sValue.append(pay_amt);
//		sValue.append(jpay_bill_id);
//		sValue.append(agent_bill_id);
//		sValue.append(MER_KEY);
		
		sValue.append(JPayAppConstants.c_code).append("=").append(c_code);
		sValue.append("&").append(JPayAppConstants.p2_pay_type).append("=").append(pay_type);
		sValue.append("&").append(JPayAppConstants.p2_pay_amt).append("=").append(pay_amt);
		sValue.append("&").append(JPayAppConstants.p2_jpay_bill_id).append("=").append(jpay_bill_id);
		sValue.append("&").append(JPayAppConstants.p2_agent_bill_id).append("=").append(agent_bill_id);
		sValue.append("&").append(JPayAppConstants.c_key).append("=").append(MER_KEY);
		
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
		
		String c_code = req.getParameter(JPayAppConstants.c_code);
        String pay_type = req.getParameter(JPayAppConstants.p2_pay_type);
        String pay_amt = req.getParameter(JPayAppConstants.p2_pay_amt);
        String jpay_bill_id = req.getParameter(JPayAppConstants.p2_jpay_bill_id);
        String agent_bill_id = req.getParameter(JPayAppConstants.p2_agent_bill_id);
        String return_sign = req.getParameter(JPayAppConstants.p2_sign);
        
        String sign = getResponeSign(c_code, pay_type, pay_amt, jpay_bill_id, agent_bill_id, MER_KEY);
        
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
