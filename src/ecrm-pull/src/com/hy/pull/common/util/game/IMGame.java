package com.hy.pull.common.util.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.UpperCamelCaseStrategy;
import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.mapper.DataHandleLogsMapper;
import com.hy.pull.service.BaseService.Enum_flag;
import com.hy.pull.service.game.LogUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;


/**
 * 拉取IM体育游戏数据工具类
 * 创建日期：2016-10-10
 * @author temdy
 */
public class IMGame {
	private static Logger logger = LogManager.getLogger(IMGame.class.getName());
	
	private static JSONObject doPostSubmitMap(String url, JSONObject xmlData) {
		
		try {
			
			CloseableHttpClient httpClient = HttpClients.createDefault();
			int timeout = 15000;
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();//设置请求和传输超时时间
			
			
			Integer statusCode = -1;
			// Create HttpClient Object
			System.out.println("请求URL："+url);
			System.out.println("请求DATA："+xmlData);
			// Send data by post method in HTTP protocol,use HttpPost instead of
			// PostMethod which was occurred in former version
			HttpPost post = new HttpPost(url);
			post.setConfig(requestConfig);
			// Construct a string entity
			StringEntity entity = new StringEntity(xmlData.toString());
			// Set XML entity
			post.setEntity(entity);
			// Set content type of request header
			post.setHeader("Content-Type", "application/json;charset=UTF-8");
			// Execute request and get the response
			HttpResponse response = httpClient.execute(post);
			// Response Header - StatusLine - status code
			
			statusCode = response.getStatusLine().getStatusCode();
			
			JSONObject jsonObject = new JSONObject();
            if(statusCode == 200)  {
            	String result = EntityUtils.toString(response.getEntity());
            	jsonObject = JSONObject.fromObject(result);
            } else {
            	jsonObject.put("Code", ""+statusCode);
    			jsonObject.put("Message", "返回响应码不正确："+statusCode);
            }
            return jsonObject;
            
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Code", "-1");
			jsonObject.put("Message", e.getMessage());
			return jsonObject;
		} finally {
			
		}
	}

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static List<Map<String, Object>> getData(String API_URL, String MerchantCode, String starttime, String endtime, String platformflag, String BetStatus) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		int page = 1;
		JSONObject result = getRecord(API_URL, MerchantCode, starttime, endtime, BetStatus, platformflag, page);
		
