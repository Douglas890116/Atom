package com.hy.pull.common.util.game;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hy.pull.common.util.GameHttpUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;


/**
 * 拉取波音游戏数据工具类
 * 创建日期：2016-10-10
 * @author temdy
 */
public class BBINGame {
	/**
	 * 拉取波音游戏数据列表
	 * @param apiUrl 接口链接
	 * @param website 网站名称
	 * @param username 会员帐号
	 * @param uppername 上层帐号
	 * @param gamekind 游戏种类（1、BB体育，3、视讯，5、机率，12、彩票，15、3D厅）
	 * @param subgamekind (gamekind=5时，值：1、2，预设为1)
	 * @param gametype 游戏类型（gamekind=12时，需强制带入）
	 * @param userKey MD5密钥
	 * @param rounddate 拉取日期
	 * @param starttime 开始时间
	 * @param endtime 结束时间
	 * @return 游戏数据列表
	 */
	public static List<Map<String, Object>> getBBINData(String apiUrl, String website, String username, String uppername,String gamekind,String subgamekind, String gametype, String userKey, String rounddate, String starttime, String endtime) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			String url = "http://linkapi.avia88.org/app/WebService/JSON/display.php/BetRecord";
			if(apiUrl==null){
				apiUrl = url;
			}
			int pagelimit = 100; // 每页记录
			String date = GameHttpUtil.getDateTime_Md(); // 美东时间 (北京时间-12小时)
			//SimpleDateFormat t = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
			String[] d = GameHttpUtil.getDate().split(" ");
			if(rounddate == null){
				rounddate = d[0];
			}
			if(starttime == null){
				starttime =  d[1];//10分钟前
			}
			if(endtime == null){
				endtime = "23:59:59";
			}
			
