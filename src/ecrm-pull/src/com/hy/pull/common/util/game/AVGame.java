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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.service.BaseService.Enum_flag;
import com.hy.pull.service.game.LogUtil;

/**
 * 拉取AV游戏数据工具类
 * 创建日期：2016-10-17
 * @author temdy
 */
public class AVGame {
	private static Logger logger = LogManager.getLogger(AVGame.class.getName());
	
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
			System.out.println("发送 POST 请求出现异常！" + e);
			logger.error(e.getStackTrace());
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
	 * 拉取AV数据列表
	 * @param apiUrl 接口链接
	 * @param agent 代理
	 * @param userKey MD5密钥
	 * @param startdate 开始日期
	 * @param enddate 结束日期
	 * @param code 代理编码
	 * @return 游戏数据列表
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> getAVData(String apiUrl,String agent,String userKey, String stardate, String enddate,String code) {
		List<Map<String, Object>> list = new ArrayList<>();
		if(stardate==null){
			stardate = GameHttpUtil.getDate();
			enddate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		}
		if(enddate==null){
			enddate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		}
		Map<String,Object> result = getAVData(apiUrl,agent,userKey,stardate, enddate, 1,code);
		//System.out.println(result);
		double totalPage = GameHttpUtil.StringToDouble(result.get("totalPage"));
		if(totalPage>0){
			for(int i = 1;i<=totalPage;i++){
				result = getAVData(apiUrl,agent,userKey,stardate, enddate, i,code);
				list.addAll((List<Map<String, Object>>) result.get("info"));
			}
		}
		return list;
	}
	
	/**
	 * 拉取AV数据列表
	 * @param apiUrl 接口链接
	 * @param agent 代理
	 * @param userKey MD5密钥
	 * @param startdate 开始日期
	 * @param enddate 结束日期
	 * @param page 页码
	 * @param code 代理编码
	 * @return 游戏数据列表
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> getAVData(String apiUrl,String agent,String userKey, String startdate,String enddate,int page,String code) {
		try{
			List<Map<String,Object>> betlist = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> winlist = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> list2 = new ArrayList<Map<String,Object>>();
			Map<String,Object> map = new HashMap<String, Object>();
			StringBuilder param = new StringBuilder();
			param.append("operatorID=");
			param.append(agent);
			param.append("&operatorPassword=");
			param.append(userKey);
			param.append("&currency=");
			param.append("CNY");
			param.append("&startDate=");
			Date bdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startdate); 
			String bnow = new SimpleDateFormat("yyyyMMdd").format(bdate);
			Date edate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(enddate);
			String enow = new SimpleDateFormat("yyyyMMdd").format(edate);
			param.append(bnow);
			param.append("&endDate=");
			param.append(enow);
			param.append("&page=");
			param.append(page);
			param.append("&pageSize=");
			param.append("100");			
			String xml = sendPost(apiUrl+"?", param.toString(), agent);
			Document doc = DocumentHelper.parseText(xml); // 将字符串转为XML
			Element rootElt = doc.getRootElement(); // 获取根节点
			List<Element> childList2 = rootElt.elements("response");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			for(Element record:childList2){
				Element records = record.element("records");
				Element pageInfo = record.element("pageInfo");
				map.put("currPage", pageInfo.elementText("currPage"));
				map.put("pageSize", pageInfo.elementText("pageSize"));
				map.put("totalPage", pageInfo.elementText("totalPage"));
				map.put("totalCount", pageInfo.elementText("totalCount"));
				Iterator<Element> iter = records.elementIterator("record"); // 获取根节点下的子节点head
				while (iter.hasNext()) {
					Map<String, Object> info = new HashMap<String, Object>();
					Element itemEle = iter.next();
					info.put("index", itemEle.elementText("index"));
					info.put("tid", itemEle.elementText("tid"));
					info.put("transType", itemEle.elementText("transType"));
					info.put("amount", itemEle.elementText("amount"));
					info.put("userID", itemEle.elementText("userID"));
					info.put("provider", itemEle.elementText("provider"));
					info.put("roundID", itemEle.elementText("roundID"));
					info.put("gameInfo", itemEle.elementText("gameInfo"));
					info.put("history", itemEle.elementText("history"));
					info.put("isRoundFinished", itemEle.elementText("isRoundFinished"));
					info.put("time", itemEle.elementText("datetime"));
					info.put("createtime", sdf.format(new Date()));
					info.put("Platformflag", code);
					String type = itemEle.elementText("transType");
					if("BET".equals(type)){
						betlist.add(info);
					}
					if("WIN".equals(type)){
						winlist.add(info);
					}				
				}
				for(Map<String, Object> infos:betlist){
					String roundID = GameHttpUtil.StringToSpace(infos.get("roundID"));
					String transType = GameHttpUtil.StringToSpace(infos.get("transType"));
					for(Map<String, Object> infoss:winlist){
						String roundID2 = GameHttpUtil.StringToSpace(infoss.get("roundID"));
						String transType2 = GameHttpUtil.StringToSpace(infoss.get("transType"));
						double amountbet = GameHttpUtil.StringToDouble(infos.get("amount"));
						if(roundID.equals(roundID2)&&!transType.equals(transType2)){
							infos.put("transType2", transType2);
							double amountwin = GameHttpUtil.StringToDouble(infoss.get("amount"));
							if(0==amountwin){
								infos.put("amount2", amountbet*-1);
							}else{
								infos.put("amount2", amountwin);
							}
						}
					}
					list2.add(infos);
				}
				map.put("info", list2);
			}
			return map;
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error(ex.getStackTrace());
		}
		return null;
	}
	
	public static void main(String[] arg){
		System.out.println(getAVData("http://api.service.bbtech.asia/API/getBetWinHistory","GSAPICNYHYI","7d32029a8b6a1b8786ded22264a9a4ad","2016-10-17 00:00:00","2016-10-18 00:00:00",""));
	}
}
