package com.maven.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.PermissionMenu;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.PermissionMenuService;
import com.maven.util.BeanToMapUtil;

@Controller
@RequestMapping("/PermissionMenu/")
public class PermissionMenuController extends BaseController{
	
	private final static String MAIN_MENUS = "MAIN_MENUS";

	private static LoggerManager log = LoggerManager.getLogger(
			PermissionMenuController.class.getName(), OutputManager.LOG_PERMISSIONMENU);
	
	@Autowired
	private PermissionMenuService permissionMenuService;
	
	@RequestMapping("/list")
	public String list(Model model, HttpSession session) {
		try {
			EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			List<PermissionMenu> menus = permissionMenuService.select(null);
			Map<String, String> mapping = new HashMap<String, String>();
			mapping.put("menucode", "sign");
			super.encryptSignTarget(menus, session.getId(), mapping);
			// 如果是sa用户，则在名称后面显示menucode
			if (SystemConstant.getProperties("admin.employeecode").equals(employee.getEmployeecode())) {
				for (PermissionMenu __pm : menus) {
					__pm.setMenuname(__pm.getMenuname() + " [ " + __pm.getMenucode() + " ]");
					__pm.setSign(super.encryptString(__pm.getMenucode(), session.getId()));
				}
			}
			String s = permissionMenuService.takeFormatMenu(menus);
			model.addAttribute("menus", s);
			session.setAttribute(MAIN_MENUS, menus);
			return "/permission/permission_list";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	@RequestMapping("/add")
	public String add(HttpServletRequest request,Model model,HttpSession session){
		try {
			Map<String,Object> params = super.getRequestParamters(request);
			String sign = (String)params.get("sign");
			if(super.decodeSign(sign, session.getId())){
				PermissionMenu parentMenue = permissionMenuService.selectByPrimaryKey(sign.split("_")[1]);
				parentMenue.setSign(sign);
				model.addAttribute("parentMenue", parentMenue);
				return "/permission/permission_add";
			}else{
				return Constant.PAGE_DECODEREFUSE;
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	@RequestMapping("/addFistMenu")
	public String addFistMenu(HttpServletRequest request,Model model,HttpSession session){
		try {
			Map<String,Object> object = getRequestParamters(request);
			PermissionMenu parentMenue = permissionMenuService.selectByPrimaryKey((String)object.get("menucode"));
			Map<String,String> ss = new HashMap<String, String>();
			ss.put("menucode", "sign");
			super.encryptSingleSignTarget(parentMenue, session.getId(), ss);
			model.addAttribute("parentMenue", parentMenue);
			return "/permission/permission_add";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Map<String,Object> save(HttpServletRequest request){
		Map<String,Object> object = getRequestParamters(request);
		try {
			object.put("isshow", 0);
			object.put("isoperate", 0);
			object.put("isprivacy", 0);
			permissionMenuService.addMenu(object);
			return super.packJSON(Constant.BooleanByte.YES, "新增菜单成功");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "新增菜单失败,请稍后尝试");
		}
	}
	
	@RequestMapping("/edit")
	public String edit(Model model,HttpServletRequest request,HttpSession session){
		Map<String,Object> params = getRequestParamters(request);
		String sign = (String)params.get("sign");
		try {
			if(super.decodeSign(sign, session.getId())){
				PermissionMenu menue = permissionMenuService.selectByPrimaryKey(sign.split("_")[1]);
				menue.setSign(sign);
				model.addAttribute("menue", menue);
				model.addAttribute("mainmenu", session.getAttribute(MAIN_MENUS));
				return "/permission/permission_edit";
			}else{
				return Constant.PAGE_DECODEREFUSE;
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public Map<String,Object> update(HttpServletRequest request,HttpSession session){
		Map<String,Object> params = getRequestParamters(request);
		String sign = (String)params.get("sign");
		try {
			if(super.decodeSign(sign, session.getId())){
				params.put("menucode", sign.split("_")[1]);
				permissionMenuService.updateMenu((PermissionMenu)
						BeanToMapUtil.convertMap(params, PermissionMenu.class));
				return super.packJSON(Constant.BooleanByte.YES, "编辑菜单成功");
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.YES, "编辑菜单失败,请稍后尝试");
		}
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String,Object> delete(HttpSession session,HttpServletRequest request){
		Map<String,Object> params = getRequestParamters(request);
		String sign = (String)params.get("sign");
		try {
			if(super.decodeSign(sign, session.getId())){
				String[] signs = sign.split("_");
				params.put("menucode", signs[1]);
				permissionMenuService.logicDelete(params);
				return super.packJSON(Constant.BooleanByte.YES, "删除成功");
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "删除失败,请稍后尝试");
		}
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}

}
