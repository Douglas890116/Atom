package com.hy.pull.common.util.game.ttg;

import java.io.IOException;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class TTGUtil {

	/**
	 * HTTP Client Object,used HttpClient Class before(version 3.x),but now the
	 * HttpClient is an interface
	 */
	//private DefaultHttpClient client;

	/**
	 * Get XML String
	 * 
	 * @return XML-Formed string
	 */
	public String getXMLString() {
		StringBuffer sb = new StringBuffer();
		sb.append(IClient.XML_HEADER);
		sb.append("<AastraIPPhoneInputScreen type=\"string\">");
		sb.append("<Title>Hello world!</Title>");
		sb.append("<Prompt>Enter value</Prompt>");
		sb.append("<URL>http://localhost/xmlserver/test.do</URL>");
		sb.append("<Parameter>value</Parameter>");
		sb.append("<Default></Default>");
		sb.append("</AastraIPPhoneInputScreen>");
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
		String result = null;
		CloseableHttpClient client = HttpClientBuilder.create().build();
//		System.out.println("请求URL："+url);
//		System.out.println("请求DATA："+xmlData);
		HttpPost post = new HttpPost(url);
		StringEntity entity = new StringEntity(xmlData);
		post.setEntity(entity);
		post.setHeader("Content-Type", "Application/xml;charset=UTF-8");
		CloseableHttpResponse response = client.execute(post);		
		int statusCode = response.getStatusLine().getStatusCode();
		if(statusCode == HttpStatus.SC_OK){
			result = EntityUtils.toString(response.getEntity());
		}		
		return result;
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
	public static String sendXMLDataByPost1(String url, String xmlData) throws ClientProtocolException, IOException {
		Integer statusCode = -1;
		DefaultHttpClient client = new DefaultHttpClient();		
		System.out.println("请求URL："+url);
		System.out.println("请求DATA："+xmlData);
		HttpPost post = new HttpPost(url);
		StringEntity entity = new StringEntity(xmlData);
		post.setEntity(entity);
		post.setHeader("Content-Type", "Application/xml;charset=UTF-8");
		HttpResponse response = client.execute(post);		
		statusCode = response.getStatusLine().getStatusCode();
		String result = EntityUtils.toString(response.getEntity());
		return result;
	}

	public static void main(String[] args) throws ClientProtocolException, IOException {

	}

}

interface IClient {
	/**
	 * The XML Header of every XML string
	 */
	public String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
}