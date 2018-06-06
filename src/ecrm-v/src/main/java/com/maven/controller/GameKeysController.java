package com.maven.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hy.pull.common.util.api.AVGameAPI;
import com.hy.pull.common.util.api.BaseInterfaceLog;
import com.hy.pull.common.util.api.GameAPI;
import com.hy.pull.common.util.game.av.AVUtil;
import com.maven.cache.SystemCache;
import com.maven.constant.Constant;
import com.maven.constant.Enum_MSG;
import com.maven.controller.BaseController;
import com.maven.entity.EmployeeMessage;
import com.maven.entity.Enterprise;
import com.maven.entity.EmployeeMessage.Enum_readstatus;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseGame;
import com.maven.entity.Game;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.game.OrderNewUtil;
import com.maven.game.enums.GameEnum;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.EmployeeMessageService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseGameService;
import com.maven.service.EnterpriseService;
import com.maven.service.GameService;
import com.maven.util.RandomString;
import com.maven.util.RandomStringNum;
import com.maven.utils.ApiKeyObject;
import com.maven.utils.StringUtil;

/**
 * API游戏接口及key管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/apigame")
public class GameKeysController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			EnterpriseOperatingBrandGameController.class.getName(), OutputManager.LOG_GAMEAPIINPUT);
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private EnterpriseGameService enterpriseGameService;
	@Autowired
	private EnterpriseService enterpriseService;
	
	
	/**
	 * index
	 * @param request
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model model){
		try {
			Map<String,Object> params = new HashMap<String, Object>();
			List<Enterprise> listEnterprise = enterpriseService.selectAll(params);
			List<Game> listGame = gameService.findGameData(params);
			model.addAttribute("listEnterprise", listEnterprise);
			model.addAttribute("listGame", listGame);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/enterprise/apikey_list";
	}
	/**
	 * edit
	 * @param request
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit(HttpServletRequest request, Model model){
		
		try {
			Map<String,Object> params = new HashMap<String, Object>();
			List<Enterprise> listEnterprise = enterpriseService.selectAll(params);
			List<Game> listGame = gameService.findGameData(params);
			model.addAttribute("listEnterprise", listEnterprise);
			model.addAttribute("listGame", listGame);
			
			Map<String,Object> paramsx = getRequestParamters(request);
			if(paramsx.containsKey("enterprisecode")) {
				model.addAttribute("enterprisecode", paramsx.get("enterprisecode"));
				model.addAttribute("gametype", paramsx.get("gametype"));
				
				Map<String, String> keydata = new HashMap<String, String>();
				keydata = SystemCache.getInstance().getAPIEnterpriseKyes(paramsx.get("enterprisecode").toString(), paramsx.get("gametype").toString(), keydata);//读取
				model.addAttribute("keydata", keydata);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/enterprise/apikey_edit";
	}
	
	/**
	 * 保存
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> save(HttpServletRequest request,Model model){
		
		try {
			Map<String,Object> params = getRequestParamters(request);
			
			String stype = String.valueOf(params.get("stype"));
			String enterprisecode = String.valueOf(params.get("enterprisecode"));
			String gametype = String.valueOf(params.get("gametype"));
			String key = String.valueOf(params.get("key"));
			String val = String.valueOf(params.get("val"));
			
			//查找出map对象
			Map<String, String> keydata = new HashMap<String, String>();
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);//读取
//			System.out.println("原map数据："+keydata);
			keydata.put(key, val);
//			System.out.println("新map数据："+keydata);
			SystemCache.getInstance().setAPIEnterpriseKyes(enterprisecode, gametype, keydata);//更新
			GameAPI.apikeys.put(enterprisecode+gametype, keydata);//更新接口类内存缓存
			
			return this.packJSON(Constant.BooleanByte.YES,"操作成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.packJSON(Constant.BooleanByte.YES,"操作失败，请稍后尝试");
		}		
	}
	
	/**
	 * apikey分页查询
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/pagekey")
	@ResponseBody
	public Map<String, Object> pagekey(HttpServletRequest request,Model model){
		
		List<ApiKeyObject> list = new ArrayList<ApiKeyObject>();
		int count = 0;
		try {
			Map<String,Object> params = getRequestParamters(request);
			List<EnterpriseGame> listEnterpriseGame = enterpriseGameService.selectAll(params);
			count = enterpriseGameService.selectAllCount(params);
			for (EnterpriseGame enterpriseGame : listEnterpriseGame) {
				
				
				Map<String, String> keydata = new HashMap<String, String>();
				keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterpriseGame.getEnterprisecode(), enterpriseGame.getGametype(), keydata);//读取
				
				ApiKeyObject object = new ApiKeyObject();
				object.setEnterprisecode(enterpriseGame.getEnterprisecode());
				object.setEnterprisename(enterpriseGame.getEnterprisename());
				object.setGametype(enterpriseGame.getGametype());
				object.setGamename(enterpriseGame.getGamename());
				
//				object.setVals(keydata.toString());
				StringBuffer stringBuffer = new StringBuffer();
				Iterator<String> iterator = keydata.keySet().iterator();
				while(iterator.hasNext()){
				     String key = iterator.next().toString();
				     String val = keydata.get(key);
				     stringBuffer.append(key).append("=").append(val).append(";<br />");
				}
				
				object.setVals(stringBuffer.toString());
				list.add(object);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		
		Map<String, Object> data = super.formatPagaMap(list, count);
		
		return data;
	}
	
	
	/**
	 * 测试API
	 * @param request
	 * @param model
	 * @return
	 */
	private String username = "";
	private String password = "";
	private String money_up = "2";
	private String money_down = "1";
	private String orderno = "";
	private Map<String, Map<String, String>> params = new HashMap<String, Map<String,String>>();
	@RequestMapping(value="/testAPI", produces = "application/json; charset=utf-8")
	@ResponseBody
	public Object testAPI(HttpServletRequest request,Model model){
		Object result = null;
		Map<String,Object> paramsxx = getRequestParamters(request);
		
		/*
		List<BaseInterfaceLog> list = SystemCache.getInstance().pageBaseInterfaceLog(1, 100);
		for (BaseInterfaceLog baseInterfaceLog : list) {
			if(baseInterfaceLog == null) {
//				continue;
			}
			System.out.println(baseInterfaceLog.toString());
		}
		System.out.println(list.size());
		*/
		EnterpriseEmployee ee = (EnterpriseEmployee)request.getSession().getAttribute(Constant.USER_SESSION);
		
		
		String stype = String.valueOf(paramsxx.get("stype"));
		String enterprisecode = String.valueOf(paramsxx.get("enterprisecode"));//企业
		String gametype = String.valueOf(paramsxx.get("gametype"));//游戏类型
		
		Map<String, String> param = null;
		if(params.containsKey(enterprisecode+gametype)) {
			param = (Map)params.get(enterprisecode+gametype);
			username = param.get("username");
			password = param.get("password");
			orderno  = param.get("orderno");
		} else {
			username = RandomStringNum.createRandomString(20);
			if(gametype.equals(GameEnum.Enum_GameType.PT.gametype)) {
				username = "HYL"+RandomStringNum.createRandomString(17);
			} else if(gametype.equals(GameEnum.Enum_GameType.AV老虎机.gametype)) {
				username = RandomStringNum.createRandomString(15);
			} else if(gametype.equals(GameEnum.Enum_GameType.棋牌.gametype)) {
				username = RandomStringNum.createRandomString(15);
			}else if(gametype.equals(GameEnum.Enum_GameType.GG.gametype)) {
				username = RandomStringNum.createRandomString(15);
			}else if(gametype.equals(GameEnum.Enum_GameType.IDN.gametype)) {
				username = RandomStringNum.createRandomString(15);
			}else if(gametype.equals(GameEnum.Enum_GameType.M88.gametype)) {
				username = RandomStringNum.createRandomString(17);
			}
			
			password = RandomStringNum.createRandomString(8);
			param = new HashMap<String, String>();
			param.put("username", username);
			param.put("password", password);
		}
		
		
		if(stype.equals("create")) {
			
			result = GameAPI.createAccount(username, password, gametype, enterprisecode);
			
		} else if(stype.equals("up")) {
			orderno = OrderNewUtil.getOrdernoUP();
			//String username, String password,String gametype,String enterprisecode,String orderno,double money
			result = GameAPI.deposit(username, password, gametype, enterprisecode, orderno, Double.valueOf(money_up).intValue());
			
		} else if(stype.equals("down")) {
			orderno = OrderNewUtil.getOrdernoDOWN();
			param.put("orderno", orderno);
			//String username, String password,String gametype,String enterprisecode,String orderno,double money
			result = GameAPI.withdraw(username, password, gametype, enterprisecode, orderno, Double.valueOf(money_down).intValue());
			
		} else if(stype.equals("balance")) {
			
			result = GameAPI.getBalance(username, password, gametype, enterprisecode);
					
		} else if(stype.equals("order")) {
			
			result = GameAPI.getOrder(username, password, gametype, enterprisecode, orderno, Double.valueOf(money_down).intValue());
			
		} else if(stype.equals("login")) {
			StringBuffer buffer = new StringBuffer();
			
			String biggametype = "DZ";
			String deviceType = "0";
			String gamecode = "0";
			result = GameAPI.login(username, password, gametype, biggametype, enterprisecode, deviceType, gamecode, ee.getEmployeecode(), null, new HashMap<String, String>());
			System.out.println("第一次登录："+result);
			buffer.append(result.toString()).append("");
			
			biggametype = "DZ";
			deviceType = "1";
			result = GameAPI.login(username, password, gametype, biggametype, enterprisecode, deviceType, gamecode, ee.getEmployeecode(), null, new HashMap<String, String>());
			System.out.println("第二次登录："+result);
			buffer.append(result.toString()).append("");
			
			biggametype = "SX";
			deviceType = "0";
			result = GameAPI.login(username, password, gametype, biggametype, enterprisecode, deviceType, gamecode, ee.getEmployeecode(), null, new HashMap<String, String>());
			System.out.println("第三次登录："+result);
			buffer.append(result.toString()).append("");
			
			biggametype = "SX";
			deviceType = "1";
			result = GameAPI.login(username, password, gametype, biggametype, enterprisecode, deviceType, gamecode, ee.getEmployeecode(), null, new HashMap<String, String>());
			System.out.println("第四次登录："+result);
			buffer.append(result.toString()).append("");
			
			return buffer.toString();
		}
		
		params.put(enterprisecode+gametype, param);
		
		return result;
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
