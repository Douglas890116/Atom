package com.hy.pull.service.game;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hy.pull.common.util.DateUtil;
import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.common.util.game.NHQGame;
import com.hy.pull.common.util.game.tag.TagUtil;
import com.hy.pull.common.util.game.xcp.XCPUtil;
import com.hy.pull.mapper.ApiGgGameinfoMapper;
import com.hy.pull.mapper.DataHandleLogsMapper;
import com.hy.pull.mapper.DataHandleMapper;
import com.hy.pull.mapper.NhqGameinfoMapper;
import com.hy.pull.mapper.TbProxyKeyMapper;
import com.hy.pull.service.BaseService;
import com.hy.pull.service.BaseService.Enum_flag;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 游行天下GG游戏服务类
 * 创建日期 2016-10-13
 * @author temdy
 */
@Service
public class GGGameService extends BaseService {
	
	Logger logger = LogManager.getLogger(GGGameService.class.getName());  
	
	@Autowired
	private TbProxyKeyMapper tbProxyKeyMapper;
	
	@Autowired
	private ApiGgGameinfoMapper apiGgGameinfoMapper;
	
	@Autowired
	private DataHandleMapper dataHandleMapper;
	@Autowired
	private DataHandleLogsMapper dataHandleLogsMapper;
	
	/**
	 * 按条件拉取数据的方法
	 * @param entity 条件集合{GAME_ID:游戏编号,PROXY_ID:代理编号,USER_NAME:帐号,BEGIN_DATE:开始日期,END_DATE:结束日期}
	 * @param username 帐号
	 * @param agentname 代理帐号
	 * @param agentpwd 代理密码
	 * @param deskey DES密钥
	 * @param md5key MD5密钥
	 * @param stardate 开始日期
	 * @param enddate 结束日期
	 * @return 数据行数
	 */
	@Override
	public Integer pullData(Map<String, Object> entity) throws Exception {
		
		/**********************获取上次拉取的最大值***********************/
		Map<String, Object> dataHandle = dataHandleMapper.selectByPrimaryKey(LogUtil.HANDLE_GG);
		dataHandle.put("lastnum", "0");
		String updatetime = dataHandle.get("updatetime").toString();
		
		
		int count = 0;//累计执行总数量
		//System.out.println("新环球游戏数据拉取开始，参数列表："+entity);
		if(entity==null){//判断是否为空，空则创建一个新的对象
			entity = new HashMap<String,Object>();
		}
		if(!entity.containsKey("GAME_ID")){//判断是否存在游戏编号，没则默认初始化
			entity.put("GAME_ID", "16");//设置所属游戏编号
		}
		
		//获取新环球游戏的所有代理密钥列表
		List<Map<String,Object>> list = tbProxyKeyMapper.selectByEntity(entity);
		int size = list.size();		
		if(size > 0){
			String apiUrl = null;//接口URL
			String cagent = null;//代理账号
			String deskey = null;//DES密钥
			String md5key = null;//MD5密钥
			String stardate = null;//开始日期
			String enddate = null;//开始日期
			String code = null;//代理编码
			String BEGIN_DATE = null;
			
			if(entity.get("BEGIN_DATE") != null){//判断是否存在开始日期
				stardate = entity.get("BEGIN_DATE").toString();
				BEGIN_DATE =  entity.get("BEGIN_DATE").toString();
			}
			if(entity.get("END_DATE") != null){//判断是否存在结束日期
				enddate = entity.get("END_DATE").toString();
			}
			
			//间隔分钟数（不得超过10）
			int intervalnum = 10;
			intervalnum = Integer.valueOf(dataHandle.get("intervalnum").toString());
			
			/***********************设定开始结束时间，每次只拉取10分钟内的数据*****************************/
			stardate = DateUtil.parse(DateUtil.add(DateUtil.parse(updatetime, "yyyy-MM-dd HH:mm:ss"), Calendar.MINUTE, -1), "yyyy-MM-dd HH:mm:ss");//后退一分钟
			enddate = DateUtil.parse(DateUtil.add(DateUtil.parse(stardate, "yyyy-MM-dd HH:mm:ss"), Calendar.MINUTE, intervalnum), "yyyy-MM-dd HH:mm:ss");//
			if(DateUtil.parse(enddate, "yyyy-MM-dd HH:mm:ss").getTime() > new Date().getTime()) {
				//最后时间不能超过当前时间
				enddate = DateUtil.parse(new Date(), "yyyy-MM-dd HH:mm:ss");
			}
			
			boolean flag = true;
			
			int len = 0;
			Map<String,Object> map = new HashMap<String,Object>();
			List<Map<String,Object>> data = null;
			for(int i = 0; i < size; i++){
				entity = list.get(i);
				apiUrl = entity.get("PROXY_API_URL").toString();
				cagent = entity.get("PROXY_NAME").toString();
				deskey = entity.get("PROXY_KEY1").toString();
				md5key = entity.get("PROXY_KEY2").toString();
				code = entity.get("PROXY_CODE") == null ? cagent : entity.get("PROXY_CODE").toString();
				map.put("Platformflag", code);
				
				if(stardate == null){//如果为空则获取数据库最大值
					stardate = apiGgGameinfoMapper.max(map);
				}
				
				data = getRecord(apiUrl, md5key, deskey,cagent, stardate,enddate,code);//获取拉取数据列表;
				if(data != null){
					len = data.size();
					count += len;
					if(len > 0){//如果有数据就入库
						apiGgGameinfoMapper.batchInsert(data);//批量入库
						
						dataHandle.put("lastnum", Integer.parseInt(dataHandle.get("lastnum").toString()) + len);
						dataHandle.put("allnum", Integer.parseInt(dataHandle.get("allnum").toString()) + len);
					}
					
					//dataHandleLogsMapper.insert(LogUtil.saveLog(LogUtil.HANDLE_GG, null, len+"个", cagent, Enum_flag.正常.value));
					
				} else {
					//为null表示出现异常
					flag = false;
					dataHandleLogsMapper.insert(LogUtil.saveLog(LogUtil.HANDLE_GG, null, "返回NULL数据", cagent, Enum_flag.异常.value));
					break;
				}
			}
			
			/***************更新配置管理库****************/
			if(flag) {
				dataHandle.put("updatetime", enddate);
				dataHandle.put("lasttime", DateUtil.parse(new Date(), "yyyyMMddHHmmss"));
				dataHandleMapper.update(dataHandle);
			}
			/***************更新配置管理库****************/
		}
		logger.debug("GG游戏数据拉取结束。。。");
		return count;
	}
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
	private static List<Map<String, Object>> getRecord(String API_URL,String MD5_KEY,String DES_KEY,String cagent,String startdate,String enddate,String code) {
//		http://testapi.gg626.com:5050/api/doReport.do
		
		//只能读取3天内的数据，而且每次区间范围为10分钟以内
		StringBuilder param = new StringBuilder();
		param.append("cagent=");
		param.append(cagent);
		param.append("/\\\\/startdate=");
		param.append(startdate);
		param.append("/\\\\/method=br");//tr=5分钟前的数据。br=10分钟前的数据。hbr=3天前的数据
		param.append("/\\\\/enddate=");
		param.append(enddate);

		String result =  getDataByBet(param.toString(),"doReport.do",  API_URL, MD5_KEY, DES_KEY, cagent );
		System.out.println("调用结果："+result);
		if(result == null) {
			return null;
		}
		
		JSONObject jsonObject = JSONObject.fromObject(result);
		if(jsonObject.getString("code").equals("0")) {
			JSONArray list = jsonObject.getJSONArray("recordlist");
			List<Map<String, Object>> listMap = new ArrayList<>();
			
			if(list != null && list.size() > 0) {
				Map<String, Object> entity = null;
				try {
					for (Object object : list) {
						JSONObject data = (JSONObject)object;
						
						String accountno = data.optString("accountno");
						if(accountno.startsWith(cagent)) {//如果账号是代理号打头，则需要去除，否则无法匹配现金网的账号
							accountno = accountno.replaceFirst(cagent, "");
						}
						
						entity = new HashMap<String, Object>();
						entity.put("gameId",data.optString("gameId"));
						entity.put("cuuency",data.optString("cuuency"));
						entity.put("linkId",data.optString("linkId"));
						entity.put("accountno",accountno);
						entity.put("autoid",data.optString("autoid"));
						entity.put("bettime", sdf.parse(data.optString("bettimeStr")) );
						entity.put("betmoney",data.optDouble("bet"));
						entity.put("netmoney",data.optDouble("profit"));
						entity.put("createtime", sdf.format(new Date()));
						entity.put("Platformflag", code);
						
						listMap.add(entity);
					}
				} catch (Exception e) {
					e.printStackTrace();
					LogUtil.addListLog(LogUtil.HANDLE_GG, API_URL, e.getMessage(), cagent, Enum_flag.异常.value);
					return null;
				}
			}
			
			return listMap;
		} else {
			LogUtil.addListLog(LogUtil.HANDLE_GG, API_URL, result, cagent, Enum_flag.异常.value);
		}
		
		return null;
	}
	private static String getDataByBet(String param, String methodName, String API_URL,String MD5_KEY,String DES_KEY,String cagent) {
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
			String odds = new String(readInputStream(in), "UTF-8");

			return odds;

		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_GG, API_URL, e.getMessage(), cagent, Enum_flag.异常.value);
			return null;
		} finally {
			try {
				httpConn.disconnect();
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		}
	}
	/**
     * 从输入流中读取数据
     * @param inStream
     * @return
     * @throws Exception
     */
    private static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len = inStream.read(buffer)) !=-1 ){
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();//网页的二进制数据
        outStream.close();
        inStream.close();
        return data;
    }
}