		//接口调用成功
		if( result.getString("Code").equals("0") ) {
			JSONObject Pagination = result.getJSONObject("Pagination");
			int CurrentPage = Pagination.getInt("TotalCount");
			int TotalPage = Pagination.getInt("TotalPage");
			int ItemPerPage = Pagination.getInt("ItemPerPage");
			int TotalCount = Pagination.getInt("TotalCount");
			if(TotalCount > 0) {
				//获取到有数据
				System.out.println("获取到数据："+TotalCount+"条");
				JSONArray array = result.getJSONArray("Result");
				list.addAll(getListData(array, MerchantCode)) ;
				
				//还有下一页
				while (CurrentPage < TotalPage) {
					result = getRecord(API_URL, MerchantCode, starttime, endtime, BetStatus,platformflag, page + 1);
					if( result.getString("Code").equals("0") ) {
						Pagination = result.getJSONObject("Pagination");
						CurrentPage = Pagination.getInt("TotalCount");
						TotalPage = Pagination.getInt("TotalPage");
						ItemPerPage = Pagination.getInt("ItemPerPage");
						TotalCount = Pagination.getInt("TotalCount");
						
						array = result.getJSONArray("Result");
						list.addAll(getListData(array, MerchantCode)) ;
					} else {
						return null;
					}
				}
				return list;
			} else {
				System.out.println("没有找到数据");
				return list;
			}
		} else {
			System.out.println("IM体育获取数据出现异常："+result);
			//查找数据失败
			return null;
		}
	}
	
	private static JSONObject getRecord(String API_URL, String MerchantCode, String starttime, String endtime, String BetStatus, String platformflag, int page) {
		
		if(API_URL.endsWith("/")) {
			API_URL = API_URL.substring(0, API_URL.length() - 1);
		}
		
//		注意: 
//		1.日志的搜寻时间区间封顶在10分钟  (举例, 2016-10-10 00.00.00 - 2016-10-10 00.10.00)。 
//		2.与其他产品不同的是，体育博彩日志一页的容纳固定在5000笔。因此无需PagSize参数。如果日志超过5,000笔，营运商需发下一页的请求。 
//		3.关于并发请求限制，请参考第1.3节。 
//		4.日志可能有15 分钟的更新延迟。建议只搜索至15 分钟前的日志  (举例，当前时间为2016-10-10 00:30:00，可搜寻的日期区间为2016-10-10 00:05:00 - 2016-10-10 00:15:00)。 	
//		5.如果需要发下一个请求，营运商需等待至当前请求处理完毕。 	  
//		6.日志可以日志的下注时间或赛事时间来搜寻  (由DateFilterType参数来决定)。	
			
		
		try {
			
			starttime = sdf.format(sdf2.parse(starttime));//时间格式：  yyyy-MM-dd HH.mm.ss  
			endtime = sdf.format(sdf2.parse(endtime));//时间格式：  yyyy-MM-dd HH.mm.ss  
			
			int pagelimit = 5000;//每页数量
			//int page = 1;//查询页数
			if(page <= 0) {
				page = 1;
			}
			
			JSONObject xmlData = new JSONObject();
			xmlData.put("MerchantCode", MerchantCode);
			xmlData.put("StartDate", starttime);
			xmlData.put("EndDate", endtime);
			xmlData.put("Page", page+"");
			xmlData.put("ProductWallet", "301");//此参数值必须为301或401
			xmlData.put("Currency", "CNY");//CNY, USD, EUR, JPY, MYR, IDR, VND, THB, KRW
			
			//1 = 下注时间  (Bet Date  注单的下注时间) 2 = 比赛时间  (Event Date所投注比赛的开赛时间)
			xmlData.put("DateFilterType", "1");
			//此参数用于决定回传那种注单. 可选参数。如果未传此参数，系统则返回所有结算与未结算注单。0 = 未结算  (Not Settled) 1 = 结算  (Settled) 
			if(BetStatus != null && !BetStatus.equals("")) {
				xmlData.put("BetStatus", BetStatus);
			}
			
			//可选参数。如果提供此参数，系统则只返回所有最后跟新时间大于此值的注单。
			//xmlData.put("LastUpdatedDate", "1");时间格式：  yyyy-MM-dd HH.mm.ss  
			xmlData.put("Language", "ZH-CN");
			
			JSONObject result =  doPostSubmitMap(API_URL.concat("/Report/GetBetLog"), xmlData);
			//System.out.println("调用结果："+result);
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Code", "-1");
			jsonObject.put("Message", e.getMessage());
			return jsonObject;
		}
		
	}
	
	private static List<Map<String, Object>> getListData(JSONArray array, String MerchantCode) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		try {
			if(array != null && array.size() > 0) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject data = array.getJSONObject(i);
					    
					Map<String, Object> mapdata = new HashMap<String, Object>();
					mapdata.put("betid", data.getString("BetId"));
					mapdata.put("providername", data.getString("ProviderName"));
					mapdata.put("gameid", data.getString("GameId"));
					mapdata.put("wagercreationdatetime", data.getString("WagerCreationDateTime"));
					mapdata.put("playerid", data.getString("PlayerId"));
					mapdata.put("currency", data.getString("Currency"));
					mapdata.put("stakeamount", data.getString("StakeAmount"));//下注额
					mapdata.put("memberexposure", data.getString("MemberExposure"));//实际扣除金额   
					mapdata.put("payoutamount", data.getString("PayoutAmount"));//可赢金额
					mapdata.put("winloss", data.getString("WinLoss"));//输赢 如果注单未结算，则返回0
					mapdata.put("oddstype", data.getString("OddsType"));//赔率类别:HK  香港盘  /  EURO  欧洲盘  /  MALAY  马来盘  / INDO  印尼盘 
					mapdata.put("wagertype", data.getString("WagerType"));//注单类别: Single  单一  / Combo  混合过关
					mapdata.put("platform", data.getString("Platform"));//Web  电脑  / Mobile  手机 
					mapdata.put("issettled", data.getString("IsSettled"));//0 = Not Settled  未结算, 1 = Settled  已结算
					mapdata.put("isconfirmed", data.getString("IsConfirmed"));//赛事的状态 0 = Pending待定, 1 = Confirmed确认, 2 = Cancelled  取消   
					mapdata.put("iscancelled", data.getString("IsCancelled"));//赛事的状态 0 = Not  cancel  未取消,  1 = Cancel已取消
					mapdata.put("bettradestatus", data.getString("BetTradeStatus"));//BetTrade 状态: Cancel  取消  / Sold  兑现成功
					mapdata.put("bettradecommission", data.getString("BetTradeCommission"));//BetTrade 返水金额. 
					mapdata.put("bettradebuybackamount", data.getString("BetTradeBuyBackAmount"));//BetTrade  金额  (buy back amount)
					mapdata.put("combotype", data.getString("ComboType"));
					mapdata.put("lastupdateddate", data.getString("LastUpdatedDate"));
					mapdata.put("detailitems", data.optString("DetailItems"));
					
					//自定义字段
					Date bettime = sdf2.parse(mapdata.get("wagercreationdatetime").toString().substring(0, 19));
					Date nettime = sdf2.parse(mapdata.get("lastupdateddate").toString().substring(0, 19));
					
					mapdata.put("createtime", new Date());
					mapdata.put("bettime", bettime);//2017-10-17 13:17:58 +08:00
					mapdata.put("nettime", nettime);
					mapdata.put("platformflag", MerchantCode);
					
					String issettled = mapdata.get("issettled").toString();
					
					if( issettled.equals("1") ) {//结算的才写投注和输赢金额
						mapdata.put("betmoney", mapdata.get("stakeamount"));
						mapdata.put("netmoney", mapdata.get("winloss"));
					} else {
						mapdata.put("betmoney", "0");
						mapdata.put("netmoney", "0");
					}
					list.add(mapdata);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	public static void main(String[] arg){
		System.out.println("2017-10-17 13:17:58 +08:00".substring(0, 19));
	}
}
