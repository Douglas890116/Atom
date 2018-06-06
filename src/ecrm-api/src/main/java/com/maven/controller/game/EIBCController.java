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
import com.maven.utils.StringUtil;

/**
 * EVEB-沙巴
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value={"/eibcgame"})
public class EIBCController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(EIBCController.class.getName(), OutputManager.LOG_USER_MESSAGE);
	private static String gametype = "eIBCGame";
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request,Model model){
		
		Map<String,Object> __parames = super.getRequestParamters(request);
		
		try {
//			String employeecode = __parames.get("employeecode").toString();
			String sessionToken = __parames.get("sessionToken").toString();
			String playtype = __parames.get("playtype").toString();
			String domain = __parames.get("domain").toString();
			
			model.addAttribute("sessionToken", sessionToken);
			model.addAttribute("domain", "."+domain);
			//webskintype=2 进入版本为大陆版 不添加则默认为亚洲版
			String url = "http://mkt.ib."+domain+"/Deposit_ProcessLogin.aspx?lang=cs&webskintype=2";
			if(playtype.equals("DZ")) {
				url += "&act=casino";
			}
			model.addAttribute("url", url);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/eibc/index";
	}
	
	@RequestMapping("/indexh5")
	public String indexh5(HttpServletRequest request,Model model){
		
		Map<String,Object> __parames = super.getRequestParamters(request);
		
		try {
//			String employeecode = __parames.get("employeecode").toString();
			String sessionToken = __parames.get("sessionToken").toString();
			String playtype = __parames.get("playtype").toString();
			String domain = __parames.get("domain").toString();
			
			model.addAttribute("sessionToken", sessionToken);
			model.addAttribute("domain", "."+domain);
			//webskintype=2 进入版本为大陆版 不添加则默认为亚洲版
			String url = "http://ismart.ib."+domain+"/Deposit_ProcessLogin.aspx?lang=cs&webskintype=2&st="+sessionToken;
			if(playtype.equals("DZ")) {
				url += "&types=CL";
			}
			model.addAttribute("url", url);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/eibc/indexh5";
	}
	
	
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}

	public static void main(String[] args) {
		//获取客户站点的请求URL地址
		String RequestURL = StringUtil.getURLDomain("http://api.hyzonghe.net//eibcgame/index?playtype=TY&sessionToken=9464954c-dc90-44ea-93f7-1d752cad4393");
		//根据域名得到顶级域名，如sport.77scm.com，得到77scm.com
		String domain = StringUtil.getURLDomain(RequestURL);
		domain = domain.substring(domain.indexOf("."), domain.length());
		domain = domain.replaceAll("/", "");
		System.out.println(domain);
	}
}
