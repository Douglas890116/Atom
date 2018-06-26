package com.hy.pull.common.util.game;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.JSONParser;

import com.hy.pull.common.util.Encrypt;
import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.common.util.MoneyHelper;
import com.hy.pull.common.util.game.sa.DeEnCode;
import com.hy.pull.service.BaseService.Enum_flag;
import com.hy.pull.service.game.LogUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * 拉取eBET游戏数据工具类
 * 创建日期：2016-10-10
 * @author temdy
 */
public class EBETGame {

	private static Logger logger = LogManager.getLogger(EBETGame.class.getName());
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
	/**
	 * 获取时间的当前日期
	 * @return
	 */
	private static String getCurrenDate_yyyyMMdd() {
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		return sdf.format(ca.getTime());
	}
	private static Map<String, String> mapGamename = new HashMap<String, String>(){{
		this.put("1", "百家乐");
		this.put("2", "龙虎");
		this.put("3", "骰宝");
		this.put("4", "轮盘");
		this.put("5", "水果机");
	}};
	/**
	 * 拉取新环球游戏数据列表
	 * @param apiUrl 接口链接
	 * @param username 帐号
	 * @param agentname 代理帐号
	 * @param agentpwd 代理密码
	 * @param deskey DES密钥
	 * @param md5key MD5密钥
	 * @param stardate 开始日期
	 * @param enddate 结束日期
	 * @param code 代理编码
	 * @return 游戏数据列表
	 */
	public static List<Map<String, Object>> getEBETData(String apiUrl,String channelid,String subchannelid,String starttime, String endtime,String code,String privateKey) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
//		String starttime = "2017-04-15 00:00:00";//日期ex：2012-03-21 00:00:00
//		String endtime = "2017-04-15 16:00:00";//日期ex：2012-03-21 23:59:59
		
		int pagelimit = 300;//每页数量
		int page = 1;//查询页数
		int totalPage = 1;
		
		Map<String, Object> returnObject = getData( apiUrl, channelid, subchannelid, starttime,  endtime, code, pagelimit, page, privateKey);
		
		if(returnObject == null) {
			return null;
		}
		
		list.addAll((List)returnObject.get("list"));
		
		if(returnObject.containsKey("totalPage")) {
			totalPage = Integer.valueOf(returnObject.get("totalPage").toString());
		}
		
		if(totalPage > 1) {
			//页数循环
			for (int i = 2; i <= totalPage; i++) {
				page ++;
//				System.out.println("当前抓取第"+page+"页：");
				returnObject = getData( apiUrl, channelid, subchannelid, starttime,  endtime, code, pagelimit, page, privateKey);
				if(returnObject == null) {
					return null;
				}
				list.addAll((List)returnObject.get("list"));
			}
		}
		
		return list;
	}
	
	private static Map<String, Object> getData(String apiUrl,String channelid,String subchannelid,String starttime, String endtime,String code, int pagelimit, int page,String privateKey) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> returnObject = new HashMap<>();
		
		int totalPage = 1;
		
		String timestamp = System.currentTimeMillis()+"";
		String signature = sign(timestamp.getBytes(), privateKey);
		
