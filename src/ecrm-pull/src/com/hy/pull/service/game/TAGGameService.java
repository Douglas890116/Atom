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
import com.hy.pull.common.util.FtpUtils;
import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.common.util.game.TAGGame;
import com.hy.pull.mapper.DataHandleLogsMapper;
import com.hy.pull.mapper.DataHandleMapper;
import com.hy.pull.mapper.TagGameinfoMapper;
import com.hy.pull.mapper.TbGameKindMapper;
import com.hy.pull.mapper.TbMaxLogMapper;
import com.hy.pull.mapper.TbProxyKeyMapper;
import com.hy.pull.service.BaseService;
import com.hy.pull.service.BaseService.Enum_flag;

/**
 * TAG游戏服务类
 * 创建日期 2016-10-13
 * @author temdy
 */
@Service
public class TAGGameService extends BaseService {

	
	Logger logger = LogManager.getLogger(TAGGameService.class.getName());  
	
	@Autowired
	private TbProxyKeyMapper tbProxyKeyMapper;
	
	@Autowired
	private TbGameKindMapper tbGameKindMapper;
	
	@Autowired
	private TbMaxLogMapper tbMaxLogMapper;
	
	@Autowired
	private TagGameinfoMapper tagGameinfoMapper;
	
	@Autowired
	private DataHandleMapper dataHandleMapper;
	@Autowired
	private DataHandleLogsMapper dataHandleLogsMapper;
	
	
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");// 设置日期格式
	/**
	 * 按条件拉取数据的方法
	 * @param entity 条件集合 {GAME_ID:游戏编号,PROXY_ID:代理编号,MAX_VALUE:上次拉取的最大值,GAME_KIND_ID:游戏种类编号}
	 * @return 数据行数
	 */
	@Override
	public Integer pullData(Map<String, Object> entity) throws Exception {
		
		Calendar calendar = Calendar.getInstance();
		
		/**********************获取上次拉取的最大值***********************/
		Map<String, Object> dataHandle = dataHandleMapper.selectByPrimaryKey(LogUtil.HANDLE_TAG);
		dataHandle.put("lastnum", "0");
		String updatetime = dataHandle.get("updatetime").toString();
		
		
		
		int count = 0;//累计执行总数量
		//System.out.println("TAG游戏数据拉取开始，参数列表："+entity);
		if(entity==null){//判断是否为空，空则创建一个新的对象
			entity = new HashMap<String,Object>();
		}
		if(!entity.containsKey("GAME_ID")){//判断是否存在游戏编号，没则默认初始化为TAG游戏
			entity.put("GAME_ID", "6");//设置所属游戏编号
		}//获取TAG游戏的所有代理密钥列表
		List<Map<String,Object>> list = tbProxyKeyMapper.selectByEntity(entity);
		int size = list.size();
		if(size > 0){
			String ip = null;//ftp服务器IP
			String agent = null;//代理名称
			String max = null;//上次拉取的最大值
			String code = null;//代理编码
			if(entity.get("MAX_VALUE") != null){//判断是否存在最大值
				max = entity.get("MAX_VALUE").toString();
			}
			
			
			//间隔分钟数（不得超过30）
			int intervalnum = 30;
			intervalnum = Integer.valueOf(dataHandle.get("intervalnum").toString());
			
			
			
			/***********************设定开始结束时间，每次只拉取25分钟内的数据*****************************/
			max = DateUtil.parse(DateUtil.add(DateUtil.parse(updatetime, "yyyyMMddHHmm"), Calendar.MINUTE, this.backMinute), "yyyyMMddHHmm");//
			String enddate = DateUtil.parse(DateUtil.add(DateUtil.parse(max, "yyyyMMddHHmm"), Calendar.MINUTE, intervalnum), "yyyyMMddHHmm");//
			if(DateUtil.parse(enddate, "yyyyMMddHHmm").getTime() > new Date().getTime()) {
				//最后时间不能超过当前时间
				enddate = DateUtil.parse(new Date(), "yyyyMMddHHmm");
			}
			
			//转换成美东时间
			Date date1 = sdf.parse(max);
			calendar.setTime(date1);
			calendar.add(Calendar.HOUR_OF_DAY, -12);
			max = sdf.format(calendar.getTime());//
			
			
			
			String userName = null;//用户名
			String password = null;//密码
			String dir = null;//数据文件目录
			String GAME_KIND_ID = null;//游戏种类编号
			if(entity.get("GAME_KIND_ID") != null){//判断是否存在游戏种类
				GAME_KIND_ID = entity.get("GAME_KIND_ID").toString();
			}
			int port = 21;//端口号
			int len = 0;
			int klen = 0;
			int mlen = 0;
			Map<String,Object> map = new HashMap<String,Object>();
			Map<String,Object> kindMap = null;
			List<Map<String,Object>> data = null;
			List<Map<String,Object>> kinds = null;
			List<Map<String,Object>> maxs = null;
			
			boolean flag = true;
			
			
			for(int i = 0; i < size; i++){
				entity = list.get(i);
				password = entity.get("PROXY_API_URL").toString();
				ip = entity.get("PROXY_KEY2").toString();
				agent = entity.get("PROXY_NAME").toString();
				code = entity.get("PROXY_CODE") == null ? agent : entity.get("PROXY_CODE").toString();
				userName = entity.get("PROXY_MD5_KEY").toString();
				if(entity.get("PROXY_KEY1")!=null){
					port = Integer.parseInt(entity.get("PROXY_KEY1").toString());
				}
				
				FtpUtils ftp = new FtpUtils(ip,port,userName,password,"/");
				
				map.put("GAME_ID", "6");//设置所属游戏编号
				if(GAME_KIND_ID != null){
					map.put("GAME_KIND_ID", GAME_KIND_ID);//设置游戏种类
				}
				kinds = tbGameKindMapper.selectByEntity(map);//获取游戏种类列表
				klen = kinds.size();
				if(klen > 0){
					for(int j = 0; j < klen; j++){
						kindMap = kinds.get(j);//获取游戏种类对象
						dir = kindMap.get("GAME_KIND_NO").toString();//获取游戏数据目录
						data = TAGGame.getTAGData(agent, dir, max, ftp,code);//获取拉取数据列表;
						if(data != null){
							len = data.size();
							count += len;
							if(len > 0){//如果有数据就入库
								tagGameinfoMapper.batchInsert(data);//批量入库
								
								dataHandle.put("lastnum", Integer.parseInt(dataHandle.get("lastnum").toString()) + len);
								dataHandle.put("allnum", Integer.parseInt(dataHandle.get("allnum").toString()) + len);
							}
							
							//dataHandleLogsMapper.insert(LogUtil.saveLog(LogUtil.HANDLE_TAG, null, len+"个", agent, Enum_flag.正常.value));
							
							
						} else {
							//为null表示出现异常
							flag = false;
							
							dataHandleLogsMapper.insert(LogUtil.saveLog(LogUtil.HANDLE_TAG, null, "返回NULL数据", agent, Enum_flag.异常.value));
							
							
							break;
						}
					}
				}
				
				ftp.closeServer();
				
			}
			
			
			/***************更新配置管理库****************/
			if(flag) {
				dataHandle.put("updatetime", enddate);
				dataHandle.put("lasttime", DateUtil.parse(new Date(), "yyyyMMddHHmmss"));
				dataHandleMapper.update(dataHandle);
			}
			/***************更新配置管理库****************/
			
			
		}
		return count;
	}
}
