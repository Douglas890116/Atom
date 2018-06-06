package com.maven.payment.dd;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.maven.payment.PayInterface;
import com.maven.util.RandomString;

import net.sf.json.JSONObject;

public class DDPayMent<M extends DDMerchantConfig, O extends DDOrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(DDMerchantConfig merchant, DDOrderConfig order) throws Exception {
		DDSave save = new DDSave();
		String jsonData = save.getJsonData(merchant, order);
		
		PostMethod post = new PostMethod(merchant.getPayUrl());
		RequestEntity requestEntity = new StringRequestEntity(jsonData, "application/json", "UTF-8");
		post.setRequestEntity(requestEntity);
		post.setRequestHeader("Content-Type", "application/json");
		post.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 1000 * 20);
		post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		HttpClient httpClient = new HttpClient();
		int statusCode = httpClient.executeMethod(post);
		if (statusCode != HttpStatus.SC_OK) {
			System.out.println("点点支付错误：" + statusCode);
			return "error";
		}
		byte[] responseBody = post.getResponseBodyAsString().getBytes(post.getResponseCharSet());
		String response = new String(responseBody, "UTF-8");
		System.out.println("请求响应数据：" + response);
		JSONObject json = JSONObject.fromObject(response);
		String status = json.getString(DDAppConstants.rep_serverCode);
		String url = json.getString(DDAppConstants.rep_returnMsg);
		if("0000".equals(status)) {
			return url;
		} else {
			System.out.println("点点支付请求错误：" + url);
			return "";
		}
	}

	@Override
	public String pay(DDMerchantConfig merchant, DDOrderConfig order) throws Exception {
		DDSave pay = new DDSave();
		String jsonData = pay.getPayOutJsonData(merchant, order);
		
		PostMethod post = new PostMethod(merchant.getPayUrl());
		RequestEntity requestEntity = new StringRequestEntity(jsonData, "application/json", "UTF-8");
		post.setRequestEntity(requestEntity);
		post.setRequestHeader("Content-Type", "application/json");
		post.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 1000 * 20);
		post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		HttpClient httpClient = new HttpClient();
		int statusCode = httpClient.executeMethod(post);
		if (statusCode != HttpStatus.SC_OK) {
			System.out.println("点点支付错误：" + statusCode);
			return "error";
		}
		byte[] responseBody = post.getResponseBodyAsString().getBytes(post.getResponseCharSet());
		String response = new String(responseBody, "UTF-8");
		System.out.println("请求响应数据：" + response);
		JSONObject json = JSONObject.fromObject(response);
		String status = json.getString(DDAppConstants.rep_serverCode);
		String msg = json.getString(DDAppConstants.rep_returnMsg);
		if ("0000".equals(status)) {
			System.out.println(msg);
			return PayInterface.PAY_SUCCESS;
		} else {
			System.err.println(msg);
			return "FAIL";
		}
	}

	public static void main(String[] args) {
		PayInterface<DDMerchantConfig, DDOrderConfig> pay = new DDPayMent<DDMerchantConfig, DDOrderConfig>();
		
		DDMerchantConfig merchant = new DDMerchantConfig();
		merchant.setMerCode("0a782d185c353f6fee9fcc852ed8e4063a848e07587978980b4d1078b844383e");
		merchant.setMd5Key("18153aa223de28ab59ef5572808f984ccfd10a8420b9c14fe601c499a482df53");
		merchant.setPayType("1005");
		merchant.setPayUrl("http://api.itapgo.com/tapgoApi/3Dcode");
		merchant.setNotifyUrl("http://pay.99scm.com/TPayment/DDPayCallback");
		merchant.setReturnUrl("http://pay.99scm.com/TPayment/success");
		
		DDOrderConfig order = new DDOrderConfig();
		order.setOrderNo(RandomString.UUID());
		order.setAmount("100");
		order.setName("name");
		order.setCustomerName("测试");
		order.setCustomerPhone("123457891597");
		order.setCustomerCertType("01");
		order.setCustomerCertId("159745164891561324897");
		order.setBankNo("CMB");
		order.setBankName("招商银行");
		order.setBankCardNo("CMB");
		try {
			System.out.println(pay.pay(merchant, order));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
