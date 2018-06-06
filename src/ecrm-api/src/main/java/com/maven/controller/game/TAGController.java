package com.maven.controller.game;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
 * AG亚游
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/taggame")
public class TAGController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(TAGController.class.getName(), OutputManager.LOG_USER_MESSAGE);
	private static String gametype = "TAGGame";
	private static String MG_GAME_SESSION__LOGIN_KEY = "TAG_GAME_SESSION__LOGIN_KEY";
	
	private static List<ApiSoltGametype> dataWeb = new ArrayList<ApiSoltGametype>();
	private static boolean dataWebFlag = true;
	
	private static int loop = 4;
	
	@Autowired
	private ApiSoltGametypeService apiSoltGametypeService;
	
	
	/**
	 * 大厅主页（包含web与H5），提供游戏选择
	 * @param request
	 * @param model
	 * @return
	 */
	@Deprecated
	@RequestMapping("/main")
	public String main(HttpServletRequest request,Model model){
		
		Map<String,Object> __parames = super.getRequestParamters(request);
		
		//2的整数小时更新一次缓存
		if(dataWeb.size() == 0 || (getCurrenHour() % loop == 0 && dataWebFlag)) {
			try {
				ApiSoltGametype item = new ApiSoltGametype();
				item.setBiggametype(gametype);
				item.setIsweb("0");
				dataWeb = apiSoltGametypeService.select(item);//只查询web游戏类
				dataWebFlag = false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		
		
		//过滤
		if(__parames.get("stype") != null) {
			String stype = com.maven.utils.AESUtil.decrypt(__parames.get("stype").toString());
			
			List<ApiSoltGametype> list = new ArrayList<ApiSoltGametype>();
			for (ApiSoltGametype apiSoltGametype : dataWeb) {
				if(apiSoltGametype.getStype() != null && apiSoltGametype.getStype().contains(stype)) {
					list.add(apiSoltGametype);
				}
			}
			model.addAttribute("data", list);
			model.addAttribute("stype", stype);
		} else {
			model.addAttribute("data", dataWeb);
		}
		
		//	第一次请求进来有员工编码时应该保存到会话
		if(__parames.get("employeecode") != null) {
			String employeecode = String.valueOf(__parames.get("employeecode"));
			request.getSession().setAttribute(MG_GAME_SESSION__LOGIN_KEY, employeecode);
		}
		
		return "/tag/main";
	}
	private static int getCurrenHour() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.HOUR_OF_DAY);//返回24小时制的当前小时数据
	}
	
	
	
	
	/**
	 * tag与png的进入游戏跳转页面，post方式
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request,Model model){
		
		Map<String,Object> __parames = super.getRequestParamters(request);
		
		String params = String.valueOf(__parames.get("params"));
		String key = String.valueOf(__parames.get("key"));
		String url = "";
		try {
			url = URLDecoder.decode(String.valueOf(__parames.get("url")),"UTF-8");//先url解码
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("params", params);
		model.addAttribute("key", key);
		model.addAttribute("url", url);
		
		return "/tag/index";
	}
	/**
	 *  tag与png的进入游戏跳转页面，post方式
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/indexh5")
	public String indexh5(HttpServletRequest request,Model model){
		
		Map<String,Object> __parames = super.getRequestParamters(request);
		
		String params = String.valueOf(__parames.get("params"));
		String key = String.valueOf(__parames.get("key"));
		String url = "";
		try {
			url = URLDecoder.decode(String.valueOf(__parames.get("url")),"UTF-8");//先url解码
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("params", params);
		model.addAttribute("key", key);
		model.addAttribute("url", url);
		
		return "/tag/indexh5";
	}
	
	/**
	 * tag进入游戏前调用接口封装必备的参数后会请求index路径
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/login")
	@Deprecated
	public String login(HttpServletRequest request,Model model){
		Map<String,Object> __parames = super.getRequestParamters(request);
		String gamecode = String.valueOf(__parames.get("gamecode"));
		
		//获取客户站点的请求URL地址
		Map<String, String> urlMap = new HashMap<String, String>();
//		urlMap.put("homeurl", String.valueOf(object.get("homeurl")));//网站首页
		
		// 获取游戏账号
		String employeecode = (String)request.getSession().getAttribute(MG_GAME_SESSION__LOGIN_KEY);
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
				GameAPI.login(__eaa.getGameaccount(), __eaa.getGamepassword(), gametype, "DZ", __eaa.getEnterprisecode(), GameEnum.Enum_deviceType.电脑.code, gamecode, employeecode, "Y", urlMap) 
				);
		return "redirect:"+object.getString("info"); 
	}
	/**
	 * tag进入游戏前调用接口封装必备的参数后会请求indexh5路径
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/loginh5")
	@Deprecated
	public String loginh5(HttpServletRequest request,Model model){
		
		Map<String,Object> __parames = super.getRequestParamters(request);
		String gamecode = String.valueOf(__parames.get("gamecode"));
		
		//获取客户站点的请求URL地址
		Map<String, String> urlMap = new HashMap<String, String>();
//		urlMap.put("homeurl", String.valueOf(object.get("homeurl")));//网站首页
		
		// 获取游戏账号
		String employeecode = (String)request.getSession().getAttribute(MG_GAME_SESSION__LOGIN_KEY);
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
				GameAPI.login(__eaa.getGameaccount(), __eaa.getGamepassword(), gametype, "DZ", __eaa.getEnterprisecode(), GameEnum.Enum_deviceType.电脑.code, gamecode, employeecode, "Y", urlMap) 
				);
		
		return "redirect:"+object.getString("info"); 
		
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}

}
