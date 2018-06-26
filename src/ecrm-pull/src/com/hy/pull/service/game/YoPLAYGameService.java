package com.hy.pull.service.game;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hy.pull.common.util.DateUtil;
import com.hy.pull.common.util.FileHelper;
import com.hy.pull.common.util.FtpUtils;
import com.hy.pull.common.util.FtpUtilsNew;
import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.common.util.YoPLAYUtils;
import com.hy.pull.common.util.game.TAGGame;
import com.hy.pull.mapper.ApiTagXmlRecordMapper;
import com.hy.pull.mapper.ApiTagXmlTimerMapper;
import com.hy.pull.mapper.ApiYoplayGameinfoMapper;
import com.hy.pull.mapper.DataHandleLogsMapper;
import com.hy.pull.mapper.DataHandleMapper;
import com.hy.pull.mapper.TagGameinfoMapper;
import com.hy.pull.mapper.TbGameKindMapper;
import com.hy.pull.mapper.TbMaxLogMapper;
import com.hy.pull.mapper.TbProxyKeyMapper;
import com.hy.pull.service.BaseService;
import com.hy.pull.service.BaseService.Enum_flag;

/**
 * TAG游戏服务类
 * 创建日期 2016-10-13
 * @author temdy
 */
@Service
public class YoPLAYGameService extends BaseService {

	
	Logger logger = LogManager.getLogger(YoPLAYGameService.class.getName());  
	
	@Autowired
	private TbProxyKeyMapper tbProxyKeyMapper;
	
	@Autowired
	private TbGameKindMapper tbGameKindMapper;
	
	@Autowired
	private TbMaxLogMapper tbMaxLogMapper;
	@Autowired
	private ApiTagXmlRecordMapper apiTagXmlRecordMapper;
	@Autowired
	private ApiTagXmlTimerMapper apiTagXmlTimerMapper;
	@Autowired
	private ApiYoplayGameinfoMapper apiYoplayGameinfoMapper;
	@Autowired
	private DataHandleMapper dataHandleMapper;
	@Autowired
	private DataHandleLogsMapper dataHandleLogsMapper;
	
