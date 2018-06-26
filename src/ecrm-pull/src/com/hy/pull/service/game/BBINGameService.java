package com.hy.pull.service.game;

import java.text.SimpleDateFormat;
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
import com.hy.pull.common.util.game.BBINGame;
import com.hy.pull.mapper.BbinGameinfoMapper;
import com.hy.pull.mapper.DataHandleLogsMapper;
import com.hy.pull.mapper.DataHandleMapper;
import com.hy.pull.mapper.TbGameKindMapper;
import com.hy.pull.mapper.TbGameTypeMapper;
import com.hy.pull.mapper.TbProxyKeyMapper;
import com.hy.pull.service.BaseService;
import com.hy.pull.service.BaseService.Enum_flag;

/**
 * 波音游戏服务类
 * 创建日期 2016-10-13
 * @author temdy
 */
@Service
public class BBINGameService extends BaseService {
	
	Logger logger = LogManager.getLogger(BBINGameService.class.getName());  
	
	@Autowired
	private BbinGameinfoMapper bbinGameinfoMapper;
	
	@Autowired
	private TbProxyKeyMapper tbProxyKeyMapper;
	
	@Autowired
	private TbGameKindMapper tbGameKindMapper;
	
	@Autowired
	private TbGameTypeMapper tbGameTypeMapper;
	
