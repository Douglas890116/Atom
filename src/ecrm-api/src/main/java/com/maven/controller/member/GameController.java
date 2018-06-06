package com.maven.controller.member;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hy.pull.common.util.api.FileHelper;
import com.hy.pull.common.util.api.GameAPI;
import com.maven.cache.SystemCache;
import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.constant.Enum_MSG;
import com.maven.controller.BaseController;
import com.maven.entity.DataHandleMaintenance;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.EmployeeApiAccoutPasswordJob;
import com.maven.entity.EmployeeMessage;
import com.maven.entity.EmployeeMessageText;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeCapitalAccount;
import com.maven.entity.EnterpriseOperatingBrandGame;
import com.maven.entity.Game;
import com.maven.entity.GameClass;
import com.maven.entity.GameClassDetails;
import com.maven.entity.PlatformApiOutput;
import com.maven.entity.UserLogs;
import com.maven.entity.EnterpriseEmployee.Enum_employeestatus;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.game.APIService;
import com.maven.game.APIServiceNew;
import com.maven.game.APIServiceUtil;
import com.maven.game.HttpUtils;
import com.maven.game.OrderNewUtil;
import com.maven.game.enums.GameEnum;
import com.maven.game.enums.GameEnum.Enum_GameType;
import com.maven.game.enums.GameEnum.Enum_deviceType;
import com.maven.interceptor.RepeatRequestIntercept;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.DataHandleMaintenanceService;
import com.maven.service.EmployeeApiAccoutPasswordJobService;
import com.maven.service.EmployeeApiAccoutService;
import com.maven.service.EmployeeMessageService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.GameClassDetailsService;
import com.maven.service.GameClassService;
import com.maven.service.GameService;
import com.maven.service.TakeDepositRecoredService;
import com.maven.service.UserLogsService;
import com.maven.util.AttrCheckout;
import com.maven.util.Encrypt;
import com.maven.util.StringUtils;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/Game")
public class GameController extends BaseController {

	private static LoggerManager log = LoggerManager.getLogger(GameController.class.getName(),
			OutputManager.LOG_USER_GAME);

	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;

	@Autowired
	private GameService gameService;

	@Autowired
	private GameClassService gameClassService;

	@Autowired
	private GameClassDetailsService gameClassDetailsService;
	@Autowired
	private EnterpriseEmployeeCapitalAccountService capitalAccountService;
	@Autowired
	private EmployeeApiAccoutPasswordJobService employeeApiAccoutPasswordJobService;
	@Autowired
	private UserLogsService userLogsService;
	@Autowired
	private TakeDepositRecoredService takeDepositRecoredService;
	@Autowired
	private EmployeeApiAccoutService employeeApiAccoutService;
	@Autowired
	private EmployeeMessageService employeeMessageService;
	@Autowired
	private DataHandleMaintenanceService dataHandleMaintenanceService;
	
