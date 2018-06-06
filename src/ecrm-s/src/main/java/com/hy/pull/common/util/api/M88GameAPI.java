package com.hy.pull.common.util.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
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
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.hy.pull.common.util.Encrypt;
import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.common.util.MapUtil;
import com.hy.pull.common.util.game.NHQGame;
import com.hy.pull.common.util.game.sa.DeEnCode;
import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.util.RandomString;
import com.maven.util.RandomStringNum;

import net.sf.json.JSONObject;


/**
 * M88游戏接口
 * 
 * Opus Gaming  体育游戏/真人游戏
 * 
 * 注意：体育及真人的API_URL等参数都不一样，各有一套
 * 
 * （当前只实现了体育的api，真人还没编码接入）
 * @author temdy
 */
public class M88GameAPI implements BaseInterface {
	
	private String SB_API_URL = "http://api.coolres.com/";
	private String SB_LOGIN_URL = "http://sport.77scm.com";
	private String SB_operator_id = "D28C7DA7-5E73-4197-9D5A-311DC60023E2";
	
	private String SX_API_URL = "http://api.coolres.com/";
	private String SX_LOGIN_URL = "http://sport.77scm.com";
	private String SX_operator_id = "D28C7DA7-5E73-4197-9D5A-311DC60023E2";
	private String SX_site_code = "HBW";
	private String SX_secret_key = "aa";
	private String SX_product_code = "sb2";
	
	//6900858
	//Zp4HFD5G6PyWwaPB3R2iBYTs3Qiydyah
	//http://cashier.1pagateway.com/payment/

	
	
	private String GAME_API_URL = "http://api.hyzonghe.net";//游戏使用的域名。代替api.hyzonghe.net
	
	
	/**
	 * 默认构造函数
	 */
	@Deprecated
	public M88GameAPI(){
		
	}
	
	/**
	 * 构建函数，初始化参数
	 * @param API_URL API接口URL
	 * @param MD5_KEY MD5密钥
	 */
	public M88GameAPI(
			String SB_API_URL, String SB_LOGIN_URL, String SB_operator_id,
			String SX_API_URL,String SX_operator_id,String SX_LOGIN_URL, String SX_site_code,String SX_secret_key,String SX_product_code,String GAME_API_URL)
	{
		this.SB_API_URL = SB_API_URL;
		this.SB_LOGIN_URL = SB_LOGIN_URL;
		this.SB_operator_id = SB_operator_id;
		this.GAME_API_URL = GAME_API_URL;
		
		//以下真人
		this.SX_API_URL = SX_API_URL;
		this.SX_LOGIN_URL = SX_LOGIN_URL;
		this.SX_operator_id = SX_operator_id;
		
		this.SX_site_code = SX_site_code;
		this.SX_secret_key = SX_secret_key;
		this.SX_product_code = SX_product_code;
	}

