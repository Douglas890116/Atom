package com.maven.controller.member;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
@SuppressWarnings({"rawtypes","unchecked","resource","deprecation"})
public class HttpPostUtil {

	
	public static String doPostSubmitMapHttps(String httpUrl,Map<String, String> params) {
		org.apache.http.client.HttpClient httpClient = null;  
        try {
        	httpClient = new SSLClient();  
        	
        	HttpPost httpPost = new HttpPost(httpUrl);//创建HttpPost对象  
            //填入各个表单域的�?
			List paramsxx = new ArrayList<BasicNameValuePair>(); 
        	
        	Iterator ite = params.entrySet().iterator();
    		while(ite.hasNext()){
    			Map.Entry<String, String> entry = (Entry<String, String>) ite.next();
    			String key = entry.getKey();//map中的key
    			String value = entry.getValue();//上面key对应的value
    			BasicNameValuePair xxx = new BasicNameValuePair(key,value);
    			paramsxx.add(xxx);
    		}
    		
        	UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(paramsxx,HTTP.UTF_8);
        	httpPost.setEntity(encodedFormEntity);
    		
            HttpResponse httpResponse = httpClient.execute(httpPost);  
            if(httpResponse.getStatusLine().getStatusCode() == 200)  {
            	String strResult = EntityUtils.toString(httpResponse.getEntity());
            	return strResult;
            } else {
            	return httpResponse.getStatusLine().getStatusCode()+"";
            }
		} catch (Exception e) {
			e.printStackTrace();
			return -1+"";
		}
	}
	
