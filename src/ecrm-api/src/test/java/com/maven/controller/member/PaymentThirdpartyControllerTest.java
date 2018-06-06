package com.maven.controller.member;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.maven.payment.yom.YOMSave;
import com.maven.payment.yom.YomAppConstants;
import com.maven.util.Encrypt;

import junit.framework.TestCase;

public class PaymentThirdpartyControllerTest extends TestCase {
	
	public void testESaving() {
		try {
			String httpUrl = "http://127.0.0.1:8081/ecrm-api/TPayment/ESaving";
			
			String params = "brandcode=EB00001L&employeecode=E00000C3&orderamount=0.01&enterprisethirdpartycode=EP00000J&traceip=192.168.1.102";
			String aesparams= Encrypt.AESEncodeEncrypt(params, "VUhEndOteNSQDAAK");
			String signature = Encrypt.MD5(params+"Yb0caH3KBSv0j1sq");
			/*
			String params2 = Encrypt.AESDecrypt(aesparams, "VUhEndOteNSQDAAK", false);
			System.out.println(params2);
			System.out.println(params);
			String signature2 = String.valueOf(signature);
			String p = params2 + "Yb0caH3KBSv0j1sq";
			String signatureconfirm = Encrypt.MD5(p);
			if (!signature2.equals(signatureconfirm)) {
				System.out.println("解密失败");
			} else {
				System.out.println("解密成功");
			}
			*/
			Map<String, String> paramsx = new HashMap<String, String>();
//			params.put("enterprisecode", "EN0000");/** 企业编码 */
//			params.put("brandcode", "EB00001L");/** 品牌编码. */
//			params.put("employeecode", "E00000BO");//员工编码  阿奎罗
//			
//			params.put("orderamount", "0.01");
//			params.put("enterprisethirdpartycode", "P005");//优付支付渠道
//			params.put("traceip", "192.168.1.102");
			paramsx.put("enterprisecode", "EN0000");
			paramsx.put("signature", signature);
			paramsx.put("params", aesparams);
System.out.println(URLEncoder.encode(httpUrl,"UTF-8"));			
			String result = com.maven.controller.member.HttpPostUtil.doPostSubmitMapHttps(httpUrl, paramsx);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	
	@SuppressWarnings("unused")
	public void testYomPayCallback() {
		try {
			String httpUrl = "http://127.0.0.1:8081/ecrm-api/TPayment/YomPayCallback";
			
			String MER_KEY = "0dg4b37ior3f2i1024jhnufyybp0uma66slm94t9f2ql04x51452d6wtv6mki8ul";
			String trade_status = "1";
			String trade_time = "2016-09-30 12:20:30";
			String trade_no = "9759B668D99C4390875F71341F0B1502_ABC";
			String return_params = "";
			String order_amount = "0.01";
			String order_no = "9759B668D99C4390875F71341F0B1502";
			String mer_no = "10982";
			
			String params = "mer_no=1&order_no=E00000C3&order_amount=1&return_params=&trade_no=192.168.1.102&trade_time=&trade_status=1";
			StringBuffer sxxx = new StringBuffer();
			sxxx.append(YomAppConstants.p2_mer_no).append("=").append(mer_no).append("&");
			sxxx.append(YomAppConstants.p2_mer_no).append("=").append(mer_no).append("&");
			sxxx.append(YomAppConstants.p2_mer_no).append("=").append(mer_no).append("&");
			sxxx.append(YomAppConstants.p2_mer_no).append("=").append(mer_no).append("&");
			sxxx.append(YomAppConstants.p2_mer_no).append("=").append(mer_no).append("&");
			sxxx.append(YomAppConstants.p2_mer_no).append("=").append(mer_no).append("&");
			String sign = YOMSave.getResponeSign(mer_no, order_no, order_amount, return_params, trade_no, trade_time, trade_status, MER_KEY);
			
			Map<String, String> paramsx = new HashMap<String, String>();
//			params.put("enterprisecode", "EN0000");/** 企业编码 */
//			params.put("brandcode", "EB00001L");/** 品牌编码. */
//			params.put("employeecode", "E00000BO");//员工编码  阿奎罗
//			
//			params.put("orderamount", "0.01");
//			params.put("enterprisethirdpartycode", "P005");//优付支付渠道
//			params.put("traceip", "192.168.1.102");
			paramsx.put("enterprisecode", "EN0000");
//			paramsx.put("signature", signature);
//			paramsx.put("params", aesparams);
			String result = HttpPostUtil.doPostSubmitMapHttps(httpUrl, paramsx);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
