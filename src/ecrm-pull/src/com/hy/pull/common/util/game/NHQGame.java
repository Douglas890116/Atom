package com.hy.pull.common.util.game;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.common.util.game.sa.DeEnCode;
import com.hy.pull.service.BaseService.Enum_flag;
import com.hy.pull.service.game.LogUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * 拉取新环球游戏数据工具类
 * 创建日期：2016-10-10
 * @author temdy
 */
public class NHQGame {
	private static Logger logger = LogManager.getLogger(NHQGame.class.getName());
	/**
	 * 拉取新环球游戏数据列表
	 * @param apiUrl 接口链接
	 * @param username 帐号
	 * @param agentname 代理帐号
	 * @param agentpwd 代理密码
	 * @param deskey DES密钥
	 * @param md5key MD5密钥
	 * @param stardate 开始日期
	 * @param enddate 结束日期
	 * @param code 代理编码
	 * @return 游戏数据列表
	 */
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
	public static List<Map<String, Object>> getNHQData(String apiUrl,String username,String agentname,String agentpwd,String deskey,String md5key,String stardate, String enddate,String code) {
//		System.out.println(apiUrl+","+username+","+agentname+","+agentpwd+","+deskey+","+md5key+","+stardate+","+enddate);
		
		double pagesize = 1000;//最多1000
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try{
			
			String begintime = getMaxDate(stardate);//获取最大值
			if(stardate != null){
				begintime = stardate;
			}
			String endtime = sdf.format(new Date());
			if(enddate != null){
				endtime = enddate;
			}
			String result = getNHQData(apiUrl,username,agentname,agentpwd, deskey,md5key, begintime, endtime, 1);
			if(result == null) {
				return null;
			}
			
			JSONObject jsonObject = JSONObject.fromObject(result);
			if(jsonObject != null && jsonObject.has("total")){
				String total = jsonObject.getString("total");			
				if (!"0".equals(total)) {
					double totals = Double.parseDouble(total);
					int pagesum = 1;
					if (totals > pagesize) {
						pagesum = (int) Math.ceil(totals / pagesize);
						for (int i = 1; i <= pagesum; i++) {
							result = getNHQData(apiUrl, username,agentname,agentpwd, deskey,md5key, begintime, endtime, i);
							//System.out.println(result);
							List list2 = getNHQData(code,result);
							if(list2 == null) {
								return null;
							}
							list.addAll(list2);
						}
					} else {

						//System.out.println(result);
						List list2 = getNHQData(code,result);
						if(list2 == null) {
							return null;
						}
						list.addAll(list2);
					}
				}
			}
		}catch (JSONException ex) {
			ex.printStackTrace();
			logger.error(ex);
			LogUtil.addListLog(LogUtil.HANDLE_NHQ, apiUrl, "json解析异常："+ex.getMessage(), agentname, Enum_flag.异常.value);
			return null;
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			LogUtil.addListLog(LogUtil.HANDLE_NHQ, apiUrl, ex.getMessage(), agentname, Enum_flag.异常.value);
			return null;
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
		List<Map<String, Object>> list = new ArrayList<>();
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
					/****
					if (info.has("UpdatedDate")) {
						entity.put("UpdateTime", info.getString("UpdatedDate"));
					} else {
						entity.put("UpdateTime", null);
					}
					****/
					entity.put("UpdateTime", sdf.format(new Date()));
					entity.put("CreateTime", sdf.format(new Date()));
					entity.put("BettingID", info.getString("BettingID"));
					entity.put("Platformflag", agent);
					list.add(entity);
				}
			}
		} catch (JSONException ex) {
			logger.error(ex);
			LogUtil.addListLog(LogUtil.HANDLE_NHQ, jsonResult, "json解析异常："+ex.getMessage(), agent, Enum_flag.异常.value);
		}
		return list;
	}
	
	/**
	 * 获取请求数据
	 * @param url 请求URL
	 * @return 请求数据
	 */
	public static String getUrl(String url,String agent) {
		StringBuilder sb = new StringBuilder();
		try {
			
			System.out.println("NHQ==="+url);
			
			InputStream in = new URL(url).openStream();			
			sb = GameHttpUtil.getResponseText(in, new StringBuilder());		
			in.close();
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_NHQ, url, e.getMessage(), agent, Enum_flag.异常.value);
			return null;
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_NHQ, url, e.getMessage(), agent, Enum_flag.异常.value);
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
		String des = GameHttpUtil.encrypt(params.toString(), key);//对原文加密
		String jsonResult = getUrl(apiUrl + "?params="+des,agent);//完整请求链接URL
		return jsonResult;
	}
	
	/**
	 * 获取新环球游戏数据结果
	 * @param apiUrl 接口URL
	 * @param username 用户名（不传此参数则获取全部游戏记录）
	 * @param agentname 代理帐号
	 * @param agentpwd 代理密码
	 * @param deskey DES密钥
	 * @param md5key MD5密钥
	 * @param begintime 开始时间
	 * @param endtime 结束时间
	 * @param pagenum 页码
	 * @return 数据结果
	 */
	private static String getNHQData(String apiUrl, String username, String agentname,String agentpwd,String deskey, String md5key, String begintime, String endtime, int pagenum) throws Exception{
		StringBuilder param = new StringBuilder();
		if(username!=null){
			param.append("username=");
			param.append(username+"!");
		}
		param.append("begintime=");
		param.append(begintime);
		param.append("!endtime=");
		param.append(endtime);		
		param.append("!isall=0");//是否显示下级包含的用户，0，不包含|1，包含
		param.append("!pagenum=");
		param.append(pagenum);
		param.append("!method=");
		param.append("info");
		String params = "?params="+DeEnCode.encrypt(param.toString(), deskey);
		String key = "&key="+DeEnCode.string2MD5(param.toString().concat(md5key));
		agentname = "&agentname="+agentname;
		agentpwd = "&agentpwd="+agentpwd;
		String jsonResult = getUrl(apiUrl.concat(params).concat(key).concat(agentname).concat(agentpwd),agentname);		
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
		//System.out.println(getNHQData("http://www.hy96.net/web","234","cyhqcome","2016-10-20 00:00:00","2016-10-24 00:00:00",""));
		System.out.println(getNHQData("http://www.hy95.net/web",null,"HYMKTLL","Hwl5A1fY","rGoVhWUK","zz6rmHvm","2017-01-02 00:00:00","2017-01-04 00:00:00",""));
		String str = "username=test00000!password=test00000!method=create";
//		String param = DeEnCode.encrypt(str, "25I7XFOc");
//		String key = DeEnCode.string2MD5(str+"1nsTx3Gw");
//		System.out.println(param);
//		System.out.println(key);
		
		
	}
}
