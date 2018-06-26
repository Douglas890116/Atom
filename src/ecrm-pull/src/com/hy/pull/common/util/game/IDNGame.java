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
 * IDN拉数据
 * 
 * IDN游戏时间是GMT+7，也就是比北京时间晚一个小时
 * 
 * 
 * 创建日期：2016-10-17
 * @author temdy
 */
public class IDNGame {
	private static Logger logger = LogManager.getLogger(IDNGame.class.getName());
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
	 * 返回多个row节点
	 * @param xmlStr
	 * @return
	 */
	private static List<Map<String, String>> getQueryParamsList(String xmlStr,String agent) {
		if(xmlStr==null || xmlStr.equals("")) {
			return null;
		}
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlStr); // 将字符串转为XML    
			Element root = doc.getRootElement();
			
			List<Element> list = root.selectNodes("//response/row");
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
			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_IDN, xmlStr, e.getMessage(), agent, Enum_flag.异常.value);
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
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
	private static SimpleDateFormat sdf3 = new SimpleDateFormat("MM/dd/yyyy");// 设置日期格式
	private static SimpleDateFormat sdf4 = new SimpleDateFormat("HH:mm");// 设置日期格式
	private static SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 获取游戏结果
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	public static List<Map<String, Object>> getDataList(String API_URL, String secret_key, String starttime,String endtime) {
		
		try {
			//只能抓取15天内的数据
			Date startDate = sdf2.parse(starttime);
			startDate.setHours(startDate.getHours() - 1);
			Date endDate   = sdf2.parse(endtime);
			endDate.setHours(endDate.getHours() - 1);
			
			if(!sdf3.format(startDate).equals(sdf3.format(endDate))) {//起止时间不能跨日，否则需要拆分出来
				
				String tempStartTime1 = starttime;
				String tempStartTime2 = sdf5.format(startDate) + " 23:59:59";
				
				List list1 = getRecord(API_URL, secret_key, tempStartTime1, tempStartTime2);//第一段
				if(list1 == null) {
					return null;
				}
				System.out.println("第一段："+tempStartTime1+" - "+tempStartTime2);
				String tempEndTime1 = sdf5.format(endDate) + " 00:00:00"; 
				String tempEndTime2 = endtime;
				
				List list2 = getRecord(API_URL, secret_key, tempEndTime1, tempEndTime2);//第二段
				if(list2 == null) {
					return null;
				}
				
				
				System.out.println("第二段："+tempEndTime1+" - "+tempEndTime2);
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				if(list1 != null && list1.size() > 0) {
					list.addAll(list1);
				}
				if(list2 != null && list2.size() > 0) {
					list.addAll(list2);
				}
				
				return list;
				
			} else {
				return getRecord(API_URL, secret_key, starttime, endtime);
			}
			
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
	private static List<Map<String, Object>> getRecord(String API_URL, String secret_key, String starttime,String endtime) {
		
		try {
			//只能抓取15天内的数据
			Date startDate = sdf2.parse(starttime);
			Date endDate   = sdf2.parse(endtime);
			
			StringBuilder xmlstr = new StringBuilder();
			xmlstr.append("<request>");
			xmlstr.append("<secret_key>").append(secret_key).append("</secret_key>");
			xmlstr.append("<id>").append("15").append("</id>");//固定值。每个api方法都有id值
			xmlstr.append("<date>").append(sdf3.format(startDate)).append("</date>");//month/date/year 07/30/2015
			xmlstr.append("<start_time>").append(sdf4.format(startDate)).append("</start_time>");//hour:minute	08:00
			xmlstr.append("<end_time>").append(sdf4.format(endDate)).append("</end_time>");//hour:minute 14:00
			xmlstr.append("</request>");
			
			String result = sendXMLDataByPost(API_URL, xmlstr.toString(), secret_key);
//			System.out.println(result);
			if(result == null) {
				return null;
			}
			List<Map<String, String>> temp = getQueryParamsList(result,secret_key);
			if(temp == null) {
				return null;
			}
			
			List<Map<String, Object>> list = new ArrayList<>();
			int size = temp.size();
			if(size > 0){
				Map<String,Object> entity = null;
				
				for (Map<String, String> map : temp) {
					
					entity = new HashMap<String,Object>();
					
//					<transaction_no>15</transaction_no>
//					<tableno>1003</tableno>
//					<userid>Ipsum</userid>
//					<date>Aug/13/2015 11:40:30</date>
//					<game>LPK</game>
//					<table>Smallest 1</table>
//					<periode>5262</periode>
//					<room>41</room>
//					<bet>15400</bet>
//					<curr_bet>15</curr_bet>
//					<status>win</status>
//					<card>H7 S9 C4 H6 S1 D3 C9</card>
//					<hand>One Pair</hand>
//					<prize>One Pair</prize>
//					<curr>IDR</curr>
//					<curr_player>RMB</curr_player>
//					<curr_amount>5000</curr_amount>
//					<amount>5000</amount>
//					<total>100000</total>
//					<agent_comission>97.11</agent_comission>
//					<agent_bill>6285.11</agent_bill>
					
					if(map.get("status").equals("Deposit") || map.get("status").equals("Withdraw")) {//转账记录，跳过
						continue;
					}
					entity.put("transactionno", map.get("transaction_no"));
					entity.put("tableno", map.get("tableno"));
					entity.put("userid", map.get("userid"));
					entity.put("betdate", map.get("date"));//Aug/13/2015 11:40:30
					entity.put("game", map.get("game"));
					entity.put("bettable", map.get("table"));
					entity.put("periode", map.get("periode"));
					entity.put("room", map.get("room"));
					entity.put("bet", map.get("bet"));
					entity.put("currbet", map.get("curr_bet"));
					entity.put("rbet", map.get("r_bet"));
					
//					status of player
//					Texas Poker = Win, Lose, Fold, Draw, Win Global Jackpot, Buy Jackpot, Gift, Deposit, Withdraw
//					Black Jack = Win, Lose
//					Domino = Win, Lose, Fold, Draw, Win Global Jackpot, Buy Jackpot
//					Ceme = Win, Lose, Buy Jackpot, Win Global Jackpot
//					Capsa Susun = Win, Lose
//					Live Poker = Win, Lose, Fold, Draw, Win Global Jackpot, Buy Jackpot, Gift
					
					entity.put("status", map.get("status"));
					entity.put("card", map.get("card"));
					entity.put("hand", map.get("hand"));
					entity.put("prize", map.get("prize"));
					entity.put("curr", map.get("curr"));
					entity.put("currplayer", map.get("curr_player"));
					entity.put("curramount", map.get("curr_amount"));
					entity.put("amount", map.get("amount"));
					entity.put("total", map.get("total"));
					entity.put("agentcomission", map.get("agent_comission"));
					entity.put("agentbill", map.get("agent_bill"));
					
					Date bettime = sdf.parse(entity.get("betdate").toString());
					
					entity.put("createtime", new Date());//自定义字段，投注时间
					entity.put("betmoney", entity.get("curramount"));//自定义字段，投注额
					entity.put("netmoney", map.get("status").equals("Win") ? entity.get("curramount") : "-"+entity.get("curramount"));//自定义字段，输赢金额
					entity.put("bettime", DateUtil.add(bettime, Calendar.HOUR, -1));//自定义字段，投注时间
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
			
			String starttime = "2017-03-06 10:00:00";
			String endtime = "2017-03-06 22:00:00";
			
			List<Map<String, Object>> list = getDataList(API_URL, secret_key, starttime, endtime);
			for (Map<String, Object> map : list) {
				System.out.println(map);
			}
			System.out.println("已获取到数据："+list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
