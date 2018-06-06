package com.hy.pull.common.util.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.common.util.MapUtil;
import com.hy.pull.common.util.game.av.AVUtil;
import com.hy.pull.common.util.game.sa.XmlUtil;
import com.maven.cache.SystemCache;
import com.maven.config.SystemConstant;
import com.maven.util.RandomString;
import com.maven.util.StringUtils;
import com.maven.utility.DateUtil;


/**
 * AV游戏接口
 * @author temdy
 */
public class AVGameAPI implements BaseInterface {
	
	private int pageSize = 300;
	
	private String API_URL = "http://api.service.bbtech.asia/";
	private String operatorID = "haoying";
	private String operatorPassword = "e2db61f0210aa179c909ea3ccbc7cee4";
	
	private String GAME_API_URL = "http://api.hyzonghe.net";//游戏使用的域名。代替api.hyzonghe.net
	
	/**
	 * 
	 */
	@Deprecated
	public AVGameAPI() {
	}

	public AVGameAPI(String API_URL, String operatorID, String operatorPassword,String GAME_API_URL) {
		this.API_URL = API_URL;
		this.operatorID = operatorID;
		this.operatorPassword = operatorPassword;
		this.GAME_API_URL = GAME_API_URL;
	}

	/**
	 * 创建用户接口
	 * @param entity 参数列表 operatorID,operatorPassword,userID=15长度
	 * operatorID 帐号
	 * operatorPassword 密码
	 * userID 用户编号 15个长度
	 * @return 返回结果
	 */
	@Override
	public Object createAccount(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() );
//		<bbtech>
//		    <response>
//		        <records>
//		            <record>
//		                <returnCode>200</returnCode>
//		                <description>SUCCESS</description>
//		                <session></session>
//		                <datetime></datetime>
//		            </record>
//		        </records>
//		    </response>
//		</bbtech>
	
		if(MapUtil.isNulls(entity, "userID")){
			
			if( String.valueOf(entity.get("userID")).length() > 15 ) {
				return Enum_MSG.参数错误.message("用户长度不能大于15位");
			}
			
			StringBuilder param = new StringBuilder();
			param.append("operatorID=");
			param.append(this.operatorID);
			param.append("&operatorPassword=");
			param.append(this.operatorPassword);
			param.append("&currency=");
			param.append("CNY");
			param.append("&userID=");
			param.append(entity.get("userID"));
			String result = sendPost(API_URL.concat("/API/createAccount") +"?", param.toString());
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() , result);
			
			if(result.indexOf("version") > -1 && result.indexOf("xml") > -1) {//正常调用并返回xml格式数据
				
				Map<String, String> object = XmlUtil.getQueryParamsAV(result);
				if(object.get("returnCode").equals("200")) {
					return Enum_MSG.成功.message("创建成功");
				} else {
					if(object.get("returnCode").equals("106")) {
						return Enum_MSG.账号已存在.message(object.get("message"));
					}
					return Enum_MSG.失败.message(object.get("returnCode"), object.get("message"));
				}
			} else {
				return result;//出现异常
			}
			
			
		}  else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	/**
	 * 获取余额
	 * @param entity 参数列表
	 * @return 返回结果 余额balance
	 */
	@Override
	public Object getBalance(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() );
