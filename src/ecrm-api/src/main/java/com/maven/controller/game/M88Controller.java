package com.maven.controller.game;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.cache.SystemCache;
import com.maven.constant.Enum_MSG;
import com.maven.controller.BaseController;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.EmployeeMessage;
import com.maven.entity.EmployeeMessage.Enum_readstatus;
import com.maven.entity.EnterpriseEmployee;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.EmployeeMessageService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.util.RegularCheck;
import com.maven.utils.StringUtil;

/**
 * 明升-M88
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value={"/m88game", "/service"})
public class M88Controller extends BaseController{
	
	
	private static LoggerManager log = LoggerManager.getLogger(M88Controller.class.getName(), OutputManager.LOG_USER_MESSAGE);
	private static String gametype = "M88Game";
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request,Model model){
		
		Map<String,Object> __parames = super.getRequestParamters(request);
		
		String url = "";
		try {
			url = URLDecoder.decode(String.valueOf(__parames.get("url")),"UTF-8");//先url解码
			String employeecode = __parames.get("employeecode").toString();
			
			model.addAttribute("url", url);
			model.addAttribute("session_token", com.maven.utils.AESUtil.encrypt(employeecode));
			
			//获取客户站点的请求URL地址
//			String RequestURL = StringUtil.getURLDomain( request.getHeader("Referer") );
			//根据域名得到顶级域名，如sport.77scm.com，得到77scm.com
			String domain = RegularCheck.takeDomain(url);
			domain = domain.substring(domain.indexOf(".") + 1, domain.length());
			domain = domain.replaceAll("/", "");
			model.addAttribute("domain", domain);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return "/m88/index";
	}
	
	/**
	 * 供M88回调的方法
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/mg88Validation")
	public void mg88Validation(HttpServletRequest request,HttpServletResponse response, HttpSession session){
		
		response.setContentType("application/xml");
		response.setCharacterEncoding("UTF-8");
		
		StringBuffer result = new StringBuffer();
		result.append("<?xml version=\"1.0\"?>");
				
		result.append("<authenticate>");
		

		if(request.getParameter("session_token") != null) {
			
			String employeecode = com.maven.utils.AESUtil.decrypt(request.getParameter("session_token"));
			EmployeeApiAccout __eaa = SystemCache.getInstance().getEmployeeGameAccount(employeecode, gametype);
			
			result.append("<status_code>").append("00").append("</status_code>");
			result.append("<status_text>").append("OK").append("</status_text>");
			
			result.append("<currency>").append("RMB").append("</currency>");
			result.append("<member_id>").append(__eaa.getGameaccount()).append("</member_id>");
			result.append("<member_name>").append(__eaa.getGameaccount()).append("</member_name>");
			result.append("<language>").append("zh-CN").append("</language>");
			result.append("<balance>").append("0").append("</balance>");
			result.append("<min_transfer>").append("1").append("</min_transfer>");
			result.append("<max_transfer>").append("99999999").append("</max_transfer>");
			result.append("<member_type>").append("CASH").append("</member_type>");
			result.append("<datetime>").append(StringUtil.getCurrentTime2()).append("</datetime>");
			
		} else {
			result.append("<status_code>").append("99").append("</status_code>");
			result.append("<status_text>").append("没有会话").append("</status_text>");
		}
		result.append("</authenticate>");
		
		try {
			response.getWriter().write(result.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
	}
	
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}

	public static void main(String[] args) {
		String domain = RegularCheck.takeDomain("http://sport.77scm.com");
		domain = domain.substring(domain.indexOf("."), domain.length());
		domain = domain.replaceAll("/", "");
		System.out.println(domain);
	}
}
