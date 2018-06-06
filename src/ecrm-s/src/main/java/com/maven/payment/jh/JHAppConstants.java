package com.maven.payment.jh;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.maven.game.OrderNewUtil2;
import com.maven.util.RandomString;

public class JHAppConstants {
	public static String getOrderNo() {
		return RandomString.createRandomString(2) + OrderNewUtil2.getPatchno();
	}
	public static String getOrderTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(new Date());
	}
	/*========== 请求参数列表 ==========*/
	public static final String req_merchantOutOrderNo = "merchantOutOrderNo";
	public static final String req_merid = "merid";
	public static final String req_noncestr = "noncestr";
	public static final String req_notifyUrl = "notifyUrl";
	public static final String req_orderMoney = "orderMoney";
	public static final String req_orderTime = "orderTime";
	public static final String req_sign = "sign";
	/*========== 响应参数列表 ==========*/
	public static final String rep_merid = "merid";
	public static final String rep_merchantOutOrderNo = "merchantOutOrderNo";
	public static final String rep_orderMoney = "orderMoney";
	public static final String rep_url = "url";
	/*========== 响应参数列表 ==========*/
	public static final String callback_merchantOutOrderNo = "merchantOutOrderNo";
	public static final String callback_merid = "merid";
	public static final String callback_msg = "msg";
	public static final String callback_payMoney = "payMoney";
	public static final String callback_noncestr = "noncestr";
	public static final String callback_orderNo = "orderNo";
	public static final String callback_payResult = "payResult";
	public static final String callback_sign = "sign";
	/*========== 通知成功后需要返回的字符串 ==========*/
	public static final String return_string = "success";
}