package com.hy.pull.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hy.pull.mapper.DataHandleLogsMapper;
import com.hy.pull.mapper.DataHandleMaintenanceMapper;
import com.hy.pull.mapper.DataHandleMapper;
import com.hy.pull.service.BaseService.Enum_flag;
import com.hy.pull.service.game.AOIGameService;
import com.hy.pull.service.game.AVGameService;
import com.hy.pull.service.game.BBIN5GameService;
import com.hy.pull.service.game.BBINGameService;
import com.hy.pull.service.game.DZDYGameService;
import com.hy.pull.service.game.EBETGameService;
import com.hy.pull.service.game.EIBCGameService;
import com.hy.pull.service.game.GBGameService;
import com.hy.pull.service.game.GGGameService;
import com.hy.pull.service.game.GGPGameService;
import com.hy.pull.service.game.HABGameService;
import com.hy.pull.service.game.IBCGameService;
import com.hy.pull.service.game.IDNGameService2;
import com.hy.pull.service.game.IMGameService;
import com.hy.pull.service.game.JDBGameService;
import com.hy.pull.service.game.LogUtil;
import com.hy.pull.service.game.M88GameService;
import com.hy.pull.service.game.MGGameService;
import com.hy.pull.service.game.NHQGameService;
import com.hy.pull.service.game.PNGGameService;
import com.hy.pull.service.game.PTGameService;
import com.hy.pull.service.game.QPGameService;
import com.hy.pull.service.game.QTGameService;
import com.hy.pull.service.game.QWPGameService;
import com.hy.pull.service.game.SAGameService;
import com.hy.pull.service.game.SGSGameService;
import com.hy.pull.service.game.TAGGameService;
import com.hy.pull.service.game.TAGGameService2;
import com.hy.pull.service.game.TGPGameService;
import com.hy.pull.service.game.TTGGameService;
import com.hy.pull.service.game.WIN88GameService;
import com.hy.pull.service.game.XCPGameService;
import com.hy.pull.service.game.YoPLAYGameService;
import com.hy.pull.service.game.ZJGameService;

/**
 * 定时拉取游戏数据的服务类 创建日期 2016-10-08
 * 
 * @author temdy
 *
 */
@Component
public class PullDataService {

	Logger logger = LogManager.getLogger(PullDataService.class.getName());
	
	@Autowired
	private DataHandleMapper dataHandleMapper;

	@Autowired
	private AOIGameService aOIGameService;
	@Autowired
	private IBCGameService iBCGameService;
	@Autowired
	private NHQGameService nHQGameService;
	@Autowired
	private SAGameService sAGameService;
	@Autowired
	private TAGGameService tAGGameService;
	@Autowired
	private TAGGameService2 tAGGameService2;//新版
	@Autowired
	private BBINGameService bBINGameService;
	@Autowired
	private AVGameService aVGameService;
	@Autowired
	private MGGameService mGGameService;
	@Autowired
	private PTGameService pTGameService;
	@Autowired
	private XCPGameService xCPGameService;
	@Autowired
	private ZJGameService zJGameService;
	@Autowired
	private TTGGameService tTGGameService;
	@Autowired
	private BBIN5GameService bBIN5GameService;
	@Autowired
	private QPGameService qPGameService;
	@Autowired
	private PNGGameService pNGGameService;
	@Autowired
	private DZDYGameService dzdyGameService;
	@Autowired
	private GGGameService ggGameService;
	@Autowired
	private SGSGameService sGSGameService;
//	@Autowired
//	private IDNGameService idnGameService;
	@Autowired
	private IDNGameService2 idnGameService2;
	@Autowired
	private M88GameService m88GameService;
	@Autowired
	private HABGameService habaGameService;
	@Autowired
	private QTGameService qtGameService;
	@Autowired
	private TGPGameService tgpGameService;
	@Autowired
	private WIN88GameService win88GameService;
	@Autowired
	private GBGameService gbGameService;
	@Autowired
	private EBETGameService ebetGameService;
	@Autowired
	private GGPGameService ggpGameService;
	@Autowired
	private EIBCGameService eibcGameService;
	@Autowired
	private YoPLAYGameService yoPLAYGameService;
	@Autowired
	private DataHandleLogsMapper dataHandleLogsMapper;
	@Autowired
	private DataHandleMaintenanceMapper dataHandleMaintenanceMapper;
	@Autowired
	private IMGameService imGameService;
	@Autowired
	private QWPGameService qwpGameService;
	@Autowired
	private JDBGameService jdbGameService;
	
	private static Map<String, String> lastDataCount = new HashMap<String, String>();

	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
	
