package com.maven.controller;

import java.util.ArrayList;
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

import com.maven.annotation.RequestInterval;
import com.maven.constant.Constant;
import com.maven.entity.AgentItemInfo;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.AgentItemInfoService;
import com.maven.service.EnterpriseOperatingBrandService;
import com.maven.service.EnterpriseService;
import com.maven.util.BeanToMapUtil;

@Controller
@RequestMapping({ "/agent/item" })
public class AgentItemController extends BaseController {
	private static LoggerManager log = LoggerManager.getLogger(AgentItemController.class.getName(),
			OutputManager.LOG_ENTERPRISEOPERATINGBRAND);

	@Autowired
	private EnterpriseService enterpriseService;

	@Autowired
	private EnterpriseOperatingBrandService enterpriseOperatingBrandService;

	@Autowired
	private AgentItemInfoService agentItemInfoService;

	public LoggerManager getLogger() {
		return log;
	}

	@RequestMapping({ "/index" })
	public String list(HttpSession session, Model model) {
		EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute("ERCM_USER_SESSION");
		try {
			List<EnterpriseOperatingBrand> list = enterpriseOperatingBrandService
					.getEnterpriseBrand(ee.getEnterprisecode());
			model.addAttribute("list", list);

			session.setAttribute("brandList", list);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "/agent/item/item_list";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping({ "/data" })
	@ResponseBody
	public Map<String, Object> data(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute("ERCM_USER_SESSION");
			Map<String, Object> obj = getRequestParamters(request);
			obj.put("enterprisecode", ee.getEnterprisecode());

			List<EnterpriseOperatingBrand> list = (List<EnterpriseOperatingBrand>) session.getAttribute("brandList");
			List<String> brandCodeList = agentItemInfoService.selectBrandCode(ee.getEnterprisecode());
			List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();
			for (EnterpriseOperatingBrand enterpriseOperatingBrand : list) {
				boolean isAdd = true;
				for (String brandCode : brandCodeList) {
					if (brandCode.equals(enterpriseOperatingBrand.getBrandcode())) {
						isAdd = false;
						break;
					}
				}
				if (isAdd) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("enterprisecode", enterpriseOperatingBrand.getEnterprisecode());
					map.put("brandcode", enterpriseOperatingBrand.getBrandcode());
					paramList.add(map);
				}
			}
			if (paramList.size() != 0) {
				agentItemInfoService.addDefaultAgentItemInfo(paramList);
			}

			int rowCount = agentItemInfoService.selectAgentItemInfoCount(obj);
			List<AgentItemInfo> rows = agentItemInfoService.selectAgentItemInfo(obj);
			return formatPagaMap(rows, rowCount);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return null;
	}

	@RequestMapping({ "/add" })
	public String add(Model model, HttpSession session) {
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute("ERCM_USER_SESSION");
			List<EnterpriseOperatingBrand> list = enterpriseOperatingBrandService
					.getEnterpriseBrand(ee.getEnterprisecode());
			model.addAttribute("list", list);
			return "/agent/item/item_add";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "/error/actionFaild";
	}

	@RequestMapping({ "/save" })
	@ResponseBody
	@RequestInterval(millinsecond = 3000L)
	public Map<String, Object> save(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			Map<String, Object> object = getRequestParamters(request);

			EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute("ERCM_USER_SESSION");

//			String logopath = object.get("logopath").toString();
//			logopath = logopath.replaceAll("http://img.hyzonghe.net:80/", "https://img.hyzonghe.net/");

			AgentItemInfo agentItemInfo = (AgentItemInfo) BeanToMapUtil.convertMap(object, AgentItemInfo.class);
			agentItemInfo.setEnterprisecode(employee.getEnterprisecode());
//			agentItemInfo.setIconpath(logopath);
			agentItemInfoService.addAgentItemInfo(agentItemInfo);
			return packJSON(Constant.BooleanByte.YES.intValue(), "推广栏目保存成功");
		} catch (LogicTransactionRollBackException e) {
			log.Error(e.getMessage(), e);
			return packJSON(Constant.BooleanByte.NO.intValue(), e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return packJSON(Constant.BooleanByte.NO.intValue(), "推广栏目保存失败，请稍后尝试");
	}

	@RequestMapping({ "/delete" })
	@ResponseBody
	public Map<String, Object> delete(HttpServletRequest request, HttpSession session) {
		try {
			String[] sign = request.getParameterValues("sign[]");
			if ((sign == null) || (sign.length == 0))
				return packJSON(Constant.BooleanByte.NO.intValue(), "请选择需要删除的数据");
			List<String> brandcodes = new ArrayList<String>();
			for (String s : sign)
				brandcodes.add(s);
			agentItemInfoService.deleteAgentItemInfos(brandcodes);
			return packJSON(Constant.BooleanByte.YES.intValue(), "删除成功");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return packJSON(Constant.BooleanByte.NO.intValue(), "解密验证未通过，禁止操作");
	}

	@RequestMapping({ "/edit" })
	public String edit(HttpServletRequest request, Model model, HttpSession session) {
		try {
			String lsh = request.getParameter("lsh");
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute("ERCM_USER_SESSION");
			Enterprise enterprise = (Enterprise) enterpriseService.selectByPrimaryKey(ee.getEnterprisecode());
			AgentItemInfo agentItemInfo = (AgentItemInfo) agentItemInfoService.selectByPrimaryKey(lsh);
			model.addAttribute("enterprise", enterprise);
			model.addAttribute("item", agentItemInfo);
			List<EnterpriseOperatingBrand> list = enterpriseOperatingBrandService
					.getEnterpriseBrand(ee.getEnterprisecode());
			model.addAttribute("list", list);
			return "/agent/item/item_edit";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "/error/actionFaild";
	}

	@RequestMapping({ "/update" })
	@ResponseBody
	public Map<String, Object> update(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> object = getRequestParamters(request);
			EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute("ERCM_USER_SESSION");
//			String logopath = object.get("logopath").toString();
//			logopath = logopath.replaceAll("http://img.hyzonghe.net:80/", "https://img.hyzonghe.net/");
			AgentItemInfo agentItemInfo = (AgentItemInfo) getRequestParamters(request, AgentItemInfo.class);
			agentItemInfo.setEnterprisecode(employee.getEnterprisecode());
//			agentItemInfo.setIconpath(logopath);
			System.out.println(agentItemInfo.getContent());
			agentItemInfoService.updateAgentItemInfo(agentItemInfo);
			return packJSON(Constant.BooleanByte.YES.intValue(), "编辑信息已保存");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return packJSON(Constant.BooleanByte.NO.intValue(), "操作失败，请稍后尝试");
	}

	@RequestMapping({ "/content" })
	public String content(HttpServletRequest request, Model model, HttpSession session) {
		try {
			String lsh = request.getParameter("lsh");
			AgentItemInfo agentItemInfo = (AgentItemInfo) agentItemInfoService.selectByPrimaryKey(lsh);

			agentItemInfo.setContent(htmlEscape(agentItemInfo.getContent()));
			model.addAttribute("item", agentItemInfo);
			return "/agent/item/item_content";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "/error/actionFaild";
	}

	private String htmlEscape(String content) {
		String result = content.replaceAll("&quot;", "\"").replaceAll("&lt;", "<").replaceAll("&gt;", ">")
				.replaceAll("&nbsp;", " ");
		return result;
	}
}