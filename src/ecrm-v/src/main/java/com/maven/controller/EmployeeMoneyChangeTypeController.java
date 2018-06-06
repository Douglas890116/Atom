package com.maven.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.annotation.OperationLog;
import com.maven.constant.Constant;
import com.maven.entity.EmployeeMoneyChangeType;
import com.maven.logger.LoggerManager;
import com.maven.logger.OparetionDescription;
import com.maven.logger.OutputManager;
import com.maven.service.EmployeeMoneyChangeTypeService;
import com.maven.util.RandomString;

@Controller
@RequestMapping("/moneyChangeType")
public class EmployeeMoneyChangeTypeController extends BaseController {
	private static LoggerManager log = LoggerManager.getLogger(EmployeeMoneyChangeTypeController.class.getName(), OutputManager.LOG_EMPLOYEEMONEYCHANGETYPE);
	
	@Autowired
	private EmployeeMoneyChangeTypeService employeeMoneyChangeTypeServiceImpl;
	
	@RequestMapping("/index")
	public String index(){
		return "/moneyChangeType/moneyChangeTypeIndex"; 
	}
	
	@RequestMapping("/add")
	public String add(){
		return "/moneyChangeType/moneyChangeTypeAdd";
	}
	
	@RequestMapping("/update")
	public String update(Model model,HttpServletRequest request,HttpSession session){
		try{
			String moneyChangeTypeCode = request.getParameter("moneychangetypecode");
			//解密标识字段的值
			boolean mark = this.decodeSign(moneyChangeTypeCode, session.getId());
			if(mark){
				EmployeeMoneyChangeType employeeMoneyChangeType = employeeMoneyChangeTypeServiceImpl.getEmployeeMoneyChangeType(moneyChangeTypeCode.split("_")[1]);
				model.addAttribute("employeeMoneyChangeType", employeeMoneyChangeType);
				return "/moneyChangeType/moneyChangeTypeUpdate";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return Constant.PAGE_DECODEREFUSE;
	}
	
	@RequestMapping("/data")
	@ResponseBody
	public Map<String,Object> findEmployeeMoneyChangeTypeData(HttpServletRequest request,HttpSession session){
		Map<String, Object> map = getRequestParamters(request);
		try {
			List<EmployeeMoneyChangeType> list = employeeMoneyChangeTypeServiceImpl.findEmployeeMoneyChangeTypeData(map);
			int count = employeeMoneyChangeTypeServiceImpl.findEmployeeMoneyChangeTypeDataCount(map);
			//加密标识字段的值
			this.encryptSign(list,session.getId(),new String[]{"moneychangetypecode"});
			return super.formatPagaMap(list, count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 删除账变类型
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/deleteEmployeeMoneyChangeType")
	@OperationLog(OparetionDescription.MONEY_CHANGE_TYPE_DELETE)
	@ResponseBody
	public Map<String,Object> deleteEmployeeMoneyChangeType(HttpServletRequest request,HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			//获取加密之后的员工编码
			String md5value = request.getParameter("deleteCode");
			//解密标识字段的值
			boolean mark = this.decodeSign(md5value, session.getId());
			if(mark){
				employeeMoneyChangeTypeServiceImpl.deleteEmployeeMoneyChangeType(md5value.split("_")[1]);
				map.put("status", "success");
			}else{
				map.put("status", "failure");
			}
			return map;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
			map.put("status", "failure");
			return map;
		}
	}
	
	/**
	 * 批量删除账变类型
	 * @param request
	 */
	@RequestMapping("/deleteSelectEmployeeMoneyChangeType")
	@OperationLog(OparetionDescription.MONEY_CHANGE_TYPE_DELETE)
	@ResponseBody
	public Map<String,Object> deleteSelectEmployeeMoneyChangeType(HttpServletRequest request,HttpSession session){
		try {
			String temp = request.getParameter("sign");
			String[] array = temp.split(",");
			boolean mark = this.decodeSign(array, session.getId());
			if(mark){
				for (int i = 0; i < array.length; i++) {
					array[i]=array[i].split("_")[1];
				}
				employeeMoneyChangeTypeServiceImpl.deleteSelectEmployeeMoneyChangeType(array);
				return super.packJSON(Constant.BooleanByte.YES, "删除成功");
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	/**
	 * 保存账变类型
	 * @param session
	 * @param request
	 * @param game
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map<String,Object> saveEmployeeMoneyChangeType(@ModelAttribute EmployeeMoneyChangeType employeeMoneyChangeType){
		try {
			employeeMoneyChangeType.setMoneychangetypecode(RandomString.UUID());
			//保存新注册的员工
			employeeMoneyChangeTypeServiceImpl.saveEmployeeMoneyChangeType(employeeMoneyChangeType);
			return super.packJSON(Constant.BooleanByte.YES, "恭喜您,操作成功");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
			return super.packJSON(Constant.BooleanByte.NO, "对不起,操作失败");
		}
	}
	
	/**
	 * 修改账变类型
	 * @param session
	 * @param request
	 * @param game
	 * @return
	 */
	@RequestMapping("/updateSave")
	@OperationLog(OparetionDescription.MONEY_CHANGE_TYPE_UPDATE)
	@ResponseBody
	public Map<String,Object> updateEmployeeMoneyChangeType(@ModelAttribute EmployeeMoneyChangeType employeeMoneyChangeType){
		try {
			employeeMoneyChangeTypeServiceImpl.updateEmployeeMoneyChangeType(employeeMoneyChangeType);
			return super.packJSON(Constant.BooleanByte.YES, "恭喜您,操作成功");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
			return super.packJSON(Constant.BooleanByte.NO, "对不起,操作失败");
		}
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