	@Autowired
	private DataHandleMapper dataHandleMapper;
	@Autowired
	private DataHandleLogsMapper dataHandleLogsMapper;
	
	
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
	/**
	 * 按条件拉取数据的方法
	 * @param entity 条件集合 {GAME_ID:游戏编号,PROXY_ID:代理编号,GAME_KIND_ID:游戏种类编号,GAME_SUB_KIND_ID:游戏子种类编号,GAME_TYPE_ID:游戏类型编号,USRE_NAME:会员帐号,ROUND_DATE:拉取日期,START_TIME:开始时间,END_TIME:结束时间}
	 * @return 数据行数
	 */
	@Override
	public Integer pullData(Map<String, Object> entity) throws Exception {
		
		/**********************获取上次拉取的最大值***********************/
		Map<String, Object> dataHandle = dataHandleMapper.selectByPrimaryKey(LogUtil.HANDLE_BBIN);
		dataHandle.put("lastnum", "0");
		String updatetime = dataHandle.get("updatetime").toString();
		
		Calendar calendar = Calendar.getInstance();
		
		int count = 0;//累计执行总数量
		//System.out.println("波音游戏数据拉取开始，参数列表："+entity);
		if(entity==null){//判断是否为空，空则创建一个新的对象
			entity = new HashMap<String,Object>();
		}
		if(!entity.containsKey("GAME_ID")){//判断是否存在游戏编号，没则默认初始化为波音游戏
			entity.put("GAME_ID", "3");//设置所属游戏编号				
		}
		//获取波音游戏的所有代理密钥列表
		List<Map<String,Object>> list = tbProxyKeyMapper.selectByEntity(entity);
		int size = list.size();		
		if(size > 0){
			String apiUrl = null;//接口URL
			String website = null;//网站
			String username = null;//会员帐号
			if(entity.get("USRE_NAME") != null){//判断是否存在会员帐号
				username = entity.get("USRE_NAME").toString();
			}
			String uppername = null;//上层帐号
			String rounddate = null;//拉取日期
			String starttime = null;//开始时间
			String endtime = null;//结束时间
			String code = null;//代理编码
			if(entity.get("ROUND_DATE") != null){//判断是否存在拉取日期
				rounddate = entity.get("ROUND_DATE").toString();
			}

			if(entity.get("START_TIME") != null){//判断是否存在开始时间
				starttime = entity.get("START_TIME").toString();
			}

			if(entity.get("END_TIME") != null){//判断是否存在结束时间
				endtime = entity.get("END_TIME").toString();
			}
			
			
			/***********************设定开始结束时间，每次只拉取25分钟内的数据*****************************/
			String timer = getNextMaxNew(updatetime);
			starttime = timer.split(";")[0];
			endtime = timer.split(";")[1];
			
			
			
			/**********每次拉一天的（暂时）
			starttime = DateUtil.parse(DateUtil.add(DateUtil.parse(updatetime, "yyyy-MM-dd HH:mm:ss"), Calendar.SECOND, +1), "yyyy-MM-dd HH:mm:ss");//
			String updateDate = starttime.split(" ")[0];
			endtime = updateDate.split(" ")[0]+" 23:59:59";
			if(Integer.valueOf(updateDate.replaceAll("-", "")) > Integer.valueOf(DateUtil.parse(getUSeast(), "yyyyMMdd")) ) {
				//最后时间不能超过当前时间
				updateDate = updatetime.split(" ")[0];
				starttime = updateDate+" 00:00:00";
				endtime = updateDate+" 23:59:59";
			}
			**************/
			
			
			
			String userKey = null;//密钥
			String gamekind = null;//游戏种类
			String GAME_KIND_ID = null;
			if(entity.get("GAME_KIND_ID") != null){//判断是否存在种类编号
				GAME_KIND_ID = entity.get("GAME_KIND_ID").toString();
			}
			String subgamekind = null;//游戏子种类
			String gametype = null;//游戏类型
			int klen = 0;//游戏种类长度
			int sklen = 0;//游戏子种类长度
			int tlen = 0;//游戏类型长度
			Map<String,Object> map = new HashMap<String,Object>();
			List<Map<String,Object>> kinds = null;//游戏种类列表
			List<Map<String,Object>> types = null;//游戏类型列表
			List<Map<String,Object>> skinds = null;//游戏子种类列表
			Map<String,Object> kindMap = null;//游戏种类Map对象
			Map<String,Object> subKindMap = null;//游戏子种类Map对象
			Map<String,Object> typeMap = null;//游戏类型Map对象
			String kid = null;//种类编号
			
			boolean flag = true;
			
			for(int i = 0; i < size; i++){//第一层，所有代理商
				entity = list.get(i);
				apiUrl = entity.get("PROXY_API_URL").toString();
				website = entity.get("PROXY_KEY1").toString();
				uppername = entity.get("PROXY_NAME").toString();
				userKey = entity.get("PROXY_MD5_KEY").toString();
				code = entity.get("PROXY_CODE") == null ? uppername : entity.get("PROXY_CODE").toString();
				map.clear();
				map.put("GAME_ID", "3");//设置游戏是波音游戏
				map.put("GAME_KIND_PARENT", "0");//顶级种类
				if(GAME_KIND_ID != null){
					map.put("GAME_KIND_ID", GAME_KIND_ID);//种类编号		
				}
				kinds = tbGameKindMapper.selectByEntity(map);//获取波音游戏的种类列表
				klen = kinds.size();
				if(klen > 0){
					for (int j = 0; j < klen; j++){
						kindMap = kinds.get(j);//获取游戏种类的对象
						gamekind = kindMap.get("GAME_KIND_NO").toString();//获取种类的编码
						//map.put("Platformflag", uppername);
						kid = kindMap.get("GAME_KIND_ID").toString();//获取种类的编号
						if(gamekind.equals("5")){
							map.clear();
							map.put("GAME_KIND_PARENT", gamekind);//顶级种类							
							skinds = tbGameKindMapper.selectByEntity(map);//获取波音电子游戏的子种类列表
							sklen = skinds.size();
							if(sklen > 0){
								for(int s = 0; s < sklen; s++){
									subKindMap = skinds.get(s);
									subgamekind = subKindMap.get("GAME_KIND_NO").toString();//获取子种类的编码
									gametype = null;
									
									int cc = insertBBIN(apiUrl, website, username, uppername, gamekind, subgamekind, gametype ,userKey, rounddate, starttime, endtime,code);
									if(cc == -1) {
										flag = false;
										dataHandleLogsMapper.insert(LogUtil.saveLog(LogUtil.HANDLE_BBIN, null, "返回NULL数据", uppername+"="+gamekind, Enum_flag.异常.value));
										break;
									}
									count += cc;//拉取数据入库;
									
								}
							}
						}else if(gamekind.equals("12")){
							map.clear();
							if(entity.get("GAME_TYPE_ID") != null){
								map.put("GAME_TYPE_ID", entity.get("GAME_TYPE_ID").toString());
							}
							types = tbGameTypeMapper.selectByEntity(map);//获取波音彩票游戏的类型列表
							tlen = types.size();
							if(tlen > 0){
								for(int t = 0; t < tlen; t++){
									typeMap = types.get(t);
									gametype = typeMap.get("GAME_TYPE_NAME").toString();//获取游戏类型
									subgamekind = null;
									int cc = insertBBIN(apiUrl, website, username, uppername, gamekind, subgamekind, gametype ,userKey, rounddate, starttime, endtime,code);//拉取数据入库;
									
									if(cc == -1) {
										flag = false;
										dataHandleLogsMapper.insert(LogUtil.saveLog(LogUtil.HANDLE_BBIN, null, "返回NULL数据", uppername+"="+gamekind, Enum_flag.异常.value));
										break;
									}
									count += cc;//拉取数据入库;
								}
							}
						}else{
							gametype = null;
							subgamekind = null;
							int cc = insertBBIN(apiUrl, website, username, uppername, gamekind, subgamekind, gametype ,userKey, rounddate, starttime, endtime,code);//拉取数据入库;
							
							if(cc == -1) {
								flag = false;
								dataHandleLogsMapper.insert(LogUtil.saveLog(LogUtil.HANDLE_BBIN, null, "返回NULL数据", uppername+"="+gamekind, Enum_flag.异常.value));
								break;
							}
							count += cc;//拉取数据入库;
						}
					}
				}
			}
			
			
			dataHandle.put("lastnum", Integer.parseInt(dataHandle.get("lastnum").toString()) + count);
			dataHandle.put("allnum", Integer.parseInt(dataHandle.get("allnum").toString()) + count);
			
			/***************更新配置管理库****************/
			//如果出现异常则数量为-1，不累加时间
			if(flag) {
				dataHandle.put("updatetime", endtime);
				dataHandle.put("lasttime", DateUtil.parse(new Date(), "yyyyMMddHHmmss"));
				dataHandleMapper.update(dataHandle);
			}
			/***************更新配置管理库****************/
		}
		logger.debug("波音游戏数据拉取结束。。。");
		return count;
	}	
	
	
	/**
	 * 波音游戏数据入库
	 * @param apiUrl 接口链接
	 * @param website 网站名称
	 * @param username 会员帐号
	 * @param uppername 上层帐号
	 * @param gamekind 游戏种类（1、BB体育，3、视讯，5、机率，12、彩票，15、3D厅）
	 * @param subgamekind (gamekind=5时，值：1、2，预设为1)
	 * @param gametype 游戏类型（gamekind=12时，需强制带入）
	 * @param userKey MD5密钥
	 * @param rounddate 拉取日期
	 * @param starttime 开始时间
	 * @param endtime 结束时间
	 * @param code 代理编码
	 * @return 入库的数量
	 */
	private int insertBBIN(String apiUrl, String website, String username, String uppername,String gamekind,String subgamekind, String gametype, String userKey,String rounddate, String starttime, String endtime,String code) {
		List<Map<String,Object>> data = BBINGame.getBBINData(apiUrl, website, username, uppername, gamekind, subgamekind, gametype ,userKey, rounddate,starttime,endtime,code);//获取拉取数据列表;
		int len = 0;
		if(data != null){
			len = data.size();
			if(len > 0){//如果有数据就入库			
				bbinGameinfoMapper.batchInsert(data);//批量入库			
			}
			return len;
		} else {
			//为null时表示出现异常
			return -1;
		}
		
	}
	
