package com.hy.pull.common.util.game.ttg;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class TTGUtil {

	/**
	 * HTTP Client Object,used HttpClient Class before(version 3.x),but now the
	 * HttpClient is an interface
	 */

	/**
	 * Get XML String
	 * 
	 * @return XML-Formed string
	 */
	public String getXMLString() {
		// A StringBuffer Object
		StringBuffer sb = new StringBuffer();
		sb.append(IClient.XML_HEADER);
		sb.append("<AastraIPPhoneInputScreen type=\"string\">");
		sb.append("<Title>Hello world!</Title>");
		sb.append("<Prompt>Enter value</Prompt>");
		sb.append("<URL>http://localhost/xmlserver/test.do</URL>");
		sb.append("<Parameter>value</Parameter>");
		sb.append("<Default></Default>");
		sb.append("</AastraIPPhoneInputScreen>");
		// return to String Formed
		return sb.toString();
	}

	/**
	 * Send a XML-Formed string to HTTP Server by post method
	 * 
	 * @param url
	 *            the request URL string
	 * @param xmlData
	 *            XML-Formed string ,will not check whether this string is
	 *            XML-Formed or not
	 * @return the HTTP response status code ,like 200 represents OK,404 not
	 *         found
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public String sendXMLDataByPost(String url, String xmlData) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		int timeout = 10000;
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();//设置请求和传输超时时间
		
		
		
		System.out.println("请求URL："+url);
		System.out.println("请求DATA："+xmlData);
		// Send data by post method in HTTP protocol,use HttpPost instead of
		// PostMethod which was occurred in former version
		HttpPost post = new HttpPost(url);
		post.setConfig(requestConfig);
		// Construct a string entity
		StringEntity entity = new StringEntity(xmlData);
		// Set XML entity
		post.setEntity(entity);
		// Set content type of request header
		post.setHeader("Content-Type", "Application/xml;charset=UTF-8");
		// Execute request and get the response
		HttpResponse response = httpClient.execute(post);
		// Response Header - StatusLine - status code
		
//		statusCode = response.getStatusLine().getStatusCode();
		String result = EntityUtils.toString(response.getEntity());
		return result;
	}

	public static void main(String[] args) throws ClientProtocolException, IOException {
		TTGUtil client = new TTGUtil();
//		Integer statusCode = client.sendXMLDataByPost("https://ams-api.stg.ttms.co:8443/", client.getXMLString());
//		if (statusCode == 200) {
//			System.out.println("Request Success,Response Success!!!");
//		} else {
//			System.out.println("Response Code :" + statusCode);
//		}
	}

}

interface IClient {
	/**
	 * The XML Header of every XML string
	 */
	public String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
}