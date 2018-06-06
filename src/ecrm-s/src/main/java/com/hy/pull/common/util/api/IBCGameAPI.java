package com.hy.pull.common.util.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.common.util.MapUtil;
import com.hy.pull.common.util.game.sa.XmlUtil;
import com.maven.config.SystemConstant;
import com.maven.util.RandomString;
import com.maven.util.StringUtils;

/**
 * IBC游戏接口
 * @author temdy
 */
public class IBCGameAPI implements BaseInterface {
	
	private String API_URL = "http://cashapi.dg20mu.com/cashapi/DoBusiness.aspx";
	private String KEY = "hq@-@688*$-$*!&88$dshxeh";
	private String AGENT = "OGCNYHYI";
	
	private String GAME_API_URL = "http://api.hyzonghe.net";//游戏使用的域名。代替api.hyzonghe.net
	
//	http://cashapi.dg20mu.com/cashapi/DoBusiness.aspx?params=
//	http://cashapi.dg20mu.com/cashapi/getdata.aspx?params=
	
	/**
	 * 默认构造函数
	 * 
	 * 不传递时使用默认的进行测试，正式时需要传递参数进来，此方法不使用
	 */
	@Deprecated
	public IBCGameAPI(){
		
	}
	
	/**
	 * 构建函数，初始化参数
	 * @param API_URL 接口URL
	 * @param KEY 密钥
	 */
	public IBCGameAPI(String API_URL,String KEY,String AGENT, String GAME_API_URL){
		this.API_URL = API_URL;
		this.KEY = KEY;
		this.AGENT = AGENT;
		this.GAME_API_URL = GAME_API_URL;
	}

	/**
	 * 创建用户接口
	 * @param entity 参数列表 username,password,limit,limitvideo,limitroulette
	 * agent 代理帐号
	 * username 用户名
	 * password 密码
	 * moneysort 币种类型 U.S. Dollar(USD), RMB(RMB), Malaysia Dollar(MYR), Korea Won(KRW), Singapore Dollar(SGD), Hong Kong Dollar(HKD)
	 * limit 游戏列表 Game Limit Ex:1,1,1,1,1,1,1,1,1,1,1,1,1, and 1 is can ,0 is not can.13 numbers
	 * limitvideo 视频列表 The limit of video,must be one number.
	 * limitroulette 密码 The limit of roulette,must be one number.
	 * @return 返回结果
	 */
	@Override
	public Object createAccount(Map<String, Object> entity) {
		
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() );
		
//		key_error	The key value is error
//		1	successful
//		0	Maybe username already exist
//		2	the password no right
//		3	Username is too long
//		10	The agent not exist

