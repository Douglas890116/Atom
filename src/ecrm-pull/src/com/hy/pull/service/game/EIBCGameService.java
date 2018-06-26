package com.hy.pull.service.game;

import java.net.URLDecoder;
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
import com.hy.pull.common.util.game.EIBCGame;
import com.hy.pull.common.util.game.IBCGame;
import com.hy.pull.mapper.ApiEibcGameinfoMapper;
import com.hy.pull.mapper.DataHandleLogsMapper;
import com.hy.pull.mapper.DataHandleMapper;
import com.hy.pull.mapper.IbcGameinfoMapper;
import com.hy.pull.mapper.TbProxyKeyMapper;
import com.hy.pull.service.BaseService;
import com.hy.pull.service.BaseService.Enum_flag;

/**
 * 新沙巴体育游戏服务类
 * 创建日期 2016-10-13
 * @author temdy
 */
@Service
public class EIBCGameService extends BaseService {
	
	Logger logger = LogManager.getLogger(EIBCGameService.class.getName());  
	
	@Autowired
	private TbProxyKeyMapper tbProxyKeyMapper;
	
	@Autowired
	private ApiEibcGameinfoMapper apiEibcGameinfoMapper;
	
	@Autowired
	private DataHandleMapper dataHandleMapper;
	@Autowired
	private DataHandleLogsMapper dataHandleLogsMapper;
	
