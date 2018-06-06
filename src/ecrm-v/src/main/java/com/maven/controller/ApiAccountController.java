package com.maven.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.cache.SystemCache;
import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.Game;
import com.maven.entity.UserLogs;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.EmployeeApiAccoutService;
import com.maven.service.UserLogsService;
import com.maven.util.GameUtils;

/**
 * 
 * 游戏账号管理
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/apiaccount")
public class ApiAccountController extends BaseController {

	private static LoggerManager log = LoggerManager.getLogger(EnterpriseOperatingBrandGameController.class.getName(),
			OutputManager.LOG_GAMEAPIINPUT);

	@Autowired
	private EmployeeApiAccoutService apiAccoutService;
	/** 企业员工 */
	@Autowired
	private UserLogsService userLogsService;
//	/** 隐私数据权限 */
//	@Autowired
//	private PrivateDataAccessService dataAccessService;
	/**
	 * index
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/index")
	@SuppressWarnings("unchecked")
	public String index(HttpServletRequest request, HttpSession session, Model model) {
		try {
			List<Game> listGame = (List<Game>) session.getAttribute(Constant.ENTERPRISE_GAMES);
			model.addAttribute("listGame", listGame);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/userjsp/apiaccount";
	}

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/pagelist")
	@ResponseBody
	public Map<String, Object> pagelist(HttpServletRequest request, HttpSession session, Model model) {

		try {
			Map<String, Object> params = getRequestParamters(request);

			/********* 非超级管理员时只能查询本团队内的数据 ************/
			EnterpriseEmployee ee = (EnterpriseEmployee) request.getSession().getAttribute(Constant.USER_SESSION);
			super.dataLimits(ee, params, session);
			
			if (!SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())) {
				params.put("enterprisecode", ee.getEnterprisecode());
			}
			List<EmployeeApiAccout> listEmployeeApiAccout = apiAccoutService.selectAll(params);
			// 在页面展示时将游戏类型换成中文
			if(null != listEmployeeApiAccout && listEmployeeApiAccout.size() > 0) {
				String gameType = null;
				for (int i = 0; i < listEmployeeApiAccout.size(); i++) {
					gameType = listEmployeeApiAccout.get(i).getGametype();
					listEmployeeApiAccout.get(i).setGametype(GameUtils.getGameTypeNameByGameType(gameType));
					gameType = null;
					//隐私数据限制
					if (null == session.getAttribute(Constant.PRIVATE_DATA_PSERSSION)) {
						listEmployeeApiAccout.get(i).setGamepassword("**********");
					}
				}
			}
			// 加密标识字段的值
			this.encryptSign(listEmployeeApiAccout, request.getSession().getId(), new String[] { "apiaccountcode" });

			int count = apiAccoutService.selectAllCount(params);

			Map<String, Object> data = super.formatPagaMap(listEmployeeApiAccout, count);

			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * 禁用选中的一条或者多条数据
	 * 
	 * @param request
	 */
	@RequestMapping("/disableSelectEmployee")
	@ResponseBody
	public Map<String, Object> disableSelectEmployee(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);

			String temp = request.getParameter("sign");
			String reason = request.getParameter("reason");
			String[] array = temp.split(",");
			boolean mark = this.decodeSign(array, session.getId());
			if (mark) {
				for (int i = 0; i < array.length; i++) {
					array[i] = array[i].split("_")[1];
				}

				apiAccoutService.tc_disableSelectEmployee(array);

				for (String employeecode : array) {
					EmployeeApiAccout item = apiAccoutService.selectByPrimaryKey(employeecode);

					// 更新缓存
					Map<String, EmployeeApiAccout> eapiaccounts = SystemCache.getInstance()
							.getEmployeeAllGameAccount(item.getEmployeecode());
					eapiaccounts.put(item.getGametype(), item);
					SystemCache.getInstance().setEmployeeAllGameAccount(item.getEmployeecode(), eapiaccounts);

					userLogsService.addActivityBetRecord(new UserLogs(item.getEnterprisecode(), item.getEmployeecode(),
							item.getLoginaccount(), UserLogs.Enum_operatype.游戏信息业务, "禁用[" + item.getGametype() + "]游戏的账号",
							loginEmployee.getLoginaccount(), reason));
				}

				return super.packJSON(Constant.BooleanByte.YES, "账号已禁用");
			} else {
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}

		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}

	/**
	 * 启用选中的一条或者多条数据
	 * 
	 * @param request
	 */
	@RequestMapping("/activateSelectEmployee")
	@ResponseBody
	public Map<String, Object> activateSelectEmployee(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);

			String temp = request.getParameter("sign");
			String reason = request.getParameter("reason");
			String[] array = temp.split(",");
			boolean mark = this.decodeSign(array, session.getId());
			if (mark) {
				for (int i = 0; i < array.length; i++) {
					array[i] = array[i].split("_")[1];
				}

				apiAccoutService.tc_activateSelectEmployee(array);

				for (String employeecode : array) {
					EmployeeApiAccout item = apiAccoutService.selectByPrimaryKey(employeecode);

					// 更新缓存
					Map<String, EmployeeApiAccout> eapiaccounts = SystemCache.getInstance()
							.getEmployeeAllGameAccount(item.getEmployeecode());
					eapiaccounts.put(item.getGametype(), item);
					SystemCache.getInstance().setEmployeeAllGameAccount(item.getEmployeecode(), eapiaccounts);

					userLogsService.addActivityBetRecord(new UserLogs(item.getEnterprisecode(), item.getEmployeecode(),
							item.getLoginaccount(), UserLogs.Enum_operatype.游戏信息业务, "启用[" + item.getGametype() + "]游戏的账号",
							loginEmployee.getLoginaccount(), reason));
				}

				return super.packJSON(Constant.BooleanByte.YES, "账号已启用");
			} else {
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}

		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}

	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
