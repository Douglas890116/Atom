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
import java.util.Map.Entry;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
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
 * M88拉数据
 * 
 * 注意：返回的数据时间跟北京时间小12小时
 * 
 * 创建日期：2016-10-17
 * @author temdy
 */
public class M88Game {
	private static Logger logger = LogManager.getLogger(M88Game.class.getName());
	private static SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy h:m:s aa",Locale.ENGLISH);// 设置日期格式 3/16/2017 5:19:50 PM
	
	/**
	 * 获取游戏结果
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	public static List<Map<String, Object>> getRecord(String API_URL, String operator_id, String starttime,String endtime) {

		List<Map<String, Object>> list = new ArrayList<>();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("operator_id", operator_id);
		params.put("date_start", starttime);
		params.put("date_end", endtime);
		
		String result = doPostSubmitMap(API_URL + "TransactionMemberDetail.API?", params, operator_id);
		System.out.println("调用结果："+result);
		if(result == null) {
			return null;
		}
		
//		System.out.println("调用结果："+result);
		Map<String, String> object = getQueryParams(result,"transactionmember_detail", operator_id);
		
		if(object == null) {
			return null;
		}
		
		if( object.get("status_code").equals("0") ) {
			
			try {

				List<Map<String, String>> temp = getQueryParamsList(result, operator_id);
				
				int size = temp.size();
				if(size > 0){
					Map<String,Object> entity = null;
					
					for (Map<String, String> map : temp) {
						
						entity = new HashMap<String,Object>();
						
						
						/********
						<trans_id>276011438</trans_id>
						<member_id>6sBMCuD9Yew5IDbsG</member_id>
						<operator_id>D28C7DA7-5E73-4197-9D5A-311DC60023E2</operator_id>
						<site_code>HBW</site_code>
						<league_id>10000004</league_id>
						<home_id>10000280</home_id>
						<away_id>10000277</away_id>
						<match_datetime>3/13/2017 4:00:00 PM</match_datetime>
						<bet_type>8</bet_type>
						<parlay_ref_no>0</parlay_ref_no>
						<odds>3.0600</odds>
						<currency>RMB</currency>
						<stake>5.0000</stake>
						<winlost_amount>-5.00</winlost_amount>
						<transaction_time>3/15/2017 4:37:45 PM</transaction_time>
						<ticket_status>processed</ticket_status>
						<version_key>284827290</version_key>
						<winlost_datetime>3/13/2017 12:00:00 AM</winlost_datetime>
						<odds_type>33</odds_type>
						<sports_type>10</sports_type>
						<bet_team>11</bet_team>
						<home_hdp>0.0000</home_hdp>
						<away_hdp>0.0000</away_hdp>
						<match_id>2925690</match_id>
						<is_live>1</is_live>
						<home_score>0</home_score>
						<away_score>0</away_score>
						<choicecode>11</choicecode>
						<choicename>Half-time 1x2 Home</choicename>
						<txn_type>INT</txn_type>
						<last_update>3/16/2017 1:55:52 PM</last_update>
						<leaguename>ITALY CUP</leaguename>
						<homename>Cagliari</homename>
						<awayname>Atalanta</awayname>
						<sportname>Soccer</sportname>
						<oddsname>EU</oddsname>
						<bettypename>First Half 1x2</bettypename>
						<winlost_status>L</winlost_status>
						********/
						
						entity.put("transid", map.get("trans_id"));
						entity.put("memberid", map.get("member_id"));
						entity.put("operatorid", map.get("operator_id"));
						entity.put("sitecode", map.get("site_code"));
						entity.put("leagueid", map.get("league_id"));
						entity.put("homeid", map.get("home_id"));
						entity.put("awayid", map.get("away_id"));
						entity.put("matchdatetime", map.get("match_datetime"));
						entity.put("bettype", map.get("bet_type"));
						entity.put("parlayrefno", map.get("parlay_ref_no"));
						entity.put("odds", map.get("odds"));
						entity.put("currency", map.get("currency"));
						entity.put("stake", map.get("stake"));
						entity.put("winlostamount", map.get("winlost_amount"));
						entity.put("transactiontime", map.get("transaction_time"));
						entity.put("ticketstatus", map.get("ticket_status"));
						entity.put("versionkey", map.get("version_key"));
						entity.put("winlostdatetime", map.get("winlost_datetime"));
						entity.put("oddstype", map.get("odds_type"));
						entity.put("sportstype", map.get("sports_type"));
						entity.put("betteam", map.get("bet_team"));
						entity.put("homehdp", map.get("home_hdp"));
						entity.put("awayhdp", map.get("away_hdp"));
						entity.put("matchid", map.get("match_id"));
						entity.put("islive", map.get("is_live"));
						entity.put("homescore", map.get("home_score"));
						entity.put("awayscore", map.get("away_score"));
						entity.put("choicecode", map.get("choicecode"));
						entity.put("choicename", map.get("choicename"));
						entity.put("txntype", map.get("txn_type"));
						entity.put("lastupdate", map.get("last_update"));
						entity.put("leaguename", map.get("leaguename"));
						entity.put("homename", map.get("homename"));
						entity.put("awayname", map.get("awayname"));
						entity.put("sportname", map.get("sportname"));
						entity.put("oddsname", map.get("oddsname"));
						entity.put("bettypename", map.get("bettypename"));
						entity.put("winloststatus", map.get("winlost_status"));
						
//						Date bettime = sdf.parse(entity.get("transactiontime").toString());//3/15/2017 4:37:45 PM
						Date nettime = sdf.parse(entity.get("lastupdate").toString());//3/16/2017 1:55:52 PM（这个应该设置为投注时间）
						
