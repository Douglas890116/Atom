package com.hy.pull.controller.manager.pull;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hy.pull.common.util.MapUtil;
import com.hy.pull.mapper.TbGameKindMapper;
import com.hy.pull.mapper.TbGameTypeMapper;
import com.hy.pull.mapper.TbProxyKeyMapper;
import com.hy.pull.service.game.AOIGameService;
import com.hy.pull.service.game.AVGameService;
import com.hy.pull.service.game.BBINGameService;
import com.hy.pull.service.game.DZDYGameService;
import com.hy.pull.service.game.IBCGameService;
import com.hy.pull.service.game.MGGameService;
import com.hy.pull.service.game.NHQGameService;
import com.hy.pull.service.game.PTGameService;
import com.hy.pull.service.game.QPGameService;
import com.hy.pull.service.game.SAGameService;
import com.hy.pull.service.game.TAGGameService;
import com.hy.pull.service.game.ZJGameService;
import com.hy.pull.service.game.XCPGameService;
import com.hy.pull.service.game.TTGGameService;
import com.hy.pull.service.game.BBIN5GameService;

/**
 * 游戏数据拉取控制中心
 * 创建日期 2016-10-14
 * @author temdy
 */
@Controller
@Scope("request")
@RequestMapping("/manager/pull")
public class PullDataController {
	
	@Autowired
	private AOIGameService aOIGameService;
	
	@Autowired
	private TAGGameService tAGGameService;
	
	@Autowired
	private BBINGameService bBINGameService;
	
	@Autowired
	private NHQGameService nHQGameService;
	
	@Autowired
	private IBCGameService iBCGameService;
	
	@Autowired
	private SAGameService sAGameService;
	
	@Autowired
	private ZJGameService zJGameService;
	
	@Autowired
	private PTGameService pTGameService;
	
	@Autowired
	private AVGameService aVGameService;
	
	@Autowired
	private MGGameService mGGameService;
	
	@Autowired
	private TbProxyKeyMapper tbProxyKeyMapper;
	
	@Autowired
	private TbGameKindMapper tbGameKindMapper;
	
	@Autowired
	private TbGameTypeMapper tbGameTypeMapper;
	
	@Autowired
	private XCPGameService xCPGameService;
	
	@Autowired
	private TTGGameService tTGGameService;
	
	@Autowired
	private QPGameService qpGGameService;
	
	@Autowired
	private DZDYGameService dZDYGameService;
	
	@Autowired
	private BBIN5GameService bBIN5GameService;
	
	/**
	 * 跳转到游戏拉取数据页面
	 * @author temdy
	 * @param entity 提交表单集合
	 * @param request 请求对象
	 * @param response 响应对象
	 * @return 跳转URL
	 */
	@RequestMapping(value = "/toPull")
	public String toPull(@RequestParam Map<String, Object> entity, HttpServletRequest request, HttpServletResponse response) {
		entity.put("GAME_ID", "1");
		request.setAttribute("aoi", tbProxyKeyMapper.selectByEntity(entity));//设置东方游戏代理集合
		entity.put("GAME_ID", "2");
		request.setAttribute("ibc", tbProxyKeyMapper.selectByEntity(entity));//设置沙巴体育游戏代理集合
		entity.put("GAME_ID", "3");
		request.setAttribute("bbin", tbProxyKeyMapper.selectByEntity(entity));//设置波音游戏代理集合
		entity.put("GAME_KIND_PARENT", "0");
		request.setAttribute("bbinKind", tbGameKindMapper.selectByEntity(entity));//设置波音游戏种类集合
		request.setAttribute("gameType", tbGameTypeMapper.selectByEntity(entity));//设置波音游戏类型集合
		entity.put("GAME_ID", "4");
		request.setAttribute("nhq", tbProxyKeyMapper.selectByEntity(entity));//设置新环球游戏代理集合
		entity.put("GAME_ID", "5");
		request.setAttribute("sa", tbProxyKeyMapper.selectByEntity(entity));//设置沙龙游戏代理集合
		
		entity.put("GAME_ID", "6");
		request.setAttribute("tag", tbProxyKeyMapper.selectByEntity(entity));//设置TAG游戏代理集合
		request.setAttribute("tagKind", tbGameKindMapper.selectByEntity(entity));//设置TAG游戏种类集合
		
		entity.put("GAME_ID", "7");
		request.setAttribute("av", tbProxyKeyMapper.selectByEntity(entity));//设置AV游戏代理集合
		
		entity.put("GAME_ID", "8");
		request.setAttribute("pt", tbProxyKeyMapper.selectByEntity(entity));//设置PT游戏代理集合
		
		entity.put("GAME_ID", "9");
		request.setAttribute("zj", tbProxyKeyMapper.selectByEntity(entity));//设置ZJ游戏代理集合
		
		entity.put("GAME_ID", "10");
		request.setAttribute("mg", tbProxyKeyMapper.selectByEntity(entity));//设置MG游戏代理集合
		
		entity.put("GAME_ID", "11");
		request.setAttribute("xcp", tbProxyKeyMapper.selectByEntity(entity));//设置XCP游戏代理集合
		
		entity.put("GAME_ID", "12");
		request.setAttribute("ttg", tbProxyKeyMapper.selectByEntity(entity));//设置TTG游戏代理集合
		
		entity.put("GAME_ID", "13");
		request.setAttribute("qp", tbProxyKeyMapper.selectByEntity(entity));//设置棋牌游戏代理集合

		
		entity.put("GAME_ID", "14");
		request.setAttribute("bbin5", tbProxyKeyMapper.selectByEntity(entity));//设置波音EVEB游戏代理集合

		
		entity.put("GAME_ID", "15");
		request.setAttribute("dzpk", tbProxyKeyMapper.selectByEntity(entity));//设置德州扑克游戏代理集合
		return "/manager/pull/list";
	}	
	
