package com.maven.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.logger.SystemVisiteLog;

@Controller
@RequestMapping("/SystemVisite")
public class SystemVisiteLogController extends BaseController{

	private static LoggerManager log = LoggerManager.getLogger(
			SystemVisiteLogController.class.getName(), OutputManager.LOG_SYSTEMVISITELOG);
	
	@RequestMapping("/ApiVisiteLog")
	public String VisiteLog(HttpServletRequest request,Model model){
		model.addAttribute("APILOG", new ArrayList<SystemVisiteLog>(SystemVisiteLog.getApiVisiteStat().values()));
		model.addAttribute("APILOGSWITH", SystemVisiteLog.isAPIVisiteLogSwith());
		return "/logger/apivisistelog";
	}
	
	@RequestMapping("/ApiSwith")
	public String SwithAPI(HttpServletRequest request,Model model){
		try {
			String  swith = request.getParameter("swith");
			if(swith.equals("on")){
				SystemVisiteLog.setAPIVisiteLogSwith(true);
			}else{
				SystemVisiteLog.setAPIVisiteLogSwith(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.Error(e.getMessage(), e);
		}
		return "redirect:/SystemVisite/ApiVisiteLog";
	}
	
	@RequestMapping("/ApiCycleSwith")
	public String SwithAPICycleStat(HttpServletRequest request,Model model){
		try {
			String  url = request.getParameter("url");
			String  swith = request.getParameter("swith");
			SystemVisiteLog visiteLog = SystemVisiteLog.getApiVisiteStat().get(url);
			if(swith.equals("on")){
				visiteLog.setOpencyclestat(true);
			}else{
				visiteLog.setOpencyclestat(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.Error(e.getMessage(), e);
		}
		return "redirect:/SystemVisite/ApiVisiteLog";
	}
	
	
	@RequestMapping("/UpdateApiStatCycle")
	public String updateAPIStatCycle(HttpServletRequest request,Model model){
		try {
			String  url = request.getParameter("url");
			String  cycle = request.getParameter("cycle");
			SystemVisiteLog visiteLog = SystemVisiteLog.getApiVisiteStat().get(url);
			int statCycle = Integer.parseInt(cycle.trim());
			visiteLog.setStatcycle(statCycle);
		} catch (Exception e) {
			e.printStackTrace();
			log.Error(e.getMessage(), e);
		}
		return "redirect:/SystemVisite/ApiVisiteLog";
	}
	
	@RequestMapping("/ApiCycleVisiteLog")
	public String viewApiCycleVisitLog(HttpServletRequest request,Model model){
		try {
			String  url = request.getParameter("url");
			SystemVisiteLog visiteLog = SystemVisiteLog.getApiVisiteStat().get(url);
			model.addAttribute("URL", visiteLog.getVisiteURL());
			model.addAttribute("APILOG", visiteLog.getCycle());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/logger/apivisistecyclelog";
	}
	
	@RequestMapping("/SysCycleVisiteLog")
	public String viewSysCycleVisitLog(HttpServletRequest request,Model model){
		try {
			String  url = request.getParameter("url");
			SystemVisiteLog visiteLog = SystemVisiteLog.getSystemVisiteStat().get(url);
			model.addAttribute("URL", visiteLog.getVisiteURL());
			model.addAttribute("APILOG", visiteLog.getCycle());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/logger/sysvisistecyclelog";
	}
	
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}

}
