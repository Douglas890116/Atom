package com.maven.payment.fb;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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

public class FBPayMent<M extends FBMerchantConfig, O extends FBOrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(FBMerchantConfig merchant, FBOrderConfig order) throws Exception {
		FBSave save = new FBSave();
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
			System.out.println("付呗支付错误：" + statusCode);
			return "error";
		}
        InputStreamReader isr = new InputStreamReader(post.getResponseBodyAsStream());
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String temp;
        while ((temp=br.readLine())!= null) sb.append(temp);
        br.close();
        isr.close();
        String response = sb.toString();
		System.out.println("请求响应数据：" + response);
		JSONObject json = JSONObject.fromObject(response);
		String status = json.getString(FBAppConstants.rep_result_code);
		String message = json.getString(FBAppConstants.rep_result_message);

		if("200".equals(status)) {
			String data = json.getString(FBAppConstants.rep_data);
			JSONObject dataJSON = JSONObject.fromObject(data);
			return dataJSON.getString(FBAppConstants.rep_qr_code);
		} else {
			System.out.println("付呗支付请求失败，error_code-" + status + ", error message-" + message);
			return "";
		}
	}

	@Override
	public String pay(FBMerchantConfig merchant, FBOrderConfig order) throws Exception {
		return null;
	}

	public static void main(String[] args) {
		PayInterface<FBMerchantConfig, FBOrderConfig> payment = new FBPayMent<FBMerchantConfig, FBOrderConfig>();
		
		FBMerchantConfig merchant = new FBMerchantConfig();
		merchant.setAppId("20170824203147113864");
		merchant.setMd5Key("199e0d74d2d6815ef7c73b5af6c2e8ef");
		merchant.setMethod("openapi.payment.order.scan");
		merchant.setPayUrl("https://shq-api.51fubei.com/gateway");
		
		FBOrderConfig order = new FBOrderConfig();
		order.setOrderId(RandomString.UUID());
		order.setOrderAmount("1.0");
		order.setOrderType("1");
		order.setNonce(RandomString.UUID());
		
		try {
			payment.save(merchant, order);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
