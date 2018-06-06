package com.maven.controller.agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.maven.controller.BaseController;
import com.maven.entity.AgentBannerInfo;
import com.maven.entity.AgentItemInfo;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.AgentBannerInfoService;
import com.maven.service.AgentItemInfoService;

@RequestMapping("/agent/promotion")
@RestController
public class AgentPromotionController extends BaseController {

	private static LoggerManager log = LoggerManager.getLogger(AgentPromotionController.class.getName(),
			OutputManager.LOG_UER_AGENT);

	@Resource
	private AgentBannerInfoService agentBannerInfoService;

	@Resource
	private AgentItemInfoService agentItemInfoService;

	@Override
	public LoggerManager getLogger() {
		return log;
	}

	/**
	 * Banner图展示
	 */
	@RequestMapping(value = "/showBannerInfo")
	public List<AgentBannerInfo> showBannerInfo(@RequestParam String brandcode, @RequestParam String bannertype) {
		List<AgentBannerInfo> resultList = new ArrayList();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("brandcode", brandcode);
		paramMap.put("bannertype", bannertype);
		try {
			resultList = agentBannerInfoService.selectAgentBannerInfo(paramMap);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return resultList;
	}

	/**
	 * 栏目展示
	 */
	@RequestMapping(value = "/showItemInfo")
	public List<AgentItemInfo> showItemInfo(@RequestParam String brandcode) {
		List<AgentItemInfo> resultList = new ArrayList();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("brandcode", brandcode);
		try {
			resultList = agentItemInfoService.selectAgentItemInfo(paramMap);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return resultList;
	}

	@RequestMapping(value = "/showAllAgentInfo")
	@ResponseBody
	public Object showAllAgentInfo(HttpServletRequest __request,HttpServletResponse __response){
//	public Map<String, Object> showAllAgentInfo(@RequestParam String brandcode, @RequestParam String bannertype) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String,Object> object = super.getRequestParamters(__request);
		String brandcode = String.valueOf(object.get("brandcode"));
		String bannertype = String.valueOf(object.get("bannertype"));
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("brandcode", brandcode);
		List<AgentItemInfo> agentItemList = new ArrayList();
		List<AgentBannerInfo> agentBannerInfoList = new ArrayList();
		try {
			agentItemList = agentItemInfoService.selectAgentItemInfo(paramMap);
			paramMap.put("bannertype", bannertype);
			agentBannerInfoList = agentBannerInfoService.selectAgentBannerInfo(paramMap);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		resultMap.put("item", agentItemList);
		resultMap.put("banner", agentBannerInfoList);
		return resultMap;
	}

}