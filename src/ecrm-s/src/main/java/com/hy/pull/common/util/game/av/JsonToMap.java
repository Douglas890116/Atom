package com.hy.pull.common.util.game.av;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.map.ListOrderedMap;


/**
 * 拉取东方游戏数据工具类
 * 创建日期：2016-10-17
 * @author temdy
 */
public class JsonToMap {

	/**
	 * json转换list
	 * @param jsonStr json字符串
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> parseJSON2List(String jsonStr) {
		JSONArray jsonArr = JSONArray.fromObject(jsonStr);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Iterator<JSONObject> it = jsonArr.iterator();
		while (it.hasNext()) {
			JSONObject json2 = it.next();
			list.add(parseJSON2Map(json2.toString()));
		}
		return list;
	}

	/**
	 * json转换map
	 * @param jsonStrjson字符串
	 * @return Map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseJSON2Map(String jsonStr) {
		ListOrderedMap map = new ListOrderedMap();
		// 最外层解析
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			map.put(k.toString(), v);
		}
		return map;
	}

}
