package com.maven.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.annotation.OperationLog;
import com.maven.constant.Constant;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeType;
import com.maven.entity.EnterpriseEmployeeType.Type;
import com.maven.logger.LoggerManager;
import com.maven.logger.OparetionDescription;
import com.maven.logger.OutputManager;
import com.maven.service.BusinessEmployeeTypeService;

@Controller
@RequestMapping("/employeeType")
public class EnterpriseEmployeeTypeController extends BaseController {
	
	private static LoggerManager log = LoggerManager.getLogger(EnterpriseEmployeeTypeController.class.getName(), OutputManager.LOG_EMPLOYEETYPE);
	//员工类别
	@Autowired
	private BusinessEmployeeTypeService businessEmployeeTypeService;
	
	
	@RequestMapping("/employeeTypeIndex")
	public String index(){
		return "/userjsp/employeeTypeIndex";
	}
	@RequestMapping("/add")
	public String add(HttpServletRequest request,HttpSession session,Model model){
		model.addAttribute(Constant.MENU_RELATION,request.getAttribute(Constant.MENU_RELATION));
		return "/userjsp/addEmployeeType";
	}
	
	@RequestMapping("/update")
	public String update(HttpServletRequest request,HttpSession session,Model model){
		model.addAttribute(Constant.MENU_RELATION,request.getAttribute(Constant.MENU_RELATION));
		try {
			String md5value=request.getParameter("employeetypecode");
			boolean mark = this.decodeSign(md5value, session.getId());
			Map<String,Object> map = new HashMap<String,Object>();
			if(mark){
				map.put("employeetypecode", md5value.split("_")[1]);
				List<EnterpriseEmployeeType> list = businessEmployeeTypeService.queryEmployeeType(map);
				//加密标识字段的值
				this.encryptSign(list,session.getId(),new String[]{"employeetypecode"});
				model.addAttribute("obj", list.get(0));
				return "/userjsp/updateEmployeeType";
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
		}
		return Constant.PAGE_DECODEREFUSE;
	}
	/**
	 * 查询数据
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping("/data")
	@ResponseBody
	public Map<String,Object> queryEmployeeType(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		//EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
		Map<String,Object> map = this.getRequestParamters(request);
		try {
			List<EnterpriseEmployeeType> list = businessEmployeeTypeService.queryEmployeeType(map);
			int count = businessEmployeeTypeService.queryEmployeeTypeCount(map);
			//加密标识字段的值
			this.encryptSign(list,session.getId(),new String[]{"employeetypecode"});
			return formatPagaMap(list, count);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 保存用户类型
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map<String,Object> saveEmployeeType(HttpServletRequest request){
		Map<String,Object> map = this.getRequestParamters(request);
		try {
			businessEmployeeTypeService.saveEmployeeType(map);
			return super.packJSON(Constant.BooleanByte.YES, "操作成功");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
			return super.packJSON(Constant.BooleanByte.NO, "操作失败");
		}
	}
	
	/**
	 * 删除用户类型
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/delete")
	@OperationLog(OparetionDescription.EMPLOYEE_TYPE_DELETE)
	@ResponseBody
	public Map<String,Object> deleteEmployeeType(HttpServletRequest request,HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			//获取加密之后的员工编码
			String md5value = request.getParameter("deleteCode");
			//解密标识字段的值
			boolean mark = this.decodeSign(md5value, session.getId());
			if(mark){
				businessEmployeeTypeService.deleteEmployeeType(md5value.split("_")[1]);
			}
			map.put("status", "success");
			return map;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
			map.put("status", "failure");
			return map;
		}
	}
	
	/**
	 * 批量删除
	 * @param request
	 */
	@RequestMapping("/deleteSelect")
	@OperationLog(OparetionDescription.EMPLOYEE_TYPE_DELETE)
	@ResponseBody
	public Map<String,Object> deleteSelect(HttpServletRequest request,HttpSession session){
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			//只有员工类型与企业类型的用户具有权限
			if(loginEmployee.getEmployeetypecode().equals(Type.企业号.value) ||loginEmployee.getEmployeetypecode().equals(Type.员工.value)){
				String temp = request.getParameter("sign");
				String[] array = temp.split(",");
				boolean mark = this.decodeSign(array, session.getId());
				if(mark){
					for (int i = 0; i < array.length; i++) {
						array[i]=array[i].split("_")[1];
					}
					businessEmployeeTypeService.tc_deleteSelect(array);
					return super.packJSON(Constant.BooleanByte.YES, "删除成功");
				}else{
					return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
				}
			}else{
				return super.packJSON(Constant.BooleanByte.NO, "您无法删除该数据");
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	/**
	 * 保存用户修改过的数据
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/saveUpdate")
	@OperationLog(OparetionDescription.EMPLOYEE_TYPE_UPDATE)
	@ResponseBody
	public Map<String,Object> updateEmployeeType(HttpServletRequest request,HttpSession session){
		Map<String,Object> parameter = this.getRequestParamters(request);
		try {
			//解密标识字段的值
			boolean mark = this.decodeSign(parameter.get("employeetypecode").toString(), session.getId());
			if(mark){
				parameter.put("employeetypecode", parameter.get("employeetypecode").toString().split("_")[1]);
				businessEmployeeTypeService.updateEmployeeType(parameter);
				return super.packJSON(Constant.BooleanByte.YES, "操作成功");
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
		}
		return super.packJSON(Constant.BooleanByte.NO, "操作失败");
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