		if(MapUtil.isNulls(entity, "username,password,limit,limitvideo,limitroulette")){
			
			if( String.valueOf(entity.get("username")).length() > 20 ) {
				return Enum_MSG.参数错误.message("用户长度不能大于20位");
			}
			if( String.valueOf(entity.get("password")).length() > 20 ) {
				return Enum_MSG.参数错误.message("密码长度不能大于20位");
			}
			
			//先检查账号是否已存在
			StringBuilder param = new StringBuilder();
			param.append("agent=");
			param.append(this.AGENT);
			param.append("$username=");
			param.append(entity.get("username").toString()+"");
			param.append("$method=caie");
			String result = post(param.toString());
			System.out.println("检查账号是否存在：调用结果："+result);
			
//			key_error	The key value is error
//			1	The customer exist
//			0	The customer not exist
//			10	The agent not exist
			
			if(result.indexOf("version") > -1 && result.indexOf("xml") > -1) {//正常调用并返回xml格式数据
				
				Map<String, String> object = XmlUtil.getQueryParamsRoot(result);
				
				if(object.get("result").equals("0")) {//不存在
					
					// 开始创建
					param = new StringBuilder();
					param.append("agent=");
					param.append(this.AGENT);
					param.append("$username=");
					param.append(entity.get("username").toString()+"");
					param.append("$moneysort=");
					param.append(entity.get("moneysort") == null ? "RMB" : entity.get("moneysort"));
					param.append("$password=");
					param.append(entity.get("password"));
					param.append("$limit=");
					param.append(entity.get("limit"));
					param.append("$limitvideo=");
					param.append(entity.get("limitvideo"));
					param.append("$limitroulette=");
					param.append(entity.get("limitroulette"));
					param.append("$method=caca");
					result = post(param.toString());
					System.out.println("调用结果："+result);
					BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() , result );
					
					if(result.indexOf("version") > -1 && result.indexOf("xml") > -1) {//正常调用并返回xml格式数据
						
						object = XmlUtil.getQueryParamsRoot(result);
						
						if(object.get("result").equals("1")) {
							return Enum_MSG.成功.message("成功");
						} else {
							return Enum_MSG.失败.message("返回错误代码："+object.get("result")+"，请对照错误代码");
						}
						
					} else {
						return result;//出现异常
					}
					
				} else if(object.get("result").equals("1")) {//已经存在
					
					return Enum_MSG.账号已存在.message("已存在账号"+entity.get("username"));
				} else {
					
					return Enum_MSG.失败.message("返回错误代码："+object.get("result")+"，请对照错误代码");
				}
				
			} else {
				return result;//出现异常
			}
			
			
		} else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
		
	}

	/**
	 * 获取余额
	 * @param entity 参数列表 username,password
	 * username 用户名
	 * password 密码
	 * @return 返回结果
	 */
	@Override
	public Object getBalance(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString()  );
//		如何检查调用成功或返回成功：首先判断是否是数字，并且是带2个小数点金额数字，返回单纯的10还不行
		
