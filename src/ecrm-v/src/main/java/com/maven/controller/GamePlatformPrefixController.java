package com.maven.controller;

import java.util.ArrayList;
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

import com.maven.cache.SystemCache;
import com.maven.constant.Constant;
import com.maven.entity.EnterpriseGame;
import com.maven.entity.Game;
import com.maven.entity.GamePlatformPrefix;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.EnterpriseGameService;
import com.maven.service.GamePlatformPrefixService;
import com.maven.service.GameService;

@Controller
@RequestMapping("/GamePlatformPrefix")
public class GamePlatformPrefixController extends BaseController {
	
	private static LoggerManager log = LoggerManager.getLogger(
			EnterpriseOperatingBrandGameController.class.getName(), OutputManager.LOG_GAMEAPIINPUT);

	@Autowired
	private GamePlatformPrefixService gamePlatformPrefixService;

	@Autowired
	private GameService gameService;
	
	@Autowired
	private EnterpriseGameService enterpriseGameService;
	
	/**
	 * 查找数据
	 * 
	 * @return
	 */
	@RequestMapping("/data")
	public String data(HttpServletRequest request, HttpSession session, Model model) {
		try {
			String _sign = request.getParameter("sign");
			if (this.decodeSign(_sign, session.getId())) {
				String enterprisecode = _sign.split("_")[1];
				
				//查当前企业的前缀配置
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("enterprisecode", enterprisecode);
				List<GamePlatformPrefix> listGamePlatformPrefix = gamePlatformPrefixService.selectAll(params);
				Map<String, GamePlatformPrefix> mapGamePlatformPrefix = new HashMap<String, GamePlatformPrefix>();
				if(listGamePlatformPrefix != null && listGamePlatformPrefix.size() > 0) {
					for (GamePlatformPrefix gamePlatformPrefix : listGamePlatformPrefix) {
						mapGamePlatformPrefix.put(gamePlatformPrefix.getGamePlatform(), gamePlatformPrefix);
					}
				}
				
				//查当前企业授权的游戏
				List<Game> _all_game = gameService.getAllGame();
				Map<String,EnterpriseGame> _enterprise_game = enterpriseGameService.takeEnterpriseGame(enterprisecode);
				for (Game _m : _all_game) {
					if(_enterprise_game.get(_m.getGamecode())!=null){
						_m.setIschoice(true);
					}
				}
				
				model.addAttribute("enterprisecode", enterprisecode);
				model.addAttribute("listGames", _all_game);
				model.addAttribute("mapGamePlatformPrefix", mapGamePlatformPrefix);
				model.addAttribute(Constant.MENU_RELATION, request.getAttribute(Constant.MENU_RELATION));
						
				return "/enterprise/prefixsetting";
			} else {
				return Constant.PAGE_DECODEREFUSE;
			}
			
			
		} catch (Exception e) {
			log.Error(e.getMessage(),e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	@RequestMapping("/Save")
	@ResponseBody
//	@OperationLog(OparetionDescription.ENTERPRISE_API)
	public Map<String,Object> save(HttpServletRequest request, HttpSession session){
		try {
			String enterprisecode = request.getParameter("enterprisecode");
			String lsh = request.getParameter("lsh");
			String stype = request.getParameter("stype");
			String prefixcode = request.getParameter("prefixcode");
			String gamePlatform = request.getParameter("gamePlatform");
			
			GamePlatformPrefix item = null;
			if(stype.equals("add")) {
				item = new GamePlatformPrefix();
				item.setPrefixcode(prefixcode);	
				item.setGamePlatform(gamePlatform);
				item.setEnterprisecode(enterprisecode);
				gamePlatformPrefixService.saveGamePlatformPrefix(item);
				
				SystemCache.getInstance().setPlatformPrefix(enterprisecode, gamePlatform, prefixcode);//缓存
				
			} else {
				item = gamePlatformPrefixService.selectByPrimaryKey(lsh);
				item.setPrefixcode(prefixcode);	
				gamePlatformPrefixService.updateGamePlatformPrefix(item);
				
				SystemCache.getInstance().setPlatformPrefix(item.getEnterprisecode(), item.getGamePlatform(), prefixcode);//缓存
				
			}
			
			
			return this.packJSON(Constant.BooleanByte.YES,"操作成功");
		} catch (Exception e) {
			log.Error(e.getMessage(),e);
			return this.packJSON(Constant.BooleanByte.YES,"操作失败，请稍后尝试");
		}
	}

	@SuppressWarnings("unused")
	private List<EnterpriseGame> extractEnterpriseGames(String[] _arr_enterprisegames,String enterprisecode) {
		if(_arr_enterprisegames==null || _arr_enterprisegames.length==0) return null;
		List<EnterpriseGame> _list_enterprisegames = new ArrayList<EnterpriseGame>();
		for (String e : _arr_enterprisegames) {
			_list_enterprisegames.add(new EnterpriseGame(null, enterprisecode, e));
		}
		return _list_enterprisegames;
	}

	@Override
	public LoggerManager getLogger() {
		return log;
	}
}

