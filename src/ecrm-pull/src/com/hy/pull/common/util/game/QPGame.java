package com.hy.pull.common.util.game;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.DocumentException;

import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.common.util.game.qp.QPUtil;
import com.hy.pull.service.BaseService.Enum_flag;
import com.hy.pull.service.game.LogUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 拉取棋牌游戏数据工具类 
 * 创建日期：2016-12-16
 * @author temdy
 */
public class QPGame {
	private static Logger logger = LogManager.getLogger(QPGame.class.getName());
	/**
	 * 获取棋牌游戏数据
	 * @param apiUrl 接口URL
	 * @param agent 代理帐号
	 * @param key 密钥
	 * @param StartTime 代理帐号
	 * @param EndTime 参数列表
	 * @param code 代理编码
	 * @return 数据列表
	 */
	public static List<Map<String,Object>> getData(String apiUrl,String agent,String key,String StartTime,String EndTime,String code){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String result = null;
		String data = null;
		try {
			SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//SSS
			if(StartTime == null){
				StartTime = GameHttpUtil.getDate();
			}
			if(EndTime == null){
				EndTime = s1.format(new Date());
			}
			JSONObject params = new JSONObject();
			params.put("AgentId", agent);
			params.put("StartTime", StartTime);
			params.put("EndTime", EndTime);
			apiUrl = apiUrl.concat("?a=" + agent + "&m=GetGameRecord");		
			data = QPUtil.Encrypt(params.toString(), key);
			result = post(apiUrl, data, agent);
			
			if(result == null) {
				return null;
			}
			
			//System.out.println(result);
			if(result != null){
				if(result.startsWith("{")){	
					JSONObject object = JSONObject.fromObject(result);
					String flag = object.getString("flag");
					if(flag.equals("1")){
						JSONArray array = JSONArray.fromObject(object.getString("content"));
						int size = array.size();
						if (array.size() > 0) {
							JSONObject json = null;
							Map<String, Object> entity = null;
							for (int i = 0; i < size; i++) {
								json = array.getJSONObject(i);
								entity = new HashMap<String,Object>();
								entity.put("TurnNumber",json.getString("TurnNumber"));
								entity.put("ServerID",json.getString("ServerID"));
								entity.put("KindID",json.getString("KindID"));
								entity.put("RoomName",json.getString("RoomName"));
								
								String StartTimex = json.getString("StartTime");
								String EndTimex = json.getString("EndTime");
								if(StartTimex.length() > 19) {
									StartTimex = StartTimex.substring(0,19);
								}
								if(EndTimex.length() > 19) {
									EndTimex = EndTimex.substring(0,19);
								}
								entity.put("StartTime", s1.format(s2.parse(StartTimex)) );
								entity.put("EndTime", s1.format(s2.parse(EndTimex)) );
								entity.put("Score",json.getString("Score"));
								entity.put("Revenue",json.getString("Revenue"));
								entity.put("TurnScore",json.getString("TurnScore"));								
								entity.put("Account",json.getString("Account"));
								entity.put("createtime",s1.format(new Date()));
								entity.put("Platformflag", code);
								list.add(entity);
							}
						}
					} else {
						logger.error(result);
						LogUtil.addListLog(LogUtil.HANDLE_QP, apiUrl +"?" + data, result, agent, Enum_flag.异常.value);
					}
				}
			}
		}catch(Exception ex){
			logger.error(ex);
			ex.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_QP, apiUrl +"?" + data, result, agent, Enum_flag.异常.value);
			return null;
		}
		return list;
	}
	
	/**
	 * Post提交
	 * @param httpUrl 接口URL
	 * @param data 提交数据
	 * @return 结果
	 */
	@SuppressWarnings("deprecation")
	private static String post(String httpUrl,String data, String agent) {
		String result = null;
		int statusCode = 0;
        try {
        	HttpPost httpRequst = new HttpPost(httpUrl);//创建HttpPost对象
            //填入各个表单域
        	List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();         	
        	BasicNameValuePair bvp = new BasicNameValuePair("d",data);
			params.add(bvp);    		
        	UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(params,HTTP.UTF_8);
    		httpRequst.setEntity(encodedFormEntity);
    		CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(httpRequst);
            statusCode = response.getStatusLine().getStatusCode();
            //System.out.println(statusCode);
			if(statusCode == HttpStatus.SC_OK){
				result = EntityUtils.toString(response.getEntity());
			} else {
				logger.error(result);
				LogUtil.addListLog(LogUtil.HANDLE_QP, httpUrl +"?" + data, result, agent, Enum_flag.异常.value);
			}
			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_QP, httpUrl +"?" + data, "statusCode="+statusCode, agent, Enum_flag.异常.value);
		}
        return result;
	}
	
	public static void main(String[] args) throws DocumentException {
		String apiUrl = "http://diwangqp99.com";
		String agent = "1";
		String key = "HY345YDLop#jkljl";
		//调用测试
//		System.out.println(getData(apiUrl,agent,key,"2017-01-01 14:00:00","2017-01-01 15:00:00",""));
		SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		System.out.println(s2.format(new Date()));
		
		System.out.println("yyyy-MM-dd HH:mm:ss".length());
	}
}
