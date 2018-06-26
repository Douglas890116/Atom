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
import com.hy.pull.common.util.game.BBINGame;
import com.hy.pull.common.util.game.BBINGame5;
import com.hy.pull.common.util.game.bbin.BBINUtil;
import com.hy.pull.mapper.Bbin5GameinfoMapper;
import com.hy.pull.mapper.DataHandleLogsMapper;
import com.hy.pull.mapper.DataHandleMapper;
import com.hy.pull.mapper.TbProxyKeyMapper;
import com.hy.pull.service.BaseService;


/**
 * 波音游戏服务类
 * 创建日期 2016-12-23
 * @author temdy
 */
@Service
public class BBIN5GameService extends BaseService {
	
	Logger logger = LogManager.getLogger(BBINGameService.class.getName());  
	
	@Autowired
	private Bbin5GameinfoMapper bbinGameinfoMapper;
	
	@Autowired
	private TbProxyKeyMapper tbProxyKeyMapper;
	
	@Autowired
	private DataHandleMapper dataHandleMapper;
	@Autowired
	private DataHandleLogsMapper dataHandleLogsMapper;
	
	private String[] kinds = null;//游戏种类(1：BB体育、3：视讯、5：机率、12：彩票、15：3D厅)
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
		
		
		int count = 0;//累计执行总数量
		//System.out.println("波音5游戏数据拉取开始，参数列表："+entity);
		if(entity==null){//判断是否为空，空则创建一个新的对象
			entity = new HashMap<String,Object>();
		}
		if(!entity.containsKey("GAME_ID")){//判断是否存在游戏编号，没则默认初始化为波音游戏
			entity.put("GAME_ID", "14");//设置所属游戏编号
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
			starttime = DateUtil.parse(DateUtil.add(DateUtil.parse(updatetime, "yyyy-MM-dd HH:mm:ss"), Calendar.MINUTE, this.backMinute), "yyyy-MM-dd HH:mm:ss");//
			endtime = DateUtil.parse(DateUtil.add(DateUtil.parse(starttime, "yyyy-MM-dd HH:mm:ss"), Calendar.MINUTE, 120), "yyyy-MM-dd HH:mm:ss");//
			if(DateUtil.parse(starttime, "yyyy-MM-dd HH:mm:ss").getTime() > new Date().getTime()) {
				//最后时间不能超过当前时间
				endtime = DateUtil.parse(new Date(), "yyyy-MM-dd HH:mm:ss");
			}
			
			
			
			String userKey = null;//密钥
			String gamekind = null;//游戏种类
			String GAME_KIND_ID = null;
			if(entity.get("GAME_KIND_ID") != null){//判断是否存在种类编号
				GAME_KIND_ID = entity.get("GAME_KIND_ID").toString();
				kinds = new String[]{GAME_KIND_ID};
			}else{
				kinds = new String[]{"1","3","5","12","15"};
			}
			String subgamekind = null;//游戏子种类
			String gametype = null;//游戏类型
			int klen = 0;//游戏种类长度
			int tlen = 0;//游戏类型长度
			String[] types = null;//游戏类型列表
			for(int i = 0; i < size; i++){//第一层，所有代理商
				entity = list.get(i);
				apiUrl = entity.get("PROXY_API_URL").toString();
				website = entity.get("PROXY_KEY1").toString();
				uppername = entity.get("PROXY_NAME").toString();
				userKey = entity.get("PROXY_MD5_KEY").toString();
				code = entity.get("PROXY_CODE") == null ? uppername : entity.get("PROXY_CODE").toString();
				klen = kinds.length;
				if(klen > 0){
					for (int j = 0; j < klen; j++){
						gamekind = kinds[j];//获取种类的编码
						if(gamekind.equals("5")){
							//System.out.println("gamekind=5: "+apiUrl+" || "+website+" || "+uppername+" || "+userKey+" || "+rounddate+" || "+starttime+" || "+endtime);
							for(int s = 1; s <= 2; s++){
								subgamekind = String.valueOf(s);//获取子种类的编码
								gametype = null;
								count += insertBBIN(apiUrl, website, username, uppername, gamekind, subgamekind, gametype ,userKey, rounddate, starttime, endtime,code);//拉取数据入库;
							}
						}else if(gamekind.equals("12")){					
							//System.out.println("gamekind=12: "+apiUrl+" || "+website+" || "+uppername+" || "+userKey+" || "+rounddate+" || "+starttime+" || "+endtime);		
							types = BBINUtil.getCPList();//获取波音彩票游戏的类型列表
							tlen = types.length;
							if(tlen > 0){
								for(int t = 0; t < tlen; t++){
									gametype = types[t];//获取游戏类型
									subgamekind = null;
									count += insertBBIN(apiUrl, website, username, uppername, gamekind, subgamekind, gametype ,userKey, rounddate, starttime, endtime,code);//拉取数据入库;
								}
							}
						}else{
							//System.out.println("gamekind=0: "+apiUrl+" || "+website+" || "+uppername+" || "+userKey+" || "+rounddate+" || "+starttime+" || "+endtime);
							gametype = null;
							subgamekind = null;
							count += insertBBIN(apiUrl, website, username, uppername, gamekind, subgamekind, gametype ,userKey, rounddate, starttime, endtime,code);//拉取数据入库;
						}
					}
				}
				
				
			}
			
			dataHandle.put("lastnum", Integer.parseInt(dataHandle.get("lastnum").toString()) + count);
			dataHandle.put("allnum", Integer.parseInt(dataHandle.get("allnum").toString()) + count);
			
			/***************更新配置管理库****************/
			dataHandle.put("updatetime", endtime);
			dataHandle.put("lasttime", DateUtil.parse(new Date(), "yyyyMMddHHmmss"));
			dataHandleMapper.update(dataHandle);
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
		List<Map<String,Object>> data = BBINGame5.getBBINData(apiUrl, website, username, uppername, gamekind, subgamekind, gametype ,userKey, rounddate,starttime,endtime,code);//获取拉取数据列表;
		int len = 0;
		if(data != null){
			len = data.size();
			if(len > 0){//如果有数据就入库
				bbinGameinfoMapper.batchInsert(data);//批量入库
			}
		}
		return len;
	}
}