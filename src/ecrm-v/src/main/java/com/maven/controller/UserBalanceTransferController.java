package com.maven.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.cache.SystemCache;
import com.maven.constant.Constant;
import com.maven.constant.Enum_MSG;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.EmployeeApiAccoutPasswordJob;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeCapitalAccount;
import com.maven.entity.EnterpriseEmployeeType;
import com.maven.entity.EnterpriseOperatingBrandGame;
import com.maven.entity.Game;
import com.maven.entity.PermissionMenu.Enum_language;
import com.maven.entity.UserLogs;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.LogicTransactionException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.game.APIServiceNew;
import com.maven.game.OrderNewUtil;
import com.maven.game.enums.GameEnum;
import com.maven.game.enums.GameEnum.Enum_GameType;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.EmployeeApiAccoutPasswordJobService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.GameService;
import com.maven.service.UserLogsService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/UserBalanceTransfer")
public class UserBalanceTransferController extends BaseController {

	private static LoggerManager log = LoggerManager.getLogger(UserBalanceTransferController.class.getName(), OutputManager.LOG_BALANCETRANSFER);
	@Autowired
	private EmployeeApiAccoutPasswordJobService employeeApiAccoutPasswordJobService;
	
	@Autowired
	private EnterpriseEmployeeCapitalAccountService capitalAccountService;
	
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;

	@Autowired
	private UserLogsService userLogsService;
	
	@Autowired
	private GameService gameService;

	
	/**
	 * 用户余额首页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String userBalance() {
		return "/userbalance/index";
	}
	
	/**
	 * 用户余额首页
	 * 
	 * 忽略平台维护状态
	 * 
	 * @return
	 */
	@RequestMapping("/index2")
	public String userBalance2() {
		return "/userbalance/index2";
	}