	/**
	 * 拉取数据接口
	 * @author temdy
	 * @param entity 提交表单集合
	 * @param request 请求对象
	 * @param response 响应对象
	 * @return 结果{code:状态码（0、失败，1、成功）,count:数据行数}
	 */
	@RequestMapping(value = "/pullData")
	@ResponseBody
	public Object pullData(@RequestParam Map<String, Object> entity, HttpServletRequest request, HttpServletResponse response) {
		entity = MapUtil.resetMap(entity);
		Map<String,Object> ret = new HashMap<String,Object>();
		ret.put("code","0");//状态码0、失败，1、成功
		ret.put("count",0);//状态码0、失败，1、成功
		try{			
			String type = entity.get("type").toString();//获取拉取类型
			switch(type){
				case "aoi":
					ret.put("code","1");//状态码0、失败，1、成功
					ret.put("count",aOIGameService.pullData(entity));
					break;
				case "tag":
					if(entity.get("MAX_VALUE") != null){
						entity.put("MAX_VALUE", entity.get("MAX_VALUE").toString().concat(".xml"));
					}
					ret.put("count",tAGGameService.pullData(entity));
					ret.put("code","1");//状态码0、失败，1、成功
					break;
				case "bbin":
					ret.put("code","1");//状态码0、失败，1、成功
					ret.put("count",bBINGameService.pullData(entity));
					break;
				case "nhq":
					ret.put("count",nHQGameService.pullData(entity));
					ret.put("code","1");//状态码0、失败，1、成功
					break;
				case "sa":
					ret.put("code","1");//状态码0、失败，1、成功
					ret.put("count",sAGameService.pullData(entity));
					break;
				case "ibc":
					ret.put("count",iBCGameService.pullData(entity));
					ret.put("code","1");//状态码0、失败，1、成功
					break;
				case "pt":
					ret.put("code","1");//状态码0、失败，1、成功
					ret.put("count",pTGameService.pullData(entity));
					break;
				case "zj":
					ret.put("count",zJGameService.pullData(entity));
					ret.put("code","1");//状态码0、失败，1、成功
					break;
				case "av":
					ret.put("count",aVGameService.pullData(entity));
					ret.put("code","1");//状态码0、失败，1、成功
					break;
				case "mg":
					ret.put("count",mGGameService.pullData(entity));
					ret.put("code","1");//状态码0、失败，1、成功
					break;
				case "xcp":
					ret.put("count",xCPGameService.pullData(entity));
					ret.put("code","1");//状态码0、失败，1、成功
					break;
				case "ttg":
					ret.put("count",tTGGameService.pullData(entity));
					ret.put("code","1");//状态码0、失败，1、成功
					break;
				case "qp":
					ret.put("count",qpGGameService.pullData(entity));
					ret.put("code","1");//状态码0、失败，1、成功
					break;
				case "bbin5":
					ret.put("code","1");//状态码0、失败，1、成功
					ret.put("count",bBIN5GameService.pullData(entity));
					break;
				case "dzpk":
					ret.put("count",dZDYGameService.pullData(entity));
					ret.put("code","1");//状态码0、失败，1、成功
					break;
			}			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return ret;
	}
}
