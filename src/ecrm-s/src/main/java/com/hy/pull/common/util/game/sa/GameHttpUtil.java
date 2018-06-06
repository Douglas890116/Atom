package com.hy.pull.common.util.game.sa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 游戏拉数据工具类 创建日期：2016-10-08
 * 
 * @author temdy
 *
 */
public class GameHttpUtil {

	/**
	 * 发送POST请求
	 * 
	 * @param url
	 *            请求URL
	 * @param param
	 *            请求参数
	 * @return 响应数据
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			System.out.println("SA请求接口地址："+url+""+param);
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			int timeout = 10000;
			conn.setConnectTimeout(timeout);
			conn.setReadTimeout(timeout);
			
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
	 * MD5加密
	 * 
	 * @param inStr
	 *            待加密字符串
	 * @return 加密后的字符串
	 */
	public static String MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];

		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}

		return hexValue.toString();
	}

	/**
	 * AES加密
	 * 
	 * @param inStr
	 *            待加密字符串
	 * @param key
	 *            密钥
	 * @return 加密后的字符串
	 */
	public static String encrypt(String inStr, String key) {
		try {
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
			String str = new sun.misc.BASE64Encoder().encode(cipher.doFinal(inStr.getBytes("UTF-8")));
			return str.replaceAll("\r|\n", "");
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 生成随机数
	 * 
	 * @param pwd_len
	 *            长度
	 * @return 生成随机数
	 */
	public static String genRandomNum(int pwd_len) {
		// 35是因为数组是从0开始的，26个字母+10个数字
		final int maxNum = 36;
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
				't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		StringBuffer pwd = new StringBuffer();
		Random r = new Random();
		while (count < pwd_len) {
			// 生成随机数，取绝对值，防止生成负数，
			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}

	/**
	 * 获取当前的美东时间
	 * 
	 * @return 美东时间
	 */
	public static String getDateTime_Md() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置日期格式
		Calendar sysCal = Calendar.getInstance();
		sysCal.add(Calendar.HOUR, -12);
		return df.format(sysCal.getTime());
	}

	/**
	 * 获取列表
	 * 
	 * @return 新的列表
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> change(List<Map<String, Object>> data, String table) {
		try{			
			String xmlpath = "com/hy/pull/common/util/game/table/tableName.xml";
			xmlpath = xmlpath.replace("tableName", table);
			InputStream in = ClassLoader.getSystemResourceAsStream(xmlpath);
			SAXReader reader = new SAXReader();
			Document doc = reader.read(in);
			Element root = doc.getRootElement();
			String flag = root.attributeValue("flag");
			if(flag.equals("true")){
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Iterator<Element> iter = root.elementIterator("column"); // 获取根节点下的子节点head
				Map<String, Object> info = null;
				Map<String, Object> entity = null;
				Element itemEle = null;
				int size = data.size();
				if(size > 0){
					for(int i=0;i<size;i++){
						info = new HashMap<String, Object>();
						entity = data.get(i);
						while (iter.hasNext()) {
							itemEle = iter.next();
							info.put(itemEle.attributeValue("name1"), entity.get(itemEle.attributeValue("name2")));
						}
						list.add(info);
					}
				}
				return list;
			}else{
				return data;
			}
		}catch(Exception ex){
			return data;
		}
	}

	public static void main(String[] args) {
		// StringBuilder param = new StringBuilder();
		// param.append("operatorID=");
		// param.append("Haoying");
		// param.append("&operatorPassword=");
		// param.append("e2db61f0210aa179c909ea3ccbc7cee4");
		// param.append("&currency=");
		// param.append("CNY");
		// param.append("&userID=");
		// param.append("cytest001111");

		// String xml =
		// sendPost("http://api.service.bbtech.asia/API/generateSession?",
		// param.toString());
		// String xml =
		// sendPost("http://api.service.bbtech.asia/API/getSession?",
		// param.toString());
		try {

			// String userKey = "Yg8OJw7";
			// System.out.println(getBBINData(null,"oriental","dhyapihq88","3",null,null,userKey));

			// System.out.println(getIBCData("http://cashapi.dg20mu.com/cashapi/getdata.aspx","huanqiuAPI","1414681529531","hq@-@688*$-$*!&88$dshxeh"));

			String s_xmlpath = "com/hy/pull/common/util/game/table/aoi.xml";
			InputStream in = ClassLoader.getSystemResourceAsStream(s_xmlpath);
			SAXReader reader = new SAXReader();
			Document doc = reader.read(in);
			Element root = doc.getRootElement();
			System.out.println(root.attributeValue("flag").equals("false"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取日期或者时间部分
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @return 日期部分
	 */
	public static String getDateOrTime(String dateStr) {
		return getDateOrTime(dateStr, 0, false);
	}

	/**
	 * 获取日期或者时间部分
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @param type
	 *            获取类型（0、日期，1、时间）
	 * @return 日期或者时间部分
	 */
	public static String getDateOrTime(String dateStr, int type) {
		return getDateOrTime(dateStr, 1, false);
	}

	/**
	 * 获取日期或者时间部分
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @param type
	 *            获取类型（0、日期，1、时间）
	 * @param flag
	 *            是否减少2分钟
	 * @return 日期或者时间部分
	 */
	public static String getDateOrTime(String dateStr, int type, boolean flag) {
		String value = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		SimpleDateFormat t = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		if (type == 1) {
			t = new SimpleDateFormat("HH:mm:ss");// 设置日期格式
		}
		try {
			Date date = sdf.parse(dateStr);// 字符串转换成日期对象
			if (type == 1) {
				if (flag) {
					Calendar c = Calendar.getInstance();
					c.setTime(date);
					c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) - 2);
					value = t.format(c.getTime());
				} else {
					value = t.format(date);
				}
			} else {
				value = t.format(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 格式化XML
	 * 
	 * @param elements
	 *            节点对象
	 * @return Map对象
	 */
	public static Map<String, Object> formatXml(Iterator<Element> elements) {
		Map<String, Object> map = new HashMap<String, Object>();
		while (elements.hasNext()) {
			Element ele = (Element) elements.next();
			map.put(ele.attributeValue("name"), ele.getText());
		}
		return map;
	}

	/**
	 * 字符串转Double
	 * 
	 * @param aValue
	 *            待转换的值
	 * @return Double
	 */
	public static double StringToDouble(Object aValue) {
		String value = String.valueOf(aValue);
		if (null == value || "null".endsWith(value) || value.equals("")) {
			return 0;
		}
		return Double.parseDouble(value);
	}

	/**
	 * 转换成空字符串
	 * 
	 * @param aValue
	 *            待转换的值
	 * @return 空字符串
	 */
	public static String StringToSpace(Object aValue) {
		String value = String.valueOf(aValue);
		if (null == value || "null".endsWith(value) || value.equals("")) {
			return "";
		}
		return value;
	}

	/**
	 * 获取10分钟前的时间
	 * 
	 * @return 10分钟前的时间
	 */
	public static String getDate() {
		SimpleDateFormat t = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) - 10);
		return t.format(c.getTime());
	}

}
