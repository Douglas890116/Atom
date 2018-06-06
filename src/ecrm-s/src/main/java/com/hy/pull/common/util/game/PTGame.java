package com.hy.pull.common.util.game;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hy.pull.common.util.game.pt.PTUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 拉取PT游戏数据工具类
 * 创建日期：2016-10-17
 * @author temdy
 */
public class PTGame {
	
	/**
	 * 获取PT游戏数据
	 * @param apiUrl 接口链接
	 * @param agent 代理名称
	 * @param stardate 开始日期
	 * @param enddate 结束日期
	 * @param ptinfo 证书对象
	 * @return 数据列表
	 * @throws Exception 抛出异常
	 */
	public static List<Map<String,Object>> getPTData(String apiUrl,String agent,String stardate, String enddate,Map<String, String> ptinfo) throws Exception {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		int perPage = 100; // 每页记录
		if(stardate==null){
			stardate = getDate();;
			enddate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		}
		if(enddate==null){
			enddate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		}
		String url = getPTParams(apiUrl,stardate,enddate,1,perPage);
		//System.out.println(url);
		String result = PTUtils.getResultByURL(url,ptinfo.get("fileurl"), ptinfo.get("keyStore"),ptinfo.get("entity"));
		//System.out.println("result1:"+result);
		if (!"1".equals(result)) {
			JSONObject jsonObject = JSONObject.fromObject(result);
			boolean isOk = jsonObject.has("pagination");
			if (isOk) {
				JSONObject pagination = jsonObject.getJSONObject("pagination");
				String total = pagination.getString("totalCount"); //总条数
				if (!"0".equals(total.trim())) {
					int totalPages = Integer.parseInt(pagination.getString("totalPages"));
					for (int i = 1; i <= totalPages; i++) {
						url = getPTParams(apiUrl,stardate,enddate,i,perPage);
						list.addAll(getPTData(agent, url,ptinfo));
					}
				}
			}
		}
		return list;
	}

	/**
	 * 获取PT游戏数据
	 * @param apiUrl 接口链接
	 * @param agent 代理名称
	 * @param ptinfo 证书对象
	 * @return 数据列表
	 * @throws Exception 抛出异常
	 */
	public static List<Map<String,Object>> getPTData(String apiUrl, String agent, Map<String, String> ptinfo) throws Exception {
		String result = PTUtils.getResultByURL(apiUrl, ptinfo.get("fileurl"),ptinfo.get("keyStore"), ptinfo.get("entity"));
		//System.out.println("result2:"+result);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if (!"1".equals(result)) {
			JSONObject jsonObject = JSONObject.fromObject(result);
			boolean isok = jsonObject.has("result");
			if (isok) {
				JSONArray josnresult = jsonObject.getJSONArray("result");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
				Map<String,Object> entity = null;
				JSONObject info = null;
				if (null != josnresult) {
					for (int i = 0; i < josnresult.size(); i++) {
						info = josnresult.getJSONObject(i);
						entity = new HashMap<String,Object>();
						entity.put("pt_gamecode",info.getString("pt_gamecode"));
						entity.put("pt_username",info.getString("pt_username"));
						entity.put("pt_windowcode",info.getString("pt_windowcode"));
						entity.put("pt_gameid",info.getString("pt_gameid"));
						entity.put("pt_gametype",info.getString("pt_gametype"));
						entity.put("pt_gamename",info.getString("pt_gamename"));
						entity.put("pt_bet",info.getString("pt_bet"));
						entity.put("pt_win",info.getString("pt_win"));
						entity.put("pt_balance",info.getString("pt_balance"));
						entity.put("pt_gamedate",info.getString("pt_gamedate"));
						entity.put("pt_info",info.getString("pt_info"));
						entity.put("pt_sessionid",info.getString("pt_sessionid"));
						entity.put("pt_progressivebet",info.getString("pt_progressivebet"));
						entity.put("pt_progressivewin",info.getString("pt_progressivewin"));
						entity.put("pt_currentbet",info.getString("pt_currentbet"));
						entity.put("pt_livenetwork",info.getString("pt_livenetwork"));
						entity.put("pt_rnum",info.getString("pt_rnum"));
						entity.put("pt_createtime",sdf.format(new Date()));
						entity.put("Platformflag", agent);
						list.add(entity);
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * 获取参数列表
	 * @param apiUrl 接口链接
	 * @param stardate 开始日期
	 * @param enddate 结束日期
	 * @param page 页面码
	 * @param perPage 显示数量
	 * @return 参数列表
	 */
	private static String getPTParams(String apiUrl,String stardate, String enddate, int page, int perPage){		
		StringBuilder params = new StringBuilder();
		params.append(apiUrl);
		params.append("/startdate/");
		params.append(stardate.replace(" ", "%20"));
		params.append("/enddate/");
		params.append(enddate.replace(" ", "%20"));
		params.append("/frozen/");
		params.append("no");
		params.append("/showinfo/");
		params.append("1");
		params.append("/page/");
		params.append(page);
		params.append("/perPage/");
		params.append(perPage);
		return params.toString();
	}
	
	/**
	 * 获取29分钟前的时间
	 * @return 29分钟前的时间
	 */
	public static String getDate(){
		SimpleDateFormat t = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.MINUTE,c.get(Calendar.MINUTE)-29);	
		return t.format(c.getTime());
	}
	
	public static void main(String[] arg){
		try {
			Map<String,String> info = new HashMap<String, String>();
			//12楼主线
			info.put("url", "https://kioskpublicapi.redhorse88.com/customreport/getdata/reportname/PlayerGames");
//			info.put("fileurl", "/home/key/play.p12");
			info.put("fileurl", "C:/p12/vbet.1114721.p12");
			info.put("keyStore", "iQ3xuZrS");
			info.put("entity", "6e48678444bd2be744bf1db15d8a46dc69fd280311c987b1ef151b2977758681a2f14252b601d0ef0811337bab00e4a27ae7ddbef01c131be8fa73a2a80365e0");
			//String s = "9e5b49e5ab515f8c6033d0f4e5800013c4de7b9e6f712e1eefa15e840298f8156cfea828b8c5137edc95287182b3f4d9c9c81f1f5da2787ff279e79c361eb8e7";
//			String a = "2016-11-16 00:20:00";
//			System.out.println(a.replace(" ", "%20"));
			System.out.println(getPTData("https://kioskpublicapi.redhorse88.com/customreport/getdata/reportname/PlayerGames","6750","2016-10-10 14:32:49","2016-10-10 14:52:49",info));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
