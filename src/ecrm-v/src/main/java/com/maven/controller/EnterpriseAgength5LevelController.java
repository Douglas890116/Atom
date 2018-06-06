package com.maven.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.constant.Constant;
import com.maven.entity.EnterpriseAgength5Level;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeLevel;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.BusinessEmployeeLovelService;
import com.maven.service.EnterpriseAgength5LevelService;
@Controller
@RequestMapping("/agenth5Level")
public class EnterpriseAgength5LevelController extends BaseController{
	private static LoggerManager log = LoggerManager.getLogger(EnterpriseAgength5LevelController.class.getName(), OutputManager.LOG_ENTERPRISEINFORMATION);
	
	@Autowired
	private EnterpriseAgength5LevelService businessEmployeeLovelService;
	
	@RequestMapping("/index")
	public String index(){
		return "/userinfo/agenth5LevelIndex";
	}
	
	/**
	 * 查询数据
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> levelQuery(HttpServletRequest request,HttpSession session){
		try {
			Map<String,Object> object = getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			object.put("enterprisecode", ee.getEnterprisecode());
			List<EnterpriseAgength5Level> rows =  businessEmployeeLovelService.takelevelQuery(object);
			Map<String,String> s = new HashMap<String, String>();
			s.put("employeelevelcode", "sign");
			super.encryptSignTarget(rows, session.getId(), s);
			int rowCount = businessEmployeeLovelService.takeLevelQueryCount(object);
			//super.encryptSign(rows,session.getId(),"enterpriseinformationcode");
			return formatPagaMap(rows,rowCount);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 保存数据
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map<String,Object> save(HttpServletRequest request,HttpSession session){
		EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			if(Double.valueOf(request.getParameter("orderamount")) < 1) {
				map.put("status", "failure");
				map.put("message", "元宝数只能是整数");
				return map;
			}
		    
			EnterpriseAgength5Level employeeLevel = new EnterpriseAgength5Level();
			employeeLevel.setEnterprisecode(ee.getEnterprisecode());
			employeeLevel.setEmployeelevelname(request.getParameter("employeelevelname"));
			employeeLevel.setUpperlevelRate(new BigDecimal(request.getParameter("upperlevelRate")));
			employeeLevel.setUpperlevelRate2(new BigDecimal(request.getParameter("upperlevelRate2")));
			employeeLevel.setAgengtcount(Integer.parseInt(request.getParameter("agengtcount")));
			employeeLevel.setPersoncount(Integer.parseInt(request.getParameter("personcount")));
			employeeLevel.setOrderamount(new BigDecimal(request.getParameter("orderamount")));
			employeeLevel.setOrd(Integer.parseInt(request.getParameter("ord")));
			businessEmployeeLovelService.tc_save(employeeLevel);
			map.put("status", "success");
			return map;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
		}
		map.put("status", "failure");
		return map;
	}
	/**
	 * 修改数据
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Map<String,Object> update(HttpServletRequest request,HttpSession session){
		EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			if(Double.valueOf(request.getParameter("orderamount")) < 1) {
				map.put("status", "failure");
				map.put("message", "元宝数只能是整数");
				return map;
			}
			
			EnterpriseAgength5Level employeeLevel = new EnterpriseAgength5Level();
			employeeLevel.setEmployeelevelcode(request.getParameter("employeelevelcode"));
			employeeLevel.setEnterprisecode(ee.getEnterprisecode());
			employeeLevel.setEmployeelevelname(request.getParameter("employeelevelname"));
			employeeLevel.setUpperlevelRate(new BigDecimal(request.getParameter("upperlevelRate")));
			employeeLevel.setUpperlevelRate2(new BigDecimal(request.getParameter("upperlevelRate2")));
			employeeLevel.setAgengtcount(Integer.parseInt(request.getParameter("agengtcount")));
			employeeLevel.setPersoncount(Integer.parseInt(request.getParameter("personcount")));
			employeeLevel.setOrderamount(new BigDecimal(request.getParameter("orderamount")));
			employeeLevel.setOrd(Integer.parseInt(request.getParameter("ord")));
			businessEmployeeLovelService.tc_update(employeeLevel);
			map.put("status", "success");
			return map;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
		}
		map.put("status", "failure");
		return map;
	}
	
	/**
	 * 批量删除功能
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/batchDelete")
	@ResponseBody
	public Map<String,Object> batchDelete(HttpServletRequest request,HttpSession session){
		try {
			String temp = request.getParameter("sign");
			String[] array = temp.split(",");
			boolean mark = this.decodeSign(array, session.getId());
			if(mark){
				for (int i = 0; i < array.length; i++) {
					array[i]=array[i].split("_")[1];
				}
				businessEmployeeLovelService.tc_batchDelete(array);
				return super.packJSON(Constant.BooleanByte.YES, "删除成功");
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		}catch (Exception e) {
				e.printStackTrace();
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	/**
	 * 删除功能
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String,Object> delete(HttpServletRequest request,HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String enterpriseCode = request.getParameter("deleteCode");
			boolean mark = this.decodeSign(enterpriseCode, session.getId());
			if(mark){
				businessEmployeeLovelService.tc_delete(enterpriseCode.split("_")[1]);
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
	
	
	
	
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}

}
