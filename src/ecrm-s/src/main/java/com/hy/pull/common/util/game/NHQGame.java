package com.hy.pull.common.util.game;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.common.util.game.sa.DeEnCode;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * 拉取新环球游戏数据工具类
 * 创建日期：2016-10-10
 * @author temdy
 */
public class NHQGame {

	/**
	 * 拉取新环球游戏数据列表
	 * @param apiUrl 接口链接
	 * @param agent 代理
	 * @param key 密钥
	 * @param stardate 开始日期
	 * @param enddate 结束日期
	 * @return 游戏数据列表
	 */
	public static List<Map<String, Object>> getNHQData(String apiUrl,String agent,String key,String stardate, String enddate) {
		double pagesize = 100;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			String begintime = getMaxDate(stardate);//获取最大值
			if(stardate != null){
				begintime = stardate;
			}
			String endtime = sdf.format(new Date());
			if(enddate != null){
				endtime = enddate;
			}
			String result = getNHQData(agent, apiUrl, key, begintime, endtime, 1);
			JSONObject jsonObject = JSONObject.fromObject(result);
			if(jsonObject.has("total")){
				String total = jsonObject.getString("total");			
				if (!"0".equals(total)) {
					double totals = Double.parseDouble(total);
					int pagesum = 1;
					if (totals > pagesize) {
						pagesum = (int) Math.ceil(totals / pagesize);
						for (int i = 1; i <= pagesum; i++) {
							result = getNHQData(agent, apiUrl, key, begintime, endtime, i);
							list.addAll(getNHQData(agent,result));
						}
					} else {
						list.addAll(getNHQData(agent,result));
					}
				}
			}
		}catch (JSONException ex) {
			System.out.println("json解析异常："+ex.toString());
		}catch (Exception ex) {
			System.out.println(ex.toString());
		}
		return list;
	}

	/**
	 * 拉取新环球游戏数据列表
	 * @param agent 代理
	 * @param jsonResult json格式结果数据
	 * @return 游戏数据列表
	 */
	public static List<Map<String, Object>> getNHQData(String agent, String jsonResult) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			JSONObject jsonObject = JSONObject.fromObject(jsonResult);
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			int size = jsonArray.size();
			if (jsonArray.size() > 0) {
				JSONObject info = null;
				Map<String, Object> entity = null;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
				for (int i = 0; i < size; i++) {
					info = jsonArray.getJSONObject(i);
					entity = new HashMap<String, Object>();
					entity.put("BettingCredits", info.getString("BettingCredits"));
					if (info.has("PreCreditsPoint")) {
						entity.put("PreCreditsPoint", info.getString("PreCreditsPoint"));
					} else {
						entity.put("PreCreditsPoint", null);
					}
					if (info.has("GameResult")) {
						entity.put("GameResult", info.getString("GameResult"));
					} else {
						entity.put("GameResult", null);
					}
					entity.put("GameRoomName", info.getString("GameRoomName"));
					entity.put("AgentAccount", info.getString("AgentAccount"));
					entity.put("GamblingCode", info.getString("GamblingCode"));
					if (info.has("AfterPayoutCredits")) {
						entity.put("AfterPayoutCredits", info.getString("AfterPayoutCredits"));
					} else {
						entity.put("AfterPayoutCredits", null);
					}
					entity.put("UserAccount", info.getString("UserAccount"));
					entity.put("GameType", info.getString("GameType"));
					System.out.println("BettingDate=============="+info.getString("BettingDate"));
					entity.put("BettingDate", info.getString("BettingDate"));
					entity.put("BettingNO", info.getString("BettingNO"));
					entity.put("DealerName", info.getString("DealerName"));
					entity.put("GameName", info.getString("GameName"));
					entity.put("SetGameNo", info.getString("SetGameNo"));
					entity.put("IsPayout", info.getString("IsPayout"));
					entity.put("ParentUserID", info.getString("ParentUserID"));
					entity.put("WinningCredits", info.getString("WinningCredits"));
					entity.put("BettingPoint", info.getString("BettingPoint"));
					entity.put("TableName", info.getString("TableName"));
					entity.put("TrackIP", info.getString("TrackIP"));
					entity.put("WashCodeCredits", info.getString("WashCodeCredits"));
					/**
					if (info.has("UpdateTime")) {
						entity.put("UpdateTime", info.getString("UpdateTime"));
					} else {
						entity.put("UpdateTime", null);
					}
					*/
					entity.put("UpdateTime", sdf.format(new Date()));
					entity.put("CreateTime", sdf.format(new Date()));
					entity.put("BettingID", info.getString("BettingID"));
					entity.put("Platformflag", agent);
					list.add(entity);
				}
			}
		} catch (JSONException ex) {
			System.out.println("json解析异常：" + ex.toString());
		}
		return list;
	}
	
	/**
	 * 获取请求数据
	 * @param url 请求URL
	 * @return 请求数据
	 */
	public static String getUrl(String url) {
		StringBuilder sb = new StringBuilder();
		try {
			InputStream in = new URL(url).openStream();			
			sb = GameHttpUtil.getResponseText(in, new StringBuilder());			
		} catch (IOException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
		return sb.toString();
	}
	
	/**
	 * 获取新环球游戏数据结果
	 * @param agent 代理帐号
	 * @param apiUrl 接口URL
	 * @param key 密钥
	 * @param begintime 开始时间
	 * @param endtime 结束时间
	 * @param pagenum 页码
	 * @return 数据结果
	 */
	private static String getNHQData(String agent, String apiUrl, String key, String begintime, String endtime, int pagenum) throws Exception{
		StringBuilder params = new StringBuilder();
		params.append("parentid=");
		params.append(agent);
		params.append("!begintime=");
		params.append(begintime);
		params.append("!endtime=");
		params.append(endtime);		
		params.append("!isall=1");
		params.append("!pagenum=");
		params.append(pagenum);
		params.append("!method=");
		params.append("info");
		String des = GameHttpUtil.encrypt(params.toString(), key);
		String jsonResult = getUrl(apiUrl + "?params="+des);
		return jsonResult;
	}
	
	/**
	 * 获取新环球游戏数据结果
	 * @param username 用户名（不传此参数则获取全部游戏记录）
	 * @param agentname 代理帐号
	 * @param agentpwd 代理密码
	 * @param apiUrl 接口URL
	 * @param deskey DES密钥
	 * @param md5key MD5密钥
	 * @param begintime 开始时间
	 * @param endtime 结束时间
	 * @param pagenum 页码
	 * @return 数据结果
	 */
	@SuppressWarnings("unused")
	private static String getNHQData(String username, String agentname,String agentpwd, String apiUrl, String deskey, String md5key, String begintime, String endtime, int pagenum) throws Exception{
		StringBuilder param = new StringBuilder();
		param.append("username=");
		param.append(username);
		param.append("!begintime=");
		param.append(begintime);
		param.append("!endtime=");
		param.append(endtime);		
		param.append("!isall=1");
		param.append("!pagenum=");
		param.append(pagenum);
		param.append("!method=");
		param.append("info");
		String params = "?params="+DeEnCode.encrypt(param.toString(), deskey);
		String key = "&key="+DeEnCode.string2MD5(param.toString().concat(md5key));
		agentname = "&agentname="+agentname;
		agentpwd = "&agentpwd="+agentpwd;
		String jsonResult = getUrl(apiUrl.concat(params).concat(key).concat(agentname).concat(agentpwd));		
		return jsonResult;
	}
	
	/**
	 * 获取数据库拉取的最大日期
	 * @param max 最大日期
	 * @return 最大日期
	 */
	private static String getMaxDate(String max){
		if(max==null){//如果获取的最大日期为空，刚设置成当前日期减少12个小时
			SimpleDateFormat t = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.set(Calendar.MINUTE,c.get(Calendar.MINUTE)-10);
			max = t.format(c.getTime());
		}
		return max;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(getNHQData("http://www.hy96.net/web","234","cyhqcome","2016-10-20 00:00:00","2016-10-24 00:00:00"));
	}
}