	public static String doPostSubmitMap(String httpUrl,Map<String, String> params) {
        try {
        	HttpPost httpRequst = new HttpPost(httpUrl);//创建HttpPost对象  
            //填入各个表单域的�?
        	List paramsxx = new ArrayList<BasicNameValuePair>(); 
        	
        	Iterator ite = params.entrySet().iterator();
    		while(ite.hasNext()){
    			Map.Entry<String, String> entry = (Entry<String, String>) ite.next();
    			String key = entry.getKey();//map中的key
    			String value = entry.getValue();//上面key对应的value
    			BasicNameValuePair xxx = new BasicNameValuePair(key,value);
    			paramsxx.add(xxx);
    		}
    		
        	UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(paramsxx,HTTP.UTF_8);
    		httpRequst.setEntity(encodedFormEntity);
    		
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);  
            if(httpResponse.getStatusLine().getStatusCode() == 200)  {
            	String strResult = EntityUtils.toString(httpResponse.getEntity());
            	return strResult;
            } else {
            	return httpResponse.getStatusLine().getStatusCode()+"";
            }
		} catch (Exception e) {
			e.printStackTrace();
			return -1+"";
		}
	}
	
	/**
	 * post方式请求�?个URL，成功则返回0，其他返�?-1
	 * 
	 * @param httpUrl 地址
	 * @param params 参数列表
	 * @return
	 */
	public static int doPostSubmit(String httpUrl,Map<String, String> params) {
        try {
        	PostMethod postMethod = new UTF8PostMethod(httpUrl);
            //填入各个表单域的�?
            NameValuePair[] data = new NameValuePair[params.size()];
            Iterator ite = params.entrySet().iterator();
            int i = 0;
    		while(ite.hasNext()){
    			Map.Entry<String, String> entry = (Entry<String, String>) ite.next();
    			String key = entry.getKey();//map中的key
    			String value = entry.getValue();//上面key对应的value
    			data[i] = new NameValuePair(key,value);
    			i++;
    		}
            //将表单的值放入postMethod�?
            postMethod.setRequestBody(data);
            //执行postMethod
            HttpClient httpClient = new HttpClient();
            //连接超时
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5 * 1000); //毫秒
            //读取超时
            //httpClient.getHttpConnectionManager().getParams().setSoTimeout(5 * 1000);//毫秒
            int result = httpClient.executeMethod(postMethod);
            postMethod.releaseConnection();//释放连接
            if(result == HttpStatus.SC_OK) {
            	return 0;
            } else {
            	return result;
            }
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	
	/**
	 * post方式请求�?个URL，成功则返回0，其他返�?-1
	 * 
	 * @param httpUrl 地址
	 * @param params 参数列表（key=value|key=value�?
	 * @return
	 */
	public static int doPostSubmit(String httpUrl,String params) {
        try {
        	PostMethod postMethod = new UTF8PostMethod(httpUrl);
            //填入各个表单域的�?
        	String[] strs = params.split("\\|");
            NameValuePair[] data = new NameValuePair[strs.length];
            int i = 0;
    		for (String string : strs) {
    			String[] strs2 = string.split("=");
    			data[i] = new NameValuePair(strs2[0],strs2.length==1? "" : strs2[1]  );
    			i ++;
			}
            //将表单的值放入postMethod�?
            postMethod.setRequestBody(data);
            //执行postMethod
            HttpClient httpClient = new HttpClient();
            //连接超时
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5 * 1000); //毫秒
            //读取超时
            httpClient.getHttpConnectionManager().getParams().setSoTimeout(5 * 1000);//毫秒
            int result = httpClient.executeMethod(postMethod);
            postMethod.releaseConnection();//释放连接
            if(result == HttpStatus.SC_OK) {
            	return 0;
            } else {
            	return result;
            }
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * post方式请求�?个URL，返回网页结果内�?
	 * 
	 * @param httpUrl 地址
	 * @param params 参数列表（key=value|key=value�?
	 * @return
	 */
	@SuppressWarnings("unused")
	public static String doPostSubmit(String httpUrl) {
        try {
        	PostMethod postMethod = new UTF8PostMethod(httpUrl);
            //填入各个表单域的�?
        	String[] strs = "v=1".split("\\|");
            NameValuePair[] data = new NameValuePair[strs.length];
            int i = 0;
    		for (String string : strs) {
    			String[] strs2 = string.split("=");
    			data[i] = new NameValuePair(strs2[0],strs2.length==1? "" : strs2[1]  );
    			i ++;
			}
//            //将表单的值放入postMethod�?
            postMethod.setRequestBody(data);
            //执行postMethod
            HttpClient httpClient = new HttpClient();
            //连接超时
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5 * 1000); //毫秒
            //读取超时
            httpClient.getHttpConnectionManager().getParams().setSoTimeout(5 * 1000);//毫秒
            int result = httpClient.executeMethod(postMethod);
            postMethod.releaseConnection();//释放连接
            if(true) {
            	StringBuffer contentBuffer = new StringBuffer();
				InputStream in = postMethod.getResponseBodyAsStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in, postMethod.getResponseCharSet()));
						
				String inputLine = null;
				while ((inputLine = reader.readLine()) != null) {
					contentBuffer.append(inputLine);
				}
				reader.close();
				in.close();
				
            	return contentBuffer.toString();
            } else {
            	return null;
            }
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static String doGetSubmit(String url) {  
		HttpClient client = new HttpClient();  
        StringBuilder sb = new StringBuilder();  
        InputStream ins = null;  
        // Create a method instance.  
        GetMethod method = new GetMethod(url);  
        // Provide custom retry handler is necessary  
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,  
                new DefaultHttpMethodRetryHandler(3, false));  
        try {  
            // Execute the method.  
            int statusCode = client.executeMethod(method);  
            //System.out.println(statusCode);  
            if (statusCode == HttpStatus.SC_OK) {  
                ins = method.getResponseBodyAsStream();  
                System.out.println(method.getPath());
                System.out.println(method.getURI().toString());
                byte[] b = new byte[1024];  
                int r_len = 0;  
                while ((r_len = ins.read(b)) > 0) {  
                    sb.append(new String(b, 0, r_len, method  
                            .getResponseCharSet()));  
                }  
            } else {  
                System.err.println("Response Code: " + statusCode);  
            }  
        } catch (HttpException e) {  
            System.err.println("Fatal protocol violation: " + e.getMessage());  
        } catch (IOException e) {  
            System.err.println("Fatal transport error: " + e.getMessage());  
        } finally {  
            method.releaseConnection();  
            if (ins != null) {  
                try {
					ins.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
            }  
        }  
        //System.out.println(sb.toString()); 
        return sb.toString();
    }  
	
	//Inner class for UTF-8 support
    public static class UTF8PostMethod extends PostMethod {
        public UTF8PostMethod(String url) {
            super(url);
        }
        @Override
        public String getRequestCharSet() {
            //return super.getRequestCharSet();
            return "UTF-8";
        }
    }
    
    
    

    public static void main(String[] args) {
    	String str = doGetSubmit("http://apis.haoservice.com/lifeservice/exp?key=a096c107b0e649859d40776b6293e512&com=shunfeng&no=088232230081");
		System.out.println(str);
	}
}
