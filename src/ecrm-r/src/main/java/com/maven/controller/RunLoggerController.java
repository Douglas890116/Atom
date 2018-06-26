package com.maven.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.logger.SwithObject;

@Controller
@RequestMapping("/RunLogger")
public class RunLoggerController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			RunLoggerController.class.getName(), OutputManager.LOG_SYSTEM_RUNLOGGER);
	
	public static final String OFF = "off";
	
	public static final String ON = "on";
	
	
	
	@RequestMapping("/Index")
	public String index(Model model){
		try {
			List<Object[]> fields = new ArrayList<Object[]>();
			for (Field s : OutputManager.class.getFields()) {
				SwithObject ss = (SwithObject)s.get(SwithObject.class);
				fields.add(new Object[]{s.getName(),ss.SWITH});
			}
			model.addAttribute("fields",fields);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "/logger/runloggerswitch";
	}
	
	@RequestMapping("/Swith")
	public String swith(HttpServletRequest request){
		String field = request.getParameter("field");
		String swith = request.getParameter("swith");
		try {
			if(StringUtils.isNotBlank(field) && StringUtils.isNotBlank(swith)){
				for (Field s : OutputManager.class.getFields()) {
					SwithObject ss = (SwithObject)s.get(SwithObject.class);
					if(s.getName().equals(field)){
						if(swith.equals(ON)){
							ss.SWITH = true;
						}else{
							ss.SWITH = false;
						}
					}
				}
			}
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "redirect:/RunLogger/Index";
	} 
	
	public static void main(String[] args) {
		long date = System.currentTimeMillis();
		for (Field s : OutputManager.class.getFields()) {
				try {
					SwithObject ss = (SwithObject)s.get(SwithObject.class);
					System.out.print(s.getName()+"    "+ss.SWITH);
					ss.SWITH = false;
					SwithObject sss = (SwithObject)s.get(SwithObject.class);
					System.out.println("    "+sss.SWITH);
					
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		System.out.println(System.currentTimeMillis()-date);
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