//		<bbtech>
//		    <response>
//		        <records>
//		            <record>
//		                <balance></balance>
//		                <currency></currency>
//		            </record>
//		        </records>
//		    </response>
//		</bbtech>
	
		if(MapUtil.isNulls(entity, "userID")){
			
			StringBuilder param = new StringBuilder();
			param.append("operatorID=");
			param.append(this.operatorID);
			param.append("&operatorPassword=");
			param.append(this.operatorPassword);
			param.append("&currency=");
			param.append("CNY");
			param.append("&userID=");
			param.append(entity.get("userID"));
			
			String result = sendPost(API_URL.concat("/API/getAccountBalance") +"?", param.toString());
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() ,result );
			
			if(result.indexOf("version") > -1 && result.indexOf("xml") > -1) {//正常调用并返回xml格式数据
				
				Map<String, String> object = XmlUtil.getQueryParamsAV(result);
				if(object.containsKey("balance")) {
					return Enum_MSG.成功.message(object.get("balance"));
				} else {
					return Enum_MSG.失败.message(object.get("returnCode"), object.get("message"));
				}
			} else {
				return result;//出现异常
			}
			
			
		}  else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	/**
	 * 下分（取款）的接口
	 * @param entity 参数列表 userID,amount,transactionID
	 * 
	 * transactionID=单号，20位长度
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object withdraw(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString()  );
//		<bbtech>
//		    <response>
//		        <records>
//		            <record>
//		                <returnCode>200</returnCode>
//		                <description>SUCCESS</description>
//		                <transactionID></transactionID>
//		                <balance></balance>
//		                <currency></currency>
//		            </record>
//		        </records>
//		    </response>
//		</bbtech>
	
		if(MapUtil.isNulls(entity, "userID,amount,transactionID")){
			
			if( String.valueOf(entity.get("transactionID")).length() != 20 ) {
				return Enum_MSG.参数错误.message("交易单号必须为20位");
			}
			if( Double.valueOf(entity.get("amount").toString()) < 1.0 ) {
				return Enum_MSG.参数错误.message("取款金额不能小于1元");
			}
			
			StringBuilder param = new StringBuilder();
			param.append("operatorID=");
			param.append(this.operatorID);
			param.append("&operatorPassword=");
			param.append(this.operatorPassword);
			param.append("&currency=");
			param.append("CNY");
			param.append("&userID=");
			param.append(entity.get("userID"));
			param.append("&amount=");
			param.append(entity.get("amount"));
			param.append("&transactionID=");
			param.append(entity.get("transactionID"));
			
			String result = sendPost(API_URL.concat("/API/AccountDebit") +"?", param.toString());
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() , result );
			
			if(result.indexOf("version") > -1 && result.indexOf("xml") > -1) {//正常调用并返回xml格式数据
				
				Map<String, String> object = XmlUtil.getQueryParamsAV(result);
				if(object.get("returnCode").equals("200")) {
					return Enum_MSG.成功.message(object.get("balance"));
				} else {
					return Enum_MSG.失败.message(object.get("returnCode"), object.get("message"));
				}
			} else {
				return result;//出现异常
			}
			
			
		}  else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	/**
	 * 上分（存款）的接口 
	 * @param entity 参数列表 userID,amount,transactionID
	 * @return 返回结果  余额balance
	 */
	@Override
	public Object deposit(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString()  );
//		<bbtech>
//		    <response>
//		        <records>
//		            <record>
//		                <returnCode>200</returnCode>
//		                <description>SUCCESS</description>
//		                <transactionID></transactionID>
//		                <balance></balance>
//		                <currency></currency>
//		            </record>
//		        </records>
//		    </response>
//		</bbtech>
	
		if(MapUtil.isNulls(entity, "userID,amount,transactionID")){
			
			if( String.valueOf(entity.get("transactionID")).length() != 20 ) {
				return Enum_MSG.参数错误.message("交易单号必须为20位");
			}
			
			StringBuilder param = new StringBuilder();
			param.append("operatorID=");
			param.append(this.operatorID);
			param.append("&operatorPassword=");
			param.append(this.operatorPassword);
			param.append("&currency=");
			param.append("CNY");
			param.append("&userID=");
			param.append(entity.get("userID"));
			param.append("&amount=");
			param.append(entity.get("amount"));
			param.append("&transactionID=");
			param.append(entity.get("transactionID"));
			
			String result = sendPost(API_URL.concat("/API/AccountCredit") +"?", param.toString());
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() ,result );
			
			if(result.indexOf("version") > -1 && result.indexOf("xml") > -1) {//正常调用并返回xml格式数据
				
				Map<String, String> object = XmlUtil.getQueryParamsAV(result);
				if(object.get("returnCode").equals("200")) {
					return Enum_MSG.成功.message(object.get("balance"));
				} else {
					return Enum_MSG.失败.message(object.get("returnCode"), object.get("message"));
				}
			} else {
				return result;//出现异常
			}
			
			
		}  else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	/**
	 * 获取游戏结果
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	@Override
	public Object getRecord(Map<String, Object> entity) {
		// TODO Auto-generated method stub
		return null;
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
	 * 获取订单接口（注意：此接口有点问题。无法根据单号精确匹配。所以通过程序二次检索订单返回。userID,transactionID,transType=DEPOSIT, WITHDRAW ）
	 * @param entity 参数列表 
	 * @return 返回结果
	 */
	@Override
	public Object getOrder(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString()  );
