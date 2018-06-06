package com.hy.pull.common.util.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import org.apache.http.client.config.RequestConfig;

import com.hy.pull.common.util.Encrypt;
import com.hy.pull.common.util.MapUtil;
import com.hy.pull.common.util.game.zj.ZJUtil;
import com.maven.config.SystemConstant;
import com.maven.util.RandomString;
import com.maven.util.RandomStringNum;
import com.maven.util.HttpPostUtil.UTF8PostMethod;

import net.sf.json.JSONObject;

/**
 * 波音游戏接口
 * 
 * 说明：
 * 1、每个代理商的调用接口URL都不一样。
 * 2、IP需要白名单。
 * 3、每个API方法都有不一样的KeyB
 * 4、波音系统时间为美东时间
 * 5、每周三例行维护,读取程式时会传代码44444
 * 6、若需带上层帐号时请全面限制为「代理层级」
 * 7、查询资料时最大每页笔数全面限制为「500」 笔
 * 8、每个API方法都需要传递key参数，格式为：key=A+B+C(验证码组合方式) 
 * A= 无意义字串长度8码
 * B=MD5(website+ username + KeyB + YYYYMMDD)
 * C=无意义字串长度9码
 * YYYYMMDD为美东时间(GMT-4)(20151216)
 * 
 * @author temdy
 */
public class BBINGameAPI implements BaseInterface {

	private int pagesize = 300;
	
	private String API_URL = "http://linkapi.avia88.org/";
	private String API_URL_LOGIN = "http://888.avia88.org/";
	private String website = "oriental";
	private String uppername = "dhyapibsbw";
	
	private String GAME_API_URL = "http://api.hyzonghe.net";//游戏使用的域名。代替api.hyzonghe.net
	
	/**
	 * 默认构造函数
	 */
	@Deprecated
	public BBINGameAPI() {
		if(this.API_URL.endsWith("/")) {
			this.API_URL += "app/WebService/JSON/display.php";
			this.API_URL_LOGIN += "app/WebService/JSON/display.php";
		} else {
			this.API_URL += "/app/WebService/JSON/display.php";
			this.API_URL_LOGIN += "/app/WebService/JSON/display.php";
		}
		
	}

