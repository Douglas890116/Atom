package com.maven.report.activity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hy.pull.common.util.api.GameAPI;
import com.maven.cache.SystemCache;
import com.maven.entity.DataHandleMaintenance;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.EmployeeApiAccoutPasswordJob;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeLevel;
import com.maven.entity.EnterpriseGame;
import com.maven.entity.Game;
import com.maven.entity.UserLogs;
import com.maven.entity.DataHandleMaintenance.Enum_flag;
import com.maven.game.APIServiceUtil;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.DataHandleMaintenanceService;
import com.maven.service.EmployeeApiAccoutPasswordJobService;
import com.maven.service.EmployeeApiAccoutService;
import com.maven.service.UserLogsService;
import com.maven.util.SmsUtilPublic;

import net.sf.json.JSONObject;

/**
 * 游戏平台密码同步任务处理器
 * @author Administrator
 *
 */
@Component
@Lazy(false)
public class DataGameAccountJob {
	
	private static LoggerManager log = LoggerManager.getLogger(DataGameAccountJob.class.getName(),OutputManager.LOG_DAY_BUT_BONUS);
	
	@Autowired
	private EmployeeApiAccoutPasswordJobService employeeApiAccoutPasswordJobService;
	@Autowired
	private EmployeeApiAccoutService employeeApiAccoutService;
	@Autowired
	private UserLogsService userLogsService;
	@Autowired
	private DataHandleMaintenanceService dataHandleMaintenanceService;
	
	
	@Scheduled(fixedDelay = (1000*60*2) )//2分钟检查一次
	public void work() {
		
		log.Error("#########################开始检查处理游戏账号密码修改#########################");
		
		try {
			
			EmployeeApiAccoutPasswordJob accoutPasswordJob = new EmployeeApiAccoutPasswordJob();
			accoutPasswordJob.setUpdatestatus(2);
			
			List<EmployeeApiAccoutPasswordJob> list = employeeApiAccoutPasswordJobService.findList(accoutPasswordJob);//查询上次处理失败需要处理的任务
			
			accoutPasswordJob = new EmployeeApiAccoutPasswordJob();
			accoutPasswordJob.setUpdatestatus(0);//查询最新的待处理的任务
			list.addAll(employeeApiAccoutPasswordJobService.findList(accoutPasswordJob));
			
			for (EmployeeApiAccoutPasswordJob employeeApiAccoutPasswordJob : list) {
				if(employeeApiAccoutPasswordJob == null) {
					continue;
				}
				
				String enterprisecode = employeeApiAccoutPasswordJob.getEnterprisecode();
				String username = employeeApiAccoutPasswordJob.getGameaccount();
				String password = employeeApiAccoutPasswordJob.getGamepassword();
				String gametype = employeeApiAccoutPasswordJob.getGametype();
				String loginaccount = employeeApiAccoutPasswordJob.getLoginaccount();//这里储存的是旧密码，加密的
				String employeecode = employeeApiAccoutPasswordJob.getEmployeecode();
				
				DataHandleMaintenance dataHandle = dataHandleMaintenanceService.selectByPrimaryKey(gametype);
				if(dataHandle != null) {
					//如果是我方人工禁用了则不参与10分钟一次检查正常或维护情况，可由运营商客户在品牌管理处自由开启和关闭
					if(dataHandle.getFlag() == Enum_flag.禁用.value) {
						continue;
					}
				}
				
				//查找此人所有游戏账号数据
				Map<String,EmployeeApiAccout>  eapiaccounts = SystemCache.getInstance().getEmployeeAllGameAccount(employeecode);
				EmployeeApiAccout accout = SystemCache.getInstance().getEmployeeGameAccount(employeecode, gametype);
				if(accout == null) {
					continue;
				}
				
				String newpassword = APIServiceUtil.decrypt(password, accout.getParentemployeecode());//解密密码
				String oldpassword = APIServiceUtil.decrypt(loginaccount, accout.getParentemployeecode());//解密密码
				
				if(newpassword.equals(oldpassword)) {
					employeeApiAccoutPasswordJob.setUpdatestatus(1);
					employeeApiAccoutPasswordJob.setUpdatetime(new Date());
					employeeApiAccoutPasswordJob.setUpdatecomment("密码一样，不需要更新");
					employeeApiAccoutPasswordJobService.updatePassword(employeeApiAccoutPasswordJob);
					continue;
				}
				
				JSONObject object = JSONObject.fromObject( GameAPI.updatePassword(username, oldpassword, gametype, enterprisecode, newpassword) );
				
				log.Error("#########################批量更新API密码结果："+object + " Enterprisecode="+enterprisecode +" gametype="+gametype + " Loginaccount="+loginaccount);
				
				//修改成功，则更新数据库
				if(object.getString("code").equals("0")) {
					
					if( accout != null) {
						
						//更新覆盖缓存数据及修改数据库记录
						accout.setGamepassword(newpassword);
						int count = employeeApiAccoutService.updatePassword(accout);
						eapiaccounts.put(gametype, accout);
						SystemCache.getInstance().setEmployeeAllGameAccount(employeecode, eapiaccounts);
						
						
						employeeApiAccoutPasswordJob.setUpdatecomment("处理成功");
						employeeApiAccoutPasswordJob.setUpdatestatus(1);//已处理
						employeeApiAccoutPasswordJob.setUpdatetime(new Date());
						employeeApiAccoutPasswordJobService.updatePassword(employeeApiAccoutPasswordJob);
						
					}
					
					
				} else if(object.getString("code").equals("1004")) {
					//不支持的接口
					log.Error("=====================================================更新密码失败："+object + " Enterprisecode="+enterprisecode +" gametype="+gametype + " Loginaccount="+loginaccount);
					continue;
				} else {
					
					employeeApiAccoutPasswordJob.setUpdatecomment(object.getString("info"));
					employeeApiAccoutPasswordJob.setUpdatestatus(2);//处理失败，标记，下次也许能成功
					employeeApiAccoutPasswordJob.setUpdatetime(new Date());
					employeeApiAccoutPasswordJobService.updatePassword(employeeApiAccoutPasswordJob);
					
					
					//更新密码失败，跳过，保留旧密码
					log.Error("=====================================================更新密码失败："+object + " Enterprisecode="+enterprisecode +" gametype="+gametype + " Loginaccount="+loginaccount);
					continue;
				}
				
			}
			
			log.Error("#########################完成检查处理游戏账号密码修改#########################");
			
		} catch (Exception e) {
			e.printStackTrace();
			//SmsUtilPublic.sendSmsByGet01(SmsUtilPublic.__NOTI_PHONENO, "检查处理游戏账号密码修改");
		}
		
	}
	
}
