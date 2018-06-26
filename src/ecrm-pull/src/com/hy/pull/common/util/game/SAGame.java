package com.hy.pull.common.util.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
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
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.common.util.game.sa.DeEnCode;
import com.hy.pull.common.util.game.sa.MD5Encoder;
import com.hy.pull.service.BaseService.Enum_flag;
import com.hy.pull.service.game.LogUtil;

/**
 * 拉取沙龙游戏数据工具类
 * 创建日期：2016-10-10
 * @author temdy
 */
public class SAGame {
	private static Logger logger = LogManager.getLogger(SAGame.class.getName());
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
			logger.error(e);
			e.printStackTrace();
			System.out.println("发送 POST 请求出现异常！" + e);
			LogUtil.addListLog(LogUtil.HANDLE_SA, url + "?"+ param, "发送 POST 请求出现异常！" + e.getMessage(), agent, Enum_flag.异常.value);
			
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
	 * 拉取沙龙游戏数据的方法
	 * @param apiUrl 请求接口URL
	 * @param agent 代理帐号
	 * @param key 用户KEY
	 * @param desKey 密钥字符串
	 * @param md5Key md5密钥
	 * @param stardate 开始日期
	 * @param enddate 结束日期
	 * @param code 代理编码
	 * @return 返回数据列表
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map<String,Object>> getSAData(String apiUrl,String agent,String key,String desKey,String md5Key, String stardate, String enddate,String code) {
		StringBuffer params = new StringBuffer();
		try{			
			if(apiUrl==null){
				apiUrl = "http://api.sa-gaming.net/api/api.aspx";
			}
			String method = "GetAllBetDetailsForTimeIntervalDV";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String Time = sdf.format(new Date());
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String fromTime = stardate;
			String toTime = enddate;
			String QS = "method="+method+"&Key="+key+"&Time="+Time+"&Checkkey="+agent+"&FromTime="+fromTime+"&ToTime="+toTime; 
//			System.out.println(QS);
			String q = DeEnCode.encrypt(QS, desKey);
			q=URLEncoder.encode(q,"GBK");
			String a = (QS+md5Key+Time+key);
			String s = MD5Encoder.encode(a);
			
			params.append("q=").append(q);
			params.append("&s=").append(s);
			//System.out.println(apiUrl+"?"+params.toString());
			// 调用接口
			String result = sendPost(apiUrl+"?", params.toString(), agent);
			
			if(result == null) {
				return null;
			}
			//System.out.println(result);
			if(result != null){
				//System.out.println("SA数据："+result);
				Document doc = DocumentHelper.parseText(result); //将字符串转为XML
				Element rootElt = doc.getRootElement(); // 获取根节点
				String msg = rootElt.element("ErrorMsgId").getText();
				if ("0".equals(msg)) {
					List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
					Element BetDetailList = rootElt.element("BetDetailList"); //获取根节点
					Iterator iter = BetDetailList.elementIterator("BetDetail"); //获取根节点下的子节点BetDetail
					Map<String, Object> entity = null;
					while (iter.hasNext()) {
						entity = new HashMap<String, Object>();
						Element itemEle = (Element) iter.next();
						entity.put("BetTime", itemEle.elementText("BetTime"));
						entity.put("PayoutTime", itemEle.elementText("PayoutTime"));
						entity.put("Username", itemEle.elementText("Username"));
						entity.put("HostID", itemEle.elementText("HostID"));
						entity.put("GameID", itemEle.elementText("GameID"));
						entity.put("Round", itemEle.elementText("Round"));
						entity.put("Set", itemEle.elementText("Set"));
						entity.put("BetID", itemEle.elementText("BetID"));
						
						entity.put("GameType", itemEle.elementText("GameType"));
						entity.put("BetType", itemEle.elementText("BetType"));
						entity.put("BetSource", itemEle.elementText("BetSource"));
						entity.put("State", itemEle.elementText("State"));
						entity.put("Detail", itemEle.elementText("Detail"));
						entity.put("createtime", sdf.format(new Date()));
						
						entity.put("BetAmount", itemEle.elementText("BetAmount"));//投注额
						entity.put("ResultAmount", itemEle.elementText("ResultAmount"));
						entity.put("validbet", itemEle.elementText("Rolling"));//洗码量，有效投注额
						
						entity.put("Platformflag", code);
						list.add(entity);
					}
					return list;
				} else {
					logger.error(result);
					LogUtil.addListLog(LogUtil.HANDLE_SA, apiUrl+"?" + params, result, agent, Enum_flag.异常.value);
				}
			}
		}catch(Exception ex){
//			System.out.println("xml格式转换异常："+ ex);
			logger.error(ex);
			LogUtil.addListLog(LogUtil.HANDLE_SA, apiUrl+"?" + params, "xml格式转换异常："+ ex.getMessage(), agent, Enum_flag.异常.value);
		}		
		return null;
	}
	
	public static void main(String[] ag){
		System.out.println(getSAData("http://api.sa-gaming.net/api/api.aspx","hygjgame20168888","32C3DD24BF3A4EDDB59240CCBA7E3975","g9G16nTs","GgaIMaiNNtg", "2016-11-29 10:01:00", "2016-11-29 14:18:00",""));
		//System.out.println(getSAData("http://api.eval.sa-gaming.net/api/api.aspx","hygjgame20168888","13CFB2D28F804318BE3BEBFE9887D992","g9G16nTs","GgaIMaiNNtg", "2016-10-20 16:36:00", "2016-10-25 16:36:00"));
	}
}
