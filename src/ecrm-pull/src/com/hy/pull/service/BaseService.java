package com.hy.pull.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hy.pull.common.util.DateUtil;
import com.hy.pull.mapper.BaseMapper;

/**
 * 服务基类
 * 创建日期 2016-10-06
 * @author temdy
 */
@Service
public abstract class BaseService {
	
	/********************************************
	 * 
	 * MG=只能拉取一个小时范围的
	 * IBC和AOI只能拉取30分钟内范围的
	 * 
	 */
	public int backMinute = -5;//为避免延迟导致的空隙问题，开始时间向后退5分钟
	public int forwordMinute = 230;//每次拉取时间的范围，分钟
	
	
	
	
	public enum Enum_flag{
    	正常(0,"正常"),
    	异常(1,"异常");
    	public Integer value;
    	public String desc;
    	private Enum_flag(Integer _value,String _desc){
    		this.value = _value;
    		this.desc = _desc;
    	}
    }
	
	
	
	
	/**
	 * 按条件拉取数据的方法
	 * @param entity 条件集合
	 * @return 数据行数
	 */
	public abstract Integer pullData(Map<String,Object> entity) throws Exception;

	/**
	 * 获取某个字段的汇总的总量、本年、本月、本周、昨天、今天等统计信息的通用方法
	 * @author temdy
	 * @param entity 条件集合
	 * @param baseMapper 通用操作接口
	 * @return Map 总量、本年、本月、本周、昨天、今天等统计数据
	 */
	public Map<String,Object> statSum(Map<String,Object> entity,BaseMapper baseMapper){
		Map<String,Object> ret = new HashMap<String,Object>();
		float all = 0;
		float year = 0;
		float month = 0;
		float week = 0;
		float yesterday = 0;
		float today = 0;
		float lastWeek = 0;
		
		//获取总汇总
		all = Float.parseFloat(baseMapper.sum(entity));
		//获取昨天的汇总
		entity.put("stat", 1);
		yesterday = Float.parseFloat(baseMapper.sum(entity));
		
		//获取今天的汇总
		entity.put("stat", 2);
		today = Float.parseFloat(baseMapper.sum(entity));
		
		//获取本周的汇总
		entity.put("stat", 3);
		week = Float.parseFloat(baseMapper.sum(entity));
		
		//获取本月的汇总
		entity.put("stat", 4);
		month = Float.parseFloat(baseMapper.sum(entity));
		
		//获取本年的汇总
		entity.put("stat", 5);
		year = Float.parseFloat(baseMapper.sum(entity));
		
		//获取本年的汇总
		entity.put("stat", 6);
		lastWeek = Float.parseFloat(baseMapper.sum(entity));
		
		entity.put("stat", null);		
		
		ret.put("all", all);
		ret.put("year", year);
		ret.put("month", month);
		ret.put("week", week);
		ret.put("yesterday", yesterday);
		ret.put("today", today);
		ret.put("lastWeek", lastWeek);
		return ret;
	}
	
	/**
	 * 获取数量的汇总的总量、本年、本月、本周、昨天、今天等统计信息的通用方法
	 * @author temdy
	 * @param entity 条件集合
	 * @param baseMapper 通用操作接口
	 * @return Map 总量、本年、本月、本周、昨天、今天等统计数据
	 */
	public Map<String,Object> statCount(Map<String,Object> entity,BaseMapper baseMapper){
		Map<String,Object> ret = new HashMap<String,Object>();
		int all = 0;
		int year = 0;
		int month = 0;
		int week = 0;
		int yesterday = 0;
		int today = 0;
		int lastWeek = 0;
		
		//获取总数量
		all = baseMapper.count(entity);
		//获取昨天的数量
		entity.put("stat", 1);
		yesterday = baseMapper.count(entity);
		
		//获取今天的数量
		entity.put("stat", 2);
		today = baseMapper.count(entity);
		
		//获取本周的数量
		entity.put("stat", 3);
		week = baseMapper.count(entity);
		
		//获取本月的数量
		entity.put("stat", 4);
		month = baseMapper.count(entity);
		
		//获取本年的数量
		entity.put("stat", 5);
		year = baseMapper.count(entity);
		
		//获取本年的数量
		entity.put("stat", 6);
		lastWeek = baseMapper.count(entity);
		
		entity.put("stat", null);
		
		ret.put("all", all);
		ret.put("year", year);
		ret.put("month", month);
		ret.put("week", week);
		ret.put("yesterday", yesterday);
		ret.put("today", today);
		ret.put("lastWeek", lastWeek);
		return ret;
	}
	
	public static List<String> getListStartEndTime(String format,int step,int stepType) {
		List<String> list = new ArrayList<String>();
		
		if(stepType != Calendar.HOUR_OF_DAY && stepType != Calendar.MINUTE) {
			stepType = Calendar.MINUTE;
		}
		
		
		
		String lastDate = DateUtil.parse(DateUtil.add(new Date(), Calendar.DATE, -1), "yyyy-MM-dd");//
		//String lastTime = DateUtil.parse(DateUtil.add(new Date(), Calendar.DATE, -1), "yyyy-MM-dd HH:mm:ss");//
		
		String enddate = "";
		
		List<String> listtemp = new ArrayList<String>();
		//小时的间隔
		if(stepType == Calendar.HOUR_OF_DAY) {
			
			if(step >= 24) {
				step = 1;
			}
			
			for (int i = 0; i <= 24; i+=step ) {
				String time = "";
				if(i < 10) {
					time = "0"+i+":00:00";
				} else {
					time = i+":00:00";
				}
				if(time.equals("24:00:00")) {
					time = "23:59:59";
				}
				enddate = lastDate+" "+time;
				
				listtemp.add(DateUtil.parse(DateUtil.parse(enddate, "yyyy-MM-dd HH:mm:ss"), format) );
			}
		} 
		else if(stepType == Calendar.MINUTE) {
			
			if(step > 60) {
				step = 30;
			}
			
			enddate = lastDate+" 00:00:00";
			listtemp.add(DateUtil.parse(DateUtil.parse(enddate, "yyyy-MM-dd HH:mm:ss"), format) );
			
			Date maxdate = DateUtil.parse(lastDate+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
			long max = maxdate.getTime();
			
			Date next = DateUtil.parse(enddate, "yyyy-MM-dd HH:mm:ss");
			//循环
			while(true) {
				next = DateUtil.add(next, Calendar.MINUTE, step);
				if(next.getTime() > max) {
					
					listtemp.add(DateUtil.parse(maxdate, format) );
					
					break;//结束
				} else {
					listtemp.add(DateUtil.parse(next, format) );
				}
			}
			
		}
		
		for (int i = 1; i < listtemp.size(); i++) {
			String string0 = listtemp.get(i-1);
			String string1 = listtemp.get(i);
//			System.out.println(string0+"="+string1);
			list.add(string0+"="+string1);
		}
		return list;
	}
	
	public static void main(String[] args) {
//		getListStartEndTime( "yyyy-MM-dd HH:mm:ss", 4, 2);
//		getListStartEndTime( "yyyyMMddHHmmss", 4, Calendar.HOUR_OF_DAY);
//		getListStartEndTime( "yyyy-MM-dd HH:mm:ss", 3, Calendar.HOUR_OF_DAY);
		
		getListStartEndTime( "yyyy-MM-dd HH:mm:ss", 60, Calendar.MINUTE);
	}
}
