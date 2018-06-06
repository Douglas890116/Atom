package com.maven.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.annotation.OperationLog;
import com.maven.cache.SystemCache;
import com.maven.constant.Constant;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseGame;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.EnterpriseOperatingBrandGame;
import com.maven.entity.Game;
import com.maven.entity.UserLogs;
import com.maven.logger.LoggerManager;
import com.maven.logger.OparetionDescription;
import com.maven.logger.OutputManager;
import com.maven.service.EnterpriseGameService;
import com.maven.service.EnterpriseOperatingBrandGameService;
import com.maven.service.EnterpriseOperatingBrandService;
import com.maven.service.UserLogsService;

@Controller
@RequestMapping("/brandGame")
public class EnterpriseOperatingBrandGameController extends BaseController {

	private static LoggerManager log = LoggerManager.getLogger(EnterpriseOperatingBrandGameController.class.getName(),
			OutputManager.LOG_ENTERPRISEOPERATINGBRANDGAME);

	/** 品牌 */
	@Autowired
	private EnterpriseOperatingBrandService enterpriseOperatingBrandService;
	/** 品牌游戏中间表 */
	@Autowired
	private EnterpriseOperatingBrandGameService enterpriseOperatingBrandGameService;
	/** 企业游戏 */
	@Autowired
	private EnterpriseGameService enterpriseGameService;
	@Autowired
	private UserLogsService userLogsService;
	/**
	 * 跳转到品牌游戏主页面
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "/brandgame/index";
	}

	/**
	 * 查询品牌
	 * 
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String, Object> queryBrandGame(HttpServletRequest request, HttpSession session) {
		Map<String, Object> map = getRequestParamters(request);
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			map.put("enterprisecode", ee.getEnterprisecode());
			List<EnterpriseOperatingBrand> list = enterpriseOperatingBrandService.getAllBrand(map);
			int count = enterpriseOperatingBrandService.getAllBrandCount(map);
			return super.formatPagaMap(list, count);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 加载所有游戏
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/loadGame")
	@ResponseBody
	public Map<String, Object> loadGame(HttpServletRequest request,HttpSession session) {
		try {
			EnterpriseOperatingBrand brand = super.getRequestParamters(request, EnterpriseOperatingBrand.class);
			if(super.decodeSign(brand.getBrandcode(), session.getId())
					&&super.decodeSign(brand.getEnterprisecode(), session.getId())){
				String _enterprisecode = brand.getEnterprisecode().split("_")[1];
				String _brandcode = brand.getBrandcode().split("_")[1];
				Map<String,EnterpriseGame>  _map_enterprise_games = enterpriseGameService.takeEnterpriseGame(_enterprisecode);
				
				List<EnterpriseOperatingBrandGame> _list_brandgames = enterpriseOperatingBrandGameService.takeBrandGames(new EnterpriseOperatingBrandGame(_brandcode));
				Map<String,Integer> _map_brandgames = new HashMap<String, Integer>();
				for (EnterpriseOperatingBrandGame e : _list_brandgames) {
					_map_brandgames.put(e.getGamecode(), 1);
					e.setGamename(SystemCache.getInstance().getGame(e.getGamecode()).getGamename());
				}
				
//				List<Game> _list_games = new ArrayList<Game>();
//				for (String s : _map_enterprise_games.keySet()) {
//					Game _me = SystemCache.getInstance().getGame(s).clone();
//					if(_map_brandgames.get(s)!=null){
//						_me.setIschoice(true);
//					}
//					_list_games.add(_me);
//				}
				return super.formatPagaMap(_list_brandgames, 100);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return super.formatPagaMap(null, 0);
	}
	
	/**
	 * 更新品牌需要开放的游戏=开关状态
	 * 
	 * @param request
	 */
	@RequestMapping("/updateBrandGame")
	@ResponseBody
	@OperationLog(OparetionDescription.ENTERPRISEOPERATINGBRAND_GAMESWITCH)
	public Map<String, Object> updateBrandGame(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee __enterpriseEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			String gamecode = request.getParameter("gamecode");
			String brandcode = request.getParameter("brandcode");
			String flag = request.getParameter("flag");
			
//			List<EnterpriseOperatingBrandGame> list = new ArrayList<EnterpriseOperatingBrandGame>();
//			list.add(new EnterpriseOperatingBrandGame(brandcode, gamecode, Integer.valueOf(flag)));
			
			if(EnterpriseOperatingBrandGame.Enum_flag.停用.value.intValue() == Integer.valueOf(flag).intValue()) {
				userLogsService.addActivityBetRecord(new UserLogs(__enterpriseEmployee.getEnterprisecode(), __enterpriseEmployee.getEmployeecode(), null, 
					     UserLogs.Enum_operatype.品牌信息业务, "品牌游戏关闭:"+gamecode, __enterpriseEmployee.getLoginaccount(), null));
			} else {
				userLogsService.addActivityBetRecord(new UserLogs(__enterpriseEmployee.getEnterprisecode(), __enterpriseEmployee.getEmployeecode(), null, 
					     UserLogs.Enum_operatype.品牌信息业务, "品牌游戏开放:"+gamecode, __enterpriseEmployee.getLoginaccount(), null));
			}
			
//			enterpriseOperatingBrandGameService.tc_brandGameAccredit(brandcode, list);
			enterpriseOperatingBrandGameService.update(new EnterpriseOperatingBrandGame(brandcode, gamecode, Integer.valueOf(flag)));
			
			return super.packJSON(Constant.BooleanByte.YES, "操作成功");
			
			
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
		}
	}

	/**
	 * 保存品牌需要开放的游戏
	 * 
	 * @param request
	 */
	@RequestMapping("/saveBrandGame")
	@ResponseBody
	@OperationLog(OparetionDescription.ENTERPRISEOPERATINGBRAND_GAMESWITCH)
	public Map<String, Object> saveBrandGame(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee __enterpriseEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			String brandcode = request.getParameter("brandcode");
			if (super.decodeSign(brandcode, session.getId())) {
				brandcode = brandcode.split("_")[1];
				List<EnterpriseOperatingBrandGame> list = new ArrayList<EnterpriseOperatingBrandGame>();
				if(StringUtils.isNotBlank(request.getParameter("gamecode"))){
					String gamecodeArray[] = request.getParameter("gamecode").split(",");
					for (int i = 0; i < gamecodeArray.length; i++) {
						list.add(new EnterpriseOperatingBrandGame(brandcode, gamecodeArray[i], EnterpriseOperatingBrandGame.Enum_flag.正常.value));
						
						userLogsService.addActivityBetRecord(new UserLogs(__enterpriseEmployee.getEnterprisecode(), __enterpriseEmployee.getEmployeecode(), null, 
							     UserLogs.Enum_operatype.品牌信息业务, "保存品牌需要开放的游戏:"+gamecodeArray[i], __enterpriseEmployee.getLoginaccount(), null));
						
					}
				}
				enterpriseOperatingBrandGameService.tc_brandGameAccredit(brandcode, list);
				return super.packJSON(Constant.BooleanByte.YES, "操作成功");
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
		}
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