	/**
	 * 获取用户所有余额，多列
	 * 
	 * 忽略平台维护的状态
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/balanceAll2")
	@ResponseBody
	public Map<String, Object> balanceAll2(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			String language = String.valueOf(session.getAttribute(Constant.LANGUAGE));
			boolean isEnglish = Enum_language.英文.value.equals(language);
			Map<String, Object> params = super.getRequestParamters(request);
			// 权限设置
			params.put("enterprisecode", ee.getEnterprisecode());
			super.dataLimits(ee, params, session);
			EnterpriseEmployee employee = enterpriseEmployeeService.takeEnterpriseEmployee(params);

			if (employee == null) {
				result.put("status", Constant.BooleanByte.NO);
				result.put("msg", isEnglish?"Account error!":"用户账号错误!");
			} else if (!employee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.会员.value)) {
				result.put("status", Constant.BooleanByte.NO);
				result.put("msg", isEnglish?"Non-member user":"非会员用户!");
			} else {
				List<Game> BrandGameAll = gameService.takeBrandGames(employee.getBrandcode(),EnterpriseOperatingBrandGame.Enum_flag.正常.value);

				Map<String, String> BalanceAll = new APIServiceNew(employee.getEnterprisecode())
						.balancesAll2(employee.getEmployeecode(), employee.getBrandcode());

				List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
				// 中心钱包永远在第一位
				String balance = BalanceAll.get("CENTER");
				Map<String, String> data = new HashMap<String, String>();
				data.put("gamecode", "00000");
				data.put("gametype", "CENTER");
				data.put("gamename", isEnglish?"My Wallet":"我的钱包");
				data.put("gamebalance", balance == null ? "0.00" : balance);
				mapList.add(data);

				for (Game game : BrandGameAll) {
					String balancex = BalanceAll.get(game.getGametype());
					Map<String, String> datax = new HashMap<String, String>();
					datax.put("gamecode", game.getGamecode());
					datax.put("gametype", game.getGametype());
					datax.put("gamename", game.getGamename());
					datax.put("gamebalance", balancex == null ? "0.00" : balancex);
					mapList.add(datax);
				}
				result.put("dataList", mapList);
				result.put("status", Constant.BooleanByte.YES);
			}
			System.out.println(result);
			return result;
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
		} catch (LogicTransactionRollBackException e) {
			log.Error(e.getMessage(), e);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 获取用户所有余额，多列
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/balanceAll")
	@ResponseBody
	public Map<String, Object> balanceAll(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			String language = String.valueOf(session.getAttribute(Constant.LANGUAGE));
			boolean isEnglish = Enum_language.英文.value.equals(language);
			Map<String, Object> params = super.getRequestParamters(request);
			// 权限设置
			params.put("enterprisecode", ee.getEnterprisecode());
			super.dataLimits(ee, params, session);
			EnterpriseEmployee employee = enterpriseEmployeeService.takeEnterpriseEmployee(params);

			if (employee == null) {
				result.put("status", Constant.BooleanByte.NO);
				result.put("msg", isEnglish?"Account error!":"用户账号错误!");
			} else if (!employee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.会员.value)) {
				result.put("status", Constant.BooleanByte.NO);
				result.put("msg", isEnglish?"Non-member user":"非会员用户!");
			} else {
				List<Game> BrandGameAll = gameService.takeBrandGames(employee.getBrandcode(),EnterpriseOperatingBrandGame.Enum_flag.正常.value);

				Map<String, String> BalanceAll = new APIServiceNew(employee.getEnterprisecode())
						.balancesAll(employee.getEmployeecode(), employee.getBrandcode());

				List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
				// 中心钱包永远在第一位
				String balance = BalanceAll.get("CENTER");
				Map<String, String> data = new HashMap<String, String>();
				data.put("gamecode", "00000");
				data.put("gametype", "CENTER");
				data.put("gamename", isEnglish?"My Wallet":"我的钱包");
				data.put("gamebalance", balance == null ? "0.00" : balance);
				mapList.add(data);

				for (Game game : BrandGameAll) {
					String balancex = BalanceAll.get(game.getGametype());
					Map<String, String> datax = new HashMap<String, String>();
					datax.put("gamecode", game.getGamecode());
					datax.put("gametype", game.getGametype());
					datax.put("gamename", game.getGamename());
					datax.put("gamebalance", balancex == null ? "0.00" : balancex);
					mapList.add(datax);
				}
				result.put("dataList", mapList);
				result.put("status", Constant.BooleanByte.YES);
			}
			System.out.println(result);
			return result;
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
		} catch (LogicTransactionRollBackException e) {
			log.Error(e.getMessage(), e);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return null;
	}
	/**
	 * 用户转分
	 * @param request
	 * @return
	 */
	@RequestMapping("/balanceTransfer")
	@ResponseBody
	public Map<String,Object> balanceTransfer(HttpServletRequest request, HttpSession session) {
		String language = String.valueOf(session.getAttribute(Constant.LANGUAGE));
		boolean isEnglish = Enum_language.英文.value.equals(language);
		
		//如果是方案2则需要忽略平台维护的状态
		boolean isFun2 = false;
		String Referer = request.getHeader("Referer");//index2
		if(Referer.indexOf("index2") > -1 ) {
			isFun2 = true;
		}
		
		try {
			Map<String, Object> params = getRequestParamters(request);
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			params.put("enterprisecode", loginEmployee.getEnterprisecode());
			String employeecode = params.get("employeecode").toString();
			int amount = Integer.parseInt(params.get("amount").toString());
			String from = params.get("from").toString();
			String to = params.get("to").toString();
			
			String gametype = from.equals("CENTER") ? to : from;
			if(amount <= 0) {
				return packJSON(Constant.BooleanByte.NO, isEnglish?"Transfer amount can not be less than 1 yuan":"转账金额不能少于1元");
			}
			if(!Enum_GameType.validate(gametype)) {
				return packJSON(Constant.BooleanByte.NO, isEnglish?"Game Type Error.":Enum_MSG.游戏类型错误.desc);
			}
			log.Debug("游戏类型验证通过....");
			
			EnterpriseEmployee employee = enterpriseEmployeeService.takeEnterpriseEmployee(params);

			if (employee == null) {
				return packJSON(Constant.BooleanByte.NO, isEnglish?"Account error!":"用户账号错误!");
			}
			
			if (!employee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.会员.value)) {
				return packJSON(Constant.BooleanByte.NO, isEnglish?"Non-member user.":"非会员用户!");
			}
			// 获取用户品牌
			String brandcode = employee.getBrandcode();
			if(isFun2 == false) {//如果是方案2则需要忽略平台维护的状态
				if(isOpenGame(brandcode, gametype,gameService.takeBrandGames(brandcode,EnterpriseOperatingBrandGame.Enum_flag.正常.value)) == null) {
					return packJSON(Constant.BooleanByte.NO, isEnglish?"The game is being maintained.":Enum_MSG.游戏维护中.desc);
				}
			}
			
			
			if(isPasswordCompelted(employee, gametype) ) {
				return packJSON(Constant.BooleanByte.NO, isEnglish?"Synchronizing game password.":Enum_MSG.游戏密码同步中.desc);
			}
			APIServiceNew api = new APIServiceNew(employee.getEnterprisecode());
			EmployeeApiAccout eea = SystemCache.getInstance().getEmployeeGameAccount(employee.getEmployeecode(), gametype);
			
			if(eea == null) {
				JSONObject jsonObject = JSONObject.fromObject(api.create(gametype, employee));
				log.Error("创建游戏账号结果："+jsonObject);
			}
			
			eea = SystemCache.getInstance().getEmployeeGameAccount(employee.getEmployeecode(), gametype);
			if(eea.getStatus().equals(EmployeeApiAccout.Enum_status.禁用.value)) {
				return packJSON(Constant.BooleanByte.NO, isEnglish?"The user is not enabled for the game.":Enum_MSG.用户未启用该游戏.desc);
			}
			
			//先查询当前的可用余额
			EnterpriseEmployeeCapitalAccount ec = capitalAccountService.takeCurrencyAccount(employeecode);
			if(ec == null){
				return packJSON(Constant.BooleanByte.NO, isEnglish?"The funds account does not exist.":Enum_MSG.资金账户不存在.desc);
			} 
			// 先查询余额
			JSONObject jsonObject = JSONObject.fromObject(api.balance(gametype, employeecode));
			if(!jsonObject.getString("code").equals("0")) {
				log.Error(employee.getEmployeecode() +"请稍后再试。转分前查询余额失败，原因："+jsonObject.getString("info"));
				return packJSON(Constant.BooleanByte.NO, (isEnglish?"Error, ":"请稍后再试。转分前查询余额失败，原因：")+jsonObject.getString("info"));
			
			}
			
			
			if (to.equals("CENTER")) {// 下分
				String patchno = OrderNewUtil.getPatchno();
				BigDecimal balance = new BigDecimal(jsonObject.getString("info"));
				
				if(amount > balance.intValue()) {
					return packJSON(Constant.BooleanByte.NO, (isEnglish?"Balance is less than ":"操作失败。转出金额" + amount +" 小于余额：")+balance.doubleValue());
				}
				
				//调用接口下分
				jsonObject = JSONObject.fromObject( api.downIntegralGame(balance, amount, gametype, eea, patchno) );
				
				if(jsonObject.getString("code").equals("0")) {
					
					log.Error(employee.getEmployeecode() + " 用户手动下分已完毕。"+gametype);
					
					userLogsService.addActivityBetRecord(new UserLogs(employee.getEnterprisecode(), employee.getEmployeecode(), employee.getLoginaccount(), 
							UserLogs.Enum_operatype.游戏信息业务, "手动下分"+gametype+"到中心钱包", null, null));
				} else {
					
					log.Error(employee.getEmployeecode() + "手动下分失败，原因："+jsonObject.getString("info"));
					return packJSON(Constant.BooleanByte.NO, (isEnglish?"Error, ":"手动下分失败，原因：")+jsonObject.getString("info"));
				}
				return packJSON(Constant.BooleanByte.YES, isEnglish?"Transfer complete.":"下分完毕");
			} else {
				// 游戏上分
				if(ec.getBalance().intValue() < amount){
					return packJSON(Constant.BooleanByte.NO, isEnglish?"Insufficient balance":"会员资金余额不足");
				} 
				
					
					String patchno = OrderNewUtil.getPatchno();
					BigDecimal beforeAmount = new BigDecimal(jsonObject.getString("info"));
					
					//调用接口上分
					jsonObject = JSONObject.fromObject( api.upIntegralGame(beforeAmount, amount, gametype, eea, patchno) );
					
					if(jsonObject.getString("code").equals("0")) {
						
						log.Error(employee.getEmployeecode() + (isEnglish?"":" 用户手动上分已完毕。")+gametype);
						
						userLogsService.addActivityBetRecord(new UserLogs(employee.getEnterprisecode(), employee.getEmployeecode(), employee.getLoginaccount(), 
								UserLogs.Enum_operatype.游戏信息业务, "手动上分到游戏平台"+gametype+"", null, null));
					} else {
						
						log.Error(employee.getEmployeecode() + "手动上分失败，原因："+jsonObject.getString("info"));
						return packJSON(Constant.BooleanByte.NO, (isEnglish?"Error, ":"手动上分失败，原因：")+jsonObject.getString("info"));
					}
					
				
				return packJSON(Constant.BooleanByte.YES, isEnglish?"Transfer complete.":"上分已完毕");
			}
		}catch(LogicTransactionException e){
			return super.packJSON(Constant.BooleanByte.NO, e.getMessage());
		}catch(LogicTransactionRollBackException e){
			return super.packJSON(Constant.BooleanByte.NO, e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, isEnglish?"System error, please try again later.":Constant.AJAX_ACTIONFAILD);
		}
	}
	@Override
	public LoggerManager getLogger() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 验证游戏开关是否打开
	 * 
	 * @param brandcode
	 * @param gameType
	 * @return
	 * @throws Exception
	 */
	private Game isOpenGame(String brandcode, String gameType,List<Game> games ) throws Exception {
		for (Game game : games) {
			if (gameType.equals(game.getGametype())) {
				return game;
			}
		}
		return null;
	}
	
	private boolean isPasswordCompelted(EnterpriseEmployee ee ,String gametype) throws Exception {
		Enum_GameType[] gametypeArray = GameEnum.Enum_GameType.values();
		//需要修改的记录放到队列中
		for (Enum_GameType enum_GameType : gametypeArray) {
			
			//正在操作的游戏需要是有密码业务的
			if(enum_GameType.gametype.equals(gametype) && enum_GameType.updatepassword == true) {
				
				//查询最新的待处理的任务
				EmployeeApiAccoutPasswordJob accoutPasswordJob = new EmployeeApiAccoutPasswordJob();
				accoutPasswordJob.setUpdatestatus(0);
				accoutPasswordJob.setGametype(gametype);
				accoutPasswordJob.setEmployeecode(ee.getEmployeecode());
				List<EmployeeApiAccoutPasswordJob> list = employeeApiAccoutPasswordJobService.findList(accoutPasswordJob);//查询上次处理失败需要处理的任务
				if(list != null && list.size() > 0) {
					return true;
				}
				
				//再查处理失败的
				accoutPasswordJob.setUpdatestatus(2);
				list.addAll(employeeApiAccoutPasswordJobService.findList(accoutPasswordJob));
				
				if(list != null && list.size() > 0) {
					return true;
				}
				
			}
		}
		
		return false;
	}
}