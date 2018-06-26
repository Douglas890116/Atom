package com.hy.pull.common.util.game;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.common.util.MoneyHelper;
import com.hy.pull.common.util.game.mg.MGUtil;
import com.hy.pull.service.BaseService.Enum_flag;
import com.hy.pull.service.game.LogUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 拉取MG游戏数据工具类 
 * 创建日期：2016-10-21
 * @author temdy
 */
public class MGGame {
	private static Logger logger = LogManager.getLogger(MGGame.class.getName());
	/**
	 * 拉取MG游戏数据列表
	 * @param apiUrl 接口URL
	 * @param agentId 代理帐号
	 * @param username 帐号
	 * @param password 密码
	 * @param startime 开始日期
	 * @param endtime 结束
	 * @param code 代理编码
	 * @return 游戏数据列表
	 */
	public static List<Map<String, Object>> getMGData(String apiUrl, String agentId,String username,String password,String startime,String endtime,String code) {
		List<Map<String, Object>> list = new ArrayList<>();
		try {
			SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(startime == null){
				startime = GameHttpUtil.getDate();
			}
			if(endtime == null){
				endtime = s1.format(new Date());
			}
			List list2 = gameinfo(apiUrl, agentId, username, password, startime, endtime,code);
			if(list2 == null) {
				return null;
			}
			list.addAll(list2);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("MG出现异常："+ex);
			LogUtil.addListLog(LogUtil.HANDLE_MG, apiUrl, ex.getMessage(), agentId, Enum_flag.异常.value);
			return null;
		}
		return list;
	}
	
	
	
	//接口根目录
		private final static String BASE_PATH = "https://ag.adminserv88.com/lps";
		
		/**
		 * 获取接口的token
		 * @param apiUrl 接口URL
		 * @param jusername 帐号
		 * @param jpassword 密码
		 * @return token
		 */
		@SuppressWarnings("deprecation")
		public static String getToken(String apiUrl, String jusername,String jpassword){
			HttpClient httpClients =  HttpClientBuilder.create().build();
			String url= apiUrl + "/j_spring_security_check?j_username="+jusername+"&j_password="+jpassword;
			//System.out.println(url);
			HttpPost request = new HttpPost(url);
		    HttpResponse response=null;
		    String token = null;
			try {
				request.addHeader("X-Requested-With", "X-Api-Client");
				request.addHeader("X-Api-Call", "X-Api-Client");
				response = httpClients.execute(request);
				String result = EntityUtils.toString(response.getEntity());
				if(result.startsWith("{")){
					JSONObject json = JSONObject.fromObject(result);
					if(json.has("token")){
						token = json.getString("token");
						return token;
					}
				}
				LogUtil.addListLog(LogUtil.HANDLE_MG, apiUrl, result, jusername, Enum_flag.异常.value);
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
				LogUtil.addListLog(LogUtil.HANDLE_MG, apiUrl, e.getMessage(), jusername, Enum_flag.异常.value);
				return null;
			}finally{
				httpClients.getConnectionManager().shutdown();
			}
		}
		
		static SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		static SimpleDateFormat s2 = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss");
		/**
		 * 查询游戏记录
		 * @param apiUrl 接口URL
		 * @param agentId 代理ID
		 * @param jusername 帐号
		 * @param jpassword 密码
		 * @param startime 开始日期
		 * @param endtime 结束日期
		 * @param code 代理编码
		 * @return
		 */
		public static List<Map<String,Object>> gameinfo(String apiUrl, String agentId, String jusername,String jpassword,String startime,String endtime,String code){
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			String result = null;
			try{
				String token = getToken(apiUrl, jusername, jpassword);
				if(token == null) {
					return null;
				}
				//System.out.println(token);
				if(token != null){
					
					String url=BASE_PATH + "/secure/agenttx/"+agentId+"?";
					StringBuilder param= new StringBuilder();
					param.append("start=");
					param.append(s2.format(s1.parse(startime)));
					param.append("&");
					param.append("end=");
					param.append(s2.format(s1.parse(endtime)));
					param.append("&");
					param.append("timezone=Asia/Shanghai");
					//System.out.println("url："+url+param);
					//System.out.println(token);
					result = post(url+param.toString(), token, agentId);
//					System.out.println("结果："+result);
					
					if(result == null) {
						return null;
					}
					
					if(result != null){
						if(result.startsWith("[")){
							JSONArray array = JSONArray.fromObject(result);//获取列表
							int size = array.size();
							if(size > 0){
								Map<String,Object> entity = null;
								JSONObject json = null;
								for (int i = 0; i < size; i++){
									entity = new HashMap<String,Object>();
									json = (JSONObject) array.get(i);
									//System.out.println("json="+json);
									
									entity.put("mg_key", json.getString("key"));
									entity.put("mg_colId", json.getString("colId"));
									entity.put("mg_agentId", json.getString("agentId"));
									entity.put("mg_mbrId", json.getString("mbrId"));
									entity.put("mg_mbrCode", json.getString("mbrCode"));
									entity.put("mg_transId", json.getString("transId"));
									entity.put("mg_gameId", json.getString("gameId"));
									entity.put("mg_transType", json.getString("transType"));
									entity.put("mg_transTime", format(json.getString("transTime")));
									entity.put("mg_mgsGameId", json.getString("mgsGameId"));
									entity.put("mg_mgsActionId", json.getString("mgsActionId"));
									entity.put("mg_clrngAmnt", json.getString("clrngAmnt"));
									entity.put("mg_balance", json.getString("balance"));
									entity.put("mg_refTransId", json.getString("refTransId"));
									entity.put("mg_refTransType", json.getString("refTransType"));
									entity.put("mg_createtime", s1.format(new Date()));
									entity.put("Platformflag", code);
									
									if(json.getString("transType").equals("bet")) {
										entity.put("mg_amnt", json.getString("amnt"));
										entity.put("mg_win", (0 - Double.valueOf(json.getString("amnt"))) );//负数
									} else {
										//如果是派彩记录则投注额为0
										entity.put("mg_amnt", 0);
										entity.put("mg_win", json.getString("amnt") );//负数
									}
									
									list.add(entity);
									
									
								}
							}
						}
//						LogUtil.addListLog(LogUtil.HANDLE_MG, apiUrl, result, jusername, Enum_flag.异常.value);
					}
				}
			}catch(Exception ex){
				logger.error(ex);
				ex.printStackTrace();
				LogUtil.addListLog(LogUtil.HANDLE_MG, apiUrl, result, jusername, Enum_flag.异常.value);
				return null;
			}		
			return list;
		} 
		
