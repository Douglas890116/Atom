package com.hy.pull.controller.manager.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hy.pull.common.util.MapUtil;
import com.hy.pull.mapper.TbGameTypeMapper;
import com.hy.pull.mapper.TbGameMapper;

import net.sf.json.JSONArray;


/**
 * 游戏类型管理控制中心
 * 创建日期 2016-10-08
 * @author temdy
 */
@Controller
@Scope("request")
@RequestMapping("/manager/type")
public class GameTypeController {
	@Autowired
	private TbGameMapper tbGameMapper;
	
	@Autowired
	private TbGameTypeMapper tbGameTypeMapper;
	
	/**
	 * 跳转页面操作
	 * @author temdy
	 * @param entity 提交表单集合
	 * @param request 请求对象
	 * @param response 响应对象
	 * @return 跳转URL
	 */
	@RequestMapping(value = "/list")
	public String list(@RequestParam Map<String, Object> entity, HttpServletRequest request, HttpServletResponse response) {
		try{
			String pageIndexName = new ParamEncoder("row").encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);		
			// 每页显示的条数   
	        int pageSize = 10;  
	        if (request.getParameter("pagesize") != null && !"".equals(request.getParameter("pagesize"))) {
	        	pageSize = Integer.parseInt(request.getParameter("pagesize"));
	        }
	        // 当前页  
	        int pageIndex = request.getParameter(pageIndexName) == null ? 0 : (Integer.parseInt(request.getParameter(pageIndexName))-1);
	        entity = MapUtil.resetMap(entity);
			pageIndex = pageIndex * pageSize;
			request.setAttribute("resultSize", tbGameTypeMapper.count(entity));
			entity.put("pageIndex", pageIndex);
			entity.put("pageSize", pageSize);
			List<Map<String,Object>> list = tbGameTypeMapper.selectByEntityLikePage(entity);	
			JSONArray array = JSONArray.fromObject(list);
			entity.put("json", array.toString());
			request.setAttribute("entity", entity);
			request.setAttribute("list", list);
			request.setAttribute("pageSize", pageSize);
			request.setAttribute("game", tbGameMapper.selectAll());
			Map<String,Object> ret = new HashMap<String,Object>();
			ret.put("GAME_KIND_PARENT", 0);
			request.setAttribute("kind", tbGameTypeMapper.selectByEntity(ret));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "/manager/game/type";
	}
	
	/**
	 * 添加操作
	 * @author temdy
	 * @param entity 提交表单集合
	 * @param request 请求对象
	 * @param response 响应对象
	 * @return msg 操作结果
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Object add(@RequestParam Map<String, Object> entity, HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> ret = new HashMap<String,Object>();
		ret.put("msg","操作失败！");
		try{			
			int rows = tbGameTypeMapper.insert(entity);	
			if(rows > 0){
				ret.put("msg","操作成功！");
				return ret;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return ret;
	}

	/**
	 * 删除操作
	 * @author temdy
	 * @param entity 提交表单集合
	 * @param request 请求对象
	 * @param response 响应对象
	 * @return msg 操作结果
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(@RequestParam Map<String, Object> entity, HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> ret = new HashMap<String,Object>();
		String msg = "操作失败！";
		try{
			int rows = tbGameTypeMapper.delete(entity);
			if(rows > 0){
				msg = "操作成功！";
			}
		}catch(Exception ex){
			ex.printStackTrace();
			msg = "操作失败";
		}
		ret.put("msg",msg);
		return ret;
	}

	/**
	 * 更新操作
	 * @author temdy
	 * @param entity 提交表单集合
	 * @param request 请求对象
	 * @param response 响应对象
	 * @return msg 操作结果
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Object update(@RequestParam Map<String, Object> entity, HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> ret = new HashMap<String,Object>();
		ret.put("msg","操作失败！");
		try{
			int rows = tbGameTypeMapper.update(entity);
			if(rows > 0){
				ret.put("msg","操作成功！");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return ret;
	}
}
