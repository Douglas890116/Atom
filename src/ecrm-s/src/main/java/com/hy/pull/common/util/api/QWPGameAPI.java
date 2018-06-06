package com.hy.pull.common.util.api;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;

import com.hy.pull.common.util.MapUtil;
import com.maven.util.RandomString;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 去玩棋牌游戏接口
 * @author klay
 *
 */
public class QWPGameAPI implements BaseInterface {
	public static void main(String[] args) {
		//===============测试用参数===============//
//		String API_URL = "http://192.168.1.126:8014";
		String API_URL = "http://13.75.93.22:8014";
//		String API_URL = "http://api.ggp88.net";
		String AGENT_NAME = "dwqwp";
		String AGENT_PASSWORD = "B0sWhycz4ybdIvVU8uxi79gm1PQl6Fza";
		String GAME_API_URL = "http://ggp8.net";
		//===============测试用参数===============//
		String account = "zack789";
		String realName = "扎克好盈";
		String password1 = "abc123";
		String password2 = "a1b2c3";
		String IP = "192.168.1.125";
		String orderId = "TEST".concat(RandomString.UUID().substring(4));
		
		// 初始化接口
		QWPGameAPI api = new QWPGameAPI(API_URL, AGENT_NAME, AGENT_PASSWORD, GAME_API_URL);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Account", account);
		params.put("Password", password1);
		params.put("OrderId", orderId);
		params.put("Amount", 100);
		params.put("IP", IP);
//		params.put("gamecode", 4);
//		params.put("returnurl", "https://www.hy17888.com");
//		params.put("startTime", "2017-10-27 15:10:00");
//		params.put("endTime", "2017-10-25 15:40:00");
		
//		String result = api.createAccount(params).toString();
//		String result = api.deposit(params).toString();
		String result = api.withdraw(params).toString();
//		String result = api.updateInfo(params).toString();
//		String result = api.login(params).toString();
//		String result = api.getRecord(params).toString();
		System.out.println(result);
	}
	