	/**
	 * 针对游戏客户端的手动方式创建游戏账号
	 * 
	 * （本接口传入参数同upIntegral）
	 * 
	 * @param gameType
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/createGameaccount", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String createGameaccount(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object, false, new String[] { "employeecode", "gametype" , "brandcode"});
			String brandcode = String.valueOf(object.get("brandcode"));
			String gametype = String.valueOf(object.get("gametype"));
			if (!Enum_GameType.validate(gametype)) {
				return Enum_MSG.参数错误.message(Enum_MSG.游戏类型错误.desc);
			}
			log.Debug("游戏类型验证通过....");
			EnterpriseEmployee ee = super.menberAuthentication(enterpriseEmployeeService.takeEnterpriseEmployee(object));
					
			/**************禁用用户不得取款**************/
			if(ee.getEmployeestatus() == Enum_employeestatus.禁用.value.byteValue()) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户已禁用.desc);
			}
			
			
			//三秒限时
			RepeatRequestIntercept.isIntercept(ee.getEmployeecode(), request.getRequestURI().replaceAll("/+", "/"), 3000);
			
			List<Game> games = gameService.takeBrandGames(brandcode,EnterpriseOperatingBrandGame.Enum_flag.正常.value);
			if (isOpenGame(brandcode, gametype,games)==null){
				
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.游戏维护中.desc);
				
			} else {
				
				//业务逻辑：检查是否已存在账号，如果不存在则需要先创建。
				APIServiceNew api = new APIServiceNew(ee.getEnterprisecode());
				EmployeeApiAccout eea = SystemCache.getInstance().getEmployeeGameAccount(ee.getEmployeecode(), gametype);
				
				if(eea == null) {
					JSONObject jsonObject = JSONObject.fromObject( api.create(gametype, ee) );
					eea = SystemCache.getInstance().getEmployeeGameAccount(ee.getEmployeecode(), gametype);
					log.Error("创建游戏账号结果："+jsonObject);
				} 
				
				log.Error(ee.getEmployeecode() + " 创建游戏账号结果："+gametype);
				
				
				String gamepassword = eea.getGamepassword();
				String loginpassword = ee.getLoginpassword2();
				if(loginpassword != null && !loginpassword.equals("")) {
					loginpassword = APIServiceUtil.decrypt(loginpassword, ee);//解密
					if(gamepassword.equals(loginpassword)) {
						gamepassword = "同登录密码";
					}
				}
				
				Map<String, String> value = new HashMap<String, String>();
				value.put("gameaccount", eea.getGameaccount());
				value.put("gamepassword", gamepassword);
				
				userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), ee.getLoginaccount(), 
						UserLogs.Enum_operatype.游戏信息业务, "手动创建了"+gametype+"游戏账号", null, null));
				
				PlatformApiOutput key = SystemCache.getInstance().getPlatformApiOutput(ee.getEnterprisecode());
				return Enum_MSG.成功.message(Encrypt.AESUNURLEncrypt(value.toString(), key.getApikey1()));
				
			}
		}catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	/**
	 * 平台下分的指定目标，需要传递分数进来
	 * 
	 * 1、只下分。
	 * 2、只下指定平台的分到中心钱包
	 * 
	 * 
	 * @param gameType
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/downIntegralGame", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String downIntegralGame(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> object = super.apiDecode(super.getRequestParamters(request));
			
//			employeecode=E000IXAF&gametype=NHQGame&brandcode=EB0000BD&money=2
			
			AttrCheckout.checkout(object, false, new String[] { "employeecode", "gametype" , "brandcode" , "money"});
			String brandcode = String.valueOf(object.get("brandcode"));
			String gametype = String.valueOf(object.get("gametype"));
			String employeecode = String.valueOf(object.get("employeecode"));
			BigDecimal accountfund = new BigDecimal(object.get("money").toString());
			
			if(accountfund.intValue() <= 0) {
				return Enum_MSG.参数错误.message("转账金额不能少于1元");
			}
			if (!Enum_GameType.validate(gametype)) {
				return Enum_MSG.参数错误.message(Enum_MSG.游戏类型错误.desc);
			}
			log.Debug("游戏类型验证通过....");
			EnterpriseEmployee ee = super.menberAuthentication(enterpriseEmployeeService.takeEnterpriseEmployee(object));
					
			/**************禁用用户不得取款**************/
			if(ee.getEmployeestatus() == Enum_employeestatus.禁用.value.byteValue()) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户已禁用.desc);
			}
			
			
			//三秒限时
			RepeatRequestIntercept.isIntercept(ee.getEmployeecode(), request.getRequestURI().replaceAll("/+", "/"), 3000);
			
			
			List<Game> games = gameService.takeBrandGames(brandcode,EnterpriseOperatingBrandGame.Enum_flag.正常.value);
			if (isOpenGame(brandcode, gametype,games)==null){
				
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.游戏维护中.desc);
				
			} else {
				
				APIServiceNew api = new APIServiceNew(ee.getEnterprisecode());
				EmployeeApiAccout eea = SystemCache.getInstance().getEmployeeGameAccount(ee.getEmployeecode(), gametype);
				
				if(eea == null) {
					return Enum_MSG.逻辑事物异常.message("该平台不存在玩家账号");
				}
				
				if(eea.getStatus().equals(EmployeeApiAccout.Enum_status.禁用.value)) {
					return Enum_MSG.用户未启用该游戏.message(Enum_MSG.游戏维护中.desc);
				}
				
				//先查询当前的可用余额
				EnterpriseEmployeeCapitalAccount ec = capitalAccountService.takeCurrencyAccount(employeecode);
				if(ec == null){
					return Enum_MSG.逻辑事物异常.message(Enum_MSG.资金账户不存在.desc);
				} 
				
				//下分前先查询余额
				JSONObject jsonObject = JSONObject.fromObject( api.balance(gametype, employeecode) );
				if(jsonObject.getString("code").equals("0")) {
					
					String patchno = OrderNewUtil.getPatchno();
					BigDecimal balance = new BigDecimal(jsonObject.getString("info"));
					
					if(accountfund.intValue() > balance.intValue()) {
						return Enum_MSG.逻辑事物异常.message("操作失败。转出金额"+accountfund.doubleValue()+" 小于余额："+balance.doubleValue());
					}
					
					//调用接口下分
					jsonObject = JSONObject.fromObject( api.downIntegralGame(balance, accountfund.intValue(), gametype, eea, patchno) );
					
					if(jsonObject.getString("code").equals("0")) {
						
						log.Error(ee.getEmployeecode() + " 用户手动下分已完毕。"+gametype);
						
						userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), ee.getLoginaccount(), 
								UserLogs.Enum_operatype.游戏信息业务, "手动下分"+gametype+"到中心钱包", null, null));
					} else {
						
						log.Error(ee.getEmployeecode() + "手动下分失败，原因："+jsonObject.getString("info"));
						return Enum_MSG.失败.message("手动下分失败，原因："+jsonObject.getString("info"));
					}
					
				} else {
					
					log.Error(ee.getEmployeecode() +"请稍后再试。手动下分前查询余额失败，原因："+jsonObject.getString("info"));
					return Enum_MSG.失败.message("请稍后再试。手动下分前查询余额失败，原因："+jsonObject.getString("info"));
				}
				
				return Enum_MSG.成功.message("手动下分已完毕");
			}
		}catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	/**
	 * 平台上分的指定目标，需要传递分数进来
	 * 
	 * 1、只上分。
	 * 2、只上当前可用余额的分，其他平台不自动下分
	 * 
	 * 
	 * @param gameType
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/upIntegralGame", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String upIntegralGame(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> object = super.apiDecode(super.getRequestParamters(request));
//			employeecode=E000IXAF&gametype=NHQGame&brandcode=EB0000BD&money=2
			AttrCheckout.checkout(object, false, new String[] { "employeecode", "gametype" , "brandcode" , "money"});
			String brandcode = String.valueOf(object.get("brandcode"));
			String gametype = String.valueOf(object.get("gametype"));
			String employeecode = String.valueOf(object.get("employeecode"));
			BigDecimal accountfund = new BigDecimal(object.get("money").toString());
			
			if(accountfund.intValue() <= 0) {
				return Enum_MSG.参数错误.message("转账金额不能少于1元");
			}
			if (!Enum_GameType.validate(gametype)) {
				return Enum_MSG.参数错误.message(Enum_MSG.游戏类型错误.desc);
			}
			log.Debug("游戏类型验证通过....");
			EnterpriseEmployee ee = super.menberAuthentication(enterpriseEmployeeService.takeEnterpriseEmployee(object));
					
			/**************禁用用户不得取款**************/
			if(ee.getEmployeestatus() == Enum_employeestatus.禁用.value.byteValue()) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户已禁用.desc);
			}
			
			
			//三秒限时
			RepeatRequestIntercept.isIntercept(ee.getEmployeecode(), request.getRequestURI().replaceAll("/+", "/"), 3000);
			
			
			List<Game> games = gameService.takeBrandGames(brandcode,EnterpriseOperatingBrandGame.Enum_flag.正常.value);
			if (isOpenGame(brandcode, gametype,games)==null){
				
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.游戏维护中.desc);
				
			} else {
				
				//	游戏密码是否已同步完毕（2分钟同步一次必要的密码）
				if( isPasswordCompelted(ee , gametype) ) {
					return Enum_MSG.逻辑事物异常.message(Enum_MSG.游戏密码同步中.desc);
				}
				
				//业务逻辑：检查是否已存在账号，如果不存在则需要先创建。然后调用转分接口进行转分。
				APIServiceNew api = new APIServiceNew(ee.getEnterprisecode());
				EmployeeApiAccout eea = SystemCache.getInstance().getEmployeeGameAccount(ee.getEmployeecode(), gametype);
				
				if(eea == null) {
					JSONObject jsonObject = JSONObject.fromObject( api.create(gametype, ee) );
					log.Error("创建游戏账号结果："+jsonObject);
				}
				eea = SystemCache.getInstance().getEmployeeGameAccount(ee.getEmployeecode(), gametype);
				
				if(eea.getStatus().equals(EmployeeApiAccout.Enum_status.禁用.value)) {
					return Enum_MSG.用户未启用该游戏.message(Enum_MSG.游戏维护中.desc);
				}
				
				//先查询当前的可用余额
				EnterpriseEmployeeCapitalAccount ec = capitalAccountService.takeCurrencyAccount(employeecode);
				if(ec == null){
					return Enum_MSG.逻辑事物异常.message(Enum_MSG.资金账户不存在.desc);
				} 
				//进行上分
				if(ec.getBalance().intValue() < accountfund.intValue() ){
					return Enum_MSG.逻辑事物异常.message("您当前的余额不足，请查看资金是否有遗留，或请充值。当前余额："+ec.getBalance());
				} 
				
				//上分前先查询余额
				JSONObject jsonObject = JSONObject.fromObject( api.balance(gametype, employeecode) );
				if(jsonObject.getString("code").equals("0")) {
					
					String patchno = OrderNewUtil.getPatchno();
					BigDecimal beforeAmount = new BigDecimal(jsonObject.getString("info"));
					
					//调用接口上分
					jsonObject = JSONObject.fromObject( api.upIntegralGame(beforeAmount, accountfund.intValue(), gametype, eea, patchno) );
					
					if(jsonObject.getString("code").equals("0")) {
						
						log.Error(ee.getEmployeecode() + " 用户手动上分已完毕。"+gametype);
						
						userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), ee.getLoginaccount(), 
								UserLogs.Enum_operatype.游戏信息业务, "手动上分到游戏平台"+gametype+"", null, null));
					} else {
						
						log.Error(ee.getEmployeecode() + "手动上分失败，原因："+jsonObject.getString("info"));
						return Enum_MSG.失败.message("手动上分失败，原因："+jsonObject.getString("info"));
					}
					
				} else {
					
					log.Error(ee.getEmployeecode() +"请稍后再试。手动上分前查询余额失败，原因："+jsonObject.getString("info"));
					return Enum_MSG.失败.message("请稍后再试。手动上分前查询余额失败，原因："+jsonObject.getString("info"));
				}
				
				return Enum_MSG.成功.message("手动上分已完毕");
			}
		}catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	/**
	 * 针对游戏客户端的手动方式上分
	 * 
	 * 首先需要检查是否有游戏账号，如果没有需要创建，然后对其他游戏下分，最后再上分。其实类似下面的play方法。
	 * 
	 * 但play方法最后会请求登录login接口相关业务，所以不能公用，以免浪费资源
	 * 
	 * 
	 * @param gameType
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/upIntegral", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String upIntegral(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object, false, new String[] { "employeecode", "gametype" , "brandcode"});
			String brandcode = String.valueOf(object.get("brandcode"));
			String gametype = String.valueOf(object.get("gametype"));
			if (!Enum_GameType.validate(gametype)) {
				return Enum_MSG.参数错误.message(Enum_MSG.游戏类型错误.desc);
			}
			log.Debug("游戏类型验证通过....");
			EnterpriseEmployee ee = super.menberAuthentication(enterpriseEmployeeService.takeEnterpriseEmployee(object));
					
			/**************禁用用户不得取款**************/
			if(ee.getEmployeestatus() == Enum_employeestatus.禁用.value.byteValue()) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户已禁用.desc);
			}
			
			
			//三秒限时
			RepeatRequestIntercept.isIntercept(ee.getEmployeecode(), request.getRequestURI().replaceAll("/+", "/"), 3000);
			
			
			List<Game> games = gameService.takeBrandGames(brandcode,EnterpriseOperatingBrandGame.Enum_flag.正常.value);
			if (isOpenGame(brandcode, gametype,games)==null){
				
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.游戏维护中.desc);
				
			} else {
				
				//	游戏密码是否已同步完毕（2分钟同步一次必要的密码）
				if( isPasswordCompelted(ee , gametype) ) {
					return Enum_MSG.逻辑事物异常.message(Enum_MSG.游戏密码同步中.desc);
				}
				
				//业务逻辑：检查是否已存在账号，如果不存在则需要先创建。然后调用转分接口进行转分。
				APIServiceNew api = new APIServiceNew(ee.getEnterprisecode());
				EmployeeApiAccout eea = SystemCache.getInstance().getEmployeeGameAccount(ee.getEmployeecode(), gametype);
				
				if(eea == null) {
					JSONObject jsonObject = JSONObject.fromObject( api.create(gametype, ee) );
					log.Error("创建游戏账号结果："+jsonObject);
				}
				eea = SystemCache.getInstance().getEmployeeGameAccount(ee.getEmployeecode(), gametype);
				
				
				if(eea.getStatus().equals(EmployeeApiAccout.Enum_status.禁用.value)) {
					return Enum_MSG.用户未启用该游戏.message(null);
				}
				
				api.transfer(gametype, eea.getEmployeecode(), null);//调用接口上分
				
				log.Error(ee.getEmployeecode() + " 用户手动上分到客户端已完毕。"+gametype);
				
				userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), ee.getLoginaccount(), 
						UserLogs.Enum_operatype.游戏信息业务, "手动上分到游戏平台"+gametype+"", null, null));
				
				return Enum_MSG.成功.message("手动上分到客户端已完毕");
			}
		}catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}

	
	/**
	 * 进入游戏
	 * 
	 * 
	 * @param gameType
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/play", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String play(HttpServletRequest request, HttpSession session) {
		try {
			log.Debug("Begin进入游戏....");
			Map<String, Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object, false, new String[] { "brandcode", "gametype"},new String[]{"playtype"});
			String brandcode = String.valueOf(object.get("brandcode"));
			String gametype = String.valueOf(object.get("gametype"));
			String playtype = String.valueOf(object.get("playtype"));
			if (!Enum_GameType.validate(gametype)) {
				return Enum_MSG.参数错误.message(Enum_MSG.游戏类型错误.desc);
			}
			log.Debug("游戏类型验证通过....");
			EnterpriseEmployee ee = super.menberAuthentication(enterpriseEmployeeService.takeEnterpriseEmployee(object));
			
			/**************禁用用户不得取款**************/
			if(ee.getEmployeestatus() == Enum_employeestatus.禁用.value.byteValue()) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户已禁用.desc);
			}
			
			/***检查平台级的禁用（不会参与10分钟一次的自动检查平台维护或正常状态的第三种状态）***/
			DataHandleMaintenance handleMaintenance = dataHandleMaintenanceService.selectByPrimaryKey(gametype);
			if( handleMaintenance != null && handleMaintenance.getFlag().toString().equals(DataHandleMaintenance.Enum_flag.禁用.value.toString())) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.游戏维护中.desc);
			}
					
			//三秒限时
			RepeatRequestIntercept.isIntercept(ee.getEmployeecode(), request.getRequestURI().replaceAll("/+", "/"), 3000);
			
			List<Game> games = gameService.takeBrandGames(brandcode,EnterpriseOperatingBrandGame.Enum_flag.正常.value);
			if (isOpenGame(brandcode, gametype,games)==null){
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.游戏维护中.desc);
			} else {
				log.Debug("调用playAction....");
				
				//游戏密码是否已同步完毕（2分钟同步一次必要的密码）
				if( isPasswordCompelted(ee , gametype) ) {
					return Enum_MSG.逻辑事物异常.message(Enum_MSG.游戏密码同步中.desc);
				}
				
				
				//获取客户站点的请求URL地址
				Map<String, String> urlMap = new HashMap<String, String>();
				urlMap.put("homeurl", String.valueOf(object.get("homeurl")));//网站首页
				urlMap.put("lobbyurl", String.valueOf(object.get("lobbyurl")));//网站大厅页
				urlMap.put("depositurl", String.valueOf(object.get("depositurl")));//网站充值页面
				urlMap.put("withdrawurl", String.valueOf(object.get("withdrawurl")));//网站取款页面
				urlMap.put("usercenterurl", String.valueOf(object.get("usercenterurl")));//网站会员中心页面
				System.out.println("登录游戏参数=====================PC============"+object);
				String result = playForStart(gametype,playtype, ee,games, urlMap);
				
				
				userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), ee.getLoginaccount(), 
						UserLogs.Enum_operatype.游戏信息业务, "登录游戏平台"+gametype+"", null, null));
				
				JSONObject resultObject = JSONObject.fromObject(result);
				if(resultObject.getString("code").equals("1")) {
					return Enum_MSG.成功.message(resultObject.getString("info"));
				} else {
					return Enum_MSG.逻辑事物异常.message(resultObject.getString("info"));
				}
				
			}
		}catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	
	/**
	 * 游戏状态
	 * 
	 * @param gameType
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/gamestatus", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String isRunGame(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object, false, new String[] { "brandcode", "gameType" });
			String brandcode = String.valueOf(object.get("brandcode"));
			String gameType = String.valueOf(object.get("gameType"));
			if (!Enum_GameType.validate(gameType)) {
				return Enum_MSG.参数错误.message(Enum_MSG.游戏类型错误.desc);
			}
			List<Game> games = gameService.takeBrandGames(brandcode,EnterpriseOperatingBrandGame.Enum_flag.正常.value);
			if (isOpenGame(brandcode, gameType,games)==null) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.游戏维护中.desc);
			}
			return Enum_MSG.成功.message(String.valueOf(true));
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		} catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}

	/**
	 * 进入游戏(APP唤醒与H5唤醒)
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/GWakeUp", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String playForPhone(HttpServletRequest request) {
		try {
			Map<String, Object> __object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(__object, false,
					new String[] { "enterprisecode", "brandcode", "employeecode", "gametype", "application" ,"Referer"});
			String enterprisecode = String.valueOf(__object.get("enterprisecode"));
			String brandcode = String.valueOf(__object.get("brandcode"));
			String gametype = String.valueOf(__object.get("gametype"));
			String application = String.valueOf(__object.get("application"));
			String employeecode = String.valueOf(__object.get("employeecode"));
			
			System.out.println("登录游戏参数=====================H5============"+__object);
			
			/***检查平台级的禁用（不会参与10分钟一次的自动检查平台维护或正常状态的第三种状态）***/
			DataHandleMaintenance handleMaintenance = dataHandleMaintenanceService.selectByPrimaryKey(gametype);
			if( handleMaintenance != null && handleMaintenance.getFlag().toString().equals(DataHandleMaintenance.Enum_flag.禁用.value.toString())) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.游戏维护中.desc);
			}
			
			/********************************游戏大类。SX\DZ\等***************************************/
			String playtype = String.valueOf(__object.get("playtype"));//
			/***
			// 是否支持唤醒
			if (!gametype.equals(Enum_GameType.环球.gametype)&& 
				!gametype.equals(Enum_GameType.AV老虎机.gametype)&& 
				!gametype.equals(Enum_GameType.沙巴.gametype)&& 
				!gametype.equals(Enum_GameType.东方.gametype)&& 
				!gametype.equals(Enum_GameType.PT.gametype)&& 
				!gametype.equals(Enum_GameType.WIN88.gametype)&& 
				!gametype.equals(Enum_GameType.波音.gametype)&& 
				!gametype.equals(Enum_GameType.沙龙.gametype)&& 
				!gametype.equals(Enum_GameType.MG.gametype)&& 
				!gametype.equals(Enum_GameType.TTG.gametype)&& 
				!gametype.equals(Enum_GameType.PNG.gametype)&& 
				!gametype.equals(Enum_GameType.DZDY.gametype)&&
				!gametype.equals(Enum_GameType.SGS.gametype)&& 
				!gametype.equals(Enum_GameType.GG.gametype)&& 
				!gametype.equals(Enum_GameType.AG.gametype)&&
				!gametype.equals(Enum_GameType.EBet.gametype)&&
				!gametype.equals(Enum_GameType.GB彩票.gametype)&&
				!gametype.equals(Enum_GameType.TGPlayer.gametype)&&
				!gametype.equals(Enum_GameType.QTtech.gametype)&&
				!gametype.equals(Enum_GameType.GGPoker.gametype)&&
				!gametype.equals(Enum_GameType.eIBCGame.gametype)&&
				!gametype.equals(Enum_GameType.HAB.gametype)&&
				!gametype.equals(Enum_GameType.YoPLAY.gametype)&&
				!gametype.equals(Enum_GameType.IDN.gametype)){
				return Enum_MSG.参数错误.message(Enum_MSG.暂不支持.desc);
			}
			*/
			
			// 游戏是否开放
			List<Game> games = gameService.takeBrandGames(brandcode,EnterpriseOperatingBrandGame.Enum_flag.正常.value);
			Game game = this.isOpenGame(brandcode, gametype,games);
			if (game == null) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.游戏维护中.desc);
			}
			
			//	游戏密码是否已同步完毕（2分钟同步一次必要的密码）
			EnterpriseEmployee __ee = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
			
			/**************禁用用户不得取款**************/
			if(__ee.getEmployeestatus() == Enum_employeestatus.禁用.value.byteValue()) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户已禁用.desc);
			}
			
			
			if( isPasswordCompelted(__ee , gametype) ) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.游戏密码同步中.desc);
			}

			// 获取游戏账号
			EmployeeApiAccout __eaa = SystemCache.getInstance().getEmployeeGameAccount(employeecode, gametype);

			
			// 创建游戏账号
			if(__eaa == null) {
				
				APIServiceNew api = new APIServiceNew(enterprisecode);
				JSONObject object = JSONObject.fromObject( api.create(gametype, __ee) );
				log.Error("创建游戏账号结果："+object);
				
				if(object.getString("code").equals("0")) {
					__eaa = SystemCache.getInstance().getEmployeeGameAccount(__ee.getEmployeecode(), gametype);
				} else {
					return Enum_MSG.失败.message(object.getString("info"));
				}
				
			}
			
			if(__eaa.getStatus().equals(EmployeeApiAccout.Enum_status.禁用.value)) {
				return Enum_MSG.用户未启用该游戏.message(null);
			}
			
			
			userLogsService.addActivityBetRecord(new UserLogs(__eaa.getEnterprisecode(), __eaa.getEmployeecode(), __eaa.getLoginaccount(), 
					UserLogs.Enum_operatype.游戏信息业务, "登录游戏平台"+gametype+"", null, null));
			
			//获取客户站点的请求URL地址
			Map<String, String> urlMap = new HashMap<String, String>();
			urlMap.put("homeurl", String.valueOf(__object.get("homeurl")));//网站首页
			urlMap.put("lobbyurl", String.valueOf(__object.get("lobbyurl")));//网站大厅页
			urlMap.put("depositurl", String.valueOf(__object.get("depositurl")));//网站充值页面
			urlMap.put("withdrawurl", String.valueOf(__object.get("withdrawurl")));//网站取款页面
			urlMap.put("usercenterurl", String.valueOf(__object.get("usercenterurl")));//网站会员中心页面
			
			String result = getURIForPhone(gametype, application, employeecode, game, __eaa, null, playtype, urlMap);
			
			JSONObject resultObject = JSONObject.fromObject(result);
			if(resultObject.getString("code").equals("1")) {
				return Enum_MSG.成功.message(resultObject.getString("info"));
			} else {
				return Enum_MSG.逻辑事物异常.message(resultObject.getString("info"));
			}
			
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		} catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.失败.message(Constant.AJAX_DECODEREFUSE);
		}
	}
	
	/**
	 * 游戏试玩
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/tryPlay", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String tryPlay(HttpServletRequest request, Model model) {
		try {
			Map<String, Object> __object = super.apiDecode(super.getRequestParamters(request));
			String __enterprisecode = String.valueOf(__object.get("enterprisecode"));
			String __gameType = String.valueOf(__object.get("gametype"));
			String __playtype = String.valueOf(__object.get("playtype"));
			if (GameEnum.Enum_GameType.AG.gametype.equals(__gameType)) {
				String __url = new APIService(__enterprisecode).tryPlay(__gameType,__playtype, Enum_deviceType.电脑.code);
				if (__url.startsWith("http://")) {
					return Enum_MSG.成功.message(__url);
				} else {
					return Enum_MSG.系统错误.message(null);
				}
			}
			if(GameEnum.Enum_GameType.环球.gametype.equals(__gameType)){
				return Enum_MSG.成功.message("http://www.hq969.com/");
			}
			if(GameEnum.Enum_GameType.东方.gametype.equals(__gameType)){
				return Enum_MSG.成功.message(HttpUtils.get("http://77777f.com/service/ogtest"));
			}
			if(GameEnum.Enum_GameType.AV老虎机.gametype.equals(__gameType)){
				return Enum_MSG.成功.message("http://jp.tokyo.com.s3-website-ap-northeast-1.amazonaws.com/gspotslots/");
			}
			
			
			
			return Enum_MSG.参数错误.message(Enum_MSG.游戏类型错误.desc);
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		} catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}

	/**
	 * 查询单个账户余额
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/balance", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String balance(HttpServletRequest request) {
		try {
			Map<String, Object> object = super.apiDecode(super.getRequestParamters(request));
			String gameType = String.valueOf(object.get("gameType"));
			if (!Enum_GameType.validate(gameType)) {
				return Enum_MSG.参数错误.message(Enum_MSG.游戏类型错误.desc);
			}
			EnterpriseEmployee ee = super.menberAuthentication(enterpriseEmployeeService.takeEnterpriseEmployee(object));
			
			/**************禁用用户不得取款**************/
			if(ee.getEmployeestatus() == Enum_employeestatus.禁用.value.byteValue()) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户已禁用.desc);
			}
			
			return new APIServiceNew(ee.getEnterprisecode()).balance(gameType, ee.getEmployeecode());
			

		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		} catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}

	/**
	 * 获取用户所有余额
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/balances", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String balances(HttpServletRequest request) {
		try {
			Map<String, Object> object = super.apiDecode(super.getRequestParamters(request));
			EnterpriseEmployee ee = super.menberAuthentication(enterpriseEmployeeService.takeEnterpriseEmployee(object));
			
			/**************禁用用户不得取款**************/
			if(ee.getEmployeestatus() == Enum_employeestatus.禁用.value.byteValue()) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户已禁用.desc);
			}
			
			return new APIServiceNew(ee.getEnterprisecode()).balances(ee.getEmployeecode(), ee.getBrandcode());
			
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		} catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	/**
	 * 获取用户所有余额，多列
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/balancesAll", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String balancesAll(HttpServletRequest request) {
		try {
			Map<String, Object> object = super.apiDecode(super.getRequestParamters(request));
			EnterpriseEmployee ee = super.menberAuthentication(enterpriseEmployeeService.takeEnterpriseEmployee(object));
			
			/**************禁用用户不得取款**************/
			if(ee.getEmployeestatus() == Enum_employeestatus.禁用.value.byteValue()) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户已禁用.desc);
			}
			
			List<Game> BrandGameAll = gameService.takeBrandGames(ee.getBrandcode(),EnterpriseOperatingBrandGame.Enum_flag.正常.value);
			
			List<Map<String, String>> returnObject = new ArrayList<Map<String,String>>();
			Map<String, String> BalanceAll = new APIServiceNew(ee.getEnterprisecode()).balancesAll(ee.getEmployeecode(), ee.getBrandcode());
			
			//中心钱包永远在第一位
			String balance = BalanceAll.get("CENTER");
			Map<String, String> data = new HashMap<String, String>();
			data.put("gamecode", "00000");
			data.put("gametype", "CENTER");
			data.put("gamename", "我的钱包");
			data.put("gamebalance", balance == null ? "0.00" : balance);
			returnObject.add(data);
			
			for (Game game : BrandGameAll) {
				String balancex = BalanceAll.get(game.getGametype());
				Map<String, String> datax = new HashMap<String, String>();
				datax.put("gamecode", game.getGamecode());
				datax.put("gametype", game.getGametype());
				datax.put("gamename", game.getGamename());
				datax.put("gamebalance", balancex == null ? "0.00" : balancex);
				
				returnObject.add(datax);
			}
			
			return Enum_MSG.成功.message(returnObject); 
			
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		} catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	/**
	 * 获取用户指定时间段内的存取款统计等
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/allMoney", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String allMoney(HttpServletRequest request) {
		try {
			Map<String, Object> object = super.apiDecode(super.getRequestParamters(request));
			EnterpriseEmployee ee = super.menberAuthentication(enterpriseEmployeeService.takeEnterpriseEmployee(object));
			
			/**************禁用用户不得取款**************/
			if(ee.getEmployeestatus() == Enum_employeestatus.禁用.value.byteValue()) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户已禁用.desc);
			}
			
			//获取存取款、领取优惠额
			object.put("start_time", null);
			object.put("end_time", null);
			Map<String, Object> object2 = takeDepositRecoredService.call_userAllMoney(object);
			
