package com.hy.pull.service.game;

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
import com.hy.pull.common.util.game.EBETGame;
import com.hy.pull.common.util.game.NHQGame;
import com.hy.pull.mapper.ApiEbetGameinfoMapper;
import com.hy.pull.mapper.DataHandleLogsMapper;
import com.hy.pull.mapper.DataHandleMapper;
import com.hy.pull.mapper.NhqGameinfoMapper;
import com.hy.pull.mapper.TbProxyKeyMapper;
import com.hy.pull.service.BaseService;
import com.hy.pull.service.BaseService.Enum_flag;

/**
 * eBET游戏服务类
 * 创建日期 2016-10-13
 * @author temdy
 */
@Service
public class EBETGameService extends BaseService {
	
	Logger logger = LogManager.getLogger(EBETGameService.class.getName());  
	
	@Autowired
	private TbProxyKeyMapper tbProxyKeyMapper;
	
	@Autowired
	private ApiEbetGameinfoMapper nhqGameinfoMapper;
	
	@Autowired
	private DataHandleMapper dataHandleMapper;
	@Autowired
	private DataHandleLogsMapper dataHandleLogsMapper;
	
	
	/**
	 * 按条件拉取数据的方法
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
		Map<String, Object> dataHandle = dataHandleMapper.selectByPrimaryKey(LogUtil.HANDLE_EBET);
		dataHandle.put("lastnum", "0");
		String updatetime = dataHandle.get("updatetime").toString();
		
		
		int count = 0;//累计执行总数量
		//System.out.println("新环球游戏数据拉取开始，参数列表："+entity);
		if(entity==null){//判断是否为空，空则创建一个新的对象
			entity = new HashMap<String,Object>();
		}
		if(!entity.containsKey("GAME_ID")){//判断是否存在游戏编号，没则默认初始化为新环球游戏
			entity.put("GAME_ID", "23");//设置所属游戏编号				
		}
		//获取新环球游戏的所有代理密钥列表
		List<Map<String,Object>> list = tbProxyKeyMapper.selectByEntity(entity);
		int size = list.size();		
		if(size > 0){
			String apiUrl = null;//接口URL
			String channelid = null;//帐号
			String subchannelid = null;//代理名称
			String privateKey = "";
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
			
			//间隔分钟数（不得超过12小时）
			int intervalnum = 1 * 60;
			intervalnum = Integer.valueOf(dataHandle.get("intervalnum").toString());
			
			/***********************设定开始结束时间，每次只拉取25分钟内的数据*****************************/
			stardate = DateUtil.parse(DateUtil.add(DateUtil.parse(updatetime, "yyyy-MM-dd HH:mm:ss"), Calendar.MINUTE, this.backMinute), "yyyy-MM-dd HH:mm:ss");//
			enddate = DateUtil.parse(DateUtil.add(DateUtil.parse(stardate, "yyyy-MM-dd HH:mm:ss"), Calendar.MINUTE, intervalnum), "yyyy-MM-dd HH:mm:ss");//
			if(DateUtil.parse(enddate, "yyyy-MM-dd HH:mm:ss").getTime() > new Date().getTime()) {
				//最后时间不能超过当前时间
				enddate = DateUtil.parse(new Date(), "yyyy-MM-dd HH:mm:ss");
			}
			
			
			int len = 0;
			Map<String,Object> map = new HashMap<String,Object>();
			List<Map<String,Object>> data = null;
			
