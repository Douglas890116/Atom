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
import com.hy.pull.common.util.game.IBCGame;
import com.hy.pull.mapper.DataHandleLogsMapper;
import com.hy.pull.mapper.DataHandleMapper;
import com.hy.pull.mapper.IbcGameinfoMapper;
import com.hy.pull.mapper.TbProxyKeyMapper;
import com.hy.pull.service.BaseService;

/**
 * 沙巴体育游戏服务类
 * 创建日期 2016-10-13
 * @author temdy
 */
@Service
public class IBCGameService extends BaseService {
	
	Logger logger = LogManager.getLogger(IBCGameService.class.getName());  
	
	@Autowired
	private TbProxyKeyMapper tbProxyKeyMapper;
	
	@Autowired
	private IbcGameinfoMapper ibcGameinfoMapper;
	
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
		Map<String, Object> dataHandle = dataHandleMapper.selectByPrimaryKey(LogUtil.HANDLE_IBC);
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
		logger.debug("沙巴体育游戏数据拉取开始，参数列表："+entity);
		if(entity==null){//判断是否为空，空则创建一个新的对象
			entity = new HashMap<String,Object>();
		}
		if(!entity.containsKey("GAME_ID")){//判断是否存在游戏编号，没则默认初始化为沙巴体育游戏
			entity.put("GAME_ID", "2");//设置所属游戏编号				
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
			String userKey = null;//密钥
			String code = null;//代理编码
			int len = 0;
			Map<String,Object> map = new HashMap<String,Object>();
			List<Map<String,Object>> data = null;
			for(int i = 0; i < size; i++){
				entity = list.get(i);
				apiUrl = entity.get("PROXY_API_URL").toString();
				agent = entity.get("PROXY_NAME").toString();
				userKey = entity.get("PROXY_MD5_KEY").toString();
				code = entity.get("PROXY_CODE") == null ? agent : entity.get("PROXY_CODE").toString();
				map.put("Platformflag", agent);
				
				vendorid = mapMaxValue.get(code);
				MAX_VALUE = mapMaxValue.get(code);
				
				
				if(MAX_VALUE == null){//如果为空则获取数据库最大值
					vendorid = ibcGameinfoMapper.max(map);
					if(vendorid == null){
						//map.put("Platformflag", null);
						vendorid = "0";
					}
				}
				data = IBCGame.getIBCData(apiUrl, agent, vendorid, userKey,code);//获取拉取数据列表;
				if(data != null){
					len = data.size();
					count += len;
					if(len > 0){//如果有数据就入库
						ibcGameinfoMapper.batchInsert(data);//批量入库
						
						/**************取这批数据的最大值ibc_vendorid****************/
						long ibc_vendorid = 0;
						for (Map<String, Object> map2 : data) {
							if(Long.valueOf(map2.get("ibc_vendorid").toString()) > ibc_vendorid) {
								ibc_vendorid = Long.valueOf(map2.get("ibc_vendorid").toString());
							}
						}
						dataHandle.put("lastnum", Integer.parseInt(dataHandle.get("lastnum").toString()) + len);
						dataHandle.put("allnum", Integer.parseInt(dataHandle.get("allnum").toString()) + len);
						mapMaxValue.put(code, ibc_vendorid+"");//更新
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
		logger.debug("沙巴体育游戏数据拉取结束。。。");
		return count;
	}
	
	public static void main(String[] args) {
		System.out.println(DateUtil.parse(new Date(), "yyyyMMddHHmmss"));
	}
}
