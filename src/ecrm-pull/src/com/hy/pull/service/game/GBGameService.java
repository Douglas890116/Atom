package com.hy.pull.service.game;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hy.pull.common.util.DateUtil;
import com.hy.pull.common.util.game.GBGame;
import com.hy.pull.common.util.game.IBCGame;
import com.hy.pull.mapper.ApiGbGameInfoMapper;
import com.hy.pull.mapper.DataHandleLogsMapper;
import com.hy.pull.mapper.DataHandleMapper;
import com.hy.pull.mapper.IbcGameinfoMapper;
import com.hy.pull.mapper.TbProxyKeyMapper;
import com.hy.pull.service.BaseService;
import com.hy.pull.service.BaseService.Enum_flag;

/**
 * GB彩票游戏服务类
 * 创建日期 2016-10-13
 * @author temdy
 */
@Service
public class GBGameService extends BaseService {
	
	Logger logger = LogManager.getLogger(GBGameService.class.getName());  
	
	@Autowired
	private TbProxyKeyMapper tbProxyKeyMapper;
	
	@Autowired
	private ApiGbGameInfoMapper ibcGameinfoMapper;
	
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
		Map<String, Object> dataHandle = dataHandleMapper.selectByPrimaryKey(LogUtil.HANDLE_GB);
		dataHandle.put("lastnum", "0");
		String updatetime = dataHandle.get("updatetime").toString();//agent=10;agent=10;
		String[] maxvalue = updatetime.split(";");
		Map<String, String> mapMaxValue = new HashMap<String, String>();
		for (String string : maxvalue) {
			if(string != null && !string.equals("")) {
				mapMaxValue.put(string.split("=")[0], string.split("=")[1]);
			}
		}
		if(mapMaxValue.size() == 0) {
			return 0;
		}
		
		
		//System.out.println(entity);
		int count = 0;//累计执行总数量
		
		if(entity==null){//判断是否为空，空则创建一个新的对象
			entity = new HashMap<String,Object>();
		}
		if(!entity.containsKey("GAME_ID")){//判断是否存在游戏编号，没则默认初始化为沙巴体育游戏
			entity.put("GAME_ID", "22");//设置所属游戏编号				
		}
		//获取沙巴体育游戏的所有代理密钥列表
		List<Map<String,Object>> list = tbProxyKeyMapper.selectByEntity(entity);
		int size = list.size();		
		if(size > 0){
			String apiUrl = null;//接口URL
			String agent = null;//代理名称
			String StartSettleID = null;//上次拉取的最大值
			String MAX_VALUE = null;
			if(entity.get("MAX_VALUE") != null){//判断是否存在最大值
				MAX_VALUE = entity.get("MAX_VALUE").toString();
			}
			String GeneralKey = null;//密钥
			String TPCode = null;//密钥
			String code = null;//代理编码
			int len = 0;
			Map<String,Object> map = new HashMap<String,Object>();
			List<Map<String,Object>> data = null;
			
			boolean flag = true;
			
			
			for(int i = 0; i < size; i++){
				entity = list.get(i);
				apiUrl = entity.get("PROXY_API_URL").toString();
				agent = entity.get("PROXY_NAME").toString();
				TPCode = entity.get("PROXY_KEY1").toString();
				GeneralKey = entity.get("PROXY_KEY2").toString();
				code = entity.get("PROXY_CODE") == null ? agent : entity.get("PROXY_CODE").toString();
				map.put("Platformflag", agent);
				
				StartSettleID = mapMaxValue.get(code);
				
				data = GBGame.getIBCData( apiUrl, TPCode, GeneralKey, StartSettleID, code);//获取拉取数据列表;
				if(data != null){
					len = data.size();
					count += len;
					if(len > 0){//如果有数据就入库
						ibcGameinfoMapper.batchInsert(data);//批量入库
						
						/**************取这批数据的最大值settleid****************/
						long settleid = 0;
						for (Map<String, Object> map2 : data) {
							if(Long.valueOf(map2.get("settleid").toString()) > settleid) {
								settleid = Long.valueOf(map2.get("settleid").toString());
							}
						}
						dataHandle.put("lastnum", Integer.parseInt(dataHandle.get("lastnum").toString()) + len);
						dataHandle.put("allnum", Integer.parseInt(dataHandle.get("allnum").toString()) + len);
						mapMaxValue.put(code, settleid+"");//更新
					}
					//dataHandleLogsMapper.insert(LogUtil.saveLog(LogUtil.HANDLE_GB, null, len+"个", TPCode, Enum_flag.正常.value));
					
				} else {
					//为null表示出现异常
					flag = false;
					dataHandleLogsMapper.insert(LogUtil.saveLog(LogUtil.HANDLE_GB, null, "返回NULL数据", TPCode, Enum_flag.异常.value));
					break;
				}
			}
			
			/***************更新配置管理库****************/
			if(count > 0){
				String value = "";
				for (String key : mapMaxValue.keySet()) {  
					value += (key + "=" +mapMaxValue.get(key)+";");
				}  
				dataHandle.put("updatetime", value);
			}
			if(flag) {
				dataHandle.put("lasttime", DateUtil.parse(new Date(), "yyyyMMddHHmmss"));
				dataHandleMapper.update(dataHandle);
			}
			/***************更新配置管理库****************/
			
		}
		logger.debug("GB彩票游戏数据拉取结束。。。");
		return count;
	}
	
	public static void main(String[] args) {
		System.out.println(DateUtil.parse(new Date(), "yyyyMMddHHmmss"));
	}
}