		/**
		 * UTC转北京时间
		 * @param utc UTC时间
		 * @return
		 */
		public static String format(String utc){	
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        long lt = new Long(utc);
	        Date date = new Date(lt);
	        return format.format(date);
		}
		
		/**
		 * 发送请求信息
		 * @param url 接口链接
		 * @param token 接口token
		 * @return 响应结果
		 */
		public static String sendPost(String url,String token,String agent) {
			String result = null;
			try {
				URL realUrl = new URL(url);
				// 打开和URL之间的连接
				URLConnection conn = realUrl.openConnection();
				HttpURLConnection httpUrlConnection = (HttpURLConnection) conn;  
				// 设置通用的请求属性
				httpUrlConnection.setRequestProperty("X-Requested-With", "X-Api-Client");
				httpUrlConnection.setRequestProperty("X-Api-Call", "X-Api-Client");
				httpUrlConnection.setRequestProperty("X-Api-Auth", token);
				System.out.println("MG请求URL："+url);
				httpUrlConnection.connect();  
				// 获取URLConnection对象对应的输出流
				StringBuilder builder = getResponseText(httpUrlConnection.getInputStream(),new StringBuilder());
				result = builder.toString();
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
				LogUtil.addListLog(LogUtil.HANDLE_MG, url + "?token="+token, e.getMessage(), agent, Enum_flag.异常.value);
			}
			return result;
		}

		/**
		 * 获取响应数据
		 * @param netIps InputStream流对象
		 * @param builder 字符串追加
		 * @return 响应数据
		 * @throws Exception 抛出异常
		 */
		private static StringBuilder getResponseText(InputStream netIps,StringBuilder builder) throws Exception {
			byte[] buffer = new byte[1024];
			int len;
			while ((len = netIps.read(buffer)) != -1) {
				builder.append(new String(buffer, 0, len, "GBK"));
			}
			return builder;
		}
		
		public static String post(String url,String token,String agent){
			String result = null;
			HttpClient httpClients =  HttpClientBuilder.create().build();
			
			//HttpPost request = new HttpPost(url);
			HttpGet request = new HttpGet(url);
		    HttpResponse response=null;
		 
			try {			
				System.out.println("MG请求URL："+url);
				
				request.addHeader("X-Requested-With", "X-Api-Client");
				request.addHeader("X-Api-Call", "X-Api-Client");	
				request.addHeader("X-Api-Auth", token);		
				request.addHeader("Content-Type", "application/json");				
				response = httpClients.execute(request);		
//				System.out.println(response.getStatusLine().getStatusCode());
				result=EntityUtils.toString(response.getEntity());
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
				LogUtil.addListLog(LogUtil.HANDLE_MG, url + "?token="+token, e.getMessage(), agent, Enum_flag.异常.value);
				return null;
			}finally{
				//httpClients.getConnectionManager().shutdown();
				HttpClientUtils.closeQuietly(httpClients);
			}
			return result;
		}
		
		
		
	public static void main(String[] a){
		System.out.println(getMGData("https://ag.adminserv88.com/lps","93980136","HYHCNYA","VMPj2GN9Fdbh","2016-12-27 20:50:00","2016-12-27 21:00:00",""));		
		
	}
}
