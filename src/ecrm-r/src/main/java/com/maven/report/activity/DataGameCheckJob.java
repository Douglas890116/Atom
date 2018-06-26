package com.maven.report.activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hy.pull.common.util.api.GameAPI;
import com.maven.cache.SystemCache;
import com.maven.entity.DataHandleMaintenance;
import com.maven.entity.DataHandleMaintenance.Enum_flag;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.EmployeeApiAccoutPasswordJob;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeLevel;
import com.maven.entity.EnterpriseGame;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.EnterpriseOperatingBrandGame;
import com.maven.entity.Game;
import com.maven.entity.UserLogs;
import com.maven.game.APIServiceUtil;
import com.maven.game.enums.GameEnum;
import com.maven.game.enums.GameEnum.Enum_GameType;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.DataHandleMaintenanceService;
import com.maven.service.EmployeeApiAccoutPasswordJobService;
import com.maven.service.EmployeeApiAccoutService;
import com.maven.service.EnterpriseGameService;
import com.maven.service.EnterpriseOperatingBrandGameService;
import com.maven.service.EnterpriseOperatingBrandService;
import com.maven.service.GameService;
import com.maven.service.UserLogsService;
import com.maven.util.MailUtil;
import com.maven.util.SmsUtilPublic;

import net.sf.json.JSONObject;

/**
 * 游戏平台维护情况检测任务处理器
 * @author Administrator
 *
 */
@Component
@Lazy(false)
public class DataGameCheckJob {
	
	private static LoggerManager log = LoggerManager.getLogger(DataGameCheckJob.class.getName(),OutputManager.LOG_DAY_BUT_BONUS);
	
	@Autowired
	private EmployeeApiAccoutService employeeApiAccoutService;
	
	/** 游戏 */
	@Autowired
	private GameService gameService;
	/** 企业游戏 */
	@Autowired
	private EnterpriseGameService enterpriseGameService;
	
	/** 品牌 */
	@Autowired
	private EnterpriseOperatingBrandService enterpriseOperatingBrandService;
	/** 品牌游戏中间表 */
	@Autowired
	private EnterpriseOperatingBrandGameService enterpriseOperatingBrandGameService;
	@Autowired
	private UserLogsService userLogsService;
	@Autowired
	private DataHandleMaintenanceService dataHandleMaintenanceService;
	
