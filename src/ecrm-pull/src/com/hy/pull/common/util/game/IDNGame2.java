package com.hy.pull.common.util.game;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.hy.pull.common.util.DateUtil;
import com.hy.pull.common.util.SGSUtil;
import com.hy.pull.common.util.game.av.JsonToMap;
import com.hy.pull.common.util.game.zj.ZJUtil;
import com.hy.pull.service.BaseService.Enum_flag;
import com.hy.pull.service.game.LogUtil;

/**
 * IDN2拉数据
 * 
 * IDN游戏时间是GMT+7，也就是比北京时间晚一个小时
 * 
 * 
 * 创建日期：2016-10-17
 * @author temdy
 */
public class IDNGame2 {
	private static Logger logger = LogManager.getLogger(IDNGame2.class.getName());
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
	private static DefaultHttpClient client;
	private static String sendXMLDataByPost(String url, String xmlData,String agent) throws ClientProtocolException, IOException {
		try {
			Integer statusCode = -1;
			if (client == null) {
				// Create HttpClient Object
				client = new DefaultHttpClient();
			}
			System.out.println("请求URL："+url);
			System.out.println("请求DATA："+xmlData);
			// Send data by post method in HTTP protocol,use HttpPost instead of
			// PostMethod which was occurred in former version
			HttpPost post = new HttpPost(url);
			// Construct a string entity
			StringEntity entity = new StringEntity(xmlData);
			// Set XML entity
			post.setEntity(entity);
			// Set content type of request header
			post.setHeader("Content-Type", "Application/xml;charset=UTF-8");
			// Execute request and get the response
			HttpResponse response = client.execute(post);
			// Response Header - StatusLine - status code
			
			statusCode = response.getStatusLine().getStatusCode();
			String result = EntityUtils.toString(response.getEntity());
			return result;
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_IDN, url+"?"+xmlData, e.getMessage(), agent, Enum_flag.异常.value);
			return null;
		}
	}
	
	/**
	 * 返回多个detail节点
	 * @param xmlStr
	 * @return
	 */
	private static List<Map<String, String>> getQueryParamsList2(String xmlStr) {
		if(xmlStr==null || xmlStr.equals("")) {
			return null;
		}
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlStr); // 将字符串转为XML    
			Element root = doc.getRootElement();
			
			List<Element> list = root.selectNodes("//response/data/detail");
			List<Map<String, String>> listData = new ArrayList<Map<String,String>>();
			
			
			for (Element element : list) {
				Map<String, String> data = new HashMap<String, String>();
				
				Iterator<Element> iterator = element.elementIterator();
				while (iterator.hasNext()) {
					Element element2 = (Element) iterator.next();
					data.put(element2.getName(), element2.getStringValue());
//					System.out.println(element2.getName() +"-"+ element2.getStringValue());
				}
				listData.add(data);
			}
			return listData;
			
		} catch (DocumentException e) {
			logger.error(e);
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取ZJ游戏数据
	 * @param agent 代理
	 * @param result 结果
	 * @return 游戏数据
	 * @throws Exception 抛出异常
	 */
	private static SimpleDateFormat sdf = new SimpleDateFormat("MMM/dd/yyyy HH:mm:ss",Locale.ENGLISH);// 设置日期格式 Aug/13/2015 11:40:30
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
	private static SimpleDateFormat sdf3 = new SimpleDateFormat("MM/dd/yyyy");// 设置日期格式
	private static SimpleDateFormat sdf4 = new SimpleDateFormat("HH:mm");// 设置日期格式
	private static SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 获取游戏结果
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	public static List<Map<String, Object>> getDataList(String API_URL, String secret_key, String starttime) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			//只能抓取15天内的数据
			List list1 = getRecord(API_URL, secret_key, starttime);
			if(list1 == null) {
				return null;
			}
			
			if(list1 != null && list1.size() > 0) {
				list.addAll(list1);
			}
			
			return list;
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_IDN, API_URL, e.getMessage(), secret_key, Enum_flag.异常.value);
			return null;
		}
	}
	
	/**
	 * 获取游戏结果
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	private static List<Map<String, Object>> getRecord(String API_URL, String secret_key, String date) {
		
		try {
			//只能抓取15天内的数据
			Date startDate = sdf2.parse(date);
			
			StringBuilder xmlstr = new StringBuilder();
			xmlstr.append("<request>");
			xmlstr.append("<secret_key>").append(secret_key).append("</secret_key>");
			xmlstr.append("<id>").append("8").append("</id>");//固定值。每个api方法都有id值
			xmlstr.append("<date>").append(sdf3.format(startDate)).append("</date>");//month/date/year 07/30/2015
			xmlstr.append("</request>");
			
			String result = sendXMLDataByPost(API_URL, xmlstr.toString(), secret_key);
//			System.out.println(result);
			if(result == null) {
				return null;
			}
			List<Map<String, String>> temp = getQueryParamsList2(result);
			if(temp == null) {
				return null;
			}
			
			List<Map<String, Object>> list = new ArrayList<>();
			int size = temp.size();
			if(size > 0){
				Map<String,Object> entity = null;
				
				for (Map<String, String> map : temp) {
					
					entity = new HashMap<String,Object>();
					
//					<userid>EGGAWONG23</userid>
//					<total_turnover>411.102</total_turnover>
//					<turnover_poker>0</turnover_poker>
//					<turnover_domino>0</turnover_domino>
//					<turnover_ceme>64.555</turnover_ceme>
//					<turnover_blackjack>0</turnover_blackjack>
//					<turnover_capsa>97.297</turnover_capsa>
//					<turnover_livepoker>249.249</turnover_livepoker>
//					<texaspoker>0</texaspoker>
//					<domino>0</domino>
//					<ceme>-22.659</ceme>
//					<blackjack>0</blackjack>
//					<capsa>-1.459</capsa>
//					<livepoker>4.794</livepoker>
//					<poker_tournament>0</poker_tournament>
//					<agent_commission>8.419</agent_commission>
//					<agent_bill>-10.904</agent_bill>
					
					entity.put("lsh", UUID.randomUUID().toString());
					entity.put("userid", map.get("userid").toLowerCase());
					entity.put("totalturnover", map.get("total_turnover"));
					entity.put("turnoverpoker", map.get("turnover_poker"));
					entity.put("turnoverdomino", map.get("turnover_domino"));
					entity.put("turnoverceme", map.get("turnover_ceme"));
					entity.put("turnoverblackjack", map.get("turnover_blackjack"));
					entity.put("turnovercapsa", map.get("turnover_capsa"));
					entity.put("turnoverlivepoker", map.get("turnover_livepoker"));
					entity.put("texaspoker", map.get("texaspoker"));
					entity.put("domino", map.get("domino"));
					entity.put("ceme", map.get("ceme"));
					entity.put("blackjack", map.get("blackjack"));
					entity.put("capsa", map.get("capsa"));
					entity.put("livepoker", map.get("livepoker"));
					entity.put("pokertournament", map.get("poker_tournament"));
					entity.put("agentcommission", map.get("agent_commission"));
					entity.put("agentbill", map.get("agent_bill"));
					
					entity.put("createtime", new Date());//自定义字段，投注时间
					entity.put("turnoverdate", startDate);//自定义字段，投注时间
					list.add(entity);
					
				}
				
				
			}
			return list;
			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_IDN, API_URL, e.getMessage(), secret_key, Enum_flag.异常.value);
			return null;
		}
	}
	

	
	
	public static void main(String[] arg){
		try {
			String API_URL = "http://devapi.idnpoker.com:2802/";//
			String secret_key = "51e344eb8077d7ac84b8ed715";//
			
			String starttime = "2017-06-25";
			
			List<Map<String, Object>> list = getDataList(API_URL, secret_key, starttime);
			for (Map<String, Object> map : list) {
				System.out.println(map);
			}
			System.out.println("已获取到数据："+list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
