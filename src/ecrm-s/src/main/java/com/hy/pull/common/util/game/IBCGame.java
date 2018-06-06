package com.hy.pull.common.util.game;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.hy.pull.common.util.GameHttpUtil;

/**
 * 拉取沙巴体育游戏数据工具类
 * 创建日期：2016-10-10
 * @author temdy
 */
public class IBCGame {
	/**
	 * 拉取沙巴体育游戏数据列表（接口每次最多返回50条记录）
	 * @param apiUrl 接口链接
	 * @param agent 代理
	 * @param vendorid 最大vendorid
	 * @param userKey MD5密钥
	 * @return 游戏数据列表
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> getIBCData(String apiUrl,String agent,String vendorid,String userKey) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			if(vendorid==null){
				vendorid = "0";
			}
			StringBuilder param = new StringBuilder();
			String p = "agent="+agent+"$vendorid="+vendorid+"$isjs=1$method=gsbrbv";
			String params = new String(Base64.encodeBase64(p.toString().getBytes("UTF-8")));
			String key = GameHttpUtil.MD5(params + userKey);
			param.append("params=");
			param.append(params);
			param.append("&key=");
			param.append(key);
			String result = GameHttpUtil.sendPost(apiUrl+"?", param.toString());
			if(result != null){
				Document xmlDoc = DocumentHelper.parseText(result);
				Element root = xmlDoc.getRootElement();
				Iterator<Element> iter = root.elementIterator();
				Map<String, Object> info = null;
				Map<String, Object> entity = null;
				while (iter.hasNext()) {
					Element itemEle = (Element) iter.next();
					Iterator<Element> elements = itemEle.elementIterator();
					info = GameHttpUtil.formatXml(elements);
					entity = new HashMap<String, Object>();
					entity.put("ibc_rowguid",info.get("rowguid"));
					entity.put("ibc_ballid",info.get("ballid"));
					entity.put("ibc_balltime",info.get("balltime"));
					entity.put("ibc_content",info.get("content"));
					entity.put("ibc_curpl",info.get("curpl"));
					entity.put("ibc_ds",info.get("ds"));
					entity.put("ibc_dxc",info.get("dxc"));
					entity.put("ibc_isbk",info.get("isbk"));
					entity.put("ibc_iscancel",info.get("iscancel"));
					entity.put("ibc_isdanger",info.get("isdanger"));
					entity.put("ibc_isjs",info.get("isjs"));
					entity.put("ibc_lose",info.get("lose"));
					entity.put("ibc_matchid",info.get("matchid"));
					entity.put("ibc_moneyrate",info.get("moneyrate"));
					entity.put("ibc_orderid",info.get("orderid"));
					entity.put("ibc_recard",info.get("recard"));
					entity.put("ibc_result",info.get("result"));
					entity.put("ibc_rqc",info.get("rqc"));
					entity.put("ibc_rqteam",info.get("rqteam"));
					entity.put("ibc_sportid",info.get("sportid"));
					entity.put("ibc_tballtime",info.get("tballtime"));
					entity.put("ibc_thisdate",info.get("thisdate"));
					entity.put("ibc_truewin",info.get("truewin"));
					entity.put("ibc_tzip",info.get("tzip"));
					entity.put("ibc_tzmoney",info.get("tzmoney"));
					entity.put("ibc_tzteam",info.get("tzteam"));
					entity.put("ibc_tztype",info.get("tztype"));
					entity.put("ibc_updatetime",info.get("updatetime"));
					entity.put("ibc_username",info.get("username"));
					entity.put("ibc_win",info.get("win"));
					entity.put("ibc_zdbf",info.get("zdbf"));
					entity.put("ibc_vendorid",info.get("vendorid"));
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
					entity.put("ibc_createtime", sdf.format(new Date()));
					entity.put("Platformflag", agent);
					list.add(entity);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
}
