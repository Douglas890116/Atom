package com.hy.pull.common.util.game;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hy.pull.common.util.MoneyHelper;
import com.hy.pull.common.util.game.pt.PTUtils;
import com.hy.pull.service.BaseService.Enum_flag;
import com.hy.pull.service.game.LogUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 拉取WIN88的PT游戏数据工具类
 * 创建日期：2016-10-17
 * @author temdy
 */
public class WIN88Game {
	private static Logger logger = LogManager.getLogger(WIN88Game.class.getName());
	/**
	 * 获取PT游戏数据
	 * @param apiUrl 接口链接
	 * @param agent 代理名称
	 * @param stardate 开始日期
	 * @param enddate 结束日期
	 * @param ptinfo 证书对象
	 * @param code 代理编码
	 * @return 数据列表
	 * @throws Exception 抛出异常
	 */
	public static List<Map<String,Object>> getPTData(String apiUrl,String agent,String stardate, String enddate,Map<String, String> ptinfo,String code) throws Exception {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		int perPage = 200; // 每页记录
		
		if(stardate==null){
			stardate = getDate();
			enddate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		}
		if(enddate==null){
			enddate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		}
		String url = getPTParams(apiUrl,stardate,enddate,1,perPage);
		//System.out.println(url);
		String result = PTUtils.getResultByURL(url,ptinfo.get("fileurl"), ptinfo.get("keyStore"),ptinfo.get("entity"),agent);
		if(result == null) {
			return null;
		}
		//System.out.println(result);
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
						list.addAll(getPTData(url,code,ptinfo));
					}
				}
			}
		} else {
			logger.error(result);
			LogUtil.addListLog(LogUtil.HANDLE_PT, url, result, agent, Enum_flag.异常.value);
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
		String result = PTUtils.getResultByURL(apiUrl, ptinfo.get("fileurl"),ptinfo.get("keyStore"), ptinfo.get("entity"),agent);
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
						entity.put("pt_gamecode",info.getString("GAMECODE"));
						entity.put("pt_username",info.getString("PLAYERNAME"));
						entity.put("pt_windowcode",info.getString("WINDOWCODE"));
						
						entity.put("pt_gametype",info.getString("GAMETYPE"));
						
						//Zhao Cai Jin Bao (zcjb)
						String gamename = info.getString("GAMENAME").replace("'", "");
						entity.put("pt_gamename", gamename);
						
						gamename = gamename.split("\\(")[1].replaceAll("\\)", "");
						gamename = gamename.trim();
						entity.put("pt_gameid", gamename);//GAMEID字段是PT返回的他们的ID字段，并无实际作用
						
						entity.put("pt_bet",info.getString("BET"));
						entity.put("pt_currentbet",info.getString("WIN"));//此字段用来存储原生的WIN金额=info.getString("CURRENTBET")
						double win = Double.valueOf(info.getString("WIN"));
						double bet = Double.valueOf(info.getString("BET"));
						entity.put("pt_win", MoneyHelper.doubleFormat(win - bet));//输的时候这里显示0，赢的时候显示盈利
						
						entity.put("pt_balance",info.getString("BALANCE"));
						entity.put("pt_gamedate",info.getString("GAMEDATE"));
						entity.put("pt_info", info.has("INFO") ? info.getString("INFO") : "");
						entity.put("pt_sessionid",info.getString("SESSIONID"));
						entity.put("pt_progressivebet",info.getString("PROGRESSIVEBET"));
						entity.put("pt_progressivewin",info.getString("PROGRESSIVEWIN"));
						
						entity.put("pt_livenetwork",info.getString("LIVENETWORK"));
						entity.put("pt_rnum", info.has("RNUM") ? info.getString("RNUM") : "0");
						entity.put("pt_createtime",sdf.format(new Date()));
						entity.put("Platformflag", agent);
						list.add(entity);
					}
				}
			}
		} else {
			logger.error(result);
			LogUtil.addListLog(LogUtil.HANDLE_PT, apiUrl, result, agent, Enum_flag.异常.value);
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
		params.append("all");
		params.append("/timeperiod/");
		params.append("specify");
		params.append("/sortby/");
		params.append("playername");
//		params.append("/showinfo/");
//		params.append("1");
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
			info.put("entity", "129f28a1a49fbd979bbae88a9742dd916c8adab3d8bbad66219ac374ce7dccd0dd9e01214ee9f85985539ced74cda5cb7a86fe3e6cdf8d17c1ace901114e60fd");
			//String s = "9e5b49e5ab515f8c6033d0f4e5800013c4de7b9e6f712e1eefa15e840298f8156cfea828b8c5137edc95287182b3f4d9c9c81f1f5da2787ff279e79c361eb8e7";
//			String a = "2016-11-16 00:20:00";
//			System.out.println(a.replace(" ", "%20"));
			System.out.println(getPTData("https://kioskpublicapi.redhorse88.com/customreport/getdata/reportname/PlayerGames","VBETCNYHYH","2017-01-09 10:00:00","2017-01-09 10:30:00",info,""));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
