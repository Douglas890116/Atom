package com.maven.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.constant.Constant;
import com.maven.entity.AgentSiteContact;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.AgentSiteContactService;

@RequestMapping("/agentcontact")
@Controller
public class AgentSiteContactController extends BaseController{

	private static LoggerManager log = LoggerManager.getLogger(AgentSiteContactController.class.getName(),
			OutputManager.LOG_AGENTSITECONTACT);
	
	@Autowired
	private AgentSiteContactService agentSiteContactService;
	
	@RequestMapping("/setting")
	public String agentContactSetting(HttpServletRequest request,HttpSession session,Model model){
		try {
			String sign = request.getParameter("domaincode");
			if(super.decodeSign(sign, session.getId())){
				Integer domaincode = Integer.parseInt(sign.split("_")[1].split("\\|")[0]);
				AgentSiteContact __contact =  agentSiteContactService.selectByDomaincode(domaincode);
				if(__contact!=null){
					__contact.setSign(super.encryptString(__contact.getId().toString(), session.getId()));
					model.addAttribute("contact", __contact);
				}
				model.addAttribute("domaincode", super.encryptString(domaincode.toString(), session.getId()));
				return "/registerlink/agentcontact";
			}else{
				return Constant.PAGE_DECODEREFUSE;
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	@RequestMapping("/saveContact")
	@ResponseBody
	public Map<String,Object> saveSetting(HttpServletRequest request,HttpSession session){
		try {
			AgentSiteContact __asc = super.getRequestParamters(request, AgentSiteContact.class);
			if(super.decodeSign(__asc.getSign1(), session.getId())){
				Integer __domaincode = Integer.parseInt(__asc.getSign1().split("_")[1]);
				__asc.setDomaincode(__domaincode);
				if(__asc.getSign()!=null){
					if(super.decodeSign(__asc.getSign(), session.getId())){
						Integer __id = Integer.parseInt(__asc.getSign().split("_")[1]); 
						__asc.setId(__id);
						agentSiteContactService.updateContact(__asc);
					}else{
						return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
					}
				}else{
					agentSiteContactService.saveContact(__asc);
				}
				return super.packJSON(Constant.BooleanByte.YES, "保存成功");
			}else{
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