			String key = GameHttpUtil.genRandomNum(4).concat(GameHttpUtil.MD5(website + userKey + date)).concat(GameHttpUtil.genRandomNum(1));
			String params = getBBINParams(website,username,uppername,gamekind,subgamekind,gametype,rounddate,starttime,endtime,key,1,pagelimit);
			//System.out.println(url+"?"+params.toString());
			String result = GameHttpUtil.sendPost(url+"?", params.toString());
			JSONObject jsonObject = JSONObject.fromObject(result);
			//System.out.println(result);
			boolean flag = jsonObject.getBoolean("result");
			if(flag){//返回为true的时候才往下执行
				JSONObject json = jsonObject.getJSONObject("pagination");
				String totalPage = json.getString("TotalPage");
				if(totalPage.equals("null")){
					totalPage = "0";
				}
				int totalPageInt = Integer.parseInt(totalPage);
				for (int i = 1; i <= totalPageInt; i++) {
					params = getBBINParams(website,username,uppername,gamekind,subgamekind,gametype,rounddate,starttime,endtime,key,i,pagelimit);
					list.addAll(getBBINData(url,uppername,params));
				}
			}
		} catch (JSONException ex) {
			System.out.println("json解析异常："+ex.toString());
		}
		return list;
	}
	
	/**
	 * 拉取波音游戏数据列表
	 * @param apiUrl 接口链接
	 * @param agent 代理
	 * @param params 参数
	 * @return 游戏数据列表
	 */
	public static List<Map<String, Object>> getBBINData(String apiUrl,String agent,String params) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			String jsonResult = GameHttpUtil.sendPost(apiUrl+"?", params);
			JSONObject jsonObject = JSONObject.fromObject(jsonResult);
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			int size = jsonArray.size();
			if (jsonArray.size() > 0) {
				JSONObject json = null;
				Map<String, Object> entity = null;
				for (int i = 0; i < size; i++) {
					json = jsonArray.getJSONObject(i);
					entity = new HashMap<String,Object>();
					String Result = json.getString("Result");
					if ("-1".equals(Result)) {
						continue;
					}	
					entity.put("bbin_WagersID",json.getString("WagersID"));
					entity.put("bbin_UserName",json.getString("UserName"));
					entity.put("bbin_WagersDate",json.getString("WagersDate"));
					entity.put("bbin_GameType",json.has("GameType") ? json.getString("GameType") : null);
					entity.put("bbin_Result",json.getString("Result"));
					entity.put("bbin_BetAmount",json.getString("BetAmount"));
					entity.put("bbin_Payoff",json.getString("Payoff"));
					entity.put("bbin_Currency",json.getString("Currency"));
					entity.put("bbin_Commissionable",json.has("Commissionable") ? json.getString("Commissionable") : null);
					entity.put("bbin_SerialID",json.has("SerialID") ? json.getString("SerialID") : null);
					entity.put("bbin_RoundNo",json.has("RoundNo") ? json.getString("RoundNo") : null);
					entity.put("bbin_GameCode",json.has("GameCode") ? json.getString("GameCode") : null);
					entity.put("bbin_ResultType",json.has("ResultType") ? json.getString("ResultType") : null);
					entity.put("bbin_Card",json.has("Card") ? json.getString("Card") : null);
					entity.put("bbin_ExchangeRate",json.getString("ExchangeRate"));
					entity.put("bbin_Commission",json.has("Commission") ? json.getString("Commission") : null);
					entity.put("bbin_Origin",json.has("Origin") ? json.getString("Origin") : null);
					entity.put("bbin_IsPaid",json.has("IsPaid") ? json.getString("IsPaid") : null);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
					entity.put("bbin_createtime",sdf.format(new Date()));
					entity.put("Platformflag", agent);
					list.add(entity);
				}
			}
		}catch (JSONException ex) {
			System.out.println("json解析异常："+ex.toString());
		}
		return list;
	}
	
	/**
	 * 获取波音游戏参数
	 * @param website 网站名称
	 * @param uppername 上层帐号
	 * @param gamekind 游戏种类（1、BB体育，3、视讯，5、机率，12、彩票，15、3D厅）
	 * @param subgamekind (gamekind=5时，值：1、2，预设为1)
	 * @param gametype 游戏类型（gamekind=12时，需强制带入）
	 * @param rounddate 拉取日期
	 * @param starttime 开始时间
	 * @param endtime 结束时间
	 * @param key md5密钥
	 * @param page 页码
	 * @param pagelimit 数量
	 * @return 参数
	 */
	private static String getBBINParams(String website, String username, String uppername, String gamekind, String subgamekind, String gametype, String rounddate, String starttime, String endtime, String key, int page, int pagelimit){
		StringBuilder params = new StringBuilder();
		params.append("website=");
		params.append(website);
		if(username!=null){
			params.append("&username=");
			params.append(username);
		}
		params.append("&uppername=");
		params.append(uppername);
		params.append("&gamekind=");
		params.append(gamekind);
		if(subgamekind!=null){
			params.append("&subgamekind=");
			params.append(subgamekind);
		}
		if(gametype!=null){
			params.append("&gametype=");
			params.append(gametype);
		}
		params.append("&rounddate=");
		params.append(rounddate);
		params.append("&starttime=");
		params.append(starttime);
		params.append("&endtime=");
		params.append(endtime);
		params.append("&key=");
		params.append(key);
		params.append("&page=");
		params.append(page);
		params.append("&pagelimit=");
		params.append(pagelimit);
		return params.toString();
	}
	
	public static void main(String[] arg){
		String url = "http://linkapi.avia88.org/app/WebService/JSON/display.php/BetRecord";
		String types[] = new String[] { "LT", "BJ3D", "PL3D", "BBPK", "BB3D",
				"BBKN", "BBRB", "SH3D", "CQSC", "JXSC", "XJSC", "CQSF", "GXSF",
				"GDSF", "TJSF", "BJPK", "BJKN", "CAKN", "AUKN", "GDE5", "JXE5",
				"SDE5", "CQWC", "JLQ3", "JSQ3", "AHQ3" };
		for (String type : types) {
			System.out.println(getBBINData(url,"oriental",null,"dhyapibet588","12",null,type,"Yg8OJw7","2016-11-14",null,null));
		}
	}
}
