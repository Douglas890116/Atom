package com.hy.pull.service.game;

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
import com.hy.pull.common.util.game.IMGame;
import com.hy.pull.mapper.ApiEibcGameinfoMapper;
import com.hy.pull.mapper.ApiImGameinfoMapper;
import com.hy.pull.mapper.DataHandleLogsMapper;
import com.hy.pull.mapper.DataHandleMapper;
import com.hy.pull.mapper.IbcGameinfoMapper;
import com.hy.pull.mapper.TbProxyKeyMapper;
import com.hy.pull.service.BaseService;
import com.hy.pull.service.BaseService.Enum_flag;

/**
 * IM体育游戏服务类
 * 创建日期 2016-10-13
 * @author temdy
 */
@Service
public class IMGameService extends BaseService {
	
	Logger logger = LogManager.getLogger(IMGameService.class.getName());  
	
	@Autowired
	private TbProxyKeyMapper tbProxyKeyMapper;
	
	@Autowired
	private ApiImGameinfoMapper apiImGameinfoMapper;
	
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
		Map<String, Object> dataHandle = dataHandleMapper.selectByPrimaryKey(LogUtil.HANDLE_IM);
		dataHandle.put("lastnum", "0");
		String updatetime = dataHandle.get("updatetime").toString();
		
		
		//System.out.println(entity);
		int count = 0;//累计执行总数量
		logger.debug("IM体育游戏数据拉取开始，参数列表："+entity);
		if(entity==null){//判断是否为空，空则创建一个新的对象
			entity = new HashMap<String,Object>();
		}
		if(!entity.containsKey("GAME_ID")){//判断是否存在游戏编号，没则默认初始化为沙巴体育游戏
			entity.put("GAME_ID", "30");//设置所属游戏编号				
		}
		//获取沙巴体育游戏的所有代理密钥列表
		List<Map<String,Object>> list = tbProxyKeyMapper.selectByEntity(entity);
		int size = list.size();		
		if(size > 0){
			String apiUrl = null;//接口URL
			String agent = null;//代理名称
			String stardate = null;//开始日期
			String enddate = null;//开始日期
			
			String platformflag = null;//代理编码
			
			String MerchantCode = null;//
			
			int len = 0;
			Map<String,Object> map = new HashMap<String,Object>();
			List<Map<String,Object>> data = null;
			
			
			//间隔分钟数（不得超过10分钟）
			int intervalnum = 10;
			intervalnum = Integer.valueOf(dataHandle.get("intervalnum").toString());
			
			
			/***********************设定开始结束时间，每次只拉取10分钟内的数据*****************************/
			stardate = DateUtil.parse(DateUtil.add(DateUtil.parse(updatetime, "yyyy-MM-dd HH:mm:ss"), Calendar.MINUTE, -5), "yyyy-MM-dd HH:mm:ss");//
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
				
				MerchantCode = entity.get("PROXY_KEY1").toString();//商户号
				
				platformflag = entity.get("PROXY_CODE") == null ? agent : entity.get("PROXY_CODE").toString();
				map.put("Platformflag", agent);
				
				data = IMGame.getData(apiUrl, MerchantCode, stardate, enddate, platformflag, null);//获取拉取数据列表;
				if(data != null){
					len = data.size();
					count += len;
					if(len > 0){//如果有数据就入库
						apiImGameinfoMapper.batchInsert(data);//批量入库
						
						dataHandle.put("lastnum", Integer.parseInt(dataHandle.get("lastnum").toString()) + len);
						dataHandle.put("allnum", Integer.parseInt(dataHandle.get("allnum").toString()) + len);
					}
					
					//dataHandleLogsMapper.insert(LogUtil.saveLog(LogUtil.HANDLE_EIBC, null, len+"个", OpCode, Enum_flag.正常.value));
				} else {
					//为null表示出现异常
					flag = false;
					dataHandleLogsMapper.insert(LogUtil.saveLog(LogUtil.HANDLE_IM, null, "返回NULL数据", MerchantCode, Enum_flag.异常.value));
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
		logger.debug("IM体育游戏数据拉取结束。。。");
		return count;
	}
	
	
	
	
	/**
	 * 每1小时重复采集结算状态为【未结算】的数据
	 * 
	 * 0 = Not Settled  未结算, 1 = Settled  已结算
	 * 
	 */
	public Integer pullDataPatchStatus(Map<String, Object> entity) throws Exception {
		int count = 0;
		
		List<Map<String, Object>> __list = new ArrayList<Map<String, Object>>();
		//查询数据库里面等待和进行中的数据
		Map<String, Object> params = new HashMap<>();
		params.put("issettled", "0");
		__list.addAll(apiImGameinfoMapper.selectByEntity(params)) ;
		logger.error("IM体育当前有【未结算】状态数据："+(__list != null ? __list.size() : 0 ) + "条");
		
		if(__list != null && __list.size() > 0) {
			
			//获取IM体育游戏的所有代理密钥列表
			entity = new HashMap<>();
			entity.put("GAME_ID", "30");
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
			
			
			//获取每条记录的北京投注时间，再减去5分钟，为起始时间。结束时间为起始时间后的10分钟内（每次最多宽度只能是10分钟）
			List<Map<String, Object>> listdata = null;
			int index = 0;
			for (Map<String, Object> data : __list) {
				if(index > 100 ) {
					break;
				}
				index ++;
				logger.error("IM体育当前正在处理第："+index+"条");
				String bettime = data.get("bettime").toString();
				String betid = data.get("betid").toString();
				
				String starttime= DateUtil.parse(DateUtil.add(DateUtil.parse(bettime, "yyyy-MM-dd HH:mm:ss"), Calendar.MINUTE, -5), "yyyy-MM-dd HH:mm:ss");
				String endtime = DateUtil.parse(DateUtil.add(DateUtil.parse(bettime, "yyyy-MM-dd HH:mm:ss"), Calendar.MINUTE, +5), "yyyy-MM-dd HH:mm:ss");
				
				//查找该记录所在的代理的账号秘钥信息
				Map<String, Object> __opcodemap = mapOpCode.get(data.get("platformflag"));
				String apiUrl = __opcodemap.get("PROXY_API_URL").toString();
				String MerchantCode = __opcodemap.get("PROXY_KEY1").toString();
				
				listdata = IMGame.getData(apiUrl, MerchantCode, starttime, endtime, data.get("platformflag").toString(), null);//
				List<Map<String, Object>> __list_update = new ArrayList<Map<String,Object>>();
				if(listdata != null && listdata.size() > 0) {
					for (Map<String, Object> map : listdata) {
						String issettled = map.get("issettled").toString();
						String __betid = map.get("betid").toString();
						
						//如果已经不是waiting和running状态了，说明该注单已经有结果了，则更新结算时间为当前时间。
						//洗码数据按照结算时间为依据来发放。这里是特殊情况
						if( issettled.equals("1") && betid.equals(__betid)) {
							map.put("nettime", new Date());
							__list_update.add(map);
							apiImGameinfoMapper.insert(map);
						}
						
					}
				}
				/**不要批量更新。每处理完一个就少一个，效率更高
				if(__list_update != null && __list_update.size() > 0 ) {
					count += apiImGameinfoMapper.batchInsert(listdata);
				}
				**/
			}
			
		} else {
			count = 0;
		}
		return count;
	}
	
	
	public static void main(String[] args) {
		System.out.println(DateUtil.parse(new Date(), "yyyyMMddHHmmss"));
	}
}
