package com.maven.payment.gst;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import com.maven.payment.gst.GSTAppConstants.GST_PayType;
import com.maven.util.Encrypt;

/**
 * 国盛通支付
 * 
 * @author Administrator
 *
 */
public class GSTSave {

	public GSTSave() {

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
	 * 获取支付请求URL
	 */
	public String getUrl(GSTMerchantConfig merchant, GSTOrderConfig order) {

		order.setOrdertime(getCurrentTime());

		String sign = getRequestSign(merchant, order);

		StringBuffer url = new StringBuffer(merchant.getPayUrl());
		url.append("?").append(GSTAppConstants.p1_input_charset).append("=").append(order.getInputCharset())
		   .append("&").append(GSTAppConstants.p1_inform_url).append("=").append(merchant.getNotiUrl())
		   .append("&").append(GSTAppConstants.p1_return_url).append("=").append(merchant.getReturnUrl())
		   .append("&").append(GSTAppConstants.p1_pay_type).append("=").append(order.getPaytype())
		   .append("&").append(GSTAppConstants.p1_bank_code).append("=").append(order.getBankCode())
		   .append("&").append(GSTAppConstants.p1_merchant_code).append("=").append(merchant.getMerNo())
		   .append("&").append(GSTAppConstants.p1_order_no).append("=").append(order.getOrderNo())
		   .append("&").append(GSTAppConstants.p1_order_amount).append("=").append(order.getOrderAmount())
		   .append("&").append(GSTAppConstants.p1_order_time).append("=").append(order.getOrdertime())
		   .append("&").append(GSTAppConstants.p1_req_referer).append("=").append(merchant.getRefererUrl())
		   .append("&").append(GSTAppConstants.p1_customer_ip).append("=").append(order.getCustomerIp())
		   .append("&").append(GSTAppConstants.p1_return_params).append("=").append(order.getPaytype())
		   .append("&").append(GSTAppConstants.p1_sign).append("=").append(sign);
		System.out.println(url);
		return url.toString();
	}

	/**
	 * 支付请求做MD5参数签名
	 * 
	 * @return
	 */
	private static String getRequestSign(GSTMerchantConfig merchant, GSTOrderConfig order) {
		//
		// 1、 参数列表中，除去sign参数外，其他所有非空的参数都要参与签名，值为空的不参与签名。
		// 2、
		// 参与签名的参数顺序按照首字母从小到大（a到z）的顺序排列，如果遇到相同首字母则按第二个字母从小到大的顺序排列，以此类推，只有商户的私钥（key）放在最后参与签名。
		// 3、 在执行MD5签名时，传入的字符串字符集编码必须与input_chaset参数值一致，否则签名的结果会不一致。

		SortedMap<String, String> parameters = new TreeMap<String, String>();
		parameters.put(GSTAppConstants.p1_input_charset, order.getInputCharset());
		parameters.put(GSTAppConstants.p1_inform_url, merchant.getNotiUrl());
		parameters.put(GSTAppConstants.p1_return_url, merchant.getReturnUrl());
		parameters.put(GSTAppConstants.p1_pay_type, order.getPaytype());
		if(order.getPaytype().equals(GST_PayType.网银支付.value))
			parameters.put(GSTAppConstants.p1_bank_code, order.getBankCode());
		parameters.put(GSTAppConstants.p1_merchant_code, merchant.getMerNo());
		parameters.put(GSTAppConstants.p1_order_no, order.getOrderNo());
		parameters.put(GSTAppConstants.p1_order_amount, order.getOrderAmount());
		parameters.put(GSTAppConstants.p1_order_time, order.getOrdertime());
		parameters.put(GSTAppConstants.p1_req_referer, merchant.getRefererUrl());
		parameters.put(GSTAppConstants.p1_customer_ip, order.getCustomerIp());
		parameters.put(GSTAppConstants.p1_return_params, order.getPaytype());
		

		StringBuffer sb = new StringBuffer();
		Set<Entry<String, String>> es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
		Iterator<Entry<String, String>> it = es.iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}

		String params = sb.substring(0, sb.length() - 1) + "&key=" + merchant.getMerKey();

		System.out.println("支付请求做MD5参数=" + params);
		String sNewString = null;

		sNewString = Encrypt.MD5(params);
		System.out.println("支付请求做MD5参数签名=" + sNewString);
		return sNewString;
	}

	/**
	 * 结果通知回参做签名校验
	 * 
	 * @param req
	 * @param MER_KEY
	 * @return
	 */
	public static boolean checkResponseSign(HttpServletRequest req, String MER_KEY) {

		// 商户号 merchant_code Sting 是 商户注册签约后，支付平台分配的唯一标识号
		// 签名 sign String 是 签名数据，签名规则见附录
		// 商户唯一订单号 order_no String(32) 是 商户系统中的唯一订单号
		// 商户订单总金额 order_amount Number 是 订单总金额以元为单位，精确到小数点后两位
		// 商户订单时间 order_time Date 是 字符串格式要求为：
		// yyyy-MM-dd HH:mm:ss
		// 例如：2015-01-01 12:45:52
		// 交易状态 trade_status String 是 success 交易成功
		// failed 交易失败
		// paying 交易中
		// 支付平台订单号 trade_no String 是 支付平台里唯一的订单号

		String p2_merchant_code = req.getParameter(GSTAppConstants.p2_merchant_code);
		String p2_order_no = req.getParameter(GSTAppConstants.p2_order_no);
		String p2_order_amount = req.getParameter(GSTAppConstants.p2_order_amount);
		String p2_return_params = req.getParameter(GSTAppConstants.p2_return_params);
		String p2_trade_no = req.getParameter(GSTAppConstants.p2_trade_no);
		String p2_order_time = req.getParameter(GSTAppConstants.p2_order_time);
		String p2_trade_status = req.getParameter(GSTAppConstants.p2_trade_status);
		String p2_sign = req.getParameter(GSTAppConstants.p2_sign);

		SortedMap<String, String> parameters = new TreeMap<String, String>();
		parameters.put(GSTAppConstants.p2_merchant_code, p2_merchant_code);
		parameters.put(GSTAppConstants.p2_order_no, p2_order_no);
		parameters.put(GSTAppConstants.p2_order_amount, p2_order_amount);
		parameters.put(GSTAppConstants.p2_return_params, p2_return_params);
		parameters.put(GSTAppConstants.p2_trade_no, p2_trade_no);
		parameters.put(GSTAppConstants.p2_order_time, p2_order_time);
		parameters.put(GSTAppConstants.p2_trade_status, p2_trade_status);

		StringBuffer sb = new StringBuffer();
		Set<Entry<String, String>> es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
		Iterator<Entry<String, String>> it = es.iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}

		String params = sb.substring(0, sb.length() - 1) + "&key=" + MER_KEY;
		System.out.println("支付响应做MD5参数=" + params);

		String sign = Encrypt.MD5(params);
		System.out.println("支付响应做MD5参数签名=" + sign);

		return sign.equals(p2_sign);
	}
}