	/**
	 * 定时任务处理结果通知
	 */
	@Scheduled(cron = "0 0/5 * * * ? ")
	public void jobNoti() {
		
		logger.error("======本阶段拉取数据情况：Num="+lastDataCount.size()+"====="+lastDataCount.toString());
		
//		MailUtil.send( "数据定时采集通知", "", buffer.toString());
//		logger.info("已发送邮件通知");
		
		//批量插入日志
		if(LogUtil.listDatalLog != null && LogUtil.listDatalLog.size() > 0 ) {
			dataHandleLogsMapper.batchInsert(LogUtil.listDatalLog);
			int count = LogUtil.listDatalLog.size();
			logger.info("=====================================已批量插入"+count);
			LogUtil.listDatalLog.clear();//清空
//			MailUtil.send( "数据定时采集通知", "", count+"个采集异常日志。");
		} else {
			logger.info("=====================================没有需要批量插入的日志");
		}
		
	}
	/**
	 * 定时游戏数据任务
	 */
	@Scheduled(cron = "0 0/5 * * * ? ")
	public void jobBBIN() {

		logger.error("===================================="+sdf.format(new Date())+"开始同步BBIN数据====================================================");
		int count = 0;
		try {
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("BBINGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_BBIN, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = bBINGameService.pullData(null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_BBIN, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("BBIN", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步BBIN数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步BBIN数据====================================================");
	}
	/**
	 * 定时游戏数据任务
	 */
	@Scheduled(cron = "0 0/5 * * * ? ")
	public void jobSGS() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步SGS数据====================================================");
		Integer count = 0;
		try {
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("SGSGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_SGS, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = sGSGameService.pullData(null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_SGS, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("SGS", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步SGS数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步SGS数据====================================================");
	}
	/**
	 * 定时游戏数据任务
	 */
	@Scheduled(cron = "0 0/5 * * * ? ")
	public void jobEIBC() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步eIBC数据====================================================");
		Integer count = 0;
		try {
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("eIBCGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_EIBC, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = eibcGameService.pullData(null);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_EIBC, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("EIBC", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步eIBC数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步eIBC数据====================================================");
	}
	/**
	 * 定时游戏数据任务
	 * 
	 * 每日补单第一次（凌晨00：50分对昨日的数据全部重新采集）
	 */
	@Scheduled(cron = "0 50 0 * * ?")
	public void jobEIBCPatch() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步eIBC补单数据====================================================");
		Integer count = 0;
		try {
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("eIBCGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_EIBC, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = eibcGameService.pullDataPatch(null);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_EIBC, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("EIBC", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步eIBC补单数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步eIBC补单数据====================================================");
	}
	/**
	 * 定时游戏数据任务
	 * 
	 * 每隔4小时对状态为等待和运行中的注单进行状态更新
	 */
	@Scheduled(fixedDelay = (1000*60*60*1) )
	public void jobEIBCPatchStatus() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步eIBC旧状态补单数据====================================================");
		Integer count = 0;
		try {
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("eIBCGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_EIBC, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = eibcGameService.pullDataPatchStatus(null);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_EIBC, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("EIBC", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步eIBC旧状态补单数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步eIBC旧状态补单数据====================================================");
	}
	
	
	/**
	 * 定时游戏数据任务
	 */
	@Scheduled(cron = "0 0/2 * * * ? ")
	public void jobIM() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步IM数据====================================================");
		Integer count = 0;
		try {
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("IMGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_IM, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = imGameService.pullData(null);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_IM, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("IM", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步IM数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步IM数据====================================================");
	}
	/**
	 * 定时游戏数据任务
	 * 
	 * 每隔4小时对状态为等待和运行中的注单进行状态更新
	 */
	@Scheduled(fixedDelay = (1000*60*60*1) )
	public void jobIMPatchStatus() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步IM旧状态补单数据====================================================");
		Integer count = 0;
		try {
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("IMGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_IM, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = imGameService.pullDataPatchStatus(null);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_IM, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("IM", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步IM旧状态补单数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步IM旧状态补单数据====================================================");
	}
	
	
	
	
	/**
	 * 定时游戏数据任务（凌晨0点5分:0 5 0 * * ?）
	 * 0 30 0 * * ?
	 * @Scheduled(cron = "0 0/50 * * * ?")
	 */
//	@Scheduled(cron = "0 45 8 * * ?")暂停采集。API接口已经废弃
	public void jobGGPoker() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步GGPoker数据====================================================");
		Integer count = 0;
		try {
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("GGPGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_GGPoker, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = ggpGameService.pullData(null);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_GGPoker, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("GGPoker", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步GGPoker数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步GGPoker数据====================================================");
	}
	/**
	 * 定时游戏数据任务
	 */
	@Scheduled(cron = "0 0/5 * * * ? ")
	public void jobEBET() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步eBET数据====================================================");
		Integer count = 0;
		try {
			
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("EBETGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_EBET, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = ebetGameService.pullData(null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_EBET, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("eBET", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步eBET数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步eBET数据====================================================");
	}
	/**
	 * 定时游戏数据任务
	 * 
	 * 每日补单第一次（凌晨00：15分对昨日的数据全部重新采集）
	 */
	@Scheduled(cron = "0 15 0 * * ?")
	public void jobEBETPatch() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步eBET补单数据====================================================");
		Integer count = 0;
		try {
			
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("EBETGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_EBET, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = ebetGameService.pullDataPatch(null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_EBET, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("eBET", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步eBET补单数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步eBET补单数据====================================================");
	}
	/**
	 * 定时游戏数据任务
	 * 
	 * 每日补单第二次（凌晨00：20分对昨日的数据全部重新采集）
	 */
	@Scheduled(cron = "0 20 0 * * ?")
	public void jobEBETPatch2() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步eBET补单数据====================================================");
		Integer count = 0;
		try {
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("EBETGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_EBET, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = ebetGameService.pullDataPatch(null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_EBET, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("eBET", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步eBET补单数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步eBET补单数据====================================================");
	}
	
	
	/**
	 * 定时游戏数据任务
	 */
	@Scheduled(cron = "0 0/5 * * * ? ")
	public void jobGB() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步GB彩票数据====================================================");
		Integer count = 0;
		try {
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("GBGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_GB, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = gbGameService.pullData(null);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_GB, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("GB彩票", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步GB彩票数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步GB彩票数据====================================================");
	}
	/**
	 * 定时游戏数据任务
	 */
	@Scheduled(cron = "0 0/2 * * * ? ")
	public void jobWIN88() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步WIN88数据====================================================");
		Integer count = 0;
		try {
			
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("W88Game");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_WIN88, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = win88GameService.pullData(null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_WIN88, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("WIN88", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步WIN88数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步WIN88数据====================================================");
	}
	/**
	 * 定时游戏数据任务
	 */
	@Scheduled(cron = "0 0/5 * * * ? ")
	public void jobTGP() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步TGP数据====================================================");
		Integer count = 0;
		try {
			
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("TGPGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_TGP, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = tgpGameService.pullData(null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_TGP, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("TGP", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步TGP数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步TGP数据====================================================");
	}
	/**
	 * 定时游戏数据任务
	 * 
	 * 每日补单第一次（凌晨1：5分对昨日的数据全部重新采集）
	 */
	@Scheduled(cron = "0 5 1 * * ?")
	public void jobTGPPatch() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步TGP补单数据====================================================");
		Integer count = 0;
		try {
			
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("TGPGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_TGP, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = tgpGameService.pullDataPatch(null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_TGP, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("TGP", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步TGP补单数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步TGP补单数据====================================================");
	}
	/**
	 * 定时游戏数据任务
	 * 
	 * 每日补单第一次（凌晨1：10分对昨日的数据全部重新采集）
	 */
	@Scheduled(cron = "0 10 1 * * ?")
	public void jobTGPPatch2() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步TGP补单数据====================================================");
		Integer count = 0;
		try {
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("TGPGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_TGP, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = tgpGameService.pullDataPatch(null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_TGP, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("TGP", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步TGP补单数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步TGP补单数据====================================================");
	}
	
	
	/**
	 * 定时游戏数据任务
	 */
	@Scheduled(cron = "0 0/5 * * * ? ")
	public void jobQTech() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步QTech数据====================================================");
		Integer count = 0;
		try {
			
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("QTGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_QT, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = qtGameService.pullData(null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_QT, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("QTech", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步QTech数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步QTech数据====================================================");
	}
	/**
	 * 定时游戏数据任务
	 * 
	 * 每日补单第一次（凌晨00：25分对昨日的数据全部重新采集）
	 */
	@Scheduled(cron = "0 25 0 * * ?")
	public void jobQTechPatch() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步QTech补单数据====================================================");
		Integer count = 0;
		try {
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("QTGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_QT, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = qtGameService.pullDataPatch(null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_QT, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("QTech", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步QTech补单数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步QTech补单数据====================================================");
	}
	
	
	
	/**
	 * 定时游戏数据任务
	 */
	@Scheduled(cron = "0 0/5 * * * ? ")
	public void jobHABA() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步HABA数据====================================================");
		Integer count = 0;
		try {
			
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("HABGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_HAB, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = habaGameService.pullData(null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_HAB, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("HABA", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步HABA数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步HABA数据====================================================");
	}
	/**
	 * 定时游戏数据任务
	 * 
	 */
	@Scheduled(cron = "0 0/5 * * * ? ")
	public void jobM88() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步M88数据====================================================");
		Integer count = 0;
		try {
			
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("M88Game");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_M88, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = m88GameService.pullData(null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_M88, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("M88", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步M88数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步M88数据====================================================");
	}
	/**
	 * 定时游戏数据任务（暂时没有客户使用）
	 */
//	@Scheduled(cron = "0 0/2 * * * ? ")
	public void jobGG() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步GG数据====================================================");
		Integer count = 0;
		try {
			
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("GGGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_GG, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = ggGameService.pullData(null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_GG, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("GG", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步GG数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步GG数据====================================================");
	}
	/**
	 * 定时游戏数据任务
	 */
	@Scheduled(cron = "0 0/5 * * * ? ")
	public void jobTTG() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步TTG数据====================================================");
		int count = 0;
		try {
			
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("TTGGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_TTG, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = tTGGameService.pullData(null);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_TTG, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("TTG", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步TTG数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步TTG数据====================================================");
	}
	/**
	 * 定时游戏数据任务
	 */
	@Scheduled(cron = "0 0/2 * * * ? ")
	public void jobMG() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步MG数据====================================================");
		int count = 0;
		try {
			
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("MGGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_MG, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = mGGameService.pullData(null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_MG, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("MG", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步MG数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步MG数据====================================================");
	}
	/**
	 * 定时游戏数据任务
	 * 
	 * 每日补单第一次（凌晨00：30分对昨日的数据全部重新采集）
	 */
	@Scheduled(cron = "0 30 0 * * ?")
	public void jobMGPatch() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步MG补单数据====================================================");
		int count = 0;
		try {
			
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("MGGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_MG, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = mGGameService.pullDataPatch(null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_MG, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("MG", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步MG补单数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步MG补单数据====================================================");
	}
	
	
	
	
	/**
	 * 定时游戏数据任务（采集xml文件）
	 */
	@Scheduled(cron = "0 0/5 * * * ? ")
	public void jobTAG() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步TAG数据====================================================");
		int count = 0;
		try {
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("TAGGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_TAG, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = tAGGameService2.pullData(null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_TAG, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("TAG", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步TAG数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步TAG数据====================================================");
	}
	/**
	 * 定时游戏数据任务（采集xml文件）
	 * 
	 * 每日补单第一次（凌晨2：10分对昨日的数据全部重新采集）
	 */
	@Scheduled(cron = "0 10 2 * * ?")
	public void jobTAGPatch() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步TAG补单数据====================================================");
		int count = 0;
		try {
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("TAGGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				logger.error("===================================="+sdf.format(new Date())+"维护中不采集数据。。。。。");
			} else {
				count = tAGGameService2.pullDataPatch(null);
				//如果失败，再来一次
				if(count == 0) {
					count = tAGGameService2.pullDataPatch(null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		lastDataCount.put("TAG", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步TAG补单数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步TAG补单数据====================================================");
	}
	/**
	 * 定时游戏数据任务（采集xml文件）
	 * 
	 * 每日补单第二次（凌晨2：30分对昨日的数据全部重新采集）
	 */
	@Scheduled(cron = "0 30 2 * * ?")
	public void jobTAGPatch2() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步TAG补单数据====================================================");
		int count = 0;
		try {
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("TAGGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				logger.error("===================================="+sdf.format(new Date())+"维护中不采集数据。。。。。");
			} else {
				count = tAGGameService2.pullDataPatch(null);
				//如果失败，再来一次
				if(count == 0) {
					count = tAGGameService2.pullDataPatch(null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		lastDataCount.put("TAG", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步TAG补单数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步TAG补单数据====================================================");
	}
	/**
	 * 定时游戏数据任务（解析xml文件并插入数据）
	 */
	@Scheduled(cron = "0 0/1 * * * ? ")
	public void jobTAGx() {
		logger.error("===================================="+sdf.format(new Date())+"开始解析TAG数据====================================================");
		int count = 0;
		try {
			
			count = tAGGameService2.doInsertData();
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_TAG, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("TAG", count+"");
		logger.error("===================================="+sdf.format(new Date())+"插入TAG数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成解析TAG数据====================================================");
	}
	
	
	
	
	
	/**
	 * 定时游戏数据任务（采集xml文件）
	 */
	@Scheduled(cron = "0 0/5 * * * ? ")
	public void jobYoPLAY() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步YoPLAY数据====================================================");
		int count = 0;
		try {
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("YoPLAYGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_YoPLAY, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = yoPLAYGameService.pullData(null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_YoPLAY, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("YoPLAY", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步YoPLAY数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步YoPLAY数据====================================================");
	}
	/**
	 * 定时游戏数据任务（采集xml文件）
	 * 
	 * 每日补单第一次（凌晨1：40分对昨日的数据全部重新采集）
	 */
	@Scheduled(cron = "0 40 1 * * ?")
	public void jobYoPLAYPatch() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步YoPLAY补单数据====================================================");
		int count = 0;
		try {
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("YoPLAYGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				logger.error("===================================="+sdf.format(new Date())+"维护中不采集数据。。。。。");
			} else {
				count = yoPLAYGameService.pullDataPatch(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		lastDataCount.put("YoPLAY", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步YoPLAY补单数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步YoPLAY补单数据====================================================");
	}
	/**
	 * 定时游戏数据任务（采集xml文件）
	 * 
	 * 每日补单第二次（凌晨1：50分对昨日的数据全部重新采集）
	 */
	@Scheduled(cron = "0 50 1 * * ?")
	public void jobYoPLAYPatch2() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步YoPLAY补单数据====================================================");
		int count = 0;
		try {
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("YoPLAYGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				logger.error("===================================="+sdf.format(new Date())+"维护中不采集数据。。。。。");
			} else {
				count = yoPLAYGameService.pullDataPatch(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		lastDataCount.put("YoPLAY", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步YoPLAY补单数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步YoPLAY补单数据====================================================");
	}
	/**
	 * 定时游戏数据任务（解析xml文件并插入数据）
	 */
	@Scheduled(cron = "0 0/1 * * * ? ")
	public void jobYoPLAYx() {
		logger.error("===================================="+sdf.format(new Date())+"开始解析YoPLAY数据====================================================");
		int count = 0;
		try {
			
			count = yoPLAYGameService.doInsertData();
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_YoPLAY, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("YoPLAY", count+"");
		logger.error("===================================="+sdf.format(new Date())+"插入YoPLAY数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成解析YoPLAY数据====================================================");
	}
	
	
	
	
	
	/**
	 * 定时游戏数据任务（暂时没有客户使用）
	 */
//	@Scheduled(cron = "0 0/2 * * * ? ")
	public void jobQP() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步QP数据====================================================");
		int count = 0;
		try {
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("QPGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_QP, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = qPGameService.pullData(null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_QP, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("QP", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步QP数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步QP数据====================================================");
	}
	
	
	
	/**
	 * 定时游戏数据任务
	 */
	@Scheduled(cron = "0 0/5 * * * ? ")
	public void jobOG() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步OG数据====================================================");
		int count = 0;
		try {
			
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("OGGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_AOI, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = aOIGameService.pullData(null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_AOI, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("OG", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步OG数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步OG数据====================================================");
	}
	/**
	 * 定时游戏数据任务
	 */
	@Scheduled(cron = "0 0/2 * * * ? ")
	public void jobNHQ() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步NHQ数据====================================================");
		int count = 0;
		try {
			
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("NHQGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_NHQ, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = nHQGameService.pullData(null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_NHQ, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("NHQ", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步NHQ数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步NHQ数据====================================================");
	}
	/**
	 * 定时游戏数据任务
	 * 
	 * 每日补单第一次（凌晨00：35分对昨日的数据全部重新采集）
	 */
	@Scheduled(cron = "0 35 0 * * ?")
	public void jobNHQPatch() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步NHQ补单数据====================================================");
		int count = 0;
		try {
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("NHQGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_NHQ, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = nHQGameService.pullDataPatch(null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_NHQ, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("NHQ", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步NHQ补单数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步NHQ补单数据====================================================");
	}
	
	
	
	
	/**
	 * 定时游戏数据任务
	 */
	@Scheduled(cron = "0 0/5 * * * ? ")
	public void jobSA() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步SA数据====================================================");
		int count = 0;
		try {
			
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("SAGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_SA, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = sAGameService.pullData(null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_SA, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("SA", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步SA数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步SA数据====================================================");
	}
	/**
	 * 定时游戏数据任务
	 * 
	 * 每日补单第一次（凌晨00：40分对昨日的数据全部重新采集）
	 * 
	 * （暂时不补单，原因是：此服务用于获取当前大厅任意日子24小時內的的下注信息. 每5分钟可以调用5次,否则报错.）
	 */
//	@Scheduled(cron = "0 40 0 * * ?")
	public void jobSAPatch() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步SA补单数据====================================================");
		int count = 0;
		try {
			
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("SAGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_SA, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = sAGameService.pullDataPatch(null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_SA, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("SA", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步SA补单数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步SA补单数据====================================================");
	}
	
	
	
	
	/**
	 * 定时游戏数据任务
	 */
	@Scheduled(cron = "0 0/2 * * * ? ")
	public void jobPT() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步PT数据====================================================");
		int count = 0;
		try {
			
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("PTGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_PT, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = pTGameService.pullData(null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_PT, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("PT", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步PT数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步PT数据====================================================");
	}
	/**
	 * 定时游戏数据任务
	 * 
	 * 每日补单第一次（凌晨00：45分对昨日的数据全部重新采集）
	 */
	@Scheduled(cron = "0 45 0 * * ?")
	public void jobPTPatch() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步PT补单数据====================================================");
		int count = 0;
		try {
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("PTGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_PT, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = pTGameService.pullDataPatch(null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_PT, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("PT", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步PT补单数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步PT补单数据====================================================");
	}
	
	
	
	
	
	/**
	 * 定时游戏数据任务（不再采集。金塔娱乐已关闭该游戏）
	 * @Scheduled(cron = "0 0/1 * * * ? ")
	 * @Scheduled(cron = "0 0/3 * * * ? ")
	 * @Scheduled(cron = "0 30 11 * * ?")
	 */
//	@Scheduled(cron = "0 0/50 * * * ?")
	public void jobIDN() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步IDN数据====================================================");
		Integer count = 0;
		try {
			
			
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("IDNGame");
			
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_IDN, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = idnGameService2.pullData(null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_IDN, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		lastDataCount.put("IDN", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步IDN数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步IDN数据====================================================");
	}
	
	
	/**
	 * 去玩棋牌数据采集定时任务
	 */
	@Scheduled(cron = "0 0/2 * * * ?")
	public void jobQWP() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步QWP数据====================================================");
		int count = 0;
		try {
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("QWPGame");
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_QWP, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = qwpGameService.pullData(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_QWP, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		logger.error("===================================="+sdf.format(new Date())+"同步QWP数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步QWP数据====================================================");
	}
	
	/**
	 * JDB168数据采集定时任务
	 */
	@Scheduled(cron = "0 0/2 * * * ?")
	public void jobJDB() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步JDB数据====================================================");
		int count = 0;
		try {
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("JDBGame");
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_JDB, "任务入口", "维护中不采集数据", "ALL", Enum_flag.异常.value);
			} else {
				count = jdbGameService.pullData(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_JDB, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		logger.error("===================================="+sdf.format(new Date())+"同步JDB数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步JDB数据====================================================");
	}
	/**
	 * JDB168下载zip文件任务
	 * 间隔1分钟下载一次
	 */
//	@Scheduled(fixedDelay = (1000 * 60 * 5))
	public void jobDownloadJDBZip() {
		logger.error("===================================="+sdf.format(new Date())+"开始下载JDB FTP数据文件====================================================");
		int count = 0;
		try {
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("JDBGame");
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_JDB, "任务入口", "维护中不下载", "ALL", Enum_flag.异常.value);
			} else {
				count = jdbGameService.downloadFtpFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_JDB, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		logger.error("===================================="+sdf.format(new Date())+"下载JDB FTP数据文件量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成下载JDB FTP数据====================================================");
	}
	
	/**
	 * JDB168解析本地文件定时任务
	 * 每1分钟读取一次
	 */
//	@Scheduled(cron = "0 0/2 * * * ?")
	public void jobReadJDBZip() {
		logger.error("===================================="+sdf.format(new Date())+"开始解析JDB本地数据====================================================");
		int count = 0;
		int size = 100; // 每次解析的数量
		try {
			Map<String, Object> map = dataHandleMaintenanceMapper.selectByPrimaryKey("JDBGame");
			if(map != null && !map.get("flag").toString().equals("1")) {
				LogUtil.addListLog(LogUtil.HANDLE_JDB, "任务入口", "维护中不下载", "ALL", Enum_flag.异常.value);
			} else {
				count = jdbGameService.getLocalRecord(size);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.addListLog(LogUtil.HANDLE_JDB, "任务入口", e.getMessage(), "ALL", Enum_flag.异常.value);
		}
		logger.error("===================================="+sdf.format(new Date())+"解析JDB本地数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成解析JDB本地数据====================================================");
	}
	
	/**
	 * 定时游戏数据任务（未使用）
	 
	@Scheduled(cron = "0 0/5 * * * ? ")
	*/
	public void jobDZDY() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步DZDY数据====================================================");
		int count = dzpk();
		lastDataCount.put("DZDY", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步DZDY数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步DZDY数据====================================================");
	}
	/**
	 * 定时游戏数据任务（未使用）
	 */
	@Deprecated
	public void jobXCP() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步XCP数据====================================================");
		int count = xcp();
		lastDataCount.put("XCP", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步XCP数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步XCP数据====================================================");
	}
	/**
	 * 定时游戏数据任务
	 * 
	 * 暂时没有用户使用
	 * 
	 * @Scheduled(cron = "0 0/5 * * * ? ")
	 */
	@Deprecated
	public void jobZJ() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步ZJ数据====================================================");
		int count = zj();
		lastDataCount.put("ZJ", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步ZJ数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步ZJ数据====================================================");
	}
	/**
	 * 定时游戏数据任务
	 * 没有该沙巴了
	 * 
	 * @Scheduled(cron = "0 0/5 * * * ? ")
	 */
	@Deprecated
	public void jobIBC() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步IBC数据====================================================");
		int count = ibc();
		lastDataCount.put("IBC", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步IBC数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步IBC数据====================================================");
	}
	/**
	 * 定时游戏数据任务（未使用）
	@Scheduled(cron = "0 0/5 * * * ? ")
	*/
	@Deprecated
	public void jobAV() {
		logger.error("===================================="+sdf.format(new Date())+"开始同步AV数据====================================================");
		int count = av();
		lastDataCount.put("AV", count+"");
		logger.error("===================================="+sdf.format(new Date())+"同步AV数据量="+count);
		logger.error("===================================="+sdf.format(new Date())+"完成同步AV数据====================================================");
	}
	/**
	 * 定时游戏数据任务（PNG因为走的是TAG线路，所以不用单独开启）
	 */
	@Deprecated
	public void jobPNG() {
//		logger.error("===================================="+sdf.format(new Date())+"开始同步PNG数据====================================================");
//		int count = png();
//		lastDataCount.put("PNG", count+"");
//		logger.error("===================================="+sdf.format(new Date())+"同步PNG数据量="+count);
//		logger.error("===================================="+sdf.format(new Date())+"完成同步PNG数据====================================================");
	}
	
	/**
	 * 拉取东方游戏数据
	 * 
	 * @return 数量
	 */
	public Integer aoi() {
		Integer count = 0;
		try {
			count = aOIGameService.pullData(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 拉取沙巴体育游戏数据
	 * 
	 * @return 数量
	 */
	public Integer ibc() {
		Integer count = 0;
		try {
			count = iBCGameService.pullData(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 拉取新环球游戏数据
	 * 
	 * @return 数量
	 */
	public Integer nhq() {
		Integer count = 0;
		try {
			count = nHQGameService.pullData(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 拉取沙龙游戏数据
	 * 
	 * @return 数量
	 */
	public Integer sa() {
		Integer count = 0;
		try {
			count = sAGameService.pullData(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 拉取XCP游戏数据
	 * 
	 * @return 数量
	 */
	public Integer xcp() {
		Integer count = 0;
		try {
			count = xCPGameService.pullData(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 拉取洲际游戏数据
	 * 
	 * @return 数量
	 */
	public Integer zj() {
		Integer count = 0;
		try {
			count = zJGameService.pullData(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 拉取MG游戏数据
	 * 
	 * @return 数量
	 */
	public Integer mg() {
		Integer count = 0;
		try {
			count = mGGameService.pullData(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 拉取AV游戏数据
	 * 
	 * @return 数量
	 */
	public Integer av() {
		Integer count = 0;
		try {
			count = aVGameService.pullData(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 拉取PT游戏数据
	 * 
	 * @return 数量
	 */
	public Integer pt() {
		Integer count = 0;
		try {
			count = pTGameService.pullData(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 拉取TAG游戏数据
	 * 
	 * @return 数量
	 */
	public Integer tag() {
		Integer count = 0;
		try {
			count = tAGGameService.pullData(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	/**
	 * 拉取波音5游戏数据
	 * 
	 * @return 数量
	 */
	public Integer bbin5() {
		Integer count = 0;
		try {
			count = bBIN5GameService.pullData(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 拉取TTG游戏数据
	 * 
	 * @return 数量
	 */
	public Integer ttg() {
		Integer count = 0;
		try {
			count = tTGGameService.pullData(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	

	/**
	 * 拉取棋牌游戏数据
	 * 
	 * @return 数量
	 */
	public Integer qp() {
		Integer count = 0;
		try {
			count = qPGameService.pullData(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	

	/**
	 * 拉取PNG游戏数据
	 * 
	 * @return 数量
	 */
	public Integer png() {
		Integer count = 0;
		try {
			count = pNGGameService.pullData(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 拉取DZPK游戏数据
	 * 
	 * @return 数量
	 */
	public Integer dzpk() {
		Integer count = 0;
		try {
			count = dzdyGameService.pullData(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	
	/**
	 * 定时游戏数据任务
	 */
	@Deprecated
	public void job() {
		FutureTask<Integer> aoi = getFutureTask(Enum_GameType.东方.intVal);
		new Thread(aoi).start();//开启东方游戏数据拉取线程
		FutureTask<Integer> ibc = getFutureTask(Enum_GameType.沙巴.intVal);
		new Thread(ibc).start();//开启沙巴体育游戏数据拉取线程
		FutureTask<Integer> bbin = getFutureTask(Enum_GameType.波音.intVal);
		new Thread(bbin).start();//开启波音游戏数据拉取线程
		FutureTask<Integer> nhq = getFutureTask(Enum_GameType.环球.intVal);
		new Thread(nhq).start();//开启新环球游戏数据拉取线程
		FutureTask<Integer> sa = getFutureTask(Enum_GameType.沙龙.intVal);
		new Thread(sa).start();//开启沙龙游戏数据拉取线程
		FutureTask<Integer> tag = getFutureTask(Enum_GameType.AG亚游.intVal);
		new Thread(tag).start();//开启TAG游戏数据拉取线程
		FutureTask<Integer> av = getFutureTask(Enum_GameType.AV老虎机.intVal);
		new Thread(av).start();//开启AV游戏数据拉取线程
		FutureTask<Integer> pt = getFutureTask(Enum_GameType.PT.intVal);
		new Thread(pt).start();//开启PT游戏数据拉取线程
		FutureTask<Integer> zj = getFutureTask(Enum_GameType.洲际.intVal);
		new Thread(zj).start();//开启洲际游戏数据拉取线程
		FutureTask<Integer> mg = getFutureTask(Enum_GameType.美高.intVal);
		new Thread(mg).start();//开启MG游戏数据拉取线程
		FutureTask<Integer> xcp = getFutureTask(Enum_GameType.祥瑞.intVal);
		new Thread(xcp).start();//开启XCP游戏数据拉取线程
		FutureTask<Integer> ttg = getFutureTask(Enum_GameType.TTG.intVal);
		new Thread(ttg).start();//开启TTG游戏数据拉取线程

		FutureTask<Integer> qp = getFutureTask(Enum_GameType.棋牌.intVal);
		new Thread(qp).start();//开启棋牌游戏数据拉取线程

		FutureTask<Integer> bbin5 = getFutureTask(Enum_GameType.波音5.intVal);
		new Thread(bbin5).start();//开启棋牌游戏数据拉取线程

		FutureTask<Integer> png = getFutureTask(Enum_GameType.PNG.intVal);
		new Thread(png).start();//开启PNG游戏数据拉取线程
		
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append("东方："+aoi.get());
			sb.append("  沙巴："+ibc.get());
			sb.append("  波音："+bbin.get());
			sb.append("  新环球："+nhq.get());
			sb.append("  沙龙："+sa.get());
			sb.append("  TAG："+tag.get());
			sb.append("  AV："+av.get());
			sb.append("  PT："+pt.get());
			sb.append("  洲际："+zj.get());
			sb.append("  MG："+mg.get());
			sb.append("  XCP："+xcp.get());
			sb.append("  TTG："+ttg.get());
			sb.append("  棋牌："+qp.get());
			sb.append("  波音5："+bbin5.get());
			sb.append("  PNG："+png.get());
			logger.info("["+sdf.format(new Date())+"]："+sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 获取拉取游戏数据任务
	 * @param name 名称
	 * @return 任务对象
	 */
	public FutureTask<Integer> getFutureTask(final int name) {
		Callable<Integer> callable = new Callable<Integer>() {
			public Integer call() throws Exception {
				Integer count = 0;
				switch (name) {
				case 1://东方ogog
					count = aoi();
					break;
				case 2://沙巴体育
					count = ibc();
					break;
				case 3://新环球
					count = nhq();
					break;
				case 4://波音
					count = 0;
					break;
				case 5://沙龙
					count = sa();
					break;
				case 6://TAG
					count = tag();
					break;
				case 7://PT
					count = pt();
					break;
				case 8://AV
					count = av();
					break;
				case 9://MG
					count = mg();
					break;
				case 10://XCP
					count = xcp();
					break;
				case 11://洲际
					count = zj();
					break;
				case 12://棋牌
					count = qp();
					break;
				case 13://TTG
					count = ttg();
					break;
				case 14://波音5
					count = bbin5();
					break;
				case 15://PNG游戏
					count = png();
					break;
				default://默认东方
					count = aoi();
					break;
				}
				return count;
			}
		};
		FutureTask<Integer> future = new FutureTask<Integer>(callable);
		return future;
	}
	private enum Enum_GameType {
		东方("OGGame","东方","aoi", 1),
		沙巴("IBCGame","沙巴","ibc", 2),
		环球("NHQGame","环球","nhq", 3),
		波音("BBINGame","波音","bbin", 4),
		沙龙("SAGame","沙龙","sa", 5),
		AG亚游("TAGGame","AG亚游","tag", 6),
		PT("PTGame","PT","pt", 7),
		AV老虎机("AVGame","AV老虎机","av", 8),
		美高("MGGame","美高","mg", 9),
		祥瑞("XCPGame","祥瑞","xcp", 10),
		洲际("ZJGame","洲际","zj", 11),
		棋牌("QPGame","棋牌","qp", 12),
		TTG("TTGGame","TTG","ttg", 13),
		波音5("BBIN5Game","波音5","bbin5", 14),
		PNG("PNGGame","PNG","png", 15),
		;
		public String gametype;
		public String name;
		public String prefix;
		public int intVal;
	    private Enum_GameType(String gametype,String name,String prefix,int intVal) {
	        this.gametype = gametype;
	        this.name = name;
	        this.prefix = prefix;
	        this.intVal = intVal;
	    }
	    
	    public static boolean validate(String gametype){
	    	for (Enum_GameType eg : Enum_GameType.values()) {
				if(gametype.equals(eg.gametype))
					return true;
			}
	    	return false;
	    }
	}
	
	public String HANDLE_TAG = "begin.time.hq.ag";
	public String HANDLE_AOI = "begin.time.hq.og.og";
	public String HANDLE_IBC = "begin.time.hq.og.ibc";
	public String HANDLE_BBIN = "begin.time.hq.bbin";
	public String HANDLE_NHQ = "begin.time.hq.nhq";
	public String HANDLE_PT = "begin.time.hq.pt";
	public String HANDLE_XCP = "begin.time.hq.xcp";
	public String HANDLE_AV = "begin.time.kr.av";
	public String HANDLE_MG = "begin.time.mg";
	public String HANDLE_QP = "begin.time.qp";
	public String HANDLE_SA = "begin.time.sa";
	public String HANDLE_TTG = "begin.time.ttg";
	public String HANDLE_ZJ = "begin.time.zj";
	public String HANDLE_DZDY = "begin.time.hq.dzdy";
	public String HANDLE_GG = "begin.time.hq.gg";
	public String HANDLE_SGS = "begin.time.hq.sgs";
	public String HANDLE_IDN = "begin.time.hq.idn";
	public String HANDLE_M88 = "begin.time.hq.m88";
	public String HANDLE_HAB = "begin.time.hq.hab";
	public String HANDLE_QT = "begin.time.hq.qt";
	public String HANDLE_TGP = "begin.time.hq.tgp";
	public String HANDLE_WIN88 = "begin.time.hq.win88";
	public String HANDLE_GB = "begin.time.hq.gb";
	public String HANDLE_EBET = "begin.time.hq.ebet";
	public String HANDLE_GGPoker = "begin.time.hq.ggpoker";
	public String HANDLE_EIBC = "begin.time.eibc";
	
}
