package com.hy.pull.common.util.game;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.common.util.SGSUtil;
import com.hy.pull.common.util.api.Enum_MSG;



/**
 * 申博拉数据
 * 创建日期：2016-10-10
 * @author temdy
 */
public class SGSGame {
	private String getHttpUrlDataByOrder(String API_URL,String client_id, String client_secret){
		
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
            
            client.executeMethod(method);  
            return method.getResponseBodyAsString();  
        } catch (Exception e) {  
            e.printStackTrace();  
            return "0";
        }  
	
	}
	/**
	 * 获取游戏结果
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	public Object getRecord(String API_URL,String client_id, String client_secret) {
		try{
			
			
			String startdate = URLEncoder.encode(SGSUtil.getDateCurrenStart1hour() ,"UTF-8");
			String enddate = URLEncoder.encode(SGSUtil.getDateCurren() ,"UTF-8");
			
			String url = API_URL.concat("/api/report/bethistory").concat("?startdate="+startdate+"&enddate="+enddate+"&includetestplayers=true");
			String result = getHttpUrlDataByOrder(url,client_id,client_secret);
			
			System.out.println("startdate="+SGSUtil.getDateCurrenStart1hour());
			System.out.println("enddate="+SGSUtil.getDateCurren());
			System.out.println("请求URL："+url);
			System.out.println("调用结果："+result);
			
			
			if( !result.equals("0")) {
				
				return Enum_MSG.成功.message(result);
				
			} else {
				return Enum_MSG.接口调用错误.message("网络异常");
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			
			return Enum_MSG.接口调用错误.message(ex.getMessage());
		}
	}
	
	/**
	 * 拉取东方游戏数据列表（接口每次最多返回300条记录）
	 * @param apiUrl 接口链接
	 * @param agent 代理
	 * @param vendorid 最大vendorid
	 * @param userKey MD5密钥
	 * @return 游戏数据列表
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> getAOIData(String apiUrl,String agent,String vendorid,String userKey) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			if(vendorid==null){
				vendorid = "0";
			}
			StringBuilder param = new StringBuilder();
			String p = "agent="+agent+"$vendorid="+vendorid+"$isjs=1$method=gbrbv";
			String params = new String(Base64.encodeBase64(p.toString().getBytes("UTF-8")));
			String key = GameHttpUtil.MD5(params + userKey);
			param.append("params=");
			param.append(params);
			param.append("&key=");
			param.append(key);
			String result = GameHttpUtil.sendPost(apiUrl+"?", param.toString());
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
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
					entity.put("aoi_createtime", sdf.format(new Date()));
					entity.put("aoi_PlatformID", info.get("PlatformID"));
					entity.put("Platformflag", agent);
					list.add(entity);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	public static void main(String[] arg){
		System.out.println(getAOIData("http://cashapi.dg20mu.com/cashapi/getdata.aspx","huanqiuAPI","0","hq@-@688*$-$*!&88$dshxeh"));
	}
}