	//本地XML存储的目录
	String localPath = "d:/yoplay_xml/";
	
	
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");// 设置日期格式
	/**ua
	 * 按条件拉取数据的方法
	 * @param entity 条件集合 {GAME_ID:游戏编号,PROXY_ID:代理编号,MAX_VALUE:上次拉取的最大值,GAME_KIND_ID:游戏种类编号}
	 * @return 数据行数
	 */
	@Override
	public Integer pullData(Map<String, Object> entity) throws Exception {
		
		Calendar calendar = Calendar.getInstance();
		
		/**********************获取上次拉取的最大值***********************/
		Map<String, Object> dataHandle = dataHandleMapper.selectByPrimaryKey(LogUtil.HANDLE_YoPLAY);
		dataHandle.put("lastnum", "0");
		
		
		
		int count = 0;//累计执行总数量
		//System.out.println("TAG游戏数据拉取开始，参数列表："+entity);
		if(entity==null){//判断是否为空，空则创建一个新的对象
			entity = new HashMap<String,Object>();
		}
		if(!entity.containsKey("GAME_ID")){//判断是否存在游戏编号，没则默认初始化为TAG游戏
			entity.put("GAME_ID", "28");//设置所属游戏编号
		}//获取TAG游戏的所有代理密钥列表
		List<Map<String,Object>> list = tbProxyKeyMapper.selectByEntity(entity);
		int size = list.size();
		if(size > 0){
			String ip = null;//ftp服务器IP
			String agent = null;//代理名称
			String max = null;//上次拉取的最大值
			String code = null;//代理编码
			if(entity.get("MAX_VALUE") != null){//判断是否存在最大值
				max = entity.get("MAX_VALUE").toString();
			}
			
			
			String userName = null;//用户名
			String password = null;//密码
			String dir = "YOPLAY";//数据文件目录
			String GAME_KIND_ID = null;//游戏种类编号
			if(entity.get("GAME_KIND_ID") != null){//判断是否存在游戏种类
				GAME_KIND_ID = entity.get("GAME_KIND_ID").toString();
			}
			int port = 21;//端口号
			int len = 0;
			int klen = 0;
			int mlen = 0;
			Map<String,Object> map = new HashMap<String,Object>();
			Map<String,Object> kindMap = null;
			List<Map<String,Object>> data = null;
			List<Map<String,Object>> kinds = null;
			List<Map<String,Object>> maxs = null;
			
			boolean flag = true;
			
			Map<String, Object> mapentity = new HashMap<String, Object>();
			List<Map<String, Object>> listtimer = new ArrayList<Map<String, Object>>();
			
			for(int i = 0; i < size; i++){
				entity = list.get(i);
				password = entity.get("PROXY_API_URL").toString();
				ip = entity.get("PROXY_KEY2").toString();
				agent = entity.get("PROXY_NAME").toString();
				code = entity.get("PROXY_CODE") == null ? agent : entity.get("PROXY_CODE").toString();
				userName = entity.get("PROXY_MD5_KEY").toString();
				if(entity.get("PROXY_KEY1")!=null){
					port = Integer.parseInt(entity.get("PROXY_KEY1").toString());
				}
				
				
				/***********************获取正常的数据*****************************/
				max = getMax(agent, dir, "normal");
				max = back20Min(max);//最大值每次都倒退20分钟
				if(max == null) {
					Calendar sysCal = Calendar.getInstance();
					sysCal.add(Calendar.HOUR_OF_DAY, -12);
					max = DateUtil.parse(sysCal.getTime(), "yyyyMMddHHmm");
				}
				
				
				// YOPLAY
		        // 获取正常的数据（List只是纯数字名字）
		        String ftpPath = "/"+dir+"/"+max.substring(0,8)+"/";
		        List<String>  result = FtpUtilsNew.downloadFtpFile(ip, userName, password, port, ftpPath, localPath, max, agent);  
		        if(result != null && result.size() > 0) {
		        	len += result.size();
		        	System.out.println("采集到"+result.size()+"个文件");
		        	apiTagXmlRecordMapper.batchInsert(getListData(result, agent, dir, "normal", localPath  +agent+ "" + ftpPath));
		        	
		        	//更新本次的最大值
		        	max = result.get(result.size() - 1) ;
		        } 
		        //更新计时器
		        max = getNextMaxNew(max);
		        updateMax(agent, dir, "normal", max);
		        
		        
		        
		        
		        /***********************获取补单的数据*****************************/
				max = getMax(agent, dir, "lostAndfound");
				max = back20Min(max);//最大值每次都倒退20分钟
				if(max == null) {
					Calendar sysCal = Calendar.getInstance();
					sysCal.add(Calendar.HOUR_OF_DAY, -12);
					max = DateUtil.parse(sysCal.getTime(), "yyyyMMddHHmm");
				}
				
										
		        // 获取补单的数据（List只是纯数字名字）
		        ftpPath = "/"+dir+"/"+max.substring(0,8)+"/lostAndfound/";
		        List<String>  result2 = FtpUtilsNew.downloadFtpFile(ip, userName, password, port, ftpPath, localPath, max, agent);  
		        if(result2 != null && result2.size() > 0) {
		        	len += result2.size();
		        	System.out.println("采集到"+result2.size()+"补单个文件");
		        	apiTagXmlRecordMapper.batchInsert(getListData(result2, agent, dir, "lostAndfound", localPath +agent + "" + ftpPath));
		        }
				
		        //更新计时器
	        	max = getNextMaxNew(max);
	        	updateMax(agent, dir, "lostAndfound", max);
	        	
				
				
			}
			
			
			/***************更新配置管理库****************/
			if(flag) {
				dataHandle.put("lastnum",  len);
				dataHandle.put("allnum", Integer.parseInt(dataHandle.get("allnum").toString()) + len);
				dataHandle.put("updatetime", max);//随便记录一个最新的值
				dataHandle.put("lasttime", DateUtil.parse(new Date(), "yyyyMMddHHmmss"));
				dataHandleMapper.update(dataHandle);
			}
			/***************更新配置管理库****************/
			
			
		}
		return count;
	}
	
