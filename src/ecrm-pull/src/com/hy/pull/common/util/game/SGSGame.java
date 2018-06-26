package com.hy.pull.common.util.game;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hy.pull.common.util.DateUtil;
import com.hy.pull.common.util.SGSUtil;
import com.hy.pull.common.util.game.av.JsonToMap;
import com.hy.pull.common.util.game.zj.ZJUtil;
import com.hy.pull.service.BaseService.Enum_flag;
import com.hy.pull.service.game.LogUtil;

/**
 * 申博拉数据
 * 创建日期：2016-10-17
 * @author temdy
 */
public class SGSGame {
	private static Logger logger = LogManager.getLogger(SGSGame.class.getName());
	private static String getHttpUrlDataByOrder(String API_URL,String client_id, String client_secret){
		
		HttpMethod method = null;  
        try {  
            org.apache.commons.httpclient.URI uri = new org.apache.commons.httpclient.URI(API_URL);  
            org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();  
            HostConfiguration hcfg = new HostConfiguration();  
            hcfg.setHost(uri);  
            
            client.setHostConfiguration(hcfg);  
            // 参数验证  
            client.getParams().setAuthenticationPreemptive(true);  
            // GET请求方式  
            method = new GetMethod(API_URL);  
            
            String X_Sgs_Date = SGSUtil.getIsoDateCurren();
			String StringToSign = client_secret + X_Sgs_Date;
			String Signature = SGSUtil.getSignature(client_secret, StringToSign);  
			String Authorization = "SGS" + " " + client_id + ":" + Signature;
            method.addRequestHeader("Authorization", Authorization);
            method.addRequestHeader("X-Sgs-Date", X_Sgs_Date);
            
            System.out.println("请求URL："+API_URL);
            org.apache.commons.httpclient.Header[] headers = method.getRequestHeaders();
			System.out.println("请求Header数据：");
			for (org.apache.commons.httpclient.Header header : headers) {
				System.out.println(header.getName()+"="+header.getValue());
			}
            
            client.executeMethod(method);  
            return method.getResponseBodyAsString();  
        } catch (Exception e) {  
        	logger.error(e);
            e.printStackTrace();  
            LogUtil.addListLog(LogUtil.HANDLE_SGS, API_URL, e.getMessage(), client_id, Enum_flag.异常.value);
            return "-1";
        }  
	
	}
	/**
	 * 获取游戏结果
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	private static String getRecord(String API_URL,String client_id, String client_secret,String startdate,String enddate) {
		try{
			
			
			
			
			String url = API_URL.concat("/api/report/bethistory").concat("?startdate="+startdate+"&enddate="+enddate+"&includetestplayers=true&issettled=true");
			String result = getHttpUrlDataByOrder(url,client_id,client_secret);
			
//			System.out.println("startdate="+SGSUtil.getDateCurrenStart1hour());
//			System.out.println("enddate="+SGSUtil.getDateCurren());
//			System.out.println("请求URL："+url);
//			System.out.println("调用结果："+result);
			
			if(result.equals("-1")) {
				return result;
			} else if(!result.contains("errdesc")) {
				return result;
			} else {
				return "-1";
			}
			
		}catch(Exception ex){
			logger.error(ex);
			ex.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_SGS, API_URL, ex.getMessage(), client_id, Enum_flag.异常.value);
			return "-1";
		}
	}
	
	/**
	 * 获取ZJ游戏数据（每次最大拉取1000条）
	 * @param apiUrl 接口URL
	 * @param agent 代理
	 * @param userKey 密钥
	 * @param flag 标志
	 * @param code 代理编码
	 * @return 游戏数据
	 * @throws Exception 抛出异常
	 */
	public static List<Map<String, Object>> getSGSData(String API_URL, String client_id,String client_secret,String code,String startdate,String enddate)throws Exception {
		List<Map<String, Object>> list = new ArrayList<>();
		
//		http://<server>/api/report/bethistory?userid=12345&startdate=2016-10-24T12:34:56%2B08:00&enddate=2016-10-26T12:34:56%2B08:00&includetestplayers=true&issettled=false
		
		String data = getRecord(API_URL, client_id, client_secret,startdate,enddate);
		System.out.println("处理前："+data);
		if(data == null) {
			return null;
		}
		
		
//		data = data.replaceFirst("ugsbetid,txid,betid,beton,betclosedon,betupdatedon,timestamp,roundid,roundstatus,userid,username,riskamt,winamt,winloss,beforebal,postbal,cur,gameprovider,gameprovidercode,gamename,gameid,platformtype,ipaddress,bettype,playtype,playertype,turnover,validbet", "").trim();//去除标题行
//		data = data.replaceFirst("ugsbetid,txid,betid,beton,betclosedon,betupdatedon,timestamp,roundid,roundstatus,userid,username,riskamt,winamt,winloss,beforebal,postbal,cur,gameprovider,gameprovidercode,gamename,gameid,platformtype,ipaddress,bettype,playtype,playertype", "").trim();//去除标题行
//		data = data.replaceFirst("ugsbetid,txid,betid,beton,betclosedon,betupdatedon,timestamp,roundid,roundstatus,userid,username,riskamt,winamt,winloss,beforebal,postbal,cur,gameprovider,gamename,gameid,platformtype,ipaddress,bettype,playtype", "").trim();//去除标题行
		data = data.replaceFirst("ugsbetid,txid,betid,beton,betclosedon,betupdatedon,timestamp,roundid,roundstatus,userid,username,riskamt,winamt,winloss,beforebal,postbal,cur,gameprovider,gameprovidercode,gamename,gameid,platformtype,ipaddress,bettype,playtype,playertype,turnover,validbet", "").trim();//去除标题行
		data.replaceFirst("\r\n", "");
//		System.out.println("处理后："+data);
		
		if( !data.equals("0") && !data.equals("")){
			list.addAll(getSGSData(code, data));
		}
		return list;
	}

