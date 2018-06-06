package com.maven.payment.xf;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.codehaus.jettison.json.JSONObject;

import com.maven.payment.PayInterface;
import com.maven.payment.xf.XFAppConstants.XF_PayType;
import com.maven.util.RandomString;

public class XFPayMent<M extends XFMerchantConfig, O extends XFOrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(XFMerchantConfig merchant, XFOrderConfig order) throws Exception {
		XFSave save = new XFSave();
		return save.getSaveUrl(merchant, order);
	}

	@Override
	public String pay(XFMerchantConfig merchant, XFOrderConfig order) throws Exception {
		XFSave save = new XFSave();
		String jsonData = save.getPayJsonData(merchant, order);
		String sign = SignUtils.HmacSHA1(jsonData, merchant.getSha1Key());
		PostMethod post = new PostMethod(merchant.getPayUrl());
		RequestEntity requestEntity = new StringRequestEntity(jsonData, "application/json", "UTF-8");
		post.setRequestEntity(requestEntity);
		post.setRequestHeader("Content-Hmac", sign);
		post.setRequestHeader("Content-Type", "application/json");
		post.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 1000 * 20);
		post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		HttpClient httpClient = new HttpClient();
		int statusCode = httpClient.executeMethod(post);
		if (statusCode != HttpStatus.SC_OK) {
			System.out.println("喜发代付，请求失败：" + statusCode);
			return "error";
		}
		byte[] responseBody = post.getResponseBodyAsString().getBytes(post.getResponseCharSet());
		String response = new String(responseBody, "UTF-8");
		System.out.println("请求响应数据：" + response);
		JSONObject json = new JSONObject(response);
		boolean status = (Boolean) json.get("success");
		if(status) return PayInterface.PAY_SUCCESS;
		System.out.println("代付失败：" + json.get("msg"));
		return json.get("msg").toString();
	}

	public static void main(String[] args) {
		PayInterface<XFMerchantConfig, XFOrderConfig> pay = new XFPayMent<XFMerchantConfig, XFOrderConfig>();
		
		XFMerchantConfig merchant = new XFMerchantConfig();
		merchant.setCid("15");
		merchant.setUid("sa");
		merchant.setSha1Key("1sXpmdwNq0y6fDrhdBMFTbaYnJK06YRxlzrKDFQOt103QgN3xAOcfEcacxHN4mhG");
		merchant.setPayUrl("https://www.dsdfpay.com/dsdf/api/outer_withdraw");
		
		XFOrderConfig order = new XFOrderConfig();
		order.setOrderId(RandomString.UUID());
		//存款参数
		order.setAmount("10");
		order.setType(XF_PayType.二维码存款.value);
		order.setTflag("WebMM");
		order.setTime((System.currentTimeMillis() / 1000) + "");
		order.setIp("180.232.108.150");
		
		try {
			pay.save(merchant, order);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}