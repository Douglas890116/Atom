package com.maven.controller.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.cache.SystemCache;
import com.maven.constant.Constant;
import com.maven.constant.Enum_MSG;
import com.maven.controller.BaseController;
import com.maven.entity.EnterpriseBannerInfo;
import com.maven.entity.EnterpriseGame;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.Game;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.EnterpriseBannerInfoService;
import com.maven.service.EnterpriseGameService;
import com.maven.service.EnterpriseOperatingBrandService;
import com.maven.util.AttrCheckout;
import com.maven.util.StringUtils;

@Controller
@RequestMapping("/EnterpriseBrand")
public class EnterpriseBrandController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			EnterpriseBrandController.class.getName(), OutputManager.LOG_UER_ENTERPRISEBRAND);
	
	@Autowired
	private EnterpriseGameService enterpriseGameService;
	
	@Autowired
	private EnterpriseOperatingBrandService enterpriseOperatingBrandService;
	
	@Autowired
	private EnterpriseBannerInfoService enterpriseBannerInfoService;
	
	
	/**
	 * 企业品牌游戏
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/EBrandGame",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String EBGame(HttpServletRequest request){
		try {
			Map<String,Object> __object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(__object, false, new String[]{"enterprisecode","brandcode"});
			EnterpriseOperatingBrand __brand = enterpriseOperatingBrandService.takeBrandByPrimaryKey(String.valueOf(__object.get("brandcode")));
			if(__brand==null) return Enum_MSG.参数错误.message(Enum_MSG.该品牌不存在.desc);
			Map<String,EnterpriseGame>  __egame = enterpriseGameService.takeEnterpriseGame(String.valueOf(__object.get("enterprisecode")));
			List<Map<String,Object>> __ebgames = new ArrayList<Map<String,Object>>();
			for (String __gamecode : __egame.keySet()) {
				Game __game = SystemCache.getInstance().getGame(__gamecode);
				Map<String,Object> __gm = new HashMap<String, Object>();
				__gm.put("gametype", __game.getGametype());
				__gm.put("gamename", __game.getGamename());
				__gm.put("picid", __game.getPicid());
				__gm.put("h5", __game.getGametype()==null?false:true);
				__gm.put("iso", __game.getIso()==null?false:true);
				__gm.put("android", __game.getAndroid()==null?false:true);
				__gm.put("sort", __game.getSort());
				__gm.put("downloadurl", __game.getDownloadurl());
				__ebgames.add(__gm);
			}
			Map<String,Object> object = new HashMap<String, Object>();
			object.put("logopath", __brand.getLogopath());
			object.put("game", __ebgames);
			return Enum_MSG.成功.message(object);
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch(LogicTransactionRollBackException e){
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.失败.message(Constant.AJAX_DECODEREFUSE);
		}
	}
	

	
	/**
	 * 企业首页BANNER-PC/H5
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/banner",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String banner(HttpServletRequest request){
		try {
			Map<String,Object> __object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(__object, false, new String[]{"enterprisecode","brandcode"});
			
			EnterpriseBannerInfo enterpriseBannerInfo = new EnterpriseBannerInfo();
			enterpriseBannerInfo.setBrandcode(__object.get("brandcode").toString());
			enterpriseBannerInfo.setEnterprisecode(__object.get("enterprisecode").toString());
			enterpriseBannerInfo.setBannertype("PC");
			
			String bannertype = String.valueOf(__object.get("bannertype"));//传递H5或PC
			if(bannertype != null && !bannertype.equals("null") && !bannertype.trim().equals("")) {
				enterpriseBannerInfo.setBannertype(bannertype);
			}
			
			List<EnterpriseBannerInfo> list = enterpriseBannerInfoService.select(enterpriseBannerInfo);
			
			return Enum_MSG.成功.message(list);
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch(LogicTransactionRollBackException e){
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.失败.message(Constant.AJAX_DECODEREFUSE);
		}
	}
	
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
	
	public static void main(String[] args) {
		String Referer = "https://m.hyzonghe.net/EnterpriseBrand/banner?signature=d26c5ba5d232d0d96b4bdfe0151b68dd&params=8vpUvN8z%2BYQ%2BNKbKHzYXakCvk0L4bz5BzBDeHMSGrfU%3D&enterprisecode=EN003K";
		Referer = StringUtils.getURLDomain( Referer );
		if(Referer != null) {
			Referer = Referer.replaceAll("https://", "");
			Referer = Referer.replaceAll("http://", "");
		}
		if(Referer.toLowerCase().startsWith("m.")) {
			System.out.println("手机");
		}
		System.out.println(Referer);
	}
	
}