//		key_error	The key value is error
//		Account_no_exist	The “username” which you want to query
//		credit is not exist
//		0.000	Successful,will return the balance , hold 2 digits
//		after the point. for example: 100.00
//		10	The agent not exist

		
		if(MapUtil.isNulls(entity, "username,password")){
			StringBuilder param = new StringBuilder();
			param.append("agent=");
			param.append(this.AGENT);
			param.append("$username=");
			param.append(entity.get("username"));
			param.append("$password=");
			param.append(entity.get("password"));
			param.append("$method=gb");
			String result = post(param.toString());

			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() ,result );
			
			if(result.indexOf("version") > -1 && result.indexOf("xml") > -1) {//正常调用并返回xml格式数据
				
				Map<String, String> object = XmlUtil.getQueryParamsRoot(result);
				
				if(StringUtils.isNumberFloat(object.get("result"))) {
					return Enum_MSG.成功.message(object.get("result"));
				} else {
					return Enum_MSG.失败.message("返回错误代码："+object.get("result")+"，请对照错误代码");
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
	 * @param entity 参数列表 username,password,billno,credit
	 * username 用户名
	 * password 密码
	 * billno 帐单号 billno=(cagent + sequence), sequence is number(13~16 digit)
	 * credit 分数 两位小数点的数字
	 * @return 返回结果
	 */
	@Override
	public Object withdraw(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString()  );
		
//		key_error	The key value is error
//		1	successful
//		0	Failure.
//		no_enough_credit	Not enough money to withdraw.
//
//		account_no_exist	The “username” which you want to query
//		credit is not exist
//
//		10	The agent not exist

		
		if(MapUtil.isNulls(entity, "username,password,billno,credit")){
			
			if( String.valueOf(entity.get("billno")).length() != 16 ) {
				return Enum_MSG.参数错误.message("订单号长度不能大于16位，并且是纯数字");
			}
			if( Double.valueOf(entity.get("credit").toString()) < 1.0 ) {
				return Enum_MSG.参数错误.message("取款金额不能小于1元");
			}
			
			StringBuilder param = new StringBuilder();
			param.append("agent=");
			param.append(this.AGENT);
			param.append("$username=");
			param.append(entity.get("username"));
			param.append("$password=");
			param.append(entity.get("password"));
			param.append("$billno=");
			param.append(this.AGENT + entity.get("billno").toString());
			param.append("$type=OUT");
			param.append("$credit=");
			param.append(Double.valueOf(entity.get("credit").toString()));
			param.append("$method=ptc");
			String result = post(param.toString());

			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() ,result );
			
			if(result.indexOf("version") > -1 && result.indexOf("xml") > -1) {//正常调用并返回xml格式数据
				
				Map<String, String> object = XmlUtil.getQueryParamsRoot(result);
				
				if(object.get("result").equals("1")) {
					return Enum_MSG.成功.message("成功");
				} else {
					return Enum_MSG.失败.message("返回错误代码："+object.get("result")+"，请对照错误代码");
				}
				
			} else {
				return result;//出现异常
			}
			
		} else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	/**
	 * 上分（存款）的接口
	 * @param entity 参数列表 username,password,billno,credit
	 * username 用户名
	 * password 密码
	 * billno 帐单号  billno=(cagent + sequence), sequence is number(13~16 digit)
	 * credit 分数
	 * @return 返回结果
	 */
	@Override
	public Object deposit(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString()  );
//		key_error	The key value is error
//		1	successful
//		0	Fail Fail,because of invalid transfer Credit,not
//		allowed to transfer.
//		account_no_exist	The “username” which you want to query
//		credit is not exist
//
//		10	The agent not exist
		
		if(MapUtil.isNulls(entity, "username,password,billno,credit")){
			
			if( String.valueOf(entity.get("billno")).length() != 16 ) {
				return Enum_MSG.参数错误.message("订单号长度不能大于16位，并且是纯数字");
			}
			
			StringBuilder param = new StringBuilder();
			param.append("agent=");
			param.append(this.AGENT);
			param.append("$username=");
			param.append(entity.get("username"));
			param.append("$password=");
			param.append(entity.get("password"));
			param.append("$billno=");
			param.append(this.AGENT + entity.get("billno").toString());
			param.append("$type=IN");
			param.append("$credit=");
			param.append(Double.valueOf(entity.get("credit").toString()));
			param.append("$method=ptc");
			String result = post(param.toString());
			
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() ,result );
			
			if(result.indexOf("version") > -1 && result.indexOf("xml") > -1) {//正常调用并返回xml格式数据
				
				Map<String, String> object = XmlUtil.getQueryParamsRoot(result);
				
				if(object.get("result").equals("1")) {
					return Enum_MSG.成功.message("成功");
				} else {
					return Enum_MSG.失败.message("返回错误代码："+object.get("result")+"，请对照错误代码");
				}
				
			} else {
				return result;//出现异常
			}
		} else {
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
	 * 更新信息接口（修改密码）
	 * @param entity 参数列表 username,password,newpassword
	 * @return 返回结果
	 */
	@Override
	public Object updateInfo(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "updateInfo", entity.toString()  );
//		如何检查调用成功或返回成功：首先判断是否是数字，并且是带2个小数点金额数字，返回单纯的10还不行
		
//
//		key_error	The key value is error
//		1	success
//		0	fail
//		10	The agent not exist

		
		if(MapUtil.isNulls(entity, "username,password,newpassword")){
			StringBuilder param = new StringBuilder();
			param.append("agent=");
			param.append(this.AGENT);
			param.append("$username=");
			param.append(entity.get("username"));
			param.append("$password=");
			param.append(entity.get("newpassword"));
			param.append("$method=ua");
			String result = post(param.toString());

			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "updateInfo", entity.toString() ,result );
			
			if(result.indexOf("version") > -1 && result.indexOf("xml") > -1) {//正常调用并返回xml格式数据
				
				Map<String, String> object = XmlUtil.getQueryParamsRoot(result);
				
				if(StringUtils.isNumberFloat(object.get("result"))) {
					return Enum_MSG.成功.message(object.get("result"));
				} else {
					return Enum_MSG.失败.message("返回错误代码："+object.get("result")+"，请对照错误代码");
				}
				
			} else {
				return result;//出现异常
			}
			
			
		}  else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	/**
	 * 获取订单接口
	 * @param entity 参数列表 username,password,billno,credit,type
	 * 
	 * type=IN/OUT
	 * @return 返回结果 返回0表示成功
	 */
	@Override
	public Object getOrder(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() );
