package com.hy.pull.common.util.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.hy.pull.common.util.MapUtil;
import com.hy.pull.common.util.StreamTool;
import com.hy.pull.common.util.game.sa.XmlUtil;
import com.hy.pull.common.util.game.tag.TagUtil;
import com.hy.pull.common.util.game.xcp.XCPUtil;
import com.maven.config.SystemConstant;
import com.maven.util.RandomString;
import com.maven.util.RandomStringNum;
import com.maven.util.HttpPostUtil.UTF8PostMethod;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * GG游戏接口
 * @author temdy
 */
public class GGGameAPI implements BaseInterface {
	
	private String API_URL = "http://testapi.gg626.com/api/";
	private String cagent = "TE171";
	private String MD5_KEY = "123456";
	private String DES_KEY = "12345678";

	private String GAME_API_URL = "http://api.hyzonghe.net";//游戏使用的域名。代替api.hyzonghe.net
	
	/**
	 * 默认构造函数
	 * 
	 * 测试时可以使用，不用传递秘钥进来
	 */
	@Deprecated
	public GGGameAPI(){
		
	}
	
	/**
	 * 构建函数，初始化参数
	 * @param API_URL API接口URL
	 * @param MD5_KEY MD5密钥
	 * @param DES_KEY DES密钥
	 */
	
	public GGGameAPI(String API_URL,String MD5_KEY,String DES_KEY,String cagent, String GAME_API_URL){
		this.API_URL = API_URL;
		this.MD5_KEY = MD5_KEY;
		this.DES_KEY = DES_KEY;
		
		this.cagent = cagent;
		this.GAME_API_URL = GAME_API_URL;
	}

	/**
	 * 创建用户接口
	 * @param entity 参数列表 loginname,password,actype
	 * 
	 * 不能出现特殊符号，只能包含下划线
	 * 
	 * loginname 游戏账号的登錄名
	 * actype 账号类型（0、试玩，1、真钱）
	 * password 密码
	 * @return 返回结果
	 */
	@Override
	public Object createAccount(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString()  );
//		<result    info=""    msg=""/>
		
		if(MapUtil.isNulls(entity, "loginname,password,actype")){	
			
			if( String.valueOf(entity.get("loginname")).length() > 15 ) {
				return Enum_MSG.参数错误.message("用户长度不能大于15位");
			}
			if( String.valueOf(entity.get("password")).length() > 12 ) {
				return Enum_MSG.参数错误.message("密码长度不能大于12位");
			}
			
			StringBuilder param = new StringBuilder();
			param.append("cagent=");
			param.append(this.cagent);
			param.append("/\\\\/loginname=");
			param.append(entity.get("loginname"));
			param.append("/\\\\/actype=");
			param.append(entity.get("actype"));
			param.append("/\\\\/password=");
			param.append(entity.get("password"));
			param.append("/\\\\/cur=CNY");
			param.append("/\\\\/method=ca");
			String result =  getData(param.toString());
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() , result );
			
			JSONObject data = JSONObject.fromObject(result);
			