	/**
	 * 根据当前记录的最后更新时间，获取本次采集数据的开始时间和结束时间
	 * 要求：
	 * 1、开始和结束时间不能跨日。
	 * 2、美东时间
	 * 3、开始时间和结束时间应介于最后的更新时间范围内。（如果会跨日，则需要精确计算）
	 * 4、如果最后更新时间不与当前美东时间同一天，则视为重复性采集数据，则应该一次性采集一天的数据。否则，每次开始和结束前后间隔不超过4小时
	 * 
	 * @param max 格式：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	private static String getNextMaxNew(String max) {
		Calendar calendar = Calendar.getInstance();
		
		try {
			//传入时间就是美东时间
			Date maxdate = null;
			calendar.setTime(DateUtil.parse(max, "yyyy-MM-dd HH:mm:ss"));
			maxdate = calendar.getTime();
			String __date_maxdate = max.substring(0, 10).replaceAll("-", "");
			
			//当前时间转换为美东时间
			Date currendate = null;
			calendar.setTime(new Date());
			calendar.add(Calendar.HOUR_OF_DAY, -12);
			currendate = calendar.getTime();
			String currentime = DateUtil.parse(currendate, "yyyy-MM-dd HH:mm:ss");
			String __date_currendate = currentime.substring(0, 10).replaceAll("-", "");
			
			//检查是否跨日，如果是相差一天，则先处理昨天整天的
			if(Integer.valueOf(__date_maxdate) < Integer.valueOf(__date_currendate)) {
				
				
				if(max.split(" ")[1].equals("23:59:59")) {
					max = DateUtil.parse(DateUtil.add(DateUtil.parse(max, "yyyy-MM-dd HH:mm:ss"), Calendar.SECOND, +1), "yyyy-MM-dd HH:mm:ss");//
					String starttime = max.substring(0, 10) + " 00:00:00";
					String endtime   = max.substring(0, 10) + " 23:59:59";
					endtime = doNotMax(endtime, currendate);
					return starttime+";"+endtime;
				} else {
					if( !max.split(" ")[1].equals("00:00:00")) {
						String starttime = max.substring(0, 10) + " 00:00:00";
						String endtime   = max.substring(0, 10) + " 23:59:59";
						endtime = doNotMax(endtime, currendate);
						return starttime+";"+endtime;
					}
				}
				
				
				calendar.setTime(DateUtil.parse(max.substring(0, 10), "yyyy-MM-dd"));
				calendar.add(Calendar.DATE, +1);
				String nextDate = DateUtil.parse(calendar.getTime(), "yyyy-MM-dd");
				String starttime = nextDate + " 00:00:00";
				String endtime   = nextDate + " 23:59:59";
				
				
				//这里还需要检查是否结束时间会超过当前美东时间
				endtime = doNotMax(endtime, currendate);
				
				return starttime+";"+endtime;
			} 
			//不是跨日的情况，每次间隔时间为前后4小时
			else {
				
				//往后推4个小时即开始时间
				calendar.setTime(maxdate);
				calendar.add(Calendar.HOUR_OF_DAY, -2);
				Date startDate = calendar.getTime();
				String starttime = DateUtil.parse(startDate, "yyyy-MM-dd HH:mm:ss");
				//减去2小时后变成跨日了，重置为开始时间
				if( !starttime.substring(0, 10).equals(max.substring(0, 10))) {
					
					starttime = max.substring(0, 10) + " 00:00:00";
				} 
				
				
				//往后推2个小时即结束时间
				calendar.setTime(maxdate);
				calendar.add(Calendar.HOUR_OF_DAY, +2);
				Date endDate = calendar.getTime();
				String endtime = DateUtil.parse(endDate, "yyyy-MM-dd HH:mm:ss");
				//加上2小时后变成跨日了，重置为结束时间
				if( !endtime.substring(0, 10).equals(max.substring(0, 10))) {
					endtime = max.substring(0, 10) + " 23:59:59";
				} else {
					//这里还需要检查是否结束时间会超过当前美东时间
					endtime = doNotMax(endtime, currendate);
				}
				
				return starttime+";"+endtime;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return max+";"+max;
	}
	
	private static String doNotMax(String endtime, Date currendate) {
		//检查是否结束时间会超过当前美东时间
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtil.parse(endtime, "yyyy-MM-dd HH:mm:ss"));
		if(calendar.getTime().getTime() >= currendate.getTime()) {
			calendar.setTime(currendate);
			calendar.add(Calendar.MINUTE, -5);
			endtime = DateUtil.parse(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
		} 
		return endtime;
	}
	
	
	public static void main(String[] args) {
		String starttime = "2017-05-04 05:20:29";
		String endtime = "2017-05-05 15:20:29";
		if( !starttime.split(" ")[0].equals(endtime.split(" ")[0])) {
			endtime = starttime.split(" ")[0]+" 23:59:59";
		}
		System.out.println("starttime="+starttime);
		System.out.println("endtime="+endtime);
	}

}
