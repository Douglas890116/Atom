package com.hy.pull.controller.manager.game;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hy.pull.common.util.DateUtil;
import com.hy.pull.mapper.ApiTagXmlTimerMapper;
import com.hy.pull.mapper.DataHandleMaintenanceMapper;
import com.hy.pull.mapper.DataHandleMapper;

/**
 * 定时任务管理器
 * 创建日期 2016-10-08
 * @author temdy
 */
@Controller
@Scope("request")
@RequestMapping("/datahand")
public class DatahanderController {
	
	@Autowired
	private DataHandleMaintenanceMapper dataHandleMaintenanceMapper;
	@Autowired
	private DataHandleMapper dataHandleMapper;
	@Autowired
	private ApiTagXmlTimerMapper apiTagXmlTimerMapper;
	
	/**
	 * 返回设备类型
	 * 
	 * @param rows
	 * @param rowCount
	 * @return
	 * @throws Exception
	 */
	private static String getSessionDeviceType(HttpServletRequest request) {
		if( com.hy.pull.common.util.WebInfoHandle.checkAgentIsMobile(request) == true ) {
			return "h5";
		} else {
			return "pc";
		}
	}
	
	/**
	 * 跳转页面操作
	 * @author temdy
	 * @param entity 提交表单集合
	 * @param request 请求对象
	 * @param response 响应对象
	 * @return 跳转URL
	 */
	@RequestMapping(value = "/list")
	public String list(@RequestParam Map<String, Object> entity, HttpServletRequest request, HttpServletResponse response) {
		List<Map<String, Object>> list = null;
		try{
			
			list = dataHandleMaintenanceMapper.selectAll();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		if(getSessionDeviceType(request).equals("pc")) {
			for (Map<String, Object> map : list) {
				if(map.get("flag").toString().equals("1")) {
					map.put("flag", "<font color='green'>正常</font>");
				} else {
					map.put("flag", "<font color='red'>维护中。。。</font>");
				}
			}
			request.setAttribute("list", list);
			return "/datahand/list";
		} else {
			for (Map<String, Object> map : list) {
				if(map.get("flag").toString().equals("1")) {
					map.put("flag", "正常</font>");
				} else {
					map.put("flag", "维护中。。。");
				}
			}
			request.setAttribute("list", list);
			return "/datahand/list_h5";
		}
	}
	
	@RequestMapping(value = "/listTask")
	public String listTask(@RequestParam Map<String, Object> entity, HttpServletRequest request, HttpServletResponse response) {
		try{
			
			List<Map<String, Object>> list = dataHandleMapper.selectAll();
			for (Map<String, Object> map : list) {
				if(map.get("lasttime") != null && map.get("lasttime").toString().length() == "20171113132032".length()) {
					boolean flag = getTimeOut(map.get("lasttime").toString());
					if(flag) {
						map.put("lasttime", "<font color='red'>超过1小时未采集</font>");
					} 
				}
				
			}
			
			request.setAttribute("list", list);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		if(getSessionDeviceType(request).equals("pc")) {
			return "/datahand/listTask";
		} else {
			return "/datahand/listTask_h5";
		}
	}
	
	@RequestMapping(value = "/listTag")
	public String listTag(@RequestParam Map<String, Object> entity, HttpServletRequest request, HttpServletResponse response) {
		try{
			
			List<Map<String, Object>> list = apiTagXmlTimerMapper.selectAll();
			for (Map<String, Object> map : list) {
				if(map.get("xmltype").toString().equals("normal")) {
					map.put("xmltype", "正常");
				} else {
					map.put("xmltype", "补单数据");
				}
				
				boolean flag = getRemark(map.get("updatetime").toString());
				if(flag) {
					map.put("remark", "<font color='red'>超过1小时未采集</font>");
				} else {
					map.put("remark", "正常");
				}
			}
			request.setAttribute("list", list);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		if(getSessionDeviceType(request).equals("pc")) {
			return "/datahand/listTag";
		} else {
			return "/datahand/listTag_h5";
		}
	}
	
	/**
	 * 批量重置AG数据采集任务
	 * @author temdy
	 * @param entity 提交表单集合
	 * @param request 请求对象
	 * @param response 响应对象
	 * @return msg 操作结果
	 */
	@RequestMapping(value = "/gosetTag")
	public String gosetTag(@RequestParam Map<String, Object> entity, HttpServletRequest request, HttpServletResponse response) {
		if(getSessionDeviceType(request).equals("pc")) {
			return "/datahand/setTag";
		} else {
			return "/datahand/setTag_h5";
		}
	}
	/**
	 * 批量重置AG数据采集任务
	 * @author temdy
	 * @param entity 提交表单集合
	 * @param request 请求对象
	 * @param response 响应对象
	 * @return msg 操作结果
	 */
	@RequestMapping(value = "/setTag")
	@ResponseBody
	public Object setTag(@RequestParam Map<String, Object> entity, HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> ret = new HashMap<String,Object>();
		ret.put("msg","更新失败！");
		try{			
			if(entity.get("updatetime").toString().length() != 12) {
				ret.put("msg","时间格式错误");
				return ret;
			}
			
			int rows = apiTagXmlTimerMapper.update(entity);	
			if(rows > 0){
				ret.put("msg","更新成功！");
				return ret;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return ret;
	}
	
	/**
	 * 更新其他定时任务的数据
	 * @author temdy
	 * @param entity 提交表单集合
	 * @param request 请求对象
	 * @param response 响应对象
	 * @return msg 操作结果
	 */
	@RequestMapping(value = "/gosetOther")
	public String gosetOther(@RequestParam Map<String, Object> entity, HttpServletRequest request, HttpServletResponse response) {
		String handlecode = request.getParameter("handlecode");
		if(handlecode.equals("begin.time.hq.ag") || handlecode.equals("begin.time.hq.og.og")) {
			request.setAttribute("messages", handlecode+"不支持更新");
			return listTask(entity, request, response);
		}
		request.setAttribute("handlecode", handlecode);
		if(getSessionDeviceType(request).equals("pc")) {
			return "/datahand/setOther";
		} else {
			return "/datahand/setOther_h5";
		}
	}
	/**
	 * 更新
	 * @author temdy
	 * @param entity 提交表单集合
	 * @param request 请求对象
	 * @param response 响应对象
	 * @return msg 操作结果
	 */
	@RequestMapping(value = "/setOther")
	@ResponseBody
	public Object setOther(@RequestParam Map<String, Object> entity, HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> ret = new HashMap<String,Object>();
		ret.put("msg","更新失败！");
		try{			
			if(entity.get("updatetime").toString().length() != "2017-08-17 22:14:00".length()) {
				ret.put("msg","时间格式错误");
				return ret;
			}
			
			int rows = dataHandleMapper.update(entity);	
			if(rows > 0){
				ret.put("msg","更新成功！");
				return ret;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return ret;
	}
	
	/**
	 * 根据时间判断是否超过1小时没有采集数据
	 * @param max
	 * @return
	 */
	private static boolean getTimeOut(String currentime) {
		Calendar calendar = Calendar.getInstance();
		
		try {
			//传入时间就是美东时间
			Date maxdate = null;
			calendar.setTime(DateUtil.parse(currentime, "yyyyMMddHHmmss"));
			calendar.add(Calendar.MINUTE, +60);
			maxdate = calendar.getTime();
			
			if(new Date().getTime() > maxdate.getTime()) {
				return true;
			}
			
		} catch( Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 根据时间判断是否超过1小时没有采集数据
	 * @param max
	 * @return
	 */
	private static boolean getRemark(String max) {
		Calendar calendar = Calendar.getInstance();
		
		try {
			//传入时间就是美东时间
			Date maxdate = null;
			calendar.setTime(DateUtil.parse(max, "yyyyMMddHHmm"));
			calendar.add(Calendar.MINUTE, +60);
			maxdate = calendar.getTime();
			
			//当前时间转换为美东时间
			Date currendate = null;
			calendar.setTime(new Date());
			calendar.add(Calendar.HOUR_OF_DAY, -12);
			currendate = calendar.getTime();
			
			if(currendate.getTime() > maxdate.getTime()) {
				return true;
			}
			
		} catch( Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
