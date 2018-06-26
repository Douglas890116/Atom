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
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.hy.pull.common.util.Encrypt;
import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.common.util.game.sa.DeEnCode;
import com.hy.pull.service.BaseService.Enum_flag;
import com.hy.pull.service.game.LogUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 新沙巴体育游戏数据工具类
 * 创建日期：2016-10-10
 * @author temdy
 */
public class EIBCGame {
	
	private static Logger logger = LogManager.getLogger(EIBCGame.class.getName());
	
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static String getSecurityToken(String method,String params,String PrivateKey) {
		try{
			
//			System.out.println("params原文："+params);
//			System.out.println("生成SecurityToken原文："+(PrivateKey + "/api/"+method+"?" + params));
			String result = Encrypt.MD5(PrivateKey + "/api/"+method+"?" + params).toUpperCase();
//			System.out.println("生成SecurityToken结果："+result);
			
			return result;
			
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error(ex);
		}
		return null;
	}
	private static String getUrl(String url,String agent) {
		//
		HttpClient httpClients =  HttpClientBuilder.create().build();
		
		HttpGet request = new HttpGet(url);
	    HttpResponse response=null;
	 
		try {
			System.out.println("IBC沙巴体育请求URL："+url);
			response = httpClients.execute(request);
			
			String result = EntityUtils.toString(response.getEntity());
//			System.out.println("result="+result);
			JSONObject resultObject = JSONObject.fromObject( result );
			
			if(resultObject.has("code")) {
				if(resultObject.getString("code").equals("SUCCESS")) {
					return result;
				}
				logger.error(resultObject);
				LogUtil.addListLog(LogUtil.HANDLE_EIBC, url, resultObject.toString(), agent, Enum_flag.异常.value);
			}
			 
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
			LogUtil.addListLog(LogUtil.HANDLE_EIBC, url, e.getMessage(), agent, Enum_flag.异常.value);
			return null;
		}finally{
			httpClients.getConnectionManager().shutdown();
		}
	}
	
