package com.hy.pull.common.util.game.zj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.hy.pull.common.util.api.Enum_MSG;

import net.sf.json.JSONObject;

/**
 * 拉取ZJ游戏数据工具类
 * 创建日期：2016-10-17 
 * @author temdy
 */
public class ZJUtil {
	
	/**
	 * 获取游戏结果
	 * @param userKey 密钥
	 * @param url 接口URL
	 * @param flag 标志
	 * @return 游戏结果
	 * @throws Exception 抛出异常
	 */
	public static String gameinfo(String userKey, String url, String flag) throws Exception {
		// 添加 请求内容
		JSONObject params = new JSONObject();
		params.put("count", "1000");
		params.put("beginId", flag);
		JSONObject join = new JSONObject();
		join.put("hashCode", userKey);
		join.put("command", "GET_RECORD");
		join.put("params", params);
		return getPost(url, join);
	}

	/**
	 * 获取请求结果
	 * @param posturl 请求链接
	 * @param join json对象
	 * @return 结果
	 * @throws Exception 抛出异常
	 */
	public static String getPost(String posturl, JSONObject join) throws Exception {
		HttpURLConnection connection = null;
		try {
			System.out.println("请求URL："+posturl);
			System.out.println("请求参数："+join);
			// 创建连接
			URL url = new URL(posturl);
			connection = (HttpURLConnection) url.openConnection();
			int timeout = 10000;
			connection.setConnectTimeout(timeout);
			connection.setReadTimeout(timeout);
			
			// 设置http连接属性
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST"); // 可以根据需要 提交
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			// 设置http头 消息
			connection.setRequestProperty("Content-Type", "application/json"); // 设定
																				// 请求格式
																				// json，也可以设定xml格式的
			// connection.setRequestProperty("Content-Type", "text/xml"); //设定
			// 请求格式 xml，
			connection.setRequestProperty("Accept", "application/json");// 设定响应的信息的格式为
			// connection.setRequestProperty("X-Auth-Token","xx");
			// //特定http服务器需要的信息，根据服务器所需要求添加
			connection.connect();
			OutputStream out = connection.getOutputStream();
			out.write(join.toString().getBytes());
			out.flush();
			out.close();
			// 读取响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String lines;
			StringBuffer sb = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			reader.close();
			// // 断开连接
			connection.disconnect();
			return sb.toString();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message("Fatal MalformedURLException: " + e.getMessage()); 
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message("Fatal UnsupportedEncodingException: " + e.getMessage()); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message("Fatal IOException: " + e.getMessage()); 
		}
	}
}
