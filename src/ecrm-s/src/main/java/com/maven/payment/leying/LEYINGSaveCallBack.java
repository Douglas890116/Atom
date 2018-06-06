package com.maven.payment.leying;

import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.maven.util.DateUtils;
import com.maven.util.Encrypt;

public class LEYINGSaveCallBack {

	public enum Enum_stateCode{
		已接受("0"),
		处理中("1"),
		处理成功("2"),
		处理失败("3");
		public String code;
		private Enum_stateCode(String code){
			this.code = code;
		}
	}
	
	public static boolean verifyCallback(HttpServletRequest request,String md5Key){
		String signMsg = request.getParameter("signMsg");
		StringBuffer sbf = new StringBuffer();
		sbf.append("orderID").append("=").append(formatString(request.getParameter("orderID"))).append("&")
		.append("resultCode").append("=").append(formatString(request.getParameter("resultCode"))).append("&")
		.append("stateCode").append("=").append(formatString(request.getParameter("stateCode"))).append("&")
		.append("orderAmount").append("=").append(formatString(request.getParameter("orderAmount"))).append("&")
		.append("payAmount").append("=").append(formatString(request.getParameter("payAmount"))).append("&")
		.append("acquiringTime").append("=").append(formatString(request.getParameter("acquiringTime"))).append("&")
		.append("completeTime").append("=").append(formatString(request.getParameter("completeTime"))).append("&")
		.append("orderNo").append("=").append(formatString(request.getParameter("orderNo"))).append("&")
		.append("partnerID").append("=").append(formatString(request.getParameter("partnerID"))).append("&")
		.append("remark").append("=").append(formatString(request.getParameter("remark"))).append("&")
		.append("charset").append("=").append(formatString(request.getParameter("charset"))).append("&")
		.append("signType").append("=").append(formatString(request.getParameter("signType"))).append("&")
		.append("pkey").append("=").append(md5Key);
		return signMsg.equals(Encrypt.MD5(sbf.toString()));
	}
	
	public static void main(String[] args) throws ParseException {
		StringBuffer sbf = new StringBuffer();
		sbf.append("orderID").append("=").append("FF8D9810541544C09D8417FCF830C742").append("&")
		.append("resultCode").append("=").append("00").append("&")
		.append("stateCode").append("=").append("2").append("&")
		.append("orderAmount").append("=").append("100").append("&")
		.append("payAmount").append("=").append("100").append("&")
		.append("acquiringTime").append("=").append(DateUtils.FormatDateToString(new Date(), "yyyyMMddHHmmss")).append("&")
		.append("completeTime").append("=").append(DateUtils.FormatDateToString(new Date(), "yyyyMMddHHmmss")).append("&")
		.append("orderNo").append("=").append("FF8D9810541544C09D8417FCF830C743").append("&")
		.append("partnerID").append("=").append("10000438336").append("&")
		.append("remark").append("=").append("").append("&")
		.append("charset").append("=").append("1").append("&")
		.append("signType").append("=").append("2").append("&");
		String url = "http://192.168.1.207:8080/ecrm-api/TPayment/LeYingCallback?"+sbf.toString();
		sbf.append("pkey").append("=").append("30820122300d06092a864886f70d01010105000382010f003082010a02820101009fc0dd7c03a5419033874a75130b4ab1eee7e503c453e7ab7114c8e287efb8970b531e98825acb3de8eeef53edcda7acc0b4e4a4f4e6802de98fd73e4ca45409d971494538afb04e1f5cb96f8c63b633863fd3ab4d5ce746b2f285db16a24d52b3f6bc20842b0d8c499a9acdf2a6752cdb27fb7ac9f2e457e1ea70ec88603f19f78baf946ff0f363bb8aedd4c62fb09911b0e285991e303f513d59e458f9ad5429ea54bd67c7794042e8df5d594c74126e9d615af2874d550c84daec4cb706c744f0333fba98a94f1238e3982dc89b202f1076a25b6f2b9986dba0a739136b214ba33373b7f4957a56b5e48a9c039babbaeb7659c4bfdb621c69559c3d3f701b0203010001");
		url+= "signMsg="+Encrypt.MD5(sbf.toString());
		System.out.println(url);
	}
	
	/**
	 * 参数过滤
	 * @param text
	 * @return
	 */
	private static String formatString(String text){ 
		if(text == null) {
			return ""; 
		}
		return text;
	}
	
	
}
