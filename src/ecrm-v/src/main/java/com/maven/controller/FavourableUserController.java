package com.maven.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.core.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityBetRecord.Enum_ecactivitycode;
import com.maven.entity.EnterpriseActivityCustomization;
import com.maven.entity.EnterpriseBrandActivity;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeType.Type;
import com.maven.entity.Favourable;
import com.maven.entity.FavourableUser;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.EnterpriseActivityCustomizationService;
import com.maven.service.EnterpriseBrandActivityService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.FavourableService;
import com.maven.service.FavourableUserService;
import com.maven.util.BeanToMapUtil;
import com.maven.util.StringUtils;
import com.maven.utils.Multiselect2sideUtil;
import com.maven.utils.StringUtil;

@Controller
@RequestMapping("/favourableuser")
public class FavourableUserController extends BaseController {
	
	private LoggerManager log = LoggerManager.getLogger(FavourableUserController.class.getName(),
			OutputManager.LOG_ACTIVITYBETRECORD);
	@Autowired
	private FavourableService favourableService;
	@Autowired
	private FavourableUserService favourableUserService;
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	
	/**
	 * index
	 * @param request
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model model){
		
		EnterpriseEmployee loginEmployee = (EnterpriseEmployee) request.getSession().getAttribute(Constant.USER_SESSION);
		
		Favourable item = new Favourable();
		/*********非超级管理员时只能查询本团队内的数据************/
		if( !SystemConstant.getProperties("admin.employeecode").equals(loginEmployee.getEmployeecode())){
			item.setEnterprisecode(loginEmployee.getEnterprisecode());
		}
		try {
			List<Favourable> list = favourableService.select(item);
			model.addAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/favourableuser/index";
	}
	
	/**
	 * add
	 * @param request
	 * @return
	 */
	@RequestMapping("/add")
	public String add(HttpServletRequest request, Model model){
		EnterpriseEmployee loginEmployee = (EnterpriseEmployee) request.getSession().getAttribute(Constant.USER_SESSION);
		
		List<FavourableUser> betrecords = null;
		try {
			//查询该企业下面的所有有效会员
			EnterpriseEmployee paramObj = new EnterpriseEmployee();
			paramObj.setEnterprisecode(loginEmployee.getEnterprisecode());
			paramObj.setEmployeetypecode(Type.会员.value);
			paramObj.setEmployeestatus( Byte.valueOf(EnterpriseEmployee.Enum_employeestatus.启用.value.toString()));
			List<EnterpriseEmployee> listEnterpriseEmployee = enterpriseEmployeeService.select(paramObj);
			
			//查询已设定好的分组数据
			Map<String, Object> parameter = getRequestParamters(request);
			parameter.put("enterprisecode", loginEmployee.getEnterprisecode());
			if(parameter.containsKey("favourableid") && parameter.get("favourableid").toString().length() > 0) {
				parameter.put("favourableid", parameter.get("favourableid"));
				model.addAttribute("favourableid", parameter.get("favourableid"));
				betrecords = favourableUserService.selectBetRecord(parameter);
			} else {
				betrecords = new ArrayList<FavourableUser>();
			}
			
			
			List<Multiselect2sideUtil> list = new ArrayList<Multiselect2sideUtil>();
			for (EnterpriseEmployee item : listEnterpriseEmployee) {
				boolean isSelected = false;
				for (FavourableUser temp : betrecords) {
					if(item.getEmployeecode().equals(temp.getEmployeecode())) {
						isSelected = true;
						break;
					}
				}
				Multiselect2sideUtil object = new Multiselect2sideUtil(item.getEmployeecode(), item.getLoginaccount(), isSelected );
				list.add(object);
			}
			model.addAttribute("list", list);
			
			
			//列表出当前所有优惠活动
			Favourable item = new Favourable();
			List<Favourable> listFavourable = favourableService.select(item);
			model.addAttribute("listFavourable", listFavourable);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/favourableuser/add";
	}
	
	/**
	 * Save
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/Save")
	@ResponseBody
	public Map<String, Object> Save(HttpServletRequest request, HttpSession session){
		try {		
			Map<String, Object> parameter = getRequestParamters(request);
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			
			String[] employeecodes = request.getParameterValues("searchable[]");
			String favourableid = request.getParameter("favourableid");
			String enterprisecode = loginEmployee.getEnterprisecode();
			
			favourableUserService.addBatchData(enterprisecode, favourableid, employeecodes);
			return super.packJSON(Constant.BooleanByte.YES, "操作成功");
			
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, e.getMessage());
		}
	}
	
	/**
	 * list页面请求数据
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/data")
	@ResponseBody
	public Map<String, Object> data(HttpServletRequest request, HttpSession session){
		try {		
			Map<String, Object> parameter = getRequestParamters(request);
			
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			/*********非超级管理员时只能查询本团队内的数据************/
			if(SystemConstant.getProperties("admin.employeecode").equals(loginEmployee.getEmployeecode())){
				//
			} else {
				parameter.put("enterprisecode", loginEmployee.getEnterprisecode());
			}
			
			List<FavourableUser> betrecords = favourableUserService.selectBetRecord(parameter);
			
			int count = favourableUserService.selectBetRecordCount(parameter);
			return super.formatPagaMap(betrecords, count);
			
			
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
	@RequestMapping("/doDelete")
	@ResponseBody
	public Map<String, Object> doDelete(HttpServletRequest request, HttpSession session){
		try {		
			Map<String, Object> parameter = getRequestParamters(request);
			
			favourableUserService.deleteActivityBetRecord(request.getParameter("lsh"));
			
			return super.packJSON(Constant.BooleanByte.YES, "操作成功");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, e.getMessage());
		}
	}
	
	

	@Override
	public LoggerManager getLogger() {
		return log;
	}
	
}