	/**
	 * 获取游戏结果
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	public static String pattern = "yyyy-MM-dd'T'HH:mm:ss";
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
	public static List<Map<String, Object>> getRecord(String API_URL,String starttime,String endtime, String PrivateKey,String OpCode) {

		List<Map<String, Object>> list = new ArrayList<>();
		
		Calendar calendar = Calendar.getInstance();
		
		//这里面的时间要转化为ISO8601格式
		try {
			//API 方法的时区是GMT-4
			Date date1 = sdf.parse(starttime);
			calendar.setTime(date1);
			calendar.add(Calendar.HOUR_OF_DAY, -12);
			starttime = DateFormatUtils.format(calendar.getTime(), pattern);
			
			//API 方法的时区是GMT-4
			Date date2 = sdf.parse(endtime);
			calendar.setTime(date2);
			calendar.add(Calendar.HOUR_OF_DAY, -12);
			endtime = DateFormatUtils.format(calendar.getTime(), pattern);
			
			//URL编码
			starttime = URLEncoder.encode(starttime,"UTF-8").toLowerCase();
			endtime = URLEncoder.encode(endtime,"UTF-8").toLowerCase();
			
			starttime = starttime.replaceAll("t", "T");
			endtime = endtime.replaceAll("t", "T");
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getStackTrace());
		}
		
		StringBuffer params = new StringBuffer();
		params.append("OpCode=").append(OpCode).append("&");
//		params.append("LastVersionKey=").append(vendorid).append("");//最大值
		params.append("StartTime=").append(starttime).append("&");
		params.append("EndTime=").append(endtime).append("");
		
		
		String SecurityToken = getSecurityToken("GetSportBettingDetail", params.toString(), PrivateKey);
		params.append("&SecurityToken=").append(SecurityToken).append("");
		
		String result = getUrl(API_URL.concat("GetSportBettingDetail?").concat(params.toString()) ,OpCode);
//		System.out.println("调用结果："+result);
//		logger.warn("调用结果："+result);
		if(result == null) {
			return null;
		}
		
		JSONObject object = JSONObject.fromObject(result);
		
//		{"LastVersionKey":11012527,"TotalRecord":8,"Data":[{"TransId":102054669407,"PlayerName":"hyfdfh2230","TransactionTime":"2017-05-12T02:06:19.92","MatchId":22314465,"LeagueId":8970,"LeagueName":"CHINA 2ND DIVISION","SportType":1,"AwayId":391643,"AwayIDName":"Shanghai Sunfun FC","HomeId":391651,"HomeIDName":"Zhenjiang Huasa FC","MatchDatetime":"2017-05-12T02:59:00","BetType":2,"ParlayRefNo":0,"BetTeam":"h","HDP":0.0000,"AwayHDP":0.0000,"HomeHDP":0.0000,"Odds":1.9400,"OddsType":3,"AwayScore":null,"HomeScore":null,"IsLive":"0","LastBallNo":null,"TicketStatus":"LOSE","Stake":12.0000,"WinLoseAmount":-12.0000,"WinLostDateTime":"2017-05-12T00:00:00","currency":"RMB","VersionKey":10996969},{"TransId":102054670915,"PlayerName":"hyfdfh2230","TransactionTime":"2017-05-12T02:06:30.683","MatchId":22314432,"LeagueId":11779,"LeagueName":"AUSTRALIA BRISBANE PREMIER LEAGUE RESERVE","SportType":1,"AwayId":289617,"AwayIDName":"Lions FC (R)","HomeId":292464,"HomeIDName":"Logan Lightning (R)","MatchDatetime":"2017-05-12T04:29:00","BetType":1,"ParlayRefNo":0,"BetTeam":"h","HDP":1.2500,"AwayHDP":1.2500,"HomeHDP":0.0000,"Odds":1.9000,"OddsType":3,"AwayScore":null,"HomeScore":null,"IsLive":"0","LastBallNo":null,"TicketStatus":"LOSE","Stake":10.0000,"WinLoseAmount":-10.0000,"WinLostDateTime":"2017-05-12T00:00:00","currency":"RMB","VersionKey":10998237},{"TransId":102054672652,"PlayerName":"hyfdfh2230","TransactionTime":"2017-05-12T02:06:40.633","MatchId":22314432,"LeagueId":11779,"LeagueName":"AUSTRALIA BRISBANE PREMIER LEAGUE RESERVE","SportType":1,"AwayId":289617,"AwayIDName":"Lions FC (R)","HomeId":292464,"HomeIDName":"Logan Lightning (R)","MatchDatetime":"2017-05-12T04:29:00","BetType":1,"ParlayRefNo":0,"BetTeam":"h","HDP":1.2500,"AwayHDP":1.2500,"HomeHDP":0.0000,"Odds":1.9000,"OddsType":3,"AwayScore":null,"HomeScore":null,"IsLive":"0","LastBallNo":null,"TicketStatus":"LOSE","Stake":10.0000,"WinLoseAmount":-10.0000,"WinLostDateTime":"2017-05-12T00:00:00","currency":"RMB","VersionKey":10998238},{"TransId":102054673040,"PlayerName":"hyfdfh2230","TransactionTime":"2017-05-12T02:06:48.787","MatchId":22314484,"LeagueId":8740,"LeagueName":"KOREA WOMEN K-LEAGUE","SportType":1,"AwayId":354177,"AwayIDName":"Boeun Sangmu (W)","HomeId":294198,"HomeIDName":"Icheon Daekyo (W)","MatchDatetime":"2017-05-12T05:59:00","BetType":1,"ParlayRefNo":0,"BetTeam":"h","HDP":-1.7500,"AwayHDP":0.0000,"HomeHDP":1.7500,"Odds":1.8600,"OddsType":3,"AwayScore":null,"HomeScore":null,"IsLive":"0","LastBallNo":null,"TicketStatus":"LOSE","Stake":10.0000,"WinLoseAmount":-10.0000,"WinLostDateTime":"2017-05-12T00:00:00","currency":"RMB","VersionKey":10999874},{"TransId":102054659612,"PlayerName":"hyfdfh2230","TransactionTime":"2017-05-12T02:05:09.973","MatchId":22312012,"LeagueId":11711,"LeagueName":"AUSTRALIA VICTORIA PREMIER LEAGUE","SportType":1,"AwayId":12408,"AwayIDName":"Green Gully Cavaliers","HomeId":41090,"HomeIDName":"Pascoe Vale SC","MatchDatetime":"2017-05-12T06:14:00","BetType":5,"ParlayRefNo":0,"BetTeam":"1","HDP":0.0000,"AwayHDP":1.0000,"HomeHDP":1.1000,"Odds":3.0500,"OddsType":3,"AwayScore":null,"HomeScore":null,"IsLive":"0","LastBallNo":null,"TicketStatus":"LOSE","Stake":10.0000,"WinLoseAmount":-10.0000,"WinLostDateTime":"2017-05-12T00:00:00","currency":"RMB","VersionKey":11000281},{"TransId":102054662401,"PlayerName":"hyfdfh2230","TransactionTime":"2017-05-12T02:05:28.123","MatchId":22312012,"LeagueId":11711,"LeagueName":"AUSTRALIA VICTORIA PREMIER LEAGUE","SportType":1,"AwayId":12408,"AwayIDName":"Green Gully Cavaliers","HomeId":41090,"HomeIDName":"Pascoe Vale SC","MatchDatetime":"2017-05-12T06:14:00","BetType":5,"ParlayRefNo":0,"BetTeam":"1","HDP":0.0000,"AwayHDP":1.0000,"HomeHDP":1.1000,"Odds":3.0500,"OddsType":3,"AwayScore":null,"HomeScore":null,"IsLive":"0","LastBallNo":null,"TicketStatus":"LOSE","Stake":10.0000,"WinLoseAmount":-10.0000,"WinLostDateTime":"2017-05-12T00:00:00","currency":"RMB","VersionKey":11000282},{"TransId":102054667401,"PlayerName":"hyfdfh2230","TransactionTime":"2017-05-12T02:06:03.877","MatchId":22304824,"LeagueId":247,"LeagueName":"CHINA FOOTBALL SUPER LEAGUE","SportType":1,"AwayId":293491,"AwayIDName":"Hebei China Fortune","HomeId":154969,"HomeIDName":"Guangzhou R&F","MatchDatetime":"2017-05-12T07:34:00","BetType":3,"ParlayRefNo":0,"BetTeam":"h","HDP":2.7500,"AwayHDP":0.0000,"HomeHDP":2.7500,"Odds":1.7800,"OddsType":3,"AwayScore":null,"HomeScore":null,"IsLive":"0","LastBallNo":null,"TicketStatus":"LOSE","Stake":10.0000,"WinLoseAmount":-10.0000,"WinLostDateTime":"2017-05-12T00:00:00","currency":"RMB","VersionKey":11001851},{"TransId":102054673691,"PlayerName":"hyfdfh2230","TransactionTime":"2017-05-12T02:06:56.547","MatchId":22304429,"LeagueId":45742,"LeagueName":"*ENGLISH PREMIER LEAGUE - CORNERS","SportType":1,"AwayId":32890,"AwayIDName":"Watford No.of Corners","HomeId":28193,"HomeIDName":"Everton No.of Corners","MatchDatetime":"2017-05-12T14:44:00","BetType":5,"ParlayRefNo":0,"BetTeam":"1","HDP":0.0000,"AwayHDP":1.0000,"HomeHDP":1.1200,"Odds":1.2900,"OddsType":3,"AwayScore":null,"HomeScore":null,"IsLive":"0","LastBallNo":null,"TicketStatus":"WON","Stake":10.0000,"WinLoseAmount":2.9000,"WinLostDateTime":"2017-05-12T00:00:00","currency":"RMB","VersionKey":11012527}],"error_code":0,"message":"Successfully executed"}
		
		//接口调用成功
		if( object.getString("error_code").equals("0")) {
			
			try {
				
				if(object.has("Data")) {
//					System.out.println("data="+object.get("Data"));
					
					JSONArray array = object.getJSONArray("Data");
					
					Map<String, Object> data = null;
					for (int i = 0; i < array.size(); i++) {
						JSONObject jsonObject = array.getJSONObject(i);
						
//						API 方法的时区是GMT-4
						
						data = new HashMap<String, Object>();
						data.put("transid", jsonObject.getString("TransId"));
						data.put("playername", jsonObject.getString("PlayerName"));
						data.put("transactiontime", jsonObject.getString("TransactionTime"));//投注时间 2017-05-12T04:29:00
						data.put("matchid", jsonObject.getString("MatchId"));
						data.put("leagueid", jsonObject.getString("LeagueId"));
						data.put("leaguename", jsonObject.getString("LeagueName"));
						data.put("sporttype", jsonObject.getString("SportType"));
						data.put("awayid", jsonObject.getString("AwayId"));
						data.put("awayidname", jsonObject.getString("AwayIDName"));
						data.put("homeid", jsonObject.getString("HomeId"));
						data.put("homeidname", jsonObject.getString("HomeIDName"));
						data.put("matchdatetime", jsonObject.getString("MatchDatetime"));
						data.put("bettype", jsonObject.getString("BetType"));
						data.put("parlayrefno", jsonObject.getString("ParlayRefNo"));
						data.put("betteam", jsonObject.getString("BetTeam"));
						data.put("hdp", jsonObject.getString("HDP"));
						data.put("awayhdp", jsonObject.getString("AwayHDP"));
						data.put("homehdp", jsonObject.getString("HomeHDP"));
						data.put("odds", jsonObject.getString("Odds"));
						data.put("oddstype", jsonObject.getString("OddsType"));
						data.put("awayscore", jsonObject.getString("AwayScore"));
						data.put("homescore", jsonObject.getString("HomeScore"));
						data.put("islive", jsonObject.getString("IsLive"));
						data.put("lastballno", jsonObject.getString("LastBallNo"));
						data.put("ticketstatus", jsonObject.getString("TicketStatus").toLowerCase());
						data.put("stake", jsonObject.getString("Stake"));
						data.put("winloseamount", jsonObject.getString("WinLoseAmount"));
						data.put("winlostdatetime", jsonObject.getString("WinLostDateTime"));//结算时间 2017-05-12T04:29:00
						data.put("currency", jsonObject.getString("currency"));
						data.put("versionkey", OpCode );//jsonObject.optString("VersionKey") // 此字段暂时无数据，不使用
						
						Date bettime = sdf2.parse(data.get("transactiontime").toString().substring(0,19).replaceAll("T", " "));
						Date nettime = sdf2.parse(data.get("winlostdatetime").toString().substring(0,19).replaceAll("T", " "));
						calendar.setTime(bettime);
						calendar.add(Calendar.HOUR_OF_DAY, +12);
						bettime = calendar.getTime();
						
						calendar.setTime(nettime);
						calendar.add(Calendar.HOUR_OF_DAY, +12);
						nettime = calendar.getTime();
						
//						System.out.println("bettime="+data.get("transactiontime")+"="+bettime.toLocaleString());
//						System.out.println("nettime="+data.get("winlostdatetime")+"="+nettime.toLocaleString());
//						System.out.println(data);
						
						data.put("createtime", new Date());
						data.put("bettime", bettime);
						data.put("nettime", bettime);//nettime字段不准确
						
						
						//重要：如果状态为等待和进行中的，金额先标志为0，避免参与系统洗码结算
						//waiting
						//running
						String ticketstatus = data.get("ticketstatus").toString();
						if(ticketstatus.equals("waiting") || ticketstatus.equals("running")) {
							data.put("betmoney", 0);
							data.put("netmoney", 0);
						} else {
							data.put("betmoney", data.get("stake"));
							data.put("netmoney", data.get("winloseamount"));
						}
						
						list.add(data);
					}
					
					return list;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
				LogUtil.addListLog(LogUtil.HANDLE_EIBC, API_URL.concat("GetSportBetLog?").concat(params.toString()), e.getMessage(), OpCode, Enum_flag.异常.value);
				return null;
			}
			
		} else {
			//没有找到游戏记录，属于正常的错误
			if(object.getString("error_code").equals("23000")) {
				return list;
			}
			logger.error("接口调用失败："+result);
			System.out.println("接口调用失败："+result);
			LogUtil.addListLog(LogUtil.HANDLE_EIBC, API_URL.concat("GetSportBetLog?").concat(params.toString()), "接口调用失败："+result, OpCode, Enum_flag.异常.value);
			return null;
		}
		return null;
	}
	
	
	public static void main(String[] arg){
		System.out.println(getRecord("http://api.prod.ib.gsoft88.net/api/","0","SKX2342KSDZXH84","HYFCN",""));
		System.out.println(DateFormatUtils.format(new Date(), pattern));
	}
}
