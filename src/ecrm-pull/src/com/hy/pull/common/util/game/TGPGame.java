package com.hy.pull.common.util.game;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hy.pull.common.util.DateUtil;
import com.hy.pull.common.util.Enum_MSG;
import com.hy.pull.common.util.SGSUtil;
import com.hy.pull.common.util.game.av.JsonToMap;
import com.hy.pull.common.util.game.zj.ZJUtil;
import com.hy.pull.service.BaseService.Enum_flag;
import com.hy.pull.service.game.LogUtil;

import net.sf.json.JSONObject;

/**
 * TGP的申博拉数据
 * 创建日期：2016-10-17
 * @author temdy
 */
public class TGPGame {
	private static Logger logger = LogManager.getLogger(TGPGame.class.getName());
	private static String getAccessToken(String API_URL, String client_id, String client_secret) {
        try {
        	
        	String httpUrl = API_URL.concat("api/oauth/token");
        	
        	HttpPost httpRequst = new HttpPost(httpUrl);//创建HttpPost对象  
            //填入各个表单域的�?
        	List paramsxx = new ArrayList<BasicNameValuePair>(); 
        	
        	BasicNameValuePair xxx1 = new BasicNameValuePair("client_id", client_id);
			paramsxx.add(xxx1);
			
			BasicNameValuePair xxx2 = new BasicNameValuePair("client_secret", client_secret);
			paramsxx.add(xxx2);
			
			BasicNameValuePair xxx3 = new BasicNameValuePair("grant_type", "client_credentials");
			paramsxx.add(xxx3);
			
			BasicNameValuePair xxx4 = new BasicNameValuePair("scope", "playerapi");
			paramsxx.add(xxx4);
    		
        	UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(paramsxx,HTTP.UTF_8);
    		httpRequst.setEntity(encodedFormEntity);
    		
    		httpRequst.addHeader("Content-Type", "application/x-www-form-urlencoded");
    		
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);  
            
            JSONObject jsonObject = new JSONObject();
            if(httpResponse.getStatusLine().getStatusCode() == 200)  {
            	jsonObject = JSONObject.fromObject(EntityUtils.toString(httpResponse.getEntity()));
            	
//            	System.out.println(jsonObject);
            	
    			if(jsonObject.has("access_token")) {
//    				System.out.println("成功获取到TOKEN="+jsonObject.getString("access_token"));
    				return Enum_MSG.成功.message(jsonObject.getString("access_token"));
    			} else {
    				
    				LogUtil.addListLog(LogUtil.HANDLE_TGP, API_URL, jsonObject.toString(), client_id, Enum_flag.异常.value);
    				logger.error("获取token失败："+jsonObject.toString());
    				
    				return Enum_MSG.失败.message(jsonObject.getString("error"),jsonObject.getString("error_description"));
    			}
    			
            } else {
            	
            	LogUtil.addListLog(LogUtil.HANDLE_TGP, API_URL, "StatusCode="+httpResponse.getStatusLine().getStatusCode()+"", client_id, Enum_flag.异常.value);
            	logger.error(httpResponse.getStatusLine().getStatusCode()+"","获取token失败");
            	return Enum_MSG.失败.message(httpResponse.getStatusLine().getStatusCode()+"","获取token失败");
            }
            
            
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			LogUtil.addListLog(LogUtil.HANDLE_TGP, API_URL, e.getMessage(), client_id, Enum_flag.异常.value);
			return Enum_MSG.失败.message("-1",e.getMessage());
		}
        
	}
	private static String getHttpUrlData(String API_URL,String client_id, String client_secret, String url){
		
		HttpMethod method = null;  
        try {  
            org.apache.commons.httpclient.URI uri = new org.apache.commons.httpclient.URI(url);  
            org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();  
            HostConfiguration hcfg = new HostConfiguration();  
            hcfg.setHost(uri);  
            
            client.setHostConfiguration(hcfg);  
            // 参数验证  
            client.getParams().setAuthenticationPreemptive(true);  
            // GET请求方式  
            method = new GetMethod(url);  
            
            JSONObject jsonObject = JSONObject.fromObject( getAccessToken( API_URL, client_id,  client_secret) );
			if( !jsonObject.getString("code").equals("0")) {
				return null;
			} 
			String AccessToken = jsonObject.getString("info");
			method.addRequestHeader("Authorization", "Bearer "+AccessToken);
			
            client.executeMethod(method);  
            return method.getResponseBodyAsString();  
        } catch (Exception e) {  
            e.printStackTrace();  
            logger.error(e);
            LogUtil.addListLog(LogUtil.HANDLE_TGP, API_URL, e.getMessage(), client_id, Enum_flag.异常.value);
            return null;
        }  
	
	}
	/**
	 * 获取游戏结果
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	private static String getRecord(String API_URL,String client_id, String client_secret,String startdate,String enddate) {
		try{
			
			
			
			
			String url = API_URL.concat("api/history/bets").concat("?startdate="+startdate+"&enddate="+enddate+"&includetestplayers=true&issettled=true");
			String result = getHttpUrlData(API_URL,client_id,client_secret,url);
			if(result == null) {
				return null;
			}
//			System.out.println("startdate="+SGSUtil.getDateCurrenStart1hour());
//			System.out.println("enddate="+SGSUtil.getDateCurren());
			System.out.println("请求URL："+url);
//			System.out.println("调用结果："+result);
			
			
			if( !result.equals("0") && !result.contains("errdesc")) {
				
				return result;
				
			} else {
				return "0";
			}
			
		}catch(Exception ex){
			logger.error(ex);
			ex.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_TGP, API_URL, ex.getMessage(), client_id, Enum_flag.异常.value);
			return null;
		}
	}
	
	/**
	 * 获取ZJ游戏数据（每次最大拉取1000条）
	 * @param apiUrl 接口URL
	 * @param agent 代理
	 * @param userKey 密钥
	 * @param flag 标志
	 * @param code 代理编码
	 * @return 游戏数据
	 * @throws Exception 抛出异常
	 */
	public static List<Map<String, Object>> getTGPData(String API_URL, String client_id,String client_secret,String code,String startdate,String enddate)throws Exception {
		List<Map<String, Object>> list = new ArrayList<>();
		
//		http://<server>/api/report/bethistory?userid=12345&startdate=2016-10-24T12:34:56%2B08:00&enddate=2016-10-26T12:34:56%2B08:00&includetestplayers=true&issettled=false
		
		String data = getRecord(API_URL, client_id, client_secret,startdate,enddate);
//		System.out.println("处理前："+data);
		if(data == null) {
			return null;
		}
		
		data = data.replaceFirst("ugsbetid,txid,betid,beton,betclosedon,betupdatedon,timestamp,roundid,roundstatus,userid,username,riskamt,winamt,winloss,beforebal,postbal,cur,gameprovider,gameprovidercode,gamename,gameid,platformtype,ipaddress,bettype,playtype,playertype,turnover,validbet", "").trim();//去除标题行
		data = data.replaceFirst("ugsbetid,txid,betid,beton,betclosedon,betupdatedon,timestamp,roundid,roundstatus,userid,username,riskamt,winamt,winloss,beforebal,postbal,cur,gameprovider,gameprovidercode,gamename,gameid,platformtype,ipaddress,bettype,playtype,playertype", "").trim();//去除标题行
		data.replaceFirst("\r\n", "");
//		System.out.println("处理后："+data);
		
		if( !data.equals("0") && !data.equals("")){
			list.addAll(getSGSData(code, data));
		}
		return list;
	}

	/**
	 * 获取ZJ游戏数据
	 * @param agent 代理
	 * @param result 结果
	 * @return 游戏数据
	 * @throws Exception 抛出异常
	 */
	private static SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy h:m:s aa",Locale.ENGLISH);// 设置日期格式
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
	private static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZ");// 设置日期格式
	private static List<Map<String, Object>> getSGSData(String agent, String result) throws Exception {
		List<Map<String, Object>> list = new ArrayList<>();
		
		String[] datas = result.split("\r\n");//多行
		
		int size = datas.length;
		if(size > 1){
			Map<String,Object> entity = null;
			String[] info = null;
			for (int i=0; i < size; i++) {
				if(datas[i].trim().equals("")) {
					continue;
				}
				info = datas[i].split(",");
				entity = new HashMap<String,Object>();
				
				entity.put("ugsbetid",info[0]);
				entity.put("txid",info[1]);
				entity.put("betid",info[2]);
				
				entity.put("beton", sdf2.parse(info[3].replaceAll("T", " ").replaceAll("\\+08:00", "")));
				entity.put("betclosedon",sdf2.parse(info[4].replaceAll("T", " ").replaceAll("\\+08:00", "")));
				entity.put("betupdatedon",sdf2.parse(info[5].replaceAll("T", " ").replaceAll("\\+08:00", "")));
				entity.put("timestamp",sdf2.parse(info[6].replaceAll("T", " ").replaceAll("\\+08:00", "")));
				
				entity.put("roundid",info[7]);
				entity.put("roundstatus",info[8]);
				entity.put("userid",info[9]);
				entity.put("username",info[10]);
				entity.put("riskamt",info[11]);//投注记录，返回的是负数？
				entity.put("winamt",info[12]);//winamt = winloss + riskamt（注意，该值是负数，所以这里的winamt其实是winloss-riskamt）
				entity.put("winloss",info[13]);
				entity.put("beforebal",info[14]);
				entity.put("postbal",info[15]);
				entity.put("cur",info[16]);
				entity.put("gameprovider",info[17]);
//				System.out.println("gameprovidercode="+info[18]);
				
				entity.put("gamename",info[19]);
				entity.put("gameid",info[20]);
				entity.put("platformtype",info[21]);
				entity.put("ipaddress",info[22]);
				entity.put("bettype",info[23]);
				entity.put("playtype","");//playtype参数可能为空
				
				entity.put("createtime", new Date());//自定义字段，投注时间
				entity.put("betmoney", Math.abs(Double.valueOf(info[11])));//自定义字段，投注额
				entity.put("netmoney", info[13]);//自定义字段，输赢金额
				entity.put("bettime", entity.get("beton"));//自定义字段，投注时间
				entity.put("Platformflag", agent);//自定义字段，企业识别码
				
				/*****暂时不手动算，使用API返回的数据***/
				double validmoney = 0;
				if(entity.get("gameprovider").toString().toLowerCase().equals("sunbet")) {
					String gamename = entity.get("gamename").toString().toLowerCase();
					if(gamename.indexOf("baccarat") > -1 || gamename.equals("bullfight")) {
						
						//如果输赢
						if(Double.valueOf(entity.get("winloss").toString()) == 0) {
							validmoney = 0;
						} else {
							validmoney = Double.valueOf(entity.get("betmoney").toString());//有效投注额为投注额
						}
						
					} else {
						validmoney = Double.valueOf(entity.get("betmoney").toString());//有效投注额为投注额
					}
				} else {
					validmoney = Double.valueOf(entity.get("betmoney").toString());//有效投注额为投注额
				}
				
				
				entity.put("validmoney", validmoney);
//				System.out.println("entity="+entity);
				
				list.add(entity);
			}
			
//			for (Map<String, Object> string : list) {
//				System.out.println(string);
//			}
		}
		return list;
	}
	
	
	public static void main(String[] arg){
		try {
			System.out.println(sdf2.parse("2017-04-17T17:06:20+08:00".replaceAll("T", " ").replaceAll("\\+08:00", "")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
