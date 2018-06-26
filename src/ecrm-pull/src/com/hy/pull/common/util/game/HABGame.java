package com.hy.pull.common.util.game;

import java.io.IOException;
import java.math.BigDecimal;
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
import com.hy.pull.common.util.game.hb.HostedSoap;
import com.hy.pull.common.util.game.hb.HostedSoapProxy;
import com.hy.pull.common.util.game.hb.PlayerCompletedGamesDTO;
import com.hy.pull.common.util.game.hb.ReportRequest;
import com.hy.pull.common.util.game.zj.ZJUtil;
import com.hy.pull.service.BaseService.Enum_flag;
import com.hy.pull.service.game.LogUtil;

/**
 * HABA哈巴电子拉数据
 * 
 * 
 * 创建日期：2016-10-17
 * @author temdy
 */
public class HABGame {
	
	private static Logger logger = LogManager.getLogger(HABGame.class.getName());
	
	/**
	 * 获取游戏结果
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	public static List<Map<String, Object>> getRecord(String API_URL,String APIKey, String BrandId, String starttime,String endtime) {

		List<Map<String, Object>> list = new ArrayList<>();
		
		ReportRequest request = new ReportRequest();
		request.setBrandId(BrandId);
		request.setAPIKey(APIKey);
		request.setDtStartUTC(starttime);
		request.setDtEndUTC(endtime);
		
//		System.out.println("starttime="+starttime);
//		System.out.println("endtime  ="+endtime);
		try {
			HostedSoap soap = new HostedSoapProxy(API_URL);
			PlayerCompletedGamesDTO[] result = soap.getBrandCompletedGameResults(request);
			
			Map<String,Object> entity = null;
			if(result.length == 0) {
				return list;
			}
			for (PlayerCompletedGamesDTO playerCompletedGamesDTO : result) {
				
				/***
				System.out.print(playerCompletedGamesDTO.getBrandGameId());System.out.print("-");
				System.out.print(playerCompletedGamesDTO.getBrandId());System.out.print("-");
				System.out.print(playerCompletedGamesDTO.getCurrencyCode());System.out.print("-");
				System.out.print(playerCompletedGamesDTO.getFriendlyGameInstanceId());System.out.print("-");
				System.out.print(playerCompletedGamesDTO.getGameInstanceId());System.out.print("-");
				System.out.print(playerCompletedGamesDTO.getGameKeyName());System.out.print("-");
				System.out.print(playerCompletedGamesDTO.getPlayerId());System.out.print("-");
				System.out.print(playerCompletedGamesDTO.getUsername());System.out.print("-");
				System.out.print(playerCompletedGamesDTO.getChannelTypeId());System.out.print("-");
				System.out.print(playerCompletedGamesDTO.getDtCompleted().getTime().toLocaleString());System.out.print("-");
				System.out.print(playerCompletedGamesDTO.getDtStarted().getTime().toLocaleString());System.out.print("-");
				System.out.print(playerCompletedGamesDTO.getGameTypeId());System.out.print("-");
				
				System.out.print(playerCompletedGamesDTO.getStake().doubleValue());System.out.print("-");//投注
				System.out.print(playerCompletedGamesDTO.getPayout().doubleValue());System.out.print("-");//支付额
				System.out.print(playerCompletedGamesDTO.getJackpotWin().doubleValue());System.out.print("-");//奖池盈
				System.out.print(playerCompletedGamesDTO.getJackpotContribution().doubleValue());System.out.print("-");
				System.out.println();
				*/
				
				entity = new HashMap<String,Object>();
				
				entity.put("friendlygameinstanceid", playerCompletedGamesDTO.getFriendlyGameInstanceId());
				entity.put("brandgameid", playerCompletedGamesDTO.getBrandGameId());
				entity.put("brandid", playerCompletedGamesDTO.getBrandId());
				entity.put("currencycode", playerCompletedGamesDTO.getCurrencyCode());
				entity.put("gameinstanceid", playerCompletedGamesDTO.getGameInstanceId());
				entity.put("gamekeyname", playerCompletedGamesDTO.getGameKeyName());
				entity.put("playerid", playerCompletedGamesDTO.getPlayerId());
				entity.put("username", playerCompletedGamesDTO.getUsername());
				entity.put("channeltypeid", playerCompletedGamesDTO.getChannelTypeId());
				entity.put("dtcompleted", playerCompletedGamesDTO.getDtCompleted().getTime());
				entity.put("dtstarted", playerCompletedGamesDTO.getDtStarted().getTime());
				entity.put("gametypeid", playerCompletedGamesDTO.getGameTypeId());
				
				entity.put("stake", playerCompletedGamesDTO.getStake().doubleValue());
				entity.put("payout", playerCompletedGamesDTO.getPayout().doubleValue());
				entity.put("jackpotwin", playerCompletedGamesDTO.getJackpotWin().doubleValue());
				entity.put("jackpotcontribution", playerCompletedGamesDTO.getJackpotContribution().doubleValue());
				
				//比如：
				//投注25（减）40支付（减）0奖池赢= -15 盈利 （这个例子就是玩家赢，我们输）
				//如果投注25（减）15支付（减）0奖池赢= 10 盈利 （这个例子就是玩家输，我们赢了10块）
				BigDecimal netmoney = playerCompletedGamesDTO.getStake().subtract(playerCompletedGamesDTO.getPayout()).subtract(playerCompletedGamesDTO.getJackpotWin()).negate();
						
				entity.put("createtime", new Date());//自定义字段，投注时间
				entity.put("betmoney", entity.get("stake"));//自定义字段，投注额
				entity.put("netmoney", netmoney.doubleValue());//自定义字段，输赢金额，正负数
				entity.put("bettime", entity.get("dtstarted"));//自定义字段，投注时间
				entity.put("nettime", entity.get("dtcompleted"));//自定义字段，结算时间
				list.add(entity);
				
			    
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			LogUtil.addListLog(LogUtil.HANDLE_HAB, API_URL, e.getMessage(), BrandId, Enum_flag.异常.value);
			return null;
		}
		
		
		return list;
	}
	

	
	
	public static void main(String[] arg){
		try {
			/*
			 * String API_URL,String APIKey, String BrandId, String starttime,String endtime
			 * */
			
			String API_URL = "https://ws-a.insvr.com/hosted.asmx?wsdl";
			String APIKey = "CD524E5F-4342-4AFA-91B5-42955EB109CD";
			String BrandId = "2b07f09a-ca12-e711-80c4-000d3a805b30";
			
			String starttime = 	"20170327134000";
			String endtime = 	"20170331135900";
			
			List<Map<String, Object>> list = getRecord(API_URL, APIKey, BrandId, starttime, endtime);
			for (Map<String, Object> map : list) {
				System.out.println(map);
			}
			System.out.println("已获取到数据："+list.size());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
