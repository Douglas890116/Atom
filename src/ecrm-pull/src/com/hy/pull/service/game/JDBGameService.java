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
import com.hy.pull.common.util.game.JDBGame;
import com.hy.pull.mapper.ApiJDBGameInfoMapper;
import com.hy.pull.mapper.DataHandleLogsMapper;
import com.hy.pull.mapper.DataHandleMapper;
import com.hy.pull.mapper.ApiJdbFileMapper;
import com.hy.pull.mapper.ApiJdbFtpMapper;
import com.hy.pull.mapper.TbProxyKeyMapper;
import com.hy.pull.service.BaseService;

@Service
public class JDBGameService extends BaseService {

	Logger logger = LogManager.getLogger(JDBGameService.class.getName());

	@Autowired
	private ApiJDBGameInfoMapper jdbGameInfoMapper;
	@Autowired
	private DataHandleLogsMapper dataHandleLogsMapper;
	@Autowired
	private TbProxyKeyMapper tbProxyKeyMapper;
	@Autowired
	private DataHandleMapper dataHandleMapper;
	@Autowired
	private ApiJdbFileMapper jdbFileMapper;
	@Autowired
	private ApiJdbFtpMapper jdbFtpConfigMapper;

	@Override
	public Integer pullData(Map<String, Object> entity) throws Exception {
		/*--------------------获取上次拉取的最大值--------------------*/
		Map<String, Object> dataHandle = dataHandleMapper.selectByPrimaryKey(LogUtil.HANDLE_JDB);
		dataHandle.put("lastnum", "0");
		String updatetime = dataHandle.get("updatetime").toString();

		int count = 0;
		if (entity == null) entity = new HashMap<String, Object>();

		if (!entity.containsKey("GAME_ID")) entity.put("GAME_ID", 32);

		//获取棋牌游戏的所有代理密钥列表
		List<Map<String,Object>> list = tbProxyKeyMapper.selectByEntity(entity);
		if (list != null && list.size() > 0) {
			String API_URL = null;
			String PARENT = null;
			String DC = null;
			String KEY = null;
			String IV = null;
			
			String START_TIME = null;
			String END_TIME = null;
			
			if (entity.get("start_time") != null) START_TIME = entity.get("start_time").toString();
			if (entity.get("end_time") != null) END_TIME = entity.get("end_time").toString();
			
			int intervalnum = 15;
			intervalnum = Integer.valueOf(dataHandle.get("intervalnum").toString());
			
			//后退10分钟，最大间隔为15分钟
			START_TIME = DateUtil.parse(DateUtil.add(DateUtil.parse(updatetime, "yyyy-MM-dd HH:mm:ss"), Calendar.MINUTE, -10), "yyyy-MM-dd HH:mm:ss");
			END_TIME = DateUtil.parse(DateUtil.add(DateUtil.parse(START_TIME, "yyyy-MM-dd HH:mm:ss"), Calendar.MINUTE, intervalnum), "yyyy-MM-dd HH:mm:ss");
			//最后时间不能超过当前时间
			//JDB不能超过当前时间3分钟
			if(DateUtil.parse(END_TIME, "yyyy-MM-dd HH:mm:ss").getTime() > DateUtil.add(new Date(), Calendar.MINUTE, -5).getTime()) {
				START_TIME = DateUtil.parse(DateUtil.add(new Date(), Calendar.MINUTE, -15), "yyyy-MM-dd HH:mm:ss");
				END_TIME = DateUtil.parse(DateUtil.add(new Date(), Calendar.MINUTE, -5), "yyyy-MM-dd HH:mm:ss");
			}
			
			boolean flag = true;
			int len = 0;
			List<Map<String, Object>> data = null;
			for (Map<String, Object> map : list) {
				API_URL = map.get("PROXY_API_URL").toString();
				PARENT = map.get("PROXY_NAME").toString();
				DC = map.get("PROXY_MD5_KEY").toString();
				KEY = map.get("PROXY_KEY1").toString();
				IV = map.get("PROXY_KEY2").toString();
				
				JDBGame api = new JDBGame(API_URL, PARENT, DC, KEY, IV);
				
				//dd-MM-yyyy HH:mm
				data = api.getRecord(
						DateUtil.parse(DateUtil.parse(START_TIME, "yyyy-MM-dd HH:mm:ss"), "dd-MM-yyyy HH:mm:00"), 
						DateUtil.parse(DateUtil.parse(END_TIME, "yyyy-MM-dd HH:mm:ss"), "dd-MM-yyyy HH:mm:00"));
				//注意：为空时表示出现异常，时间不推进
				if (data != null) {
					if(data.size() > 0) {
						len = jdbGameInfoMapper.batchInsert(data);
						count += len;
						dataHandle.put("lastnum", Integer.parseInt(dataHandle.get("lastnum").toString()) + len);
						dataHandle.put("allnum", Integer.parseInt(dataHandle.get("allnum").toString()) + len);
					}
				} else {
					flag = false;
					dataHandleLogsMapper.insert(LogUtil.saveLog(LogUtil.HANDLE_JDB, null, "返回NULL数据", PARENT, Enum_flag.异常.value));
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
	/**
	 * 从ftp服务器下载数据文件
	 */
	public int downloadFtpFile() {
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("status", 1);
		List<Map<String,Object>> list = jdbFtpConfigMapper.selectByEntity(entity);
		int count = 0;
		if (list != null && list.size() > 0) {
			String FTP_NAME = null;
			String FTP_PSWD = null;
			String FTP_IP   = null;
			Integer FTP_PORT= null;
			String DATE_FOLDER = null;
			String LOCAL_FOLDER = null;
			
			String targetFolder = null;
			
			List<Map<String, Object>> listData = null;
			for (Map<String, Object> map : list) {
				FTP_NAME = (String) map.get("ftpname");
				FTP_PSWD = (String) map.get("ftppswd");
				FTP_IP =   (String) map.get("ftpip");
				FTP_PORT = (Integer) map.get("ftpport");
				DATE_FOLDER = (String) map.get("datefolder");
				LOCAL_FOLDER = (String) map.get("localfolder");
				if (LOCAL_FOLDER.endsWith("/") || LOCAL_FOLDER.endsWith("\\")) {
					LOCAL_FOLDER = LOCAL_FOLDER.substring(0, LOCAL_FOLDER.length()-1);
				}
				// 将上次的下载目录日期+1变为本次要下载的目录
				targetFolder = DateUtil.parse(DateUtil.add(DateUtil.parse(DATE_FOLDER, "yyyyMMdd"), Calendar.DATE, 1), "yyyyMMdd");
				
				// 如果将要下载的日期大于今天，则下载今天的文件
				if (new Date().before(DateUtil.parse(targetFolder, "yyyyMMdd")))
					targetFolder = DATE_FOLDER;
				
				String localFolder = LOCAL_FOLDER + "/" + FTP_NAME;
				JDBGame api = new JDBGame();
				listData = api.downloadFtpZips(FTP_IP, FTP_PORT, FTP_NAME, FTP_PSWD, targetFolder, localFolder);
				if (listData != null) {
					if (listData.size() > 0) {
						count += jdbFileMapper.batchInsert(listData);
					}
					map.put("updatetime", DateUtil.parse(new Date(), "yyyy-MM-dd HH:mm:ss"));
					map.put("datefolder", targetFolder);
					map.put("lastnum", count);
					map.put("totalnum", Integer.parseInt(map.get("totalnum").toString()) + count);
					// 当下载的文件目录 与 结束下载的目录 一致，则结束停止下载，将状态status 设置为0
					if (map.get("endfolder")!=null && map.get("endfolder").equals(targetFolder))
						map.put("status", 0);
					jdbFtpConfigMapper.update(map);
				} else {
					dataHandleLogsMapper.insert(LogUtil.saveLog(LogUtil.HANDLE_JDB, null, "下载FTP数据文件异常", FTP_NAME, Enum_flag.异常.value));
				}
			}
		}
		return count;
	}
	/**
	 * 读取本地数据插入数据库
	 * @param size 读取的文件数量
	 * @return
	 */
	public int getLocalRecord(int size) {
		int count = 0;
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("status", 0);
		entity.put("limit", size);

		List<Map<String, Object>> mapList = jdbFileMapper.selectByEntity(entity);
		if (mapList != null && mapList.size() > 0) {

			List<String> fileList = new ArrayList<String>();
			for (Map<String, Object> map : mapList) {
				fileList.add(map.get("file").toString());
				map.put("status", 1);
			}
			JDBGame api = new JDBGame();
			List<Map<String, Object>> record = api.fileAnalysis(fileList);
			boolean flag = true;
			// 为空说明解析出错
			if (record != null) {
				if (record.size() > 0) {
					count += jdbGameInfoMapper.batchInsert(record);
				}
			} else {
				flag = false;
				dataHandleLogsMapper.insert(LogUtil.saveLog(LogUtil.HANDLE_JDB, null, "解析本地数据文件异常", "", Enum_flag.异常.value));
			}
			// 如果解析数据没有出错，则更新文件状态
			if (flag) {
				jdbFileMapper.batchUpdate(mapList);
			}
		}
		return count;
	}
}