						entity.put("createtime", new Date());//自定义字段，投注时间
						entity.put("betmoney", entity.get("stake"));//自定义字段，投注额
						entity.put("netmoney", entity.get("winlostamount"));//自定义字段，输赢金额，正负数
						entity.put("bettime", nettime);//自定义字段，投注时间
						entity.put("nettime", nettime);//自定义字段，结算时间
//						System.out.println("entity="+entity);
						list.add(entity);
						
						
					}
					
					
				}
				return list;
				
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
				LogUtil.addListLog(LogUtil.HANDLE_M88, API_URL, e.getMessage(), operator_id, Enum_flag.异常.value);
				return null;
			}
		} else if( object.get("status_code").equals("16") ) {
			//没有找到记录
			return list;
		}
//		System.out.println("M88数据同步出现异常："+object);
		logger.error("M88数据同步出现异常："+object);
		LogUtil.addListLog(LogUtil.HANDLE_M88, API_URL, "M88数据同步出现异常："+object, operator_id, Enum_flag.异常.value);
		return null;
	}
	
	private static String doPostSubmitMap(String httpUrl,Map<String, String> params,String agent) {
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
    		System.out.println("请求URL："+httpUrl);
    		System.out.println("请求params："+params);
        	UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(paramsxx,HTTP.UTF_8);
    		httpRequst.setEntity(encodedFormEntity);
    		
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);  
            if(httpResponse.getStatusLine().getStatusCode() == 200)  {
            	String strResult = EntityUtils.toString(httpResponse.getEntity());
            	return strResult;
            } else {
//            	return httpResponse.getStatusLine().getStatusCode()+"";
            	logger.error(httpResponse.getStatusLine().getStatusCode()+"");
            	LogUtil.addListLog(LogUtil.HANDLE_M88, httpUrl+"?"+params.toString(), httpResponse.getStatusLine().getStatusCode()+"", agent, Enum_flag.异常.value);
            	return null;
            }
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_M88, httpUrl+"?"+params.toString(), e.getMessage(), agent, Enum_flag.异常.value);
			return null;
		}
	}
	

	/**
	 * 返回投注记录
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
			
			List<Element> list = root.selectNodes("//transactionmember_detail/bets/row");
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
			LogUtil.addListLog(LogUtil.HANDLE_M88, xmlStr, e.getMessage(), agent, Enum_flag.异常.value);
			return null;
		}
	}
	/**
	 * 返回单个节点
	 * @param xmlStr
	 * @return
	 */
	private static Map<String, String> getQueryParams(String xmlStr,String rootcode,String agent) {
		if(xmlStr==null || xmlStr.equals("")) {
			return null;
		}
		Document doc = null;
		Map<String, String> data = new HashMap<String, String>();
		try {
			doc = DocumentHelper.parseText(xmlStr); // 将字符串转为XML    
			Element root = doc.getRootElement();
			List<Element> list2 = root.selectNodes("//"+rootcode);
			
			for (Element element : list2) {
				List<Element> list = element.elements();
				for (Element element2 : list) {
					data.put(element2.getName(), element2.getStringValue());
					//System.out.println(element2.getName() +"-"+ element2.getStringValue());
				}
			}
			return data;
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_M88, xmlStr, e.getMessage(), agent, Enum_flag.异常.value);
			return null;
		}
	}

	
	
	public static void main(String[] arg){
		try {
//			/**/
			String SB_API_URL = "http://api.coolres.com/";
			String SB_LOGIN_URL = "http://sport.77scm.com";
			String SB_operator_id = "D28C7DA7-5E73-4197-9D5A-311DC60023E2";
			
			String startDate = 	"2017-11-07 20:10:00";
			String endDate = 	"2017-11-07 20:30:00";
			
			List<Map<String, Object>> list = getRecord(SB_API_URL, SB_operator_id, startDate, endDate);
			for (Map<String, Object> map : list) {
				System.out.println(map);
			}
			System.out.println("已获取到数据："+list.size());
//			
			
			System.out.println(sdf.parse("11/7/2017 8:14:27 PM"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