	/**
	 * 创建用户接口
	 * @param entity 参数列表 username,odds_type
	 * 
	 * username=17位数
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object createAccount(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString()  );
		try{
			if(MapUtil.isNulls(entity, "username,odds_type")){	
				
				if( String.valueOf(entity.get("username")).length() > 17 ) {
					return Enum_MSG.参数错误.message("用户长度不能大于17位，当前"+String.valueOf(entity.get("username")).length())+"位";
				}
				
				StringBuffer buffer = new StringBuffer();
				buffer.append("operator_id=").append(this.SB_operator_id).append("&");
				buffer.append("first_name=").append(entity.get("username")).append("&");
				buffer.append("user_name=").append(entity.get("username")).append("&");
				buffer.append("Language=").append("zh-CN").append("&");
				buffer.append("odds_type=").append(entity.get("odds_type")).append("&");//
				buffer.append("currency=").append("RMB").append("&");//人民币
				String url = SB_API_URL + "CreateMember.API?" + buffer.toString();
				
				String result = getUrl(url);
				if(result.equals("-1") ){
					return Enum_MSG.接口调用错误.message("网络异常");
				}
				
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() , result );
				Map<String, String> object = getQueryParams(result,"create_member");
				
				//接口调用成功
				if( object.get("status_code").equals("0") ) {
					
					return Enum_MSG.成功.message(object.get("status_text"));
					
				} else if( object.get("status_code").equals("6") ) {
					return Enum_MSG.账号已存在.message(object.get("status_text"));
					
				} else {//网络异常
					return Enum_MSG.失败.message(object.get("status_code"), object.get("status_text"));
				}
				
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			
			return Enum_MSG.接口调用错误.message(ex.getMessage());
		}
	}

	/**
	 * 获取余额
	 * @param entity 参数列表 username
	 * username 用户名
	 * @return 返回结果
	 */
	@Override
	public Object getBalance(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString()  );
		try{
			if(MapUtil.isNulls(entity, "username")){	
				
				StringBuffer buffer = new StringBuffer();
				buffer.append("operator_id=").append(this.SB_operator_id).append("&");
				buffer.append("user_name=").append(entity.get("username")).append("&");
				String url = SB_API_URL + "CheckUserBalance.API?" + buffer.toString();
				
				String result = getUrl(url);
				if(result.equals("-1") ){
					return Enum_MSG.接口调用错误.message("网络异常");
				}
				
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() , result );
				Map<String, String> object = getQueryParams(result,"check_user_balance");
				
				//接口调用成功
				if( object.get("status_code").equals("0") ) {
					
					return Enum_MSG.成功.message(object.get("user_balance"));
					
				} else {//网络异常
					return Enum_MSG.失败.message(object.get("status_code"), object.get("status_text"));
				}
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			
			return Enum_MSG.接口调用错误.message(ex.getMessage());
		}
	}
	

	/**
	 * 下分（取款）的接口
	 * @param entity 参数列表 username,amount,trans_id
	 * @return 返回结果
	 */
	@Override
	public Object withdraw(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() );
		try{
			if(MapUtil.isNulls(entity, "username,amount,trans_id")){	
				
				if( Double.valueOf(entity.get("amount").toString()) < 1.0 ) {
					return Enum_MSG.参数错误.message("取款金额不能小于1元");
				}
				if( String.valueOf(entity.get("trans_id")).length() > 20 ) {
					return Enum_MSG.参数错误.message("订单号长度不能大于20位，当前"+String.valueOf(entity.get("trans_id")).length())+"位";
				}
				
				StringBuffer buffer = new StringBuffer();
				buffer.append("operator_id=").append(this.SB_operator_id).append("&");
				buffer.append("amount=").append(entity.get("amount")).append("&");
				buffer.append("user_name=").append(entity.get("username")).append("&");
				buffer.append("trans_id=").append(entity.get("trans_id")).append("&");
				buffer.append("currency=").append("RMB").append("&");
				buffer.append("direction=").append("0").append("&");//1 = Credit  （转账进opus） 0 = Debit  （转账出opus） 
				
				String url = SB_API_URL + "FundTransfer.API?" + buffer.toString();
				
				String result = getUrl(url);
				if(result.equals("-1") ){
					return Enum_MSG.接口调用错误.message("网络异常");
				}
				
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() , result );
				Map<String, String> object = getQueryParams(result,"fund_transfer");
				
				//接口调用成功
				if( object.get("status_code").equals("0") && object.get("status").equals("0") ) {
					
					return Enum_MSG.成功.message(object);
					
				} else {//网络异常
					return Enum_MSG.失败.message(object.get("status_code"), object.get("status_text"));
				}
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			
			return Enum_MSG.接口调用错误.message(ex.getMessage());
		}
	}

	/**
	 * 上分（存款）的接口
	 * @param entity 参数列表 username,amount,trans_id
	 * @return 返回结果
	 */
	@Override
	public Object deposit(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() );
		try{
			
			if(MapUtil.isNulls(entity, "username,amount,trans_id")){	
				
				if( Double.valueOf(entity.get("amount").toString()) < 1.0 ) {
					return Enum_MSG.参数错误.message("取款金额不能小于1元");
				}
				if( String.valueOf(entity.get("trans_id")).length() > 20 ) {
					return Enum_MSG.参数错误.message("订单号长度不能大于20位，当前"+String.valueOf(entity.get("trans_id")).length())+"位";
				}
				
				StringBuffer buffer = new StringBuffer();
				buffer.append("operator_id=").append(this.SB_operator_id).append("&");
				buffer.append("amount=").append(entity.get("amount")).append("&");
				buffer.append("user_name=").append(entity.get("username")).append("&");
				buffer.append("trans_id=").append(entity.get("trans_id")).append("&");
				buffer.append("currency=").append("RMB").append("&");
				buffer.append("direction=").append("1").append("&");//1 = Credit  （转账进opus） 0 = Debit  （转账出opus） 
				
				String url = SB_API_URL + "FundTransfer.API?" + buffer.toString();
				
				String result = getUrl(url);
				if(result.equals("-1") ){
					return Enum_MSG.接口调用错误.message("网络异常");
				}
				
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() , result );
				Map<String, String> object = getQueryParams(result,"fund_transfer");
				
				//接口调用成功
				if( object.get("status_code").equals("0") && object.get("status").equals("0") ) {
					
					return Enum_MSG.成功.message(object);
					
				} else {//网络异常
					return Enum_MSG.失败.message(object.get("status_code"), object.get("status_text"));
				}
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			
			return Enum_MSG.接口调用错误.message(ex.getMessage());
		}
		
	}

	/**
	 * 获取游戏结果
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	@Override
	public Object getRecord(Map<String, Object> entity) {

		String startDate = 	"2017-03-16 13:40:00";
		String endDate = 	"2017-03-16 13:59:00";
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("operator_id=").append(this.SB_operator_id).append("&");
		buffer.append("date_start=").append(startDate).append("&");
		buffer.append("date_end=").append(endDate).append("&");
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("operator_id", this.SB_operator_id);
		params.put("date_start", startDate);
		params.put("date_end", endDate);
		
//		String url = SB_API_URL + "TransactionMemberDetail.API?" + buffer.toString();
//		
//		String result = getUrl(url);
//		if(result.equals("-1") ){
//			return Enum_MSG.接口调用错误.message("网络异常");
//		}
		String result = doPostSubmitMap(SB_API_URL + "TransactionMemberDetail.API?", params);
		
		System.out.println("调用结果："+result);
		Map<String, String> object = getQueryParams(result,"transactionmember_detail");
		if( object.get("status_code").equals("0") ) {
			List<Map<String, String>> list = getQueryParamsList(result);
			return list;
		}
		System.out.println("M88数据同步出现异常："+object);
		return null;
	}
	
	private static String doPostSubmitMap(String httpUrl,Map<String, String> params) {
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
    		
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);  
            if(httpResponse.getStatusLine().getStatusCode() == 200)  {
            	String strResult = EntityUtils.toString(httpResponse.getEntity());
            	return strResult;
            } else {
            	return httpResponse.getStatusLine().getStatusCode()+"";
            }
		} catch (Exception e) {
			e.printStackTrace();
			return -1+"";
		}
	}

	/**
	 * 更新信息接口
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	@Override
	public Object updateInfo(Map<String, Object> entity) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 获取订单接口
	 * @param entity 参数列表 username,trans_id
	 * 
	 * 
	 * @return 返回结果 返回单号
	 */
	@Override
	public Object getOrder(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString()  );
		try{
			if(MapUtil.isNulls(entity, "username,trans_id")){	
				
				StringBuffer buffer = new StringBuffer();
				buffer.append("operator_id=").append(this.SB_operator_id).append("&");
				buffer.append("trans_id=").append(entity.get("trans_id")).append("&");
				buffer.append("user_name=").append(entity.get("username")).append("&");
				
				String url = SB_API_URL + "CheckFundTransferStatus.API?" + buffer.toString();
				
				String result = getUrl(url);
				if(result.equals("-1") ){
					return Enum_MSG.接口调用错误.message("网络异常");
				}
				
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() , result );
				Map<String, String> object = getQueryParams(result,"check_fund_transfer_status");
				
				//接口调用成功
				if( object.get("status_code").equals("0") ) {
					
					return Enum_MSG.成功.message(object);
					
				} else {//网络异常
					return Enum_MSG.失败.message(object.get("status_code"), object.get("status_text"));
				}
				
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			
			return Enum_MSG.接口调用错误.message(ex.getMessage());
		}
	}

	/**
	 * 登录的接口
	 * @param entity 参数列表 username,employeecode
	 * 
	 * 注意：当前url默认是体育游戏，如需要增加真人，请另外编码实现
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object login(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString()  );
		try{
			if(MapUtil.isNulls(entity, "username,employeecode")){	
				
				String API_ROOT = this.GAME_API_URL;
				StringBuilder url = new StringBuilder();
				url.append(API_ROOT);//API路径
				url.append("/m88game/index?employeecode="+entity.get("employeecode"));
				
				url.append("&url=").append( URLEncoder.encode(this.SB_LOGIN_URL,"UTF-8") ).append("&");//
				
				
				return Enum_MSG.成功.message(url.toString());
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			
			return Enum_MSG.接口调用错误.message(ex.getMessage());
		}
	}

	/**
	 * 是否在线接口
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	@Override
	public Object isOnLine(Map<String, Object> entity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static String getUrl(String urls) {
		System.out.println("请求URL："+urls);
		StringBuilder sb = new StringBuilder();
		try {
			
			URL url = new URL(urls);
	        HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();
	        int timeout = 10000;
	        urlcon.setConnectTimeout(timeout);
	        urlcon.setReadTimeout(timeout);
	        urlcon.connect();
	           
			InputStream in = urlcon.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(in);
			BufferedReader reader = new BufferedReader(inputStreamReader);
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			urlcon.disconnect();
			reader.close();
			inputStreamReader.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		}
		return sb.toString();
	}
	/**
	 * 返回投注记录
	 * @param xmlStr
	 * @return
	 */
	private static List<Map<String, String>> getQueryParamsList(String xmlStr) {
		if(xmlStr==null || xmlStr.equals("")) {
			return null;
		}
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlStr); // 将字符串转为XML    
			Element root = doc.getRootElement();
			
			List<Element> list = root.selectNodes("//transactionmember_detail/bets/row");
			List<Map<String, String>> listData = new ArrayList<Map<String,String>>();
			
			
			for (Element element : list) {
				Map<String, String> data = new HashMap<String, String>();
				
				Iterator<Element> iterator = element.elementIterator();
				while (iterator.hasNext()) {
					Element element2 = (Element) iterator.next();
					data.put(element2.getName(), element2.getStringValue());
//					System.out.println(element2.getName() +"-"+ element2.getStringValue());
				}
				listData.add(data);
			}
			return listData;
			
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 返回单个节点
	 * @param xmlStr
	 * @return
	 */
	private static Map<String, String> getQueryParams(String xmlStr,String rootcode) {
		if(xmlStr==null || xmlStr.equals("")) {
			return null;
		}
		Document doc = null;
		Map<String, String> data = new HashMap<String, String>();
		try {
			doc = DocumentHelper.parseText(xmlStr); // 将字符串转为XML    
			Element root = doc.getRootElement();
			List<Element> list2 = root.selectNodes("//"+rootcode);
			
			for (Element element : list2) {
				List<Element> list = element.elements();
				for (Element element2 : list) {
					data.put(element2.getName(), element2.getStringValue());
					//System.out.println(element2.getName() +"-"+ element2.getStringValue());
				}
			}
			return data;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		M88GameAPI nhq = new M88GameAPI();
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("username", "6sBMCuD9Yew5ID12G");
		entity.put("odds_type", "32");
		System.out.println(nhq.createAccount(entity));//username,odds_type
		
		entity.put("amount", "1");
		entity.put("trans_id", RandomStringNum.createRandomString(20));
		System.out.println(nhq.deposit(entity));//username,amount,trans_id
		
		entity.put("amount", "1");
		entity.put("trans_id", RandomStringNum.createRandomString(20));
		System.out.println(nhq.withdraw(entity));//username,amount,trans_id
		
		System.out.println(nhq.getBalance(entity));
		
		System.out.println(nhq.getOrder(entity));
		
		entity.put("employeecode", "E000ISU4");
		System.out.println(nhq.login(entity));
//		System.out.println(nhq.getRecord(entity));
		
	}
}
