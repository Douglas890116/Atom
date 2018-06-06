package com.maven.controller.activity;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.constant.Enum_MSG;
import com.maven.controller.BaseController;
import com.maven.controller.member.MessageController;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;

@RequestMapping("/BrandActivity")
@Controller
public class BrandActivityDispatchController extends BaseController{

	private static LoggerManager log = LoggerManager.getLogger(
			MessageController.class.getName(), OutputManager.LOG_BRANDACTIVITYDISPATCH);
	
	/*@Autowired
	private EnterpriseActivityCustomizationService enterpriseActivityCustomizationService;*/
	
	@RequestMapping("/trigger")
	public void activity(HttpServletRequest request,HttpServletResponse response,Model model){
		try {
			Map<String,Object> __parames = super.apiDecode(super.getRequestParamters(request));
			request.setAttribute("activityparams", __parames);
			String way = String.valueOf(__parames.get("way"));
			/*String __enterprisebrandactivitycode = String.valueOf(__parames.get("enterprisebrandactivitycode"));
			EnterpriseActivityCustomization __eacustomization = 
					enterpriseActivityCustomizationService.selectByEnterprisebrandactivitycode(__enterprisebrandactivitycode);*/
			if(way.equals(Enum_brandactivity.活动列表.value)){
				request.getRequestDispatcher("/ActivityInfo/List").forward(request, response);
			} else {
				request.getRequestDispatcher("/BrandActivity/notemplate").forward(request, response);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			try {
				request.getRequestDispatcher("/BrandActivity/notemplate").forward(request, response);
			} catch (ServletException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				log.Error(e1.getMessage(), e1);
			}
		}
	}
	
	@RequestMapping(value={"/notemplate"},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String notemplate(){
		return Enum_MSG.逻辑事物异常.message("企业活动不存在");
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
	
	/**
	 * 活动信息请求类型
	 */
	public enum Enum_brandactivity{
		活动列表("List","所有活动列表"),
		;
		public String value;
		public String desc;
		
		private Enum_brandactivity(String value,String desc){
			this.value=value;
			this.desc=desc;
		}
	}
}
