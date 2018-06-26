package com.hy.pull.common.util.game.mg;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 拉取MG游戏数据工具类(时间间隔最长1小时)
 * 创建日期：2016-10-21
 * @author temdy
 */
public class MGUtil {
	
	
	
	public static void main(String[] a){
		JSONArray array = JSONArray.fromObject("[]");//获取列表
		//System.out.println(getToken(BASE_PATH,"HYHCNYA","VMPj2GN9Fdbh"));
	}
}
