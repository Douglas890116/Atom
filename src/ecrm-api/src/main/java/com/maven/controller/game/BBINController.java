package com.maven.controller.game;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
import com.maven.entity.EmployeeMessage;
import com.maven.entity.EmployeeMessage.Enum_readstatus;
import com.maven.entity.EnterpriseEmployee;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.EmployeeMessageService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.utils.StringUtil;

/**
 * 波音-BBIN
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/bbingame")
public class BBINController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(BBINController.class.getName(), OutputManager.LOG_USER_MESSAGE);
	
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request,Model model){
		
		Map<String,Object> __parames = super.getRequestParamters(request);
		
		String url = "";
		try {
			url = URLDecoder.decode(String.valueOf(__parames.get("url")),"UTF-8");//先url解码
			model.addAttribute("url", url);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("website", __parames.get("website"));
		model.addAttribute("username", __parames.get("username"));
		model.addAttribute("password", __parames.get("password"));
		model.addAttribute("uppername", __parames.get("uppername"));
		model.addAttribute("key", __parames.get("key"));
		
		//直达视讯时
		if(__parames.get("page_site") != null && !__parames.get("page_site").equals("null")) {
			model.addAttribute("page_site", __parames.get("page_site"));
			model.addAttribute("page_present", __parames.get("page_present"));
		}
		
		
		return "/bbin/index";
	}
	@Override
	public LoggerManager getLogger() {
		return log;
	}

}
