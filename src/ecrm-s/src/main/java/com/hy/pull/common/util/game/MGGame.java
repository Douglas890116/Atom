package com.hy.pull.common.util.game;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.common.util.game.mg.MGUtil;

/**
 * 拉取MG游戏数据工具类 
 * 创建日期：2016-10-21
 * @author temdy
 */
public class MGGame {
	/**
	 * 拉取MG游戏数据列表
	 * @param apiUrl 接口URL
	 * @param agent 代理
	 * @param username 帐号
	 * @param password 密码
	 * @param startime 开始日期
	 * @param endtime 结束
	 * @return 游戏数据列表
	 */
	public static List<Map<String, Object>> getMGData(String apiUrl, String agent,String username,String password,String startime,String endtime) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(startime == null){
				startime = GameHttpUtil.getDate();
			}
			if(endtime == null){
				endtime = s1.format(new Date());
			}
			list.addAll(MGUtil.gameinfo(apiUrl,agent, username, password, startime, endtime));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	public static void main(String[] args) {
		System.out.println(getMGData("https://ag.adminserv88.com/lps","HYCNY","HYCNY","Password123","2016-11-11 00:00:00",null));
	}
}
