package com.maven.controller.game;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hy.pull.common.util.api.GameAPI;
import com.hy.pull.common.util.api.SGSGameAPI;
import com.maven.cache.SystemCache;
import com.maven.constant.Enum_MSG;
import com.maven.controller.BaseController;
import com.maven.entity.ApiSoltGametype;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.EmployeeMessage;
import com.maven.entity.EmployeeMessage.Enum_readstatus;
import com.maven.entity.EnterpriseEmployee;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.game.enums.GameEnum;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.ApiSoltGametypeService;
import com.maven.service.EmployeeMessageService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.util.StringUtils;
import com.maven.utils.StringUtil;

import net.sf.json.JSONObject;

/**
 * SGS申博游戏
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/sgsgame")
public class SGSController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(SGSController.class.getName(), OutputManager.LOG_USER_MESSAGE);
	
	private static String gametype = "SGSGame";
	private static String SGS_GAME_SESSION__LOGIN_KEY = "SGS_GAME_SESSION__LOGIN_KEY";
	
	private static List<Map<String, String>> dataWeb = new ArrayList<Map<String, String>>();
	private static boolean dataWebFlag = true;
	
	private static List<Map<String, String>> dataH5 = new ArrayList<Map<String, String>>();
	private static boolean dataH5Flag = true;
	
	private static int loop = 4;
	
	@Autowired
	private ApiSoltGametypeService apiSoltGametypeService;
	
	
	
	/***********************这是PC端的************************/
	@RequestMapping( value = {"/index"} )
	public String index(HttpServletRequest request, Model model){
		
		Map<String,Object> __parames = super.getRequestParamters(request);
		
		//2的整数小时更新一次缓存
		if(dataWeb.size() == 0 || (getCurrenHour() % loop == 0 && dataWebFlag)) {
			try {
				dataWeb = SGSGameAPI.listGames;
				dataWebFlag = false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		
		
		//过滤
		if(__parames.get("stype") != null) {
			String stype = __parames.get("stype").toString();
			/**/
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			for (Map<String, String> item : dataWeb) {
				if(item.get("type").equals(stype)) {
					list.add(item);
				}
			}
			model.addAttribute("data", list);
			model.addAttribute("stype", stype);
		} else {
			model.addAttribute("data", dataWeb);
		}
		
		
//		第一次请求进来有员工编码时应该保存到会话
		if(__parames.get("employeecode") != null) {
			String employeecode = String.valueOf(__parames.get("employeecode"));
			request.getSession().setAttribute(SGS_GAME_SESSION__LOGIN_KEY, employeecode);
		}
		
		return "/sgs/index";
	}
	@RequestMapping("/login")
	public String login(HttpServletRequest request,Model model){
		Map<String,Object> __parames = super.getRequestParamters(request);
		String gamecode = String.valueOf(__parames.get("gamecode"));
		
		// 获取游戏账号
		String employeecode = (String)request.getSession().getAttribute(SGS_GAME_SESSION__LOGIN_KEY);
		if(employeecode == null) {
			model.addAttribute("message", "会话已过期，请关闭页面并重新登录");
			return "/error/error";
		}
		EmployeeApiAccout __eaa = SystemCache.getInstance().getEmployeeGameAccount(employeecode, gametype);
		
		//获取客户站点的请求URL地址
		Map<String, String> urlMap = new HashMap<String, String>();
//		urlMap.put("homeurl", String.valueOf(object.get("homeurl")));//网站首页
		
		if(__eaa.getStatus().equals(EmployeeApiAccout.Enum_status.禁用.value)) {
			//return Enum_MSG.用户未启用该游戏.message(null);
			model.addAttribute("message", Enum_MSG.用户未启用该游戏.desc);
			return "/error/error";
		}
		
		
		JSONObject object = JSONObject.fromObject( 
				GameAPI.login(__eaa.getGameaccount(), __eaa.getGamepassword(), gametype, "DZ", __eaa.getEnterprisecode(), GameEnum.Enum_deviceType.电脑.code, gamecode, employeecode, "Y", urlMap) 
				);
		return "redirect:"+object.getString("info"); 
	}
	
	
	/***********************这是H5端的************************/
	@RequestMapping("/indexh5")
	public String indexh5(HttpServletRequest request,Model model){
		
		Map<String,Object> __parames = super.getRequestParamters(request);
		
		//2的整数小时更新一次缓存
		if(dataH5.size() == 0 || (getCurrenHour() % loop == 0 && dataH5Flag)) {
			try {
				dataH5 = SGSGameAPI.listGamesh5;
				dataH5Flag = false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		
		
		//过滤
		if(__parames.get("stype") != null) {
			String stype = __parames.get("stype").toString();
			
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			for (Map<String, String> item : dataH5) {
				if(item.get("type").equals(stype)) {
					list.add(item);
				}
			}
			model.addAttribute("data", list);
			model.addAttribute("stype", stype);
		} else {
			model.addAttribute("data", dataH5);
		}
		
//		第一次请求进来有员工编码时应该保存到会话
		if(__parames.get("employeecode") != null) {
			String employeecode = String.valueOf(__parames.get("employeecode"));
			request.getSession().setAttribute(SGS_GAME_SESSION__LOGIN_KEY, employeecode);
		}
		
		return "/sgs/indexh5";
	}
	
	@RequestMapping("/loginh5")
	public String loginh5(HttpServletRequest request,Model model){
		Map<String,Object> __parames = super.getRequestParamters(request);
		String gamecode = String.valueOf(__parames.get("gamecode"));
		
		
		//获取客户站点的请求URL地址
		Map<String, String> urlMap = new HashMap<String, String>();
//		urlMap.put("homeurl", String.valueOf(object.get("homeurl")));//网站首页
		
		// 获取游戏账号
		String employeecode = (String)request.getSession().getAttribute(SGS_GAME_SESSION__LOGIN_KEY);
		if(employeecode == null) {
			model.addAttribute("message", "会话已过期，请关闭页面并重新登录");
			return "/error/error";
		}
		EmployeeApiAccout __eaa = SystemCache.getInstance().getEmployeeGameAccount(employeecode, gametype);
		
		
		if(__eaa.getStatus().equals(EmployeeApiAccout.Enum_status.禁用.value)) {
			//return Enum_MSG.用户未启用该游戏.message(null);
			model.addAttribute("message", Enum_MSG.用户未启用该游戏.desc);
			return "/error/error";
		}
		
		
		JSONObject object = JSONObject.fromObject( 
				GameAPI.login(__eaa.getGameaccount(), __eaa.getGamepassword(), gametype, "DZ", __eaa.getEnterprisecode(), GameEnum.Enum_deviceType.手机.code, gamecode, employeecode, "Y", urlMap) 
				);
		return "redirect:"+object.getString("info"); 
	}
	
	private static int getCurrenHour() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.HOUR_OF_DAY);//返回24小时制的当前小时数据
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}

}
