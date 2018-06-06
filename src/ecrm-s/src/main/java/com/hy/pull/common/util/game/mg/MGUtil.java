package com.hy.pull.common.util.game.mg;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 拉取MG游戏数据工具类
 * 创建日期：2016-10-21
 * @author temdy
 */
public class MGUtil {
	
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
		HttpPost request = new HttpPost(url);
	    HttpResponse response=null;
	    String token = null;
		try {
			request.addHeader("X-Requested-With", "X-Api-Client");
			request.addHeader("X-Api-Call", "X-Api-Client");
			response = httpClients.execute(request);
			String result = EntityUtils.toString(response.getEntity());
			JSONObject json = JSONObject.fromObject(result);
			if(json.has("token")){
				token = json.getString("token");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			httpClients.getConnectionManager().shutdown();
		}
		return token;
	}
	
	/**
	 * 查询游戏记录
	 * @param apiUrl 接口URL
	 * @param jusername 帐号
	 * @param jpassword 密码
	 * @param startime 开始日期
	 * @param endtime 结束日期
	 * @return
	 */
	public static List<Map<String,Object>> gameinfo(String apiUrl, String agent, String jusername,String jpassword,String startime,String endtime){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try{
			String token = getToken(apiUrl, jusername, jpassword);
			SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat s2 = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss");
			String url=BASE_PATH + "/secure/hortx/86292241?";
			StringBuilder param= new StringBuilder();
			param.append("start=");
			param.append(s2.format(s1.parse(startime)));
			param.append("&");
			param.append("end=");
			param.append(s2.format(s1.parse(endtime)));
			param.append("&");
			param.append("timezone=Asia/Shanghai");
			String result = sendPost(url+param, token);
			if(result != null){
				JSONArray array = JSONArray.fromObject(result);//获取列表
				int size = array.size();
				if(size > 0){
					Map<String,Object> entity = null;
					JSONObject json = null;
					for (int i = 0; i < size; i++){
						entity = new HashMap<String,Object>();
						json = JSONObject.fromObject(array.get(i));
						entity.put("mg_key", json.get("key"));
						entity.put("mg_colId", json.get("colId"));
						entity.put("mg_horNeKey", json.get("horNeKey"));
						entity.put("mg_mbrNeKey", json.get("mbrNeKey"));
						entity.put("mg_betTransKey", json.get("betTransKey"));
						entity.put("mg_gameKey", json.get("gameKey"));
						entity.put("mg_type", json.get("type"));
						entity.put("mg_product", json.get("product"));
						entity.put("mg_transactionTimestampDate", json.get("transactionTimestampDate"));
						entity.put("mg_amount", json.get("amount"));
						entity.put("mg_mbrCode", json.get("mbrCode"));
						entity.put("mg_mbrAlias", json.get("mbrAlias"));
						entity.put("mg_mbrUsername", json.get("mbrUsername"));
						entity.put("mg_gameCasinoId", json.get("gameCasinoId"));
						entity.put("mg_gamePokerId", json.get("gamePokerId"));
						entity.put("mg_gamePokerType", json.get("gamePokerType"));
						entity.put("mg_refKey", json.get("refKey"));
						entity.put("mg_refType", json.get("refType"));
						entity.put("mg_afterTxWalletAmount", json.get("afterTxWalletAmount"));
						entity.put("mg_mgsGameId", json.get("mgsGameId"));
						entity.put("mg_mgsActionId", json.get("mgsActionId"));
						entity.put("mg_clrngAmnt", json.get("clrngAmnt"));
						entity.put("mg_createtime", s1.format(new Date()));
						entity.put("Platformflag", agent);
						list.add(entity);
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		return list;
	} 
	
	/**
	 * 发送请求信息
	 * @param url 接口链接
	 * @param token 接口token
	 * @return 响应结果
	 */
	public static String sendPost(String url,String token) {
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
			
			httpUrlConnection.connect();  
			// 获取URLConnection对象对应的输出流
			StringBuilder builder = getResponseText(httpUrlConnection.getInputStream(),new StringBuilder());
			result = builder.toString();
		} catch (Exception e) {
			e.printStackTrace();
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
	
	public static SimpleDateFormat foo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	public static String getCurrenDateUTC() {
		

		Calendar gc = GregorianCalendar.getInstance();


//		System.out.println("gc.getTime():"+gc.getTime());


//		System.out.println("gc.getTimeInMillis():"+new Date(gc.getTimeInMillis()));




		//当前系统默认时区的时间：


		Calendar calendar=new GregorianCalendar();


//		System.out.print("时区："+calendar.getTimeZone().getID()+" ");


//		System.out.println("时间："+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE));


		//美国洛杉矶时区


		TimeZone tz=TimeZone.getTimeZone("America/Los_Angeles");


		//时区转换


		calendar.setTimeZone(tz);


//		System.out.print("时区："+calendar.getTimeZone().getID()+" ");


//		System.out.println("时间："+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE));


		//1、取得本地时间：


		java.util.Calendar cal = java.util.Calendar.getInstance();

		 


		//2、取得时间偏移量：


		int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);

		 


		//3、取得夏令时差：


		int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);

		 


		//4、从本地时间里扣除这些差量，即可以取得UTC时间：


		cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));

		//之后调用cal.get(int x)或cal.getTimeInMillis()方法所取得的时间即是UTC标准时间。


		String UTCTIME = foo.format(new Date(cal.getTimeInMillis()));
//		System.out.println("UTC:"+ UTCTIME);
//
//
//		Calendar calendar1 = Calendar.getInstance();
//
//
//		TimeZone tztz = TimeZone.getTimeZone("GMT");       
//
//
//		calendar1.setTimeZone(tztz);
//
//
//		System.out.println(calendar.getTime());
//
//
//		System.out.println(calendar.getTimeInMillis()); 
		
		return UTCTIME + " UTC";
	}
	
	public static String getCurrenDateUTCstart() {
		

		Calendar gc = GregorianCalendar.getInstance();


//		System.out.println("gc.getTime():"+gc.getTime());


//		System.out.println("gc.getTimeInMillis():"+new Date(gc.getTimeInMillis()));




		//当前系统默认时区的时间：


		Calendar calendar=new GregorianCalendar();
		calendar.set(Calendar.DAY_OF_MONTH, -1);

//		System.out.print("时区："+calendar.getTimeZone().getID()+" ");


//		System.out.println("时间："+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE));


		//美国洛杉矶时区


		TimeZone tz=TimeZone.getTimeZone("America/Los_Angeles");


		//时区转换


		calendar.setTimeZone(tz);


//		System.out.print("时区："+calendar.getTimeZone().getID()+" ");


//		System.out.println("时间："+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE));


		//1、取得本地时间：


		java.util.Calendar cal = java.util.Calendar.getInstance();

		 


		//2、取得时间偏移量：


		int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);

		 


		//3、取得夏令时差：


		int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);

		 


		//4、从本地时间里扣除这些差量，即可以取得UTC时间：


		cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));

		//之后调用cal.get(int x)或cal.getTimeInMillis()方法所取得的时间即是UTC标准时间。


		String UTCTIME = foo.format(new Date(cal.getTimeInMillis()));
//		System.out.println("UTC:"+ UTCTIME);
//
//
//		Calendar calendar1 = Calendar.getInstance();
//
//
//		TimeZone tztz = TimeZone.getTimeZone("GMT");       
//
//
//		calendar1.setTimeZone(tztz);
//
//
//		System.out.println(calendar.getTime());
//
//
//		System.out.println(calendar.getTimeInMillis()); 
		
		return UTCTIME + " UTC";
	}
	
	public static void main(String[] args) {
		String pattern = "yyyy-MM-dd'T'HH:mm:ssZZ";
        System.out.println(DateFormatUtils.format(new Date(), pattern));
	}
}
