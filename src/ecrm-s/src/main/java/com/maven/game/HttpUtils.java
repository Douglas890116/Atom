package com.maven.game;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.util.EntityUtils;

import com.maven.exception.GameAPIRequestException;

public class HttpUtils {
	
	public static String getcontent(String url) throws Exception{
		Response response = Request.Get(url).socketTimeout(15000).connectTimeout(15000).execute();
		HttpResponse httpResopnse = response.returnResponse();
		return EntityUtils.toString(httpResopnse.getEntity(), "utf8");
	}
	
	public static String get(String url) throws Exception{
		Response response = Request.Get(url).connectTimeout(5000).execute();
		HttpResponse httpResopnse = response.returnResponse();
		if (httpResopnse.getStatusLine().getStatusCode()!=200) {
			throw new GameAPIRequestException("接入平台游戏接口调用异常");
		}
		return EntityUtils.toString(httpResopnse.getEntity(), "utf8");
	}
	
	
	public static String post(String url) throws Exception{
		Response response = Request.Post(url).connectTimeout(5000).execute();
		HttpResponse httpResopnse = response.returnResponse();
		if (httpResopnse.getStatusLine().getStatusCode()!=200) {
			throw new GameAPIRequestException("接入平台游戏接口调用异常");
		}
		return EntityUtils.toString(httpResopnse.getEntity(), "utf8");
	}

	public static void main(String[] args) {
		 try {
			Request.Post("http://www.baidu.com").socketTimeout(1000).connectTimeout(1).execute();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