	/**
	 * 获取ZJ游戏数据
	 * @param agent 代理
	 * @param result 结果
	 * @return 游戏数据
	 * @throws Exception 抛出异常
	 */
	private static SimpleDateFormat sdf = new SimpleDateFormat("d/M/yy h:m:s aa",Locale.ENGLISH);// 设置日期格式
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
	private static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZ");// 设置日期格式
	private static List<Map<String, Object>> getSGSData(String agent, String result) throws Exception {
		List<Map<String, Object>> list = new ArrayList<>();
		
		String[] datas = result.split("\r\n");//多行
		
		int size = datas.length;
		if(size > 0){
			Map<String,Object> entity = null;
			String[] info = null;
			for (int i=0; i < size; i++) {
				System.out.println("SGS=========="+datas[i]);
				if(datas[i].trim().equals("")) {
					continue;
				}
				
				info = datas[i].split(",");
//				System.out.println(info);
				entity = new HashMap<String,Object>();
				
				entity.put("ugsbetid",info[0]);
				entity.put("txid",info[1]);
				entity.put("betid",info[2]);
				
				entity.put("beton", sdf2.parse(info[3].replaceAll("T", " ").substring(0, 19)));
				entity.put("betclosedon",sdf2.parse(info[4].replaceAll("T", " ").substring(0, 19)));
				entity.put("betupdatedon",sdf2.parse(info[5].replaceAll("T", " ").substring(0, 19)));
				entity.put("timestamp",sdf2.parse(info[6].replaceAll("T", " ").substring(0, 19)));
				
				entity.put("roundid",info[7]);
				entity.put("roundstatus",info[8]);
				entity.put("userid",info[9]);
				entity.put("username",info[10]);
//				Win loss 是贏的金額
//				Winamt 是贏的金額加您下注的金額
				entity.put("riskamt",info[11]);//投注记录，返回的是负数？
				entity.put("winamt",info[12]);//winamt = winloss + riskamt（注意，该值是负数，所以这里的winamt其实是winloss-riskamt）
				entity.put("winloss",info[13]);
				entity.put("beforebal",info[14]);
				entity.put("postbal",info[15]);
				entity.put("cur",info[16]);
				entity.put("gameprovider",info[17]);
				entity.put("gamename",info[18]);
				entity.put("gameid",info[19]);
				entity.put("platformtype",info[20]);
				entity.put("ipaddress",info[21]);
				entity.put("bettype",info[22]);
				entity.put("playtype",info.length>23 ? info[23] : "");//playtype参数可能为空
				
				entity.put("createtime", new Date());//自定义字段，投注时间
				entity.put("betmoney", Math.abs(Double.valueOf(info[11])));//自定义字段，投注额
				entity.put("netmoney", info[13]);//自定义字段，输赢金额
				entity.put("bettime", entity.get("beton"));//自定义字段，投注时间
				entity.put("Platformflag", agent);//自定义字段，企业识别码
				
				/*****暂时不手动算，使用API返回的数据***/
				double validmoney = 0;
				if(entity.get("gameprovider").toString().toLowerCase().equals("sunbet")) {
					
					String gamename = entity.get("gamename").toString().toLowerCase();
					if(gamename.indexOf("baccarat") > -1 || gamename.equals("bullfight")) {
						
						//如果输赢
						if(Double.valueOf(entity.get("winloss").toString()) == 0) {
							validmoney = 0;
						} else {
							validmoney = Double.valueOf(entity.get("betmoney").toString());//有效投注额为投注额
						}
						
					} else {
						validmoney = Double.valueOf(entity.get("betmoney").toString());//有效投注额为投注额
					}
				} else {
					validmoney = Double.valueOf(entity.get("betmoney").toString());//有效投注额为投注额
				}
				
				
				entity.put("validmoney", validmoney);
//				System.out.println("entity="+entity);
				
				list.add(entity);
			}
			
//			for (Map<String, Object> string : list) {
//				System.out.println("data=sgs======================="+string);
//			}
		}
		return list;
	}
	
	
	public static void main(String[] arg){
		System.out.println(sdf.format(new Date()));
		String API_URL = "http://sctrapi.sbuat.com/";
		String client_id = "NBO";
		String client_secret = "EE4DMAbSHoWZyMG3FrlFTtsVaNBJ56sUysMWIEAwTF1J";
		try {
			//M/d/yyyy h:m:s aa
//			getSGSData(API_URL, client_id, client_secret,  "");
			System.out.println(sdf.parse("19/5/17 7:14:56 PM").toLocaleString());
			System.out.println(sdf2.format(sdf.parse("19/5/17 7:14:56 PM")));
			System.out.println("2017-07-06T21:47:54".length());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