//		key_error	The key value is error
//		1	successful
//		0	Fail Fail,because of invalid transfer Credit,not
//		allowed to transfer.
//		account_no_exist	The “username” which you want to query
//		credit is not exist
//
//		10	The agent not exist
		
		if(MapUtil.isNulls(entity, "username,password,billno,credit,type")){
			
			if( String.valueOf(entity.get("billno")).length() != 16 ) {
				return Enum_MSG.参数错误.message("订单号长度不能大于16位，并且是纯数字");
			}
			if( !String.valueOf(entity.get("type")).equals("IN") && !String.valueOf(entity.get("type")).equals("OUT")) {
				return Enum_MSG.参数错误.message("业务类型必须是IN或者OUT");
			}
			
			StringBuilder param = new StringBuilder();
			param.append("agent=");
			param.append(this.AGENT);
			param.append("$username=");
			param.append(entity.get("username"));
			param.append("$password=");
			param.append(entity.get("password"));
			param.append("$billno=");
			param.append(this.AGENT + entity.get("billno").toString());
			param.append("$type=");
			param.append(entity.get("type"));
			param.append("$credit=");
			param.append(Double.valueOf(entity.get("credit").toString()));
			param.append("$method=ctc");
			String result = post(param.toString());
			
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() ,result );
			
			if(result.indexOf("version") > -1 && result.indexOf("xml") > -1) {//正常调用并返回xml格式数据
				
				Map<String, String> object = XmlUtil.getQueryParamsRoot(result);
				
				if(object.get("result").equals("1")) {
					return Enum_MSG.成功.message("成功");
				} else {
					return Enum_MSG.失败.message("返回错误代码："+object.get("result")+"，请对照错误代码");
				}
				
			} else {
				return result;//出现异常
			}
		} else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	/**
	 * 登录的接口（沙巴体育有PC和H5版本）
	 * @param entity 参数列表 username,password,gametype（gamekind）
	 * agent 代理帐号
	 * username 用户名
	 * password 密码
	 * domain 域名
	 * gametype 游戏类型
value:
1: 视讯
2: 体育
3: 彩票
4: 电子游戏
11:新平台(明升)
21:手机体育,og手机版

	 * gamekind 游戏种类 默认为0，如果为电子游戏时，需要传递具体的代码（附：电子游戏种类表）
	 * platformname 平台名称 平台名称:Oriental,ibc,ag,opus
	 * @return 返回结果 直接将参数POST请求到目标网址
	 */
	@Override
	public Object login(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString()  );
