package com.hy.pull.common.util.game;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.common.util.MoneyHelper;
import com.hy.pull.service.BaseService.Enum_flag;
import com.hy.pull.service.game.LogUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 拉取GB彩票游戏数据工具类
 * 创建日期：2016-10-10
 * @author temdy
 */
public class GBGame {
	private static Logger logger = LogManager.getLogger(GBGame.class.getName());
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
	
	/**
	 * 拉取沙巴体育游戏数据列表
	 * @param apiUrl 接口链接
	 * @param TPCode 代理
	 * @param StartSettleID 最大StartSettleID
	 * @param code 代理编码
	 * @return 游戏数据列表
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> getIBCData(String API_URL,String TPCode,String GeneralKey,String StartSettleID,String code) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		String EndSettleID = "-1";//結算流水號結束。輸入-1為傳回最大筆數。
		if(StartSettleID == null || StartSettleID.equals("0")) {
			StartSettleID = "0";//結算流水號開始(大於)。流水號輸入1，則回傳2(含)之後注單，但並不包括1。
		}
		//每次最多返回200笔
		JSONObject object = new JSONObject();
		JSONObject GB = new JSONObject();
		GB.put("Method", "GetGamingSettle");
		GB.put("TPCode", TPCode);
		GB.put("AuthKey", GeneralKey);
		
		JSONObject Params = new JSONObject();
		Params.put("StartSettleID", StartSettleID );
		Params.put("EndSettleID", EndSettleID );
		GB.put("Params", Params);
		object.put("GB", GB);
		
		
		String result =  sendDataByPost(API_URL, object, TPCode);
		System.out.println("调用结果："+result);
		logger.warn("调用结果："+result);
		if(result == null) {
			return null;
		}
		
		JSONObject data = JSONObject.fromObject(result);
		
		//	接口调用成功
		if( data.getJSONObject("GB").getJSONObject("Result").getString("Success").equals("1")) {//交易狀態，1：成功、2：失敗、3：查無資料、4：進行中
			
//			BetTotalCnt	本次注單數量
//			BetTotalAmt	本次注單實際下注總金額：SUM(RealBetAmt) ，基數100

//			  "SettleID": "5746847",
//			  "BetID": "5606714" ,
//			  "BetGrpNO": "2015122915440044573528" ,
//			  "TPCode": "99999",
//			  "GBSN": "1193141",
//			  "MemberID": "wantbet",
//			  "CurCode": "cny",
//			  "BetDT": "2015-12-29 15:44:05",
//			  "BetType": "1",
//			  "BetTypeParam1": "1",
//			  "BetTypeParam2": "1",
//			  "Wintype": "1",
//			  "HxMGUID": "0",
//			  "InitBetAmt": "200",
//			  "RealBetAmt": "200",
//			  "HoldingAmt": "200",
//			  "InitBetRate": "25100",
//			  "RealBetRate": "0",
//			  "PreWinAmt": "50200",
//			  "BetResult": "0",
//			  "WLAmt": "0",
//			  "RefundBetAmt": "0",
//			  "TicketBetAmt": "200",
//			  "TicketResult": "0",
//			  "TicketWLAmt": "0",
//			  "SettleDT": "2015-12-29 15:44:41",
			
//			KenoList：数组数据
//			LottoList：数组数据
//			SscList：数组数据
//			PkxList：数组数据
			
			JSONArray listdata = data.getJSONObject("GB").getJSONObject("Result").getJSONObject("ReturnSet").getJSONArray("SettleList");//多条
			
			try {
				
				for (int i = 0; i < listdata.size(); i++) {
					JSONObject jsonObject = listdata.getJSONObject(i);
					
//					取得會員下注已結算交易，金額及賠率基數為100，
//					例如：
//					RealBetAmt=1000，代表下注金額為10元；
//					RealBetRate=195，代表賠率為1.95。
							
					Map<String, Object> item = new HashMap<String, Object>();
					item.put("settleid", jsonObject.getString("SettleID"));
					item.put("betid", jsonObject.getString("BetID"));
					item.put("betgrpno", jsonObject.getString("BetGrpNO"));
					item.put("tpcode", jsonObject.getString("TPCode"));
					item.put("gbsn", jsonObject.getString("GBSN"));
					item.put("memberid", jsonObject.getString("MemberID"));
					item.put("curcode", jsonObject.getString("CurCode"));
					item.put("betdt", jsonObject.getString("BetDT"));
					
//					1：獨贏
//					2：串關(M*N)
//					3：追號(連下M期)，KENO才有追號功能
					item.put("bettype", jsonObject.getString("BetType"));
					
					item.put("bettypeparam1", jsonObject.getString("BetTypeParam1"));
					item.put("bettypeparam2", jsonObject.getString("BetTypeParam2"));
					
//					Wintype	1：獨贏、追號
//					2：串關
					item.put("wintype", jsonObject.getString("Wintype"));
					
					item.put("hxmguid", jsonObject.getString("HxMGUID"));
					item.put("initbetamt", jsonObject.getString("InitBetAmt"));
					item.put("realbetamt", jsonObject.getString("RealBetAmt"));//实际下注金额
					item.put("holdingamt", jsonObject.getString("HoldingAmt"));
					item.put("initbetrate", jsonObject.getString("InitBetRate"));
					item.put("realbetrate", jsonObject.getString("RealBetRate"));//实际赔率
					item.put("prewinamt", jsonObject.getString("PreWinAmt"));
					
//					0：輸、1：贏、2：平手、3：調整、4：取消、5：兌現
					item.put("betresult", jsonObject.getString("BetResult"));
					
//					輸贏金額，基數100
					item.put("wlamt", jsonObject.getString("WLAmt"));
//					退還金額，基數100
					item.put("refundbetamt", jsonObject.getString("RefundAmt"));
					
					
					item.put("ticketbetamt", jsonObject.getString("TicketBetAmt"));
					
//					票據最終狀態
//					0：輸、1：贏、2：平手、4：取消、5：兌現、
//					R：Rollback，體育專屬，表示先前說的都不算，回沖輸贏，等待下次結算
					item.put("ticketresult", jsonObject.getString("TicketResult"));
					
					item.put("ticketwlamt", jsonObject.getString("TicketWLAmt"));
					item.put("settledt", jsonObject.getString("SettleDT"));//結算時間
					
					item.put("kenolist", jsonObject.getString("KenoList"));
					item.put("lottolist", jsonObject.getString("LottoList"));
					item.put("ssclist", jsonObject.getString("SscList"));
					item.put("pkxlist", jsonObject.optString("PkxList"));
					
					item.put("createtime", new Date());
					item.put("bettime", sdf.parse(item.get("betdt").toString().replaceAll("T", " ").replaceAll("\\+08:00", "")));
					item.put("betmoney", MoneyHelper.doubleFormat(Double.valueOf(item.get("realbetamt").toString()) / 100.00) );
					double netmoney =  (Double.valueOf(item.get("wlamt").toString()) - Double.valueOf(item.get("realbetamt").toString()) + Double.valueOf(item.get("refundbetamt").toString()) ) / 100.00 ;
					item.put("netmoney",  MoneyHelper.doubleFormat(netmoney) );//输赢金额+返还金额=最终的输赢金额
					
					list.add(item);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
				LogUtil.addListLog(LogUtil.HANDLE_GB, API_URL+ object.toString(), e.getMessage(), TPCode, Enum_flag.异常.value);
				return null;
			}
			
		} else {
			//获取数据失败
			logger.warn(result);
			LogUtil.addListLog(LogUtil.HANDLE_GB, API_URL+ object.toString(), result, TPCode, Enum_flag.异常.value);
		}
		
		return list;
	}
	
	private static DefaultHttpClient client;
	private static String sendDataByPost(String url, JSONObject xmlData, String agent) {
		try {
			Integer statusCode = -1;
			if (client == null) {
				// Create HttpClient Object
				client = new DefaultHttpClient();
			}
//			System.out.println("请求URL："+url);
//			System.out.println("请求DATA："+xmlData);
			// Send data by post method in HTTP protocol,use HttpPost instead of
			// PostMethod which was occurred in former version
			HttpPost post = new HttpPost(url);
			// Construct a string entity
			StringEntity entity = new StringEntity(xmlData.toString());
			// Set XML entity
			post.setEntity(entity);
			// Set content type of request header
			post.setHeader("Content-Type", "application/json;charset=UTF-8");
			// Execute request and get the response
			HttpResponse response = client.execute(post);
			// Response Header - StatusLine - status code
			
			statusCode = response.getStatusLine().getStatusCode();
			String result = EntityUtils.toString(response.getEntity());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			LogUtil.addListLog(LogUtil.HANDLE_GB, url +  xmlData.toString(), e.getMessage(), agent, Enum_flag.异常.value);
			return null;
		}
	}
	
	
	public static void main(String[] arg){
		System.out.println(getIBCData("http://cashapi.dg20mu.com/cashapi/getdata.aspx","OGCNYHYI",null,"hq@-@688*$-$*!&88$dshxeh",""));
	}
}