	private String apiUrl;
	private String authorization;
	private String gameAPIURL;
	/**
	 * 
	 * @param apiUrl        接口地址
	 * @param agentName     代理账号
	 * @param agentPassword 代理密码
	 * @param gameAPIURL    游戏接口地址
	 */
	public QWPGameAPI(String apiUrl, String agentName, String agentPassword, String gameAPIURL) {
		this.apiUrl = apiUrl;
		this.gameAPIURL = gameAPIURL;
		this.authorization = getAuthorization(apiUrl.concat("/token"), agentName, agentPassword, "api");
	}
	/**
	 * 创建账号
	 * @param params {"Account": "string","RealName": "string","Password": "string","QQ": "string","Email": "string","IP": "string"}
	 * @return Object: 结果
	 */
	@Override
	public Object createAccount(Map<String, Object> params) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", params.toString());
		try {
			if (MapUtil.isNulls(params, "Account,Password")) {
				
				String account = params.get("Account").toString();
				
				if (account.length() < 6)
					return Enum_MSG.参数错误.message("用户长度不能小于6位");
				if (account.length() > 20)
					return Enum_MSG.参数错误.message("用户长度不能大于20位");
				
				if (isAccountExist(account)) {
					return Enum_MSG.账号已存在.message("[" + account + "]已存在");
				}
				
				StringBuffer url = new StringBuffer(apiUrl);
				url.append(代理创建玩家账户);
				Map<String, Object> result = doPostSubmit(url.toString(), params);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", result.toString());
				
				if ((Boolean)result.get(STATUS)) {
					return Enum_MSG.成功.message("创建账号成功!");
				} else {
					return Enum_MSG.失败.message(result.get(MESSAGE));
				}
			} else {
				return Enum_MSG.参数错误.message("缺少必要参数");
			}
		} catch (HttpException e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		}
	}
	/**
	 * 判断账户是否存在 存在 - true，不存在 - false
	 * @param account
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	private boolean isAccountExist(String account) throws HttpException, IOException {
		StringBuffer url = new StringBuffer(apiUrl);
		url.append(代理查询玩家名是否被注册);
		url.append("/").append(account);
		Map<String, Object> result = doGetSubmit(url.toString());
		return (Boolean)result.get(STATUS);
	}
	
	/**
	 * 查询玩家余额
	 * @param account 玩家账号
	 * @return
	 */
	@Override
	public Object getBalance(Map<String, Object> params) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", params.toString());
		try {
			if (params.get("account") == null) 
				return Enum_MSG.参数错误.message("缺少必要参数[account]");
			String account = params.get("account").toString();
			
			StringBuffer url = new StringBuffer(apiUrl);
			url.append(代理查询玩家余额);
			url.append("/" + account);
			
			Map<String, Object> result = doGetSubmit(url.toString());
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", result.toString());
			
			if ((Boolean)result.get(STATUS)) {
				JSONObject json = JSONObject.fromObject(result.get(MESSAGE));
				Double balance = json.getDouble("balance");
				return Enum_MSG.成功.message(balance.toString());
			} else {
				return Enum_MSG.失败.message(result.get(MESSAGE));
			}
		} catch (HttpException e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		}
	}
	/**
	 * 游戏下分取款
	 * @param params Account,Amount,IP,OrderId
	 * @return
	 */
	@Override
	public Object withdraw(Map<String, Object> params) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", params.toString());
		try {
			if (MapUtil.isNulls(params, "Account,Amount,IP,OrderId")) {
				
				StringBuffer url = new StringBuffer(apiUrl);
				url.append(代理给下级玩家充值或提款);
				// 提款 - 1
				params.put("TransType", 1);
				
				Map<String, Object> result = doPostSubmit(url.toString(), params);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", result.toString());

				if ((Boolean)result.get(STATUS)) {
					return Enum_MSG.成功.message("下分成功!");
				} else {
					return Enum_MSG.失败.message(result.get(MESSAGE));
				}
			} else {
				return Enum_MSG.参数错误.message("缺少必要参数");
			}
		} catch (HttpException e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		}
	}
	/**
	 * 游戏上分充值
	 * @param params Account,Amount,IP,OrderId
	 * @return
	 */
	@Override
	public Object deposit(Map<String, Object> params) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", params.toString());
		try {
			if (MapUtil.isNulls(params, "Account,Amount,IP,OrderId")) {
				
				StringBuffer url = new StringBuffer(apiUrl);
				url.append(代理给下级玩家充值或提款);
				// 充值 - 0
				params.put("TransType", 0);
				
				Map<String, Object> result = doPostSubmit(url.toString(), params);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", result.toString());

				if ((Boolean)result.get(STATUS)) {
					return Enum_MSG.成功.message("上分成功!");
				} else {
					return Enum_MSG.失败.message(result.get(MESSAGE));
				}
			} else {
				return Enum_MSG.参数错误.message("缺少必要参数");
			}
		} catch (HttpException e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		}
	}
	/**
	 * 查询游戏记录
	 * @param params
	 * @return
	 * @throws IOException 
	 * @throws HttpException 
	 */
	@Override
	public Object getRecord(Map<String, Object> params) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getRecord", params.toString());
		try {
			if (MapUtil.isNulls(params, "startTime,endTime")) {
				
				StringBuffer url = new StringBuffer(apiUrl);
				url.append(获取游戏记录);
				url.append("?startTime=").append(URLEncoder.encode(params.get("startTime").toString(), "UTF-8"));
				url.append("&endTime=").append(URLEncoder.encode(params.get("endTime").toString(), "UTF-8"));
				url.append("&currentPage=1&pageSize=1000");

				Map<String, Object> result = doGetSubmit(url.toString());
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getRecord", result.toString());

				if ((Boolean)result.get(STATUS)) {
					JSONObject jsonObject = JSONObject.fromObject(result.get(MESSAGE));
					List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
					int count = jsonObject.getInt("Total");
					if (count > 0) {
						JSONArray dataList = jsonObject.getJSONArray("Rows");
						for (int i = 0; i < dataList.size(); i++) {
							JSONObject data = dataList.getJSONObject(i);
							Map<String, Object> resultMap = new HashMap<String, Object>();
							resultMap.put("Account", data.getString("Account"));
							resultMap.put("KindId", data.getInt("KindId"));
							resultMap.put("ServerId", data.getInt("ServerId"));
							resultMap.put("RoomName", data.getString("RoomName"));
							resultMap.put("StartTime", data.getString("StartTime"));
							resultMap.put("EndTime", data.getString("EndTime"));
							resultMap.put("RecordTime", data.getString("RecordTime"));
							resultMap.put("Score", data.getInt("Score"));
							resultMap.put("TurnScore", data.getInt("TurnScore"));
							resultMap.put("Revenue", data.getInt("Revenue"));
							resultMap.put("TurnNumber", data.getInt("TurnNumber"));
							resultMap.put("CardData", data.getString("CardData"));
							resultList.add(resultMap);
						}
					}
					return Enum_MSG.成功.message(resultList);
				} else {
					return Enum_MSG.失败.message(result.get(MESSAGE));
				}
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}
		} catch (HttpException e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		}
	}
	/**
	 * 重置密码
	 * @param params
	 * @return
	 */
	@Override
	public Object updateInfo(Map<String, Object> params) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "updateInfo", params.toString());
		try {
			if (MapUtil.isNulls(params, "Account,Password,IP")) {
				
				StringBuffer url = new StringBuffer(apiUrl);
				url.append(代理重置玩家密码);
				
				Map<String, Object> result = doPutSubmit(url.toString(), params);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "updateInfo", result.toString());
				
				if ((Boolean)result.get(STATUS)) {
					return Enum_MSG.成功.message("密码修改成功!");
				} else {
					return Enum_MSG.失败.message(result.get(MESSAGE));
				}
			} else {
				return Enum_MSG.参数错误.message("缺少必要参数");
			}
		} catch (HttpException e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		}
	}
	/**
	 * 查询交易订单状态
	 * @param orderId 订单号
	 * @return
	 */
	@Override
	public Object getOrder(Map<String, Object> params) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", params.toString());
		try {
			if (params.get("orderId") == null)
				return Enum_MSG.参数错误.message("缺少必要参数[orderId]");

			String orderId = params.get("orderId").toString();
			StringBuffer url = new StringBuffer(apiUrl);
			url.append(代理查询玩家订单状态);
			url.append("/").append(orderId);

			Map<String, Object> result = doGetSubmit(url.toString());
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", result.toString());

			if ((Boolean)result.get(STATUS)) {
				return Enum_MSG.成功.message("订单交易成功!");
			} else {
				return Enum_MSG.失败.message(result.get(MESSAGE));
			}
		} catch (HttpException e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		}
	}
	/**
	 * 
	 */
	@Override
	public Object login(Map<String, Object> params) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", params.toString());
		// 前端接收参数:token(请求令牌),type(请求游戏类型:1=赢三张，2=港式五张，3=富姐德州，4=斗地主，5=牛牛),url(需要返回的URL)
		// ?GGPparams=token=$token&type=1&url=http://www.baidu.com
		// http://ggp8.net?GGPparams=token=$token&type=1&url=http://www.baidu.com(url路径需要是http开头的,不然跳转会出现问题)
		System.out.println("去玩登录游戏参数：" + params);
		try {
			if (MapUtil.isNulls(params, "Account,Password,returnurl")) {
				String user_token = getAuthorization(apiUrl.concat("/token"), params.get("Account").toString(), params.get("Password").toString(), "player");
				// type=0，表示大厅
				String type = "0";
				if (params.get("gamecode") != null
						&& StringUtils.isNotBlank(params.get("gamecode").toString())
						&& !params.get("gamecode").toString().equals("null")) {
					type = params.get("gamecode").toString();
				}
				String return_url = params.get("returnurl").toString();
				StringBuffer url = new StringBuffer(gameAPIURL);
				url.append("?GGPparams=token=");
				url.append(user_token.split(" ")[1]);
				url.append("&type=").append(type);
				url.append("&url=").append(return_url);
				return Enum_MSG.成功.message(url.toString());
			} else {
				return Enum_MSG.参数错误.message("缺少必要参数");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		}
	}
	/**
	 * 用户是否在游戏中
	 */
	@Override
	public Object isOnLine(Map<String, Object> params) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "isOnLine", params.toString());
		try {
			if (MapUtil.isNulls(params, "account")) {
				StringBuffer api = new StringBuffer(apiUrl);
				api.append(代理获取玩家信息);
				api.append("?field=2");
				api.append("&value=").append(params.get("account"));
				api.append("&pageSize=").append(20);
				api.append("&currentPage=").append(1);
				
				Map<String, Object> result = doGetSubmit(api.toString());
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "isOnLine", result.toString());
				if ((Boolean)result.get(STATUS)) {
					JSONObject jsonObject = JSONObject.fromObject(result.get(MESSAGE));
					int count = jsonObject.getInt("Total");
					if (count == 1) {
						JSONArray data = jsonObject.getJSONArray("Rows");
						JSONObject userInfo = data.getJSONObject(0);
						boolean isOnline = userInfo.getBoolean("IsInGame");
						return Enum_MSG.成功.message(isOnline);
					}
					return jsonObject;
				}
				return Enum_MSG.失败.message(result.get(MESSAGE));
			} else {
				return Enum_MSG.参数错误.message("缺少必要参数[account]");
			}
		} catch (HttpException e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		}
	}
	/*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- 以下方法为通用方法 -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-*/
	/**
	 * 获取接口令牌
	 * @return
	 */
	private String getAuthorization(String apiUrl, String name, String password, String usertype) {
		try {
			NameValuePair[] parametersBody = new NameValuePair[4];
			parametersBody[0] = new NameValuePair("username", name);
			parametersBody[1] = new NameValuePair("password", password);
			parametersBody[2] = new NameValuePair("userType", usertype);
			parametersBody[3] = new NameValuePair("grant_type", "password");

			PostMethod post = new PostMethod(apiUrl);
			post.setRequestBody(parametersBody);
			post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			post.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 1000 * 10);
			post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, CHARSET);

			HttpClient httpClient = new HttpClient();
			int statusCode = httpClient.executeMethod(post);
			if (statusCode != HttpStatus.SC_OK)
				return "status：" + statusCode;
			int contentLength = (int) post.getResponseContentLength();
			byte[] buffer = new byte[contentLength];
			for (int i = 0; i < contentLength;) {
				int readLength = post.getResponseBodyAsStream().read(buffer, i, contentLength - i);
				if (readLength == -1)
					break;
				i += readLength;
			}
			String result = new String(buffer, CHARSET);
			JSONObject jsonObject = JSONObject.fromObject(result);
			String tokenType = jsonObject.getString("token_type");
			String accessToken = jsonObject.getString("access_token");
			String authorization = tokenType + " " + accessToken;
			System.out.println("授权码：" + authorization);
			return authorization;
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "error";
	}
	/**
	 * 发起GET请求
	 * @param url
	 * @param params
	 * @param contentType
	 * @return
	 * @throws IOException 
	 * @throws HttpException 
	 */
	private Map<String, Object> doGetSubmit(String url) throws HttpException, IOException {
		GetMethod get = new GetMethod(url);
		get.setRequestHeader("Content-Type", "application/json");
		get.setRequestHeader("Authorization", this.authorization);
		get.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 1000 * 10);
		get.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		get.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, CHARSET);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put(STATUS, true);
		result.put(MESSAGE, "成功!");

		HttpClient httpClient = new HttpClient();
		int statusCode = httpClient.executeMethod(get);

		if (statusCode != HttpStatus.SC_OK) result.put(STATUS, false);

		int contentLength = (int) get.getResponseContentLength();
		byte[] buffer = new byte[contentLength];
		for (int i = 0; i < contentLength;) {
			int readLength = get.getResponseBodyAsStream().read(buffer, i, contentLength - i);
			if (readLength == -1)
				break;
			i += readLength;
		}

		result.put(MESSAGE, new String(buffer, CHARSET));
		return result;
	}
	/***
	 * 发起PUT请求
	 * @param url    请求地址
	 * @param params 请求参数
	 * @return
	 * @throws IOException 
	 * @throws HttpException 
	 */
	private Map<String, Object> doPutSubmit(String url, Map<String, Object> params) throws HttpException, IOException {
		PutMethod put = new PutMethod(url);
		if (params != null && params.size() > 0) {
			String jsonData = JSONObject.fromObject(params).toString();
			System.out.println("参数：" + jsonData);
			RequestEntity requestEntity = new StringRequestEntity(jsonData.toString(), "application/json", CHARSET);
			put.setRequestEntity(requestEntity);
		}
		put.setRequestHeader("Content-Type", "application/json");
		put.setRequestHeader("Authorization", this.authorization);
		put.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 1000 * 10);
		put.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		put.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, CHARSET);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put(STATUS, true);
		result.put(MESSAGE, "成功!");

		HttpClient httpClient = new HttpClient();
		int statusCode = httpClient.executeMethod(put);

		if (statusCode != HttpStatus.SC_OK) result.put(STATUS, false);

		int contentLength = (int) put.getResponseContentLength();
		byte[] buffer = new byte[contentLength];
		for (int i = 0; i < contentLength;) {
			int readLength = put.getResponseBodyAsStream().read(buffer, i, contentLength - i);
			if (readLength == -1)
				break;
			i += readLength;
		}

		result.put(MESSAGE, new String(buffer, CHARSET));
		return result;
	}
	/***
	 * 发起POST请求
	 * @param url    请求地址
	 * @param params 请求参数
	 * @return
	 * @throws IOException 
	 * @throws HttpException 
	 */
	private Map<String, Object> doPostSubmit(String url, Map<String, Object> params) throws HttpException, IOException {
		PostMethod post = new PostMethod(url);
		if (params != null && params.size() > 0) {
			String jsonData = JSONObject.fromObject(params).toString();
			System.out.println("参数：" + jsonData);
			RequestEntity requestEntity = new StringRequestEntity(jsonData.toString(), "application/json", CHARSET);
			post.setRequestEntity(requestEntity);
		}
		post.setRequestHeader("Content-Type", "application/json");
		post.setRequestHeader("Authorization", this.authorization);
		post.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 1000 * 10);
		post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, CHARSET);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put(STATUS, true);
		result.put(MESSAGE, "成功!");

		HttpClient httpClient = new HttpClient();
		int statusCode = httpClient.executeMethod(post);

		if (statusCode != HttpStatus.SC_OK) result.put(STATUS, false);

		int contentLength = (int) post.getResponseContentLength();
		byte[] buffer = new byte[contentLength];
		for (int i = 0; i < contentLength;) {
			int readLength = post.getResponseBodyAsStream().read(buffer, i, contentLength - i);
			if (readLength == -1)
				break;
			i += readLength;
		}

		result.put(MESSAGE, new String(buffer, CHARSET));
		return result;
	}
	
	// 一些常用固定值
	private static final String CHARSET = "UTF-8";
	private static final String STATUS = "status";
	private static final String MESSAGE = "message";
	
	
	// 玩家接口
	private static final String 代理查询玩家名是否被注册 = "/api/player";
	private static final String 代理创建玩家账户 = "/api/player";
	private static final String 代理获取玩家信息 = "/api/player/players";
	private static final String 代理重置玩家密码 = "/api/player/reset-password";
	// 游戏记录接口
	private static final String 获取游戏记录 = "/api/tickets/game-record";
	// 交易接口
	private static final String 代理查询玩家余额 = "/api/trans/balance";
	private static final String 代理查询玩家订单状态 = "/api/trans/order/";
	private static final String 代理给下级玩家充值或提款 = "/api/trans/player";
}