package com.maven.logger;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class SystemVisiteLog {
	
	private static Map<String,SystemVisiteLog> systemVisiteStat = new HashMap<String, SystemVisiteLog>();
	
	private static Map<String,SystemVisiteLog> apiVisiteStat = new HashMap<String, SystemVisiteLog>();
	
	private static boolean APIVisiteLogSwith = true;
	
	private static boolean SysVisiteLogSwith = true;
	
	/**
	 * 访问URL
	 */
	private String visiteURL;
	/**
	 * 访问方法名
	 */
	private String methodName;
	/**
	 * 访问次数
	 */
	private int visiteTime;
	/**
	 * 第一次访问时间
	 */
	private Date firstVisitDate;
	/**
	 * 最后一次访问时间
	 */
	private Date lastVisiteDate;
	/**
	 * 是否开启周期统计
	 */
	private boolean opencyclestat;
	/**
	 * 统计周期(秒)
	 */
	private int statcycle = 3600 ;
	/**
	 * 周期访问次数
	 */
	private Stack<SystemVisiteLog.Cycle> cycle = new Stack<SystemVisiteLog.Cycle>();
	
	private SystemVisiteLog(String url){
		this.visiteURL = url;
		this.firstVisitDate = new Date();
		this.visiteTime = 0;
	}
	/**
	 * 管理系统访问统计-触发
	 * @param url
	 */
	public static void SystemTrigger(String url){
		if(SysVisiteLogSwith){
			SystemVisiteLog visiteLog = systemVisiteStat.get(url);
			if(visiteLog == null){
				visiteLog = new SystemVisiteLog(url);
				systemVisiteStat.put(url, visiteLog);
			}
			visiteLog.addVisiTime();
		}
	}
	
	/**
	 * API访问统计 -触发
	 * @param url
	 */
	public static void ApiTrigger(String url){
		if(SysVisiteLogSwith){
			SystemVisiteLog visiteLog = apiVisiteStat.get(url);
			if(visiteLog == null){
				visiteLog = new SystemVisiteLog(url);
				apiVisiteStat.put(url, visiteLog);
			}
			visiteLog.addVisiTime();
		}
	}
	
	/**
	 * 增加访问次数
	 */
	public void addVisiTime(){
		this.visiteTime ++;
		this.lastVisiteDate = new Date();
		if(opencyclestat){
			if(this.cycle.size()==0){
				cycle.push(new Cycle());
			}
			Cycle c = cycle.peek();
			Calendar calendar =Calendar.getInstance();
			calendar.setTime(c.getStartDate());
			calendar.add(Calendar.SECOND, this.statcycle);
			if(calendar.getTime().after(new Date())){
				c.addVisiteTime();
			}else{
				c.setEndDate(new Date());
				Cycle cc = new Cycle();
				cc.addVisiteTime();
				cycle.push(cc);
			}
		}
	}
	
	
	public class Cycle implements Cloneable{
		/**
		 * 起始时间
		 */
		private Date startDate;
		/**
		 * 结束时间
		 */
		private Date endDate;
		/**
		 * 访问次数
		 */
		private Integer visiteTime = 0;
		
		public Cycle(){
			this.startDate = new Date();
		}
		public void addVisiteTime(){
			this.visiteTime ++;
		}
		public Date getStartDate() {
			return startDate;
		}
		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}
		public Date getEndDate() {
			return endDate;
		}
		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}
		public Integer getVisiteTime() {
			return visiteTime;
		}
		public void setVisiteTime(Integer visiteTime) {
			this.visiteTime = visiteTime;
		}
		
		@Override
		protected Cycle clone() throws CloneNotSupportedException {
			return (Cycle)super.clone();
		}
	}



	public static Map<String, SystemVisiteLog> getSystemVisiteStat() {
		return systemVisiteStat;
	}

	public static Map<String, SystemVisiteLog> getApiVisiteStat() {
		return apiVisiteStat;
	}
	public String getVisiteURL() {
		return visiteURL;
	}

	public String getMethodName() {
		return methodName;
	}

	public int getVisiteTime() {
		return visiteTime;
	}

	public Date getFirstVisitDate() {
		return firstVisitDate;
	}

	public Date getLastVisiteDate() {
		return lastVisiteDate;
	}

	public boolean isOpencyclestat() {
		return opencyclestat;
	}

	public void setOpencyclestat(boolean opencyclestat) {
		this.opencyclestat = opencyclestat;
	}
	public long getStatcycle() {
		return statcycle;
	}

	public void setStatcycle(int statcycle) {
		this.statcycle = statcycle;
	}
	public List<Cycle> getCycle() {
		return cycle;
	}
	public static boolean isAPIVisiteLogSwith() {
		return APIVisiteLogSwith;
	}
	public static void setAPIVisiteLogSwith(boolean aPIVisiteLogSwith) {
		APIVisiteLogSwith = aPIVisiteLogSwith;
	}
	public static boolean isSysVisiteLogSwith() {
		return SysVisiteLogSwith;
	}
	public static void setSysVisiteLogSwith(boolean sysVisiteLogSwith) {
		SysVisiteLogSwith = sysVisiteLogSwith;
	}
}
