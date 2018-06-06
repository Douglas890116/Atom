package com.maven.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.constant.Constant;
import com.maven.entity.ApiTagXmlTimer;
import com.maven.entity.DataHandle;
import com.maven.entity.DataHandleLogs;
import com.maven.entity.DataHandleMaintenance;
import com.maven.entity.EnterpriseEmployee;
import com.maven.game.enums.GameEnum.Enum_GameType;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.ApiTagXmlTimerService;
import com.maven.service.DataHandleLogsService;
import com.maven.service.DataHandleMaintenanceService;
import com.maven.service.DataHandleService;

@Controller
@RequestMapping("/datahand")
public class DataHandleController extends BaseController {

	private LoggerManager log = LoggerManager.getLogger(DataHandleController.class.getName(),
			OutputManager.LOG_ACTIVITYBETRECORD);
	@Autowired
	private DataHandleMaintenanceService dataHandleMaintenanceService;
	@Autowired
	private DataHandleService dataHandleService;
	@Autowired
	private DataHandleLogsService dataHandleLogsService;
	@Autowired
	private ApiTagXmlTimerService apiTagXmlTimerService;

	/**
	 * 数据同步状态
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/status")
	public String status(HttpServletRequest request, Model model) {

		return "/datahand/status";
	}

	/**
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/statusdata")
	@ResponseBody
	public Map<String, Object> statusdata(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> parameter = getRequestParamters(request);

			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			List<DataHandleMaintenance> betrecords = dataHandleMaintenanceService.selectBetRecord(parameter);
			for (DataHandleMaintenance dataHandleMaintenance : betrecords) {
				Enum_GameType __tt = Enum_GameType.getByGametype(dataHandleMaintenance.getGametype());
				dataHandleMaintenance.setGamename(__tt.name);
			}
			int countRecord = dataHandleMaintenanceService.selectBetRecordCount(parameter);
			Map<String, Object> data = super.formatPagaMap(betrecords, countRecord);

			return data;

		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}

	@RequestMapping(value = "/updateState", method = RequestMethod.POST)
	@ResponseBody
	public Boolean updateState(@RequestParam String gametype, @RequestParam Integer flag) {
		if (gametype != null && !gametype.equals("")) {
			if (flag != null) {
				DataHandleMaintenance dataHandleMaintenance=new DataHandleMaintenance();
				dataHandleMaintenance.setGametype(gametype);
				dataHandleMaintenance.setFlag(flag);
				dataHandleMaintenance.setLasttime(new Date());
				try {
					dataHandleMaintenanceService.updateActivityBetRecord(dataHandleMaintenance);
					return true;
				} catch (Exception e) {
					e.printStackTrace();
					log.Error(e.getMessage(), e);
					return false;
				}
			}
		}
		return false;
	}

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式

	/**
	 * 数据同步清单
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model model) {
		return "/datahand/index";
	}

	/**
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/indexdata")
	@ResponseBody
	public Map<String, Object> indexdata(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> parameter = getRequestParamters(request);

			Date currendate = new Date();

			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			List<DataHandle> betrecords = dataHandleService.selectBetRecord(parameter);
			for (DataHandle dataHandle : betrecords) {
				if (dataHandle.getLasttime().length() == 14) {
					Date date = sdf.parse(dataHandle.getLasttime());
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.add(Calendar.MINUTE, 30);

					if (calendar.getTime().getTime() < currendate.getTime()) {// 超过30分钟
						dataHandle.setFlag(1);
					}
				}
			}

			int countRecord = dataHandleService.selectBetRecordCount(parameter);
			Map<String, Object> data = super.formatPagaMap(betrecords, countRecord);

			return data;

		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/updatedata")
	@ResponseBody
	public Map<String, Object> updatedata(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> parameter = getRequestParamters(request);

			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			DataHandle betrecords = super.getRequestParamters(request, DataHandle.class);
			dataHandleService.updateActivityBetRecord(betrecords);

			return super.packJSON(Constant.BooleanByte.YES, "成功");

		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}

	/**
	 * 数据同步日志
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/logs")
	public String logs(HttpServletRequest request, Model model) {
		return "/datahand/log";
	}

	/**
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/logdata")
	@ResponseBody
	public Map<String, Object> logdata(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> parameter = getRequestParamters(request);

			Date currendate = new Date();

			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			List<DataHandleLogs> betrecords = dataHandleLogsService.selectBetRecord(parameter);

			int countRecord = dataHandleLogsService.selectBetRecordCount(parameter);
			Map<String, Object> data = super.formatPagaMap(betrecords, countRecord);

			return data;

		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * AG数据同步任务
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/tag")
	public String tag(HttpServletRequest request, Model model) {
		return "/datahand/tag";
	}

	/**
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/tagdata")
	@ResponseBody
	public Map<String, Object> tagdata(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> parameter = getRequestParamters(request);

			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			List<ApiTagXmlTimer> betrecords = apiTagXmlTimerService.selectBetRecord(parameter);
			for (ApiTagXmlTimer apiTagXmlTimer : betrecords) {

				boolean flag = getRemark(apiTagXmlTimer.getUpdatetime());
				if (flag) {
					apiTagXmlTimer.setRemark("<font color='red'>超过2小时未采集</font>");
				} else {
					apiTagXmlTimer.setRemark("");
				}
			}

			int countRecord = apiTagXmlTimerService.selectBetRecordCount(parameter);
			Map<String, Object> data = super.formatPagaMap(betrecords, countRecord);

			return data;

		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/settag")
	@ResponseBody
	public Map<String, Object> settag(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> parameter = getRequestParamters(request);

			if (parameter.get("updatetime").toString().length() != 12) {
				return super.packJSON(Constant.BooleanByte.NO, "更新失败：时间格式不正确");
			}

			ApiTagXmlTimer data = new ApiTagXmlTimer();
			data.setUpdatetime(parameter.get("updatetime").toString());
			apiTagXmlTimerService.updateActivityBetRecord(data);

			return super.packJSON(Constant.BooleanByte.YES, "已更新");

		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "更新失败：" + e.getMessage());
		}
	}

	/**
	 * 根据时间判断是否超过2小时没有采集数据
	 * 
	 * @param max
	 * @return
	 */
	private static boolean getRemark(String max) {
		Calendar calendar = Calendar.getInstance();

		try {
			// 传入时间就是美东时间
			Date maxdate = null;
			calendar.setTime(com.hy.pull.common.util.DateUtil.parse(max, "yyyyMMddHHmm"));
			calendar.add(Calendar.MINUTE, +120);
			maxdate = calendar.getTime();

			// 当前时间转换为美东时间
			Date currendate = null;
			calendar.setTime(new Date());
			calendar.add(Calendar.HOUR_OF_DAY, -12);
			currendate = calendar.getTime();

			if (currendate.getTime() > maxdate.getTime()) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public LoggerManager getLogger() {
		return log;
	}

}
