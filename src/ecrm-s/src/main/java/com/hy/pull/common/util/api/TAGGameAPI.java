package com.hy.pull.common.util.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
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

import com.hy.pull.common.util.MapUtil;
import com.hy.pull.common.util.game.sa.XmlUtil;
import com.hy.pull.common.util.game.tag.TagUtil;
import com.hy.pull.common.util.game.xcp.XCPUtil;
import com.maven.config.SystemConstant;
import com.maven.util.RandomString;
import com.maven.util.RandomStringNum;
import com.maven.util.HttpPostUtil.UTF8PostMethod;

import net.sf.json.JSONObject;

/**
 * TAG游戏接口
 * @author temdy
 */
public class TAGGameAPI implements BaseInterface {
	
	private String API_URL = "http://gi.22222s.com:81";
	private String API_URL_GCL = "http://gci.22222s.com:81/forwardGame.do";//该域名只用于进入游戏
	private String cagent = "BE9_AGIN";
	private String MD5_KEY = "OpKJeWAh6Y7u";
	private String DES_KEY = "rKvFDCXi";
	private String oddtype = "A";//限红

	private String GAME_API_URL = "http://api.hyzonghe.net";//游戏使用的域名。代替api.hyzonghe.net
	
	
	
	/**
	 * 默认构造函数
	 * 
	 * 测试时可以使用，不用传递秘钥进来
	 */
	@Deprecated
	public TAGGameAPI(){
		
	}
	
	/**
	 * 构建函数，初始化参数
	 * @param API_URL API接口URL
	 * @param MD5_KEY MD5密钥
	 * @param DES_KEY DES密钥
	 */
	
	public TAGGameAPI(String API_URL,String MD5_KEY,String DES_KEY,String cagent,String API_URL_GCL, String oddtype,String GAME_API_URL){
		this.API_URL = API_URL;
		this.MD5_KEY = MD5_KEY;
		this.DES_KEY = DES_KEY;
		
		this.cagent = cagent;
		this.API_URL_GCL = API_URL_GCL;
		this.oddtype = oddtype;
		this.GAME_API_URL = GAME_API_URL;
	}

	/**
	 * 创建用户接口
	 * @param entity 参数列表 loginname,actype,password,oddtype
	 * 
	 * loginname 游戏账号的登錄名
	 * actype 账号类型（0、试玩，1、真钱）
	 * password 密码
	 * oddtype 盘口, 设定新玩家可下注的范围（默认值： A）
	 * @return 返回结果
	 */
	@Override
	public Object createAccount(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString()  );
//		<result    info=""    msg=""/>
		
		if(MapUtil.isNulls(entity, "loginname,actype,password,oddtype")){	
			
			if( String.valueOf(entity.get("loginname")).length() > 20 ) {
				return Enum_MSG.参数错误.message("用户长度不能大于20位");
			}
			if( String.valueOf(entity.get("password")).length() > 20 ) {
				return Enum_MSG.参数错误.message("密码长度不能大于20位");
			}
			
			if(oddtype == null || oddtype.equals("")) {
				oddtype = "A";
			}
			
			StringBuilder param = new StringBuilder();
			param.append("cagent=");
			param.append(this.cagent);
			param.append("/\\\\/loginname=");
			param.append(entity.get("loginname"));
			param.append("/\\\\/method=lg");
			param.append("/\\\\/actype=");
			param.append(entity.get("actype"));
			param.append("/\\\\/password=");
			param.append(entity.get("password"));
			param.append("/\\\\/oddtype=");
			param.append(oddtype);
			param.append("/\\\\/cur=CNY");
			String result =  getData(param.toString());
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() , result );
			
			Map<String, String> data = XmlUtil.getQueryParamsByTAG(result);
			
