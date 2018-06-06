package com.maven.controller.game;

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
import com.hy.pull.common.util.game.av.AVUtil;
import com.maven.cache.SystemCache;
import com.maven.constant.Enum_MSG;
import com.maven.controller.BaseController;
import com.maven.entity.EmployeeMessage;
import com.maven.entity.EmployeeMessage.Enum_readstatus;
import com.maven.entity.EnterpriseEmployee;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.EmployeeMessageService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.util.RandomString;
import com.maven.util.RandomStringNum;
import com.maven.utils.StringUtil;

/**
 * AV老虎机
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/avgame")
public class AVController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(AVController.class.getName(), OutputManager.LOG_USER_MESSAGE);
	
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request,Model model){
		
		Map<String,Object> __parames = super.getRequestParamters(request);
		
		String sessionCode = String.valueOf(__parames.get("sessionCode"));
		
		//type2为设备类型，包括HTML5、FLASH
		List<Map<String, String>> listGame1 = new ArrayList<Map<String,String>>();//type1=SEXY SLOT
		List<Map<String, String>> listGame2 = new ArrayList<Map<String,String>>();//type1=SEXY CASCADING
		
		List<Map<String, String>> listGame = SystemCache.getInstance().getAvGameList();
		for (Map<String, String> map : listGame) {
			if(map.get("type2").equals("FLASH")) {
				
				/**
				String gameLink = "";//游戏完整链接
				//gameName-ch、gameName-en为名字
				//type2为设备类型，包括HTML5、FLASH
				//gameURL为游戏URL地址
				gameLink = gameLink.replaceAll("{session}", sessionCode );
				*/
				
				String gameLink = map.get("gameURL");
				gameLink = gameLink.replaceAll("\\{session\\}", sessionCode );
				map.put("gameURL", gameLink);
				
				if(map.get("type1").indexOf("CASCADING") > -1) {
					listGame2.add(map);
				} else {
					listGame1.add(map);
				}
			}
		}
		model.addAttribute("listGame1", listGame1);
		model.addAttribute("listGame2", listGame2);
		
		return "/av/index";
	}
	
	@RequestMapping("/indexh5")
	public String indexh5(HttpServletRequest request,Model model){
		/*
		SystemCache.getInstance().pageBaseInterfaceLog(1, 100);
		for (int i = 0; i < 102; i++) {
			BaseInterfaceLog item = new BaseInterfaceLog(i+"", i+"333", i+"5555");
			System.out.println(item.getLodId());
			SystemCache.getInstance().addBaseInterfaceLog(item);
		}
		SystemCache.getInstance().pageBaseInterfaceLog(1, 100);
		
		
		Map<String, String> mapxx = new HashMap<String, String>();
		for (int i = 0; i < 100; i++) {
			String clientOrderid = RandomStringNum.createRandomString(20);
			String apiOrderid = RandomString.createRandomString(10 * (i % 2 + 1) );
			SystemCache.getInstance().setAPIClientOrderid(clientOrderid, apiOrderid);
			
			mapxx.put(clientOrderid , apiOrderid);
		}
		System.out.println(mapxx.size());
		Iterator<String> iterator = mapxx.keySet().iterator();    
		while(iterator.hasNext()){    
		     String key;    
		     String value;    
		     key = iterator.next().toString();    
		     value = mapxx.get(key);  
		     
		     String clientOrderid = SystemCache.getInstance().getAPIClientOrderid(key);
		     System.out.println(key+"--"+value + "--"+clientOrderid + "--"+clientOrderid.equals(value));    
		}   
		*/
		
		
		Map<String,Object> __parames = super.getRequestParamters(request);
		
		String sessionCode = String.valueOf(__parames.get("sessionCode"));
		
		//type2为设备类型，包括HTML5、FLASH
		List<Map<String, String>> listGame1 = new ArrayList<Map<String,String>>();//type1=SEXY SLOT
		List<Map<String, String>> listGame2 = new ArrayList<Map<String,String>>();//type1=SEXY CASCADING
		List<Map<String, String>> listGame = SystemCache.getInstance().getAvGameList();
		for (Map<String, String> map : listGame) {
			
			String gameLink = map.get("gameURL");
			gameLink = gameLink.replaceAll("\\{session\\}", sessionCode );
			map.put("gameURL", gameLink);
			
			
			if(map.get("type2").equals("HTML5")) {

				/**
				String gameLink = "";//游戏完整链接
				//gameName-ch、gameName-en为名字
				//type2为设备类型，包括HTML5、FLASH
				//gameURL为游戏URL地址
				gameLink = gameLink.replaceAll("{session}", sessionCode );
				*/
				
				if(map.get("type1").indexOf("CASCADING") > -1) {
					listGame2.add(map);
				} else {
					listGame1.add(map);
				}
			}
		}
		model.addAttribute("listGame1", listGame1);
		model.addAttribute("listGame2", listGame2);
		return "/av/indexh5";
	}
	
	/**
	 * H5版本的游戏类型切换
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/changeh5")
	public String changeh5(HttpServletRequest request,Model model){
		
		Map<String,Object> __parames = super.getRequestParamters(request);
		
		//SLOT/CASCADING
		String type = String.valueOf(__parames.get("type"));
		
		//type2为设备类型，包括HTML5、FLASH
		List<Map<String, String>> listGame1 = new ArrayList<Map<String,String>>();//type1=SEXY SLOT
		List<Map<String, String>> listGame2 = new ArrayList<Map<String,String>>();//type1=SEXY CASCADING
		List<Map<String, String>> listGame = SystemCache.getInstance().getAvGameList();
		for (Map<String, String> map : listGame) {
			if(map.get("type2").equals("HTML5")) {

				/**
				String gameLink = "";//游戏完整链接
				//gameName-ch、gameName-en为名字
				//type2为设备类型，包括HTML5、FLASH
				//gameURL为游戏URL地址
				gameLink = gameLink.replaceAll("{session}", sessionCode );
				*/
				
				if(map.get("type1").indexOf(type.toUpperCase()) > -1) {
					listGame2.add(map);
				} else {
					listGame1.add(map);
				}
			}
		}
		model.addAttribute("listGame1", listGame1);
		return "/av/indexh5";
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}

}
