package com.maven.controller.game;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.constant.Enum_MSG;
import com.maven.controller.BaseController;
import com.maven.entity.ApiSoltGametype;
import com.maven.entity.EmployeeMessage;
import com.maven.entity.EmployeeMessage.Enum_readstatus;
import com.maven.entity.EnterpriseEmployee;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.ApiSoltGametypeService;
import com.maven.service.EmployeeMessageService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.utils.StringUtil;

/**
 * WIN88的PT游戏
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/win88game")
public class WIN88Controller extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(WIN88Controller.class.getName(), OutputManager.LOG_USER_MESSAGE);
	
	private static String gametype = "PTGame";
	private static String PNG_GAME_SESSION__LOGIN_KEY = "PT_GAME_SESSION__LOGIN_KEY";
	
	private static List<ApiSoltGametype> dataWeb = new ArrayList<ApiSoltGametype>();
	private static boolean dataWebFlag = true;
	
	private static List<ApiSoltGametype> dataH5 = new ArrayList<ApiSoltGametype>();
	private static boolean dataH5Flag = true;
	
	private static int loop = 4;
	
	@Autowired
	private ApiSoltGametypeService apiSoltGametypeService;
	
	
	
	/***********************这是PC端的************************/
	
	
	@RequestMapping( value = {"/ptnew","/index"} )
	public String ptnew(HttpServletRequest request, Model model){
		
		Map<String,Object> __parames = super.getRequestParamters(request);
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String gamecode = request.getParameter("gamecode");
		String gamename = request.getParameter("gamename");//页面上的名字搜索
		String stype = request.getParameter("stype");//分类过滤
		
		if( StringUtil.getString(gamecode).length() == 0) {
			gamecode = "";	
		}
		//如有搜索名字，则分类要去掉
		if(gamename != null && !gamename.trim().equals("") && !gamename.trim().equals("null")) {
			stype = null;
		}

		request.getSession().setAttribute("username", username);
		request.getSession().setAttribute("password", password);
		request.getSession().setAttribute("gametype", gamecode);
		request.getSession().setAttribute("homeurl", request.getParameter("homeurl"));
		
		if(gamecode.equals("7bal")) {
			return "/win88/indexsx";
		}
		
		
		
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
		model.addAttribute("data", dataWeb);
		
		//过滤分类
		if(stype != null) {
			stype = com.maven.utils.AESUtil.decrypt(stype);
			
			List<ApiSoltGametype> list = new ArrayList<ApiSoltGametype>();
			for (ApiSoltGametype apiSoltGametype : dataWeb) {
				if(apiSoltGametype.getStype() != null && apiSoltGametype.getStype().contains(stype)) {
					list.add(apiSoltGametype);
				}
			}
			model.addAttribute("data", list);
			model.addAttribute("stype", stype);
		} else {
			//
		}
		
		//过滤名字搜索
		if(gamename != null) {
			List<ApiSoltGametype> list = new ArrayList<ApiSoltGametype>();
			for (ApiSoltGametype apiSoltGametype : dataWeb) {
				if(apiSoltGametype.getCnname() != null && apiSoltGametype.getCnname().contains(gamename)) {
					list.add(apiSoltGametype);
				}
			}
			model.addAttribute("data", list);
			model.addAttribute("gamename", gamename);
		} else {
			//
		}
		
//		return "/win88/ptgame_new";
		return "/win88/index_new";//新版
	}
	
	
	
	/***********************这是H5端的************************/
	@RequestMapping("/loginh5")
	public String loginh5(HttpServletRequest request,Model model){
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String gamecode = request.getParameter("gamecode");
		request.getSession().setAttribute("homeurl", request.getParameter("homeurl"));
		
		if( StringUtil.getString(gamecode).length() == 0) {
			gamecode = "";	
		}

		request.getSession().setAttribute("username", username);
		request.getSession().setAttribute("password", password);
		request.getSession().setAttribute("gametype", gamecode);
		
		return "/win88/loginh5";
	}
	@RequestMapping("/indexh5")
	public String indexh5(HttpServletRequest request,Model model){
		
		Map<String,Object> __parames = super.getRequestParamters(request);
		
		String gamename = request.getParameter("gamename");//页面上的名字搜索
		String stype = request.getParameter("stype");//分类过滤
		
		//如有搜索名字，则分类要去掉
		if(gamename != null && !gamename.trim().equals("") && !gamename.trim().equals("null")) {
			stype = null;
		}
		
		//2的整数小时更新一次缓存
		if(dataH5.size() == 0 || (getCurrenHour() % loop == 0 && dataH5Flag)) {
			try {
				ApiSoltGametype item = new ApiSoltGametype();
				item.setBiggametype(gametype);
				item.setIsh5("0");
				dataH5 = apiSoltGametypeService.select(item);//只查询web游戏类
				dataH5Flag = false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		model.addAttribute("data", dataH5);
		
		//过滤
		if(stype != null) {
			stype = com.maven.utils.AESUtil.decrypt(stype);
			
			List<ApiSoltGametype> list = new ArrayList<ApiSoltGametype>();
			for (ApiSoltGametype apiSoltGametype : dataH5) {
				if(apiSoltGametype.getStype() != null && apiSoltGametype.getStype().contains(stype)) {
					list.add(apiSoltGametype);
				}
			}
			model.addAttribute("data", list);
			model.addAttribute("stype", stype);
		} else {
			//
		}
		
		//过滤名字搜索
		if(gamename != null) {
			List<ApiSoltGametype> list = new ArrayList<ApiSoltGametype>();
			for (ApiSoltGametype apiSoltGametype : dataH5) {
				if(apiSoltGametype.getCnname() != null && apiSoltGametype.getCnname().contains(gamename)) {
					list.add(apiSoltGametype);
				}
			}
			model.addAttribute("data", list);
			model.addAttribute("gamename", gamename);
		} else {
			//
		}
		
		
//		return "/win88/indexh5";
		return "/win88/indexh5_new";//新版
	}
	@RequestMapping("/supporth5")
	public String supporth5(HttpServletRequest request){
		return "/win88/support";
	}
	@RequestMapping("/logouth5")
	public String logouth5(HttpServletRequest request){
		return "/win88/logout";
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
