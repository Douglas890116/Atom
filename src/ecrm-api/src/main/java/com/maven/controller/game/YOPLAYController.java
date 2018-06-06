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
 * YOPLAY
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/yoplaygame")
public class YOPLAYController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(YOPLAYController.class.getName(), OutputManager.LOG_USER_MESSAGE);
	
	
	
	/**
	 * 进入游戏跳转页面，post方式
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
		
		return "/yoplay/index";
	}
	
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}

}
