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
import com.hy.pull.common.util.game.QWPGame;
import com.hy.pull.mapper.ApiQWPGameInfoMapper;
import com.hy.pull.mapper.DataHandleLogsMapper;
import com.hy.pull.mapper.DataHandleMapper;
import com.hy.pull.mapper.TbProxyKeyMapper;
import com.hy.pull.service.BaseService;

@Service
public class QWPGameService extends BaseService {

	Logger logger = LogManager.getLogger(QWPGameService.class.getName());
	
	@Autowired
	private ApiQWPGameInfoMapper qwpGameInfoMapper;
	@Autowired
	private DataHandleLogsMapper dataHandleLogsMapper;
	@Autowired
	private TbProxyKeyMapper tbProxyKeyMapper;
	@Autowired
	private DataHandleMapper dataHandleMapper;
	
	@Override
	public Integer pullData(Map<String, Object> entity) throws Exception {
		/*--------------------获取上次拉取的最大值--------------------*/
		Map<String, Object> dataHandle = dataHandleMapper.selectByPrimaryKey(LogUtil.HANDLE_QWP);
		dataHandle.put("lastnum", "0");
		String updatetime = dataHandle.get("updatetime").toString();

		int count = 0;
		if (entity == null) entity = new HashMap<String, Object>();

		if (!entity.containsKey("GAME_ID")) entity.put("GAME_ID", 31);

		//获取棋牌游戏的所有代理密钥列表
		List<Map<String,Object>> list = tbProxyKeyMapper.selectByEntity(entity);
		if (list != null && list.size() > 0) {
			String API_URL = null;
			String AGENT_NAME = null;
			String AGENT_PASSWORD = null;
			String START_TIME = null;
			String END_TIME = null;
			
			if (entity.get("start_time") != null) START_TIME = entity.get("start_time").toString();
			if (entity.get("end_time") != null) END_TIME = entity.get("end_time").toString();
			
			int intervalnum = 15;
			intervalnum = Integer.valueOf(dataHandle.get("intervalnum").toString());
			
			START_TIME = DateUtil.parse(DateUtil.add(DateUtil.parse(updatetime, "yyyy-MM-dd HH:mm:ss"), Calendar.MINUTE, this.backMinute), "yyyy-MM-dd HH:mm:ss");
			END_TIME = DateUtil.parse(DateUtil.add(DateUtil.parse(START_TIME, "yyyy-MM-dd HH:mm:ss"), Calendar.MINUTE, intervalnum), "yyyy-MM-dd HH:mm:ss");
			//最后时间不能超过当前时间
			if(DateUtil.parse(END_TIME, "yyyy-MM-dd HH:mm:ss").getTime() > new Date().getTime()) {
				END_TIME = DateUtil.parse(new Date(), "yyyy-MM-dd HH:mm:ss");
			}
			
			boolean flag = true;
			int len = 0;
			List<Map<String, Object>> data = null;
			for (Map<String, Object> map : list) {
				API_URL = map.get("PROXY_API_URL").toString();
				AGENT_NAME = map.get("PROXY_NAME").toString();
				AGENT_PASSWORD = map.get("PROXY_KEY1").toString();
				
				QWPGame api = new QWPGame(API_URL, AGENT_NAME, AGENT_PASSWORD);
				data = api.getRecord(START_TIME, END_TIME, 1, 1000);
				if (data != null ) {

					if(data.size() > 0) {
						len = data.size();
						qwpGameInfoMapper.batchInsert(data);
						count += len;
						dataHandle.put("lastnum", Integer.parseInt(dataHandle.get("lastnum").toString()) + data.size());
						dataHandle.put("allnum", Integer.parseInt(dataHandle.get("allnum").toString()) + data.size());
					}
					
				} else {
					flag = false;
					dataHandleLogsMapper.insert(LogUtil.saveLog(LogUtil.HANDLE_QWP, null, "返回NULL数据", AGENT_NAME, Enum_flag.异常.value));
					break;
				}
			}
			
			/*--------------------更新配置管理库--------------------*/
			if(flag) {
				dataHandle.put("updatetime", END_TIME);
				dataHandle.put("lasttime", DateUtil.parse(new Date(), "yyyyMMddHHmmss"));
				dataHandleMapper.update(dataHandle);
			}
			/*--------------------更新配置管理库--------------------*/
		}
		return count;
	}
}