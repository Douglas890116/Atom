package com.hy.pull.service.game;

import java.util.ArrayList;
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
import com.hy.pull.common.util.game.ZJGame;
import com.hy.pull.mapper.DataHandleLogsMapper;
import com.hy.pull.mapper.DataHandleMapper;
import com.hy.pull.mapper.TbProxyKeyMapper;
import com.hy.pull.mapper.ZjGameinfoMapper;
import com.hy.pull.service.BaseService;

/**
 * ZJ游戏服务类
 * 创建日期 2016-10-18
 * @author temdy
 */
@Service
public class ZJGameService extends BaseService {

	
	Logger logger = LogManager.getLogger(PTGameService.class.getName());  
	
	@Autowired
	private ZjGameinfoMapper zjGameinfoMapper;
	
	@Autowired
	private TbProxyKeyMapper tbProxyKeyMapper;
	
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
		Map<String, Object> dataHandle = dataHandleMapper.selectByPrimaryKey(LogUtil.HANDLE_ZJ);
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
		
		
		int count = 0;//累计执行总数量
		//System.out.println("ZJ游戏数据拉取开始，参数列表："+entity);
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		if(entity==null){//判断是否为空，空则创建一个新的对象
			entity = new HashMap<String,Object>();
		}
		if(!entity.containsKey("GAME_ID")){//判断是否存在游戏编号，没则默认初始化为ZJ游戏
			entity.put("GAME_ID", "9");//设置所属游戏编号				
		}
		//获取ZJ游戏的所有代理密钥列表
		List<Map<String,Object>> list = tbProxyKeyMapper.selectByEntity(entity);
		int size = list.size();		
		if(size > 0){
			String apiUrl = null;//接口URL
			String agent = null;//代理名称
			String userKey = null;//开始日期
			String startId = null;//最大id
			String code = null;//代理编码
			if(entity.get("MAX_VALUE") != null){//判断是否存在最大值
				startId = entity.get("MAX_VALUE").toString();
			}
			int len = 0;

			Map<String,Object> map = new HashMap<String,Object>();
			for(int i = 0; i < size; i++){
				entity = list.get(i);
				apiUrl = entity.get("PROXY_API_URL").toString();
				agent = entity.get("PROXY_NAME").toString();
				userKey = entity.get("PROXY_MD5_KEY").toString();
				code = entity.get("PROXY_CODE") == null ? agent : entity.get("PROXY_CODE").toString();

				
				startId = mapMaxValue.get(code);
				
				
				if(startId == null){//如果为空则获取数据库最大值
					map.put("Platformflag", agent);
					startId = zjGameinfoMapper.max(map);
					if(startId == null){
						map.put("Platformflag", null);
						startId = zjGameinfoMapper.max(map);
					}
				}
				data = ZJGame.getZJData(apiUrl, agent,userKey,startId,code);//获取拉取数据列表;
				if(data != null){
					len = data.size();
					count += len;//累计执行结果
					if(len > 0){//如果有数据就入库
						zjGameinfoMapper.batchInsert(data);//批量入库
						
						/**************取这批数据的最大值ibc_vendorid****************/
						long id = 0;
						for (Map<String, Object> map2 : data) {
							if(Long.valueOf(map2.get("id").toString()) > id) {
								id = Long.valueOf(map2.get("id").toString());
							}
						}
						dataHandle.put("lastnum", Integer.parseInt(dataHandle.get("lastnum").toString()) + len);
						dataHandle.put("allnum", Integer.parseInt(dataHandle.get("allnum").toString()) + len);
						mapMaxValue.put(code, id+"");//更新
					}
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
			dataHandle.put("lasttime", DateUtil.parse(new Date(), "yyyyMMddHHmmss"));
			dataHandleMapper.update(dataHandle);
			/***************更新配置管理库****************/
		}
		logger.debug("ZJ游戏数据拉取结束。。。");
		return count;
	}

}