	/**
	 * 构建函数，初始化参数
	 * @param API_URL	本代理商的接口API地址
	 * @param website 站点名称
	 */
	public BBINGameAPI(String API_URL, String website, String uppername,String API_URL_LOGIN,String GAME_API_URL) {
		this.API_URL = API_URL;
		this.API_URL_LOGIN = API_URL_LOGIN;
		this.website = website;
		this.uppername = uppername;
		this.GAME_API_URL = GAME_API_URL;
		
		if(this.API_URL.endsWith("/")) {
			this.API_URL += "app/WebService/JSON/display.php";
		} else {
			this.API_URL += "/app/WebService/JSON/display.php";
		}
		
		if(this.API_URL_LOGIN.endsWith("/")) {
			this.API_URL_LOGIN += "app/WebService/JSON/display.php";
		} else {
			this.API_URL_LOGIN += "/app/WebService/JSON/display.php";
		}
	}

	
	/**
	 * 创建用户接口
	 * @param entity 参数列表 username,uppername,password
	 * 
	 * username=会员帐号(请输入4-20个字元,仅可输入英文字母以及数字的组合)
	 * uppername=上层帐号
	 * password=密码须为6~12码英文或数字且符合0~9及a~z字元
	 * @return 返回结果
	 */
	@Override
	public Object createAccount(Map<String, Object> entity) {
		try{
			
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() );
			
			
//			返回示例
			
			if(MapUtil.isNulls(entity, "username,password,KeyB")){
				
				if( String.valueOf(entity.get("username")).length() > 20 ) {
					return Enum_MSG.参数错误.message("用户长度不能大于20位");
				}
				if( String.valueOf(entity.get("password")).length() > 12 ) {
					return Enum_MSG.参数错误.message("密码长度不能大于12位");
				}
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("website", this.website);
				params.put("username", entity.get("username"));
				params.put("uppername", this.uppername);
				params.put("password", entity.get("password"));
				
				//生成key
				String A = RandomString.createRandomString(8);
				String B = Encrypt.MD5(this.website + entity.get("username").toString() + entity.get("KeyB").toString() + getCurrenDateMD_yyyyMMdd());
				String C = RandomString.createRandomString(9);
				String key = A  +  B  +  C;
				
				params.put("key", key);
				
				String result = doGetSubmit(API_URL + "/CreateMember", params) + "";
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() , result);
				
				JSONObject object =  JSONObject.fromObject( result );
				
				//接口调用成功
				if(object.has("result")) {
					
					if(object.getString("result").equals("true")) {
						
						return Enum_MSG.成功.message(object.getJSONObject("data").get("Message"));
					} else {
						
						if(object.getJSONObject("data").getString("Code").equals("21001") ) {
							return Enum_MSG.账号已存在.message(object.getJSONObject("data").getString("Message"));
						}
						
						return Enum_MSG.失败.message(
								object.getJSONObject("data").getString("Code"),
								object.getJSONObject("data").getString("Message"));
					}
					
				} else {//网络异常
					return object.toString();
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
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	@Override
	public Object getBalance(Map<String, Object> entity) {
		try{
			
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() );
			
//			返回示例
			
			if(MapUtil.isNulls(entity, "username,password,KeyB")){
				
//				if( String.valueOf(entity.get("username")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户名长度应为20个字符");
//				}
//				if( String.valueOf(entity.get("password")).length() != 8 ) {
//					return Enum_MSG.参数错误.message("用户登录密码长度应为8个字符");
//				}
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("website", this.website);
				params.put("username", entity.get("username"));
				params.put("password", entity.get("password"));
				params.put("uppername", this.uppername);
				
				//生成key
				String A = RandomString.createRandomString(9);
				String B = Encrypt.MD5(this.website + entity.get("username").toString() + entity.get("KeyB").toString() + getCurrenDateMD_yyyyMMdd());
				String C = RandomString.createRandomString(2);
				String key = A  +  B  +  C;
				params.put("key", key);
				
				int page = 1;
				params.put("page", page);
				params.put("pagelimit", pagesize);
				
				String result = doGetSubmit(API_URL + "/CheckUsrBalance", params);
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() , result);
				
				JSONObject object =  JSONObject.fromObject( result );
				
				//接口调用成功
				if(object.has("result")) {
					
					if(object.getString("result").equals("true")) {
						
						object = (JSONObject)object.getJSONArray("data").get(0);
						
						return Enum_MSG.成功.message(object.getString("Balance"));
					} else {
						
						return Enum_MSG.失败.message(
								object.getJSONObject("data").getString("Code"),
								object.getJSONObject("data").getString("Message"));
					}
					
				} else {//网络异常
					return object.toString();
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
	 * 下分（取款）的接口 username,password,KeyB,remitno,remit
	 * 
	 * remitno=转帐序号(唯一值)，可用贵公司转帐纪录的流水号，以避免重覆转帐，别名transid。必须是19位数
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	@Override
	public Object withdraw(Map<String, Object> entity) {
		try{
			
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() );
//			返回示例
			
			if(MapUtil.isNulls(entity, "username,password,KeyB,remitno,remit")){
				
//				if( String.valueOf(entity.get("username")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户名长度应为20个字符");
//				}
//				if( String.valueOf(entity.get("password")).length() != 8 ) {
//					return Enum_MSG.参数错误.message("用户登录密码长度应为8个字符");
//				}
				if( String.valueOf(entity.get("remitno")).length() != 19 ) {
					return Enum_MSG.参数错误.message("单号长度应为19个字符");
				}
				if( Double.valueOf(String.valueOf(entity.get("remit"))) < 1.00 ) {
					return Enum_MSG.参数错误.message("金额不能小于1元，建议金额为大于0的正整数");
				}
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("website", this.website);
				params.put("username", entity.get("username"));
				params.put("password", entity.get("password"));
				params.put("uppername", this.uppername);
				
				//生成key
				String A = RandomString.createRandomString(3);
				String B = Encrypt.MD5(this.website + entity.get("username").toString() + entity.get("remitno").toString() + entity.get("KeyB").toString() + getCurrenDateMD_yyyyMMdd());
				String C = RandomString.createRandomString(4);
				String key = A  +  B  +  C;
				params.put("key", key);
				
				params.put("remitno", entity.get("remitno"));
				params.put("remit", entity.get("remit"));
				params.put("action", "OUT");
				
				String result = doGetSubmit(API_URL + "/Transfer", params);
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() , result );
				
				JSONObject object =  JSONObject.fromObject( result );
				
				//接口调用成功
				if(object.has("result")) {
					
					if(object.getString("result").equals("true")) {
						
						return Enum_MSG.成功.message(object.getJSONObject("data").getString("Message"));
					} else {
						
						return Enum_MSG.失败.message(
								object.getJSONObject("data").getString("Code"),
								object.getJSONObject("data").getString("Message"));
					}
					
				} else {//网络异常
					return object.toString();
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
	 * 上分（存款）的接口 username,password,KeyB,remitno,remit
	 * 
	 * remitno=转帐序号(唯一值)，可用贵公司转帐纪录的流水号，以避免重覆转帐，别名transid。必须是19位数
	 * 
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	@Override
	public Object deposit(Map<String, Object> entity) {
		try{
			
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString()  );
//			返回示例
			
			if(MapUtil.isNulls(entity, "username,password,KeyB,remitno,remit")){
				
//				if( String.valueOf(entity.get("username")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户名长度应为20个字符");
//				}
//				if( String.valueOf(entity.get("password")).length() != 8 ) {
//					return Enum_MSG.参数错误.message("用户登录密码长度应为8个字符");
//				}
				if( String.valueOf(entity.get("remitno")).length() != 19 ) {
					return Enum_MSG.参数错误.message("单号长度应为19个字符");
				}
				if( Double.valueOf(String.valueOf(entity.get("remit"))) < 1.00 ) {
					return Enum_MSG.参数错误.message("金额不能小于1元，建议金额为大于0的正整数");
				}
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("website", this.website);
				params.put("username", entity.get("username"));
				params.put("password", entity.get("password"));
				params.put("uppername", this.uppername);
				
				//生成key
				String A = RandomString.createRandomString(3);
				String B = Encrypt.MD5(this.website + entity.get("username").toString() + entity.get("remitno").toString() + entity.get("KeyB").toString() + getCurrenDateMD_yyyyMMdd());
				String C = RandomString.createRandomString(4);
				String key = A  +  B  +  C;
				params.put("key", key);
				
				params.put("remitno", entity.get("remitno"));
				params.put("remit", entity.get("remit"));
				params.put("action", "IN");
				
				String result = doGetSubmit(API_URL + "/Transfer", params);
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() , result );
				
				JSONObject object =  JSONObject.fromObject( result );
				
				//接口调用成功
				if(object.has("result")) {
					
					if(object.getString("result").equals("true")) {
						
						return Enum_MSG.成功.message(object.getJSONObject("data").getString("Message"));
					} else {
						
						return Enum_MSG.失败.message(
								object.getJSONObject("data").getString("Code"),
								object.getJSONObject("data").getString("Message"));
					}
					
				} else {//网络异常
					return object.toString();
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
	 * 获取订单接口 username,password,KeyB,transid
	 * 
	 * transid=转帐序号(唯一值)，可用贵公司转帐纪录的流水号，以避免重覆转帐，别名transid。必须是19位数
	 * 
	 * @param entity 参数列表
	 * @return 返回结果 状态(1:成功；-1:处理中或失败 
	 */
	@Override
	public Object getOrder(Map<String, Object> entity) {
		try{
			
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString()  );
//			返回示例
			
			if(MapUtil.isNulls(entity, "username,password,KeyB,transid")){
				
//				if( String.valueOf(entity.get("username")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户名长度应为20个字符");
//				}
//				if( String.valueOf(entity.get("password")).length() != 8 ) {
//					return Enum_MSG.参数错误.message("用户登录密码长度应为8个字符");
//				}
				
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("website", this.website);
				params.put("transid", entity.get("transid"));
				
				//生成key
				String A = RandomString.createRandomString(3);
				String B = Encrypt.MD5(this.website + entity.get("KeyB").toString() + getCurrenDateMD_yyyyMMdd());
				String C = RandomString.createRandomString(4);
				String key = A  +  B  +  C;
				params.put("key", key);
				
				
				String result = doGetSubmit(API_URL + "/CheckTransfer", params);
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() , result );
				
				JSONObject object =  JSONObject.fromObject( result );
				
				//接口调用成功
				if(object.has("result")) {
					
					if(object.getString("result").equals("true") && object.getJSONObject("data").getString("Status").equals("1")) {
						
						return Enum_MSG.成功.message("成功");
					} else {
						
						return Enum_MSG.失败.message(
								object.getJSONObject("data").getString("Code"),
								object.getJSONObject("data").getString("Message"));
					}
					
				} else {//网络异常
					return object.toString();
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
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	@Override
	public Object login(Map<String, Object> entity) {
		
//		视讯游戏
//		GameType 游戏名称
//		3001 百家乐
//		3002 二八杠
//		3003 龙虎斗
//		3005 三公
//		3006 温州牌九
//		3007 轮盘
//		3008 骰宝
//		3010 德州扑克
//		3011 色碟
//		3012 牛牛
//		3014 无限21点
//		3015 番摊
		
		try{
			
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() );
			
//			返回示例
			
			if(MapUtil.isNulls(entity, "username,password,KeyB")){
				
//				if( String.valueOf(entity.get("username")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户名长度应为20个字符");
//				}
//				if( String.valueOf(entity.get("password")).length() != 8 ) {
//					return Enum_MSG.参数错误.message("用户登录密码长度应为8个字符");
//				}
				
				//生成key
				String A = RandomString.createRandomString(9);
				String B = Encrypt.MD5(this.website + entity.get("username").toString() + entity.get("KeyB").toString() + getCurrenDateMD_yyyyMMdd());
				String C = RandomString.createRandomString(6);
				String key = A  +  B  +  C;
				
				
				
				//组装进入游戏的完整URL（经由api项目请求）
				String apiroot = this.GAME_API_URL;
				
				String playtype = "";
				
				
				StringBuilder url = new StringBuilder();
				url.append(apiroot);//API路径
				url.append("/bbingame/index?");
				
				url.append("website=").append(this.website).append("&");
				url.append("username=").append(entity.get("username")).append("&");
				url.append("password=").append(entity.get("password")).append("&");
				url.append("uppername=").append(this.uppername).append("&");
				url.append("key=").append(key).append("&");
				//视讯时，可直达，不经过整合页面
				if(entity.get("playtype") != null) {
					playtype = entity.get("playtype").toString();
					if(playtype.equals("SX")) {
						url.append("page_site=").append("live").append("&");
						url.append("page_present=").append("live").append("&");
					}
				}
				url.append("url=").append(URLEncoder.encode(API_URL_LOGIN + "/Login" ,"UTF-8")).append("&");//对url进行code
				
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() , url.toString() );
				
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
	
	
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
	/**
	 * 获取美国东部时间的当前日期
	 * @return
	 */
	private static String getCurrenDateMD_yyyyMMdd() {
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		ca.add(Calendar.HOUR_OF_DAY, -12);
		return sdf.format(ca.getTime());
	}
	/**
	 * 获取美国东部时间的当前时间
	 * @return
	 */
	private static String getCurrenDateMDyyyyMMddHHmmss() {
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		ca.add(Calendar.HOUR_OF_DAY, -12);
		return sdf2.format(ca.getTime());
	}
	
	
	private static String doGetSubmit(String url, Map<String, Object> params) {  
		
		Iterator ite = params.entrySet().iterator();
		if( !url.endsWith("?")) {
			url += "?";
		}
		while(ite.hasNext()){
			Map.Entry<String, Object> entry = (Entry<String, Object>) ite.next();
			String key = entry.getKey();//map中的key
			String value = entry.getValue().toString();//上面key对应的value
			url += (key+"="+value+"&");
		}
		System.out.println("请求URL："+url);
		
		HttpClient client = new HttpClient();  
        StringBuilder sb = new StringBuilder();  
        InputStream ins = null;  
        // Create a method instance.  
        GetMethod method = new GetMethod(url);  
        // Provide custom retry handler is necessary  
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,  new DefaultHttpMethodRetryHandler(3, false));  
        int timeout = 10000;
        method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,  timeout);          
        method.getParams().setParameter(HttpMethodParams.HEAD_BODY_CHECK_TIMEOUT,  timeout);          
        
        try {  
            // Execute the method.  
            int statusCode = client.executeMethod(method);  
            //System.out.println(statusCode);  
            if (statusCode == HttpStatus.SC_OK) {  
                ins = method.getResponseBodyAsStream();  
                byte[] b = new byte[1024];  
                int r_len = 0;  
                while ((r_len = ins.read(b)) > 0) {  
                    sb.append(new String(b, 0, r_len, method  
                            .getResponseCharSet()));  
                }  
            } else {  
                System.err.println("Response Code: " + statusCode);  
            }  
        } catch (HttpException e) {  
            System.err.println("Fatal protocol violation: " + e.getMessage());  
            return Enum_MSG.接口调用错误.message("Fatal protocol violation: " + e.getMessage()); 
        } catch (IOException e) {  
            System.err.println("Fatal transport error: " + e.getMessage());  
            return Enum_MSG.接口调用错误.message("Fatal transport error: " + e.getMessage()); 
        } finally {  
            method.releaseConnection();  
            if (ins != null) {  
                try {
					ins.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
            }  
        }  
        //System.out.println(sb.toString()); 
//        return Enum_MSG.成功.message(sb.toString()); 
        return sb.toString();
    }  
	
	public static void main(String[] args) {
		BBINGameAPI bbinGameAPI = new BBINGameAPI();
		
		Map<String, Object> entity = new HashMap<String, Object>();
//		entity.put("username", "hym"+RandomString.createRandomString(17));
		entity.put("username", "bs"+RandomString.createRandomString(17));
		entity.put("password", "fafdadsad");
		entity.put("remitno", RandomStringNum.createRandomString(19));
		entity.put("remit", "20");
		
		
		entity.put("transid", "7210623465560357486");
		entity.put("KeyB", "rzCosy3F6t");//createAccount
		System.out.println(bbinGameAPI.createAccount(entity));
		

		
		entity.put("KeyB", "n1TBaber84");//deposit/withdraw
//		System.out.println(bbinGameAPI.deposit(entity));
		
//		entity.put("KeyB", "n1TBaber84");//deposit/withdraw
//		System.out.println(bbinGameAPI.withdraw(entity));;
//		
		entity.put("KeyB", "4xZ5474fQ");//getBalance
//		entity.put("password", "28865105");//
//		entity.put("username", "hyntestnb2");//
//		System.out.println(bbinGameAPI.getBalance(entity));
		
//		entity.put("KeyB", "Pt9E7JI7Bg");//getOrder
//		System.out.println(bbinGameAPI.getOrder(entity));;
		
		entity.put("KeyB", "Oa21bV0");//getOrder
		System.out.println(bbinGameAPI.login(entity));;
	}

	
}