//		code value as bellow:
//		0	Account can’t be used
//		account_no_exist	Account not exist
//		10	The agent not exist

		
		if(MapUtil.isNulls(entity, "username,password,gametype")){
			
			if(
				!String.valueOf(entity.get("gametype")).equals("2") &&//体育
				
				!String.valueOf(entity.get("gametype")).equals("21") ) {//手机体育,og手机版
				
				return Enum_MSG.参数错误.message("gametype参数只能专递2,21代码");
			}
			
			if(String.valueOf(entity.get("gametype")).equals("4") && !entity.containsKey("gamekind")) {
				return Enum_MSG.参数错误.message("gametype参数为4电子游戏时，请传递gamekind游戏代码参数");
			} else {
				entity.put("gamekind", 0);
			}
			
			String apiroot = this.GAME_API_URL;
			
			StringBuilder param = new StringBuilder();
			param.append("agent=");
			param.append(this.AGENT);
			param.append("$username=");
			param.append(entity.get("username"));
			param.append("$password=");
			param.append(entity.get("password"));
			param.append("$domain=");
			param.append(apiroot);
			param.append("$gametype=");
			param.append(entity.get("gametype"));
			param.append("$gamekind=");
			param.append(entity.get("gamekind"));
			param.append("$platformname=ibc");
//			param.append(entity.get("platformname"));
			param.append("$lang=");
			param.append(entity.get("lang") == null ? "zh" : entity.get("lang"));//zh中文，en英文，jp日文，kr 韩文
			param.append("$method=tg");
			
			
			try {
				String paramstr = new String(Base64.encodeBase64(param.toString().getBytes("UTF-8")));
				
				System.out.println("参数原文："+param.toString());
				System.out.println("参数密文params："+paramstr);
				String key = GameHttpUtil.MD5(paramstr + KEY);
				System.out.println("参数密文key："+key);
				
				
				//	组装进入游戏的完整URL（经由api项目请求）
				StringBuilder url = new StringBuilder();
				url.append(apiroot);//API路径
				
				if( String.valueOf(entity.get("gametype")).equals("2")) {//PC
					url.append("/ibcgame/index");
				} else {//H5
					url.append("/ibcgame/indexh5");
				}
				
				url.append("?");
				
				url.append("params=").append(paramstr).append("&");
				url.append("key=").append(key).append("&");
				url.append("url=").append(URLEncoder.encode(this.API_URL,"UTF-8")).append("&");//对url进行code
				
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() ,url.toString() );
				
				return Enum_MSG.成功.message(url.toString());
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				
				return Enum_MSG.系统错误.message(e.getMessage());
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
	
	/**
	 * 获取URL
	 * @param param 参数
	 * @return 返回URL
	 */
	private String post(String param){
		try {
			StringBuilder params = new StringBuilder();
			//String p = "agent="+agent+"$vendorid="+vendorid+"$isjs=1$method=gbrbv";
			System.out.println("参数原文："+param);
			param = new String(Base64.encodeBase64(param.getBytes("UTF-8")));
			System.out.println("参数密文："+param);
			
			String key = GameHttpUtil.MD5(param + KEY);
			params.append("params=");
			params.append(param);
			params.append("&key=");
			params.append(key);
			
			String result = sendPost(API_URL+"?", params.toString());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			
			return Enum_MSG.接口调用错误.message(e.getMessage());
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
	
	public static void main(String[] arg){
		IBCGameAPI og = new IBCGameAPI();
		Map<String, Object> entity = new HashMap<String, Object>();
//		entity.put("username", RandomString.createRandomString(20));
		entity.put("username", "odITudNU40y@#&_*eoP1");
//		entity.put("username", "hyl"+RandomString.createRandomString(17));//27
		entity.put("password", "12345678");
//		entity.put("password", "1234567810");
		
		entity.put("limit", "1,1,1,1,1,1,1,1,1,1,1,1,1");
		entity.put("limitvideo", "1");
		entity.put("limitroulette", "1");
		
//		entity.put("billno", StringUtils.getCurrenDate()+"12");
		entity.put("credit", "1");
		
		entity.put("billno", "2016112413431315");
		entity.put("type", "OUT");
		
		entity.put("domain", "IN");
		entity.put("gametype", "2");
		entity.put("gamekind", "0");
//		System.out.println(og.createAccount(entity));
		System.out.println(og.deposit(entity));
//		System.out.println(og.withdraw(entity));
//		System.out.println(og.getBalance(entity));
//		System.out.println(og.getOrder(entity));username,password,domain,gametype（gamekind）
//		System.out.println(og.login(entity));
//		System.out.println(og.updateInfo(entity));
	}
}