	/**
	 * 补单数据
	 * 
	 * @return 数据行数
	 */
	public Integer pullDataPatch(Map<String, Object> entity) throws Exception {
		
		
		int count = 0;//累计执行总数量
		//System.out.println("TAG游戏数据拉取开始，参数列表："+entity);
		if(entity==null){//判断是否为空，空则创建一个新的对象
			entity = new HashMap<String,Object>();
		}
		if(!entity.containsKey("GAME_ID")){//判断是否存在游戏编号，没则默认初始化为TAG游戏
			entity.put("GAME_ID", "28");//设置所属游戏编号
		}//获取TAG游戏的所有代理密钥列表
		List<Map<String,Object>> list = tbProxyKeyMapper.selectByEntity(entity);
		int size = list.size();
		if(size > 0){
			String ip = null;//ftp服务器IP
			String agent = null;//代理名称
			String max = null;//上次拉取的最大值
			String code = null;//代理编码
			if(entity.get("MAX_VALUE") != null){//判断是否存在最大值
				max = entity.get("MAX_VALUE").toString();
			}
			
			
			String userName = null;//用户名
			String password = null;//密码
			String dir = "YOPLAY";//数据文件目录
			String GAME_KIND_ID = null;//游戏种类编号
			if(entity.get("GAME_KIND_ID") != null){//判断是否存在游戏种类
				GAME_KIND_ID = entity.get("GAME_KIND_ID").toString();
			}
			int port = 21;//端口号
			int len = 0;
			int klen = 0;
			int mlen = 0;
			Map<String,Object> map = new HashMap<String,Object>();
			Map<String,Object> kindMap = null;
			List<Map<String,Object>> data = null;
			List<Map<String,Object>> kinds = null;
			List<Map<String,Object>> maxs = null;
			
			boolean flag = true;
			
			Map<String, Object> mapentity = new HashMap<String, Object>();
			List<Map<String, Object>> listtimer = new ArrayList<Map<String, Object>>();
			
			for(int i = 0; i < size; i++){
				entity = list.get(i);
				password = entity.get("PROXY_API_URL").toString();
				ip = entity.get("PROXY_KEY2").toString();
				agent = entity.get("PROXY_NAME").toString();
				code = entity.get("PROXY_CODE") == null ? agent : entity.get("PROXY_CODE").toString();
				userName = entity.get("PROXY_MD5_KEY").toString();
				if(entity.get("PROXY_KEY1")!=null){
					port = Integer.parseInt(entity.get("PROXY_KEY1").toString());
				}
				
				
				/**********每次拉一天的（美东时间昨日）**************/
				//当前时间转换为美东时间
				Date currendate = null;
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(DateUtil.parse(DateUtil.parse(DateUtil.add(new Date(), Calendar.DATE, -1), "yyyyMMdd")+"0000", "yyyyMMddHHmm"));
				calendar.add(Calendar.HOUR_OF_DAY, -12);
				currendate = calendar.getTime();
				String lastDate = DateUtil.parse(currendate, "yyyyMMdd");
				max = lastDate+"1200";
				
				
				
				// YOPLAY
		        // 获取正常的数据（List只是纯数字名字）
		        String ftpPath = "/"+dir+"/"+max.substring(0,8)+"/";
		        List<String>  result = FtpUtilsNew.downloadFtpFile(ip, userName, password, port, ftpPath, localPath, max, agent);  
		        if(result != null && result.size() > 0) {
		        	System.out.println("采集到"+result.size()+"个文件");
		        	apiTagXmlRecordMapper.batchInsert(getListData(result, agent, dir, "normal", localPath  +agent+ "" + ftpPath));
		        	
		        } 
		        
		        
		        /***********************获取补单的数据*****************************/
		        
		        // 获取补单的数据（List只是纯数字名字）
		        ftpPath = "/"+dir+"/"+max.substring(0,8)+"/lostAndfound/";
		        List<String>  result2 = FtpUtilsNew.downloadFtpFile(ip, userName, password, port, ftpPath, localPath, max, agent);  
		        if(result2 != null && result2.size() > 0) {
		        	System.out.println("采集到"+result2.size()+"补单个文件");
		        	apiTagXmlRecordMapper.batchInsert(getListData(result2, agent, dir, "lostAndfound", localPath +agent + "" + ftpPath));
		        }
				
				
			}
			
			
			/***************更新配置管理库****************/
			/***************更新配置管理库****************/
			
			
		}
		return count;
	}
	