//		<BBTECH>
//		    <response>
//		        <pageInfo>
//		            <currPage>1</currPage>
//		            <pageSize></pageSize>
//		            <totalPage></totalPage>
//		            <totalCount></totalCount>
//		        </pageInfo>
//		        <records>
//		            <record>
//		                <index>1</index>
//		                <tid></tid>
//		                <transID></transID>
//		                <transType></transType>
//		                <amount></amount>
//		                <currency></currency>
//		                <userID></userID>
//		                <datetime></datetime>
//		            </record>
//		        </records>
//		    </response>
//		</BBTECH>
		
		
		
		
		if(MapUtil.isNulls(entity, "userID,transactionID,transType")){
			
			if( String.valueOf(entity.get("transactionID")).length() != 20 ) {
				return Enum_MSG.参数错误.message("交易单号必须为20位");
			}
			if( !String.valueOf(entity.get("transType")).equals("DEPOSIT") &&
				!String.valueOf(entity.get("transType")).equals("WITHDRAW") 	) {
				return Enum_MSG.参数错误.message("交易类型只能接受DEPOSIT存款与WITHDRAW取款两种标识符");
			}
			//startDate,endDate,page,pageSize
			Date now = new Date();
			
			//只检索当天与昨天的
			String page = "1";
			String startDate = parseDate(addDate(now, Calendar.DATE, -1), "yyyyMMdd") ;
			String endDate = parseDate(now, "yyyyMMdd");
			
			StringBuilder param = new StringBuilder();
			param.append("operatorID=");
			param.append(this.operatorID);
			param.append("&operatorPassword=");
			param.append(this.operatorPassword);
			param.append("&currency=");
			param.append("CNY");
			
			param.append("&userID=");
			param.append(entity.get("userID"));
			param.append("&transType=");
			param.append(entity.get("transType"));
			param.append("&startDate=");
			param.append(startDate);
			param.append("&endDate=");
			param.append(endDate);
			param.append("&page=");
			param.append(page);
			param.append("&pageSize=");
			param.append(this.pageSize);
			
			String result = sendPost(API_URL.concat("/API/searchTransaction") +"?", param.toString());
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() ,result );
			
			if(result.indexOf("version") > -1 && result.indexOf("xml") > -1) {//正常调用并返回xml格式数据
				
				List<Map<String, String>> object = XmlUtil.getQueryParamsAVList(result);
				//返回的结果集再次进行匹配单号
				for (Map<String, String> map : object) {
//					System.out.println(map);
					// 找到了
					if(map.get("transID").equals( String.valueOf(entity.get("transactionID")) )) {
						
						return Enum_MSG.成功.message(map.get("amount"));//返回本单金额
					}
				}
				
				return Enum_MSG.失败.message("没有找到订单");//
				
			} else {
				return result;//出现异常
			}
			
			
		}  else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	/**
	 * unicode 转字符串
	 */
	public static String unicode2String(String unicode) {
	 
	    StringBuffer string = new StringBuffer();
	 
	    String[] hex = unicode.split("\\\\u");
	 
	    for (int i = 1; i < hex.length; i++) {
	 
	        // 转换出每一个代码点
	        int data = Integer.parseInt(hex[i], 16);
	 
	        // 追加成string
	        string.append((char) data);
	    }
	 
	    return string.toString();
	}
	/**
	 * 登录的接口
	 * 
	 * 流程：获取H5或者PC的登录地址，将返回的session code替换，请求即可
	 * 
	 * 获取所有游戏的URL地址保存到redis，然后每次请求登录时先创建会话，将返回的session code替换，请求即可（链接中包含url特殊符号，需要转换）
	 * 
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	@Override
	public Object login(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString()  );
		
		if(MapUtil.isNulls(entity, "userID,deviceType")){
			
			String deviceType = String.valueOf(entity.get("deviceType"));
			if( !deviceType.equals("0") && !deviceType.equals("1")) {
				return Enum_MSG.参数错误.message("AV游戏目前只能接受PC端与手机H5端的请求，即设备类型为0和1");
			}
			
			List<Map<String, String>> listGame = null;
			if( listGame == null || listGame.size() == 0) {
				listGame = (List)getGameListRNG(entity);
//				SystemCache.getInstance().setAvGameList(listGame);
				
				for (Map<String, String> map : listGame) {
					
					map.put("gameName-ch", StringEscapeUtils.unescapeHtml(map.get("gameName-ch")));//中文转码
					map.put("gameName-jp", StringEscapeUtils.unescapeHtml(map.get("gameName-jp")));//中文转码
					
					String gameLink = map.get("gameURL");
					gameLink = gameLink.replaceAll("&amp;", "&");
					
					map.put("gameURL", gameLink);//链接转码
//					System.out.println(map.get("gameName-en") + "=" +map.get("gameName-ch") + "=" +map.get("gameName-jp") + "=" + map.get("type2")+ "=" + map.get("type1"));
				}
				
				System.out.println("已从接口获取到游戏列表数据"+listGame.size());
			} else {
				System.out.println("已从缓存获取到游戏列表数据");
			}
			
			StringBuilder param = new StringBuilder();
			param.append("operatorID=");
			param.append(this.operatorID);
			param.append("&operatorPassword=");
			param.append(this.operatorPassword);
			param.append("&currency=");
			param.append("CNY");
			param.append("&userID=");
			param.append(entity.get("userID"));
			
			String result = sendPost(API_URL.concat("/API/generateSession") +"?", param.toString());
			System.out.println("调用结果："+result);
			
			if(result.indexOf("version") > -1 && result.indexOf("xml") > -1) {//正常调用并返回xml格式数据
				
				Map<String, String> object = XmlUtil.getQueryParamsAV(result);
				if(object.get("returnCode").equals("200")) {
					
					String sessionCode = object.get("session");
					
					StringBuilder url = new StringBuilder();
					try {
						
						String API_ROOT = this.GAME_API_URL;
						
						url.append(API_ROOT);//API路径
						
						if( deviceType.equals("0")) {//PC
							url.append("/avgame/index");
						} else {//H5
							url.append("/avgame/indexh5");
						}
						
						url.append("?sessionCode=").append(sessionCode).append("&");//
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() , url.toString() );
					
					return Enum_MSG.成功.message(url.toString());
					
				} else {
					return Enum_MSG.失败.message(object.get("returnCode"), object.get("message"));
				}
			} else {
				return result;//出现异常
			}
			
			
		}  else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}
	public Object getSeesion(Map<String, Object> entity) {
		if(MapUtil.isNulls(entity, "userID")){
			
			
			StringBuilder param = new StringBuilder();
			param.append("operatorID=");
			param.append(this.operatorID);
			param.append("&operatorPassword=");
			param.append(this.operatorPassword);
			param.append("&currency=");
			param.append("CNY");
			param.append("&userID=");
			param.append(entity.get("userID"));
			
			String result = sendPost(API_URL.concat("/API/getSession") +"?", param.toString());
			System.out.println("调用结果："+result);
			
			if(result.indexOf("version") > -1 && result.indexOf("xml") > -1) {//正常调用并返回xml格式数据
				
				Map<String, String> object = XmlUtil.getQueryParamsAV(result);
				if(object.get("returnCode").equals("200")) {
					return Enum_MSG.成功.message(object.get("session"));
				} else {
					return Enum_MSG.失败.message(object.get("returnCode"), object.get("message"));
				}
			} else {
				return result;//出现异常
			}
			
			
		}  else {
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

	/**
	 * 获取游戏列表
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	public Object getGameListRNG(Map<String, Object> entity) {
		if(MapUtil.isNulls(entity, "userID")){
			
			
			StringBuilder param = new StringBuilder();
			param.append("operatorID=");
			param.append(this.operatorID);
			param.append("&operatorPassword=");
			param.append(this.operatorPassword);
			param.append("&currency=");
			param.append("CNY");
			param.append("&userID=");
			param.append(entity.get("userID"));
			
			String result = sendPost(API_URL.concat("/API/getGameListRNG") +"?", param.toString());
			System.out.println("调用结果："+result);
			if(result.indexOf("version") > -1 && result.indexOf("xml") > -1) {//正常调用并返回xml格式数据
				
				return XmlUtil.getQueryParamsAVListGame(result);
			} else {
				return result;//出现异常
			}
			
			
		}  else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}
	
	/**
	 * 获取游戏列表（在线）
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	public Object getLiveGameLobby(Map<String, Object> entity) {
		
		if(MapUtil.isNulls(entity, "userID,3rdparty")){
			
			
			StringBuilder param = new StringBuilder();
			param.append("operatorID=");
			param.append(this.operatorID);
			param.append("&operatorPassword=");
			param.append(this.operatorPassword);
			param.append("&currency=");
			param.append("CNY");
			param.append("&userID=");
			param.append(entity.get("userID"));
			
			String result = sendPost(API_URL.concat("/API/getLiveGameLobby") +"?", param.toString());
			System.out.println("调用结果："+result);
			
			
			if(result.indexOf("version") > -1 && result.indexOf("xml") > -1) {//正常调用并返回xml格式数据
				
				Map<String, String> object = XmlUtil.getQueryParamsAV(result);
				if(object.get("returnCode").equals("200")) {
					return Enum_MSG.成功.message(object.get("balance"));
				} else {
					return Enum_MSG.失败.message(object.get("returnCode"), object.get("message"));
				}
			} else {
				return result;//出现异常
			}
			
			
		}  else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}
	
	
	/**
	 * 发送POST请求
	 * 
	 * @param url
	 *            请求URL
	 * @param param
	 *            请求参数
	 * @return 响应数据
	 */
	private static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
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
			// e.printStackTrace();
			System.out.println("发送 POST 请求出现异常！" + e);
			
			return Enum_MSG.接口调用错误.message(e.getMessage());
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
	private static StringBuilder getResponseText(InputStream netIps, StringBuilder builder) throws Exception {
		byte[] buffer = new byte[1024];
		int len;
		while ((len = netIps.read(buffer)) != -1) {
			builder.append(new String(buffer, 0, len, "UTF-8"));
		}
		return builder;

	}
	
	/**
	 * 把日期转换成指定格式的日期字符串
	 * @Method parse
	 * @param  @param date【需要转换的日期】
	 * @param  @param format【合法的日期时间格式如：yyyy-MM-dd、yyyy-MM-dd HH：mm：ss、yyyy年MM月dd、yyyy/MM/dd HH：mm：ss等】
	 * @param  @return
	 * @return String
	 * @throws
	 */
	private static String parseDate(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String temp = "";
		if(date!=null){
			try {
				temp = sdf.format(date);
			} catch (Exception e) {
				System.out.println(date + "不是【" + format + "】格式的日期！");
			}
		}
		return temp;
	}
	/**
	 * 把日期date的parm参数的值加上number
	 * @Method add
	 * @param  @param date【需要进行运算的日期】
	 * @param  @param parm【日期参数如：年（Calendar.YEAR）、月（Calendar.MONTH）、日（Calendar.DATE）、小时（Calendar.HOUR）、分钟（Calendar.MINUTE）、秒（Calendar.SECOND）】
	 * @param  @param number【需要加的数值。传入负数可进行减操作】
	 * @param  @return
	 * @return Date
	 * @throws
	 */
	private static Date addDate(Date date,int parm,int number){
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(parm, number);
		return ca.getTime();
	}
	public static void main(String[] args) {
		AVGameAPI gameAPI = new AVGameAPI();
		
		Map<String, Object> entity = new HashMap<String, Object>();
		String userID = RandomString.createRandomString(15);
		System.out.println(userID);
		
		entity.put("userID", "y&w*2_0X@2#Q39x");
		entity.put("3rdparty", "NT0005");
		
		entity.put("amount", "1");
		entity.put("transType", "WITHDRAW");
//		2e4f9fd926f5fdc3ed1559ea168f0cc1
//		http://gspotslots.bbtech.asia/UIS/connect?token=2e4f9fd926f5fdc3ed1559ea168f0cc1&amp;config=tokyo_ch&amp;gameconfig=mysisters&amp;room=10002
//		http://gspotslots.bbtech.asia/UIS/connect?token=691eb71a20371d4f8f05fec8298c704e&amp;config=tokyo_ch&amp;gameconfig=slotgames_lines25_yakinbyouto_slots&amp;room=10002
//		http://gspotslots.bbtech.asia/UIS/connect?token=691eb71a20371d4f8f05fec8298c704e&config=tokyo_ch&gameconfig=miracle_jq_ayu&room=10002
		entity.put("deviceType", "1");
		
		String transactionID = RandomString.createRandomString(20);
		entity.put("transactionID", transactionID);
		System.out.println("transactionID="+transactionID);
		
//		entity.put("userID", RandomString.createRandomString(15));
		
//		5G0vVVB9a0E0r7gLSdj0=DEPOSIT
//		U19DJ6mUl1xQtif4xb1d=WITHDRAW
		 
		System.out.println(gameAPI.createAccount(entity));
//		System.out.println(gameAPI.deposit(entity));
//		System.out.println(gameAPI.withdraw(entity));
//		System.out.println(gameAPI.getBalance(entity));
//		System.out.println(gameAPI.getLiveGameLobby(entity));
//		System.out.println(gameAPI.getSeesion(entity));
//		System.out.println(gameAPI.getGameListRNG(entity));
		
//		System.out.println(gameAPI.getOrder(entity));
		
		System.out.println("hyecz00123vmhbj3hwka".length());
		
	}
}