			//	接口调用成功
			if( data.getString("code").equals("0")) {//创建用户接口成功为0的标记
				return Enum_MSG.成功.message("创建成功");
			} else {
				
				if(data.getString("code").equals("2")) {//登陆失败，账户已存在或密码错误
					return Enum_MSG.账号已存在.message(data.get("msg"));
				}
				return Enum_MSG.失败.message(data.getString("code"), data.getString("msg"));
			}
			
			
		}  else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	/**
	 * 获取余额
	 * @param entity 参数列表 loginname,actype,password
	 * 
	 * loginname 游戏账号的登錄名
	 * actype 账号类型（0、试玩，1、真钱）
	 * password 密码
	 * @return 返回结果
	 */
	@Override
	public Object getBalance(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString()  );
		
		if(MapUtil.isNulls(entity, "loginname,password")){
			StringBuilder param = new StringBuilder();
			param.append("cagent=");
			param.append(this.cagent);
			param.append("/\\\\/loginname=");
			param.append(entity.get("loginname"));
			param.append("/\\\\/method=gb");
			param.append("/\\\\/password=");
			param.append(entity.get("password"));
			param.append("/\\\\/cur=CNY");

			String result =  getData(param.toString());
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() , result );
			JSONObject data = JSONObject.fromObject(result);
			
			//	接口调用成功
			if( data.getString("code").equals("0")) {
				return Enum_MSG.成功.message(data.getString("balance"));
			} else {
				return Enum_MSG.失败.message(data.getString("code"), data.getString("msg"));
			}
			
		} else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	
	
	/**
	 * 下分（取款）的接口
	 * @param entity 参数列表 loginname,actype,password,billno,credit
	 * 
	 * loginname 游戏账号的登錄名
	 * password 密码
	 * billno  (cagent+序列), 序列是唯一的13~16位数
	 * credit 转款额度(如 000.00), 只保留小数点后两个位,即:100.00
	 * @return 返回结果
	 */
	public Object withdraw(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString()  );
		
		if(MapUtil.isNulls(entity, "loginname,password,billno,credit")){
			
			if( Double.valueOf(String.valueOf(entity.get("credit"))) < 1.00 ) {
				return Enum_MSG.参数错误.message("金额不能小于1元，建议金额为大于0的正整数");
			}
			if( String.valueOf(entity.get("billno")).length() != 16 ) {
				return Enum_MSG.参数错误.message("订单号必须是16位数的数字，不含代理账号");
			}
			if( String.valueOf(entity.get("billno")).startsWith(this.cagent) ) {
				return Enum_MSG.参数错误.message("订单号不含代理账号");
			}
			
			StringBuilder param = new StringBuilder();
			param.append("cagent=");
			param.append(this.cagent);
			param.append("/\\\\/method=tc");//
			param.append("/\\\\/loginname=");
			param.append(entity.get("loginname"));
			/*
			  (cagent+序列), 序列是唯一的13~16位数, 例如:页 19 / 42 Copyright©AsiaGaming
			 cagent = ‘XXXXX’ 及 序列 = 1234567890987, 那么billno = XXXXX1234567890987,
			 */
			param.append("/\\\\/billno=");
			param.append(this.cagent + entity.get("billno").toString());//本方法组装单号
			param.append("/\\\\/type=OUT");//OUT: 從遊戲账號转款到網站賬號
			param.append("/\\\\/credit=");
			param.append(entity.get("credit"));
			param.append("/\\\\/password=");
			param.append(entity.get("password"));
			param.append("/\\\\/cur=CNY");
			param.append("/\\\\/cur=192.168.1.1");//
			
			String result =  getData(param.toString());
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() , result );
			
			JSONObject data = JSONObject.fromObject(result);
			
			//	接口调用成功
			if( data.getString("code").equals("0")) {
				return Enum_MSG.成功.message("取款成功");
			} else {
				return Enum_MSG.失败.message(data.getString("code"), data.getString("msg"));
			}
			
			
		} else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	
	
	/**
	 * 上分（存款）的接口 loginname,actype,password,billno,credit
	 * 
	 * loginname 游戏账号的登錄名
	 * password 密码
	 * billno  (cagent+序列), 序列是唯一的13~16位数，本接口要求传递16位数字
	 * credit 转款额度(如 000.00), 只保留小数点后两个位,即:100.00
	 * @return 返回结果
	 */
	@Override
	public Object deposit(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString()  );
		
		if(MapUtil.isNulls(entity, "loginname,password,billno,credit")){
			
			if( Double.valueOf(String.valueOf(entity.get("credit"))) < 1.00 ) {
				return Enum_MSG.参数错误.message("金额不能小于1元，建议金额为大于0的正整数");
			}
			if( String.valueOf(entity.get("billno")).length() != 16 ) {
				return Enum_MSG.参数错误.message("订单号必须是16位数的数字，不含代理账号");
			}
			if( String.valueOf(entity.get("billno")).startsWith(this.cagent) ) {
				return Enum_MSG.参数错误.message("订单号不含代理账号");
			}
			
			StringBuilder param = new StringBuilder();
			param.append("cagent=");
			param.append(this.cagent);
			param.append("/\\\\/method=tc");//
			param.append("/\\\\/loginname=");
			param.append(entity.get("loginname"));
			/*
			  (cagent+序列), 序列是唯一的13~16位数, 例如:页 19 / 42 Copyright©AsiaGaming
			 cagent = ‘XXXXX’ 及 序列 = 1234567890987, 那么billno = XXXXX1234567890987,
			 */
			param.append("/\\\\/billno=");
			param.append(this.cagent + entity.get("billno").toString());//本方法组装单号
			param.append("/\\\\/type=IN");//OUT: 從遊戲账號转款到網站賬號
			param.append("/\\\\/credit=");
			param.append(entity.get("credit"));
			param.append("/\\\\/password=");
			param.append(entity.get("password"));
			param.append("/\\\\/cur=CNY");
			param.append("/\\\\/cur=192.168.1.1");//
			
			String result =  getData(param.toString());
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() , result );
			
			JSONObject data = JSONObject.fromObject(result);
			
			//	接口调用成功
			if( data.getString("code").equals("0")) {
				return Enum_MSG.成功.message("存款成功");
			} else {
				return Enum_MSG.失败.message(data.getString("code"), data.getString("msg"));
			}
			
			
		} else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	/**
	 * 获取游戏结果
	 * @param entity 参数列表 startdate,enddate
	 * 
	 * 2016- 04- 16 22:00:22 
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object getRecord(Map<String, Object> entity) {
//		http://testapi.gg626.com:5050/api/doReport.do
		
		//只能读取3天内的数据，而且每次区间范围为10分钟以内
		StringBuilder param = new StringBuilder();
		param.append("cagent=");
		param.append(this.cagent);
		param.append("/\\\\/startdate=");
		param.append(entity.get("startdate"));
		param.append("/\\\\/method=br");
		param.append("/\\\\/enddate=");
		param.append(entity.get("enddate"));

		String result =  getDataByBet(param.toString(),"doReport.do");
		System.out.println("调用结果："+result);
		
		JSONObject jsonObject = JSONObject.fromObject(result);
		if(jsonObject.getString("code").equals("0")) {
			JSONArray list = jsonObject.getJSONArray("recordlist");
			if(list != null && list.size() > 0) {
				for (Object object : list) {
					JSONObject data = (JSONObject)object;
					System.out.println(data);
				}
			}
		}
		
		return null;
	}

	/**
	 * 更新信息接口
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	@Override
	public Object updateInfo(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "updateInfo", entity.toString()  );
		if(MapUtil.isNulls(entity, "loginname,password")){
			
			StringBuilder param = new StringBuilder();
			param.append("cagent=");
			param.append(this.cagent);
			param.append("/\\\\/loginname=");
			param.append(entity.get("loginname"));
			param.append("/\\\\/method=rp");
			param.append("/\\\\/password=");
			param.append(entity.get("password"));

			String result =  getData(param.toString());
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "updateInfo", entity.toString() , result );
			
			JSONObject data = JSONObject.fromObject(result);
			
			//	接口调用成功
			if(data.getString("code").equals("0")) {//调用成功
				return Enum_MSG.成功.message("密码已重置");
			} else {
				return Enum_MSG.失败.message(data.getString("code"), data.getString("msg"));
			}
			
			
		}  else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	/**
	 * 获取订单接口
	 * @param entity 参数列表 billno,actype
	 * billno  (cagent+序列), 序列是唯一的13~16位数
	 * @return 返回结果
	 */
	@Override
	public Object getOrder(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString()  );
		if(MapUtil.isNulls(entity, "billno")){
			
			if( String.valueOf(entity.get("billno")).length() != 16 ) {
				return Enum_MSG.参数错误.message("订单号必须是16位数的数字，不含代理账号");
			}
			if( String.valueOf(entity.get("billno")).startsWith(this.cagent) ) {
				return Enum_MSG.参数错误.message("订单号不含代理账号");
			}
			
			
			StringBuilder param = new StringBuilder();
			param.append("cagent=");
			param.append(this.cagent);
			param.append("/\\\\/billno=");
			param.append(this.cagent + entity.get("billno").toString());
			param.append("/\\\\/method=qx");

			String result =  getData(param.toString());
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() , result );
			
			JSONObject data = JSONObject.fromObject(result);
			
			//	接口调用成功
			if(data.getString("code").equals("0")) {//调用成功
				return Enum_MSG.成功.message("订单处理成功");
			} else {
				return Enum_MSG.失败.message(data.getString("code"), data.getString("msg"));
			}
			
			
		}  else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	/**
	 * 登录的接口
	 * @param entity 参数列表 loginname,password,playtype
	 * loginname 游戏账号的登錄名
	 * password 密码
	 * 
	 * playtype=0,101,103,104
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object login(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() );
		
		if(MapUtil.isNulls(entity, "loginname,password")){		
			
			String API_ROOT = this.GAME_API_URL;
			

//			0	 游戏大厅	
//			101 	 捕鱼天下	
//			103 	 单挑王	
//			104 	 金鲨银鲨
			String playtype = String.valueOf(entity.get("gamecode"));
			if(playtype != null && !playtype.equals("") && !playtype.equals("null")) {
				if(playtype.equals("101") || playtype.equals("103") || playtype.equals("104")) {
					//
				} else {
					playtype = "0";
				}
			} else {
				playtype = "0";
			}
			
			StringBuilder param = new StringBuilder();
			param.append("cagent=");
			param.append(this.cagent);
			param.append("/\\\\/loginname=");
			param.append(entity.get("loginname"));
			param.append("/\\\\/password=");
			param.append(entity.get("password"));
			param.append("/\\\\/sid=");
			param.append(this.cagent + RandomString.createRandomString(16));
			param.append("/\\\\/lang=1");
			param.append("/\\\\/gametype=");
			param.append(playtype);
			param.append("/\\\\/cur=CNY");
			param.append("/\\\\/method=fw");
			param.append("/\\\\/ip=192.168.1.1");
			
			
			String result =  getData(param.toString());
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() , result );
			
			JSONObject data = JSONObject.fromObject(result);
			
			//	接口调用成功
			if(data.getString("code").equals("0")) {//调用成功
				return Enum_MSG.成功.message(data.getString("url"));
			} else {
				return Enum_MSG.失败.message(data.getString("code"), data.getString("msg"));
			}
			
		} else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
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
	
	private String getDataByBet(String param, String methodName) {
		TagUtil tag = new TagUtil(DES_KEY);
		String params = "";
		try {
			params = tag.encrypt(param);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String key = XCPUtil.MD5(params + MD5_KEY);
		String urlStr = "http://testapi.gg626.com:5050/api/".concat(methodName).concat("?params=").concat(params).concat("&key=").concat(key);
		System.out.println("参数原文：" + param);
		System.out.println("参数密文：" + key);
		System.out.println("请求URL：" + urlStr);

		URL url = null;
		HttpURLConnection httpConn = null;
		InputStream in = null;
		try {
			url = new URL(urlStr);
			httpConn = (HttpURLConnection) url.openConnection();
			HttpURLConnection.setFollowRedirects(true);
			httpConn.setConnectTimeout(30000);
			httpConn.setReadTimeout(30000);
			httpConn.setRequestMethod("GET");

			httpConn.setRequestProperty("GGaming", "WEB_GG_GI_" + cagent);// cagent请参考上线说明,

			in = httpConn.getInputStream();
			String odds = new String(StreamTool.readInputStream(in), "UTF-8");

			return odds;

		} catch (Exception e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message( e.getMessage());
		} finally {
			try {
				httpConn.disconnect();
			} catch (Exception ex) {
				ex.printStackTrace();
				return Enum_MSG.接口调用错误.message( ex.getMessage());
			}
		}
	}
	
	private String getData(String param, String methodName) {
		TagUtil tag = new TagUtil(DES_KEY);
		String params = "";
		try {
			params = tag.encrypt(param);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String key = XCPUtil.MD5(params + MD5_KEY);
		String urlStr = API_URL.concat(methodName).concat("?params=").concat(params).concat("&key=").concat(key);
		System.out.println("参数原文：" + param);
		System.out.println("参数密文：" + key);
		System.out.println("请求URL：" + urlStr);

		URL url = null;
		HttpURLConnection httpConn = null;
		InputStream in = null;
		try {
			url = new URL(urlStr);
			httpConn = (HttpURLConnection) url.openConnection();
			HttpURLConnection.setFollowRedirects(true);
			httpConn.setConnectTimeout(30000);
			httpConn.setReadTimeout(30000);
			httpConn.setRequestMethod("GET");

			httpConn.setRequestProperty("GGaming", "WEB_GG_GI_" + cagent);// cagent请参考上线说明,

			in = httpConn.getInputStream();
			String odds = new String(StreamTool.readInputStream(in), "UTF-8");

			return odds;

		} catch (Exception e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message( e.getMessage());
		} finally {
			try {
				httpConn.disconnect();
			} catch (Exception ex) {
				ex.printStackTrace();
				return Enum_MSG.接口调用错误.message( ex.getMessage());
			}
		}
	}
	
	
	
	
	/**
	 * 获取接口的数据
	 * @param url 接口URL
	 * @return 响应数据
	 */
	public String getData(String param) {
		return getData(param,"doLink.do");
	}
	
	public static void main(String[] a){
//		password=XQ5Lv5Xq, loginname=zOne6KTd83sMEpb
		
		GGGameAPI tag = new GGGameAPI();
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("loginname", RandomString.createRandomString(15));//#&*
		entity.put("actype", "1");
		entity.put("password", RandomString.createRandomString(8));
//		System.out.println(tag.createAccount(entity));//loginname,password,actype
//		
		entity.put("billno", RandomStringNum.createRandomString(16));
		entity.put("credit", "20");
//		System.out.println(tag.deposit(entity));//loginname,password,billno,credit
//		
//		entity.put("billno", RandomStringNum.createRandomString(16));
//		entity.put("credit", "1");
//		System.out.println(tag.withdraw(entity));//loginname,password,billno,credit
//		
//		System.out.println(tag.getOrder(entity));//billno
//		
		System.out.println(tag.getBalance(entity));//loginname,password
//		
		entity.put("playtype", "0");
//		System.out.println(tag.login(entity));//loginname,password,playtype
//		
//		entity.put("playtype", "0");
//		entity.put("loginname", "zOne6KTd83sMEpb");
//		entity.put("password", "XQ5Lv5Xq");
//		System.out.println(tag.login(entity));//loginname,password,playtype
		
		entity.put("startdate", "2017-01-21 17:45:00");
		entity.put("enddate", "2017-01-21 17:55:00");
		System.out.println(tag.getRecord(entity));//startdate,enddate
	}
}