	public int doInsertData() {
		
		Map<String, Object> mapentity = new HashMap<String, Object>();
		mapentity.put("status", 0);//查询待处理的
		mapentity.put("platformtype", "YOPLAY");//YOPLAY
		List<Map<String, Object>> list = apiTagXmlRecordMapper.selectByEntity(mapentity);
		
		int count = 0;
		if(list != null && list.size() > 0) {
			int size1 = list.size();
			
			String filePath = null;
			String str = null;
			String agent = null;
			Map<String, Object> data = null;
			
			for (int i = 0; i < size1; i++) {
					
				data = list.get(i);
				
				agent = data.get("agentcode").toString();
				filePath = data.get("filepath").toString();
				
				str = FileHelper.readFile(filePath);
				if(str == null){
					data.put("status", 1);
					apiTagXmlRecordMapper.insert(data);//更新为已处理
					continue;
				}
				count += getData(str, data);
					
			}
		}
		
		return count;
	}
	
	private int getData(String xmlstr, Map<String, Object> data) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String agent = data.get("agentcode").toString();
		try {
			List<Map<String, Object>> listInsert = new ArrayList<Map<String, Object>>();
			
			String xmlf = "<?xml version='1.0' encoding='UTF-8'?><result>" +  xmlstr + "</result>";
			Document doc = DocumentHelper.parseText(xmlf); // 将字符串转为XML
			Element rootElt = doc.getRootElement(); // 获取根节点
			Iterator<Element> iter = rootElt.elementIterator("row"); // 获取根节点下的子节点head
			Map<String, Object> info = null;
			String dataType = null;
			Element itemEle = null;
			String bettime = null;
			double cost = 0;
			double Earn = 0;
			while (iter.hasNext()) {
				info = new HashMap<String, Object>();
				itemEle = iter.next();
				dataType = itemEle.attributeValue("dataType");
				
				String gameType = itemEle.attributeValue("gameType") ;
				info.put("billNo", itemEle.attributeValue("billNo"));
				info.put("playerName", itemEle.attributeValue("playerName"));
				info.put("agentCode", itemEle.attributeValue("agentCode"));
				info.put("gameType", gameType);
				info.put("gameCode", gameType+"-"+YoPLAYUtils.__gameType.get(gameType));
				info.put("netAmount", itemEle.attributeValue("netAmount"));
				bettime = itemEle.attributeValue("betTime");
				if (!"".equals(GameHttpUtil.StringToSpace(bettime))) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(df.parse(itemEle.attributeValue("betTime")));
					cal.add(Calendar.HOUR, 12);
					bettime = df.format(cal.getTime());
				}
				info.put("betTime", bettime);
				info.put("betAmount", itemEle.attributeValue("betAmount"));
				info.put("validBetAmount", itemEle.attributeValue("validBetAmount"));
				info.put("flag", itemEle.attributeValue("flag"));
				info.put("playType", itemEle.attributeValue("playType"));
				info.put("currency", itemEle.attributeValue("currency"));
				info.put("tableCode", itemEle.attributeValue("tableCode"));
				info.put("loginIP", itemEle.attributeValue("loginIP"));
				info.put("recalcuTime", itemEle.attributeValue("recalcuTime"));
				info.put("platformType", itemEle.attributeValue("platformType"));
				info.put("remark", itemEle.attributeValue("remark"));
				info.put("round", itemEle.attributeValue("round"));
				info.put("slottype", itemEle.attributeValue("slottype"));
				info.put("result", itemEle.attributeValue("result"));
				info.put("mainbillno", itemEle.attributeValue("mainbillno"));
				info.put("beforeCredit", itemEle.attributeValue("beforeCredit"));
				info.put("deviceType", itemEle.attributeValue("deviceType"));
				info.put("betAmountBase", itemEle.attributeValue("betAmountBase"));
				info.put("betAmountBonus", itemEle.attributeValue("betAmountBonus"));
				info.put("netAmountBase", itemEle.attributeValue("netAmountBase"));
				info.put("netAmountBonus", itemEle.attributeValue("netAmountBonus"));
				
				
				info.put("createtime", df.format(new Date()));
				info.put("betmoney", info.get("betAmount"));
				info.put("netmoney", info.get("netAmount"));
				info.put("validmoney", info.get("validBetAmount"));
				
				listInsert.add(info);
				
			}
			int a = 0;
			if(listInsert.size() > 0) {
				a = apiYoplayGameinfoMapper.batchInsert(listInsert);
			}
			
