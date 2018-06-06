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
 * 拉取东方游戏数据工具类
 * 创建日期：2016-10-10
 * @author temdy
 */
public class AOIGame {
	/**
	 * 拉取东方游戏数据列表（接口每次最多返回300条记录）
	 * @param apiUrl 接口链接
	 * @param agent 代理
	 * @param vendorid 最大vendorid
	 * @param userKey MD5密钥
	 * @return 游戏数据列表
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> getAOIData(String apiUrl,String agent,String vendorid,String userKey) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			if(vendorid==null){
				vendorid = "0";
			}
			StringBuilder param = new StringBuilder();
			String p = "agent="+agent+"$vendorid="+vendorid+"$isjs=1$method=gbrbv";
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
					entity.put("aoi_ProductID", info.get("ProductID"));
					entity.put("aoi_UserName", info.get("UserName"));
					entity.put("aoi_GameRecordID", info.get("GameRecordID"));
					entity.put("aoi_OrderNumber", info.get("OrderNumber"));
					entity.put("aoi_TableID", info.get("TableID"));
					entity.put("aoi_Stage", info.get("Stage"));
					entity.put("aoi_Inning", info.get("Inning"));
					entity.put("aoi_GameNameID", info.get("GameNameID"));
					entity.put("aoi_GameBettingKind", info.get("GameBettingKind"));
					entity.put("aoi_GameBettingContent", info.get("GameBettingContent"));
					entity.put("aoi_ResultType", info.get("ResultType"));
					entity.put("aoi_BettingAmount", info.get("BettingAmount"));
					entity.put("aoi_CompensateRate", info.get("CompensateRate"));
					entity.put("aoi_WinLoseAmount", info.get("WinLoseAmount"));
					entity.put("aoi_Balance", info.get("Balance"));
					entity.put("aoi_AddTime", info.get("AddTime"));
					entity.put("aoi_VendorId", info.get("VendorId"));
					entity.put("aoi_ValidAmount", info.get("ValidAmount"));
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
					entity.put("aoi_createtime", sdf.format(new Date()));
					entity.put("aoi_PlatformID", info.get("PlatformID"));
					entity.put("Platformflag", agent);
					list.add(entity);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	public static void main(String[] arg){
		System.out.println(getAOIData("http://cashapi.dg20mu.com/cashapi/getdata.aspx","huanqiuAPI","0","hq@-@688*$-$*!&88$dshxeh"));
	}
}