			boolean flag = true;
			
			
			for(int i = 0; i < size; i++){
				entity = list.get(i);
				apiUrl = entity.get("PROXY_API_URL").toString();
				channelid = entity.get("PROXY_KEY1").toString();
				subchannelid = entity.get("PROXY_KEY2").toString();
				privateKey = entity.get("PROXY_MD5_KEY").toString();
				code =  entity.get("PROXY_CODE").toString();
				map.put("Platformflag", code);
				
//				if(stardate == null){//如果为空则获取数据库最大值
//					stardate = nhqGameinfoMapper.max(map);
//				}
				
				data = EBETGame.getEBETData(apiUrl, channelid, subchannelid,stardate,enddate,code, privateKey);//获取拉取数据列表;
				if(data != null){
					len = data.size();
					count += len;
					if(len > 0){//如果有数据就入库
						nhqGameinfoMapper.batchInsert(data);//批量入库
						
						dataHandle.put("lastnum", Integer.parseInt(dataHandle.get("lastnum").toString()) + len);
						dataHandle.put("allnum", Integer.parseInt(dataHandle.get("allnum").toString()) + len);
					}
					//dataHandleLogsMapper.insert(LogUtil.saveLog(LogUtil.HANDLE_EBET, null, len+"个", channelid+"="+subchannelid, Enum_flag.正常.value));
				} else {
					//为null表示出现异常
					flag = false;
					dataHandleLogsMapper.insert(LogUtil.saveLog(LogUtil.HANDLE_EBET, null, "返回NULL数据", channelid+"="+subchannelid, Enum_flag.异常.value));
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
		logger.debug("eBET游戏数据拉取结束。。。");
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
		List<String> listTime = getListStartEndTime( "yyyy-MM-dd HH:mm:ss", 4, Calendar.HOUR_OF_DAY);
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
		
		
		int count = 0;//累计执行总数量
		//System.out.println("新环球游戏数据拉取开始，参数列表："+entity);
		if(entity==null){//判断是否为空，空则创建一个新的对象
			entity = new HashMap<String,Object>();
		}
		if(!entity.containsKey("GAME_ID")){//判断是否存在游戏编号，没则默认初始化为新环球游戏
			entity.put("GAME_ID", "23");//设置所属游戏编号				
		}
		//获取新环球游戏的所有代理密钥列表
		List<Map<String,Object>> list = tbProxyKeyMapper.selectByEntity(entity);
		int size = list.size();		
		if(size > 0){
			String apiUrl = null;//接口URL
			String channelid = null;//帐号
			String subchannelid = null;//代理名称
			String privateKey = "";
			String code = null;//代理编码
			
			
			int len = 0;
			Map<String,Object> map = new HashMap<String,Object>();
			List<Map<String,Object>> data = null;
			
			boolean flag = true;
			
			
			for(int i = 0; i < size; i++){
				entity = list.get(i);
				apiUrl = entity.get("PROXY_API_URL").toString();
				channelid = entity.get("PROXY_KEY1").toString();
				subchannelid = entity.get("PROXY_KEY2").toString();
				privateKey = entity.get("PROXY_MD5_KEY").toString();
				code =  entity.get("PROXY_CODE").toString();
				map.put("Platformflag", code);
				
//				if(stardate == null){//如果为空则获取数据库最大值
//					stardate = nhqGameinfoMapper.max(map);
//				}
				
				data = EBETGame.getEBETData(apiUrl, channelid, subchannelid,stardate,enddate,code, privateKey);//获取拉取数据列表;
				if(data != null){
					len = data.size();
					count += len;
					if(len > 0){//如果有数据就入库
						nhqGameinfoMapper.batchInsert(data);//批量入库
						
					}
					//dataHandleLogsMapper.insert(LogUtil.saveLog(LogUtil.HANDLE_EBET, null, len+"个", channelid+"="+subchannelid, Enum_flag.正常.value));
				} else {
					//为null表示出现异常
					flag = false;
					dataHandleLogsMapper.insert(LogUtil.saveLog(LogUtil.HANDLE_EBET, null, "返回NULL数据", channelid+"="+subchannelid, Enum_flag.异常.value));
					break;
				}
			}
			
			/***************更新配置管理库****************/
			/***************更新配置管理库****************/
		}
		logger.debug("eBET游戏数据拉取结束。。。");
		return count;
	}
}
