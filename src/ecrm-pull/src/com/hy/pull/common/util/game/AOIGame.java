package com.hy.pull.common.util.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.service.PullDataService;
import com.hy.pull.service.BaseService.Enum_flag;
import com.hy.pull.service.game.LogUtil;


/**
 * 拉取东方游戏数据工具类
 * 创建日期：2016-10-10
 * @author temdy
 */
public class AOIGame {
	
	private static Logger logger = LogManager.getLogger(AOIGame.class.getName());
	
	/**
	 * 发送POST请求
	 * 
	 * @param url
	 *            请求URL
	 * @param param
	 *            请求参数
	 * @return 响应数据
	 */
	public static String sendPost(String url, String param, String agent) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			// conn.setRequestProperty("ContentType","text/xml;charset=utf-8");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();

			StringBuilder builder = getResponseText(conn.getInputStream(), new StringBuilder());
			result = builder.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			LogUtil.addListLog(LogUtil.HANDLE_AOI, url + "?"+ param, "发送 POST 请求出现异常！" + e.getMessage(), agent, Enum_flag.异常.value);
			
			return null;
			// e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 获取响应数据
	 * 
	 * @param netIps
	 *            InputStream流对象
	 * @param builder
	 *            字符串追加
	 * @return 响应数据
	 * @throws Exception
	 *             抛出异常
	 */
	public static StringBuilder getResponseText(InputStream netIps, StringBuilder builder) throws Exception {
		byte[] buffer = new byte[1024];
		int len;
		while ((len = netIps.read(buffer)) != -1) {
			builder.append(new String(buffer, 0, len, "UTF-8"));
		}
		return builder;

	}
	
	/**
	 * 拉取东方游戏数据列表（接口每次最多返回300条记录）
	 * @param apiUrl 接口链接
	 * @param agent 代理
	 * @param vendorid 最大vendorid
	 * @param userKey MD5密钥
	 * @param code 代理编码
	 * @return 游戏数据列表
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> getAOIData(String apiUrl,String agent,String vendorid,String userKey,String code) {
		List<Map<String, Object>> list = new ArrayList<>();
		StringBuilder param = new StringBuilder();
		try {
			if(vendorid==null){
				vendorid = "0";
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			String p = "agent="+agent+"$vendorid="+vendorid+"$isjs=1$method=gbrbv";
			String params = new String(Base64.encodeBase64(p.toString().getBytes("UTF-8")));
			String key = GameHttpUtil.MD5(params + userKey);
			param.append("params=");
			param.append(params);
			param.append("&key=");
			param.append(key);
			String result = sendPost(apiUrl+"?", param.toString(), agent);
			
			if(result == null){
				return null;
			}
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
					
					entity.put("aoi_createtime", sdf.format(new Date()));
					entity.put("aoi_PlatformID", info.get("PlatformID"));
					entity.put("Platformflag", code);
					list.add(entity);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			LogUtil.addListLog(LogUtil.HANDLE_AOI, apiUrl+"?"+param, ex.getMessage(), agent, Enum_flag.异常.value);
			return null;
		}
		return list;
	}
	
	public static void main(String[] arg){
		System.out.println(getAOIData("http://cashapi.dg20mu.com/cashapi/getdata.aspx","huanqiuAPI","0","hq@-@688*$-$*!&88$dshxeh",""));
	}
}
