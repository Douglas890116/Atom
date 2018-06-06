package com.hy.pull.common.util.game;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hy.pull.common.util.game.av.JsonToMap;
import com.hy.pull.common.util.game.zj.ZJUtil;


/**
 * 拉取ZJ游戏数据工具类
 * 创建日期：2016-10-17
 * @author temdy
 */
public class ZJGame {
	
	/**
	 * 获取ZJ游戏数据（每次最大拉取1000条）
	 * @param apiUrl 接口URL
	 * @param agent 代理
	 * @param userKey 密钥
	 * @param flag 标志
	 * @return 游戏数据
	 * @throws Exception 抛出异常
	 */
	public static List<Map<String, Object>> getZJData(String apiUrl,String agent,String userKey,String flag)throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		String hashCode = "lilongapi_05302907-42f9-4701-a427-62";
//		String url = "http://zjapi.cg8080.com/zhouji/app/api.do";
		if(flag == null){
			flag = "0";
		}
		Map<String,Object> info = JsonToMap.parseJSON2Map(ZJUtil.gameinfo(userKey, apiUrl,flag));
		info = JsonToMap.parseJSON2Map(info.get("params").toString());
		if(info.get("recordList") != null){
			List<Map<String,Object>> result = JsonToMap.parseJSON2List(info.get("recordList").toString());
			if(null!=result && result.size()>0){
				list.addAll(getZJData(agent, result));
			}
		}
		return list;
	}

	/**
	 * 获取ZJ游戏数据
	 * @param agent 代理
	 * @param result 结果
	 * @return 游戏数据
	 * @throws Exception 抛出异常
	 */
	private static List<Map<String, Object>> getZJData(String agent, List<Map<String,Object>> result) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		int size = result.size();
		if(size > 0){
			Map<String,Object> entity = null;
			Map<String,Object> info = null;
			for (int i=0; i < size; i++) {
				info = result.get(i);
				entity = new HashMap<String,Object>();
				entity.put("id",info.get("id"));
				entity.put("userName",info.get("userName"));
				entity.put("currency",info.get("currency"));
				entity.put("gameType",info.get("gameType"));
				entity.put("tableInfoId",info.get("tableInfoId"));
				entity.put("shoeInfoId",info.get("shoeInfoId"));
				entity.put("gameInfoId",info.get("gameInfoId"));
				entity.put("tableName",info.get("tableName"));
				entity.put("issueNo",info.get("issueNo"));
				entity.put("bankerResult",info.get("bankerResult").toString());
				entity.put("resultList",info.get("resultList").toString());
				entity.put("pokerList",info.get("pokerList").toString());
				entity.put("stakeAmount", info.get("stakeAmount"));
				entity.put("validStake",info.get("validStake"));
				entity.put("winLoss",info.get("winLoss"));
				entity.put("comm",info.get("comm"));
				entity.put("balanceAfter",info.get("balanceAfter"));
				entity.put("endTime",sdf.format(info.get("endTime")));
				entity.put("ip",info.get("ip"));
				entity.put("Details",info.get("Details"));
				entity.put("betTime",info.get("bettime"));
				entity.put("createtime",sdf.format(new Date()));
				entity.put("Platformflag", agent);
				list.add(entity);
			}
		}
		return list;
	}
	
	public static void main(String[] arg){
		try {
			System.out.println(getZJData("http://zjapi.cg8080.com/zhouji/app/api.do","6750","lilongapi_05302907-42f9-4701-a427-62",""));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
