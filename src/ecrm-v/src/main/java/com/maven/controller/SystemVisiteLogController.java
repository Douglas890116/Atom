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
			RunLoggerController.class.getName(), OutputManager.LOG_SYSTEMVISITELOG);
	
	@RequestMapping("/ApiVisiteLog")
	public String VisiteLog(HttpServletRequest request,Model model){
		model.addAttribute("APILOG", new ArrayList<SystemVisiteLog>(SystemVisiteLog.getApiVisiteStat().values()));
		model.addAttribute("APILOGSWITH", SystemVisiteLog.isAPIVisiteLogSwith());
		return "/logger/apivisistelog";
	}
	
	@RequestMapping("/SysVisiteLog")
	public String SYSVisiteLog(HttpServletRequest request,Model model){
		model.addAttribute("APILOG", new ArrayList<SystemVisiteLog>(SystemVisiteLog.getSystemVisiteStat().values()));
		model.addAttribute("APILOGSWITH", SystemVisiteLog.isSysVisiteLogSwith());
		return "/logger/sysvisistelog";
	}
	
	@RequestMapping("/SysSwith")
	public String SwithSYS(HttpServletRequest request,Model model){
		try {
			String  swith = request.getParameter("swith");
			if(swith.equals("on")){
				SystemVisiteLog.setSysVisiteLogSwith(true);
			}else{
				SystemVisiteLog.setSysVisiteLogSwith(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.Error(e.getMessage(), e);
		}
		return "redirect:/SystemVisite/SysVisiteLog";
	}
	
	@RequestMapping("/SysCycleSwith")
	public String SwithSYSCycleStat(HttpServletRequest request,Model model){
		try {
			String  url = request.getParameter("url");
			String  swith = request.getParameter("swith");
			SystemVisiteLog visiteLog = SystemVisiteLog.getSystemVisiteStat().get(url);
			if(swith.equals("on")){
				visiteLog.setOpencyclestat(true);
			}else{
				visiteLog.setOpencyclestat(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.Error(e.getMessage(), e);
		}
		return "redirect:/SystemVisite/SysVisiteLog";
	}
	
	
	@RequestMapping("/UpdateSysStatCycle")
	public String updateSYSStatCycle(HttpServletRequest request,Model model){
		try {
			String  url = request.getParameter("url");
			String  cycle = request.getParameter("cycle");
			SystemVisiteLog visiteLog = SystemVisiteLog.getSystemVisiteStat().get(url);
			int statCycle = Integer.parseInt(cycle.trim());
			visiteLog.setStatcycle(statCycle);
		} catch (Exception e) {
			e.printStackTrace();
			log.Error(e.getMessage(), e);
		}
		return "redirect:/SystemVisite/SysVisiteLog";
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