			//更新为已处理
			data.put("status", 1);
			apiTagXmlRecordMapper.insert(data);
			return a;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * AG最后8个文件会有数据覆盖式写入，要倒退16分钟重复采集
	 * @param max
	 * @return
	 */
	private static String back20Min(String max) {
		Calendar calendar = Calendar.getInstance();
		
		try {
			calendar.setTime(DateUtil.parse(max, "yyyyMMddHHmm"));
			calendar.add(Calendar.MINUTE, -30);
			String temp_max = DateUtil.parse(calendar.getTime(), "yyyyMMddHHmm");
			
			//如果倒退后已经不同日，则不去倒退
			if( !max.substring(0,8).equals(temp_max.substring(0,8))) {
				return max;
			} else {
				return temp_max;
			}
			
		} catch( Exception e) {
			e.printStackTrace();
		}
		return max;
	}
	
	private static String getNextMaxNew(String max) {
		Calendar calendar = Calendar.getInstance();
		
		try {
			//传入时间就是美东时间
			Date maxdate = null;
			calendar.setTime(DateUtil.parse(max, "yyyyMMddHHmm"));
			maxdate = calendar.getTime();
			
			//当前时间转换为美东时间
			Date currendate = null;
			calendar.setTime(new Date());
			calendar.add(Calendar.HOUR_OF_DAY, -12);
			currendate = calendar.getTime();
			String currentime = DateUtil.parse(currendate, "yyyyMMddHHmm");
			
			
			//如果是跨日则步长为小时
			//如果不是跨日则步长为2分钟
			final int daymin = 60*24;
			int intervalnum = 2;//步长
			if( !currentime.substring(0,8).equals(max.substring(0, 8))) {
				intervalnum = daymin;//步长
			} else {
				int h1 = Integer.valueOf(DateUtil.parse(currendate, "HH"));
				int h2 = Integer.valueOf(DateUtil.parse(maxdate, "HH"));
				if( (h1 - h2) > 1 ) {
					intervalnum = 60;//步长
				}
			}
			
			//如果加了步长之后是跨日的,则要从12时开始（美东时间是12时为0凌晨）
			String str1 = DateUtil.parse(DateUtil.add(maxdate, Calendar.MINUTE, intervalnum), "yyyyMMdd");
			if( !currentime.substring(0,8).equals(str1) ) {
				max = str1.concat("1200");//从当天的12:00开始
				return max;
			} else {
				str1 = DateUtil.parse(DateUtil.add(maxdate, Calendar.MINUTE, intervalnum), "yyyyMMddHHmm");
			}
			
			//最大不能超过当前时间
			String enddate = DateUtil.parse(DateUtil.add(maxdate, Calendar.MINUTE, intervalnum), "yyyyMMddHHmm");//
			if(DateUtil.parse(enddate, "yyyyMMddHHmm").getTime() > currendate.getTime()) {
				//重置为当前美东时间  -20分钟
				calendar = Calendar.getInstance();
				calendar.add(Calendar.HOUR_OF_DAY, -12);
				calendar.add(Calendar.MINUTE, -20);
				maxdate = calendar.getTime();
				max = DateUtil.parse(maxdate, "yyyyMMddHHmm");
			} else {
				max = str1;
			}
			
			return max;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return max;
	}

	/**
	 * 根据时间计算转换为美东时间
	 * @param max
	 * @return
	 */
	private static String getUSeast(String max) {
		Calendar calendar = Calendar.getInstance();
		
		//当前时间转换为美东时间
		Date date1 = null;
		try {
			calendar.setTime(DateUtil.parse(max, "yyyyMMddHHmm"));
			calendar.add(Calendar.HOUR_OF_DAY, -12);
			date1 = calendar.getTime();
			return DateUtil.parse(date1, "yyyyMMddHHmm");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return max;
	}
	
	
	private int updateMax(String agentcode, String platformtype, String xmltype, String max) {
		Map<String, Object> mapentity = new HashMap<String, Object>();
		mapentity.put("agentcode", agentcode);
		mapentity.put("platformtype", platformtype);//AGIN/HUNTER/XIN
		mapentity.put("xmltype", xmltype);//normal/lostAndfound
		mapentity.put("updatetime", max);
		return apiTagXmlTimerMapper.insert(mapentity);
	}
	private String getMax(String agentcode, String platformtype, String xmltype) {
		Map<String, Object> mapentity = new HashMap<String, Object>();
		mapentity.put("agentcode", agentcode);
		mapentity.put("platformtype", platformtype);//AGIN/HUNTER/XIN
		mapentity.put("xmltype", xmltype);//normal/lostAndfound
		List<Map<String, Object>> listtimer = apiTagXmlTimerMapper.selectByEntity(mapentity);
		Map<String, Object> maxtimer = null;
		if(listtimer != null && listtimer.size() > 0) {
			maxtimer = listtimer.get(0);
			return maxtimer.get("updatetime").toString();
		}
    	return null;
	}
	private List<Map<String, Object>> getListData(List<String>  result, String agentcode, String platformtype, String xmltype, String filepath) {
		List<Map<String, Object>> listdata = new ArrayList<Map<String, Object>>();
    	for (String string : result) {
    		Map<String, Object> data = new HashMap<String, Object>();
    		data.put("agentcode", agentcode);
    		data.put("platformtype", platformtype);
    		data.put("filenumber", string);
    		data.put("xmltype", xmltype);
    		data.put("filepath", filepath+""+string+".xml");
    		data.put("createtime", new Date());
    		data.put("status", 0);//待处理
    		listdata.add(data);
		}
    	return listdata;
	}
	public static void main(String[] args) {
		
		try {
			String strtime="201708050000";//201707300912
	    	System.out.println(getNextMaxNew(strtime));
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
		try {
			String strtime="201707300912";//201707300912
			
//			for (int i = 0; i < 50; i++) {
//				strtime = getNextMaxNew(strtime);
//				System.out.println(i+"下一个时间："+strtime);
//				Thread.sleep(1000);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
	}
}