//			#{total_deposit_money,mode=OUT,jdbcType=DECIMAL},
//			#{total_take_money,mode=OUT,jdbcType=DECIMAL},
//			#{total_activity_money,mode=OUT,jdbcType=DECIMAL},
//			#{total_bet_money,mode=OUT,jdbcType=DECIMAL},
//			#{total_net_money,mode=OUT,jdbcType=DECIMAL},
//			#{total_stream_money,mode=OUT,jdbcType=DECIMAL}
			
			return Enum_MSG.成功.message(object2);
			
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		} catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}

	/**
	 * 获取PT游戏账号
	 * 
	 * @return
	 */
	@RequestMapping(value = "/PtLogin", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getPtLoginAccount(HttpServletRequest request) {
		try {
			Map<String, Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object, false, new String[] { "enterprisecode", "employeecode" });
			EnterpriseEmployee ee = super.menberAuthentication(
					enterpriseEmployeeService.takeEnterpriseEmployee(object));
			EmployeeApiAccout account = SystemCache.getInstance().getEmployeeGameAccount(ee.getEmployeecode(),
					Enum_GameType.PT.gametype);
			if (account == null) {
				APIServiceNew hyApiHelper = new APIServiceNew(ee.getEnterprisecode());
				if (account == null) {
					
					String info = hyApiHelper.create(Enum_GameType.PT.gametype, ee);
					
					JSONObject jsonObject = JSONObject.fromObject(info);
					if (jsonObject.getString("code").equals("0")) {
						
						account = SystemCache.getInstance().getEmployeeGameAccount(ee.getEmployeecode(),Enum_GameType.PT.gametype);
								
					} else {
						
						return Enum_MSG.失败.message(jsonObject.getString("info"));
					}
				}
			}
			PlatformApiOutput key = SystemCache.getInstance().getPlatformApiOutput(ee.getEnterprisecode());
			StringBuffer sbf = new StringBuffer();
			sbf.append("user=").append(account.getGameaccount()).append("&password=").append(account.getGamepassword());
			return Enum_MSG.成功.message(Encrypt.AESUNURLEncrypt(sbf.toString(), key.getApikey1()));
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		} catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}

	/**
	 * 获取PT游戏分类
	 * 
	 * @return
	 */
	@RequestMapping(value = "/PtGameMenu", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getPtGameMenu() {
		try {
			List<GameClass> menu = gameClassService.takeFormatMenu(Enum_GameType.PT.gametype);
			return Enum_MSG.成功.message(menu);
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		} catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}

	/**
	 * 获取PT分类详细游戏
	 * 
	 * @return
	 */
	@RequestMapping(value = "/PtGameDetailsMenu", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getPtGameDetailsMenu(HttpServletRequest request) {
		try {
			GameClassDetails details = super.getRequestParamters(request, GameClassDetails.class);
			AttrCheckout.checkout(details, false, new String[] { "gameclasscode" });
			List<GameClassDetails> list = gameClassDetailsService.select(details);
			return Enum_MSG.成功.message(list);
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		} catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}

	/**
	 * 好赢移动端登陆
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getLoginInfo", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getLoginInfo(HttpServletRequest request) {
		try {
			Map<String, Object> object = super.fixedCommunicationDecode(
					SystemConstant.getProperties(Constant.HY_APP_MD5Key),
					SystemConstant.getProperties(Constant.HY_APP_AESKey), super.getRequestParamters(request));
			AttrCheckout.checkout(object, false, new String[] { "user", "password" });
			EnterpriseEmployee loginParam = new EnterpriseEmployee();
			loginParam.setLoginaccount(String.valueOf(object.get("user")));
			loginParam.setLoginpassword(Encrypt.MD5(String.valueOf(object.get("password"))));
			EnterpriseEmployee ee = enterpriseEmployeeService.getPhoneLogin(loginParam);
			if (ee == null)
				return Enum_MSG.失败.message(Enum_MSG.用户名或密码错误.desc);

			EmployeeApiAccout account = SystemCache.getInstance().getEmployeeGameAccount(ee.getEmployeecode(),Enum_GameType.环球.gametype);
			
			APIServiceNew api = new APIServiceNew(ee.getEnterprisecode());
			if (account == null) {
				String info = api.create(Enum_GameType.环球.gametype, ee);
				
				JSONObject jsonObject = JSONObject.fromObject(info);
				if (jsonObject.getString("code").equals("0")) {
					
					account = SystemCache.getInstance().getEmployeeGameAccount(ee.getEmployeecode(),Enum_GameType.环球.gametype);
							
				} else {
					return Enum_MSG.失败.message(jsonObject.getString("info"));
				}
			}
			List<Game> games = gameService.takeBrandGames(ee.getBrandcode(),EnterpriseOperatingBrandGame.Enum_flag.正常.value);
			api.transfer(Enum_GameType.环球.gametype, ee.getEmployeecode(),games);
			StringBuffer sbf = new StringBuffer();
			sbf.append("user=").append(account.getGameaccount()).append("&password=").append(account.getGamepassword());
			return Enum_MSG.成功.message(Encrypt.AESEncrypt(sbf.toString(), SystemConstant.getProperties(Constant.HY_APP_AESKey)));
					
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.失败.message(e.getMessage());
		}
	}

	/**
	 * 验证游戏密码是否已改完，如果没同步完成则不能上下分和进入游戏，否则会异常
	 * 
	 * 只检查有密码的游戏平台
	 * 
	 * @param brandcode
	 * @param gameType
	 * @return
	 * @throws Exception
	 */
	private Enum_GameType[] gametypeArray = GameEnum.Enum_GameType.values();
	private boolean isPasswordCompelted(EnterpriseEmployee ee ,String gametype) throws Exception {
		
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

	/**
	 * 进入游戏
	 * 
	 * @param gametype
	 * @param ee
	 * @return
	 * @throws Exception
	 */
	private String playForStart(String gametype,String playtype, EnterpriseEmployee ee,List<Game> games, Map<String, String> urlMap) throws Exception {
		
		//业务逻辑：检查是否已存在账号，如果不存在则需要先创建。然后调用play接口进行转分、登录、返回游戏地址
		APIServiceNew api = new APIServiceNew(ee.getEnterprisecode());
		
		EmployeeApiAccout eea = SystemCache.getInstance().getEmployeeGameAccount(ee.getEmployeecode(), gametype);
		
		if(eea == null) {
			
			JSONObject object = JSONObject.fromObject( api.create(gametype, ee) );
			log.Error("创建游戏账号结果："+object);
			
		}
		
		eea = SystemCache.getInstance().getEmployeeGameAccount(ee.getEmployeecode(), gametype);
		
		if(eea.getStatus().equals(EmployeeApiAccout.Enum_status.禁用.value)) {
			return Enum_MSG.失败.message(Enum_MSG.用户未启用该游戏.desc);
		}
		
		JSONObject object = JSONObject.fromObject( api.play(gametype, playtype, ee.getEmployeecode(), games, Enum_deviceType.电脑.code, playtype, urlMap) );
		log.Error("登录游戏结果："+object);
		
		if(object.getString("code").equals("0")) {
			return Enum_MSG.成功.message(object.getString("info"));//返回登录地址
			
		} else {
//			throw new Exception(object.getString("info"));
			return Enum_MSG.失败.message(object.getString("info"));
		}
		
		
	}
	
	

	/**
	 * 获取唤起命令
	 * @param gametype
	 * @param application
	 * @param employeecode
	 * @param game
	 * @param __eaa
	 * @param __api
	 * @return
	 * @throws Exception
	 */
	private String getURIForPhone(String gametype, String application, String employeecode, Game game, EmployeeApiAccout __eaa,
			APIService __apii, String playtype, Map<String, String> urlMap) throws Exception {
		
		if(__eaa.getStatus().equals(EmployeeApiAccout.Enum_status.禁用.value)) {
			return Enum_MSG.用户未启用该游戏.message(Enum_MSG.用户未启用该游戏.desc);
		}
		
		// 封装唤起url
		List<Game> games = gameService.takeBrandGames(__eaa.getBrandcode(),EnterpriseOperatingBrandGame.Enum_flag.正常.value);
		if (gametype.equals(Enum_GameType.环球.gametype)) {//jason 20161218.mg与ttg一样
			
			
			/****************新版***************************/
			String userpwd = Encrypt.AESEncrypt("user=" + __eaa.getGameaccount() + "&password=" + __eaa.getGamepassword(),SystemConstant.getProperties(Constant.HY_APP_AESKey));
					
			if (application.equals("android")) {
				return Enum_MSG.成功.message(game.getAndroid().replace("{0}", userpwd));
			} else if (application.equals("iso")) {
				return Enum_MSG.成功.message(game.getIso().replace("{0}", userpwd));
			} else if (application.equals("h5")) {
				APIServiceNew api = new APIServiceNew(__eaa.getEnterprisecode());
				JSONObject object = JSONObject.fromObject( api.play(gametype, playtype, employeecode, games, Enum_deviceType.手机.code, "", urlMap) );
				log.Error("H5登录游戏结果NHQ："+object);
				if(object.getString("code").equals("0")) {
					return Enum_MSG.成功.message(object.getString("info"));//返回登录地址
				} else {
					return Enum_MSG.失败.message(object.getString("info"));
				}
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.参数类型不存在.desc.replace("{0}", application));
			}
			
			
		} else if (gametype.equals(Enum_GameType.AV老虎机.gametype)) {
			if (application.equals("h5")) {
				return Enum_MSG.成功.message(__apii.play(gametype,"", employeecode,games,Enum_deviceType.手机.code));
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.参数类型不存在.desc.replace("{0}", application));
			}
		} else if (gametype.equals(Enum_GameType.MG.gametype)) {//jason 20161218.mg与ttg一样
			if (application.equals("h5")) {
				
				APIServiceNew api = new APIServiceNew(__eaa.getEnterprisecode());
				JSONObject object = JSONObject.fromObject( api.play(gametype, playtype, employeecode, games, Enum_deviceType.手机.code, "", urlMap) );
				log.Error("H5登录游戏结果MG："+object);
				if(object.getString("code").equals("0")) {
					return Enum_MSG.成功.message(object.getString("info"));//返回登录地址
				} else {
					return Enum_MSG.失败.message(object.getString("info"));
				}
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.参数类型不存在.desc.replace("{0}", application));
			}
			
		} else if (gametype.equals(Enum_GameType.HAB.gametype)) {//jason 20161218.mg与ttg一样
			if (application.equals("h5")) {
				
				APIServiceNew api = new APIServiceNew(__eaa.getEnterprisecode());
				JSONObject object = JSONObject.fromObject( api.play(gametype, playtype, employeecode, games, Enum_deviceType.手机.code, "", urlMap) );
				log.Error("H5登录游戏结果HB哈巴："+object);
				if(object.getString("code").equals("0")) {
					return Enum_MSG.成功.message(object.getString("info"));//返回登录地址
				} else {
					return Enum_MSG.失败.message(object.getString("info"));
				}
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.参数类型不存在.desc.replace("{0}", application));
			}
			
		} else if (gametype.equals(Enum_GameType.SGS.gametype)) {//jason 20161218.mg与ttg一样
			if (application.equals("h5")) {
				
				APIServiceNew api = new APIServiceNew(__eaa.getEnterprisecode());
				JSONObject object = JSONObject.fromObject( api.play(gametype, playtype, employeecode, games, Enum_deviceType.手机.code, "", urlMap) );
				log.Error("H5登录游戏结果SGS："+object);
				if(object.getString("code").equals("0")) {
					return Enum_MSG.成功.message(object.getString("info"));//返回登录地址
				} else {
					return Enum_MSG.失败.message(object.getString("info"));
				}
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.参数类型不存在.desc.replace("{0}", application));
			}
			
		} else if (gametype.equals(Enum_GameType.TTG.gametype)) {//jason 20161218.mg与ttg一样
			if (application.equals("h5")) {
				
				APIServiceNew api = new APIServiceNew(__eaa.getEnterprisecode());
				JSONObject object = JSONObject.fromObject( api.play(gametype, playtype, employeecode, games, Enum_deviceType.手机.code, "", urlMap) );
				log.Error("H5登录游戏结果TTG："+object);
				if(object.getString("code").equals("0")) {
					return Enum_MSG.成功.message(object.getString("info"));//返回登录地址
				} else {
					return Enum_MSG.失败.message(object.getString("info"));
				}
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.参数类型不存在.desc.replace("{0}", application));
			}
			
		} else if (gametype.equals(Enum_GameType.PNG.gametype)) {//jason 20161218.mg与ttg一样
			if (application.equals("h5")) {
				
				APIServiceNew api = new APIServiceNew(__eaa.getEnterprisecode());
				JSONObject object = JSONObject.fromObject( api.play(gametype, playtype, employeecode, games, Enum_deviceType.手机.code, "", urlMap) );
				log.Error("H5登录游戏结果PNG："+object);
				if(object.getString("code").equals("0")) {
					return Enum_MSG.成功.message(object.getString("info"));//返回登录地址
				} else {
					return Enum_MSG.失败.message(object.getString("info"));
				}
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.参数类型不存在.desc.replace("{0}", application));
			}
			
		} else if (gametype.equals(Enum_GameType.DZDY.gametype)) {//jason 20161218.mg与ttg一样
			if (application.equals("h5")) {
				
				APIServiceNew api = new APIServiceNew(__eaa.getEnterprisecode());
				JSONObject object = JSONObject.fromObject( api.play(gametype, playtype, employeecode, games, Enum_deviceType.手机.code, "", urlMap) );
				log.Error("H5登录游戏结果DZDY："+object);
				if(object.getString("code").equals("0")) {
					return Enum_MSG.成功.message(object.getString("info"));//返回登录地址
				} else {
					return Enum_MSG.失败.message(object.getString("info"));
				}
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.参数类型不存在.desc.replace("{0}", application));
			}
			
		} else if (gametype.equals(Enum_GameType.AG.gametype)) {//jason 20161218.mg与ttg一样
			if (application.equals("h5")) {
				
				/*****新版*****/
				
				String gamecode = "6";//捕鱼
				if(playtype != null ) {
					if(playtype.equals("SX")) {
						gamecode = "11";//移动端大厅
					} else if(playtype.equals("XIN")) {
						gamecode = "8";//8       电子游戏  (XIN电子游戏大厅) 
					}
				}
				APIServiceNew api = new APIServiceNew(__eaa.getEnterprisecode());
				JSONObject object = JSONObject.fromObject( api.play(gametype, playtype, employeecode, games, Enum_deviceType.手机.code, gamecode, urlMap) );//
				log.Error("H5登录游戏结果AG："+object);
				if(object.getString("code").equals("0")) {
					return Enum_MSG.成功.message(object.getString("info"));//返回登录地址
				} else {
					return Enum_MSG.失败.message(object.getString("info"));
				}
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.参数类型不存在.desc.replace("{0}", application));
			}
		} else if (gametype.equals(Enum_GameType.PT.gametype) || gametype.equals(Enum_GameType.WIN88.gametype)) {
			if (application.equals("h5")) {
				
				/*****新版*****/
				APIServiceNew api = new APIServiceNew(__eaa.getEnterprisecode());
				JSONObject object = JSONObject.fromObject( api.play(gametype, playtype, employeecode, games, Enum_deviceType.手机.code, "", urlMap) );
				log.Error("H5登录游戏结果PT："+object);
				if(object.getString("code").equals("0")) {
					return Enum_MSG.成功.message(object.getString("info"));//返回登录地址
				} else {
					return Enum_MSG.失败.message(object.getString("info"));
				}
				
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.参数类型不存在.desc.replace("{0}", application));
			}
		} else if (gametype.equals(Enum_GameType.沙龙.gametype)) {//jason 20161218.mg与ttg一样
			if (application.equals("h5")) {
				
				APIServiceNew api = new APIServiceNew(__eaa.getEnterprisecode());
				JSONObject object = JSONObject.fromObject( api.play(gametype, playtype, employeecode, games, Enum_deviceType.手机.code, "", urlMap) );
				log.Error("H5登录游戏结果沙龙："+object);
				if(object.getString("code").equals("0")) {
					return Enum_MSG.成功.message(object.getString("info"));//返回登录地址
				} else {
					return Enum_MSG.失败.message(object.getString("info"));
				}
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.参数类型不存在.desc.replace("{0}", application));
			}
			
		} else if (gametype.equals(Enum_GameType.GG.gametype)) {//jason 20161218.mg与ttg一样
			if (application.equals("h5")) {
				
				APIServiceNew api = new APIServiceNew(__eaa.getEnterprisecode());
				JSONObject object = JSONObject.fromObject( api.play(gametype, playtype, employeecode, games, Enum_deviceType.手机.code, playtype, urlMap) );
				log.Error("H5登录游戏结果GG游行填写："+object);
				if(object.getString("code").equals("0")) {
					return Enum_MSG.成功.message(object.getString("info"));//返回登录地址
				} else {
					return Enum_MSG.失败.message(object.getString("info"));
				}
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.参数类型不存在.desc.replace("{0}", application));
			}
			
		} else if (gametype.equals(Enum_GameType.波音.gametype)) {//jason 20161218.mg与ttg一样
			if (application.equals("h5")) {
				
				APIServiceNew api = new APIServiceNew(__eaa.getEnterprisecode());
				JSONObject object = JSONObject.fromObject( api.play(gametype, playtype, employeecode, games, Enum_deviceType.手机.code, playtype, urlMap) );
				log.Error("H5登录游戏结果波音游行填写："+object);
				if(object.getString("code").equals("0")) {
					return Enum_MSG.成功.message(object.getString("info"));//返回登录地址
				} else {
					return Enum_MSG.失败.message(object.getString("info"));
				}
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.参数类型不存在.desc.replace("{0}", application));
			}
			
		} else if (gametype.equals(Enum_GameType.沙巴.gametype) ) {//jason 20161218.mg与ttg一样
			if (application.equals("h5")) {
				
				/****
				//获取账户余额  因沙巴H5不能小于1块，否则不能进入游戏，所以判断
				EnterpriseEmployeeCapitalAccount ec = capitalAccountService.takeCurrencyAccount(employeecode);
				if( ec.getBalance().doubleValue() < 1.00 ) {
					return Enum_MSG.余额不足.message("因沙巴体育手机游戏余额不能小于1元，请您先充值至少1元！");
				}
				*****/
				
				/*****新版*****/
				APIServiceNew api = new APIServiceNew(__eaa.getEnterprisecode());
				JSONObject object = JSONObject.fromObject( api.play(gametype, playtype, employeecode, games, Enum_deviceType.手机.code, "", urlMap) );
				log.Error("H5登录游戏结果SB："+object);
				if(object.getString("code").equals("0")) {
					return Enum_MSG.成功.message(object.getString("info"));//返回登录地址
				} else {
					return Enum_MSG.失败.message(object.getString("info"));
				}
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.参数类型不存在.desc.replace("{0}", application));
			}
		} else if (gametype.equals(Enum_GameType.东方.gametype) ) {//jason 20161218.mg与ttg一样
			if (application.equals("h5")) {
				
				APIServiceNew api = new APIServiceNew(__eaa.getEnterprisecode());
				JSONObject object = JSONObject.fromObject( api.play(gametype, playtype, employeecode, games, Enum_deviceType.手机.code, "", urlMap) );
				log.Error("H5登录游戏结果东方OG："+object);
				if(object.getString("code").equals("0")) {
					return Enum_MSG.成功.message(object.getString("info"));//返回登录地址
				} else {
					return Enum_MSG.失败.message(object.getString("info"));
				}
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.参数类型不存在.desc.replace("{0}", application));
			}
		} else if (gametype.equals(Enum_GameType.EBet.gametype)) {
			if (application.equals("h5")) {
				APIServiceNew api = new APIServiceNew(__eaa.getEnterprisecode());
				JSONObject object = JSONObject.fromObject( api.play(gametype, playtype, employeecode, games, Enum_deviceType.手机.code, playtype, urlMap) );
				log.Error("H5登录游戏结果EBet："+object);
				if(object.getString("code").equals("0")) {
					return Enum_MSG.成功.message(object.getString("info"));//返回登录地址
				} else {
					return Enum_MSG.失败.message(object.getString("info"));
				}
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.参数类型不存在.desc.replace("{0}", application));
			}
			
		} else if (gametype.equals(Enum_GameType.GB彩票.gametype)) {
			if (application.equals("h5")) {
				APIServiceNew api = new APIServiceNew(__eaa.getEnterprisecode());
				JSONObject object = JSONObject.fromObject( api.play(gametype, playtype, employeecode, games, Enum_deviceType.手机.code, playtype, urlMap) );
				log.Error("H5登录游戏结果GB彩票："+object);
				if(object.getString("code").equals("0")) {
					return Enum_MSG.成功.message(object.getString("info"));//返回登录地址
				} else {
					return Enum_MSG.失败.message(object.getString("info"));
				}
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.参数类型不存在.desc.replace("{0}", application));
			}
			
		} else if (gametype.equals(Enum_GameType.TGPlayer.gametype)) {
			if (application.equals("h5")) {
				APIServiceNew api = new APIServiceNew(__eaa.getEnterprisecode());
				JSONObject object = JSONObject.fromObject( api.play(gametype, playtype, employeecode, games, Enum_deviceType.手机.code, playtype, urlMap) );
				log.Error("H5登录游戏结果TGP："+object);
				if(object.getString("code").equals("0")) {
					return Enum_MSG.成功.message(object.getString("info"));//返回登录地址
				} else {
					return Enum_MSG.失败.message(object.getString("info"));
				}
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.参数类型不存在.desc.replace("{0}", application));
			}
			
		} else if (gametype.equals(Enum_GameType.WIN88.gametype)) {
			if (application.equals("h5")) {
				APIServiceNew api = new APIServiceNew(__eaa.getEnterprisecode());
				JSONObject object = JSONObject.fromObject( api.play(gametype, playtype, employeecode, games, Enum_deviceType.手机.code, playtype, urlMap) );
				log.Error("H5登录游戏结果WIN88："+object);
				if(object.getString("code").equals("0")) {
					return Enum_MSG.成功.message(object.getString("info"));//返回登录地址
				} else {
					return Enum_MSG.失败.message(object.getString("info"));
				}
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.参数类型不存在.desc.replace("{0}", application));
			}
			
		} else if (gametype.equals(Enum_GameType.QTtech.gametype)) {
            if (application.equals("h5")) {
                APIServiceNew api = new APIServiceNew(__eaa.getEnterprisecode());
                JSONObject object = JSONObject.fromObject( api.play(gametype, playtype, employeecode, games, Enum_deviceType.手机.code, playtype, urlMap) );
                log.Error("H5登录游戏结果QTtech："+object);
                if(object.getString("code").equals("0")) {
                    return Enum_MSG.成功.message(object.getString("info"));//返回登录地址
                } else {
                    return Enum_MSG.失败.message(object.getString("info"));
                }
            } else {
                return Enum_MSG.参数错误.message(Enum_MSG.参数类型不存在.desc.replace("{0}", application));
            }
            
        } else if (gametype.equals(Enum_GameType.GGPoker.gametype)) {
            if (application.equals("h5")) {
                APIServiceNew api = new APIServiceNew(__eaa.getEnterprisecode());
                JSONObject object = JSONObject.fromObject( api.play(gametype, playtype, employeecode, games, Enum_deviceType.手机.code, playtype, urlMap) );
                log.Error("H5登录游戏结果GGPoker："+object);
                if(object.getString("code").equals("0")) {
                    return Enum_MSG.成功.message(object.getString("info"));//返回登录地址
                } else {
                    return Enum_MSG.失败.message(object.getString("info"));
                }
            } else {
                return Enum_MSG.参数错误.message(Enum_MSG.参数类型不存在.desc.replace("{0}", application));
            }
            
        } else if (gametype.equals(Enum_GameType.IDN.gametype)) {
            if (application.equals("h5")) {
                APIServiceNew api = new APIServiceNew(__eaa.getEnterprisecode());
                JSONObject object = JSONObject.fromObject( api.play(gametype, playtype, employeecode, games, Enum_deviceType.手机.code, playtype, urlMap) );
                log.Error("H5登录游戏结果IDN："+object);
                if(object.getString("code").equals("0")) {
                    return Enum_MSG.成功.message(object.getString("info"));//返回登录地址
                } else {
                    return Enum_MSG.失败.message(object.getString("info"));
                }
            } else {
                return Enum_MSG.参数错误.message(Enum_MSG.参数类型不存在.desc.replace("{0}", application));
            }
            
        } else if (gametype.equals(Enum_GameType.eIBCGame.gametype) ) {//jason 20161218.mg与ttg一样
			if (application.equals("h5")) {
				
				APIServiceNew api = new APIServiceNew(__eaa.getEnterprisecode());
				JSONObject object = JSONObject.fromObject( api.play(gametype, playtype, employeecode, games, Enum_deviceType.手机.code, "", urlMap) );
				log.Error("H5登录游戏结果eIBCGame："+object);
				if(object.getString("code").equals("0")) {
					return Enum_MSG.成功.message(object.getString("info"));//返回登录地址
				} else {
					return Enum_MSG.失败.message(object.getString("info"));
				}
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.参数类型不存在.desc.replace("{0}", application));
			}
		} else if (gametype.equals(Enum_GameType.YoPLAY.gametype) ) {//jason 20161218.mg与ttg一样
			if (application.equals("h5")) {
				
				APIServiceNew api = new APIServiceNew(__eaa.getEnterprisecode());
				JSONObject object = JSONObject.fromObject( api.play(gametype, playtype, employeecode, games, Enum_deviceType.手机.code, "", urlMap) );
				log.Error("H5登录游戏结果YoPLAY："+object);
				if(object.getString("code").equals("0")) {
					return Enum_MSG.成功.message(object.getString("info"));//返回登录地址
				} else {
					return Enum_MSG.失败.message(object.getString("info"));
				}
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.参数类型不存在.desc.replace("{0}", application));
			}
		} else if (gametype.equals(Enum_GameType.IM体育.gametype) ) {//jason 20161218.mg与ttg一样
			if (application.equals("h5")) {
				
				APIServiceNew api = new APIServiceNew(__eaa.getEnterprisecode());
				JSONObject object = JSONObject.fromObject( api.play(gametype, playtype, employeecode, games, Enum_deviceType.手机.code, "", urlMap) );
				log.Error("H5登录游戏结果IM体育："+object);
				if(object.getString("code").equals("0")) {
					return Enum_MSG.成功.message(object.getString("info"));//返回登录地址
				} else {
					return Enum_MSG.失败.message(object.getString("info"));
				}
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.参数类型不存在.desc.replace("{0}", application));
			}
		} else if (gametype.equals(Enum_GameType.JDB168.gametype)) {//jason 20161218.mg与ttg一样
			if (application.equals("h5")) {
				
				APIServiceNew api = new APIServiceNew(__eaa.getEnterprisecode());
				JSONObject object = JSONObject.fromObject( api.play(gametype, playtype, employeecode, games, Enum_deviceType.手机.code, "", urlMap) );
				log.Error("H5登录游戏结果加多宝："+object);
				if(object.getString("code").equals("0")) {
					return Enum_MSG.成功.message(object.getString("info"));//返回登录地址
				} else {
					return Enum_MSG.失败.message(object.getString("info"));
				}
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.参数类型不存在.desc.replace("{0}", application));
			}
			
		} else if (gametype.equals(Enum_GameType.去玩棋牌.gametype)) {//jason 20161218.mg与ttg一样
			if (application.equals("h5")) {
				
				APIServiceNew api = new APIServiceNew(__eaa.getEnterprisecode());
				JSONObject object = JSONObject.fromObject( api.play(gametype, playtype, employeecode, games, Enum_deviceType.手机.code, "", urlMap) );
				log.Error("H5登录游戏结果去玩棋牌："+object);
				if(object.getString("code").equals("0")) {
					return Enum_MSG.成功.message(object.getString("info"));//返回登录地址
				} else {
					return Enum_MSG.失败.message(object.getString("info"));
				}
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.参数类型不存在.desc.replace("{0}", application));
			}
			
		} else {
			return Enum_MSG.参数错误.message(Enum_MSG.暂不支持.desc);
		}
	}
	

	
	@RequestMapping(value = "/0", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String sendMessage(HttpServletRequest request, HttpSession session) {
		try {
			String flag = request.getParameter("flag");
			String content = "尊敬的金塔用户：由于本周一 GG德州系统维护升级导致 扑克游戏维护48小时， 对此给您带来不便我们深表歉意；即日起您方可享受10%存送优惠~详情请联系在线客服，我们将竭诚为你服务！";
			
			List<EmployeeMessage> messages = new ArrayList<EmployeeMessage>();
			EnterpriseEmployee ee = enterpriseEmployeeService.takeEmployeeByCode("E000IUZ1");
			
			
			EmployeeMessageText text = new EmployeeMessageText();
			text.setContent(content);
			text.setDatastatus(String.valueOf(Constant.Enum_DataStatus.正常.value));
			
			List<String> __list = FileHelper.readFileList("/data/wwwroot/test_ggp.txt");
			for (String string : __list) {
				
				if(string == null || string.trim().equals("")) {
					continue;
				}
				
				//开始处理业务逻辑
				String[] datas = string.split(",");
				String username = datas[1];
				String money = datas[2];
				
				
				EmployeeApiAccout apiAccout = new EmployeeApiAccout();
				apiAccout.setGametype(Enum_GameType.GGPoker.gametype);
				apiAccout.setGameaccount(username);
				apiAccout = employeeApiAccoutService.selectFirst(apiAccout);
				if(apiAccout == null) {
					System.out.println("不能根据游戏账号查找到会员信息："+username);
				} else {
					EmployeeMessage message = new EmployeeMessage();
					message.setEnterprisecode(ee.getEnterprisecode());
					message.setBrandcode(ee.getBrandcode());
					message.setSendemployeecode(ee.getEmployeecode());
					message.setSendemployeeaccount(ee.getLoginaccount());
					message.setAcceptemployeecode(apiAccout.getEmployeecode());//接受会员编号
					message.setAcceptaccount(apiAccout.getLoginaccount());//接受会员账号
					message.setMessagetype(String.valueOf(EmployeeMessage.Enum_messagetype.系统消息.value));
					message.setReadstatus(String.valueOf(EmployeeMessage.Enum_readstatus.未阅读.value));
					messages.add(message);
				}
				
			}
			
			if(flag != null && flag.equals("do")) {
				if(messages.size() > 0) {
					employeeMessageService.tc_sendMessage(messages, text);
				}
			}
			
			return Enum_MSG.成功.message("已发送"+messages.size()+"人。");
			
		}catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	
	
	
	public static void main(String[] args) {
		
		
		
	}
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