	/**
	 * 按条件拉取数据的方法
	 * @param entity 条件集合{GAME_ID:游戏编号,PROXY_ID:代理编号,MAX_VALUE:上次拉取的最大值}
	 * @return 数据行数
	 */
	@Override
	public Integer pullData(Map<String, Object> entity) throws Exception {
		
		/**********************获取上次拉取的最大值***********************/
		Map<String, Object> dataHandle = dataHandleMapper.selectByPrimaryKey(LogUtil.HANDLE_EIBC);
		dataHandle.put("lastnum", "0");
		String updatetime = dataHandle.get("updatetime").toString();//agent=10;agent=10;
		
		
		//System.out.println(entity);
		int count = 0;//累计执行总数量
		logger.debug("新沙巴体育游戏数据拉取开始，参数列表："+entity);
		if(entity==null){//判断是否为空，空则创建一个新的对象
			entity = new HashMap<String,Object>();
		}
		if(!entity.containsKey("GAME_ID")){//判断是否存在游戏编号，没则默认初始化为沙巴体育游戏
			entity.put("GAME_ID", "27");//设置所属游戏编号				
		}
		//获取沙巴体育游戏的所有代理密钥列表
		List<Map<String,Object>> list = tbProxyKeyMapper.selectByEntity(entity);
		int size = list.size();		
		if(size > 0){
			String apiUrl = null;//接口URL
			String agent = null;//代理名称
			String vendorid = null;//上次拉取的最大值
			String MAX_VALUE = null;
			String stardate = null;//开始日期
			String enddate = null;//开始日期
			
			if(entity.get("MAX_VALUE") != null){//判断是否存在最大值
				vendorid = entity.get("MAX_VALUE").toString();
				MAX_VALUE = entity.get("MAX_VALUE").toString();
			}
			String code = null;//代理编码
			
			String OpCode = null;//OpCode
			String PrivateKey = null;//PrivateKey
			
			int len = 0;
			Map<String,Object> map = new HashMap<String,Object>();
			List<Map<String,Object>> data = null;
			
			
			//间隔分钟数（不得超过12小时）
			int intervalnum = 6 * 60;
			intervalnum = Integer.valueOf(dataHandle.get("intervalnum").toString());
			
			
			/***********************设定开始结束时间，每次只拉取25分钟内的数据*****************************/
			stardate = DateUtil.parse(DateUtil.add(DateUtil.parse(updatetime, "yyyy-MM-dd HH:mm:ss"), Calendar.MINUTE, -3*60), "yyyy-MM-dd HH:mm:ss");//
			enddate = DateUtil.parse(DateUtil.add(DateUtil.parse(stardate, "yyyy-MM-dd HH:mm:ss"), Calendar.MINUTE, intervalnum), "yyyy-MM-dd HH:mm:ss");//
			if(DateUtil.parse(enddate, "yyyy-MM-dd HH:mm:ss").getTime() > new Date().getTime()) {
				//最后时间不能超过当前时间
				enddate = DateUtil.parse(new Date(), "yyyy-MM-dd HH:mm:ss");
			}
			
			boolean flag = true;
			
			
			for(int i = 0; i < size; i++){
				entity = list.get(i);
				apiUrl = entity.get("PROXY_API_URL").toString();
				agent = entity.get("PROXY_NAME").toString();
				
				OpCode = entity.get("PROXY_KEY1").toString();//OpCode
				PrivateKey = entity.get("PROXY_KEY2").toString();//PrivateKey
				
				code = entity.get("PROXY_CODE") == null ? agent : entity.get("PROXY_CODE").toString();
				map.put("Platformflag", agent);
				
				
				data = EIBCGame.getRecord( apiUrl, stardate, enddate,  PrivateKey, OpCode);//获取拉取数据列表;
				if(data != null){
					len = data.size();
					count += len;
					if(len > 0){//如果有数据就入库
						apiEibcGameinfoMapper.batchInsert(data);//批量入库
						
						dataHandle.put("lastnum", Integer.parseInt(dataHandle.get("lastnum").toString()) + len);
						dataHandle.put("allnum", Integer.parseInt(dataHandle.get("allnum").toString()) + len);
					}
					
					//dataHandleLogsMapper.insert(LogUtil.saveLog(LogUtil.HANDLE_EIBC, null, len+"个", OpCode, Enum_flag.正常.value));
				} else {
					//为null表示出现异常
					flag = false;
					dataHandleLogsMapper.insert(LogUtil.saveLog(LogUtil.HANDLE_EIBC, null, "返回NULL数据", OpCode, Enum_flag.异常.value));
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
		logger.debug("新沙巴体育游戏数据拉取结束。。。");
		return count;
	}
	
	/**
	 * 每2小时重复采集状态为【waiting】和【running】的数据
	 * 
	 * 
	 */
	public Integer pullDataPatchStatus(Map<String, Object> entity) throws Exception {
		int count = 0;
		
		List<Map<String, Object>> __list = new ArrayList<Map<String, Object>>();
		//查询数据库里面等待和进行中的数据
		Map<String, Object> params = new HashMap<>();
		params.put("ticketstatus", "waiting");
		__list.addAll(apiEibcGameinfoMapper.selectByEntity(params)) ;
		logger.error("沙巴体育当前有waiting状态数据："+(__list != null ? __list.size() : 0 ) + "条");
		params.put("ticketstatus", "running");
		__list.addAll(apiEibcGameinfoMapper.selectByEntity(params)) ;
		logger.error("沙巴体育当前有running状态数据："+(__list != null ? __list.size() : 0 ) + "条");
		
		if(__list != null && __list.size() > 0) {
			
			//获取沙巴体育游戏的所有代理密钥列表
			entity = new HashMap<>();
			entity.put("GAME_ID", "27");
			List<Map<String,Object>> list = tbProxyKeyMapper.selectByEntity(entity);
			Map<String, Map<String,Object>> mapOpCode = new HashMap<String, Map<String,Object>>();
			for (Map<String, Object> map : list) {
//				apiUrl = entity.get("PROXY_API_URL").toString();
//				agent = entity.get("PROXY_NAME").toString();
//				OpCode = entity.get("PROXY_KEY1").toString();//OpCode
//				PrivateKey = entity.get("PROXY_KEY2").toString();//PrivateKey
//				code = entity.get("PROXY_CODE") == null ? agent : entity.get("PROXY_CODE").toString();
//				map.put("Platformflag", agent);
				mapOpCode.put(map.get("PROXY_KEY1").toString(), map);
			}
			
			/**************找到list中开始时间和结束时间
			String starttime = __list.get(0).get("bettime").toString();
			String endtime11 = __list.get(__list.size() - 1).get("bettime").toString();
			if( !starttime.substring(0, 10).equals(endtime11.substring(0, 10)) ) {
				//不是同一天的
			} else {
				//是同一天的
			}
			**************/
			
			//获取每条记录的北京投注时间，再减去1个小时，为起始时间。结束时间为起始时间后的11小时
			List<Map<String, Object>> listdata = null;
			int index = 0;
			for (Map<String, Object> data : __list) {
				index ++;
				logger.error("沙巴体育当前正在处理第："+index+"条");
				String bettime = data.get("bettime").toString();
				String transid = data.get("transid").toString();
				
				String starttime = DateUtil.parse(DateUtil.add(DateUtil.parse(bettime, "yyyy-MM-dd HH:mm:ss"), Calendar.MINUTE, -10), "yyyy-MM-dd HH:mm:ss");
				String endtime = DateUtil.parse(DateUtil.add(DateUtil.parse(bettime, "yyyy-MM-dd HH:mm:ss"), Calendar.MINUTE, +10), "yyyy-MM-dd HH:mm:ss");
				
				//查找该记录所在的代理的账号秘钥信息
				Map<String, Object> __opcodemap = mapOpCode.get(data.get("versionkey"));
				String apiUrl = __opcodemap.get("PROXY_API_URL").toString();
				String PrivateKey = __opcodemap.get("PROXY_KEY2").toString();
				String OpCode = __opcodemap.get("PROXY_KEY1").toString();
				
				listdata = EIBCGame.getRecord( apiUrl, starttime, endtime,  PrivateKey, OpCode);//获取拉取数据列表;
				if(listdata != null && listdata.size() > 0) {
					//logger.error("查找到记录数："+listdata.size()+"条");
					for (Map<String, Object> map : listdata) {
						String ticketstatus = map.get("ticketstatus").toString();
						String __transid = map.get("transid").toString();
						
						//如果已经不是waiting和running状态了，说明该注单已经有结果了，则更新结算时间为当前时间。
						//洗码数据按照结算时间为依据来发放。这里是特殊情况
						if(!ticketstatus.equals("waiting") && !ticketstatus.equals("running") && transid.equals(__transid)) {
							map.put("nettime", new Date());
							apiEibcGameinfoMapper.insert(map);
							//System.out.println("完成了==="+__transid);
							count ++;
						}
						
					}
				}
				
				/**不要批量更新。每处理完一个就少一个，效率更高
				if(listdata != null && listdata.size() > 0 ) {
					count += apiEibcGameinfoMapper.batchInsert(listdata);
				}
				*/
			}
			
		} else {
			count = 0;
		}
		return count;
	}
	
	
	/**
	 * 每日补单
	 * 
	 * 
	 */
	public Integer pullDataPatch(Map<String, Object> entity) throws Exception {
		int count = 0;
		
		/**********每次拉一天的（北京时间昨日）**************/
		List<String> listTime = getListStartEndTime( "yyyy-MM-dd HH:mm:ss", 2, Calendar.HOUR_OF_DAY);
		for (String string : listTime) {
			String stardate = string.split("=")[0];
			String enddate = string.split("=")[1];
			count += pullDataPatch(entity, stardate, enddate);
		}
		
		return count;
	}
	/**
	 * 补单数据
	 * 
	 * @return 数据行数
	 */
	private Integer pullDataPatch(Map<String, Object> entity,String stardate,String enddate) throws Exception {
		
		/**********************获取上次拉取的最大值***********************/
		
		
		//System.out.println(entity);
		int count = 0;//累计执行总数量
		logger.debug("新沙巴体育游戏数据拉取开始，参数列表："+entity);
		if(entity==null){//判断是否为空，空则创建一个新的对象
			entity = new HashMap<String,Object>();
		}
		if(!entity.containsKey("GAME_ID")){//判断是否存在游戏编号，没则默认初始化为沙巴体育游戏
			entity.put("GAME_ID", "27");//设置所属游戏编号				
		}
		//获取沙巴体育游戏的所有代理密钥列表
		List<Map<String,Object>> list = tbProxyKeyMapper.selectByEntity(entity);
		int size = list.size();		
		if(size > 0){
			String apiUrl = null;//接口URL
			String agent = null;//代理名称
			String vendorid = null;//上次拉取的最大值
			String MAX_VALUE = null;
			
			if(entity.get("MAX_VALUE") != null){//判断是否存在最大值
				vendorid = entity.get("MAX_VALUE").toString();
				MAX_VALUE = entity.get("MAX_VALUE").toString();
			}
			String code = null;//代理编码
			
			String OpCode = null;//OpCode
			String PrivateKey = null;//PrivateKey
			
			int len = 0;
			Map<String,Object> map = new HashMap<String,Object>();
			List<Map<String,Object>> data = null;
			
			
			
			boolean flag = true;
			
			
			for(int i = 0; i < size; i++){
				entity = list.get(i);
				apiUrl = entity.get("PROXY_API_URL").toString();
				agent = entity.get("PROXY_NAME").toString();
				
				OpCode = entity.get("PROXY_KEY1").toString();//OpCode
				PrivateKey = entity.get("PROXY_KEY2").toString();//PrivateKey
				
				code = entity.get("PROXY_CODE") == null ? agent : entity.get("PROXY_CODE").toString();
				map.put("Platformflag", agent);
				
				
				data = EIBCGame.getRecord( apiUrl, stardate, enddate,  PrivateKey, OpCode);//获取拉取数据列表;
				if(data != null){
					len = data.size();
					count += len;
					if(len > 0){//如果有数据就入库
						apiEibcGameinfoMapper.batchInsert(data);//批量入库
						
					}
					
					//dataHandleLogsMapper.insert(LogUtil.saveLog(LogUtil.HANDLE_EIBC, null, len+"个", OpCode, Enum_flag.正常.value));
				} else {
					//为null表示出现异常
					flag = false;
					dataHandleLogsMapper.insert(LogUtil.saveLog(LogUtil.HANDLE_EIBC, null, "返回NULL数据", OpCode, Enum_flag.异常.value));
					break;
				}
			}
			
			/***************更新配置管理库****************/
			/***************更新配置管理库****************/
			
		}
		logger.debug("新沙巴体育游戏数据拉取结束。。。");
		return count;
	}
	
	public static void main(String[] args) {
//		System.out.println(DateUtil.parse(new Date(), "yyyyMMddHHmmss"));
		System.out.println(URLDecoder.decode("2017-10-25T00%3a31%3a14"));
		System.out.println(URLDecoder.decode("2017-10-25T01%3a01%3a14"));
		System.out.println("http://api.prod.ib.gsoft88.net/api/GetSportBettingDetail?OpCode=HYECN&StartTime=2017-10-25T00%3a31%3a14&EndTime=2017-10-25T01%3a01%3a14&SecurityToken=A41ABAD46C1F41FC9B6F484E85C00E16");
	}
}
