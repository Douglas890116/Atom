package com.hy.pull.common.util.game;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hy.pull.common.util.GameHttpUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DZDYGame {

	/**
	 * 获取数据的方法
	 * @param apiUrl 接口URL
	 * @param channelId 渠道ID
	 * @param md5Key MD5密钥
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param code 代理编码
	 * @return 游戏数据
	 */
	public static List<Map<String, Object>> getData(String apiUrl, String channelId, String md5Key,String startDate,String endDate, String code) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			if(startDate == null){
				startDate = GameHttpUtil.getDate();
				endDate = format.format(new Date());
			}
			if(endDate == null){
				endDate = format.format(new Date());
			}
			long time = System.currentTimeMillis();
			long startTime = format.parse(startDate).getTime();
			long endTime = format.parse(endDate).getTime();
			String encryption = MD5(time+md5Key);
			String postUrl = apiUrl.concat("?s=2").concat("&channelid="+channelId).concat("&time="+time).concat("&encryption="+encryption).concat("&startTime="+startTime).concat("&endTime="+endTime);
			String result = getUrl(postUrl);
			if(result != null){
				if(result.startsWith("{")){
					JSONObject json = JSONObject.fromObject(result).getJSONObject("d");
					String flag = json.getString("code");
					if(flag.equals("0")){
						JSONArray array = json.getJSONArray("record");
						int size = array.size();
						if (size > 0) {
							JSONObject info = null;
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
							Map<String, Object> entity = null;
							for (int i = 0; i < size; i++) {
								info = array.getJSONObject(i);
								entity = new HashMap<String, Object>();
								entity.put("gameID", info.getString("gameID"));
								entity.put("accounts", info.getString("accounts"));
								entity.put("serverName", info.getString("serverName"));
								entity.put("tableID", info.getString("tableID"));
								entity.put("chairID", info.getString("chairID"));
								entity.put("userCount", info.getString("userCount"));
								entity.put("handCard", info.getString("handCard"));
								entity.put("cellScore", info.getString("cellScore"));
								entity.put("allBet", info.getString("allBet"));
								entity.put("profit", info.getString("profit"));
								entity.put("Revenue", info.getString("Revenue"));
								entity.put("GameStartTime", info.getString("GameStartTime"));
								entity.put("GameEndTime", info.getString("GameEndTime"));
								entity.put("channelId", channelId);
								entity.put("createtime", sdf.format(new Date()));
								entity.put("Platformflag", code);
								list.add(entity);
							}
						}
					}else if(flag.equals("130")){
						System.out.println("参数不完整。。。");
					}else if(flag.equals("131")){
						System.out.println("渠道不正确。。。");
					}else if(flag.equals("132")){
						System.out.println("操作超时。。。");
					}else if(flag.equals("133")){
						System.out.println("验证未通过。。。");
					}else if(flag.equals("134")){
						System.out.println("帐号不存在。。。");
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return list;
	}
	

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		String apiUrl = "http://103.230.243.85:96/dataHandle";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String bd = "2017-01-03 00:00:00";
		String ed = "2017-01-04 00:00:00";
		String s = "2";
		String channelid = "iq6sebo0h7c87y0v";
		String md5Key = "miqu*7@";
		long time = System.currentTimeMillis();
		long startTime = format.parse(bd).getTime();
		long endTime = format.parse(ed).getTime();
		String encryption = MD5(time+md5Key);
		System.out.println("开始："+format.format(new Date()));	
		apiUrl = apiUrl.concat("?s="+s).concat("&channelid="+channelid).concat("&time="+time).concat("&encryption="+encryption).concat("&startTime="+startTime).concat("&endTime="+endTime);
		System.out.println(apiUrl);
		String result = getUrl(apiUrl);
		System.out.println("结束："+format.format(new Date()));	
		System.out.println(result);		
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
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return sb.toString();
	}

	/**
	 * MD532位加密
	 * @param sourceStr
	 * @return 加密后的字符串
	 */
	public static String MD5(String sourceStr) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(sourceStr.getBytes("UTF-8"));
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

}