	public static void main(String[] args) {
		
		Calendar calendar = Calendar.getInstance();
		int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);//24小时
		if(weekDay == Calendar.MONDAY && (hour >= 10 && hour <= 14)) {
			System.out.println("SA沙龙每周一上午10:30~下午2:00");
		} else {
			System.out.println("正常");
		}
	}
	
	@Scheduled(fixedDelay = (1000 * 60 * 10) )//5分钟检查一次
	public void work() {
		
		StringBuffer buffer = new StringBuffer();
		
		boolean issend = false;//有异常的就要发送邮件
		
		log.Error("#########################开始游戏平台维护情况#########################");
		buffer.append("#########################开始游戏平台维护情况#########################").append("\r\n");
			
		Enum_GameType[] array = GameEnum.Enum_GameType.values();
		
		Map<String, Game> GameMap = new HashMap<String, Game>();
		try {
			List<Game> listGame = gameService.getAllGame();
			for (Game game : listGame) {
				GameMap.put(game.getGametype(), game);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Calendar calendar = Calendar.getInstance();
		int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);//24小时
		
		for (Enum_GameType enum_GameType : array) {	
			
			if(enum_GameType.gametype.equals(Enum_GameType.东方AG.gametype) || 
					enum_GameType.gametype.equals(Enum_GameType.沙巴.gametype) ||
					enum_GameType.gametype.equals(Enum_GameType.AV老虎机.gametype) ||
					enum_GameType.gametype.equals(Enum_GameType.PNG.gametype) ||
					enum_GameType.gametype.equals(Enum_GameType.洲际.gametype) ||
					enum_GameType.gametype.equals(Enum_GameType.棋牌.gametype) ||
					enum_GameType.gametype.equals(Enum_GameType.DZDY.gametype) ||
					enum_GameType.gametype.equals(Enum_GameType.IDN.gametype) ||
					enum_GameType.gametype.equals(Enum_GameType.祥瑞.gametype)) {
				continue;
			}
				
			try {
				
				DataHandleMaintenance dataHandle = dataHandleMaintenanceService.selectByPrimaryKey(enum_GameType.gametype);
				if(dataHandle == null) {
					dataHandle = new DataHandleMaintenance();
					dataHandle.setGametype(enum_GameType.gametype);
					dataHandleMaintenanceService.addActivityBetRecord(dataHandle);
				} else {
					
					//如果是我方人工禁用了则不参与10分钟一次检查正常或维护情况，可由运营商客户在品牌管理处自由开启和关闭
					if(dataHandle.getFlag() == Enum_flag.禁用.value) {
						continue;
					}
				}
				
				
				log.Error("===================开始检查平台".concat(enum_GameType.gametype).concat(" ").concat(enum_GameType.name));
				buffer.append("===================开始检查平台".concat(enum_GameType.gametype).concat(" ").concat(enum_GameType.name)).append("\r\n");
				
				String result = "";
				EmployeeApiAccout item = new EmployeeApiAccout();
				item.setGametype(enum_GameType.gametype);
				
				List<EmployeeApiAccout> list = employeeApiAccoutService.getEmployeeApiAccoutByObject(item);
				if(list != null && list.size() > 0) {
					
					EmployeeApiAccout data = list.get(0);
					
					JSONObject object = new JSONObject();
					try {
						String str = GameAPI.getBalance(data.getGameaccount(), data.getGamepassword(), data.getGametype(), data.getEnterprisecode()).toString() ;
						object = JSONObject.fromObject(str);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					//查找该游戏相关的企业
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("gamecode", GameMap.get(enum_GameType.gametype).getGamecode());
					List<EnterpriseGame> listEnterpriseGame = enterpriseGameService.selectAll(params);
					
					
					
					/*******************HY真人是每周一10-12进行维护*********************/
					if(enum_GameType.gametype.equals(Enum_GameType.环球.gametype)) {
						if(weekDay == Calendar.MONDAY && (hour >= 10 && hour < 12)) {
							object.put("code", "-1");
							object.put("info", "HY真人是每周一上午10-12例行性维护");
						}
					}
					
					/*******************SA沙龙每周一上午10:30~下午2:00*********************/
					if(enum_GameType.gametype.equals(Enum_GameType.沙龙.gametype)) {
						if(weekDay == Calendar.MONDAY && (hour >= 10 && hour < 14)) {
							object.put("code", "-1");
							object.put("info", "SA沙龙每周一上午10:30~下午2:00");
						}
					}
					/*******************AG每週一 11:30 ~ 13:00 AG旗舰厅、VIP厅*********************/
					if(enum_GameType.gametype.equals(Enum_GameType.AG.gametype)) {
						if(weekDay == Calendar.MONDAY && (hour >= 10 && hour < 13)) {
							object.put("code", "-1");
							object.put("info", "AG每週一 11:30 ~ 13:00");
						}
					}
					/*******************TGP申博每週一 10:30 ~ 14:00 *********************/
					if(enum_GameType.gametype.equals(Enum_GameType.TGPlayer.gametype)) {
						if(weekDay == Calendar.MONDAY && (hour >= 10 && hour < 14)) {
							object.put("code", "-1");
							object.put("info", "TGP申博每週一 10:30 ~ 14:00常规维护");
						}
					}
					
					/*******************每週二 10:00 ~ 11:00 AGQ极速厅 AG国际厅、竞咪厅*********************/
					if(enum_GameType.gametype.equals(Enum_GameType.AG.gametype)) {
						if(weekDay == Calendar.TUESDAY && (hour >= 10 && hour < 11)) {
							object.put("code", "-1");
							object.put("info", "每週二 10:00 ~ 11:00 AGQ极速厅 AG国际厅、竞咪厅");
						}
					}
					/*******************BBIN平台 将于08月02日（三）进行例行维护。*********************/
					if(enum_GameType.gametype.equals(Enum_GameType.波音.gametype)) {
						if(weekDay == Calendar.WEDNESDAY && (hour >= 9 && hour < 12)) {
							object.put("code", "-1");
							object.put("info", "BBIN平台 将于每周三09:00 ~ 12:00进行例行维护");
						}
					}
					
					/*******************GGPoker平台 将于08月02日（三）进行例行维护。
					if(enum_GameType.gametype.equals(Enum_GameType.GGPoker.gametype)) {
						if(weekDay == Calendar.WEDNESDAY && (hour >= 9 && hour < 22)) {
							object.put("code", "-1");
							object.put("info", "GGPoker平台 将于每周三09:00 ~ 22:00进行例行维护");
						}
					}
					*********************/
					
					if(object.containsKey("code") && object.getString("code").equals("0")) {
						result = "正常 "+object.getString("info");
						
						List<EnterpriseOperatingBrandGame> listdata = new ArrayList<EnterpriseOperatingBrandGame>();
						
						//如果是正常的，则需要添加
						for (EnterpriseGame enterpriseGame : listEnterpriseGame) {
							
							//查企业的品牌
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("enterprisecode", enterpriseGame.getEnterprisecode());
							List<EnterpriseOperatingBrand> listEnterpriseOperatingBrand = enterpriseOperatingBrandService.getAllBrand(map);
							for (EnterpriseOperatingBrand enterpriseOperatingBrand : listEnterpriseOperatingBrand) {
								
								//维护中的要更正为正常
								EnterpriseOperatingBrandGame temp = new EnterpriseOperatingBrandGame(
										enterpriseOperatingBrand.getBrandcode(), 
										GameMap.get(enum_GameType.gametype).getGamecode(), EnterpriseOperatingBrandGame.Enum_flag.维护.value);
								
								List<EnterpriseOperatingBrandGame> __listdata = enterpriseOperatingBrandGameService.takeBrandGames(temp);
								if(__listdata != null && __listdata.size() > 0 ) {
									temp.setFlag(EnterpriseOperatingBrandGame.Enum_flag.正常.value);
									listdata.add(temp);
									
									addlog(enterpriseGame, GameMap.get(enum_GameType.gametype), "开启");
								}
							}
							
						}
						
						dataHandle.setFlag(Enum_flag.正常.value);
						dataHandle.setLasttime(new Date());
						dataHandleMaintenanceService.updateActivityBetRecord(dataHandle);
						
						//批量修改
						if(listdata.size() > 0) {
							enterpriseOperatingBrandGameService.updateListData(listdata);
						}
						
						
						
					} else {
						result = "#####检测异常#####"+object + " =="+data.getLoginaccount()+"==="+data.getEnterprisecode();
						issend = true;
						
						List<EnterpriseOperatingBrandGame> listdata = new ArrayList<EnterpriseOperatingBrandGame>();
						
						for (EnterpriseGame enterpriseGame : listEnterpriseGame) {
							
							//查企业的品牌
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("enterprisecode", enterpriseGame.getEnterprisecode());
							List<EnterpriseOperatingBrand> listEnterpriseOperatingBrand = enterpriseOperatingBrandService.getAllBrand(map);
							for (EnterpriseOperatingBrand enterpriseOperatingBrand : listEnterpriseOperatingBrand) {
								
								//正常的要改为维护中
								EnterpriseOperatingBrandGame temp = new EnterpriseOperatingBrandGame(
										enterpriseOperatingBrand.getBrandcode(), 
										GameMap.get(enum_GameType.gametype).getGamecode(), EnterpriseOperatingBrandGame.Enum_flag.正常.value);
								
								List<EnterpriseOperatingBrandGame> __listdata = enterpriseOperatingBrandGameService.takeBrandGames(temp);
								if(__listdata != null && __listdata.size() > 0 ) {
									temp.setFlag(EnterpriseOperatingBrandGame.Enum_flag.维护.value);
									listdata.add(temp);
									
									addlog(enterpriseGame, GameMap.get(enum_GameType.gametype), "关闭");
								}
							}
							
						}
						
						//批量修改
						if(listdata.size() > 0) {
							enterpriseOperatingBrandGameService.updateListData(listdata);
						}
						
						
						dataHandle.setFlag(Enum_flag.维护.value);
						dataHandle.setLasttime(new Date());
						dataHandleMaintenanceService.updateActivityBetRecord(dataHandle);
					}
					
				} else {
					result = "该平台没有找到可以使用的游戏账号";
				}
				
				log.Error("===================完成检查平台".concat(enum_GameType.gametype).concat(" ").concat(enum_GameType.name).concat(" = ").concat(result));
				buffer.append("===================完成检查平台".concat(enum_GameType.gametype).concat(" ").concat(enum_GameType.name).concat(" = ").concat(result)).append("\r\n");
				
			} catch (Exception e) {
				e.printStackTrace();
				SmsUtilPublic.sendSmsByGet01(SmsUtilPublic.__NOTI_PHONENO, "游戏平台维护情况");
			}
		}
		
		log.Error("#########################完成游戏平台维护情况#########################");
		buffer.append("#########################完成游戏平台维护情况#########################").append("\r\n");
		
		if(issend) {
			MailUtil.send( "游戏平台检测通知", "", buffer.toString());
		}
		
	}
	
	
	
	private void addlog(EnterpriseGame enterpriseGame, Game game, String status) {
		
		String content = status + " 企业下所有品牌的"+game.getGamename();
		try {
			userLogsService.addActivityBetRecord(new UserLogs(
					enterpriseGame.getEnterprisecode(), 
					null, 
					null, 
					UserLogs.Enum_operatype.游戏信息业务, content, "sa", "自动检查平台维护情况"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