			//	接口调用成功
			if(data.get("info").equals("0")) {//创建用户接口成功为0的标记
				return Enum_MSG.成功.message("已创建或已存在");
			} else {
				
				if(data.get("msg").contains("error:60002")) {
					return Enum_MSG.账号已存在.message(data.get("msg"));
				}
				return Enum_MSG.失败.message(data.get("info"), data.get("msg"));
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
		if(MapUtil.isNulls(entity, "loginname,actype,password")){
			StringBuilder param = new StringBuilder();
			param.append("cagent=");
			param.append(this.cagent);
			param.append("/\\\\/loginname=");
			param.append(entity.get("loginname"));
			param.append("/\\\\/method=gb");
			param.append("/\\\\/actype=");
			param.append(entity.get("actype"));
			param.append("/\\\\/password=");
			param.append(entity.get("password"));
			param.append("/\\\\/cur=CNY");

			String result =  getData(param.toString());
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() , result );
			Map<String, String> data = XmlUtil.getQueryParamsByTAG(result);
			
			//	接口调用成功
			if(data.get("msg").equals("")) {//调用成功
				return Enum_MSG.成功.message(data.get("info"));
			} else {
				return Enum_MSG.失败.message(data.get("info"), data.get("msg"));
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
	 * actype 账号类型（0、试玩，1、真钱）
	 * password 密码
	 * flag 标志（预备转帐的结果0、失败，1、成功）
	 * billno  (cagent+序列), 序列是唯一的13~16位数
	 * credit 转款额度(如 000.00), 只保留小数点后两个位,即:100.00
	 * fixcredit 可为空，不可用额度(只有  AGTEX  平台 才需 调用此参数)
	 * gameCategory 可为空，(只有  AGTEX  平台 才需 调用此参数)1、代表对战，0 、代表对赌
	 * @return 返回结果
	 */
	public Object withdraw(Map<String, Object> entity) {
		entity.put("method", "tc");
		
		JSONObject result = JSONObject.fromObject(doWithdraw(entity).toString());
		
		//预备转账操作成功
		if(result.getString("code").equals("0")) {
			entity.put("flag", "1");
		} else {
			entity.put("flag", "0");
		}
		
		entity.put("method", "tcc");
		return doWithdraw(entity);
	}
	
	/**
	 * 下分（取款）的接口
	 * @param entity 参数列表 loginname,actype,password,billno,credit
	 * 
	 * loginname 游戏账号的登錄名
	 * actype 账号类型（0、试玩，1、真钱）
	 * password 密码
	 * flag 标志（预备转帐的结果0、失败，1、成功）
	 * billno  (cagent+序列), 序列是唯一的13~16位数
	 * credit 转款额度(如 000.00), 只保留小数点后两个位,即:100.00
	 * fixcredit 可为空，不可用额度(只有  AGTEX  平台 才需 调用此参数)
	 * gameCategory 可为空，(只有  AGTEX  平台 才需 调用此参数)1、代表对战，0 、代表对赌
	 * @return 返回结果
	 */
	private Object doWithdraw(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "doWithdraw", entity.toString()  );
		if(MapUtil.isNulls(entity, "loginname,actype,password,billno,credit")){
			
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
			if(entity.get("flag") != null){//转帐确认的时候才有的参数
				param.append("/\\\\/method=tcc");//转帐确认
			}else{
				param.append("/\\\\/method=tc");//预备转帖
			}
			param.append("/\\\\/loginname=");
			param.append(entity.get("loginname"));
			/*
			  (cagent+序列), 序列是唯一的13~16位数, 例如:页 19 / 42 Copyright©AsiaGaming
			 cagent = ‘XXXXX’ 及 序列 = 1234567890987, 那么billno = XXXXX1234567890987,
			 BBIN  平台的  billno ， 只 可使用 數字,  请使用 9 19  字符 内, ,例如 :123456445676789098
			 MG  平台的  billno ， 只 可使用 數字,  请使用 8 8  字符 内,例如 :123456 78
			 */
			param.append("/\\\\/billno=");
			param.append(this.cagent + entity.get("billno").toString());//本方法组装单号
			param.append("/\\\\/type=OUT");//OUT: 從遊戲账號转款到網站賬號
			param.append("/\\\\/credit=");
			param.append(entity.get("credit"));
			param.append("/\\\\/actype=");
			param.append(entity.get("actype"));
			param.append("/\\\\/password=");
			param.append(entity.get("password"));
			if(entity.get("flag") != null){
				/*
				 * 值 = 1 代表调用‘预备转账 PrepareTransferCredit’ API 成功
				 * 值 = 0 代表调用‘預備轉賬 PrepareTransferCredit’出错或出现错误码
				 */
				param.append("/\\\\/flag=");
				param.append(entity.get("flag"));				
			}
			if(entity.get("fixcredit") != null){
				/*
				 * 不可用额度
				 * 只有  AGTEX  平台 才需 调用此参数， 其他游戏则不需要理会 (AGTEX  平台 是)
				 */
				param.append("/\\\\/fixcredit=");
				param.append(entity.get("fixcredit"));				
			}
			if(entity.get("gameCategory") != null){
				/*
				 * 值 = 1 代表对战
				 * 值 = 0 代表对赌
				 * 值为空时 默认值为 0
				 * 只有  AGTEX  平台才需调用此参数， 其他游戏则不需要理会 (AGTEX  平台是棋牌平台) 
				 */
				param.append("/\\\\/gameCategory=");
				param.append(entity.get("gameCategory"));				
			}
			param.append("/\\\\/cur=CNY");
			
			String result =  getData(param.toString());
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "doWithdraw", entity.toString() , result );
			
			Map<String, String> data = XmlUtil.getQueryParamsByTAG(result);
			
			//	接口调用成功
			if(data.get("msg").equals("")) {//调用成功
				return Enum_MSG.成功.message("取款成功");
			} else {
				return Enum_MSG.失败.message(data.get("info"), data.get("msg"));
			}
			
			
		} else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	/**
	 * 上分（存款）的接口 loginname,actype,password,billno,credit
	 * 
	 * loginname 游戏账号的登錄名
	 * actype 账号类型（0、试玩，1、真钱）
	 * password 密码
	 * flag 标志（预备转帐的结果0、失败，1、成功）
	 * billno  (cagent+序列), 序列是唯一的13~16位数，本接口要求传递16位数字
	 * credit 转款额度(如 000.00), 只保留小数点后两个位,即:100.00
	 * fixcredit 可为空，不可用额度(只有  AGTEX  平台 才需 调用此参数)
	 * gameCategory 可为空，(只有  AGTEX  平台 才需 调用此参数)1、代表对战，0 、代表对赌
	 * @return 返回结果
	 */
	private Object doDeposit(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "doDeposit", entity.toString()  );
		if(MapUtil.isNulls(entity, "loginname,actype,password,billno,credit")){
			
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
			if(entity.get("flag") != null){//转帐确认的时候才有的参数
				param.append("/\\\\/method=tcc");//转帐确认
			}else{
				param.append("/\\\\/method=tc");//预备转帖
			}
			param.append("/\\\\/loginname=");
			param.append(entity.get("loginname"));
			/*
			  (cagent+序列), 序列是唯一的13~16位数, 例如:页 19 / 42 Copyright©AsiaGaming
			 cagent = ‘XXXXX’ 及 序列 = 1234567890987, 那么billno = XXXXX1234567890987,
			 BBIN  平台的  billno ， 只 可使用 數字,  请使用 9 19  字符 内, ,例如 :123456445676789098
			 MG  平台的  billno ， 只 可使用 數字,  请使用 8 8  字符 内,例如 :123456 78
			 */
			param.append("/\\\\/billno=");
			param.append(this.cagent + entity.get("billno").toString());//本方法组装单号
			param.append("/\\\\/type=IN");//IN: 从网站账号转款到游戏账号;
			param.append("/\\\\/credit=");
			param.append(entity.get("credit"));
			param.append("/\\\\/actype=");
			param.append(entity.get("actype"));
			param.append("/\\\\/password=");
			param.append(entity.get("password"));
			if(entity.get("flag") != null){
				/*
				 * 值 = 1 代表调用‘预备转账 PrepareTransferCredit’ API 成功
				 * 值 = 0 代表调用‘預備轉賬 PrepareTransferCredit’出错或出现错误码
				 */
				param.append("/\\\\/flag=");
				param.append(entity.get("flag"));				
			}
			if(entity.get("fixcredit") != null){
				/*
				 * 不可用额度
				 * 只有  AGTEX  平台 才需 调用此参数， 其他游戏则不需要理会 (AGTEX  平台 是)
				 */
				param.append("/\\\\/fixcredit=");
				param.append(entity.get("fixcredit"));				
			}
			if(entity.get("gameCategory") != null){
				/*
				 * 值 = 1 代表对战
				 * 值 = 0 代表对赌
				 * 值为空时 默认值为 0
				 * 只有  AGTEX  平台才需调用此参数， 其他游戏则不需要理会 (AGTEX  平台是棋牌平台) 
				 */
				param.append("/\\\\/gameCategory=");
				param.append(entity.get("gameCategory"));				
			}
			param.append("/\\\\/cur=CNY");
			String result =  getData(param.toString());
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "doDeposit", entity.toString() , result );
			
			Map<String, String> data = XmlUtil.getQueryParamsByTAG(result);
			
			//	接口调用成功
			if(data.get("msg").equals("")) {//调用成功
				return Enum_MSG.成功.message("存款成功");
			} else {
				return Enum_MSG.失败.message(data.get("info"), data.get("msg"));
			}
			
		} else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}
	
	/**
	 * 上分（存款）的接口 loginname,actype,password,billno,credit
	 * 
	 * loginname 游戏账号的登錄名
	 * actype 账号类型（0、试玩，1、真钱）
	 * password 密码
	 * flag 标志（预备转帐的结果0、失败，1、成功）
	 * billno  (cagent+序列), 序列是唯一的13~16位数，本接口要求传递16位数字
	 * credit 转款额度(如 000.00), 只保留小数点后两个位,即:100.00
	 * fixcredit 可为空，不可用额度(只有  AGTEX  平台 才需 调用此参数)
	 * gameCategory 可为空，(只有  AGTEX  平台 才需 调用此参数)1、代表对战，0 、代表对赌
	 * @return 返回结果
	 */
	@Override
	public Object deposit(Map<String, Object> entity) {
		entity.put("method", "tc");
		
		JSONObject result = JSONObject.fromObject(doDeposit(entity).toString());
		
		//预备转账操作成功
		if(result.getString("code").equals("0")) {
			entity.put("flag", "1");
		} else {
			entity.put("flag", "0");
		}
		
		entity.put("method", "tcc");
		return doDeposit(entity);
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
	 * 获取订单接口
	 * @param entity 参数列表 billno,actype
	 * billno  (cagent+序列), 序列是唯一的13~16位数
	 * actype 账号类型（0、试玩，1、真钱）
	 * @return 返回结果
	 */
	@Override
	public Object getOrder(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString()  );
		if(MapUtil.isNulls(entity, "billno,actype")){
			
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
			param.append(this.cagent + ""+ entity.get("billno").toString());
			param.append("/\\\\/method=qos");
			param.append("/\\\\/actype=");
			param.append(entity.get("actype"));
			param.append("/\\\\/cur=CNY");

			String result =  getData(param.toString());
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() , result );
			
			Map<String, String> data = XmlUtil.getQueryParamsByTAG(result);
			
			//	接口调用成功
			if(data.get("msg").equals("0")) {//调用成功
				return Enum_MSG.成功.message("订单处理成功");
			} else {
				return Enum_MSG.失败.message(data.get("info"), data.get("msg"));
			}
			
			
		}  else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	/**
	 * 登录的接口
	 * @param entity 参数列表 loginname,actype,password,oddtype,dm,sid,gameType
	 * loginname 游戏账号的登錄名
	 * actype 账号类型（0、试玩，1、真钱）
	 * password 密码
	 * dm 密码 代表返回的网站域名  您的网站域名是www.bet.com, dm = www.bet.com dm也可等如你的域名ip (dm=123.22.112.1) ，这里默认给api域名
	 * sid  (cagent+序列), 序列是唯一的13~16位数
	 * gameType 游戏类型 (AGIN 平台, 为空将导入整合页面)
	 * oddtype 盘口, 设定新玩家可下注的范围（默认值： A）
	 * session_token 生成方式：当用戶登陆网站，网站保存Session Token在内存
	 * 
	 * gameType=真人大厅类型、子游戏类型，请参见对照表，AG捕鱼请传递6。同时包括PNG平台
	 * 
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object login(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() );
		if(MapUtil.isNulls(entity, "loginname,actype,password,oddtype,deviceType")){		
			
			String API_ROOT = this.GAME_API_URL;
			String deviceType = String.valueOf(entity.get("deviceType"));
			if( !deviceType.equals("0") && !deviceType.equals("1")) {
				return Enum_MSG.参数错误.message("AV游戏目前只能接受PC端与手机H5端的请求，即设备类型为0和1");
			}
			
			if(oddtype == null || oddtype.equals("")) {
				oddtype = "A";
			}
			
//			游戏类型  (AGIN平台, 为空将导入整合页面) 
//			值      游戏类型 
//			0       大厅 
//			1       旗舰厅  (AGQ厅) 
//			2       国际厅  (AGIN厅) 
//			3       多台  (自选多台) 
//			4       包桌  (VIP包桌) 
//			5       竞咪  (竞咪厅) 
//			6       捕鱼王 
//			7       活动厅 
//			8       电子游戏  (XIN电子游戏大厅) 
//			10     彩票游戏 
//			11     HTML5大厅  (AGIN移动网页版游戏平台大厅) 
//			12     旗舰厅  HTML5百家乐 
//			500   XIN电子游戏独立版大厅
			
			StringBuilder param = new StringBuilder();
			param.append("cagent=");
			param.append(this.cagent);
			param.append("/\\\\/loginname=");
			param.append(entity.get("loginname"));
			param.append("/\\\\/actype=");
			param.append(entity.get("actype"));
			param.append("/\\\\/password=");
			param.append(entity.get("password"));
			param.append("/\\\\/dm=");
			param.append(API_ROOT);
			param.append("/\\\\/sid=");
			param.append(this.cagent + getPatchno());
			param.append("/\\\\/lang=1");
			param.append("/\\\\/oddtype=");
			param.append(oddtype);
			param.append("/\\\\/cur=CNY");
			
			//真人大厅类型、子游戏类型，请参见对照表，AG捕鱼请传递6
			if(entity.get("gameType") != null && !entity.get("gameType").toString().equals("null")) {
				param.append("/\\\\/gameType=");
				param.append(entity.get("gameType"));
			} else {
				param.append("/\\\\/gameType=");
				param.append("");
			}
			
			//如果是手机H5时
			if( deviceType.equals("1") ) {
				param.append("/\\\\/mh5=y");
			}
			
			if(entity.get("mh5") != null){
				/*
				 * mh5=y 代表 AGIN 移动网页版
				 */
				param.append("/\\\\/mh5=");
				param.append(entity.get("mh5"));
			}
			if(entity.get("flashid") != null){
				/*
				 * HB 平台(o Habanero  平台)
				 */
				param.append("/\\\\/flashid=");
				param.append(entity.get("flashid"));				
			}
			if(entity.get("session_token") != null){
				/*
				 * 生成方式：当用戶登陆网站，网站保存Session Token在内存，用于驗證用户合法性，接入Iframe及手机Html5时必须带入session_token
				 */
				param.append("/\\\\/session_token=");
				param.append(entity.get("session_token"));
			}
			
			//自动为其生成32位数的token
			param.append("/\\\\/session_token=");
			param.append(RandomString.createRandomString(32));
			System.out.println(param);
			StringBuilder url = new StringBuilder();
			
			url.append(API_ROOT);//API路径
			
			if( deviceType.equals("0")) {//PC
				url.append("/taggame/index");
			} else {//H5
				url.append("/taggame/indexh5");
			}
			
			TagUtil tag = new TagUtil(DES_KEY);
			String params = "";
			try {
				params = tag.encrypt(param.toString());
				String key = XCPUtil.MD5(params+MD5_KEY);
				
				url.append("?params=").append(params).append("&");//
				url.append("key=").append(key).append("&");//
				url.append("url=").append( URLEncoder.encode(this.API_URL_GCL,"UTF-8") ).append("&");//
				
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() , url.toString() );
			
			return Enum_MSG.成功.message(url.toString());//返回跳转的地址
			
		} else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	/**
	 * 
	 * 获取16位数单号
	 * 
	 * 获取毫秒级+三位流水号=16位数
	 * @return
	 */
	private static int POKE = 100;
	public static String getPatchno(){
		POKE++;
		if(POKE>=999) POKE = 100;
		return (new Date().getTime()*1000 + POKE) + "";
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
	 * TAG游戏强制要求超市时间必须在30秒以上
	 * @param param
	 * @param methodName
	 * @return
	 */
	private String getData(String param, String methodName) {  
		
		TagUtil tag = new TagUtil(DES_KEY);
		String params = "";
		try {
			params = tag.encrypt(param);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String key = XCPUtil.MD5(params+MD5_KEY);
		String url = API_URL.concat(methodName).concat("?params=").concat(params).concat("&key=").concat(key);
		System.out.println("参数原文："+param);
		System.out.println("参数密文："+key);
		System.out.println("请求URL："+url);
		
		HttpClient client = new HttpClient();  
		client.getHttpConnectionManager().getParams().setConnectionTimeout(31 * 1000); //30秒以上
        StringBuilder sb = new StringBuilder();  
        InputStream ins = null;  
        // Create a method instance.  
        GetMethod method = new GetMethod(url);  
        // Provide custom retry handler is necessary  
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,  new DefaultHttpMethodRetryHandler(3, false));  
                
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
                return getXMLMsg("-1001", "Response Code: " + statusCode);
            }  
        } catch (HttpException e) {  
            System.err.println("Fatal protocol violation: " + e.getMessage());  
            return getXMLMsg("-1001", "Fatal protocol violation: " + e.getMessage());
        } catch (IOException e) {  
            System.err.println("Fatal transport error: " + e.getMessage());  
            return getXMLMsg("-1001", "Fatal transport error: " + e.getMessage());
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
        return sb.toString();
    }  
	
	private static String getXMLMsg(String code,String info) {
		StringBuffer msg = new StringBuffer();
		msg.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		msg.append("<result ");
		msg.append(" info='").append(code).append("'");
		msg.append(" msg='").append(info).append("' />");
		return msg.toString();
	}
	
	/**
	 * 获取接口的数据
	 * @param url 接口URL
	 * @return 响应数据
	 */
	public String getData(String param) {
		return getData(param,"/doBusiness.do");
	}
	
	public static void main(String[] a){
		TAGGameAPI tag = new TAGGameAPI();
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("loginname", RandomString.createRandomString(20));//#&*
		entity.put("password", "test0071");
		
//		entity.put("loginname", "hypmcq001");//#&*
//		entity.put("password", "yat12345678");
		
		entity.put("actype", "1");
		
//		entity.put("billno", "HNIf0vULhnTIj9xx");
		entity.put("oddtype", "A");
		entity.put("credit", "10");
//		entity.put("credit", "100.00");
//		entity.put("flag", "1");
		entity.put("deviceType", "0");
		entity.put("gameType", "0");//捕鱼

		System.out.println(tag.createAccount(entity));
		entity.put("credit", "1.00");
		entity.put("billno", RandomStringNum.createRandomString(16));
		System.out.println(tag.deposit(entity));
		
//		entity.put("billno", RandomStringNum.createRandomString(16));
//		entity.put("credit", "2.00");
//		System.out.println(tag.withdraw(entity));
//		System.out.println(tag.getBalance(entity));
//		System.out.println(tag.getOrder(entity));
//		System.out.println(tag.login(entity));
	}
}

