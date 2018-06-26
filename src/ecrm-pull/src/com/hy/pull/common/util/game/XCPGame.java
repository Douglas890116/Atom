package com.hy.pull.common.util.game;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.common.util.game.xcp.XCPUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 拉取XCP游戏数据工具类
 * 创建日期：2016-10-22
 * @author temdy
 */
public class XCPGame {
	private static Logger logger = LogManager.getLogger(XCPGame.class.getName());
	/**
	 * 获取游戏数据列表
	 * @param apiUrl 接口URL
	 * @param agent 代理
	 * @param btime 开始时间
	 * @param etime 结束时间
	 * @param deskey 密钥
	 * @param firstkey 开始KEY
	 * @param lastkey 结束KEY
	 * @param code 代理编码
	 * @return 游戏数据列表
	 * @throws Exception 抛出Exception异常
	 */
	public static List<Map<String, Object>> getXCPGame(String apiUrl, String agent, String btime, String etime, String deskey,
			String firstkey, String lastkey,String code) throws Exception {
		if(apiUrl == null){
			apiUrl = "http://www.22scm.com:8001/_api/webApis2.php?RetailParams=";
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int ipagerecords = 100;
		int icurrpage = 1;
		if(btime==null){
			btime = GameHttpUtil.getDate();
			etime = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).concat(" 23:59:59");
		}
		if(etime==null){
			etime = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).concat(" 23:59:59");
		}
		String result = getData(apiUrl, agent, btime, etime, ipagerecords, icurrpage, deskey, firstkey, lastkey);
		//System.out.println(result);
		JSONObject json = JSONObject.fromObject(result);
		String flag = String.valueOf(json.get("code"));
		if ("1".equals(flag)) {
			String data = json.getString("msg");
			JSONObject jsonmsg = JSONObject.fromObject(data);
			double affects = GameHttpUtil.StringToDouble(jsonmsg.getString("affects"));
			int pagesum = 1;
			if (affects > ipagerecords) {
				pagesum = (int) Math.ceil(affects / ipagerecords);
				for (int i = 1; i <= pagesum; i++) {
					result = getData(apiUrl, agent, btime, etime, ipagerecords, i, deskey, firstkey, lastkey);
					json = JSONObject.fromObject(result);
					data = json.getString("msg");
					list.addAll(getXCPGame(code, data));
				}
			} else {
				list.addAll(getXCPGame(code, data));
			}
		}
		return list;
	}

	/**
	 * 获取游戏数据列表
	 * @param agent 代理
	 * @param data 数据
	 * @return 游戏数据列表
	 * @throws Exception 抛出Exception异常
	 */
	private static List<Map<String, Object>> getXCPGame(String agent, String data) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		JSONObject json = JSONObject.fromObject(data);
		JSONArray array = json.getJSONArray("results");
		int size = array.size();
		if (size > 0) {
			JSONObject info = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			Map<String, Object> entity = null;
			for (int i = 0; i < size; i++) {
				info = array.getJSONObject(i);
				entity = new HashMap<String, Object>();
				entity.put("xcp_projectid", info.getString("projectid"));
				entity.put("xcp_lotteryid", info.getString("lotteryid"));
				entity.put("xcp_username", info.getString("username"));
				entity.put("xcp_issue", info.getString("issue"));
				entity.put("xcp_isgetprize", info.getString("isgetprize"));
				entity.put("xcp_updatetime", info.getString("updatetime"));
				entity.put("xcp_writetime", info.getString("writetime"));
				entity.put("xcp_totalprice", info.getString("totalprice"));
				entity.put("xcp_code", info.getString("code"));
				entity.put("xcp_bonus", info.getString("bonus"));
				entity.put("xcp_codetimes", info.getString("codetimes"));
				entity.put("xcp_prize", info.getString("prize"));
				entity.put("xcp_createtime", sdf.format(new Date()));
				entity.put("Platformflag", agent);
				list.add(entity);
			}
		}
		return list;
	}

	/**
	 * 获取游戏数据的方法
	 * @param apiUrl 接口URL
	 * @param agent 代理
	 * @param btime 开始时间
	 * @param etime 结束时间
	 * @param ipagerecords 拉取记录数量
	 * @param icurrpage 当前页码
	 * @param deskey 密钥
	 * @param firstkey 开始KEY
	 * @param lastkey 结束KEY
	 * @return 游戏数据
	 * @throws Exception 抛出Exception异常
	 */
	private static String getData(String apiUrl, String agent, String btime, String etime, int ipagerecords,
			int icurrpage, String deskey, String firstkey, String lastkey) throws Exception {
		StringBuilder param = new StringBuilder();
		param.append("type=66");
		param.append("&agent=");
		param.append(agent);
		param.append("&btime=");
		param.append(btime);
		param.append("&etime=");
		param.append(etime);
		param.append("&ipagerecords=");
		param.append(ipagerecords);
		param.append("&icurrpage=");
		param.append(icurrpage);
		param.append("&time=");
		param.append(System.currentTimeMillis());
		//System.out.println(param.toString());
		String des = XCPUtil.encrypt(param.toString(), deskey);
		String md5 = XCPUtil.MD5(firstkey.concat(des).concat(lastkey));
		String url = apiUrl.concat(md5).concat(des);
		//System.out.println(url);
		return XCPUtil.getUrl(url);
	}
	
	public static void main(String[] agrs){
		try {
			System.out.println(getXCPGame("http://www.webapi888.com/_api/webApis.php?RetailParams=","APIZDDWGJ","2017-01-01 15:00:00","2017-01-01 16:59:59","abcd1234","bfi3j8effifjnwzi","0lx499eliuds83oj",""));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
