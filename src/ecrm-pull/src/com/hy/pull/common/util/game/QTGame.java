package com.hy.pull.common.util.game;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.hy.pull.common.util.Enum_MSG;
import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.common.util.MoneyHelper;
import com.hy.pull.service.BaseService.Enum_flag;
import com.hy.pull.service.game.LogUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 拉取QT游戏数据工具类
 * 创建日期：2016-10-10
 * @author temdy
 */
public class QTGame {
	private static Logger logger = LogManager.getLogger(QTGame.class.getName());
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
	
	/**
	 * 拉取东方游戏数据列表（接口每次最多返回100条记录）
	 * @param apiUrl 接口链接
	 * @param agent 代理
	 * @param vendorid 最大vendorid
	 * @param userKey MD5密钥
	 * @param code 代理编码
	 * @return 游戏数据列表
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> getQTData(String API_URL, String agentname, String agentpwd,String fromDateTime,String toDateTime) {
		List<Map<String, Object>> list = new ArrayList<>();
		try {
			
//			String fromDateTime = "2017-04-10T00:00:00";//YYYY-MM-DDThh:mm:ss
//			String toDateTime = "2017-04-12T19:00:00";//YYYY-MM-DDThh:mm:ss
			
			fromDateTime = fromDateTime.replaceAll(" ", "T");
			toDateTime = toDateTime.replaceAll(" ", "T");
			
			
			int pageSize = 100;//最大不得超过100
			int indexPage = 0;
			int totalPage = 0;
			
			fromDateTime = fromDateTime.replaceAll(" ", "T");
			toDateTime = toDateTime.replaceAll(" ", "T");
			
			Map<String, Object> returnObject = getDataByPage(API_URL, agentname, agentpwd, fromDateTime, toDateTime, pageSize, indexPage);
			if(returnObject == null) {
				return null;
			}
			
			list.addAll((List)returnObject.get("list"));
			
			if(returnObject.containsKey("totalPage")) {
				totalPage = Integer.valueOf(returnObject.get("totalPage").toString());
			} else {
				LogUtil.addListLog(LogUtil.HANDLE_QT, API_URL, returnObject.toString(), agentname, Enum_flag.异常.value);
			}
			
			if(totalPage > 0) {
				//页数循环
				for (int i = 1; i < totalPage; i++) {
					indexPage ++;
					System.out.println("当前抓取第"+indexPage+"页：");
					returnObject = getDataByPage(API_URL, agentname, agentpwd, fromDateTime, toDateTime, pageSize, indexPage);
					if(returnObject == null) {
						return null;
					}
					
					list.addAll((List)returnObject.get("list"));
				}
			}
			
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_QT, API_URL, ex.getMessage(), agentname, Enum_flag.异常.value);
			return null;
		}
		return list;
	}
	
	private static Map<String, Object> getDataByPage(String API_URL, String agentname, String agentpwd,String fromDateTime,String toDateTime, int pageSize, int indexPage) {
		Map<String, Object> returnObject = new HashMap<>();
		List<Map<String, Object>> list = new ArrayList<>();
		try {
			
			int totalPage = 0;
			
			String url = API_URL.concat("/v1/game-rounds?").concat("from=").concat(fromDateTime).concat("&to=").concat(toDateTime).concat("&size=").concat(pageSize+"").concat("&page=").concat(indexPage+"").concat("&status=COMPLETED");
			String result = getUrl(url,  API_URL,  agentname,  agentpwd );
			
//			System.out.println("调用结果："+result);
			if(result == null) {
				return null;
			}
			
			JSONObject object = JSONObject.fromObject(result);
			
			//接口调用成功
			if( !object.has("code") ) {
				
				int totalCount = object.getInt("totalCount");
				System.out.println("QTech找到数据："+totalCount+"条");
				
				//计算总页数
				if(totalCount > pageSize) {
					if(totalCount % pageSize != 0) {
						totalPage = totalCount / pageSize + 1;
					} else {
						totalPage = totalCount / pageSize ;
					}
				}
				returnObject.put("totalPage", totalPage);
				returnObject.put("totalCount", totalCount);
				
				if(totalCount > 0 ) {
					JSONArray list2 = object.getJSONArray("items");
					for (int i = 0; i < list2.size(); i++) {
						JSONObject item = list2.getJSONObject(i);

						
						HashMap<String, Object> entity = new HashMap<String, Object>();
						entity.put("gameid", item.getString("gameId"));
						entity.put("amount", item.getString("totalBet"));//局的总投注
						entity.put("created", item.getString("initiated"));//2017-04-12T13:10:12.068+08:00[Asia/Shanghai]
						entity.put("gameprovider", item.getString("gameProvider"));
						entity.put("gtype", "PAYOUT");//例如BET,  PAYOUT,  ROLLBACK
						entity.put("gameclienttype", item.getString("gameClientType"));
						entity.put("gamecategory", item.getString("gameCategory"));
						entity.put("balance", "0");
						entity.put("currency", item.getString("currency"));
						entity.put("playerdevice", item.getString("device"));
						entity.put("id", item.getString("id"));
						entity.put("operatorid", item.getString("operatorId"));
						entity.put("playergameroundid", item.getString("id"));
						entity.put("wallettransactionid", item.getString("id"));
						entity.put("playerid", item.getString("playerId"));
						entity.put("roundstatus", item.getString("status"));//Valid values are: PENDING,  FAILED and COMPLETED 
						
						entity.put("createtime", sdf.format(new Date()));
						entity.put("bettime", sdf.parse(entity.get("created").toString().split("\\.")[0].replaceAll("T", " ")));
						
						entity.put("betmoney", item.getDouble("totalBet"));
						entity.put("netmoney", MoneyHelper.doubleFormat(item.getDouble("totalPayout") - item.getDouble("totalBet")));
						
						list.add(entity);
						
					}
				} 
			} else {
				logger.error(result);
				LogUtil.addListLog(LogUtil.HANDLE_QT, API_URL, result, agentname, Enum_flag.异常.value);
			}
			
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_QT, API_URL, ex.getMessage(), agentname, Enum_flag.异常.value);
			return null;
		}
		returnObject.put("list", list);
		return returnObject;
	}
	
	private static String getAccessToken(String API_URL, String agentname, String agentpwd) {
		String urls = API_URL.concat("/v1/auth/token?grant_type=password&response_type=token&username=").concat(agentname).concat("&password=").concat(agentpwd);
		HttpClient httpClients =  HttpClientBuilder.create().build();
		
		HttpPost request = new HttpPost(urls);
	    HttpResponse response=null;
	 
		try {
			
			response = httpClients.execute(request);
			
			String result = EntityUtils.toString(response.getEntity());
			JSONObject jsonObject = JSONObject.fromObject(result);
			if(jsonObject.has("access_token")) {
				System.out.println("成功获取到TOKEN="+jsonObject.get("access_token"));
				return com.hy.pull.common.util.Enum_MSG.成功.message(jsonObject.get("access_token"));
			} else {
//				return com.hy.pull.common.util.Enum_MSG.失败.message(jsonObject.get("code"));
				LogUtil.addListLog(LogUtil.HANDLE_QT, API_URL, result, agentname, Enum_flag.异常.value);
				logger.error(result);
				return null;
			}
			 
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_QT, API_URL, e.getMessage(), agentname, Enum_flag.异常.value);
			return null;
		}finally{
			httpClients.getConnectionManager().shutdown();
		}
		
	}
	
	private static String getUrl(String url, String API_URL, String agentname, String agentpwd) {
		//
		HttpClient httpClients =  HttpClientBuilder.create().build();
		
		HttpGet request = new HttpGet(url);
	    HttpResponse response=null;
	 
		try {
			
			String strs = getAccessToken( API_URL,  agentname,  agentpwd) ;
			if(strs == null) {
				return null;
			}
			
			JSONObject token = JSONObject.fromObject(strs);
			String accesstoken = "";
			if(token.getString("code").equals("0")) {
				accesstoken = token.getString("info");
			} else {
//				return token.toString();
				return null;
			}
			
			request.addHeader("Authorization", "Bearer "+accesstoken);
			request.addHeader("Time-Zone", "Asia/Shanghai");
			
			/****调试信息***/
//			System.out.println("接口URL："+url);
//			Header[] headers = request.getAllHeaders();
//			System.out.println("请求Header数据：");
//			for (Header header : headers) {
//				System.out.println(header.getName()+"="+header.getValue());
//			}
//			System.out.println("请求方式："+request.getMethod());
			
			response = httpClients.execute(request);
			
			String result = EntityUtils.toString(response.getEntity());
			JSONObject resultObject = JSONObject.fromObject( result );
			
			if(resultObject.has("code")) {
				return Enum_MSG.失败.message(resultObject.getString("code"), resultObject.getString("message"));
			} else {
				logger.error(result);
				LogUtil.addListLog(LogUtil.HANDLE_QT, url, result, agentname, Enum_flag.异常.value);
			}
			
			return result;
			 
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_QT, url, e.getMessage(), agentname, Enum_flag.异常.value);
			return null;
		}finally{
			httpClients.getConnectionManager().shutdown();
		}
	}
	
	public static void main(String[] arg){
		String ss = "2017-04-12T13:10:12.068+08:00[Asia/Shanghai]";
		try {
			System.out.println(sdf.parse(ss.split("\\.")[0].replaceAll("T", " ")).toLocaleString());
			
			System.out.println("2017-04-12 13:10:12".replaceAll(" ", "T"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
