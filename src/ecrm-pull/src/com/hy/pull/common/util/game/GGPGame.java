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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.common.util.game.sa.DeEnCode;
import com.hy.pull.service.BaseService.Enum_flag;
import com.hy.pull.service.game.LogUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * GGPoker游戏
 * @author Administrator
 *
 */
public class GGPGame {

	private static Logger logger = LogManager.getLogger(GGPGame.class.getName());
	
	private static final String pattern2 = "yyyy-MM-dd";
	public static SimpleDateFormat foo2 = new SimpleDateFormat(pattern2);
	
	private static final String pattern = "yyyyMMdd";
	public static SimpleDateFormat foo = new SimpleDateFormat(pattern);
	
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
	public static List<Map<String, Object>> getData(String apiUrl, String siteId, String SecretKey, String fromDate,String toDate, String code) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try{
			
//			String fromDate = "2017-04-23";//yyyy-MM-dd
//			String toDate = "2017-04-23";//yyyy-MM-dd
			
			
			if(!fromDate.equals(toDate)) {//跨日期采集汇总数据，需要拆分
				Date startDate = foo2.parse(fromDate);
				Date endDate = foo2.parse(toDate);
				
				//Date1.after(Date2),当Date1大于Date2时，返回TRUE，当小于等于时，返回false
				//Date1.before(Date2)，当Date1小于Date2时，返回TRUE，当大于等于时，返回false
				while( !startDate.after(endDate)) {
					List list2 = getData(apiUrl, siteId, SecretKey, foo2.format(startDate), foo2.format(startDate));
					if(list2 == null) {
						return null;
					}
					list.addAll(list2);
					
					//+1天
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(startDate);
					calendar.add(Calendar.DATE, +1);
					startDate = calendar.getTime();
				}
			    
			} else {
				List list2 = getData(apiUrl, siteId, SecretKey, fromDate, toDate);
				if(list2 == null) {
					return null;
				}
				list.addAll(list2);
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error(ex.getStackTrace());
			LogUtil.addListLog(LogUtil.HANDLE_GGPoker, apiUrl, ex.getMessage(), siteId, Enum_flag.异常.value);
			return null;
		}
		return list;
	}
	
	private static List<Map<String, Object>> getData(String apiUrl, String siteId, String SecretKey, String fromDate,String toDate) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try{
			
			String str = siteId+fromDate+""+toDate+SecretKey;
			String fingerprint = DeEnCode.string2MD5(str);
			
			//String result = getUrl(apiUrl + "/ngr?siteId="+siteId+"&fromDate="+fromDate+"&toDate="+toDate+"&fingerprint="+fingerprint, siteId);
			String result = getUrl(apiUrl + "/ggr?siteId="+siteId+"&fromDate="+fromDate+"&toDate="+toDate+"&fingerprint="+fingerprint, siteId);
			if(result.equals("-1")) {
				return null;
			}
			
			JSONObject jsonObject = JSONObject.fromObject(result);
//			System.out.println(fromDate+"调用结果："+jsonObject.getJSONArray("data"));
//			logger.error(fromDate+"调用结果："+jsonObject.getJSONArray("data"));
			JSONArray array = jsonObject.getJSONArray("data");
			for (int i = 0; i < array.size(); i++) {
				JSONObject object = array.getJSONObject(i);
//				System.out.println(object);
				
//				{"username":"dca24aa4-7222-49cd-a76f-0781ac32002b","ngr":5.76}
				
				String gamedate = fromDate.replaceAll("-", "");
				
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("lsh", gamedate + object.getString("username"));//确保每人每天只有一条记录
				data.put("username", object.getString("username"));
				//String ngr = object.getString("ngr");
				String ngr = object.getString("ggr");
				if(ngr == null || ngr.equals("null") || ngr.equals("")) {
					ngr = "0.00";
				}
				data.put("ngr", ngr);
				data.put("gamedate", gamedate);
				list.add(data);
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error(ex.getStackTrace());
			LogUtil.addListLog(LogUtil.HANDLE_GGPoker, apiUrl, ex.getMessage(), siteId, Enum_flag.异常.value);
			return null;
		}
		return list;
	}

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		
		String fromDate = "2017-04-23";//yyyy-MM-dd
		String toDate = "2017-05-22";//yyyy-MM-dd
		
		if(!fromDate.equals(toDate)) {//跨日期采集汇总数据，需要拆分
			Date startDate = foo2.parse(fromDate);
			Date endDate = foo2.parse(toDate);
			
			while( !startDate.after(endDate)) {
				System.out.println("start="+foo2.format(startDate));
				//+1天
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(startDate);
				calendar.add(Calendar.DATE, +1);
				startDate = calendar.getTime();
			}
		}
	}
	
	/**
	 * 获取请求数据
	 * @param url 请求URL
	 * @return 请求数据
	 */
	public static String getUrl(String url,String siteId) {
		StringBuilder sb = new StringBuilder();
		try {
			InputStream in = new URL(url).openStream();			
			sb = GameHttpUtil.getResponseText(in, new StringBuilder());			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getStackTrace());
			LogUtil.addListLog(LogUtil.HANDLE_GGPoker, url, e.getMessage(), siteId, Enum_flag.异常.value);
			return "-1";
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
			logger.error(e.getStackTrace());
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getStackTrace());
			e.printStackTrace();
		}
		return result;
	}

}