//		System.out.println(privateKey);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("startTimeStr", starttime);
		params.put("endTimeStr", endtime);
		params.put("pageSize", pagelimit+"");//最多上限5000
		params.put("pageNum", page+"");
		params.put("channelId", channelid);
		params.put("subChannelId", subchannelid);
		params.put("signature", signature);
		params.put("timestamp", timestamp);
		
		JSONObject result =  doPostSubmitMap(apiUrl, params, subchannelid);
		System.out.println("eBET结果="+result);
		
		if(result == null) {
			return null;
		}
		
		//	接口调用成功
		if( result.getString("status").equals("200") ) {
			
			int count = result.getInt("count");
			if( count % pagelimit != 0) {
				totalPage = count / pagelimit + 1;
			} else {
				totalPage = count / pagelimit ;
			}
//			System.out.println("拉取eBET数据结果：count="+count+",totalPage="+totalPage);
			returnObject.put("totalPage", totalPage);
			returnObject.put("totalCount", count);
			
			JSONArray jsonArray = result.getJSONArray("betHistories");
			Map<String, Object> item = null;
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i); 
				
				item = new HashMap<String, Object>();
				String gametype = jsonObject.getString("gameType");
				item.put("gametype", gametype);
				item.put("gamename", mapGamename.get(gametype));
				item.put("betmap", jsonObject.getString("betMap"));
				item.put("judgeresult", jsonObject.getString("judgeResult"));
				item.put("userid", jsonObject.getString("userId"));
				item.put("bethistoryid", jsonObject.getString("betHistoryId"));
				item.put("payouttime", jsonObject.getString("payoutTime"));
				item.put("gametime", jsonObject.getString("createTime"));
				item.put("roundno", jsonObject.optString("roundNo"));
				item.put("subchannelid", jsonObject.getString("subChannelId"));
				item.put("validbet", jsonObject.getString("validBet"));
				item.put("payout", jsonObject.getString("payout"));
				item.put("username", jsonObject.getString("username"));
				
				item.put("bankercards", jsonObject.optString("bankerCards"));
				item.put("playercards", jsonObject.optString("playerCards"));
				item.put("alldices", jsonObject.optString("allDices"));
				item.put("dragoncard", jsonObject.optString("dragonCard"));
				item.put("tigercard", jsonObject.optString("tigerCard"));
				item.put("numbercard", jsonObject.optString("number"));
				
				item.put("createtime", new Date());
				item.put("bettime", new Date(Long.valueOf( item.get("gametime").toString()+"000" )));//这个时间戳是10位数的，所以加三个数转换成Java的时间戳
				
				JSONArray array = jsonObject.getJSONArray("betMap");
				double betmoney = 0;
				for (int j = 0; j < array.size(); j++) {
					JSONObject jsonObject2 = array.getJSONObject(j);
					betmoney += jsonObject2.getDouble("betMoney");
				}
				
				item.put("betmoney", betmoney);
				//派彩额里面包含了投注额
				item.put("netmoney", MoneyHelper.doubleFormat(Double.valueOf(item.get("payout").toString()) - betmoney ));
				
				list.add(item);
			}
			
		} else {
			//查找数据失败
		}
		returnObject.put("list", list);
		return returnObject;
	}

	// 私钥匙加密
	private static String sign(byte[] data, String privateKey) {
		try {
			byte[] keyBytes = Base64.getDecoder().decode(privateKey.getBytes());
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
			Signature signature = Signature.getInstance("MD5withRSA");
			signature.initSign(priKey);
			signature.update(data);
			return new String(Base64.getEncoder().encode(signature.sign()));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return null;
	}
	public static JSONObject doPostSubmitMap(String httpUrl,Map<String, String> params, String agent) {
        try {
        	
        	HttpPost httpRequst = new HttpPost(httpUrl);//创建HttpPost对象  
            //填入各个表单域的�?
        	List paramsxx = new ArrayList<BasicNameValuePair>(); 
        	
        	Iterator ite = params.entrySet().iterator();
    		while(ite.hasNext()){
    			Map.Entry<String, String> entry = (Entry<String, String>) ite.next();
    			String key = entry.getKey();//map中的key
    			String value = entry.getValue();//上面key对应的value
    			BasicNameValuePair xxx = new BasicNameValuePair(key,value);
    			paramsxx.add(xxx);
    		}
    		
        	UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(paramsxx,HTTP.UTF_8);
    		httpRequst.setEntity(encodedFormEntity);
    		httpRequst.addHeader("contentType", "application/x-www-form-urlencoded");
    		
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);  
            
            JSONObject jsonObject = new JSONObject();
            if(httpResponse.getStatusLine().getStatusCode() == 200)  {
            	String str = EntityUtils.toString(httpResponse.getEntity());
            	str = new JSONParser().parse(str).toString();
//            	System.out.println(str);
            	jsonObject = JSONObject.fromObject(str);
            } else {
            	jsonObject.put("status", ""+httpResponse.getStatusLine().getStatusCode());
    			jsonObject.put("message", "返回响应码不正确："+EntityUtils.toString(httpResponse.getEntity()));
    			System.out.println("eBET真人出现错误："+jsonObject);
    			logger.error("eBET真人出现错误："+jsonObject);
    			LogUtil.addListLog(LogUtil.HANDLE_EBET, httpUrl+"?" + params.toString(), "eBET真人出现错误："+jsonObject, agent, Enum_flag.异常.value);
    			return null;
            }
            
            return jsonObject;
            
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			LogUtil.addListLog(LogUtil.HANDLE_EBET, httpUrl+"?" + params.toString(), e.getMessage(), agent, Enum_flag.异常.value);
			return null;
		}
        
	}
	
	public static void main(String[] args) throws Exception {
		String apiUrl = "http://haoying.ebet.im:8888/api/userbethistory";
		String channelid = "201";
		String subchannelid = "1247";
		String starttime = "2017-11-14 07:00:00";//日期ex：2012-03-21 00:00:00
		String endtime   = "2017-11-14 17:00:00";//日期ex：2012-03-21 23:59:59
		String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJCflAeD1DFKBYfCCLV6r1IuaCi3J8udjDVFZC4Z07UA2TWMD79a+TA4IWqKNApG7xUFj4pCPClyb9E6pcM2BUA9j35TmDTduvUiJr/cbxkXdeSz9PTDLeudPp2PVjdDwIGdwAOgSS8+cdJBuX/LXzmjnOZj480x4nGruTrdi+uNAgMBAAECgYBupfjeiwjksQpsJJL/Lh9G1ASS6haDXUVxWGbeMppcCIsmwcMml1bBgqBmX9iS6FRxE/EPSb+3wjs0rBc4tHLs3emP8pl41iYwcO5lvNGMfXIgFvsLgoup/vj8IpRmi4Y+E7eiqTuPBwftbGB/2iN9KMCMD+l0ZzlMROyiCqw0zQJBAMUAGH7/5tERDr3bcZ8DD8NBdTUnzVJK182oNOyfWqbBBxH+8dzQn/X0weOyUkO//kCiXKm4fYpVJDZ+gKJhx+cCQQC778RIg/eQAANdjjtG0Jricuj47Twz0risMhKo0/0Uhru1e9XivynrKycGP3C/MykBVzNVPn3FGs6Shp4IA/JrAkByzlB4SsgIFOnDaUy4/37DKrWUqcJ1b9p+JsXZFDEvNxTYvvvS1N4z51TLTpO0mgIhRr27xyGaaW32OBjdCSEHAkEApYdiL6ikVN3eGPncddvof4lMb2usecygwXH9A1xr7Tdaf1eKJIPRLQO+BH++E3nBJSAj43H+Hqwkw+PzrwWi7wJBAIy0ljB4mfSOgFOK+HbUfQBUtSGHEGtndWG3IX1iqUZpow7+cVAE7xG3tv4vAhLEXe+YqxNdnkLT5FX0218p+Mk="; 
		
		List<Map<String, Object>> list = getEBETData(apiUrl, channelid, subchannelid, starttime, endtime, "", privateKey);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}
}
